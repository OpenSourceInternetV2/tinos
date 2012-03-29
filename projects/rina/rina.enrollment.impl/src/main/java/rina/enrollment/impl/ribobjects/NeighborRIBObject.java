package rina.enrollment.impl.ribobjects;

import rina.enrollment.api.Neighbor;
import rina.ipcprocess.api.IPCProcess;
import rina.ribdaemon.api.BaseRIBObject;
import rina.ribdaemon.api.ObjectInstanceGenerator;
import rina.ribdaemon.api.RIBDaemonException;
import rina.ribdaemon.api.RIBObject;

public class NeighborRIBObject extends BaseRIBObject{

	private Neighbor neighbor = null;
	
	public NeighborRIBObject(IPCProcess ipcProcess, String objectName, Neighbor neighbor) {
		super(ipcProcess, Neighbor.NEIGHBOR_RIB_OBJECT_CLASS, 
				ObjectInstanceGenerator.getObjectInstance(), objectName);
		this.neighbor = neighbor;
	}
	
	@Override
	public RIBObject read() throws RIBDaemonException{
		return this;
	}
	
	@Override
	public void create(String objectClass, long objectInstance, String objectName, Object objectValue) throws RIBDaemonException{
		write(objectValue);
	}
	
	@Override
	public void write(Object object) throws RIBDaemonException {
		if (!(object instanceof Neighbor)){
			throw new RIBDaemonException(RIBDaemonException.OBJECTCLASS_DOES_NOT_MATCH_OBJECTNAME, 
					"Object class ("+object.getClass().getName()+") does not match object name "+
					this.getObjectName());
		}
		
		this.neighbor = (Neighbor) object;
	}
	
	@Override
	public void delete(Object object) throws RIBDaemonException {
		this.getParent().removeChild(this.getObjectName());
	}
	
	@Override
	public Object getObjectValue(){
		return neighbor;
	}
}
