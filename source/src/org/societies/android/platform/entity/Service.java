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

import static org.societies.android.api.cis.SocialContract.Services.*;

/**
 * A service entity.
 * 
 * @author Kato
 */
public class Service extends Entity {

	private long id = ENTITY_DEFAULT_ID;
	
	@Expose private String globalId;
	@Expose private String name;
	@Expose private String description;
	private long ownerId;
	@Expose private String type;
	@Expose private String appType;
	@Expose private boolean available;
	@Expose private String dependency;
	@Expose private String config;
	@Expose private String url;
	@Expose private long creationDate = System.currentTimeMillis() / 1000;
	@Expose private long lastModifiedDate = System.currentTimeMillis() / 1000;
	
	@Expose private String globalIdOwner;
	
	/**
	 * Gets a list of all "dirty" services.
	 * @param resolver The content resolver.
	 * @return A list of updated services.
	 * @throws Exception If an error occurs while fetching.
	 */
	public static List<Service> getUpdatedServices(
			ContentResolver resolver) throws Exception {
		return Entity.getEntities(
				Service.class,
				resolver,
				CONTENT_URI,
				null,
				DIRTY + " = 1",
				null,
				null);
	}
	
	@Override
	protected void populate(Cursor cursor) {
		super.populate(cursor);
		
		setId(				Entity.getLong(cursor, _ID));
		setGlobalId(		Entity.getString(cursor, GLOBAL_ID));
		setName(			Entity.getString(cursor, NAME));
		setDescription(		Entity.getString(cursor, DESCRIPTION));
		setOwnerId(			Entity.getLong(cursor, _ID_OWNER));
		setType(			Entity.getString(cursor, TYPE));
		setAppType(			Entity.getString(cursor, APP_TYPE));
		setAvailable(		Entity.getBoolean(cursor, AVAILABLE));
		setDependency(		Entity.getString(cursor, DEPENDENCY));
		setConfig(			Entity.getString(cursor, CONFIG));
		setUrl(				Entity.getString(cursor, URL));
		setCreationDate(	Entity.getLong(cursor, CREATION_DATE));
		setLastModifiedDate(Entity.getLong(cursor, LAST_MODIFIED_DATE));
	}
	
	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = super.getEntityValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(NAME, name);
		values.put(DESCRIPTION, description);
		values.put(_ID_OWNER, ownerId);
		values.put(TYPE, type);
		values.put(APP_TYPE, appType);
		values.put(AVAILABLE, available);
		values.put(DEPENDENCY, dependency);
		values.put(CONFIG, config);
		values.put(URL, url);
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
		// TODO: ownerId
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
	
	public String getAppType() {
		return appType;
	}
	
	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	public boolean isAvailable() {
		return available;
	}
	
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public String getDependency() {
		return dependency;
	}
	
	public void setDependency(String dependency) {
		this.dependency = dependency;
	}
	
	public String getConfig() {
		return config;
	}
	
	public void setConfig(String config) {
		this.config = config;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
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
