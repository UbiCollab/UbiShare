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

public class ServiceActivity extends Entity {

	private String id;
	private String globalId;
	private String globalIdFeedOwner;
	private String globalIdActor;
	private String globalIdObject;
	private String globalIdVerb;
	private String globalIdTarget;
	private Date creationDate;
	private Date lastModifiedDate;
	

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
	
	public String getGlobalIdFeedOwner() {
		return globalIdFeedOwner;
	}
	
	public void setGlobalIdFeedOwner(String globalIdFeedOwner) {
		this.globalIdFeedOwner = globalIdFeedOwner;
	}
	
	public String getGlobalIdActor() {
		return globalIdActor;
	}
	
	public void setGlobalIdActor(String globalIdActor) {
		this.globalIdActor = globalIdActor;
	}
	
	public String getGlobalIdObject() {
		return globalIdObject;
	}
	
	public void setGlobalIdObject(String globalIdObject) {
		this.globalIdObject = globalIdObject;
	}
	
	public String getGlobalIdVerb() {
		return globalIdVerb;
	}
	
	public void setGlobalIdVerb(String globalIdVerb) {
		this.globalIdVerb = globalIdVerb;
	}
	
	public String getGlobalIdTarget() {
		return globalIdTarget;
	}
	
	public void setGlobalIdTarget(String globalIdTarget) {
		this.globalIdTarget = globalIdTarget;
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
}
