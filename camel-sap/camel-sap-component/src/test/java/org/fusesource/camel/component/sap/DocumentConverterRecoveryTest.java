package org.fusesource.camel.component.sap;

import java.io.File;
import org.apache.camel.TypeConversionException;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.fusesource.camel.component.sap.model.idoc.Document;
import org.fusesource.camel.component.sap.model.idoc.DocumentList;
import org.fusesource.camel.component.sap.util.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentConverterRecoveryTest extends CamelTestSupport {

	private static final String DOCUMENT_STRING =
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		"<idoc:Document xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:TEST_IDOC_TYPE-TEST_IDOC_TYPE_EXTENSION-TEST_SYSTEM_RELEASE-TEST_APPLICATION_RELEASE=\"http://sap.fusesource.org/idoc/TEST_REPOSITORY/TEST_IDOC_TYPE/TEST_IDOC_TYPE_EXTENSION/TEST_SYSTEM_RELEASE/TEST_APPLICATION_RELEASE\" xmlns:idoc=\"http://sap.fusesource.org/idoc\" archiveKey=\"archiveKeyValue\" client=\"clientValue\" creationDate=\"1861-04-12T11:50:37.722-0500\" creationTime=\"2014-07-29T04:30:15.725-0400\" direction=\"directionValue\" EDIMessage=\"ediMessageValue\" EDIMessageGroup=\"editMessageGroupValue\" EDIMessageType=\"editMessageTypeValue\" EDIStandardFlag=\"ediStandardFlagValue\" EDIStandardVersion=\"ediStandardVersionValue\" EDITransmissionFile=\"ediTransmissionFileValue\" iDocCompoundType=\"idocCompoundTypeValue\" iDocNumber=\"idocNumberValue\" iDocSAPRelease=\"idocSAPReleaseValue\" iDocType=\"idocTypeValue\" iDocTypeExtension=\"idocTypeExtensionValue\" messageCode=\"messageCodeValue\" messageFunction=\"messageFunctionValue\" messageType=\"messageTypeValue\" outputMode=\"outputModeValue\" recipientAddress=\"recipientAddressValue\" recipientLogicalAddress=\"recipientLogicalAddressValue\" recipientPartnerFunction=\"recipientPartnerFunctionValue\" recipientPartnerNumber=\"recipientPartnerNumberValue\" recipientPartnerType=\"recipientPartnerTypeValue\" recipientPort=\"recipientPortValue\" senderAddress=\"senderAddressValue\" senderLogicalAddress=\"senderLogicalAddressValue\" senderPartnerFunction=\"senderPartnerFunctionValue\" senderPartnerNumber=\"senderPartnerNumberValue\" senderPartnerType=\"senderPartnerTypeValue\" senderPort=\"senderPortValue\" serialization=\"serializationValue\" status=\"statusValue\" testFlag=\"testFlagValue\">" +
		"  <rootSegment xsi:type=\"TEST_IDOC_TYPE-TEST_IDOC_TYPE_EXTENSION-TEST_SYSTEM_RELEASE-TEST_APPLICATION_RELEASE:ROOT\" document=\"/\">" +
		"    <segmentChildren parent=\"//@rootSegment\">" +
		"      <LEVEL1 parent=\"//@rootSegment\" document=\"/\" CHAR_FIELD=\"1234ABCDEF\" QUAN_FIELD=\"1234567890123456789\" UNIT_FIELD=\"LBS\" NUMC_FIELD=\"1234567890\" DATS_FIELD=\"1863-07-03T00:00:00.000-0500\" TIMS_FIELD=\"1970-01-01T12:15:30.000-0500\" CURR_FIELD=\"1234567890123456789\" CUKY_FIELD=\"USD\" LANG_FIELD=\"EN\" CLNT_FIELD=\"100\" INT1_FIELD=\"255\" INT2_FIELD=\"65535\" INT4_FIELD=\"4294967295\" FLTP_FIELD=\"2.5E+14\" ACCP_FIELD=\"186307\" PREC_FIELD=\"12\" LRAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" DEC_FIELD=\"1234567890\" RAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" STRING_FIELD=\"01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\" RAWSTRING_FIELD=\"0F0E0D0C0B0A09080706050403020100\">" +
		"        <segmentChildren parent=\"//@rootSegment/@segmentChildren/@LEVEL1.0\">" +
		"          <LEVEL2 parent=\"//@rootSegment/@segmentChildren/@LEVEL1.0\" document=\"/\" CHAR_FIELD=\"1234ABCDEF\" QUAN_FIELD=\"1234567890123456789\" UNIT_FIELD=\"LBS\" NUMC_FIELD=\"1234567890\" DATS_FIELD=\"1863-07-03T00:00:00.000-0500\" TIMS_FIELD=\"1970-01-01T12:15:30.000-0500\" CURR_FIELD=\"1234567890123456789\" CUKY_FIELD=\"USD\" LANG_FIELD=\"EN\" CLNT_FIELD=\"100\" INT1_FIELD=\"255\" INT2_FIELD=\"65535\" INT4_FIELD=\"4294967295\" FLTP_FIELD=\"2.5E+14\" ACCP_FIELD=\"186307\" PREC_FIELD=\"12\" LRAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" DEC_FIELD=\"1234567890\" RAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" STRING_FIELD=\"01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\" RAWSTRING_FIELD=\"0F0E0D0C0B0A09080706050403020100\">" +
		"            <segmentChildren parent=\"//@rootSegment/@segmentChildren/@LEVEL1.0/@segmentChildren/@LEVEL2.0\">" +
		"              <LEVEL3 parent=\"//@rootSegment/@segmentChildren/@LEVEL1.0/@segmentChildren/@LEVEL2.0\" document=\"/\" CHAR_FIELD=\"1234ABCDEF\" QUAN_FIELD=\"1234567890123456789\" UNIT_FIELD=\"LBS\" NUMC_FIELD=\"1234567890\" DATS_FIELD=\"1863-07-03T00:00:00.000-0500\" TIMS_FIELD=\"1970-01-01T12:15:30.000-0500\" CURR_FIELD=\"1234567890123456789\" CUKY_FIELD=\"USD\" LANG_FIELD=\"EN\" CLNT_FIELD=\"100\" INT1_FIELD=\"255\" INT2_FIELD=\"65535\" INT4_FIELD=\"4294967295\" FLTP_FIELD=\"2.5E+14\" ACCP_FIELD=\"186307\" PREC_FIELD=\"12\" LRAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" DEC_FIELD=\"1234567890\" RAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" STRING_FIELD=\"01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\" RAWSTRING_FIELD=\"0F0E0D0C0B0A09080706050403020100\"/>" +
		"            </segmentChildren>" +
		"          </LEVEL2>" +
		"        </segmentChildren>" +
		"      </LEVEL1>" +
		"    </segmentChildren>" +
		"  </rootSegment>" +
		"</idoc:Document>";

	private static final String DOCUMENT_LIST_STRING =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<idoc:DocumentList xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:TEST_IDOC_TYPE-TEST_IDOC_TYPE_EXTENSION-TEST_SYSTEM_RELEASE-TEST_APPLICATION_RELEASE=\"http://sap.fusesource.org/idoc/TEST_REPOSITORY/TEST_IDOC_TYPE/TEST_IDOC_TYPE_EXTENSION/TEST_SYSTEM_RELEASE/TEST_APPLICATION_RELEASE\" xmlns:idoc=\"http://sap.fusesource.org/idoc\">" +
			  "<document archiveKey=\"archiveKeyValue\" client=\"clientValue\" creationDate=\"1861-04-12T18:52:06.937-0500\" creationTime=\"2014-07-29T16:30:15.940-0400\" direction=\"directionValue\" EDIMessage=\"ediMessageValue\" EDIMessageGroup=\"editMessageGroupValue\" EDIMessageType=\"editMessageTypeValue\" EDIStandardFlag=\"ediStandardFlagValue\" EDIStandardVersion=\"ediStandardVersionValue\" EDITransmissionFile=\"ediTransmissionFileValue\" iDocCompoundType=\"idocCompoundTypeValue\" iDocNumber=\"idocNumberValue\" iDocSAPRelease=\"idocSAPReleaseValue\" iDocType=\"idocTypeValue\" iDocTypeExtension=\"idocTypeExtensionValue\" messageCode=\"messageCodeValue\" messageFunction=\"messageFunctionValue\" messageType=\"messageTypeValue\" outputMode=\"outputModeValue\" recipientAddress=\"recipientAddressValue\" recipientLogicalAddress=\"recipientLogicalAddressValue\" recipientPartnerFunction=\"recipientPartnerFunctionValue\" recipientPartnerNumber=\"recipientPartnerNumberValue\" recipientPartnerType=\"recipientPartnerTypeValue\" recipientPort=\"recipientPortValue\" senderAddress=\"senderAddressValue\" senderLogicalAddress=\"senderLogicalAddressValue\" senderPartnerFunction=\"senderPartnerFunctionValue\" senderPartnerNumber=\"senderPartnerNumberValue\" senderPartnerType=\"senderPartnerTypeValue\" senderPort=\"senderPortValue\" serialization=\"serializationValue\" status=\"statusValue\" testFlag=\"testFlagValue\">" +
				"  <rootSegment xsi:type=\"TEST_IDOC_TYPE-TEST_IDOC_TYPE_EXTENSION-TEST_SYSTEM_RELEASE-TEST_APPLICATION_RELEASE:ROOT\" document=\"//@document.0\">" +
				"    <segmentChildren parent=\"//@document.0/@rootSegment\">" +
				"      <LEVEL1 parent=\"//@document.0/@rootSegment\" document=\"//@document.0\" CHAR_FIELD=\"1234ABCDEF\" QUAN_FIELD=\"1234567890123456789\" UNIT_FIELD=\"LBS\" NUMC_FIELD=\"1234567890\" DATS_FIELD=\"1863-07-03T00:00:00.000-0500\" TIMS_FIELD=\"1970-01-01T12:15:30.000-0500\" CURR_FIELD=\"1234567890123456789\" CUKY_FIELD=\"USD\" LANG_FIELD=\"EN\" CLNT_FIELD=\"100\" INT1_FIELD=\"255\" INT2_FIELD=\"65535\" INT4_FIELD=\"4294967295\" FLTP_FIELD=\"2.5E+14\" ACCP_FIELD=\"186307\" PREC_FIELD=\"12\" LRAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" DEC_FIELD=\"1234567890\" RAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" STRING_FIELD=\"01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\" RAWSTRING_FIELD=\"0F0E0D0C0B0A09080706050403020100\">" +
				"        <segmentChildren parent=\"//@document.0/@rootSegment/@segmentChildren/@LEVEL1.0\">" +
				"          <LEVEL2 parent=\"//@document.0/@rootSegment/@segmentChildren/@LEVEL1.0\" document=\"//@document.0\" CHAR_FIELD=\"1234ABCDEF\" QUAN_FIELD=\"1234567890123456789\" UNIT_FIELD=\"LBS\" NUMC_FIELD=\"1234567890\" DATS_FIELD=\"1863-07-03T00:00:00.000-0500\" TIMS_FIELD=\"1970-01-01T12:15:30.000-0500\" CURR_FIELD=\"1234567890123456789\" CUKY_FIELD=\"USD\" LANG_FIELD=\"EN\" CLNT_FIELD=\"100\" INT1_FIELD=\"255\" INT2_FIELD=\"65535\" INT4_FIELD=\"4294967295\" FLTP_FIELD=\"2.5E+14\" ACCP_FIELD=\"186307\" PREC_FIELD=\"12\" LRAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" DEC_FIELD=\"1234567890\" RAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" STRING_FIELD=\"01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\" RAWSTRING_FIELD=\"0F0E0D0C0B0A09080706050403020100\">" +
				"            <segmentChildren parent=\"//@document.0/@rootSegment/@segmentChildren/@LEVEL1.0/@segmentChildren/@LEVEL2.0\">" +
				"              <LEVEL3 parent=\"//@document.0/@rootSegment/@segmentChildren/@LEVEL1.0/@segmentChildren/@LEVEL2.0\" document=\"//@document.0\" CHAR_FIELD=\"1234ABCDEF\" QUAN_FIELD=\"1234567890123456789\" UNIT_FIELD=\"LBS\" NUMC_FIELD=\"1234567890\" DATS_FIELD=\"1863-07-03T00:00:00.000-0500\" TIMS_FIELD=\"1970-01-01T12:15:30.000-0500\" CURR_FIELD=\"1234567890123456789\" CUKY_FIELD=\"USD\" LANG_FIELD=\"EN\" CLNT_FIELD=\"100\" INT1_FIELD=\"255\" INT2_FIELD=\"65535\" INT4_FIELD=\"4294967295\" FLTP_FIELD=\"2.5E+14\" ACCP_FIELD=\"186307\" PREC_FIELD=\"12\" LRAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" DEC_FIELD=\"1234567890\" RAW_FIELD=\"0F0E0D0C0B0A09080706050403020100\" STRING_FIELD=\"01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz\" RAWSTRING_FIELD=\"0F0E0D0C0B0A09080706050403020100\"/>" +
				"            </segmentChildren>" +
				"          </LEVEL2>" +
				"        </segmentChildren>" +
				"      </LEVEL1>" +
				"    </segmentChildren>" +
				"  </rootSegment>" +
			  "</document>" +
			"</idoc:DocumentList>";

	@Test
	public void testToDocumentFromStringWithBadInput() throws Exception {

		//
		// Given
		//

		File file = new File("data/testRegistry.ecore");
		Util.loadRegistry(file);
		String badDocument = DOCUMENT_STRING.replace("CHAR_FIELD=\"1234ABCDEF\"", "CHAR_FIELD=\"&1234ABCDEF\"");
		String goodDocument = DOCUMENT_STRING;

		//
		// Then
		//

		Document document = context.getTypeConverter().mandatoryConvertTo(Document.class, goodDocument);
		assertNotNull(document, "Subsequent valid document string not converted");
		assertThrows(TypeConversionException.class, () -> context.getTypeConverter().mandatoryConvertTo(Document.class, badDocument));
	}

	@Test
	public void testToDocumentListFromStringWithBadInput() throws Exception {

		//
		// Given
		//

		File file = new File("data/testRegistry.ecore");
		Util.loadRegistry(file);
		String badDocumentList = DOCUMENT_LIST_STRING.replace("CHAR_FIELD=\"1234ABCDEF\"", "CHAR_FIELD=\"&1234ABCDEF\"");
		String goodDocumentList = DOCUMENT_LIST_STRING;

		//
		// Then
		//

		DocumentList documentList = context.getTypeConverter().mandatoryConvertTo(DocumentList.class, goodDocumentList);
		assertNotNull(documentList, "Subsequent valid document string not converted");
		assertThrows(TypeConversionException.class, () -> context.getTypeConverter().mandatoryConvertTo(DocumentList.class, badDocumentList));
	}

}
