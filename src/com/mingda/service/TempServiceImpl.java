package com.mingda.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.mingda.common.Pager;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.JzAddassistdataDAO;
import com.mingda.dao.JzMedicalafterBillDAO;
import com.mingda.dao.JzMedicalafterDAO;
import com.mingda.dao.JzMedicalafterfileDAO;
import com.mingda.dao.JzYearDAO;
import com.mingda.dao.MaBillDAO;
import com.mingda.dao.MaMonthDAO;
import com.mingda.dao.MemberBaseinfoviewDAO;
import com.mingda.dao.Payview01DAO;
import com.mingda.dao.SecondApproveDAO;
import com.mingda.dao.SecondBatchDAO;
import com.mingda.dao.SecondBillDAO;
import com.mingda.dao.TempApproveDAO;
import com.mingda.dao.TempApprovefileDAO;
import com.mingda.dao.TempBillDAO;
import com.mingda.dao.TempCalcRuleDAO;
import com.mingda.dao.TempMonthDAO;
import com.mingda.dao.TempPersonDAO;
import com.mingda.dto.BillDTO;
import com.mingda.dto.DeptDTO;
import com.mingda.dto.DiagnoseTypeDTO;
import com.mingda.dto.JzMedicalafterBillDTO;
import com.mingda.dto.JzMedicalafterRuleDTO;
import com.mingda.dto.JzMedicalafterfileDTO;
import com.mingda.dto.JzYearDTO;
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
import com.mingda.model.JzAddassistdata;
import com.mingda.model.JzMedicalafter;
import com.mingda.model.JzMedicalafterBill;
import com.mingda.model.JzMedicalafterBillExample;
import com.mingda.model.JzMedicalafterfile;
import com.mingda.model.JzMedicalafterfileExample;
import com.mingda.model.JzYear;
import com.mingda.model.JzYearExample;
import com.mingda.model.MaMonth;
import com.mingda.model.MemberBaseinfoview;
import com.mingda.model.MemberBaseinfoviewExample;
import com.mingda.model.MemberBaseinfoviewExample.Criteria;
import com.mingda.model.Payview01;
import com.mingda.model.Payview01Example;
import com.mingda.model.SecondApprove;
import com.mingda.model.SecondApproveExample;
import com.mingda.model.SecondBatch;
import com.mingda.model.SecondBillExample;
import com.mingda.model.TempApprove;
import com.mingda.model.TempApprovefile;
import com.mingda.model.TempApprovefileExample;
import com.mingda.model.TempCalcRule;
import com.mingda.model.TempCalcRuleExample;
import com.mingda.model.TempCalcRuleSpe;
import com.mingda.model.TempMonth;
import com.mingda.model.TempPerson;
import com.mingda.model.TempPersonExample;

public class TempServiceImpl implements TempService {
	static Logger log = Logger.getLogger(TempServiceImpl.class);
	private ExtendsDAO extendsDAO;
	private MemberBaseinfoviewDAO memberBaseinfoviewDAO;
	private JzAddassistdataDAO jzAddassistdataDAO;
	private TempApproveDAO tempApproveDAO;
	private TempBillDAO tempBillDAO;
	private MaBillDAO maBillDAO;
	private TempPersonDAO tempPersonDAO;
	private TempMonthDAO tempMonthDAO;
	private MaMonthDAO maMonthDAO;
	private Pager pager;
	private SecondApproveDAO secondApproveDAO;
	private SecondBatchDAO secondBatchDAO;
	private SecondBillDAO secondBillDAO;
	private JzMedicalafterDAO jzMedicalafterDAO;
	private TempCalcRuleDAO tempCalcRuleDAO;
	private JzMedicalafterfileDAO jzMedicalafterfileDAO;
	private TempApprovefileDAO tempApprovefileDAO;
	private Payview01DAO payview01DAO;
	private JzMedicalafterBillDAO jzMedicalafterBillDAO;
	private JzYearDAO jzYearDAO;

