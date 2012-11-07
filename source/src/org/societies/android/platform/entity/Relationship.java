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

import static org.societies.android.api.cis.SocialContract.People.CONTENT_URI;
import static org.societies.android.api.cis.SocialContract.Relationship.*;

/**
 * A relationship entity.
 * 
 * @author Kato
 */
public class Relationship extends Entity {

	private int id;
	private String globalId;
	private String globalIdP1;
	private String globalIdP2;
	private String type;
	private String creationDate;
	private String lastModifiedDate;
	
	/**
	 * Gets a list of all the relationships in the database.
	 * @param resolver The content resolver.
	 * @return A list of all relationships in the database.
	 */
	public static List<Relationship> getRelationships(ContentResolver resolver) {
		return Entity.getEntities(
				Relationship.class, resolver, CONTENT_URI, null, null, null, null);
	}

	@Override
	protected void populate(Cursor cursor) {
		setId(Entity.getInt(cursor, _ID));
		setGlobalId(Entity.getString(cursor, GLOBAL_ID));
		setGlobalIdP1(Entity.getString(cursor, GLOBAL_ID_P1));
		setGlobalIdP2(Entity.getString(cursor, GLOBAL_ID_P2));
		setType(Entity.getString(cursor, TYPE));
		//setCreationDate(Entity.getString(cursor, CREATION_DATE)); TODO: add creation date?
		//setLastModifiedDate(Entity.getString(cursor, LAST_MODIFIED_DATE)); TODO: add last modified date?
	}

	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(GLOBAL_ID_P1, globalIdP1);
		values.put(GLOBAL_ID_P2, globalIdP2);
		values.put(TYPE, type);
		//values.put(CREATION_DATE, creationDate); TODO: add creation date?
		//values.put(LAST_MODIFIED_DATE, lastModifiedDate); TODO: add last modified date?
		
		return values;
	}
	
	@Override
	protected Uri getContentUri() {
		return CONTENT_URI;
	}

	@Override
	public String serialize() {
		StringBuilder builder = new StringBuilder();
		
		builder.append(String.format(SERIALIZE_FORMAT, GLOBAL_ID, globalId));
		builder.append(String.format(SERIALIZE_FORMAT, GLOBAL_ID_P1, globalIdP1));
		builder.append(String.format(SERIALIZE_FORMAT, GLOBAL_ID_P2, globalIdP2));
		builder.append(String.format(SERIALIZE_FORMAT, TYPE, type));
		builder.append(String.format(SERIALIZE_FORMAT, TYPE, type));
		//builder.append(String.format(SERIALIZE_FORMAT, CREATION_DATE, creationDate)); TODO: add creation date?
		//builder.append(String.format(SERIALIZE_FORMAT, LAST_MODIFIED_DATE, lastModifiedDate)); TODO: add last modified date?
		
		return builder.toString();
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	private void setId(int id) {
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
	
	public String getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
