package com.mingda.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mingda.common.Pager;
import com.mingda.dao.ChronicBillDAO;
import com.mingda.dao.ChronicBizDAO;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dto.BillInfoDTO;
import com.mingda.dto.ChronicBizDTO;
import com.mingda.dto.ChronicCheckDTO;
import com.mingda.model.ChronicBill;
import com.mingda.model.ChronicBillExample;
import com.mingda.model.ChronicBiz;

public class ChronicBizServiceImpl implements ChronicBizService{
	private Pager pager;
	private ExtendsDAO extendsDAO;
	private ChronicBillDAO chronicBillDAO;
	private ChronicBizDAO chronicBizDAO;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ChronicCheckDTO> findChronicMember(String sql, int currentpage,
			String url) {
		List<ChronicCheckDTO> list = new ArrayList<ChronicCheckDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			ChronicCheckDTO e = new ChronicCheckDTO();
			String MEMBERNAME = (String) s.get("MEMBERNAME");
			e.setMembername(MEMBERNAME);
			String PAPERID = (String) s.get("PAPERID");
			e.setPaperid(PAPERID);
			String SSN = (String) s.get("SSN");
			e.setSsn(SSN);
			String FAMILYID = (String) s.get("FAMILYID");
			if (null != FAMILYID) {
				e.setFamilyId(new BigDecimal(FAMILYID).intValue());
			}
			String FAMILYNO = (String) s.get("FAMILYNO");
			e.setFamilyno(FAMILYNO);
			String DS = (String) s.get("DS");
			e.setDs(DS);
			String SALVATION_ID = (String) s.get("SALVATION_ID");
			e.setSalvationId(SALVATION_ID);
			String ASSIST_TYPE = (String) s.get("ASSIST_TYPE");
			e.setAssisType(ASSIST_TYPE);
			String MEMBERID = (String) s.get("MEMBER_ID");
			e.setMemberId(MEMBERID);
			String MEMBER_TYPE = (String) s.get("MEMBER_TYPE");
			if (null != MEMBER_TYPE) {
				e.setMemberType(MEMBER_TYPE);
				if ("1".equals(MEMBER_TYPE)) {
					e.setMemberTypeVal("城市");
				}
				if ("2".equals(MEMBER_TYPE)) {
					e.setMemberTypeVal("农村");
				}
			}
			list.add(e);
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BillInfoDTO findBillInfo(String memberId,String memberType) {
		BillInfoDTO billInfo = new BillInfoDTO();
		String sql = "select * "
					+ "  from chronic_bill b "
					+ " where b.chronicbill_id in "
					+ "       (select max(bill.chronicbill_id) "
					+ "          from chronic_bill bill "
					+ "         where bill.optsts = '1' "
					+ "         and bill.member_id = '"+ memberId +"' "
					+ "         and bill.member_type = '"+ memberType + "' "
					+ "         group by bill.MEMBER_ID, bill.member_type) "
					+ "   and b.member_id = '"+ memberId +"' "
					+ "   and b.member_type = '"+ memberType +"' ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			billInfo.setMemberId(s.get("MEMBER_ID").toString());
			billInfo.setMemberType(s.get("MEMBER_TYPE").toString());
			BigDecimal balance = (BigDecimal) s.get("BALANCE");
			billInfo.setBalance(balance);
			BigDecimal bizid = (BigDecimal) s.get("BIZID");
			billInfo.setBizId(bizid);
			BigDecimal payout = (BigDecimal) s.get("PAYOUT");
			billInfo.setPayout(payout);
			BigDecimal chronicbillid = (BigDecimal) s.get("CHRONICBILL_ID");
			billInfo.setChronicbillId(chronicbillid.intValue());
		}
		return billInfo;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ChronicCheckDTO findBizInfo(String memberId,String memberType){
		ChronicCheckDTO chronicCheckDTO = new ChronicCheckDTO();
		String sql = "select * from mv_chronic mv where mv.member_type='"+ memberType +"' and mv.member_id='"+ memberId +"' and mv.sts='1' and mv.salstate ='1' ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			chronicCheckDTO.setMembername(s.get("MEMBERNAME").toString());
			chronicCheckDTO.setPaperid(s.get("PAPERID").toString());
			chronicCheckDTO.setFamilyno(s.get("FAMILYNO").toString());
		}
		return chronicCheckDTO;
	}
	
