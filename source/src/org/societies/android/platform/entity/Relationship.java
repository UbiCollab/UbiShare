package org.societies.android.platform.entity;

import java.util.Date;

public class Relationship {

	private String id;
	private String globalId;
	private String globalIdP1;
	private String globalIdP2;
	private String type;
	private Date creationDate;
	private Date lastModifiedDate;
	private boolean dirty;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getGlobalId() {
		return globalId;
	}
	
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
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
