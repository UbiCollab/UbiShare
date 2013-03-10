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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.societies.android.box.BoxConstants;
import org.societies.android.platform.entity.Community;
import org.societies.android.platform.entity.CommunityActivity;
import org.societies.android.platform.entity.Entity;
import org.societies.android.platform.entity.Membership;
import org.societies.android.platform.entity.Person;
import org.societies.android.platform.entity.Sharing;

import android.content.ContentResolver;
import android.util.Log;

import com.box.androidlib.Box;
import com.box.androidlib.BoxSynchronous;
import com.box.androidlib.DAO.BoxFile;
import com.box.androidlib.DAO.BoxFolder;
import com.box.androidlib.DAO.Update;
import com.box.androidlib.ResponseListeners.CreateFolderListener;
import com.box.androidlib.ResponseListeners.GetAccountTreeListener;
import com.box.androidlib.ResponseListeners.GetFileInfoListener;
import com.box.androidlib.ResponseListeners.GetUpdatesListener;
import com.box.androidlib.ResponseListeners.InviteCollaboratorsListener;
import com.box.androidlib.ResponseListeners.RenameListener;
import com.box.androidlib.ResponseParsers.AccountTreeResponseParser;
import com.box.androidlib.ResponseParsers.FileResponseParser;
import com.box.androidlib.ResponseParsers.FolderResponseParser;
import com.box.androidlib.ResponseParsers.UpdatesResponseParser;

/**
 * Handles the interaction with Box.com
 * 
 * @author Kato
 */
public class BoxHandler {
	
	private static final String TAG = "BoxHandler";
	
	/** The character used as separator in entity file names. */
	public static final String ENTITY_FILE_NAME_SEPARATOR = "_";
	/** The file name format of uploaded entities. */
	public static final String ENTITY_FILE_NAME_FORMAT =
			"%s" + ENTITY_FILE_NAME_SEPARATOR + "%s";
	/** The extension of deleted entity files. */
	public static final String ENTITY_DELETED_EXTENSION = ".del";
	
	/** The ID of the root folder in Box. */
	public static final long BOX_ROOT_FOLDER_ID = 0;
	
	private ExecutorService mThreadPool;
	private String mAuthToken;
	private boolean mInitialized;
	private ContentResolver mResolver;
	private BoxSynchronous mBoxInstance;
	
	/**
	 * Initializes a new BoxHandler.
	 * @param resolver The content resolver.
	 */
	public BoxHandler(ContentResolver resolver) {
		mInitialized = false;
		mResolver = resolver;
	}
	
	/**
	 * Initializes the Box handler. A call to this function is mandatory before
	 * any other interaction with box.
	 * @param authToken The token used to authenticate.
	 */
	public void initialize(String authToken) {
		mAuthToken = authToken;
		mThreadPool = Executors.newSingleThreadExecutor();
		mBoxInstance = BoxSynchronous.getInstance(BoxConstants.API_KEY);
		
		mInitialized = true;
	}
	
	/**
	 * Cancels all running operations.
	 * @throws InterruptedException If the thread is interrupted while waiting.
	 */
	public void cancelRunningOperations() throws InterruptedException {
		mThreadPool.shutdownNow();
		mThreadPool.awaitTermination(30, TimeUnit.SECONDS);
	}
	
	/**
	 * Uploads a community to Box.
	 * @param community The community to upload.
	 * @throws IOException If an error occurs while uploading.
	 */
	public void uploadCommunity(Community community) throws IOException {
		if (!mInitialized)
			throw new IllegalStateException("Not initialized.");
		else {
			long targetId;
			try {
				targetId = Long.parseLong(community.getGlobalId());
			} catch (NumberFormatException e) {
				long creationDate = new Date().getTime() / 1000;
				BoxFolder folder = createFolder(
						community.getName() + ENTITY_FILE_NAME_SEPARATOR + creationDate,
						BOX_ROOT_FOLDER_ID);
				
				community.setGlobalId(String.valueOf(folder.getId()));
				targetId = folder.getId();
			}
			
			addUploadOperation(
					community,
					String.format(
							ENTITY_FILE_NAME_FORMAT,
							community.getClass().getName(),
							community.getGlobalId()),
					targetId);
		}
	}
	
	/**
	 * Uploads a community activity to Box.
	 * @param activity The activity to upload.
	 */
	public void uploadCommunityActivity(CommunityActivity activity) {
		if (!mInitialized)
			throw new IllegalStateException("Not initialized.");
		else {
			long targetId = Long.parseLong(activity.getGlobalIdFeedOwner());
			addUploadOperation(activity, null, targetId);
		}
	}
	
	/**
	 * Uploads a sharing to Box.
	 * @param sharing The sharing to upload.
	 */
	public void uploadSharing(Sharing sharing)
	{
		if (!mInitialized)
			throw new IllegalStateException("Not initialized.");
		else {
			long targetId = Long.parseLong(sharing.getGlobalIdCommunity());
			addUploadOperation(sharing, null, targetId);
		}
	}
	
