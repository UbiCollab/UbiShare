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

import static org.societies.android.api.cis.SocialContract.Relationship.*;

/**
 * A relationship entity.
 * 
 * @author Kato
 */
public class Relationship extends Entity {

	private long id = ENTITY_DEFAULT_ID;
	
	private String globalId;
	private String globalIdP1;
	private String globalIdP2;
	private String type;
	private long creationDate;
	private long lastModifiedDate;
	
	/**
	 * Gets a list of all the relationships that have been updated since the last
	 * synchronization.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @param resolver The content resolver.
	 * @return A list of updated relationships.
	 * @throws Exception If an error occurs while fetching.
	 */
	public static List<Relationship> getUpdatedRelationships(
			long lastSync, ContentResolver resolver) throws Exception {
		return Entity.getEntities(
				Relationship.class,
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
		setGlobalIdP1(		Entity.getString(cursor, GLOBAL_ID_P1));
		setGlobalIdP2(		Entity.getString(cursor, GLOBAL_ID_P2));
		setType(			Entity.getString(cursor, TYPE));
		setCreationDate(	Entity.getLong(cursor, CREATION_DATE));
		setLastModifiedDate(Entity.getLong(cursor, LAST_MODIFIED_DATE));
	}

	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(GLOBAL_ID_P1, globalIdP1);
		values.put(GLOBAL_ID_P2, globalIdP2);
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
	
	public String getGlobalIdP1() {
		return globalIdP1;
	}
	
	public void setGlobalIdP1(String globalIdP1) {
		this.globalIdP1 = globalIdP1;
	}
	
	public String getGlobalIdP2() {
		return globalIdP2;
	}
	
	public void setGlobalIdP2(String globalIdP2) {
		this.globalIdP2 = globalIdP2;
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