	public List<TempDTO> findAddmember(TempDTO tempDTO) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		MemberBaseinfoviewExample example = new MemberBaseinfoviewExample();
		Criteria c1 = example.createCriteria();
		c1.andPaperidEqualTo(tempDTO.getPaperid());
		Criteria c2 = example.createCriteria();
		c2.andFamilynoEqualTo(tempDTO.getFamilyno());
		example.or(c1);
		example.or(c2);
		List<MemberBaseinfoview> ms = memberBaseinfoviewDAO
				.selectByExample(example);
		for (MemberBaseinfoview m : ms) {
			tempDTO = new TempDTO();
			tempDTO.setMembername(m.getMembername());
			tempDTO.setMemberType(m.getDs());
			tempDTO.setAddress(m.getAddress());
			tempDTO.setSex(m.getSex());
			tempDTO.setPaperid(m.getPaperid());
			tempDTO.setRelmaster(m.getRelmaster());
			tempDTO.setFamilyno(m.getFamilyno());
			tempDTO.setMastername(m.getMastername());
			tempDTO.setOrganizationId(m.getFamilyno().substring(1, 10));
			tempDTO.setMemberId(m.getMemberId());
			tempDTO.setMemberType(m.getDs());
			list.add(tempDTO);
		}
		return list;
	}

	@SuppressWarnings({})
	public List<TempDTO> findTempmember(TempDTO tempDTO) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		TempPersonExample example = new TempPersonExample();
		example.createCriteria().andPaperidEqualTo(tempDTO.getPaperid())
				.andOrganizationidLike(tempDTO.getOrganizationId() + "%");
		List<TempPerson> tps = tempPersonDAO.selectByExample(example);
		if (tps != null && tps.size() > 0) {

		} else {
			String sql = "insert into temp_person "
					+ " (member_id,  member_type, membername, paperid,  safesort, linkmode, address, "
					+ "  birthday, sex,  organizationid,  status, familyno, relmaster, ssn, nation, personstate, assist_type, assist_typex) "
					+ " select mem.member_id, mem.ds,  mem.membername,  mem.paperid, "
					+ " med.medicare_type, mem.linkmode, mem.address,  mem.birthday, "
					+ "  mem.sex, substr(mem.familyno, 1, 10), "
					+ "  '1', mem.familyno, mem.relmaster,  mem.ssn, mem.nation, mem.personstate, mem.assist_type, mem.assist_typex "
					+ "  from MEMBER_BASEINFOVIEW02 mem "
					+ " left join member_medicare med "
					+ " on med.member_id=mem.member_id and med.member_type=med.member_type "
					+ " where mem.paperid = '" + tempDTO.getPaperid()
					+ "' and mem.familyno like '" + tempDTO.getOrganizationId()
					+ "%'";
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("executsql", sql);
			extendsDAO.updateAll(param);
		}
		tps = tempPersonDAO.selectByExample(example);
		for (TempPerson s : tps) {
			TempDTO e = new TempDTO();
			e.setMembername(s.getMembername());
			e.setMemberId(s.getMemberId().toString());
			e.setMemberType(s.getMemberType().toString());
			e.setSex(s.getSex());
			e.setPaperid(s.getPaperid());
			e.setAddress(s.getAddress());
			e.setFamilyno(s.getFamilyno());
			e.setRelmaster(s.getRelmaster());
			e.setPersonstate(s.getPersonstate());
			e.setAssistTypex(s.getAssistTypex());
			if (!"".equals(s.getAssistType()) && null != s.getAssistType()) {
				e.setA1(s.getAssistType().substring(0, 1));
				e.setA2(s.getAssistType().substring(1, 2));
				e.setA3(s.getAssistType().substring(2, 3));
				e.setA4(s.getAssistType().substring(3, 4));
				e.setA5(s.getAssistType().substring(4, 5));
			}
			if (!"".equals(s.getAssistTypex()) && null != s.getAssistTypex()) {
				e.setA6(s.getAssistTypex().substring(0, 1));
				e.setA7(s.getAssistTypex().substring(1, 2));
				e.setA8(s.getAssistTypex().substring(2, 3));
				e.setA9(s.getAssistTypex().substring(3, 4));
				e.setA10(s.getAssistTypex().substring(4, 5));
				e.setA11(s.getAssistTypex().substring(5, 6));
			}
			e.setAssistTypeTxt(genAssistype(e));
			list.add(e);
		}
		return list;
	}

	private String genAssistype(TempDTO e) {
		String tempstr = "";
		if ("1".equals(e.getMemberType())) {
			if ("1".equals(e.getA1())) {
				tempstr = tempstr + "固定保障,";

			}
			if ("1".equals(e.getA2())) {
				tempstr = tempstr + "分类施保,";

			}
			if ("1".equals(e.getA3())) {
				tempstr = tempstr + "三无,";

			}
			if ("1".equals(e.getA4())) {
				tempstr = tempstr + "五保户,";

			}
			if ("1".equals(e.getA5())) {
				tempstr = tempstr + "优抚对象,";

			}
			if ("1".equals(e.getA6())) {
				tempstr = tempstr + "孤儿,";
			}
			if ("0".equals(e.getA1()) && "0".equals(e.getA2())
					&& "0".equals(e.getA3()) && "0".equals(e.getA4())
					&& "0".equals(e.getA5())
					&& "000000".equals(e.getAssistTypex())) {
				tempstr = "普通居民";
			}
		} else if ("2".equals(e.getMemberType())) {
			if ("2".equals(e.getA1())) {
				tempstr = tempstr + "一般户,";

			}
			if ("1".equals(e.getA2())) {
				tempstr = tempstr + "重点户,";

			}
			if ("1".equals(e.getA3())) {
				tempstr = tempstr + "三无,";

			}
			if ("1".equals(e.getA4())) {
				tempstr = tempstr + "五保户,";

			}
			if ("1".equals(e.getA5())) {
				tempstr = tempstr + "优抚对象,";

			}
			if ("1".equals(e.getA6())) {
				tempstr = tempstr + "孤儿,";
			}
			if ("0".equals(e.getA1()) && "0".equals(e.getA2())
					&& "0".equals(e.getA3()) && "0".equals(e.getA4())
					&& "0".equals(e.getA5())
					&& "000000".equals(e.getAssistTypex())) {
				tempstr = "普通居民";
			}
		}
		return tempstr;
	}

	/*
	 * private void genSaltypeval(TempDTO e) { String tempstr = ""; if
	 * ("1".equals(e.getMemberType())) { if ("1".equals(e.getA1())) { tempstr =
	 * tempstr + "固定保障,";
	 * 
	 * } if ("1".equals(e.getA2())) { tempstr = tempstr + "分类施保,";
	 * 
	 * } if ("1".equals(e.getA3())) { tempstr = tempstr + "三无,";
	 * 
	 * } if ("1".equals(e.getA4())) { tempstr = tempstr + "五保户,";
	 * 
	 * } if ("1".equals(e.getA5())) { tempstr = tempstr + "优抚对象,";
	 * 
	 * } } else if ("2".equals(e.getMemberType())) { if ("2".equals(e.getA1()))
	 * { tempstr = tempstr + "一般户,";
	 * 
	 * } if ("1".equals(e.getA2())) { tempstr = tempstr + "重点户,";
	 * 
	 * } if ("1".equals(e.getA3())) { tempstr = tempstr + "三无,";
	 * 
	 * } if ("1".equals(e.getA4())) { tempstr = tempstr + "五保户,";
	 * 
	 * } if ("1".equals(e.getA5())) { tempstr = tempstr + "优抚对象,";
	 * 
	 * } } e.setSaltypeval(tempstr); }
	 */

	public TempDTO findAddmeberinfo(TempDTO tempDTO) {
		MemberBaseinfoviewExample example = new MemberBaseinfoviewExample();
		example.createCriteria().andMemberIdEqualTo(tempDTO.getMemberId())
				.andDsEqualTo(tempDTO.getMemberType());
		List<MemberBaseinfoview> ms = memberBaseinfoviewDAO
				.selectByExample(example);
		if (null != ms && ms.size() > 0) {
			MemberBaseinfoview m = ms.get(0);
			tempDTO.setMembername(m.getMembername());
			tempDTO.setMemberType(m.getDs());
			tempDTO.setAddress(m.getAddress());
			tempDTO.setSex(m.getSex());
			tempDTO.setPaperid(m.getPaperid());
			tempDTO.setRelmaster(m.getRelmaster());
			tempDTO.setFamilyno(m.getFamilyno());
			tempDTO.setMastername(m.getMastername());
			tempDTO.setOrganizationId(m.getFamilyno().substring(1, 10));
		}
		if (null != tempDTO.getApproveId()) {
			JzAddassistdata a = jzAddassistdataDAO.selectByPrimaryKey(tempDTO
					.getApproveId().intValue());
			tempDTO.setBegintime(a.getBeginTime());
			tempDTO.setEndtime(a.getEndTime());
			tempDTO.setHospitalname(a.getHospitalName());
			tempDTO.setPayAssist(a.getPayAssist());
			tempDTO.setPayMedicare(a.getPayMedicare());
			tempDTO.setPayOutmedicare(a.getPayOutmedicare());
			tempDTO.setPayTotal(a.getPayTotal());
			tempDTO.setAssistype(a.getAssistType());
			tempDTO.setInhospitalsicken(a.getDiagnoseName());
		}
		return tempDTO;
	}

	public TempDTO saveAddApplyInfo(TempDTO tempDTO) {
		JzAddassistdata record = new JzAddassistdata();
		if (null == tempDTO.getApproveId()) {
			record.setAssistTime(new Date());
			record.setBeginTime(tempDTO.getBegintime());
			record.setEndTime(tempDTO.getEndtime());
			record.setBizStatus("1");
			record.setDiagnoseName(tempDTO.getInhospitalsicken());
			record.setHospitalName(tempDTO.getHospitalname());
			record.setFamilyNo(tempDTO.getFamilyno());
			record.setFamilyAddress(tempDTO.getAddress());
			record.setIdCard(tempDTO.getPaperid());
			record.setMemberId(tempDTO.getMemberId());
			record.setMemberType(tempDTO.getMemberType());
			record.setSex(tempDTO.getSex());
			record.setName(tempDTO.getMembername());
			record.setOperTime(new Date());
			record.setOrganizationId(tempDTO.getOrganizationId());
			record.setPayAssist(tempDTO.getPayAssist());
			record.setPayMedicare(tempDTO.getPayMedicare());
			record.setPayOutmedicare(tempDTO.getPayOutmedicare());
			record.setPayTotal(tempDTO.getPayTotal());
			record.setAssistType(tempDTO.getAssistype());
			record.setDiagnoseName(tempDTO.getInhospitalsicken());
			Integer id = jzAddassistdataDAO.insertSelective(record);
			tempDTO.setApproveId(id.longValue());
		} else {
			record.setAssistTime(new Date());
			record.setBeginTime(tempDTO.getBegintime());
			record.setEndTime(tempDTO.getEndtime());
			record.setBizStatus("1");
			record.setDiagnoseName(tempDTO.getInhospitalsicken());
			record.setHospitalName(tempDTO.getHospitalname());
			record.setFamilyNo(tempDTO.getFamilyno());
			record.setFamilyAddress(tempDTO.getAddress());
			record.setIdCard(tempDTO.getPaperid());
			record.setMemberId(tempDTO.getMemberId());
			record.setMemberType(tempDTO.getMemberType());
			record.setSex(tempDTO.getSex());
			record.setName(tempDTO.getMembername());
			record.setOperTime(new Date());
			record.setOrganizationId(tempDTO.getOrganizationId());
			record.setPayAssist(tempDTO.getPayAssist());
			record.setPayMedicare(tempDTO.getPayMedicare());
			record.setPayOutmedicare(tempDTO.getPayOutmedicare());
			record.setPayTotal(tempDTO.getPayTotal());
			record.setAssistType(tempDTO.getAssistype());
			record.setDiagnoseName(tempDTO.getInhospitalsicken());
			record.setBizId(tempDTO.getApproveId().intValue());
			jzAddassistdataDAO.updateByPrimaryKeySelective(record);
		}
		return tempDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TempDTO> findAddapplys(TempDTO tempDTO) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		String sql = "select t.biz_id, mem.membername, mem.paperid, mem.familyno, "
				+ "t.pay_total, t.pay_medicare, t.pay_outmedicare, t.pay_assist, t.biz_status,"
				+ "t.assist_type, t.diagnose_name, t.hospital_name, t.assist_time , t.member_id, t.member_type "
				+ " from jz_addassistdata t, MEMBER_BASEINFOVIEW02 mem "
				+ "where t.member_id = mem.member_id  and t.member_type = mem.ds  and t.member_id='"
				+ tempDTO.getMemberId()
				+ "' and t.member_type='"
				+ tempDTO.getMemberType() + "'  order by t.assist_time";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		// BIZ_ID MEMBERNAME PAPERID FAMILYNO PAY_TOTAL PAY_MEDICARE
		// PAY_OUTMEDICARE PAY_ASSIST BIZ_STATUS ASSIST_TYPE DIAGNOSE_NAME
		// HOSPITAL_NAME ASSIST_TIME MEMBER_ID MEMBER_TYPE

		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setApplytime((Date) s.get("ASSIST_TIME"));
			e.setInhospitalsicken((String) s.get("DIAGNOSE_NAME"));
			e.setHospitalname((String) s.get("HOSPITAL_NAME"));
			e.setApproveId(new Long(s.get("BIZ_ID").toString()));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			list.add(e);
		}
		return list;
	}

	public void removeAddapply(TempDTO tempDTO) {
		Integer bizId = new Integer(tempDTO.getApproveId().intValue());
		jzAddassistdataDAO.deleteByPrimaryKey(bizId);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempDTO> findAddapprove(String sql, int currentpage, String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// MEMBERNAME PAPERID FAMILYNO MEMBER_ID MEMBER_TYPE CS PAY_TOTAL
		// PAY_MEDICARE PAY_OUTMEDICARE PAY_ASSIST
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			// e.setApproveId(new Long(s.get("BIZ_ID").toString()));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setCs((BigDecimal) s.get("CS"));
			list.add(e);
		}
		return list;
	}

	public List<TempDTO> findAftermember(TempDTO tempDTO) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		MemberBaseinfoviewExample example = new MemberBaseinfoviewExample();
		String idcard = tempDTO.getPaperid();
		if(idcard.length()==15){
			idcard = this.idcard15to18(idcard);
		}
		Criteria c1 = example.createCriteria();
		c1.andPaperid18EqualTo(idcard).andFamilynoLike(
				tempDTO.getOrganizationId() + "%");
		Criteria c2 = example.createCriteria();
		c2.andFamilynoEqualTo(tempDTO.getFamilyno());
		example.or(c1);
		example.or(c2);
		List<MemberBaseinfoview> ms = memberBaseinfoviewDAO
				.selectByExample(example);
		for (MemberBaseinfoview m : ms) {
			tempDTO = new TempDTO();
			tempDTO.setMembername(m.getMembername());
			tempDTO.setMemberType(m.getDs());
			tempDTO.setAddress(m.getAddress());
			tempDTO.setSex(m.getSex());
			tempDTO.setPaperid(m.getPaperid18());
			tempDTO.setRelmaster(m.getRelmaster());
			tempDTO.setFamilyno(m.getFamilyno());
			tempDTO.setMastername(m.getMastername());
			tempDTO.setOrganizationId(m.getFamilyno().substring(1, 10));
			tempDTO.setMemberId(m.getMemberId());
			tempDTO.setPersonstate(m.getPersonstate());
			tempDTO.setAssistype(m.getAssistType());
			tempDTO.setAssistTypex(m.getAssistTypex());
			if (!"".equals(m.getAssistType()) && null != m.getAssistType()) {
				tempDTO.setA1(m.getAssistType().substring(0, 1));
				tempDTO.setA2(m.getAssistType().substring(1, 2));
				tempDTO.setA3(m.getAssistType().substring(2, 3));
				tempDTO.setA4(m.getAssistType().substring(3, 4));
				tempDTO.setA5(m.getAssistType().substring(4, 5));
			}
			if (!"".equals(m.getAssistTypex()) && null != m.getAssistTypex()) {
				tempDTO.setA6(m.getAssistTypex().substring(0, 1));
				tempDTO.setA7(m.getAssistTypex().substring(1, 2));
				tempDTO.setA8(m.getAssistTypex().substring(2, 3));
				tempDTO.setA9(m.getAssistTypex().substring(3, 4));
				tempDTO.setA10(m.getAssistTypex().substring(4, 5));
				tempDTO.setA11(m.getAssistTypex().substring(5, 6));

			}
			tempDTO.setAssistTypeTxt(genAssistype(tempDTO));
			list.add(tempDTO);
		}
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public String idcard15to18(String idcard){
		String sql=" select IDCARD15TO18('"+idcard+"') as idcard from dual ";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if(rs.size()>0){
			HashMap s = rs.get(0);
			idcard = (String)s.get("IDCARD");
		}
		return idcard;
	}

	@Override
	public TempDTO findAftermeberinfo(TempDTO tempDTO) {
		MemberBaseinfoviewExample example = new MemberBaseinfoviewExample();
		example.createCriteria().andMemberIdEqualTo(tempDTO.getMemberId())
				.andDsEqualTo(tempDTO.getMemberType());
		List<MemberBaseinfoview> ms = memberBaseinfoviewDAO
				.selectByExample(example);
		if (null != ms && ms.size() > 0) {
			MemberBaseinfoview m = ms.get(0);
			tempDTO.setMembername(m.getMembername());
			tempDTO.setMemberType(m.getDs());
			tempDTO.setAddress(m.getAddress());
			tempDTO.setSex(m.getSex());
			tempDTO.setPaperid(m.getPaperid18());
			tempDTO.setRelmaster(m.getRelmaster());
			tempDTO.setFamilyno(m.getFamilyno());
			tempDTO.setMastername(m.getMastername());
			tempDTO.setOrganizationId(m.getFamilyno().substring(0, 10));
			tempDTO.setPersonstate(m.getPersonstate());
			tempDTO.setAssistTypeM(m.getAssistType());
			tempDTO.setAssistType(m.getAssistType());
			tempDTO.setAssistTypex(m.getAssistTypex());
			if (!"".equals(m.getAssistType()) && null != m.getAssistType()) {
				tempDTO.setA1(m.getAssistType().substring(0, 1));
				tempDTO.setA2(m.getAssistType().substring(1, 2));
				tempDTO.setA3(m.getAssistType().substring(2, 3));
				tempDTO.setA4(m.getAssistType().substring(3, 4));
				tempDTO.setA5(m.getAssistType().substring(4, 5));
			}
			if (!"".equals(m.getAssistTypex()) && null != m.getAssistTypex()) {
				tempDTO.setA6(m.getAssistTypex().substring(0, 1));
				tempDTO.setA7(m.getAssistTypex().substring(1, 2));
				tempDTO.setA8(m.getAssistTypex().substring(2, 3));
				tempDTO.setA9(m.getAssistTypex().substring(3, 4));
				tempDTO.setA10(m.getAssistTypex().substring(4, 5));
				tempDTO.setA11(m.getAssistTypex().substring(5, 6));
			}
			tempDTO.setAssistTypeTxt(genAssistype(tempDTO));
			
			// 查询参保编号
			String medicaretype = this.findSSN(tempDTO).getMedicareType();
			String ssn = "";
			if("2".equals(medicaretype)){
				ssn = m.getPaperid();
			}else{
				ssn = this.findSSN(tempDTO).getSsn();
			}

			tempDTO.setMedicareType(medicaretype);
			tempDTO.setSsn(ssn);
		}
		if (null != tempDTO.getApproveId()) {
			JzMedicalafter a = jzMedicalafterDAO.selectByPrimaryKey(tempDTO
					.getApproveId().intValue());
			tempDTO.setBegintime(a.getBeginTime());
			tempDTO.setEndtime(a.getEndTime());
			tempDTO.setHospitalname(a.getHospitalName());
			tempDTO.setPayAssist(a.getPayAssist());
			tempDTO.setPayMedicare(a.getPayMedicare());
			tempDTO.setPayOutmedicare(a.getPayOutmedicare());
			tempDTO.setPayTotal(a.getPayTotal());
			tempDTO.setAssistype(a.getAssistType());
			tempDTO.setInhospitalsicken(a.getDiagnoseName());
			tempDTO.setHospitaltype(a.getHospitalType());
			tempDTO.setPaySumAssistIn(a.getPaySumassistIn());
			tempDTO.setPaySumAssistOut(a.getPaySumassistOut());
			tempDTO.setSumMedicareScope(a.getSumMedicarescope());
			tempDTO.setPayCIAssist(a.getPayCiassist());
			tempDTO.setMedicareType(a.getMedicareType());
			tempDTO.setBizStatus(a.getBizStatus().trim());
			tempDTO.setPaySumAssistScopeIn(a.getSumAssitscope());
			tempDTO.setPayPreSumAssistScopeIn(a.getSumPreAssitscope());
			tempDTO.setInsurance(a.getInsurance());
			tempDTO.setOtherType(a.getOtherType());
			tempDTO.setSsn(a.getSsn());
			tempDTO.setHospitalLocal(a.getHospitalLocal());
			if (null == a.getHospitalLevel()) {
				tempDTO.setHospitalLevel(0);
			} else {
				tempDTO.setHospitalLevel(Integer.valueOf(a.getHospitalLevel()
						.trim()));
			}
			if (null == a.getHospitalId()) {
				tempDTO.setHospitalId(0);
			} else {
				tempDTO.setHospitalId(a.getHospitalId());
			}
			if (null == a.getDiagnoseTypeId()) {
				tempDTO.setDiagnoseTypeId(0);
			} else {
				tempDTO.setDiagnoseTypeId(a.getDiagnoseTypeId());
			}
			if (null == a.getIcdId()) {
				tempDTO.setIcdId(0);
			} else {
				tempDTO.setIcdId(a.getIcdId());
			}
			if (null == a.getSpecbiz()) {
				tempDTO.setSpecBiz(0);
			} else {
				tempDTO.setSpecBiz(Integer.valueOf(a.getSpecbiz().trim()));
			}
			tempDTO.setCalcMsg(a.getCalcmsg());
			if (null == a.getHospitalId()) {
				tempDTO.setHospitalId(0);
			} else {
				tempDTO.setHospitalId(a.getHospitalId());
			}
			if (null == a.getDiagnoseTypeId()) {
				tempDTO.setDiagnoseTypeId(0);
			} else {
				tempDTO.setDiagnoseTypeId(a.getDiagnoseTypeId());
			}
			if (null == a.getIcdId()) {
				tempDTO.setIcdId(0);
			} else {
				tempDTO.setIcdId(a.getIcdId());
			}
			if (null == a.getSpecbiz()) {
				tempDTO.setSpecBiz(0);
			} else {
				tempDTO.setSpecBiz(Integer.parseInt(a.getSpecbiz().trim()));
			}
			tempDTO.setCalcMsg(a.getCalcmsg());
		}
		return tempDTO;
	}

	@Override
	public TempDTO saveAfterApplyInfo(TempDTO tempDTO) {
		JzMedicalafter record = new JzMedicalafter();
		if (null == tempDTO.getApproveId()) {
			record.setApplyTime(new Date());
			record.setAssistTime(new Date());
			record.setBeginTime(tempDTO.getBegintime());
			record.setEndTime(tempDTO.getEndtime());
			if ("220110".equals(tempDTO.getOrg())) {
				record.setBizStatus(tempDTO.getBizStatus());
			} else {
				record.setBizStatus("-1");
			}
			record.setDiagnoseName(tempDTO.getInhospitalsicken());
			record.setHospitalName(tempDTO.getHospitalname());
			record.setFamilyNo(tempDTO.getFamilyno());
			record.setFamilyAddress(tempDTO.getAddress());
			record.setIdCard(tempDTO.getPaperid());
			record.setMemberId(tempDTO.getMemberId());
			record.setMemberType(tempDTO.getMemberType());
			record.setSex(tempDTO.getSex());
			record.setName(tempDTO.getMembername());
			record.setOperTime(new Date());
			record.setOrganizationId(tempDTO.getOrganizationId());
			record.setPayAssist(tempDTO.getPayAssist());
			record.setPayMedicare(tempDTO.getPayMedicare());
			record.setPayOutmedicare(tempDTO.getPayOutmedicare());
			record.setPayTotal(tempDTO.getPayTotal());
			record.setAssistType(tempDTO.getAssistype());
			record.setDiagnoseName(tempDTO.getInhospitalsicken());
			record.setMedicareType(tempDTO.getMedicareType());
			record.setPersonstate(tempDTO.getPersonstate());
			record.setAssistTypeM(tempDTO.getAssistTypeM());
			record.setAssistTypex(tempDTO.getAssistTypex());
			// 大病保险
			record.setPaySumassistIn(tempDTO.getPaySumAssistIn());
			record.setPaySumassistOut(tempDTO.getPaySumAssistOut());
			record.setSumMedicarescope(tempDTO.getSumMedicareScope());
			record.setPayCiassist(tempDTO.getPayCIAssist());
			record.setSumAssitscope(tempDTO.getPaySumAssistScopeIn());
			record.setSumPreAssitscope(tempDTO.getPayPreSumAssistScopeIn());
			//
			record.setHospitalId((int) tempDTO.getHospitalId());
			record.setDiagnoseTypeId(new Integer(tempDTO.getDiagnoseTypeId()));
			record.setIcdId(new Integer(tempDTO.getIcdId()));
			record.setSpecbiz(String.valueOf(tempDTO.getSpecBiz()));
			record.setCalcmsg(tempDTO.getCalcMsg());
			record.setInsurance(tempDTO.getInsurance());
			record.setHospitalLevel(String.valueOf(tempDTO.getHospitalLevel()));
			record.setHospitalLocal(tempDTO.getHospitalLocal());
			record.setHospitalType(tempDTO.getHospitaltype());
			record.setOtherType(tempDTO.getOtherType());
			record.setSsn(tempDTO.getSsn());
			record.setBusinessYear(tempDTO.getBusinessyear());
			Integer id = jzMedicalafterDAO.insertSelective(record);
			tempDTO.setApproveId(id.longValue());
			if (!"".equals(tempDTO.getAssistTypeM())
					&& null != tempDTO.getAssistTypeM()) {
				tempDTO.setA1(tempDTO.getAssistTypeM().substring(0, 1));
				tempDTO.setA2(tempDTO.getAssistTypeM().substring(1, 2));
				tempDTO.setA3(tempDTO.getAssistTypeM().substring(2, 3));
				tempDTO.setA4(tempDTO.getAssistTypeM().substring(3, 4));
				tempDTO.setA5(tempDTO.getAssistTypeM().substring(4, 5));
			}
			if (!"".equals(tempDTO.getAssistTypex())
					&& null != tempDTO.getAssistTypex()) {
				tempDTO.setA6(tempDTO.getAssistTypex().substring(0, 1));
				tempDTO.setA7(tempDTO.getAssistTypex().substring(1, 2));
				tempDTO.setA8(tempDTO.getAssistTypex().substring(2, 3));
				tempDTO.setA9(tempDTO.getAssistTypex().substring(3, 4));
				tempDTO.setA10(tempDTO.getAssistTypex().substring(4, 5));
				tempDTO.setA11(tempDTO.getAssistTypex().substring(5, 6));
			}
			tempDTO.setAssistTypeTxt(genAssistype(tempDTO));
		} else {
			record.setBeginTime(tempDTO.getBegintime());
			record.setEndTime(tempDTO.getEndtime());
			record.setBizStatus(tempDTO.getBizStatus());
			record.setDiagnoseName(tempDTO.getInhospitalsicken());
			record.setHospitalName(tempDTO.getHospitalname());
			record.setFamilyNo(tempDTO.getFamilyno());
			record.setFamilyAddress(tempDTO.getAddress());
			record.setIdCard(tempDTO.getPaperid());
			record.setMemberId(tempDTO.getMemberId());
			record.setMemberType(tempDTO.getMemberType());
			record.setSex(tempDTO.getSex());
			record.setName(tempDTO.getMembername());
			record.setOperTime(new Date());
			record.setOrganizationId(tempDTO.getOrganizationId());
			record.setPayAssist(tempDTO.getPayAssist());
			record.setPayMedicare(tempDTO.getPayMedicare());
			record.setPayOutmedicare(tempDTO.getPayOutmedicare());
			record.setPayTotal(tempDTO.getPayTotal());
			record.setAssistType(tempDTO.getAssistype());
			record.setDiagnoseName(tempDTO.getInhospitalsicken());
			record.setBizId(tempDTO.getApproveId().intValue());
			record.setMedicareType(tempDTO.getMedicareType());
			record.setPersonstate(tempDTO.getPersonstate());
			record.setAssistTypeM(tempDTO.getAssistTypeM());
			record.setAssistTypex(tempDTO.getAssistTypex());
			// 大病保险
			record.setPaySumassistIn(tempDTO.getPaySumAssistIn());
			record.setPaySumassistOut(tempDTO.getPaySumAssistOut());
			record.setSumMedicarescope(tempDTO.getSumMedicareScope());
			record.setPayCiassist(tempDTO.getPayCIAssist());
			record.setSumAssitscope(tempDTO.getPaySumAssistScopeIn());
			record.setSumPreAssitscope(tempDTO.getPayPreSumAssistScopeIn());
			//
			record.setHospitalId((int) tempDTO.getHospitalId());
			record.setDiagnoseTypeId(new Integer(tempDTO.getDiagnoseTypeId()));
			record.setIcdId(new Integer(tempDTO.getIcdId()));
			record.setSpecbiz(String.valueOf(tempDTO.getSpecBiz()));
			record.setCalcmsg(tempDTO.getCalcMsg());
			record.setInsurance(tempDTO.getInsurance());
			record.setHospitalLevel(String.valueOf(tempDTO.getHospitalLevel()));
			record.setHospitalLocal(tempDTO.getHospitalLocal());
			record.setHospitalType(tempDTO.getHospitaltype());
			record.setOtherType(tempDTO.getOtherType());
			record.setSsn(tempDTO.getSsn());
			record.setBusinessYear(tempDTO.getBusinessyear());
			jzMedicalafterDAO.updateByPrimaryKeySelective(record);
			if (!"".equals(tempDTO.getAssistTypeM())
					&& null != tempDTO.getAssistTypeM()) {
				tempDTO.setA1(tempDTO.getAssistTypeM().substring(0, 1));
				tempDTO.setA2(tempDTO.getAssistTypeM().substring(1, 2));
				tempDTO.setA3(tempDTO.getAssistTypeM().substring(2, 3));
				tempDTO.setA4(tempDTO.getAssistTypeM().substring(3, 4));
				tempDTO.setA5(tempDTO.getAssistTypeM().substring(4, 5));
			}
			if (!"".equals(tempDTO.getAssistTypex())
					&& null != tempDTO.getAssistTypex()) {
				tempDTO.setA6(tempDTO.getAssistTypex().substring(0, 1));
				tempDTO.setA7(tempDTO.getAssistTypex().substring(1, 2));
				tempDTO.setA8(tempDTO.getAssistTypex().substring(2, 3));
				tempDTO.setA9(tempDTO.getAssistTypex().substring(3, 4));
				tempDTO.setA10(tempDTO.getAssistTypex().substring(4, 5));
				tempDTO.setA11(tempDTO.getAssistTypex().substring(5, 6));
			}
			tempDTO.setAssistTypeTxt(genAssistype(tempDTO));
		}
		return tempDTO;
	}

	public void removeAfterapply(TempDTO tempDTO) {
		Integer bizId = new Integer(tempDTO.getApproveId().intValue());
		jzMedicalafterDAO.deleteByPrimaryKey(bizId);
	}

	@SuppressWarnings({ "rawtypes" })
	public List<TempDTO> findAfterapplys(TempDTO tempDTO) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		String sql = "select t.biz_id, mem.membername, mem.paperid18, mem.familyno, "
				+ "t.pay_total, t.pay_medicare, t.pay_outmedicare, t.pay_assist, t.pay_ciassist, t.biz_status, mem.personstate, mem.assist_type, mem.assist_typex, "
				+ "t.assist_type as mt, t.diagnose_name, t.hospital_name, t.assist_time , t.member_id, t.member_type, t.other_type, t.insurance "
				+ " from jz_medicalafter t, MEMBER_BASEINFOVIEW02 mem "
				+ "where t.member_id = mem.member_id  and t.member_type = mem.ds  and t.member_id='"
				+ tempDTO.getMemberId()
				+ "' and t.member_type='"
				+ tempDTO.getMemberType() + "'  order by t.assist_time";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID18"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayCIAssist((BigDecimal) s.get("PAY_CIASSIST"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setApplytime((Date) s.get("ASSIST_TIME"));
			e.setInhospitalsicken((String) s.get("DIAGNOSE_NAME"));
			e.setHospitalname((String) s.get("HOSPITAL_NAME"));
			e.setApproveId(new Long(s.get("BIZ_ID").toString()));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setPersonstate((String) s.get("PERSONSTATE"));
			e.setBizStatus((String) s.get("BIZ_STATUS"));
			e.setAssistype((String) s.get("MT"));
			e.setInsurance((BigDecimal) s.get("INSURANCE"));
			String assistypem = (String) s.get("ASSIST_TYPE");
			String assisttypex = (String) s.get("ASSIST_TYPEX");
			e.setAssistTypex(assisttypex);
			if (!"".equals(assistypem) && null != assistypem) {
				e.setA1(assistypem.substring(0, 1));
				e.setA2(assistypem.substring(1, 2));
				e.setA3(assistypem.substring(2, 3));
				e.setA4(assistypem.substring(3, 4));
				e.setA5(assistypem.substring(4, 5));
			}
			if (!"".equals(assisttypex) && null != assisttypex) {
				e.setA6(assisttypex.substring(0, 1));
				e.setA7(assisttypex.substring(1, 2));
				e.setA8(assisttypex.substring(2, 3));
				e.setA9(assisttypex.substring(3, 4));
				e.setA10(assisttypex.substring(4, 5));
				e.setA11(assisttypex.substring(5, 6));
			}
			e.setAssistTypeTxt(genAssistype(e));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempDTO> findAfterapprove(String sql, int currentpage,
			String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// MEMBERNAME PAPERID FAMILYNO MEMBER_ID MEMBER_TYPE CS PAY_TOTAL
		// PAY_MEDICARE PAY_OUTMEDICARE PAY_ASSIST
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setPayCIAssist((BigDecimal) s.get("PAY_CIASSIST"));
			// e.setApproveId(new Long(s.get("BIZ_ID").toString()));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setCs((BigDecimal) s.get("CS"));
			e.setPersonstate((String) s.get("PERSONSTATE"));
			e.setOrgname1((String) s.get("ORGNAME1"));
			e.setOrgname2((String) s.get("ORGNAME2"));
			String assistypem = (String) s.get("ASSIST_TYPE");
			String assisttypex = (String) s.get("ASSIST_TYPEX");
			if (!"".equals(assistypem) && null != assistypem) {
				e.setA1(assistypem.substring(0, 1));
				e.setA2(assistypem.substring(1, 2));
				e.setA3(assistypem.substring(2, 3));
				e.setA4(assistypem.substring(3, 4));
				e.setA5(assistypem.substring(4, 5));
			}
			if (!"".equals(assisttypex) && null != assisttypex) {
				e.setA6(assisttypex.substring(0, 1));
				e.setA7(assisttypex.substring(1, 2));
				e.setA8(assisttypex.substring(2, 3));
				e.setA9(assisttypex.substring(3, 4));
				e.setA10(assisttypex.substring(4, 5));
				e.setA11(assisttypex.substring(5, 6));
			}
			e.setAssistTypeTxt(genAssistype(e));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempDTO> findAfterapprovedone(String sql, int currentpage,
			String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// MEMBERNAME PAPERID FAMILYNO MEMBER_ID MEMBER_TYPE CS PAY_TOTAL
		// PAY_MEDICARE PAY_OUTMEDICARE PAY_ASSIST
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setPayCIAssist((BigDecimal) s.get("PAY_CIASSIST"));
			e.setApproveId(new Long(s.get("BIZ_ID").toString()));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setApprovests((String) s.get("BIZ_STATUS"));
			e.setNation((String) s.get("DIAGNOSE_NAME"));
			e.setPersonstate((String) s.get("PERSONSTATE"));
			e.setAssistype((String) s.get("ASSIST_TYPE"));
			e.setAssistTime((Date) s.get("ASSIST_TIME"));
			e.setOpertime((Date) s.get("OPER_TIME"));
			e.setBegintime((Date) s.get("BEGIN_TIME"));
			e.setEndtime((Date) s.get("END_TIME"));
			String assistypem = (String) s.get("ASSIST_TYPE_M");
			String assistypex = (String) s.get("ASSIST_TYPEX");
			if (!"".equals(assistypem) && null != assistypem) {
				e.setA1(assistypem.substring(0, 1));
				e.setA2(assistypem.substring(1, 2));
				e.setA3(assistypem.substring(2, 3));
				e.setA4(assistypem.substring(3, 4));
				e.setA5(assistypem.substring(4, 5));
			}
			if (!"".equals(assistypex) && null != assistypex) {
				e.setA6(assistypex.substring(0, 1));
				e.setA7(assistypex.substring(1, 2));
				e.setA8(assistypex.substring(2, 3));
				e.setA9(assistypex.substring(3, 4));
				e.setA10(assistypex.substring(4, 5));
				e.setA11(assistypex.substring(5, 6));
			}
			e.setAssistTypeTxt(genAssistype(e));
			list.add(e);
		}
		return list;
	}

	public TempDTO findTempmeberinfo(TempDTO tempDTO) {
		TempPersonExample example = new TempPersonExample();
		example.createCriteria()
				.andMemberIdEqualTo(new BigDecimal(tempDTO.getMemberId()))
				.andMemberTypeEqualTo(new BigDecimal(tempDTO.getMemberType()));
		List<TempPerson> ms = tempPersonDAO.selectByExample(example);
		if (null != ms && ms.size() > 0) {
			TempPerson m = ms.get(0);
			tempDTO.setMembername(m.getMembername());
			tempDTO.setMemberType(m.getMemberType().toString());
			tempDTO.setAddress(m.getAddress());
			tempDTO.setSex(m.getSex());
			tempDTO.setPaperid(m.getPaperid());
			tempDTO.setRelmaster(m.getRelmaster());
			tempDTO.setFamilyno(m.getFamilyno());
			tempDTO.setOrganizationId(m.getOrganizationid());
			tempDTO.setMemberId(m.getMemberId().toString());
			tempDTO.setSsn(m.getSsn());
			tempDTO.setNation(m.getNation());
			tempDTO.setSafesort(m.getSafesort());
			tempDTO.setBirthday(m.getBirthday());
			tempDTO.setLinkmode(m.getLinkmode());
			tempDTO.setPersonstate(m.getPersonstate());
			tempDTO.setAssistTypeM(m.getAssistType());
			tempDTO.setAssistTypex(m.getAssistTypex());
			tempDTO.setStatus(m.getStatus());
			if (!"".equals(m.getAssistType()) && null != m.getAssistType()) {
				tempDTO.setA1(m.getAssistType().substring(0, 1));
				tempDTO.setA2(m.getAssistType().substring(1, 2));
				tempDTO.setA3(m.getAssistType().substring(2, 3));
				tempDTO.setA4(m.getAssistType().substring(3, 4));
				tempDTO.setA5(m.getAssistType().substring(4, 5));
			}
			if (!"".equals(m.getAssistTypex()) && null != m.getAssistTypex()) {
				tempDTO.setA6(m.getAssistTypex().substring(0, 1));
				tempDTO.setA7(m.getAssistTypex().substring(1, 2));
				tempDTO.setA8(m.getAssistTypex().substring(2, 3));
				tempDTO.setA9(m.getAssistTypex().substring(3, 4));
				tempDTO.setA10(m.getAssistTypex().substring(4, 5));
				tempDTO.setA11(m.getAssistTypex().substring(5, 6));
			}
			tempDTO.setAssistype(genAssistype(tempDTO));
		}
		if (null != tempDTO.getApproveId()) {
			TempApprove a = tempApproveDAO.selectByPrimaryKey(tempDTO
					.getApproveId());
			tempDTO.setApprovests(a.getApprovests());
			tempDTO.setApplytime(a.getApplytime());
			tempDTO.setApprovetime(a.getApprovetime());
			tempDTO.setYbSickenval(a.getYbSickenval());
			tempDTO.setTsSickenval(a.getTsSickenval());
			tempDTO.setHospitalname(a.getHospitalname());
			tempDTO.setInhospitalsicken(a.getInhospitalsicken());
			tempDTO.setMedicaltime(a.getMedicaltime());
			tempDTO.setPayAssist(a.getPayAssist());
			tempDTO.setPayMedicare(a.getPayMedicare());
			tempDTO.setPayAssistscope(a.getPayAssistscope());
			tempDTO.setPayMinline(a.getPayMinline());
			tempDTO.setPayOutmedicare(a.getPayOutmedicare());
			tempDTO.setPayTotal(a.getPayTotal());
			tempDTO.setPayYouhui(a.getPayYouhui());
			tempDTO.setImplsts(a.getImplsts());
			tempDTO.setPaySumAssistIn(a.getPaySumassistIn());
			tempDTO.setPaySumAssistOut(a.getPaySumassistOut());
			tempDTO.setSumMedicareScope(a.getSumMedicarescope());
			tempDTO.setPayCIAssist(a.getPayCiassist());
			tempDTO.setAssistType(a.getAssistType());
			tempDTO.setMedicaltimeEnd(a.getMedicaltimeEnd());
			String a1 = a.getYbSicken();
			if (null != a1 && !"".equals(a1)) {
				a1 = a1.replace("@", " ");
				tempDTO.setYbSicken(a1);
			}
			String b1 = a.getTsSicken();
			if (null != b1 && !"".equals(b1)) {
				b1 = b1.replace("@", " ");
				tempDTO.setTsSicken(b1);
			}
			tempDTO.setSaltype(a.getSaltype());
			tempDTO.setBusinessyear(a.getBusinessYear());
		}
		return tempDTO;

	}

	public TempDTO saveTempApplyInfo(TempDTO tempDTO) {
		TempApprove record = new TempApprove();
		record.setApproveId(tempDTO.getApproveId());
		record.setApplytime(new Date());
		record.setApprovests("1");
		record.setHospitalname(tempDTO.getHospitalname());
		record.setInhospitalsicken(tempDTO.getInhospitalsicken());
		record.setMedicaltime(tempDTO.getMedicaltime());
		record.setMemberId(tempDTO.getMemberId());
		record.setMemberType(tempDTO.getMemberType());
		record.setSaltype(tempDTO.getSafesort());
		String a = "";
		if (null != tempDTO.getIcdnames() && tempDTO.getIcdnames().length > 0) {
			for (int i = 0; i < tempDTO.getIcdnames().length; i++) {
				a = a + tempDTO.getIcdnames()[i] + "@";
			}
		}
		record.setYbSicken(a);
		record.setYbSickenval(tempDTO.getYbSickenval());
		String b = "";
		if (null != tempDTO.getIcdnames() && tempDTO.getTsicds().length > 0) {
			for (int i = 0; i < tempDTO.getTsicds().length; i++) {
				b = b + tempDTO.getTsicds()[i] + "@";
			}
		}
		record.setTsSicken(b);
		record.setTsSickenval(tempDTO.getTsSickenval());

		record.setAssistType(tempDTO.getAssistType());
		record.setMedicaltimeEnd(tempDTO.getMedicaltimeEnd());
		record.setIdCard(tempDTO.getPaperid());
		
		record.setBusinessYear(tempDTO.getBusinessyear());
		
		// 费用的录入
		record.setPayTotal(tempDTO.getPayTotal());
		record.setPayOutmedicare(tempDTO.getPayOutmedicare());
		record.setPayMedicare(tempDTO.getPayMedicare());
		record.setPayYouhui(tempDTO.getPayYouhui());
		record.setPayMinline(tempDTO.getPayMinline());
		record.setPayAssistscope(tempDTO.getPayAssistscope());
		record.setPayAssist(tempDTO.getPayAssist());
		// 大病医疗
		record.setPaySumassistIn(tempDTO.getPaySumAssistIn());
		record.setPaySumassistOut(tempDTO.getPaySumAssistOut());
		record.setSumMedicarescope(tempDTO.getSumMedicareScope());
		record.setPayCiassist(tempDTO.getPayCIAssist());
		tempDTO.setApproveId(tempApproveDAO.insertSelective(record));

		// 更新参保状态
		TempPerson record1 = new TempPerson();
		record1.setSafesort(tempDTO.getSafesort());
		TempPersonExample example = new TempPersonExample();
		example.createCriteria()
				.andMemberIdEqualTo(new BigDecimal(tempDTO.getMemberId()))
				.andMemberTypeEqualTo(new BigDecimal(tempDTO.getMemberType()));
		tempPersonDAO.updateByExampleSelective(record1, example);
		return tempDTO;
	}

	public TempDTO saveTempApproveInfo(TempDTO tempDTO) {
		String organizationId = tempDTO.getOrganizationId();
		TempApprove record = new TempApprove();
		record.setPayTotal(tempDTO.getPayTotal());
		record.setPayOutmedicare(tempDTO.getPayOutmedicare());
		record.setPayMedicare(tempDTO.getPayMedicare());
		record.setPayYouhui(tempDTO.getPayYouhui());
		record.setPayMinline(tempDTO.getPayMinline());
		record.setPayAssistscope(tempDTO.getPayAssistscope());
		record.setPayAssist(tempDTO.getPayAssist());
		record.setApproveId(tempDTO.getApproveId());
		record.setApprovetime(new Date());
		record.setPaySumassistIn(tempDTO.getPaySumAssistIn());
		record.setPaySumassistOut(tempDTO.getPaySumAssistOut());
		record.setSumMedicarescope(tempDTO.getSumMedicareScope());
		record.setPayCiassist(tempDTO.getPayCIAssist());
		record.setBusinessYear(tempDTO.getBusinessyear());
		if (organizationId.trim().length() == 8) {
			record.setApprovests("1");
		} else {
			record.setApprovests(tempDTO.getApprovests());
		}
		record.setImplsts(tempDTO.getImplsts());
		tempApproveDAO.updateByPrimaryKeySelective(record);
		// 更新tempperson
		TempPerson record1 = new TempPerson();
		record1.setMembername(tempDTO.getMembername());
		record1.setPaperid(tempDTO.getPaperid());
		TempPersonExample example = new TempPersonExample();
		example.createCriteria()
				.andMemberIdEqualTo(new BigDecimal(tempDTO.getMemberId()))
				.andMemberTypeEqualTo(new BigDecimal(tempDTO.getMemberType()));
		tempPersonDAO.updateByExampleSelective(record1, example);
		return tempDTO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempDTO> findTempperson(String sql, int currentpage, String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setMemberId(s.get("MEMBER_ID").toString());
			e.setMemberType(s.get("MEMBER_TYPE").toString());
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setRelmaster((String) s.get("RELMASTER"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempDTO> findTempapprove(String sql, int currentpage, String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// APPROVE_ID MEMBER_ID MEMBER_TYPE APPLYTIME APPROVESTS MEMBERNAME
		// FAMILYNO PAPERID RELMASTER
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setApproveId(new Long(s.get("APPROVE_ID").toString()));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setApplytime((Date) s.get("APPLYTIME"));
			e.setRelmaster((String) s.get("RELMASTER"));
			e.setApprovests((String) s.get("APPROVESTS"));
			e.setImplsts((String) s.get("IMPLSTS"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			if (!"".equals(s.get("PAY_CIASSIST"))
					&& null != s.get("PAY_CIASSIST")) {
				e.setPayCIAssist((BigDecimal) s.get("PAY_CIASSIST"));
			} else {
				e.setPayCIAssist(BigDecimal.ZERO);
			}
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPersonstate((String) s.get("PERSONSTATE"));
			String assistType = (String) s.get("ASSIST_TYPE");
			String assistTypex = (String) s.get("ASSIST_TYPEX");
			if (!"".equals(assistType) && null != assistType) {
				e.setA1(assistType.substring(0, 1));
				e.setA2(assistType.substring(1, 2));
				e.setA3(assistType.substring(2, 3));
				e.setA4(assistType.substring(3, 4));
				e.setA5(assistType.substring(4, 5));
			}
			if (!"".equals(assistTypex) && null != assistTypex) {
				e.setA6(assistTypex.substring(0, 1));
				e.setA7(assistTypex.substring(1, 2));
				e.setA8(assistTypex.substring(2, 3));
				e.setA9(assistTypex.substring(3, 4));
				e.setA10(assistTypex.substring(4, 5));
				e.setA11(assistTypex.substring(5, 6));
			}
			e.setAssistype(genAssistype(e));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<TempDTO> findMaapprove(String sql, int currentpage, String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// APPROVE_ID MEMBER_ID MEMBER_TYPE APPLYTIME APPROVESTS MEMBERNAME
		// FAMILYNO PAPERID RELMASTER
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setApproveId(new Long(s.get("APPROVE_ID").toString()));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setApplytime((Date) s.get("APPLYTIME"));
			e.setRelmaster((String) s.get("RELMASTER"));
			e.setApprovests((String) s.get("APPROVESTS"));
			e.setImplsts((String) s.get("IMPLSTS"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			list.add(e);
		}
		return list;
	}

	/**
	 * 临时救助特殊处理保障金
	 * 
	 * @return
	 */
	public BigDecimal findtempspemoney(TempCalcRuleSpe tempCalcRuleSpe,
			TempDTO tempDTO) {
		BigDecimal assis = BigDecimal.ZERO;// 救助金
		BigDecimal mline = BigDecimal.ZERO;// 起助线
		BigDecimal payself = BigDecimal.ZERO;// 个人支付费用 纳入救助范围
		BigDecimal deduction = BigDecimal.ZERO;// 速算扣除数
		BigDecimal scale = BigDecimal.ZERO;// 报销比例
		int level = 0; // 级别
		mline = new BigDecimal(tempCalcRuleSpe.getPersonType());
		if ((tempDTO.getPayTotal().subtract(tempDTO.getPayMedicare())
				.subtract(mline)).compareTo(BigDecimal.ZERO) == 0
				|| (tempDTO.getPayTotal().subtract(tempDTO.getPayMedicare())
						.subtract(mline)).compareTo(BigDecimal.ZERO) == 1) {
			// 计算个人支付费用
			// payself
			// =tempDTO.getPay_Sum_AssistScope_In().subtract(tempDTO.getPay_PreSum_AssistScope_In());
			payself = tempDTO.getPayTotal().subtract(tempDTO.getPayMedicare())
					.subtract(mline).subtract(tempDTO.getPayOutmedicare())
					.subtract(tempDTO.getPayCIAssist());
			// 确定级别
			if (payself.compareTo(BigDecimal.ZERO) == 1
					&& (payself.compareTo(tempCalcRuleSpe.getM1()) == -1 || payself
							.compareTo(tempCalcRuleSpe.getM1()) == 0)) {
				level = 0;
			} else if (payself.compareTo(tempCalcRuleSpe.getM1()) == 1
					&& (payself.compareTo(tempCalcRuleSpe.getM2()) == -1 || payself
							.compareTo(tempCalcRuleSpe.getM2()) == 0)) {
				level = 1;
			} else if (payself.compareTo(tempCalcRuleSpe.getM2()) == 1
					&& (payself.compareTo(tempCalcRuleSpe.getM3()) == -1 || payself
							.compareTo(tempCalcRuleSpe.getM3()) == 0)) {
				level = 2;
			} else if (payself.compareTo(tempCalcRuleSpe.getM3()) == 1
					&& (payself.compareTo(tempCalcRuleSpe.getM4()) == -1 || payself
							.compareTo(tempCalcRuleSpe.getM4()) == 0)) {
				level = 3;
			} else if (payself.compareTo(tempCalcRuleSpe.getM4()) == 1) {
				level = 4;
			}
			List<HashMap<String, BigDecimal>> clist = CalcScope(tempCalcRuleSpe);
			deduction = clist.get(level).get("d");
			scale = clist.get(level).get("c");
			assis = payself.multiply(scale).subtract(deduction);
			log.debug(" 本次扣除数：" + deduction + "////比例：" + scale + "////救助金额："
					+ assis);
		} else {
			// 没有达到起助线!
			assis = new BigDecimal("-1");
		}
		return assis;
	}

	// 计算分段
	private List<HashMap<String, BigDecimal>> CalcScope(
			TempCalcRuleSpe tempCalcRuleSpe) {
		BigDecimal[] bds = { BigDecimal.ZERO, tempCalcRuleSpe.getM1(),
				tempCalcRuleSpe.getM2(), tempCalcRuleSpe.getM3(),
				tempCalcRuleSpe.getM4(), new BigDecimal(Double.MAX_VALUE) };
		BigDecimal[] s = { tempCalcRuleSpe.getScale1(),
				tempCalcRuleSpe.getScale2(), tempCalcRuleSpe.getScale3(),
				tempCalcRuleSpe.getScale4(), tempCalcRuleSpe.getScale5() };
		List<HashMap<String, BigDecimal>> clist = new ArrayList<HashMap<String, BigDecimal>>();
		for (int i = 0; i < 5; i++) {
			HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();
			map.put("a", bds[i]);
			map.put("b", bds[i + 1]);
			map.put("c", s[i]);
			if (bds[i].floatValue() == 0) {
				map.put("d", new BigDecimal(0));
			} else {
				if (i != 0) {
					BigDecimal c1 = map.get("c");
					BigDecimal c2 = clist.get(i - 1).get("c");
					BigDecimal c3 = map.get("a");
					BigDecimal c4 = clist.get(i - 1).get("d");
					BigDecimal c5 = ((c1.subtract(c2)).multiply(c3)).add(c4);

					map.put("d", c5);
				}
			}
			log.debug(map.get("a").toString() + ">>>>>"
					+ map.get("b").toString() + ">>>>>"
					+ map.get("c").toString() + ">>>>>"
					+ map.get("d").toString());
			clist.add(map);
		}
		return clist;
	}

	/**
	 * 计算医后报销救助金 //(1)门诊特殊慢性病救助金 //(2)住院救助金 //(3) 手工填写
	 */
	@Override
	@SuppressWarnings({ "rawtypes" })
	public HashMap<String, Comparable> findMaMoney(TempDTO tempDTO) {
		log.debug("计算医后报销救助金开始-----------------------------");
		HashMap<String, Comparable> map = new HashMap<String, Comparable>();
		BigDecimal m = BigDecimal.ZERO;// 封顶线
		BigDecimal zyTopline = BigDecimal.ZERO;// 住院封顶线
		BigDecimal mzTopline = BigDecimal.ZERO;// 门诊封顶线
		BigDecimal maTopline = BigDecimal.ZERO;// （住院+门诊）封顶线
		BigDecimal zpay = BigDecimal.ZERO;// 个人救助金总额（住院+门诊）
		BigDecimal zyPay = BigDecimal.ZERO;// 住院个人救助金总额
		BigDecimal mzPay = BigDecimal.ZERO;// 门诊个人救助金总额
		BigDecimal assis = BigDecimal.ZERO;// 救助金
		BigDecimal mline = BigDecimal.ZERO;// 起助线
		BigDecimal scale = BigDecimal.ZERO;// 计算比例
		BigDecimal sumAssis = BigDecimal.ZERO;// 累计纳入救助范围
		BigDecimal preSumAssis = BigDecimal.ZERO;// 前一次累计纳入救助范围
		BigDecimal nowAssis = BigDecimal.ZERO;
		BigDecimal preAssis = BigDecimal.ZERO;
		String result = "";
		int level = 0; // 级别
		BigDecimal deduction = BigDecimal.ZERO;// 速算扣除数
		String assistTypeM = "";
		String assistTypex = "";
		String a1 = "";
		String a2 = "";
		String a3 = "";
		String a4 = "";
		String a5 = "";
		String a6 = "";
		String a7 = "";
		String a8 = "";
		String a9 = "";
		String a10 = "";
		String a11 = "";
		String organizationId = tempDTO.getOrganizationId();
		if (null == organizationId || "".equals(organizationId)) {
			result = "机构编码为空";
		} else {
			organizationId = organizationId.substring(0, 6);
			// 住院业务封顶线 type=3
			zyTopline = getToplineByType(organizationId, "3");
			// 门诊大病业务封顶线 type=1
			mzTopline = getToplineByType(organizationId, "1");
			// （门诊大病+住院）封顶线 type=9
			maTopline = getToplineByType(organizationId, "9");

			// 判断身份类别
			assistTypeM = tempDTO.getAssistTypeM();
			if (assistTypeM != null && !"".equals(assistTypeM)) {
				a1 = assistTypeM.substring(0, 1);
				a2 = assistTypeM.substring(1, 2);
				a3 = assistTypeM.substring(2, 3);
				a4 = assistTypeM.substring(3, 4);
				a5 = assistTypeM.substring(4, 5);
			}
			assistTypex = tempDTO.getAssistTypex();
			if (assistTypex != null && !"".equals(assistTypex)) {
				a6 = assistTypex.substring(0, 1);
				a7 = assistTypex.substring(1, 2);
				a8 = assistTypex.substring(2, 3);
				a9 = assistTypex.substring(3, 4);
				a10 = assistTypex.substring(4, 5);
				a11 = assistTypex.substring(5, 6);
			}
			// 乾安和桦甸五保户、三无人员的住院封顶线是15000元
			if ("220225".equals(organizationId)
					&& ("1".equals(a3) || "1".equals(a4))) {
				zyTopline = new BigDecimal("15000");
			} else if ("220803".equals(organizationId)
					&& ("1".equals(a3) || "1".equals(a4))) {
				zyTopline = BigDecimal.ZERO;
			}

			// 住院业务个人救助金总额(本年度)type = 2
			zyPay = getTotalPayAssistPer(tempDTO, "2");
			// 门诊大病业务个人救助金总额(本年度)type = 1
			mzPay = getTotalPayAssistPer(tempDTO, "1");
			// （住院业务+门诊大病业务）个人救助金总额(本年度)
			zpay = zyPay.add(mzPay);

			if ((BigDecimal.ZERO.compareTo(zyPay) == -1 && BigDecimal.ZERO
					.compareTo(mzPay) == -1)
					|| (BigDecimal.ZERO.compareTo(zyPay) == -1
							&& BigDecimal.ZERO.compareTo(mzPay) == 0 && "1"
								.equals(tempDTO.getAssistype()))
					|| (BigDecimal.ZERO.compareTo(zyPay) == 0
							&& BigDecimal.ZERO.compareTo(mzPay) == -1 && "2"
								.equals(tempDTO.getAssistype()))) {
				m = maTopline;
			} else if ((BigDecimal.ZERO.compareTo(zyPay) == -1 || BigDecimal.ZERO
					.compareTo(zyPay) == 0)
					&& BigDecimal.ZERO.compareTo(mzPay) == 0
					&& "2".equals(tempDTO.getAssistype())) {
				m = zyTopline;
			} else if (BigDecimal.ZERO.compareTo(zyPay) == 0
					&& (BigDecimal.ZERO.compareTo(mzPay) == -1 || BigDecimal.ZERO
							.compareTo(mzPay) == 0)
					&& "1".equals(tempDTO.getAssistype())) {
				m = mzTopline;
			}

			if (!"0".equals(a1) || "1".equals(a3) || "1".equals(a4)
					|| "1".equals(a5) || "1".equals(a6)) {
				JzMedicalafterRuleDTO jzMedicalafterRuleDTO = findMedicalafterRule(tempDTO);
				if (jzMedicalafterRuleDTO.getRuleId() != null) {
					// 门诊特殊慢性病救助金
					if ("1".equals(tempDTO.getAssistype())) {
						m = jzMedicalafterRuleDTO.getMztsTopLine();
						mline = jzMedicalafterRuleDTO.getMztsSl();
						if ("1".equals(a4)) {
							// 五保户救助比例
							scale = jzMedicalafterRuleDTO.getWbhScale();
						} else if ("1".equals(a3)) {
							// 三无人员救助比例
							scale = jzMedicalafterRuleDTO.getSanwuScale();
						} else if ("1".equals(a6)) {
							// 孤儿救助比例
							scale = jzMedicalafterRuleDTO.getWbhScale();
						} else if (!"0".equals(a1) || "1".equals(a5)) {
							// 城乡低保对象、重点优抚对象
							if ("1".equals(tempDTO.getMedicareType())
									|| "2".equals(tempDTO.getMedicareType())) {
								scale = jzMedicalafterRuleDTO.getMztsScale();
							} else if ("3".equals(tempDTO.getMedicareType())) {
								if ("1".equals(tempDTO.getMemberType())) {
									scale = jzMedicalafterRuleDTO
											.getWcCsScale();
								} else if ("2".equals(tempDTO.getMemberType())) {
									scale = jzMedicalafterRuleDTO
											.getWcNcScale();
								}
							}
						}
						if (m.subtract(zpay).compareTo(BigDecimal.ZERO) == 1) {
							if (tempDTO.getPayTotal()
									.subtract(tempDTO.getPayMedicare())
									.subtract(tempDTO.getPayOutmedicare())
									// .subtract(tempDTO.getPayCIAssist())
									.subtract(mline).compareTo(BigDecimal.ZERO) == 0
									|| tempDTO
											.getPayTotal()
											.subtract(tempDTO.getPayMedicare())
											.subtract(
													tempDTO.getPayOutmedicare())
											// .subtract(tempDTO.getPayCIAssist())
											.subtract(mline)
											.compareTo(BigDecimal.ZERO) == 1) {
								assis = (tempDTO.getPayTotal()
										.subtract(tempDTO.getPayMedicare())
										.subtract(tempDTO.getPayOutmedicare())
										.subtract(mline)
								// .subtract(tempDTO.getPayCIAssist())
								).multiply(scale);
								if (m.subtract(zpay).compareTo(assis) == -1) {
									assis = m.subtract(zpay);
								}
								result = "成功";
							} else {
								result = "没有达到起助线!";
							}
						} else {
							result = "保障金大于封顶线，您重新填写参与计算金额!";
						}

					}
					// 住院救助金
					else if ("2".equals(tempDTO.getAssistype())) {
						sumAssis = tempDTO.getPaySumAssistScopeIn();
						preSumAssis = tempDTO.getPayPreSumAssistScopeIn();
						m = jzMedicalafterRuleDTO.getZyTopLine();
						mline = jzMedicalafterRuleDTO.getZySl();
						if ("1".equals(a4)) {
							// 五保户救助比例
							scale = jzMedicalafterRuleDTO.getWbhScale();
						} else if ("1".equals(a3)) {
							// 三无人员救助比例
							scale = jzMedicalafterRuleDTO.getSanwuScale();
						} else if ("1".equals(a6)) {
							// 孤儿救助比例
							scale = jzMedicalafterRuleDTO.getWbhScale();
						} else if (!"0".equals(a1) || "1".equals(a5)) {
							// 城乡低保对象、重点优抚对象
							if ("1".equals(tempDTO.getMedicareType())) {
								scale = jzMedicalafterRuleDTO.getcCsScale();
							} else if ("2".equals(tempDTO.getMedicareType())) {
								scale = jzMedicalafterRuleDTO.getcNcScale();
							} else if ("0".equals(tempDTO.getMedicareType())) {
								if ("1".equals(tempDTO.getMemberType())) {
									scale = jzMedicalafterRuleDTO
											.getWcCsScale();
								} else if ("2".equals(tempDTO.getMemberType())) {
									scale = jzMedicalafterRuleDTO
											.getWcNcScale();
								}
							}
						}
						TempCalcRuleSpe tempCalcRuleSpe = new TempCalcRuleSpe();
						tempCalcRuleSpe.setM1(jzMedicalafterRuleDTO.getZyM1());
						tempCalcRuleSpe.setM2(jzMedicalafterRuleDTO.getZyM2());
						tempCalcRuleSpe.setM3(jzMedicalafterRuleDTO.getZyM3());
						tempCalcRuleSpe.setM4(jzMedicalafterRuleDTO.getZyM4());
						tempCalcRuleSpe.setScale1(jzMedicalafterRuleDTO
								.getZyScale1());
						tempCalcRuleSpe.setScale2(jzMedicalafterRuleDTO
								.getZyScale2());
						tempCalcRuleSpe.setScale3(jzMedicalafterRuleDTO
								.getZyScale3());
						tempCalcRuleSpe.setScale4(jzMedicalafterRuleDTO
								.getZyScale4());
						tempCalcRuleSpe.setScale5(jzMedicalafterRuleDTO
								.getZyScale5());
						List<HashMap<String, BigDecimal>> clist = CalcScope(tempCalcRuleSpe);

						if (m.subtract(zpay).compareTo(BigDecimal.ZERO) == 1) {
							if ("1".equals(a3) || "1".equals(a4)
									|| "1".equals(a6)) {
								if (tempDTO.getPayTotal()
										.subtract(tempDTO.getPayMedicare())
										.subtract(tempDTO.getPayCIAssist())
										.subtract(tempDTO.getInsurance())
										.subtract(mline)
										.compareTo(BigDecimal.ZERO) == 0
										|| tempDTO
												.getPayTotal()
												.subtract(
														tempDTO.getPayMedicare())
												.subtract(
														tempDTO.getPayCIAssist())
												.subtract(
														tempDTO.getInsurance())
												.subtract(mline)
												.compareTo(BigDecimal.ZERO) == 1) {
									assis = (tempDTO.getPayTotal()
											.subtract(tempDTO.getPayMedicare())
											.subtract(mline).subtract(tempDTO
											.getPayCIAssist().subtract(
													tempDTO.getInsurance())))
											.multiply(scale);
									if (m.subtract(zpay).compareTo(assis) == -1) {
										assis = m.subtract(zpay);
									}
									result = "成功";
								} else {
									result = "没有达到起助线!";
								}
							} else if (!"0".equals(a1) || "1".equals(a5)) {
								/*
								 * if ("1".equals(tempDTO.getMedicareType()) ||
								 * "2".equals(tempDTO.getMedicareType())) {
								 */
								sumAssis = sumAssis.multiply(scale);
								level = getLevel(sumAssis,
										jzMedicalafterRuleDTO);
								deduction = clist.get(level).get("d");
								scale = clist.get(level).get("c");
								nowAssis = sumAssis.multiply(scale).subtract(
										deduction);

								level = getLevel(preSumAssis,
										jzMedicalafterRuleDTO);
								deduction = clist.get(level).get("d");
								scale = clist.get(level).get("c");
								preAssis = preSumAssis.multiply(scale)
										.subtract(deduction);
								// 计算救助金
								if (nowAssis.subtract(preAssis).compareTo(
										BigDecimal.ZERO) == 0
										|| nowAssis.subtract(preAssis)
												.compareTo(BigDecimal.ZERO) == 1) {
									assis = nowAssis.subtract(preAssis)
											.subtract(tempDTO.getInsurance());
									if (m.subtract(zpay).compareTo(assis) == -1) {
										assis = m.subtract(zpay);
									}
									if (assis.compareTo(BigDecimal.ZERO) == -1) {
										result = "救助金额小于零";
									} else {
										result = "成功";
									}
								} else {
									result = "没有达到起助线!";
								}
								/*
								 * } else if
								 * ("3".equals(tempDTO.getMedicareType())) {
								 * result = "成功"; }
								 */
							}
						} else {
							result = "保障金大于封顶线，您重新填写参与计算金额!";
						}
					}

				}
				// 手工填写
				else {
					if (zyTopline.compareTo(BigDecimal.ZERO) == 0) {
						result = "成功";
					} else {
						if (m.subtract(zpay).compareTo(BigDecimal.ZERO) == -1
								|| zyTopline.subtract(zyPay).compareTo(
										BigDecimal.ZERO) == -1
								|| mzTopline.subtract(mzPay).compareTo(
										BigDecimal.ZERO) == -1) {
							result = "保障金大于封顶线，您重新填写参与计算金额!";
						} else {
							result = "成功";
						}
					}
				}
			} else {
				/*
				 * if("1".equals(a2)){ if("1".equals(tempDTO.getMemberType())){
				 * result = "分类施保不在救助范围内！"; }else
				 * if("2".equals(tempDTO.getMemberType())){ result =
				 * "重点户不在救助范围内！"; } }else{ result = "普通居民不在救助范围内！"; }
				 */
				result = "普通居民不在救助范围内！";
			}
		}
		BigDecimal i = assis;
		assis = i.setScale(2, RoundingMode.HALF_EVEN);
		map.put("m", assis);
		map.put("info", result);
		return map;
	}

	// 医后报销确定级别
	private int getLevel(BigDecimal payself,
			JzMedicalafterRuleDTO jzMedicalafterRuleDTO) {
		int level = 0;
		if (payself.compareTo(BigDecimal.ZERO) == 1
				&& (payself.compareTo(jzMedicalafterRuleDTO.getZyM1()) == -1 || payself
						.compareTo(jzMedicalafterRuleDTO.getZyM1()) == 0)) {
			level = 0;
		} else if (payself.compareTo(jzMedicalafterRuleDTO.getZyM1()) == 1
				&& (payself.compareTo(jzMedicalafterRuleDTO.getZyM2()) == -1 || payself
						.compareTo(jzMedicalafterRuleDTO.getZyM2()) == 0)) {
			level = 1;
		} else if (payself.compareTo(jzMedicalafterRuleDTO.getZyM2()) == 1
				&& (payself.compareTo(jzMedicalafterRuleDTO.getZyM3()) == -1 || payself
						.compareTo(jzMedicalafterRuleDTO.getZyM3()) == 0)) {
			level = 2;
		} else if (payself.compareTo(jzMedicalafterRuleDTO.getZyM3()) == 1
				&& (payself.compareTo(jzMedicalafterRuleDTO.getZyM4()) == -1 || payself
						.compareTo(jzMedicalafterRuleDTO.getZyM4()) == 0)) {
			level = 3;
		} else if (payself.compareTo(jzMedicalafterRuleDTO.getZyM4()) == 1) {
			level = 4;
		}
		return level;
	}

	@SuppressWarnings({ "rawtypes" })
	public HashMap<String, Comparable> findtempmoney(TempDTO tempDTO) {
		log.debug("计算临时救助金开始-----------------------------");
		int year = Integer.parseInt(tempDTO.getBusinessyear());// 获取年份
		HashMap<String, Comparable> map = new HashMap<String, Comparable>();
		BigDecimal m = BigDecimal.ZERO;// 封顶线
		BigDecimal zpay = BigDecimal.ZERO;// 个人救助金总额
		BigDecimal assis = BigDecimal.ZERO;// 救助金
		BigDecimal mline = BigDecimal.ZERO;// 起助线
		BigDecimal scale = BigDecimal.ZERO;// 计算比例
		BigDecimal nscale = BigDecimal.ZERO;// 未参保/参合，纳入救助范围
		String result = "";
		String organizationId = tempDTO.getOrganizationId();
		if (null == organizationId || "".equals(organizationId)) {
			result = "机构编码为空";
		} else {
			organizationId = organizationId.substring(0, 6);
			HashMap<String, String> param = new HashMap<String, String>();
			String sql = "select  t.line as S  from top_line t where t.organization_id='"
					+ organizationId + "' and t.top_type='4'";
			param.put("executsql", sql);
			List<HashMap> rs = extendsDAO.queryAll(param);
			if (null != rs && rs.size() > 0) {
				HashMap s = rs.get(0);
				BigDecimal a = (BigDecimal) s.get("S");
				if (null != a) {
					m = a;
				}
			}
			sql = "select sum(ta.pay_assist)as zpay from temp_approve ta "
					+ "where to_char(ta.medicaltime_end, 'yyyy') = '" + year + "' "
					+ "and ta.member_id = '" + tempDTO.getMemberId()
					+ "' and ta.member_type = '" + tempDTO.getMemberType()
					+ "' and ta.approvests = '2'";
			param.put("executsql", sql);
			List<HashMap> rs1 = extendsDAO.queryAll(param);
			if (null != rs1 && rs1.size() > 0) {
				HashMap s = rs1.get(0);
				BigDecimal a = (BigDecimal) s.get("ZPAY");
				if (null != a) {
					zpay = a;
				}
			}
			sql = "select s.person_type, s.scale1, s.m1, s.scale2, s.m2, s.scale3, s.m3, s.scale4, s.m4, s.scale5 from temp_calc_rule_spe s where s.organization_id = '"
					+ organizationId + "'";
			param.put("executsql", sql);
			List<HashMap> rs3 = extendsDAO.queryAll(param);
			if (null != rs3 && rs3.size() > 0) {
				HashMap s = rs3.get(0);
				TempCalcRuleSpe tempCalcRuleSpe = new TempCalcRuleSpe();
				tempCalcRuleSpe.setM1(new BigDecimal(s.get("M1").toString()));
				tempCalcRuleSpe.setM2(new BigDecimal(s.get("M2").toString()));
				tempCalcRuleSpe.setM3(new BigDecimal(s.get("M3").toString()));
				tempCalcRuleSpe.setM4(new BigDecimal(s.get("M4").toString()));
				tempCalcRuleSpe.setScale1(new BigDecimal(s.get("SCALE1")
						.toString()));
				tempCalcRuleSpe.setScale2(new BigDecimal(s.get("SCALE2")
						.toString()));
				tempCalcRuleSpe.setScale3(new BigDecimal(s.get("SCALE3")
						.toString()));
				tempCalcRuleSpe.setScale4(new BigDecimal(s.get("SCALE4")
						.toString()));
				tempCalcRuleSpe.setScale5(new BigDecimal(s.get("SCALE5")
						.toString()));
				tempCalcRuleSpe.setPersonType(s.get("PERSON_TYPE").toString());
				if (m.subtract(zpay).compareTo(BigDecimal.ZERO) == 1) {
					assis = findtempspemoney(tempCalcRuleSpe, tempDTO);
					if (m.subtract(zpay).compareTo(assis) == -1) {
						assis = m.subtract(zpay);
					}
					if (assis.compareTo(new BigDecimal("-1")) == 0) {
						result = "没有达到起助线!";
					} else {
						result = "成功";
					}
				} else {
					result = "保障金大于封顶线，您重新填写参与计算金额!";
				}
			} else {
				sql = "select t.person_type, t.scale, t.person_type_nj, t.scale_nj, t.n_scale from temp_calc_rule t where t.organization_id = '"
						+ organizationId + "'";
				param.put("executsql", sql);
				List<HashMap> rs2 = extendsDAO.queryAll(param);
				if (null != rs2 && rs2.size() > 0) {
					HashMap s = rs2.get(0);
					if ("1".equals(tempDTO.getSafesort())) {
						scale = new BigDecimal(s.get("SCALE").toString());
						mline = new BigDecimal(s.get("PERSON_TYPE").toString());
					} else if ("2".equals(tempDTO.getSafesort())) {
						scale = new BigDecimal(s.get("SCALE_NJ").toString());
						mline = new BigDecimal(s.get("PERSON_TYPE_NJ")
								.toString());
					} else if ("0".equals(tempDTO.getSafesort())) {
						nscale = new BigDecimal(s.get("N_SCALE").toString());
						if ("1".equals(tempDTO.getMemberType())) {
							scale = new BigDecimal(s.get("SCALE").toString());
							mline = new BigDecimal(s.get("PERSON_TYPE")
									.toString());
						} else if ("2".equals(tempDTO.getMemberType())) {
							scale = new BigDecimal(s.get("SCALE_NJ").toString());
							mline = new BigDecimal(s.get("PERSON_TYPE_NJ")
									.toString());
						}
					}
				}
				log.debug("计算临时救助金开始-----------------------------"
						+ m.doubleValue() + ">>>" + zpay.doubleValue());
				if (m.subtract(zpay).compareTo(BigDecimal.ZERO) == 1) {
					// 参保\参合人员
					if ("1".equals(tempDTO.getSafesort())
							|| "2".equals(tempDTO.getSafesort())) {
						/*
						 * if ((tempDTO.getPayTotal().doubleValue() - tempDTO
						 * .getPayMedicare().doubleValue()) -
						 * mline.doubleValue() >= 0) {
						 */
						if (tempDTO.getPayTotal()
								.subtract(tempDTO.getPayMedicare())
								.subtract(tempDTO.getPayOutmedicare())
								.subtract(tempDTO.getPayCIAssist())
								.subtract(mline).compareTo(BigDecimal.ZERO) == 0
								|| tempDTO.getPayTotal()
										.subtract(tempDTO.getPayMedicare())
										.subtract(tempDTO.getPayOutmedicare())
										.subtract(tempDTO.getPayCIAssist())
										.subtract(mline)
										.compareTo(BigDecimal.ZERO) == 1) {
							assis = (tempDTO.getPayTotal()
									.subtract(tempDTO.getPayMedicare())
									.subtract(mline)
									.subtract(tempDTO.getPayOutmedicare())
									.subtract(tempDTO.getPayCIAssist()))
									.multiply(scale);
							/*
							 * assis = new Double(((tempDTO.getPayTotal()
							 * .doubleValue() - tempDTO.getPayMedicare()
							 * .doubleValue()) - mline.doubleValue() - tempDTO
							 * .getPayOutmedicare().doubleValue())
							 * scale.doubleValue()); if ((m.doubleValue() -
							 * zpay.doubleValue()) < assis .doubleValue()) {
							 * assis = m.doubleValue() - zpay.doubleValue(); }
							 * else { assis.doubleValue(); }
							 */
							if (m.subtract(zpay).compareTo(assis) == -1) {
								assis = m.subtract(zpay);
							}
							result = "成功";
						} else {
							result = "没有达到起助线!";
						}
						// 未参保\未参合人员
					} else if ("0".equals(tempDTO.getSafesort())) {
						if (((tempDTO.getPayTotal().subtract(tempDTO
								.getPayCIAssist())).multiply(nscale)).subtract(
								mline).compareTo(BigDecimal.ZERO) == 0
								|| ((tempDTO.getPayTotal().subtract(tempDTO
										.getPayCIAssist())).multiply(nscale))
										.subtract(mline).compareTo(
												BigDecimal.ZERO) == 1) {
							assis = (((tempDTO.getPayTotal().subtract(tempDTO
									.getPayCIAssist())).multiply(nscale))
									.subtract(mline)).multiply(scale);
							if (m.subtract(zpay).compareTo(assis) == -1) {
								assis = m.subtract(zpay);
							}
							result = "成功";
						} else {
							result = "没有达到起助线!";
						}
						/*
						 * if (((tempDTO.getPayTotal().doubleValue() * nscale) -
						 * mline .doubleValue()) >= 0) { assis = new Double(
						 * ((tempDTO.getPayTotal().doubleValue() * nscale) -
						 * mline .doubleValue()) scale.doubleValue()); if
						 * ((m.doubleValue() - zpay.doubleValue()) < assis
						 * .doubleValue()) { assis = m.doubleValue() -
						 * zpay.doubleValue(); } else { assis.doubleValue(); }
						 * result = "成功"; } else { result = "没有达到起助线!"; }
						 */
					}
				} else {
					result = "保障金大于封顶线，您重新填写参与计算金额!";
				}
			}
		}
		BigDecimal i = assis;
		assis = i.setScale(2, RoundingMode.HALF_EVEN);
		map.put("m", assis);
		map.put("info", result);
		log.debug("计算临时救助金开始-----------------------------");
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findTempStatInfo(String organizationId) {
		String result = "";
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "select count(*) as rs, nvl(sum(t.pay_total), 0) as total, "
				+ " nvl(sum(t.pay_medicare), 0) as tc, nvl(sum(t.pay_assist), 0) as jzj "
				+ " from temp_approve t, temp_person mem where mem.member_id = t.member_id "
				+ " and mem.member_type = t.member_type and (t.implsts is null or t.implsts = '1') "
				+ " and t.approvests = '2' and mem.organizationid like '"
				+ organizationId + "%'";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			result = "人数：" + s.get("RS").toString() + "（人）总金额："
					+ s.get("TOTAL").toString() + "（元）统筹："
					+ s.get("TC").toString() + "（元）救助金："
					+ s.get("JZJ").toString() + "（元）";
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findMaStatInfo(String organizationId) {
		String result = "";
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "select count(*) as rs, nvl(sum(t.pay_total), 0) as total, "
				+ " nvl(sum(t.pay_medicare), 0) as tc, nvl(sum(t.pay_assist), 0) as jzj "
				+ " from jz_medicalafter t  where (t.implsts is null or t.implsts = '1') "
				+ " and t.family_no like '"
				+ organizationId
				+ "%' and t.biz_status = '1'";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			result = "人数：" + s.get("RS").toString() + "（人）总金额："
					+ s.get("TOTAL").toString() + "（元）统筹："
					+ s.get("TC").toString() + "（元）救助金："
					+ s.get("JZJ").toString() + "（元）";
		}
		return result;
	}

	public TempDTO saveTempPerson(TempDTO tempDTO) {
		TempPerson record = new TempPerson();
		record.setAddress(tempDTO.getAddress());
		record.setBirthday(tempDTO.getBirthday());
		record.setLinkmode(tempDTO.getLinkmode());
		record.setMembername(tempDTO.getMembername());
		record.setOrganizationid(tempDTO.getOrganizationId());
		record.setMemberType(new BigDecimal(tempDTO.getMemberType()));
		record.setPaperid(tempDTO.getPaperid());
		record.setSafesort(tempDTO.getSafesort());
		record.setSex(tempDTO.getSex());
		record.setFamilyno(tempDTO.getPaperid());
		record.setSsn(tempDTO.getSsn());
		record.setRelmaster("本人");
		record.setNation(tempDTO.getNation());
		if (null == tempDTO.getMemberId()) {
			record.setStatus("1");
			record.setPersonstate("正常");
			record.setAssistType("00000");
			record.setAssistTypex("000000");
			BigDecimal id = tempPersonDAO.insertSelective(record);
			tempDTO.setMemberId(id.toString());
			tempDTO.setPersonstate("正常");
			tempDTO.setAssistype("00000");
			tempDTO.setAssistTypex("000000");
		} else {
			record.setStatus(tempDTO.getStatus());
			record.setPersonstate(tempDTO.getPersonstate());
			record.setAssistType(tempDTO.getAssistTypeM());
			record.setAssistTypex(tempDTO.getAssistTypex());
			record.setMemberId(new BigDecimal(tempDTO.getMemberId()));
			int i = tempPersonDAO.updateByPrimaryKey(record);
			tempDTO.setMemberId(i + "");

		}
		tempDTO.setRelmaster(record.getRelmaster());
		tempDTO.setFamilyno(record.getFamilyno());
		if (!"".equals(tempDTO.getAssistype())
				&& null != tempDTO.getAssistype()) {
			tempDTO.setA1(tempDTO.getAssistype().substring(0, 1));
			tempDTO.setA2(tempDTO.getAssistype().substring(1, 2));
			tempDTO.setA3(tempDTO.getAssistype().substring(2, 3));
			tempDTO.setA4(tempDTO.getAssistype().substring(3, 4));
			tempDTO.setA5(tempDTO.getAssistype().substring(4, 5));
		}
		if (!"".equals(tempDTO.getAssistTypex())
				&& null != tempDTO.getAssistTypex()) {
			tempDTO.setA6(tempDTO.getAssistTypex().substring(0, 1));
			tempDTO.setA7(tempDTO.getAssistTypex().substring(1, 2));
			tempDTO.setA8(tempDTO.getAssistTypex().substring(2, 3));
			tempDTO.setA9(tempDTO.getAssistTypex().substring(3, 4));
			tempDTO.setA10(tempDTO.getAssistTypex().substring(4, 5));
			tempDTO.setA11(tempDTO.getAssistTypex().substring(5, 6));

		}
		tempDTO.setAssistTypeTxt(genAssistype(tempDTO));
		tempDTO.setOrganizationId(tempDTO.getOrganizationId());
		return tempDTO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempMonthDTO> findTempMonths(String organizationId) {
		List<TempMonthDTO> list = new ArrayList<TempMonthDTO>();
		HashMap<String, String> param = new HashMap<String, String>();
		// MID ORGANIZATION_ID YEAR MONTH RS SALMONEY MMID
		String sql = "select *  from temp_month m, ("
				+ "select count(1) as rs, sum(b.salmoney) as salmoney, "
				+ "b.mid as mmid from temp_bill b group by b.mid) bb where m.organization_id = '"
				+ organizationId + "' and m.mid=bb.mmid";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempMonthDTO e = new TempMonthDTO();
			e.setMid((BigDecimal) s.get("MID"));
			e.setYear((String) s.get("YEAR"));
			e.setMonth((String) s.get("MONTH"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setRs((BigDecimal) s.get("RS"));
			e.setTotalmoney((BigDecimal) s.get("SALMONEY"));
			e.setYm(e.getYear() + "年" + e.getMonth() + "月");
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TempMonthDTO> findMaMonths(String organizationId) {
		List<TempMonthDTO> list = new ArrayList<TempMonthDTO>();
		HashMap<String, String> param = new HashMap<String, String>();
		// MID ORGANIZATION_ID YEAR MONTH RS SALMONEY MMID
		String sql = "select *  from ma_month m, ("
				+ "select count(1) as rs, sum(b.salmoney) as salmoney, "
				+ "b.mid as mmid from ma_bill b group by b.mid) bb where m.organization_id = '"
				+ organizationId + "' and m.mid=bb.mmid";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempMonthDTO e = new TempMonthDTO();
			e.setMid((BigDecimal) s.get("MID"));
			e.setYear((String) s.get("YEAR"));
			e.setMonth((String) s.get("MONTH"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setRs((BigDecimal) s.get("RS"));
			e.setTotalmoney((BigDecimal) s.get("SALMONEY"));
			e.setYm(e.getYear() + "年" + e.getMonth() + "月");
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveGenBillData(TempMonthDTO tempMonthDTO, String app) {
		TempMonth record = new TempMonth();
		record.setMonth(tempMonthDTO.getMonth());
		record.setYear(tempMonthDTO.getYear());
		record.setOrganizationId(tempMonthDTO.getOrganizationId());
		BigDecimal mid = tempMonthDAO.insert(record);
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "insert into temp_bill (bill_id, member_id, member_type, salmoney, ap_id, logo, familyno, paperid, bankaccount, opertime, mid, membername, mastername, masterpaperid)"
				+ " select sq_account.nextval  , mem.member_id,mem.member_type , t.pay_assist ,t.approve_id , '"
				+ tempMonthDTO.getYear()
				+ "年"
				+ tempMonthDTO.getMonth()
				+ "月' , mem.familyno, mem.paperid, null, sysdate, '"
				+ mid.toString()
				+ "' ,mem.membername ,null,null"
				+ " from temp_approve t, temp_person mem where mem.member_id = t.member_id"
				+ " and mem.member_type = t.member_type and (t.implsts is null or t.implsts = '1') and t.approvests=2 and mem.organizationid like '"
				+ tempMonthDTO.getOrganizationId() + "%'";
		if (null == app || "".equals(app)) {

		} else if ("1".equals(app)) {
			sql = sql + " and  t.member_type='1' ";
		} else if ("2".equals(app)) {
			sql = sql + " and  t.member_type='2' ";
		}
		param.put("executsql", sql);
		extendsDAO.insertAll(param);
		sql = "update temp_approve a  set a.implsts = 2 where exists (select 1 "
				+ " from temp_bill b  where b.mid = '"
				+ mid.toString()
				+ "'  and b.ap_id = a.approve_id)";
		param.put("executsql", sql);
		extendsDAO.updateAll(param);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InputStream findFileStream(TempMonthDTO tempMonthDTO) {
		/*
		 * TempBillExample example = new TempBillExample();
		 * example.createCriteria().andMidEqualTo(
		 * tempMonthDTO.getMid().longValue()); List<TempBill> rs =
		 * this.tempBillDAO.selectByExample(example); String sInputString = "";
		 * for (TempBill s : rs) { String a = s.getPaperid() + "," +
		 * s.getMembername() + "," + s.getSalmoney() + "\r\n"; sInputString =
		 * sInputString + a; }
		 */
		String sql = " select * from TEMP_BILL bill "
				+ " left join member_baseinfoview02 base "
				+ " on bill.member_id = base.member_id and bill.member_type = base.ds "
				+ " where  bill.MID = '" + tempMonthDTO.getMid().longValue()
				+ "' ";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		String sInputString = "";
		for (HashMap s : rs) {
			String a = s.get("PAPERID") + "," + s.get("MEMBERNAME") + ","
					+ s.get("SALMONEY") + "," + s.get("ADDRESS") + "\r\n";
			sInputString = sInputString + a;
		}
		try {
			sInputString = new String(sInputString.getBytes("GBK"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
				sInputString.getBytes());
		return tInputStringStream;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InputStream findMaFileStream(TempMonthDTO tempMonthDTO) {
		/*
		 * MaBillExample example = new MaBillExample();
		 * example.createCriteria().andMidEqualTo(
		 * tempMonthDTO.getMid().longValue()); List<MaBill> rs =
		 * this.maBillDAO.selectByExample(example); String sInputString = "";
		 * for (MaBill s : rs) { String a = s.getPaperid() + "," +
		 * s.getMembername() + "," + s.getSalmoney() + "\r\n"; sInputString =
		 * sInputString + a; }
		 */
		String sql = " select * from ma_bill bill "
				+ " left join member_baseinfoview02 base "
				+ " on bill.member_id = base.member_id and bill.member_type = base.ds "
				+ " where  bill.MID = '" + tempMonthDTO.getMid().longValue()
				+ "' ";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		String sInputString = "";
		for (HashMap s : rs) {
			String a = s.get("PAPERID") + "," + s.get("MEMBERNAME") + ","
					+ s.get("SALMONEY") + "," + s.get("ADDRESS") + "\r\n";
			sInputString = sInputString + a;
		}
		try {
			sInputString = new String(sInputString.getBytes("GBK"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
				sInputString.getBytes());
		return tInputStringStream;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String removeMaBillMonth(TempMonthDTO tempMonthDTO) {
		HashMap<String, String> param = new HashMap<String, String>();

		String sql = "select * from jz_medicalafter t "
				+ " where t.biz_status = '1' "
				+ " and (t.implsts is null or t.implsts = '1') "
				+ " and t.organization_id like '"
				+ tempMonthDTO.getOrganizationId() + "%'";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			return "存在没有发放的数据，不能够删除当前发放业务!";
		} else {

			String str1 = "";

			str1 = "update jz_medicalafter ap  set ap.implsts = 1 "
					+ " where exists (select 1  from ma_bill bill where bill.ap_id = ap.biz_id "
					+ " and bill.mid = '" + tempMonthDTO.getMid() + "')";

			param.put("executsql", str1);
			extendsDAO.insertAll(param);
			String str2 = "delete ma_bill bill where bill.mid = '"
					+ tempMonthDTO.getMid() + "'";
			param.put("executsql", str2);
			extendsDAO.insertAll(param);
			String str3 = "delete ma_month m where m.mid = '"
					+ tempMonthDTO.getMid() + "'";
			param.put("executsql", str3);
			extendsDAO.insertAll(param);
			return "删除成功";
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void saveGenMaBillData(TempMonthDTO tempMonthDTO, String app) {
		MaMonth record = new MaMonth();
		record.setMonth(tempMonthDTO.getMonth());
		record.setYear(tempMonthDTO.getYear());
		record.setOrganizationId(tempMonthDTO.getOrganizationId());
		BigDecimal mid = maMonthDAO.insert(record);
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "";

		sql = "insert into ma_bill (bill_id, member_id, member_type, "
				+ " salmoney, ap_id,  logo, familyno, paperid, "
				+ " bankaccount, opertime, mid,  membername, mastername, masterpaperid) "
				+ " select sq_account.nextval, t.member_id, "
				+ " t.member_type, t.pay_assist,  t.biz_id, " + " '"
				+ tempMonthDTO.getYear() + "年" + tempMonthDTO.getMonth()
				+ "月',  t.family_no,  t.id_card,  null, " + " sysdate, '"
				+ mid.toString() + "', t.name, null, null "
				+ " from jz_medicalafter t where  "
				+ " (t.implsts is null or t.implsts = '1') "
				+ " and t.biz_status = 1 " + " and t.organization_id like '"
				+ tempMonthDTO.getOrganizationId() + "%' ";

		if (null == app || "".equals(app)) {

		} else if ("1".equals(app)) {
			sql = sql + " and  t.member_type='1' ";
		} else if ("2".equals(app)) {
			sql = sql + " and  t.member_type='2' ";
		}

		param.put("executsql", sql);
		extendsDAO.insertAll(param);
		sql = "update jz_medicalafter a set a.implsts = 2 where exists (select 1 from ma_bill b where b.mid = '"
				+ mid.toString() + "'  and b.ap_id = a.biz_id)";
		param.put("executsql", sql);
		extendsDAO.updateAll(param);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String removeBillMonth(TempMonthDTO tempMonthDTO) {
		HashMap<String, String> param = new HashMap<String, String>();

		String sql = "select t.pay_total,t.pay_medicare,t.pay_assist, t.implsts, t.approve_id ,t.member_id,t.member_type ,t.applytime ,t.approvests,mem.membername,mem.familyno,mem.paperid,mem.relmaster  "
				+ " from temp_approve t, temp_person mem where mem.member_id = t.member_id "
				+ " and mem.member_type = t.member_type  and t.approvests ='2' and (t.implsts is null or t.implsts='1')   and mem.organizationid like '"
				+ tempMonthDTO.getOrganizationId() + "%' order by mem.familyno";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			return "存在没有发放的数据，不能够删除当前发放业务!";
		} else {

			String str1 = "update temp_approve ap set ap.implsts = 1 "
					+ "where exists (select 1 from temp_bill bill "
					+ "where bill.ap_id = ap.approve_id and bill.mid = '"
					+ tempMonthDTO.getMid() + "')";
			param.put("executsql", str1);
			extendsDAO.insertAll(param);
			String str2 = "delete temp_bill bill where bill.mid = '"
					+ tempMonthDTO.getMid() + "'";
			param.put("executsql", str2);
			extendsDAO.insertAll(param);
			String str3 = "delete temp_month m where m.mid = '"
					+ tempMonthDTO.getMid() + "'";
			param.put("executsql", str3);
			extendsDAO.insertAll(param);
			return "删除成功";
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempBillDTO> findUsedBills(String sql, int currentpage,
			String url) {
		List<TempBillDTO> list = new ArrayList<TempBillDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// MEMBER_ID MEMBER_TYPE MEMBERNAME PAPERID SALMONEY OPERTIME ORGNAME
		for (HashMap s : rs) {
			TempBillDTO e = new TempBillDTO();
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setSalmoney((BigDecimal) s.get("SALMONEY"));
			e.setOpertime((Date) s.get("OPERTIME"));
			e.setOrgname((String) s.get("ORGNAME"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempMonthDTO> findTempStat(String organizationId) {
		List<TempMonthDTO> list = new ArrayList<TempMonthDTO>();
		String sql = "select count(1) as rc, " + " sum(bill.salmoney) as zm, "
				+ " (select a.orgname " + " from manager_org a "
				+ " where a.organization_id = org.organization_id) as orgname "
				+ "  from temp_bill bill, temp_person p, manager_org org "
				+ " where bill.member_id = p.member_id "
				+ " and bill.member_type = p.member_type "
				+ " and p.organizationid like org.organization_id || '%' "
				+ " and (org.organization_id = '" + organizationId + "') "
				+ " group by org.organization_id";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempMonthDTO e = new TempMonthDTO();
			e.setMonth((String) s.get("ORGNAME"));
			e.setMid((BigDecimal) s.get("RC"));
			e.setRs((BigDecimal) s.get("ZM"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TempMonthDTO> findMaStat(String organizationId) {
		List<TempMonthDTO> list = new ArrayList<TempMonthDTO>();
		String sql = "select count(1) as rc, " + " sum(bill.salmoney) as zm, "
				+ " (select a.orgname " + " from manager_org a "
				+ " where a.organization_id = org.organization_id) as orgname "
				+ " from ma_bill bill, manager_org org "
				+ " where bill.familyno like org.organization_id || '%' "
				+ " and (org.organization_id = '" + organizationId + "') "
				+ " group by org.organization_id";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempMonthDTO e = new TempMonthDTO();
			e.setMonth((String) s.get("ORGNAME"));
			e.setMid((BigDecimal) s.get("RC"));
			e.setRs((BigDecimal) s.get("ZM"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempSecondDTO> findSecnodmembers(String sql, int currentpage,
			String url) {
		List<TempSecondDTO> list = new ArrayList<TempSecondDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// FAMILYNO MEMBERNAME PAPERID PAY_TOTAL PAY_MEDICARE
		// PAY_OUTMEDICARE PAY_ASSIST MEDICARE_TYPE PAYSELF SALMONEY
		for (HashMap s : rs) {
			TempSecondDTO e = new TempSecondDTO();
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));

			BigDecimal paytotal = (BigDecimal) s.get("PAY_TOTAL");
			paytotal = paytotal.setScale(2, BigDecimal.ROUND_HALF_UP);
			e.setPayTotal(paytotal);

			BigDecimal payself = (BigDecimal) s.get("PAYSELF");
			payself = payself.setScale(2, BigDecimal.ROUND_HALF_UP);
			e.setPaySelf(payself);

			BigDecimal payciassist = (BigDecimal) s.get("PAY_CIASSIST");
			payciassist = payciassist.setScale(2, BigDecimal.ROUND_HALF_UP);
			e.setPayCIAssist(payciassist);

			BigDecimal salmoney = (BigDecimal) s.get("SALMONEY");
			salmoney = salmoney.setScale(2, BigDecimal.ROUND_HALF_UP);
			e.setSalmoney(salmoney);

			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("DS"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TempSecondDTO findToplineByOrganno(String organizationId) {
		TempSecondDTO s = new TempSecondDTO();
		String sql = "select max(line) as line from top_line t where t.top_type = '5' and  instr('a'||'"
				+ organizationId + "' ,'a'||t.organization_id)>0";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		BigDecimal line = (BigDecimal) rs.get(0).get("LINE");
		s.setTopline(line);
		return s;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findSecnodmembersinfo(String sql) {
		sql = "select count(1) rs, sum(salmoney) as zm from ( " + sql + ")";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		BigDecimal rss = (BigDecimal) rs.get(0).get("RS");
		BigDecimal zm = (BigDecimal) rs.get(0).get("ZM");
		if (null == zm) {
			zm = new BigDecimal(0);
		}
		zm = zm.setScale(2, BigDecimal.ROUND_HALF_UP);
		String result = "救助人数：" + rss + "（人）" + "总救助金 ：" + zm + "（元）";
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String saveSecondApproves(TempSecondDTO tempSecondDTO, String app,
			String minpay) {
		String organizationId = tempSecondDTO.getOrganizationId();
		String jwhere = "";
		String jwhere2 = "";
		if (null == app || "".equals(app)) {

		} else if ("1".equals(app)) {
			jwhere = jwhere + " and m.ds='1' ";
		} else if ("2".equals(app)) {
			jwhere = jwhere + " and m.ds='2' ";
		}
		if (null == minpay || "".equals(minpay)) {
		} else {
			jwhere2 = jwhere2 + "and decode (sign(((payself -"
					+ tempSecondDTO.getSalscope() + ") * "
					+ tempSecondDTO.getSalpercent() + ")-"
					+ tempSecondDTO.getTopline() + ") ,1,"
					+ tempSecondDTO.getTopline() + ",0 ,"
					+ tempSecondDTO.getTopline() + ",-1, ((payself - "
					+ tempSecondDTO.getSalscope() + ") * "
					+ tempSecondDTO.getSalpercent() + ") ) >= "
					+ Long.parseLong(minpay);
		}
		String sql = "insert into second_approve  (approve_id, familyno, membername, paperid, pay_total, "
				+ "pay_medicare, pay_outmedicare, pay_assist, medicare_type, payself, salmoney, approvests, sts, flag, member_id,member_type) "
				+ " select hibernate_sequence.nextval, FAMILYNO, MEMBERNAME, PAPERID, "
				+ " PAY_TOTAL,  PAY_MEDICARE, PAY_OUTMEDICARE, PAY_ASSIST, MEDICARE_TYPE,  PAYSELF,   decode (sign(((payself - "
				+ tempSecondDTO.getSalscope()
				+ ") * "
				+ tempSecondDTO.getSalpercent()
				+ ")-"
				+ tempSecondDTO.getTopline()
				+ ") ,1,"
				+ tempSecondDTO.getTopline()
				+ ",0 ,"
				+ tempSecondDTO.getTopline()
				+ ",-1, ((payself - "
				+ tempSecondDTO.getSalscope()
				+ ") * "
				+ tempSecondDTO.getSalpercent()
				+ ") ) as salmoney ,'1','1','1' ,member_id,ds from (select m.familyno, "
				+ " m.membername,  m.paperid,  m.member_id, m.ds,  pay.pay_total, pay.pay_medicare, "
				+ " pay.pay_outmedicare, pay.pay_assist, pay.medicare_type, "
				+ " decode(pay.medicare_type, 0, ((pay.pay_total * 0.8) - pay.pay_assist), "
				+ " (pay.pay_total - pay.pay_medicare - pay.pay_outmedicare - "
				+ " pay.pay_assist - pay.pay_ciassist)) as payself  from MEMBER_BASEINFOVIEW02 m, "
				+ " (select mem.member_id,  mem.ds, max(p.medicare_type) medicare_type , sum(p.pay_total) pay_total, "
				+ " sum(p.pay_medicare) pay_medicare,  sum(p.pay_outmedicare) pay_outmedicare, "
				+ " sum(p.pay_assist) pay_assist, sum(p.pay_ciassist) pay_ciassist  from payview p, MEMBER_BASEINFOVIEW02 mem "
				+ " where mem.member_id = p.member_id and mem.ds = p.member_type "
				+ " and p.oper_time between "
				+ " (to_date('"
				+ tempSecondDTO.getYear()
				+ "-01-01:00:00:00', 'YYYY-MM-DD:HH24:MI:SS')) and "
				+ " (to_date('"
				+ tempSecondDTO.getYear()
				+ "-12-31:23:59:59', 'YYYY-MM-DD:HH24:MI:SS')) and mem.familyno like '"
				+ organizationId
				+ "%' "
				+ " group by mem.member_id, mem.ds) pay "
				+ " where pay.member_id = m.member_id  and pay.ds = m.ds "
				+ jwhere
				+ " order by m.familyno) k where payself > "
				+ tempSecondDTO.getSalscope() + "" + jwhere2;
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		extendsDAO.insertAll(param);
		return "1";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SecondApproveDTO> findSecondApproves(String sql) {
		List<SecondApproveDTO> list = new ArrayList<SecondApproveDTO>();
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		// APPROVE_ID FAMILYNO MEMBERNAME PAPERID PAY_TOTAL PAY_MEDICARE
		// PAY_OUTMEDICARE PAY_ASSIST MEDICARE_TYPE PAYSELF SALMONEY APPROVESTS
		// STS FLAG
		for (HashMap s : rs) {
			SecondApproveDTO e = new SecondApproveDTO();
			e.setApproveId((BigDecimal) s.get("APPROVE_ID"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setMedicareType((BigDecimal) s.get("MEDICARE_TYPE"));
			e.setPayself((BigDecimal) s.get("PAYSELF"));
			e.setSalmoney((BigDecimal) s.get("SALMONEY"));
			e.setApprovests((String) s.get("APPROVESTS"));
			e.setSts((String) s.get("STS"));
			e.setFlag((String) s.get("FLAG"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SecondApproveDTO> findSecondApproves(String sql,
			int currentpage, String url) {
		List<SecondApproveDTO> list = new ArrayList<SecondApproveDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// APPROVE_ID FAMILYNO MEMBERNAME PAPERID PAY_TOTAL PAY_MEDICARE
		// PAY_OUTMEDICARE PAY_ASSIST MEDICARE_TYPE PAYSELF SALMONEY APPROVESTS
		// STS FLAG
		for (HashMap s : rs) {
			SecondApproveDTO e = new SecondApproveDTO();
			e.setApproveId((BigDecimal) s.get("APPROVE_ID"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setMedicareType((BigDecimal) s.get("MEDICARE_TYPE"));
			e.setPayself((BigDecimal) s.get("PAYSELF"));
			e.setSalmoney((BigDecimal) s.get("SALMONEY"));
			e.setApprovests((String) s.get("APPROVESTS"));
			e.setSts((String) s.get("STS"));
			e.setFlag((String) s.get("FLAG"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			list.add(e);
		}
		return list;
	}

	public SecondApproveDTO saveSecondApprove(SecondApproveDTO secondApproveDTO) {
		SecondApprove record = new SecondApprove();
		record.setApproveId(secondApproveDTO.getApproveId());
		record.setApprovests(secondApproveDTO.getApprovests());
		secondApproveDAO.updateByPrimaryKeySelective(record);
		return secondApproveDTO;
	}

	public void removeSecondApproves(String organizationId) {
		SecondApproveExample example = new SecondApproveExample();
		example.createCriteria().andFamilynoLike(organizationId + "%");
		secondApproveDAO.deleteByExample(example);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SecondBatchDTO> findSecondBatchs(SecondBatchDTO secondBatchDTO) {
		List<SecondBatchDTO> list = new ArrayList<SecondBatchDTO>();
		String organizationId = secondBatchDTO.getOrganizationId();
		int year = secondBatchDTO.getYear();
		String sql = "select * from second_batch t where   t.oper_time between (to_date('"
				+ year
				+ "-01-01:00:00:00', 'YYYY-MM-DD:HH24:MI:SS')) and "
				+ " (to_date('"
				+ year
				+ "-12-31:23:59:59', 'YYYY-MM-DD:HH24:MI:SS'))"
				+ "and t.organization_id='" + organizationId + "'";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		// /BATCH_ID ORGANZIATION_ID OPER_TIME

		for (HashMap s : rs) {
			SecondBatchDTO e = new SecondBatchDTO();
			e.setBatchId((BigDecimal) s.get("BATCH_ID"));
			e.setOperTime((Date) s.get("OPER_TIME"));
			e.setOrganizationId((String) s.get("ORGANZIATION_ID"));
			list.add(e);
		}
		return list;
	}

	public String saveSecondbills(String organizationId) {

		SecondBatch b = new SecondBatch();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR) - 1;
		// 临时修改-20140106
		// b.setOperTime(new Date());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
			b.setOperTime(sdf.parse(year + "-12-31 23:59:59"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b.setOrganizationId(organizationId);
		b.setBatchId(secondBatchDAO.insert(b));

		String sql = "  insert into second_bill "
				+ " (bill_id, batch_id, member_id, member_type, paperid, membername,familyno, salmoney, oper_time) "
				+ " select hibernate_sequence.nextval ,'"
				+ b.getBatchId()
				+ "',tp.member_id,tp.member_type ,tp.paperid  ,tp.membername  ,tp.familyno  ,tp.salmoney, "
				// + " sysdate "
				+ " to_date('" + year
				+ "-12-31 23:59:59', 'YYYY/MM/DD:HH24:MI:SS')"
				+ " from second_approve tp  where tp.familyno like '"
				+ organizationId + "%' and tp.approvests=1";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		extendsDAO.insertAll(param);
		return "保存成功";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SecondBatchDTO> findSecondBatchs(String organizationId, int year) {
		List<SecondBatchDTO> list = new ArrayList<SecondBatchDTO>();
		String sql = "select sum(c.salmoney) as zm, count(*) as hs, "
				+ " c.batch_id, b.oper_time,  to_char( b.oper_time, 'yyyy') as year "
				+ " from second_bill c, second_batch b where b.batch_id = c.batch_id "
				+ " and b.organization_id = '" + organizationId
				+ "' and to_char(b.oper_time, 'yyyy') = '" + year + "' "
				+ " group by c.batch_id, b.oper_time";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			SecondBatchDTO e = new SecondBatchDTO();
			e.setBatchId((BigDecimal) s.get("BATCH_ID"));
			e.setOperTime((Date) s.get("OPER_TIME"));
			e.setOrganizationId((String) s.get("ORGANZIATION_ID"));
			e.setHs((BigDecimal) s.get("HS"));
			e.setZm((BigDecimal) s.get("ZM"));
			e.setYear(new Integer(s.get("YEAR").toString()));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SecondBatchDTO> findSecondBatchByOrgID(String organizationId) {
		List<SecondBatchDTO> list = new ArrayList<SecondBatchDTO>();
		String sql = " select b.batch_id, b.oper_time, to_char(b.oper_time, 'yyyy') as year "
				+ "from second_batch b where b.organization_id = '220110'";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			SecondBatchDTO e = new SecondBatchDTO();
			e.setBatchId((BigDecimal) s.get("BATCH_ID"));
			e.setYear(new Integer(s.get("YEAR").toString()));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InputStream findFileStreamSecond(SecondBatchDTO secondBatchDTO) {
		/*
		 * SecondBillExample example = new SecondBillExample();
		 * example.createCriteria
		 * ().andBatchIdEqualTo(secondBatchDTO.getBatchId()); List<SecondBill>
		 * rs = secondBillDAO.selectByExample(example); String sInputString =
		 * ""; for (SecondBill s : rs) { String a = s.getPaperid() + "," +
		 * s.getMembername() + "," + s.getSalmoney() + "\r\n"; sInputString =
		 * sInputString + a; }
		 */
		String sql = " select decode(bill.member_type,'1','城市','2','农村',null,'来源不详')as SOUCE,bill.paperid,bill.membername,bill.salmoney,base.address"
				+ " from second_bill bill "
				+ " left join member_baseinfoview02 base "
				+ " on bill.member_id = base.member_id and bill.member_type = base.ds "
				+ " where  bill.batch_id = '"
				+ secondBatchDTO.getBatchId()
				+ "' ";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		String sInputString = "";
		for (HashMap s : rs) {
			String a = s.get("PAPERID") + "," + s.get("MEMBERNAME") + ","
					+ s.get("SALMONEY") + "," + s.get("ADDRESS") + ","
					+ s.get("SOUCE") + "\r\n";
			sInputString = sInputString + a;
		}
		try {
			sInputString = new String(sInputString.getBytes("GBK"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(
				sInputString.getBytes());
		return tInputStringStream;
	}

	public void removeSecondBill(SecondBatchDTO secondBatchDTO) {
		SecondBillExample example = new SecondBillExample();
		example.createCriteria().andBatchIdEqualTo(secondBatchDTO.getBatchId());
		this.secondBillDAO.deleteByExample(example);
		this.secondBatchDAO.deleteByPrimaryKey(secondBatchDTO.getBatchId());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<SecondBillDTO> findSecondBills(String sql, int currentpage,
			String url) {
		List<SecondBillDTO> list = new ArrayList<SecondBillDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			SecondBillDTO e = new SecondBillDTO();
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setSalmoney((BigDecimal) s.get("SALMONEY"));
			list.add(e);
		}
		return list;
	}

	public TempDTO saveAfteryn(TempDTO tempDTO) {
		JzMedicalafter record = new JzMedicalafter();
		record.setBizId(tempDTO.getApproveId().intValue());
		record.setBizStatus(tempDTO.getApprovests());
		record.setOperTime(new Date());
		jzMedicalafterDAO.updateByPrimaryKeySelective(record);
		return tempDTO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<TempBillDTO> findUsedMaBills(String sql, int currentpage,
			String url) {
		List<TempBillDTO> list = new ArrayList<TempBillDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// MEMBER_ID MEMBER_TYPE MEMBERNAME PAPERID SALMONEY OPERTIME ORGNAME
		for (HashMap s : rs) {
			TempBillDTO e = new TempBillDTO();
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setSalmoney((BigDecimal) s.get("SALMONEY"));
			e.setOpertime((Date) s.get("OPERTIME"));
			e.setOrgname((String) s.get("ORGNAME"));
			list.add(e);
		}
		return list;
	}

	public TempRuleDTO findTempRuleByOrgid(String organizationId) {
		TempRuleDTO tr = new TempRuleDTO();
		TempCalcRuleExample example = new TempCalcRuleExample();
		example.createCriteria().andOrganizationIdEqualTo(organizationId);
		List<TempCalcRule> rs = tempCalcRuleDAO.selectByExample(example);
		if (null != rs && rs.size() == 1) {
			TempCalcRule tcr = rs.get(0);
			tr.setOrganizationId(tcr.getOrganizationId());
			tr.setPersonType(tcr.getPersonType());
			tr.setRuletempId(tcr.getRuletempId());
			tr.setScale(tcr.getScale());
			tr.setPersonTypeNj(tcr.getPersonTypeNj());
			tr.setScaleNj(tcr.getScaleNj());
			tr.setNscale(tcr.getnScale());
		} else {
			tr.setOrganizationId(organizationId);
		}
		return tr;
	}

	public TempRuleDTO saveTempRule(TempRuleDTO tr) {
		TempCalcRule tcr = new TempCalcRule();
		tcr.setOrganizationId(tr.getOrganizationId());
		tcr.setPersonType(tr.getPersonType());
		tcr.setRuletempId(tr.getRuletempId());
		tcr.setScale(tr.getScale().divide(new BigDecimal(100)));
		tcr.setPersonTypeNj(tr.getPersonTypeNj());
		tcr.setScaleNj(tr.getScaleNj().divide(new BigDecimal(100)));
		tcr.setnScale(tr.getNscale().divide(new BigDecimal(100)));
		if (null == tr.getRuletempId()) {
			this.tempCalcRuleDAO.insertSelective(tcr);
		} else {
			this.tempCalcRuleDAO.updateByPrimaryKeySelective(tcr);
		}
		return tr;
	}

	@Override
	public void saveJzMedicalafterfiles(List<JzMedicalafterfileDTO> files) {
		for (JzMedicalafterfileDTO s : files) {
			JzMedicalafterfile e = new JzMedicalafterfile();
			e.setBizId(s.getBizId());
			e.setFilename(s.getFilename());
			e.setRealfilename(s.getRealfilename());
			e.setRealpath(s.getRealpath());
			jzMedicalafterfileDAO.insert(e);
		}

	}

	@Override
	public List<JzMedicalafterfileDTO> findJzMedicalafterfiles(String bizId) {
		List<JzMedicalafterfileDTO> list = new ArrayList<JzMedicalafterfileDTO>();
		JzMedicalafterfileExample example = new JzMedicalafterfileExample();
		example.createCriteria().andBizIdEqualTo(new BigDecimal(bizId));
		List<JzMedicalafterfile> rs = jzMedicalafterfileDAO
				.selectByExample(example);
		for (JzMedicalafterfile s : rs) {
			JzMedicalafterfileDTO e = new JzMedicalafterfileDTO();
			e.setBizId(s.getBizId());
			e.setFilename(s.getFilename());
			e.setRealfilename(s.getRealfilename());
			e.setRealpath(s.getRealpath());
			e.setFileId(s.getFileId());
			list.add(e);
		}
		return list;
	}

	@Override
	public void saveTempApprovefiles(List<TempApprovefileDTO> files) {
		for (TempApprovefileDTO s : files) {
			TempApprovefile e = new TempApprovefile();
			e.setBizId(s.getBizId());
			e.setFilename(s.getFilename());
			e.setRealfilename(s.getRealfilename());
			e.setRealpath(s.getRealpath());
			tempApprovefileDAO.insert(e);
		}

	}

	@Override
	public List<TempApprovefileDTO> findTempApprovefiles(String bizId) {
		List<TempApprovefileDTO> list = new ArrayList<TempApprovefileDTO>();
		TempApprovefileExample example = new TempApprovefileExample();
		example.createCriteria().andBizIdEqualTo(new BigDecimal(bizId));
		List<TempApprovefile> rs = tempApprovefileDAO.selectByExample(example);
		for (TempApprovefile s : rs) {
			TempApprovefileDTO e = new TempApprovefileDTO();
			e.setBizId(s.getBizId());
			e.setFilename(s.getFilename());
			e.setRealfilename(s.getRealfilename());
			e.setRealpath(s.getRealpath());
			e.setFileId(s.getFileId());
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void removeSecondAll(String organizationId, int year) {
		HashMap<String, String> param = new HashMap<String, String>();
		String sql1 = "delete from SECOND_APPROVE app "
				+ " where app.familyno like '" + organizationId + "%'";
		param.put("executsql", sql1);
		extendsDAO.updateAll(param);
		String sql2 = "delete from SECOND_BILL bill " + " where exists "
				+ " (select b.batch_id " + " from SECOND_BATCH b "
				+ " where b.organization_id = '" + organizationId + "'"
				+ " and b.oper_time between (to_date('" + year
				+ "-01-01:00:00:00', 'YYYY-MM-DD:HH24:MI:SS')) and "
				+ " (to_date('" + year
				+ "-12-31:23:59:59', 'YYYY-MM-DD:HH24:MI:SS')) "
				+ " and b.batch_id = bill.batch_id) ";
		param.put("executsql", sql2);
		extendsDAO.updateAll(param);
		String sql3 = "delete from SECOND_BATCH bat where bat.organization_id ='"
				+ organizationId
				+ "'"
				+ " and bat.oper_time between (to_date('"
				+ year
				+ "-01-01:00:00:00', 'YYYY-MM-DD:HH24:MI:SS')) and "
				+ " (to_date('"
				+ year
				+ "-12-31:23:59:59', 'YYYY-MM-DD:HH24:MI:SS'))";
		param.put("executsql", sql3);
		extendsDAO.updateAll(param);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TempMonthDTO findMaBill(TempMonthDTO tempMonthDTO) {
		List<TempMonthDTO> list = new ArrayList<TempMonthDTO>();
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "select *  from ma_month m, ( "
				+ " select count(1) as rs, sum(b.salmoney) as salmoney, "
				+ " b.mid as mmid from ma_bill b group by b.mid) bb where m.organization_id = '"
				+ tempMonthDTO.getOrganizationId() + "' "
				+ " and m.mid=bb.mmid and m.mid='" + tempMonthDTO.getMid()
				+ "' ";

		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempMonthDTO e = new TempMonthDTO();
			e.setMid((BigDecimal) s.get("MID"));
			e.setYear((String) s.get("YEAR"));
			e.setMonth((String) s.get("MONTH"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setRs((BigDecimal) s.get("RS"));
			e.setTotalmoney((BigDecimal) s.get("SALMONEY"));
			e.setYm(e.getYear() + "年" + e.getMonth() + "月");
			list.add(e);
		}
		return list.get(0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TempMonthDTO findtempBill(TempMonthDTO tempMonthDTO) {
		List<TempMonthDTO> list = new ArrayList<TempMonthDTO>();
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "select *  from temp_month m, ("
				+ "select count(1) as rs, sum(b.salmoney) as salmoney, "
				+ "b.mid as mmid from temp_bill b group by b.mid) bb where m.organization_id = '"
				+ tempMonthDTO.getOrganizationId() + "' "
				+ "and m.mid=bb.mmid and m.mid='" + tempMonthDTO.getMid()
				+ "' ";
		;
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempMonthDTO e = new TempMonthDTO();
			e.setMid((BigDecimal) s.get("MID"));
			e.setYear((String) s.get("YEAR"));
			e.setMonth((String) s.get("MONTH"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setRs((BigDecimal) s.get("RS"));
			e.setTotalmoney((BigDecimal) s.get("SALMONEY"));
			e.setYm(e.getYear() + "年" + e.getMonth() + "月");
			list.add(e);
		}
		return list.get(0);
	}

	@Override
	public TempDTO findPayview01(TempDTO tempDTO) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		Payview01Example example = new Payview01Example();
		example.createCriteria().andMemberIdEqualTo(tempDTO.getMemberId())
				.andMemberTypeEqualTo(tempDTO.getMemberType());
		example.setOrderByClause("oper_time desc");
		List<Payview01> rs = payview01DAO.selectByExample(example);
		if (rs.size() > 0) {
			for (Payview01 s : rs) {
				TempDTO e = new TempDTO();
				e.setApproveId(s.getBizId().longValue());
				e.setMemberId(s.getMemberId());
				e.setMemberType(s.getMemberType());
				e.setPaperid(s.getIdCard());
				e.setBiztype(s.getBiztype());
				list.add(e);
			}
			return list.get(0);
		} else {
			return new TempDTO();
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public List<TempDTO> findPayviews(TempDTO tempDTO) {
		Calendar c = new GregorianCalendar();// 新建日期对象
		int year = c.get(Calendar.YEAR);// 获取年份
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, String> param = new HashMap<String, String>();
		String jwhere = "";
		if ((tempDTO.getPaperid() == null || "".equals(tempDTO.getPaperid()))
				&& (tempDTO.getFamilyno() != null && !"".equals(tempDTO
						.getFamilyno()))) {
			jwhere = jwhere + " and mem.familyno = '" + tempDTO.getFamilyno()
					+ "' ";
		} else if ((tempDTO.getFamilyno() == null || "".equals(tempDTO
				.getFamilyno()))
				&& (tempDTO.getPaperid() != null && !"".equals(tempDTO
						.getPaperid()))) {
			jwhere = jwhere + " and mem.paperid18 = '" + tempDTO.getPaperid()
					+ "' ";
		} else if ((tempDTO.getFamilyno() != null && !"".equals(tempDTO
				.getFamilyno()))
				&& (tempDTO.getPaperid() != null && !"".equals(tempDTO
						.getPaperid()))) {
			jwhere = jwhere + " and mem.paperid18 = '" + tempDTO.getPaperid()
					+ "' ";
		}
		String sql = "select mem.membername,pay.* from payview01 pay "
				+ " left join member_baseinfoview02 mem on mem.member_id = pay.member_id and mem.ds=pay.member_type "
				+ " where 1=1 " + jwhere
				+ " and to_char(pay.oper_time,'yyyy')='" + year
				+ "' order by pay.oper_time desc ";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setApproveId(new BigDecimal(s.get("BIZ_ID").toString())
					.longValue());
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setPaperid((String) s.get("ID_CARD"));
			e.setBiztype((String) s.get("BIZTYPE"));
			e.setPayTotal(new BigDecimal(s.get("PAY_TOTAL").toString()));
			e.setPayMedicare(new BigDecimal(s.get("PAY_MEDICARE").toString()));
			e.setPayOutmedicare(new BigDecimal(s.get("PAY_OUTMEDICARE")
					.toString()));
			e.setPayAssist(new BigDecimal(s.get("PAY_ASSIST").toString()));
			e.setPayCIAssist(new BigDecimal(s.get("PAY_CIASSIST").toString()));
			e.setMedicareType((String) s.get("MEDICARE_TYPE"));
			e.setOpertime((Date) s.get("OPER_TIME"));
			String biztype = (String) s.get("BIZTYPE");
			String assistflag = ((String) s.get("ASSIST_FLAG")).trim();
			if ("biz".equals(biztype)) {
				e.setAssistFlag("结算");
			} else if ("ma".equals(biztype)) {
				if ("-1".equals(assistflag)) {
					e.setAssistFlag("乡镇录入");
				} else if ("1".equals(assistflag)) {
					e.setAssistFlag("区县同意");
				} else if ("0".equals(assistflag)) {
					e.setAssistFlag("区县不同意");
				}
			} else if ("ta".equals(assistflag)) {
				if ("1".equals(assistflag)) {
					e.setAssistFlag("乡镇审批");
				} else if ("2".equals(assistflag)) {
					e.setAssistFlag("区县审批");
				} else if ("3".equals(assistflag)) {
					e.setAssistFlag("不同意");
				}
			}
			e.setBizType((String) s.get("BIZ_TYPE"));
			e.setMembername((String) s.get("MEMBERNAME"));
			list.add(e);
		}
		return list;
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public JzMedicalafterRuleDTO findMedicalafterRule(TempDTO tempDTO) {
		String sql = "";
		String organizationId = tempDTO.getOrganizationId();
		JzMedicalafterRuleDTO e = new JzMedicalafterRuleDTO();
		if (null == organizationId || "".equals(organizationId)) {
		} else {
			organizationId = organizationId.substring(0, 6);
			HashMap<String, String> param = new HashMap<String, String>();
			sql = "select rl.rule_id, rl.organization_id, rl.mzts_top_line, rl.mzts_sl, rl.mzts_scale, rl.zy_sl, rl.zy_scale1, rl.zy_m1, rl.zy_scale2, rl.zy_m2, rl.zy_scale3, rl.zy_m3, rl.zy_scale4, rl.zy_m4, rl.zy_scale5, rl.c_nc_scale, rl.c_cs_scale, rl.wc_nc_scale, rl.wc_cs_scale, rl.create_time, rl.update_time, rl.flag, rl.zy_top_line, rl.WBH_SCALE, rl.SANWU_SCALE from jz_medicalafter_rule rl where rl.organization_id = '"
					+ organizationId + "' and rl.flag='1'";
			param.put("executsql", sql);
			List<HashMap> rs = extendsDAO.queryAll(param);
			if (null != rs && rs.size() > 0) {
				HashMap s = rs.get(0);
				e.setRuleId(new BigDecimal(s.get("RULE_ID").toString()));
				e.setMztsTopLine(new BigDecimal(s.get("MZTS_TOP_LINE")
						.toString()));
				e.setMztsSl(new BigDecimal(s.get("MZTS_SL").toString()));
				e.setMztsScale(new BigDecimal(s.get("MZTS_SCALE").toString()));
				e.setZySl(new BigDecimal(s.get("ZY_SL").toString()));
				e.setZyScale1(new BigDecimal(s.get("ZY_SCALE1").toString()));
				e.setZyM1(new BigDecimal(s.get("ZY_M1").toString()));
				e.setZyScale2(new BigDecimal(s.get("ZY_SCALE2").toString()));
				e.setZyM2(new BigDecimal(s.get("ZY_M2").toString()));
				e.setZyScale3(new BigDecimal(s.get("ZY_SCALE3").toString()));
				e.setZyM3(new BigDecimal(s.get("ZY_M3").toString()));
				e.setZyScale4(new BigDecimal(s.get("ZY_SCALE4").toString()));
				e.setZyM4(new BigDecimal(s.get("ZY_M4").toString()));
				e.setZyScale5(new BigDecimal(s.get("ZY_SCALE5").toString()));
				e.setcNcScale(new BigDecimal(s.get("C_NC_SCALE").toString()));
				e.setcCsScale(new BigDecimal(s.get("C_CS_SCALE").toString()));
				e.setWcNcScale(new BigDecimal(s.get("WC_NC_SCALE").toString()));
				e.setWcCsScale(new BigDecimal(s.get("WC_CS_SCALE").toString()));
				e.setFlag(s.get("FLAG").toString());
				e.setZyTopLine(new BigDecimal(s.get("ZY_TOP_LINE").toString()));
				e.setWbhScale(new BigDecimal(s.get("WBH_SCALE").toString()));
				e.setSanwuScale(new BigDecimal(s.get("SANWU_SCALE").toString()));
			}
		}
		return e;
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public String findValBiz(TempDTO tempDTO) {
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "select valmodifybiz('" + tempDTO.getMemberId() + "','"
				+ tempDTO.getMemberType() + "','" + tempDTO.getApproveId()
				+ "','" + tempDTO.getBizType() + "') as r from dual";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		return rs.get(0).get("R").toString();
	}

	@Override
	public int findMaAppCount(TempDTO tempDTO) {
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "select * from jz_medicalafter af where af.member_id='"
				+ tempDTO.getMemberId() + "' and af.member_type = '"
				+ tempDTO.getMemberType()
				+ "' and af.biz_status='-1' and af.assist_type='2' ";
		param.put("executsql", sql);
		int count = extendsDAO.queryCnt(param);
		return count;
	}

	@Override
	public int findTaAppCount(TempDTO tempDTO) {
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "select * from temp_approve app where app.member_id='"
				+ tempDTO.getMemberId() + "' and app.member_type = '"
				+ tempDTO.getMemberType() + "' and app.approvests='1' ";
		param.put("executsql", sql);
		int count = extendsDAO.queryCnt(param);
		return count;
	}

	@SuppressWarnings("rawtypes")
	public List<TempDTO> findAfterannexadd(String sql, int currentpage,
			String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setApproveId(new Long(s.get("BIZ_ID").toString()));
			e.setName((String) s.get("NAME"));
			e.setAddress((String) s.get("FAMILY_ADDRESS"));
			e.setPaperid((String) s.get("ID_CARD"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setFamilyno((String) s.get("FAMILY_NO"));
			e.setAssistype((String) s.get("ASSIST_TYPE"));
			e.setPersonstate((String) s.get("PERSONSTATE"));
			e.setHospitalname((String) s.get("HOSPITAL_NAME"));
			e.setDiagnoseName((String) s.get("DIAGNOSE_NAME"));
			e.setBizStatus((String) s.get("BIZ_STATUS"));
			e.setAssistTypeM((String) s.get("ASSIST_TYPE_BASE"));
			e.setAssistTypex((String) s.get("ASSIST_TYPEX_BASE"));
			e.setAssistype((String) s.get("ASSIST_TYPE"));
			if (!"".equals(e.getAssistTypeM()) && null != e.getAssistTypeM()) {
				e.setA1(e.getAssistTypeM().substring(0, 1));
				e.setA2(e.getAssistTypeM().substring(1, 2));
				e.setA3(e.getAssistTypeM().substring(2, 3));
				e.setA4(e.getAssistTypeM().substring(3, 4));
				e.setA5(e.getAssistTypeM().substring(4, 5));
			}
			if (!"".equals(e.getAssistTypex()) && null != e.getAssistTypex()) {
				e.setA6(e.getAssistTypex().substring(0, 1));
				e.setA7(e.getAssistTypex().substring(1, 2));
				e.setA8(e.getAssistTypex().substring(2, 3));
				e.setA9(e.getAssistTypex().substring(3, 4));
				e.setA10(e.getAssistTypex().substring(4, 5));
				e.setA11(e.getAssistTypex().substring(5, 6));
			}
			e.setAssistTypeTxt(genAssistype(e));
			list.add(e);
		}
		return list;
	}

	public TempDTO findMAmemberinfo(TempDTO tempDTO) {
		TempDTO e = new TempDTO();
		Integer bizId = new Integer(tempDTO.getApproveId().intValue());
		JzMedicalafter a = jzMedicalafterDAO.selectByPrimaryKey(bizId);
		e.setBizid(Long.valueOf(a.getBizId()));
		e.setApproveId(tempDTO.getApproveId());
		e.setMemberId(a.getMemberId());
		e.setMemberType(a.getMemberType());
		e.setSsn(a.getSsn());
		e.setMembername(a.getName());
		e.setSex(a.getSex());
		e.setAddress(a.getFamilyAddress());
		e.setFamilyno(a.getFamilyNo());
		e.setPaperid(a.getIdCard());
		e.setAssistTypeM(a.getAssistTypeM());
		e.setAssistTypex(a.getAssistTypex());
		e.setHospitalname(a.getHospitalName());
		e.setDiagnoseName(a.getDiagnoseName());
		e.setAssistType(a.getAssistType());
		e.setMedicareType(a.getMedicareType());
		e.setPayAssist(a.getPayAssist());
		e.setPayTotal(a.getPayTotal());
		e.setPayMedicare(a.getPayMedicare());
		e.setPayOutmedicare(a.getPayOutmedicare());
		e.setPayCIAssist(a.getPayCiassist());
		e.setSumMedicareScope(a.getSumMedicarescope());
		e.setPaySumAssistIn(a.getPaySumassistIn());
		e.setPaySumAssistOut(a.getPaySumassistOut());
		e.setPayPreSumAssistScopeIn(a.getSumPreAssitscope());
		e.setPaySumAssistScopeIn(a.getSumAssitscope());
		e.setBegintime(a.getBeginTime());
		e.setEndtime(a.getEndTime());
		e.setSystime(new Date());
		e.setOpertime(a.getOperTime());
		e.setSsn(a.getSsn());
		return e;
	}

	@SuppressWarnings("rawtypes")
	public List<TempDTO> findTempannexadd(String sql, int currentpage,
			String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			e.setApproveId(new Long(s.get("APPROVE_ID").toString()));
			e.setName((String) s.get("MEMBERNAME"));
			e.setAddress((String) s.get("ADDRESS"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setPayMedicare((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPayOutmedicare((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPayAssist((BigDecimal) s.get("PAY_ASSIST"));
			e.setPayTotal((BigDecimal) s.get("PAY_TOTAL"));
			e.setMemberId((String) s.get("MEMBER_ID"));
			e.setMemberType((String) s.get("MEMBER_TYPE"));
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setPersonstate((String) s.get("PERSONSTATE"));
			e.setHospitalname((String) s.get("HOSPITALNAME"));
			e.setDiagnoseName((String) s.get("DIAGNOSE_NAME"));
			e.setBizStatus((String) s.get("APPROVESTS"));
			e.setAssistype((String) s.get("ASSIST_TYPE"));
			e.setAssistTypex((String) s.get("ASSIST_TYPEX"));
			if (!"".equals(e.getAssistype()) && null != e.getAssistype()) {
				e.setA1(e.getAssistype().substring(0, 1));
				e.setA2(e.getAssistype().substring(1, 2));
				e.setA3(e.getAssistype().substring(2, 3));
				e.setA4(e.getAssistype().substring(3, 4));
				e.setA5(e.getAssistype().substring(4, 5));
			}
			if (!"".equals(e.getAssistTypex()) && null != e.getAssistTypex()) {
				e.setA6(e.getAssistTypex().substring(0, 1));
				e.setA7(e.getAssistTypex().substring(1, 2));
				e.setA8(e.getAssistTypex().substring(2, 3));
				e.setA9(e.getAssistTypex().substring(3, 4));
				e.setA10(e.getAssistTypex().substring(4, 5));
				e.setA11(e.getAssistTypex().substring(5, 6));
			}
			e.setAssistTypeTxt(genAssistype(e));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public TempDTO findTempmemberinfo(TempDTO tempDTO) {
		TempDTO e = new TempDTO();
		Integer bizId = new Integer(tempDTO.getApproveId().intValue());
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = " select pee.membername, pee.familyno, pee.paperid, pee.relmaster, pee.address, "
				+ " pee.personstate, pee.assist_type, app.approve_id, app.applytime, app.approvests, app.hospitalname "
				+ " from temp_person pee , temp_approve app "
				+ " where app.member_id=pee.member_id and app.member_type=pee.member_type "
				+ " and app.approve_id= " + bizId;
		;
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		HashMap s = rs.get(0);
		e.setApproveId(tempDTO.getApproveId());
		e.setMembername((String) s.get("MEMBERNAME"));
		e.setFamilyno((String) s.get("FAMILYNO"));
		e.setPaperid((String) s.get("PAPERID"));
		e.setHospitalname((String) s.get("HOSPITALNAME"));
		return e;
	}

	public TempDTO isline(TempDTO tempDTO) {
		BigDecimal m = BigDecimal.ZERO;// 封顶线
		BigDecimal zyTopline = BigDecimal.ZERO;// 住院封顶线
		BigDecimal mzTopline = BigDecimal.ZERO;// 门诊封顶线
		BigDecimal maTopline = BigDecimal.ZERO;// （住院+门诊）封顶线
		BigDecimal zpay = BigDecimal.ZERO;// 个人救助金总额（住院+门诊）
		BigDecimal zyPay = BigDecimal.ZERO;// 住院个人救助金总额
		BigDecimal mzPay = BigDecimal.ZERO;// 门诊个人救助金总额
		BigDecimal assis = tempDTO.getPayAssist();
		String organizationId = tempDTO.getOrganizationId();
		if (null == organizationId || "".equals(organizationId)) {
			tempDTO.setResult("机构编码为空");
		} else {
			organizationId = organizationId.substring(0, 6);
			// 住院业务封顶线 type=3
			zyTopline = getToplineByType(organizationId, "3");
			// 门诊大病业务封顶线 type=1
			mzTopline = getToplineByType(organizationId, "1");
			// （门诊大病+住院）封顶线 type=9
			maTopline = getToplineByType(organizationId, "9");

			// 住院业务个人救助金总额(本年度)type = 2
			zyPay = getTotalPayAssistPer(tempDTO, "2");
			// 门诊大病业务个人救助金总额(本年度)type = 1
			mzPay = getTotalPayAssistPer(tempDTO, "1");
			// （住院业务+门诊大病业务）个人救助金总额(本年度)
			zpay = zyPay.add(mzPay);

			if ((BigDecimal.ZERO.compareTo(zyPay) == -1 && BigDecimal.ZERO
					.compareTo(mzPay) == -1)
					|| (BigDecimal.ZERO.compareTo(zyPay) == -1
							&& BigDecimal.ZERO.compareTo(mzPay) == 0 && "1"
								.equals(tempDTO.getAssistype()))
					|| (BigDecimal.ZERO.compareTo(zyPay) == 0
							&& BigDecimal.ZERO.compareTo(mzPay) == -1 && "2"
								.equals(tempDTO.getAssistype()))) {
				m = maTopline;
			} else if ((BigDecimal.ZERO.compareTo(zyPay) == -1 || BigDecimal.ZERO
					.compareTo(zyPay) == 0)
					&& BigDecimal.ZERO.compareTo(mzPay) == 0
					&& "2".equals(tempDTO.getAssistype())) {
				m = zyTopline;
			} else if (BigDecimal.ZERO.compareTo(zyPay) == 0
					&& (BigDecimal.ZERO.compareTo(mzPay) == -1 || BigDecimal.ZERO
							.compareTo(mzPay) == 0)
					&& "1".equals(tempDTO.getAssistype())) {
				m = mzTopline;
			}
		}
		if (m.subtract(zpay).compareTo(assis) == -1
				|| (zyTopline.subtract(zyPay).compareTo(assis) == -1 && "2"
						.equals(tempDTO.getAssistype()))
				|| mzTopline.subtract(mzPay).compareTo(assis) == -1
				&& "1".equals(tempDTO.getAssistype())) {
			tempDTO.setResult("0");
			tempDTO.setPayAssist(assis);
			if (m.subtract(zpay).compareTo(assis) == -1) {
				tempDTO.setTopLine(m);
			} else if (zyTopline.subtract(zyPay).compareTo(assis) == -1
					&& "2".equals(tempDTO.getAssistype())) {
				tempDTO.setTopLine(zyTopline);
			} else if (mzTopline.subtract(mzPay).compareTo(assis) == -1
					&& "1".equals(tempDTO.getAssistype())) {
				tempDTO.setTopLine(mzTopline);
			}
			tempDTO.setTotlePay(zpay);
			tempDTO.setZyPay(zyPay);
			tempDTO.setMzPay(mzPay);
		} else {
			tempDTO.setResult("1");
		}

		return tempDTO;
	}

	// 住院业务封顶线 type=3
	// 门诊大病业务封顶线 type=1
	// （门诊大病+住院）封顶线 type=9
	@SuppressWarnings("rawtypes")
	public BigDecimal getToplineByType(String organizationId, String type) {
		BigDecimal m = BigDecimal.ZERO;
		HashMap<String, String> param = new HashMap<String, String>();
		String sql = "select  t.line as S  from top_line t where t.organization_id='"
				+ organizationId + "' and t.top_type='" + type + "'";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			BigDecimal a = (BigDecimal) s.get("S");
			if (null != a) {
				m = a;
			}
		}
		return m;
	}

	// 住院业务个人救助金总额(本年度)type = 2
	// 门诊大病业务个人救助金总额(本年度)type = 1
	@SuppressWarnings("rawtypes")
	private BigDecimal getTotalPayAssistPer(TempDTO tempDTO, String type) {
		Calendar c = new GregorianCalendar();// 新建日期对象
		int year = c.get(Calendar.YEAR);// 获取年份
		BigDecimal zpay = BigDecimal.ZERO;// 个人救助金总额
		HashMap<String, String> param = new HashMap<String, String>();
		String jwhere = "";
		if ("3".equals(type)) {
		} else {
			jwhere = jwhere + " and pv.biz_type='" + type + "' ";
		}
		if ("220506".equals(tempDTO.getOrg())) {
			jwhere = jwhere + " and pv.diagnose_type_id =0 ";
		}
		String sql = " select sum(pv.pay_assist)as zpay from payview03 pv "
				+ "where to_char(pv.end_time, 'yyyy') = '" + year + "' "
				+ "and pv.id_card = '" + tempDTO.getPaperid()
				// + "' " + "and pv.member_id = '" + tempDTO.getMemberId()
				// + "' and pv.member_type = '" + tempDTO.getMemberType()
				+ "' " + jwhere;
		param.put("executsql", sql);
		List<HashMap> rs1 = extendsDAO.queryAll(param);
		if (null != rs1 && rs1.size() > 0) {
			HashMap s = rs1.get(0);
			BigDecimal a = (BigDecimal) s.get("ZPAY");
			if (null != a) {
				zpay = a;
			}
		}
		return zpay;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DiagnoseTypeDTO> findDiagnoseTypesByOrg(String organizationId) {
		List<DiagnoseTypeDTO> diagnosetypes = new ArrayList<DiagnoseTypeDTO>();
		String sql = " select * from diagnose_type dtype "
				+ " where dtype.organization_id ='" + organizationId + "' "
				+ " order by dtype.seq ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			DiagnoseTypeDTO e = new DiagnoseTypeDTO();
			e.setDiagnoseTypeId(new Integer(s.get("DIAGNOSE_TYPE_ID")
					.toString()));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setDiagnoseTypeName((String) s.get("DIAGNOSE_TYPE_NAME"));
			e.setScaler(new BigDecimal(s.get("SCALER").toString()));
			e.setSeq(new Integer(s.get("SEQ").toString()));
			diagnosetypes.add(e);
		}
		return diagnosetypes;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OutIcdDTO> findOuticdsByOrg(String organizationId) {
		List<OutIcdDTO> outicds = new ArrayList<OutIcdDTO>();
		String sql = "select t.*, icd.name from ICD10 icd, out_icd t "
				+ " where 1=1 " + " and t.organization_id = '" + organizationId
				+ "' " + " and t.icd_id = icd.icd_id "
				+ " and t.outtype = '1' and t.sts = '1' " + " order by t.seq ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			OutIcdDTO e = new OutIcdDTO();
			e.setCalcType((String) s.get("CALC_TYPE"));
			e.setFixValue((BigDecimal) s.get("FIX_VALUE"));
			e.setName((String) s.get("NAME"));
			e.setIcdId(new Integer(s.get("ICD_ID").toString()));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setOuttype((String) s.get("OUTTYPE"));
			e.setScale((BigDecimal) s.get("SCALE"));
			e.setSts((String) s.get("STS"));
			outicds.add(e);
		}
		return outicds;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DeptDTO> findMaHospitalNameById(String organizationId) {
		List<DeptDTO> hnames = new ArrayList<DeptDTO>();
		String sql = " select aft.hospital_name as name from jz_medicalafter aft "
				+ " where aft.organization_id like '"
				+ organizationId
				+ "%'"
				+ " and aft.biz_status = '1' "
				+ " and aft.implsts = '2' "
				+ " group by aft.hospital_name ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			DeptDTO e = new DeptDTO();
			e.setName((String) s.get("NAME"));
			hnames.add(e);
		}
		return hnames;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TempDTO> findMaAccounts(String sql, int currentpage, String url) {
		List<TempDTO> list = new ArrayList<TempDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			TempDTO e = new TempDTO();
			Long BIZ_ID = new Long(s.get("BIZ_ID").toString());
			String HNAME = (String) s.get("HNAME");
			String ASSIST_TYPE = (String) s.get("ASSIST_TYPE");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String NAME = (String) s.get("NAME");
			String ID_CARD = (String) s.get("ID_CARD");
			String ICDNAME = (String) s.get("ICDNAME");
			BigDecimal PAY_TOTAL = (BigDecimal) s.get("PAY_TOTAL");
			BigDecimal PAY_MEDICARE = (BigDecimal) s.get("PAY_MEDICARE");
			BigDecimal PAY_OUTMEDICARE = (BigDecimal) s.get("PAY_OUTMEDICARE");
			BigDecimal PAY_ASSIST = (BigDecimal) s.get("PAY_ASSIST");
			BigDecimal PAY_CIASSIST = (BigDecimal) s.get("PAY_CIASSIST");
			BigDecimal PAY_SELF = (BigDecimal) s.get("PAY_SELF");
			String DIAGNOSE_NAME = (String) s.get("DIAGNOSE_NAME");
			Date BEGIN_TIME = (Date) s.get("BEGIN_TIME");
			Date END_TIME = (Date) s.get("END_TIME");
			Date OPER_TIME = (Date) s.get("OPER_TIME");
			String HOSPITAL_TYPE = (String) s.get("HOSPITAL_TYPE");
			String MASTERNAME = (String) s.get("MASTERNAME");
			String ORGNAME1 = (String) s.get("ORGNAME1");
			String ORGNAME2 = (String) s.get("ORGNAME2");
			String MEMINFO = (String) s.get("MEMINFO");
			// start 梅河口20131018重大疾病-------------------------------------
			BigDecimal DIAGNOSETYPEID = (BigDecimal) s.get("DIAGNOSE_TYPE_ID");
			String DORG = (String) s.get("DORG");
			BigDecimal DTYPEID = (BigDecimal) s.get("DTYPEID");
			String DTYPENAME = (String) s.get("DTYPENAME");
			BigDecimal SCALER = (BigDecimal) s.get("SCALER");
			// end 梅河口20131018重大疾病-------------------------------------
			if (null != PAY_TOTAL) {
				e.setPayTotal(PAY_TOTAL);
			} else {
				e.setPayTotal(BigDecimal.ZERO);
			}
			if (null != PAY_MEDICARE) {
				e.setPayMedicare(PAY_MEDICARE);
			} else {
				e.setPayMedicare(BigDecimal.ZERO);
			}
			if (null != PAY_OUTMEDICARE) {
				e.setPayOutmedicare(PAY_OUTMEDICARE);
			} else {
				e.setPayOutmedicare(BigDecimal.ZERO);
			}
			if (null != PAY_ASSIST) {
				e.setPayAssist(PAY_ASSIST);
			} else {
				e.setPayAssist(BigDecimal.ZERO);
			}
			if (null != PAY_CIASSIST) {
				e.setPayCIAssist(PAY_CIASSIST);
			} else {
				e.setPayCIAssist(BigDecimal.ZERO);
			}
			if (null != PAY_SELF) {
				e.setPayself(PAY_SELF);
			} else {
				e.setPayself(BigDecimal.ZERO);
			}
			e.setBizid(BIZ_ID);
			e.setHname(HNAME);
			e.setFamilyno(FAMILY_NO);
			e.setName(NAME);
			e.setPaperid(ID_CARD);
			e.setIcdname(ICDNAME);
			e.setDiagnoseName(DIAGNOSE_NAME);
			e.setBegintime(BEGIN_TIME);
			e.setEndtime(END_TIME);
			e.setOpertime(OPER_TIME);
			e.setMastername(MASTERNAME);
			e.setOrgname1(ORGNAME1);
			e.setOrgname2(ORGNAME2);
			e.setMeminfo(MEMINFO);
			// 1：门诊特殊大病，2，住院
			if ("1".equals(ASSIST_TYPE)) {
				e.setBizType("门诊特殊大病");
			} else if ("2".equals(ASSIST_TYPE)) {
				e.setBizType("住院");
			}
			if ("1".equals(HOSPITAL_TYPE)) {
				e.setHospitaltype("辖区内");
			} else if ("2".equals(HOSPITAL_TYPE)) {
				e.setHospitaltype("其他");
			}
			// start 梅河口20131018重大疾病-------------------------------------
			if (null != DIAGNOSETYPEID) {
				e.setDiagnosetypeid(DIAGNOSETYPEID.toString());
			}
			e.setDorg(DORG);
			if (null != DTYPEID) {
				e.setDtypeid(DTYPEID.toString());
			}
			e.setDtypename(DTYPENAME);
			if (null != SCALER) {
				e.setScaler(SCALER.toString());
			} else {
				e.setScaler("0");
			}
			// end 梅河口20131018重大疾病-------------------------------------
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String findInfo(String sql) {
		String info = "";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			info = "总人次：" + s.get("ZRC").toString() + " 总金额："
					+ s.get("ZM").toString() + "（元）" + " 总救助金："
					+ s.get("ZJZJ").toString() + "（元）" + " 总大病保险："
					+ s.get("ZDBBX").toString() + "（元）";
		}
		return info;
	}

	public TempDTO findMaByBizId(TempDTO tempDTO) {
		JzMedicalafter ma = jzMedicalafterDAO.selectByPrimaryKey((tempDTO
				.getBizid().intValue()));
		tempDTO.setName(ma.getName());
		tempDTO.setHospitalname(ma.getHospitalName());
		tempDTO.setFamilyno(ma.getFamilyNo());
		tempDTO.setPaperid(ma.getIdCard());
		tempDTO.setOpertime(ma.getOperTime());
		tempDTO.setBegintime(ma.getBeginTime());
		tempDTO.setEndtime(ma.getEndTime());
		tempDTO.setDiagnoseName(ma.getDiagnoseName());
		tempDTO.setPayAssist(ma.getPayAssist());
		tempDTO.setPayTotal(ma.getPayTotal());
		tempDTO.setPayMedicare(ma.getPayMedicare());
		tempDTO.setPayOutmedicare(ma.getPayOutmedicare());
		BigDecimal payself = ma.getPayTotal().subtract(ma.getPayMedicare())
				.subtract(ma.getPayAssist()).subtract(ma.getPayCiassist());
		tempDTO.setPayself(payself);
		tempDTO.setPayCIAssist(ma.getPayCiassist());
		tempDTO.setBiztype(ma.getAssistType());
		tempDTO.setMemberId(ma.getMemberId());
		tempDTO.setMemberType(ma.getMemberType());
		return tempDTO;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BillDTO> findafteraccounts(TempDTO tempDTO) {
		List<BillDTO> list = new ArrayList<BillDTO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String beginTime = sdf.format(tempDTO.getBegintime());
		String endTime = sdf.format(tempDTO.getEndtime());
		String jwhere = "";
		if ("1".equals(tempDTO.getMemberType())) {
			jwhere = "  and aft.member_type='1' ";
		} else if ("2".equals(tempDTO.getMemberType())) {
			jwhere = "  and aft.member_type='2' ";
		} else {
		}
		String sql = " select aft.hospital_name as dmname, "
				+ " sum(decode(aft.assist_type, '1', 1, 0)) as mzcs, "
				+ " sum(decode(aft.assist_type, '1', aft.pay_assist, 0)) as mzjzj, "
				+ " sum(decode(aft.assist_type, '1', aft.pay_total, 0)) as mzzfy, "
				+ " sum(decode(aft.assist_type, '1', "
				+ " (aft.pay_total - aft.pay_medicare - aft.pay_outmedicare), "
				+ " 0)) as mzgrzf, "
				+ " sum(decode(aft.assist_type, '1', aft.pay_medicare, 0)) as mztc, "
				+ " sum(decode(aft.assist_type, '1', 0, 0)) as mzqt, "
				+ " sum(decode(aft.assist_type, '2', 1, 0)) as zycs, "
				+ " sum(decode(aft.assist_type, '2', aft.pay_assist, 0)) as zyjzj, "
				+ " sum(decode(aft.assist_type, '2', aft.pay_total, 0)) as zyzfy, "
				+ " sum(decode(aft.assist_type, '2', "
				+ " (aft.pay_total - aft.pay_medicare - aft.pay_outmedicare), "
				+ " 0)) as zygrzf, "
				+ " sum(decode(aft.assist_type, '2', aft.pay_medicare, 0)) as zytc, "
				+ " sum(decode(aft.assist_type, '2', aft.pay_ciassist, 0)) as zydbbx, "
				+ " sum(decode(aft.assist_type, '2', 0, 0)) as zyqt "
				+ " from jz_medicalafter aft " + " where 1=1 "
				+ " and aft.assist_type in ('1', '2') "
				+ " and aft.biz_status='1' " + " and aft.implsts='2' "
				+ " and aft.oper_time BETWEEN TO_DATE('" + beginTime
				+ "', 'yyyy-MM-dd') AND " + " TO_DATE('" + endTime
				+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss') " + jwhere
				+ " and aft.organization_id like '"
				+ tempDTO.getOrganizationId() + "%' "
				+ " group by aft.hospital_name "
				+ " order by aft.hospital_name ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			BillDTO e = new BillDTO();
			e.setDmname((String) s.get("DMNAME"));
			e.setMzcs((BigDecimal) s.get("MZCS"));
			e.setMzgrzf((BigDecimal) s.get("MZGRZF"));
			e.setMzjzj((BigDecimal) s.get("MZJZJ"));
			e.setMztc((BigDecimal) s.get("MZTC"));
			e.setMzzfy((BigDecimal) s.get("MZZFY"));
			e.setMzqt((BigDecimal) s.get("MZQT"));
			e.setZycs((BigDecimal) s.get("ZYCS"));
			e.setZygrzf((BigDecimal) s.get("ZYGRZF"));
			e.setZyjzj((BigDecimal) s.get("ZYJZJ"));
			e.setZytc((BigDecimal) s.get("ZYTC"));
			e.setZyzfy((BigDecimal) s.get("ZYZFY"));
			e.setZydbbx((BigDecimal) s.get("ZYDBBX"));
			e.setZyqt((BigDecimal) s.get("ZYQT"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int findTempAppCounts(String sql) {
		HashMap param = new HashMap();
		param.put("executsql", sql);
		int s = extendsDAO.queryCnt(param);
		return s;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void upTempApp(String sql) {
		HashMap param = new HashMap();
		param.put("executsql", sql);
		extendsDAO.updateAll(param);
	}

	public TempDTO upTempBack(TempDTO tempDTO) {
		TempApprove record = new TempApprove();
		record.setApproveId(tempDTO.getApproveId());
		record.setApprovests("1");
		record.setApprovetime(new Date());
		tempApproveDAO.updateByPrimaryKeySelective(record);
		return tempDTO;
	}

	public void delMaFile(String fid) {
		this.jzMedicalafterfileDAO.deleteByPrimaryKey(new BigDecimal(fid));
	}

	public void delTaFile(String fid) {
		this.tempApprovefileDAO.deleteByPrimaryKey(new BigDecimal(fid));
	}

	@SuppressWarnings("rawtypes")
	public TempDTO findSSN(TempDTO tempDTO) {
		TempDTO t = new TempDTO();
		String ssn = "";
		String medicaretype = "";
		String sql = "select * from member_medicare mm "
				+ " where mm.member_id='" + tempDTO.getMemberId()
				+ "' and mm.member_type='" + tempDTO.getMemberType() + "' ";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (rs.size() == 0) {
			ssn = "";
			medicaretype = "";
		} else {
			ssn = (String) rs.get(0).get("SSN");
			medicaretype  = (String) rs.get(0).get("MEDICARE_TYPE");
		}
		t.setSsn(ssn);
		t.setMedicareType(medicaretype);
		return t;
	}

	public String getassisttext(String ASSIST_TYPE, String DS) {
		String dsval = "";
		String a1 = ASSIST_TYPE.substring(0, 1);
		String a2 = ASSIST_TYPE.substring(1, 2);
		String a3 = ASSIST_TYPE.substring(2, 3);
		String a4 = ASSIST_TYPE.substring(3, 4);
		String a5 = ASSIST_TYPE.substring(4, 5);
		String a6 = ASSIST_TYPE.substring(5, 6);
		if ("1".equals(DS)) {
			if ("1".equals(a1)) {
				dsval = dsval + "城市低保户、";
			}
			if ("1".equals(a2)) {
				dsval = dsval + "分类施保、";
			}
			if ("1".equals(a3)) {
				dsval = dsval + "三无家庭、";
			}
			if ("1".equalsIgnoreCase(a4)) {
				dsval = dsval + "五保户、";
			}
			if ("1".equals(a5)) {
				dsval = dsval + "优抚对象、";
			}
			if ("1".equals(a6)) {
				dsval = dsval + "孤儿、";
			}
		} else if ("2".equals(DS)) {
			if ("2".equals(a1)) {
				dsval = dsval + "农村低保一般户、";
			}
			if ("1".equals(a2)) {
				dsval = dsval + "重点户、";
			}
			if ("1".equals(a3)) {
				dsval = dsval + "三无家庭、";
			}
			if ("1".equalsIgnoreCase(a4)) {
				dsval = dsval + "五保户、";
			}
			if ("1".equals(a5)) {
				dsval = dsval + "优抚对象、";
			}
			if ("1".equals(a6)) {
				dsval = dsval + "孤儿、";
			}
		}
		return dsval;
	}

	@SuppressWarnings("rawtypes")
	public String getTicketNo() {
		String ticketno = "";
		String sql = "select sq_billyh.nextval as ticketno from dual";
		HashMap<String, String> param = new HashMap<String, String>();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		BigDecimal no = (BigDecimal) rs.get(0).get("TICKETNO");
		DecimalFormat df = new DecimalFormat("0000000");
		String no1 = df.format(Integer.parseInt(no.toString()));
		Calendar c = Calendar.getInstance();
		// 获得系统当前日期
		String year = c.get(Calendar.YEAR) + "";
		String type = "YH";
		ticketno = type + year + no1;
		return ticketno;
	}

	public JzMedicalafterBillDTO saveJzMedicalafterBill(
			JzMedicalafterBillDTO jmbDTO) {
		JzMedicalafterBillDTO jmb = new JzMedicalafterBillDTO();
		// 如果有先修改票据状态0
		JzMedicalafterBillExample example = new JzMedicalafterBillExample();
		example.createCriteria().andBizIdEqualTo(jmbDTO.getBizId())
				.andStsEqualTo("1");
		List<JzMedicalafterBill> rs = jzMedicalafterBillDAO
				.selectByExample(example);
		if (rs.size() > 0) {
			for (JzMedicalafterBill r : rs) {
				JzMedicalafterBill record1 = new JzMedicalafterBill();
				record1.setBillId(r.getBillId());
				record1.setSts("0");
				jzMedicalafterBillDAO.updateByPrimaryKeySelective(record1);
			}
		}
		JzMedicalafterBill record = new JzMedicalafterBill();
		record.setBillId(getTicketNo());
		record.setPrintTime(new Date());
		record.setBizId(jmbDTO.getBizId());
		record.setUserId(jmbDTO.getUserId());
		record.setSts("1");
		jzMedicalafterBillDAO.insert(record);
		jmb.setBillId(record.getBillId());
		JzMedicalafterBill bill = jzMedicalafterBillDAO
				.selectByPrimaryKey(record.getBillId());
		jmb.setBizId(bill.getBizId());
		jmb.setPrintTime(bill.getPrintTime());
		jmb.setSts(bill.getSts());
		jmb.setUserId(bill.getUserId());
		return jmb;

	}

	public String getBusinessYear(JzYearDTO jzYearDTO) {
		Calendar c = new GregorianCalendar();// 新建日期对象
		int year = c.get(Calendar.YEAR);// 获取年份
		String month = "";
		String date = "";
		String businessyear = "";
		JzYearExample example = new JzYearExample();
		example.createCriteria()
				.andOrganizationIdEqualTo(jzYearDTO.getOrganizationId())
				.andStsEqualTo("1");
		List<JzYear> jlist = jzYearDAO.selectByExample(example);
		if(jlist.size()>0){
			JzYear jy = jlist.get(0);
			month = jy.getBusinessMonth();
			date = jy.getBusinessDate();
		}
		businessyear = year+"-"+month+"-"+date+ " 00:00:00";
		return businessyear;
	}
	
	public String getToolsmenu() {
		return pager.getToolsmenu();
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public MemberBaseinfoviewDAO getMemberBaseinfoviewDAO() {
		return memberBaseinfoviewDAO;
	}

	public void setMemberBaseinfoviewDAO(
			MemberBaseinfoviewDAO memberBaseinfoviewDAO) {
		this.memberBaseinfoviewDAO = memberBaseinfoviewDAO;
	}

	public JzAddassistdataDAO getJzAddassistdataDAO() {
		return jzAddassistdataDAO;
	}

	public void setJzAddassistdataDAO(JzAddassistdataDAO jzAddassistdataDAO) {
		this.jzAddassistdataDAO = jzAddassistdataDAO;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public TempApproveDAO getTempApproveDAO() {
		return tempApproveDAO;
	}

	public void setTempApproveDAO(TempApproveDAO tempApproveDAO) {
		this.tempApproveDAO = tempApproveDAO;
	}

	public TempBillDAO getTempBillDAO() {
		return tempBillDAO;
	}

	public void setTempBillDAO(TempBillDAO tempBillDAO) {
		this.tempBillDAO = tempBillDAO;
	}

	public void setTempPersonDAO(TempPersonDAO tempPersonDAO) {
		this.tempPersonDAO = tempPersonDAO;
	}

	public TempPersonDAO getTempPersonDAO() {
		return tempPersonDAO;
	}

	public void setTempMonthDAO(TempMonthDAO tempMonthDAO) {
		this.tempMonthDAO = tempMonthDAO;
	}

	public TempMonthDAO getTempMonthDAO() {
		return tempMonthDAO;
	}

	public SecondApproveDAO getSecondApproveDAO() {
		return secondApproveDAO;
	}

	public void setSecondApproveDAO(SecondApproveDAO secondApproveDAO) {
		this.secondApproveDAO = secondApproveDAO;
	}

	public SecondBatchDAO getSecondBatchDAO() {
		return secondBatchDAO;
	}

	public void setSecondBatchDAO(SecondBatchDAO secondBatchDAO) {
		this.secondBatchDAO = secondBatchDAO;
	}

	public SecondBillDAO getSecondBillDAO() {
		return secondBillDAO;
	}

	public void setSecondBillDAO(SecondBillDAO secondBillDAO) {
		this.secondBillDAO = secondBillDAO;
	}

	public JzMedicalafterDAO getJzMedicalafterDAO() {
		return jzMedicalafterDAO;
	}

	public void setJzMedicalafterDAO(JzMedicalafterDAO jzMedicalafterDAO) {
		this.jzMedicalafterDAO = jzMedicalafterDAO;
	}

	public void setMaMonthDAO(MaMonthDAO maMonthDAO) {
		this.maMonthDAO = maMonthDAO;
	}

	public MaMonthDAO getMaMonthDAO() {
		return maMonthDAO;
	}

	public void setMaBillDAO(MaBillDAO maBillDAO) {
		this.maBillDAO = maBillDAO;
	}

	public MaBillDAO getMaBillDAO() {
		return maBillDAO;
	}

	public void setTempCalcRuleDAO(TempCalcRuleDAO tempCalcRuleDAO) {
		this.tempCalcRuleDAO = tempCalcRuleDAO;
	}

	public TempCalcRuleDAO getTempCalcRuleDAO() {
		return tempCalcRuleDAO;
	}

	public JzMedicalafterfileDAO getJzMedicalafterfileDAO() {
		return jzMedicalafterfileDAO;
	}

	public void setJzMedicalafterfileDAO(
			JzMedicalafterfileDAO jzMedicalafterfileDAO) {
		this.jzMedicalafterfileDAO = jzMedicalafterfileDAO;
	}

	public void setTempApprovefileDAO(TempApprovefileDAO tempApprovefileDAO) {
		this.tempApprovefileDAO = tempApprovefileDAO;
	}

	public TempApprovefileDAO getTempApprovefileDAO() {
		return tempApprovefileDAO;
	}

	public Payview01DAO getPayview01DAO() {
		return payview01DAO;
	}

	public void setPayview01DAO(Payview01DAO payview01dao) {
		payview01DAO = payview01dao;
	}

	public JzMedicalafterBillDAO getJzMedicalafterBillDAO() {
		return jzMedicalafterBillDAO;
	}

	public void setJzMedicalafterBillDAO(
			JzMedicalafterBillDAO jzMedicalafterBillDAO) {
		this.jzMedicalafterBillDAO = jzMedicalafterBillDAO;
	}

	public JzYearDAO getJzYearDAO() {
		return jzYearDAO;
	}

	public void setJzYearDAO(JzYearDAO jzYearDAO) {
		this.jzYearDAO = jzYearDAO;
	}
}
