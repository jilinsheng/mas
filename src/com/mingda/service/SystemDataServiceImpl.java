package com.mingda.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mingda.dao.BizdeptDAO;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.ExtendsncDAO;
import com.mingda.dao.HospitalPayDAO;
import com.mingda.dao.Icd10DAO;
import com.mingda.dao.ManagerEmpDAO;
import com.mingda.dao.ManagerOrgDAO;
import com.mingda.dto.DeptDTO;
import com.mingda.dto.DictDTO;
import com.mingda.dto.HospitalPayDTO;
import com.mingda.dto.IcdDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.dto.UserDTO;
import com.mingda.model.Bizdept;
import com.mingda.model.BizdeptExample;
import com.mingda.model.HospitalPay;
import com.mingda.model.Icd10;
import com.mingda.model.Icd10Example;
import com.mingda.model.ManagerEmp;
import com.mingda.model.ManagerEmpExample;
import com.mingda.model.ManagerOrg;
import com.mingda.model.ManagerOrgExample;
import com.mingda.model.ManagerOrgExample.Criteria;

public class SystemDataServiceImpl implements SystemDataService {

	private ExtendsDAO extendsDAO;
	private ExtendsncDAO extendsncDAO;
	private BizdeptDAO bizdeptDAO;
	private ManagerOrgDAO managerOrgDAO;
	private ManagerEmpDAO managerEmpDAO;
	private Icd10DAO icd10DAO;
	private HospitalPayDAO hospitalPayDAO;

