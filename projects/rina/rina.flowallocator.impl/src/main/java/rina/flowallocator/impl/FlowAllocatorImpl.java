package rina.flowallocator.impl;

import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import rina.cdap.api.BaseCDAPSessionManager;
import rina.cdap.api.CDAPSessionManager;
import rina.cdap.api.message.CDAPMessage;
import rina.cdap.api.message.ObjectValue;
import rina.encoding.api.BaseEncoder;
import rina.encoding.api.Encoder;
import rina.flowallocator.api.BaseFlowAllocator;
import rina.flowallocator.api.DirectoryForwardingTable;
import rina.flowallocator.api.FlowAllocatorInstance;
import rina.flowallocator.api.QoSCube;
import rina.flowallocator.api.message.Flow;
import rina.flowallocator.impl.ribobjects.DirectoryForwardingTableEntrySetRIBObject;
import rina.flowallocator.impl.ribobjects.FlowSetRIBObject;
import rina.flowallocator.impl.tcp.TCPServer;
import rina.flowallocator.impl.validation.AllocateRequestValidator;
import rina.ipcprocess.api.IPCProcess;
import rina.ipcservice.api.FlowService;
import rina.ipcservice.api.IPCException;
import rina.ribdaemon.api.BaseRIBDaemon;
import rina.ribdaemon.api.RIBDaemon;
import rina.ribdaemon.api.RIBDaemonException;
import rina.ribdaemon.api.RIBObject;
import rina.ribdaemon.api.SimpleSetRIBObject;
import rina.utils.types.Unsigned;

/** 
 * Implements the Flow Allocator
 */
public class FlowAllocatorImpl extends BaseFlowAllocator{
	
	private static final Log log = LogFactory.getLog(FlowAllocatorImpl.class);

	/**
	 * Flow allocator instances, each one associated to a port_id
	 */
	private Map<Integer, FlowAllocatorInstance> flowAllocatorInstances = null;
	
	/**
	 * Validates allocate requests
	 */
	private AllocateRequestValidator allocateRequestValidator = null;
	
	/**
	 * The RIB Daemon
	 */
	private RIBDaemon ribDaemon = null;
	
	/**
	 * The Encoder
	 */
	private Encoder encoder = null;
	
	/**
	 * Will wait for incoming data connections
	 */
	private TCPServer tcpServer = null;
	
	/**
	 * The directory forwarding table
	 */
	private DirectoryForwardingTable directoryForwardingTable = null;

	/**
	 * The minimum value of a port id not generated by the sockets API
	 * (It has to be greater than 65535 to avoid colliding with the OS 
	 * generated socket number)
	 */
	private static int MIN_PORT_ID_VALUE = 1;
	
	/**
	 * Used for the generation of port ids
	 */
	private int portIdCounter = MIN_PORT_ID_VALUE;
	
	/**
	 * Stores the list of pending Sockets for which a 
	 * M_CREATE message for a Flow object still has not arrived
	 */
	private Map<Integer, Socket> pendingSockets = null;
	
	private CDAPSessionManager cdapSessionManager = null;
	
	public FlowAllocatorImpl(){
		allocateRequestValidator = new AllocateRequestValidator();
		flowAllocatorInstances = new HashMap<Integer, FlowAllocatorInstance>();
		tcpServer = new TCPServer(this);
		pendingSockets = new Hashtable<Integer, Socket>();
	}
	
	@Override
	public void setIPCProcess(IPCProcess ipcProcess){
		super.setIPCProcess(ipcProcess);
		this.ribDaemon = (RIBDaemon) getIPCProcess().getIPCProcessComponent(BaseRIBDaemon.getComponentName());
		this.encoder = (Encoder) getIPCProcess().getIPCProcessComponent(BaseEncoder.getComponentName());
		this.cdapSessionManager = (CDAPSessionManager) getIPCProcess().getIPCProcessComponent(BaseCDAPSessionManager.getComponentName());
		this.directoryForwardingTable = new DirectoryForwardingTableImpl(this.ribDaemon);
		populateRIB(ipcProcess);
		ipcProcess.execute(tcpServer);
	}
	
	/**
	 * Returns the directory
	 * @return
	 */
	public DirectoryForwardingTable getDirectoryForwardingTable(){
		return this.directoryForwardingTable;
	}
	
