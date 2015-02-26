package com.mingda.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import oracle.sql.BLOB;

import com.mingda.common.Pager;
import com.mingda.dao.AddressBookDAO;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.JzBizDAO;
import com.mingda.dao.JzBizcheckDAO;
import com.mingda.dao.JzBizmediaDAO;
import com.mingda.dao.JzPayDAO;
import com.mingda.dto.AddrBookDTO;
import com.mingda.dto.BillDTO;
import com.mingda.dto.BizCheckDTO;
import com.mingda.dto.BizDTO;
import com.mingda.dto.GsDTO;
import com.mingda.dto.MediaDTO;
import com.mingda.dto.PayDTO;
import com.mingda.model.AddressBook;
import com.mingda.model.JzBiz;
import com.mingda.model.JzBizcheck;
import com.mingda.model.JzBizcheckWithBLOBs;
import com.mingda.model.JzBizmedia;
import com.mingda.model.JzBizmediaExample;
import com.mingda.model.JzPay;
import com.mingda.model.JzPayExample;

public class BaseBizServiceImpl implements BaseBizService {

	private Pager pager;
	private ExtendsDAO extendsDAO;
	private JzBizcheckDAO jzBizcheckDAO;
	private JzBizDAO jzBizDAO;
	private JzPayDAO jzPayDAO;
	private AddressBookDAO addressBookDAO;
	private JzBizmediaDAO jzBizmediaDAO;

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		// BIZ_ID TOTAL AYOUTMEDICARE PAYASSISTSCOPE PAYMEDICARE
		for (HashMap s : rs) {
			BizDTO e = new BizDTO();
			String SSN = (String) s.get("SSN");
			String BIZ_TYPE = (String) s.get("BIZ_TYPE");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String ID_CARD = (String) s.get("ID_CARD");
			String ICDNAME = (String) s.get("DIAGNOSE_NAME");
			BigDecimal ASSISMONEY = (BigDecimal) s.get("ASSISMONEY");
			BigDecimal PAYSELF = (BigDecimal) s.get("PAYSELF");
			BigDecimal PAYOUTMEDICARE = (BigDecimal) s.get("PAYOUTMEDICARE");
			BigDecimal PAYASSISTSCOPE = (BigDecimal) s.get("PAYASSISTSCOPE");
			BigDecimal PAYMEDICARE = (BigDecimal) s.get("PAYMEDICARE");
			BigDecimal BIZ_ID = (BigDecimal) s.get("BIZ_ID");
			BigDecimal TOTAL = (BigDecimal) s.get("TOTAL");
			String NAME = (String) s.get("NAME");
			String HNAME = (String) s.get("HNAME");
			String INAME = (String) s.get("INAME");
			Date ENDTIME = (Date) s.get("END_TIME");
			Date BEGINTIME = (Date) s.get("BEGIN_TIME");
			Date OPERTIME = (Date) s.get("OPER_TIME");
			BigDecimal PAYCIASSIST = (BigDecimal) s.get("PAYCIASSIST");
			String MASTERNAME = (String) s.get("MASTERNAME");
			String IDCARD = (String) s.get("ID_CARD");
			String MEMINFO = (String) s.get("MEMINFO");
			//start 梅河口20131018重大疾病-------------------------------------
			BigDecimal DIAGNOSETYPEID = (BigDecimal) s.get("DIAGNOSE_TYPE_ID") ;
			String DORG = (String) s.get("DORG");
			BigDecimal DTYPEID = (BigDecimal) s.get("DTYPEID");
		    String DTYPENAME = (String) s.get("DTYPENAME");
		    BigDecimal SCALER = (BigDecimal) s.get("SCALER");
		    //end 梅河口20131018重大疾病-------------------------------------
		    String INTYPEID = (String) s.get("INTYPEID");
		    e.setIntypeid(INTYPEID);
 			e.setEndTime(ENDTIME);
			e.setBeginTime(BEGINTIME);
			e.setOperTime(OPERTIME);
			e.setBizId(BIZ_ID.intValue());
			e.setName(NAME);
			e.setFamilyNo(FAMILY_NO);
			e.setMastername(MASTERNAME);
			e.setPaperid(IDCARD);
			e.setMeminfo(MEMINFO);
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
			if (null != PAYOUTMEDICARE) {
				e.setPayoutmedicare(PAYOUTMEDICARE.toString());
			} else {
				e.setPayoutmedicare("0");
			}
			if (null != PAYASSISTSCOPE) {
				e.setPayassistscope(PAYASSISTSCOPE.toString());
			} else {
				e.setPayassistscope("0");
			}
			if (null != PAYMEDICARE) {
				e.setPaymedicare(PAYMEDICARE.toString());
			} else {
				e.setPaymedicare("0");
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
			e.setIname(INAME);
			
			if (null != PAYCIASSIST) {
				e.setPayciassist(PAYCIASSIST.toString());
			} else {
				e.setPayciassist("0");
			}
			//start 梅河口20131018重大疾病-------------------------------------
			if(null != DIAGNOSETYPEID){
				e.setDiagnoseTypeId(DIAGNOSETYPEID.toString());
			}
			e.setDorg(DORG);
			if(null != DTYPEID){
				e.setDtypeid(DTYPEID.toString());
			}
			e.setDtypename(DTYPENAME);
			if (null != SCALER) {
				e.setScaler(SCALER.toString());
			} else {
				e.setScaler("0");
			}
			//end   梅河口20131018重大疾病-------------------------------------
			// 1：门诊，2，住院，3：购药 ，4：重大疾病住院，5：重大疾病门诊注射，6：重大疾病门诊购药
						if ("1".equals(BIZ_TYPE) && "0".equals(e.getScaler())) {
							e.setBizType("门诊");
						}
						if ("2".equals(BIZ_TYPE) && "0".equals(e.getScaler())) {
							e.setBizType("住院");
						}
						if ("3".equals(BIZ_TYPE)&& "0".equals(e.getScaler())) {
							e.setBizType("购药");
						}
						if ("1".equals(BIZ_TYPE) && !"0".equals(e.getScaler())) {
							e.setBizType("重大疾病门诊注射");
						}
						if ("2".equals(BIZ_TYPE) && !"0".equals(e.getScaler())) {
							e.setBizType("重大疾病住院");
						}
						if ("3".equals(BIZ_TYPE)&& !"0".equals(e.getScaler())) {
							e.setBizType("重大疾病门诊购药");
						}
			e.setBiztype(BIZ_TYPE);
			list.add(e);
		}
		return list;
	}

