package com.mingda.dto;

import java.math.BigDecimal;

public class OrganizationDTO {

	private String organizationId;
	private String serialnumber;
	private String orgname;
	private String fullname;
	private String parentorgid;
	private BigDecimal depth;
	private String status;
	private String orgpower;
	private String orgup;

	public String getOrgpower() {
		return orgpower;
	}

	public void setOrgpower(String orgpower) {
		this.orgpower = orgpower;
	}

	public String getOrgup() {
		return orgup;
	}

	public void setOrgup(String orgup) {
		this.orgup = orgup;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getParentorgid() {
		return parentorgid;
	}

	public void setParentorgid(String parentorgid) {
		this.parentorgid = parentorgid;
	}

	public BigDecimal getDepth() {
		return depth;
	}

	public void setDepth(BigDecimal depth) {
		this.depth = depth;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