	/**
	 * Called by the flow allocator instance when it finishes to cleanup the state.
	 * @param portId
	 */
	public synchronized void removeFlowAllocatorInstance(int portId){
		this.flowAllocatorInstances.remove(new Integer(portId));
	}
	
	/**
	 * Closes all the sockets and stops
	 */
	@Override
	public void stop(){
		this.tcpServer.setEnd(true);
	}
	
	private void populateRIB(IPCProcess ipcProcess){
		try{
			RIBObject ribObject = new FlowSetRIBObject(this, ipcProcess);
			ribDaemon.addRIBObject(ribObject);
		    ribObject = new SimpleSetRIBObject(ipcProcess, 
					QoSCube.QOSCUBE_SET_RIB_OBJECT_NAME, 
					QoSCube.QOSCUBE_SET_RIB_OBJECT_CLASS, 
					QoSCube.QOSCUBE_RIB_OBJECT_CLASS);
			ribDaemon.addRIBObject(ribObject);
			ribObject = new DirectoryForwardingTableEntrySetRIBObject(ipcProcess);
			ribDaemon.addRIBObject(ribObject);
		}catch(RIBDaemonException ex){
			ex.printStackTrace();
			log.error("Could not subscribe to RIB Daemon:" +ex.getMessage());
		}
	}
	
	public Map<Integer, Socket> getPendingSockets(){
		return this.pendingSockets;
	}
	
