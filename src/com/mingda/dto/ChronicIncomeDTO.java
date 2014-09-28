package com.mingda.dto;

import java.util.Date;

public class ChronicIncomeDTO {
	private Date opertime;
	private String organizationId;
	public Date getOpertime() {
		return opertime;
	}
	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
}
