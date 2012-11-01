package org.societies.android.platform.entity;

import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;

import static org.societies.android.api.cis.SocialContract.Membership.*;

/**
 * A membership entity.
 * 
 * @author Kato
 */
public class Membership extends Entity {

	private String id;
	private String globalId;
	private String globalIdMember;
	private String globalIdCommunity;
	private String type;
	private String creationDate;
	private String lastModifiedDate;
	private boolean dirty;
	
	/**
	 * Gets a list of all the memberships in the database.
	 * @param resolver The content resolver.
	 * @return A list of all the memberships in the database.
	 */
	public static List<Membership> getMemberships(ContentResolver resolver) {
		return Entity.getEntities(
				Membership.class, resolver, CONTENT_URI, null, null, null, null);
	}
	
	@Override
	protected void populate(Cursor cursor) {
		setId(Entity.getString(cursor, _ID));
		setGlobalId(Entity.getString(cursor, GLOBAL_ID));
		setGlobalIdMember(Entity.getString(cursor, GLOBAL_ID_MEMBER));
		setGlobalIdCommunity(Entity.getString(cursor, GLOBAL_ID_COMMUNITY));
		setType(Entity.getString(cursor, TYPE));
		//setCreationDate(Entity.getString(cursor, CREATION_DATE)); TODO: add creation date?
		//setLastModifiedDate(Entity.getString(cursor, LAST_MODIFIED_DATE)); TODO: add last modified date?
		//setDirty(Entity.getBoolean(cursor, DIRTY)); TODO: add dirty flag?
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
		StringBuilder builder = new StringBuilder();
		
		builder.append(String.format(SERIALIZE_FORMAT, GLOBAL_ID, globalId));
		builder.append(String.format(SERIALIZE_FORMAT, GLOBAL_ID_MEMBER, globalIdMember));
		builder.append(String.format(SERIALIZE_FORMAT, GLOBAL_ID_COMMUNITY, globalIdCommunity));
		builder.append(String.format(SERIALIZE_FORMAT, TYPE, type));
		//builder.append(String.format(SERIALIZE_FORMAT, CREATION_DATE, creationDate)); TODO: add creation date?
		//builder.append(String.format(SERIALIZE_FORMAT, LAST_MODIFIED_DATE, lastModifiedDate)); TODO: add last modified date?
		//builder.append(String.format(SERIALIZE_FORMAT, DIRTY, dirty)); TODO: add dirty flag?
		
		return builder.toString();
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
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
}
