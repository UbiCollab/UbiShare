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
package org.societies.android.sync.box;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.societies.android.box.BoxConstants;
import org.societies.android.platform.entity.Community;
import org.societies.android.platform.entity.CommunityActivity;
import org.societies.android.platform.entity.Entity;
import org.societies.android.platform.entity.Membership;
import org.societies.android.platform.entity.Person;
import org.societies.android.platform.entity.PersonActivity;
import org.societies.android.platform.entity.Relationship;

import com.box.androidlib.DAO.Update;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
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
	
	/** The minimum amount of time between each sync, in seconds. */
	private static final int MIN_SYNC_INTERVAL = 10;
	
	private ContentResolver mResolver;
	private AccountManager mAccountManager;
	private BoxHandler mBoxHandler;
	private SharedPreferences mPreferences;
	private boolean mIsCancelled;

	/**
	 * Initiates a new BoxSyncAdapter.
	 * @param context The context to operate in.
	 * @param autoInitialize Whether or not to auto initialize the adapter.
	 */
	public BoxSyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		
		mPreferences = context.getSharedPreferences(
				BoxConstants.PREFERENCE_FILE, Context.MODE_PRIVATE);
		mResolver = context.getContentResolver();
		mAccountManager = AccountManager.get(context);
		mBoxHandler = new BoxHandler(mResolver);
		mIsCancelled = false;
	}
	
	@Override
	public void onSyncCanceled() {
		mIsCancelled = true;
		
		try {
			mBoxHandler.cancelRunningOperations();
		} catch (InterruptedException e) { /* IGNORED */ }
		
		super.onSyncCanceled();
	}
	
	@Override
	public void onPerformSync(
			Account account,
			Bundle extras,
			String authority,
			ContentProviderClient provider,
			SyncResult syncResult) {
		try {
			Log.i(TAG, "Sync Started.");
			String authToken = mAccountManager.blockingGetAuthToken(
				account, BoxConstants.AUTH_TOKEN_FLAG, true);
			
			Log.i(TAG, "Initializing...");
			mBoxHandler.initialize(authToken);
			
			long lastSync = mPreferences.getLong(BoxConstants.PREFERENCE_LAST_SYNC, 0);
			Log.i(TAG, "Last Sync: " + new Date(lastSync * 1000) + " (" + lastSync + ")");
			
			if ((new Date().getTime() / 1000) - lastSync < MIN_SYNC_INTERVAL) {
				Log.i(TAG, "Terminating sync: last sync completed within the last " + 
							MIN_SYNC_INTERVAL + " seconds.");
				mBoxHandler.waitForRunningOperationsToComplete(true);
				return;
			}
			
			processBoxUpdates(lastSync);
			
			processDeletedEntities();
			
			//syncPeople(lastSync);
			//syncPeopleActivities(lastSync);
			
			syncCommunities(lastSync);
			
			Log.i(TAG, "Waiting for community sync to complete...");
			mBoxHandler.waitForRunningOperationsToComplete(false);
			
			syncCommunityActivities(lastSync);
			
			syncMemberships(lastSync);
			//syncRelationships(lastSync);
			
			Log.i(TAG, "Waiting for operations to complete...");
			mBoxHandler.waitForRunningOperationsToComplete(true);
			
			mPreferences.edit().putLong(
					BoxConstants.PREFERENCE_LAST_SYNC,
					new Date().getTime() / 1000
			).commit();
			
			Log.i(TAG, "Sync finished!");
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}
	
	/**
	 * Processes the deleted entities.
	 * @throws Exception If an error occurs while processing.
	 */
	private void processDeletedEntities() throws Exception {
		if (mIsCancelled) return;
		
		Log.i(TAG, "Processing deleted entities...");
		
		List<Entity> deletedEntities = new ArrayList<Entity>();
		
		deletedEntities.addAll(Entity.getDeletedEntities(Community.class, mResolver));
		deletedEntities.addAll(Entity.getDeletedEntities(CommunityActivity.class, mResolver));
		deletedEntities.addAll(Entity.getDeletedEntities(Membership.class, mResolver));
		// TODO: add the rest
		
		Log.i(TAG, "Deleting entities: " + deletedEntities.size());
		
		mBoxHandler.deleteEntities(deletedEntities);
	}

	/**
	 * Processes the updates from Box.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @throws IOException If an error occurs while fetching updates.
	 */
	private void processBoxUpdates(long lastSync) throws IOException {
		if (mIsCancelled) return;
		
		Log.i(TAG, "Fetching updates from Box...");
		
		List<Update> updates = null;
		if (lastSync > 0)
			updates = mBoxHandler.getUpdatesSince(lastSync);
		
		Log.i(TAG, "Processing updates: " + (updates == null ? "full_sync" : updates.size()));
		
		mBoxHandler.processUpdates(updates);
	}
	
	/**
	 * Synchronizes the people.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @throws Exception If an error occurs while syncing.
	 */
	private void syncPeople(long lastSync) throws Exception {
		if (mIsCancelled) return;
		
		Log.i(TAG, "Started People Sync");
		
		List<Person> people = Person.getUpdatedPeople(0, mResolver);
		
		Log.i(TAG, "Syncing people: " + people.size());
		
		for (Person person : people)
			Log.i(TAG, "Person ID: " + person.getId());
	}
	
	/**
	 * Synchronizes the people activities.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @throws Exception If an error occurs while syncing.
	 */
	private void syncPeopleActivities(long lastSync) throws Exception {
		if (mIsCancelled) return;
		
		Log.i(TAG, "Started Person Activities Sync");
		
		List<PersonActivity> activities =
				PersonActivity.getUpdatedPersonActivities(lastSync, mResolver);
		
		Log.i(TAG, "Syncing person activities: " + activities.size());
		
		/*for (PersonActivity activity : activities)
			mBoxHandler.uploadEntity(activity);*/
	}
	
	/**
	 * Synchronizes the communities.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @throws Exception If an error occurs while syncing.
	 */
	private void syncCommunities(long lastSync) throws Exception {
		if (mIsCancelled) return;
		
		Log.i(TAG, "Started Communities Sync");
		
		List<Community> communities =
				Community.getUpdatedCommunities(lastSync, mResolver);
		
		Log.i(TAG, "Syncing communities: " + communities.size());
		
		for (Community community : communities)
			mBoxHandler.uploadCommunity(community);
	}
	
	/**
	 * Synchronizes the community activities.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @throws Exception If an error occurs while syncing.
	 */
	private void syncCommunityActivities(long lastSync) throws Exception {
		if (mIsCancelled) return;
		
		Log.i(TAG, "Started Community Activities Sync");
		
		List<CommunityActivity> activities =
				CommunityActivity.getUpdatedCommunityActivities(lastSync, mResolver);
		
		Log.i(TAG, "Syncing community activities: " + activities.size());
		
		for (CommunityActivity activity : activities)
			mBoxHandler.uploadCommunityActivity(activity);
	}
	
	/**
	 * Synchronizes the memberships.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @throws Exception If an error occurs while syncing.
	 */
	private void syncMemberships(long lastSync) throws Exception {
		if (mIsCancelled) return;
		
		Log.i(TAG, "Started Memberships Sync");
		
		List<Membership> memberships =
				Membership.getUpdatedMemberships(lastSync, mResolver);
		
		Log.i(TAG, "Syncing memberships: " + memberships.size());
		
		for (Membership membership : memberships)
			mBoxHandler.uploadMembership(membership);
	}
	
	/**
	 * Synchronizes the relationships.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @throws Exception If an error occurs while syncing.
	 */
	private void syncRelationships(long lastSync) throws Exception {
		if (mIsCancelled) return;
		
		Log.i(TAG, "Started Relationships Sync");
		
		List<Relationship> relationships =
				Relationship.getUpdatedRelationships(lastSync, mResolver);
		
		Log.i(TAG, "Syncing relationships: " + relationships.size());
		
		/*
		for (Relationship relationship : relationships)
			mBoxHandler.uploadEntity(relationship);*/
	}
}
