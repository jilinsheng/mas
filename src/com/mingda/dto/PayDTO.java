package com.mingda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PayDTO {
	private String payFlag;
	private BigDecimal payTotal;
	private BigDecimal payAccount;
	private BigDecimal payMedicare;
	private BigDecimal payOther;
	private BigDecimal payOutmedicare;
	private BigDecimal payMedicarescope;
	private BigDecimal payMinline;
	private BigDecimal payCash;
	private BigDecimal payAssistscope;
	private BigDecimal payAssist;
	private BigDecimal paySelf;
	private String calcMsg;
	private Integer userId;
	private Date operTime;
	private String sts;
	private BigDecimal seq;
	private Integer bizId;
	private BigDecimal payMedbalance;
	private BigDecimal sumMedicarescope;
	private BigDecimal sumAssistscope;
	private BigDecimal payCIAssist;
	private String memberId;
	private String membeType;
	private String subsection;
	private BigDecimal persum;
	private BigDecimal pnum;
	private String subfrom;
	private String subto;
	private String name;
	private String paperid;
	private String familyno;
	private String biztype;
	private String hospitalname;
	private String diagnosename;
	private Date begintime;
	private Date endtime;
	private String orgname;
	

	public String getPayFlag() {
		return payFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	public BigDecimal getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(BigDecimal payTotal) {
		this.payTotal = payTotal;
	}

	public BigDecimal getPayAccount() {
		return payAccount;
	}

	public void setPayAccount(BigDecimal payAccount) {
		this.payAccount = payAccount;
	}

	public BigDecimal getPayMedicare() {
		return payMedicare;
	}

	public void setPayMedicare(BigDecimal payMedicare) {
		this.payMedicare = payMedicare;
	}

	public BigDecimal getPayOther() {
		return payOther;
	}

	public void setPayOther(BigDecimal payOther) {
		this.payOther = payOther;
	}

	public BigDecimal getPayOutmedicare() {
		return payOutmedicare;
	}

	public void setPayOutmedicare(BigDecimal payOutmedicare) {
		this.payOutmedicare = payOutmedicare;
	}

	public BigDecimal getPayMedicarescope() {
		return payMedicarescope;
	}

	public void setPayMedicarescope(BigDecimal payMedicarescope) {
		this.payMedicarescope = payMedicarescope;
	}

	public BigDecimal getPayMinline() {
		return payMinline;
	}

	public void setPayMinline(BigDecimal payMinline) {
		this.payMinline = payMinline;
	}

	public BigDecimal getPayCash() {
		return payCash;
	}

	public void setPayCash(BigDecimal payCash) {
		this.payCash = payCash;
	}

	public BigDecimal getPayAssistscope() {
		return payAssistscope;
	}

	public void setPayAssistscope(BigDecimal payAssistscope) {
		this.payAssistscope = payAssistscope;
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

	public String getCalcMsg() {
		return calcMsg;
	}

	public void setCalcMsg(String calcMsg) {
		this.calcMsg = calcMsg;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public BigDecimal getSeq() {
		return seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public Integer getBizId() {
		return bizId;
	}

	public void setBizId(Integer bizId) {
		this.bizId = bizId;
	}

	public BigDecimal getPayMedbalance() {
		return payMedbalance;
	}

	public void setPayMedbalance(BigDecimal payMedbalance) {
		this.payMedbalance = payMedbalance;
	}

	public BigDecimal getSumMedicarescope() {
		return sumMedicarescope;
	}

	public void setSumMedicarescope(BigDecimal sumMedicarescope) {
		this.sumMedicarescope = sumMedicarescope;
	}

	public BigDecimal getSumAssistscope() {
		return sumAssistscope;
	}

	public void setSumAssistscope(BigDecimal sumAssistscope) {
		this.sumAssistscope = sumAssistscope;
	}

	public BigDecimal getPayCIAssist() {
		return payCIAssist;
	}

	public void setPayCIAssist(BigDecimal payCIAssist) {
		this.payCIAssist = payCIAssist;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMembeType() {
		return membeType;
	}

	public void setMembeType(String membeType) {
		this.membeType = membeType;
	}

	public String getSubsection() {
		return subsection;
	}

	public void setSubsection(String subsection) {
		this.subsection = subsection;
	}

	public BigDecimal getPersum() {
		return persum;
	}

	public void setPersum(BigDecimal persum) {
		this.persum = persum;
	}

	public String getSubfrom() {
		return subfrom;
	}

	public void setSubfrom(String subfrom) {
		this.subfrom = subfrom;
	}

	public String getSubto() {
		return subto;
	}

	public void setSubto(String subto) {
		this.subto = subto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getBiztype() {
		return biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public String getDiagnosename() {
		return diagnosename;
	}

	public void setDiagnosename(String diagnosename) {
		this.diagnosename = diagnosename;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public BigDecimal getPnum() {
		return pnum;
	}

	public void setPnum(BigDecimal pnum) {
		this.pnum = pnum;
	}
}
