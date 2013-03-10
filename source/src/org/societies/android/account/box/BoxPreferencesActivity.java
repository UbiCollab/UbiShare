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
package org.societies.android.account.box;

import org.societies.android.platform.R;
import org.societies.android.sync.box.BoxSyncAdapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;

public class BoxPreferencesActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_preferences);
    }
    
    public void fullSync(View view) {
    	requestFullSync();
    	
    	Toast.makeText(this, "Full Sync Requested.", Toast.LENGTH_LONG).show();
    }
    
    private void requestFullSync() {
    	AccountManager manager = AccountManager.get(this);
    	Account[] boxAccounts = manager.getAccountsByType(getString(R.string.box_account_type));
    	String authority = getString(R.string.provider_authority);
    	Bundle extras = new Bundle();
    	extras.putBoolean(BoxSyncAdapter.EXTRA_FULL_SYNC, true);
    	
    	for (Account account : boxAccounts)
    		ContentResolver.requestSync(account, authority, extras);
    }
}
