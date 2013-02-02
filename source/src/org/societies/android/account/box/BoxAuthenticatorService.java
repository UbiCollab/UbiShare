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

import org.societies.android.api.cis.SocialContract;
import org.societies.android.box.BoxConstants;
import org.societies.android.platform.R;
import org.societies.android.platform.entity.Me;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;

/**
 * A service wrapper for BoxAuthenticator.
 * 
 * @author Kato
 */
public class BoxAuthenticatorService extends Service {

	private static final String TAG = "AccountAuthenticatorService";
	
	private static BoxAuthenticator mAuthenticator;
	
	/**
	 * Initializes the service.
	 */
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
		String accountType = context.getString(R.string.box_account_type);
		String authority = context.getString(R.string.provider_authority);
		
		Account account = new Account(username, accountType);
		AccountManager manager = AccountManager.get(context);
		
		Log.i(TAG, "Adding Account...");
		if (manager.addAccountExplicitly(account, password, userdata)) {
			result = new Bundle();
			result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
			result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
			
			ContentResolver.setIsSyncable(account, authority, 1);
			ContentResolver.setSyncAutomatically(account, authority, true);
			ContentResolver.addPeriodicSync(
					account, authority, new Bundle(), BoxConstants.ACCOUNT_SYNC_FREQUENCY);
			
			insertRecords(context, account);
		}
		
		return result;
	}
	
	/**
	 * Inserts a record into the Me and People table.
	 * @param context The context to use.
	 * @param account The Box account.
	 */
	private static void insertRecords(Context context, Account account) {
		ContentResolver resolver = context.getContentResolver();
		
		Log.i(TAG, "Inserting People Record...");
		long id = insertPersonEntity(account, resolver);
		
		Log.i(TAG, "Inserting Me Record...");
		insertMeEntity(account, id, resolver);
	}
	
	/**
	 * Inserts a record into the People table.
	 * @param account The Box account.
	 * @param resolver The content resolver.
	 * @return The ID of the inserted record, or -1 on error.
	 */
	private static long insertPersonEntity(Account account, ContentResolver resolver) {
		/* TO BE USED WHEN PEOPLE TABLE IS SYNCHRONIZED
		 * 
		Person personEntity = new Person();
		personEntity.setAccountName(account.name);
		personEntity.setAccountType(account.type);
		personEntity.setEmail(account.name);
		personEntity.setName(account.name);
		personEntity.setDescription(new String());
		personEntity.setGlobalId(SocialContract.GLOBAL_ID_PENDING);
		
		Uri personUri = personEntity.insert(resolver);
		long id = Long.parseLong(personUri.getLastPathSegment());
		
		*/
		
		long id = -1;
		
		Cursor cursor = resolver.query(
				SocialContract.People.CONTENT_URI,
				new String[] { SocialContract.People._ID },
				SocialContract.People.EMAIL + " = ?",
				new String[] { account.name },
				null);
		
		if (cursor.moveToFirst())
			id = cursor.getLong(cursor.getColumnIndex(SocialContract.People._ID));
		
		cursor.close();
		
		return id;
	}
	
	/**
	 * Inserts a record into the Me table.
	 * @param account The Box account.
	 * @param personId The ID of the related record in the People table.
	 * @param resolver The content resolver.
	 * @return The URI of the inserted record.
	 */
	private static Uri insertMeEntity(
			Account account, long personId, ContentResolver resolver) {
		Me meEntity = new Me();
		meEntity.setAccountName(account.name);
		meEntity.setAccountType(account.type);
		meEntity.setDisplayName(account.name);
		meEntity.setName(account.name);
		meEntity.setPassword(new String());
		meEntity.setUsername(account.name);
		meEntity.setPersonId(personId);
		
		return meEntity.insert(resolver);
	}
}