	/**
	 * Uploads a membership to Box.
	 * @param membership The membership to upload.
	 * @throws Exception If an error occurs while uploading.
	 */
	public void uploadMemberships(List<Membership> memberships) throws Exception {
		if (!mInitialized)
			throw new IllegalStateException("Not initialized.");
		else if (memberships.size() == 0)
			return;
		else {
			Community community = Entity.getEntity(
					Community.class, memberships.get(0).getCommunityId(), mResolver);
			
			long targetId = Long.parseLong(community.getGlobalId());
			List<String> emails = new ArrayList<String>();
			
			for (Membership membership : memberships) {
				addUploadOperation(membership, null, targetId);
				
				if (community.getOwnerId() != membership.getMemberId()) {
					Person collaborator = Entity.getEntity(
							Person.class, membership.getMemberId(), mResolver);
					emails.add(collaborator.getUserName());
				}
			}
			
			if (emails.size() > 0)
				inviteCollaborators(emails.toArray(new String[emails.size()]), targetId);
		}
	}
	
	/**
	 * Processes the specified Box updates.
	 * @param updates The Box updates to process, or <code>null</code> to
	 * retrieve all the data from Box.
	 * @throws IOException If an error occurs while processing updates.
	 */
	public void processUpdates(List<Update> updates) throws IOException {
		if (updates == null)
			fetchAllEntities();
		else {
			for (Update update : updates) {
				if (update.getFiles().size() > 0) {
					Log.i(TAG, "Update contains the following files:");
					
					for (BoxFile file : update.getFiles())
						Log.i(TAG, file.getFileName());
					
					downloadEntities(update.getFiles());
				} else {
					Log.i(TAG, "Update does not contain files. Fetching all files in folder.");
					
					downloadAllEntities(update.getFolderId());
				}
			}
		}
	}
	
	/**
	 * Gets a list of updates since the specified timestamp.
	 * @param beginTimestamp The Unix time (in seconds) of the oldest updates to get.
	 * @return A list of updates since the specified timestamp.
	 * @throws IOException If an error occurs while fetching updates.
	 */
	public List<Update> getUpdatesSince(long beginTimestamp) throws IOException {
		long endTimeStamp = (new Date().getTime() / 1000) + (30 * 60);
		beginTimestamp = beginTimestamp - (20);
		
		UpdatesResponseParser response = mBoxInstance.getUpdates(
				mAuthToken, beginTimestamp, endTimeStamp, null);
		
		if (response.getStatus().equals(GetUpdatesListener.STATUS_S_GET_UPDATES))
			return response.getUpdates();
		else
			throw new IOException("Failed to get updates: " + response.getStatus());
	}
	
