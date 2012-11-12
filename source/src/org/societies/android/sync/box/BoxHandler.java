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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.societies.android.box.BoxConstants;
import org.societies.android.platform.entity.Community;
import org.societies.android.platform.entity.CommunityActivity;
import org.societies.android.platform.entity.Entity;
import org.societies.android.platform.entity.Membership;
import org.societies.android.platform.entity.Person;
import org.societies.android.platform.entity.PersonActivity;
import org.societies.android.platform.entity.Relationship;
import org.societies.android.platform.entity.Service;
import org.societies.android.platform.entity.ServiceActivity;
import org.societies.android.platform.entity.Sharing;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.box.androidlib.Box;
import com.box.androidlib.BoxSynchronous;
import com.box.androidlib.DAO.BoxFolder;
import com.box.androidlib.DAO.DAO;
import com.box.androidlib.DAO.SearchResult;
import com.box.androidlib.ResponseParsers.AccountTreeResponseParser;
import com.box.androidlib.ResponseParsers.SearchResponseParser;

/**
 * Handles the interaction with Box.com
 * 
 * @author Kato
 */
public class BoxHandler {
	
	private static final String TAG = "BoxHandler";
	
	/* TEMPORARY CONSTANTS */
	private static final String BOX_FOLDER_ROOT = "UbiShare";
	/* END TEMPORARY CONSTANTS */
	
	private List<BoxOperation> mOperations;
	private Map<Class<? extends Entity>, String> mFolderMap;
	private BoxFolder mRootFolder;
	private String mAuthToken;
	private boolean mInitialized;
	private ContentResolver mResolver;
	private Context mContext;
	private SharedPreferences mPreferences;
	
	/**
	 * Initializes a new BoxHandler.
	 * @param context The context to operate in.
	 */
	public BoxHandler(Context context) {
		mOperations = Collections.synchronizedList(new ArrayList<BoxOperation>());
		mFolderMap = new HashMap<Class<? extends Entity>, String>();
		mInitialized = false;
		mResolver = context.getContentResolver();
		mContext = context;
	}
	
	/**
	 * Initializes the Box handler. A call to this function is mandatory before
	 * any other interaction with box.
	 * @param authToken The token used to authenticate.
	 */
	public void initialize(String authToken) {
		mAuthToken = authToken;
		
		mPreferences = mContext.getSharedPreferences(
				BoxConstants.PREFERENCE_FILE, Context.MODE_PRIVATE);
		
		initFolderMappings();
		loadPreferences();
		
		if (mRootFolder == null)
			fetchDirectoryTree();
		
		if (mRootFolder.getFoldersInFolder().size() < 9) {
			createFolderStructure();
			fetchDirectoryTree();
		}
		
		mInitialized = true;
	}
	
	/**
	 * Cancels all running operations.
	 */
	public void cancelRunningOperations() {
		synchronized (mOperations) {
			for (BoxOperation operation : mOperations) {
				operation.cancel();
			}
		}
	}
	
	/**
	 * Uploads a database entity to Box.
	 * @param entity The entity to upload.
	 */
	public void uploadEntity(Entity entity) {
		if (!mInitialized)
			throw new IllegalStateException("Not initialized.");
		else {
			BoxUploadOperation operation =
					new BoxUploadOperation(entity, mAuthToken, this, mResolver);
			
			mOperations.add(operation);
			operation.start();
		}
	}
	
	/**
	 * Gets the folder ID of the specified entity class.
	 * @param entityClass The entity class.
	 * @return The ID of the folder related to the specified entity class.
	 */
	public long getFolderId(Class<? extends Entity> entityClass) {
		for (BoxFolder subFolder : mRootFolder.getFoldersInFolder()) {
			if (subFolder.getFolderName().equals(mFolderMap.get(entityClass)))
				return subFolder.getId();
		}
		
		throw new IllegalArgumentException(
				"Folder of entity type " + entityClass.getName() + " not found");
	}
	
	/**
	 * Initializes the mapping of entities and box folders.
	 */
	private void initFolderMappings() {
		mFolderMap.put(Community.class, "communities");
		mFolderMap.put(CommunityActivity.class, "communities_activity");
		mFolderMap.put(Membership.class, "memberships");
		mFolderMap.put(Person.class, "people");
		mFolderMap.put(PersonActivity.class, "people_activity");
		mFolderMap.put(Relationship.class, "relationships");
		mFolderMap.put(Service.class, "services");
		mFolderMap.put(ServiceActivity.class, "services_activity");
		mFolderMap.put(Sharing.class, "sharings");
	}
	
	/**
	 * Loads the preferences.
	 */
	private void loadPreferences() {
		String rootJson = mPreferences.getString(
				BoxConstants.PREFERENCE_DIRECTORY_TREE, null);
		
		if (rootJson != null)
			mRootFolder = DAO.fromJSON(rootJson, BoxFolder.class);
	}
	
	/**
	 * Fetches the directory three of the root folder and stores it in 
	 * the shared preferences.
	 */
	private void fetchDirectoryTree() {
		try {
			// TODO: Add root folder to shared preferences
			SearchResponseParser searchParser =
					BoxSynchronous.getInstance(BoxConstants.API_KEY).search(
							mAuthToken,
							BOX_FOLDER_ROOT,
							Box.SORT_RELEVANCE,
							1,
							100,
							Box.DIRECTION_DESC,
							new String[0]);
			
			SearchResult searchResult = searchParser.getSearchResult();
			BoxFolder root = searchResult.getFolders().get(0);
			
			AccountTreeResponseParser treeParser =
					BoxSynchronous.getInstance(BoxConstants.API_KEY).getAccountTree(
							mAuthToken,
							root.getId(),
							new String[] {
								Box.PARAM_SIMPLE,
								Box.PARAM_NOFILES
							});
			
			mRootFolder = treeParser.getFolder();
			
			mPreferences.edit().putString(
					BoxConstants.PREFERENCE_DIRECTORY_TREE, DAO.toJSON(mRootFolder));
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * Gets the ID of the root folder.
	 * @return The ID of the root folder.
	 */
	private long getRootFolderId() {
		if (mRootFolder != null)
			return mRootFolder.getId();
		else
			throw new IllegalStateException("Root folder not initialized");
	}
	
	/**
	 * Creates the folder structure.
	 */
	private void createFolderStructure() {
		try {
			for (Class<? extends Entity> entityClass : mFolderMap.keySet())
				BoxSynchronous.getInstance(BoxConstants.API_KEY).createFolder(
						mAuthToken,
						getRootFolderId(),
						mFolderMap.get(entityClass),
						false);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
}
