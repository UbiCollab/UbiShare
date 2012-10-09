package org.societies.android.sync.box;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * A service wrapper for BoxSyncAdapter.
 * 
 * @author Kato
 */
public class BoxSyncService extends Service {
	
	private static BoxSyncAdapter mSyncAdapter = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		if (mSyncAdapter == null)
			mSyncAdapter = new BoxSyncAdapter(getApplicationContext(), true);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mSyncAdapter.getSyncAdapterBinder();
	}
}
