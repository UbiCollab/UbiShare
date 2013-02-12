/**
 * Copyright 2012 UbiCollab.org
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.societies.android.box.BoxConstants;
import org.societies.android.platform.entity.Entity;

import android.content.ContentResolver;
import android.util.Log;

import com.box.androidlib.Box;
import com.box.androidlib.BoxSynchronous;
import com.box.androidlib.DAO.BoxFile;
import com.box.androidlib.ResponseListeners.FileUploadListener;
import com.box.androidlib.ResponseListeners.RenameListener;
import com.box.androidlib.ResponseParsers.FileResponseParser;

/**
 * Thread handling Box upload operations.
 *
 * @author Kato
 */
public class BoxUploadOperation extends Thread {
	
	private static final String TAG = "BoxUploadOperation";
	
	private Entity mEntity;
	private BoxSynchronous mBoxInstance;
	private String mAuthToken;
	private ContentResolver mResolver;
	private long mTargetId;
	private String mFileName;
	
	/**
	 * Initializes an upload operation.
	 * @param entity The entity to upload.
	 * @param fileName The name of the file to upload to, or <code>null</code>
	 * if the file name should be generated.
	 * @param targetId The ID of the folder to upload to.
	 * @param authToken The authentication token.
	 * @param resolver The content resolver.
	 */
	public BoxUploadOperation(
			Entity entity,
			String fileName,
			long targetId,
			String authToken,
			ContentResolver resolver) {
		mEntity = entity;
		mFileName = fileName;
		mTargetId = targetId;
		mBoxInstance = BoxSynchronous.getInstance(BoxConstants.API_KEY);
		mAuthToken = authToken;
		mResolver = resolver;
	}
	
	@Override
	public void run() {
		try {
			uploadEntity();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * Uploads the entity to a file.
	 * @throws IOException If an error occurs while uploading.
	 */
	private void uploadEntity() throws IOException {
		long fileId = -1;
		
		String uploadAction = Box.UPLOAD_ACTION_OVERWRITE;
		if (mFileName == null) {
			try {
				fileId = Long.parseLong(mEntity.getGlobalId());
			} catch (NumberFormatException e) {
				uploadAction = Box.UPLOAD_ACTION_UPLOAD;
			}
			mEntity.setGlobalId(null);
		} else {
			uploadAction = Box.UPLOAD_ACTION_UPLOAD;
		}
		
		FileResponseParser response = upload(
				uploadAction,
				mEntity.serialize(),
				(mFileName == null ? String.valueOf(mEntity.hashCode()) : mFileName),
				(fileId != -1 ? fileId : mTargetId));
		
		if (response.getStatus().equals(FileUploadListener.STATUS_UPLOAD_OK)) {
			if (mFileName == null) {
				fileId = response.getFile().getId();
				mEntity.setGlobalId(String.valueOf(fileId));
				renameFile(response.getFile());
			}
			
			mEntity.setDirty(0);
			mEntity.update(mResolver);
		} else if (response.getStatus().equals(FileUploadListener.STATUS_FILE_DELETED)) {
			// This will only occur when debugging.
			mEntity.setGlobalId(null);
			uploadEntity();
		} else {
			throw new IOException("Failed to upload entity: " + response.getStatus());
		}
	}
	
	/**
	 * Renames the specified file.
	 * @param file The file to rename.
	 * @throws IOException If an error occurs while renaming.
	 */
	private void renameFile(BoxFile file) throws IOException {
		String status = mBoxInstance.rename(
				mAuthToken,
				Box.TYPE_FILE,
				file.getId(),
				String.format(
						BoxHandler.ENTITY_FILE_NAME_FORMAT,
						mEntity.getClass().getName(),
						file.getId()));
		
		if (!status.equals(RenameListener.STATUS_S_RENAME_NODE))
			throw new IOException("Failed to rename file: " + file.getFileName());
	}
	
	/**
	 * Uploads the specified content to a file.
	 * @param action The action to perform (see Box.upload() doc).
	 * @param content The content to upload.
	 * @param name The name of the file to upload to.
	 * @param destinationId The folder ID or file ID, depending on the action.
	 * @return The response.
	 * @throws IOException If an error occurs while uploading.
	 */
	private FileResponseParser upload(
			String action,
			String content,
			String name,
			long destinationId) throws IOException {
		return mBoxInstance.upload(
				mAuthToken,
				action,
				createInputStream(content),
				name,
				destinationId,
				null,
				null);
	}
	
	/**
	 * Creates an input stream containing the specified string.
	 * @param content The content of the stream.
	 * @return An input stream containing the specified string.
	 */
	private InputStream createInputStream(String content) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
		
		return inputStream;
	}
}
