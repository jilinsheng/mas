package com.mingda.dto;

import java.math.BigDecimal;

public class ReportDTO {
	// -------- 费用------------------------
	private BigDecimal ROWNUM;// 序号
	private BigDecimal PNUM;// 人数
	private BigDecimal TOTAL;// 总费用
	private BigDecimal OUTMEDICARE;// 目录外费用
	private BigDecimal MEDICARE;// 医保
	private BigDecimal ASSIST;// 医疗救助
	private BigDecimal SELF;// 个人费用
	private String SUBSECTION;// 费用
	// ---------基本------------------------------
	private String PARENTORG; // 上级行政区
	private BigDecimal ZHUYUANP; // 住院救助人数
	private BigDecimal ZHUYUANM; // 住院救助金额
	private BigDecimal MENZHENP; // 门诊救助人数
	private BigDecimal MENZHENM; // 门诊救助金额
	private BigDecimal YAODIANP; // 药店救助人数
	private BigDecimal YAODIANM; // 药店救助金额
	private BigDecimal LINSHIP; // 临时救助人数
	private BigDecimal LINSHIM; // 临时救助金额
	private String REGION; // 地区
	private String REGIONNAME; // 地区名称
	private String ORGNAME; // 上级行政区名称
	// private BigDecimal PNUM; // 低保人口
	// ------------------对象----------------------------
	// private String ORGNAME // 地区名称
	private String PARENTORGNAME; // 上级行政区名称
	private String ORGID; // 地区ID
	private String PARENTORGID; // 上级地区ID
	private BigDecimal ZYSWRC; // 住院三无人次
	private BigDecimal ZYSWCBRC; // 住院三无参保人次
	private BigDecimal ZYSWJE; // 住院三无金额
	private BigDecimal ZYYBRC; // 住院一般人次
	private BigDecimal ZYYBCBRC; // 住院一般参保人次
	private BigDecimal ZYYBJE; // 住院一般金额
	private BigDecimal MZSWRC; // 门诊三无人次
	private BigDecimal MZSWCBRC; // 门诊三无参保人次
	private BigDecimal MZSWJE; // 门诊三无金额
	private BigDecimal MZYBRC; // 门诊一般人次
	private BigDecimal MZYBCBRC; // 门诊一般参保人次
	private BigDecimal MZYBJE; // 门诊一般金额
	private BigDecimal YDSWRC; // 药店三无人次
	private BigDecimal YDSWCBRC; // 药店三无参保人次
	private BigDecimal YDSWJE; // 药店三无金额
	private BigDecimal YDYBRC; // 药店一般人次
	private BigDecimal YDYBCBRC; // 药店一般参保人次
	private BigDecimal YDYBJE; // 药店一般金额

	// ----------------------机构--------------------------------
	// private BigDecimal ROWNUM; //序号
	private BigDecimal HOSPITAL_ID; // 医院ID
	private String NAME; // 医院名称
	private String DEPT_LEVEL; // 医院级别
	// private BigDecimal PNUM; //人次
	// private BigDecimal TOTAL; //总费用
	// private BigDecimal OUTMEDICARE;//目录外费用
	// private BigDecimal MEDICARE;//医保
	// private BigDecimal ASSIST;//医疗救助
	// private BigDecimal SELF;//个人费用
	private String FULLNAME;// 地区全称
	// --------------------------------------------
	// private String PARENTORGNAME;
	// private String ORGNAME;
	private BigDecimal PNUM3;
	private BigDecimal PNUM2;
	private BigDecimal PNUM1;
	private BigDecimal PNUM0;
	private BigDecimal TOTAL3;
	private BigDecimal TOTAL2;
	private BigDecimal TOTAL1;
	private BigDecimal TOTAL0;
	private BigDecimal OUTMEDICARE3;
	private BigDecimal OUTMEDICARE2;
	private BigDecimal OUTMEDICARE1;
	private BigDecimal OUTMEDICARE0;
	private BigDecimal MEDICARE3;
	private BigDecimal MEDICARE2;
	private BigDecimal MEDICARE1;
	private BigDecimal MEDICARE0;
	private BigDecimal ASSIST3;
	private BigDecimal ASSIST2;
	private BigDecimal ASSIST1;
	private BigDecimal ASSIST0;
	private BigDecimal SELF3;
	private BigDecimal SELF2;
	private BigDecimal SELF1;
	private BigDecimal SELF0;

	// -----------------医疗机构调查表----------------------------

