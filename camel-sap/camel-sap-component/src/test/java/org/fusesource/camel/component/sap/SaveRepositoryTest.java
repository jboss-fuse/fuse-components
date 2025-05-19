package org.fusesource.camel.component.sap;

import java.io.File;
import com.sap.conn.idoc.IDocRepository;
import com.sap.conn.idoc.jco.JCoIDoc;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import org.fusesource.camel.component.sap.model.idoc.Document;
import org.fusesource.camel.component.sap.model.idoc.IdocPackage;
import org.fusesource.camel.component.sap.util.IDocUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
public class SaveRepositoryTest extends SapIDocTestSupport {

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
	@Disabled("Deactivate due to failure while launched with maven 'A frozen model should not be modified'")
	public void saveRegistry() throws Exception {

		//
		// Given
		//

		JCoDestination jcoDestination = JCoDestinationManager.getDestination(TEST_DEST);
		IDocRepository repository = JCoIDoc.getIDocRepository(jcoDestination);

		IDocUtil.getEPackage(repository, IdocPackage.eNS_URI);
		IDocUtil.getEPackage(repository, TEST_URL);
		File file = new File("data/testRegistry.ecore");

		//
		// When
		//

		IDocUtil.saveRegistry(file);

		//
		// Then
		//

	}

	@Test
	public void saveDocument() throws Exception {

		//
		// Given
		//


		File file = new File("data/testDocument.xml");
		Document document = createAndPopulateDocument();

		//
		// When
		//

		IDocUtil.save(file, document);

		//
		// Then
		//

	}

}
