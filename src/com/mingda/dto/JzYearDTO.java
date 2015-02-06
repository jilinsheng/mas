package com.mingda.dto;

public class JzYearDTO {
	
	private Integer yId;
    private String organizationId;
    private String businessMonth;
    private String businessDate;
    private Short parameter;
    private String sts;
    
	public Integer getyId() {
		return yId;
	}
	public void setyId(Integer yId) {
		this.yId = yId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getBusinessMonth() {
		return businessMonth;
	}
	public void setBusinessMonth(String businessMonth) {
		this.businessMonth = businessMonth;
	}
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	public Short getParameter() {
		return parameter;
	}
	public void setParameter(Short parameter) {
		this.parameter = parameter;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
    

}
