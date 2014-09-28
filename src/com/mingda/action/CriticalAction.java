package com.mingda.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mingda.dto.BizCheckDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.CriticalService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CriticalAction extends ActionSupport {
	static final Logger log = Logger.getLogger(CriticalAction.class);
	private List<OrganizationDTO> orgs;
	private String oid;
	private String term;
	private String toolsmenu;
	private String value;
	private String cur_page;
	private String operational;
	private String method;
	private List<BizCheckDTO> bizchecks;
	private SystemDataService systemDataService;
	private CriticalService criticalService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings({ "rawtypes" })
	public String querycriticalinhospitalinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querycriticalinhospitals(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		String var = value;
		if (null == cur_page || "".equals(cur_page)) {
			cur_page = "1";
			String jwhere = "";
			if (!"".equals(var)) {
				if ("=".equals(operational)) {
					var = " = '" + var + "'";
				} else if ("like".equals(operational)) {
					var = "like  '%" + var + "%'";
				} else {
					var = "";
				}
				if ("SSN".equals(term)) {
					jwhere = jwhere + " and biz.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and biz.family_no  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  biz.name  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  biz.id_card " + var;
				} else {
				}
			}
			if ("1".equals(method)) {
				// 住院
				jwhere = jwhere + "and biz.end_time is null";
			} else if ("2".equals(method)) {
				// 出院
				jwhere = jwhere + "and biz.end_time is not null";
			} else {
				// 全部
			}
			sql = "select biz.biz_id, biz.ssn, d.name as hname,  biz.biz_type,   biz.family_no,  biz.name, "
					+ " biz.dept_name,biz.area_name,biz.begin_time, biz.diagnose_name, "
					+ " biz.id_card  ,biz.assist_flag ,biz.audit_flag ,biz.end_time , f.scaler from jz_biz biz, bizdept d, diagnose_type f"
					+ " where d.hospital_id(+) = biz.hospital_id"
					+ " and biz.diagnose_type_id = f.diagnose_type_id(+)"
					+ " and biz.biz_type = 2" 
					+ " and f.scaler > 0"
					+ " and biz.reg_status=1 "
					+ " and biz.family_no like '"
					+ oid
					+ "%'"
					+ jwhere
					+ " order by biz.begin_time desc , biz.family_no";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}
		bizchecks = criticalService.findCritical(sql,
				new BigDecimal(cur_page).intValue(),
				"page/critical/querycriticaldrugs.action");
		setToolsmenu(criticalService.getToolsmenu());
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public String querycriticalinjectioninit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querycriticalinjections(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		String var = value;
		if (null == cur_page || "".equals(cur_page)) {
			cur_page = "1";
			String jwhere = "";
			if (!"".equals(var)) {
				if ("=".equals(operational)) {
					var = " = '" + var + "'";
				} else if ("like".equals(operational)) {
					var = "like  '%" + var + "%'";
				} else {
					var = "";
				}
				if ("SSN".equals(term)) {
					jwhere = jwhere + " and biz.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and biz.family_no  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  biz.name  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  biz.id_card " + var;
				} else {
				}
			}
			sql = "select biz.biz_id, biz.ssn, d.name as hname,  biz.biz_type,   biz.family_no,  biz.name, "
					+ " biz.dept_name,biz.area_name,biz.begin_time, biz.diagnose_name, "
					+ " biz.id_card  ,biz.assist_flag ,biz.audit_flag ,biz.end_time , f.scaler from jz_biz biz, bizdept d, diagnose_type f"
					+ " where d.hospital_id(+) = biz.hospital_id"
					+ " and biz.diagnose_type_id = f.diagnose_type_id(+)"
					+ " and biz.biz_type = 1" 
					+ " and f.scaler > 0"
					+ " and biz.reg_status=1 "
					+ " and biz.family_no like '"
					+ oid
					+ "%'"
					+ jwhere
					+ " order by biz.begin_time desc , biz.family_no";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}
		bizchecks = criticalService.findCritical(sql,
				new BigDecimal(cur_page).intValue(),
				"page/critical/querycriticaldrugs.action");
		setToolsmenu(criticalService.getToolsmenu());
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public String querycriticaldrugsinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querycriticaldrugs(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		String var = value;
		if (null == cur_page || "".equals(cur_page)) {
			cur_page = "1";
			String jwhere = "";
			if (!"".equals(var)) {
				if ("=".equals(operational)) {
					var = " = '" + var + "'";
				} else if ("like".equals(operational)) {
					var = "like  '%" + var + "%'";
				} else {
					var = "";
				}
				if ("SSN".equals(term)) {
					jwhere = jwhere + " and biz.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and biz.family_no  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  biz.name  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  biz.id_card " + var;
				} else {
				}
			}
			sql = "select biz.biz_id, biz.ssn, d.name as hname,  biz.biz_type,   biz.family_no,  biz.name, "
					+ " biz.dept_name,biz.area_name,biz.begin_time, biz.diagnose_name, "
					+ " biz.id_card  ,biz.assist_flag ,biz.audit_flag ,biz.end_time , f.scaler from jz_biz biz, bizdept d, diagnose_type f"
					+ " where d.hospital_id(+) = biz.hospital_id"
					+ " and biz.diagnose_type_id = f.diagnose_type_id(+)"
					+ " and biz.biz_type = 3" 
					+ " and f.scaler > 0"
					+ " and biz.reg_status=1 "
					+ " and biz.family_no like '"
					+ oid
					+ "%'"
					+ jwhere
					+ " order by biz.begin_time desc , biz.family_no";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}
		bizchecks = criticalService.findCritical(sql,
				new BigDecimal(cur_page).intValue(),
				"page/critical/querycriticaldrugs.action");
		setToolsmenu(criticalService.getToolsmenu());
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}

	public List<OrganizationDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrganizationDTO> orgs) {
		this.orgs = orgs;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
	}

	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational;
	}

	public CriticalService getCriticalService() {
		return criticalService;
	}

	public void setCriticalService(CriticalService criticalService) {
		this.criticalService = criticalService;
	}

	public List<BizCheckDTO> getBizchecks() {
		return bizchecks;
	}

	public void setBizchecks(List<BizCheckDTO> bizchecks) {
		this.bizchecks = bizchecks;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
