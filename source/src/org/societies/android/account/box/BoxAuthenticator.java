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

import org.societies.android.box.BoxConstants;
import org.societies.android.account.box.BoxLoginActivity;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * An account authenticator that handles adding of new Box accounts.
 * 
 * @author Kato
 */
class BoxAuthenticator extends AbstractAccountAuthenticator {

	private Context mContext;
	
	/**
	 * Initiates a new account authenticator.
	 * @param context The context to operate in.
	 */
	public BoxAuthenticator(Context context) {
		super(context);
	
		mContext = context;
	}

	@Override
	public Bundle addAccount(
			AccountAuthenticatorResponse response,
			String accountType,
			String authTokenType,
			String[] requiredFeatures,
			Bundle options
	) throws NetworkErrorException {
		Bundle result = new Bundle();
		
		Intent loginIntent = new Intent(mContext, BoxLoginActivity.class);
		loginIntent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		
		result.putParcelable(AccountManager.KEY_INTENT, loginIntent);
		
		return result;
	}

	@Override
	public Bundle confirmCredentials(
			AccountAuthenticatorResponse response,
			Account account,
			Bundle options
	) throws NetworkErrorException {
		return null; // Not in use
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
		return null; // Not in use
	}

	@Override
	public Bundle getAuthToken(
			AccountAuthenticatorResponse response,
			Account account,
			String authTokenType,
			Bundle options
	) throws NetworkErrorException {
		AccountManager manager = AccountManager.get(mContext);
		String authToken = manager.getUserData(account, BoxConstants.ACCOUNT_USERDATA_AUTH_TOKEN);
		
		Bundle result = new Bundle();
		result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
		result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
		result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
		
		return result;
	}

	@Override
	public String getAuthTokenLabel(String authTokenType) {
		return null; // Not in use
	}

	@Override
	public Bundle hasFeatures(
			AccountAuthenticatorResponse response,
			Account account,
			String[] features
	) throws NetworkErrorException {
		return null; // Not in use
	}

	@Override
	public Bundle updateCredentials(
			AccountAuthenticatorResponse response,
			Account account,
			String authTokenType,
			Bundle options
	) throws NetworkErrorException {
		return null; // Not in use
	}

}