	/**
	 * The Flow Allocator TCP server notifies that a new TCP 
	 * data flow has been accepted. This operation has to read the remote 
	 * port id and either create a Flow Allocator instance or pass the 
	 * information to an existing one.
	 * @param socket
	 */
	public void newConnectionAccepted(Socket socket){
		byte[] buffer = new byte[2];
		int tcpRendezvousId = -1;
		try{
			int dataRead = socket.getInputStream().read(buffer);
			if (dataRead <1){
				//TODO fix this
				throw new Exception();
			}
			Unsigned unsigned = new Unsigned(2);
			unsigned.setValue(buffer);
			tcpRendezvousId = new Long(unsigned.getValue()).intValue();
			synchronized(pendingSockets){
				pendingSockets.put(new Integer(tcpRendezvousId), socket);
				notifyFlowAllocatorInstanceIfExists(tcpRendezvousId, socket);
			}
			log.debug("The TCP Rendez-vous Id is: "+tcpRendezvousId);
		}catch(Exception ex){
			log.error("Accepted incoming TCP connection, but could not read the TCP Rendez-vous Id, closing the socket.");
			try{
				socket.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * If a Flow Allocator instance is waiting for this socket, this operation will 
	 * find it and notify about the socket having arrived, so it can procede with the
	 * flow allocation procedure
	 * @param portId
	 * @param socket
	 */
	private void notifyFlowAllocatorInstanceIfExists(int portId, Socket socket){
		Iterator<Integer> iterator = flowAllocatorInstances.keySet().iterator();
		FlowAllocatorInstance flowAllocatorInstance = null;
		
		while(iterator.hasNext()){
			flowAllocatorInstance = flowAllocatorInstances.get(iterator.next());
			if (flowAllocatorInstance.getFlow().getTcpRendezvousId() == portId){
				flowAllocatorInstance.setSocket(socket);
			}
		}
		
		//TODO, if it is not there, add a timer to avoid having the socket being 
		//stored and open forever
	}
	
	/**
	 * Generates a portid for the flow allocation
	 * @return a valid portId
	 */
	private int generatePortId(){
		if (portIdCounter == Integer.MAX_VALUE){
			portIdCounter = MIN_PORT_ID_VALUE;
		}else{
			portIdCounter++;
		}
		
		while(portIdAlreadyInUse(portIdCounter)){
			portIdCounter++;
		}
		
		return portIdCounter;
	}
	
	/**
	 * Checks if a portId is already in use
	 * @param candidate
	 * @return
	 */
	private boolean portIdAlreadyInUse(int candidate){
		return flowAllocatorInstances.get(new Integer(candidate)) != null;
	}
	
	/**
	 * When an Flow Allocator receives a Create_Request PDU for a Flow object, it consults its local Directory to see if it has an entry.
	 * If there is an entry and the address is this IPC Process, it creates an FAI and passes the Create_request to it.If there is an 
	 * entry and the address is not this IPC Process, it forwards the Create_Request to the IPC Process designated by the address.
	 * @param cdapMessage
	 * @param underlyingPortId
	 */
	public void createFlowRequestMessageReceived(CDAPMessage cdapMessage, int underlyingPortId){
		Flow flow = null;
		long myAddress = 0;
		
		try{
			flow = (Flow) encoder.decode(cdapMessage.getObjValue().getByteval(), Flow.class);
		}catch (Exception ex){
			ex.printStackTrace();
			return;
		}
	
		long address = directoryForwardingTable.getAddress(flow.getDestinationNamingInfo());
		myAddress = this.getIPCProcess().getAddress().longValue();
		
		if (address == 0){
			//error, the table should have at least returned a default IPC process address to continue looking for the application process
			log.error("The directory forwarding table returned no entries when looking up " + flow.getDestinationNamingInfo().toString());
			return;
		}
		
		if (address == myAddress){
			//There is an entry and the address is this IPC Process, create a FAI, extract the Flow object from the CDAP message and
			//call the FAI
			int portId = generatePortId();
			log.debug("The destination application process is reachable through me. Assigning the local portId "+portId+" to the flow allocation.");
			FlowAllocatorInstance flowAllocatorInstance = new FlowAllocatorInstanceImpl(this.getIPCProcess(), this, cdapSessionManager, portId);
			flowAllocatorInstance.createFlowRequestMessageReceived(flow, cdapMessage, underlyingPortId);
			flowAllocatorInstances.put(new Integer(new Integer(portId)), flowAllocatorInstance);
			//Check if the socket was already established
			log.debug("Looking for the socket associated to TCP rendez-vous Id "+flow.getTcpRendezvousId());
			Socket socket = pendingSockets.remove(new Integer((int)flow.getTcpRendezvousId()));
			if (socket != null){
				flowAllocatorInstance.setSocket(socket);
			}

			//TODO, if socket is null, add a timer to avoid waiting forever. Upon expiration, send a negative M_CREATE response
			log.debug("Could not find a socket associated to TCP rendez-vous Id "+flow.getTcpRendezvousId()+ ". Waiting for it.");
			return;
		}
		
		//The address is not this IPC process, forward the CDAP message to that address increment the hop count of the Flow object
		//extract the flow object from the CDAP message
		flow.setHopCount(flow.getHopCount() - 1);
		if (flow.getHopCount()  <= 0){
			//TODO send negative create Flow response CDAP message to the source IPC process, specifying that the application process
			//could not be found before the hop count expired
		}

		try{
			int destinationPortId = Utils.mapAddressToPortId(address, this.getIPCProcess());
			RIBDaemon ribDaemon = (RIBDaemon) getIPCProcess().getIPCProcessComponent(BaseRIBDaemon.getComponentName());
			ObjectValue objectValue = new ObjectValue();
			objectValue.setByteval(encoder.encode(flow));
			cdapMessage.setObjValue(objectValue);
			ribDaemon.sendMessage(cdapMessage, destinationPortId, null);
		}catch(Exception ex){
			//Error that has to be fixed, we cannot continue, log it and return
			log.error("Fatal error when serializing a Flow object. " +ex.getMessage());
			return;
		}
	}
	
	/**
	 * Called by the flow allocator instance when a request for a local flow is received
	 * @param flowService
	 * @param objectName
	 * @throws IPCException
	 */
	public synchronized void receivedLocalFlowRequest(FlowService flowService, String objectName) throws IPCException{
		int portId = generatePortId();
		FlowAllocatorInstance flowAllocatorInstance = new FlowAllocatorInstanceImpl(this.getIPCProcess(), this, portId);
		FlowService clonedFlowService = new FlowService();
		clonedFlowService.setSourceAPNamingInfo(flowService.getSourceAPNamingInfo());
		clonedFlowService.setDestinationAPNamingInfo(flowService.getDestinationAPNamingInfo());
		clonedFlowService.setQoSSpecification(flowService.getQoSSpecification());
		clonedFlowService.setPortId(flowService.getPortId());
		flowAllocatorInstance.receivedLocalFlowRequest(clonedFlowService, objectName);
		flowAllocatorInstances.put(new Integer(new Integer(portId)), flowAllocatorInstance);
	}
	
	/**
	 * Called by the flow allocator instance when a response for a local flow is received
	 * @param portId
	 * @param flowService
	 * @param result
	 * @param resultReason
	 * @throws IPCException
	 */
	public void receivedLocalFlowResponse(int portId, int remotePortId, boolean result, String resultReason) throws IPCException{
		FlowAllocatorInstance flowAllocatorInstance = getFlowAllocatorInstance(portId);
		flowAllocatorInstance.receivedLocalFlowResponse(remotePortId, result, resultReason);
		if (!result){
			flowAllocatorInstances.remove(new Integer(portId));
		}
	}
	
	/**
	 * Validate the request, create a Flow Allocator Instance and forward it the request for further processing
	 * @param allocateRequest
	 * @param applicationProcess
	 * @throws IPCException
	 */
	public synchronized int submitAllocateRequest(FlowService flowService) throws IPCException{
		log.debug("Local application invoked allocate request: "+flowService.toString());
		allocateRequestValidator.validateAllocateRequest(flowService);
		int portId = generatePortId();
		flowService.setPortId(portId);
		FlowAllocatorInstance flowAllocatorInstance = new FlowAllocatorInstanceImpl(this.getIPCProcess(), this, cdapSessionManager, portId);
		flowAllocatorInstance.submitAllocateRequest(flowService);
		flowAllocatorInstances.put(new Integer(portId), flowAllocatorInstance);
		return portId;
	}

	/**
	 * Forward the call to the right FlowAllocator Instance. If the application process 
	 * rejected the flow request, remove the flow allocator instance from the list of 
	 * active flow allocator instances
	 * @param portId
	 * @param success
	 */
	public synchronized void submitAllocateResponse(int portId, boolean success, String reason) throws IPCException{
		log.debug("Local application invoked allocate response for portId "+portId+" with result "+success);
		FlowAllocatorInstance flowAllocatorInstance = getFlowAllocatorInstance(portId);
		flowAllocatorInstance.submitAllocateResponse(success, reason);
		if (!success){
			flowAllocatorInstances.remove(portId);
		}
	}

	/**
	 * Forward the deallocate request to the Flow Allocator Instance.
	 * @param portId
	 */
	public synchronized void submitDeallocate(int portId) throws IPCException{
		log.debug("Local application invoked deallocate request for flow at portId "+portId);
		FlowAllocatorInstance flowAllocatorInstance = getFlowAllocatorInstance(portId);
		flowAllocatorInstance.submitDeallocate();
	}
	
	/**
	 * Request to deallocate a local flow
	 * @param portId
	 * @throws IPCException
	 */
	public void receivedDeallocateLocalFlowRequest(int portId) throws IPCException{
		FlowAllocatorInstance flowAllocatorInstance = getFlowAllocatorInstance(portId);
		flowAllocatorInstance.receivedDeallocateLocalFlowRequest();
	}
	
	/**
	 * Sends an SDU through the flow identified by portId
	 * This function is just for the RINA prototype over TCP. When DTP and DTCP are implemented
	 * this operation will be removed from here.
	 * @param portId
	 * @param sdu
	 * @throws IPCException
	 */
	public void submitTransfer(int portId, byte[] sdu) throws IPCException{
		FlowAllocatorInstance flowAllocatorInstance = getFlowAllocatorInstance(portId);
		flowAllocatorInstance.submitTransfer(sdu);
	}
	
	/**
	 * Returns the flow allocator instance that manages the flow identified by portId
	 * @param portId
	 * @return
	 * @throws IPCException
	 */
	private FlowAllocatorInstance getFlowAllocatorInstance(int portId) throws IPCException{
		FlowAllocatorInstance flowAllocatorInstance = flowAllocatorInstances.get(new Integer(portId));
		if (flowAllocatorInstance == null){
			throw new IPCException(IPCException.NO_FLOW_ALLOCATOR_INSTANCE_FOR_THIS_PORTID_CODE , 
					IPCException.NO_FLOW_ALLOCATOR_INSTANCE_FOR_THIS_PORTID);
		}
		
		return flowAllocatorInstance;
	}
}