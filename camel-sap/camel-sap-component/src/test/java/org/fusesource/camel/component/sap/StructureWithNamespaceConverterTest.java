package org.fusesource.camel.component.sap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.fusesource.camel.component.sap.converter.StructureConverter;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.fusesource.camel.component.sap.model.rfc.Table;
import org.fusesource.camel.component.sap.util.RfcUtil;
import org.fusesource.camel.component.sap.util.Util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class StructureWithNamespaceConverterTest extends CamelTestSupport {
	
	public static final String REQUEST_STRING =
			"<?xml version=\"1.0\" encoding=\"ASCII\"?>" +
			"<RHT_TEST_RFC:Request xmlns:RHT_TEST_RFC=\"http://sap.fusesource.org/rfc/JBF/RHT:TEST_RFC\" xmlns:RHT=\"http://sap.fusesource.org/rfc/JBF/RHT:TEST_RFC\" MESSAGE=\"Hello, World!\" RHT:PARAMETER1=\"X\">" +
			  "<RHT:PARAMETER2 xmlns:RHT=\"http://sap.fusesource.org/rfc/JBF/RHT:TEST_RFC\" RHT:PARAMETER1=\"Y\" PARAMETER2=\"Z\" RHT:PARAMETER3=\"W\"/>" +
			  "<RHT:TABLE2>" +
			  	"<row xmlns:RHT=\"http://sap.fusesource.org/rfc/JBF/RHT:TEST_RFC\" RHT:PARAMETER1=\"Y\" PARAMETER2=\"Z\" RHT:PARAMETER3=\"W\"/>" +
			  "</RHT:TABLE2>" +
			  "<TABLE1>" +
			  	"<row xmlns:RHT=\"http://sap.fusesource.org/rfc/JBF/RHT:TEST_RFC\" RHT:PARAMETER1=\"Y\" PARAMETER2=\"Z\" RHT:PARAMETER3=\"W\"/>" +
			  "</TABLE1>" +
			"</RHT_TEST_RFC:Request>";

	@BeforeAll
	public static void setupClass() throws IOException {
		File file = new File("data/testNamespaceRegistry.ecore");
		Util.loadRegistry(file);
	}

	@Test
	public void testToStructureFromString() throws Exception {

		//
		// Given
		//
		
		//
		// When
		//

		Structure request = context.getTypeConverter().mandatoryConvertTo(Structure.class, REQUEST_STRING);

		//
		// Then
		//
		
		verifyStructure(request);
		
	}

	@Test
	public void testToStructureFormInputStream() throws Exception{

		//
		// Given
		//
		ByteArrayInputStream bais = new ByteArrayInputStream(REQUEST_STRING.getBytes());
		
		//
		// When
		//

		Structure request = context.getTypeConverter().mandatoryConvertTo(Structure.class, bais);

		//
		// Then
		//

		verifyStructure(request);

	}

	@Test
	public void testToStructureFromByteArray() throws Exception {
		
		//
		// Given
		//
		
		//
		// When
		//
		
		Structure request = context.getTypeConverter().mandatoryConvertTo(Structure.class, REQUEST_STRING.getBytes());

		//
		// Then
		//

		verifyStructure(request);

	}

	@Test
	public void testToString() throws Exception {

		//
		// Given
		//
		
		Structure request = createAndPopulateRequest();
		
		//
		// When
		//
		
		String requestString = context.getTypeConverter().mandatoryConvertTo(String.class, request);

		//
		// Then
		//

		request = StructureConverter.toStructure(requestString);
		verifyStructure(request);

	}

	@Test
	public void testToOutputStream() throws Exception {

		//
		// Given
		//
		
		Structure request = createAndPopulateRequest();
		
		//
		// When
		//

		OutputStream os = context.getTypeConverter().mandatoryConvertTo(OutputStream.class, request);

		//
		// Then
		//
		
		byte[] bytes = ((ByteArrayOutputStream)os).toByteArray();
		request = StructureConverter.toStructure(bytes);
		verifyStructure(request);
		
	}

	@Test
	public void testToInputStream() throws Exception {

		//
		// Given
		//
		
		Structure request = createAndPopulateRequest();
		
		//
		// When
		//
		
		InputStream is = context.getTypeConverter().mandatoryConvertTo(InputStream.class, request);

		//
		// Then
		//

		request = StructureConverter.toStructure(is);
		verifyStructure(request);

	}
	
	@SuppressWarnings("unchecked")
	public void verifyStructure(Structure request) throws Exception {
		
		assertThat("The request to verify is an unexpected null value", request, notNullValue());
		
		assertThat("request.get(MESSAGE) returned '" +  request.get("MESSAGE") + "' instead of expected value '" + "Hello, World!" + "'", request.get("MESSAGE"), is("Hello, World!"));
		assertThat("request.get(/RHT/PARAMETER1) returned '" +  request.get("/RHT/PARAMETER1") + "' instead of expected value '" + "X" + "'", request.get("/RHT/PARAMETER1"), is("X"));
		
		Structure structure = request.get("/RHT/PARAMETER2", Structure.class);
		assertThat("request.get(/RHT/PARAMETER2) returned unexpected null value", structure, notNullValue());
		assertThat("structure.get(/RHT/PARAMETER1) returned '" +  structure.get("/RHT/PARAMETER1") + "' instead of expected value '" + "Y" + "'", structure.get("/RHT/PARAMETER1"), is("Y"));
		assertThat("structure.get(PARAMETER2) returned '" +  structure.get("PARAMETER2") + "' instead of expected value '" + "Z" + "'", structure.get("PARAMETER2"), is("Z"));
		assertThat("structure.get(/RHT/PARAMETER3) returned '" +  structure.get("/RHT/PARAMETER3") + "' instead of expected value '" + "W" + "'", structure.get("/RHT/PARAMETER3"), is("W"));
		
		Table<? extends Structure> table = request.get("TABLE1", Table.class);
		assertThat("request.get(TABLE1) returned unexpected null value", table, notNullValue());
		List<? extends Structure> rows = table.getRows();
		assertThat("rows.size() returned '" + rows.size() + "' instead of expected value of '1'", rows.size(), is(1));
		Structure tableRow = rows.get(0);
		assertThat("tableRow.get(/RHT/PARAMETER1) returned '" +  tableRow.get("/RHT/PARAMETER1") + "' instead of expected value '" + "Y" + "'", tableRow.get("/RHT/PARAMETER1"), is("Y"));
		assertThat("tableRow.get(PARAMETER2) returned '" +  tableRow.get("PARAMETER2") + "' instead of expected value '" + "Z" + "'", tableRow.get("PARAMETER2"), is("Z"));
		assertThat("tableRow.get(/RHT/PARAMETER3) returned '" +  tableRow.get("/RHT/PARAMETER3") + "' instead of expected value '" + "W" + "'", tableRow.get("/RHT/PARAMETER3"), is("W"));

		table = request.get("/RHT/TABLE2", Table.class);
		assertThat("request.get(/RHT/TABLE2) returned unexpected null value", table, notNullValue());
		rows = table.getRows();
		assertThat("rows.size() returned '" + rows.size() + "' instead of expected value of '1'", rows.size(), is(1));
		tableRow = rows.get(0);
		assertThat("tableRow.get(/RHT/PARAMETER1) returned '" +  tableRow.get("/RHT/PARAMETER1") + "' instead of expected value '" + "Y" + "'", tableRow.get("/RHT/PARAMETER1"), is("Y"));
		assertThat("tableRow.get(PARAMETER2) returned '" +  tableRow.get("PARAMETER2") + "' instead of expected value '" + "Z" + "'", tableRow.get("PARAMETER2"), is("Z"));
		assertThat("tableRow.get(/RHT/PARAMETER3) returned '" +  tableRow.get("/RHT/PARAMETER3") + "' instead of expected value '" + "W" + "'", tableRow.get("/RHT/PARAMETER3"), is("W"));

	}
	
	@SuppressWarnings("rawtypes")
	protected Structure createAndPopulateRequest() throws Exception {
		Structure request = (Structure) RfcUtil.createInstance("JBF", "/RHT/TEST_RFC", "Request");
		
		request.put("MESSAGE", "Hello, World!");
		request.put("/RHT/PARAMETER1", "X");
		
		Structure struct = request.get("/RHT/PARAMETER2", Structure.class);
		struct.put("/RHT/PARAMETER1", "Y");
		struct.put("PARAMETER2", "Z");
		struct.put("/RHT/PARAMETER3", "W");
		
		Table table = request.get("TABLE1", Table.class);
		Structure row = table.add();
		row.put("/RHT/PARAMETER1", "Y");
		row.put("PARAMETER2", "Z");
		row.put("/RHT/PARAMETER3", "W");
		
		table = request.get("/RHT/TABLE2", Table.class);
		row = table.add();
		row.put("/RHT/PARAMETER1", "Y");
		row.put("PARAMETER2", "Z");
		row.put("/RHT/PARAMETER3", "W");

		return request;
	}	


}
