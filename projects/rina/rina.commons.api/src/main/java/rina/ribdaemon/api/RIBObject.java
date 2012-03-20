package rina.ribdaemon.api;

import java.util.List;

import rina.cdap.api.CDAPSessionDescriptor;
import rina.cdap.api.message.CDAPMessage;

/**
 * Implements the create/delete/read/write/start/stop RIB functionality for certain objects (identified by objectNames)
 * @author eduardgrasa
 *
 */
public interface RIBObject{

	/* RIB Object attributes */
	public String getObjectName();
	public String getObjectClass();
	public long getObjectInstance();
	public Object getObjectValue();

	/* Tree management operations */
	public RIBObject getParent();
	public void setParent(RIBObject parent);
	public List<RIBObject> getChildren();
	public void setChildren(List<RIBObject> children);
	public int getNumberOfChildren();
	public void addChild(RIBObject child) throws RIBDaemonException;
	public void removeChild(String objectName) throws RIBDaemonException;

	/* LOCAL API */
	public void create(String objectClass, String objectName, long objectInstance, Object objectValue) throws RIBDaemonException;
	public void delete(String objectClass, String objectName, long objectInstance, Object objectValue) throws RIBDaemonException;
	public RIBObject read(String objectClass, String objectName, long objectInstance) throws RIBDaemonException;
	public void write(String objectClass, String objectName, long objectInstance, Object object) throws RIBDaemonException;
	public void start(String objectClass, String objectName, long objectInstance, Object object) throws RIBDaemonException;
	public void stop(String objectClass, String objectName, long objectInstance, Object object) throws RIBDaemonException;


	/* REMOTE API */	
	public void create(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor) throws RIBDaemonException;
	public void delete(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor) throws RIBDaemonException;
	public void read(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor) throws RIBDaemonException;
	public void cancelRead(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor) throws RIBDaemonException;
	public void write(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor) throws RIBDaemonException;
	public void start(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor) throws RIBDaemonException;
	public void stop(CDAPMessage cdapMessage, CDAPSessionDescriptor cdapSessionDescriptor) throws RIBDaemonException;
}
