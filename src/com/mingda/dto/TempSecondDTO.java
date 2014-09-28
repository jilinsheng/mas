package com.mingda.dto;

import java.math.BigDecimal;

public class TempSecondDTO {
	private String salscope; // ¾ÈÖú·¶Î§
	private String salpercent; // ¾ÈÖú±ÈÀý
	private BigDecimal topline;
	private String percent; // ×Ô¸¶±ÈÀý
	private String organizationId;
	private String year;
	private String memberId;
	private String memberType;
	private String familyno;
	private String membername;
	private String paperid;
	private BigDecimal payTotal;
	private BigDecimal payMedicare;
	private BigDecimal payOutmedicare;
	private BigDecimal payAssist;
	private BigDecimal paySelf;
	private BigDecimal salmoney;
	private BigDecimal payCIAssist;

	public String getSalscope() {
		return salscope;
	}

	public void setSalscope(String salscope) {
		this.salscope = salscope;
	}

	public String getSalpercent() {
		return salpercent;
	}

	public void setSalpercent(String salpercent) {
		this.salpercent = salpercent;
	}
	public BigDecimal getTopline() {
		return topline;
	}

	public void setTopline(BigDecimal topline) {
		this.topline = topline;
	}

	public String getPercent() {
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
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

	public String getFamilyno() {
		return familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public BigDecimal getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(BigDecimal payTotal) {
		this.payTotal = payTotal;
	}

	public BigDecimal getPayMedicare() {
		return payMedicare;
	}

	public void setPayMedicare(BigDecimal payMedicare) {
		this.payMedicare = payMedicare;
	}

	public BigDecimal getPayOutmedicare() {
		return payOutmedicare;
	}

	public void setPayOutmedicare(BigDecimal payOutmedicare) {
		this.payOutmedicare = payOutmedicare;
	}

	public BigDecimal getPayAssist() {
		return payAssist;
	}

	public void setPayAssist(BigDecimal payAssist) {
		this.payAssist = payAssist;
	}

	public BigDecimal getPaySelf() {
		return paySelf;
	}

	public void setPaySelf(BigDecimal paySelf) {
		this.paySelf = paySelf;
	}

	public BigDecimal getSalmoney() {
		return salmoney;
	}

	public void setSalmoney(BigDecimal salmoney) {
		this.salmoney = salmoney;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public BigDecimal getPayCIAssist() {
		return payCIAssist;
	}

	public void setPayCIAssist(BigDecimal payCIAssist) {
		this.payCIAssist = payCIAssist;
	}

}
