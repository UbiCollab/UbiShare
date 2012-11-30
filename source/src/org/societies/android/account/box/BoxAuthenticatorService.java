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

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;

/**
 * A service wrapper for BoxAuthenticator.
 * 
 * @author Kato
 */
public class BoxAuthenticatorService extends Service {

	//private static final String TAG = "AccountAuthenticatorService";
	private static BoxAuthenticator mAuthenticator;
	
	public BoxAuthenticatorService() {
		super();
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		if (mAuthenticator == null)
			mAuthenticator = new BoxAuthenticator(this);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mAuthenticator.getIBinder();
	}
	
	/**
	 * Adds an account to the AccountManager. Use this method when the result should be passed
	 * back to the authentication manager.
	 * @param context The context to use.
	 * @param username The username of the account.
	 * @param password The password of the account, or null if none.
	 * @param userdata A bundle of string values to use as userdata, or null if none.
	 * @param response The AccountAuthenticatorResponse, or null if none.
	 */
	public static void addAccount(
			Context context,
			String username,
			String password,
			Bundle userdata,
			Parcelable response
	) {
		AccountAuthenticatorResponse authResponse = 
				(AccountAuthenticatorResponse)response;
		Bundle result = BoxAuthenticatorService.addAccount(
				context, username, password, userdata);
		
		if (authResponse != null)
			authResponse.onResult(result);
	}
	
	/**
	 * Adds an account to the AccountManager.
	 * @param context The context to use.
	 * @param username The username of the account.
	 * @param password The password of the account, or null if none.
	 * @param userdata A bundle of string values to use as userdata, or null if none.
	 * @return A bundle of account name and type, or null if the account was not added.
	 */
	public static Bundle addAccount(
			Context context, String username, String password, Bundle userdata) {
		Bundle result = null;
		String authority = context.getString(R.string.box_account_type);
		
		Account account = new Account(username, authority);
		AccountManager manager = AccountManager.get(context);
		
		if (manager.addAccountExplicitly(account, password, userdata)) {
			result = new Bundle();
			result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
			result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
		}
		
		return result;
	}
}
