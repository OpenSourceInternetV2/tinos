package rina.applibrary.api;

import java.util.List;

/**
 * Classes implementing this interface provide the behavior of the ApplicationRegistration class
 * @author eduardgrasa
 *
 */
public interface ApplicationRegistrationImpl {
	
	/**
	 * Registers the application to the specified list of DIFs. If the list is null, the application is 
	 * registered in all the DIFs that currently exist in this system.
	 * @param applicationProcess The naming information of the application process that is registering
	 * @param difNames The list of difNames to which the application process is registering. If the list is null it will 
	 * register to all the DIFs available in the system
	 * @param flowAcceptor Decides what flows will be accepted and what flows will be rejected. If it is null, all the 
	 * incoming flows are accepted
	 * @param flowListener If provided, every time a new flow is accepted the flowListener will be called (non-blocking
	 * mode). In non-blocking mode calls to "accept" will throw an Exception. If it is null, users of this class will have 
	 * to call the "accept" blocking operation in order to get the accepted flows.
	 * @throws IPCException
	 */
	public void register(ApplicationProcess applicationProcess, List<String> difNames, FlowAcceptor flowAcceptor, FlowListener flowListener) throws IPCException;
	
	/**
	 * This operation will block until a new incoming flow is accepted.
	 * @param SDUListener the SDUs received by this flow will be sent to the SDUListener
	 * @return the accepted Flow
	 */
	public Flow accept(SDUListener sduListener) throws IPCException;
	
	/**
	 * Cancel the registration
	 * @throws IPCException
	 */
	public void unregister() throws IPCException;
}
