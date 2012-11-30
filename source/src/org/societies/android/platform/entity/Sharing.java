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
package org.societies.android.platform.entity;

import java.util.List;

import com.google.renamedgson.annotations.Expose;

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

	private long id = ENTITY_DEFAULT_ID;
	
	@Expose private String globalId;
	private long serviceId;
	private long ownerId;
	private long communityId;
	@Expose private String type;
	@Expose private long creationDate;
	@Expose private long lastModifiedDate;
	
	@Expose private String globalIdService;
	@Expose private String globalIdOwner;
	@Expose private String globalIdCommunity;
	
	/**
	 * Gets a list of all the sharings that have been updated since the last
	 * synchronization.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @param resolver The content resolver.
	 * @return A list of updated sharings.
	 * @throws Exception If an error occurs while fetching.
	 */
	public static List<Sharing> getUpdatedSharings(
			long lastSync, ContentResolver resolver) throws Exception {
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
		setId(				Entity.getLong(cursor, _ID));
		setGlobalId(		Entity.getString(cursor, GLOBAL_ID));
		setServiceId(		Entity.getLong(cursor, _ID_SERVICE));
		setOwnerId(			Entity.getLong(cursor, _ID_OWNER));
		setCommunityId(		Entity.getLong(cursor, _ID_COMMUNITY));
		setType(			Entity.getString(cursor, TYPE));
		setCreationDate(	Entity.getLong(cursor, CREATION_DATE));
		setLastModifiedDate(Entity.getLong(cursor, LAST_MODIFIED_DATE));
	}

	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(_ID_SERVICE, serviceId);
		values.put(_ID_OWNER, ownerId);
		values.put(_ID_COMMUNITY, communityId);
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
	protected void fetchGlobalIds(ContentResolver resolver) {
		// TODO: implement
	}
	
	@Override
	public void fetchLocalId(ContentResolver resolver) {
		setId(Entity.getLocalId(CONTENT_URI, _ID, GLOBAL_ID, globalId, resolver));
		// TODO: serviceId, ownerId, communityId
	}
	
	@Override
	public long getId() {
		return id;
	}
	
	@Override
	protected void setId(long id) {
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
	
	public long getServiceId() {
		return serviceId;
	}
	
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	
	public long getCommunityId() {
		return communityId;
	}
	
	public void setCommunityId(long communityId) {
		this.communityId = communityId;
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
}
