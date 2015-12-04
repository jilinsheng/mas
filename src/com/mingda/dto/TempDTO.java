package com.mingda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TempDTO {
	private Long approveId;
	private String membername;
	private String memberId;
	private String memberType;
	private String ybSicken;
	private String ybSickenval;
	private String tsSicken;
	private String tsSickenval;
	private String saltype;
	private String inhospitalsicken;
	private BigDecimal payTotal;
	private BigDecimal payOutmedicare;
	private BigDecimal payMedicare;
	private BigDecimal payYouhui;
	private BigDecimal payMinline;
	private BigDecimal payAssistscope;
	private BigDecimal cs ;
	private String hospitalname;
	private Date medicaltime;
	private Date applytime;
	private Date approvetime;
	private String approvests;
	private String implsts;
	private BigDecimal payAssist;
	private String organizationId;
	private String familyno;
	private String paperid;
	private String ssn;
	private String relmaster;
	private String mastername;
	private String sex;
	private String name;
	private String age;
	private String linkmode;
	private String address;
	private String safesort;// 保险类别
	private String fmsort;// 身份类别
	private String a1;
	private String a2;
	private String a3;
	private String a4;
	private String a5;
	private String a6;
	private String a7;
	private String a8;
	private String a9;
	private String a10;
	private String a11;
	private String assistTypex;
	private String saltypeval;
	private String assistype;
	private Date begintime;
	private Date endtime;
	private Date birthday;
	private String nation;
	private String[] icdnames;
	private String[] tsicds;
	private String medicareType;
	private String hospitaltype;
	private String personstate;
	private String assistTypeM;
	private String assistTypeTxt;
	private BigDecimal paySumAssistIn;
	private BigDecimal paySumAssistOut;
	private BigDecimal sumMedicareScope;
	private BigDecimal payCIAssist;
	private int calcType;
	private BigDecimal oldPayTotal;
	private BigDecimal oldPayMedicare;
	private BigDecimal oldPayOutMedicare;
	private String biztype;
	private BigDecimal paySumAssistScopeIn;
	private BigDecimal payPreSumAssistScopeIn;
	private String dbButtonFlag;
	private String jzjButtonFlag;
	private String bizStatus;
	private Date opertime;
	private String bizType;
	private String diagnoseName;
	private String status;
	private Date assistTime;
	private String assistFlag;
	private String result;
	private BigDecimal topLine;
	private BigDecimal totlePay;
	private BigDecimal zyPay;
	private BigDecimal mzPay;
	private String orgname1;
	private String orgname2;
	private long hospitalId;
	private int diagnoseTypeId;
	private int icdId;
	private int specBiz;
	private String calcMsg;
	private String org;
	private Long bizid;
	private String hname;
	private String icdname;
	private BigDecimal payself;
	
	private String diagnosetypeid;
	private String dorg;
	private String dtypeid;
	private String dtypename;
	private String scaler;
	private String otherType;
	private String meminfo;
	
	private String assistType;
	private Date medicaltimeEnd;
	private BigDecimal insurance;
	private int hospitalLevel;
	private Date systime;
	private String medicaretypetext;
	
	private String hospitalLocal;
	private String businessyear;
	
	private Boolean MedicareFlag;

	private String personTypeex;
	private Date fintime;
	private String bizTypeex;
	private String bizTypeexTxt;
	
	public BigDecimal getPaySumAssistScopeIn() {
		return paySumAssistScopeIn;
	}

	public void setPaySumAssistScopeIn(BigDecimal paySumAssistScopeIn) {
		this.paySumAssistScopeIn = paySumAssistScopeIn;
	}

	public BigDecimal getPayPreSumAssistScopeIn() {
		return payPreSumAssistScopeIn;
	}

	public void setPayPreSumAssistScopeIn(BigDecimal payPreSumAssistScopeIn) {
		this.payPreSumAssistScopeIn = payPreSumAssistScopeIn;
	}

	public String[] getTsicds() {
		return tsicds;
	}

	public void setTsicds(String[] tsicds) {
		this.tsicds = tsicds;
	}

	public String[] getIcdnames() {
		return icdnames;
	}

	public void setIcdnames(String[] icdnames) {
		this.icdnames = icdnames;
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

	public String getAssistype() {
		return assistype;
	}

	public void setAssistype(String assistype) {
		this.assistype = assistype;
	}

	public String getSaltypeval() {
		return saltypeval;
	}

	public void setSaltypeval(String saltypeval) {
		this.saltypeval = saltypeval;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getA2() {
		return a2;
	}

	public void setA2(String a2) {
		this.a2 = a2;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getA5() {
		return a5;
	}

	public void setA5(String a5) {
		this.a5 = a5;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getRelmaster() {
		return relmaster;
	}

	public void setRelmaster(String relmaster) {
		this.relmaster = relmaster;
	}

	public String getMastername() {
		return mastername;
	}

	public void setMastername(String mastername) {
		this.mastername = mastername;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getLinkmode() {
		return linkmode;
	}

	public void setLinkmode(String linkmode) {
		this.linkmode = linkmode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSafesort() {
		return safesort;
	}

	public void setSafesort(String safesort) {
		this.safesort = safesort;
	}

	public String getFmsort() {
		return fmsort;
	}

	public void setFmsort(String fmsort) {
		this.fmsort = fmsort;
	}

	public Long getApproveId() {
		return approveId;
	}

	public void setApproveId(Long approveId) {
		this.approveId = approveId;
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

	public String getYbSicken() {
		return ybSicken;
	}

	public void setYbSicken(String ybSicken) {
		this.ybSicken = ybSicken;
	}

	public String getYbSickenval() {
		return ybSickenval;
	}

	public void setYbSickenval(String ybSickenval) {
		this.ybSickenval = ybSickenval;
	}

	public String getTsSicken() {
		return tsSicken;
	}

	public void setTsSicken(String tsSicken) {
		this.tsSicken = tsSicken;
	}

	public String getTsSickenval() {
		return tsSickenval;
	}

	public void setTsSickenval(String tsSickenval) {
		this.tsSickenval = tsSickenval;
	}

	public String getSaltype() {
		return saltype;
	}

	public void setSaltype(String saltype) {
		this.saltype = saltype;
	}

	public String getInhospitalsicken() {
		return inhospitalsicken;
	}

	public void setInhospitalsicken(String inhospitalsicken) {
		this.inhospitalsicken = inhospitalsicken;
	}

	public BigDecimal getPayTotal() {
		return payTotal;
	}

	public void setPayTotal(BigDecimal payTotal) {
		this.payTotal = payTotal;
	}

	public BigDecimal getPayOutmedicare() {
		return payOutmedicare;
	}

	public void setPayOutmedicare(BigDecimal payOutmedicare) {
		this.payOutmedicare = payOutmedicare;
	}

	public BigDecimal getPayMedicare() {
		return payMedicare;
	}

	public void setPayMedicare(BigDecimal payMedicare) {
		this.payMedicare = payMedicare;
	}

	public BigDecimal getPayYouhui() {
		return payYouhui;
	}

	public void setPayYouhui(BigDecimal payYouhui) {
		this.payYouhui = payYouhui;
	}

	public BigDecimal getPayMinline() {
		return payMinline;
	}

	public void setPayMinline(BigDecimal payMinline) {
		this.payMinline = payMinline;
	}

	public BigDecimal getPayAssistscope() {
		return payAssistscope;
	}

	public void setPayAssistscope(BigDecimal payAssistscope) {
		this.payAssistscope = payAssistscope;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public Date getMedicaltime() {
		return medicaltime;
	}

	public void setMedicaltime(Date medicaltime) {
		this.medicaltime = medicaltime;
	}

	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	public Date getApprovetime() {
		return approvetime;
	}

	public void setApprovetime(Date approvetime) {
		this.approvetime = approvetime;
	}

	public String getApprovests() {
		return approvests;
	}

	public void setApprovests(String approvests) {
		this.approvests = approvests;
	}

	public String getImplsts() {
		return implsts;
	}

	public void setImplsts(String implsts) {
		this.implsts = implsts;
	}

	public BigDecimal getPayAssist() {
		return payAssist;
	}

	public void setPayAssist(BigDecimal payAssist) {
		this.payAssist = payAssist;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getMembername() {
		return membername;
	}

	public BigDecimal getCs() {
		return cs;
	}

	public void setCs(BigDecimal cs) {
		this.cs = cs;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNation() {
		return nation;
	}

	public void setMedicareType(String medicareType) {
		this.medicareType = medicareType;
	}

	public String getMedicareType() {
		return medicareType;
	}

	public String getHospitaltype() {
		return hospitaltype;
	}

	public void setHospitaltype(String hospitaltype) {
		this.hospitaltype = hospitaltype;
	}

	public String getPersonstate() {
		return personstate;
	}

	public void setPersonstate(String personstate) {
		this.personstate = personstate;
	}

	public String getAssistTypeM() {
		return assistTypeM;
	}

	public void setAssistTypeM(String assistTypeM) {
		this.assistTypeM = assistTypeM;
	}

	public String getAssistTypeTxt() {
		return assistTypeTxt;
	}

	public void setAssistTypeTxt(String assistTypeTxt) {
		this.assistTypeTxt = assistTypeTxt;
	}

	public BigDecimal getPaySumAssistIn() {
		return paySumAssistIn;
	}

	public void setPaySumAssistIn(BigDecimal paySumAssistIn) {
		this.paySumAssistIn = paySumAssistIn;
	}

	public BigDecimal getPaySumAssistOut() {
		return paySumAssistOut;
	}

	public void setPaySumAssistOut(BigDecimal paySumAssistOut) {
		this.paySumAssistOut = paySumAssistOut;
	}

	public BigDecimal getSumMedicareScope() {
		return sumMedicareScope;
	}

	public void setSumMedicareScope(BigDecimal sumMedicareScope) {
		this.sumMedicareScope = sumMedicareScope;
	}

	public BigDecimal getPayCIAssist() {
		return payCIAssist;
	}

	public void setPayCIAssist(BigDecimal payCIAssist) {
		this.payCIAssist = payCIAssist;
	}

	public int getCalcType() {
		return calcType;
	}

	public void setCalcType(int calcType) {
		this.calcType = calcType;
	}

	public BigDecimal getOldPayTotal() {
		return oldPayTotal;
	}

	public void setOldPayTotal(BigDecimal oldPayTotal) {
		this.oldPayTotal = oldPayTotal;
	}

	public BigDecimal getOldPayMedicare() {
		return oldPayMedicare;
	}

	public void setOldPayMedicare(BigDecimal oldPayMedicare) {
		this.oldPayMedicare = oldPayMedicare;
	}

	public BigDecimal getOldPayOutMedicare() {
		return oldPayOutMedicare;
	}

	public void setOldPayOutMedicare(BigDecimal oldPayOutMedicare) {
		this.oldPayOutMedicare = oldPayOutMedicare;
	}

	public String getBiztype() {
		return biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

	public String getDbButtonFlag() {
		return dbButtonFlag;
	}

	public void setDbButtonFlag(String dbButtonFlag) {
		this.dbButtonFlag = dbButtonFlag;
	}

	public String getJzjButtonFlag() {
		return jzjButtonFlag;
	}

	public void setJzjButtonFlag(String jzjButtonFlag) {
		this.jzjButtonFlag = jzjButtonFlag;
	}

	public String getBizStatus() {
		return bizStatus;
	}

	public void setBizStatus(String bizStatus) {
		this.bizStatus = bizStatus;
	}

	public Date getOpertime() {
		return opertime;
	}

	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getDiagnoseName() {
		return diagnoseName;
	}

	public void setDiagnoseName(String diagnoseName) {
		this.diagnoseName = diagnoseName;
	}

	public String getA6() {
		return a6;
	}

	public void setA6(String a6) {
		this.a6 = a6;
	}

	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public String getA8() {
		return a8;
	}

	public void setA8(String a8) {
		this.a8 = a8;
	}

	public String getA9() {
		return a9;
	}

	public void setA9(String a9) {
		this.a9 = a9;
	}

	public String getA10() {
		return a10;
	}

	public void setA10(String a10) {
		this.a10 = a10;
	}

	public String getA11() {
		return a11;
	}

	public void setA11(String a11) {
		this.a11 = a11;
	}

	public String getAssistTypex() {
		return assistTypex;
	}

	public void setAssistTypex(String assistTypex) {
		this.assistTypex = assistTypex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAssistTime() {
		return assistTime;
	}

	public void setAssistTime(Date assistTime) {
		this.assistTime = assistTime;
	}

	public String getAssistFlag() {
		return assistFlag;
	}

	public void setAssistFlag(String assistFlag) {
		this.assistFlag = assistFlag;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public BigDecimal getTopLine() {
		return topLine;
	}

	public void setTopLine(BigDecimal topLine) {
		this.topLine = topLine;
	}

	public BigDecimal getTotlePay() {
		return totlePay;
	}

	public void setTotlePay(BigDecimal totlePay) {
		this.totlePay = totlePay;
	}

	public BigDecimal getZyPay() {
		return zyPay;
	}

	public void setZyPay(BigDecimal zyPay) {
		this.zyPay = zyPay;
	}

	public BigDecimal getMzPay() {
		return mzPay;
	}

	public void setMzPay(BigDecimal mzPay) {
		this.mzPay = mzPay;
	}

	public String getOrgname1() {
		return orgname1;
	}

	public void setOrgname1(String orgname1) {
		this.orgname1 = orgname1;
	}

	public String getOrgname2() {
		return orgname2;
	}

	public void setOrgname2(String orgname2) {
		this.orgname2 = orgname2;
	}

	public String getCalcMsg() {
		return calcMsg;
	}

	public void setCalcMsg(String calcMsg) {
		this.calcMsg = calcMsg;
	}

	public long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public int getDiagnoseTypeId() {
		return diagnoseTypeId;
	}

	public void setDiagnoseTypeId(int diagnoseTypeId) {
		this.diagnoseTypeId = diagnoseTypeId;
	}

	public int getIcdId() {
		return icdId;
	}

	public void setIcdId(int icdId) {
		this.icdId = icdId;
	}

	public int getSpecBiz() {
		return specBiz;
	}

	public void setSpecBiz(int specBiz) {
		this.specBiz = specBiz;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public Long getBizid() {
		return bizid;
	}

	public void setBizid(Long bizid) {
		this.bizid = bizid;
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

	public BigDecimal getPayself() {
		return payself;
	}

	public void setPayself(BigDecimal payself) {
		this.payself = payself;
	}

	public String getDiagnosetypeid() {
		return diagnosetypeid;
	}

	public void setDiagnosetypeid(String diagnosetypeid) {
		this.diagnosetypeid = diagnosetypeid;
	}

	public String getDorg() {
		return dorg;
	}

	public void setDorg(String dorg) {
		this.dorg = dorg;
	}

	public String getDtypeid() {
		return dtypeid;
	}

	public void setDtypeid(String dtypeid) {
		this.dtypeid = dtypeid;
	}

	public String getDtypename() {
		return dtypename;
	}

	public void setDtypename(String dtypename) {
		this.dtypename = dtypename;
	}

	public String getScaler() {
		return scaler;
	}

	public void setScaler(String scaler) {
		this.scaler = scaler;
	}

	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	public String getMeminfo() {
		return meminfo;
	}

	public void setMeminfo(String meminfo) {
		this.meminfo = meminfo;
	}

	public String getAssistType() {
		return assistType;
	}

	public void setAssistType(String assistType) {
		this.assistType = assistType;
	}

	public Date getMedicaltimeEnd() {
		return medicaltimeEnd;
	}

	public void setMedicaltimeEnd(Date medicaltimeEnd) {
		this.medicaltimeEnd = medicaltimeEnd;
	}

	public BigDecimal getInsurance() {
		return insurance;
	}

	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}

	public int getHospitalLevel() {
		return hospitalLevel;
	}

	public void setHospitalLevel(int hospitalLevel) {
		this.hospitalLevel = hospitalLevel;
	}

	public Date getSystime() {
		return systime;
	}

	public void setSystime(Date systime) {
		this.systime = systime;
	}

	public String getMedicaretypetext() {
		return medicaretypetext;
	}

	public void setMedicaretypetext(String medicaretypetext) {
		this.medicaretypetext = medicaretypetext;
	}

	public String getHospitalLocal() {
		return hospitalLocal;
	}

	public void setHospitalLocal(String hospitalLocal) {
		this.hospitalLocal = hospitalLocal;
	}

	public String getBusinessyear() {
		return businessyear;
	}

	public void setBusinessyear(String businessyear) {
		this.businessyear = businessyear;
	}

	public Boolean getMedicareFlag() {
		return MedicareFlag;
	}

	public void setMedicareFlag(Boolean medicareFlag) {
		MedicareFlag = medicareFlag;
	}

	public String getPersonTypeex() {
		return personTypeex;
	}

	public void setPersonTypeex(String personTypeex) {
		this.personTypeex = personTypeex;
	}

	public String getBizTypeex() {
		return bizTypeex;
	}

	public void setBizTypeex(String bizTypeex) {
		this.bizTypeex = bizTypeex;
	}

	public Date getFintime() {
		return fintime;
	}

	public void setFintime(Date fintime) {
		this.fintime = fintime;
	}

	public String getBizTypeexTxt() {
		return bizTypeexTxt;
	}

	public void setBizTypeexTxt(String bizTypeexTxt) {
		this.bizTypeexTxt = bizTypeexTxt;
	}

}
