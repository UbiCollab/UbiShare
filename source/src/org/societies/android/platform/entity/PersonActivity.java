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
package org.societies.android.platform.entity;

import static org.societies.android.api.cis.SocialContract.PeopleActivity.*;

import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * A person activity entity.
 * 
 * @author Kato
 */
public class PersonActivity extends Entity {

	private int id = -1;
	private String globalId;
	private String globalIdFeedOwner;
	private String globalIdActor;
	private String globalIdObject;
	private String globalIdVerb;
	private String globalIdTarget;
	private String creationDate;
	
	/**
	 * Gets a list of all the person activities that have been updated since the last
	 * synchronization.
	 * @param resolver The content resolver.
	 * @return A list of updated person activities.
	 */
	public static List<PersonActivity> getUpdatedPersonActivities(ContentResolver resolver) {
		return Entity.getEntities(
				PersonActivity.class, resolver, CONTENT_URI, null, null, null, null);
	}

	@Override
	protected void populate(Cursor cursor) {
		setId(					Entity.getInt(cursor, _ID));
		setGlobalId(			Entity.getString(cursor, GLOBAL_ID));
		setGlobalIdFeedOwner(	Entity.getString(cursor, GLOBAL_ID_FEED_OWNER));
		setGlobalIdActor(		Entity.getString(cursor, GLOBAL_ID_ACTOR));
		setGlobalIdObject(		Entity.getString(cursor, GLOBAL_ID_OBJECT));
		setGlobalIdVerb(		Entity.getString(cursor, GLOBAL_ID_VERB));
		setGlobalIdTarget(		Entity.getString(cursor, GLOBAL_ID_TARGET));
		setCreationDate(		Entity.getString(cursor, CREATION_DATE));
	}
	
	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(GLOBAL_ID_FEED_OWNER, globalIdFeedOwner);
		values.put(GLOBAL_ID_ACTOR, globalIdActor);
		values.put(GLOBAL_ID_OBJECT, globalIdObject);
		values.put(GLOBAL_ID_VERB, globalIdVerb);
		values.put(GLOBAL_ID_TARGET, globalIdTarget);
		values.put(CREATION_DATE, creationDate);
		
		return values;
	}
	
	@Override
	protected Uri getContentUri() {
		return CONTENT_URI;
	}
	
	@Override
	public void fetchLocalId(ContentResolver resolver) {
		setId(Entity.getLocalId(CONTENT_URI, _ID, GLOBAL_ID, globalId, resolver));
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	protected void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String getGlobalId() {
		return globalId;
	}
	
	@Override
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	
	public String getGlobalIdFeedOwner() {
		return globalIdFeedOwner;
	}
	
	public void setGlobalIdFeedOwner(String globalIdFeedOwner) {
		this.globalIdFeedOwner = globalIdFeedOwner;
	}
	
	public String getGlobalIdActor() {
		return globalIdActor;
	}
	
	public void setGlobalIdActor(String globalIdActor) {
		this.globalIdActor = globalIdActor;
	}
	
	public String getGlobalIdObject() {
		return globalIdObject;
	}
	
	public void setGlobalIdObject(String globalIdObject) {
		this.globalIdObject = globalIdObject;
	}
	
	public String getGlobalIdVerb() {
		return globalIdVerb;
	}
	
	public void setGlobalIdVerb(String globalIdVerb) {
		this.globalIdVerb = globalIdVerb;
	}
	
	public String getGlobalIdTarget() {
		return globalIdTarget;
	}
	
	public void setGlobalIdTarget(String globalIdTarget) {
		this.globalIdTarget = globalIdTarget;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
}
