package com.mingda.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import com.mingda.common.Pager;
import com.mingda.dao.ChronicBillDAO;
import com.mingda.dao.ChronicCheckDAO;
import com.mingda.dao.ChronicItemDAO;
import com.mingda.dao.ChronicSessionDAO;
import com.mingda.dao.ChronicStatusDAO;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.MemberBaseinfoviewDAO;
import com.mingda.dao.OutIcdDAO;
import com.mingda.dto.BillDTO;
import com.mingda.dto.BillInfoDTO;
import com.mingda.dto.BizDTO;
import com.mingda.dto.ChronicCheckDTO;
import com.mingda.dto.ChronicIncomeDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.model.ChronicBill;
import com.mingda.model.ChronicBillExample;
import com.mingda.model.ChronicCheck;
import com.mingda.model.ChronicCheckExample;
import com.mingda.model.ChronicItem;
import com.mingda.model.ChronicItemExample;
import com.mingda.model.ChronicStatus;
import com.mingda.model.ChronicStatusExample;
import com.mingda.model.MemberBaseinfoview;
import com.mingda.model.MemberBaseinfoviewExample;
import com.mingda.model.OutIcd;
import com.mingda.model.OutIcdKey;

public class ChronicServiceImpl implements ChronicService {
	private Pager pager;
	private ExtendsDAO extendsDAO;
	private ChronicBillDAO chronicBillDAO;
	private ChronicCheckDAO chronicCheckDAO;
	private ChronicStatusDAO chronicStatusDAO;
	private MemberBaseinfoviewDAO memberBaseinfoviewDAO;
	private ChronicItemDAO chronicItemDAO;
	private OutIcdDAO outIcdDAO;
	private ChronicSessionDAO chronicSessionDAO;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ChronicCheckDTO> findChronicCancels(String sql,
			int currentpage, String url) {
		List<ChronicCheckDTO> list = new ArrayList<ChronicCheckDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// MEMBERNAME PAPERID SSN FAMILYID FAMILYNO DS SALVATION_ID ASSIST_TYPE
		// CHRONICCHECK_ID MEMBER_ID ICD_ID CHECKED1 DETAIL1
		// CHECKTIME1 OPERATOR1 OPTTIME1 CHECKED2 DETAIL2 CHECKTIME2 OPERATOR2
		// OPTTIME2 STS OPERATOR1NAME OPERATOR2NAME CHRONICSTATUS_ID
		// CURCHECK_ID STS SICKEN SALSTATE TMPFLAG
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
			String dsval = "";
			String a1 = ASSIST_TYPE.substring(0, 1);
			String a2 = ASSIST_TYPE.substring(1, 2);
			String a3 = ASSIST_TYPE.substring(2, 3);
			String a4 = ASSIST_TYPE.substring(3, 4);
			String a5 = ASSIST_TYPE.substring(4, 5);
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
				if ("2".equals(a3)) {
					dsval = dsval + "孤儿、";
				}
				if ("1".equalsIgnoreCase(a4)) {
					dsval = dsval + "五保户、";
				}
				if ("1".equals(a5)) {
					dsval = dsval + "优抚对象、";
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
				if ("2".equals(a3)) {
					dsval = dsval + "孤儿、";
				}
				if ("1".equalsIgnoreCase(a4)) {
					dsval = dsval + "五保户、";
				}
				if ("1".equals(a5)) {
					dsval = dsval + "优抚对象、";
				}
			}
			String MEMBERID = (String) s.get("MEMBERID");
			e.setMemberid(MEMBERID);
			e.setDsval(dsval);
			BigDecimal CHRONICCHECK_ID = (BigDecimal) s.get("CHRONICCHECK_ID");
			if (null != CHRONICCHECK_ID) {
				e.setChroniccheckId(CHRONICCHECK_ID.intValue());
			}
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
			String ICD_ID = (String) s.get("ICD_ID");
			e.setIcdId(ICD_ID);
			BigDecimal CHECKED1 = (BigDecimal) s.get("CHECKED1");
			if (null != CHECKED1) {
				e.setChecked1(CHECKED1.shortValue());
			}
			String DETAIL1 = (String) s.get("DETAIL1");
			e.setDetail1(DETAIL1);
			Date CHECKTIME1 = (Date) s.get("CHECKTIME1");
			e.setChecktime1(CHECKTIME1);
			String OPERATOR1 = (String) s.get("OPERATOR1");
			e.setOperator1(OPERATOR1);
			Date OPTTIME1 = (Date) s.get("OPTTIME1");
			e.setOpttime1(OPTTIME1);
			String OPERATOR1NAME = (String) s.get("OPERATOR1NAME");
			e.setOperator1name(OPERATOR1NAME);
			BigDecimal CHECKED2 = (BigDecimal) s.get("CHECKED2");
			if (null != CHECKED2) {
				e.setChecked2(CHECKED2.shortValue());
			}
			String DETAIL2 = (String) s.get("DETAIL2");
			e.setDetail2(DETAIL2);
			Date CHECKTIME2 = (Date) s.get("CHECKTIME2");
			e.setChecktime2(CHECKTIME2);
			String OPERATOR2 = (String) s.get("OPERATOR2");
			e.setOperator2(OPERATOR2);
			Date OPTTIME2 = (Date) s.get("OPTTIME2");
			e.setOpttime2(OPTTIME2);
			String OPERATOR2NAME = (String) s.get("OPERATOR2NAME");
			e.setOperator2name(OPERATOR2NAME);
			BigDecimal CHRONICSTATUS_ID = (BigDecimal) s
					.get("CHRONICSTATUS_ID");
			if (null != CHRONICSTATUS_ID) {
				e.setChronicstatusId(CHRONICSTATUS_ID.intValue());
			}
			BigDecimal CURCHECK_ID = (BigDecimal) s.get("CURCHECK_ID");
			if (null != CURCHECK_ID) {
				e.setCurcheckId(CURCHECK_ID.intValue());
			}
			String SSSTS = (String) s.get("SSSTS");
			e.setSssts(SSSTS);
			String SICKEN = (String) s.get("SICKEN");
			e.setSicken(SICKEN);
			String SALSTATE = (String) s.get("SALSTATE");
			e.setSalstate(SALSTATE);
			String TMPFLAG = (String) s.get("TMPFLAG");
			e.setTmpflag(TMPFLAG);
			String icdname = (String) s.get("NAME");
			e.setIcdIdval(icdname);
			BigDecimal FIXVALUE = (BigDecimal) s.get("FIX_VALUE");
			e.setMedicalmoney(FIXVALUE.doubleValue());
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ChronicCheckDTO> findChronicChecks(String sql, int currentpage,
			String url) {
		List<ChronicCheckDTO> list = new ArrayList<ChronicCheckDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// MEMBERNAME PAPERID SSN FAMILYID FAMILYNO DS SALVATION_ID ASSIST_TYPE
		// CHRONICCHECK_ID MEMBER_ID ICD_ID CHECKED1 DETAIL1
		// CHECKTIME1 OPERATOR1 OPTTIME1 CHECKED2 DETAIL2 CHECKTIME2 OPERATOR2
		// OPTTIME2 STS OPERATOR1NAME OPERATOR2NAME CHRONICSTATUS_ID
		// CURCHECK_ID STS SICKEN SALSTATE TMPFLAG
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
			String ASSIST_TYPEX = (String) s.get("ASSIST_TYPEX");
			e.setAssistTypex(ASSIST_TYPEX);
			String dsval = "";
			String a1 = ASSIST_TYPE.substring(0, 1);
			String a2 = ASSIST_TYPE.substring(1, 2);
			String a3 = ASSIST_TYPE.substring(2, 3);
			String a4 = ASSIST_TYPE.substring(3, 4);
			String a5 = ASSIST_TYPE.substring(4, 5);
			String a6 = ASSIST_TYPEX.substring(0, 1);
			String a7 = ASSIST_TYPEX.substring(1, 2);
			String a8 = ASSIST_TYPEX.substring(2, 3);
			String a9 = ASSIST_TYPEX.substring(3, 4);
			String a10 = ASSIST_TYPEX.substring(4, 5);
			String a11 = ASSIST_TYPEX.substring(5, 6);
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
			String MEMBERID = (String) s.get("MEMBERID");
			e.setMemberid(MEMBERID);
			e.setDsval(dsval);
			BigDecimal CHRONICCHECK_ID = (BigDecimal) s.get("CHRONICCHECK_ID");
			if (null != CHRONICCHECK_ID) {
				e.setChroniccheckId(CHRONICCHECK_ID.intValue());
			}
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
			String ICD_ID = (String) s.get("ICD_ID");
			e.setIcdId(ICD_ID);
			BigDecimal CHECKED1 = (BigDecimal) s.get("CHECKED1");
			if (null != CHECKED1) {
				e.setChecked1(CHECKED1.shortValue());
			}
			String DETAIL1 = (String) s.get("DETAIL1");
			e.setDetail1(DETAIL1);
			Date CHECKTIME1 = (Date) s.get("CHECKTIME1");
			e.setChecktime1(CHECKTIME1);
			String OPERATOR1 = (String) s.get("OPERATOR1");
			e.setOperator1(OPERATOR1);
			Date OPTTIME1 = (Date) s.get("OPTTIME1");
			e.setOpttime1(OPTTIME1);
			String OPERATOR1NAME = (String) s.get("OPERATOR1NAME");
			e.setOperator1name(OPERATOR1NAME);
			BigDecimal CHECKED2 = (BigDecimal) s.get("CHECKED2");
			if (null != CHECKED2) {
				e.setChecked2(CHECKED2.shortValue());
			}
			String DETAIL2 = (String) s.get("DETAIL2");
			e.setDetail2(DETAIL2);
			Date CHECKTIME2 = (Date) s.get("CHECKTIME2");
			e.setChecktime2(CHECKTIME2);
			String OPERATOR2 = (String) s.get("OPERATOR2");
			e.setOperator2(OPERATOR2);
			Date OPTTIME2 = (Date) s.get("OPTTIME2");
			e.setOpttime2(OPTTIME2);
			String OPERATOR2NAME = (String) s.get("OPERATOR2NAME");
			e.setOperator2name(OPERATOR2NAME);
			BigDecimal CHRONICSTATUS_ID = (BigDecimal) s
					.get("CHRONICSTATUS_ID");
			if (null != CHRONICSTATUS_ID) {
				e.setChronicstatusId(CHRONICSTATUS_ID.intValue());
			}
			BigDecimal CURCHECK_ID = (BigDecimal) s.get("CURCHECK_ID");
			if (null != CURCHECK_ID) {
				e.setCurcheckId(CURCHECK_ID.intValue());
			}
			String SSSTS = (String) s.get("SSSTS");
			e.setSssts(SSSTS);
			String SICKEN = (String) s.get("SICKEN");
			e.setSicken(SICKEN);
			String SALSTATE = (String) s.get("SALSTATE");
			e.setSalstate(SALSTATE);
			String TMPFLAG = (String) s.get("TMPFLAG");
			e.setTmpflag(TMPFLAG);
			list.add(e);
		}
		return list;
	}

