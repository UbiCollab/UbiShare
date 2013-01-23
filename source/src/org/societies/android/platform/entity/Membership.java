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

import org.societies.android.api.cis.SocialContract.Communities;
import org.societies.android.api.cis.SocialContract.People;

import com.google.renamedgson.annotations.Expose;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import static org.societies.android.api.cis.SocialContract.Membership.*;

/**
 * A membership entity.
 * 
 * @author Kato
 */
public class Membership extends Entity {

	private long id = ENTITY_DEFAULT_ID;
	
	@Expose private String globalId;
	private long memberId;
	private long communityId;
	@Expose private String type;
	@Expose private long creationDate;
	@Expose private long lastModifiedDate;
	
	@Expose private String globalIdMember;
	@Expose private String globalIdCommunity;
	
	/**
	 * Gets a list of all the memberships that have been updated since the last
	 * synchronization.
	 * @param lastSync The Unix time (in seconds) of the last synchronization.
	 * @param resolver The content resolver.
	 * @return A list of updated memberships.
	 * @throws Exception If an error occurs while fetching.
	 */
	public static List<Membership> getUpdatedMemberships(
			long lastSync, ContentResolver resolver) throws Exception {
		List<Membership> updatedMemberships = Entity.getEntities(
				Membership.class,
				resolver,
				CONTENT_URI,
				null,
				LAST_MODIFIED_DATE + " > " + lastSync,
				null,
				null);
		
		for (Membership membership : updatedMemberships)
			membership.fetchGlobalIds(resolver);
		
		return updatedMemberships;
	}
	
	@Override
	protected void populate(Cursor cursor) {
		super.populate(cursor);
		
		setId(				Entity.getLong(cursor, _ID));
		setGlobalId(		Entity.getString(cursor, GLOBAL_ID));
		setMemberId(		Entity.getLong(cursor, _ID_MEMBER));
		setCommunityId(		Entity.getLong(cursor, _ID_COMMUNITY));
		setType(			Entity.getString(cursor, TYPE));
		setCreationDate(	Entity.getLong(cursor, CREATION_DATE));
		setLastModifiedDate(Entity.getLong(cursor, LAST_MODIFIED_DATE));
	}

	@Override
	protected ContentValues getEntityValues() {
		ContentValues values = new ContentValues();
		
		values.put(GLOBAL_ID, globalId);
		values.put(_ID_MEMBER, memberId);
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
		setGlobalIdCommunity(
				Entity.getGlobalId(
						Communities.CONTENT_URI,
						communityId,
						Communities.GLOBAL_ID,
						resolver));
		
		setGlobalIdMember(
				Entity.getGlobalId(
						People.CONTENT_URI,
						memberId,
						People.GLOBAL_ID,
						resolver));
	}
	
	@Override
	public void fetchLocalId(ContentResolver resolver) {
		setId(Entity.getLocalId(CONTENT_URI, _ID, GLOBAL_ID, globalId, resolver));
		setMemberId(
				Entity.getLocalId(
						People.CONTENT_URI,
						People._ID,
						People.GLOBAL_ID,
						globalIdMember,
						resolver));
		setCommunityId(
				Entity.getLocalId(
						Communities.CONTENT_URI,
						Communities._ID,
						Communities.GLOBAL_ID,
						globalIdCommunity,
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
	
	public long getMemberId() {
		return memberId;
	}
	
	public void setMemberId(long memberId) {
		this.memberId = memberId;
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

	public String getGlobalIdMember() {
		return globalIdMember;
	}

	public void setGlobalIdMember(String globalIdMember) {
		this.globalIdMember = globalIdMember;
	}

	public String getGlobalIdCommunity() {
		return globalIdCommunity;
	}

	public void setGlobalIdCommunity(String globalIdCommunity) {
		this.globalIdCommunity = globalIdCommunity;
	}
}
