/**
 * 
 */
package org.societies.android.platform;

import java.util.Date;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * @author Babak Farshchian
 *
 */
public class SocietiesSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "SocietiesSyncAdapter";
    private final AccountManager mAccountManager;
    private final Context mContext;

    private Date mLastUpdated;

	public SocietiesSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
        mContext = context;
        mAccountManager = AccountManager.get(context);
	}

	/* (non-Javadoc)
	 * @see android.content.AbstractThreadedSyncAdapter#onPerformSync(android.accounts.Account, android.os.Bundle, java.lang.String, android.content.ContentProviderClient, android.content.SyncResult)
	 */
	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		// TODO Auto-generated method stub

	}

}
