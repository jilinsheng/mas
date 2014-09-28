package com.mingda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SecondBatchDTO {
	private BigDecimal batchId;
	private String organizationId;
	private Date operTime;
	private int year;
	private BigDecimal zm;
	private BigDecimal hs;

	
	public BigDecimal getZm() {
		return zm;
	}

	public void setZm(BigDecimal zm) {
		this.zm = zm;
	}

	public BigDecimal getHs() {
		return hs;
	}

	public void setHs(BigDecimal hs) {
		this.hs = hs;
	}

	public BigDecimal getBatchId() {
		return batchId;
	}

	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

}