	public int chronicbizsave(BillInfoDTO billInfoDTO,ChronicBizDTO chronicBillDTO) {
		int return_i = 0;
		//
		String memberType = billInfoDTO.getMemberType();
		String memberId = billInfoDTO.getMemberId();
		BigDecimal paytotal = chronicBillDTO.getPayTotal();
		BigDecimal payassist = chronicBillDTO.getPayAssist();
		BigDecimal payself = chronicBillDTO.getPaySelf();
		BigDecimal paymedbalance = chronicBillDTO.getPayMedbalance();
		Integer userid = chronicBillDTO.getUserId();
		Date opertime1 = chronicBillDTO.getOperTime();
		Date createtime = chronicBillDTO.getCreatTime();
		String sts = chronicBillDTO.getSts();
		String hospitalname = chronicBillDTO.getHospitalName();
		String remark = chronicBillDTO.getRemark();
		String icard = chronicBillDTO.getIcard();
		String membername = chronicBillDTO.getMemvername();
		String familyno = chronicBillDTO.getFamilyNo();
		ChronicBiz record1 = new ChronicBiz();
		record1.setSeq(new BigDecimal("1"));
		record1.setPayFlag("1");
		record1.setPayTotal(paytotal);
		record1.setPayAssist(payassist);
		record1.setPaySelf(payself);
		record1.setPayMedbalance(paymedbalance);
		record1.setUserId(userid);
		record1.setOperTime(opertime1);
		record1.setCreatetime(createtime);
		record1.setSts(sts);
		record1.setHospitalName(hospitalname);
		record1.setRemark(remark);
		record1.setMembername(membername);
		record1.setFamilyno(familyno);
		record1.setIcard(icard);
		record1.setMemberId(memberId);
		record1.setMemberType(memberType);
		int biz_id = chronicBizDAO.insert(record1);
		//
		if(biz_id>0){
			String subject = billInfoDTO.getSubject();
			BigDecimal income = billInfoDTO.getIncome();
			BigDecimal payout = billInfoDTO.getPayout();
			BigDecimal balance = billInfoDTO.getBalance();
			Date opertime = billInfoDTO.getOpertime();
			String optsts = billInfoDTO.getOptsts();
			ChronicBill record = new ChronicBill();
			record.setMemberId(memberId);
			record.setMemberType(memberType);
			record.setSubject(subject);
			record.setIncome(income);
			record.setPayout(payout);
			record.setBalance(balance);
			record.setOpertime(opertime);
			record.setBizid(biz_id);
			record.setOptsts(optsts);
			int return_bill = chronicBillDAO.insert(record);
			if(return_bill>0){
				return_i = 1;
			}
		}
		return return_i;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ChronicBizDTO> findChronicMembers(String sql, int currentpage,
			String url) {
		List<ChronicBizDTO> list = new ArrayList<ChronicBizDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			ChronicBizDTO e = new ChronicBizDTO();
			String MEMBERNAME = (String) s.get("MEMBERNAME");
			e.setMemvername(MEMBERNAME);
			String ICARD = (String) s.get("ICARD");
			e.setIcard(ICARD);
			String FAMILYNO = (String) s.get("FAMILYNO");
			e.setFamilyNo(FAMILYNO);
			String MEMBERID = (String) s.get("MEMBER_ID");
			e.setMemberId(MEMBERID);
			String MEMBER_TYPE = (String) s.get("MEMBER_TYPE");
			e.setMemberType(MEMBER_TYPE);
			String HOSPITAL_NAME = (String) s.get("HOSPITAL_NAME");
			e.setHospitalName(HOSPITAL_NAME);
			BigDecimal PAY_MEDBALANCE = (BigDecimal) s.get("PAY_MEDBALANCE");
			e.setPayMedbalance(PAY_MEDBALANCE);
			BigDecimal PAY_TOTAL = (BigDecimal) s.get("PAY_TOTAL");
			e.setPayTotal(PAY_TOTAL);
			BigDecimal PAY_ASSIST = (BigDecimal) s.get("PAY_ASSIST");
			e.setPayAssist(PAY_ASSIST);
			BigDecimal PAY_SELF = (BigDecimal) s.get("PAY_SELF");
			e.setPaySelf(PAY_SELF);
			BigDecimal BIZ_ID = (BigDecimal) s.get("BIZ_ID");
			e.setBizId(BIZ_ID.intValue());
			list.add(e);
		}
		return list;
	}
	