	/**
	 * Marks the specified entities as deleted in Box.
	 * @param deletedEntities The entities that is deleted.
	 * @throws Exception If an error occurs while marking.
	 */
	public void deleteEntities(List<Entity> deletedEntities) throws Exception {
		for (Entity entity : deletedEntities) {
			if (entity instanceof Community)
				deleteCommunity((Community) entity);
			else {
				try {
					FileResponseParser response =  mBoxInstance.getFileInfo(
							mAuthToken, Long.parseLong(entity.getGlobalId()));
					
					if (!response.getStatus().equals(GetFileInfoListener.STATUS_S_GET_FILE_INFO))
						Log.e(TAG, "Failed to get file info: " + response.getStatus());
					else
						markAsDeleted(response.getFile(), entity);
				} catch (NumberFormatException e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}
		}
	}
	
	/**
	 * Marks a community as deleted in Box.
	 * @param community The community that is deleted.
	 * @throws Exception If an error occurs while marking.
	 */
	private void deleteCommunity(Community community) throws Exception {
		try {
			long targetId = Long.parseLong(community.getGlobalId());
			BoxFolder communityFolder = getDirectoryTree(targetId);
			
			for (BoxFile file : communityFolder.getFilesInFolder())
				markAsDeleted(file, community);
		} catch (NumberFormatException e) {
			Log.i(TAG, "Tried to delete unsynced community.");
		}
	}
	
	/**
	 * Marks a file as deleted in Box.
	 * @param file The file to mark.
	 * @param entity The entity related to the file.
	 * @throws Exception If an error occurs while marking.
	 */
	private void markAsDeleted(BoxFile file, Entity entity) throws Exception {
		if (!file.getFileName().endsWith(ENTITY_DELETED_EXTENSION)) {
			String status = mBoxInstance.rename(
					mAuthToken,
					Box.TYPE_FILE,
					file.getId(),
					file.getFileName() + ENTITY_DELETED_EXTENSION);
			
			if (status.equals(RenameListener.STATUS_E_NO_ACCESS)
					|| status.equals(RenameListener.STATUS_E_FILENAME_IN_USE)
					|| status.equals(RenameListener.STATUS_E_RENAME_NODE))
				Entity.setUnsuccessfulDelete(entity, mResolver);
			else if (status.equals(RenameListener.STATUS_E_NO_TARGET))
				Entity.deleteEntity(entity.getClass(), entity.getGlobalId(), mResolver);
		}
	}
	
	/**
	 * Waits for running operations to complete.
	 * @param stop Whether or not the execution of operations should stop after the currently
	 * running operations are finished.
	 * @throws InterruptedException If the thread is interrupted while waiting.
	 */
	public void waitForRunningOperationsToComplete(boolean stop) throws InterruptedException {
		mThreadPool.shutdown();
		mThreadPool.awaitTermination(120, TimeUnit.SECONDS);
		
		if (!stop) {
			mThreadPool = Executors.newSingleThreadExecutor();
		}
	}
	
	/**
	 * Adds an upload operation to the thread pool.
	 * @param entity The entity to upload.
	 * @param fileName The name of the file to upload to, or <code>null</code> if the
	 * name should be automatically generated.
	 * @param targetId The ID of the folder to upload to.
	 */
	private void addUploadOperation(Entity entity, String fileName, long targetId) {
		BoxUploadOperation operation =
				new BoxUploadOperation(
						entity, fileName, targetId, mAuthToken, mResolver);
		
		mThreadPool.execute(operation);
	}
	
	/**
	 * Invites a collaborator to the specified folder.
	 * @param emails The email addresses of the collaborators to invite.
	 * @param targetId The ID of the folder to share.
	 * @throws Exception If an error occurs while inviting.
	 */
	private void inviteCollaborators(String[] emails, long targetId) throws Exception {
		String status = mBoxInstance.inviteCollaborators(
				mAuthToken,
				Box.TYPE_FOLDER,
				targetId,
				null,
				emails,
				Box.ITEM_ROLE_EDITOR,
				false,
				true,
				null);
		
		if (!status.equals(InviteCollaboratorsListener.STATUS_S_INVITE_COLLABORATORS)
				&& !status.equals(InviteCollaboratorsListener.STATUS_USER_ALREADY_COLLABORATOR))
			throw new IOException("Failed to invite collaborator: " + status);
	}
	
	/**
	 * Creates a folder in Box.
	 * @param name The name of the folder.
	 * @param parentId The ID of the parent folder. The ID 0 (zero) is the
	 * root folder.
	 * @return The newly created folder, or <code>null</code> on error.
	 * @throws IOException If an error occurs while creating folder.
	 */
	private BoxFolder createFolder(String name, long parentId) throws IOException {
		FolderResponseParser response = mBoxInstance.createFolder(
				mAuthToken, parentId, name, false);
		
		if (response.getStatus().equals(CreateFolderListener.STATUS_CREATE_OK))
			return response.getFolder();
		else
			return null;
	}
	
	/**
	 * Downloads the specified Box files.
	 * @param files The list of files to download.
	 */
	private void downloadEntities(List<? extends BoxFile> files) {
		if (files.size() > 0) {
			BoxDownloadOperation operation = new BoxDownloadOperation(
					files, mAuthToken, this, mResolver);
			
			mThreadPool.execute(operation);
		}
	}
	
	/**
	 * Gets the files in the specified Box folder.
	 * @param folderId The ID of the folder.
	 * @return The list of files in the specified folder.
	 */
	public List<BoxFile> getFilesInFolder(long folderId) {
		List<BoxFile> files = new ArrayList<BoxFile>();
		try {
			BoxFolder root = getDirectoryTree(folderId);
			
			for (BoxFile file : root.getFilesInFolder())
				files.add(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return files;
	}
	
	/**
	 * Downloads all the entities in the directory tree under the
	 * specified root folder.
	 * @param rootFolder The root folder.
	 */
	private void downloadAllEntities(BoxFolder rootFolder) {
		downloadEntities(rootFolder.getFilesInFolder());
		
		for (BoxFolder subFolder : rootFolder.getFoldersInFolder())
			downloadAllEntities(subFolder);
	}
	
	/**
	 * Downloads all the entities in the specified folder.
	 * @param folderId The ID of the folder.
	 * @throws IOException If an error occurs while downloading.
	 */
	private void downloadAllEntities(long folderId) throws IOException {
		downloadAllEntities(getDirectoryTree(folderId));
	}
	
	/**
	 * Gets the directory three of the specified folder.
	 * @param rootFolderId The ID of the root folder in the tree.
	 * @return The directory tree of the specified folder.
	 * @throws IOException If an error occurs while fetching directory tree.
	 */
	private BoxFolder getDirectoryTree(long rootFolderId) throws IOException {
		AccountTreeResponseParser treeParser = mBoxInstance.getAccountTree(
				mAuthToken, rootFolderId, new String[] { Box.PARAM_SIMPLE });
		
		if (!treeParser.getStatus().equals(GetAccountTreeListener.STATUS_LISTING_OK))
			throw new IOException("Failed to get directory tree of folder: " + rootFolderId);
		else
			return treeParser.getFolder();
	}
	
	/**
	 * Fetches all the entities from Box.
	 * @throws IOException If an error occurs while fetching.
	 */
	private void fetchAllEntities() throws IOException {
		downloadAllEntities(BOX_ROOT_FOLDER_ID);
	}
}
