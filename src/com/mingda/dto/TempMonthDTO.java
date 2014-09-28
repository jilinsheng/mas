package com.mingda.dto;

import java.math.BigDecimal;

public class TempMonthDTO {
	private BigDecimal mid;
	private String organizationId;
	private String year;
	private String month;
	private BigDecimal rs;
	private BigDecimal totalmoney;
	private String ym;
	
	
	public String getYm() {
		return ym;
	}
	public void setYm(String ym) {
		this.ym = ym;
	}
	public BigDecimal getRs() {
		return rs;
	}
	public void setRs(BigDecimal rs) {
		this.rs = rs;
	}
	public BigDecimal getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(BigDecimal totalmoney) {
		this.totalmoney = totalmoney;
	}
	public BigDecimal getMid() {
		return mid;
	}
	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
