package org.fusesource.camel.component.sap;

import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.fusesource.camel.component.sap.model.idoc.Document;
import org.fusesource.camel.component.sap.model.idoc.DocumentList;
import org.fusesource.camel.component.sap.util.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.mockpolicies.Slf4jMockPolicy;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.ext.Environment;

@RunWith(PowerMockRunner.class)
@MockPolicy({Slf4jMockPolicy.class})
@PrepareForTest({ JCoDestinationManager.class, Environment.class, JCoIDoc.class })
public class DocumentConverterRecoveryTest extends SapIDocTestSupport {

	public static final String DOCUMENT_STRING = 
			"<?xml version=\"1.0\" encoding=\"ASCII\"?>" +
			"<idoc:Document xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:TEST_IDOC_TYPE-TEST_IDOC_TYPE_EXTENSION-TEST_SYSTEM_RELEASE-TEST_APPLICATION_RELEASE=\"http://sap.fusesource.org/idoc/TEST_REPOSITORY/TEST_IDOC_TYPE/TEST_IDOC_TYPE_EXTENSION/TEST_SYSTEM_RELEASE/TEST_APPLICATION_RELEASE\" xmlns:idoc=\"http://sap.fusesource.org/idoc\" archiveKey=\"archiveKeyValue\" client=\"clientValue\" creationDate=\"1861-04-12T11:50:37.722-0500\" creationTime=\"2014-07-29T04:30:15.725-0400\" direction=\"directionValue\" EDIMessage=\"ediMessageValue\" EDIMessageGroup=\"editMessageGroupValue\" EDIMessageType=\"editMessageTypeValue\" EDIStandardFlag=\"ediStandardFlagValue\" EDIStandardVersion=\"ediStandardVersionValue\" EDITransmissionFile=\"ediTransmissionFileValue\" iDocCompoundType=\"idocCompoundTypeValue\" iDocNumber=\"idocNumberValue\" iDocSAPRelease=\"idocSAPReleaseValue\" iDocType=\"idocTypeValue\" iDocTypeExtension=\"idocTypeExtensionValue\" messageCode=\"messageCodeValue\" messageFunction=\"messageFunctionValue\" messageType=\"messageTypeValue\" outputMode=\"outputModeValue\" recipientAddress=\"recipientAddressValue\" recipientLogicalAddress=\"recipientLogicalAddressValue\" recipientPartnerFunction=\"recipientPartnerFunctionValue\" recipientPartnerNumber=\"recipientPartnerNumberValue\" recipientPartnerType=\"recipientPartnerTypeValue\" recipientPort=\"recipientPortValue\" senderAddress=\"senderAddressValue\" senderLogicalAddress=\"senderLogicalAddressValue\" senderPartnerFunction=\"senderPartnerFunctionValue\" senderPartnerNumber=\"senderPartnerNumberValue\" senderPartnerType=\"senderPartnerTypeValue\" senderPort=\"senderPortValue\" serialization=\"serializationValue\" status=\"statusValue\" testFlag=\"testFlagValue\">" +
			"  <rootSegment xsi:type=\"TEST_IDOC_TYPE-TEST_IDOC_TYPE_EXTENSION-TEST_SYSTEM_RELEASE-TEST_APPLICATION_RELEASE:ROOT\" document=\"/\">" +
			"    <segmentChildren parent=\"//@rootSegment\">" +
			"      <LEVEL1 parent=\"//@rootSegment\" document=\"/\" FIELD0=\"FIELD0_VALUE\" FIELD1=\"FIELD1_VALUE\" FIELD2=\"FIELD2_VALUE\" FIELD3=\"FIELD3_VALUE\" FIELD4=\"FIELD4_VALUE\" FIELD5=\"FIELD5_VALUE\" FIELD6=\"FIELD6_VALUE\" FIELD7=\"FIELD7_VALUE\" FIELD8=\"FIELD8_VALUE\" FIELD9=\"FIELD9_VALUE\" FIELD10=\"FIELD10_VALUE\" FIELD11=\"FIELD11_VALUE\" FIELD12=\"FIELD12_VALUE\" FIELD13=\"FIELD13_VALUE\" FIELD14=\"FIELD14_VALUE\" FIELD15=\"FIELD15_VALUE\" FIELD16=\"FIELD16_VALUE\" FIELD17=\"FIELD17_VALUE\" FIELD18=\"FIELD18_VALUE\" FIELD19=\"FIELD19_VALUE\" FIELD20=\"FIELD20_VALUE\">" +
			"        <segmentChildren parent=\"//@rootSegment/@segmentChildren/@LEVEL1.0\">" +
			"          <LEVEL2 parent=\"//@rootSegment/@segmentChildren/@LEVEL1.0\" document=\"/\" FIELD0=\"FIELD0_VALUE\" FIELD1=\"FIELD1_VALUE\" FIELD2=\"FIELD2_VALUE\" FIELD3=\"FIELD3_VALUE\" FIELD4=\"FIELD4_VALUE\" FIELD5=\"FIELD5_VALUE\" FIELD6=\"FIELD6_VALUE\" FIELD7=\"FIELD7_VALUE\" FIELD8=\"FIELD8_VALUE\" FIELD9=\"FIELD9_VALUE\" FIELD10=\"FIELD10_VALUE\" FIELD11=\"FIELD11_VALUE\" FIELD12=\"FIELD12_VALUE\" FIELD13=\"FIELD13_VALUE\" FIELD14=\"FIELD14_VALUE\" FIELD15=\"FIELD15_VALUE\" FIELD16=\"FIELD16_VALUE\" FIELD17=\"FIELD17_VALUE\" FIELD18=\"FIELD18_VALUE\" FIELD19=\"FIELD19_VALUE\" FIELD20=\"FIELD20_VALUE\">" +
			"            <segmentChildren parent=\"//@rootSegment/@segmentChildren/@LEVEL1.0/@segmentChildren/@LEVEL2.0\">" +
			"              <LEVEL3 parent=\"//@rootSegment/@segmentChildren/@LEVEL1.0/@segmentChildren/@LEVEL2.0\" document=\"/\" FIELD0=\"FIELD0_VALUE\" FIELD1=\"FIELD1_VALUE\" FIELD2=\"FIELD2_VALUE\" FIELD3=\"FIELD3_VALUE\" FIELD4=\"FIELD4_VALUE\" FIELD5=\"FIELD5_VALUE\" FIELD6=\"FIELD6_VALUE\" FIELD7=\"FIELD7_VALUE\" FIELD8=\"FIELD8_VALUE\" FIELD9=\"FIELD9_VALUE\" FIELD10=\"FIELD10_VALUE\" FIELD11=\"FIELD11_VALUE\" FIELD12=\"FIELD12_VALUE\" FIELD13=\"FIELD13_VALUE\" FIELD14=\"FIELD14_VALUE\" FIELD15=\"FIELD15_VALUE\" FIELD16=\"FIELD16_VALUE\" FIELD17=\"FIELD17_VALUE\" FIELD18=\"FIELD18_VALUE\" FIELD19=\"FIELD19_VALUE\" FIELD20=\"FIELD20_VALUE\"/>" +
			"            </segmentChildren>" +
			"          </LEVEL2>" +
			"        </segmentChildren>" +
			"      </LEVEL1>" +
			"    </segmentChildren>" +
			"  </rootSegment>" +
			"</idoc:Document>";

