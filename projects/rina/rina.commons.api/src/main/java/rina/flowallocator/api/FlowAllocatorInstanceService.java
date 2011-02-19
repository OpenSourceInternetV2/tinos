package rina.flowallocator.api;

import rina.cdap.api.message.CDAPMessage;
import rina.ipcservice.api.AllocateRequest;
/**
* The interface between the FA and the FAI
* @author elenitrouva
*
*/
public interface FlowAllocatorInstanceService{

/**
* Called by the FA to forward an Allocate request to a FAI
* @param request
*/
public void forwardAllocateRequest(AllocateRequest request) ;


/**
* Called by the FA to forward an DeAllocate to a FAI
* @param port_id
*/
public void forwardDeallocate(int port_id) ;


/**
* Called by the FA when it receives a createFlowRequest through the subscription to the RIB Daemon.
* The FA instantiates a FAI and passes the request to it.
* @param CDAP message
*/
public void receivedCreateFlowRequest(CDAPMessage message);
//TODO: Not sure about the param





}