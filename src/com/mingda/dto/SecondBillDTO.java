package com.mingda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class SecondBillDTO {
	private BigDecimal billId;
	private BigDecimal batchId;
	private String memberId;
	private String memberType;
	private String paperid;
	private String familyno;
	private BigDecimal salmoney;
	private Date operTime;
	private String membername;
	

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public BigDecimal getBillId() {
		return billId;
	}

	public void setBillId(BigDecimal billId) {
		this.billId = billId;
	}

	public BigDecimal getBatchId() {
		return batchId;
	}

	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
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

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getFamilyno() {
		return familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}
 

	public BigDecimal getSalmoney() {
		return salmoney;
	}

	public void setSalmoney(BigDecimal salmoney) {
		this.salmoney = salmoney;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

}
