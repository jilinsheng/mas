package com.mingda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class BizCheckDTO {
	private Integer bizId;
	private Integer hospitalId;
	private String bizType;
	private String organizationId;
	private String centerId;
	private String ssn;
	private String memberId;
	private String memberType;
	private String assistId;
	private String familyNo;
	private String familyAddress;
	private String medicareType;
	private String medicareTypeex;
	private String personType;
	private String name;
	private String sex;
	private String idCard;
	private Date beginTime;
	private String deptName;
	private String areaName;
	private Integer icdId;
	private String diagnoseCode;
	private String diagnoseName;
	private String certPath;
	private Date endTime;
	private String outFlag;
	private Date finTime;
	private String confirmFlag;
	private String auditFlag;
	private String assistFlag;
	private String outCause;
	private String outHospital;
	private String inHospital;
	private String billNo;
	private String gatherFlag;
	private Integer userId;
	private Date operTime;

	private BigDecimal bizcheck;
	private BigDecimal checked1;
	private String detail1;
	private Date checktime;
	private String operator;
	private Date opttime;
	private BigDecimal checked2;
	private String detail2;
	private Date checktime2;
	private String operator2;
	private Date opttime2;
	private String checktimes;
	private String checktime2s;
	private String operatorname;
	private String operator1name;
	private String operator2name;
	
	private  String ds;
	 private byte[] cPic01;
	private byte[] cPic02;
	private byte[] cPic03;

	private String detail;
	private BigDecimal checked;

	private String hname;// 医院名称
	private String icdname;// 患病名称
	private String days; // 住院天数
	private String diagnoseTypeName;
	private BigDecimal mondeystand;

	

	public String getDiagnoseTypeName() {
		return diagnoseTypeName;
	}

	public void setDiagnoseTypeName(String diagnoseTypeName) {
		this.diagnoseTypeName = diagnoseTypeName;
	}

	public Integer getBizId() {
		return bizId;
	}

	public void setBizId(Integer bizId) {
		this.bizId = bizId;
	}

	public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getCenterId() {
		return centerId;
	}

	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
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

	public String getAssistId() {
		return assistId;
	}

	public void setAssistId(String assistId) {
		this.assistId = assistId;
	}

	public String getFamilyNo() {
		return familyNo;
	}

	public void setFamilyNo(String familyNo) {
		this.familyNo = familyNo;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getMedicareType() {
		return medicareType;
	}

	public void setMedicareType(String medicareType) {
		this.medicareType = medicareType;
	}

	public String getMedicareTypeex() {
		return medicareTypeex;
	}

	public void setMedicareTypeex(String medicareTypeex) {
		this.medicareTypeex = medicareTypeex;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getIcdId() {
		return icdId;
	}

	public void setIcdId(Integer icdId) {
		this.icdId = icdId;
	}

	public String getDiagnoseCode() {
		return diagnoseCode;
	}

	public void setDiagnoseCode(String diagnoseCode) {
		this.diagnoseCode = diagnoseCode;
	}

	public String getDiagnoseName() {
		return diagnoseName;
	}

	public void setDiagnoseName(String diagnoseName) {
		this.diagnoseName = diagnoseName;
	}

	public String getCertPath() {
		return certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOutFlag() {
		return outFlag;
	}

	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}

	public Date getFinTime() {
		return finTime;
	}

	public void setFinTime(Date finTime) {
		this.finTime = finTime;
	}

	public String getConfirmFlag() {
		return confirmFlag;
	}

	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getAssistFlag() {
		return assistFlag;
	}

	public void setAssistFlag(String assistFlag) {
		this.assistFlag = assistFlag;
	}

	public String getOutCause() {
		return outCause;
	}

	public void setOutCause(String outCause) {
		this.outCause = outCause;
	}

	public String getOutHospital() {
		return outHospital;
	}

	public void setOutHospital(String outHospital) {
		this.outHospital = outHospital;
	}

	public String getInHospital() {
		return inHospital;
	}

	public void setInHospital(String inHospital) {
		this.inHospital = inHospital;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getGatherFlag() {
		return gatherFlag;
	}

	public void setGatherFlag(String gatherFlag) {
		this.gatherFlag = gatherFlag;
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

	public BigDecimal getBizcheck() {
		return bizcheck;
	}

	public void setBizcheck(BigDecimal bizcheck) {
		this.bizcheck = bizcheck;
	}

	public BigDecimal getChecked1() {
		return checked1;
	}

	public void setChecked1(BigDecimal checked1) {
		this.checked1 = checked1;
	}

	public String getDetail1() {
		return detail1;
	}

	public void setDetail1(String detail1) {
		this.detail1 = detail1;
	}

	public Date getChecktime() {
		return checktime;
	}

	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getOpttime() {
		return opttime;
	}

	public void setOpttime(Date opttime) {
		this.opttime = opttime;
	}

	public BigDecimal getChecked2() {
		return checked2;
	}

	public void setChecked2(BigDecimal checked2) {
		this.checked2 = checked2;
	}

	public String getDetail2() {
		return detail2;
	}

	public void setDetail2(String detail2) {
		this.detail2 = detail2;
	}

	public Date getChecktime2() {
		return checktime2;
	}

	public void setChecktime2(Date checktime2) {
		this.checktime2 = checktime2;
	}

	public String getOperator2() {
		return operator2;
	}

	public void setOperator2(String operator2) {
		this.operator2 = operator2;
	}

	public Date getOpttime2() {
		return opttime2;
	}

	public void setOpttime2(Date opttime2) {
		this.opttime2 = opttime2;
	}

	public String getHname() {
		return hname;
	}

	public void setHname(String hname) {
		this.hname = hname;
	}

	public String getIcdname() {
		return icdname;
	}

	public void setIcdname(String icdname) {
		this.icdname = icdname;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}

	public void setChecked(BigDecimal checked) {
		this.checked = checked;
	}

	public BigDecimal getChecked() {
		return checked;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getDays() {
		return days;
	}

	public void setChecktimes(String checktimes) {
		this.checktimes = checktimes;
	}

	public String getChecktimes() {
		return checktimes;
	}

	public void setChecktime2s(String checktime2s) {
		this.checktime2s = checktime2s;
	}

	public String getChecktime2s() {
		return checktime2s;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	public String getOperatorname() {
		return operatorname;
	}

	public void setOperator2name(String operator2name) {
		this.operator2name = operator2name;
	}

	public String getOperator2name() {
		return operator2name;
	}

	public void setOperator1name(String operator1name) {
		this.operator1name = operator1name;
	}

	public String getOperator1name() {
		return operator1name;
	}

	public BigDecimal getMondeystand() {
		return mondeystand;
	}

	public void setMondeystand(BigDecimal mondeystand) {
		this.mondeystand = mondeystand;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public byte[] getcPic01() {
		return cPic01;
	}

	public void setcPic01(byte[] cPic01) {
		this.cPic01 = cPic01;
	}

	public byte[] getcPic02() {
		return cPic02;
	}

	public void setcPic02(byte[] cPic02) {
		this.cPic02 = cPic02;
	}

	public byte[] getcPic03() {
		return cPic03;
	}

	public void setcPic03(byte[] cPic03) {
		this.cPic03 = cPic03;
	}

	
}