	public ChronicCheckDTO findChronicCheckDTO(ChronicCheckDTO chronicCheckDTO) {
		MemberBaseinfoviewExample example = new MemberBaseinfoviewExample();
		example.createCriteria()
				.andMemberIdEqualTo(chronicCheckDTO.getMemberId())
				.andDsEqualTo(chronicCheckDTO.getDs());
		List<MemberBaseinfoview> mems = memberBaseinfoviewDAO
				.selectByExample(example);
		if (chronicCheckDTO.getOrganziationId().length() == 8) {
			ChronicCheckExample exampleCC = new ChronicCheckExample();
			exampleCC.createCriteria()
					.andMemberIdEqualTo(chronicCheckDTO.getMemberId())
					.andMemberTypeEqualTo(chronicCheckDTO.getDs());
			exampleCC.setOrderByClause("CHRONICCHECK_ID desc");
			List<ChronicCheck> ccs = chronicCheckDAO.selectByExample(exampleCC);
			if (ccs != null && ccs.size() == 1) {
				ChronicCheck cc = ccs.get(0);
				chronicCheckDTO.setSts2(cc.getSts());
			}
		}
		if (mems != null && mems.size() == 1) {
			MemberBaseinfoview memrs = mems.get(0);
			chronicCheckDTO.setMemberid(memrs.getMemberId());
			chronicCheckDTO.setMemberId(memrs.getMemberId());
			chronicCheckDTO.setMembername(memrs.getMembername());
			chronicCheckDTO.setMemberType(memrs.getDs());
			chronicCheckDTO.setPaperid(memrs.getPaperid());
			chronicCheckDTO.setFamilyno(memrs.getFamilyno());
			chronicCheckDTO.setSsn(memrs.getSsn());
			chronicCheckDTO.setSalvationId(memrs.getSalvationId());
			chronicCheckDTO.setAssisType(memrs.getAssistType());
			if (null == chronicCheckDTO.getChroniccheckId()
					|| "".equals(chronicCheckDTO.getChroniccheckId())) {
			} else {
				ChronicCheck chronicCheck = chronicCheckDAO
						.selectByPrimaryKey(chronicCheckDTO.getChroniccheckId());
				chronicCheckDTO.setChroniccheckId(chronicCheck
						.getChroniccheckId());
				chronicCheckDTO.setMemberType(chronicCheck.getMemberType());
				chronicCheckDTO.setMemberId(chronicCheck.getMemberId());
				chronicCheckDTO.setIcdId(chronicCheck.getIcdId());

				chronicCheckDTO.setChecked1(chronicCheck.getChecked1());
				chronicCheckDTO.setDetail1(chronicCheck.getDetail1());
				chronicCheckDTO.setChecktime1(chronicCheck.getChecktime1());
				chronicCheckDTO.setOperator1(chronicCheck.getOperator1());
				chronicCheckDTO.setOpttime1(chronicCheck.getOpttime1());

				chronicCheckDTO.setChecked2(chronicCheck.getChecked2());
				chronicCheckDTO.setDetail2(chronicCheck.getDetail2());
				chronicCheckDTO.setChecktime2(chronicCheck.getChecktime2());
				chronicCheckDTO.setOperator2(chronicCheck.getOperator2());
				chronicCheckDTO.setOpttime2(chronicCheck.getOpttime2());

				chronicCheckDTO.setSts(chronicCheck.getSts());
				chronicCheckDTO.setOperator1name(chronicCheck
						.getOperator1name());
				chronicCheckDTO.setOperator2name(chronicCheck
						.getOperator2name());
				chronicCheckDTO.setMainId(chronicCheck.getMainId());
				String mainIdval = findSickens(chronicCheckDTO.getMainId());
				chronicCheckDTO.setMainIdval(mainIdval);
				String icdIdval = findSickens(chronicCheckDTO.getIcdId());
				chronicCheckDTO.setIcdIdval(icdIdval);
			}
			return chronicCheckDTO;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String findSickens(String icd) {
		String icdIdval = "";
		String icdId = icd;
		if (!"".equals(icd)) {
			if (-1 == icdId.lastIndexOf(",")) {
			} else {
				icdId = icdId.substring(0, icdId.length() - 1);
			}
			String sql = "select * from icd10 i where i.icd_id in (" + icdId
					+ ") and i.salv_flag = 1";
			HashMap param = new HashMap();
			param.put("executsql", sql);
			List<HashMap> sickens = extendsDAO.queryAll(param);
			for (HashMap s : sickens) {
				icdIdval = icdIdval + (String) s.get("NAME") + ",";
			}
		}
		return icdIdval;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ChronicCheckDTO saveChronicMember(ChronicCheckDTO chronicCheckDTO) {
		String organizationId = chronicCheckDTO.getOrganziationId();
		Short checked = chronicCheckDTO.getChecked();
		String detail = chronicCheckDTO.getDetail();
		Date checktime = chronicCheckDTO.getChecktime();
		String operator = chronicCheckDTO.getOperator();
		Date opttime = chronicCheckDTO.getOpttime();
		String operatorname = chronicCheckDTO.getOperatorname();
		String memberId = chronicCheckDTO.getMemberId();
		String memberType = chronicCheckDTO.getMemberType();
		Integer chroniccheckId = chronicCheckDTO.getChroniccheckId();
		String salstate = "";
		ChronicCheck record = new ChronicCheck();
		if (8 == organizationId.length()) {

			record.setChecked1(checked);
			record.setChecktime1(checktime);
			record.setOpttime1(opttime);
			record.setOperator1name(operatorname);
			record.setDetail1(detail);
			record.setOperator1(operator);
			record.setIcdId(chronicCheckDTO.getIcdId());
			record.setMainId(chronicCheckDTO.getMainId());
			record.setSts("1");
			record.setMemberId(memberId);
			record.setMemberType(memberType);

			if (null == chroniccheckId) {
				chronicCheckDAO.insertSelective(record);
			} else {
				record.setChroniccheckId(chroniccheckId);
				chronicCheckDAO.updateByPrimaryKeySelective(record);
			}
		} else if (6 == organizationId.length()) {
			record.setChecked2(checked);
			record.setChecktime2(checktime);
			record.setOpttime2(opttime);
			record.setOperator2name(operatorname);
			record.setDetail2(detail);
			record.setOperator2(operator);

			HashMap param = new HashMap();
			String sql = "select * from chronic_temp t where t.organization_id='"
					+ organizationId + "'";
			param.put("executsql", sql);
			List<HashMap> rs = extendsDAO.queryAll(param);

			if (null != rs && rs.size() > 0) {
				if ("1".equals(memberType)) {
					chronicCheckDTO
							.setMainId((String) rs.get(0).get("TYPE_CS"));
				} else if ("2".equals(memberType)) {
					chronicCheckDTO
							.setMainId((String) rs.get(0).get("TYPE_NC"));
				}
			}
			record.setMainId(chronicCheckDTO.getMainId());
			record.setIcdId(chronicCheckDTO.getIcdId());
			record.setSts("2");
			record.setMemberId(memberId);
			record.setMemberType(memberType);
			if (1 == checked) {
				salstate = "1";
			} else if (0 == checked) {
				salstate = "0";
			}
			record.setChroniccheckId(chroniccheckId);
			chronicCheckDAO.updateByPrimaryKeySelective(record);
			// 审批状态表
			ChronicStatus chronicStatus = null;
			ChronicStatusExample example = new ChronicStatusExample();
			example.createCriteria().andMemberIdEqualTo(memberId)
					.andMemberTypeEqualTo(memberType);
			List<ChronicStatus> srs = chronicStatusDAO.selectByExample(example);
			if (null == srs || 0 == srs.size()) {
				chronicStatus = new ChronicStatus();
				chronicStatus.setCurcheckId(record.getChroniccheckId());
				chronicStatus.setMemberId(memberId);
				chronicStatus.setMemberType(memberType);
				chronicStatus.setSalstate(salstate);
				chronicStatus.setSts("1");
				chronicStatus.setTmpflag("1");
				chronicStatus.setSicken(record.getIcdId());
				chronicStatusDAO.insertSelective(chronicStatus);
			} else {
				chronicStatus = srs.get(0);
				chronicStatus.setCurcheckId(record.getChroniccheckId());
				chronicStatus.setSalstate(salstate);
				chronicStatus.setSts("1");
				chronicStatus.setSicken(record.getIcdId());
				chronicStatusDAO.updateByPrimaryKeySelective(chronicStatus);
			}
			// 编辑审批患病表
			ChronicItemExample itemexample = new ChronicItemExample();
			itemexample.createCriteria().andChroniccheckIdEqualTo(
					chronicStatus.getCurcheckId());
			chronicItemDAO.deleteByExample(itemexample);
			String sicken = chronicStatus.getSicken();
			String[] sickens = sicken.split(",");
			for (int i = 0; i < sickens.length; i++) {
				String icdid = sickens[i];
				if (null == icdid || "".equals(icdid)) {

				} else {
					ChronicItem item = new ChronicItem();
					item.setChroniccheckId(chronicStatus.getCurcheckId());
					item.setIcdId(new Integer(icdid));
					chronicItemDAO.insertSelective(item);
				}
			}
		}
		chronicCheckDTO.setDs(memberType);
		chronicCheckDTO.setChroniccheckId(record.getChroniccheckId());
		chronicCheckDTO = findChronicCheckDTO(chronicCheckDTO);
		return chronicCheckDTO;
	}

	public boolean findIsChronic(ChronicCheckDTO chronicCheckDTO) {
		@SuppressWarnings("unused")
		String organizationId = chronicCheckDTO.getOrganziationId();
		ChronicCheckExample example = new ChronicCheckExample();
		example.createCriteria()
				.andMemberIdEqualTo(chronicCheckDTO.getMemberId())
				.andMemberTypeEqualTo(chronicCheckDTO.getDs())
				.andStsEqualTo("1");
		List<ChronicCheck> rs = chronicCheckDAO.selectByExample(example);
		if (null == rs || 0 == rs.size()) {
			return true;
		} else {
			return true;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ChronicCheckDTO> findChronicMoneys(String sql, int currentpage,
			String url) {
		List<ChronicCheckDTO> list = new ArrayList<ChronicCheckDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// MEMBERNAME PAPERID SSN FAMILYID FAMILYNO DS SALVATION_ID ASSIST_TYPE
		// CHRONICCHECK_ID MEMBER_ID ICD_ID CHECKED1 DETAIL1
		// CHECKTIME1 OPERATOR1 OPTTIME1 CHECKED2 DETAIL2 CHECKTIME2 OPERATOR2
		// OPTTIME2 STS OPERATOR1NAME OPERATOR2NAME CHRONICSTATUS_ID
		// CURCHECK_ID STS SICKEN SALSTATE TMPFLAG
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
			String ASSIST_TYPEX = (String) s.get("ASSIST_TYPEX");
			e.setAssistTypex(ASSIST_TYPEX);
			String dsval = "";
			String a1 = ASSIST_TYPE.substring(0, 1);
			String a2 = ASSIST_TYPE.substring(1, 2);
			String a3 = ASSIST_TYPE.substring(2, 3);
			String a4 = ASSIST_TYPE.substring(3, 4);
			String a5 = ASSIST_TYPE.substring(4, 5);
			String a6 = ASSIST_TYPEX.substring(0, 1);
			String a7 = ASSIST_TYPEX.substring(1, 2);
			String a8 = ASSIST_TYPEX.substring(2, 3);
			String a9 = ASSIST_TYPEX.substring(3, 4);
			String a10 = ASSIST_TYPEX.substring(4, 5);
			String a11 = ASSIST_TYPEX.substring(5, 6);

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
			e.setDsval(dsval);
			String MEMBERID = (String) s.get("MEMBER_ID");
			e.setMemberid(MEMBERID);
			BigDecimal CHRONICCHECK_ID = (BigDecimal) s.get("CHRONICCHECK_ID");
			if (null != CHRONICCHECK_ID) {
				e.setChroniccheckId(CHRONICCHECK_ID.intValue());
			}
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
			BigDecimal CHRONICSTATUS_ID = (BigDecimal) s
					.get("CHRONICSTATUS_ID");
			if (null != CHRONICSTATUS_ID) {
				e.setChronicstatusId(CHRONICSTATUS_ID.intValue());
			}
			String SICKEN = (String) s.get("SICKEN");
			e.setSicken(SICKEN);
			String SALSTATE = (String) s.get("SALSTATE");
			e.setSalstate(SALSTATE);
			String TMPFLAG = (String) s.get("TMPFLAG");
			e.setTmpflag(TMPFLAG);
			BigDecimal CURCHECK_ID = (BigDecimal) s.get("CURCHECK_ID");
			if (null != CURCHECK_ID) {
				e.setCurcheckId(CURCHECK_ID.intValue());
			}
			BigDecimal income = (BigDecimal) s.get("INCOME");
			e.setIncome(income.doubleValue());
			BigDecimal payout = (BigDecimal) s.get("PAYOUT");
			e.setPayout(payout.doubleValue());
			BigDecimal balance = (BigDecimal) s.get("BALANCE");
			e.setBalance(balance.doubleValue());
			BigDecimal vbalance = (BigDecimal) s.get("VBALANCE");
			e.setVbalance(vbalance.doubleValue());
			BigDecimal MEDICALMONEY = (BigDecimal) s.get("MEDICALMONEY");
			if (null == MEDICALMONEY) {
				e.setMedicalmoney(0.0);
			} else {
				e.setMedicalmoney(MEDICALMONEY.doubleValue());
			}
			e.setIcdIdval((String) s.get("ICDNAME"));
			e.setMeminfo((String) s.get("MEMINFO"));
			e.setOpttime2((Date) s.get("OPTTIME2"));
			e.setOrgname1((String) s.get("ORGNAME1"));
			e.setOrgname2((String) s.get("ORGNAME2"));
			list.add(e);
		}
		return list;

	}

	// type 1>>>存入金额、2>>>清空账余额

	public String saveChronicMoney(ChronicCheckDTO chronicCheckDTO, String type) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		boolean flag = true;
		ChronicStatus chronicStatus = chronicStatusDAO
				.selectByPrimaryKey(chronicCheckDTO.getChronicstatusId());
		/*
		 * if (null == chronicStatus.getTmpflag() ||
		 * "".equals(chronicStatus.getTmpflag())) { flag = true; } else { if
		 * (chronicStatus.getTmpflag().equals(chronicCheckDTO.getTmpflag())) {
		 * flag = false; } else { flag = true; } }
		 */
		if (!flag) {
			return "此用户已经操作过了，请重新登录才能再次操作！";
		} else {
			if ("1".equals(chronicStatus.getSalstate())) {
				Double balance = new Double(0.0);
				Double income = null;
				ChronicBill chronicBill = new ChronicBill();
				chronicBill.setMemberId(chronicStatus.getMemberId());
				chronicBill.setMemberType(chronicStatus.getMemberType());
				// 查询余额
				ChronicBillExample example = new ChronicBillExample();
				example.createCriteria()
						.andMemberIdEqualTo(chronicBill.getMemberId())
						.andMemberTypeEqualTo(chronicBill.getMemberType())
						.andOptstsEqualTo("1");
				example.setOrderByClause("CHRONICBILL_ID desc");
				List<ChronicBill> rs = chronicBillDAO.selectByExample(example);
				if (null != rs && rs.size() > 0) {
					balance = rs.get(0).getBalance().doubleValue();
				} else {
					balance = 0.0;
				}
				if ("1".equals(type)) {
					income = chronicCheckDTO.getMedicalmoney();
					chronicBill.setIncome(new BigDecimal(income));
					chronicBill.setPayout(new BigDecimal(0));
					chronicBill.setBalance(new BigDecimal(balance + income));
					chronicBill.setOpertime(new Date());
					result = sdf.format(date) + "单笔存入";
					chronicBill.setSubject(result);
					chronicBill.setOptsts("1");
					chronicBill.setBizid(-1);
					chronicBillDAO.insertSelective(chronicBill);
					chronicStatus.setTmpflag(chronicCheckDTO.getTmpflag());
					chronicStatusDAO.updateByPrimaryKeySelective(chronicStatus);
					return result;
				} else if ("2".equals(type)) {
					income = balance;
					chronicBill.setIncome(new BigDecimal(income * -1));
					chronicBill.setPayout(new BigDecimal(0));
					chronicBill.setBalance(new BigDecimal(0));
					chronicBill.setOpertime(new Date());
					result = sdf.format(date) + "账户清零";
					chronicBill.setSubject(result);
					chronicBill.setOptsts("1");
					chronicBill.setBizid(-1);
					chronicBillDAO.insertSelective(chronicBill);
					chronicStatus.setTmpflag(chronicCheckDTO.getTmpflag());
					chronicStatusDAO.updateByPrimaryKeySelective(chronicStatus);
					return result;
				} else {
					return "操作不成功，请重新查询！";
				}
			} else {
				result = "您操作的用户不是救助对象，请重新查询！";
				return result;
			}
		}
	}

	// 查询 审批金额信息
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ChronicCheckDTO findChronicMoneyDTO(ChronicCheckDTO chronicCheckDTO) {
		String sql = "select ss.*,  nvl(m.income, 0) as income, nvl(m.payout, 0) as payout, "
				+ " nvl(m.balance, 0) as balance  from chronic_status ss, "
				+ " (select sum(bill.income) as income,  sum(bill.payout) as payout, "
				+ " (sum(bill.income) - sum(bill.payout)) as balance,  bill.member_id, "
				+ " bill.member_type from chronic_bill bill where bill.optsts = '1' "
				+ " group by bill.member_id, bill.member_type) m "
				+ " where ss.chronicstatus_id = '"
				+ chronicCheckDTO.getChronicstatusId()
				+ "' "
				+ " and m.member_type(+) = ss.member_type "
				+ " and m.member_id(+) = ss.member_id";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			BigDecimal income = (BigDecimal) s.get("INCOME");
			chronicCheckDTO.setIncome(income.doubleValue());
			BigDecimal payout = (BigDecimal) s.get("PAYOUT");
			chronicCheckDTO.setPayout(payout.doubleValue());
			BigDecimal balance = (BigDecimal) s.get("BALANCE");
			chronicCheckDTO.setBalance(balance.doubleValue());
		}
		return chronicCheckDTO;
	}

	public ChronicCheckDTO findChronicMoneyYear(ChronicCheckDTO chronicCheckDTO) {
		Calendar c = new GregorianCalendar();// 新建日期对象
		int year = c.get(Calendar.YEAR);// 获取年份

		String sql = "select ss.*,  nvl(m.income, 0) as income, nvl(m.payout, 0) as payout, "
				+ " nvl(m.balance, 0) as balance  from chronic_status ss, "
				+ " (select sum(bill.income) as income,  sum(bill.payout) as payout, "
				+ " (sum(bill.income) - sum(bill.payout)) as balance,  bill.member_id, "
				+ " bill.member_type from chronic_bill bill where bill.optsts = '1'  and to_char( bill.opertime,'yyyy')='"
				+ year
				+ "'"
				+ " group by bill.member_id, bill.member_type) m "
				+ " where ss.chronicstatus_id = '"
				+ chronicCheckDTO.getChronicstatusId()
				+ "' "
				+ " and m.member_type(+) = ss.member_type "
				+ " and m.member_id(+) = ss.member_id";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			BigDecimal income = (BigDecimal) s.get("INCOME");
			chronicCheckDTO.setIncome(income.doubleValue());
			BigDecimal payout = (BigDecimal) s.get("PAYOUT");
			chronicCheckDTO.setPayout(payout.doubleValue());
			BigDecimal balance = (BigDecimal) s.get("BALANCE");
			chronicCheckDTO.setBalance(balance.doubleValue());
		}
		return chronicCheckDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap findChronicMoneyInfo(String organizationId) {
		Calendar c = new GregorianCalendar();// 新建日期对象
		int year = c.get(Calendar.YEAR);// 获取年份

		String sql1 = "select mem.MEMBER_ID, mem.chroniccheck_id as curcheck_id, "
				+ " mem.sicken,   mem.sts, mem.tmpflag,  mem.salstate, mem.chronicstatus_id, "
				+ "  mem.MEMBERNAME,  mem.PAPERID,  mem.SSN, mem.FAMILYNO, mem.member_type as DS, "
				+ " mem.ASSIST_TYPE, mem.assist_typex, null as salvation_id,  (nvl(mem.fix_value, 0)) as medicalmoney, "
				+ "  nvl(m.income, 0) as income,   nvl(m.payout, 0) as payout, "
				+ "  nvl(m.income, 0) - nvl(m.payout, 0) as balance,  decode(nvl(m.income, 0), "
				+ "  0,  (nvl(mem.fix_value, 0) / 2), "
				+ "   (nvl(mem.fix_value, 0)) - nvl(m.income, 0)) as vbalance from mv_chronic mem, "
				+ "  (select bill.member_id as mid, bill.member_type as mds,  sum(bill.income) as income, "
				+ "  sum(bill.payout) as payout   from chronic_bill bill "
				+ " where bill.opertime BETWEEN TO_DATE('"
				+ year
				+ "-01-01', 'yyyy-MM-dd') AND "
				+ "  TO_DATE('"
				+ year
				+ "-12-31 23:59:59', 'yyyy-MM-dd hh24:mi:ss') "
				+ "  AND bill.optsts = '1'   group by bill.MEMBER_ID, bill.member_type) m "
				+ "  where mem.sts = '1' and mem.salstate = '1'  "
				+ "  and mem.personstate='正常' "
				+ "  and m.mid(+) = mem.MEMBER_ID and m.mds(+) = mem.member_type  "
				+ "  and (mem.ASSIST_TYPE <> '00000'or mem.assist_typex <> '000000') and mem.familyno like '"
				+ organizationId + "%'";
		String sql = "select count(1) as zs, "
				+ "nvl(sum(income),0) as income, "
				+ " nvl(sum(payout),0) as payout, "
				+ " nvl(sum(balance),0) as balance, "
				+ " nvl(sum(medicalmoney),0) as medicalmoney " + " from ("
				+ sql1 + " )";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			return s;
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveChronicMoneyAll(String value, String organizationId) {

		String sql = "";
		int batch_id = this.extendsDAO.querySeq();

		// 插入batch表
		HashMap param = new HashMap();
		sql = "insert into chronic_batch (organization_id, opertime, batch_id) values ('"
				+ organizationId + "', sysdate, '" + batch_id + "')";
		param.put("executsql", sql);
		extendsDAO.insertAll(param);

		// 插入bill表
		// /bill.opertime BETWEEN " + " TO_DATE('" + year
		// + "-01-01', 'yyyy-MM-dd') AND " + " TO_DATE('" + year
		// + "-12-31 23:59:59', " + " 'yyyy-MM-dd hh24:mi:ss')
		sql = "insert into chronic_bill "
				+ " (chronicbill_id, "
				+ " member_type, "
				+ " member_id, "
				+ " subject, "
				+ " income, "
				+ " payout, "
				+ " balance, "
				+ " opertime,bizid,optsts) "
				+ " select sq_chronicbill_id.nextval, ds, MEMBER_ID, "
				+ " to_char(sysdate,'yyyy-MM-dd')||'批量存入' , medicalmoney, 0.0, "
				+ " balance + medicalmoney, sysdate, -1,1 "
				+ " from (select mem.MEMBER_ID, "
				+ " mem.DS, "
				+ " (nvl(icd.fix_value, 0) / 2) as medicalmoney, "
				+ " nvl(m.income, 0) as income, "
				+ " nvl(m.payout, 0) as payout, "
				+ " nvl(m.income, 0) - nvl(m.payout, 0) as balance "
				+ " from chronic_status s, "
				+ " chronic_check ck, "
				+ " MEMBER_BASEINFOVIEW02 mem, "
				+ " out_icd icd, "
				+ " (select bill.member_id as mid, "
				+ " bill.member_type as mds, "
				+ " sum(bill.income) as income, "
				+ " sum(bill.payout) as payout "
				+ " from chronic_bill bill  where  1=1 and bill.optsts='1' "
				+ " group by bill.MEMBER_ID, bill.member_type) m "
				+ " where mem.MEMBER_ID = s.member_id "
				+ " and mem.DS = s.member_type "
				+ " and s.salstate = 1 "
				+ " and m.mid(+) = mem.MEMBER_ID "
				+ " and m.mds(+) = mem.ds "
				+ " and ck.chroniccheck_id = s.curcheck_id "
				+ " and icd.outtype='2' "
				+ " and mem.assist_type<>'00000' "
				+ " and ck.main_id = icd.icd_id(+)  and icd.organization_id = '"
				+ organizationId + "' " + " and mem.familyno like '"
				+ organizationId + "%' "
				+ " order by mem.FAMILYNO) where medicalmoney>0 ";
		param = new HashMap();
		param.put("executsql", sql);
		extendsDAO.insertAll(param);
		// 插入 bill_batch 表

		sql = "insert into chronic_bill_batch (chronicbill_id, batch_id)"
				+ " select b.chronicbill_id , '"
				+ batch_id
				+ "' "
				+ " from chronic_bill b "
				+ " where 1=1 " 
				+ " and b.bizid = -1 "
				+ " and exists (select 1 from MEMBER_BASEINFOVIEW02 mem "
				+ " where mem.member_id = b.member_id and mem.ds = mem.ds and mem.familyno like '"
				+ organizationId + "%') and not exists ( "
				+ " select bb.chronicbill_id " + " from chronic_bill_batch bb "
				+ " where bb.chronicbill_id = b.chronicbill_id) "
				+ " and b.optsts='1'";
		param = new HashMap();
		param.put("executsql", sql);
		extendsDAO.insertAll(param);

	}

	public List<BillInfoDTO> findChronicBillInfo(ChronicCheckDTO chronicCheckDTO) {
		List<BillInfoDTO> list = new ArrayList<BillInfoDTO>();
		ChronicBillExample example = new ChronicBillExample();
		example.createCriteria()
				.andMemberIdEqualTo(chronicCheckDTO.getMemberId())
				.andMemberTypeEqualTo(chronicCheckDTO.getMemberType())
				.andOptstsEqualTo("1");
		example.setOrderByClause("OPERTIME");
		List<ChronicBill> rs = chronicBillDAO.selectByExample(example);
		for (ChronicBill s : rs) {
			BillInfoDTO e = new BillInfoDTO();
			e.setBalance(s.getBalance());
			e.setChronicbillId(s.getChronicbillId());
			e.setIncome(s.getIncome());
			e.setMemberId(s.getMemberId());
			e.setMemberType(s.getMemberType());
			e.setOpertime(s.getOpertime());
			e.setPayout(s.getPayout());
			e.setSubject(s.getSubject());
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OutIcdDTO> findChronicOutIcds(String organizationId) {
		String sql = "select t.outtype,t.icd_id,t.calc_type,t.fix_value,t.scale ,t.seq ,icd.name "
				+ " from out_icd t,icd10 icd where t.organization_id = '220281' and icd.icd_id=t.icd_id "
				+ " order by t.icd_id, t.seq";
		List<OutIcdDTO> list = new ArrayList<OutIcdDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		// OUTTYPE ICD_ID CALC_TYPE FIX_VALUE SCALE SEQ NAME
		for (HashMap s : rs) {
			OutIcdDTO e = new OutIcdDTO();
			String OUTTYPE = (String) s.get("OUTTYPE");
			e.setOuttype(OUTTYPE);
			BigDecimal ICD_ID = (BigDecimal) s.get("ICD_ID");
			e.setIcdId(ICD_ID.intValue());
			String CALC_TYPE = (String) s.get("CALC_TYPE");
			e.setCalcType(CALC_TYPE);
			BigDecimal FIX_VALUE = (BigDecimal) s.get("FIX_VALUE");
			e.setFixValue(FIX_VALUE);
			BigDecimal SCALE = (BigDecimal) s.get("SCALE");
			e.setScale(SCALE);
			BigDecimal SEQ = (BigDecimal) s.get("SEQ");
			e.setSeq(SEQ.intValue());
			String NAME = (String) s.get("NAME");
			e.setName(NAME);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ChronicIncomeDTO> findChronicIncomeCnt(String organizationId) {
		Calendar c = new GregorianCalendar();// 新建日期对象
		int year = c.get(Calendar.YEAR);// 获取年份
		List<ChronicIncomeDTO> list = new ArrayList<ChronicIncomeDTO>();
		String sql = "select * from chronic_batch t "
				+ " where t.organization_id='"
				+ organizationId
				+ "' and t.opertime BETWEEN  TO_DATE('"
				+ year
				+ "-01-01', 'yyyy-MM-dd')  AND"
				+ " TO_DATE('"
				+ year
				+ "-12-31 23:59:59',  'yyyy-MM-dd hh24:mi:ss') order by t.opertime";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			ChronicIncomeDTO e = new ChronicIncomeDTO();
			e.setOpertime((Date) s.get("OPERTIME"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			list.add(e);
		}
		return list;
	}

	public String findMoney(ChronicCheckDTO chronicCheckDTO) {
		OutIcdKey key = new OutIcdKey();
		key.setIcdId(new Integer(chronicCheckDTO.getMainId()));
		key.setOrganizationId(chronicCheckDTO.getOrganziationId());
		OutIcd rs = outIcdDAO.selectByPrimaryKey(key);
		if (null == rs) {
			return "0";
		} else {
			return rs.getFixValue().toString();
		}
	}

	public String getToolsmenu() {
		return pager.getToolsmenu();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BizDTO> findBizCheckAccounts(String sql, int currentpage,
			String url) {
		List<BizDTO> list = new ArrayList<BizDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// SSN HNAME BIZ_TYPE FAMILY_NO NAME ID_CARD ICDNAME ICDCODE ASSISMONEY
		// BIZ_ID TOTAL
		for (HashMap s : rs) {
			BizDTO e = new BizDTO();
			String diagnose_type_name = (String) s.get("diagnose_type_name"
					.toUpperCase());
			String SSN = (String) s.get("SSN");
			String BIZ_TYPE = (String) s.get("BIZ_TYPE");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String ID_CARD = (String) s.get("ID_CARD");
			String ICDNAME = (String) s.get("DIAGNOSE_NAME");
			BigDecimal ASSISMONEY = (BigDecimal) s.get("ASSISMONEY");
			BigDecimal PAYSELF = (BigDecimal) s.get("PAYSELF");
			BigDecimal BIZ_ID = (BigDecimal) s.get("BIZ_ID");
			BigDecimal TOTAL = (BigDecimal) s.get("TOTAL");
			String NAME = (String) s.get("NAME");
			String HNAME = (String) s.get("HNAME");
			Date ENDTIME = (Date) s.get("END_TIME");
			Date BEGINTIME = (Date) s.get("BEGIN_TIME");
			Date OPERTIME = (Date) s.get("OPER_TIME");
			BigDecimal SCALER = (BigDecimal) s.get("SCALER");
			e.setEndTime(ENDTIME);
			e.setBeginTime(BEGINTIME);
			e.setOperTime(OPERTIME);
			e.setBizId(BIZ_ID.intValue());
			e.setName(NAME);
			e.setFamilyNo(FAMILY_NO);
			if (null != ASSISMONEY) {
				e.setAssismoney(ASSISMONEY.toString());
			} else {
				e.setAssismoney("0");
			}
			if (null != TOTAL) {
				e.setTotal(TOTAL.toString());
			} else {
				e.setTotal("0");
			}
			if (null != PAYSELF) {
				e.setPayself(PAYSELF.toString());
			} else {
				e.setPayself("0");
			}
			e.setHname(HNAME);
			e.setIcdname(ICDNAME);
			e.setSsn(SSN);
			e.setIdCard(ID_CARD);
			e.setDtypename(diagnose_type_name);
			if (null != SCALER) {
				e.setScaler(SCALER.toString());
			} else {
				e.setScaler("0");
			}
			// 1：门诊，2，住院，3：购药
			if ("1".equals(BIZ_TYPE) && "0".equals(e.getScaler())) {
				e.setBizType("门诊");
			}
			if ("2".equals(BIZ_TYPE) && "0".equals(e.getScaler())) {
				e.setBizType("住院");
			}
			if ("3".equals(BIZ_TYPE) && "0".equals(e.getScaler())) {
				e.setBizType("购药");
			}
			if ("1".equals(BIZ_TYPE) && !"0".equals(e.getScaler())) {
				e.setBizType("重大疾病门诊注射");
			}
			if ("2".equals(BIZ_TYPE) && !"0".equals(e.getScaler())) {
				e.setBizType("重大疾病住院");
			}
			if ("3".equals(BIZ_TYPE) && !"0".equals(e.getScaler())) {
				e.setBizType("重大疾病门诊购药");
			}
			e.setMeminfo((String) s.get("MEMINFO"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findInfo(String sql) {
		String info = "";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			info = "总人次：" + s.get("ZRC").toString() + " 总金额："
					+ s.get("ZM").toString() + "（元）" + " 总救助金："
					+ s.get("ZJZJ").toString() + "（元）" + " 个人承担："
					+ s.get("ZJZJ1").toString() + "（元）";
		}
		return info;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BillDTO> findcheckaccounts(BillDTO billDTO) {
		List<BillDTO> list = new ArrayList<BillDTO>();
		String sql = "select d.name as dmname, "
				+ " sum(decode(biz.biz_type, '3', 1, 0)) as mzcs, "
				+ " sum(decode(biz.biz_type, '3', c.assismoney, 0)) as mzjzj, "
				+ " sum(decode(biz.biz_type, '3', c.total, 0)) as mzzfy, "
				+ " sum(decode(biz.biz_type, '3', c.payself, 0)) as mzgrzf, "
				+ " sum(decode(biz.biz_type, '3', c.medicare, 0)) as mztc, "
				+ " sum(decode(biz.biz_type, "
				+ " '3', "
				+ " (c.total - c.payself - c.medicare - c.assismoney), "
				+ " 0)) as mzqt, "
				+ " sum(decode(biz.biz_type, '3', 1, 0)) as zycs, "
				+ " sum(decode(biz.biz_type, '3', c.assismoney, 0)) as zyjzj, "
				+ " sum(decode(biz.biz_type, '3', c.total, 0)) as zyzfy, "
				+ " sum(decode(biz.biz_type, '3', c.payself, 0)) as zygrzf, "
				+ " sum(decode(biz.biz_type, '3', c.medicare, 0)) as zytc, "
				+ " sum(decode(biz.biz_type, "
				+ " '3', "
				+ " (c.total - c.payself - c.medicare - c.assismoney), "
				+ " 0)) as zyqt "
				+ " from jz_biz biz, "
				+ " (select sum(nvl(b.pay_total, 0)) as total, "
				+ " sum(nvl(b.pay_assist, 0)) as assismoney, "
				+ " sum(nvl(b.pay_self, 0)) as payself, "
				+ " sum(nvl(b.pay_medicare, 0) + nvl(b.pay_other, 0)) as medicare, "
				+ " b.biz_id " + " from jz_pay b "
				+ " where   mod(b.seq,2)=1 and b.sts=1 and 1 = 1 "
				+ " and b.oper_time BETWEEN TO_DATE('" + billDTO.getBeginDate()
				+ "', 'yyyy-MM-dd') AND " + " TO_DATE('" + billDTO.getEndDate()
				+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss') "
				+ " group by b.biz_id) c, " + " bizdept d "
				+ " where c.biz_id = biz.biz_id "
				+ " and d.hospital_id = biz.hospital_id "
				+ " and biz.biz_type in (3) and biz.assist_flag = 1"
				+ " and biz.organization_id like '"
				+ billDTO.getOrganizationId() + "%'  "
				+ " group by d.name  order by d.name";
		// MZCS MZJZJ MZZFY MZGRZF MZTC MZQT ZYCS ZYJZJ ZYZFY ZYGRZF ZYTC ZYQT
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
			e.setZyqt((BigDecimal) s.get("ZYQT"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OutIcdDTO> findOutIcdByOrg(String organizationId) {
		String sql = "select a.*, i.name " + " from out_icd a, icd10 i "
				+ " where a.organization_id = " + organizationId + " "
				+ " and i.icd_id = a.icd_id "
				+ " and a.sts = 1   and a.calc_type=1 and a.outtype=2"
				+ " order by a.seq";
		List<OutIcdDTO> list = new ArrayList<OutIcdDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		// OUTTYPE ICD_ID CALC_TYPE FIX_VALUE SCALE SEQ NAME
		for (HashMap s : rs) {
			OutIcdDTO e = new OutIcdDTO();
			String OUTTYPE = (String) s.get("OUTTYPE");
			e.setOuttype(OUTTYPE);
			BigDecimal ICD_ID = (BigDecimal) s.get("ICD_ID");
			e.setIcdId(ICD_ID.intValue());
			String CALC_TYPE = (String) s.get("CALC_TYPE");
			e.setCalcType(CALC_TYPE);
			BigDecimal FIX_VALUE = (BigDecimal) s.get("FIX_VALUE");
			e.setFixValue(FIX_VALUE);
			BigDecimal SCALE = (BigDecimal) s.get("SCALE");
			e.setScale(SCALE);
			BigDecimal SEQ = (BigDecimal) s.get("SEQ");
			e.setSeq(SEQ.intValue());
			String NAME = (String) s.get("NAME");
			e.setName(NAME);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BizDTO> findBizCheckAccountstat(String sql) {
		List<BizDTO> list = new ArrayList<BizDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			BizDTO e = new BizDTO();
			BigDecimal ASSISMONEY = (BigDecimal) s.get("ASSISMONEY");
			String HNAME = (String) s.get("HNAME");
			if (null != ASSISMONEY) {
				e.setAssismoney(ASSISMONEY.toString());
			} else {
				e.setAssismoney("0");
			}
			e.setHname(HNAME);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, String> findChronicSubject(String organizationId) {
		HashMap<String, String> map = new HashMap<String, String>();
		String sql = " select distinct (subject) as subject  from chronic_bill bill "
				+ " where REGEXP_LIKE(bill.subject, '存入')  and REGEXP_instr(bill.subject, '退费')=0  "
				+ " and bill.optsts ='1' "
				+ " and exists (select 1  from MEMBER_BASEINFOVIEW02 mem  where bill.member_id = mem.member_id "
				+ " and bill.member_type = mem.ds  and mem.familyno like '"
				+ organizationId + "%') ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			map.put((String) s.get("SUBJECT"), (String) s.get("SUBJECT"));
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ChronicCheckDTO> findChronicMoney01(String sql,
			int currentpage, String url) {
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
			String FAMILYNO = (String) s.get("FAMILYNO");
			e.setFamilyno(FAMILYNO);
			BigDecimal income = (BigDecimal) s.get("INCOME");
			e.setIncome(income.doubleValue());
			e.setMeminfo((String) s.get("MEMINFO"));
			e.setDetail((String) s.get("SUBJECT"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BizDTO> findBizCheckAccountstat1(String sql) {
		List<BizDTO> list = new ArrayList<BizDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			BizDTO e = new BizDTO();
			BigDecimal ASSISMONEY = (BigDecimal) s.get("INCOME");
			BigDecimal RC = (BigDecimal) s.get("RC");
			String HNAME = (String) s.get("ORGNAME");
			if (null != ASSISMONEY) {
				e.setAssismoney(ASSISMONEY.toString());
			} else {
				e.setAssismoney("0");
			}
			e.setHname(HNAME);
			e.setTotal(RC.toString());
			list.add(e);
		}
		return list;
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

	public ChronicCheckDAO getChronicCheckDAO() {
		return chronicCheckDAO;
	}

	public void setChronicCheckDAO(ChronicCheckDAO chronicCheckDAO) {
		this.chronicCheckDAO = chronicCheckDAO;
	}

	public ChronicStatusDAO getChronicStatusDAO() {
		return chronicStatusDAO;
	}

	public void setChronicStatusDAO(ChronicStatusDAO chronicStatusDAO) {
		this.chronicStatusDAO = chronicStatusDAO;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public Pager getPager() {
		return pager;
	}

	public void setMemberBaseinfoviewDAO(
			MemberBaseinfoviewDAO memberBaseinfoviewDAO) {
		this.memberBaseinfoviewDAO = memberBaseinfoviewDAO;
	}

	public MemberBaseinfoviewDAO getMemberBaseinfoviewDAO() {
		return memberBaseinfoviewDAO;
	}

	public void setChronicItemDAO(ChronicItemDAO chronicItemDAO) {
		this.chronicItemDAO = chronicItemDAO;
	}

	public ChronicItemDAO getChronicItemDAO() {
		return chronicItemDAO;
	}

	public OutIcdDAO getOutIcdDAO() {
		return outIcdDAO;
	}

	public void setOutIcdDAO(OutIcdDAO outIcdDAO) {
		this.outIcdDAO = outIcdDAO;
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setChronicSessionDAO(ChronicSessionDAO chronicSessionDAO) {
		this.chronicSessionDAO = chronicSessionDAO;
	}

	public ChronicSessionDAO getChronicSessionDAO() {
		return chronicSessionDAO;
	}
}
