package org.tempuri;

public class IServiceYljzProxy implements org.tempuri.IServiceYljz {
  private String _endpoint = null;
  private org.tempuri.IServiceYljz iServiceYljz = null;
  
  public IServiceYljzProxy() {
    _initIServiceYljzProxy();
  }
  
  public IServiceYljzProxy(String endpoint) {
    _endpoint = endpoint;
    _initIServiceYljzProxy();
  }
  
  private void _initIServiceYljzProxy() {
    try {
      iServiceYljz = (new org.tempuri.ServiceYljzLocator()).getBasicHttpBinding_IServiceYljz();
      if (iServiceYljz != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iServiceYljz)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iServiceYljz)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iServiceYljz != null)
      ((javax.xml.rpc.Stub)iServiceYljz)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.IServiceYljz getIServiceYljz() {
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz;
  }
  
  public java.lang.String userUpdate(java.lang.Long user_ID, java.lang.String account, java.lang.String password, java.lang.String name, java.lang.String admin_Flag, java.lang.Integer sts) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.userUpdate(user_ID, account, password, name, admin_Flag, sts);
  }
  
  public java.lang.String getUserListByDept(java.lang.Long hospital_ID, java.lang.Integer userStatus) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getUserListByDept(hospital_ID, userStatus);
  }
  
  public java.lang.String assignUser(java.lang.Long user_ID, java.lang.String password, java.lang.String name, java.lang.String admin_Flag, java.lang.Integer sts) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.assignUser(user_ID, password, name, admin_Flag, sts);
  }
  
  public java.lang.String getUserRightList(java.lang.Long hospital_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getUserRightList(hospital_ID);
  }
  
  public java.lang.String setUserRight(java.lang.Long user_ID, java.lang.Integer right_ID, java.lang.Integer setType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.setUserRight(user_ID, right_ID, setType);
  }
  
  public java.lang.String setBizMedia(java.lang.Long bizID, java.lang.String mediaFile, java.lang.Integer mediaType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.setBizMedia(bizID, mediaFile, mediaType);
  }
  
  public java.lang.String getBizMedia(java.lang.Long bizID, java.lang.Integer mediaType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getBizMedia(bizID, mediaType);
  }
  
  public java.lang.String getServerTime() throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getServerTime();
  }
  
  public java.lang.String getFamilyNoByName(java.lang.String memberName) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getFamilyNoByName(memberName);
  }
  
  public java.lang.String getOrgDataByCode(java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getOrgDataByCode(orgCode);
  }
  
  public java.lang.String getCIAssistByPaperIDEx(java.lang.String paperID, java.lang.String orgCode, java.lang.String medicareType, java.lang.Integer calcType, java.math.BigDecimal old_Pay_Total, java.math.BigDecimal old_Pay_Medicare, java.math.BigDecimal old_Pay_OutMedicare, java.math.BigDecimal pay_Total, java.math.BigDecimal pay_Medicare, java.math.BigDecimal pay_OutMedicare, java.lang.String end_Time, java.lang.String business_Year) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getCIAssistByPaperIDEx(paperID, orgCode, medicareType, calcType, old_Pay_Total, old_Pay_Medicare, old_Pay_OutMedicare, pay_Total, pay_Medicare, pay_OutMedicare, end_Time, business_Year);
  }
  
  public java.math.BigDecimal getSumDataByPaperIDEx(java.lang.String paperID, java.lang.Integer medicareType, java.lang.String finishYear, java.lang.Integer nType, java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getSumDataByPaperIDEx(paperID, medicareType, finishYear, nType, orgCode);
  }
  
  public java.math.BigDecimal getDbbxQfx(java.lang.Integer ds) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getDbbxQfx(ds);
  }
  
  public java.math.BigDecimal getDbbx(java.math.BigDecimal sumPreScope, java.math.BigDecimal zfyMony, java.math.BigDecimal tcMony, java.math.BigDecimal noPay, java.lang.Integer wsFlag, java.lang.Integer ds) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getDbbx(sumPreScope, zfyMony, tcMony, noPay, wsFlag, ds);
  }
  
  public java.lang.String queryBiz(java.lang.Long hospital_ID, java.lang.Integer biz_Type, java.lang.Integer medicare_Type, java.lang.String family_No, java.lang.String name, java.lang.String id_Card, java.lang.String begin_Time1, java.lang.String begin_Time2, java.lang.String end_Time1, java.lang.String end_Time2, java.lang.String oper_Time1, java.lang.String oper_Time2, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.queryBiz(hospital_ID, biz_Type, medicare_Type, family_No, name, id_Card, begin_Time1, begin_Time2, end_Time1, end_Time2, oper_Time1, oper_Time2, user_ID);
  }
  
  public java.lang.String getAssistSumByUser(java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistSumByUser(hospital_ID, begin_time, end_time);
  }
  
  public java.lang.String getAssistSumByHospital(java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistSumByHospital(hospital_ID, begin_time, end_time);
  }
  
  public java.lang.String getAssistSumByHospitalOrg(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time, java.lang.Integer personType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistSumByHospitalOrg(orgCode, hospital_ID, begin_time, end_time, personType);
  }
  
  public java.lang.String getAssistSumByMed(java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistSumByMed(hospital_ID, begin_time, end_time);
  }
  
  public java.lang.String getAssistSumByMedOrg(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time, java.lang.Integer personType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistSumByMedOrg(orgCode, hospital_ID, begin_time, end_time, personType);
  }
  
  public java.lang.String getAssistListByHospital(java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time, java.lang.Integer personType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistListByHospital(hospital_ID, begin_time, end_time, personType);
  }
  
  public java.lang.String getAssistListByHospitalOrg(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time, java.lang.Integer personType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistListByHospitalOrg(orgCode, hospital_ID, begin_time, end_time, personType);
  }
  
  public java.lang.String getAssistListByHospitalOrgEx(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time, java.lang.Integer personType, java.lang.Integer bizType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistListByHospitalOrgEx(orgCode, hospital_ID, begin_time, end_time, personType, bizType);
  }
  
  public java.lang.String getAssistListByMed(java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistListByMed(hospital_ID, begin_time, end_time);
  }
  
  public java.lang.String getAssistListByMedOrg(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String begin_time, java.lang.String end_time, java.lang.Integer personType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistListByMedOrg(orgCode, hospital_ID, begin_time, end_time, personType);
  }
  
  public java.lang.String getHospitalDept(java.lang.Long hospital_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getHospitalDept(hospital_ID);
  }
  
  public java.lang.String addHospitalDept(java.lang.Long hospital_ID, java.lang.String dept_Name) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.addHospitalDept(hospital_ID, dept_Name);
  }
  
  public java.lang.String deleteHospitalDept(java.lang.Long hospital_ID, java.lang.String dept_Name) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.deleteHospitalDept(hospital_ID, dept_Name);
  }
  
  public java.lang.String modifyHospitalDept(java.lang.Long hospital_ID, java.lang.String old_DeptName, java.lang.String new_DeptName) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.modifyHospitalDept(hospital_ID, old_DeptName, new_DeptName);
  }
  
  public java.lang.String getHospitalArea(java.lang.Long hospital_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getHospitalArea(hospital_ID);
  }
  
  public java.lang.String addHospitalArea(java.lang.Long hospital_ID, java.lang.String area_Name) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.addHospitalArea(hospital_ID, area_Name);
  }
  
  public java.lang.String deleteHospitalArea(java.lang.Long hospital_ID, java.lang.String area_Name) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.deleteHospitalArea(hospital_ID, area_Name);
  }
  
  public java.lang.String modifyHospitalArea(java.lang.Long hospital_ID, java.lang.String old_AreaName, java.lang.String new_AreaName) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.modifyHospitalArea(hospital_ID, old_AreaName, new_AreaName);
  }
  
  public java.lang.String hospitalCert(byte[] licenseKey) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.hospitalCert(licenseKey);
  }
  
  public java.lang.String getHospitalPayInfo(java.lang.Long hospital_ID, java.lang.String pay_time) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getHospitalPayInfo(hospital_ID, pay_time);
  }
  
  public java.lang.String getHospitalLicFee(java.lang.Long hospital_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getHospitalLicFee(hospital_ID);
  }
  
  public java.lang.String setHospitalVer(java.lang.Long hospital_ID, java.lang.String verNum) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.setHospitalVer(hospital_ID, verNum);
  }
  
  public java.lang.String changeHospitalAlert(java.lang.Long hospital_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.changeHospitalAlert(hospital_ID);
  }
  
  public java.lang.String userLogin(java.lang.String account, java.lang.String password) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.userLogin(account, password);
  }
  
  public java.lang.String getNewAccount() throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getNewAccount();
  }
  
  public java.lang.String userAdd(java.lang.Long hospital_ID, java.lang.String account, java.lang.String password, java.lang.String name, java.lang.String admin_Flag) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.userAdd(hospital_ID, account, password, name, admin_Flag);
  }
  
  public java.lang.String userDelete(java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.userDelete(user_ID);
  }
  
  public java.lang.String getRegFile(java.lang.Long biz_id) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getRegFile(biz_id);
  }
  
  public java.lang.String getExistBiz(java.lang.String memberType, java.lang.String memberID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getExistBiz(memberType, memberID);
  }
  
  public java.lang.String getCheckStatus(java.lang.Long biz_id) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getCheckStatus(biz_id);
  }
  
  public java.lang.String getStatInfoByMember(java.lang.String memberType, java.lang.String memberID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getStatInfoByMember(memberType, memberID);
  }
  
  public java.lang.String getAssistMoney(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Long biz_id, java.lang.Long out_biz_id, java.lang.Integer bizType, java.lang.Integer inTypeID, java.lang.String begin_Time, java.lang.String dept_Name, java.lang.String area_Name, java.lang.Long icdID, java.lang.String diagnose_Code, java.lang.String diagnose_Name, java.lang.String end_Time, java.lang.String fin_Time, java.lang.Integer diagnoseType, java.lang.Integer specFlag, java.math.BigDecimal pay_Total, java.math.BigDecimal pay_Account, java.math.BigDecimal pay_Medicare, java.math.BigDecimal pay_Other, java.math.BigDecimal pay_OutMedicare, java.math.BigDecimal pay_MinLine, java.math.BigDecimal pay_Cash, java.math.BigDecimal pay_Dbbx, java.lang.Integer gatherFlag, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistMoney(orgCode, hospital_ID, memberType, memberID, biz_id, out_biz_id, bizType, inTypeID, begin_Time, dept_Name, area_Name, icdID, diagnose_Code, diagnose_Name, end_Time, fin_Time, diagnoseType, specFlag, pay_Total, pay_Account, pay_Medicare, pay_Other, pay_OutMedicare, pay_MinLine, pay_Cash, pay_Dbbx, gatherFlag, user_ID);
  }
  
  public java.lang.String getAssistMoneyDbbx(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Long biz_id, java.lang.Long out_biz_id, java.lang.Integer bizType, java.lang.Integer inTypeID, java.lang.String begin_Time, java.lang.String dept_Name, java.lang.String area_Name, java.lang.Long icdID, java.lang.String diagnose_Code, java.lang.String diagnose_Name, java.lang.String end_Time, java.lang.String fin_Time, java.math.BigDecimal pay_Total, java.math.BigDecimal pay_Account, java.math.BigDecimal pay_Medicare, java.math.BigDecimal pay_Other, java.math.BigDecimal pay_OutMedicare, java.math.BigDecimal pay_MinLine, java.math.BigDecimal pay_Cash, java.math.BigDecimal pay_Dbbx, java.lang.Integer gatherFlag, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistMoneyDbbx(orgCode, hospital_ID, memberType, memberID, biz_id, out_biz_id, bizType, inTypeID, begin_Time, dept_Name, area_Name, icdID, diagnose_Code, diagnose_Name, end_Time, fin_Time, pay_Total, pay_Account, pay_Medicare, pay_Other, pay_OutMedicare, pay_MinLine, pay_Cash, pay_Dbbx, gatherFlag, user_ID);
  }
  
  public java.lang.String getAssistMoneyMed(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Long biz_id, java.math.BigDecimal pay_Total, java.math.BigDecimal pay_Assist, java.math.BigDecimal pay_Balance, java.lang.Integer gatherFlag, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistMoneyMed(orgCode, hospital_ID, memberType, memberID, biz_id, pay_Total, pay_Assist, pay_Balance, gatherFlag, user_ID);
  }
  
  public java.lang.String getAssistMoneyMed0506(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Long biz_id, java.lang.Integer diagnoseTypeID, java.math.BigDecimal pay_Total, java.math.BigDecimal pay_Medicare, java.math.BigDecimal pay_Assist, java.lang.Integer gatherFlag, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistMoneyMed0506(orgCode, hospital_ID, memberType, memberID, biz_id, diagnoseTypeID, pay_Total, pay_Medicare, pay_Assist, gatherFlag, user_ID);
  }
  
  public java.lang.String getAssistMoneyMed0506Ex(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Long biz_id, java.lang.Integer diagnoseTypeID, java.math.BigDecimal pay_Total, java.math.BigDecimal pay_Medicare, java.math.BigDecimal pay_Dbbx, java.math.BigDecimal pay_Assist, java.lang.Integer gatherFlag, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistMoneyMed0506Ex(orgCode, hospital_ID, memberType, memberID, biz_id, diagnoseTypeID, pay_Total, pay_Medicare, pay_Dbbx, pay_Assist, gatherFlag, user_ID);
  }
  
  public java.lang.String getAssistMoneyMed0506Ex3(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Long biz_id, java.lang.Integer diagnoseTypeID, java.math.BigDecimal pay_Total, java.math.BigDecimal pay_Medicare, java.math.BigDecimal pay_OutMedicare, java.math.BigDecimal pay_Dbbx, java.math.BigDecimal pay_Assist, java.lang.Integer gatherFlag, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistMoneyMed0506Ex3(orgCode, hospital_ID, memberType, memberID, biz_id, diagnoseTypeID, pay_Total, pay_Medicare, pay_OutMedicare, pay_Dbbx, pay_Assist, gatherFlag, user_ID);
  }
  
  public java.lang.String getAssistMoneyAfterEx(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.Integer hospital_Level, java.lang.Integer hospital_Local, java.lang.Integer hospital_Type, java.lang.String memberType, java.lang.String memberID, java.lang.String medicareType, java.lang.Integer bizType, java.lang.Integer specBiz, java.lang.String begin_Time, java.lang.String end_Time, java.lang.Integer diagnose_Type_ID, java.lang.Integer icd_ID, java.math.BigDecimal pay_Total, java.math.BigDecimal pay_Medicare, java.math.BigDecimal pay_Dbbx, java.math.BigDecimal pay_Sybx, java.math.BigDecimal pay_OutMedicare, java.lang.String business_Year, java.lang.Integer zzFlag) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistMoneyAfterEx(orgCode, hospital_ID, hospital_Level, hospital_Local, hospital_Type, memberType, memberID, medicareType, bizType, specBiz, begin_Time, end_Time, diagnose_Type_ID, icd_ID, pay_Total, pay_Medicare, pay_Dbbx, pay_Sybx, pay_OutMedicare, business_Year, zzFlag);
  }
  
  public java.lang.String getAssistList(java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Integer bizType, java.lang.String beginTime, java.lang.String endTime) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistList(hospital_ID, memberType, memberID, bizType, beginTime, endTime);
  }
  
  public java.lang.String getAssistChargeByHospital(java.lang.Long hospital_ID, java.lang.Integer bizType, java.lang.String beginTime, java.lang.String endTime) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getAssistChargeByHospital(hospital_ID, bizType, beginTime, endTime);
  }
  
  public java.lang.String refundFee(java.lang.Long biz_id, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.refundFee(biz_id, user_ID);
  }
  
  public java.lang.String refundMedFee(java.lang.Long biz_id, java.lang.String memberType, java.lang.String memberID, java.math.BigDecimal payAssist, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.refundMedFee(biz_id, memberType, memberID, payAssist, user_ID);
  }
  
  public java.lang.String refundMedFee0506(java.lang.Long biz_id, java.lang.String memberType, java.lang.String memberID, java.math.BigDecimal payAssist, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.refundMedFee0506(biz_id, memberType, memberID, payAssist, user_ID);
  }
  
  public java.lang.String getChargeListByUser(java.lang.Integer bizType, java.lang.Integer chargeType, java.util.Calendar beginTime, java.util.Calendar endTime, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getChargeListByUser(bizType, chargeType, beginTime, endTime, user_ID);
  }
  
  public java.lang.String queryOutDisease(java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.queryOutDisease(orgCode);
  }
  
  public java.lang.String queryDiagnoseType(java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.queryDiagnoseType(orgCode);
  }
  
  public java.lang.String queryDiagnoseTypeOrg(java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.queryDiagnoseTypeOrg(orgCode);
  }
  
  public java.lang.String queryDiagnoseTypeByBizID(java.lang.Long biz_id) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.queryDiagnoseTypeByBizID(biz_id);
  }
  
  public java.lang.String setDiagnoseTypeByBizID(java.lang.Long biz_id, java.lang.Long diagnose_type_id) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.setDiagnoseTypeByBizID(biz_id, diagnose_type_id);
  }
  
  public java.lang.String getBillData(java.lang.Long biz_id) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getBillData(biz_id);
  }
  
  public java.lang.String getMedBillData(java.lang.Long biz_id) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getMedBillData(biz_id);
  }
  
  public java.lang.String setBJzBillNo(java.lang.Long biz_id, java.lang.String bill_no, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.setBJzBillNo(biz_id, bill_no, user_ID);
  }
  
  public java.lang.String getBillNo(java.lang.Integer bizType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getBillNo(bizType);
  }
  
  public java.lang.String getConfirmListByHospital(java.lang.Long hospital_ID, java.lang.Integer viewType, java.lang.String begin_time, java.lang.String end_time) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getConfirmListByHospital(hospital_ID, viewType, begin_time, end_time);
  }
  
  public java.lang.String confirmByHospital(java.lang.Long biz_id, java.lang.Integer confirmFlag, java.lang.String confirmDesc, java.lang.Long user_ID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.confirmByHospital(biz_id, confirmFlag, confirmDesc, user_ID);
  }
  
  public java.lang.String getData(java.lang.Integer value) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getData(value);
  }
  
  public org.datacontract.schemas._2004._07.WcfYljz.CompositeType getDataUsingDataContract(org.datacontract.schemas._2004._07.WcfYljz.CompositeType composite) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getDataUsingDataContract(composite);
  }
  
  public java.lang.String getClientIp() throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getClientIp();
  }
  
  public org.datacontract.schemas._2004._07.WcfYljz.TestDataRet testDefType(org.datacontract.schemas._2004._07.WcfYljz.TestData TD) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.testDefType(TD);
  }
  
  public java.lang.String getOnline(java.lang.Long hospital_ID, java.lang.String hospital_Name) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getOnline(hospital_ID, hospital_Name);
  }
  
  public java.lang.String writeLog(java.lang.Long hospital_ID, java.lang.String hospital_Name, java.lang.Long user_ID, java.lang.String user_Name, java.lang.String opt, java.lang.String opt_Para) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.writeLog(hospital_ID, hospital_Name, user_ID, user_Name, opt, opt_Para);
  }
  
  public java.lang.String writeLogEx(java.lang.Long hospital_ID, java.lang.String hospital_Name, java.lang.Long user_ID, java.lang.String user_Name, java.lang.String opt, java.lang.String opt_Para, java.lang.String OSVer, java.lang.String ipAddress, java.lang.String scanDevice, java.lang.String appVer) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.writeLogEx(hospital_ID, hospital_Name, user_ID, user_Name, opt, opt_Para, OSVer, ipAddress, scanDevice, appVer);
  }
  
  public java.lang.String hospital_Pay(java.lang.Long listID, java.lang.String hospital_Name, java.lang.Long hospital_ID, java.math.BigDecimal fee_Amount, java.lang.String fee_Time, java.lang.String contact, java.lang.String phone_Moible, java.lang.String phone_Fix, java.lang.String QQNum, java.lang.String note_Image, java.lang.String remark) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.hospital_Pay(listID, hospital_Name, hospital_ID, fee_Amount, fee_Time, contact, phone_Moible, phone_Fix, QQNum, note_Image, remark);
  }
  
  public java.lang.String getPersonInfo(java.lang.Integer certType, java.lang.String orgCode, java.lang.String certNo) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getPersonInfo(certType, orgCode, certNo);
  }
  
  public java.lang.String getPersonInfoRemote(java.lang.Integer certType, java.lang.String orgCode, java.lang.String certNo) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getPersonInfoRemote(certType, orgCode, certNo);
  }
  
  public java.lang.String getPersonInfoByAssistID(java.lang.String assistID, java.lang.String memberID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getPersonInfoByAssistID(assistID, memberID);
  }
  
  public java.lang.String getPersonInfoByIn(java.lang.Long hospital_ID, java.lang.Integer certType, java.lang.String orgCode, java.lang.String certNo) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getPersonInfoByIn(hospital_ID, certType, orgCode, certNo);
  }
  
  public java.lang.String getPharmacyAssist(java.lang.String memberType, java.lang.String memberID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getPharmacyAssist(memberType, memberID);
  }
  
  public java.lang.String getPharmacyICD(java.lang.String memberType, java.lang.String memberID, java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getPharmacyICD(memberType, memberID, orgCode);
  }
  
  public java.lang.String setMemberMedicareInfo(java.lang.String memberType, java.lang.String memberID, java.lang.String medicareType, java.lang.String SSN) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.setMemberMedicareInfo(memberType, memberID, medicareType, SSN);
  }
  
  public java.lang.String getOrgCodeByFamilyNo(java.lang.String familyNo) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getOrgCodeByFamilyNo(familyNo);
  }
  
  public java.lang.String getDeptOrgByFamilyNo(java.lang.String familyNo, java.lang.Long hospitalID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getDeptOrgByFamilyNo(familyNo, hospitalID);
  }
  
  public java.lang.String getDeptListByOrg(java.lang.String orgNo, java.lang.Integer deptType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getDeptListByOrg(orgNo, deptType);
  }
  
  public java.lang.String getOrgListByDept(java.lang.Long deptID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getOrgListByDept(deptID);
  }
  
  public java.lang.String getBizID() throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getBizID();
  }
  
  public java.lang.String getHospitalPayID() throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getHospitalPayID();
  }
  
  public java.lang.String getExistInReg(java.lang.String memberType, java.lang.String memberID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getExistInReg(memberType, memberID);
  }
  
  public java.lang.String queryInType(java.lang.String orgCode) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.queryInType(orgCode);
  }
  
  public java.lang.String checkInReg(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Long biz_id, java.lang.String beginTime, java.lang.String deptName, java.lang.String areaname, java.lang.Integer diagnoseType, java.lang.String diagnoseCode, java.lang.String diagnoseName, java.lang.Integer inType, java.lang.Integer spec_Diagnose_flag, java.lang.Integer gatherFlag, java.lang.Long userID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.checkInReg(orgCode, hospital_ID, memberType, memberID, biz_id, beginTime, deptName, areaname, diagnoseType, diagnoseCode, diagnoseName, inType, spec_Diagnose_flag, gatherFlag, userID);
  }
  
  public java.lang.String checkInRegEx(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Long biz_id, java.lang.String beginTime, java.lang.String deptName, java.lang.String areaname, java.lang.Integer diagnoseType, java.lang.String diagnoseCode, java.lang.String diagnoseName, java.lang.Integer inType, java.lang.Integer gatherFlag, java.lang.Long userID) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.checkInRegEx(orgCode, hospital_ID, memberType, memberID, biz_id, beginTime, deptName, areaname, diagnoseType, diagnoseCode, diagnoseName, inType, gatherFlag, userID);
  }
  
  public java.lang.String getInpatientsList(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.String memberType, java.lang.String memberID, java.lang.Integer queryType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getInpatientsList(orgCode, hospital_ID, memberType, memberID, queryType);
  }
  
  public java.lang.String getInpatientsListByHospital(java.lang.String orgCode, java.lang.Long hospital_ID, java.lang.Integer queryType) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.getInpatientsListByHospital(orgCode, hospital_ID, queryType);
  }
  
  public java.lang.String deleteInReg(java.lang.Long biz_id) throws java.rmi.RemoteException{
    if (iServiceYljz == null)
      _initIServiceYljzProxy();
    return iServiceYljz.deleteInReg(biz_id);
  }
  
  
}