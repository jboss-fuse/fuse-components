package org.fusesource.camel.component.sap;

import java.io.File;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.jco.JCoDestinationManager;
import org.fusesource.camel.component.sap.model.idoc.DocumentList;
import org.fusesource.camel.component.sap.util.IDocUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class SaveDocumentListTest extends SapIDocTestSupport {

	@Override
	public void setupResources() throws Exception {
		super.setupResources();

		MockedStatic<JCoDestinationManager> dest = Mockito.mockStatic(JCoDestinationManager.class);
		dest.when(() -> JCoDestinationManager.getDestination(TEST_DEST)).thenReturn(mockDestination);
		MockedStatic<JCoIDoc> idoc = Mockito.mockStatic(JCoIDoc.class);
		idoc.when(() -> JCoIDoc.getIDocRepository(mockDestination)).thenReturn(mockIDocRepository);
		idoc.when(() -> JCoIDoc.getIDocFactory()).thenReturn(mockIDocFactory);
		idoc.when(() -> JCoIDoc.getServer(TEST_SERVER)).thenReturn(mockIDocServer);
	}

	@Test
	public void saveDocument() throws Exception {

		//
		// Given
		//
		File file = new File("data/testDocumentList.xml");
		DocumentList documentList = createAndPopulateDocumentList();
		
		//
		// When
		//

		IDocUtil.save(file, documentList);

		//
		// Then
		//

	}

}
