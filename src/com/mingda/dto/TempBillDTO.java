package com.mingda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TempBillDTO {
	private Long billId;
	private String memberId;
	private String memberType;
	private BigDecimal salmoney;
	private Long apId;
	private String logo;
	private String familyno;
	private String paperid;
	private String bankaccount;
	private Date opertime;
	private Long mid;
	private String membername;
	private String mastername;
	private String masterpaperid;
	private String orgname;

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public Long getBillId() {
		return billId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
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

	public BigDecimal getSalmoney() {
		return salmoney;
	}

	public void setSalmoney(BigDecimal salmoney) {
		this.salmoney = salmoney;
	}

	public Long getApId() {
		return apId;
	}

	public void setApId(Long apId) {
		this.apId = apId;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getFamilyno() {
		return familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getMastername() {
		return mastername;
	}

	public void setMastername(String mastername) {
		this.mastername = mastername;
	}

	public String getMasterpaperid() {
		return masterpaperid;
	}

	public void setMasterpaperid(String masterpaperid) {
		this.masterpaperid = masterpaperid;
	}

}
