package rina.encoding.impl.googleprotobuf.flow.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import rina.encoding.impl.googleprotobuf.flow.FlowEncoder;
import rina.flowallocator.api.ConnectionId;
import rina.flowallocator.api.message.Flow;
import rina.ipcprocess.api.IPCProcess;
import rina.ipcservice.api.ApplicationProcessNamingInfo;
import rina.ipcservice.api.QualityOfServiceSpecification;

/**
 * Test if the serialization/deserialization mechanisms for the Flow object work
 * @author eduardgrasa
 *
 */
public class FlowEncoderTest {
	
	private Flow flow = null;
	private Flow flow2 = null;
	private FlowEncoder flowSerializer = null;
	private IPCProcess fakeIPCProcess = null;
	
	@Before
	public void setup(){
		fakeIPCProcess = new FakeIPCProcess();
		flowSerializer = new FlowEncoder();
		flowSerializer.setIPCProcess(fakeIPCProcess);
		
		flow = new Flow();
		flow.setAccessControl(new byte[]{0x01, 0x02, 0x03, 0x04});
		flow.setCreateFlowRetries(2);
		flow.setCurrentFlowId(0);
		flow.setDestinationAddress(1);
		flow.setDestinationNamingInfo(new ApplicationProcessNamingInfo("b", null));
		flow.setDestinationPortId(430);
		flow.setHopCount(3);
		List<ConnectionId> flowIds = new ArrayList<ConnectionId>();
		ConnectionId connectionId = new ConnectionId();
		connectionId.setDestinationCEPId(43);
		connectionId.setSourceCEPId(55);
		connectionId.setQosId(1);
		flowIds.add(connectionId);
		flow.setFlowIds(flowIds);
		flow.setSourceAddress(2);
		flow.setSourceNamingInfo(new ApplicationProcessNamingInfo("a", null));
		flow.setSourcePortId(3327);
		
		flow2 = new Flow();
		flow2.setSourceAddress(1);
		flow2.setDestinationAddress(2);
		flow2.setDestinationNamingInfo(new ApplicationProcessNamingInfo("d", null));
		flow2.setSourceNamingInfo(new ApplicationProcessNamingInfo("c", null));
		flow2.setHopCount(1);
		QualityOfServiceSpecification qosSpec = new QualityOfServiceSpecification();
		qosSpec.setDelay(24);
		qosSpec.setAverageBandwidth(100000);
		flow2.setQosParameters(qosSpec);
	}
	
	@Test
	public void testSerilalization() throws Exception{
		byte[] serializedFlow = flowSerializer.encode(flow);
		for(int i=0; i<serializedFlow.length; i++){
			System.out.print(serializedFlow[i] + " ");
		}
		System.out.println("");
		
		Flow recoveredFlow = (Flow) flowSerializer.decode(serializedFlow, Flow.class.toString());
		Assert.assertArrayEquals(flow.getAccessControl(), recoveredFlow.getAccessControl());
		Assert.assertEquals(flow.getCreateFlowRetries(), recoveredFlow.getCreateFlowRetries());
		Assert.assertEquals(flow.getCurrentFlowId(), recoveredFlow.getCurrentFlowId());
		Assert.assertEquals(flow.getDestinationAddress(), recoveredFlow.getDestinationAddress());
		Assert.assertEquals(flow.getDestinationNamingInfo(), recoveredFlow.getDestinationNamingInfo());
		Assert.assertEquals(flow.getDestinationPortId(), recoveredFlow.getDestinationPortId());
		Assert.assertEquals(flow.getHopCount(), recoveredFlow.getHopCount());
		Assert.assertEquals(flow.getFlowIds().get(0).getDestinationCEPId(), recoveredFlow.getFlowIds().get(0).getDestinationCEPId());
		Assert.assertEquals(flow.getFlowIds().get(0).getSourceCEPId(), recoveredFlow.getFlowIds().get(0).getSourceCEPId());
		Assert.assertEquals(flow.getFlowIds().get(0).getQosId(), recoveredFlow.getFlowIds().get(0).getQosId());
		Assert.assertEquals(flow.getSourceAddress(), recoveredFlow.getSourceAddress());
		Assert.assertEquals(flow.getSourceNamingInfo(), recoveredFlow.getSourceNamingInfo());
		Assert.assertEquals(flow.getSourcePortId(), recoveredFlow.getSourcePortId());
		
		serializedFlow = flowSerializer.encode(flow2);
		for(int i=0; i<serializedFlow.length; i++){
			System.out.print(serializedFlow[i] + " ");
		}
		recoveredFlow = (Flow) flowSerializer.decode(serializedFlow, Flow.class.toString());
		Assert.assertEquals(flow2.getDestinationAddress(), recoveredFlow.getDestinationAddress());
		Assert.assertEquals(flow2.getDestinationNamingInfo(), recoveredFlow.getDestinationNamingInfo());
		Assert.assertEquals(flow2.getHopCount(), recoveredFlow.getHopCount());
		Assert.assertEquals(flow2.getSourceAddress(), recoveredFlow.getSourceAddress());
		Assert.assertEquals(flow2.getSourceNamingInfo(), recoveredFlow.getSourceNamingInfo());
		Assert.assertEquals(flow2.getQosParameters().getAverageBandwidth(), recoveredFlow.getQosParameters().getAverageBandwidth());
	}

}
