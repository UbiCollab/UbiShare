package org.societies.android.sync.box;

import java.io.IOException;

import org.societies.android.box.BoxConstants;

import com.box.androidlib.Box;
import com.box.androidlib.DAO.User;
import com.box.androidlib.ResponseListeners.GetAccountInfoListener;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

/**
 * Adapter for synchronizing local data with Box.
 * 
 * @author Kato
 */
public class BoxSyncAdapter extends AbstractThreadedSyncAdapter {
	
	private static final String TAG = "BoxSyncAdapter";
	
	private AccountManager mAccountManager;

	/**
	 * Initiates a new BoxSyncAdapter.
	 * @param context The context to operate in.
	 * @param autoInitialize Whether or not to auto initialize the adapter.
	 */
	public BoxSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		
		mAccountManager = AccountManager.get(context);
	}

	@Override
	public void onPerformSync(
			Account account,
			Bundle extras,
			String authority,
			ContentProviderClient provider,
			SyncResult syncResult
	) {
		String authToken = null;
		
		try {
			authToken = mAccountManager.blockingGetAuthToken(
					account,
					BoxConstants.AUTH_TOKEN_FLAG,
					true);
			
			Log.i(TAG, "(onPerformSync) authToken: " + authToken);
			
			Box.getInstance(BoxConstants.API_KEY).getAccountInfo(authToken, new GetAccountInfoListener() {
				public void onIOException(IOException e) {
					Log.e(TAG, e.getMessage());
				}
				
				public void onComplete(User user, String status) {
					if (user != null)
						Log.i(TAG, "User: " + user.getLogin());
				}
			});
		} catch (Exception e) {
		}
	}

}