	private String LEV; // 医疗机构级别
	private String FLAG0; // 是否签约
	private String FLAG1; // 是否有救助流程
	private String FLAG2; // 是否能及时结算、医保
	private String FLAG3; // 是否能及时结算、新农合
	private String FLAG4; // 是否可以安装救助系统

	// --------------------总体----------------------------------
	// private BigDecimal ROWNUM; //序号
	// private String ORGNAME; //机构名称
	// private String FULLNAME; //全称
	// private String NAME; //姓名
	private String FAMILY_ADDRESS; // 家庭住址
	private String ID_CARD; // 身份证号
	private String PERSON_TYPE; // 低保类型
	private String DIAGNOSE_NAME; // 病种
	private BigDecimal PAY_TOTAL; // 总费用
	private BigDecimal PAY_OUTMEDICARE; // 目标外费用
	private BigDecimal PAY_MEDICARE; // 医保
	private BigDecimal PAY_ASSIST;// 救助
	private BigDecimal PAY_SELF; // 自费部分

	// ----------------------病种--------------------------------
	private BigDecimal TOP; // 达到 封顶人数
	private BigDecimal LEVEL4; // 省级以上
	private BigDecimal LEVEL3; // 市级人次
	private BigDecimal LEVEL2; // 县级
	private BigDecimal LEVEL1; // 乡镇

	public void setROWNUM(BigDecimal rOWNUM) {
		ROWNUM = rOWNUM;
	}

	public BigDecimal getROWNUM() {
		return ROWNUM;
	}

	public void setPNUM(BigDecimal pNUM) {
		PNUM = pNUM;
	}

	public BigDecimal getPNUM() {
		return PNUM;
	}

	public void setTOTAL(BigDecimal tOTAL) {
		TOTAL = tOTAL;
	}

	public BigDecimal getTOTAL() {
		return TOTAL;
	}

	public void setOUTMEDICARE(BigDecimal oUTMEDICARE) {
		OUTMEDICARE = oUTMEDICARE;
	}

	public BigDecimal getOUTMEDICARE() {
		return OUTMEDICARE;
	}

	public void setMEDICARE(BigDecimal mEDICARE) {
		MEDICARE = mEDICARE;
	}

	public BigDecimal getMEDICARE() {
		return MEDICARE;
	}

	public void setASSIST(BigDecimal aSSIST) {
		ASSIST = aSSIST;
	}

	public BigDecimal getASSIST() {
		return ASSIST;
	}

	public void setSELF(BigDecimal sELF) {
		SELF = sELF;
	}

	public BigDecimal getSELF() {
		return SELF;
	}

	public void setSUBSECTION(String sUBSECTION) {
		SUBSECTION = sUBSECTION;
	}

	public String getSUBSECTION() {
		return SUBSECTION;
	}

	public void setPARENTORG(String pARENTORG) {
		PARENTORG = pARENTORG;
	}

	public String getPARENTORG() {
		return PARENTORG;
	}

	public void setZHUYUANP(BigDecimal zHUYUANP) {
		ZHUYUANP = zHUYUANP;
	}

	public BigDecimal getZHUYUANP() {
		return ZHUYUANP;
	}

	public void setZHUYUANM(BigDecimal zHUYUANM) {
		ZHUYUANM = zHUYUANM;
	}

	public BigDecimal getZHUYUANM() {
		return ZHUYUANM;
	}

	public void setMENZHENP(BigDecimal mENZHENP) {
		MENZHENP = mENZHENP;
	}

	public BigDecimal getMENZHENP() {
		return MENZHENP;
	}

	public void setMENZHENM(BigDecimal mENZHENM) {
		MENZHENM = mENZHENM;
	}

	public BigDecimal getMENZHENM() {
		return MENZHENM;
	}

	public void setYAODIANP(BigDecimal yAODIANP) {
		YAODIANP = yAODIANP;
	}

	public BigDecimal getYAODIANP() {
		return YAODIANP;
	}

	public void setYAODIANM(BigDecimal yAODIANM) {
		YAODIANM = yAODIANM;
	}

	public BigDecimal getYAODIANM() {
		return YAODIANM;
	}

	public void setREGION(String rEGION) {
		REGION = rEGION;
	}

	public String getREGION() {
		return REGION;
	}

	public void setREGIONNAME(String rEGIONNAME) {
		REGIONNAME = rEGIONNAME;
	}

	public String getREGIONNAME() {
		return REGIONNAME;
	}

	public void setORGNAME(String oRGNAME) {
		ORGNAME = oRGNAME;
	}

	public String getORGNAME() {
		return ORGNAME;
	}

	public void setPARENTORGNAME(String pARENTORGNAME) {
		PARENTORGNAME = pARENTORGNAME;
	}

