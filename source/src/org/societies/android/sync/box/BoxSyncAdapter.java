package org.societies.android.sync.box;

import java.util.List;

import org.societies.android.api.cis.SocialContract;
import org.societies.android.box.BoxConstants;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

/**
 * Adapter for synchronizing SocialProvider data with Box.
 * 
 * @author Kato
 */
public class BoxSyncAdapter extends AbstractThreadedSyncAdapter {
	
	private static final String TAG = "BoxSyncAdapter";
	
	private Context mContext;
	private AccountManager mAccountManager;
	private BoxHandler mBoxHandler;

	/**
	 * Initiates a new BoxSyncAdapter.
	 * @param context The context to operate in.
	 * @param autoInitialize Whether or not to auto initialize the adapter.
	 */
	public BoxSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		
		mContext = context;
		mAccountManager = AccountManager.get(context);
		mBoxHandler = new BoxHandler();
	}
	
	@Override
	public void onSyncCanceled() {
		mBoxHandler.cancelRunningOperations();
		
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
			String authToken = mAccountManager.blockingGetAuthToken(
				account, BoxConstants.AUTH_TOKEN_FLAG, true);
			
			mBoxHandler.initialize(authToken);
			
			syncPeople();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * Synchronizes the entries in the People table.
	 */
	private void syncPeople() {
		Log.i(TAG, "Started People Sync");
		
		List<DatabaseEntity> entities = DatabaseEntity.getEntities(
				mContext, SocialContract.People.CONTENT_URI, null, null, null, null);
		
		for (DatabaseEntity entity : entities)
			mBoxHandler.uploadEntity(
					entity,
					entity.mColumnValues.getAsString(SocialContract.People.EMAIL),
					BoxHandler.BOX_PEOPLE_FOLDER);
	}
}
