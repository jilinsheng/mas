package com.mingda.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.mingda.dto.BillDTO;
import com.mingda.dto.DeptDTO;
import com.mingda.dto.DiagnoseTypeDTO;
import com.mingda.dto.JzMedicalafterBillDTO;
import com.mingda.dto.JzMedicalafterRuleDTO;
import com.mingda.dto.JzMedicalafterfileDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.dto.SecondApproveDTO;
import com.mingda.dto.SecondBatchDTO;
import com.mingda.dto.SecondBillDTO;
import com.mingda.dto.TempApprovefileDTO;
import com.mingda.dto.TempBillDTO;
import com.mingda.dto.TempDTO;
import com.mingda.dto.TempMonthDTO;
import com.mingda.dto.TempRuleDTO;
import com.mingda.dto.TempSecondDTO;

public interface TempService {

	List<TempDTO> findTempmember(TempDTO tempDTO);

	TempDTO findAddmeberinfo(TempDTO tempDTO);

	TempDTO saveAddApplyInfo(TempDTO tempDTO);

	List<TempDTO> findAddapplys(TempDTO tempDTO);

	void removeAddapply(TempDTO tempDTO);

	List<TempDTO> findAddapprove(String sql, int intValue, String string);

	String getToolsmenu();

	TempDTO findTempmeberinfo(TempDTO tempDTO);

	TempDTO saveTempApplyInfo(TempDTO tempDTO);

	TempDTO saveTempApproveInfo(TempDTO tempDTO);

	List<TempDTO> findTempapprove(String sql, int intValue, String url);

	@SuppressWarnings("rawtypes")
	HashMap findtempmoney(TempDTO tempDTO);

	String findTempStatInfo(String organizationId);

	TempDTO saveTempPerson(TempDTO tempDTO);

	List<TempDTO> findTempperson(String sql, int intValue, String url);

	List<TempMonthDTO> findTempMonths(String organizationId);

	void saveGenBillData(TempMonthDTO tempMonthDTO , String app);

	InputStream findFileStream(TempMonthDTO tempMonthDTO);

	String removeBillMonth(TempMonthDTO tempMonthDTO);

	List<TempBillDTO> findUsedBills(String sql, int intValue, String url);

	List<TempMonthDTO> findTempStat(String organizationId);

	List<TempDTO> findAddmember(TempDTO tempDTO);

	List<TempSecondDTO> findSecnodmembers(String sql, int intValue, String url);

	TempSecondDTO findToplineByOrganno(String organizationId);

	String findSecnodmembersinfo(String sql);

	String saveSecondApproves(TempSecondDTO tempSecondDTO,String app,String minpay);

	List<SecondApproveDTO> findSecondApproves(String sql);

	List<SecondApproveDTO> findSecondApproves(String sql, int intValue,
			String string);

	SecondApproveDTO saveSecondApprove(SecondApproveDTO secondApproveDTO);

	void removeSecondApproves(String organizationId);

	List<SecondBatchDTO> findSecondBatchs(SecondBatchDTO secondBatchDTO);

	String saveSecondbills(String organizationId);

	List<SecondBatchDTO> findSecondBatchs(String organizationId,int year);

	void removeSecondBill(SecondBatchDTO secondBatchDTO);

	InputStream findFileStreamSecond(SecondBatchDTO secondBatchDTO);

	List<SecondBatchDTO> findSecondBatchByOrgID(String organizationId);

	List<SecondBillDTO> findSecondBills(String sql, int intValue, String string);

	List<TempDTO> findAftermember(TempDTO tempDTO);

	TempDTO findAftermeberinfo(TempDTO tempDTO);

	TempDTO saveAfterApplyInfo(TempDTO tempDTO);

	void removeAfterapply(TempDTO tempDTO);

	List<TempDTO> findAfterapplys(TempDTO tempDTO);

	List<TempDTO> findAfterapprove(String sql, int intValue, String string);

	List<TempDTO> findAfterapprovedone(String sql, int intValue, String string);

	TempDTO saveAfteryn(TempDTO tempDTO);

	String findMaStatInfo(String organizationId);

	List<TempDTO> findMaapprove(String sql, int intValue, String string);

	List<TempMonthDTO> findMaMonths(String organizationId);

	String removeMaBillMonth(TempMonthDTO tempMonthDTO);

	void saveGenMaBillData(TempMonthDTO tempMonthDTO, String app);

	List<TempBillDTO> findUsedMaBills(String sql, int intValue, String string);

	List<TempMonthDTO> findMaStat(String oid);

	InputStream findMaFileStream(TempMonthDTO tempMonthDTO);

	public TempRuleDTO saveTempRule(TempRuleDTO tr);

	public TempRuleDTO findTempRuleByOrgid(String organizationId);

	public void saveJzMedicalafterfiles(List<JzMedicalafterfileDTO> files);

	public List<JzMedicalafterfileDTO> findJzMedicalafterfiles(String bizId);

	void delMaFile(String fid);
	
	public void saveTempApprovefiles(List<TempApprovefileDTO> files);

	public List<TempApprovefileDTO> findTempApprovefiles(String bizId);

	void delTaFile(String fid);
	
	void removeSecondAll(String organizationId,int year);
	
	TempMonthDTO findMaBill(TempMonthDTO tempMonthDTO);
	
	TempMonthDTO findtempBill(TempMonthDTO tempMonthDTO);
	
	TempDTO findPayview01(TempDTO tempDTO);
	@SuppressWarnings("rawtypes")
	public HashMap<String, Comparable> findMaMoney(TempDTO tempDTO);
	
	public JzMedicalafterRuleDTO findMedicalafterRule(TempDTO tempDTO);
	
	public List<TempDTO> findPayviews(TempDTO tempDTO);
	
	public int findMaAppCount(TempDTO tempDTO);
	
	public int findTaAppCount(TempDTO tempDTO);

	String findValBiz(TempDTO tempDTO);
	
	public List<TempDTO> findAfterannexadd(String sql, int intValue, String string);
	
	public TempDTO findMAmemberinfo(TempDTO tempDTO);
	
	public List<TempDTO> findTempannexadd(String sql, int intValue, String string);
	
	public TempDTO findTempmemberinfo(TempDTO tempDTO);
	
	public TempDTO isline(TempDTO tempDTO);

	List<DiagnoseTypeDTO> findDiagnoseTypesByOrg(String organizationId);

	List<OutIcdDTO> findOuticdsByOrg(String organizationId);
	
	List<TempDTO> findMaAccounts(String sql, int currentpage,
			String url);
	String findInfo(String sql);
	
	TempDTO findMaByBizId(TempDTO tempDTO);
	
	List<BillDTO> findafteraccounts(TempDTO tempDTO);
	
	List<DeptDTO> findMaHospitalNameById(String organizationId);
	
	int findTempAppCounts(String sql);
	
	void upTempApp(String sql);
	
	public TempDTO upTempBack(TempDTO tempDTO);
	
	public String findSSN(TempDTO tempDTO);
	
	public String getassisttext(String ASSIST_TYPE,String DS);
	
	public String getTicketNo();
	
	public JzMedicalafterBillDTO saveJzMedicalafterBill(JzMedicalafterBillDTO jmbDTO);

}
