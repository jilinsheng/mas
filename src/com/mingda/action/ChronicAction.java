package com.mingda.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mingda.dto.BillDTO;
import com.mingda.dto.BillInfoDTO;
import com.mingda.dto.BizDTO;
import com.mingda.dto.ChronicCheckDTO;
import com.mingda.dto.ChronicIncomeDTO;
import com.mingda.dto.DeptDTO;
import com.mingda.dto.IcdDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.ChronicService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ChronicAction extends ActionSupport {
	private String oid;
	private String term;
	private String toolsmenu;
	private String value;
	private String totalstr;
	private String result;
	private String cur_page;
	private String operational;
	private String checked1;
	private String checked2;
	private List<OrganizationDTO> orgs;
	private List<ChronicCheckDTO> ccds;
	private List<ChronicIncomeDTO> cis;
	private List<OutIcdDTO> outicds;
	private List<BillInfoDTO> bis;
	private List<IcdDTO> icds;
	private ChronicCheckDTO chronicCheckDTO;
	private ChronicService chronicService;
	private SystemDataService systemDataService;
	private String type;
	private String uuid;
	private String opertime1;
	private String opertime2;
	private String salstate;
	private List<DeptDTO> depts;
	private List<BizDTO> bizs;
	private String assismoney1;
	private String assismoeny2;
	private String hid;
	private List<BillDTO> bills;
	private BillDTO billDTO;
	private String opttime1from;
	private String opttime1to;
	private String opttime2from;
	private String opttime2to;
	@SuppressWarnings("rawtypes")
	private HashMap map;
	private String salstatus;
	private String method;
	private static final long serialVersionUID = 512473115545552208L;

	private String orgid;
	private String biztype;

	// 慢性病审批
	public String approvechronicmemberinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		// boolean flag = chronicService.findIsChronic(chronicCheckDTO);
		String organizationId = user.getOrganizationId();
		chronicCheckDTO.setOrganziationId(organizationId);
		if (8 == organizationId.length()) {
			/*
			 * if (flag) { chronicCheckDTO = chronicService
			 * .findChronicCheckDTO(chronicCheckDTO); return SUCCESS; } else {
			 * result = "此居民正在审批中！"; return ERROR; }
			 */
			chronicCheckDTO = chronicService
					.findChronicCheckDTO(chronicCheckDTO);
			return SUCCESS;
		} else if (6 == organizationId.length()) {
			/*
			 * chronicCheckDTO = chronicService
			 * .findChronicCheckDTO(chronicCheckDTO);
			 */
			chronicCheckDTO = chronicService
					.findChronicCheckDTO(chronicCheckDTO);
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	public String approvechronicmember() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		String operatorId = user.getEmpid();
		chronicCheckDTO.setOrganziationId(organizationId);
		chronicCheckDTO.setOperator(operatorId);
		chronicCheckDTO.setChecktime(new Date());
		chronicCheckDTO.setOpttime(new Date());
		chronicCheckDTO = chronicService.saveChronicMember(chronicCheckDTO);
		return SUCCESS;
	}

	// 慢性病审批
	public String querychronicmemberinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {

			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicmember() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String var = value;
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
					jwhere = jwhere + " and  mem.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  mem.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  mem.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}
			String jwhere2 = "";
			if (8 == organizationId.length()) {
				jwhere2 = " and (ss.salstate <> '1' or ss.salstate is null) ";
				sql = "select mem.MEMBERNAME,  mem.PAPERID, mem.SSN,  mem.FAMILYID, "
						+ " mem.FAMILYNO,mem.member_id as memberid ,  mem.DS,  mem.salvation_id, mem.ASSIST_TYPE, mem.assist_typex, "
						+ " ss.chronicstatus_id,  ss.curcheck_id,  ss.sts as sssts,   ss.sicken, ss.salstate, ss.tmpflag "
						+ " from chronic_status ss, MEMBER_BASEINFOVIEW02 mem "
						+ " where mem.personstate='正常' and (mem.assist_type <> '00000' or mem.assist_typex <> '000000') "
						+ " and mem.MEMBER_ID = ss.member_id(+) and  mem.DS=ss.member_type(+) "
						+ " " + jwhere2;
				sql = sql + jwhere + "  order by mem.FAMILYNO";

			} else if (6 == organizationId.length()) {
				jwhere2 = "  and ck.sts = 1   and (ss.salstate <> '1' or ss.salstate is null)  and ck.checked1 is not null  and ck.checked2 is null ";

				sql = "select mem.MEMBERNAME,  mem.PAPERID, mem.SSN,  mem.FAMILYID, "
						+ " mem.FAMILYNO,mem.member_id as memberid ,  mem.DS,  mem.salvation_id, mem.ASSIST_TYPE, mem.assist_typex, ck.*, "
						+ " ss.chronicstatus_id,  ss.curcheck_id,  ss.sts as sssts,   ss.sicken, ss.salstate, ss.tmpflag "
						+ " from chronic_status ss, MEMBER_BASEINFOVIEW02 mem, chronic_check ck "
						+ " where mem.personstate='正常' and (mem.assist_type <> '00000' or mem.assist_typex <> '000000') "
						+ "and  mem.MEMBER_ID = ss.member_id(+) and  mem.DS=ss.member_type(+) and ck.member_type(+)=mem.DS"
						+ " and ck.member_id(+) = mem.MEMBER_ID  " + jwhere2;
				sql = sql + jwhere + "  order by mem.FAMILYNO";
			}

			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicChecks(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicmember.action"));
		setToolsmenu(chronicService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	// 慢性病审批
	// 慢性病复查审批
	public String querychronicmembermodifyinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length() /* || 8 == organizationId.length() */) {

			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicmembermodify() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String var = value;
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
					jwhere = jwhere + " and  mem.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  mem.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  mem.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}
			String jwhere2 = "";
			if (!"".equals(salstatus)) {
				jwhere2 = jwhere2 + " and ss.salstate= '" + salstatus + "' ";
			} else {
				jwhere2 = jwhere2 + " and ss.salstate in ('1', '0') ";
			}
			if (8 == organizationId.length()) {
				// jwhere2 = " and ss.salstate in ('1','0') ";
				sql = "select mem.MEMBERNAME,  mem.PAPERID, mem.SSN,  mem.FAMILYID, "
						+ " mem.FAMILYNO,mem.member_id as memberid ,  mem.DS,  mem.salvation_id, mem.ASSIST_TYPE, mem.ASSIST_TYPEX, "
						+ " ss.chronicstatus_id,  ss.curcheck_id,  ss.sts as sssts,   ss.sicken, ss.salstate, ss.tmpflag, "
						+ " decode(ss.salstate, '1', '救助对象', '0', '非救助对象', null) as salstat_text, "
						+ " decode(mem.ds, '1', '城市', '2', '农村') as ds_text, "
						+ " decode(mem.a1, 1, '在保户', 2, '在保户', null) || "
						+ " decode(mem.a4, 1, '-五保户', null) || "
						+ "	decode(mem.a5, 1, '-优抚对象', null) || "
						+ "	decode(mem.a3, 1, '-三无人员', null) || "
						+ " decode(mem.a6, 1, '-孤儿', null) as meminfo "
						+ " from chronic_status ss, MEMBER_BASEINFOVIEW02 mem "
						+ " where mem.MEMBER_ID = ss.member_id(+) and  mem.DS=ss.member_type(+) and (mem.assist_type <> '00000' or mem.assist_typex <> '000000') "
						+ " and not exists (select 1 from chronic_check ck where "
						+ " ck.sts = 1 and ck.member_type(+) = mem.DS and ck.member_id(+) = mem.MEMBER_ID)"
						+ jwhere2;
				sql = sql + jwhere + "  order by mem.FAMILYNO";
			} else if (6 == organizationId.length()) {
				// jwhere2 = "  and ss.salstate in ('1','0')  ";
				sql = "select mem.MEMBERNAME,  mem.PAPERID, mem.SSN,  mem.FAMILYID, "
						+ " mem.FAMILYNO,mem.member_id as memberid ,  mem.DS,  mem.salvation_id, mem.ASSIST_TYPE, mem.ASSIST_TYPEX, ck.*, "
						+ " ss.chronicstatus_id,  ss.curcheck_id,  ss.sts as sssts,   ss.sicken, ss.salstate, ss.tmpflag, "
						+ " decode(ss.salstate, '1', '救助对象', '0', '非救助对象', null) as salstat_text, "
						+ " decode(mem.ds, '1', '城市', '2', '农村') as ds_text, "
						+ " decode(mem.a1, 1, '在保户', 2, '在保户', null) || "
						+ " decode(mem.a4, 1, '-五保户', null) || "
						+ "	decode(mem.a5, 1, '-优抚对象', null) || "
						+ "	decode(mem.a3, 1, '-三无人员', null) || "
						+ " decode(mem.a6, 1, '-孤儿', null) as meminfo "
						+ " from chronic_status ss, MEMBER_BASEINFOVIEW02 mem, chronic_check ck "
						+ " where   mem.MEMBER_ID = ss.member_id(+) and  mem.DS=ss.member_type(+)  and (mem.assist_type <> '00000' or mem.assist_typex <> '000000') "
						+ "  and ss.curcheck_id = ck.chroniccheck_id(+) "
						+ jwhere2;
				sql = sql + jwhere + "  order by mem.FAMILYNO";
			}

			session.put("sql", sql);
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("SALSTAT_TEXT,val", "救助状态");
			title.put("DS_TEXT,val", "来源");
			title.put("MEMINFO,val", "身份类别");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicChecks(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicmembermodify.action"));
		setToolsmenu(chronicService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	// 慢性病修改审批
	// 慢性病业务查询
	public String querychronicmemberdoneinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {

			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicmemberdone() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String var = value;
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
					jwhere = jwhere + " and  mem.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and  mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  mem.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  mem.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}
			String jwhere2 = "";
			if ("".equals(checked1)) {
			} else {
				jwhere2 = jwhere2 + " and ck.checked1 ='" + checked1 + "'";
			}
			if ("".equals(checked2)) {
			} else {
				jwhere2 = jwhere2 + " and ck.checked2 ='" + checked2 + "'";
			}
			if ("".equals(opttime1from) && "".equals(opttime1to)) {
			} else if (!"".equals(opttime1from) && !"".equals(opttime1to)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date optFromDate = format.parse(opttime1from);
					Date optToDate = format.parse(opttime1to);
					if (optFromDate.after(optToDate)) {
						jwhere2 = jwhere2
								+ " and ck.opttime1 BETWEEN TO_DATE('"
								+ opttime1to
								+ " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime1from
								+ " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					} else if (optFromDate.before(optToDate)
							|| optFromDate.equals(optToDate)) {
						jwhere2 = jwhere2
								+ " and ck.opttime1 BETWEEN TO_DATE('"
								+ opttime1from
								+ " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime1to
								+ " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (!"".equals(opttime1from) && "".equals(opttime1to)) {
				jwhere2 = jwhere2 + " and ck.opttime1 < TO_DATE('"
						+ opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			} else if ("".equals(opttime1from) && !"".equals(opttime1to)) {
				jwhere2 = jwhere2 + " and ck.opttime1 > TO_DATE('" + opttime1to
						+ " 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if ("".equals(opttime2from) && "".equals(opttime2to)) {
			} else if (!"".equals(opttime2from) && !"".equals(opttime2to)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date optFromDate = format.parse(opttime2from);
					Date optToDate = format.parse(opttime2to);
					if (optFromDate.after(optToDate)) {
						jwhere2 = jwhere2
								+ " and ck.opttime2 BETWEEN TO_DATE('"
								+ opttime2to
								+ " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime2from
								+ " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					} else if (optFromDate.before(optToDate)
							|| optFromDate.equals(optToDate)) {
						jwhere2 = jwhere2
								+ " and ck.opttime2 BETWEEN TO_DATE('"
								+ opttime2from
								+ " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime2to
								+ " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (!"".equals(opttime2from) && "".equals(opttime2to)) {
				jwhere2 = jwhere2 + " and ck.opttime2 < TO_DATE('"
						+ opttime2from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			} else if ("".equals(opttime2from) && !"".equals(opttime2to)) {
				jwhere2 = jwhere2 + " and ck.opttime2 > TO_DATE('" + opttime2to
						+ " 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			sql = "select mem.MEMBERNAME,  mem.PAPERID, mem.SSN,  mem.FAMILYID, "
					+ " mem.FAMILYNO,mem.member_id as memberid ,  mem.DS,  mem.salvation_id, mem.ASSIST_TYPE, mem.ASSIST_TYPEX, ck.*, "
					+ " decode(ck.checked1,'1','同意','0','不同意',null,'未审批')as detail1txt, "
					+ " decode(ck.checked2,'1','同意','0','不同意',null,'未审批')as detail2txt, "
					+ " ss.chronicstatus_id,  ss.curcheck_id,  ss.sts as sssts,   ss.sicken, ss.salstate, ss.tmpflag, "
					+ " (decode(mem.DS, '1', '城市', '2', '农村', null) || decode(substr(mem.ASSIST_TYPE, 1, 1),1,'-在保户',2,'-在保户',null) || "
					+ " decode(substr(mem.ASSIST_TYPE, 3, 1), 1, '-三无人员', null) || "
					+ " decode(substr(mem.ASSIST_TYPE, 4, 1), 1, '-五保户', null) || "
					+ " decode(substr(mem.ASSIST_TYPE, 5, 1), 1, '-优抚对象', null) || "
					+ " decode(substr(mem.assist_typex, 1, 1), 1, '-孤儿', null)) as meminfo "
					+ " from chronic_status ss, MEMBER_BASEINFOVIEW02 mem, chronic_check ck "
					+ " where mem.MEMBER_ID = ss.member_id(+) and  mem.DS=ss.member_type(+)  and ck.member_type(+)=mem.DS and (mem.assist_type <> '00000' or mem.assist_typex <> '000000') "
					+ " and ck.member_id(+) = mem.MEMBER_ID  " + jwhere2;
			sql = sql + jwhere
					+ "  order by mem.FAMILYNO,  ck.opttime1, ck.opttime2";
			session.put("sql", sql);
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("MEMINFO,val", "身份类别");
			title.put("OPTTIME1,val", "乡镇审批时间");
			title.put("OPTTIME2,val", "区县审批时间");
			title.put("DETAIL1TXT,val", "乡镇审批意见");
			title.put("DETAIL2TXT,val", "区县审批意见");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicChecks(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicmemberdone.action"));
		setToolsmenu(chronicService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	// 慢性病业务查询
	// 获得慢性病名称
	public String editsicken() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		organizationId = organizationId.substring(0, 6);
		outicds = systemDataService.findSickens(organizationId, "1", "1", "2");
		return SUCCESS;
	}

	// 慢性病审批金额
	public String querychronicinit() {
		uuid = java.util.UUID.randomUUID().toString();
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronic() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String var = value;
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
					jwhere = jwhere + " and  mem.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and  mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  mem.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  mem.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}

			Calendar c = new GregorianCalendar();// 新建日期对象
			int year = c.get(Calendar.YEAR);// 获取年份
			sql = "select mem.MEMBER_ID, "
					+ " s.curcheck_id, "
					+ " s.sicken, "
					+ " s.sts, "
					+ " s.tmpflag ,"
					+ " s.salstate, "
					+ " s.chronicstatus_id, "
					+ " mem.MEMBERNAME, "
					+ " mem.PAPERID, "
					+ " mem.SSN, "
					+ " mem.FAMILYNO, "
					+ " mem.DS, "
					+ " mem.ASSIST_TYPE, "
					+ " mem.ASSIST_TYPEX, "
					+ " mem.salvation_id, "
					+ " (nvl( icd.fix_value ,0)) as medicalmoney, "
					+ " nvl(m.income, 0) as income, "
					+ " nvl(m.payout, 0) as payout, "
					+ " nvl(m.income, 0) - nvl(m.payout, 0) as balance, (nvl( icd.fix_value ,0)-nvl(m.income, 0)) as vbalance "
					+ " from chronic_status s, "
					+ " chronic_check ck, "
					+ " MEMBER_BASEINFOVIEW02 mem, "
					+ " out_icd icd, "
					+ " (select bill.member_id as mid, "
					+ " bill.member_type as mds, "
					+ " sum(bill.income) as income, "
					+ " sum(bill.payout) as payout "
					+ " from chronic_bill bill where bill.opertime  BETWEEN TO_DATE('"
					+ year
					+ "-01-01', 'yyyy-MM-dd') AND TO_DATE('"
					+ year
					+ "-12-31 23:59:59', 'yyyy-MM-dd hh24:mi:ss') "
					+ " AND bill.optsts = '1' "
					+ " group by bill.MEMBER_ID, bill.member_type) m "
					+ " where  mem.MEMBER_ID = s.member_id "
					+ " and mem.DS = s.member_type "
					+ " and s.salstate = 1 "
					+ " and m.mid(+) = mem.MEMBER_ID "
					+ " and m.mds(+) = mem.ds "
					+ " and ck.chroniccheck_id = s.curcheck_id "
					+ " and ck.main_id = icd.icd_id(+) and icd.outtype='2' and icd.organization_id = '"
					+ organizationId
					+ "' and exists (select 1 from chronic_bill b  "
					+ "where  b.member_id = mem.MEMBER_ID and b.member_type = mem.ds and b.opertime  BETWEEN TO_DATE('"
					+ year
					+ "-01-01', 'yyyy-MM-dd') AND TO_DATE('"
					+ year
					+ "-12-31 23:59:59', 'yyyy-MM-dd hh24:mi:ss') and b.optsts = '1')";
			sql = sql + jwhere + "  order by mem.FAMILYNO";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicMoneys(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronic.action?uuid=" + uuid));
		setToolsmenu(chronicService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	public String querychronicappendinit() {
		uuid = java.util.UUID.randomUUID().toString();
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicappend() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String var = value;
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
					jwhere = jwhere + " and  mem.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and  mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  mem.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  mem.PAPERID " + var;
				} else {
				}
			}
			if ("".equals(opertime1) && "".equals(opertime2)) {
			} else if (!"".equals(opertime1) && !"".equals(opertime2)) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date optFromDate = format.parse(opertime1);
					Date optToDate = format.parse(opertime2);
					if (optFromDate.after(optToDate)) {
						jwhere = jwhere
								+ " and  mem.opttime2 BETWEEN TO_DATE('"
								+ opertime2
								+ " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opertime1
								+ " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					} else if (optFromDate.before(optToDate)
							|| optFromDate.equals(optToDate)) {
						jwhere = jwhere
								+ " and  mem.opttime2 BETWEEN TO_DATE('"
								+ opertime1
								+ " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opertime2
								+ " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else if (!"".equals(opertime1) && "".equals(opertime2)) {
				jwhere = jwhere + " and  mem.opttime2 < TO_DATE('" + opertime1
						+ " 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			} else if ("".equals(opertime1) && !"".equals(opertime2)) {
				jwhere = jwhere + " and  mem.opttime2 > TO_DATE('" + opertime2
						+ " 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}

			Calendar c = new GregorianCalendar();// 新建日期对象
			int year = c.get(Calendar.YEAR);// 获取年份
			String sql1 = "select mem.MEMBER_ID, mem.chroniccheck_id as curcheck_id, "
					+ " mem.sicken,   mem.sts, mem.tmpflag,  mem.salstate, mem.chronicstatus_id, "
					+ "  mem.MEMBERNAME,  mem.PAPERID,  mem.SSN, mem.FAMILYNO, mem.member_type as DS, "
					+ " mem.ASSIST_TYPE, mem.assist_typex, mem.opttime2, null as salvation_id, "
					+ " (nvl(mem.fix_value, 0)) as medicalmoney, "
					+ "  nvl(m.income, 0) as income,   nvl(m.payout, 0) as payout, "
					+ "  nvl(m.income, 0) - nvl(m.payout, 0) as balance,  decode(nvl(m.income, 0), "
					+ "  0,  (nvl(mem.fix_value, 0)), "
					+ " (nvl(mem.fix_value, 0)) - nvl(m.income, 0)) as vbalance from mv_chronic mem, "
					+ " (select bill.member_id as mid, bill.member_type as mds,  sum(bill.income) as income, "
					+ " sum(bill.payout) as payout   from chronic_bill bill "
					+ " where bill.opertime BETWEEN TO_DATE('"
					+ year
					+ "-01-01', 'yyyy-MM-dd') AND "
					+ "  TO_DATE('"
					+ year
					+ "-12-31 23:59:59', 'yyyy-MM-dd hh24:mi:ss') "
					+ "  AND bill.optsts = '1'   group by bill.MEMBER_ID, bill.member_type) m "
					+ "  where mem.sts = '1' and mem.salstate = '1' "
					+ "  and mem.personstate='正常' "
					+ "  and m.mid(+) = mem.MEMBER_ID and m.mds(+) = mem.member_type  "
					+ "  and (mem.ASSIST_TYPE <> '00000' or mem.assist_typex <> '000000') ";
			/*
			 * sql = + " and not exists (select 1 from chronic_bill b  " +
			 * "where  b.member_id = mem.MEMBER_ID and b.member_type = mem.member_type and b.opertime  BETWEEN TO_DATE('"
			 * + year + "-01-01', 'yyyy-MM-dd') AND TO_DATE('" + year +
			 * "-12-31 23:59:59', 'yyyy-MM-dd hh24:mi:ss') and b.optsts = '1' )"
			 * ;
			 */
			sql = sql1 + " and 1=1";
			sql = sql + jwhere + "  order by mem.FAMILYNO";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicMoneys(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicappend.action?uuid=" + uuid));
		setToolsmenu(chronicService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	// 单个存入审批金额
	public String savechronicmoney() {
		JSONObject json = new JSONObject();
		chronicCheckDTO.setTmpflag(uuid);
		String str = chronicService.saveChronicMoney(chronicCheckDTO, type);
		chronicCheckDTO = chronicService.findChronicMoneyYear(chronicCheckDTO);
		json.put("str", str);
		json.put("income", chronicCheckDTO.getIncome());
		json.put("payout", chronicCheckDTO.getPayout());
		json.put("balance", chronicCheckDTO.getBalance());
		result = json.toString();
		return SUCCESS;
	}

	// 单个账户清零
	public String cleanchronicmoney() {
		String str = chronicService.saveChronicMoney(chronicCheckDTO, type);
		chronicCheckDTO = chronicService.findChronicMoneyDTO(chronicCheckDTO);
		result = str;
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String findmoney() {
		JSONObject json = new JSONObject();
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		chronicCheckDTO.setOrganziationId(organizationId.substring(0, 6));
		outicds = systemDataService.findSickens(
				chronicCheckDTO.getOrganziationId(), "9", "9", "");
		// String a1 = chronicCheckDTO.getAssisType().substring(0, 1);
		// String a2 = chronicCheckDTO.getAssisType().substring(1, 2);
		String a3 = chronicCheckDTO.getAssisType().substring(2, 3);
		String a4 = chronicCheckDTO.getAssisType().substring(3, 4);
		// String a5 = chronicCheckDTO.getAssisType().substring(4, 5);

		if (null != outicds && outicds.size() > 0
				&& (a3.equals("1") || a4.equals("1"))) {
			int temp = 0;
			int mid = 0;
			for (OutIcdDTO s : outicds) {
				int a = s.getFixValue().intValue();
				if (a > temp) {
					temp = a;
					mid = s.getIcdId();
				}
			}
			json.put("str", temp);
			json.put("flag", "0");
			json.put("mainId", mid);
		} else {
			String str = chronicService.findMoney(chronicCheckDTO);
			json.put("str", str);
			json.put("flag", "1");
		}
		result = json.toString();
		return SUCCESS;
	}

	// 慢性病审批金额
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicallinit() {
		uuid = java.util.UUID.randomUUID().toString();
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length()) {
			String jwhere = "";
			String sql = "";
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}
			Calendar c = new GregorianCalendar();// 新建日期对象
			int year = c.get(Calendar.YEAR);// 获取年份
			sql = "select mem.MEMBER_ID, mem.chroniccheck_id as curcheck_id, "
					+ " mem.sicken,   mem.sts, mem.tmpflag,  mem.salstate, mem.chronicstatus_id, "
					+ "  mem.MEMBERNAME,  mem.PAPERID,  mem.SSN, mem.FAMILYNO, mem.member_type as DS, "
					+ " mem.ASSIST_TYPE, mem.ASSIST_TYPEX, null as salvation_id,  (nvl(mem.fix_value, 0)) as medicalmoney, "
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
					+ "  where mem.sts = '1' and mem.salstate = '1' "
					+ " and mem.personstate='正常' "
					+ " and m.mid(+) = mem.MEMBER_ID and m.mds(+) = mem.member_type  "
					+ "   and (mem.ASSIST_TYPE <> '00000' or mem.assist_typex <> '000000') ";
			sql = sql + jwhere + "  order by mem.FAMILYNO";
			session.put("sql", sql);
			cur_page = "1";
			setCcds(chronicService.findChronicMoneys(sql, new BigDecimal(
					cur_page).intValue(),
					"page/chronic/querychronicall.action?uuid=" + uuid));
			cis = chronicService.findChronicIncomeCnt(organizationId);
			if (null == cis || cis.size() < 2) {
				salstate = "1";
			} else {
				salstate = "0";
			}

			HashMap s = chronicService.findChronicMoneyInfo(organizationId);
			result = "救助对象总数：" + s.get("ZS").toString() + "人"
					+ "&nbsp;&nbsp;本年度已发救助金总数：" + s.get("INCOME").toString()
					+ "元&nbsp;&nbsp;本年度已支出金额总额：" + s.get("PAYOUT").toString()
					+ "元";
			setToolsmenu(chronicService.getToolsmenu());
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicall() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String var = value;
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
					jwhere = jwhere + " and  mem.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and  mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  mem.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  mem.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}
			Calendar c = new GregorianCalendar();// 新建日期对象
			int year = c.get(Calendar.YEAR);// 获取年份
			sql = "select mem.MEMBER_ID, "
					+ " s.curcheck_id, "
					+ " s.sicken, "
					+ " s.sts, "
					+ " s.tmpflag ,"
					+ " s.salstate, "
					+ " s.chronicstatus_id, "
					+ " mem.MEMBERNAME, "
					+ " mem.PAPERID, "
					+ " mem.SSN, "
					+ " mem.FAMILYNO, "
					+ " mem.DS, "
					+ " mem.ASSIST_TYPE, "
					+ " mem.ASSIST_TYPEX, "
					+ " mem.salvation_id, "
					+ " (nvl( icd.fix_value ,0)) as medicalmoney, "
					+ " nvl(m.income, 0) as income, "
					+ " nvl(m.payout, 0) as payout, "
					+ " nvl(m.income, 0) - nvl(m.payout, 0) as balance  ,"
					+ "  decode(nvl(m.income, 0) ,0 ,(nvl(icd.fix_value, 0) / 2) ,(nvl(icd.fix_value, 0)) - nvl(m.income, 0) ) as vbalance"
					+ " from chronic_status s, "
					+ " chronic_check ck, "
					+ " MEMBER_BASEINFOVIEW02 mem, "
					+ " out_icd icd, "
					+ " (select bill.member_id as mid, "
					+ " bill.member_type as mds, "
					+ " sum(bill.income) as income, "
					+ " sum(bill.payout) as payout "
					+ " from chronic_bill bill where bill.opertime  BETWEEN TO_DATE('"
					+ year
					+ "-01-01', 'yyyy-MM-dd') AND TO_DATE('"
					+ year
					+ "-12-31 23:59:59', 'yyyy-MM-dd hh24:mi:ss') "
					+ " AND bill.optsts = '1' "
					+ " group by bill.MEMBER_ID, bill.member_type) m "
					+ " where mem.MEMBER_ID = s.member_id "
					+ " and mem.DS = s.member_type "
					+ " and s.salstate = 1 "
					+ " and m.mid(+) = mem.MEMBER_ID "
					+ " and m.mds(+) = mem.ds "
					+ " and ck.chroniccheck_id = s.curcheck_id "
					+ " and ck.main_id = icd.icd_id(+)  and (mem.ASSIST_TYPE <>'00000' or mem.ASSIST_TYPEX <> '000000')  and icd.outtype='2'  and icd.organization_id = '"
					+ organizationId + "' ";
			sql = sql + jwhere + "  order by mem.FAMILYNO";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicMoneys(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicall.action?uuid=" + uuid));
		HashMap s = chronicService.findChronicMoneyInfo(organizationId);
		result = "救助对象总数：" + s.get("ZS").toString() + "人"
				+ "&nbsp;&nbsp;本年度已发救助金总数：" + s.get("INCOME").toString()
				+ "元&nbsp;&nbsp;本年度已支出金额总额：" + s.get("PAYOUT").toString() + "元";
		setToolsmenu(chronicService.getToolsmenu());
		cis = chronicService.findChronicIncomeCnt(organizationId);
		if (null == cis || cis.size() < 2) {
			salstate = "1";
		} else {
			salstate = "0";

		}

		return SUCCESS;
	}

	public String querychronicmoneyinit() {
		uuid = java.util.UUID.randomUUID().toString();
		opertime1 = "";
		opertime2 = "";
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicmoney() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String var = value;
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
					jwhere = jwhere + " and  t.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and  t.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  t.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  t.PAPERID " + var;
				} else {
				}
			}
			String jwhere1 = "";
			if ((opertime1.equals("") || null == opertime1)
					&& (opertime2.equals("") || null == opertime2)) {
			} else if (opertime1.equals("") || null == opertime1) {
				jwhere1 = jwhere1 + " and t.opertime <= TO_DATE('"
						+ opertime2.substring(0, 10) + "', 'yyyy-MM-dd')";

			} else if (opertime2.equals("") || null == opertime2) {
				jwhere1 = jwhere1 + " and t.opertime >= TO_DATE('"
						+ opertime1.substring(0, 10) + "', 'yyyy-MM-dd') ";

			} else {
				jwhere1 = jwhere1 + " and t.opertime  BETWEEN TO_DATE('"
						+ opertime1.substring(0, 10)
						+ "', 'yyyy-MM-dd') AND TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}

			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  t.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  t.familyno like '" + oid + "%' ";
			}
			if (null == salstate || "".equals(salstate)) {
			} else {
				jwhere = jwhere + " and c.salstate = '" + salstate + "' ";

			}
			if ("1".equals(method)) {
				jwhere = jwhere + " and  t.ds=1";
			} else if ("2".equals(method)) {
				jwhere = jwhere + " and t.ds=2 ";
			}
			/*
			 * sql = "select mem.MEMBER_ID, " + " s.curcheck_id, " +
			 * " s.sicken, " + " s.sts, " + " s.tmpflag ," + " s.salstate, " +
			 * " s.chronicstatus_id, " + " mem.MEMBERNAME, " + " mem.PAPERID, "
			 * + " mem.SSN, " + " mem.FAMILYNO, " + " mem.DS, " +
			 * " mem.ASSIST_TYPE, " + " mem.salvation_id, " +
			 * " (nvl( icd.fix_value ,0)) as medicalmoney, " +
			 * " nvl(m.income, 0) as income, " + " nvl(m.payout, 0) as payout, "
			 * +
			 * " nvl(m.income, 0) - nvl(m.payout, 0) as balance  ,(nvl( icd.fix_value ,0)-nvl(m.income, 0)) as vbalance "
			 * + " from chronic_status s, " + " chronic_check ck, " +
			 * " MEMBER_BASEINFOVIEW02 mem, " + " out_icd icd, " +
			 * " (select bill.member_id as mid, " + " bill.member_type as mds, "
			 * + " sum(bill.income) as income, " +
			 * " sum(bill.payout) as payout " +
			 * " from chronic_bill bill where 1=1 " + jwhere1 +
			 * " group by bill.MEMBER_ID, bill.member_type) m " +
			 * " where mem.MEMBER_ID = s.member_id " +
			 * " and mem.DS = s.member_type " + " and m.mid(+) = mem.MEMBER_ID "
			 * + " and m.mds(+) = mem.ds " +
			 * " and ck.chroniccheck_id = s.curcheck_id " +
			 * " and ck.main_id = icd.icd_id(+)  and icd.organization_id = '" +
			 * organizationId + "'";
			 */
			sql = " select t.MEMBER_ID, "
					+ " c.curcheck_id,"
					+ " c.sicken,"
					+ " c.sts,"
					+ " c.tmpflag,"
					+ " c.salstate,"
					+ " c.chronicstatus_id,"
					+ " t.MEMBERNAME,"
					+ " t.PAPERID,"
					+ " t.SSN,"
					+ " t.FAMILYNO,"
					+ " t.DS,"
					+ " t.ASSIST_TYPE,"
					+ " t.assist_typex,"
					+ " t.salvation_id,"
					+ " (nvl(icd.fix_value, 0)) as medicalmoney,"
					+ " nvl(t.income, 0) as income,"
					+ " nvl(t.payout, 0) as payout,"
					+ " nvl(t.income, 0) - nvl(t.payout, 0) as balance,"
					+ " (nvl(icd.fix_value, 0) - nvl(t.income, 0)) as vbalance, "
					+ " (decode(t.DS, '1', '城市', '2', '农村', null) || "
					+ " decode(substr(t.ASSIST_TYPE,1,1), 1, '-在保户', 2, '-在保户', null) ||  "
					+ " decode(substr(t.ASSIST_TYPE,3,1), 1, '-三无人员', null) || "
					+ " decode(substr(t.ASSIST_TYPE,4,1), 1, '-五保户', null) || "
					+ " decode(substr(t.ASSIST_TYPE,5,1), 1, '-优抚对象', null) || "
					+ " decode(substr(t.assist_typex,1,1), 1, '-孤儿', null)) as meminfo, "
					+ " org1.orgname as orgname1,org2.orgname as orgname2 "
					+ " from v_chronic c"
					+ " left join v_bill t on (c.member_id = t.member_id and t.ds = c.member_type)"
					+ " left join manager_org org1 on org1.depth = 4 "
					+ " and org1.organization_id = substr(t.familyno, 1, 8) "
					+ " left join manager_org org2 on org2.depth = 5 "
					+ " and org2.organization_id = substr(t.familyno, 1, 10) "
					+ " , "
					+ " out_icd icd"
					+ " where  icd.organization_id = substr(t.familyno, 0, 6)    and icd.outtype='2'  "
					+ " and icd.icd_id = c.main_id" + " and t.familyno like '"
					+ organizationId + "%' and 1 = 1  " + jwhere1;
			sql = sql + jwhere + "  order by t.FAMILYNO";
			session.put("sql", sql);
			HashMap title = new HashMap();
			// 家庭编号 姓名 身份证号 对象类别 账户信息 已发救助金
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("INCOME,val", "累计救助金");
			title.put("PAYOUT,val", "累计账户支出");
			title.put("BALANCE,val", "当前账户余额");
			title.put("MEDICALMONEY,val", "本年救助金上限");
			title.put("MEMINFO,val", "身份类别");
			title.put("ORGNAME1,val", "所属街道/乡镇");
			title.put("ORGNAME2,val", "所属社区/村");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicMoneys(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicmoney.action?opertime1=" + opertime1
						+ "&opertime2=" + opertime2 + "&uuid=" + uuid));
		HashMap s = chronicService.findChronicMoneyInfo(organizationId);
		result = "救助对象总数：" + s.get("ZS").toString() + "人"
				+ "&nbsp;&nbsp;本年度已发救助金总数：" + s.get("INCOME").toString()
				+ "元&nbsp;&nbsp;本年度已支出金额总额：" + s.get("PAYOUT").toString() + "元";
		setToolsmenu(chronicService.getToolsmenu());
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}

	// 发放救助金
	@SuppressWarnings("rawtypes")
	public String savechronicmoneyallinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// outicds = chronicService.findChronicOutIcds(organizationId);
		HashMap s = chronicService.findChronicMoneyInfo(organizationId);
		result = "救助对象总数：" + s.get("ZS").toString() + "人"
				+ "&nbsp;&nbsp;本年度已发救助金总数：" + s.get("INCOME").toString()
				+ "元&nbsp;&nbsp;本年度已支出金额总额：" + s.get("PAYOUT").toString() + "元";
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String savechronicmoneyall() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// HashMap s = chronicService.findChronicMoneyInfo(organizationId);
		// BigDecimal bincome = (BigDecimal) s.get("INCOME");
		chronicService.saveChronicMoneyAll(value, organizationId);
		HashMap s = chronicService.findChronicMoneyInfo(organizationId);
		BigDecimal aincome = (BigDecimal) s.get("INCOME");
		BigDecimal payout = (BigDecimal) s.get("PAYOUT");
		// 本次发放金额
		// BigDecimal v = aincome.subtract(bincome);
		// 总数
		// BigDecimal zs = (BigDecimal) s.get("ZS");
		// 未发人数
		result = "<table align=\"center\" class=\"formtable\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"650px\">"
				+ "<tr><td  class=\"formtd1\">救助对象总数：</td><td  class=\"formtd2\">"
				+ s.get("ZS").toString()
				+ "人</td></tr>"
				+ "<tr><td class=\"formtd1\">本年度已发救助金总数：</td><td class=\"formtd2\">"
				+ aincome.doubleValue()
				+ "元</td></tr>"
				+ "<tr><td class=\"formtd1\">本年度已支出金额总额：</td><td class=\"formtd2\">"
				+ payout.doubleValue() + "元</td></tr>" + "</table>";
		return SUCCESS;
	}

	// 查询账单信息
	public String querybillinfo() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		chronicCheckDTO.setOrganziationId(organizationId);
		chronicCheckDTO = this.chronicService
				.findChronicCheckDTO(chronicCheckDTO);
		bis = chronicService.findChronicBillInfo(chronicCheckDTO);
		return SUCCESS;
	}

	// 慢性对账单
	public String checkbillsinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() > 6) {
			orgid = organizationId.substring(0, 6);
		} else {
			orgid = organizationId;
		}
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg1(organizationId);
		int len = depts.size();
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
			if ("220506".equals(orgid)) {
				depts.remove(len);
				depts.remove(len - 1);
				depts.remove(len - 2);
				depts.remove(len - 3);
			}
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String checkbills() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String sql = "";
		// start 梅河口20131018重大疾病-------------------------------------
		if (organizationId.length() > 6) {
			orgid = organizationId.substring(0, 6);
		} else {
			orgid = organizationId;
		}
		String m_jwhere = "";
		String m_sql = "";
		String m_where = "";
		String m_from = "";
		// end 梅河口20131018重大疾病-------------------------------------
		if (null == cur_page || "".equals(cur_page)) {
			jwhere = "";
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
			if (null == hid || "".equals(hid)) {

			} else if ("-1".equals(hid)) {

			} else {
				jwhere = jwhere + " and biz.hospital_id  ='" + hid + "' ";
			}
			String jwhere1 = "";
			if ((opertime1.equals("") || null == opertime1)
					&& (opertime2.equals("") || null == opertime2)) {
			} else if (opertime1.equals("") || null == opertime1) {
				jwhere1 = jwhere1 + " and b.oper_time <= TO_DATE('"
						+ opertime2.substring(0, 10) + "', 'yyyy-MM-dd')";

			} else if (opertime2.equals("") || null == opertime2) {
				jwhere1 = jwhere1 + "and b.oper_time >= TO_DATE('"
						+ opertime1.substring(0, 10) + "', 'yyyy-MM-dd') ";

			} else {
				jwhere1 = jwhere1 + " and b.oper_time BETWEEN TO_DATE('"
						+ opertime1.substring(0, 10)
						+ "', 'yyyy-MM-dd') AND TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}

			if ((assismoney1.equals("") || null == assismoney1)
					&& (assismoeny2.equals("") || null == assismoeny2)) {
			} else if (assismoney1.equals("") || null == assismoney1) {
				jwhere = jwhere + " and c.assismoney <=" + assismoeny2;

			} else if (assismoeny2.equals("") || null == assismoeny2) {
				jwhere = jwhere + " and c.assismoney >= " + assismoney1;

			} else {
				jwhere = jwhere + " and c.assismoney BETWEEN " + assismoney1
						+ " AND " + assismoeny2;
			}

			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  biz.family_no like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
			}
			if ("1".equals(method)) {
				jwhere = jwhere + " and  biz.member_type=1";
			} else if ("2".equals(method)) {
				jwhere = jwhere + " and  biz.member_type=2 ";
			}
			if ("220506".equals(organizationId.substring(0, 6))) {
				m_sql = m_sql
						+ " , (decode(sign(dt.scaler),'1','重大疾病','普通')||'-购药')as biztypetext, dt.scaler, "
						+ " decode(sign(dt.scaler),'1','0.00',c.assismoney) as assismoney_p, "
						+ " decode(sign(dt.scaler),'1',c.assismoney,'0.00') as assismoney_s ";
				/*
				 * if ("6".equals(biztype)){ m_where = m_where +
				 * " and  dt.scaler > 0 "; }else if("3".equals(biztype)){
				 * m_where = m_where +
				 * " and (dt.scaler <= 0 or dt.scaler is null) "; }
				 */
				m_where = m_where
						+ " and (dt.scaler = 0 or dt.scaler is null) ";
			}
			sql = "select biz.biz_id, biz.ssn,d.name as hname,biz.biz_type,biz.family_no, "
					+ "biz.name, biz.id_card, e.name as icdname,e.icdcode, "
					+ "c.assismoney,c.total,c.payself,biz.DIAGNOSE_NAME ,biz.begin_time, biz.end_time,  biz.oper_time "
					+ "   , (decode(biz.member_type, '1', '城市', '2', '农村', null) || "
					+ " decode(substr(biz.person_type,1,1), 1, '-在保户', 2, '-在保户', null) ||  "
					+ " decode(substr(biz.person_type,3,1), 1, '-三无人员', null) || "
					+ " decode(substr(biz.person_type,4,1), 1, '-五保户', null) || "
					+ " decode(substr(biz.person_type,5,1), 1, '-优抚对象', null) || "
					+ " decode(substr(biz.person_typex,1,1), 1, '-孤儿', null)) as meminfo , dt.diagnose_type_name "
					+ m_sql
					+ " from jz_biz biz,(select sum(b.pay_total) as total, "
					+ "sum(b.pay_assist) as assismoney, sum(b.PAY_SELF) as payself , b.biz_id "
					+ "from jz_pay b where   mod(b.seq,2)=1 and b.sts=1 and 1=1 "
					+ jwhere1
					+ " group by b.biz_id)c,bizdept d,icd10 e ,diagnose_type dt "
					// + "MEMBER_BASEINFOVIEW02 mem  "
					+ "where   "
					// +
					// "mem.member_id = biz.member_id  and mem.ds = biz.member_type and "
					+ "c.biz_id = biz.biz_id and biz.assist_flag = 1 and biz.out_biz_id is null and biz.biz_type=3 "
					+ "and d.hospital_id(+) = biz.hospital_id and e.icd_id(+) = biz.icd_id  and biz.diagnose_type_id = dt.diagnose_type_id(+) "
					+ m_where + jwhere + " order by biz.end_time desc";
			map.put("sql", sql);
			HashMap title = new HashMap();
			title.put("SSN,val", "社会救助号");
			title.put("FAMILY_NO,val", "家庭编号");
			title.put("NAME,val", "姓名");
			title.put("ID_CARD,val", "身份证号");
			title.put("TOTAL,val", "总费用");
			if ("220506".equals(orgid)) {
				title.put("BIZTYPETEXT,val", "救助类型");
				title.put("ASSISMONEY_P,val", "医疗救助金");
				title.put("ASSISMONEY_S,val", "重大疾病救助金");
			} else {
				title.put("ASSISMONEY,val", "救助金");
				title.put("BIZ_TYPE,val", "救助类型（1门诊2住院3购药）");
			}
			title.put("HNAME,val", "医院名称");
			title.put("DIAGNOSE_NAME,val", "患病名称");
			title.put("MEMINFO,val", "身份类别");
			map.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		// 执行顺序
		setBizs(chronicService.findBizCheckAccounts(sql, new BigDecimal(
				cur_page).intValue(), "page/chronic/checkbills.action"));
		setToolsmenu(chronicService.getToolsmenu());
		String sql1 = " select count(1) as zrc, nvl(sum(total), 0) as zm, nvl(sum(assismoney), 0) as zjzj , nvl(sum(payself), 0) as zjzj1 from ( "
				+ sql + " )";
		setResult(chronicService.findInfo(sql1));
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg1(organizationId);
		int len = depts.size();
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
			if ("220506".equals(orgid)) {
				depts.remove(len);
				depts.remove(len - 1);
				depts.remove(len - 2);
				depts.remove(len - 3);
			}
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		return SUCCESS;
	}

	public String getDept() {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		List<DeptDTO> depts = systemDataService.findDeptsByOrg1(oid);
		for (DeptDTO s : depts) {
			JSONObject jo = JSONObject.fromObject(s);
			arr.add(jo);
		}
		json.put("hs", arr);
		result = json.toString();
		return SUCCESS;
	}

	/**
	 * 对账单统计
	 * 
	 * @return
	 */
	public String checkbillsstatinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (2 == organizationId.length() || 4 == organizationId.length()
				|| 6 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String checkbillsstat() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String jwhere = "";
		String sql = "";
		String jwhere1 = "";
		if ((opertime1.equals("") || null == opertime1)
				&& (opertime2.equals("") || null == opertime2)) {
		} else if (opertime1.equals("") || null == opertime1) {
			jwhere1 = jwhere1 + " and b.oper_time <= TO_DATE('"
					+ opertime2.substring(0, 10) + "', 'yyyy-MM-dd')";

		} else if (opertime2.equals("") || null == opertime2) {
			jwhere1 = jwhere1 + "and b.oper_time >= TO_DATE('"
					+ opertime1.substring(0, 10) + "', 'yyyy-MM-dd') ";

		} else {
			jwhere1 = jwhere1 + " and b.oper_time BETWEEN TO_DATE('"
					+ opertime1.substring(0, 10)
					+ "', 'yyyy-MM-dd') AND TO_DATE('"
					+ opertime2.substring(0, 10)
					+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
		}
		if (oid == null || "".equals(oid)) {
			jwhere = jwhere + " and  biz.family_no like '" + organizationId
					+ "%' ";
		} else {
			jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
		}
		if ("1".equals(method)) {
			jwhere = jwhere + " and  biz.member_type=1";
		} else if ("2".equals(method)) {
			jwhere = jwhere + " and  biz.member_type=2 ";
		}
		sql = "select d.name as hname, sum(c.assismoney) as assismoney, sum(c.total) as total, "
				+ " sum(c.payself) as payself from jz_biz biz, (select sum(b.pay_total) as total, "
				+ " sum(b.pay_assist) as assismoney,  sum(b.PAY_SELF) as payself, "
				+ " b.biz_id "
				+ " from jz_pay b "
				+ " where mod(b.seq, 2) = 1 "
				+ " and b.sts = 1 "
				+ " and 1 = 1 "
				+ jwhere1
				+ "  group by b.biz_id) c, "
				+ " bizdept d, "
				+ " icd10 e "
				+ " where c.biz_id = biz.biz_id"
				+ " and biz.assist_flag = 1"
				+ " and biz.out_biz_id is null"
				+ " and biz.biz_type = 3"
				+ " and d.hospital_id(+) = biz.hospital_id"
				+ " and e.icd_id(+) = biz.icd_id" + jwhere + " group by d.name";
		map.put("sql", sql);
		// SSN HNAME BIZ_TYPE FAMILY_NO NAME ID_CARD ICDNAME ICDCODE
		// ASSISMONEY
		// BIZ_ID TOTAL
		HashMap title = new HashMap();
		title.put("ASSISMONEY,val", "救助金");
		title.put("HNAME,val", "医院名称");
		map.put("title", title);
		sql = (String) map.get("sql");
		// 执行顺序
		setBizs(chronicService.findBizCheckAccountstat(sql));
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printcheckbills() {
		bills = chronicService.findcheckaccounts(billDTO);
		map = new HashMap();
		map.put("begin", billDTO.getBeginDate());
		map.put("end", billDTO.getEndDate());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("current", sdf.format(new Date()));
		return SUCCESS;
	}

	public String printcheckaccountslj() {
		return SUCCESS;
	}

	// 门诊慢性病政策查询
	public String chronicpolicyqueryinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length() || 4 == organizationId.length()
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	public String chronicpolicyquery() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		outicds = chronicService.findOutIcdByOrg(this.oid);
		if (6 == organizationId.length() || 4 == organizationId.length()
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	// 门诊慢性病政策查询

	@SuppressWarnings("rawtypes")
	public String querychronicgsinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 4 == organizationId.length()
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			depts = new ArrayList<DeptDTO>();
			DeptDTO e = new DeptDTO();
			e.setDeptLevel("全部");
			e.setHospitalId(-1);
			depts.add(e);
			Calendar c = new GregorianCalendar();// 新建日期对象
			int year = c.get(Calendar.YEAR);// 获取年份
			for (int i = 0; i <= year - 2010; i++) {
				e = new DeptDTO();
				e.setDeptLevel(2010 + i + "年");
				e.setHospitalId(2010 + i);
				depts.add(e);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicgs() {
		uuid = java.util.UUID.randomUUID().toString();
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String jwhere = "";

			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  c.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  c.familyno like '" + oid + "%' ";
			}
			if (null == value || "".equals(value)) {

			} else {
				jwhere = jwhere + " and c.opttime2 BETWEEN  " + " TO_DATE('"
						+ value + "-01-01', 'yyyy-MM-dd') AND " + " TO_DATE('"
						+ value + "-12-31 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}
			String jwhere2 = "";
			if (null == term || "".equals(term)) {

			} else {
				jwhere2 = jwhere2 + " bill.opertime BETWEEN " + " TO_DATE('"
						+ term + "-01-01', 'yyyy-MM-dd') AND " + " TO_DATE('"
						+ term
						+ "-12-31 23:59:59', 'yyyy-MM-dd hh24:mi:ss') AND";
			}
			String jwhere3 = "";
			if (null == method || "".equals(method)) {

			} else if ("1".equals(method)) {
				jwhere3 = " and c.a1='1'";
			} else if ("2".equals(method)) {
				jwhere3 = " and c.a1='2'";
			} else if ("3".equals(method)) {
				jwhere3 = " and c.a4='1' ";
			} else if ("4".equals(method)) {
				jwhere3 = " and c.a5='1'";
			} else if ("5".equals(method)) {
				jwhere3 = " and c.a3='1'";
			} else if ("6".equals(method)) {
				jwhere3 = " and c.a6='1'";
			} else if ("0".equals(method)) {
				jwhere3 = " and (c.ASSIST_TYPE <>'00000' or c.ASSIST_TYPEX <> '000000') ";
			} else if ("9".equals(method)) {
				jwhere3 = " and c.ASSIST_TYPE ='00000' and c.ASSIST_TYPEX = '000000' ";
			}
			// Calendar c = new GregorianCalendar();// 新建日期对象
			// int year = c.get(Calendar.YEAR);// 获取年份
			sql = "select c.MEMBER_ID,  c.sicken, c.sts, c.tmpflag,  c.salstate, c.opttime2, "
					+ " c.chronicstatus_id, c.MEMBERNAME, c.PAPERID, c.SSN, c.FAMILYNO, "
					+ " c.member_type, c.ASSIST_TYPE, c.assist_typex, '' as salvation_id, "
					+ " (nvl(icd.fix_value, 0)) as medicalmoney, nvl(m.income, 0) as income, "
					+ " nvl(m.payout, 0) as payout, nvl(m.income, 0) - nvl(m.payout, 0) as balance, "
					+ " decode(nvl(m.income, 0),  0,  (nvl(icd.fix_value, 0) / 2), "
					+ "(nvl(icd.fix_value, 0)) - nvl(m.income, 0)) as vbalance, "
					+ " c.name icdname , "
					+ "(decode(c.member_type, '1', '城市', '2', '农村', null) "
					+ "|| decode(c.a1, 1, '-在保户', 2, '-在保户', null) || decode(c.a4, 1, '-五保户', null) "
					+ "|| decode(c.a5, 1, '-优抚对象', null) "
					+ "|| decode(c.a3, 1, '-三无人员', null) "
					+ "|| decode(c.a6, 1, '-孤儿', null)) as meminfo   from mv_chronic c　"
					+ " left join (select bill.member_id as mid, "
					+ " bill.member_type as mds, "
					+ " sum(bill.income) as income, "
					+ " sum(bill.payout) as payout "
					+ " from chronic_bill bill "
					+ " where "
					+ jwhere2
					+ "  bill.optsts = '1' "
					+ " group by bill.MEMBER_ID, bill.member_type) m on m.mid =  c.MEMBER_ID and m.mds = c.member_type "
					+ " left join out_icd icd on icd.outtype='2' and icd.icd_id = c.main_id and icd.organization_id='"
					+ organizationId
					+ "' "
					+ " where c.familyno like '"
					+ oid
					+ "%' "
					+ jwhere3
					+ "  and c.salstate = '1' and c.sts = '1' "
					+ " "
					+ " and c.personstate ='正常' ";
			sql = sql + jwhere + "  order by c.FAMILYNO";
			session.put("sql", sql);
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("MEDICALMONEY,val", "本年度应发救助金");
			title.put("VBALANCE,val", "应发救助金");
			title.put("ICDNAME,val", "主要患病名称");
			title.put("OPTTIME2,val", "终审时间");
			title.put("MEMINFO,val", "身份类别");
			title.put("PAYOUT,val", "支出");
			title.put("INCOME,val", "存入");
			title.put("BALANCE,val", "余额");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicMoneys(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicgs.action"));
		setToolsmenu(chronicService.getToolsmenu());

		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		depts = new ArrayList<DeptDTO>();
		Calendar c = new GregorianCalendar();// 新建日期对象
		int year = c.get(Calendar.YEAR);// 获取年份
		for (int i = 0; i <= year - 2010; i++) {
			DeptDTO e = new DeptDTO();
			e.setDeptLevel(2010 + i + "年");
			e.setHospitalId(2010 + i);
			depts.add(e);
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querychronicbillbysubjectinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		map = chronicService.findChronicSubject(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querychronicbillbysubject() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		map = chronicService.findChronicSubject(organizationId);
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {
			sql = "select mem.familyno, mem.membername, mem.paperid, bill.income, "
					+ " (decode(mem.ds, '1', '城市', '2', '农村', null) || "
					+ " decode(mem.a1, 1, '-在保户', 2, '-在保户', null) || "
					+ " decode(mem.a4, 1, '-五保户', null) || "
					+ " decode(mem.a5, 1, '-优抚对象', null) || "
					+ " decode(mem.a3, 1, '-三无人员', null) || "
					+ " decode(mem.a6, 1, '-孤儿', null)) as meminfo  , bill.subject "
					+ " from MEMBER_BASEINFOVIEW02 mem, chronic_bill bill "
					+ " where bill.member_id = mem.member_id "
					+ " and bill.member_type = mem.ds "
					+ " and subject = '"
					+ type
					+ "' "
					+ " and bill.optsts = '1' "
					+ " and mem.personstate='正常' "
					+ " and mem.familyno like '"
					+ organizationId + "%' " + " order by mem.familyno";
			session.put("sql", sql);
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("SUBJECT,val", "科目");
			title.put("INCOME,val", "存入");
			title.put("MEMINFO,val", "身份类型");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicMoney01(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicbillbysubject.action"));
		setToolsmenu(chronicService.getToolsmenu());
		return SUCCESS;
	}

	public String querychronicbatchcancelinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length()) {

			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicbatchcancel() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {

			String var = value;
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
					jwhere = jwhere + " and  mem.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and  mem.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  mem.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  mem.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}
			// String jwhere2 = "";
			/*
			 * if (null==checked1||"".equals(checked1)) { } else { jwhere2 =
			 * jwhere2 + " and ck.checked1 ='" + checked1 + "'"; } if
			 * (null==checked2||"".equals(checked2)) { } else { jwhere2 =
			 * jwhere2 + " and ck.checked2 ='" + checked2 + "'"; }
			 */
			sql = "select mem.MEMBERNAME,  mem.PAPERID,  mem.SSN,  mem.FAMILYID,  mem.FAMILYNO,   mem.member_id as memberid,  mem.member_type as DS,"
					+ " mem.ssn as salvation_id,   mem.ASSIST_TYPE, mem.ASSIST_TYPEX, mem.chronicstatus_id,   mem.chroniccheck_id as curcheck_id,"
					+ " mem.sts as sssts,  mem.sicken, mem.salstate,  mem.tmpflag ,mem.fix_value ,mem.name "
					+ " from mv_chronic mem where (mem.assist_type <> '00000' or mem.assist_typex <> '000000') "
					+ " and mem.sts = '1'  and mem.salstate = 1  and mem.familyno like '220505%' ";
			/*
			 * sql =
			 * "select mem.MEMBERNAME,  mem.PAPERID, mem.SSN,  mem.FAMILYID, " +
			 * " mem.FAMILYNO,mem.member_id as memberid ,  mem.DS,  mem.salvation_id, mem.ASSIST_TYPE, ck.*, "
			 * +
			 * " ss.chronicstatus_id,  ss.curcheck_id,  ss.sts as sssts,   ss.sicken, ss.salstate, ss.tmpflag "
			 * +
			 * " from chronic_status ss, MEMBER_BASEINFOVIEW02 mem, chronic_check ck "
			 * +
			 * " where mem.MEMBER_ID = ss.member_id(+) and  mem.DS=ss.member_type(+)  and ck.member_type(+)=mem.DS and mem.assist_type <> '00000'"
			 * +
			 * " and ck.member_id(+) = mem.MEMBER_ID and ck.chroniccheck_id=ss.curcheck_id and ss.salstate=1 "
			 * + jwhere2; sql = sql + jwhere +
			 * "  order by mem.FAMILYNO, mem.RELMASTER , ck.opttime1, ck.opttime2"
			 * ;
			 */
			sql = sql + jwhere + "  order by mem.FAMILYNO";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setCcds(chronicService.findChronicCancels(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronic/querychronicbatchcancel.action"));
		setToolsmenu(chronicService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	public String querychronicstatlinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length()) {

			/*
			 * if (2 == organizationId.length()) { orgs =
			 * systemDataService.findOrganizationExt(organizationId); } else {
			 * orgs = systemDataService.findOrgParentAndChilds(organizationId);
			 * }
			 */
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicstatl() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String jwhere = "";
		String sql = "";
		String jwhere1 = "";
		if ((opertime1.equals("") || null == opertime1)
				&& (opertime2.equals("") || null == opertime2)) {
		} else if (opertime1.equals("") || null == opertime1) {
			jwhere1 = jwhere1 + " and b.opertime <= TO_DATE('"
					+ opertime2.substring(0, 10) + "', 'yyyy-MM-dd')";

		} else if (opertime2.equals("") || null == opertime2) {
			jwhere1 = jwhere1 + "and b.opertime >= TO_DATE('"
					+ opertime1.substring(0, 10) + "', 'yyyy-MM-dd') ";

		} else {
			jwhere1 = jwhere1 + " and b.opertime BETWEEN TO_DATE('"
					+ opertime1.substring(0, 10)
					+ "', 'yyyy-MM-dd') AND TO_DATE('"
					+ opertime2.substring(0, 10)
					+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
		}
		/*if (oid == null || "".equals(oid)) {
			jwhere = jwhere + " and  biz.family_no like '" + organizationId
					+ "%' ";
		} else {
			jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
		}*/
		if ("1".equals(method)) {
			jwhere = jwhere + " and  b.member_type=1";
		} else if ("2".equals(method)) {
			jwhere = jwhere + " and  b.member_type=2 ";
		}
		sql = "select max(o.orgname) as orgname,count(*) as rc, sum(b.income) as income "
				+ " from mv_chronic_bill b, manager_org o "
				+ " where b.bizid = '-1' "
				+ " and b.optsts = '1' "
				+ " and instr(b.subject, '账户清零') = 0 "
				+ " and b.familyno like o.organization_id || '%' "
				+ " and (o.parentorgid = '"+organizationId+"' or o.organization_id='"+organizationId+"') "
				+ "   " +jwhere1 +jwhere
				+ " group by o.organization_id "
				+ " order by o.organization_id";
		System.out.println(sql);
		map.put("sql", sql);
		// SSN HNAME BIZ_TYPE FAMILY_NO NAME ID_CARD ICDNAME ICDCODE
		// ASSISMONEY
		// BIZ_ID TOTAL
		HashMap title = new HashMap();
		title.put("ORGNAME,val", "地区");
		title.put("RC,val", "人次");
		title.put("INCOME,val", "救助金");
		map.put("title", title);
		sql = (String) map.get("sql");
		// 执行顺序
		setBizs(chronicService.findBizCheckAccountstat1(sql));
		// 获取机构
		/*if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}*/
		return SUCCESS;
	}

	public ChronicService getChronicService() {
		return chronicService;
	}

	public void setChronicService(ChronicService chronicService) {
		this.chronicService = chronicService;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public List<OrganizationDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrganizationDTO> orgs) {
		this.orgs = orgs;
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

	public String getTotalstr() {
		return totalstr;
	}

	public void setTotalstr(String totalstr) {
		this.totalstr = totalstr;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String curPage) {
		cur_page = curPage;
	}

	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational;
	}

	public void setCcds(List<ChronicCheckDTO> ccds) {
		this.ccds = ccds;
	}

	public List<ChronicCheckDTO> getCcds() {
		return ccds;
	}

	public void setChronicCheckDTO(ChronicCheckDTO chronicCheckDTO) {
		this.chronicCheckDTO = chronicCheckDTO;
	}

	public ChronicCheckDTO getChronicCheckDTO() {
		return chronicCheckDTO;
	}

	public void setIcds(List<IcdDTO> icds) {
		this.icds = icds;
	}

	public List<IcdDTO> getIcds() {
		return icds;
	}

	public void setChecked2(String checked2) {
		this.checked2 = checked2;
	}

	public String getChecked2() {
		return checked2;
	}

	public void setChecked1(String checked1) {
		this.checked1 = checked1;
	}

	public String getChecked1() {
		return checked1;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setBis(List<BillInfoDTO> bis) {
		this.bis = bis;
	}

	public List<BillInfoDTO> getBis() {
		return bis;
	}

	public void setOuticds(List<OutIcdDTO> outicds) {
		this.outicds = outicds;
	}

	public List<OutIcdDTO> getOuticds() {
		return outicds;
	}

	public void setOpertime1(String opertime1) {
		this.opertime1 = opertime1;
	}

	public String getOpertime1() {
		return opertime1;
	}

	public void setOpertime2(String opertime2) {
		this.opertime2 = opertime2;
	}

	public String getOpertime2() {
		return opertime2;
	}

	public void setSalstate(String salstate) {
		this.salstate = salstate;
	}

	public String getSalstate() {
		return salstate;
	}

	public void setCis(List<ChronicIncomeDTO> cis) {
		this.cis = cis;
	}

	public List<ChronicIncomeDTO> getCis() {
		return cis;
	}

	public void setBizs(List<BizDTO> bizs) {
		this.bizs = bizs;
	}

	public List<BizDTO> getBizs() {
		return bizs;
	}

	public List<DeptDTO> getDepts() {
		return depts;
	}

	public void setDepts(List<DeptDTO> depts) {
		this.depts = depts;
	}

	public String getAssismoney1() {
		return assismoney1;
	}

	public void setAssismoney1(String assismoney1) {
		this.assismoney1 = assismoney1;
	}

	public String getAssismoeny2() {
		return assismoeny2;
	}

	public void setAssismoeny2(String assismoeny2) {
		this.assismoeny2 = assismoeny2;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public List<BillDTO> getBills() {
		return bills;
	}

	public void setBills(List<BillDTO> bills) {
		this.bills = bills;
	}

	public BillDTO getBillDTO() {
		return billDTO;
	}

	public void setBillDTO(BillDTO billDTO) {
		this.billDTO = billDTO;
	}

	@SuppressWarnings("rawtypes")
	public HashMap getMap() {
		return map;
	}

	@SuppressWarnings("rawtypes")
	public void setMap(HashMap map) {
		this.map = map;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getOpttime1from() {
		return opttime1from;
	}

	public void setOpttime1from(String opttime1from) {
		this.opttime1from = opttime1from;
	}

	public String getOpttime1to() {
		return opttime1to;
	}

	public void setOpttime1to(String opttime1to) {
		this.opttime1to = opttime1to;
	}

	public String getOpttime2from() {
		return opttime2from;
	}

	public void setOpttime2from(String opttime2from) {
		this.opttime2from = opttime2from;
	}

	public String getOpttime2to() {
		return opttime2to;
	}

	public void setOpttime2to(String opttime2to) {
		this.opttime2to = opttime2to;
	}

	public String getSalstatus() {
		return salstatus;
	}

	public void setSalstatus(String salstatus) {
		this.salstatus = salstatus;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getBiztype() {
		return biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

}
