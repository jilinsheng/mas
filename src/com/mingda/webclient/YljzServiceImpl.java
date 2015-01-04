package com.mingda.webclient;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.tempuri.IServiceYljzProxy;

import com.mingda.webclient.model.AfterDTO;
import com.mingda.webclient.model.CiDTO;

public class YljzServiceImpl implements YljzService {
	private IServiceYljzProxy iServiceYljzProxy;

	public IServiceYljzProxy getiServiceYljzProxy() {
		return iServiceYljzProxy;
	}

	public void setiServiceYljzProxy(IServiceYljzProxy iServiceYljzProxy) {
		this.iServiceYljzProxy = iServiceYljzProxy;
	}

	/**
	 * private BigDecimal paySumAssistIn; private BigDecimal PaySumAssistOut;
	 * private BigDecimal SumMedicareScope; private BigDecimal PayCIAssist;
	 * private String returnFlag; private String resultFlag; private BigDecimal
	 * pay_Sum_AssistScope_In; private BigDecimal pay_PreSum_AssistScope_In;
	 */
	@Override
	public CiDTO getCiAssistByPaperID(CiDTO cidto) {
		try {
			/*
			 * String xml = iServiceYljzProxy.getCIAssistByPaperID(
			 * cidto.getPaperID(), cidto.getMedicareType(),
			 * cidto.getPay_Total(), cidto.getPay_Medicare(),
			 * cidto.getPay_OutMedicare());
			 */

			String xml = iServiceYljzProxy.getCIAssistByPaperID(
					cidto.getPaperID(), cidto.getMedicareType(),
					cidto.getCalcType(), cidto.getOld_Pay_Total(),
					cidto.getOld_Pay_Medicare(),
					cidto.getOld_Pay_OutMedicare(), cidto.getPay_Total(),
					cidto.getPay_Medicare(), cidto.getPay_OutMedicare());
			Document document = DocumentHelper.parseText(xml);
			String returnFlag = document.selectSingleNode(
					"//GetCIAssistByPaperID/ReturnFlag").getText();
			String resultFlag = document.selectSingleNode(
					"//GetCIAssistByPaperID/ResultFlag").getText();
			String paySumAssistIn = document.selectSingleNode(
					"//GetCIAssistByPaperID/PaySumAssistIn").getText();
			String paySumAssistOut = document.selectSingleNode(
					"//GetCIAssistByPaperID/PaySumAssistOut").getText();
			String sumMedicareScope = document.selectSingleNode(
					"//GetCIAssistByPaperID/SumMedicareScope").getText();
			String payCIAssist = document.selectSingleNode(
					"//GetCIAssistByPaperID/PayCIAssist").getText();
			String pay_PreSum_AssistScope_In = document.selectSingleNode(
					"//GetCIAssistByPaperID/Pay_PreSum_AssistScope_In")
					.getText();
			String pay_Sum_AssistScope_In = document.selectSingleNode(
					"//GetCIAssistByPaperID/Pay_Sum_AssistScope_In").getText();
			if (null == paySumAssistIn || "".equals("0")) {
				paySumAssistIn = "0";
			}
			if (null == paySumAssistOut || "".equals("0")) {
				paySumAssistOut = "0";
			}
			if (null == sumMedicareScope || "".equals("0")) {
				sumMedicareScope = "0";
			}
			if (null == payCIAssist || "".equals("0")) {
				payCIAssist = "0";
			}
			cidto.setPayCIAssist(new BigDecimal(payCIAssist));
			cidto.setPaySumAssistIn(new BigDecimal(paySumAssistIn));
			cidto.setPaySumAssistOut(new BigDecimal(paySumAssistOut));
			cidto.setSumMedicareScope(new BigDecimal(sumMedicareScope));
			cidto.setResultFlag(resultFlag);
			cidto.setReturnFlag(returnFlag);
			cidto.setPay_PreSum_AssistScope_In(new BigDecimal(
					pay_PreSum_AssistScope_In));
			cidto.setPay_Sum_AssistScope_In(new BigDecimal(
					pay_Sum_AssistScope_In));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return cidto;
	}
	
	public AfterDTO getAssistMoneyAfter(AfterDTO afterDTO) {
		try {
			String xml = iServiceYljzProxy.getAssistMoneyAfter(
					afterDTO.getOrgCode(),afterDTO.getHospital_ID(),
					afterDTO.getMemberType(),afterDTO.getMemberID(),
					afterDTO.getMedicareType(),afterDTO.getBizType(),
					afterDTO.getSpecBiz(),afterDTO.getBegin_Time(),
					afterDTO.getEnd_Time(),afterDTO.getDiagnose_Type_ID(),
					afterDTO.getIcd_ID(),afterDTO.getPay_Total(),
					afterDTO.getPay_Medicare(),afterDTO.getPay_Dbbx(),
					afterDTO.getPay_Sybx(),
					afterDTO.getPay_OutMedicare());
			Document document = DocumentHelper.parseText(xml);
			String returnFlag = document.selectSingleNode(
					"//GetAssistMoneyAfter/ReturnFlag").getText();
			String resultFlag = document.selectSingleNode(
					"//GetAssistMoneyAfter/ResultFlag").getText();
			String message = document.selectSingleNode(
					"//GetAssistMoneyAfter/Message").getText();
			String assistMoney="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistMoney")!=null){
				assistMoney = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistMoney").getText();
			}
			String assistSum="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistSum") != null){
				assistSum = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistSum").getText();
			}
			String assistSumIn="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistSumIn") != null){
				assistSumIn = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistSumIn").getText();
			}
			String assistSumOut="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistSumOut") != null){
				assistSumOut = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistSumOut").getText();
			}
			String assistCIA = "";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistCIA") != null){
				assistCIA = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistCIA").getText();
			}
			String calcMsg="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/CalcMsg") != null){
				calcMsg = document.selectSingleNode(
						"//GetAssistMoneyAfter/CalcMsg").getText();
			}
			
			if (null == assistMoney || "".equals(assistMoney)) {
				assistMoney = "0";
			}	
			if (null == assistSum || "".equals(assistSum)) {
				assistSum = "0";
			}	
			if (null == assistSumIn || "".equals(assistSumIn)) {
				assistSumIn = "0";
			}	
			if (null == assistSumOut || "".equals(assistSumOut)) {
				assistSumOut = "0";
			}	
			if (null == assistCIA || "".equals(assistCIA)) {
				assistCIA = "0";
			}
			afterDTO.setReturnFlag(returnFlag);
			afterDTO.setResultFlag(resultFlag);
			afterDTO.setMessage(message);
			afterDTO.setAssistMoney(new BigDecimal(assistMoney));
			afterDTO.setAssistSum(new BigDecimal(assistSum));
			afterDTO.setAssistSumIn(new BigDecimal(assistSumIn));
			afterDTO.setAssistSumOut(new BigDecimal(assistSumOut));
			afterDTO.setAssistCIA(new BigDecimal(assistCIA));
			afterDTO.setCalcMsg(calcMsg);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return afterDTO;
	}
	
	public AfterDTO getAssistMoneyAfterEx(AfterDTO afterDTO) {
		try {
			String xml = iServiceYljzProxy.getAssistMoneyAfterEx(
					afterDTO.getOrgCode(),afterDTO.getHospital_ID(),
					afterDTO.getHospital_Level(),
					afterDTO.getMemberType(),afterDTO.getMemberID(),
					afterDTO.getMedicareType(),afterDTO.getBizType(),
					afterDTO.getSpecBiz(),afterDTO.getBegin_Time(),
					afterDTO.getEnd_Time(),afterDTO.getDiagnose_Type_ID(),
					afterDTO.getIcd_ID(),afterDTO.getPay_Total(),
					afterDTO.getPay_Medicare(),afterDTO.getPay_Dbbx(),
					afterDTO.getPay_Sybx(),
					afterDTO.getPay_OutMedicare());
			/*System.out.println("getAssistMoneyAfterEx("+afterDTO.getOrgCode()+","
					+afterDTO.getHospital_ID()+","
					+afterDTO.getHospital_Level()+","
					+afterDTO.getMemberType()+","
					+afterDTO.getMemberID()+","
					+afterDTO.getMedicareType()+","
					+afterDTO.getBizType()+","
					+afterDTO.getSpecBiz()+","
					+afterDTO.getBegin_Time()+","
					+afterDTO.getEnd_Time()+","
					+afterDTO.getDiagnose_Type_ID()+","
					+afterDTO.getIcd_ID()+","
					+afterDTO.getPay_Total()+","
					+afterDTO.getPay_Medicare()+","
					+afterDTO.getPay_Dbbx()+","
					+afterDTO.getPay_Sybx()+","
					+afterDTO.getPay_OutMedicare()+")");*/
			Document document = DocumentHelper.parseText(xml);
			String returnFlag = document.selectSingleNode(
					"//GetAssistMoneyAfter/ReturnFlag").getText();
			String resultFlag = document.selectSingleNode(
					"//GetAssistMoneyAfter/ResultFlag").getText();
			String message = document.selectSingleNode(
					"//GetAssistMoneyAfter/Message").getText();
			String assistMoney="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistMoney")!=null){
				assistMoney = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistMoney").getText();
			}
			String assistSum="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistSum") != null){
				assistSum = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistSum").getText();
			}
			String assistSumIn="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistSumIn") != null){
				assistSumIn = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistSumIn").getText();
			}
			String assistSumOut="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistSumOut") != null){
				assistSumOut = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistSumOut").getText();
			}
			String assistCIA = "";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/AssistCIA") != null){
				assistCIA = document.selectSingleNode(
						"//GetAssistMoneyAfter/AssistCIA").getText();
			}
			String calcMsg="";
			if(document.selectSingleNode(
					"//GetAssistMoneyAfter/CalcMsg") != null){
				calcMsg = document.selectSingleNode(
						"//GetAssistMoneyAfter/CalcMsg").getText();
			}
			
			if (null == assistMoney || "".equals(assistMoney)) {
				assistMoney = "0";
			}	
			if (null == assistSum || "".equals(assistSum)) {
				assistSum = "0";
			}	
			if (null == assistSumIn || "".equals(assistSumIn)) {
				assistSumIn = "0";
			}	
			if (null == assistSumOut || "".equals(assistSumOut)) {
				assistSumOut = "0";
			}	
			if (null == assistCIA || "".equals(assistCIA)) {
				assistCIA = "0";
			}
			afterDTO.setReturnFlag(returnFlag);
			afterDTO.setResultFlag(resultFlag);
			afterDTO.setMessage(message);
			afterDTO.setAssistMoney(new BigDecimal(assistMoney));
			afterDTO.setAssistSum(new BigDecimal(assistSum));
			afterDTO.setAssistSumIn(new BigDecimal(assistSumIn));
			afterDTO.setAssistSumOut(new BigDecimal(assistSumOut));
			afterDTO.setAssistCIA(new BigDecimal(assistCIA));
			afterDTO.setCalcMsg(calcMsg);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return afterDTO;
	}
}
