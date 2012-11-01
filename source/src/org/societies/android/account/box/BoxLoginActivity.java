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
package org.societies.android.account.box;

import java.io.IOException;

import org.societies.android.box.BoxConstants;
import org.societies.android.platform.R;
import org.societies.android.account.box.BoxAuthenticatorService;

import com.box.androidlib.Box;
import com.box.androidlib.DAO.User;
import com.box.androidlib.ResponseListeners.GetAccountInfoListener;
import com.box.androidlib.activities.BoxAuthentication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;

/**
 * Shows the Box login screen to the user and adds the account if login was succeeded.
 * 
 * @author Kato
 */
public class BoxLoginActivity extends Activity {
	
	private static final String TAG = "BoxLoginActivity";
	private static final int AUTH_REQUEST_CODE = 100;
	
	private AccountAuthenticatorResponse mResponse = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle intentExtras = getIntent().getExtras();
        if (intentExtras != null)
        	mResponse = intentExtras.getParcelable(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);
        
        Intent intent = new Intent(this, BoxAuthentication.class);
        intent.putExtra(BoxConstants.API_KEY_FLAG, BoxConstants.API_KEY);
        startActivityForResult(intent, AUTH_REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == AUTH_REQUEST_CODE) {
    		if (resultCode == BoxAuthentication.AUTH_RESULT_SUCCESS) {
    			final String authToken = data.getStringExtra(BoxConstants.AUTH_TOKEN_FLAG);
    			
    			Log.i(TAG, "Received authToken: " + authToken);
    			
    			Box.getInstance(BoxConstants.API_KEY).getAccountInfo(authToken, new GetAccountInfoListener() {
					public void onIOException(IOException e) {
						showErrorMessage(getString(R.string.box_login_failed));
						finish();
					}
					
					public void onComplete(User user, String status) {
						if (status.equals(GetAccountInfoListener.STATUS_GET_ACCOUNT_INFO_OK) && user != null) {
							Bundle userdata = new Bundle();
							userdata.putString(BoxConstants.ACCOUNT_USERDATA_AUTH_TOKEN, authToken);
							
							BoxAuthenticatorService.addAccount(
									getApplicationContext(),
									user.getLogin(),
									null,
									userdata,
									mResponse);
						} else {
							showErrorMessage(getString(R.string.box_login_failed));
						}
						
						finish();
					}
				});
    		} else if (resultCode == BoxAuthentication.AUTH_RESULT_FAIL) {
    			showErrorMessage(getString(R.string.box_login_failed));
    			finish();
    		} else {
    			finish();
    		}
    	}
    }
    
    /**
     * Shows the specified error message.
     * @param message The error message to show.
     */
    private void showErrorMessage(String message) {
    	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
