package rina.rmt.impl.tcp.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import rina.cdap.api.CDAPMessageHandler;
import rina.cdap.api.CDAPSessionDescriptor;
import rina.cdap.api.CDAPSessionManager;
import rina.cdap.api.message.CDAPMessage;
import rina.cdap.api.message.CDAPMessage.Opcode;
import rina.ipcprocess.api.IPCProcess;
import rina.ribdaemon.api.MessageSubscriber;
import rina.ribdaemon.api.MessageSubscription;
import rina.ribdaemon.api.RIBDaemon;
import rina.ribdaemon.api.RIBDaemonException;
import rina.ribdaemon.api.RIBObject;
import rina.ribdaemon.api.UpdateStrategy;
import rina.rmt.api.RMT;

public class FakeRIBDaemon implements RIBDaemon {
	
	private static final Log log = LogFactory.getLog(FakeRIBDaemon.class);

	private IPCProcess ipcProcess = null;
	
	private boolean messageReceived = false;
	
	public void setIPCProcess(IPCProcess ipcProcess) {
		this.ipcProcess = ipcProcess;
	}

	public void cdapMessageDelivered(byte[] message, int portId) {
		log.info("Received message: " +printBytes(message));
		String decodedMessage = new String(message);
		log.info("Decoded message: "+decodedMessage);
		messageReceived = true;
		
		RMT rmt = (RMT) ipcProcess.getIPCProcessComponent(RMT.class.getName());
		try {
			rmt.sendCDAPMessage(portId, "CDAP message is coming back".getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isMessageReceived(){
		return messageReceived;
	}

	public Object read(String arg0, long arg1, String arg2, Object arg3)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0, long arg1, String arg2)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void sendMessages(CDAPMessage[] arg0, UpdateStrategy arg1) {
		// TODO Auto-generated method stub
		
	}

	public void subscribeToMessages(MessageSubscription arg0,
			MessageSubscriber arg1) throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void unsubscribeFromMessages(MessageSubscription arg0,
			MessageSubscriber arg1) throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void write(String arg0, long arg1, String arg2, Object arg3)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}
	
	private String printBytes(byte[] message){
		String result = "";
		for(int i=0; i<message.length; i++){
			result = result + String.format("%02X", message[i]) + " ";
		}
		
		return result;
	}

	public void setCDAPSessionManager(CDAPSessionManager arg0) {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return RIBDaemon.class.getName();
	}

	public void flowDeallocated(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void processOperation(CDAPMessage arg0, CDAPSessionDescriptor arg1)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public Object processOperation(Opcode arg0, String arg1, String arg2,
			long arg3, Object arg4) throws RIBDaemonException {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendMessage(CDAPMessage arg0, int arg1, CDAPMessageHandler arg2)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void cancelRead(String arg0, String arg1, long arg2, Object arg3)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void create(String arg0, String arg1, long arg2, Object arg3)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void delete(String arg0, String arg1, long arg2, Object arg3)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public Object read(String arg0, String arg1, long arg2)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		return null;
	}

	public void start(String arg0, String arg1, long arg2, Object arg3)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void stop(String arg0, String arg1, long arg2, Object arg3)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void write(String arg0, String arg1, long arg2, Object arg3)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void addRIBObject(RIBObject arg0) throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public void removeRIBObject(RIBObject arg0, String arg1)
			throws RIBDaemonException {
		// TODO Auto-generated method stub
		
	}

	public List<RIBObject> getRIBObjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
