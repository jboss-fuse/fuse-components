/**
 * Copyright 2014 Red Hat, Inc.
 * 
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 */
package org.fusesource.camel.component.sap;

import java.io.File;
import java.io.FileNotFoundException;

import org.fusesource.camel.component.sap.model.rfc.RfcFactory;
import org.fusesource.camel.component.sap.model.rfc.TIDState;
import org.fusesource.camel.component.sap.model.rfc.TIDStore;
import org.fusesource.camel.component.sap.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.conn.jco.server.JCoServerContext;
import com.sap.conn.jco.server.JCoServerTIDHandler;

/**
 * Handler for transactions received from an SAP system.
 * 
 * <ul>
 * <b>Successful transaction call sequence</b>
 * <li>checkTID - returns true</li>
 * <li>call handler- no exception</li>
 * <li>commit</li>
 * <li>confirmTID</li>
 * </ul>
 * 
 * <ul>
 * <b>Unsuccessful transaction call sequence</b>
 * <li>checkTID - returns true</li>
 * <li>call handler- throws exception</li>
 * <li>rollback</li>
 * </ul>
 * 
 * <ul>
 * <b>Unsuccessfully committed transaction call sequence</b>
 * <li>checkTID - returns true</li>
 * <li>call handler- throws exception</li>
 * <li>commit - throws exception</li>
 * <li>rollback</li>
 * </ul>
 * 
 * <ul>
 * <b>Duplicate transaction call sequence</b>
 * <li>checkTID - returns false</li>
 * <li>call handler- throws exception</li>
 * <li>confirmTID</li>
 * </ul>
 * 
 * @author William Collins <punkhornsw@gmail.com>
 * 
 */
public class ServerTIDHandler implements JCoServerTIDHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ServerTIDHandler.class);

	TIDStore availableTIDs = RfcFactory.eINSTANCE.createTIDStore();

	File tidStoreFile;

	public ServerTIDHandler(File tidStoreLocation, String serverName) throws Exception {
		this.tidStoreFile = new File(tidStoreLocation, serverName);
		loadTIDs();
	}

	@Override
	public boolean checkTID(JCoServerContext serverContext, String tid) {
		TIDState state = TIDState.getByName(availableTIDs.getEntries().get(tid));
		if (state == null) {
			availableTIDs.getEntries().put(tid, TIDState.CREATED.getName());
			try {
				saveTIDs();
			} catch (Exception e) {
				throw new RuntimeException("Failed to save transaction IDs", e);
			}
			LOG.debug("Checked TID '" + tid + "': true");
			return true;
		}

		if (state == TIDState.CREATED || state == TIDState.ROLLED_BACK) {
			LOG.debug("Checked TID '" + tid + "': true");
			return true;
		}

		LOG.debug("Checked TID '" + tid + "': false");
		return false;
	}

	@Override
	public void commit(JCoServerContext serverContext, String tid) {
		availableTIDs.getEntries().put(tid, TIDState.COMMITTED.getName());
		try {
			saveTIDs();
		} catch (Exception e) {
			throw new RuntimeException("Failed to save transaction IDs", e);
		}
		LOG.debug("Committed TID '" + tid + "'");
	}

	@Override
	public void rollback(JCoServerContext serverContext, String tid) {
		availableTIDs.getEntries().put(tid, TIDState.ROLLED_BACK.getName());
		try {
			saveTIDs();
		} catch (Exception e) {
			throw new RuntimeException("Failed to save transaction IDs", e);
		}
		LOG.debug("Rolledback TID '" + tid + "'");
	}

	@Override
	public void confirmTID(JCoServerContext serverContext, String tid) {
		availableTIDs.getEntries().remove(tid);
		try {
			saveTIDs();
		} catch (Exception e) {
			LOG.warn("Failed to save transaction IDs", e);
		}
		LOG.debug("Confirmed TID '" + tid + "'");
	}

	public void execute(JCoServerContext serverContext) {
		String tid = serverContext.getTID();
		if (tid != null) {
			availableTIDs.getEntries().put(tid, TIDState.EXECUTED.getName());
			try {
				saveTIDs();
			} catch (Exception e) {
				throw new RuntimeException("Failed to save transaction IDs", e);
			}
			LOG.debug("Executed TID '" + tid + "'");
		}
	}

	private void saveTIDs() throws Exception {
		Util.save(tidStoreFile, availableTIDs);
	}

	private void loadTIDs() throws Exception {
		try {
			TIDStore tidStore = (TIDStore) Util.load(tidStoreFile);
			availableTIDs.getEntries().clear();
			availableTIDs.getEntries().addAll(tidStore.getEntries());
		} catch (FileNotFoundException e) {
			// No file saved yet: ignore.
		} catch (Exception e) {
			// Assume File is corrupted: delete file.
			LOG.warn("Failed to load TID store file: deleting TID store file");
			LOG.debug("Failed to load TID store file", e);
			try {
				tidStoreFile.delete();
			} catch (Exception e1) {
				// Ignore
			}
		}
	}

}
