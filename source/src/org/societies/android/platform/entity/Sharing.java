package org.societies.android.platform.entity;

import java.util.Date;

public class Sharing {

	private String id;
	private String globalId;
	private String globalIdService;
	private String globalIdOwner;
	private String globalIdCommunity;
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
