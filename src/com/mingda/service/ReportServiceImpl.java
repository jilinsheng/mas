package com.mingda.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mingda.common.Pager;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.PayDTO;
import com.mingda.dto.RateDTO;
import com.mingda.dto.ReportDTO;

public class ReportServiceImpl implements ReportService {
	private ExtendsDAO extendsDAO;
	private Pager pager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ReportDTO> findfeiyongcszhuyuan(String sql) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			ReportDTO e = new ReportDTO();
			e.setROWNUM((BigDecimal) s.get("ROWNUM"));
			e.setPNUM((BigDecimal) s.get("PNUM"));
			e.setTOTAL(((BigDecimal) s.get("TOTAL")));
			e.setOUTMEDICARE(((BigDecimal) s.get("OUTMEDICARE")));
			e.setMEDICARE(((BigDecimal) s.get("MEDICARE")));
			e.setASSIST(((BigDecimal) s.get("ASSIST")));
			e.setSELF(((BigDecimal) s.get("SELF")));
			e.setSUBSECTION(((String) s.get("SUBSECTION")));
			list.add(e);
		}
		return list;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ReportDTO> findjiben(String sql) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			ReportDTO e = new ReportDTO();
			e.setPNUM((BigDecimal) s.get("PNUM"));
			e.setYAODIANM((BigDecimal) s.get("YAODIANM"));
			e.setYAODIANP((BigDecimal) s.get("YAODIANP"));
			e.setMENZHENM((BigDecimal) s.get("MENZHENM"));
			e.setMENZHENP((BigDecimal) s.get("MENZHENP"));
			e.setZHUYUANM((BigDecimal) s.get("ZHUYUANM"));
			e.setZHUYUANP((BigDecimal) s.get("ZHUYUANP"));
			e.setLINSHIP((BigDecimal) s.get("LINSHIP"));
			e.setLINSHIM((BigDecimal) s.get("LINSHIM"));
			e.setREGION((String) s.get("REGION"));
			e.setORGNAME((String) s.get("ORGNAME"));
			e.setPARENTORG((String) s.get("PARENTORG"));
			e.setREGIONNAME((String) s.get("REGIONNAME"));
			list.add(e);
		}
		return list;

	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> findQuarters() {
		List<String> list = new ArrayList<String>();
		HashMap param = new HashMap();
		String sql = "select distinct to_char(biz.oper_time,'yyyy-Q') as quarter from jz_biz biz order by to_char(biz.oper_time,'yyyy-Q') desc";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			String e = (String) s.get("QUARTER");

			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> findMonth() {
		List<String> list = new ArrayList<String>();
		HashMap param = new HashMap();
		String sql = "select distinct to_char(biz.oper_time,'yyyy-mm') as month from jz_biz biz order by to_char(biz.oper_time,'yyyy-mm') desc";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			String e = (String) s.get("MONTH");

			list.add(e);

		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ReportDTO> findduixiang(String sql) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			ReportDTO e = new ReportDTO();
			e.setORGNAME((String) s.get("ORGNAME"));
			e.setPARENTORGNAME((String) s.get("PARENTORGNAME"));
			e.setORGID((String) s.get("ORGID"));
			e.setPARENTORGID((String) s.get("PARENTORGID"));
			e.setZYSWRC((BigDecimal) s.get("ZYSWRC"));
			e.setZYSWCBRC((BigDecimal) s.get("ZYSWRC"));
			e.setZYSWJE((BigDecimal) s.get("ZYSWJE"));
			e.setZYYBRC((BigDecimal) s.get("ZYYBRC"));
			e.setZYYBCBRC((BigDecimal) s.get("ZYYBCBRC"));
			e.setZYYBJE((BigDecimal) s.get("ZYYBJE"));
			e.setMZSWRC((BigDecimal) s.get("MZSWRC"));
			e.setMZSWCBRC((BigDecimal) s.get("MZSWCBRC"));
			e.setMZSWJE((BigDecimal) s.get("MZSWJE"));
			e.setMZYBRC((BigDecimal) s.get("MZYBRC"));
			e.setMZYBCBRC((BigDecimal) s.get("MZYBCBRC"));
			e.setMZYBJE((BigDecimal) s.get("MZYBJE"));
			e.setYDSWRC((BigDecimal) s.get("YDSWRC"));
			e.setYDSWCBRC((BigDecimal) s.get("YDSWCBRC"));
			e.setYDSWJE((BigDecimal) s.get("YDSWJE"));
			e.setYDYBRC((BigDecimal) s.get("YDYBRC"));
			e.setYDYBCBRC((BigDecimal) s.get("YDYBCBRC"));
			e.setYDYBJE((BigDecimal) s.get("YDYBJE"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ReportDTO> findjigou(String sql) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			ReportDTO e = new ReportDTO();
			e.setROWNUM((BigDecimal) s.get("ROWNUM"));
			e.setHOSPITAL_ID((BigDecimal) s.get("HOSPITAL_ID"));
			e.setNAME((String) s.get("NAME"));
			e.setDEPT_LEVEL((String) s.get("DEPT_LEVEL"));
			e.setPNUM((BigDecimal) s.get("PNUM"));
			e.setTOTAL((BigDecimal) s.get("TOTAL"));
			e.setOUTMEDICARE((BigDecimal) s.get("OUTMEDICARE"));
			e.setMEDICARE((BigDecimal) s.get("MEDICARE"));
			e.setASSIST((BigDecimal) s.get("ASSIST"));
			e.setSELF((BigDecimal) s.get("SELF"));
			e.setFULLNAME((String) s.get("FULLNAME"));
			// -----省市 县街
			e.setPARENTORGNAME((String) s.get("PARENTORGNAME"));
			e.setORGNAME((String) s.get("ORGNAME"));
			e.setPNUM3((BigDecimal) s.get("PNUM3"));
			e.setPNUM2((BigDecimal) s.get("PNUM2"));
			e.setPNUM1((BigDecimal) s.get("PNUM1"));
			e.setPNUM0((BigDecimal) s.get("PNUM0"));

			e.setASSIST3((BigDecimal) s.get("ASSIST3"));
			e.setASSIST2((BigDecimal) s.get("ASSIST2"));
			e.setASSIST1((BigDecimal) s.get("ASSIST1"));
			e.setASSIST0((BigDecimal) s.get("ASSIST0"));

			e.setOUTMEDICARE3((BigDecimal) s.get("OUTMEDICARE3"));
			e.setOUTMEDICARE2((BigDecimal) s.get("OUTMEDICARE2"));
			e.setOUTMEDICARE1((BigDecimal) s.get("OUTMEDICARE1"));
			e.setOUTMEDICARE0((BigDecimal) s.get("OUTMEDICARE0"));

			e.setMEDICARE3((BigDecimal) s.get("MEDICARE3"));
			e.setMEDICARE2((BigDecimal) s.get("MEDICARE2"));
			e.setMEDICARE1((BigDecimal) s.get("MEDICARE1"));
			e.setMEDICARE0((BigDecimal) s.get("MEDICARE0"));

			e.setTOTAL3((BigDecimal) s.get("TOTAL3"));
			e.setTOTAL2((BigDecimal) s.get("TOTAL2"));
			e.setTOTAL1((BigDecimal) s.get("TOTAL1"));
			e.setTOTAL0((BigDecimal) s.get("TOTAL0"));

			e.setSELF3((BigDecimal) s.get("SELF3"));
			e.setSELF2((BigDecimal) s.get("SELF2"));
			e.setSELF1((BigDecimal) s.get("SELF1"));
			e.setSELF0((BigDecimal) s.get("SELF0"));

			list.add(e);
		}

		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<OrganizationDTO> findRegion(String level) {
		List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
		HashMap param = new HashMap();
		String sql = " select * from manager_org org where org.depth = " + level + " order by org.organization_id ";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			OrganizationDTO e = new OrganizationDTO();
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setOrgname((String) s.get("ORGNAME"));
			e.setFullname((String) s.get("FULLNAME"));
			e.setParentorgid((String) s.get("PARENTOrgID"));
			e.setDepth((BigDecimal) s.get("DEPT"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ReportDTO> findzongti(String sql) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			ReportDTO e = new ReportDTO();
			e.setROWNUM((BigDecimal) s.get("ROWNUM"));
			e.setORGNAME((String) s.get("ORGNAME"));
			e.setFULLNAME((String) s.get("FULLNAME"));
			e.setNAME((String) s.get("NAME"));
			e.setFAMILY_ADDRESS((String) s.get("FAMILY_ADDRESS"));
			e.setID_CARD((String) s.get("ID_CARD"));
			e.setPERSON_TYPE((String) s.get("PERSON_TYPE"));
			e.setDIAGNOSE_NAME((String) s.get("DIAGNOSE_NAME"));
			e.setPAY_TOTAL((BigDecimal) s.get("PAY_TOTAL"));
			e.setPAY_OUTMEDICARE((BigDecimal) s.get("PAY_OUTMEDICARE"));
			e.setPAY_MEDICARE((BigDecimal) s.get("PAY_MEDICARE"));
			e.setPAY_ASSIST((BigDecimal) s.get("PAY_ASSIST"));
			e.setPAY_SELF((BigDecimal) s.get("PAY_SELF"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ReportDTO> findbingzhong(String sql) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			ReportDTO e = new ReportDTO();
			e.setROWNUM((BigDecimal) s.get("ROWNUM"));
			e.setPNUM((BigDecimal) s.get("PNUM"));
			e.setTOTAL(((BigDecimal) s.get("TOTAL")));
			e.setOUTMEDICARE(((BigDecimal) s.get("OUTMEDICARE")));
			e.setMEDICARE(((BigDecimal) s.get("MEDICARE")));
			e.setASSIST(((BigDecimal) s.get("ASSIST")));
			e.setSELF(((BigDecimal) s.get("SELF")));
			e.setNAME((String) s.get("NAME"));
			e.setTOP((BigDecimal) s.get("TOP"));
			e.setLEVEL4((BigDecimal) s.get("LEVEL4"));
			e.setLEVEL3((BigDecimal) s.get("LEVEL3"));
			e.setLEVEL2((BigDecimal) s.get("LEVEL2"));
			e.setLEVEL1((BigDecimal) s.get("LEVEL1"));
			e.setFULLNAME((String) s.get("FULLNAME"));
			e.setORGNAME((String) s.get("ORGNAME"));
			e.setPARENTORG((String) s.get("PARENTORG"));
			e.setREGIONNAME((String) s.get("REGIONNAME"));
			e.setREGION((String) s.get("REGION"));
			list.add(e);
		}
		return list;
	}

/**
 * 病种取前300种
 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> findDiagnose() {
		List<String> list = new ArrayList<String>();
		HashMap param = new HashMap();
		// String sql = "select distinct biz.diagnose_name as diagnose from jz_biz biz where biz.diagnose_name is not null";
		String sql = " select * from ( "
				+ "	select  distinct biz.diagnose_name as diagnose ,count(1) as ct from jz_biz biz where biz.diagnose_name is not null group by   biz.diagnose_name "
				+ "	order by ct desc " + "	) where rownum <100 " + "	order by rownum ";

		// String sql =
		// " select distinct icd.name as diagnose from jz_biz biz inner join ICD10 icd on biz.diagnose_code =  icd.icdcode ";
		param.put("executsql", sql.toUpperCase());
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			String e = (String) s.get("DIAGNOSE");
			//HashMap hm = new HashMap();
			//hm.put("key", e);
			//hm.put("value", e);
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ReportDTO> findjibenpnum(String sql1, List<ReportDTO> data) {
		HashMap param = new HashMap();
		param.put("executsql", sql1);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			for (ReportDTO e : data) {
				if (e.getREGION().equals(s.get("ORGANIZATION_ID"))) {
					e.setPNUM((BigDecimal) s.get("PNUM"));
				}
			}
		}
		return data;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<OrganizationDTO> findRegion(String level, String orgID) {
		List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
		HashMap param = new HashMap();
		String jwhere = "";
		if (orgID.length() >= 6) {
			jwhere = " and org.organization_id like '" + orgID.substring(0, 4) + "%' ";
		} else {
			jwhere = " and org.organization_id like '" + orgID + "%' ";
		}
		String sql = " select * from manager_org org where org.depth = " + level + jwhere + " order by org.organization_id ";
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			OrganizationDTO e = new OrganizationDTO();
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setOrgname((String) s.get("ORGNAME"));
			e.setFullname((String) s.get("FULLNAME"));
			e.setParentorgid((String) s.get("PARENTOrgID"));
			e.setDepth((BigDecimal) s.get("DEPT"));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ReportDTO> findyiliaojigou(String sql) {
		List<ReportDTO> list = new ArrayList<ReportDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			ReportDTO e = new ReportDTO();
			e.setROWNUM((BigDecimal) s.get("ROWNUM"));
			e.setNAME((String)s.get("NAME"));
			e.setLEV((String)s.get("LEV"));
			e.setFLAG0((String)s.get("FLAG0"));
			e.setFLAG1((String)s.get("FLAG1"));
			e.setFLAG2((String)s.get("FLAG2"));
			e.setFLAG3((String)s.get("FLAG3"));
			e.setFLAG4((String)s.get("FLAG4"));
			list.add(e);
		}
		return list;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PayDTO> findPayviews(String sql) {
		List<PayDTO> list = new ArrayList<PayDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			PayDTO e = new PayDTO();
			e.setSubsection((String)s.get("SUBSECTION"));
			e.setPersum((BigDecimal)s.get("PERSUM"));
			e.setPayTotal(new BigDecimal(toFormat((BigDecimal)s.get("TOTAL"))));
			e.setPayOutmedicare(new BigDecimal(toFormat((BigDecimal)s.get("OUTMEDICARE"))));
			e.setPayMedicare(new BigDecimal(toFormat((BigDecimal)s.get("MEDICARE"))));
			e.setPayAssist(new BigDecimal(toFormat((BigDecimal)s.get("ASSIST"))));
			e.setPaySelf(new BigDecimal(toFormat((BigDecimal)s.get("SELF"))));
			e.setPayCIAssist(new BigDecimal(toFormat((BigDecimal)s.get("CIASSIST"))));
			list.add(e);
		}
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<RateDTO> findRate(String sql){
		List<RateDTO> list = new ArrayList<RateDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			RateDTO e = new RateDTO();
			e.setSubsection((String)s.get("SUBSECTION"));
			e.setPersum((BigDecimal)s.get("PERSUM"));
			e.setPertotal((BigDecimal)s.get("PERTOTAL"));
			e.setRate((BigDecimal)s.get("RATE"));
			list.add(e);
		}
		return list;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes" })
	public List<PayDTO> findPayPersons(String sql, int currentpage,
			String url) {
		List<PayDTO> list = new ArrayList<PayDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			PayDTO e = new PayDTO();
			e.setMemberId((String)s.get("MEMBERID"));
			e.setMembeType((String)s.get("MEMBERTYPE"));
			e.setName((String)s.get("MEMBERNAME"));
			e.setPaperid((String)s.get("PAPERID"));
			e.setFamilyno((String)s.get("FAMILYNO"));
			e.setPayTotal(new BigDecimal(toFormat((BigDecimal)s.get("TOTAL"))));
			e.setPayOutmedicare(new BigDecimal(toFormat((BigDecimal)s.get("OUTMEDICARE"))));
			e.setPayMedicare(new BigDecimal(toFormat((BigDecimal)s.get("MEDICARE"))));
			e.setPaySelf(new BigDecimal(toFormat((BigDecimal)s.get("SELF"))));
			e.setPayAssist(new BigDecimal(toFormat((BigDecimal)s.get("ASSIST"))));
			e.setPayCIAssist(new BigDecimal(toFormat((BigDecimal)s.get("CIASSIST"))));
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<PayDTO> findAllpaysByPer(String sql){
		List<PayDTO> list = new ArrayList<PayDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			PayDTO e = new PayDTO();
			e.setMemberId((String)s.get("MEMBERID"));
			e.setMembeType((String)s.get("MEMBERTYPE"));
			e.setName((String)s.get("MEMBERNAME"));
			e.setPaperid((String)s.get("PAPERID"));
			e.setFamilyno((String)s.get("FAMILYNO"));
			e.setPayTotal(new BigDecimal(toFormat((BigDecimal)s.get("PAY_TOTAL"))));
			e.setPayOutmedicare(new BigDecimal(toFormat((BigDecimal)s.get("PAY_OUTMEDICARE"))));
			e.setPayMedicare(new BigDecimal(toFormat((BigDecimal)s.get("PAY_MEDICARE"))));
			e.setPaySelf(new BigDecimal(toFormat((BigDecimal)s.get("PAY_SELF"))));
			e.setPayAssist(new BigDecimal(toFormat((BigDecimal)s.get("PAY_ASSIST"))));
			e.setPayCIAssist(new BigDecimal(toFormat((BigDecimal)s.get("PAY_CIASSIST"))));
			e.setOperTime((Date)s.get("OPER_TIME"));
			String btype = (String) s.get("BIZTYPE");
			if("biz".equals(btype)){
				e.setBiztype("医院住院");
			}else if("ma".equals(btype)){
				e.setBiztype("民政医后");
			}
			e.setHospitalname((String)s.get("HOSPITALNAME"));
			e.setDiagnosename((String) s.get("DIAGNOSE_NAME"));
			e.setBegintime((Date)s.get("BEGIN_TIME"));
			e.setEndtime((Date)s.get("END_TIME"));
			list.add(e);
		}
		return list;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PayDTO> findPayInviews(String sql) {
		List<PayDTO> list = new ArrayList<PayDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			PayDTO e = new PayDTO();
			e.setOrgname((String)s.get("ORGNAME"));
			e.setSubsection((String)s.get("SUBSECTION"));
			e.setPersum((BigDecimal)s.get("PERSUM"));
			e.setPayTotal(new BigDecimal(toFormat((BigDecimal)s.get("TOTAL"))));
			e.setPayOutmedicare(new BigDecimal(toFormat((BigDecimal)s.get("OUTMEDICARE"))));
			e.setPayMedicare(new BigDecimal(toFormat((BigDecimal)s.get("MEDICARE"))));
			e.setPayAssist(new BigDecimal(toFormat((BigDecimal)s.get("ASSIST"))));
			e.setPaySelf(new BigDecimal(toFormat((BigDecimal)s.get("SELF"))));
			e.setPayCIAssist(new BigDecimal(toFormat((BigDecimal)s.get("CIASSIST"))));
			list.add(e);
		}
		return list;
	}
	
	//格式化保留小数后2位
	private String toFormat(BigDecimal num){
		String snum="";
		java.text.DecimalFormat df=new java.text.DecimalFormat("0.00");
		Double dd = num.doubleValue();
		snum = df.format(dd);
		return snum;
	}
	
	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public String getToolsmenu() {
		return pager.getToolsmenu();
	}
}
