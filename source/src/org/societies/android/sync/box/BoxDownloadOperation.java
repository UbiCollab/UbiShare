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
import com.box.androidlib.ResponseListeners.FileDownloadListener;
import com.box.androidlib.ResponseParsers.DefaultResponseParser;

/**
 * Thread handling box download operation.
 * 
 * @author Kato
 */
public class BoxDownloadOperation extends BoxOperation {
	
	private static final String TAG = "BoxDownloadOperation";

	private BoxSynchronous mBoxInstance;
	private BoxHandler mBoxHandler;
	private String mAuthToken;
	private ContentResolver mResolver;
	private long mFileId;
	private long mFolderId;
	
	/**
	 * Initializes a new download operation.
	 * @param fileId The ID of the file to download.
	 * @param folderId The ID of the folder containing the file.
	 * @param authToken The authentication token.
	 * @param resolver The content resolver.
	 * @param boxHandler The box handler instance.
	 */
	public BoxDownloadOperation(
			long fileId,
			long folderId,
			String authToken,
			ContentResolver resolver,
			BoxHandler boxHandler) {
		mFileId = fileId;
		mFolderId = folderId;
		mBoxHandler = boxHandler;
		mAuthToken = authToken;
		mResolver = resolver;
		mBoxInstance = BoxSynchronous.getInstance(BoxConstants.API_KEY);
	}
	
	@Override
	public void run() {
		try {
			String serialized = downloadFileContents();
			Entity entity = getEntity(serialized);
			
			if (entity.getId() == -1)
				entity.insert(mResolver);
			else
				entity.update(mResolver);
		} catch (IllegalStateException ise) {
			Log.e(TAG, ise.getMessage(), ise);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		} 
	}
	
	/**
	 * Gets the entity represented by the specified string.
	 * @param serialized The serialized entity.
	 * @return The entity represented by the specified string, or <code>null</code>
	 * if the entity cannot be deserialized.
	 */
	private Entity getEntity(String serialized) {
		Class<? extends Entity> entityClass = mBoxHandler.getEntityClass(mFolderId);
		
		Entity entity = Entity.deserialize(serialized, entityClass);
		
		if (entity != null)
			entity.fetchLocalId(mResolver);
		
		return entity;
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
					mAuthToken, mFileId, outStream, null, null, null);
			
			if (!response.getStatus().equals(FileDownloadListener.STATUS_DOWNLOAD_OK)) {
				throw new IOException(
						"Failed to download file: " + response.getStatus());
			} else {
				fileContents = outStream.toString();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (outStream != null)
				outStream.close();
		}
		
		return fileContents;
	}
}