	@Override
	public byte[] findBizBlob(String bizcheck, String cPic) {
		byte[] vs = new byte[0];
		if(null==bizcheck||"".equals(bizcheck)){
			return null;
		}
		JzBizcheckWithBLOBs r = this.jzBizcheckDAO
				.selectByPrimaryKey(new BigDecimal(bizcheck));

		if ("1".equals(cPic)) {
			vs = r.getcPic01();
		}
		if ("2".equals(cPic)) {
			vs = r.getcPic02();
		}
		if ("3".equals(cPic)) {
			vs = r.getcPic03();
		}
		return vs;
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
					+ s.get("ZJZJ").toString() + "（元）" + " 个人承担："
					+ s.get("ZJZJ1").toString() + "（元）";
		}
		return info;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BizCheckDTO> findMedicalMembers(String sql, int currentpage,
			String url) {

		List<BizCheckDTO> list = new ArrayList<BizCheckDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		// BIZ_ID SSN HNAME BIZ_TYPE FAMILY_NO NAME ID_CARD ICDNAME CHECK1
		// CHECK2
		for (HashMap s : rs) {
			BizCheckDTO e = new BizCheckDTO();
			String SSN = (String) s.get("SSN");
			String BIZ_TYPE = (String) s.get("BIZ_TYPE");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String ID_CARD = (String) s.get("ID_CARD");
			String ICDNAME = (String) s.get("DIAGNOSE_NAME");
			BigDecimal BIZ_ID = (BigDecimal) s.get("BIZ_ID");
			String NAME = (String) s.get("NAME");
			String HNAME = (String) s.get("HNAME");
			BigDecimal CHECK1 = (BigDecimal) s.get("CHECKED1");
			BigDecimal CHECK2 = (BigDecimal) s.get("CHECKED2");
			String DEPT_NAME = (String) s.get("DEPT_NAME");
			String AREA_NAME = (String) s.get("AREA_NAME");
			Date begintime = (Date) s.get("BEGIN_TIME");
			Date endtime = (Date) s.get("END_TIME");
			BigDecimal MONEYSTAND = (BigDecimal) s.get("MONEYSTAND");
			String DIAGNOSE_TYPE_NAME = (String) s.get("DIAGNOSE_TYPE_NAME");
			String PERSON_TYPE = (String)s.get("PERSON_TYPE");
			String MEMBER_TYPE = (String) s.get("MEMBER_TYPE");
			String PERSON_TYPE_TXT = this.getassisttext(PERSON_TYPE, MEMBER_TYPE);
 			e.setPersonType(PERSON_TYPE_TXT);
			e.setBizId(BIZ_ID.intValue());
			e.setName(NAME);
			e.setFamilyNo(FAMILY_NO);
			e.setHname(HNAME);
			e.setDeptName(DEPT_NAME);
			e.setAreaName(AREA_NAME);
			// 计算天数
			long days = 0;
			if (null != begintime) {
				if (null == endtime) {
					Date now = new Date();
					days = (now.getTime() - begintime.getTime())
							/ (24 * 60 * 60 * 1000);
				} else {
					days = (endtime.getTime() - begintime.getTime())
							/ (24 * 60 * 60 * 1000);
				}
			}
			e.setDays(String.valueOf(days));
			e.setBeginTime(begintime);
			e.setEndTime(endtime);
			e.setIcdname(ICDNAME);
			e.setSsn(SSN);
			e.setIdCard(ID_CARD);
			// 1：门诊，2，住院，3：购药
			if ("1".equals(BIZ_TYPE)) {
				e.setBizType("门诊");
			}
			if ("2".equals(BIZ_TYPE)) {
				e.setBizType("住院");
			}
			if ("3".equals(BIZ_TYPE)) {
				e.setBizType("购药");
			}
			e.setChecked1(CHECK1);
			e.setChecked2(CHECK2);
			e.setDiagnoseTypeName(DIAGNOSE_TYPE_NAME);
			e.setMondeystand(MONEYSTAND);
			list.add(e);
		}
		return list;
	}