	public static final String DOCUMENT_LIST_STRING = 
			"<?xml version=\"1.0\" encoding=\"ASCII\"?>" +
			"<idoc:DocumentList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:TEST_IDOC_TYPE-TEST_IDOC_TYPE_EXTENSION-TEST_SYSTEM_RELEASE-TEST_APPLICATION_RELEASE=\"http://sap.fusesource.org/idoc/TEST_REPOSITORY/TEST_IDOC_TYPE/TEST_IDOC_TYPE_EXTENSION/TEST_SYSTEM_RELEASE/TEST_APPLICATION_RELEASE\" xmlns:idoc=\"http://sap.fusesource.org/idoc\">" +
			  "<document archiveKey=\"archiveKeyValue\" client=\"clientValue\" creationDate=\"1861-04-12T18:52:06.937-0500\" creationTime=\"2014-07-29T16:30:15.940-0400\" direction=\"directionValue\" EDIMessage=\"ediMessageValue\" EDIMessageGroup=\"editMessageGroupValue\" EDIMessageType=\"editMessageTypeValue\" EDIStandardFlag=\"ediStandardFlagValue\" EDIStandardVersion=\"ediStandardVersionValue\" EDITransmissionFile=\"ediTransmissionFileValue\" iDocCompoundType=\"idocCompoundTypeValue\" iDocNumber=\"idocNumberValue\" iDocSAPRelease=\"idocSAPReleaseValue\" iDocType=\"idocTypeValue\" iDocTypeExtension=\"idocTypeExtensionValue\" messageCode=\"messageCodeValue\" messageFunction=\"messageFunctionValue\" messageType=\"messageTypeValue\" outputMode=\"outputModeValue\" recipientAddress=\"recipientAddressValue\" recipientLogicalAddress=\"recipientLogicalAddressValue\" recipientPartnerFunction=\"recipientPartnerFunctionValue\" recipientPartnerNumber=\"recipientPartnerNumberValue\" recipientPartnerType=\"recipientPartnerTypeValue\" recipientPort=\"recipientPortValue\" senderAddress=\"senderAddressValue\" senderLogicalAddress=\"senderLogicalAddressValue\" senderPartnerFunction=\"senderPartnerFunctionValue\" senderPartnerNumber=\"senderPartnerNumberValue\" senderPartnerType=\"senderPartnerTypeValue\" senderPort=\"senderPortValue\" serialization=\"serializationValue\" status=\"statusValue\" testFlag=\"testFlagValue\">" +
			    "<rootSegment xsi:type=\"TEST_IDOC_TYPE-TEST_IDOC_TYPE_EXTENSION-TEST_SYSTEM_RELEASE-TEST_APPLICATION_RELEASE:ROOT\" document=\"//@document.0\">" +
			      "<segmentChildren parent=\"//@document.0/@rootSegment\">" +
			        "<LEVEL1 parent=\"//@document.0/@rootSegment\" document=\"//@document.0\" FIELD0=\"FIELD0_VALUE\" FIELD1=\"FIELD1_VALUE\" FIELD2=\"FIELD2_VALUE\" FIELD3=\"FIELD3_VALUE\" FIELD4=\"FIELD4_VALUE\" FIELD5=\"FIELD5_VALUE\" FIELD6=\"FIELD6_VALUE\" FIELD7=\"FIELD7_VALUE\" FIELD8=\"FIELD8_VALUE\" FIELD9=\"FIELD9_VALUE\" FIELD10=\"FIELD10_VALUE\" FIELD11=\"FIELD11_VALUE\" FIELD12=\"FIELD12_VALUE\" FIELD13=\"FIELD13_VALUE\" FIELD14=\"FIELD14_VALUE\" FIELD15=\"FIELD15_VALUE\" FIELD16=\"FIELD16_VALUE\" FIELD17=\"FIELD17_VALUE\" FIELD18=\"FIELD18_VALUE\" FIELD19=\"FIELD19_VALUE\" FIELD20=\"FIELD20_VALUE\">" +
			          "<segmentChildren parent=\"//@document.0/@rootSegment/@segmentChildren/@LEVEL1.0\">" +
			            "<LEVEL2 parent=\"//@document.0/@rootSegment/@segmentChildren/@LEVEL1.0\" document=\"//@document.0\" FIELD0=\"FIELD0_VALUE\" FIELD1=\"FIELD1_VALUE\" FIELD2=\"FIELD2_VALUE\" FIELD3=\"FIELD3_VALUE\" FIELD4=\"FIELD4_VALUE\" FIELD5=\"FIELD5_VALUE\" FIELD6=\"FIELD6_VALUE\" FIELD7=\"FIELD7_VALUE\" FIELD8=\"FIELD8_VALUE\" FIELD9=\"FIELD9_VALUE\" FIELD10=\"FIELD10_VALUE\" FIELD11=\"FIELD11_VALUE\" FIELD12=\"FIELD12_VALUE\" FIELD13=\"FIELD13_VALUE\" FIELD14=\"FIELD14_VALUE\" FIELD15=\"FIELD15_VALUE\" FIELD16=\"FIELD16_VALUE\" FIELD17=\"FIELD17_VALUE\" FIELD18=\"FIELD18_VALUE\" FIELD19=\"FIELD19_VALUE\" FIELD20=\"FIELD20_VALUE\">" +
			              "<segmentChildren parent=\"//@document.0/@rootSegment/@segmentChildren/@LEVEL1.0/@segmentChildren/@LEVEL2.0\">" +
			                "<LEVEL3 parent=\"//@document.0/@rootSegment/@segmentChildren/@LEVEL1.0/@segmentChildren/@LEVEL2.0\" document=\"//@document.0\" FIELD0=\"FIELD0_VALUE\" FIELD1=\"FIELD1_VALUE\" FIELD2=\"FIELD2_VALUE\" FIELD3=\"FIELD3_VALUE\" FIELD4=\"FIELD4_VALUE\" FIELD5=\"FIELD5_VALUE\" FIELD6=\"FIELD6_VALUE\" FIELD7=\"FIELD7_VALUE\" FIELD8=\"FIELD8_VALUE\" FIELD9=\"FIELD9_VALUE\" FIELD10=\"FIELD10_VALUE\" FIELD11=\"FIELD11_VALUE\" FIELD12=\"FIELD12_VALUE\" FIELD13=\"FIELD13_VALUE\" FIELD14=\"FIELD14_VALUE\" FIELD15=\"FIELD15_VALUE\" FIELD16=\"FIELD16_VALUE\" FIELD17=\"FIELD17_VALUE\" FIELD18=\"FIELD18_VALUE\" FIELD19=\"FIELD19_VALUE\" FIELD20=\"FIELD20_VALUE\"/>" +
			              "</segmentChildren>" +
			            "</LEVEL2>" +
			          "</segmentChildren>" +
			        "</LEVEL1>" +
			      "</segmentChildren>" +
			    "</rootSegment>" +
			  "</document>" +
			"</idoc:DocumentList>";

	
	@Override
	public void doPreSetup() throws Exception {
		super.doPreSetup();
		PowerMockito.mockStatic(JCoDestinationManager.class, Environment.class, JCoIDoc.class);
		when(JCoDestinationManager.getDestination(TEST_DEST)).thenReturn(mockDestination);
		when(JCoIDoc.getIDocRepository(mockDestination)).thenReturn(mockIDocRepository);
		when(JCoIDoc.getIDocFactory()).thenReturn(mockIDocFactory);
		when(JCoIDoc.getServer(TEST_SERVER)).thenReturn(mockIDocServer);
		
	}

