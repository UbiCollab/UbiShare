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

import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import static org.societies.android.api.cis.SocialContract.Sharing.*;

/**
 * A sharing entity.
 * 
 * @author Kato
 */
public class Sharing extends Entity {

	private int id = -1;
	private String globalId;
	private String globalIdService;
	private String globalIdOwner;
	private String globalIdCommunity;
	private String type;
	private long creationDate;
	private long lastModifiedDate;
	
	/**
	 * Gets a list of all the sharings that have been updated since the last
	 * synchronization.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @param resolver The content resolver.
	 * @return A list of updated sharings.
	 */
	public static List<Sharing> getUpdatedSharings(
			long lastSync, ContentResolver resolver) {
		return Entity.getEntities(
				Sharing.class,
				resolver,
				CONTENT_URI,
				null,
				LAST_MODIFIED_DATE + " > ?",
				new String[] { String.valueOf(lastSync) },
				null);
	}
	
	@Override
	protected void populate(Cursor cursor) {
		setId(					Entity.getInt(cursor, _ID));
		setGlobalId(			Entity.getString(cursor, GLOBAL_ID));
		setGlobalIdService(		Entity.getString(cursor, GLOBAL_ID_SERVICE));
		setGlobalIdOwner(		Entity.getString(cursor, GLOBAL_ID_OWNER));
		setGlobalIdCommunity(	Entity.getString(cursor, GLOBAL_ID_COMMUNITY));
		setType(				Entity.getString(cursor, TYPE));
		setCreationDate(		Entity.getLong(cursor, CREATION_DATE));
		setLastModifiedDate(	Entity.getLong(cursor, LAST_MODIFIED_DATE));
	}

	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(GLOBAL_ID_SERVICE, globalIdService);
		values.put(GLOBAL_ID_OWNER, globalIdOwner);
		values.put(GLOBAL_ID_COMMUNITY, globalIdCommunity);
		values.put(TYPE, type);
		values.put(CREATION_DATE, creationDate);
		values.put(LAST_MODIFIED_DATE, lastModifiedDate);
		
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
	
	public String getGlobalIdService() {
		return globalIdService;
	}
	
	public void setGlobalIdService(String globalIdService) {
		this.globalIdService = globalIdService;
	}
	
	public String getGlobalIdOwner() {
		return globalIdOwner;
	}
	
	public void setGlobalIdOwner(String globalIdOwner) {
		this.globalIdOwner = globalIdOwner;
	}
	
	public String getGlobalIdCommunity() {
		return globalIdCommunity;
	}
	
	public void setGlobalIdCommunity(String globalIdCommunity) {
		this.globalIdCommunity = globalIdCommunity;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public long getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	
	public long getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(long lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
