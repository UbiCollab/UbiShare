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

import static org.societies.android.api.cis.SocialContract.People.*;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * A person entity.
 * 
 * @author Kato
 */
public class Person extends Entity {
	
	private long id = ENTITY_DEFAULT_ID;
	
	@Expose private String globalId;
	@Expose private String name;
	@Expose private String description;
	@Expose private String email;
	@Expose private long creationDate;
	@Expose private long lastModifiedDate;
	
	/**
	 * Gets a list of all the people that have been updated since the last
	 * synchronization.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @param resolver The content resolver.
	 * @return A list of updated people.
	 * @throws Exception If an error occurs while fetching.
	 */
	public static List<Person> getUpdatedPeople(
			long lastSync, ContentResolver resolver) throws Exception {
		return Entity.getEntities(
				Person.class,
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
		setName(			Entity.getString(cursor, NAME));
		setDescription(		Entity.getString(cursor, DESCRIPTION));
		setEmail(			Entity.getString(cursor, EMAIL));
		setCreationDate(	Entity.getLong(cursor, CREATION_DATE));
		setLastModifiedDate(Entity.getLong(cursor, LAST_MODIFIED_DATE));
	}
	
	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(NAME, name);
		values.put(DESCRIPTION, description);
		values.put(EMAIL, email);
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
		// Deliberately empty
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