	@Test
	public void testToDocumentFromStringWithBadInput() throws Exception {
		
		//
		// Given
		//
		
		File file = new File("data/testRegistry.ecore");
		Util.loadRegistry(file);
		String badDocument = DOCUMENT_STRING.replace("FIELD0=\"FIELD0_VALUE\"", "FIELD0=\"&FIELD0_VALUE\"");
		String goodDocument = DOCUMENT_STRING;
		
		//
		// When
		//

		template.sendBody("direct:document", badDocument);
		template.sendBody("direct:document", goodDocument);
		
		//
		// Then
		//
		List<Exchange> exchanges = getMockEndpoint("mock:result").getExchanges();
		
		// First document string sent is invalid and should return a null document 
		Exchange exchange1 = exchanges.get(0);
		Message message1 = exchange1.getIn();
		Document document1 = message1.getBody(Document.class);
		assertNull("Invalid document string inadvertantly converted", document1);
		
		// Second document string sent is valid and should return a non-null document 
		Exchange exchange2 = exchanges.get(1);
		Message message2 = exchange2.getIn();
		Document document2 = message2.getBody(Document.class);
		assertNotNull("Subsequent valid document string not converted", document2);
		
	}

	@Test
	public void testToDocumentListFromStringWithBadInput() throws Exception {
		
		//
		// Given
		//
		
		File file = new File("data/testRegistry.ecore");
		Util.loadRegistry(file);
		String badDocumentList = DOCUMENT_LIST_STRING.replace("FIELD0=\"FIELD0_VALUE\"", "FIELD0=\"&FIELD0_VALUE\"");
		String goodDocumentList = DOCUMENT_LIST_STRING;
		
		//
		// When
		//

		template.sendBody("direct:documentlist", badDocumentList);
		template.sendBody("direct:documentlist", goodDocumentList);
		
		//
		// Then
		//
		List<Exchange> exchanges = getMockEndpoint("mock:result").getExchanges();
		
		// First document list string sent is invalid and should return a null document 
		Exchange exchange1 = exchanges.get(0);
		Message message1 = exchange1.getIn();
		DocumentList  documentList1 = message1.getBody(DocumentList.class);
		assertNull("Invalid document list string inadvertantly converted", documentList1);
		
		// Second document List string sent is valid and should return a non-null document 
		Exchange exchange2 = exchanges.get(1);
		Message message2 = exchange2.getIn();
		DocumentList documentList2 = message2.getBody(DocumentList.class);
		assertNotNull("Subsequent valid document list string not converted", documentList2);
		
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:document").to("sap-idoc-destination:TEST_DEST:TEST_IDOC_TYPE:TEST_IDOC_TYPE_EXTENSION:TEST_SYSTEM_VERSION:TEST_APPLICATION_VERSION").to("mock:result");
				from("direct:documentlist").to("sap-idoclist-destination:TEST_DEST:TEST_IDOC_TYPE:TEST_IDOC_TYPE_EXTENSION:TEST_SYSTEM_VERSION:TEST_APPLICATION_VERSION").to("mock:result");
			}
		};
	}

}
