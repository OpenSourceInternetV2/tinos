package rina.enrollment.impl.statemachines;

import rina.applicationprocess.api.ApplicationProcessNameSynonym;
import rina.cdap.api.CDAPSessionDescriptor;
import rina.cdap.api.message.CDAPMessage;

/**
 * Interface that all the enrollment state machines have to implement
 * @author eduardgrasa
 *
 */
public interface EnrollmentStateMachine {
	
	/**
	 * All the possible states of all the enrollment state machines
	 */
	public enum State {NULL, READ_ADDRESS, INITIALIZE_NEW_MEMBER, INITIALIZE_NEW_MEMBER_SEND_RESPONSE, 
		WAITING_FOR_STARTUP, ENROLLED, WAITING_CONNECTION, WAITING_READ_ADDRESS, INITIALIZING_DATA};
	
	/**
	 * An M_CONNECT message has been received
	 * @param cdapMessage
	 * @param cdapSessionDescriptor
	 */
	public void connect(CDAPMessage cdapMessage, int portId);
	
	/**
	 * Called by the EnrollmentTask when it got an M_CONNECT_R message
	 * @param cdapMessage
	 * @param cdapSessionDescriptor
	 */
	public void connectResponse(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor);
	
	/**
	 * Called by the EnrollmentTask when it got an M_RELEASE message
	 * @param cdapMessage
	 * @param cdapSessionDescriptor
	 */
	public void release(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor);
	
	/**
	 * Called by the EnrollmentTask when it got an M_RELEASE_R message
	 * @param cdapMessage
	 * @param cdapSessionDescriptor
	 */
	public void releaseResponse(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor);
	
	/**
	 * The state of this enrollment state machine
	 * @return
	 */
	public State getState();
	
	/**
	 * Called by the DIFMembersSetObject to initiate the enrollment sequence 
	 * with a remote IPC Process
	 * @param cdapMessage
	 * @param portId
	 */
	public void initiateEnrollment(ApplicationProcessNameSynonym candidate, int portId);
	
	/**
	 * Called by the EnrollmentTask when it receives an M_READ CDAP mesasge 
	 * on certain object names
	 * @param cdapMessage
	 * @param portId
	 */
	public void read(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor);
	
	/**
	 * Called by the EnrollmentTask when it receives an M_CANCELREAD CDAP message 
	 * on certain object names
	 * @param cdapMessage
	 * @param portId
	 */
	public void cancelread(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor);
	
	/**
	 * Called by the EnrollmentTask when it receives an M_START CDAP message
	 * @param cdapMessage
	 * @param cdapSessionDescriptor
	 */
	public void start(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor);
}
