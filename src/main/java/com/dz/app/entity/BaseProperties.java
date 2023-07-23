package com.dz.app.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
@Access(AccessType.FIELD)
public class BaseProperties {

	@Column(name = "ACTIVE")
	private String active;
	
	@Column(name = "CREATEDBY")
	private String createdBy;

	@Column(name = "CREATEDON")
	private String createdOn;
	
	@Column(name = "UPDATEDON")
	private String updatedOn;
	
	@Column(name = "UPDATEDBY")
	private String updatedBy;

	
	public BaseProperties(String active,String createdOn, String createdBy, String updatedOn, String updatedBy) {
		super();
		this.active = active;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "BaseProperties [active=" + active + ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", updatedBy=" + updatedBy + "]";
	}
	public BaseProperties() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
