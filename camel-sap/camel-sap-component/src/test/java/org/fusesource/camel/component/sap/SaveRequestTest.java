package org.fusesource.camel.component.sap;

import java.io.File;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.server.JCoServerFactory;
import org.fusesource.camel.component.sap.model.rfc.Structure;
import org.fusesource.camel.component.sap.util.Util;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class SaveRequestTest extends SapRfcTestSupport {

	@SuppressWarnings("deprecation")
	@Override
	public void setupResources() throws Exception {
		super.setupResources();

		MockedStatic<JCoDestinationManager> dest = Mockito.mockStatic(JCoDestinationManager.class);
		dest.when(() -> JCoDestinationManager.getDestination(DESTINATION_NAME)).thenReturn(mockDestination);
		MockedStatic<JCoServerFactory> server = Mockito.mockStatic(JCoServerFactory.class);
		server.when(() -> JCoServerFactory.getServer(SERVER_NAME)).thenReturn(mockServer);
	}

	@Test
	public void saveDocument() throws Exception {

		//
		// Given
		//
		enhanceParameterListMetaData();

		File file = new File("data/testRequest.xml");
		Structure request = createAndPopulateRequest();
		
		//
		// When
		//

		Util.save(file, request);

		//
		// Then
		//

	}

}
