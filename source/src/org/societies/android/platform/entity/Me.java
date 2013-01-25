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

import com.google.renamedgson.annotations.Expose;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import static org.societies.android.api.cis.SocialContract.Me.*;

/**
 * A me entity.
 * 
 * @author Kato
 */
public class Me extends Entity {
	
	private long id = ENTITY_DEFAULT_ID;
	
	@Expose private long personId;
	@Expose private String name;
	@Expose private String displayName;
	@Expose private String username;
	@Expose private String password;
	@Expose private long creationDate = System.currentTimeMillis() / 1000;
	@Expose private long lastModifiedDate = System.currentTimeMillis() / 1000;

	@Override
	protected void populate(Cursor cursor) {
		super.populate(cursor);
		
		setId(				Entity.getLong(cursor, _ID));
		setPersonId(		Entity.getLong(cursor, _ID_PEOPLE));
		setName(			Entity.getString(cursor, NAME));
		setDisplayName(		Entity.getString(cursor, DISPLAY_NAME));
		setUsername(		Entity.getString(cursor, USER_NAME));
		setPassword(		Entity.getString(cursor, PASSWORD));
		setCreationDate(	Entity.getLong(cursor, CREATION_DATE));
		setLastModifiedDate(Entity.getLong(cursor, LAST_MODIFIED_DATE));
	}

	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(_ID_PEOPLE, personId);
		values.put(NAME, name);
		values.put(DISPLAY_NAME, displayName);
		values.put(USER_NAME, username);
		values.put(PASSWORD, password);
		values.put(CREATION_DATE, creationDate);
		values.put(LAST_MODIFIED_DATE, lastModifiedDate);
		
		return values;
	}

	@Override
	protected Uri getContentUri() {
		return CONTENT_URI;
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
	protected void fetchGlobalIds(ContentResolver resolver) {
		// Deliberately empty
	}

	@Override
	public void fetchLocalId(ContentResolver resolver) {
		// Deliberately empty
	}

	@Override
	public String getGlobalId() {
		return null; /* NOT USED */
	}

	@Override
	public void setGlobalId(String globalId) {
		// Deliberately empty
	}
	
	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
