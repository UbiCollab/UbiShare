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

import org.societies.android.box.BoxConstants;

import android.os.Handler;
import android.util.Log;

import com.box.androidlib.Box;
import com.box.androidlib.BoxSynchronous;
import com.box.androidlib.DAO.BoxFile;
import com.box.androidlib.DAO.BoxFolder;
import com.box.androidlib.DAO.SearchResult;
import com.box.androidlib.ResponseListeners.FileUploadListener;
import com.box.androidlib.ResponseParsers.AccountTreeResponseParser;
import com.box.androidlib.ResponseParsers.SearchResponseParser;
import com.box.androidlib.Utils.Cancelable;

/**
 * Handles the interaction with Box.net
 * 
 * @author Kato
 */
public class BoxHandler {
	
	private static final String TAG = "BoxHandler";
	
	/* TEMPORARY CONSTANTS */
	public static final String BOX_ROOT_FOLDER = "UbiShare";
	public static final String BOX_PEOPLE_FOLDER = "people";
	/* END TEMPORARY CONSTANTS */

	private List<Cancelable> mOperations;
	private Map<String, Boolean> mUploadStatuses;
	private Map<String, Long> mFolderIds;
	private String mAuthToken;
	private final Handler mHandler;
	
	public BoxHandler() {
		mOperations = Collections.synchronizedList(new ArrayList<Cancelable>());
		mUploadStatuses = Collections.synchronizedMap(new HashMap<String, Boolean>());
		mFolderIds = new HashMap<String, Long>();
		mHandler = new Handler();
	}
	
	/**
	 * Initializes the Box handler. A call to this function is mandatory before
	 * any other interaction with box.
	 * @param authToken The token used to authenticate.
	 */
	public void initialize(String authToken) {
		mAuthToken = authToken;
		
		initFolderIds();
	}
	
	/**
	 * Cancels all running operations.
	 */
	public void cancelRunningOperations() {
		synchronized (mOperations) {
			for (Cancelable operation : mOperations) {
				operation.cancel();
			}
		}
	}
	
	/**
	 * Uploads a database entity to Box.
	 * @param entity The entity to upload.
	 * @param fileName The name of the file to upload to.
	 * @param folderName The name of the folder to upload to.
	 */
	public void uploadEntity(DatabaseEntity entity, String fileName, String folderName) {
		// TODO: Add logic to get GLOBAL_ID
		
		InputStream inputStream = createInputStream(entity.serialize());
		uploadData(inputStream, fileName, mFolderIds.get(folderName));
	}
	
	/**
	 * Uploads the data from the specified input stream.
	 * @param inputStream The input stream containing the data to upload.
	 * @param fileName The name of the file to upload to.
	 * @param folderId The ID of the folder to contain the uploaded file.
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
					
					public void onProgress(long progress) { /* Not used */ }
					
					public void onMalformedURLException(MalformedURLException mue) {
						Log.e(TAG, mue.getMessage(), mue);
					}
					
					public void onFileNotFoundException(FileNotFoundException fnfe) {
						Log.e(TAG, fnfe.getMessage(), fnfe);
					}
					
					public void onComplete(BoxFile file, String status) {
						mUploadStatuses.put(fileName, status.equals(FileUploadListener.STATUS_UPLOAD_OK));
						
						try {
							inputStream.close();
						} catch (IOException e) {
							Log.e(TAG, e.getMessage(), e);
						}
					}
				});
				
				mOperations.add(cancelable);
			}
		});
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
							Box.DIRECTION_DESC,
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
}
