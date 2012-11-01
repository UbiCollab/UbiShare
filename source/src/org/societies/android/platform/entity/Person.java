package org.societies.android.platform.entity;

import java.util.List;

import static org.societies.android.api.cis.SocialContract.People.*;

import android.content.ContentResolver;
import android.database.Cursor;

/**
 * A person entity.
 * 
 * @author Kato
 */
public class Person extends Entity {
	
	private String id;
	private String globalId;
	private String name;
	private String description;
	private String email;
	private String creationDate;
	private String lastModifiedDate;
	private boolean dirty;
	
	/**
	 * Gets a list of all the people in the database.
	 * @param resolver The content resolver.
	 * @return A list of all the people in the database.
	 */
	public static List<Person> getPeople(ContentResolver resolver) {
		return Entity.getEntities(
				Person.class, resolver, CONTENT_URI, null, null, null, null);
	}
	
	@Override
	protected void populate(Cursor cursor) {
		setId(Entity.getString(cursor, _ID));
		setDescription(Entity.getString(cursor, DESCRIPTION));
		setCreationDate(Entity.getString(cursor, CREATION_DATE));
		setEmail(Entity.getString(cursor, EMAIL));
		setGlobalId(Entity.getString(cursor, GLOBAL_ID));
		setLastModifiedDate(Entity.getString(cursor, LAST_MODIFIED_DATE));
		setName(Entity.getString(cursor, NAME));
		//setDirty(Entity.getBoolean(cursor, DIRTY)); TODO: Add dirty flag?
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
		builder.append(String.format(SERIALIZE_FORMAT, NAME, name));
		builder.append(String.format(SERIALIZE_FORMAT, DESCRIPTION, description));
		builder.append(String.format(SERIALIZE_FORMAT, EMAIL, email));
		builder.append(String.format(SERIALIZE_FORMAT, CREATION_DATE, creationDate));
		builder.append(String.format(SERIALIZE_FORMAT, LAST_MODIFIED_DATE, lastModifiedDate));
		// builder.append(String.format(SERIALIZE_FORMAT, DIRTY, dirty)); TODO: Add dirty flag?
		
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
