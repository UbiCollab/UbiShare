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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.societies.android.box.BoxConstants;
import org.societies.android.platform.entity.Community;
import org.societies.android.platform.entity.CommunityActivity;
import org.societies.android.platform.entity.Entity;
import org.societies.android.platform.entity.Membership;
import org.societies.android.platform.entity.Sharing;

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
	private List<? extends BoxFile> mFiles;
	private BoxHandler mBoxHandler;
	private List<String> mMissingCommunities;
	
	/**
	 * Initializes a new download operation.
	 * @param files The files to download.
	 * @param authToken The authentication token.
	 * @param boxHandler The BoxHandler instance.
	 * @param resolver The content resolver.
	 */
	public BoxDownloadOperation(
			List<? extends BoxFile> files,
			String authToken,
			BoxHandler boxHandler,
			ContentResolver resolver) {
		mFiles = files;
		mAuthToken = authToken;
		mResolver = resolver;
		mBoxHandler = boxHandler;
		mBoxInstance = BoxSynchronous.getInstance(BoxConstants.API_KEY);
		mMissingCommunities = new ArrayList<String>();
	}
	
	@Override
	public void run() {
		try {
			LinkedList<BoxFile> downloadQueue = new LinkedList<BoxFile>();
			
			for (BoxFile boxFile : mFiles) {
				if (getEntityClass(boxFile) == Community.class)
					downloadQueue.addFirst(boxFile);
				else
					downloadQueue.addLast(boxFile);
			}
			
			for (BoxFile boxFile : downloadQueue)
				processFile(boxFile);
			
			for (String communityGlobalId : mMissingCommunities) {
				long globalId = Long.parseLong(communityGlobalId);
				
				List<BoxFile> communityFiles = mBoxHandler.getFilesInFolder(globalId);
				
				new BoxDownloadOperation(
						communityFiles, mAuthToken, mBoxHandler, mResolver).run();
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * Processes the specified Box file.
	 * @param boxFile The file to process.
	 * @throws Exception If an error occurs while processing file.
	 */
	private void processFile(BoxFile boxFile) throws Exception {
		Class<? extends Entity> entityClass = getEntityClass(boxFile);
		if (entityClass == null)
			return;
		
		if (isDeletedFile(boxFile)) {
			Entity.deleteEntity(
					entityClass, String.valueOf(boxFile.getId()), mResolver);
		} else {
			String serialized = downloadFileContents(boxFile);
			Entity entity = getEntity(boxFile, serialized, entityClass);
			
			if (entity != null) {
				if (communityExists(entity)) {
					entity.setAccountName(Entity.SELECTION_ACCOUNT_NAME);
					entity.setAccountType(Entity.SELECTION_ACCOUNT_TYPE);
					
					if (entity.getId() == -1)
						entity.insert(mResolver);
					else
						entity.update(mResolver);
				}
			}
		}
	}

	/**
	 * Gets the entity class of the file to download.
	 * @param boxFile The file to get entity class of.
	 * @return The entity class of the file to download.
	 * @throws ClassCastException If the class is not a subclass of {@link Entity}.
	 */
	private Class<? extends Entity> getEntityClass(BoxFile boxFile) throws ClassCastException {
		String fileName = boxFile.getFileName();
		int separatorIndex = fileName.indexOf(BoxHandler.ENTITY_FILE_NAME_SEPARATOR);
		
		if (separatorIndex != -1) {
			String className = fileName.substring(0, separatorIndex);
			
			try {
				return Class.forName(className).asSubclass(Entity.class);
			} catch (ClassNotFoundException e) {
				Log.i(TAG, "Unknown file: " + fileName);
			}
		}
		
		return null;
	}

	/**
	 * Gets the entity represented by the specified string.
	 * @param boxFile The file currently in progress.
	 * @param serialized The serialized entity.
	 * @param entityClass The class of the entity.
	 * @return The entity represented by the specified string, or <code>null</code>
	 * if the entity cannot be deserialized.
	 * @throws ClassNotFoundException If the entity class is not found.
	 */
	private Entity getEntity(BoxFile boxFile, String serialized, Class<? extends Entity> entityClass)
			throws ClassNotFoundException {
		Entity entity = Entity.deserialize(serialized, entityClass);
		
		if (entity != null) {
			if (entity.getGlobalId() == null || entity.getGlobalId().length() == 0)
				entity.setGlobalId(String.valueOf(boxFile.getId()));
			
			entity.fetchLocalId(mResolver);
		}
		
		return entity;
	}
	
	/**
	 * Checks whether the file to be downloaded is deleted.
	 * @param boxFile The file to check.
	 * @return Whether or not the file to be downloaded is deleted.
	 */
	private boolean isDeletedFile(BoxFile boxFile) {
		return boxFile.getFileName().endsWith(BoxHandler.ENTITY_DELETED_EXTENSION);
	}
	
	/**
	 * Checks whether the community of the specified entity exists. If not,
	 * the community needs to be downloaded before inserting the entity into
	 * the database.
	 * @param entity The entity to check.
	 * @return Whether or not the community of the entity exists.
	 * @throws Exception If an error occurs while interacting with the database.
	 */
	private boolean communityExists(Entity entity) throws Exception {
		if (entity instanceof Community)
			return true;
		
		String communityGlobalId = getCommunityGlobalId(entity);
		
		if (Community.communityExists(communityGlobalId, mResolver)) {
			return true;
		} else {
			if (communityGlobalId != null)
				mMissingCommunities.add(communityGlobalId);
			
			return false;
		}
	}
	
	/**
	 * Gets the global ID of the community related to the specified entity.
	 * @param entity An entity related to a community.
	 * @return The global ID of the community related to the entity.
	 */
	private String getCommunityGlobalId(Entity entity) {
		String communityGlobalId = null;
		if (entity instanceof Membership)
			communityGlobalId = ((Membership) entity).getGlobalIdCommunity();
		else if (entity instanceof CommunityActivity)
			communityGlobalId = ((CommunityActivity) entity).getGlobalIdFeedOwner();
		else if (entity instanceof Sharing)
			communityGlobalId = ((Sharing) entity).getGlobalIdCommunity();
		else if (entity instanceof Community)
			communityGlobalId = entity.getGlobalId();
		
		return communityGlobalId;
	}

	/**
	 * Downloads the content of the file into a string.
	 * @param boxFile The file to download.
	 * @return A string containing the contents of the file.
	 * @throws IOException If an error occurs while downloading.
	 */
	private String downloadFileContents(BoxFile boxFile) throws IOException {
		String fileContents = null;
		
		ByteArrayOutputStream outStream = null;
		try {
			outStream = new ByteArrayOutputStream();
			
			DefaultResponseParser response = mBoxInstance.download(
					mAuthToken, boxFile.getId(), outStream, null, null, null);
			
			if (!response.getStatus().equals(FileDownloadListener.STATUS_DOWNLOAD_OK))
				throw new IOException(
						"Failed to download file: " + response.getStatus());
			else
				fileContents = outStream.toString();
		} finally {
			if (outStream != null)
				outStream.close();
		}
		
		return fileContents;
	}
}
