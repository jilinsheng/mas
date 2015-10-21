package com.mingda.webclient.model;

import java.math.BigDecimal;
import java.util.Date;
/**
 *     /// 医后救助计算
        /// </summary>
        /// <param name="OrgCode">所在机构编号</param>
        /// <param name="Hospital_ID">医院ID</param>
        /// <param name="MemberType">1：城市，2：农村</param>
        /// <param name="MemberID">成员ID</param>
        /// <param name="MedicareType">1:医保，2：新农合，0：未参保参合</param>
        /// <param name="BizType">1：门诊，2：住院</param>
        /// <param name="SpecBiz">0：普通救助，1：重大疾病救助</param>
        /// <param name="Begin_Time">开始入院时间</param>
        /// <param name="End_Time">出院时间</param>
        /// <param name="Diagnose_Type_ID">病种类别ID</param>
        /// <param name="Icd_ID">病种ID</param>
        /// <param name="Pay_Total">总费用</param>
        /// <param name="Pay_Medicare">统筹</param>
        /// <param name="Pay_OutMedicare">目录外费用</param>
返回值
                    new XElement("GetAssistMoneyAfter",
                        new XElement("ReturnFlag", "1"),
                        new XElement("ResultFlag", "1"),
                        new XElement("Message", "成功"),
                        new XElement("AssistMoney", CPay.Pay_Assist.ToString("f2")),
                        new XElement("AssistSum", CurrentSumAssist.ToString("f2")),
                        new XElement("AssistSumIn", CurrentSumAssistIn.ToString("f2")),
                        new XElement("AssistSumOut", CurrentSumAssistOut.ToString("f2")),
                        new XElement("AssistCIA", CPay.Pay_CIAssist.ToString("f2")),
                        new XElement("CalcMsg", CPay.Calc_Str)

AssistMoney：本次救助金
AssistSum：本年累计
AssistSumIn：本年住院累计
AssistSumOut：本年门诊累计
AssistCIA：本次大病保险
CalcMsg：计算描述
public string GetAssistMoneyAfter(string OrgCode, long Hospital_ID, string MemberType, string MemberID,
                    int BizType, string Begin_Time,  string End_Time,
                    int Diagnose_Type_ID, int Icd_ID,
                    Decimal Pay_Total,
                    Decimal Pay_Medicare,
                    Decimal Pay_OutMedicare)
        {

 * @author Administrator
 *
 */
public class AfterDTO {
	private String OrgCode;
	private long Hospital_ID;
	private int Hospital_Level;
	private int Hospital_Local;
	private int Hospital_Type;
	private String MemberType;
	private String MemberID;
	private String MedicareType;
	private int BizType;
	private int SpecBiz;
	private String Begin_Time;
	private String End_Time;
	private int Diagnose_Type_ID;
	private int Icd_ID;
	private BigDecimal Pay_Total;
	private BigDecimal Pay_Medicare;
	private BigDecimal Pay_OutMedicare;
	private BigDecimal Pay_Dbbx;
	private BigDecimal Pay_Sybx;
	
	private String ReturnFlag;
	private String ResultFlag;
	private String Message;
	private BigDecimal AssistMoney;
	private BigDecimal AssistSum;
	private BigDecimal AssistSumIn;
	private BigDecimal AssistSumOut;
	private BigDecimal AssistCIA;
	private String CalcMsg;
	private String businessyear;
	private Integer zzFlag;
	