	public String getPARENTORGNAME() {
		return PARENTORGNAME;
	}

	public void setORGID(String oRGID) {
		ORGID = oRGID;
	}

	public String getORGID() {
		return ORGID;
	}

	public void setPARENTORGID(String pARENTORGID) {
		PARENTORGID = pARENTORGID;
	}

	public String getPARENTORGID() {
		return PARENTORGID;
	}

	public void setZYSWRC(BigDecimal zYSWRC) {
		ZYSWRC = zYSWRC;
	}

	public BigDecimal getZYSWRC() {
		return ZYSWRC;
	}

	public void setZYSWCBRC(BigDecimal zYSWCBRC) {
		ZYSWCBRC = zYSWCBRC;
	}

	public BigDecimal getZYSWCBRC() {
		return ZYSWCBRC;
	}

	public void setZYSWJE(BigDecimal zYSWJE) {
		ZYSWJE = zYSWJE;
	}

	public BigDecimal getZYSWJE() {
		return ZYSWJE;
	}

	public void setZYYBRC(BigDecimal zYYBRC) {
		ZYYBRC = zYYBRC;
	}

	public BigDecimal getZYYBRC() {
		return ZYYBRC;
	}

	public void setZYYBCBRC(BigDecimal zYYBCBRC) {
		ZYYBCBRC = zYYBCBRC;
	}

	public BigDecimal getZYYBCBRC() {
		return ZYYBCBRC;
	}

	public void setZYYBJE(BigDecimal zYYBJE) {
		ZYYBJE = zYYBJE;
	}

	public BigDecimal getZYYBJE() {
		return ZYYBJE;
	}

	public void setMZSWRC(BigDecimal mZSWRC) {
		MZSWRC = mZSWRC;
	}

	public BigDecimal getMZSWRC() {
		return MZSWRC;
	}

	public void setMZSWCBRC(BigDecimal mZSWCBRC) {
		MZSWCBRC = mZSWCBRC;
	}

	public BigDecimal getMZSWCBRC() {
		return MZSWCBRC;
	}

	public void setMZSWJE(BigDecimal mZSWJE) {
		MZSWJE = mZSWJE;
	}

	public BigDecimal getMZSWJE() {
		return MZSWJE;
	}

	public void setMZYBRC(BigDecimal mZYBRC) {
		MZYBRC = mZYBRC;
	}

	public BigDecimal getMZYBRC() {
		return MZYBRC;
	}

	public void setMZYBCBRC(BigDecimal mZYBCBRC) {
		MZYBCBRC = mZYBCBRC;
	}

	public BigDecimal getMZYBCBRC() {
		return MZYBCBRC;
	}

	public void setMZYBJE(BigDecimal mZYBJE) {
		MZYBJE = mZYBJE;
	}

	public BigDecimal getMZYBJE() {
		return MZYBJE;
	}

	public void setYDSWRC(BigDecimal yDSWRC) {
		YDSWRC = yDSWRC;
	}

	public BigDecimal getYDSWRC() {
		return YDSWRC;
	}

	public void setYDSWCBRC(BigDecimal yDSWCBRC) {
		YDSWCBRC = yDSWCBRC;
	}

	public BigDecimal getYDSWCBRC() {
		return YDSWCBRC;
	}

	public void setYDSWJE(BigDecimal yDSWJE) {
		YDSWJE = yDSWJE;
	}

	public BigDecimal getYDSWJE() {
		return YDSWJE;
	}

	public void setYDYBRC(BigDecimal yDYBRC) {
		YDYBRC = yDYBRC;
	}

	public BigDecimal getYDYBRC() {
		return YDYBRC;
	}

	public void setYDYBCBRC(BigDecimal yDYBCBRC) {
		YDYBCBRC = yDYBCBRC;
	}

	public BigDecimal getYDYBCBRC() {
		return YDYBCBRC;
	}

	public void setYDYBJE(BigDecimal yDYBJE) {
		YDYBJE = yDYBJE;
	}

	public BigDecimal getYDYBJE() {
		return YDYBJE;
	}

	public void setHOSPITAL_ID(BigDecimal hOSPITAL_ID) {
		HOSPITAL_ID = hOSPITAL_ID;
	}

