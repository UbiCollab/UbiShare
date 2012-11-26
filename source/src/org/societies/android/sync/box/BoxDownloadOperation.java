/**
 * Copyright 2012 UbiCollab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.societies.android.sync.box;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.societies.android.box.BoxConstants;
import org.societies.android.platform.entity.Entity;

import android.content.ContentResolver;
import android.util.Log;

import com.box.androidlib.BoxSynchronous;
import com.box.androidlib.DAO.BoxFile;
import com.box.androidlib.ResponseListeners.FileDownloadListener;
import com.box.androidlib.ResponseParsers.DefaultResponseParser;

/**
 * Thread handling box download operation.
 * 
 * @author Kato
 */
public class BoxDownloadOperation extends Thread {
	
	private static final String TAG = "BoxDownloadOperation";

	private BoxSynchronous mBoxInstance;
	private String mAuthToken;
	private ContentResolver mResolver;
	private BoxFile mFile;
	
	/**
	 * Initializes a new download operation.
	 * @param file The file to download.
	 * @param authToken The authentication token.
	 * @param resolver The content resolver.
	 */
	public BoxDownloadOperation(
			BoxFile file, String authToken, ContentResolver resolver) {
		mFile = file;
		mAuthToken = authToken;
		mResolver = resolver;
		mBoxInstance = BoxSynchronous.getInstance(BoxConstants.API_KEY);
	}
	
	@Override
	public void run() {
		try {
			if (isDeletedFile()) {
				Entity.deleteEntity(
						getEntityClass(), String.valueOf(mFile.getId()), mResolver);
			} else {
				String serialized = downloadFileContents();
				Entity entity = getEntity(serialized);
				
				if (entity.getId() == -1)
					entity.insert(mResolver);
				else
					entity.update(mResolver);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * Gets the entity class of the file to download.
	 * @return The entity class of the file to download.
	 * @throws ClassNotFoundException If the entity class is not found.
	 * @throws ClassCastException If the class is not a subclass of {@link Entity}.
	 */
	private Class<? extends Entity> getEntityClass()
			throws ClassNotFoundException, ClassCastException {
		String fileName = mFile.getFileName();
		int separatorIndex = fileName.indexOf(BoxHandler.ENTITY_FILE_NAME_SEPARATOR);
		String className = fileName.substring(0, separatorIndex);
		
		return Class.forName(className).asSubclass(Entity.class);
	}

	/**
	 * Gets the entity represented by the specified string.
	 * @param serialized The serialized entity.
	 * @return The entity represented by the specified string, or <code>null</code>
	 * if the entity cannot be deserialized.
	 * @throws ClassNotFoundException If the entity class is not found.
	 */
	private Entity getEntity(String serialized) throws ClassNotFoundException {
		Class<? extends Entity> entityClass = getEntityClass();
		
		Entity entity = Entity.deserialize(serialized, entityClass);
		
		if (entity != null) {
			entity.setGlobalId(String.valueOf(mFile.getId()));
			entity.fetchLocalId(mResolver);
		}
		
		return entity;
	}
	
	/**
	 * Checks whether the file to be downloaded is deleted.
	 * @return Whether or not the file to be downloaded is deleted.
	 */
	private boolean isDeletedFile() {
		return mFile.getFileName().endsWith(BoxHandler.ENTITY_DELETED_EXTENSION);
	}

	/**
	 * Downloads the content of the file into a string.
	 * @return A string containing the contents of the file.
	 * @throws IOException If an error occurs while downloading.
	 */
	private String downloadFileContents() throws IOException {
		String fileContents = null;
		
		ByteArrayOutputStream outStream = null;
		try {
			outStream = new ByteArrayOutputStream();
			
			DefaultResponseParser response = mBoxInstance.download(
					mAuthToken, mFile.getId(), outStream, null, null, null);
			
			if (!response.getStatus().equals(FileDownloadListener.STATUS_DOWNLOAD_OK))
				throw new IOException(
						"Failed to download file: " + response.getStatus());
			else
				fileContents = outStream.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			if (outStream != null)
				outStream.close();
		}
		
		return fileContents;
	}
}
