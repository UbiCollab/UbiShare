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
import static org.societies.android.api.cis.SocialContract.Services.*;

/**
 * A service entity.
 * 
 * @author Kato
 */
public class Service extends Entity {

	private int id = -1;
	private String globalId;
	private String name;
	private String description;
	private String ownerId;
	private String type;
	private String appType;
	private boolean available;
	private String dependency;
	private String config;
	private String url;
	private String creationDate;
	private String lastModifiedDate;
	
	/**
	 * Gets a list of all services that have been updated since the last
	 * synchronization.
	 * @param resolver The content resolver.
	 * @return A list of updated services.
	 */
	public static List<Service> getUpdatedServices(ContentResolver resolver) {
		return Entity.getEntities(
				Service.class, resolver, CONTENT_URI, null, null, null, null);
	}
	
	/**
	 * Gets the service with the specified global ID.
	 * @param globalId The global ID of the service.
	 * @param resolver The content resolver.
	 * @return The service with the specified global ID, or <code>null</code> if
	 * it does not exist.
	 */
	public static Service getService(String globalId, ContentResolver resolver) {
		List<Service> queryResult = Entity.getEntities(
				Service.class,
				resolver,
				CONTENT_URI,
				null,
				GLOBAL_ID + "=?",
				new String[] { globalId },
				null);
		
		if (queryResult.size() > 0)
			return queryResult.get(0);
		else
			return null;
	}

	@Override
	protected void populate(Cursor cursor) {
		setId(					Entity.getInt(cursor, _ID));
		setGlobalId(			Entity.getString(cursor, GLOBAL_ID));
		setName(				Entity.getString(cursor, NAME));
		setDescription(			Entity.getString(cursor, DESCRIPTION));
		setOwnerId(				Entity.getString(cursor, OWNER_ID));
		setType(				Entity.getString(cursor, TYPE));
		setAppType(				Entity.getString(cursor, APP_TYPE));
		setAvailable(			Entity.getBoolean(cursor, AVAILABLE));
		setDependency(			Entity.getString(cursor, DEPENDENCY));
		setConfig(				Entity.getString(cursor, CONFIG));
		setUrl(					Entity.getString(cursor, URL));
		setCreationDate(		Entity.getString(cursor, CREATION_DATE));
		setLastModifiedDate(	Entity.getString(cursor, LAST_MODIFIED_DATE));
	}
	
	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(NAME, name);
		values.put(DESCRIPTION, description);
		values.put(OWNER_ID, ownerId);
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
	
	public String getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(String ownerId) {
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
