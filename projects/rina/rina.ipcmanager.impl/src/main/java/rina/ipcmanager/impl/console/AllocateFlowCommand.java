package rina.ipcmanager.impl.console;

import rina.ipcmanager.impl.IPCManagerImpl;

/**
 * The command to create a new IPC Process
 * @author eduardgrasa
 *
 */
public class AllocateFlowCommand extends ConsoleCommand{

	public static final String ID = "allocateflow";
	private static final String USAGE = "allocateflow sourceipcprocessname sourceipcprocessinstance destinationapplicationprocessname [destinationapplicationprocessinstance]";
	
	/**
	 * Required parameter
	 */
	private String sourceIPCProcessName = null;
	
	/**
	 * Required parameter
	 */
	private String sourceIPCProcessInstance = null;
	
	/**
	 * Required parameter
	 */
	private String destinationApplicationProcessName = null;
	
	/**
	 * Required parameter
	 */
	private String destinationApplicationProcessInstance = null;
	
	public AllocateFlowCommand(IPCManagerImpl ipcManagerImpl){
		super(ID, ipcManagerImpl);
	}
	
	@Override
	public String execute(String[] splittedCommand) {
		if (splittedCommand.length < 4 || splittedCommand.length >5){
			return "Wrong number of parameters. Usage: "+USAGE;
		}
		
		sourceIPCProcessName = splittedCommand[1];
		sourceIPCProcessInstance = splittedCommand[2];
		destinationApplicationProcessName = splittedCommand[3];
		if (splittedCommand.length == 5){
			destinationApplicationProcessInstance = splittedCommand[4];
		}
		
		try{
			this.getIPCManagerImpl().allocateFlow(sourceIPCProcessName, sourceIPCProcessInstance, 
					destinationApplicationProcessName, destinationApplicationProcessInstance);
			return "Allocate Flow process started successfully";
		}catch(Exception ex){
			return "Problems starting the allocate flow process " +ex.getMessage();
		}
	}

}