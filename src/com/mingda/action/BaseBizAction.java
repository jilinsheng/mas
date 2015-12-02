package com.mingda.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.mingda.common.FileHandle;
import com.mingda.common.ImageWithByte;
import com.mingda.dto.AddrBookDTO;
import com.mingda.dto.BillDTO;
import com.mingda.dto.BizCheckDTO;
import com.mingda.dto.BizDTO;
import com.mingda.dto.DeptDTO;
import com.mingda.dto.GsDTO;
import com.mingda.dto.MediaDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.PayDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.BaseBizService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseBizAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6099354085489721498L;
	/**
	 * 
	 */
	static final Logger log = Logger.getLogger(BaseBizAction.class);
	private String hid;
	private String oid;
	private String empid;
	private String opertime1;
	private String opertime2;
	private String assismoney1;
	private String assismoeny2;
	private String term;
	private String toolsmenu;
	private String value;
	private String totalstr;
	private String result;
	private String cur_page;
	private String operational;
	private String method;
	private List<OrganizationDTO> orgs;
	private List<DeptDTO> depts;
	private List<BizDTO> bizs;
	private List<BizCheckDTO> bizchecks;
	private BizCheckDTO bizCheckDTO;
	private List<BillDTO> bills;
	private BillDTO billDTO;
	private BizDTO bizDTO;
	private List<PayDTO> pays;
	private List<GsDTO> gs;
	private List<AddrBookDTO> addrlist;
	private AddrBookDTO addrBookDTO;
	private String biztype;
	@SuppressWarnings("rawtypes")
	private HashMap map;
	private List<MediaDTO> pics;
	// private String file1s;
	// private String file2s;
	// private String file3s;
	// private String file4s;
	private String pageflag;
	private BaseBizService baseBizService;
	private SystemDataService systemDataService;
	private String order;
	private String cpic;
	private String bizcheck;
	private HashMap<String, String> cmap;
	private String longitude;
	private String latitude;
	private String showtype;
	
	public HashMap<String, String> getCmap() {
		return cmap;
	}
	public void setCmap(HashMap<String, String> cmap) {
		this.cmap = cmap;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String himap() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		cmap = baseBizService.queryCenterPoint(organizationId);
		bills = baseBizService.queryHisPoint(organizationId);
		longitude=cmap.get("longitude");
		latitude=cmap.get("latitude");
		return SUCCESS;}

	/**
	 * 转院对账单
	 * 
	 * @return
	 */
	public String querycheckaccoutscinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querycheckaccoutsc() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String sql = "";
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
				jwhere = jwhere + " and biz.out_hospital_id  ='" + hid + "' ";
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
			if ("1".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=1 ";
			} else if ("2".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=2 ";
			} else if ("3".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=3 ";
			}

			sql = "select biz.biz_id, biz.ssn,d.name as hname,biz.biz_type,biz.family_no, "
					+ "biz.name, biz.id_card, e.name as icdname,e.icdcode, "
					+ "c.assismoney,c.total,c.payself,biz.DIAGNOSE_NAME ,biz.begin_time, biz.end_time, "
					+ " biz.oper_time , d1.name as iname "
					+ " from jz_biz biz,(select sum(b.pay_total) as total, "
					+ "sum(b.pay_assist) as assismoney, sum(b.PAY_SELF) as payself , b.biz_id  "
					+ "from jz_pay b where    b.sts=1 and 1=1  "
					+ jwhere1
					+ " group by b.biz_id) c,bizdept d ,   bizdept d1 , icd10 e  "
					+ "where c.biz_id = biz.biz_id and biz.assist_flag = 1  "
					+ "and  d1.hospital_id(+) = biz.out_hospital_id and biz.out_biz_id is not null "
					+ "and biz.reg_status=1 and  biz.biz_type in (1, 2) "
					+ "and d.hospital_id(+) = biz.hospital_id and e.icd_id(+) = biz.icd_id"
					+ jwhere + " order by biz.end_time desc";
			map.put("sql", sql);
			// SSN HNAME BIZ_TYPE FAMILY_NO NAME ID_CARD ICDNAME ICDCODE
			// ASSISMONEY
			// BIZ_ID TOTAL
			HashMap title = new HashMap();
			title.put("SSN,val", "社会救助号");
			title.put("FAMILY_NO,val", "家庭编号");
			title.put("NAME,val", "姓名");
			title.put("ID_CARD,val", "身份证号");
			title.put("ASSISMONEY,val", "救助金");
			title.put("TOTAL,val", "总费用");
			title.put("BIZ_TYPE,val", "救助类型（1门诊2住院3购药）");
			title.put("INAME,val", "首诊医院");
			title.put("HNAME,val", "医院名称");
			title.put("DIAGNOSE_NAME,val", "患病名称");
			map.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		// 执行顺序
		setBizs(baseBizService.findBizCheckAccounts(sql, new BigDecimal(
				cur_page).intValue(), "page/basebiz/querycheckaccouts.action"));
		setToolsmenu(baseBizService.getToolsmenu());
		String sql1 = " select count(1) as zrc, nvl(sum(total), 0) as zm, nvl(sum(assismoney), 0) as zjzj , nvl(sum(payself), 0) as zjzj1 from ( "
				+ sql + " )";
		setResult(baseBizService.findInfo(sql1));
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}

		return SUCCESS;
	}

	/**
	 * 对账单 查询 定点医院
	 * 
	 * @return
	 */
	public String querycheckaccoutsinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();

		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		int len = depts.size();
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querycheckaccouts() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String sql = "";
		
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
		
			if ("2".equals(biztype)) {
				jwhere = jwhere + " and  biz.biz_type_ex=2 ";
				showtype="2";
			} else if ("3".equals(biztype)) {
				jwhere = jwhere + " and  biz.biz_type_ex=3 ";
				showtype="2";
			} else if ("6".equals(biztype)) {
				jwhere = jwhere + " and  biz.biz_type_ex=6 ";
				showtype="2";
			} else if ("7".equals(biztype)) {
				jwhere = jwhere + " and  biz.biz_type_ex=7 ";
				showtype="2";
			} else if ("5".equals(biztype)) {
				jwhere = jwhere + " and  biz.biz_type_ex=5 ";
				showtype="2";
			} else if ("1".equals(biztype)) {
				jwhere = jwhere + " and  biz.biz_type_ex=1 ";
				showtype="2";
			} else if ("4".equals(biztype)) {
				jwhere = jwhere + " and  biz.biz_type_ex=4 ";
				showtype="2";
			} else if ("01".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=1 ";
				showtype="1";
			} else if ("02".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=2 ";
				showtype="1";
			} else if ("".equals(biztype)){
				jwhere = jwhere + " and  biz.BIZ_TYPE in ('1','2') ";
				showtype="1";
			}

			sql = "select biz.biz_id, biz.ssn,d.name as hname," 
					+ " biz.biz_type,"
					+ " decode(biz.biz_type,'1','门诊','2','住院','3','购药') as biztypetxt,"
					+ " biz.biz_type_ex,"
				    + " decode(biz.biz_type_ex, '2', '大病门诊', '3', '特殊疾病门诊', '6', '基本医疗住院','7','重特大疾病住院','5','特殊疾病住院','1','基本医疗门诊','4','基本医疗住院及特大疾病住院','其他') as biztypeextxt,"
					+ "biz.family_no, "
					+ "biz.name, f.mastername, biz.id_card, e.name as icdname,e.icdcode, "
					+ "c.assismoney,c.total,c.payself, c.payoutmedicare,  c.payassistscope, c.paymedicare, c.payciassist, "
					+ "biz.DIAGNOSE_NAME ,biz.begin_time, biz.end_time,  biz.oper_time, biz.diagnose_type_id "
					+ ", (decode(biz.member_type, '1', '城市', '2', '农村', null) || "
					+ " decode(substr(biz.person_type, 1, 1), 1, '-在保户', 2, '-在保户', null) || "
					+ " decode(substr(biz.person_type, 3, 1), 1, '-三无人员', null) || "
					+ " decode(substr(biz.person_type, 4, 1), 1, '-五保户', null) || "
					+ " decode(substr(biz.person_type, 5, 1), 1, '-优抚对象', null) || "
					+ " decode(substr(biz.person_typex, 1, 1), 1, '-孤儿', null)) as meminfo, "
					+ " decode(biz.in_type_id,'0','其他','1','消化','31','精神病','48','外伤','49','未经医保/新农合确认的转诊') as intypeid "
					+ " from jz_biz biz,(select sum(b.pay_total) as total, "
					+ "sum(b.pay_assist) as assismoney, sum(b.PAY_SELF) as payself ,sum(b.pay_outmedicare) as payoutmedicare,  "
					+ "sum(b.pay_assistscope) as payassistscope, sum(b.pay_medicare) as paymedicare, sum(b.pay_ciassist) as payciassist, b.biz_id "
					+ "from jz_pay b where    b.sts=1 and 1=1  "
					+ jwhere1
					+ " group by b.biz_id) c,bizdept d,icd10 e,member_baseinfoview02 f  "
					+ "where c.biz_id = biz.biz_id and biz.assist_flag = 1 and biz.out_biz_id is null and biz.reg_status=1 "
					+ "and d.hospital_id(+) = biz.hospital_id and e.icd_id(+) = biz.icd_id and f.member_id(+) = biz.member_id and f.ds(+) = biz.member_type "
					+ jwhere + " order by biz.end_time desc";
			map.put("sql", sql);
			// SSN HNAME BIZ_TYPE FAMILY_NO NAME ID_CARD ICDNAME ICDCODE
			// ASSISMONEY
			// BIZ_ID TOTAL PAYOUTMEDICARE PAYASSISTSCOPE PAYMEDICARE
			HashMap title = new LinkedHashMap();
			title.put("BIZTYPETXT,val", "救助方式（住院/门诊）");
			title.put("FAMILY_NO,val", "家庭编号");
			title.put("HNAME,val", "就诊医院");
			title.put("MASTERNAME,val", "户主姓名");
			title.put("NAME,val", "患者姓名");
			title.put("ID_CARD,val", "身份证号");
			//title.put("SSN,val", "社会救助号");
			title.put("MEMINFO,val", "人员类别");
			//title.put("ICDNAME,val", "确诊患病名称");
			title.put("DIAGNOSE_NAME,val", "确诊患病名称");
			title.put("BEGIN_TIME,val", "入院时间");
			title.put("END_TIME,val", "出院时间");
			title.put("TOTAL,val", "总费用");
			title.put("PAYMEDICARE,val", "统筹支付");
			title.put("PAYOUTMEDICARE,val", "目录外费用");
			title.put("PAYCIASSIST,val", "大病保险");
			//title.put("PAYASSISTSCOPE,val", "纳入救助");
			title.put("ASSISMONEY,val", "医疗救助金");
			title.put("PAYSELF,val", "个人承担");
			title.put("OPER_TIME,val", "结算时间");
			title.put("INTYPEID,val", "住院类别");
			map.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		// 执行顺序
		setBizs(baseBizService.findBizCheckAccounts(sql, new BigDecimal(
				cur_page).intValue(), "page/basebiz/querycheckaccouts.action"));
		setToolsmenu(baseBizService.getToolsmenu());
		String sql1 = " select count(1) as zrc, nvl(sum(total), 0) as zm, nvl(sum(assismoney), 0) as zjzj , nvl(sum(payself), 0) as zjzj1 from ( "
				+ sql + " )";
		setResult(baseBizService.findInfo(sql1));
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		int len = depts.size();
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}

		return SUCCESS;
	}

	/**
	 * 住院对账单查询
	 * 
	 * @return
	 */
	public String querycheckaccoutsainit() {

		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querycheckaccoutsa() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String sql = "";
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
			if ("1".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=1 ";
			} else if ("2".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=2 ";
			} else if ("3".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=3 ";
			}

			sql = "select biz.biz_id, biz.ssn,d.name as hname,biz.biz_type,biz.family_no, "
					+ "biz.name, biz.id_card, e.name as icdname,e.icdcode, "
					+ "c.assismoney,c.total,c.payself,biz.DIAGNOSE_NAME ,biz.begin_time, biz.end_time,  biz.oper_time "
					+ " from jz_biz biz,(select sum(b.pay_total) as total, "
					+ "sum(b.pay_assist) as assismoney, sum(b.PAY_SELF) as payself , b.biz_id "
					+ "from jz_pay b where    b.sts=1 and 1=1  "
					+ jwhere1
					+ " group by b.biz_id)c,bizdept d,icd10 e  "
					+ "where c.biz_id = biz.biz_id and biz.assist_flag = 1 and biz.out_biz_id is null and biz.reg_status=1 and  biz.biz_type in (1,2) "
					+ "and d.hospital_id(+) = biz.hospital_id and e.icd_id(+) = biz.icd_id"
					+ jwhere + " order by biz.end_time desc";
			map.put("sql", sql);
			// SSN HNAME BIZ_TYPE FAMILY_NO NAME ID_CARD ICDNAME ICDCODE
			// ASSISMONEY
			// BIZ_ID TOTAL
			HashMap title = new HashMap();
			title.put("SSN,val", "社会救助号");
			title.put("FAMILY_NO,val", "家庭编号");
			title.put("NAME,val", "姓名");
			title.put("ID_CARD,val", "身份证号");
			title.put("ASSISMONEY,val", "救助金");
			title.put("TOTAL,val", "总费用");
			title.put("BIZ_TYPE,val", "救助类型（1门诊2住院3购药）");
			title.put("HNAME,val", "医院名称");
			title.put("DIAGNOSE_NAME,val", "患病名称");
			map.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		// 执行顺序
		setBizs(baseBizService.findBizCheckAccounts(sql, new BigDecimal(
				cur_page).intValue(), "page/basebiz/querycheckaccouts.action"));
		setToolsmenu(baseBizService.getToolsmenu());
		String sql1 = " select count(1) as zrc, nvl(sum(total), 0) as zm, nvl(sum(assismoney), 0) as zjzj , nvl(sum(payself), 0) as zjzj1 from ( "
				+ sql + " )";
		setResult(baseBizService.findInfo(sql1));
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}

		return SUCCESS;
	}

	/**
	 * 门诊特殊慢性病对仗
	 * 
	 * @return
	 */
	public String querycheckaccoutsbinit() {

		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querycheckaccoutsb() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String sql = "";
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
			if ("1".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=1 ";
			} else if ("2".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=2 ";
			} else if ("3".equals(biztype)) {
				jwhere = jwhere + " and  biz.BIZ_TYPE=3 ";
			}

			sql = "select biz.biz_id, biz.ssn,d.name as hname,biz.biz_type,biz.family_no, "
					+ "biz.name, biz.id_card, e.name as icdname,e.icdcode, "
					+ "c.assismoney,c.total,c.payself,biz.DIAGNOSE_NAME ,biz.begin_time, biz.end_time,  biz.oper_time "
					+ " from jz_biz biz,(select sum(b.pay_total) as total, "
					+ "sum(b.pay_assist) as assismoney, sum(b.PAY_SELF) as payself , b.biz_id "
					+ "from jz_pay b where    b.sts=1 and 1=1  "
					+ jwhere1
					+ " group by b.biz_id)c,bizdept d,icd10 e  "
					+ "where c.biz_id = biz.biz_id and biz.assist_flag = 1 and biz.out_biz_id is null and biz.reg_status=1 and  biz.biz_type in (1,2)"
					+ "and d.hospital_id(+) = biz.hospital_id and e.icd_id(+) = biz.icd_id"
					+ jwhere + " order by biz.end_time desc";
			map.put("sql", sql);
			// SSN HNAME BIZ_TYPE FAMILY_NO NAME ID_CARD ICDNAME ICDCODE
			// ASSISMONEY
			// BIZ_ID TOTAL
			HashMap title = new HashMap();
			title.put("SSN,val", "社会救助号");
			title.put("FAMILY_NO,val", "家庭编号");
			title.put("NAME,val", "姓名");
			title.put("ID_CARD,val", "身份证号");
			title.put("ASSISMONEY,val", "救助金");
			title.put("TOTAL,val", "总费用");
			title.put("BIZ_TYPE,val", "救助类型（1门诊2住院3购药）");
			title.put("HNAME,val", "医院名称");
			title.put("DIAGNOSE_NAME,val", "患病名称");
			map.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		// 执行顺序
		setBizs(baseBizService.findBizCheckAccounts(sql, new BigDecimal(
				cur_page).intValue(), "page/basebiz/querycheckaccouts.action"));
		setToolsmenu(baseBizService.getToolsmenu());
		String sql1 = " select count(1) as zrc, nvl(sum(total), 0) as zm, nvl(sum(assismoney), 0) as zjzj , nvl(sum(payself), 0) as zjzj1 from ( "
				+ sql + " )";
		setResult(baseBizService.findInfo(sql1));
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}

		return SUCCESS;
	}

	/**
	 * 门诊特殊慢性病公示
	 * 
	 * @return
	 */
	public String queryoutpatientgsinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryoutpatientgs() {
		String jwhere1 = "";
		if ((opertime1.equals("") || null == opertime1)
				&& (opertime2.equals("") || null == opertime2)) {
		} else if (opertime1.equals("") || null == opertime1) {
			jwhere1 = jwhere1 + " and b.oper_time <= TO_DATE('"
					+ opertime2.substring(0, 10) + "', 'yyyy-MM-dd')";

		} else if (opertime2.equals("") || null == opertime2) {
			jwhere1 = jwhere1 + " and b.oper_time >= TO_DATE('"
					+ opertime1.substring(0, 10) + "', 'yyyy-MM-dd') ";

		} else {
			jwhere1 = jwhere1 + " and b.oper_time BETWEEN TO_DATE('"
					+ opertime1.substring(0, 10)
					+ "', 'yyyy-MM-dd') AND TO_DATE('"
					+ opertime2.substring(0, 10)
					+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
		}
		String jwhere = "";
		if (oid == null || "".equals(oid)) {
			jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
		} else {
			jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
		}
		String sql = "select biz.ssn, biz.biz_id,d.name as dname,biz.name,biz.family_no, "
				+ " biz.family_address,biz.id_card,biz.dept_name,biz.area_name, "
				+ " biz.diagnose_name,c.total,c.assismoney from jz_biz biz, "
				+ " (select sum(nvl(b.pay_total, 0)) as total, "
				+ " sum(nvl(b.pay_assist, 0)) as assismoney, "
				+ " sum(nvl(b.pay_self, 0)) as payself, "
				+ " sum(nvl(b.pay_medicare, 0) + nvl(b.pay_other, 0)) as medicare, "
				+ " b.biz_id "
				+ " from jz_pay b "
				+ " where 1 = 1 "
				+ jwhere1
				+ " group by b.biz_id) c, "
				+ " bizdept d "
				+ " where c.biz_id = biz.biz_id "
				+ " and d.hospital_id = biz.hospital_id "
				+ " and biz.biz_type = 1 "
				+ " and biz.assist_flag = 1 and biz.out_biz_id is null and biz.reg_status=1  "
				+ jwhere + " order by biz.family_no";
		bizs = baseBizService.findBizs(sql);
		map = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("current", sdf.format(new Date()));
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}

	/**
	 * 门诊特殊慢性病统计
	 * 
	 * @return
	 */
	public String queryoutpatientstatinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		orgs = systemDataService.findOrganizationExt(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryoutpatientstat() {
		billDTO.setBiztype("1");
		bills = baseBizService.findcheckaccounts(billDTO);
		map = new HashMap();
		map.put("begin", billDTO.getBeginDate());
		map.put("end", billDTO.getEndDate());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("current", sdf.format(new Date()));
		return SUCCESS;
	}

	public String getDept() {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		List<DeptDTO> depts = systemDataService.findDeptsByOrg(oid);
		for (DeptDTO s : depts) {
			JSONObject jo = JSONObject.fromObject(s);
			arr.add(jo);
		}
		json.put("hs", arr);
		result = json.toString();
		log.error("result>>>>>" + result);
		return SUCCESS;
	}

	public String getDept1() {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		List<DeptDTO> depts = systemDataService.findDeptsByOrg1(oid);
		for (DeptDTO s : depts) {
			JSONObject jo = JSONObject.fromObject(s);
			arr.add(jo);
		}
		json.put("hs", arr);
		result = json.toString();
		log.error("result>>>>>" + result);
		return SUCCESS;
	}

	public String getDept2() {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		List<DeptDTO> depts = systemDataService.findDeptsByOrg2(oid);
		for (DeptDTO s : depts) {
			JSONObject jo = JSONObject.fromObject(s);
			arr.add(jo);
		}
		json.put("hs", arr);
		result = json.toString();
		log.error("result>>>>>" + result);
		return SUCCESS;
	}

	/**
	 * 对账单统计
	 * 
	 * @return
	 */
	public String checkaccoutstatinit() {
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

	public String checkaccoutstatcinit() {
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

	public String checkaccoutstatljinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		orgs = systemDataService.findOrganizationExt(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printcheckaccounts() {
		billDTO.setBiztype("2");
		bills = baseBizService.findcheckaccounts(billDTO);
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String fullname = user.getFullname();
		String title = "";
		if ("1".equals(billDTO.getDs())) {
			title = fullname + "城市低保";
		} else if ("2".equals(billDTO.getDs())) {
			title = fullname + "农村低保";
		} else {
			title = fullname + "";
		}
		map = new HashMap();
		map.put("begin", billDTO.getBeginDate());
		map.put("end", billDTO.getEndDate());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("current", sdf.format(new Date()));
		map.put("title", title);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printcheckaccountsc() {
		billDTO.setBiztype("2");
		bills = baseBizService.findcheckaccountsc(billDTO);
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

	/**
	 * 公示报表
	 * 
	 * @return
	 */
	public String printgsinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printgs() {
		gs = baseBizService.findGsList(oid);
		map = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("current", sdf.format(new Date()));
		/*
		 * UserDTO user = (UserDTO) ActionContext.getContext().getSession().get(
		 * "user"); String organizationId = user.getOrganizationId(); orgs =
		 * systemDataService.findOrgParentAndChilds(organizationId);
		 */
		return SUCCESS;
	}

	public String printmedicalmemberinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printmedicalmember() {
		String jwhere1 = "";
		if ((opertime1.equals("") || null == opertime1)
				&& (opertime2.equals("") || null == opertime2)) {
		} else if (opertime1.equals("") || null == opertime1) {
			jwhere1 = jwhere1 + " and b.oper_time <= TO_DATE('"
					+ opertime2.substring(0, 10) + "', 'yyyy-MM-dd')";

		} else if (opertime2.equals("") || null == opertime2) {
			jwhere1 = jwhere1 + " and b.oper_time >= TO_DATE('"
					+ opertime1.substring(0, 10) + "', 'yyyy-MM-dd') ";

		} else {
			jwhere1 = jwhere1 + " and b.oper_time BETWEEN TO_DATE('"
					+ opertime1.substring(0, 10)
					+ "', 'yyyy-MM-dd') AND TO_DATE('"
					+ opertime2.substring(0, 10)
					+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
		}
		String jwhere = "";
		if (oid == null || "".equals(oid)) {
			jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
		} else {
			jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
		}
		String sql = "select biz.ssn, biz.biz_id,d.name as dname,biz.name,biz.family_no, "
				+ " biz.family_address,biz.id_card,biz.dept_name,biz.area_name, "
				+ " biz.diagnose_name,c.total,c.assismoney,biz.begin_time,biz.end_time from jz_biz biz, "
				+ " (select sum(nvl(b.pay_total, 0)) as total, "
				+ " sum(nvl(b.pay_assist, 0)) as assismoney, "
				+ " sum(nvl(b.pay_self, 0)) as payself, "
				+ " sum(nvl(b.pay_medicare, 0) + nvl(b.pay_other, 0)) as medicare, "
				+ " b.biz_id "
				+ " from jz_pay b "
				+ " where 1 = 1 "
				+ jwhere1
				+ " group by b.biz_id) c, "
				+ " bizdept d "
				+ " where c.biz_id = biz.biz_id "
				+ " and d.hospital_id = biz.hospital_id "
				+ " and biz.biz_type = 2 "
				+ " and biz.assist_flag = 1 and biz.reg_status=1"
				+ jwhere
				+ " order by biz.family_no";
		bizs = baseBizService.findBizs(sql);
		map = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("current", sdf.format(new Date()));
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}

	/**
	 * 住院审核
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public String approvemedicalmember() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		bizCheckDTO.setOrganizationId(organizationId);
		if (8 == organizationId.length()) {
			bizCheckDTO.setOperator(user.getEmpid());
			bizCheckDTO.setOperator1name(bizCheckDTO.getOperatorname());
		} else if (6 == organizationId.length()) {
			bizCheckDTO.setOperator2(user.getEmpid());
			bizCheckDTO.setOperator2name(bizCheckDTO.getOperatorname());
		}
		baseBizService.saveBizCheck(bizCheckDTO);
		bizCheckDTO = baseBizService.findBizCheck(bizCheckDTO);
		readfiles(bizCheckDTO.getBizId());
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String approvemedicalmemberinit() {
		/*
		 * 身份验证：D:\ftproot\yljz\2010\220281\YZ\YZ367.jpg
		 * 入院登记：D:\ftproot\yljz\2010\220281\DJ\DJ367.jpg
		 * 门诊结算：D:\ftproot\yljz\2010\220281\MZ\MZ367.jpg
		 * 住院结算：D:\ftproot\yljz\2010\220281\ZY\ZY367.jpg
		 */

		bizCheckDTO = baseBizService.findBizCheck(bizCheckDTO);
		readfiles(bizCheckDTO.getBizId());

		// 页面特殊处理
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		pageflag = systemDataService.findPageflag(organizationId);

		return SUCCESS;
	}

	private void readfiles(Integer bizId) {
		String picpath = FileHandle.getInstance().picpath1;
		pics = this.baseBizService.findBizPics(bizId, picpath);
	}

	public String getImgFromByte() {
		return "success";
	}

	public InputStream getInputStream() {
		byte[] pic = null;
		try {
			pic = baseBizService.findBizBlob(bizcheck, cpic);
			if (null == pic) {
				String path = this.getClass().getClassLoader().getResource("")
						.getPath()
						+ "com/mingda/common/00001.jpg";
				System.out.println(path);
				return new ByteArrayInputStream(ImageWithByte.image2Bytes(path));
			}
			System.out.println(bizcheck + ",,,," + cpic);
			System.out.println(pic.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(pic);
	}

	public String getFileName() {
		//DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = java.util.UUID.randomUUID().toString()+".jpg";
		try {
			// 中文文件名也是需要转码为 ISO8859-1，否则乱码
			return new String(fileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			return "impossible.txt";
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querymedicalmembers() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String jorder = "";
		String sql = "";
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
			if (oid == null || "".equals(oid)) {

			} else {
				jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
			}
			if (8 == organizationId.length()) {
				/*
				 * jwhere = jwhere +
				 * " and  ck.checked1 is null   and  ck.checked2 is null ";
				 */
				jwhere = jwhere
						+ " and  ck.checked1 is null and ck.checked2 is not null ";
			} else if (6 == organizationId.length()) {
				/*
				 * jwhere = jwhere +
				 * " and  ck.checked1 is not null and ck.checked2 is null";
				 */
				jwhere = jwhere + " and  ck.checked2 is null ";
			}

			if (order == null || "".equals(order)) {

			} else if ("type".equals(order)) {
				jorder = jorder + "diagnose_type_name,biz.begin_time desc ";
			} else if ("time".equals(order)) {
				jorder = jorder + "biz.begin_time desc ";
			} else if ("hospital".equals(order)) {
				jorder = jorder + " biz.hospital_id,biz.begin_time desc ";
			}
			
			if (null == hid || "".equals(hid)) {

			} else if ("-1".equals(hid)) {

			} else {
				jwhere = jwhere + " and biz.hospital_id  ='" + hid + "' ";
			}

			sql = "select (select t.diagnose_type_name  from diagnose_type t where t.diagnose_type_id = biz. DIAGNOSE_TYPE_ID and t.sts='1') diagnose_type_name "
					+ ", biz.biz_id,biz.ssn, d.name as hname, "
					+ " biz.biz_type, biz.family_no,biz.name, biz.id_card, e.name as icdname"
					+ ", ck.checked1,ck.checked2 , biz.dept_name , biz.area_name , biz.begin_time ,biz.end_time , "
					+ " biz.person_type||biz.person_typex as person_type, "
					+ " biz.member_type, "
					+ " DIAGNOSE_NAME,"
				    + " biz.spec_diagnose_flag,"
					+ " biz.biz_type_ex,"
				    + " biz.medicareflag,"
				    + " biz.reg_time "
					//+ ", biz.pay_ex as MONEYSTAND"
					+ " from jz_bizcheck ck,jz_biz biz,bizdept d,icd10 e "
					+ "where biz.biz_type=2 and biz.reg_status=1 and ck.biz_id(+) =biz.biz_id and d.hospital_id(+) = biz.hospital_id"
					+ " and e.icd_id(+) = biz.icd_id "
					+ jwhere
					+ "  and biz.assist_flag = 0 order by " + jorder ;
			map.put("sql", sql);
			HashMap title = new HashMap();
			title.put("FAMILY_NO,val", "家庭编号");
			title.put("NAME,val", "姓名");
			title.put("ID_CARD,val", "身份证号");
			title.put("HNAME,val", "就诊医院");
			title.put("DEPT_NAME,val", "入院科室");
			title.put("AREA_NAME,val", "病区");
			title.put("DIAGNOSE_TYPE_NAME,val", "诊断类型");
			title.put("DIAGNOSE_NAME,val", "诊断");
			title.put("BEGIN_TIME,val", "入院时间");
			map.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		// 执行顺序
		setBizchecks(baseBizService
				.findMedicalMembers(sql, new BigDecimal(cur_page).intValue(),
						"page/basebiz/querymedicalmembers.action"));
		setToolsmenu(baseBizService.getToolsmenu());

		// 获取机构
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		// 页面特殊处理
		pageflag = systemDataService.findPageflag(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querymedicalmembersdone() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String sql = "";
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
			if (oid == null || "".equals(oid)) {
			} else {
				jwhere = jwhere + " and  biz.family_no like '" + oid + "%' ";
			}
			if (8 == organizationId.length()) {
				jwhere = jwhere
						+ " and  ck.checked1 is not null  and  ck.checked2 is null ";
			} else if (6 == organizationId.length()) {
				jwhere = jwhere + " and ck.checked2 is not null";
			}
			sql = "select (select t.diagnose_type_name  from diagnose_type t where t.diagnose_type_id = biz. DIAGNOSE_TYPE_ID and t.sts='1') diagnose_type_name,"
					+ " biz.DIAGNOSE_TYPE_ID, biz.biz_id,biz.ssn, d.name as hname, "
					+ " biz.biz_type, biz.family_no,biz.name, biz.id_card, e.name as icdname"
					+ ", ck.checked1,ck.checked2,biz.dept_name , biz.area_name ,biz.DIAGNOSE_NAME, "
					+ " biz.begin_time, "
					+ " biz.end_time, "
					+ " biz.person_type||biz.person_typex as person_type, "
					+ " biz.member_id, "
					+ " biz.member_type "
					+ " from jz_bizcheck ck,jz_biz biz,bizdept d,icd10 e "
					+ "where  biz.biz_type=2 and biz.reg_status=1 and ck.biz_id(+) =biz.biz_id and d.hospital_id(+) = biz.hospital_id"
					+ " and e.icd_id(+) = biz.icd_id "
					+ jwhere
					+ "  and biz.assist_flag = 0";
			map.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		// 执行顺序
		setBizchecks(baseBizService
				.findMedicalMembers(sql, new BigDecimal(cur_page).intValue(),
						"page/basebiz/querymedicalmembers.action"));
		setToolsmenu(baseBizService.getToolsmenu());

		// 获取机构
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		// 页面特殊处理
		pageflag = systemDataService.findPageflag(organizationId);
		return SUCCESS;
	}

	public String querymedicalmembersinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		if (null != organizationId
				&& !"".equals(organizationId)
				&& (organizationId.length() == 6 || organizationId.length() == 8)) {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
			// 页面特殊处理
			pageflag = systemDataService.findPageflag(organizationId);
			// 获取医院列表
			depts = systemDataService.findDeptsByOrg(organizationId);
			if (null != depts && depts.size() > 0) {
				DeptDTO element = new DeptDTO();
				element.setHospitalId(null);
				element.setName("全部");
				depts.add(0, element);
			} else {
				DeptDTO element = new DeptDTO();
				element.setHospitalId(-1);
				element.setName("无");
				depts.add(0, element);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	public String querymedicalmembersdoneinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		if (null != organizationId
				&& !"".equals(organizationId)
				&& (organizationId.length() == 6 || organizationId.length() == 8)) {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
			// 页面特殊处理
			pageflag = systemDataService.findPageflag(organizationId);
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}

	public String viewbizpay() {

		bizDTO = baseBizService.findBiz(bizDTO);
		bizCheckDTO = new BizCheckDTO();
		bizCheckDTO.setFamilyNo(bizDTO.getFamilyNo());
		bizCheckDTO.setBizId(bizDTO.getBizId());
		pays = baseBizService.findBizPayInfo(bizDTO);
		readfiles(bizCheckDTO.getBizId());
		return SUCCESS;
	}

	public String viewbizpay1() {

		bizDTO = baseBizService.findBiz(bizDTO);
		bizCheckDTO = new BizCheckDTO();
		bizCheckDTO.setFamilyNo(bizDTO.getFamilyNo());
		bizCheckDTO.setBizId(bizDTO.getBizId());
		pays = baseBizService.findBizPayInfo(bizDTO);
		readfiles(bizCheckDTO.getBizId());
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryinhospitalinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 页面特殊处理
		pageflag = systemDataService.findPageflag(organizationId);
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryinhospital() {
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
			sql = "select " 
					+ " (select t.diagnose_type_name "
					+ " from diagnose_type t "
					+ " where t.diagnose_type_id = biz. DIAGNOSE_TYPE_ID and t.sts='1') diagnose_type_name,"
					+ " biz.biz_id, biz.ssn, d.name as hname,  biz.biz_type,   biz.family_no,  biz.name, "
					+ "biz.dept_name,biz.area_name,biz.begin_time,biz.diagnose_name, "
					+ " biz.id_card  ,biz.assist_flag ,biz.audit_flag ,biz.end_time  from jz_biz biz, bizdept d "
					+ " where d.hospital_id(+) = biz.hospital_id"
					+ " and biz.biz_type = 2 and biz.reg_status=1 "
					+ " and biz.family_no like '"
					+ oid
					+ "%'"
					+ jwhere
					+ " order by biz.begin_time desc , biz.family_no";
			session.put("sql", sql);
		} else {
			sql = (String) session.get("sql");
		}
		bizchecks = baseBizService.findInHospital(sql,
				new BigDecimal(cur_page).intValue(),
				"page/basebiz/queryinhospital.action");
		setToolsmenu(baseBizService.getToolsmenu());
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		// 页面特殊处理
		pageflag = systemDataService.findPageflag(organizationId);
		return SUCCESS;
	}

	/**
	 * 查询通讯录列表
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String findAddrBookList() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String sql = "";
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
				if ("NAME".equals(term)) {
					jwhere = jwhere + " and book.name  " + var;
				} else if ("DUTY".equals(term)) {
					jwhere = jwhere + " and book.duty  " + var;
				} else if ("WORKPHONE".equals(term)) {
					jwhere = jwhere + " and  book.Work_Phone  " + var;
				} else if ("MOBILEPHONE".equals(term)) {
					jwhere = jwhere + " and  book.Mobile_Phone " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  book.organization_id like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  book.organization_id like '" + oid
						+ "%' ";
			}
			sql = "select book.name, case when book.duty = '1' then '区低保中心主任'  when book.duty = '2' then '街道主任'  when book.duty = '3' then '低保专员' end as duty ,book.work_phone,book.mobile_phone,book.emp_id,book.organization_id,org.orgname,book.sts "
					+ "from Address_Book book,manager_org org where org.organization_id = book.organization_id "
					+ jwhere;
			map.put("sql", sql);
			HashMap title = new HashMap();
			title.put("NAME,val", "姓名");
			title.put("DUTY,val", "职务");
			title.put("WORKPHONE,val", "办公电话");
			title.put("MOBILEPHONE,val", "手机号码");

			map.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		addrlist = baseBizService.findAddrBookList(sql,
				new BigDecimal(cur_page).intValue(), "findAddrBookList.action",
				organizationId);
		setToolsmenu(baseBizService.getToolsmenu());
		orgs = systemDataService.findOrgParentAndChilds(organizationId);
		return SUCCESS;
	}

	/**
	 * 查询单体通讯记录
	 * 
	 * @return
	 */
	public String findAddrBook() {
		if ("edit".equals(method) || "view".equals(method)) {
			addrBookDTO = baseBizService.findAddrBookById(new Integer(Integer
					.parseInt(empid)));
			addrBookDTO.setOrgname(systemDataService.findOrganziation(
					addrBookDTO.getOrganizationId()).getOrgname());
			String sts = addrBookDTO.getSts();
			if ("1".equals(sts)) {
				addrBookDTO.setStsval("是");
			} else {
				addrBookDTO.setStsval("否");
			}
			String duty = addrBookDTO.getDuty();
			if ("1".equals(duty)) {
				addrBookDTO.setDutyval("区低保中心主任");
			} else if ("2".equals(duty)) {
				addrBookDTO.setDutyval("街道主任");
			} else if ("3".equals(duty)) {
				addrBookDTO.setDutyval("低保专员");
			} else {
				addrBookDTO.setDutyval("");
			}

		} else if ("add".equals(method)) {
			UserDTO user = (UserDTO) ActionContext.getContext().getSession()
					.get("user");
			String organizationId = user.getOrganizationId();
			addrBookDTO = new AddrBookDTO();
			addrBookDTO.setOrganizationId(organizationId);
			addrBookDTO.setOrgname(systemDataService.findOrganziation(
					organizationId).getOrgname());
		}
		return SUCCESS;

	}

	public String delAddrBook() {
		baseBizService.delAddrBook(new Integer(Integer.parseInt(empid)));
		return SUCCESS;
	}

	public String saveAddrBook() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		baseBizService.saveAddrBook(addrBookDTO);
		addrBookDTO.setOrgname(systemDataService.findOrganziation(
				organizationId).getOrgname());
		String sts = addrBookDTO.getSts();
		if ("1".equals(sts)) {
			addrBookDTO.setStsval("是");
		} else {
			addrBookDTO.setStsval("否");
		}
		String duty = addrBookDTO.getDuty();
		if ("1".equals(duty)) {
			addrBookDTO.setDutyval("区低保中心主任");
		} else if ("2".equals(duty)) {
			addrBookDTO.setDutyval("街道主任");
		} else if ("3".equals(duty)) {
			addrBookDTO.setDutyval("低保专员");
		} else {
			addrBookDTO.setDutyval("");
		}
		method = "view";

		return SUCCESS;
	}

	public BaseBizService getBaseBizService() {
		return baseBizService;
	}

	public void setBaseBizService(BaseBizService baseBizService) {
		this.baseBizService = baseBizService;
	}

	public List<OrganizationDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrganizationDTO> orgs) {
		this.orgs = orgs;
	}

	public List<DeptDTO> getDepts() {
		return depts;
	}

	public void setDepts(List<DeptDTO> depts) {
		this.depts = depts;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOpertime1() {
		return opertime1;
	}

	public void setOpertime1(String opertime1) {
		this.opertime1 = opertime1;
	}

	public String getOpertime2() {
		return opertime2;
	}

	public void setOpertime2(String opertime2) {
		this.opertime2 = opertime2;
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

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String curPage) {
		cur_page = curPage;
	}

	public List<BizDTO> getBizs() {
		return bizs;
	}

	public void setBizs(List<BizDTO> bizs) {
		this.bizs = bizs;
	}

	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational;
	}

	public void setBizchecks(List<BizCheckDTO> bizchecks) {
		this.bizchecks = bizchecks;
	}

	public List<BizCheckDTO> getBizchecks() {
		return bizchecks;
	}

	public void setBizCheckDTO(BizCheckDTO bizCheckDTO) {
		this.bizCheckDTO = bizCheckDTO;
	}

	public BizCheckDTO getBizCheckDTO() {
		return bizCheckDTO;
	}

	public void setBills(List<BillDTO> bills) {
		this.bills = bills;
	}

	public List<BillDTO> getBills() {
		return bills;
	}

	public void setBillDTO(BillDTO billDTO) {
		this.billDTO = billDTO;
	}

	public BillDTO getBillDTO() {
		return billDTO;
	}

	@SuppressWarnings({ "rawtypes" })
	public void setMap(HashMap map) {
		this.map = map;
	}

	@SuppressWarnings("rawtypes")
	public HashMap getMap() {
		return map;
	}

	public void setBizDTO(BizDTO bizDTO) {
		this.bizDTO = bizDTO;
	}

	public BizDTO getBizDTO() {
		return bizDTO;
	}

	public void setPays(List<PayDTO> pays) {
		this.pays = pays;
	}

	public List<PayDTO> getPays() {
		return pays;
	}

	public void setAssismoney1(String assismoney1) {
		this.assismoney1 = assismoney1;
	}

	public String getAssismoney1() {
		return assismoney1;
	}

	public void setAssismoeny2(String assismoeny2) {
		this.assismoeny2 = assismoeny2;
	}

	public String getAssismoeny2() {
		return assismoeny2;
	}

	public void setGs(List<GsDTO> gs) {
		this.gs = gs;
	}

	public List<GsDTO> getGs() {
		return gs;
	}

	public void setAddrlist(List<AddrBookDTO> addrlist) {
		this.addrlist = addrlist;
	}

	public List<AddrBookDTO> getAddrlist() {
		return addrlist;
	}

	public void setAddrBookDTO(AddrBookDTO addrBookDTO) {
		this.addrBookDTO = addrBookDTO;
	}

	public AddrBookDTO getAddrBookDTO() {
		return addrBookDTO;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getEmpid() {
		return empid;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void setPics(List<MediaDTO> pics) {
		this.pics = pics;
	}

	public List<MediaDTO> getPics() {
		return pics;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

	public String getBiztype() {
		return biztype;
	}

	public void setPageflag(String pageflag) {
		this.pageflag = pageflag;
	}

	public String getPageflag() {
		return pageflag;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getCpic() {
		return cpic;
	}

	public void setCpic(String cpic) {
		this.cpic = cpic;
	}

	public String getBizcheck() {
		return bizcheck;
	}

	public void setBizcheck(String bizcheck) {
		this.bizcheck = bizcheck;
	}
	public String getShowtype() {
		return showtype;
	}
	public void setShowtype(String showtype) {
		this.showtype = showtype;
	}

}
