package com.mingda.dto;

import java.util.Date;

public class OrgEnabledDTO {
	
	private String organizationId;
	private Integer hopitalSts;
	private Date hospitalTime;
	private Integer manualSts;
	private Date manualTime;
	
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public Integer getHopitalSts() {
		return hopitalSts;
	}
	public void setHopitalSts(Integer hopitalSts) {
		this.hopitalSts = hopitalSts;
	}
	public Date getHospitalTime() {
		return hospitalTime;
	}
	public void setHospitalTime(Date hospitalTime) {
		this.hospitalTime = hospitalTime;
	}
	public Integer getManualSts() {
		return manualSts;
	}
	public void setManualSts(Integer manualSts) {
		this.manualSts = manualSts;
	}
	public Date getManualTime() {
		return manualTime;
	}
	public void setManualTime(Date manualTime) {
		this.manualTime = manualTime;
	}
	
}
