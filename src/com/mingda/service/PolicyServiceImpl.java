package com.mingda.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.mingda.common.Pager;
import com.mingda.dao.ExtendsDAO;
import com.mingda.dao.ManagerOrgDAO;
import com.mingda.dao.OrgPolicyDAO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.PolicyDTO;
import com.mingda.model.ManagerOrg;
import com.mingda.model.ManagerOrgExample;
import com.mingda.model.ManagerOrgExample.Criteria;
import com.mingda.model.OrgPolicy;
import com.mingda.model.OrgPolicyExample;

public class PolicyServiceImpl implements PolicyService {
	static Logger log = Logger.getLogger(PolicyServiceImpl.class);
	private ExtendsDAO extendsDAO;
	private OrgPolicyDAO orgPolicyDAO ;
	private ManagerOrgDAO managerOrgDAO;
	private Pager pager;
	
	@Override
	public PolicyDTO savePolicyFiles(PolicyDTO policyDTO){
		OrgPolicy record = new OrgPolicy();
		if (null == policyDTO.getPolicyId()) {
			record.setOrganizationid(policyDTO.getOrganizationid());
			record.setPolicyTitle(policyDTO.getPolicyTitle());
			record.setPolicyMsg(policyDTO.getPolicyMsg());
			record.setCreatTime(policyDTO.getCreatTime());
			record.setOperTime(policyDTO.getOperTime());
			record.setStatus(policyDTO.getStatus());
			record.setFileNum(policyDTO.getFileNum());
			Integer pid = orgPolicyDAO.insertSelective(record);
			policyDTO.setPolicyId(pid.toString());
		}else{
			record.setOperTime(policyDTO.getOperTime());
			record.setStatus(policyDTO.getStatus());
			orgPolicyDAO.updateByPrimaryKeySelective(record);
		}
		return policyDTO;
	}
	
	@Override
	public PolicyDTO findPolicy(PolicyDTO policyDTO){
		PolicyDTO p = new PolicyDTO();
		OrgPolicy  op = orgPolicyDAO.selectByPrimaryKey(Integer.parseInt(policyDTO.getPolicyId()));
		p.setPolicyId(op.getpId().toString());
		p.setPolicyTitle(op.getPolicyTitle());
		p.setPolicyMsg(op.getPolicyMsg());
		p.setFileNum(op.getFileNum());
		p.setOrganizationid(op.getOrganizationid());
		p.setStatus(op.getStatus());
		p.setCreatTime(op.getCreatTime());
		p.setOperTime(op.getOperTime());
		return p;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<PolicyDTO> findPolicyList(String sql){
		List<PolicyDTO> policylist = new ArrayList<PolicyDTO>();
		HashMap param = new HashMap();
		param.put("executsql", sql);
		List<HashMap> rs = extendsDAO.queryAll(param);
		for (HashMap s : rs) {
			PolicyDTO e = new PolicyDTO();
			e.setOrganizationid((String) s.get("ORGANIZATION_ID"));
			e.setSerialnumber((String) s.get("SERIALNUMBER"));
			e.setFullname((String) s.get("FULLNAME"));
			e.setOrgname((String) s.get("ORGNAME"));
			if(s.get("UPLOADNUM")==null){
				e.setUploadnum(BigDecimal.ZERO);
			}else{
				e.setUploadnum((BigDecimal) s.get("UPLOADNUM"));
			}
			policylist.add(e);
		}
		return policylist;
	}
	
	public List<OrganizationDTO> findOrgALL(){
		List<OrganizationDTO> orgs = new ArrayList<OrganizationDTO>();
		ManagerOrgExample example = new ManagerOrgExample();
		Criteria cc = example.createCriteria();
		cc.andDepthEqualTo(new BigDecimal("2")).andStatusEqualTo("1");
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
	
	public List<PolicyDTO> findPolicyByOrgid(PolicyDTO policyDTO) {
		List<PolicyDTO> policylist = new ArrayList<PolicyDTO>();
		OrgPolicyExample example = new OrgPolicyExample();
		example.createCriteria()
				.andOrganizationidEqualTo(policyDTO.getOrganizationid())
				.andStatusEqualTo("1");
		List<OrgPolicy> rs = orgPolicyDAO.selectByExample(example);
		for(OrgPolicy s : rs){
			PolicyDTO p = new PolicyDTO();
			p.setOrganizationid(s.getOrganizationid());
			p.setPolicyId(s.getpId().toString());
			p.setPolicyTitle(s.getPolicyTitle());
			p.setPolicyMsg(s.getPolicyMsg());
			p.setFileNum(s.getFileNum());
			p.setCreatTime(s.getCreatTime());
			p.setOperTime(s.getOperTime());
			policylist.add(p);
		}
		return policylist;
	}
	
	public OrganizationDTO getOrgName(String orgid){
		OrganizationDTO o = new OrganizationDTO();
		ManagerOrgExample example = new ManagerOrgExample();
		example.createCriteria().andSerialnumberEqualTo(orgid);
		ManagerOrg r = managerOrgDAO.selectByExample(example).get(0);
		o.setFullname(r.getFullname());
		o.setOrganizationId(r.getOrganizationId());
		o.setOrgname(r.getOrgname());
		o.setSerialnumber(r.getSerialnumber());
		return o;
	}
	
	@SuppressWarnings("rawtypes")
	public List<PolicyDTO> findpolicybyorgid(String sql, int currentpage, String url) {
		List<PolicyDTO> list = new ArrayList<PolicyDTO>();
		HashMap<String, Comparable> param = new HashMap<String, Comparable>();
		param.put("executsql", sql);
		pager = new Pager(currentpage, extendsDAO.queryCnt(param), url, 14);
		param.put("start", pager.getStart());
		param.put("end", pager.getEnd());
		List<HashMap> rs = extendsDAO.queryRow(param);
		for (HashMap s : rs) {
			PolicyDTO e = new PolicyDTO();
			e.setPolicyTitle((String) s.get("POLICY_TITLE"));
			e.setPolicyMsg((String) s.get("POLICY_MSG"));
			e.setFileNum((String) s.get("FILE_NUM"));
			e.setOperTime((Date) s.get("OPER_TIME"));
			e.setCreatTime((Date) s.get("CREAT_TIME"));
			e.setStatus((String) s.get("STATUS"));
			BigDecimal id = (BigDecimal) s.get("P_ID");
			e.setPolicyId(id.toString());
			list.add(e);
		}
		return list;
	}
	
	public int deletepolicybyid(Integer pid){
		OrgPolicy record = new OrgPolicy();
		record.setpId(pid);
		record.setStatus("0");
		record.setOperTime(new Date());
		int i = orgPolicyDAO.updateByPrimaryKeySelective(record);
		return i;
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
	public OrgPolicyDAO getOrgPolicyDAO() {
		return orgPolicyDAO;
	}
	public void setOrgPolicyDAO(OrgPolicyDAO orgPolicyDAO) {
		this.orgPolicyDAO = orgPolicyDAO;
	}

	public ManagerOrgDAO getManagerOrgDAO() {
		return managerOrgDAO;
	}

	public void setManagerOrgDAO(ManagerOrgDAO managerOrgDAO) {
		this.managerOrgDAO = managerOrgDAO;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

}