	public List<IcdDTO> findSickens(String type) {
		List<IcdDTO> list = new ArrayList<IcdDTO>();
		Icd10Example example = new Icd10Example();
		example.createCriteria().andSalvFlagEqualTo(type);
		List<Icd10> rs = icd10DAO.selectByExample(example);
		for (Icd10 s : rs) {
			IcdDTO e = new IcdDTO();
			e.setIcdId(s.getIcdId());
			e.setName(s.getName());
			e.setIcdcode(s.getIcdcode());
			e.setPycode(s.getPycode());
			list.add(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<OutIcdDTO> findSickens(String organizationId, String type,
			String flag, String outtype) {
		List<OutIcdDTO> list = new ArrayList<OutIcdDTO>();
		String sql = "select t.*, icd.name from ICD10 icd, out_icd t "
				+ " where t.sts=" + flag + "  and SALV_FLAG = '" + type
				+ "'  and t.organization_id = '" + organizationId
				+ "'  and t.icd_id = icd.icd_id";
		if ("".equals(outtype)) {
		} else {
			sql = sql + " and t.outtype ='" + outtype + "'";
		}
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		// ORGANIZATION_ID ICD_ID OUTTYPE CALC_TYPE FIX_VALUE SCALE SEQ STS NAME
		for (HashMap s : rs) {
			OutIcdDTO e = new OutIcdDTO();
			e.setCalcType((String) s.get("CALC_TYPE"));
			e.setFixValue((BigDecimal) s.get("FIX_VALUE"));
			e.setName((String) s.get("NAME"));
			e.setIcdId(new Integer(s.get("ICD_ID").toString()));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setOuttype((String) s.get("OUTTYPE"));
			e.setScale((BigDecimal) s.get("SCALE"));
			// e.setSeq((Integer) s.get("SEQ"));
			e.setSts((String) s.get("STS"));
			list.add(e);
		}

		return list;
	}

	public List<OrganizationDTO> findOrgChilds(String organizationId) {
		List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
		ManagerOrgExample example = new ManagerOrgExample();
		Criteria cc = example.createCriteria();
		cc.andParentorgidEqualTo(organizationId).andStatusEqualTo("1");
		example.setOrderByClause("organization_id");
		List<ManagerOrg> rs = managerOrgDAO.selectByExample(example);
		for (ManagerOrg e : rs) {
			OrganizationDTO s = new OrganizationDTO();
			s.setDepth(e.getDepth());
			s.setFullname(e.getFullname());
			s.setOrganizationId(e.getOrganizationId());
			s.setOrgname(e.getOrgname());
			s.setSerialnumber(e.getSerialnumber());
			s.setStatus(e.getStatus());
			s.setOrgpower(e.getOrgpower());
			s.setOrgup(e.getOrgup());
			orgs.add(s);

		}
		return orgs;
	}

	public List<OrganizationDTO> findOrgParentAndChilds(String organizationId) {
		List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
		ManagerOrgExample example = new ManagerOrgExample();
		Criteria c = example.createCriteria();
		c.andOrganizationIdEqualTo(organizationId).andStatusEqualTo("1");
		Criteria cc = example.createCriteria();
		cc.andParentorgidEqualTo(organizationId).andStatusEqualTo("1");
		example.or(cc);
		example.setOrderByClause("organization_id");
		List<ManagerOrg> rs = managerOrgDAO.selectByExample(example);
		for (ManagerOrg e : rs) {
			OrganizationDTO s = new OrganizationDTO();
			s.setDepth(e.getDepth());
			s.setFullname(e.getFullname());
			s.setOrganizationId(e.getOrganizationId());
			s.setOrgname(e.getOrgname());
			s.setSerialnumber(e.getSerialnumber());
			s.setStatus(e.getStatus());
			s.setOrgpower(e.getOrgpower());
			s.setOrgup(e.getOrgup());
			orgs.add(s);
		}
		return orgs;
	}

	public List<OrganizationDTO> findOrgAll(String organizationId) {
		List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
		ManagerOrgExample example = new ManagerOrgExample();
		Criteria c = example.createCriteria();
		c.andOrganizationIdLike(organizationId + "%").andStatusEqualTo("1");
		example.setOrderByClause("organization_id");
		List<ManagerOrg> rs = managerOrgDAO.selectByExample(example);
		for (ManagerOrg e : rs) {
			OrganizationDTO s = new OrganizationDTO();
			s.setDepth(e.getDepth());
			s.setFullname(e.getFullname());
			s.setOrganizationId(e.getOrganizationId());
			s.setOrgname(e.getOrgname());
			s.setSerialnumber(e.getSerialnumber());
			s.setStatus(e.getStatus());
			s.setOrgpower(e.getOrgpower());
			s.setOrgup(e.getOrgup());
			s.setParentorgid(e.getParentorgid());
			orgs.add(s);
		}
		return orgs;
	}

	// 对账单所需机构
	public List<OrganizationDTO> findOrganizationExt(String organizationId) {
		List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
		ManagerOrgExample example = new ManagerOrgExample();
		List<BigDecimal> values = new ArrayList<BigDecimal>();
		values.add(new BigDecimal(1));
		values.add(new BigDecimal(2));
		values.add(new BigDecimal(3));
		example.createCriteria().andDepthIn(values).andOrgpowerEqualTo("1")
				.andOrganizationIdLike(organizationId + "%")
				.andStatusEqualTo("1");
		example.setOrderByClause("organization_id");
		List<ManagerOrg> rs = managerOrgDAO.selectByExample(example);
		for (ManagerOrg e : rs) {
			OrganizationDTO s = new OrganizationDTO();
			s.setDepth(e.getDepth());
			s.setFullname(e.getFullname());
			s.setOrganizationId(e.getOrganizationId());
			s.setOrgname(e.getOrgname());
			s.setSerialnumber(e.getSerialnumber());
			s.setStatus(e.getStatus());
			s.setOrgpower(e.getOrgpower());
			s.setOrgup(e.getOrgup());
			orgs.add(s);
		}
		return orgs;
	}

	public OrganizationDTO findOrganziation(String organizationId) {
		OrganizationDTO orgdto = new OrganizationDTO();
		ManagerOrg org = managerOrgDAO.selectByPrimaryKey(organizationId);
		orgdto.setDepth(org.getDepth());
		orgdto.setFullname(org.getFullname());
		orgdto.setOrganizationId(org.getOrganizationId());
		orgdto.setOrgname(org.getOrgname());
		orgdto.setParentorgid(org.getParentorgid());
		orgdto.setOrgpower(org.getOrgpower());
		orgdto.setOrgup(org.getOrgup());
		orgdto.setSerialnumber(org.getSerialnumber());
		orgdto.setStatus(org.getStatus());
		return orgdto;
	}

	public List<UserDTO> findUsersByOrgan(String organizationId) {
		List<UserDTO> list = new ArrayList<UserDTO>();
		ManagerEmpExample example = new ManagerEmpExample();
		example.createCriteria().andOrganizationIdEqualTo(organizationId);
		List<ManagerEmp> rs = managerEmpDAO.selectByExample(example);
		for (ManagerEmp s : rs) {
			UserDTO e = new UserDTO();
			e.setAccout(s.getAccout());
			e.setEmpid(s.getEmpid());
			e.setEmpname(s.getEmpname());
			e.setPwd(s.getPwd());
			e.setOrganizationId(s.getOrganizationId());
			list.add(e);
		}
		return list;
	}

	public String saveEmp(UserDTO userDTO) throws RuntimeException {
		try {
			ManagerEmp record = new ManagerEmp();
			record.setPwd("123456");
			record.setAccout(userDTO.getAccout());
			record.setEmpname(userDTO.getEmpname());
			record.setOrganizationId(userDTO.getOrganizationId());
			ManagerEmpExample example = new ManagerEmpExample();
			example.createCriteria().andAccoutEqualTo(record.getAccout());
			List<ManagerEmp> rs = managerEmpDAO.selectByExample(example);
			if (null != rs && rs.size() > 0) {
				return "0";
			} else {
				managerEmpDAO.insertSelective(record);
				return "1";
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void saveResetemp(UserDTO userDTO) throws RuntimeException {
		try {
			ManagerEmp record = new ManagerEmp();
			record.setEmpid(userDTO.getEmpid());
			record.setPwd("123456");
			managerEmpDAO.updateByPrimaryKeySelective(record);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeEmp(UserDTO userDTO) throws RuntimeException {
		try {
			String sql = "delete from manager_emp_role t where t.emp_id = '"
					+ userDTO.getEmpid() + "'";
			HashMap param = new HashMap();
			param.put("executsql", sql);
			extendsDAO.updateAll(param);

			ManagerEmp record = new ManagerEmp();
			record.setEmpid(userDTO.getEmpid());
			record.setPwd("123456");
			managerEmpDAO.deleteByPrimaryKey(record.getEmpid());
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<DeptDTO> findDept() {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DeptDTO> findDeptsByOrg(String organizationId) {
		List<DeptDTO> depts = new ArrayList<DeptDTO>();
		String sql = " select o. HOSPITAL_ID,  o. ORGANIZATION_ID,  o. NAME, "
				+ " o. DEPT_TYPE,  o. DEPT_LEVEL,  o. STATUS,  o. ISBASE, "
				+ " o. ISACCEPT,  o. IP from bizdept_org t, bizdept o "
				+ " where t.organization_id = '" + organizationId + "' "
				+ " and t.hospital_id = o.hospital_id "
				+ " and o.dept_type = '1' " + " and t.sts = '1' "
				+ " order by o.organization_id, o.hospital_id  ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			DeptDTO e = new DeptDTO();
			e.setDeptLevel((String) s.get("DEPT_LEVEL"));
			e.setDeptType((String) s.get("DEPT_TYPE"));
			e.setHospitalId(new Integer(s.get("HOSPITAL_ID").toString()));
			e.setName((String) s.get("NAME"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setStatus((String) s.get("STATUS"));
			depts.add(e);
		}
		return depts;
	}

	// 药店
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DeptDTO> findDeptsByOrg1(String oid) {
		List<DeptDTO> depts = new ArrayList<DeptDTO>();
		String sql = " select o. HOSPITAL_ID,  o. ORGANIZATION_ID,  o. NAME, "
				+ " o. DEPT_TYPE,  o. DEPT_LEVEL,  o. STATUS,  o. ISBASE, "
				+ " o. ISACCEPT,  o. IP from bizdept_org t, bizdept o "
				+ " where t.organization_id = '" + oid + "' "
				+ " and t.hospital_id = o.hospital_id "
				+ " and o.dept_type = '0' " + " and t.sts = '1' "
				+ " order by o.organization_id, o.hospital_id  ";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			DeptDTO e = new DeptDTO();
			e.setDeptLevel((String) s.get("DEPT_LEVEL"));
			e.setDeptType((String) s.get("DEPT_TYPE"));
			e.setHospitalId(new Integer(s.get("HOSPITAL_ID").toString()));
			e.setName((String) s.get("NAME"));
			e.setOrganizationId((String) s.get("ORGANIZATION_ID"));
			e.setStatus((String) s.get("STATUS"));
			depts.add(e);
		}
		/*
		 * BizdeptExample example = new BizdeptExample();
		 * example.createCriteria().andOrganizationIdEqualTo(oid)
		 * .andDeptTypeEqualTo("0");
		 * example.setOrderByClause("organization_id , name"); List<Bizdept> rs
		 * = bizdeptDAO.selectByExample(example); for (Bizdept s : rs) { DeptDTO
		 * e = new DeptDTO(); e.setDeptLevel(s.getDeptLevel());
		 * e.setDeptType(s.getDeptType()); e.setHospitalId(s.getHospitalId());
		 * e.setName(s.getName()); e.setOrganizationId(s.getOrganizationId());
		 * e.setStatus(s.getStatus()); depts.add(e); }
		 */
		return depts;
	}

	// 药店和医院
	public List<DeptDTO> findDeptsByOrg2(String oid) {
		List<DeptDTO> depts = new ArrayList<DeptDTO>();
		BizdeptExample example = new BizdeptExample();
		example.createCriteria().andOrganizationIdEqualTo(oid);
		example.setOrderByClause("organization_id , name");
		List<Bizdept> rs = bizdeptDAO.selectByExample(example);
		for (Bizdept s : rs) {
			DeptDTO e = new DeptDTO();
			e.setDeptLevel(s.getDeptLevel());
			e.setDeptType(s.getDeptType());
			e.setHospitalId(s.getHospitalId());
			e.setName(s.getName());
			e.setOrganizationId(s.getOrganizationId());
			e.setStatus(s.getStatus());
			depts.add(e);
		}
		return depts;
	}

	public List<DeptDTO> findDeptsByOrg3(String hname) {
		List<DeptDTO> depts = new ArrayList<DeptDTO>();
		BizdeptExample example = new BizdeptExample();
		example.createCriteria().andNameLike("%" + hname + "%");
		example.setOrderByClause("organization_id , name");
		List<Bizdept> rs = bizdeptDAO.selectByExample(example);
		for (Bizdept s : rs) {
			DeptDTO e = new DeptDTO();
			e.setDeptLevel(s.getDeptLevel());
			e.setDeptType(s.getDeptType());
			e.setHospitalId(s.getHospitalId());
			e.setName(s.getName());
			e.setOrganizationId(s.getOrganizationId());
			e.setStatus(s.getStatus());
			depts.add(e);
		}
		return depts;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserDTO> findMus(String oid) {
		List<UserDTO> mus = new ArrayList<UserDTO>();
		String sql = "select emp.empname ,emp.accout ,emp.pwd ,org.orgname "
				+ " from manager_emp emp, manager_org org "
				+ " where org.organization_id = emp.organization_id "
				+ " and emp.organization_id like '" + oid + "%' "
				+ " and length(emp.organization_id) < 10 "
				+ " order by emp.organization_id";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			UserDTO e = new UserDTO();
			e.setAccout((String) s.get("ACCOUT"));
			e.setEmpname((String) s.get("EMPNAME"));
			e.setPwd((String) s.get("PWD"));
			e.setOrgname((String) s.get("ORGNAME"));
			mus.add(e);
		}
		return mus;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserDTO> findHus(String oid) {
		List<UserDTO> hus = new ArrayList<UserDTO>();
		String sql = "select t.account as ACCOUT, t.password as pwd, t.name as empname, d.name as orgname "
				+ " from bizdept_user t, bizdept d "
				+ " where d.hospital_id = t.hospital_id "
				+ " and t.hospital_id = '" + oid + "'";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			UserDTO e = new UserDTO();
			e.setAccout((String) s.get("ACCOUT"));
			e.setEmpname((String) s.get("EMPNAME"));
			e.setPwd((String) s.get("PWD"));
			e.setOrgname((String) s.get("ORGNAME"));
			hus.add(e);
		}
		return hus;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<HospitalPayDTO> findHospitalpays(String oid, String b) {
		List<HospitalPayDTO> hpays = new ArrayList<HospitalPayDTO>();
		String a = "";
		if ("".equals(oid)) {
		} else {
			a = " and t.hospital_id = '" + oid + "'";
		}
		String sql = "select d.name, t.hospital_id, t.fee_flag, t.fee_amount, t.fee_time"
				+ " from hospital_pay t, bizdept d "
				+ " where d.hospital_id = t.hospital_id "
				+ a
				+ "  order by d.organization_id, d.name , t.fee_time";

		sql = "select d.name, t.hospital_id, t.fee_flag, t.fee_amount, t.fee_time "
				+ " from hospital_pay t, bizdept d "
				+ " where d.hospital_id = t.hospital_id "
				+ " "
				+ a
				+ " union  select d.name, t.hospital_id, t.fee_flag, t.fee_amount, t.fee_time "
				+ " from hospital_pay t, bizdept d "
				+ " where d.hospital_id = t.hospital_id "
				+ " and d.organization_id  in (select  d.organization_id "
				+ " from hospital_pay t, bizdept d "
				+ " where d.hospital_id = t.hospital_id "
				+ "  and t.hospital_id = '" + oid + "')";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		// NAME HOSPITAL_ID FEE_FLAG FEE_AMOUNT FEE_TIME
		for (HashMap s : rs) {
			HospitalPayDTO e = new HospitalPayDTO();
			BigDecimal FEE_AMOUNT = (BigDecimal) s.get("FEE_AMOUNT");
			if (null == FEE_AMOUNT) {
				FEE_AMOUNT = new BigDecimal(0);
			}
			e.setFeeAmount(FEE_AMOUNT);
			e.setFeeFlag((String) s.get("FEE_FLAG"));
			e.setFeeTime((Date) s.get("FEE_TIME"));
			e.setHname((String) s.get("NAME"));
			Integer HOSPITAL_ID = new Integer(s.get("HOSPITAL_ID").toString());
			e.setHospitalId(HOSPITAL_ID);
			hpays.add(e);
		}
		return hpays;
	}

	public HospitalPayDTO findHospitalpay(HospitalPayDTO hospitalPayDTO) {
		HospitalPay hospitalPay = hospitalPayDAO
				.selectByPrimaryKey(hospitalPayDTO.getHospitalId());
		hospitalPayDTO.setFeeAmount(hospitalPay.getFeeAmount());
		hospitalPayDTO.setFeeFlag(hospitalPay.getFeeFlag());
		hospitalPayDTO.setFeeTime(hospitalPay.getFeeTime());
		hospitalPayDTO.setHospitalId(hospitalPay.getHospitalId());
		return hospitalPayDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DeptDTO> findDeptsByOrgExt(String string) {
		List<DeptDTO> depts = new ArrayList<DeptDTO>();
		String sql = " select  b.hospital_id , b.name from bizdept b , hospital_pay p "
				+ "where b.hospital_id=p.hospital_id(+) and p.fee_flag  is null order by b.organization_id, b.name";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			DeptDTO e = new DeptDTO();
			e.setHospitalId(new Integer(s.get("HOSPITAL_ID").toString()));
			e.setName((String) s.get("NAME"));
			depts.add(e);
		}
		return depts;
	}

	public HospitalPayDTO saveHospitalpay(HospitalPayDTO hospitalPayDTO) {
		HospitalPay record = new HospitalPay();
		record.setFeeAmount(hospitalPayDTO.getFeeAmount());
		record.setFeeFlag(hospitalPayDTO.getFeeFlag());
		if (hospitalPayDTO.getFeeTime() == null) {
			record.setFeeTime(new Date());
		} else {
			record.setFeeTime(hospitalPayDTO.getFeeTime());
		}
		record.setHospitalId(hospitalPayDTO.getHospitalId());

		HospitalPay e = hospitalPayDAO.selectByPrimaryKey(record
				.getHospitalId());
		if (null == e) {
			hospitalPayDAO.insertSelective(record);
		} else {
			hospitalPayDAO.updateByPrimaryKeySelective(record);
		}
		return hospitalPayDTO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DictDTO> findNations() {
		List<DictDTO> dicts = new ArrayList<DictDTO>();
		String sql = "select * from sys_t_dictionary dict where dict.dictsort_id='120' order by sequence";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsncDAO.queryAll(param);
		for (HashMap s : rs) {
			DictDTO e = new DictDTO();
			e.setItem((String) s.get("ITEM"));
			dicts.add(e);
		}
		return dicts;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap<String, String> findvalpaperid(String paperId) {
		HashMap<String, String> h = new HashMap<String, String>();
		HashMap param = new HashMap();
		String sql = "select fn_checkidcard('" + paperId + "') as a from dual";
		param.put("executsql", sql);
		HashMap rs = extendsDAO.queryAll(param).get(0);
		BigDecimal flag = (BigDecimal) rs.get("A");
		h.put("flag", flag.toString());
		if ("1".equals(flag.toString())) {
			sql = "select fn_getbirth('" + paperId + "') as a from dual";
			param.put("executsql", sql);
			rs = extendsDAO.queryAll(param).get(0);
			h.put("birth", (String) rs.get("A"));
		} else {
			h.put("birth", "");
		}
		return h;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findChachong(String memberId, String paperId) {
		String sql = "select * from MEMBER_BASEINFOVIEW02 mem where mem.paperid='"
				+ paperId + "' and mem.personstate = '正常'";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		String flag = "0";
		if (null != rs && rs.size() > 0) {
			if ("".equals(memberId)) {
				flag = "0";
			} else {
				if (rs.size() < 3) {
					flag = "1";
				} else {
					flag = "0";
				}
			}
		} else {
			flag = "1";
		}
		return flag;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String findPageflag(String organizationId) {
		String sql = "select * from diagnose_type t where  instr('"
				+ organizationId + "' , t.organization_id)>0";
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		String flag = "0";
		if (null != rs && rs.size() > 0) {
			flag = "1";
		}
		return flag;
	}

	public ExtendsDAO getExtendsDAO() {
		return extendsDAO;
	}

	public void setExtendsDAO(ExtendsDAO extendsDAO) {
		this.extendsDAO = extendsDAO;
	}

	public BizdeptDAO getBizdeptDAO() {
		return bizdeptDAO;
	}

	public void setBizdeptDAO(BizdeptDAO bizdeptDAO) {
		this.bizdeptDAO = bizdeptDAO;
	}

	public void setManagerOrgDAO(ManagerOrgDAO managerOrgDAO) {
		this.managerOrgDAO = managerOrgDAO;
	}

	public ManagerOrgDAO getManagerOrgDAO() {
		return managerOrgDAO;
	}

	public void setIcd10DAO(Icd10DAO icd10DAO) {
		this.icd10DAO = icd10DAO;
	}

	public Icd10DAO getIcd10DAO() {
		return icd10DAO;
	}

	public void setManagerEmpDAO(ManagerEmpDAO managerEmpDAO) {
		this.managerEmpDAO = managerEmpDAO;
	}

	public ManagerEmpDAO getManagerEmpDAO() {
		return managerEmpDAO;
	}

	public HospitalPayDAO getHospitalPayDAO() {
		return hospitalPayDAO;
	}

	public void setHospitalPayDAO(HospitalPayDAO hospitalPayDAO) {
		this.hospitalPayDAO = hospitalPayDAO;
	}

	public void setExtendsncDAO(ExtendsncDAO extendsncDAO) {
		this.extendsncDAO = extendsncDAO;
	}

	public ExtendsncDAO getExtendsncDAO() {
		return extendsncDAO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean findEmprole(String empid, String url) {
		String sql = "select rolename, role_id, url, flag, empid, empname, accout, pwd, organization_id from v_emprole er where er.EMPID='"
				+ empid + "' and er.URL='" + url + "'";
		// ROLENAME ROLE_ID URL FLAG EMPID EMPNAME ACCOUT PWD ORGANIZATION_ID
		System.out.println(sql);
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		if (null != rs && rs.size() > 0) {
			if (rs.size() == 1) {
				String f = (String) rs.get(0).get("FLAG");
				if ("1".equals(f)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return true;
		}
	}
}
