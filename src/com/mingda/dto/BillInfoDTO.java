package com.mingda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BillInfoDTO {
	private Integer chronicbillId;
	private String memberType;
	private String memberId;
	private String subject;
	private BigDecimal income;
	private BigDecimal payout;
	private BigDecimal balance;
	private Date opertime;
	private BigDecimal bizId;
	private String optsts;
	public Integer getChronicbillId() {
		return chronicbillId;
	}
	public void setChronicbillId(Integer chronicbillId) {
		this.chronicbillId = chronicbillId;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public BigDecimal getPayout() {
		return payout;
	}
	public void setPayout(BigDecimal payout) {
		this.payout = payout;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public Date getOpertime() {
		return opertime;
	}
	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}
	public BigDecimal getBizId() {
		return bizId;
	}
	public void setBizId(BigDecimal bizId) {
		this.bizId = bizId;
	}
	public String getOptsts() {
		return optsts;
	}
	public void setOptsts(String optsts) {
		this.optsts = optsts;
	}
	
}
