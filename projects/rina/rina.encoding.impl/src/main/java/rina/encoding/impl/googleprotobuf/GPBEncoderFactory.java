package rina.encoding.impl.googleprotobuf;

import rina.applicationprocess.api.DAFMember;
import rina.applicationprocess.api.WhatevercastName;
import rina.efcp.api.DataTransferConstants;
import rina.encoding.api.Encoder;
import rina.encoding.api.EncoderFactory;
import rina.encoding.impl.EncoderImpl;
import rina.encoding.impl.googleprotobuf.applicationregistration.ApplicationRegistrationEncoder;
import rina.encoding.impl.googleprotobuf.dafmember.DafMemberArrayEncoder;
import rina.encoding.impl.googleprotobuf.dafmember.DafMemberEncoder;
import rina.encoding.impl.googleprotobuf.datatransferconstants.DataTransferConstantsEncoder;
import rina.encoding.impl.googleprotobuf.directoryforwardingtable.DirectoryForwardingTableEntryArrayEncoder;
import rina.encoding.impl.googleprotobuf.directoryforwardingtable.DirectoryForwardingTableEntryEncoder;
import rina.encoding.impl.googleprotobuf.enrollment.EnrollmentInformationEncoder;
import rina.encoding.impl.googleprotobuf.flow.FlowEncoder;
import rina.encoding.impl.googleprotobuf.flowservice.FlowServiceEncoder;
import rina.encoding.impl.googleprotobuf.qoscube.QoSCubeArrayEncoder;
import rina.encoding.impl.googleprotobuf.qoscube.QoSCubeEncoder;
import rina.encoding.impl.googleprotobuf.whatevercast.WhatevercastNameArrayEncoder;
import rina.encoding.impl.googleprotobuf.whatevercast.WhatevercastNameEncoder;
import rina.enrollment.api.EnrollmentInformationRequest;
import rina.flowallocator.api.DirectoryForwardingTableEntry;
import rina.flowallocator.api.QoSCube;
import rina.flowallocator.api.message.Flow;
import rina.ipcservice.api.ApplicationRegistration;
import rina.ipcservice.api.FlowService;

/**
 * Creates instances of encoders that encode/decode objects using the Google protocol buffers 
 * encoding/decoding format
 * @author eduardgrasa
 *
 */
public class GPBEncoderFactory implements EncoderFactory{

	public Encoder createEncoderInstance() {
		EncoderImpl encoder = new EncoderImpl();
		
		encoder.addEncoder(DAFMember.class.getName(), new DafMemberEncoder());
		encoder.addEncoder(DAFMember[].class.getName(), new DafMemberArrayEncoder());
		encoder.addEncoder(DataTransferConstants.class.getName(), new DataTransferConstantsEncoder());
		encoder.addEncoder(DirectoryForwardingTableEntry.class.getName(), new DirectoryForwardingTableEntryEncoder());
		encoder.addEncoder(DirectoryForwardingTableEntry[].class.getName(), new DirectoryForwardingTableEntryArrayEncoder());
		encoder.addEncoder(EnrollmentInformationRequest.class.getName(), new EnrollmentInformationEncoder());
		encoder.addEncoder(Flow.class.getName(), new FlowEncoder());
		encoder.addEncoder(QoSCube.class.getName(), new QoSCubeEncoder());
		encoder.addEncoder(QoSCube[].class.getName(), new QoSCubeArrayEncoder());
		encoder.addEncoder(WhatevercastName.class.getName(), new WhatevercastNameEncoder());
		encoder.addEncoder(WhatevercastName[].class.getName(), new WhatevercastNameArrayEncoder());
		encoder.addEncoder(FlowService.class.getName(), new FlowServiceEncoder());
		encoder.addEncoder(ApplicationRegistration.class.getName(), new ApplicationRegistrationEncoder());
		
		return encoder;
	}

}