	public String getOrgCode() {
		return OrgCode;
	}
	public void setOrgCode(String orgCode) {
		OrgCode = orgCode;
	}
	public Long getHospital_ID() {
		return Hospital_ID;
	}
	public void setHospital_ID(Long hospital_ID) {
		Hospital_ID = hospital_ID;
	}
	public String getMemberType() {
		return MemberType;
	}
	public void setMemberType(String memberType) {
		MemberType = memberType;
	}
	public String getMemberID() {
		return MemberID;
	}
	public void setMemberID(String memberID) {
		MemberID = memberID;
	}
	public String getMedicareType() {
		return MedicareType;
	}
	public void setMedicareType(String medicareType) {
		MedicareType = medicareType;
	}
	public Integer getBizType() {
		return BizType;
	}
	public void setBizType(Integer bizType) {
		BizType = bizType;
	}
	public Integer getSpecBiz() {
		return SpecBiz;
	}
	public void setSpecBiz(Integer specBiz) {
		SpecBiz = specBiz;
	}
	public String getBegin_Time() {
		return Begin_Time;
	}
	public void setBegin_Time(String begin_Time) {
		Begin_Time = begin_Time;
	}
	public String getEnd_Time() {
		return End_Time;
	}
	public void setEnd_Time(String end_Time) {
		End_Time = end_Time;
	}
	public Integer getDiagnose_Type_ID() {
		return Diagnose_Type_ID;
	}
	public void setDiagnose_Type_ID(Integer diagnose_Type_ID) {
		Diagnose_Type_ID = diagnose_Type_ID;
	}
	public Integer getIcd_ID() {
		return Icd_ID;
	}
	public void setIcd_ID(Integer icd_ID) {
		Icd_ID = icd_ID;
	}
	public BigDecimal getPay_Total() {
		return Pay_Total;
	}
	public void setPay_Total(BigDecimal pay_Total) {
		Pay_Total = pay_Total;
	}
	public BigDecimal getPay_Medicare() {
		return Pay_Medicare;
	}
	public void setPay_Medicare(BigDecimal pay_Medicare) {
		Pay_Medicare = pay_Medicare;
	}
	public BigDecimal getPay_OutMedicare() {
		return Pay_OutMedicare;
	}
	public void setPay_OutMedicare(BigDecimal pay_OutMedicare) {
		Pay_OutMedicare = pay_OutMedicare;
	}
	public String getReturnFlag() {
		return ReturnFlag;
	}
	public void setReturnFlag(String returnFlag) {
		ReturnFlag = returnFlag;
	}
	public String getResultFlag() {
		return ResultFlag;
	}
	public void setResultFlag(String resultFlag) {
		ResultFlag = resultFlag;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public BigDecimal getAssistMoney() {
		return AssistMoney;
	}
	public void setAssistMoney(BigDecimal assistMoney) {
		AssistMoney = assistMoney;
	}
	public BigDecimal getAssistSum() {
		return AssistSum;
	}
	public void setAssistSum(BigDecimal assistSum) {
		AssistSum = assistSum;
	}
	public BigDecimal getAssistSumIn() {
		return AssistSumIn;
	}
	public void setAssistSumIn(BigDecimal assistSumIn) {
		AssistSumIn = assistSumIn;
	}
	public BigDecimal getAssistSumOut() {
		return AssistSumOut;
	}
	public void setAssistSumOut(BigDecimal assistSumOut) {
		AssistSumOut = assistSumOut;
	}
	public BigDecimal getAssistCIA() {
		return AssistCIA;
	}
	public void setAssistCIA(BigDecimal assistCIA) {
		AssistCIA = assistCIA;
	}
	public String getCalcMsg() {
		return CalcMsg;
	}
	public void setCalcMsg(String calcMsg) {
		CalcMsg = calcMsg;
	}
	public BigDecimal getPay_Dbbx() {
		return Pay_Dbbx;
	}
	public void setPay_Dbbx(BigDecimal pay_Dbbx) {
		Pay_Dbbx = pay_Dbbx;
	}
	public BigDecimal getPay_Sybx() {
		return Pay_Sybx;
	}
	public void setPay_Sybx(BigDecimal pay_Sybx) {
		Pay_Sybx = pay_Sybx;
	}
	public int getHospital_Level() {
		return Hospital_Level;
	}
	public void setHospital_Level(int hospital_Level) {
		Hospital_Level = hospital_Level;
	}
	public void setHospital_ID(long hospital_ID) {
		Hospital_ID = hospital_ID;
	}
	public void setBizType(int bizType) {
		BizType = bizType;
	}
	public void setSpecBiz(int specBiz) {
		SpecBiz = specBiz;
	}
	public void setDiagnose_Type_ID(int diagnose_Type_ID) {
		Diagnose_Type_ID = diagnose_Type_ID;
	}
	public void setIcd_ID(int icd_ID) {
		Icd_ID = icd_ID;
	}
	public int getHospital_Local() {
		return Hospital_Local;
	}
	public void setHospital_Local(int hospital_Local) {
		Hospital_Local = hospital_Local;
	}
	public int getHospital_Type() {
		return Hospital_Type;
	}
	public void setHospital_Type(int hospital_Type) {
		Hospital_Type = hospital_Type;
	}
	public String getBusinessyear() {
		return businessyear;
	}
	public void setBusinessyear(String businessyear) {
		this.businessyear = businessyear;
	}
	public Integer getZzFlag() {
		return zzFlag;
	}
	public void setZzFlag(Integer zzFlag) {
		this.zzFlag = zzFlag;
	}


}
