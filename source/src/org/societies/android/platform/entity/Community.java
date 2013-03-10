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

import org.societies.android.api.cis.SocialContract.People;

import com.google.renamedgson.annotations.Expose;

import static org.societies.android.api.cis.SocialContract.Communities.*;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * A community entity.
 * 
 * @author Kato
 */
public class Community extends Entity {

	private long id = ENTITY_DEFAULT_ID;
	
	@Expose private String globalId;
	@Expose private String name;
	private long ownerId;
	@Expose private String type;
	@Expose private String description;
	@Expose private long creationDate = System.currentTimeMillis() / 1000;
	@Expose private long lastModifiedDate = System.currentTimeMillis() / 1000;
	
	@Expose private String globalIdOwner;
	
	/**
	 * Gets a list of all the "dirty" communities.
	 * @param resolver The content resolver.
	 * @return A list of updated communities.
	 * @throws Exception If an error occurs while fetching.
	 */
	public static List<Community> getUpdatedCommunities(
			ContentResolver resolver) throws Exception {
		List<Community> updatedCommunities = Entity.getEntities(
				Community.class,
				resolver,
				CONTENT_URI,
				null,
				DIRTY + " = 1",
				null,
				null);
		
		for (Community community : updatedCommunities)
			community.fetchGlobalIds(resolver);
		
		return updatedCommunities;
	}
	
	/**
	 * Checks whether a community with the specified global ID exists.
	 * @param globalId The global ID of the community.
	 * @param resolver The content resolver.
	 * @return Whether or not the specified community exists.
	 * @throws Exception If an error occurs while fetching.
	 */
	public static boolean communityExists(
			String globalId, ContentResolver resolver) throws Exception {
		if (globalId == null)
			return false;
		
		List<Community> communities = Entity.getEntities(
				Community.class,
				resolver,
				CONTENT_URI,
				null,
				GLOBAL_ID + " = ?",
				new String[] { globalId },
				null);
		
		return communities.size() > 0;
	}
	
	@Override
	protected void populate(Cursor cursor) {
		super.populate(cursor);
		
		setId(				Entity.getLong(cursor, _ID));
		setGlobalId(		Entity.getString(cursor, GLOBAL_ID));
		setName(			Entity.getString(cursor, NAME));
		setOwnerId(			Entity.getLong(cursor, _ID_OWNER));
		setType(			Entity.getString(cursor, TYPE));
		setDescription(		Entity.getString(cursor, DESCRIPTION));
		setCreationDate(	Entity.getLong(cursor, CREATION_DATE));
		setLastModifiedDate(Entity.getLong(cursor, LAST_MODIFIED_DATE));
	}
	
	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = super.getEntityValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(NAME, name);
		values.put(_ID_OWNER, ownerId);
		values.put(TYPE, type);
		values.put(DESCRIPTION, description);
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
		setGlobalIdOwner(
				Entity.getGlobalId(
						People.CONTENT_URI,
						ownerId,
						People.GLOBAL_ID,
						resolver));
	}
	
	@Override
	public void fetchLocalId(ContentResolver resolver) {
		setId(Entity.getLocalId(CONTENT_URI, _ID, GLOBAL_ID, globalId, resolver));
		setOwnerId(
				Entity.getLocalId(
						People.CONTENT_URI,
						People._ID,
						People.GLOBAL_ID,
						globalIdOwner,
						resolver));
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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

	public String getGlobalIdOwner() {
		return globalIdOwner;
	}

	public void setGlobalIdOwner(String globalIdOwner) {
		this.globalIdOwner = globalIdOwner;
	}
}
