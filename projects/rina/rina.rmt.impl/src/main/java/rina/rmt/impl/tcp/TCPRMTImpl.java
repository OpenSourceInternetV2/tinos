package rina.rmt.impl.tcp;

import java.io.IOException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import rina.ipcprocess.api.IPCProcess;
import rina.ipcservice.api.ApplicationProcessNamingInfo;
import rina.ipcservice.api.IPCException;
import rina.ipcservice.api.QoSParameters;
import rina.rmt.api.RMT;

/**
 * Specifies the interface of the Relaying and Multiplexing task. Mediates the access to one or more (N-1) DIFs 
 * or physical media
 * @author eduardgrasa
 */
public class TCPRMTImpl implements RMT{
	private static final Log log = LogFactory.getLog(TCPRMTImpl.class);
	
	private Map<String, Socket> forwardingTable = null;
	
	private IPCProcess ipcProcess = null;
	
	/**
	 * The thread pool implementation
	 */
	private ExecutorService executorService = null;
	
	/**
	 * The maximum number of worker threads in the RMT Thread pool
	 * (1 for listening to incoming connections + MAX-1 for reading 
	 * data from sockets)
	 */
	private static int MAXWORKERTHREADS = 10;
	
	/**
	 * The server that will listen for incoming connections to this RMT
	 */
	private RMTServer rmtServer = null;

	public TCPRMTImpl(){
		this(RMTServer.DEFAULT_PORT);
	}
	
	public TCPRMTImpl(int port){
		this.forwardingTable = new Hashtable<String, Socket>();
		this.executorService = Executors.newFixedThreadPool(MAXWORKERTHREADS);
		this.rmtServer = new RMTServer(this, port);
		executorService.execute(rmtServer);
	}
	
	public void setIPCProcess(IPCProcess ipcProcess) {
		this.ipcProcess = ipcProcess;
	}

	/**
	 * When the RMT receives an EFCP PDU via a send primitive, it inspects the destination 
	 * address field and the connection-id field of the PDU. Using the FIB, it determines 
	 * which queue, the PDU should be placed on
	 * @param pdu
	 */
	public synchronized void sendEFCPPDU(byte[] pdu) {
		//It will never be called by this implementation since DTP is not implemented yet and 
		//each flow allocation triggers a new TCP connection
	}
	
	/**
	 * Cause the RMT to allocate a new flow through an N-1 DIF or the underlying
	 * physical media
	 * @param apNamingInfo the destination application process naming information 
	 * @param qosparams the quality of service requested by the flow
	 * @return int the portId allocated to the flow
	 * @throws Exception if there was an issue allocating the flow
	 */
	public int allocateFlow(ApplicationProcessNamingInfo apNamingInfo, QoSParameters qosparams) throws Exception{
		String host = apNamingInfo.getApplicationProcessName();
		int port = RMTServer.DEFAULT_PORT;
		
		if (apNamingInfo.getApplicationProcessInstance() != null){
			try{
				port = Integer.parseInt(apNamingInfo.getApplicationProcessInstance());
			}catch(NumberFormatException ex){
				ex.printStackTrace();
				port = RMTServer.DEFAULT_PORT;
			}
		}
		
		Socket socket = new Socket(host, port);
		newConnectionAccepted(socket);
		return socket.getPort();
	}

	/**
	 * Send a CDAP message to the IPC process address identified by the 'address' parameter. 
	 * This operation is invoked by the management tasks of the IPC process, usually to 
	 * send CDAP messages to the nearest neighbors. The RMT will lookup the 'address' 
	 * parameter in the forwarding table, and send the capMessage using the management flow 
	 * that was established when this IPC process joined the DIF.
	 * @param address
	 * @param cdapMessage
	 * @throws IPCException
	 */
	public synchronized void sendCDAPMessage(byte[] address, byte[] cdapMessage) {
		String socketAddress = new String(address);
		Socket socket = forwardingTable.get(socketAddress);
		byte[] delimitedSdu = ipcProcess.getDelimiter().getDelimitedSdu(cdapMessage);
		try{
			socket.getOutputStream().write(delimitedSdu);
			log.info("Sent PDU "+printBytes(delimitedSdu));
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * When a remote IPC process connects to the RMT of this IPC Process this operation is called.
	 * It will add the socket to the forwarding table and start a new Thread to read data from the
	 * socket
	 * @param socket
	 */
	public void newConnectionAccepted(Socket socket){
		forwardingTable.put(socket.getInetAddress().getHostAddress(), socket);
		TCPSocketReader tcpSocketReader = new TCPSocketReader(socket, ipcProcess.getRibDaemon(), ipcProcess.getDelimiter());
		executorService.execute(tcpSocketReader);
	}
	
	private String printBytes(byte[] message){
		String result = "";
		for(int i=0; i<message.length; i++){
			result = result + String.format("%02X", message[i]) + " ";
		}
		
		return result;
	}
}