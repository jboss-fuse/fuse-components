package org.fusesource.camel.component.sap;

import java.io.File;
import org.apache.camel.TypeConversionException;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.fusesource.camel.component.sap.util.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StructureConverterRecoveryTest extends CamelTestSupport {

	public static final String REQUEST_STRING = 
			"<?xml version=\"1.0\" encoding=\"ASCII\"?>" +
			"<TEST_FUNCTION_MODULE:Request xmlns:TEST_FUNCTION_MODULE=\"http://sap.fusesource.org/rfc/TEST_REPOSITORY/TEST_FUNCTION_MODULE\" PARAM_LIST_CHAR_PARAM=\"ABCDEFGHIJ\" PARAM_LIST_NUM_PARAM=\"0123456789\" PARAM_LIST_INT_PARAM=\"1968526677\" PARAM_LIST_FLOAT_PARAM=\"1.0E38\" PARAM_LIST_BCD_PARAM=\"100.00000000000001\" PARAM_LIST_BINARY_PARAM=\"55\" PARAM_LIST_BINARY_ARRAY_PARAM=\"FF0F1E2D3C4B5A607988\" PARAM_LIST_DATE_PARAM=\"" + SapRfcTestSupport.DATE_PARAM_IN_VAL_STR + "\" PARAM_LIST_TIME_PARAM=\"" + SapRfcTestSupport.TIME_PARAM_IN_VAL_STR + "\" PARAM_LIST_STRING_PARAM=\"Four score and seven years ago ...\">" +
			  "<PARAM_LIST_STRUCTURE_PARAM CHAR_PARAM=\"ABCDEFGHIJ\" NUM_PARAM=\"0123456789\" INT_PARAM=\"1968526677\" FLOAT_PARAM=\"1.0E38\" BCD_PARAM=\"100.00000000000001\" BINARY_PARAM=\"55\" BINARY_ARRAY_PARAM=\"FF0F1E2D3C4B5A607988\" DATE_PARAM=\"" + SapRfcTestSupport.DATE_PARAM_IN_VAL_STR + "\" TIME_PARAM=\"" + SapRfcTestSupport.TIME_PARAM_IN_VAL_STR + "\" STRING_PARAM=\"Four score and seven years ago ...\"/>" +
			  "<PARAM_LIST_TABLE_PARAM>" +
			    "<row CHAR_PARAM=\"ABCDEFGHIJ\" NUM_PARAM=\"0123456789\" INT_PARAM=\"1968526677\" FLOAT_PARAM=\"1.0E38\" BCD_PARAM=\"100.00000000000001\" BINARY_PARAM=\"55\" BINARY_ARRAY_PARAM=\"FF0F1E2D3C4B5A607988\" DATE_PARAM=\"" + SapRfcTestSupport.DATE_PARAM_IN_VAL_STR + "\" TIME_PARAM=\"" + SapRfcTestSupport.TIME_PARAM_IN_VAL_STR + "\" STRING_PARAM=\"Four score and seven years ago ...\"/>" +
			  "</PARAM_LIST_TABLE_PARAM>" +
			"</TEST_FUNCTION_MODULE:Request>";

	@Test
	public void testToStructureFromStringWithBadInput() throws Exception {

		//
		// Given
		//
		
		File file = new File("data/testRfcRegistry.ecore");
		Util.loadRegistry(file);
		String badRequest = REQUEST_STRING.replace("CHAR_PARAM=\"ABCDEFGHIJ\"", "CHAR_PARAM=\"&ABCDEFGHIJ\"");

		//
		// Then
		//

		assertThrows(TypeConversionException.class, () -> context.getTypeConverter().mandatoryConvertTo(Structure.class, badRequest));
	}

	
	@Test
	public void testToStructureFromStringWithGoodInput() throws Exception {

		//
		// Given
		//
		
		File file = new File("data/testRfcRegistry.ecore");
		Util.loadRegistry(file);
		String goodRequest = REQUEST_STRING;
		
		//
		// Then
		//

		Structure request = context.getTypeConverter().mandatoryConvertTo(Structure.class, goodRequest);
		assertNotNull(request, "Subsequent valid request string not converted");
	}
}