	public BigDecimal getHOSPITAL_ID() {
		return HOSPITAL_ID;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getNAME() {
		return NAME;
	}

	public void setDEPT_LEVEL(String dEPT_LEVEL) {
		DEPT_LEVEL = dEPT_LEVEL;
	}

	public String getDEPT_LEVEL() {
		return DEPT_LEVEL;
	}

	public void setFULLNAME(String fULLNAME) {
		FULLNAME = fULLNAME;
	}

	public String getFULLNAME() {
		return FULLNAME;
	}

	public void setFAMILY_ADDRESS(String fAMILY_ADDRESS) {
		FAMILY_ADDRESS = fAMILY_ADDRESS;
	}

	public String getFAMILY_ADDRESS() {
		return FAMILY_ADDRESS;
	}

	public void setID_CARD(String iD_CARD) {
		ID_CARD = iD_CARD;
	}

	public String getID_CARD() {
		return ID_CARD;
	}

	public void setPERSON_TYPE(String pERSON_TYPE) {
		PERSON_TYPE = pERSON_TYPE;
	}

	public String getPERSON_TYPE() {
		return PERSON_TYPE;
	}

	public void setDIAGNOSE_NAME(String dIAGNOSE_NAME) {
		DIAGNOSE_NAME = dIAGNOSE_NAME;
	}

	public String getDIAGNOSE_NAME() {
		return DIAGNOSE_NAME;
	}

	public void setPAY_TOTAL(BigDecimal pAY_TOTAL) {
		PAY_TOTAL = pAY_TOTAL;
	}

	public BigDecimal getPAY_TOTAL() {
		return PAY_TOTAL;
	}

	public void setPAY_OUTMEDICARE(BigDecimal pAY_OUTMEDICARE) {
		PAY_OUTMEDICARE = pAY_OUTMEDICARE;
	}

	public BigDecimal getPAY_OUTMEDICARE() {
		return PAY_OUTMEDICARE;
	}

	public void setPAY_MEDICARE(BigDecimal pAY_MEDICARE) {
		PAY_MEDICARE = pAY_MEDICARE;
	}

	public BigDecimal getPAY_MEDICARE() {
		return PAY_MEDICARE;
	}

	public void setPAY_ASSIST(BigDecimal pAY_ASSIST) {
		PAY_ASSIST = pAY_ASSIST;
	}

	public BigDecimal getPAY_ASSIST() {
		return PAY_ASSIST;
	}

	public void setPAY_SELF(BigDecimal pAY_SELF) {
		PAY_SELF = pAY_SELF;
	}

	public BigDecimal getPAY_SELF() {
		return PAY_SELF;
	}

	public void setTOP(BigDecimal tOP) {
		TOP = tOP;
	}

	public BigDecimal getTOP() {
		return TOP;
	}

	public void setLEVEL3(BigDecimal lEVEL3) {
		LEVEL3 = lEVEL3;
	}

	public BigDecimal getLEVEL3() {
		return LEVEL3;
	}

	public void setLEVEL2(BigDecimal lEVEL2) {
		LEVEL2 = lEVEL2;
	}

	public BigDecimal getLEVEL2() {
		return LEVEL2;
	}

	public void setLEVEL1(BigDecimal lEVEL1) {
		LEVEL1 = lEVEL1;
	}

	public BigDecimal getLEVEL1() {
		return LEVEL1;
	}

	public void setPNUM3(BigDecimal pNUM3) {
		PNUM3 = pNUM3;
	}

	public BigDecimal getPNUM3() {
		return PNUM3;
	}

	public void setPNUM2(BigDecimal pNUM2) {
		PNUM2 = pNUM2;
	}

	public BigDecimal getPNUM2() {
		return PNUM2;
	}

	public void setPNUM1(BigDecimal pNUM1) {
		PNUM1 = pNUM1;
	}

	public BigDecimal getPNUM1() {
		return PNUM1;
	}

	public void setPNUM0(BigDecimal pNUM0) {
		PNUM0 = pNUM0;
	}

	public BigDecimal getPNUM0() {
		return PNUM0;
	}

	public void setTOTAL3(BigDecimal tOTAL3) {
		TOTAL3 = tOTAL3;
	}

	public BigDecimal getTOTAL3() {
		return TOTAL3;
	}

	public void setTOTAL2(BigDecimal tOTAL2) {
		TOTAL2 = tOTAL2;
	}

	public BigDecimal getTOTAL2() {
		return TOTAL2;
	}

	public void setTOTAL1(BigDecimal tOTAL1) {
		TOTAL1 = tOTAL1;
	}

	public BigDecimal getTOTAL1() {
		return TOTAL1;
	}

	public void setTOTAL0(BigDecimal tOTAL0) {
		TOTAL0 = tOTAL0;
	}

	public BigDecimal getTOTAL0() {
		return TOTAL0;
	}

	public void setOUTMEDICARE3(BigDecimal oUTMEDICARE3) {
		OUTMEDICARE3 = oUTMEDICARE3;
	}

	public BigDecimal getOUTMEDICARE3() {
		return OUTMEDICARE3;
	}

	public void setOUTMEDICARE2(BigDecimal oUTMEDICARE2) {
		OUTMEDICARE2 = oUTMEDICARE2;
	}

	public BigDecimal getOUTMEDICARE2() {
		return OUTMEDICARE2;
	}

	public void setOUTMEDICARE1(BigDecimal oUTMEDICARE1) {
		OUTMEDICARE1 = oUTMEDICARE1;
	}

	public BigDecimal getOUTMEDICARE1() {
		return OUTMEDICARE1;
	}

	public void setOUTMEDICARE0(BigDecimal oUTMEDICARE0) {
		OUTMEDICARE0 = oUTMEDICARE0;
	}

	public BigDecimal getOUTMEDICARE0() {
		return OUTMEDICARE0;
	}

	public void setMEDICARE3(BigDecimal mEDICARE3) {
		MEDICARE3 = mEDICARE3;
	}

	public BigDecimal getMEDICARE3() {
		return MEDICARE3;
	}

	public void setMEDICARE2(BigDecimal mEDICARE2) {
		MEDICARE2 = mEDICARE2;
	}

	public BigDecimal getMEDICARE2() {
		return MEDICARE2;
	}

	public void setMEDICARE1(BigDecimal mEDICARE1) {
		MEDICARE1 = mEDICARE1;
	}

	public BigDecimal getMEDICARE1() {
		return MEDICARE1;
	}

	public void setMEDICARE0(BigDecimal mEDICARE0) {
		MEDICARE0 = mEDICARE0;
	}

	public BigDecimal getMEDICARE0() {
		return MEDICARE0;
	}

	public void setASSIST3(BigDecimal aSSIST3) {
		ASSIST3 = aSSIST3;
	}

	public BigDecimal getASSIST3() {
		return ASSIST3;
	}

	public void setASSIST2(BigDecimal aSSIST2) {
		ASSIST2 = aSSIST2;
	}

	public BigDecimal getASSIST2() {
		return ASSIST2;
	}

	public void setASSIST1(BigDecimal aSSIST1) {
		ASSIST1 = aSSIST1;
	}

	public BigDecimal getASSIST1() {
		return ASSIST1;
	}

	public void setASSIST0(BigDecimal aSSIST0) {
		ASSIST0 = aSSIST0;
	}

	public BigDecimal getASSIST0() {
		return ASSIST0;
	}

	public void setSELF3(BigDecimal sELF3) {
		SELF3 = sELF3;
	}

	public BigDecimal getSELF3() {
		return SELF3;
	}

	public void setSELF2(BigDecimal sELF2) {
		SELF2 = sELF2;
	}

	public BigDecimal getSELF2() {
		return SELF2;
	}

	public void setSELF1(BigDecimal sELF1) {
		SELF1 = sELF1;
	}

	public BigDecimal getSELF1() {
		return SELF1;
	}

	public void setSELF0(BigDecimal sELF0) {
		SELF0 = sELF0;
	}

	public BigDecimal getSELF0() {
		return SELF0;
	}

	public void setLEVEL4(BigDecimal lEVEL4) {
		LEVEL4 = lEVEL4;
	}

	public BigDecimal getLEVEL4() {
		return LEVEL4;
	}

	public BigDecimal getLINSHIP() {
		return LINSHIP;
	}

	public void setLINSHIP(BigDecimal lINSHIP) {
		LINSHIP = lINSHIP;
	}

	public BigDecimal getLINSHIM() {
		return LINSHIM;
	}

	public void setLINSHIM(BigDecimal lINSHIM) {
		LINSHIM = lINSHIM;
	}

	public String getLEV() {
		return LEV;
	}

	public void setLEV(String lEV) {
		LEV = lEV;
	}

	public String getFLAG0() {
		return FLAG0;
	}

	public void setFLAG0(String fLAG0) {
		FLAG0 = fLAG0;
	}

	public String getFLAG1() {
		return FLAG1;
	}

	public void setFLAG1(String fLAG1) {
		FLAG1 = fLAG1;
	}

	public String getFLAG2() {
		return FLAG2;
	}

	public void setFLAG2(String fLAG2) {
		FLAG2 = fLAG2;
	}

	public String getFLAG3() {
		return FLAG3;
	}

	public void setFLAG3(String fLAG3) {
		FLAG3 = fLAG3;
	}

	public String getFLAG4() {
		return FLAG4;
	}

	public void setFLAG4(String fLAG4) {
		FLAG4 = fLAG4;
	}

}
