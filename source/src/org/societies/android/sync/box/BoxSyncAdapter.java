package org.societies.android.sync.box;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.societies.android.api.cis.SocialContract;
import org.societies.android.box.BoxConstants;

import com.box.androidlib.Box;
import com.box.androidlib.BoxSynchronous;
import com.box.androidlib.DAO.BoxFile;
import com.box.androidlib.DAO.BoxFolder;
import com.box.androidlib.DAO.SearchResult;
import com.box.androidlib.ResponseListeners.FileUploadListener;
import com.box.androidlib.ResponseParsers.AccountTreeResponseParser;
import com.box.androidlib.ResponseParsers.SearchResponseParser;
import com.box.androidlib.Utils.Cancelable;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * Adapter for synchronizing local data with Box.
 * 
 * @author Kato
 */
public class BoxSyncAdapter extends AbstractThreadedSyncAdapter {
	
	private static final String TAG = "BoxSyncAdapter";
	
	private static final String BOX_ROOT_FOLDER = "UbiShare";
	private static final String BOX_PEOPLE_FOLDER = "people";
	
	private Context mContext;
	private AccountManager mAccountManager;
	private final Handler mHandler;
	private String mAuthToken;
	private List<Cancelable> mCancelables;
	private Map<String, Boolean> mUploadStatuses;
	private Map<String, Long> mFolderIds;

	/**
	 * Initiates a new BoxSyncAdapter.
	 * @param context The context to operate in.
	 * @param autoInitialize Whether or not to auto initialize the adapter.
	 */
	public BoxSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		
		mContext = context;
		mAccountManager = AccountManager.get(context);
		mHandler = new Handler();
		mCancelables = Collections.synchronizedList(new ArrayList<Cancelable>());
		mUploadStatuses = Collections.synchronizedMap(new HashMap<String, Boolean>());
		mFolderIds = new HashMap<String, Long>();
	}
	
	@Override
	public void onSyncCanceled() {
		synchronized (mCancelables) {
			for (Cancelable cancelable : mCancelables) {
				cancelable.cancel();
			}
		}
		
		super.onSyncCanceled();
	}
	
	@Override
	public void onPerformSync(
			Account account,
			Bundle extras,
			String authority,
			ContentProviderClient provider,
			SyncResult syncResult
	) {
		try {
			mAuthToken = mAccountManager.blockingGetAuthToken(
				account, BoxConstants.AUTH_TOKEN_FLAG, true);
			
			initFolderIds();
			
			syncPeople();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * Initiates the map of folders and IDs.
	 */
	private void initFolderIds() {
		try {
			SearchResponseParser searchParser =
					BoxSynchronous.getInstance(BoxConstants.API_KEY).search(
							mAuthToken,
							BOX_ROOT_FOLDER,
							Box.SORT_RELEVANCE,
							1,
							100,
							Box.DIRECTION_ASC,
							new String[0]);
			
			SearchResult searchResult = searchParser.getSearchResult();
			BoxFolder root = searchResult.getFolders().get(0);
			
			AccountTreeResponseParser treeParser =
					BoxSynchronous.getInstance(BoxConstants.API_KEY).getAccountTree(
							mAuthToken, root.getId(), new String[0]);
			
			BoxFolder treeRoot = treeParser.getFolder();
			addFolderIdsFromTree(treeRoot);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * Adds the folder IDs in a folder tree to the list.
	 * @param root The root folder.
	 */
	private void addFolderIdsFromTree(BoxFolder root) {
		for (BoxFolder subFolder : root.getFoldersInFolder())
			addFolderIdsFromTree(subFolder);
		
		mFolderIds.put(root.getFolderName(), root.getId());
	}
	
	/**
	 * Synchronizes the people.
	 */
	private void syncPeople() {
		Log.i(TAG, "Started People Sync");
		
		List<Map<String, String>> people = fetchPeople();
		
		Log.i(TAG, "Syncing " + people.size() + " people...");
		
		for (Map<String, String> person : people) {
			String serialized = serializeMap(person);
			InputStream inputStream = createInputStream(serialized);
			
			uploadData(inputStream, String.valueOf(serialized.hashCode()), mFolderIds.get(BOX_PEOPLE_FOLDER));
		}
	}
	
	/**
	 * Serializes the specified map into a string.
	 * @param map The map to serialize.
	 * @return The string representation of the map.
	 */
	private String serializeMap(Map<String, String> map) {
		String serialized = "";
		for (String key : map.keySet()) {
			serialized += String.format("%s:%s%s", key, map.get(key), "\n");
		}
		
		return serialized;
	}

	/**
	 * Creates an input stream containing the specified string.
	 * @param content The string content to stream over.
	 * @return An input stream containing the specified string.
	 */
	private InputStream createInputStream(String content) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
		return inputStream;
	}
	
	/**
	 * Fetches the list of people.
	 * @return A list of Map<String, String>.
	 */
	private List<Map<String, String>> fetchPeople() {
		Cursor cursor = null;
		
		List<Map<String, String>> peopleList = new ArrayList<Map<String, String>>();
		try {
			cursor = mContext.getContentResolver().query(
					SocialContract.People.CONTENT_URI, null, null, null, null);
			
			Log.i(TAG, "Cursor has " + cursor.getCount() + " rows.");
			
			for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
				Map<String, String> personMap = new HashMap<String, String>();
				
				for (int i = 0; i < cursor.getColumnCount(); i++)
					personMap.put(cursor.getColumnName(i), cursor.getString(i));
				
				peopleList.add(personMap);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			if (cursor != null)
				cursor.close();
		}
		
		return peopleList;
	}
	
	/**
	 * Uploads the data from the specified input stream.
	 * @param inputStream The input stream containing the data to upload.
	 * @param fileName The name of the file to upload to.
	 * @param folderId Tha ID of the folder to contain the uploaded file.
	 */
	private void uploadData(final InputStream inputStream, final String fileName, final long folderId) {
		mUploadStatuses.put(fileName, false);
		
		mHandler.post(new Runnable() {
			
			public void run() {
				Cancelable cancelable = Box.getInstance(BoxConstants.API_KEY).upload(
						mAuthToken,
						Box.UPLOAD_ACTION_UPLOAD,
						inputStream,
						fileName,
						folderId,
						new FileUploadListener() {
					
					public void onIOException(IOException ioe) {
						Log.e(TAG, ioe.getMessage(), ioe);
					}
					
					public void onProgress(long progress) {
						// Deliberately empty
					}
					
					public void onMalformedURLException(MalformedURLException mue) {
						Log.e(TAG, mue.getMessage(), mue);
					}
					
					public void onFileNotFoundException(FileNotFoundException fnfe) {
						Log.e(TAG, fnfe.getMessage(), fnfe);
					}
					
					public void onComplete(BoxFile file, String status) {
						Log.i(TAG, "Data upload completed with status: " + status);
						
						mUploadStatuses.put(fileName, status.equals(FileUploadListener.STATUS_UPLOAD_OK));
					}
				});
				
				mCancelables.add(cancelable);
			}
		});
	}
}
