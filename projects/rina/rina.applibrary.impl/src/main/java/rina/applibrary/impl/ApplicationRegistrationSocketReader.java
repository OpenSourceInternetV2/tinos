package rina.applibrary.impl;

import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import rina.delimiting.api.BaseSocketReader;
import rina.delimiting.api.Delimiter;

/**
 * Reads the socket used to register an unregister the application with the 
 * local RINA software stack
 * @author eduardgrasa
 *
 */
public class ApplicationRegistrationSocketReader extends BaseSocketReader{
	
	private static final Log log = LogFactory.getLog(ApplicationRegistrationSocketReader.class);
	
	/**
	 * The queue to send back the M_CREATE_R and M_DELETE_R CDAP messages 
	 * to the ApplicationRegistrationImpl class
	 */
	private BlockingQueue<byte[]> registrationQueue = null;
	
	public ApplicationRegistrationSocketReader(Socket socket, Delimiter delimiter, 
			BlockingQueue<byte[]> registrationQueue) {
		super(socket, delimiter);
		this.registrationQueue = registrationQueue;
	}

	@Override
	public void processPDU(byte[] pdu) {
		log.debug("Received pdu: " + printBytes(pdu));
		try{
			this.registrationQueue.put(pdu);
		}catch(InterruptedException ex){
			ex.printStackTrace();
			//TODO what to do?
		}
	}

	@Override
	public void socketDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