	public void saveBizCheck(BizCheckDTO bizCheckDTO) {
		String organizationId = bizCheckDTO.getOrganizationId();
		BigDecimal checked = bizCheckDTO.getChecked();
		String detail = bizCheckDTO.getDetail();
		String operator = bizCheckDTO.getOperator();
		String operator2 = bizCheckDTO.getOperator2();
		String operator1name = bizCheckDTO.getOperator1name();
		String operator2name = bizCheckDTO.getOperator2name();
		BigDecimal checkid = bizCheckDTO.getBizcheck();
		if (8 == organizationId.length()) {
			JzBizcheckWithBLOBs record = new JzBizcheckWithBLOBs();
			record.setBizId(bizCheckDTO.getBizId());
			record.setChecked1(checked);
			record.setChecktime(new Date());
			record.setDetail1(detail);
			record.setOpttime(new Date());
			record.setOperator(operator);
			record.setOperatorname(operator1name);
			if (null == checkid) {
				jzBizcheckDAO.insertSelective(record);
			} else {
				record.setBizcheck(checkid);
				jzBizcheckDAO.updateByPrimaryKeySelective(record);
			}
		} else if (6 == organizationId.length()) {
			JzBizcheckWithBLOBs record = new JzBizcheckWithBLOBs();
			record.setBizId(bizCheckDTO.getBizId());
			record.setChecked2(checked);
			record.setChecktime2(new Date());
			record.setDetail2(detail);
			record.setOpttime2(new Date());
			record.setBizcheck(checkid);
			record.setOperator2(operator2);
			record.setOperator2name(operator2name);
			if (null == checkid) {
				jzBizcheckDAO.insertSelective(record);
			} else {
				jzBizcheckDAO.updateByPrimaryKeySelective(record);
			}
			if (checked.toString().equals("0")) {
				JzBiz record1 = new JzBiz();
				record1.setBizId(bizCheckDTO.getBizId());
				record1.setAuditFlag("2");
				jzBizDAO.updateByPrimaryKeySelective(record1);
			} else if (checked.toString().equals("1")) {
				JzBiz record1 = new JzBiz();
				record1.setBizId(bizCheckDTO.getBizId());
				record1.setAuditFlag("1");
				jzBizDAO.updateByPrimaryKeySelective(record1);
			} else if (checked.toString().equals("3")) {
				JzBiz record1 = new JzBiz();
				record1.setBizId(bizCheckDTO.getBizId());
				record1.setAuditFlag("3");
				jzBizDAO.updateByPrimaryKeySelective(record1);
			}
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BizCheckDTO findBizCheck(BizCheckDTO e) {
		String sql = "select biz.biz_id, biz.ssn, d.name as hname, "
				+ " biz.biz_type,   biz.family_no,  biz.name, "
				+ " biz.id_card, e.name as icdname, "
				+ " ck.checked1,  ck.checked2 ,ck.bizcheck ,ck.detail1,ck.detail2 ,"
				+ " biz.DIAGNOSE_NAME ,ck.checktime,"
				+ " ck.checktime2,ck.operatorname ,ck.operator2name , "
				+ " biz.begin_time,biz.end_time , biz.dept_name, biz.area_name , ck.c_pic01,  ck.c_pic02,  ck.c_pic03,  ck.ds "
				+ " from jz_bizcheck ck, jz_biz biz, bizdept d, icd10 e "
				+ " where ck.biz_id(+) = biz.biz_id "
				+ " and d.hospital_id(+) = biz.hospital_id "
				+ " and e.icd_id(+) = biz.icd_id " + " and biz.biz_id='"
				+ e.getBizId() + "'";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			HashMap s = rs.get(0);
			String SSN = (String) s.get("SSN");
			String BIZ_TYPE = (String) s.get("BIZ_TYPE");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String ID_CARD = (String) s.get("ID_CARD");
			String ICDNAME = (String) s.get("DIAGNOSE_NAME");
			BigDecimal BIZ_ID = (BigDecimal) s.get("BIZ_ID");
			String NAME = (String) s.get("NAME");
			String HNAME = (String) s.get("HNAME");
			BigDecimal BIZCHECK = (BigDecimal) s.get("BIZCHECK");
			BigDecimal CHECK1 = (BigDecimal) s.get("CHECKED1");
			BigDecimal CHECK2 = (BigDecimal) s.get("CHECKED2");
			String DETAIL1 = (String) s.get("DETAIL1");
			String DETAIL2 = (String) s.get("DETAIL2");
			Date checktime = (Date) s.get("CHECKTIME");
			Date checktime2 = (Date) s.get("CHECKTIME2");
			String OPERATORNAME = (String) s.get("OPERATORNAME");
			String OPERATOR2NAME = (String) s.get("OPERATOR2NAME");
			String DEPT_NAME = (String) s.get("DEPT_NAME");
			String AREA_NAME = (String) s.get("AREA_NAME");
			Date begintime = (Date) s.get("BEGIN_TIME");
			Date endtime = (Date) s.get("END_TIME");
			e.setDeptName(DEPT_NAME);
			e.setAreaName(AREA_NAME);
			e.setBeginTime(begintime);
			e.setEndTime(endtime);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (null != checktime) {
				e.setChecktimes(sdf.format(checktime));
			}
			if (null != checktime2) {
				e.setChecktime2s(sdf.format(checktime2));
			}
			e.setBizId(BIZ_ID.intValue());
			e.setName(NAME);
			e.setFamilyNo(FAMILY_NO);
			e.setHname(HNAME);
			e.setIcdname(ICDNAME);
			e.setSsn(SSN);
			e.setIdCard(ID_CARD);
			e.setChecktime(checktime);
			e.setChecktime2(checktime2);
			// 1：门诊，2，住院，3：购药
			if ("1".equals(BIZ_TYPE)) {
				e.setBizType("门诊");
			}
			if ("2".equals(BIZ_TYPE)) {
				e.setBizType("住院");
			}
			if ("3".equals(BIZ_TYPE)) {
				e.setBizType("购药");
			}
			e.setBizcheck(BIZCHECK);
			e.setChecked1(CHECK1);
			e.setChecked2(CHECK2);
			e.setDetail1(DETAIL1);
			e.setDetail2(DETAIL2);
			e.setOperator1name(OPERATORNAME);
			e.setOperator2name(OPERATOR2NAME);
			BLOB bb1 = (BLOB) s.get("C_PIC01");
			BLOB bb2 = (BLOB) s.get("C_PIC01");
			BLOB bb3 = (BLOB) s.get("C_PIC01");
			if (null != bb1) {
				e.setcPic01(bb1.getBytes());
			}
			if (null != bb2) {
				e.setcPic02(bb2.getBytes());
			}
			if (null != bb3) {
				e.setcPic03(bb3.getBytes());
			}

			e.setDs((String) s.get("DS"));
		}
		return e;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BillDTO> findcheckaccountsc(BillDTO billDTO) {
		List<BillDTO> list = new ArrayList<BillDTO>();
		String b = "";
		if ("1".equals(billDTO.getDs())) {
			b = "  and biz.member_type='1' ";
		} else if ("2".equals(billDTO.getDs())) {
			b = "  and biz.member_type='2' ";
		} else {
		}

		String sql = "select d.name as dmname, "
				+ " sum(decode(biz.biz_type, '1', 1, 0)) as mzcs, "
				+ " sum(decode(biz.biz_type, '1', c.assismoney, 0)) as mzjzj, "
				+ " sum(decode(biz.biz_type, '1', c.total, 0)) as mzzfy, "
				+ " sum(decode(biz.biz_type, '1', c.payself+(c.total - c.payself - c.medicare - c.assismoney), 0)) as mzgrzf, "
				+ " sum(decode(biz.biz_type, '1', c.medicare, 0)) as mztc, "
				+ " sum(decode(biz.biz_type, "
				+ " '1', "
				+ " 0, "
				+ " 0)) as mzqt, "
				+ " sum(decode(biz.biz_type, '2', 1, 0)) as zycs, "
				+ " sum(decode(biz.biz_type, '2', c.assismoney, 0)) as zyjzj, "
				+ " sum(decode(biz.biz_type, '2', c.total, 0)) as zyzfy, "
				+ " sum(decode(biz.biz_type, '2', c.payself+(c.total - c.payself - c.medicare - c.assismoney), 0)) as zygrzf, "
				+ " sum(decode(biz.biz_type, '2', c.medicare, 0)) as zytc, "
				+ " sum(decode(biz.biz_type, "
				+ " '2', "
				+ " 0, "
				+ " 0)) as zyqt "
				+ " from jz_biz biz, "
				+ " (select sum(nvl(b.pay_total, 0)) as total, "
				+ " sum(nvl(b.pay_assist, 0)) as assismoney, "
				+ " sum(nvl(b.pay_self, 0)) as payself, "
				+ " sum(nvl(b.pay_medicare, 0) + nvl(b.pay_other, 0)) as medicare, "
				+ " b.biz_id "
				+ " from jz_pay b "
				+ " where   mod(b.seq,2)=1 and b.sts=1 and 1 = 1 "
				+ " and b.oper_time BETWEEN TO_DATE('"
				+ billDTO.getBeginDate()
				+ "', 'yyyy-MM-dd') AND "
				+ " TO_DATE('"
				+ billDTO.getEndDate()
				+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss') "
				+ " group by b.biz_id) c, "
				+ " bizdept d "
				+ " where c.biz_id = biz.biz_id "
				+ " "
				+ b
				+ " and d.hospital_id = biz.out_hospital_id "
				+ " and biz.biz_type in ('1','2') and biz.out_biz_id is not null and biz.assist_flag = 1"
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BillDTO> findcheckaccounts(BillDTO billDTO) {
		List<BillDTO> list = new ArrayList<BillDTO>();
		String b = "";
		if ("1".equals(billDTO.getDs())) {
			b = "  and biz.member_type='1' ";
		} else if ("2".equals(billDTO.getDs())) {
			b = "  and biz.member_type='2' ";
		} else {
		}

		String sql = "select d.name as dmname, "
				+ " sum(decode(biz.biz_type, '1', 1, 0)) as mzcs, "
				+ " sum(decode(biz.biz_type, '1', c.assismoney, 0)) as mzjzj, "
				+ " sum(decode(biz.biz_type, '1', c.total, 0)) as mzzfy, "
				+ " sum(decode(biz.biz_type, '1', c.payself+(c.total - c.payself - c.medicare - c.assismoney), 0)) as mzgrzf, "
				+ " sum(decode(biz.biz_type, '1', c.medicare, 0)) as mztc, "
				+ " sum(decode(biz.biz_type, "
				+ " '1', "
				+ " 0, "
				+ " 0)) as mzqt, "
				+ " sum(decode(biz.biz_type, '2', 1, 0)) as zycs, "
				+ " sum(decode(biz.biz_type, '2', c.assismoney, 0)) as zyjzj, "
				+ " sum(decode(biz.biz_type, '2', c.total, 0)) as zyzfy, "
				+ " sum(decode(biz.biz_type, '2', c.payself+(c.total - c.payself - c.medicare - c.assismoney), 0)) as zygrzf, "
				+ " sum(decode(biz.biz_type, '2', c.medicare, 0)) as zytc, "
				+ " sum(decode(biz.biz_type, '2', c.payciassist, 0)) as zydbbx, "
				+ " sum(decode(biz.biz_type, "
				+ " '2', "
				+ " 0, "
				+ " 0)) as zyqt "
				+ " from jz_biz biz, "
				+ " (select sum(nvl(b.pay_total, 0)) as total, "
				+ " sum(nvl(b.pay_assist, 0)) as assismoney, "
				+ " sum(nvl(b.pay_self, 0)) as payself, "
				+ " sum(nvl(b.pay_medicare, 0) + nvl(b.pay_other, 0)) as medicare, "
				+ " sum(nvl(b.pay_ciassist, 0)) as payciassist, "
				+ " b.biz_id "
				+ " from jz_pay b "
				+ " where   mod(b.seq,2)=1 and b.sts=1 and 1 = 1 "
				+ " and b.oper_time BETWEEN TO_DATE('"
				+ billDTO.getBeginDate()
				+ "', 'yyyy-MM-dd') AND "
				+ " TO_DATE('"
				+ billDTO.getEndDate()
				+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss') "
				+ " group by b.biz_id) c, "
				+ " bizdept d "
				+ " where c.biz_id = biz.biz_id "
				+ " "
				+ b
				+ " and d.hospital_id = biz.hospital_id "
				+ " and biz.biz_type in ('1','2') and biz.out_biz_id is  null and biz.assist_flag = 1"
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
			e.setZydbbx((BigDecimal) s.get("ZYDBBX"));
			e.setZyqt((BigDecimal) s.get("ZYQT"));
			list.add(e);
		}
		return list;
	}

	public List<PayDTO> findBizPayInfo(BizDTO bizDTO) {
		List<PayDTO> list = new ArrayList<PayDTO>();
		JzPayExample example = new JzPayExample();
		example.createCriteria().andBizIdEqualTo(bizDTO.getBizId());
		List<JzPay> rs = jzPayDAO.selectByExample(example);
		for (JzPay s : rs) {
			PayDTO e = new PayDTO();
			e.setPayFlag(s.getPayFlag());
			e.setPayTotal(s.getPayTotal());
			e.setPayAccount(s.getPayAccount());
			e.setPayMedicare(s.getPayMedicare());
			e.setPayOther(s.getPayOther());
			e.setPayOutmedicare(s.getPayOutmedicare());
			e.setPayMedicarescope(s.getPayMedicarescope());
			e.setPayMinline(s.getPayMinline());
			e.setPayCash(s.getPayCash());
			e.setPayAssistscope(s.getPayAssistscope());
			e.setPayAssist(s.getPayAssist());
			e.setPaySelf(s.getPaySelf());
			e.setCalcMsg(s.getCalcMsg());
			e.setUserId(s.getUserId());
			e.setOperTime(s.getOperTime());
			e.setSts(s.getSts());
			e.setSeq(s.getSeq());
			e.setBizId(s.getBizId());
			e.setPayCIAssist(s.getPayCiassist());
			list.add(e);
		}
		return list;
	}

	public BizDTO findBiz(BizDTO e) {
		JzBiz s = jzBizDAO.selectByPrimaryKey(e.getBizId());
		e.setBizId(s.getBizId());
		e.setHospitalId(s.getHospitalId());
		e.setBizType(s.getBizType());
		e.setOrganizationId(s.getOrganizationId());
		e.setCenterId(s.getCenterId());
		e.setSsn(s.getSsn());
		e.setMemberId(s.getMemberId());
		e.setMemberType(s.getMemberType());
		e.setAssistId(s.getAssistId());
		e.setFamilyNo(s.getFamilyNo());
		e.setFamilyAddress(s.getFamilyAddress());
		e.setMedicareType(s.getMedicareType());
		e.setMedicareTypeex(s.getMedicareTypeex());
		e.setPersonType(s.getPersonType());
		e.setName(s.getName());
		e.setSex(s.getSex());
		e.setIdCard(s.getIdCard());
		e.setBeginTime(s.getBeginTime());
		e.setDeptName(s.getDeptName());
		e.setAreaName(s.getAreaName());
		e.setIcdId(s.getIcdId());
		e.setDiagnoseCode(s.getDiagnoseCode());
		e.setDiagnoseName(s.getDiagnoseName());
		e.setCertPath(s.getCertPath());
		e.setEndTime(s.getEndTime());
		e.setOutFlag(s.getOutFlag());
		e.setFinTime(s.getFinTime());
		e.setConfirmFlag(s.getConfirmFlag());
		e.setAuditFlag(s.getAuditFlag());
		e.setAssistFlag(s.getAssistFlag());
		e.setOutCause(s.getOutCause());
		e.setOutHospital(s.getOutHospital());
		e.setInHospital(s.getInHospital());
		e.setBillNo(s.getBillNo());
		e.setGatherFlag(s.getGatherFlag());
		e.setUserId(s.getUserId());
		e.setOperTime(s.getOperTime());
		e.setHname(s.getHname());
		e.setIcdname(s.getDiagnoseName());
		return e;
	}

	/**
	 * 公示
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<GsDTO> findGsList(String organizationId) {
		List<GsDTO> list = new ArrayList<GsDTO>();
		String sql = "select m.familyno, m.membername, m.paperid, m.ssn, icd.name as sicken , m.address"
				+ " from MEMBER_BASEINFOVIEW02 m, chronic_status c ,chronic_check ck ,icd10 icd "
				+ " where m.member_id = c.member_id "
				+ " and m.DS = c.member_type"
				+ " and m.assist_type <> '00000' and m.familyno like '"
				+ organizationId
				+ "%'"
				+ " and c.salstate = 1 and ck.chroniccheck_id=c.curcheck_id and icd.icd_id=ck.main_id";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			GsDTO e = new GsDTO();
			e.setFamilyno((String) s.get("FAMILYNO"));
			e.setSsn((String) s.get("SSN"));
			e.setMembername((String) s.get("MEMBERNAME"));
			e.setPaperid((String) s.get("PAPERID"));
			e.setSicken((String) s.get("SICKEN"));
			e.setAddress((String) s.get("ADDRESS"));
			list.add(e);
		}
		return list;
	}

	@Override
	public AddrBookDTO findAddrBookById(Integer empId) {

		AddressBook address = addressBookDAO.selectByPrimaryKey(empId);
		AddrBookDTO detail = new AddrBookDTO();
		detail.setEmpId(address.getEmpId());
		detail.setDuty(address.getDuty());
		detail.setMobilePhone(address.getMobilePhone());
		detail.setName(address.getName());
		detail.setOrganizationId(address.getOrganizationId());
		detail.setSts(address.getSts());
		detail.setWorkPhone(address.getWorkPhone());
		return detail;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AddrBookDTO> findAddrBookList(String sql, int currentpage,
			String url, String organizationId) {
		List<AddrBookDTO> list = new ArrayList<AddrBookDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			AddrBookDTO e = new AddrBookDTO();
			e.setDuty((String) s.get("DUTY"));
			e.setEmpId(((BigDecimal) s.get("EMP_ID")).intValue());
			e.setMobilePhone((String) s.get("MOBILE_PHONE"));
			e.setName((String) s.get("NAME"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setSts((String) s.get("STS"));
			e.setWorkPhone((String) s.get("WORK_PHONE"));
			e.setOrgname((String) s.get("ORGNAME"));
			// 操作权限
			if (e.getOrganizationId().equals(organizationId)) {
				e.setPrivilege("1");
			} else {
				e.setPrivilege("0");
			}
			list.add(e);
		}
		return list;
	}

	@Override
	public void saveAddrBook(AddrBookDTO addrBookDTO) {
		Integer empid = addrBookDTO.getEmpId();
		AddressBook record = new AddressBook();

		record.setDuty(addrBookDTO.getDuty());
		record.setMobilePhone(addrBookDTO.getMobilePhone());
		record.setName(addrBookDTO.getName());
		record.setOrganizationId(addrBookDTO.getOrganizationId());
		record.setWorkPhone(addrBookDTO.getWorkPhone());
		record.setSts(addrBookDTO.getSts());
		if (null == empid || "".equals(empid)) {
			addressBookDAO.insertSelective(record);
		} else {
			record.setEmpId(addrBookDTO.getEmpId());
			addressBookDAO.updateByPrimaryKeySelective(record);
		}
	}

	public void delAddrBook(Integer empId) {
		addressBookDAO.deleteByPrimaryKey(empId);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BizDTO> findBizs(String sql) {
		List<BizDTO> list = new ArrayList<BizDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			BizDTO e = new BizDTO();
			String DNAME = (String) s.get("DNAME");
			String NAME = (String) s.get("NAME");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String FAMILY_ADDRESS = (String) s.get("FAMILY_ADDRESS");
			String ID_CARD = (String) s.get("ID_CARD");
			String DEPT_NAME = (String) s.get("DEPT_NAME");
			String AREA_NAME = (String) s.get("AREA_NAME");
			String DIAGNOSE_NAME = (String) s.get("DIAGNOSE_NAME");
			String SSN = (String) s.get("SSN");
			BigDecimal TOTAL = (BigDecimal) s.get("TOTAL");
			BigDecimal ASSISMONEY = (BigDecimal) s.get("ASSISMONEY");
			Date BEGIN_TIME = (Date) s.get("BEGIN_TIME");
			Date END_TIME = (Date) s.get("END_TIME");
			e.setBeginTime(BEGIN_TIME);
			e.setEndTime(END_TIME);
			e.setDeptName(DEPT_NAME + AREA_NAME);
			e.setHname(DNAME);
			e.setDiagnoseName(DIAGNOSE_NAME);
			e.setAssismoney(ASSISMONEY.toString());
			e.setTotal(TOTAL.toString());
			e.setFamilyNo(FAMILY_NO);
			e.setIdCard(ID_CARD);
			e.setName(NAME);
			e.setFamilyAddress(FAMILY_ADDRESS);
			e.setSsn(SSN);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BizCheckDTO> findInHospital(String sql, int currentpage,
			String url) {
		List<BizCheckDTO> list = new ArrayList<BizCheckDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			BizCheckDTO e = new BizCheckDTO();
			String SSN = (String) s.get("SSN");
			String BIZ_TYPE = (String) s.get("BIZ_TYPE");
			String FAMILY_NO = (String) s.get("FAMILY_NO");
			String ID_CARD = (String) s.get("ID_CARD");
			String ICDNAME = (String) s.get("DIAGNOSE_NAME");
			BigDecimal BIZ_ID = (BigDecimal) s.get("BIZ_ID");
			String NAME = (String) s.get("NAME");
			String HNAME = (String) s.get("HNAME");
			String DEPT_NAME = (String) s.get("DEPT_NAME");
			String AREA_NAME = (String) s.get("AREA_NAME");
			Date BEGIN_TIME = (Date) s.get("BEGIN_TIME");
			Date END_TIME = (Date) s.get("END_TIME");
			String DIAGNOSE_NAME = (String) s.get("DIAGNOE_NAME");
			String ASSIST_FLAG = (String) s.get("ASSIST_FLAG");
			String AUDIT_FLAG = (String) s.get("AUDIT_FLAG");
			String DIAGNOSE_TYPE_NAME = (String) s.get("DIAGNOSE_TYPE_NAME");
			// 计算天数
			long days = 0;
			if (null != END_TIME) {
				days = ((END_TIME.getTime() - BEGIN_TIME.getTime()) / (24 * 60 * 60 * 1000)) + 1;
			} else {
				if (null != BEGIN_TIME) {
					Date now = new Date();
					days = ((now.getTime() - BEGIN_TIME.getTime()) / (24 * 60 * 60 * 1000)) + 1;
				}
			}
			e.setBizId(BIZ_ID.intValue());
			e.setName(NAME);
			e.setFamilyNo(FAMILY_NO);
			e.setHname(HNAME);
			e.setDeptName(DEPT_NAME);
			e.setAreaName(AREA_NAME);
			e.setBeginTime(BEGIN_TIME);
			e.setDiagnoseName(DIAGNOSE_NAME);
			e.setIcdname(ICDNAME);
			e.setSsn(SSN);
			e.setIdCard(ID_CARD);
			e.setDays(String.valueOf(days));
			// 1：门诊，2，住院，3：购药
			if ("1".equals(BIZ_TYPE)) {
				e.setBizType("门诊");
			}
			if ("2".equals(BIZ_TYPE)) {
				e.setBizType("住院");
			}
			if ("3".equals(BIZ_TYPE)) {
				e.setBizType("购药");
			}
			e.setAssistFlag(ASSIST_FLAG);
			e.setAuditFlag(AUDIT_FLAG);
			e.setEndTime(END_TIME);
			e.setDiagnoseTypeName(DIAGNOSE_TYPE_NAME);
			list.add(e);
		}
		return list;
	}

	public List<MediaDTO> findBizPics(Integer bizId, String picpath) {
		List<MediaDTO> list = new ArrayList<MediaDTO>();
		JzBizmediaExample example = new JzBizmediaExample();
		example.createCriteria().andBizIdEqualTo(bizId);
		example.setOrderByClause("filetype desc");
		List<JzBizmedia> rs = jzBizmediaDAO.selectByExample(example);
		for (JzBizmedia s : rs) {
			MediaDTO e = new MediaDTO();
			e.setBizId(s.getBizId());
			e.setBizmediaId(s.getBizmediaId());
			e.setFileallpath(picpath + s.getFilepath());
			e.setFilepath(s.getFilepath());
			e.setFiletype(s.getFiletype());
			String filetype = s.getFiletype();
			String filetypeval = "";
			if ("1".equals(filetype)) {
				filetypeval = "身份验证";
			} else if ("".equals(filetype)) {
				filetypeval = "证件";
			} else if ("3".equals(filetype)) {
				filetypeval = "结算票据";
			}
			e.setFiletypeval(filetypeval);
			list.add(e);
		}
		return list;
	}

	@Override
	public void saveCheckPic(String bizcheck, byte[] pic) {
		JzBizcheckWithBLOBs record = new JzBizcheckWithBLOBs();
		record.setBizcheck(new BigDecimal(bizcheck));
		record.setcPic01(pic);
		record.setcPic02(pic);
		record.setcPic03(pic);
		this.jzBizcheckDAO.updateByPrimaryKeySelective(record);
	}

	public String getToolsmenu() {
		return pager.genToolsmenu();
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

	public void setJzBizcheckDAO(JzBizcheckDAO jzBizcheckDAO) {
		this.jzBizcheckDAO = jzBizcheckDAO;
	}

	public JzBizcheckDAO getJzBizcheckDAO() {
		return jzBizcheckDAO;
	}

	public void setJzBizDAO(JzBizDAO jzBizDAO) {
		this.jzBizDAO = jzBizDAO;
	}

	public JzBizDAO getJzBizDAO() {
		return jzBizDAO;
	}

	public void setJzPayDAO(JzPayDAO jzPayDAO) {
		this.jzPayDAO = jzPayDAO;
	}

	public JzPayDAO getJzPayDAO() {
		return jzPayDAO;
	}

	public AddressBookDAO getAddressBookDAO() {
		return addressBookDAO;
	}

	public void setAddressBookDAO(AddressBookDAO addressBookDAO) {
		this.addressBookDAO = addressBookDAO;
	}

	public void setJzBizmediaDAO(JzBizmediaDAO jzBizmediaDAO) {
		this.jzBizmediaDAO = jzBizmediaDAO;
	}

	public JzBizmediaDAO getJzBizmediaDAO() {
		return jzBizmediaDAO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap<String, String> queryCenterPoint(String organizationId) {
		HashMap<String, String> e = new HashMap<String, String>();
		String sql = "select round( avg(p.longitude),6) as longitude, round( avg(p.latitude),6 ) as latitude "
				+ " from bizdept b, bizdept_ip p "
				+ " where b.organization_id = '"
				+ organizationId.substring(0, 6)
				+ "' "
				+ " and b.hospital_id = p.hospital_id "
				+ " and b.dept_type = '1'";
		// System.out.println(sql);
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs) {
			// System.out.println(rs.get(0).get("LONGITUDE"));
			e.put("longitude", rs.get(0).get("LONGITUDE") + "");
			e.put("latitude", rs.get(0).get("LATITUDE") + "");
		}
		return e;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BillDTO> queryHisPoint(String organizationId) {
		List<BillDTO> list = new ArrayList<BillDTO>();
		String sql = "select b.name ,p.longitude,p.latitude ,a.rs "
				+ " from bizdept b, " + "  bizdept_ip p, "
				+ " (select biz.hospital_id as hid, count(*) as rs "
				+ "  from jz_biz biz, bizdept d "
				+ " where d.hospital_id(+) = biz.hospital_id "
				+ "   and biz.biz_type = 2 " + "   and biz.reg_status = 1 "
				+ "  and (biz.assist_flag = 0 or biz.assist_flag = 2) "
				+ "  and biz.reg_status = 1 " + "  and biz.REG_STATUS = 1 "
				+ "  and biz.family_no like '" + organizationId + "%' "
				+ "  group by biz.hospital_id) a "
				+ " where a.hid = p.hospital_id "
				+ "   and b.hospital_id = p.hospital_id "
				+ "   and b.dept_type = '1'";
		// System.out.println(sql);
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			BillDTO e = new BillDTO();
			e.setDmname((String) s.get("NAME"));
			e.setBiztype((String) s.get("LONGITUDE"));
			e.setDs((String) s.get("LATITUDE"));
			e.setLjmzcs((BigDecimal) s.get("RS"));
			list.add(e);
		}
		return list;
	}
	
	private String getassisttext(String ASSIST_TYPE, String DS) {
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
}