	public int chronicbizback(ChronicBizDTO chronicBizDTO,BillInfoDTO billInfoDTO_i,BillInfoDTO billInfoDTO_u,BillInfoDTO billInfoDTO_u1,String type){
		int return_u = 0;
		//BIZ表修改为取消状态
		ChronicBiz biz = new ChronicBiz();
		biz.setBizId(chronicBizDTO.getBizId());
		biz.setSts(chronicBizDTO.getSts());
		int i=chronicBizDAO.updateByPrimaryKeySelective(biz);
		//BILL表的插入
		ChronicBill bill_i= new ChronicBill();
		bill_i.setMemberId(billInfoDTO_i.getMemberId());
		bill_i.setMemberType(billInfoDTO_i.getMemberType());
		bill_i.setSubject(billInfoDTO_i.getSubject());
		bill_i.setIncome(billInfoDTO_i.getIncome());
		bill_i.setPayout(billInfoDTO_i.getPayout());
		bill_i.setBalance(billInfoDTO_i.getBalance());
		bill_i.setOpertime(billInfoDTO_i.getOpertime());
		bill_i.setBizid(billInfoDTO_i.getBizId().intValue());
		bill_i.setOptsts(billInfoDTO_i.getOptsts());
		int i1=chronicBillDAO.insert(bill_i);
		//BILL表的修改
		ChronicBill bill_u = new ChronicBill();
		if("1".equals(type)){
			bill_u.setOptsts(billInfoDTO_u.getOptsts());
			bill_u.setChronicbillId(billInfoDTO_u.getChronicbillId());
			int i2=chronicBillDAO.updateByPrimaryKeySelective(bill_u);
			if(i>0 && i1>0 &&i2>0){
				return_u=1;
			}
		}else if("2".equals(type)){
			bill_u.setOptsts(billInfoDTO_u.getOptsts());
			ChronicBillExample example = new ChronicBillExample();
			example.createCriteria().andBizidEqualTo(billInfoDTO_u.getBizId().intValue());
			int i3=chronicBillDAO.updateByExampleSelective(bill_u, example);
			//
			ChronicBill bill_u1 = new ChronicBill();
			bill_u1.setBalance(billInfoDTO_u1.getBalance());
			bill_u1.setChronicbillId(billInfoDTO_u1.getChronicbillId());
			int i4=chronicBillDAO.updateByPrimaryKeySelective(bill_u1);
			if(i>0 && i1>0 && i3>0 & i4>0){
				return_u=1;
			}
		}
		return return_u;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BillInfoDTO findBill(String bizid){
		BillInfoDTO billInfoDTO = new BillInfoDTO();
		String sql = "select * "
			+ "  from chronic_bill b "
			+ " where b.BIZID = " + bizid;
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			billInfoDTO.setMemberId(s.get("MEMBER_ID").toString());
			billInfoDTO.setMemberType(s.get("MEMBER_TYPE").toString());
			BigDecimal balance = (BigDecimal) s.get("BALANCE");
			billInfoDTO.setBalance(balance);
			BigDecimal payout = (BigDecimal) s.get("PAYOUT");
			billInfoDTO.setPayout(payout);
		}
		return billInfoDTO;
	}
	
	public String getToolsmenu() {
		return pager.getToolsmenu();
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public ChronicBillDAO getChronicBillDAO() {
		return chronicBillDAO;
	}

	public void setChronicBillDAO(ChronicBillDAO chronicBillDAO) {
		this.chronicBillDAO = chronicBillDAO;
	}

	public ChronicBizDAO getChronicBizDAO() {
		return chronicBizDAO;
	}

	public void setChronicBizDAO(ChronicBizDAO chronicBizDAO) {
		this.chronicBizDAO = chronicBizDAO;
	}
}