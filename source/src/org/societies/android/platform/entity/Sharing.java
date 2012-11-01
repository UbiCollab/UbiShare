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

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;

public class Sharing extends Entity {

	private String id;
	private String globalId;
	private String globalIdService;
	private String globalIdOwner;
	private String globalIdCommunity;
	private String type;
	private Date creationDate;
	private Date lastModifiedDate;
	private boolean dirty;
	
	@Override
	protected void populate(Cursor cursor) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(ContentResolver resolver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(ContentResolver resolver) {
		// TODO Auto-generated method stub
	}

	@Override
	public void insert(ContentResolver resolver) {
		// TODO Auto-generated method stub
	}

	@Override
	public String serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
}
