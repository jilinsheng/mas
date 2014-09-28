package com.mingda.action;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.PayDTO;
import com.mingda.dto.RateDTO;
import com.mingda.dto.ReportDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.ReportService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ReportAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	static final Logger log = Logger.getLogger(ReportAction.class);
	private String quarter;
	@SuppressWarnings("rawtypes")
	private HashMap map;
	private List<ReportDTO> data;
	private List<String> quarterlist;
	private ReportService reportService;
	private SystemDataService systemDataService;
	private String ext;
	private String reporttype;
	@SuppressWarnings("rawtypes")
	private Map reportlist;
	private String reportname;
	private List<String> monthlist;
	private String start_month;
	private String end_month;
	private String month;
	private String level;
	private List<OrganizationDTO> regionlist;
	private String result;
	private String region;
	private List<OrganizationDTO> citys;
	private String city;
	private List<OrganizationDTO> streets;
	private String street;
	private String type;
	private List<String> diagnoselist;
	private String diagnose;
	private List<OrganizationDTO> regionlist2;
	private List<OrganizationDTO> citys2;
	private List<PayDTO> paylist;
	private List<PayDTO> paylist01;
	private List<PayDTO> paylist02;
	private List<PayDTO> paylist03;
	private List<PayDTO> paylist04;
	private String membertype;
	private String opttime1from;
	private String opttime1to;
	private List<OrganizationDTO> orgs;
	private String oid;
	private PayDTO payDTO;
	private String cur_page;
	private String toolsmenu;
	private String year;
	private List<RateDTO> ratelist;
	
	// 登录人所属机构信息
	UserDTO user = (UserDTO) ActionContext.getContext().getSession().get("user");
	String organizationId = user.getOrganizationId();
	int orgLen = organizationId.length(); // 组织机构长度
	String organizationname = user.getOrgname(); // 组织机构名称

	/**
	 * ajax 地市两级联动
	 * 
	 * @return
	 */
	public String getcity() {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		List<OrganizationDTO> citys = systemDataService.findOrgChilds(region);
		for (OrganizationDTO s : citys) {
			JSONObject jo = JSONObject.fromObject(s);
			arr.add(jo);
		}
		json.put("hs", arr);
		result = json.toString();
		log.error("result>>>>>" + result);
		return SUCCESS;
	}

	/**
	 * ajax 地市街三级联动
	 * 
	 * @return
	 */
	public String getcitystreet() {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		JSONArray arr1 = new JSONArray();
		List<OrganizationDTO> citys = systemDataService.findOrgChilds(region);
		List<OrganizationDTO> streets = systemDataService.findOrgChilds(citys.get(0).getOrganizationId());
		for (OrganizationDTO s : citys) {
			JSONObject jo = JSONObject.fromObject(s);
			arr.add(jo);
		}
		for (OrganizationDTO s : streets) {
			JSONObject jo = JSONObject.fromObject(s);
			arr1.add(jo);
		}
		json.put("hs", arr);
		json.put("st", arr1);
		result = json.toString();
		log.error("result>>>>>" + result);
		return SUCCESS;
	}

	/**
	 * 获取报表列表
	 * 
	 * @return 1. 基本情况 4. 费用情况
	 */

	public String reportInit() {
		setReportlist(new HashMap<String, String>());
		if ("1".equals(reporttype)) {
			monthlist = reportService.findMonth();
			return "jiben";

			// reportlist.put("jibencsinit","城市医疗救助情况统计表");
			// reportlist.put("jibenncinit","农村医疗救助情况统计表");
		} else if ("2".equals(reporttype)) {
			return "zijin";
		}

		else if ("3".equals(reporttype)) {
			monthlist = reportService.findMonth();
			return "duixiang";
			// reportlist.put("duixiangcsinit", "城市医疗救助对象情况统计表");
			// reportlist.put("duixiangncinit", "农村医疗救助对象情况统计表");
		} else if ("4".equals(reporttype)) {
			quarterlist = reportService.findQuarters();
			return "feiyong";

			// reportlist.put("feiyongcszhuyuaninit","城市住院救助医疗费用情况统计表" );
			// reportlist.put("feiyongnczhuyuaninit","农村住院救助医疗费用情况统计表");
			// reportlist.put("feiyongcsmenzheninit","城市门诊特殊慢性病院救助医疗费用情况统计表");
			// reportlist.put("feiyongncmenzheninit","农村门诊特殊慢性病救助医疗费用情况统计表");
			// reportlist.put("feiyongcslinshiinit","吉林省城市临时救助医疗费用情况统计表");
			// reportlist.put("feiyongnclinshiinit","吉林省农村临时救助医疗费用情况统计表");
		} else if ("5".equals(reporttype)) {
			monthlist = reportService.findMonth();
			regionlist = reportService.findRegion("2", organizationId);

			// 用于权限当区级登录时city只可选择部门区县
			if (organizationId.length() == 6) {
				List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
				OrganizationDTO org = new OrganizationDTO();
				org.setOrgname(user.getOrgname());
				org.setOrganizationId(organizationId);
				list.add(org);
				citys = list;
			} else {
				citys = systemDataService.findOrgChilds(regionlist.get(0).getOrganizationId());
			}

			diagnoselist = reportService.findDiagnose();
			return "bingzhong";
			// reportlist.put("bingzhongcsinit", "城市医疗救助病种情况调查表");
			// reportlist.put("bingzhongncinit", "农村医疗救助病种情况调查表");
			// reportlist.put("bingzhongcscityinit", "县(市、区)城市医疗救助病种情况调查表");
			// reportlist.put("bingzhongnccityinit", "县(市、区)农村医疗救助病种情况调查表");
			// reportlist.put("bingzhongcssickinit", "吉林省城市病种救助情况调查表");
			// reportlist.put("bingzhongncsickinit", "吉林省农村病种救助情况调查表");
		}

		else if ("6".equals(reporttype)) {
			quarterlist = reportService.findQuarters();

			regionlist = reportService.findRegion("2", organizationId);

			monthlist = reportService.findMonth();

			regionlist2 = reportService.findRegion("2");
			OrganizationDTO orgall = new OrganizationDTO();
			orgall.setOrgname("全部");
			orgall.setOrganizationId("0");
			regionlist2.add(0, orgall);
			citys2 = new ArrayList<OrganizationDTO>();
			citys2.add(orgall);

			// 用于权限当区级登录时city只可选择部门区县
			if (organizationId.length() == 6) {
				List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
				OrganizationDTO org = new OrganizationDTO();
				org.setOrgname(user.getOrgname());
				org.setOrganizationId(organizationId);
				list.add(org);
				citys = list;
			} else {
				citys = systemDataService.findOrgChilds(regionlist.get(0).getOrganizationId());
			}
			return "jigou";
			// reportlist.put("jigoussinit", "省市级医疗救助医疗机构花费情况统计表");
			// reportlist.put("jigouxjinit", "县街级医疗救助医疗机构花费情况统计表");
			// reportlist.put("jigouinit","各级医疗机构花费情况调查表" );
			// reportlist.put("jigouxsqinit","县（市、区）定点医疗机构花费情况调查表");
		} else if ("7".equals(reporttype)) {
			// reportlist.put("canbao", "参保");
			// reportlist.put("canhe", "参合");
			return "canhecanbao";
		} else if ("8".equals(reporttype)) {

			regionlist = reportService.findRegion("2", organizationId);

			// 用于权限当区级登录时city只可选择部门区县
			if (organizationId.length() == 6) {
				List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
				OrganizationDTO org = new OrganizationDTO();
				org.setOrgname(user.getOrgname());
				org.setOrganizationId(organizationId);
				list.add(org);
				citys = list;
			} else {
				citys = systemDataService.findOrgChilds(regionlist.get(0).getOrganizationId());
			}
			// regionlist = reportService.findRegion("2");
			// citys = systemDataService.findOrgChilds(regionlist.get(0).getOrganizationId());
			streets = systemDataService.findOrgChilds(citys.get(0).getOrganizationId());
			monthlist = reportService.findMonth();
			return "zongti";

			// reportlist.put("zongticjinit", "城市医疗救助情况统计表");
			// reportlist.put("zongtinxinit", "农村医疗救助情况统计表");
			// reportlist.put("zongticsinit", "城市医疗救助情况调查表");
			// reportlist.put("zongtincinit", "农村医疗救助情况调查表");

		} 
		//医疗机构调查表
		else if ("9".equals(reporttype)) {
			regionlist = reportService.findRegion("2", organizationId);
			// 用于权限当区级登录时city只可选择部门区县
			if (organizationId.length() == 6) {
				List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();
				OrganizationDTO org = new OrganizationDTO();
				org.setOrgname(user.getOrgname());
				org.setOrganizationId(organizationId);
				list.add(org);
				citys = list;
			} else {
				citys = systemDataService.findOrgChilds(regionlist.get(0).getOrganizationId());
			}
			return "yiliaojigou";	
		}

		return SUCCESS;
	}

	/**
	 * 基本情况 城市
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String jibencs() {
		String jwhere = "";
		if (!(start_month == null || "".equals(start_month))) {
			jwhere = jwhere + " and to_char(ubiz.oper_time,'yyyy-mm') between '" + start_month + "' and '" + end_month + "'";
		}
		String sql = " select  org2.organization_id as parentorg,t.*,org2.orgname,m.pnum from "
				+ " (select sum(decode(ubiz.biz_type,'2',1,0)) as zhuyuanp, "
				+ " sum(decode(ubiz.biz_type,'2',ubiz.pay_assist,0)) as zhuyuanm, "
				+ " sum(decode(ubiz.biz_type,'1',1,0)) as menzhenp, "
				+ " sum(decode(ubiz.biz_type,'1',ubiz.pay_assist,0)) as menzhenm, "
				+ " sum(decode(ubiz.biz_type,'3',1,0)) as yaodianp, "
				+ " sum(decode(ubiz.biz_type,'3',ubiz.pay_assist,0)) as yaodianm, "
				+ " sum(decode(ubiz.biz_type,'4',1,0)) as linship, "
				+ " sum(decode(ubiz.biz_type,'4',ubiz.pay_assist,0)) as linshim, "
				+ " substr(ubiz.family_no,0,6) as region,org.orgname regionname "
				+ " from (select biz.biz_id,biz.biz_type,biz.family_no,biz.assist_flag,biz.member_type,biz.oper_time,pay.pay_assist "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_status = '1' "
				+ " union "
				+ " select app.approve_id,'4',tp.organizationid,'1',app.member_type,app.approvetime,app.pay_assist "
				+ " from temp_approve app inner join  temp_person tp on app.member_id = tp.member_id where app.approvests = '2'"
				+ " union " 
				+ " select ma.biz_id, '2', ma.family_no, '1', ma.member_type, ma.oper_time, ma.pay_assist " 
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '2' " 
				+ " union " 
				+ " select ma.biz_id, '1', ma.family_no, '1', ma.member_type, ma.oper_time, ma.pay_assist " 
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '1') ubiz "
				+ " inner join manager_org org on substr(ubiz.family_no,0,6) = org.organization_id "
				+ " where ubiz.member_type = '1' "
				+ " and ubiz.assist_flag='1' "
				+ " and substr(ubiz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ jwhere
				+ " group by substr(ubiz.family_no,0,6),org.orgname) t "
				+ " inner join manager_org org1 on t.region = org1.organization_id "
				+ " inner join manager_org org2 on org1.parentorgid = org2.organization_id "
				+ " left join (select count(mem.member_id) pnum,substr(mem.familyno,0,6) as familyno from MEMBER_BASEINFOVIEW02 mem where mem.ds='1' and mem.a1 = '1' group by substr(mem.familyno,0,6)) m on m.familyno = t.region "
				+ " order by org2.organization_id";
		data = reportService.findjiben(sql);
		map = new HashMap();
		map.put("start_month", start_month);
		map.put("end_month", end_month);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 基本情况 农村
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String jibennc() {
		String jwhere = "";
		if (!(start_month == null || "".equals(start_month))) {
			jwhere = jwhere + " and to_char(ubiz.oper_time,'yyyy-mm') between '" + start_month + "' and '" + end_month + "'";
		}
		String sql = " select  org2.organization_id as parentorg,t.*,org2.orgname,m.pnum from "
				+ " (select sum(decode(ubiz.biz_type,'2',1,0)) as zhuyuanp, "
				+ " sum(decode(ubiz.biz_type,'2',ubiz.pay_assist,0)) as zhuyuanm, "
				+ " sum(decode(ubiz.biz_type,'1',1,0)) as menzhenp, "
				+ " sum(decode(ubiz.biz_type,'1',ubiz.pay_assist,0)) as menzhenm, "
				+ " sum(decode(ubiz.biz_type,'3',1,0)) as yaodianp, "
				+ " sum(decode(ubiz.biz_type,'3',ubiz.pay_assist,0)) as yaodianm, "
				+ " sum(decode(ubiz.biz_type,'4',1,0)) as linship, "
				+ " sum(decode(ubiz.biz_type,'4',ubiz.pay_assist,0)) as linshim, "
				+ " substr(ubiz.family_no,0,6) as region,org.orgname regionname "
				+ " from (select biz.biz_id,biz.biz_type,biz.family_no,biz.assist_flag,biz.member_type,biz.oper_time,pay.pay_assist "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_status = '1' "
				+ " union "
				+ " select app.approve_id,'4',tp.organizationid,'1',app.member_type,app.approvetime,app.pay_assist "
				+ " from temp_approve app inner join  temp_person tp on app.member_id = tp.member_id where app.approvests = '2'"
				+ " union " 
				+ " select ma.biz_id, '2', ma.family_no, '1', ma.member_type, ma.oper_time, ma.pay_assist " 
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '2' " 
				+ " union " 
				+ " select ma.biz_id, '1', ma.family_no, '1', ma.member_type, ma.oper_time, ma.pay_assist " 
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '1') ubiz "
				+ " inner join manager_org org on substr(ubiz.family_no,0,6) = org.organization_id "
				+ " where ubiz.member_type = '2' "
				+ " and ubiz.assist_flag='1' "
				+ " and substr(ubiz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ jwhere
				+ " group by substr(ubiz.family_no,0,6),org.orgname) t "
				+ " inner join manager_org org1 on t.region = org1.organization_id "
				+ " inner join manager_org org2 on org1.parentorgid = org2.organization_id "
				+ " left join (select count(mem.member_id) pnum,substr(mem.familyno,0,6) as familyno from MEMBER_BASEINFOVIEW02 mem where mem.ds='2' and substr(mem.assist_type,0,3) <> '000' group by substr(mem.familyno,0,6)) m on m.familyno = t.region "
				+ " order by org2.organization_id";
		data = reportService.findjiben(sql);
		map = new HashMap();
		map.put("start_month", start_month);
		map.put("end_month", end_month);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 对象情况 城市
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String duixiangcs() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + " and TO_CHAR(tb.oper_time,'yyyy-mm') = '" + month + "'";
		}
		String sql = " select org1.orgname as orgname,org2.orgname as parentorgname,org1.organization_id orgid,org2.organization_id parentorgid,nvl(sum(zyswrc),0) as zyswrc,nvl(sum(zyswcbrc),0) as zyswcbrc,nvl(sum(zyswje),0) as zyswje,nvl(sum(zyybrc),0) as zyybrc, "
				+ " nvl(sum(zyybcbrc),0) as zyybcbrc,nvl(sum(zyybje),0) as zyybje, "
				+ " nvl(sum(mzswrc),0) as mzswrc,nvl(sum(mzswcbrc),0) as mzswcbrc,nvl(sum(mzswje),0) as mzswje,nvl(sum(mzybrc),0) as mzybrc,nvl(sum(mzybcbrc),0) as mzybcbrc,nvl(sum(mzybje),0) as mzybje, "
				+ " nvl(sum(ydswrc),0) as ydswrc,nvl(sum(ydswcbrc),0) as ydswcbrc,nvl(sum(ydswje),0) as ydswje,nvl(sum(ydybrc),0) as ydybrc,nvl(sum(ydybcbrc),0) as ydybcbrc,nvl(sum(ydybje),0) as ydybje "
				+ " from "
				+ " (select t.orgid , "
				+ " case when t.biz_type = '2' and t.person_type like '__1%' then t.pnum end as zyswrc, "
				+ " case when t.biz_type = '2' and t.person_type like '1_1%' then t.medicarenum end as zyswcbrc, "
				+ " case when t.biz_type = '2' and t.person_type like '__1%' then t.assist end as zyswje, "
				+ " case when t.biz_type = '2' and t.person_type like '__0%' then t.pnum end as   zyybrc, "
				+ " case when t.biz_type = '2' and t.person_type like '1_0%' then t.medicarenum end as   zyybcbrc, "
				+ " case when t.biz_type = '2' and t.person_type like '__0%' then t.assist end as zyybje, "
				+ " case when t.biz_type = '1' and t.person_type like '__0%' then t.pnum end as mzybrc, "
				+ " case when t.biz_type = '1' and t.person_type like '1_0%' then t.medicarenum end as mzybcbrc, "
				+ " case when t.biz_type = '1' and t.person_type like '__0%' then t.assist end as mzybje, "
				+ " case when t.biz_type = '1' and t.person_type like '__1%' then t.pnum end as mzswrc, "
				+ " case when t.biz_type = '1' and t.person_type like '1_1%' then t.medicarenum end as mzswcbrc, "
				+ " case when t.biz_type = '1' and t.person_type like '__1%' then t.assist end as mzswje, "
				+ " case when t.biz_type = '3' and t.person_type like '__0%' then t.pnum end as ydybrc, "
				+ " case when t.biz_type = '3' and t.person_type like '1_0%' then t.medicarenum end as ydybcbrc, "
				+ " case when t.biz_type = '3' and t.person_type like '__0%' then t.assist end as ydybje, "
				+ " case when t.biz_type = '3' and t.person_type like '__1%' then t.pnum end as ydswrc, "
				+ " case when t.biz_type = '3' and t.person_type like '1_1%' then t.medicarenum end as ydswcbrc, "
				+ " case when t.biz_type = '3' and t.person_type like '__1%' then t.assist end as ydswje "
				+ " from  "
				//修改
				+ " (select substr(tb.org, 0, 6) orgid,tb.biztype as biz_type,tb.persontype as person_type,count(tb.bizid) as pnum,sum(decode(tb.medicaretype, '2', 1, 0)) as medicarenum,sum(tb.payassist) as assist "
				+ " from ( "
				+ " select org.organization_id as org,biz.biz_id as bizid,biz.biz_type as biztype,biz.person_type || biz.person_typex as persontype,biz.medicare_type as medicaretype,pay.pay_assist as payassist,pay.oper_time "
				+ " from jz_biz biz "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where  biz.member_type = '1' "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ " and biz.assist_flag='1' "
				+ "  union all "
				+ " select ma.organization_id as org,ma.biz_id,ma.assist_type as biztype,ma.assist_type_m || decode(ma.assist_typex,null,'0',substr(ma.assist_typex,0,1)) as persontype,ma.medicare_type as medicaretype,ma.pay_assist as payassist,ma.oper_time "
				+ " from jz_medicalafter ma "
				+ " where ma.biz_status = '1' "
				+ " and ma.member_type = '1' "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ " ) tb where 1=1 "
				/*+ " (select substr(org.organization_id,0,6) orgid,biz.biz_type,biz.person_type,count(biz.biz_id) as pnum,sum(decode(biz.medicare_type,'1',1,0)) as medicarenum,sum(pay.pay_assist) as assist "
				+ " from jz_biz biz "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.member_type = '1'  "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ " and biz.assist_flag='1' "*/
				+ jwhere
				+ " group by substr(tb.org, 0, 6),tb.biztype, tb.persontype )t) tt "
				/*+ " group by substr(org.organization_id,0,6),biz.biz_type,biz.person_type) t) tt "*/
				+ " inner join manager_org org1 on org1.organization_id = tt.orgid "
				+ " inner join manager_org org2 on org1.parentorgid = org2.organization_id "
				+ " group by org1.orgname ,org2.orgname ,org1.organization_id ,org2.organization_id "
				+ " order by org1.organization_id";
		data = reportService.findduixiang(sql);
		map = new HashMap();
		map.put("month", month);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 对象情况 农村
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String duixiangnc() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + " and TO_CHAR(tb.oper_time,'yyyy-mm') = '" + month + "'";
		}
		String sql = " select org1.orgname as orgname,org2.orgname as parentorgname,org1.organization_id orgid,org2.organization_id parentorgid,nvl(sum(zyswrc),0) as zyswrc,nvl(sum(zyswcbrc),0) as zyswcbrc,nvl(sum(zyswje),0) as zyswje,nvl(sum(zyybrc),0) as zyybrc, "
				+ " nvl(sum(zyybcbrc),0) as zyybcbrc,nvl(sum(zyybje),0) as zyybje, "
				+ " nvl(sum(mzswrc),0) as mzswrc,nvl(sum(mzswcbrc),0) as mzswcbrc,nvl(sum(mzswje),0) as mzswje,nvl(sum(mzybrc),0) as mzybrc,nvl(sum(mzybcbrc),0) as mzybcbrc,nvl(sum(mzybje),0) as mzybje, "
				+ " nvl(sum(ydswrc),0) as ydswrc,nvl(sum(ydswcbrc),0) as ydswcbrc,nvl(sum(ydswje),0) as ydswje,nvl(sum(ydybrc),0) as ydybrc,nvl(sum(ydybcbrc),0) as ydybcbrc,nvl(sum(ydybje),0) as ydybje "
				+ " from "
				+ " (select t.orgid , "
				+ " case when t.biz_type = '2' and t.person_type like '___1%' then t.pnum end as zyswrc, "
				+ " case when t.biz_type = '2' and t.person_type like '2__1%' then t.medicarenum end as zyswcbrc, "
				+ " case when t.biz_type = '2' and t.person_type like '___1%' then t.assist end as zyswje, "
				+ " case when t.biz_type = '2' and t.person_type like '___0%' then t.pnum end as   zyybrc, "
				+ " case when t.biz_type = '2' and t.person_type like '2__0%' then t.medicarenum end as   zyybcbrc, "
				+ " case when t.biz_type = '2' and t.person_type like '___0%' then t.assist end as zyybje, "
				+ " case when t.biz_type = '1' and t.person_type like '___0%' then t.pnum end as mzybrc, "
				+ " case when t.biz_type = '1' and t.person_type like '2__0%' then t.medicarenum end as mzybcbrc, "
				+ " case when t.biz_type = '1' and t.person_type like '___0%' then t.assist end as mzybje, "
				+ " case when t.biz_type = '1' and t.person_type like '___1%' then t.pnum end as mzswrc, "
				+ " case when t.biz_type = '1' and t.person_type like '2__1%' then t.medicarenum end as mzswcbrc, "
				+ " case when t.biz_type = '1' and t.person_type like '___1%' then t.assist end as mzswje, "
				+ " case when t.biz_type = '3' and t.person_type like '___0%' then t.pnum end as ydybrc, "
				+ " case when t.biz_type = '3' and t.person_type like '2__0%' then t.medicarenum end as ydybcbrc, "
				+ " case when t.biz_type = '3' and t.person_type like '___0%' then t.assist end as ydybje, "
				+ " case when t.biz_type = '3' and t.person_type like '___1%' then t.pnum end as ydswrc, "
				+ " case when t.biz_type = '3' and t.person_type like '2__1%' then t.medicarenum end as ydswcbrc, "
				+ " case when t.biz_type = '3' and t.person_type like '___1%' then t.assist end as ydswje "
				+ " from "
				//修改
				+ " (select substr(tb.org, 0, 6) orgid,tb.biztype as biz_type,tb.persontype as person_type,count(tb.bizid) as pnum,sum(decode(tb.medicaretype, '2', 1, 0)) as medicarenum,sum(tb.payassist) as assist "
				+ " from ( "
				+ " select org.organization_id as org,biz.biz_id as bizid,biz.biz_type as biztype,biz.person_type || biz.person_typex as persontype,biz.medicare_type as medicaretype,pay.pay_assist as payassist,pay.oper_time "
				+ " from jz_biz biz "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where  biz.member_type = '2' "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ " and biz.assist_flag='1' "
				+ "  union all "
				+ " select ma.organization_id as org,ma.biz_id,ma.assist_type as biztype,ma.assist_type_m || decode(ma.assist_typex,null,'0',substr(ma.assist_typex,0,1)) as persontype,ma.medicare_type as medicaretype,ma.pay_assist as payassist,ma.oper_time "
				+ " from jz_medicalafter ma "
				+ " where ma.biz_status = '1' "
				+ " and ma.member_type = '2' "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ " ) tb where 1=1 "
				/*+ " (select substr(org.organization_id,0,6) orgid,biz.biz_type,biz.person_type,count(biz.biz_id) as pnum,sum(decode(biz.medicare_type,'2',1,0)) as medicarenum,sum(pay.pay_assist) as assist "
				+ " from jz_biz biz "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where  biz.member_type = '2' "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ " and biz.assist_flag='1' "*/
				+ jwhere
				+ " group by substr(tb.org, 0, 6),tb.biztype, tb.persontype )t) tt "
				/*+ " group by substr(org.organization_id,0,6),biz.biz_type,biz.person_type) t) tt "*/
				+ "  inner join manager_org org1 on org1.organization_id = tt.orgid "
				+ "  inner join manager_org org2 on org1.parentorgid = org2.organization_id "
				+ " group by org1.orgname ,org2.orgname ,org1.organization_id ,org2.organization_id "
				+ " order by org1.organization_id ";
		data = reportService.findduixiang(sql);
		map = new HashMap();
		map.put("month", month);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	public String feiyongcondition() {
		return SUCCESS;
	}

	/**
	 * 费用分析-农住院
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String feiyongnczhuyuan() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + "and to_char(pay.oper_time,'yyyy-Q') = '" + quarter + "'";
		}
		String sql = "select rownum,t.* from "
				+ " (select case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end as subsection, "
				+ " count(pay.biz_id) as pnum,nvl(sum(pay.pay_total),0) as total ,nvl(sum(pay.pay_outmedicare),0) as outmedicare, "
				+ " nvl(sum(pay.pay_medicare),0) as medicare,nvl(sum(pay.pay_assist),0) as assist, "
				+ " nvl(sum(pay.pay_self),0) as self "
				+ " from " 
				//+ " jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " (select biz.biz_id,pay.pay_total,pay.pay_medicare,pay.pay_outmedicare,pay.pay_assist,pay.pay_self,pay.oper_time from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_type = '2' "
				+ " and biz.assist_flag = '1' "
				+ " and biz.member_type = '2' "
				+ " and pay.pay_total >0 "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				//修改
				+ " union "
				+ " select ma.biz_id,ma.pay_total,ma.pay_medicare,ma.pay_outmedicare,ma.pay_assist, "
				+ " nvl(ma.pay_total-ma.pay_medicare-ma.pay_assist-ma.pay_ciassist, 0) as pay_self, ma.oper_time "
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '2' and ma.member_type = '2' "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ ") pay where 1=1 "
				+ jwhere
				+ " group by case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end order by CAST(substr(subsection,0,INSTR(subsection,'-',1,1)-1) as int) ) t ";
		data = reportService.findfeiyongcszhuyuan(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		map.put("organizationname", organizationname);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 费用分析-城住院
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String feiyongcszhuyuan() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + "and to_char(pay.oper_time,'yyyy-Q') = '" + quarter + "'";
		}
		String sql = "select rownum,t.* from "
				+ " (select case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end as subsection, "
				+ " count(pay.biz_id) as pnum,nvl(sum(pay.pay_total),0) as total ,nvl(sum(pay.pay_outmedicare),0) as outmedicare, "
				+ " nvl(sum(pay.pay_medicare),0) as medicare,nvl(sum(pay.pay_assist),0) as assist, "
				+ " nvl(sum(pay.pay_self),0) as self "
				/*+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_type = '2' "
				+ " and biz.assist_flag = '1' "
				+ " and biz.member_type = '1' "
				+ " and pay.pay_total >0 "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId*/
				+ " from " 
				+ " (select biz.biz_id,pay.pay_total,pay.pay_medicare,pay.pay_outmedicare,pay.pay_assist,pay.pay_self,pay.oper_time from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_type = '2' "
				+ " and biz.assist_flag = '1' "
				+ " and biz.member_type = '1' "
				+ " and pay.pay_total >0 "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				//修改
				+ " union "
				+ " select ma.biz_id,ma.pay_total,ma.pay_medicare,ma.pay_outmedicare,ma.pay_assist, "
				+ " nvl(ma.pay_total-ma.pay_medicare-ma.pay_assist-ma.pay_ciassist, 0) as pay_self, ma.oper_time "
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '2' and ma.member_type = '1' "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ ") pay where 1=1 "
				+ jwhere
				+ " group by case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end order by CAST(substr(subsection,0,INSTR(subsection,'-',1,1)-1) as int) ) t ";
		data = reportService.findfeiyongcszhuyuan(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		map.put("organizationname", organizationname);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 
	 * 费用分析-农门诊
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String feiyongncmenzhen() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + "and to_char(pay.oper_time,'yyyy-Q') = '" + quarter + "'";
		}
		String sql = "select rownum,t.* from "
				+ " (select case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end as subsection, "
				+ " count(pay.biz_id) as pnum,nvl(sum(pay.pay_total),0) as total ,nvl(sum(pay.pay_outmedicare),0) as outmedicare, "
				+ " nvl(sum(pay.pay_medicare),0) as medicare,nvl(sum(pay.pay_assist),0) as assist, "
				+ " nvl(sum(pay.pay_self),0) as self "
				/*+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_type = '1' "
				+ " and biz.assist_flag = '1' "
				+ " and biz.member_type = '1' "
				+ " and pay.pay_total >0 "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId*/
				+ " from " 
				+ " (select biz.biz_id,pay.pay_total,pay.pay_medicare,pay.pay_outmedicare,pay.pay_assist,pay.pay_self,pay.oper_time from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_type = '1' "
				+ " and biz.assist_flag = '1' "
				+ " and biz.member_type = '2' "
				+ " and pay.pay_total >0 "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				//修改
				+ " union "
				+ " select ma.biz_id,ma.pay_total,ma.pay_medicare,ma.pay_outmedicare,ma.pay_assist, "
				+ " nvl(ma.pay_total-ma.pay_medicare-ma.pay_assist-ma.pay_ciassist, 0) as pay_self, ma.oper_time "
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '1' and ma.member_type = '2' "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ ") pay where 1=1 "
				+ jwhere
				+ " group by case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end order by CAST(substr(subsection,0,INSTR(subsection,'-',1,1)-1) as int) ) t ";
		data = reportService.findfeiyongcszhuyuan(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		map.put("organizationname", organizationname);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 费用分析-城门诊
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String feiyongcsmenzhen() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + "and to_char(pay.oper_time,'yyyy-Q') = '" + quarter + "'";
		}
		String sql = "select rownum,t.* from "
				+ " (select case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end as subsection, "
				+ " count(pay.biz_id) as pnum,nvl(sum(pay.pay_total),0) as total ,nvl(sum(pay.pay_outmedicare),0) as outmedicare, "
				+ " nvl(sum(pay.pay_medicare),0) as medicare,nvl(sum(pay.pay_assist),0) as assist, "
				+ " nvl(sum(pay.pay_self),0) as self "
				/*+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_type = '1' "
				+ " and biz.assist_flag = '1' "
				+ " and biz.member_type = '1' "
				+ " and pay.pay_total >0 "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId*/
				+ " from " 
				+ " (select biz.biz_id,pay.pay_total,pay.pay_medicare,pay.pay_outmedicare,pay.pay_assist,pay.pay_self,pay.oper_time from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " where biz.biz_type = '1' "
				+ " and biz.assist_flag = '1' "
				+ " and biz.member_type = '1' "
				+ " and pay.pay_total >0 "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				//修改
				+ " union "
				+ " select ma.biz_id,ma.pay_total,ma.pay_medicare,ma.pay_outmedicare,ma.pay_assist, "
				+ " nvl(ma.pay_total-ma.pay_medicare-ma.pay_assist-ma.pay_ciassist, 0) as pay_self, ma.oper_time "
				+ " from jz_medicalafter ma where ma.biz_status = '1' and ma.assist_type = '1' and ma.member_type = '1' "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ ") pay where 1=1 "
				+ jwhere
				+ " group by case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end order by CAST(substr(subsection,0,INSTR(subsection,'-',1,1)-1) as int)) t ";
		data = reportService.findfeiyongcszhuyuan(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		map.put("organizationname", organizationname);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 费用分析-农临时
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String feiyongnclinshi() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + "and to_char(pay.approvetime,'yyyy-Q') = '" + quarter + "'";
		}
		String sql = "select rownum,t.* from "
				+ " (select case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end as subsection, "
				+ " count(pay.approve_id) as pnum,nvl(sum(pay.pay_total),0) as total ,nvl(sum(pay.pay_outmedicare),0) as outmedicare, "
				+ " nvl(sum(pay.pay_medicare),0) as medicare,nvl(sum(pay.pay_assist),0) as assist, "
				+ " nvl(sum(pay.pay_total-pay.pay_medicare-pay.pay_assist),0) as self "
				+ " from temp_approve pay inner join temp_person tp on pay.member_id = tp.member_id "
				+ " where pay.approvests = '2' "
				+ " and tp.member_type = '2' "
				+ " and pay.pay_total >0 "
				+ " and substr(tp.organizationid,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ jwhere
				+ " group by case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end order by CAST(substr(subsection,0,INSTR(subsection,'-',1,1)-1) as int) ) t ";
		data = reportService.findfeiyongcszhuyuan(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		map.put("organizationname", organizationname);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 费用分析-城临时
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String feiyongcslinshi() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + "and to_char(pay.approvetime,'yyyy-Q') = '" + quarter + "'";
		}
		String sql = "select rownum,t.* from "
				+ " (select case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end as subsection, "
				+ " count(pay.approve_id) as pnum,nvl(sum(pay.pay_total),0) as total ,nvl(sum(pay.pay_outmedicare),0) as outmedicare, "
				+ " nvl(sum(pay.pay_medicare),0) as medicare,nvl(sum(pay.pay_assist),0) as assist, "
				+ " nvl(sum(pay.pay_total-pay.pay_medicare-pay.pay_assist),0) as self "
				+ " from temp_approve pay inner join temp_person tp on pay.member_id = tp.member_id "
				+ " where pay.approvests = '2' "
				+ " and tp.member_type = '1' "
				+ " and pay.pay_total >0 "
				+ " and substr(tp.organizationid,0,"
				+ orgLen
				+ ")= "
				+ organizationId
				+ jwhere
				+ " group by case when pay.pay_total between 0 and 1000 then '1-1000' "
				+ " when pay.pay_total between 1000.01 and 2000 then '1001-2000' "
				+ " when pay.pay_total between 2000.01 and 3000 then '2001-3000' "
				+ " when pay.pay_total between 3000.01 and 4000 then '3001-4000' "
				+ " when pay.pay_total between 4000.01 and 5000 then '4001-5000' "
				+ " when pay.pay_total between 5000.01 and 6000 then '5001-6000' "
				+ " when pay.pay_total between 6000.01 and 7000 then '6001-7000' "
				+ " when pay.pay_total between 7000.01 and 8000 then '7001-8000' "
				+ " when pay.pay_total between 8000.01 and 9000 then '8001-9000' "
				+ " when pay.pay_total between 9000.01 and 10000 then '9001-10000' "
				+ " when pay.pay_total between 10000.01 and 20000 then '10001-20000' "
				+ " when pay.pay_total between 20000.01 and 30000 then '20001-30000' "
				+ " when pay.pay_total between 30000.01 and 40000 then '30001-40000' "
				+ " when pay.pay_total between 40000.01 and 50000 then '40001-50000' "
				+ " when pay.pay_total >= 50000.01 then '>50000' end order by CAST(substr(subsection,0,INSTR(subsection,'-',1,1)-1) as int) ) t ";
		data = reportService.findfeiyongcszhuyuan(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		map.put("organizationname", organizationname);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 机构 省市级
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String jigouss() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-Q') = '" + quarter + "'";
		}
		String sql = "select tt.fullname,tt.orgname,tt.organization_id,tt.parentorgname, "
				+ " nvl(sum(pnum3),0) as pnum3,nvl(sum(pnum2),0) as pnum2,nvl(sum(pnum1),0) as pnum1,nvl(sum(pnum0),0) as pnum0, "
				+ "  nvl(sum(total3),0) as total3,nvl(sum(total2),0) as total2,nvl(sum(total1),0) as total1,nvl(sum(total0),0) as total0, "
				+ "  nvl(sum(outmedicare3),0) as outmedicare3,nvl(sum(outmedicare2),0) as outmedicare2,nvl(sum(outmedicare1),0) as outmedicare1,nvl(sum(outmedicare0),0) as outmedicare0, "
				+ "  nvl(sum(medicare3),0) as medicare3,nvl(sum(medicare2),0) as medicare2,nvl(sum(medicare1),0) as medicare1,nvl(sum(medicare0),0) as medicare0, "
				+ "  nvl(sum(assist3),0) as assist3,nvl(sum(assist2),0) as assist2,nvl(sum(assist1),0) as assist1,nvl(sum(assist0),0) as assist0, "
				+ "  nvl(sum(self3),0) as self3,nvl(sum(self2),0) as self2,nvl(sum(self1),0) as self1,nvl(sum(self0),0) as self0 "
				+ "  from " + " (select t.fullname,t.orgname,t.organization_id ,t.parentorgname, "
				+ " case when t.dept_level >=4 then t.pnum end as pnum3, "
				+ " case when t.dept_level =3 then t.pnum end as pnum2, "
				+ " case when t.dept_level =2 then t.pnum end as pnum1, "
				+ " case when t.dept_level =1 then t.pnum end as pnum0, "
				+ " case when t.dept_level >=4 then t.total end as total3, "
				+ " case when t.dept_level =3 then t.total end as total2, "
				+ " case when t.dept_level =2 then t.total end as total1, "
				+ " case when t.dept_level =1 then t.total end as total0, "
				+ " case when t.dept_level >=4 then t.outmedicare end as outmedicare3, "
				+ " case when t.dept_level =3 then t.outmedicare end as outmedicare2, "
				+ " case when t.dept_level =2 then t.outmedicare end as outmedicare1, "
				+ " case when t.dept_level =1 then t.outmedicare end as outmedicare0, "
				+ " case when t.dept_level >=4 then t.medicare end as medicare3, "
				+ " case when t.dept_level =3 then t.medicare end as medicare2, "
				+ " case when t.dept_level =2 then t.medicare end as medicare1, "
				+ " case when t.dept_level =1 then t.medicare end as medicare0, "
				+ " case when t.dept_level >=4 then t.assist end as assist3, "
				+ " case when t.dept_level =3 then t.assist end as assist2, "
				+ " case when t.dept_level =2 then t.assist end as assist1, "
				+ " case when t.dept_level =1 then t.assist end as assist0, "
				+ " case when t.dept_level >=4 then t.self end as self3, "
				+ " case when t.dept_level =3 then t.self end as self2, "
				+ " case when t.dept_level =2 then t.self end as self1, "
				+ " case when t.dept_level =1 then t.self end as self0 " + " from " + " (select count(biz.biz_id) as pnum, "
				+ " sum(pay.pay_total) as total, " + " sum(pay.pay_outmedicare) as outmedicare, "
				+ " sum(pay.pay_medicare) as medicare, " + " sum(pay.pay_assist) as assist, " + " sum(pay.pay_self) as self "
				+ " ,org.fullname,org.orgname,org2.orgname as parentorgname,org.organization_id " + " ,dept.dept_level "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " inner join manager_org org2 on org.parentorgid = org2.organization_id " + " where biz.assist_flag = '1' "
				+ " and substr(biz.family_no,0," + orgLen + ")= " + organizationId + jwhere
				+ " group by org.fullname,org.orgname,org2.orgname,org.organization_id,dept.dept_level) t) tt "
				+ " group by tt.fullname,tt.orgname,tt.organization_id,tt.parentorgname " + " order by organization_id";
		data = reportService.findjigou(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 机构 各级别
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String jigou() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-Q') = '" + quarter + "'";
			jwhere = jwhere + " and dept.dept_level = '" + level + "'";
		}
		String sql = "select rownum,t.* from ( " + " select biz.hospital_id,dept.name,"
				+ " case when dept.dept_level = '1' then '乡镇医院'" + "  when dept.dept_level = '2' then '区（县）医院'"
				+ "  when dept.dept_level = '3' then '市级医院'" + " when dept.dept_level >= '4' then '省级医院' end dept_level,"
				+ " count(biz.biz_id) as pnum, sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare,"
				+ " sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist,sum(pay.pay_self) as self"
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id"
				+ " inner join bizdept dept on biz.hospital_id = dept.hospital_id" + " where biz.assist_flag ='1'" + jwhere
				+ " group by biz.hospital_id,dept.name,case when dept.dept_level = '1' then '乡镇医院'"
				+ "  when dept.dept_level = '2' then '区（县）医院'" + "  when dept.dept_level = '3' then '市级医院'"
				+ " when dept.dept_level >= '4' then '省级医院' end) t ";
		data = reportService.findjigou(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		map.put("level", level);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 机构 县市 区
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String jigouxsq() {
		String jwhere = "";
		if (!(quarter == null || "".equals(quarter))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-Q') = '" + quarter + "'";
			jwhere = jwhere + " and org.organization_id like '" + city + "%'";
		}
		String sql = " select rownum,t.* from (  select biz.hospital_id,dept.name,org.fullname,org.organization_id, "
				+ " case when dept.dept_level = '1' then '乡镇医院' "
				+ " when dept.dept_level = '2' then '区（县）医院' when dept.dept_level = '3' "
				+ " then '市级医院' when dept.dept_level >= '4' then '省级医院' end dept_level, count(biz.biz_id) as pnum, sum(pay.pay_total) as total, "
				+ " sum(pay.pay_outmedicare) as outmedicare, sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist, "
				+ " sum(pay.pay_self) as self from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id  "
				+ " inner join bizdept dept on biz.hospital_id = dept.hospital_id  "
				+ " inner join manager_org org on org.organization_id = substr(dept.organization_id,0,6) "
				+ " where biz.assist_flag = '1'  "
				+ jwhere
				+ " group by biz.hospital_id,dept.name,case when dept.dept_level = '1' then '乡镇医院'  "
				+ " when dept.dept_level = '2' then '区（县）医院' when dept.dept_level = '3' then '市级医院' when dept.dept_level >= '4' then '省级医院' end,org.fullname,org.organization_id) t ";
		data = reportService.findjigou(sql);
		map = new HashMap();
		map.put("quarter", quarter);
		map.put("region", city);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 总体 城街
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String zongticj() {
		String jwhere = "";
		if (!(start_month == null || "".equals(start_month))) {
			jwhere = jwhere + " and to_char(ubiz.oper_time,'yyyy-mm') between '" + start_month + "' and '" + end_month + "'";
			jwhere = jwhere + " and org.organization_id like '" + city + "%'";
		}
		String sql = " select  org2.organization_id as parentorg,t.*,org2.orgname,m.pnum from "
				+ " (select sum(decode(ubiz.biz_type,'2',1,0)) as zhuyuanp, "
				+ " sum(decode(ubiz.biz_type,'2',ubiz.pay_assist,0)) as zhuyuanm, "
				+ " sum(decode(ubiz.biz_type,'1',1,0)) as menzhenp, "
				+ " sum(decode(ubiz.biz_type,'1',ubiz.pay_assist,0)) as menzhenm, "
				+ " sum(decode(ubiz.biz_type,'3',1,0)) as yaodianp, "
				+ " sum(decode(ubiz.biz_type,'3',ubiz.pay_assist,0)) as yaodianm, "
				+ " sum(decode(ubiz.biz_type,'4',1,0)) as linship, "
				+ " sum(decode(ubiz.biz_type,'4',ubiz.pay_assist,0)) as linshim, "
				+ " substr(ubiz.family_no,0,8) as region,org.orgname regionname "
				+ " from  (select biz.biz_id,  biz.biz_type,   biz.family_no, biz.assist_flag,  biz.member_type, biz.oper_time,  pay.pay_assist "
				+ "  from jz_biz biz " + " inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " union all "
                + " select ma.biz_id, ma.assist_type, ma.family_no, ma.biz_status, ma.member_type, ma.oper_time, ma.pay_assist "
                + " from jz_medicalafter ma where ma.biz_status = '1' "
				+ " union "
				+ " select app.approve_id, '4',tp.organizationid, '1',app.member_type,app.approvetime,app.pay_assist "
				+ " from temp_approve app " + " inner join temp_person tp on app.member_id = tp.member_id "
				+ " where app.approvests = '2') ubiz   "
				+ " inner join manager_org org on substr(ubiz.family_no, 0, 8) = org.organization_id "
				+ " where ubiz.member_type = '1' " + " and ubiz.assist_flag = '1' " + jwhere + " and substr(ubiz.family_no,0,"
				+ orgLen + ")= " + organizationId + " group by substr(ubiz.family_no, 0, 8), org.orgname) t "
				+ " inner join manager_org org1 on t.region = org1.organization_id "
				+ " inner join manager_org org2 on org1.parentorgid = org2.organization_id "
				+ " left join (select count(mem.member_id) pnum, " + " substr(mem.familyno, 0, 8) as familyno "
				+ " from MEMBER_BASEINFOVIEW02 mem " + " where mem.ds = '1' " + " and mem.a1 = '1' "
				+ " group by substr(mem.familyno, 0, 8)) m on m.familyno = t.region " + " order by org2.organization_id ";
		data = reportService.findjiben(sql); // 总体 与基本字段相同
		map = new HashMap();
		map.put("start_month", start_month);
		map.put("end_month", end_month);
		map.put("region", city); 
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 总体 农乡
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String zongtinx() {
		String jwhere = "";
		if (!(start_month == null || "".equals(start_month))) {
			jwhere = jwhere + " and to_char(ubiz.oper_time,'yyyy-mm') between '" + start_month + "' and '" + end_month + "'";
			jwhere = jwhere + " and org.organization_id like '" + city + "%'";
		}
		String sql = " select  org2.organization_id as parentorg,t.*,org2.orgname,m.pnum from "
				+ " (select sum(decode(ubiz.biz_type,'2',1,0)) as zhuyuanp, "
				+ " sum(decode(ubiz.biz_type,'2',ubiz.pay_assist,0)) as zhuyuanm, "
				+ " sum(decode(ubiz.biz_type,'1',1,0)) as menzhenp, "
				+ " sum(decode(ubiz.biz_type,'1',ubiz.pay_assist,0)) as menzhenm, "
				+ " sum(decode(ubiz.biz_type,'3',1,0)) as yaodianp, "
				+ " sum(decode(ubiz.biz_type,'3',ubiz.pay_assist,0)) as yaodianm, "
				+ " sum(decode(ubiz.biz_type,'4',1,0)) as linship, "
				+ " sum(decode(ubiz.biz_type,'4',ubiz.pay_assist,0)) as linshim, "
				+ " substr(ubiz.family_no,0,8) as region,org.orgname regionname "
				+ " from  (select biz.biz_id,  biz.biz_type,   biz.family_no, biz.assist_flag,  biz.member_type, biz.oper_time,  pay.pay_assist "
				+ "  from jz_biz biz " + " inner join jz_pay_view pay on biz.biz_id = pay.biz_id " 
				+ " union all "
                + " select ma.biz_id, ma.assist_type, ma.family_no, ma.biz_status, ma.member_type, ma.oper_time, ma.pay_assist "
                + " from jz_medicalafter ma where ma.biz_status = '1' "
				+ " union "
				+ " select app.approve_id, '4',tp.organizationid, '1',app.member_type,app.approvetime,app.pay_assist "
				+ " from temp_approve app " + " inner join temp_person tp on app.member_id = tp.member_id "
				+ " where app.approvests = '2') ubiz   "
				+ " inner join manager_org org on substr(ubiz.family_no, 0, 8) = org.organization_id "
				+ " where ubiz.member_type = '2' " + " and ubiz.assist_flag = '1' " + jwhere + " and substr(ubiz.family_no,0,"
				+ orgLen + ")= " + organizationId + " group by substr(ubiz.family_no, 0, 8), org.orgname) t "
				+ " inner join manager_org org1 on t.region = org1.organization_id "
				+ " inner join manager_org org2 on org1.parentorgid = org2.organization_id "
				+ " left join (select count(mem.member_id) pnum, " + " substr(mem.familyno, 0, 8) as familyno "
				+ " from MEMBER_BASEINFOVIEW02 mem " + " where  mem.ds='2' and substr(mem.assist_type,0,3) <> '000'  "
				+ " group by substr(mem.familyno, 0, 8)) m on m.familyno = t.region " + " order by org2.organization_id ";
		data = reportService.findjiben(sql); // 总体 与基本字段相同
		map = new HashMap();
		map.put("start_month", start_month);
		map.put("end_month", end_month);
		map.put("region", city);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 总体 城市
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String zongtics() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			if ("4".equals(type)) {
				jwhere = jwhere + " and to_char(app.approvetime,'yyyy-mm') = '" + month + "'";
				jwhere = jwhere + " and tp.organizationid like '" + street + "%'";
				String sqlstr = "select rownum,org.orgname,org.fullname,tp.membername as name ,tp.address as family_address ,tp.paperid as id_card , "
						+ " case    when mv.assist_type like '11%' then '分类施保' "
						+ " when mv.assist_type like '21%' then '重点户' "
						+ " when mv.assist_type like '0%' then '非保障对象' "
						+ " when mv.assist_type like '1%' then '城市低保' "
						+ " when mv.assist_type like '2%' then '农村低保' "
						+ " when mv.assist_type like '3%' then '五保供养' "
						+ " when mv.assist_type like '4%' then '重点优抚' "
						+ " when mv.assist_type like '5%' then '特殊困难' "
						+ " when mv.assist_type like '2%' then '农村低保' "
						+ " else '不详' "
						+ " end person_type,app.inhospitalsicken as diagnose_name,app.pay_total,app.pay_outmedicare,app.pay_medicare,app.pay_assist,app.pay_total - app.pay_medicare - app.pay_assist as pay_self"
						+ " from temp_approve app "
						+ " inner join temp_person tp on app.member_id = tp.member_id "
						+ " left join MEMBER_BASEINFOVIEW02 mv on mv.member_id = tp.member_id "
						+ " inner join manager_org org on org.organization_id = substr(tp.organizationid, 0, 8) "
						+ " where app.approvests = '2' " + jwhere + " and tp.member_type = '1'";
				data = reportService.findzongti(sqlstr);
			} else {
				jwhere = jwhere + " and to_char(ual.oper_time,'yyyy-mm') = '" + month + "'";
				jwhere = jwhere + " and ual.family_no like '" + street + "%'";
				jwhere = jwhere + " and ual.biz_type='" + type + "'";
				String sql = " select rownum,org.orgname,org.fullname,ual.name,ual.family_address,ual.id_card, "
						+ " case when ual.person_type like '11%'then '分类施保' " + " when ual.person_type like '21%'then '重点户' "
						+ " when ual.person_type like '0%' then '非保障对象' " + " when ual.person_type like '1%' then '城市低保' "
						+ " when ual.person_type like '2%' then '农村低保' " + " when ual.person_type like '3%' then '五保供养' "
						+ " when ual.person_type like '4%' then '重点优抚' " + " when ual.person_type like '5%' then '特殊困难' "
						+ " when ual.person_type like '2%' then '农村低保' " + " end person_type, " + " ual.diagnose_name, "
						+ " ual.pay_total,ual.pay_outmedicare,ual.pay_medicare,ual.pay_assist,ual.pay_self "
						+ " from ("
						+ " select biz.biz_id,biz.family_no,biz.name,biz.family_address,biz.id_card,biz.person_type,biz.diagnose_name,pay.pay_total,pay.pay_outmedicare, "
						+ " pay.pay_medicare,pay.pay_assist,pay.pay_self,biz.assist_flag,biz.oper_time,biz.member_type,biz.biz_type "
						+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
						+ " union all "
						+ " select af.biz_id, af.family_no, af.name, af.family_address, af.id_card, af.assist_type_m as person_type, af.diagnose_name, af.pay_total, af.pay_outmedicare,"
						+ " af.pay_medicare, af.pay_assist, (af.pay_total - af.pay_medicare - af.pay_assist) as pay_self, af.biz_status as assist_flag, af.oper_time, af.member_type, af.assist_type as biz_type "
						+ " from jz_medicalafter af where af.biz_status = '1' "
						+ " ) ual"
						+ " inner join manager_org org on org.organization_id = substr(ual.family_no,0,8) "
						+ " where ual.assist_flag = '1' " + jwhere + " and ual.member_type = '1' ";
				data = reportService.findzongti(sql);
			}
		}
		if ("1".equals(type)) {
			type = "特殊慢性病";
		} else if ("2".equals(type)) {
			type = "住院";
		} else if ("3".equals(type)) {
			type = "一般慢性病";
		} else if ("4".equals(type)) {
			type = "临时";
		}
		map = new HashMap();
		map.put("month", month);
		map.put("region", street);
		map.put("type", type);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 总体 农村
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String zongtinc() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			if ("4".equals(type)) {
				jwhere = jwhere + " and to_char(app.approvetime,'yyyy-mm') = '" + month + "'";
				jwhere = jwhere + " and tp.organizationid like '" + street + "%'";
				String sqlstr = "select rownum,org.orgname,org.fullname,tp.membername as name ,tp.address as family_address ,tp.paperid as id_card , "
						+ " case    when mv.assist_type like '11%' then '分类施保' "
						+ " when mv.assist_type like '21%' then '重点户' "
						+ " when mv.assist_type like '0%' then '非保障对象' "
						+ " when mv.assist_type like '1%' then '城市低保' "
						+ " when mv.assist_type like '2%' then '农村低保' "
						+ " when mv.assist_type like '3%' then '五保供养' "
						+ " when mv.assist_type like '4%' then '重点优抚' "
						+ " when mv.assist_type like '5%' then '特殊困难' "
						+ " when mv.assist_type like '2%' then '农村低保' "
						+ " else '不详' "
						+ " end person_type,app.inhospitalsicken as diagnose_name,app.pay_total,app.pay_outmedicare,app.pay_medicare,app.pay_assist,app.pay_total - app.pay_medicare - app.pay_assist as pay_self"
						+ " from temp_approve app "
						+ " inner join temp_person tp on app.member_id = tp.member_id "
						+ " left join MEMBER_BASEINFOVIEW02 mv on mv.member_id = tp.member_id "
						+ " inner join manager_org org on org.organization_id = substr(tp.organizationid, 0, 8) "
						+ " where app.approvests = '2' " + jwhere + " and tp.member_type = '2'";
				data = reportService.findzongti(sqlstr);
			} else {
				jwhere = jwhere + " and to_char(ual.oper_time,'yyyy-mm') = '" + month + "'";
				jwhere = jwhere + " and ual.family_no like '" + street + "%'";
				jwhere = jwhere + " and ual.biz_type='" + type + "'";
				String sql = " select rownum,org.orgname,org.fullname,ual.name,ual.family_address,ual.id_card, "
						+ " case when ual.person_type like '11%'then '分类施保' " + " when ual.person_type like '21%'then '重点户' "
						+ " when ual.person_type like '0%' then '非保障对象' " + " when ual.person_type like '1%' then '城市低保' "
						+ " when ual.person_type like '2%' then '农村低保' " + " when ual.person_type like '3%' then '五保供养' "
						+ " when ual.person_type like '4%' then '重点优抚' " + " when ual.person_type like '5%' then '特殊困难' "
						+ " when ual.person_type like '2%' then '农村低保' " + " end person_type, " + " ual.diagnose_name, "
						+ " ual.pay_total,ual.pay_outmedicare,ual.pay_medicare,ual.pay_assist,ual.pay_self "
						+ " from ("
						+ " select biz.biz_id,biz.family_no,biz.name,biz.family_address,biz.id_card,biz.person_type,biz.diagnose_name,pay.pay_total,pay.pay_outmedicare, "
						+ " pay.pay_medicare,pay.pay_assist,pay.pay_self,biz.assist_flag,biz.oper_time,biz.member_type,biz.biz_type "
						+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
						+ " union all "
						+ " select af.biz_id, af.family_no, af.name, af.family_address, af.id_card, af.assist_type_m as person_type, af.diagnose_name, af.pay_total, af.pay_outmedicare,"
						+ " af.pay_medicare, af.pay_assist, (af.pay_total - af.pay_medicare - af.pay_assist) as pay_self, af.biz_status as assist_flag, af.oper_time, af.member_type, af.assist_type as biz_type "
						+ " from jz_medicalafter af where af.biz_status = '1' "
						+ " ) ual"
						+ " inner join manager_org org on org.organization_id = substr(ual.family_no,0,8) "
						+ " where ual.assist_flag = '1' " + jwhere + " and ual.member_type = '2' ";
				data = reportService.findzongti(sql);
			}
		}
		if ("1".equals(type)) {
			type = "特殊慢性病";
		} else if ("2".equals(type)) {
			type = "住院";
		} else if ("3".equals(type)) {
			type = "一般慢性病";
		} else if ("4".equals(type)) {
			type = "临时";
		}
		map = new HashMap();
		map.put("month", month);
		map.put("region", street);
		map.put("type", type);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 病种 城市
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bingzhongcs() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
		}
		String sql = " select rownum, t.* from( "
				+ " select count(biz.biz_id) as pnum,biz.diagnose_name as name, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare,sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist,sum(pay.pay_self) as self,nvl(sum(biz.istop),0) as top, "
				+ " sum(decode(dept.dept_level,4,1,0)) as level4, " + " sum(decode(dept.dept_level,3,1,0)) as level3, "
				+ " sum(decode(dept.dept_level,2,1,0)) as level2, " + " sum(decode(dept.dept_level,1,1,0)) as level1 "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id " + " where biz.assist_flag='1' " + jwhere
				+ " and biz.member_type = '1' " + " group by biz.diagnose_name) t ";
		data = reportService.findbingzhong(sql);
		map = new HashMap();
		map.put("month", month);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 病种农村
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bingzhongnc() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
		}
		String sql = " select rownum, t.* from( "
				+ " select count(biz.biz_id) as pnum,biz.diagnose_name as name, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare,sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist,sum(pay.pay_self) as self,nvl(sum(biz.istop),0) as top, "
				+ " sum(decode(dept.dept_level,4,1,0)) as level4, " + " sum(decode(dept.dept_level,3,1,0)) as level3, "
				+ " sum(decode(dept.dept_level,2,1,0)) as level2, " + " sum(decode(dept.dept_level,1,1,0)) as level1 "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id " + " where biz.assist_flag='1' " + jwhere
				+ " and biz.member_type = '2' " + " group by biz.diagnose_name) t ";
		data = reportService.findbingzhong(sql);
		map = new HashMap();
		map.put("month", month);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 病种 城市单城
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bingzhongcscity() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
			jwhere = jwhere + " and biz.family_no like  '" + city + "%'";
		}
		String sql = " select rownum, t.* from( "
				+ " select count(biz.biz_id) as pnum,biz.diagnose_name as name, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare,sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist,sum(pay.pay_self) as self,nvl(sum(biz.istop),0) as top, "
				+ " sum(decode(dept.dept_level,4,1,0)) as level4, " + " sum(decode(dept.dept_level,3,1,0)) as level3, "
				+ " sum(decode(dept.dept_level,2,1,0)) as level2, " + " sum(decode(dept.dept_level,1,1,0)) as level1, "
				+ " org.fullname " + " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " where biz.assist_flag='1' " + jwhere + " and biz.member_type = '1' "
				+ " group by biz.diagnose_name,org.fullname) t";
		data = reportService.findbingzhong(sql);
		map = new HashMap();
		map.put("month", month);
		map.put("regioin", region);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 病种农村 单城
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bingzhongnccity() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
			jwhere = jwhere + " and biz.family_no like  '" + city + "%'";
		}
		String sql = " select rownum, t.* from( "
				+ " select count(biz.biz_id) as pnum,biz.diagnose_name as name, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare,sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist,sum(pay.pay_self) as self,nvl(sum(biz.istop),0) as top, "
				+ " sum(decode(dept.dept_level,4,1,0)) as level4, " + " sum(decode(dept.dept_level,3,1,0)) as level3, "
				+ " sum(decode(dept.dept_level,2,1,0)) as level2, " + " sum(decode(dept.dept_level,1,1,0)) as level1, "
				+ " org.fullname " + " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " where biz.assist_flag='1' " + jwhere + " and biz.member_type = '2' "
				+ " group by biz.diagnose_name,org.fullname) t";
		data = reportService.findbingzhong(sql);
		map = new HashMap();
		map.put("month", month);
		map.put("regioin", region);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	public List<PayDTO> getPaylist() {
		return paylist;
	}

	public void setPaylist(List<PayDTO> paylist) {
		this.paylist = paylist;
	}

	/**
	 * 病种 城市单病
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bingzhongcssick() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
			jwhere = jwhere + " and biz.diagnose_name =  '" + diagnose + "'";
		}
		String sql = " select rownum,org2.orgname,org2.organization_id as parentorg,t.* from( "
				+ " select count(biz.biz_id) as pnum,org.orgname as regionname,org.organization_id as region, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare, "
				+ " sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist, "
				+ " sum(pay.pay_self) as self,nvl(sum(biz.istop),0) as top, " + " sum(decode(dept.dept_level,4,1,0)) as level4, "
				+ " sum(decode(dept.dept_level,3,1,0)) as level3, " + " sum(decode(dept.dept_level,2,1,0)) as level2, "
				+ " sum(decode(dept.dept_level,1,1,0)) as level1 "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " where biz.assist_flag='1' " + jwhere + " and biz.member_type = '1' " + " and substr(biz.family_no,0,"
				+ orgLen + ")= " + organizationId + " group by org.organization_id,org.orgname order by region) t "
				+ " inner join manager_org org1 on t.region = org1.organization_id "
				+ " inner join manager_org org2 on org1.parentorgid = org2.organization_id ";
		data = reportService.findbingzhong(sql);
		map = new HashMap();
		map.put("month", month);
		map.put("diagnose", diagnose);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 病种 农村单病
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String bingzhongncsick() {
		String jwhere = "";
		if (!(month == null || "".equals(month))) {
			jwhere = jwhere + " and to_char(biz.oper_time,'yyyy-mm') = '" + month + "'";
			jwhere = jwhere + " and biz.diagnose_name =  '" + diagnose + "'";
		}
		String sql = " select rownum,org2.orgname,org2.organization_id as parentorg,t.* from( "
				+ " select count(biz.biz_id) as pnum,org.orgname as regionname,org.organization_id as region, "
				+ " sum(pay.pay_total) as total,sum(pay.pay_outmedicare) as outmedicare, "
				+ " sum(pay.pay_medicare) as medicare,sum(pay.pay_assist) as assist, "
				+ " sum(pay.pay_self) as self,nvl(sum(biz.istop),0) as top, " + " sum(decode(dept.dept_level,4,1,0)) as level4, "
				+ " sum(decode(dept.dept_level,3,1,0)) as level3, " + " sum(decode(dept.dept_level,2,1,0)) as level2, "
				+ " sum(decode(dept.dept_level,1,1,0)) as level1 "
				+ " from jz_biz biz inner join jz_pay_view pay on biz.biz_id = pay.biz_id "
				+ " inner join BizDept dept on biz.hospital_id = dept.hospital_id "
				+ " inner join manager_org org on substr(biz.family_no,0,6) = org.organization_id "
				+ " where biz.assist_flag='1' " + jwhere + " and biz.member_type = '2' " + " and substr(biz.family_no,0,"
				+ orgLen + ")= " + organizationId + " group by org.organization_id,org.orgname order by region) t "
				+ " inner join manager_org org1 on t.region = org1.organization_id "
				+ " inner join manager_org org2 on org1.parentorgid = org2.organization_id ";
		data = reportService.findbingzhong(sql);
		map = new HashMap();
		map.put("month", month);
		map.put("diagnose", diagnose);
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}
	
	
	/**
	 * 医疗机构调查表
	 * @param quarter
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String yiliaojigou() {
		String jwhere = "";
		jwhere = jwhere + " and t.organization_id like '" + city + "%'";
		
		String sql = " select rownum,t.name, "
					+" decode(t.dept_type, 1, '县(区)医院', 2, '市级医院', 3, '省级医院') as lev, "
					+" decode(t.status, 0, '×', 1, '√') as flag0,"
					+" '√' as flag1,"
					+" '√' as flag2,"
					+" '√' as flag3,"
					+" '√' as flag4 "
					+" from bizdept t "
					+" where t.dept_type = '1' "  + jwhere ;
		data = reportService.findyiliaojigou(sql);
		map = new HashMap();
		map.put("region", systemDataService.findOrganziation(city).getOrgname());
		if (data == null || data.size() == 0) {
			return "nodata";
		} else {
			return SUCCESS;
		}
	}
	
	public String analyseInpatientCostInit(){
		
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String analyseInpatientCost(){
		Map session = ActionContext.getContext().getSession();
		String jwhere = "";
		if(!"".equals(membertype)&& !(null==membertype)){
			jwhere = jwhere + " and bpay.member_type = '" + membertype + "' "; 
		}
		if("".equals(opttime1from) && "".equals(opttime1to)){
		}else{
			jwhere = jwhere + " and  exists ( select 1 from payview02 av where av.biztype in ('ma', 'biz') and av.member_id = bpay.member_id and av.member_type = bpay.member_type " ;
			if(!"".equals(opttime1from) && !"".equals(opttime1to)){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date optFromDate = format.parse(opttime1from);
					Date optToDate = format.parse(opttime1to);
					if(optFromDate.after(optToDate)){
						jwhere = jwhere + " and av.oper_time BETWEEN TO_DATE('" + opttime1to + " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime1from + " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}else if(optFromDate.before(optToDate)|| optFromDate.equals(optToDate)){
						jwhere = jwhere + " and av.oper_time BETWEEN TO_DATE('" + opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime1to + " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}
				}catch(ParseException  e){
					e.printStackTrace();
				}
			}else if(!"".equals(opttime1from) && "".equals(opttime1to)){
				jwhere = jwhere + " and av.oper_time < TO_DATE('" + opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}else if("".equals(opttime1from) && !"".equals(opttime1to)){
				jwhere = jwhere + " and av.oper_time > TO_DATE('" + opttime1to + " 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			jwhere = jwhere + ") ";
		}
		String sql= " select rownum, t.* from (select case "
				+" when apay.total between 0 and 1000 then          "  
                +"  '0--1000'                                           "  
                +" when apay.total between 1000.01 and 3000 then          "  
                +"  '1000--3000'                                           "  
                +" when apay.total between 3000.01 and 5000 then          "  
                +"  '3000--5000'                                           "  
                +" when apay.total between 5000.01 and 10000 then   "  
                +"  '5000--10000'                                       "  
                +" when apay.total between 10000.01 and 20000 then  "  
                +"  '10000--20000'                                      "  
                +" when apay.total between 20000.01 and 30000 then  "  
                +"  '20000--30000'                                      "  
                +" when apay.total between 30000.01 and 40000 then  "  
                +"  '30000--40000'                                      "  
                +" when apay.total between 40000.01 and 50000 then  "  
                +"  '40000--50000'                                      "  
                +" when apay.total between 50000.01 and 60000 then  "  
                +"  '50000--60000'                                      "  
                +" when apay.total between 60000.01 and 70000 then  "  
                +"  '60000--70000'                                      "  
                +" when apay.total between 70000.01 and 80000 then  "  
                +"  '70000--80000'                                      "  
                +" when apay.total between 80000.01 and 90000 then  "  
                +"  '80000--90000'                                      "  
                +" when apay.total between 90000.01 and 100000 then "
                +"  '90000--100000'                                     "
                +" when apay.total between 100000.01 and 150000 then "
                +"  '100000--150000'                                     "
                +" when apay.total between 150000.01 and 200000 then "
                +"  '150000--200000'                                     "
                +" when apay.total between 200000.01 and 250000 then "
                +"  '200000--250000'                                     "
                +" when apay.total between 250000.01 and 300000 then "
                +"  '250000--300000'                                     "
                +" when apay.total >= 300000.01 then                "
                +"  '300000-->'                                       "
                +" end as subsection,                                  "
                +" count(apay.persum) as persum,      "
                +" nvl(sum(apay.total), 0) as total,                "
                +" nvl(sum(apay.outmedicare), 0) as outmedicare,    "
                +" nvl(sum(apay.medicare), 0) as medicare,          "
                +" nvl(sum(apay.assist), 0) as assist,              "
                +" nvl(sum(apay.self), 0) as self,                  "
                +" nvl(sum(apay.ciassist), 0) as ciassist           "
                +" from (select count(bpay.biz_id) as pnum,         "
                +" count(distinct(bpay.member_id || bpay.member_type)) as persum, "
                +" nvl(sum(bpay.pay_total), 0) as total, "
                +" nvl(sum(bpay.pay_outmedicare), 0) as outmedicare, "
                +" nvl(sum(bpay.pay_medicare), 0) as medicare, "
                +" nvl(sum(bpay.pay_assist), 0) as assist, "
                +" nvl(sum(bpay.pay_self), 0) as self, "
                +" nvl(sum(bpay.pay_ciassist), 0) as ciassist "
                +" from (select pay.biz_id, pay.pay_total, pay.pay_medicare, "
                +" pay.pay_outmedicare, pay.pay_assist, "
               /* +" decode(sign(pay.pay_total - pay.pay_medicare - pay.pay_assist - "
                +" pay.pay_ciassist),-1,0,nvl(pay.pay_total - pay.pay_medicare - pay.pay_assist - "
                +" pay.pay_ciassist, "
                +" 0))as pay_self, "*/
                +" nvl(pay.pay_total - pay.pay_medicare - pay.pay_assist - "
                +" pay.pay_ciassist, 0) as pay_self, "
                +" pay.id_card, "
                +" pay.pay_ciassist, pay.member_id, pay.member_type, pay.oper_time from payview02 pay "
                +" where pay.biztype in ('ma', 'biz')) bpay "
                +" where 1 = 1 " + jwhere
                +" group by bpay.member_id || bpay.member_type) apay "
                +" where 1 = 1 "
                +" group by case "
                +" when apay.total between 0 and 1000 then          "  
                +"  '0--1000'                                           "  
                +" when apay.total between 1000.01 and 3000 then          "  
                +"  '1000--3000'                                           "  
                +" when apay.total between 3000.01 and 5000 then          "  
                +"  '3000--5000'                                           "  
                +" when apay.total between 5000.01 and 10000 then   "  
                +"  '5000--10000'                                       "  
                +" when apay.total between 10000.01 and 20000 then  "  
                +"  '10000--20000'                                      "  
                +" when apay.total between 20000.01 and 30000 then  "  
                +"  '20000--30000'                                      "  
                +" when apay.total between 30000.01 and 40000 then  "  
                +"  '30000--40000'                                      "  
                +" when apay.total between 40000.01 and 50000 then  "  
                +"  '40000--50000'                                      "  
                +" when apay.total between 50000.01 and 60000 then  "  
                +"  '50000--60000'                                      "  
                +" when apay.total between 60000.01 and 70000 then  "  
                +"  '60000--70000'                                      "  
                +" when apay.total between 70000.01 and 80000 then  "  
                +"  '70000--80000'                                      "  
                +" when apay.total between 80000.01 and 90000 then  "  
                +"  '80000--90000'                                      "  
                +" when apay.total between 90000.01 and 100000 then "
                +"  '90000--100000'                                     "
                +" when apay.total between 100000.01 and 150000 then "
                +"  '100000--150000'                                     "
                +" when apay.total between 150000.01 and 200000 then "
                +"  '150000--200000'                                     "
                +" when apay.total between 200000.01 and 250000 then "
                +"  '200000--250000'                                     "
                +" when apay.total between 250000.01 and 300000 then "
                +"  '250000--300000'                                     "
                +" when apay.total >= 300000.01 then                "
                +"  '300000-->'                                       "
  				+" end "
  				+" order by CAST(substr(subsection, 0,"
  				+" INSTR(subsection, '-', 1, 1) - 1) as int)) t  ";
			paylist = reportService.findPayviews(sql);
			session.put("sql", sql);
			HashMap title = new HashMap();	
			//title.put("ROWNUM,val", "序号");	
			title.put("SUBSECTION,val", "医疗总费用范围");
			title.put("PERSUM,val", "人数");
			title.put("TOTAL,val", "总费用");						
			title.put("MEDICARE,val", "医保/农合报销");
			title.put("CIASSIST,val", "大病保险");
			title.put("ASSIST,val", "民政救助");
			title.put("SELF,val", "个人自理");
			title.put("OUTMEDICARE,val", "目录外费用");		
			session.put("title", title);	
		return SUCCESS;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryPayPersonsinfo(){
		Map session = ActionContext.getContext().getSession();
		String subsection = payDTO.getSubsection();
		String jwhere = "";
		String jwhere1 = "";
		String sql = "";
		if (null == cur_page || "".equals(cur_page)) {
		
			if(!"".equals(membertype)&& !(null==membertype)){
				jwhere1 = jwhere1 + " and bpay.member_type = '" + membertype + "' "; 
			}
			if("".equals(opttime1from) && "".equals(opttime1to)){
			}else{
				jwhere1 = jwhere1 + " and  exists ( select 1 from payview02 av where av.biztype in ('ma', 'biz') and av.member_id = bpay.member_id and av.member_type = bpay.member_type " ;
				if(!"".equals(opttime1from) && !"".equals(opttime1to)){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					try{
						Date optFromDate = format.parse(opttime1from);
						Date optToDate = format.parse(opttime1to);
						if(optFromDate.after(optToDate)){
							jwhere1 = jwhere1 + " and av.oper_time BETWEEN TO_DATE('" + opttime1to + " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
									+ " AND TO_DATE('" + opttime1from + " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
						}else if(optFromDate.before(optToDate)|| optFromDate.equals(optToDate)){
							jwhere1 = jwhere1 + " and av.oper_time BETWEEN TO_DATE('" + opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
									+ " AND TO_DATE('" + opttime1to + " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
						}
					}catch(ParseException  e){
						e.printStackTrace();
					}
				}else if(!"".equals(opttime1from) && "".equals(opttime1to)){
					jwhere1 = jwhere1 + " and av.oper_time < TO_DATE('" + opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
				}else if("".equals(opttime1from) && !"".equals(opttime1to)){
					jwhere1 = jwhere1 + " and av.oper_time > TO_DATE('" + opttime1to + " 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
				}
				jwhere1 = jwhere1 + ") ";
			}
		if(null==subsection&&"".equals(subsection)){
		}else{
			String subfrom = subsection.split("--")[0];
			String subto = subsection.split("--")[1];
			payDTO.setSubfrom(subfrom+".01");
			payDTO.setSubto(subto);
			if(">".equals(subto)){
				jwhere  = jwhere + " and apay.total >= " + payDTO.getSubfrom();
			} else {
				jwhere = jwhere + " and apay.total between " + payDTO.getSubfrom() + " and " + payDTO.getSubto();
			}
		}
		 sql= " select apay.*,base.membername,base.paperid,base.familyno "
					 + " from (select count(bpay.biz_id) as pnum, "
					 + " bpay.member_id as memberid,                      "
                     + " bpay.member_type as membertype,                  "
                     + " nvl(sum(bpay.pay_total), 0) as total,            "
                     + " nvl(sum(bpay.pay_outmedicare), 0) as outmedicare,"
                     + " nvl(sum(bpay.pay_medicare), 0) as medicare,      "
                     + " nvl(sum(bpay.pay_assist), 0) as assist,          "
                     + " nvl(sum(bpay.pay_self), 0) as self,              "
                     + " nvl(sum(bpay.pay_ciassist), 0) as ciassist       "
                     + " from (select pay.biz_id, "
					 + " pay.pay_total,                         "
                     + " pay.pay_medicare,                      "
                     + " pay.pay_outmedicare,                   "
                     + " pay.pay_assist,                        "
                     + " nvl(pay.pay_total - pay.pay_medicare - "
                     + "     pay.pay_assist - pay.pay_ciassist, "
                     + "     0) as pay_self,                    "
                     + " pay.id_card,                           "
                     + " pay.pay_ciassist,                      "
                     + " pay.member_id,                         "
                     + " pay.member_type,                       "
                     + " pay.oper_time                          "
                     + " from payview02 pay "
                     + " where pay.biztype in ('ma', 'biz')) bpay "
                     + " where 1 = 1 "+ jwhere1
                     + " group by bpay.member_id , bpay.member_type) apay left join member_baseinfoview02 base "
                     + " on apay.memberid = base.member_id and apay.membertype = base.ds "
                     + " where 1 = 1 " + jwhere ;
		session.put("sql", sql);
		HashMap title = new HashMap();	
		title.put("MEMBERNAME,val", "姓名");
		title.put("PAPERID,val", "身份证号码");
		title.put("FAMILYNO,val", "家庭编号");
		title.put("TOTAL,val", "总费用");						
		title.put("MEDICARE,val", "医保/农合报销");
		title.put("CIASSIST,val", "大病保险");
		title.put("ASSIST,val", "民政救助");
		title.put("SELF,val", "个人自理");
		title.put("OUTMEDICARE,val", "目录外费用");		
		session.put("title", title);	
		cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		paylist = reportService.findPayPersons(sql,new BigDecimal(
				cur_page).intValue(), "page/report/queryPayPersonsinfo.action?payDTO.subsection="+subsection
																			+"&opttime1from="+opttime1from
																			+"&opttime1to="+opttime1to
																			+"&membertype="+membertype);
		setToolsmenu(reportService.getToolsmenu());
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryAllpaysByPerinfo(){
		Map session = ActionContext.getContext().getSession();
		String sql = "";
		reportname = 
		sql = " select pay.*, nvl(pay.pay_total - pay.pay_medicare - pay.pay_assist - pay.pay_ciassist,0) as pay_self," 
				+ " base.membername, base.paperid, base.familyno " 
				+ " from "
				+ " ( select b.member_id, "
				+ " b.member_type,                                  "
			    + " b.id_card,                                      "
			    + " b.medicare_type,                                "
			    + " b.biz_id,                                       "
			    + " nvl(p.pay_total ,0) as pay_total,               "
			    + " nvl(p.pay_medicare ,0) as pay_medicare,         "
			    + " nvl( p.pay_outmedicare  ,0) as pay_outmedicare, "
			    + " nvl( p.pay_assist  ,0)  as pay_assist,          "
			    + " nvl(   p.pay_ciassist, 0)  as pay_ciassist,     "
			    + " p.oper_time,                                    "
			    + " 'biz' as biztype,                               "
			    + " b.hospitalname,                                 "
			    + " b.diagnose_name,                                "
			    + " b.begin_time,                                   "
			    + " b.end_time                                      "
			    + " from (select b.*,biz.name as hospitalname from jz_biz b "
			    + " left join bizdept biz on                            "
			    + " biz.hospital_id=b.hospital_id) b, jz_pay p            "
			    + " where b.biz_id = p.biz_id                              "
			    + " and b.biz_type = '2'                                 "
			    + " and b.biz_status = '1'                               "
			    + " and b.assist_flag = '1'                              "
			    + " and p.sts = '1'                                      "
			    + " union all "
			    + " select ma.member_id, "
				+ " ma.member_type,                               "
			    + " ma.id_card,                                   "
			    + " ma.medicare_type,                             "
			    + " ma.biz_id,                                    "
			    + " nvl(ma.pay_total,0) as pay_total,             "
			    + " nvl(ma.pay_medicare,0) as pay_medicare,       "
			    + " nvl(ma.pay_outmedicare,0) as pay_outmedicare, "
			    + " nvl(  ma.pay_assist,0) as pay_assist,         "
			    + " nvl(ma.pay_ciassist,0) as pay_ciassist,       "
			    + " ma.oper_time as oper_time,                    "
			    + " 'ma' as biztype,                              "
			    + " ma.hospital_name as hospitalname,             "
			    + " ma.diagnose_name,                             "
			    + " ma.begin_time,                                "
			    + " ma.end_time                                   "
			    + " from jz_medicalafter ma "
			    + " where  ma.assist_type='2' and ma.biz_status='1' ) "
				+ " pay "
				+ " left join member_baseinfoview02 base "
				+ " on base.member_id = pay.member_id "
				+ " and base.ds = pay.member_type "
				+ " where pay.member_id = '"+ payDTO.getMemberId() + "' and pay.member_type = '" + payDTO.getMembeType() + "'"
				+ " order by pay.oper_time desc ";
		paylist = reportService.findAllpaysByPer(sql);
		reportname = paylist.get(0).getName();
		session.put("sql", sql);
		HashMap title = new HashMap();	
		title.put("MEMBERNAME,val", "姓名");
		title.put("ID_CARD,val", "身份证号码");
		title.put("FAMILYNO,val", "家庭编号");
		title.put("HOSPITALNAME,val", "就诊医院");
		title.put("DIAGNOSE_NAME,val", "诊断");
		title.put("BEGIN_TIME,val", "入院时间");
		title.put("END_TIME,val", "出院时间");
		title.put("PAY_TOTAL,val", "总费用");						
		title.put("PAY_MEDICARE,val", "医保/农合报销");
		title.put("PAY_CIASSIST,val", "大病保险");
		title.put("PAY_ASSIST,val", "民政救助");
		title.put("PAY_SELF,val", "个人自理");
		title.put("PAY_OUTMEDICARE,val", "目录外费用");
		title.put("OPER_TIME,val","救助时间");
		session.put("title", title);	
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String specialInpatientCostInit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 8 == organizationId.length() 
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;

		} else {
			result = "此功能为区县、街道使用！";
			return "result";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String specialInpatientCost(){
		Map session = ActionContext.getContext().getSession();
		if(null==oid && "".equals(oid)){
			
		}else{
			orgLen = oid.length();
		}
		String jwhere = "";
		if("".equals(opttime1from) && "".equals(opttime1to)){
		}else{
			//jwhere = jwhere + " and bpay.member_id in( select av.member_id from payview02 av where av.biztype in ('ma', 'biz') " ;
			if(!"".equals(opttime1from) && !"".equals(opttime1to)){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date optFromDate = format.parse(opttime1from);
					Date optToDate = format.parse(opttime1to);
					if(optFromDate.after(optToDate)){
						jwhere = jwhere + " and payy.oper_time BETWEEN TO_DATE('" + opttime1to + " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime1from + " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}else if(optFromDate.before(optToDate)|| optFromDate.equals(optToDate)){
						jwhere = jwhere + " and payy.oper_time BETWEEN TO_DATE('" + opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime1to + " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}
				}catch(ParseException  e){
					e.printStackTrace();
				}
			}else if(!"".equals(opttime1from) && "".equals(opttime1to)){
				jwhere = jwhere + " and payy.oper_time < TO_DATE('" + opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}else if("".equals(opttime1from) && !"".equals(opttime1to)){
				jwhere = jwhere + " and payy.oper_time > TO_DATE('" + opttime1to + " 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			//jwhere = jwhere + ") ";
		}
		String sql=" select rownum, t.* from (select case "
                + " when apay.a3 = '1' then                      "
                + "  '城市\"三无\"住院患者'                      "
                + " when apay.a4 = '1' then                      "
                + "  '农村\"五保\"住院患者'                      "
                + " when apay.a6 = '1' then                      "
                + "  '城乡\"孤儿\"住院患者'                      "
                + " when apay.diagnose_name like '%尿毒症%' then "
                + "  '尿毒症患者'                                "
                + " when apay.diagnose_name like '%精神%' then "
                + "  '重性精神病患者'                            "
				+ " end as subsection,                            "
	            + " count(apay.pnum) as pnum,                     "
	            + " count(apay.persum) as persum,                 "
	            + " nvl(sum(apay.total), 0) as total,             "
	            + " nvl(sum(apay.outmedicare), 0) as outmedicare, "
	            + " nvl(sum(apay.medicare), 0) as medicare,       "
	            + " nvl(sum(apay.assist), 0) as assist,           "
	            + " nvl(sum(apay.self), 0) as self,               "
	            + " nvl(sum(apay.ciassist), 0) as ciassist        "
	            + " from (select count(payy.biz_id) as pnum,      "
	            + " count(distinct(payy.member_id || payy.member_type)) as persum, "
                + " nvl(sum(payy.pay_total), 0) as total,              "
                + " nvl(sum(payy.pay_outmedicare), 0) as outmedicare,  "
                + " nvl(sum(payy.pay_medicare), 0) as medicare,        "
                + " nvl(sum(payy.pay_assist), 0) as assist,            "
                + " nvl(sum(payy.pay_self), 0) as self,                "
                + " nvl(sum(payy.pay_ciassist), 0) as ciassist,        "
                + " payy.a3,                                           "
                + " payy.a4,                                           "
                + " payy.a6,                                           "
                + " payy.diagnose_name                                 "
                + " from (select biz.member_id, "
				+ " biz.member_type,                                   "
                + " biz.biz_id,                                        "
                + " pay.pay_total,                                     "
                + " pay.pay_medicare,                                  "
                + " pay.pay_outmedicare,                               "
                + " pay.pay_assist,                                    "
                + " nvl(pay.pay_total - pay.pay_medicare -             "
                + "  pay.pay_ciassist - pay.pay_assist, 0) as pay_self,"
                + " pay.pay_ciassist,                                  "
                + " pay.oper_time,                                     "
                + " biz.person_type || biz.person_typex as persontype, "
                + " substr(biz.person_type, 1, 1) as a1,               "
                + " substr(biz.person_type, 3, 1) as a3,               "
                + " substr(biz.person_type, 4, 1) as a4,               "
                + " substr(biz.person_type, 5, 1) as a5,               "
                + " substr(biz.person_typex, 1, 1) as a6,              "
                + " biz.diagnose_name                                  "
				+ " from jz_biz biz	,jz_pay pay 					   "
                + " where  biz.biz_id = pay.biz_id 					   " 
				+ " and biz.biz_type = '2'      					   "
                + " and regexp_instr(diagnose_name,'尿毒症')<=0        "
                + "  and biz.biz_status = '1' "
                + " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+ " union "
				+ " select biz.member_id, "
				+ " biz.member_type,                                   "
                + " biz.biz_id,                                        "
                + " pay.pay_total,                                     "
                + " pay.pay_medicare,                                  "
                + " pay.pay_outmedicare,                               "
                + " pay.pay_assist,                                    "
                + " nvl(pay.pay_total - pay.pay_medicare -             "
                + "  pay.pay_ciassist - pay.pay_assist, 0) as pay_self,"
                + " pay.pay_ciassist,                                  "
                + " pay.oper_time,                                     "
                + " biz.person_type || biz.person_typex as persontype, "
                + " substr(biz.person_type, 1, 1) as a1,               "
                + " substr(biz.person_type, 3, 1) as a3,               "
                + " substr(biz.person_type, 4, 1) as a4,               "
                + " substr(biz.person_type, 5, 1) as a5,               "
                + " substr(biz.person_typex, 1, 1) as a6,              "
                + " biz.diagnose_name                                  "
				+ " from jz_biz biz	,jz_pay pay 					   "
                + " where  biz.biz_id = pay.biz_id 					   " 
				+ " and biz.biz_type in ('1','2')  					   "
                + " and regexp_instr(diagnose_name,'尿毒症')>0         "
                + "  and biz.biz_status = '1' "
                + " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
                + " union "
                + " select ma.member_id, "
				+ " ma.member_type,                                     "
                + " ma.biz_id,                                          "
                + " ma.pay_total,                                       "
                + " ma.pay_medicare,                                    "
                + " ma.pay_outmedicare,                                 "
                + " ma.pay_assist,                                      "
                + " nvl(ma.pay_total - ma.pay_medicare -                "
                + " ma.pay_assist - ma.pay_ciassist,0) as pay_self,     "
                + " ma.pay_ciassist,                                    "
                + " ma.oper_time,                                       "
                + " base.assist_type || base.assist_typex as persontype,"
                + " substr(base.assist_type, 1, 1) as a1,               "
                + " substr(base.assist_type, 3, 1) as a3,               "
                + " substr(base.assist_type, 4, 1) as a4,               "
                + " substr(base.assist_type, 5, 1) as a5,               "
                + " substr(base.assist_typex, 1, 1) as a6,              "
                + " ma.diagnose_name                                    "
				+ " from jz_medicalafter ma  							"
                + " left join member_baseinfoview02 base  				"
                + "   on ma.member_id = base.member_id    				"
                + "  and ma.member_type = base.ds         				"
                + " where ma.biz_status = '1'                 			"
                + "  and ma.assist_type = '2'             		        "
                + "  and regexp_instr(diagnose_name,'尿毒症')<=0        "
                + " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+ " union "
	            + " select ma.member_id, "
	            + " ma.member_type,                                     "
	            + " ma.biz_id,                                          "
	            + " ma.pay_total,                                       "
	            + " ma.pay_medicare,                                    "
	            + " ma.pay_outmedicare,                                 "
	            + " ma.pay_assist,                                      "
	            + " nvl(ma.pay_total - ma.pay_medicare -                "
	            + " ma.pay_assist - ma.pay_ciassist,0) as pay_self,     "
	            + " ma.pay_ciassist,                                    "
	            + " ma.oper_time,                                       "
	            + " base.assist_type || base.assist_typex as persontype,"
	            + " substr(base.assist_type, 1, 1) as a1,               "
	            + " substr(base.assist_type, 3, 1) as a3,               "
	            + " substr(base.assist_type, 4, 1) as a4,               "
	            + " substr(base.assist_type, 5, 1) as a5,               "
	            + " substr(base.assist_typex, 1, 1) as a6,              "
	            + " ma.diagnose_name                                    "
	            + " from jz_medicalafter ma  							"
	            + " left join member_baseinfoview02 base  				"
	            + "   on ma.member_id = base.member_id    				"
	            + "  and ma.member_type = base.ds         				"
	            + " where ma.biz_status = '1'                 			"
	            + "  and ma.assist_type in ('1','2')         	        "
	            + "  and regexp_instr(diagnose_name,'尿毒症')>0         "
	            + " and substr(ma.family_no,0,"
	            + orgLen
	            + ")= "
				+ oid
                + " ) payy "
                + " where 1 = 1 " + jwhere
                + " group by payy.member_id || payy.member_type, payy.a3,payy.a4,payy.a6,payy.diagnose_name "
                + " ) apay "
                + " where 1 = 1 group by case "
                + " when apay.a3 = '1' then                      "
                + "  '城市\"三无\"住院患者'                      "
                + " when apay.a4 = '1' then                      "
                + "  '农村\"五保\"住院患者'                      "
                + " when apay.a6 = '1' then                      "
                + "  '城乡\"孤儿\"住院患者'                      "
                + " when apay.diagnose_name like '%尿毒症%' then "
                + "  '尿毒症患者'                                "
                + " when apay.diagnose_name like '%精神%' then "
                + "  '重性精神病患者'                            "
				+ " end ) t			                            "
                + " where t.subsection in ('城市\"三无\"住院患者','农村\"五保\"住院患者','城乡\"孤儿\"住院患者','尿毒症患者','重性精神病患者') ";
		paylist = reportService.findPayviews(sql);
		session.put("sql", sql);
		HashMap title = new HashMap();	
		//title.put("ROWNUM,val", "序号");	
		title.put("SUBSECTION,val", "特殊群体类别");
		title.put("PERSUM,val", "人数");
		title.put("TOTAL,val", "总费用");						
		title.put("MEDICARE,val", "医保/农合报销");
		title.put("CIASSIST,val", "大病保险");
		title.put("ASSIST,val", "民政救助");
		title.put("SELF,val", "个人自理");
		title.put("OUTMEDICARE,val", "目录外费用");		
		session.put("title", title);	
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 8 == organizationId.length() 
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
	return SUCCESS;
                
	}
	
	@SuppressWarnings("rawtypes")
	public String inhospitalCostInit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 8 == organizationId.length() 
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;

		} else {
			result = "此功能为区县、街道使用！";
			return "result";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String inhospitalCost(){
		Map session = ActionContext.getContext().getSession();
		if(null==oid && "".equals(oid)){
			
		}else{
			orgLen = oid.length();
		}
		String jwhere = "";
		
		
		if("".equals(opttime1from) && "".equals(opttime1to)){
		}else{
			//jwhere = jwhere + " and bpay.member_id in( select av.member_id from payview02 av where av.biztype in ('ma', 'biz') " ;
			if(!"".equals(opttime1from) && !"".equals(opttime1to)){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try{
					Date optFromDate = format.parse(opttime1from);
					Date optToDate = format.parse(opttime1to);
					if(optFromDate.after(optToDate)){
						jwhere = jwhere + " and payy.oper_time BETWEEN TO_DATE('" + opttime1to + " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime1from + " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}else if(optFromDate.before(optToDate)|| optFromDate.equals(optToDate)){
						jwhere = jwhere + " and payy.oper_time BETWEEN TO_DATE('" + opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') "
								+ " AND TO_DATE('" + opttime1to + " 23:59:59','yyyy-MM-dd hh24:mi:ss')";
					}
				}catch(ParseException  e){
					e.printStackTrace();
				}
			}else if(!"".equals(opttime1from) && "".equals(opttime1to)){
				jwhere = jwhere + " and payy.oper_time < TO_DATE('" + opttime1from + " 00:00:00','yyyy-MM-dd hh24:mi:ss') ";
			}else if("".equals(opttime1from) && !"".equals(opttime1to)){
				jwhere = jwhere + " and payy.oper_time > TO_DATE('" + opttime1to + " 23:59:59','yyyy-MM-dd hh24:mi:ss') ";
			}
			//jwhere = jwhere + ") ";
		}
		//合计：
		String sql="select a.org,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist from (select rownum, t.*"
				+" from (select case "
				+" when apay.a1 in ('1', '2') then "
				+" '普通低保' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') " 
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a1 in ('1', '2') then "
				+" '普通低保' "
				+" end, "
				+" apay.org "
				+"union "
				+"select case "
				+" when apay.a2 = '11' then "
				+" '城市分类施保' "
				+" when apay.a2 = '21' then "
				+" '农村重点户' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a2 = '11' then "
				+" '城市分类施保' "
				+" when apay.a2 = '21' then "
				+" '农村重点户' "
				+" end, "
				+" apay.org "
				+"union "
				+"select case "
				+" when apay.a3 = '1' then "
				+" '三无' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') " 
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a3 = '1' then "
				+" '三无' "
				+" end, "
				+" apay.org "
				+"union "
				+"select case "
				+" when apay.a4 = '1' then "
				+" '五保' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a4 = '1' then "
				+" '五保' "
				+" end, "
				+" apay.org "
				+"union "
				+"select case "
				+" when apay.a6 = '1' then "
				+" '孤儿' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a6 = '1' then "
				+" '孤儿' "
				+" end, "
				+" apay.org "
				+"union "
				+"select case "
				+" when apay.diagnose_name like '%尿毒症%' then "
				+" '尿毒症患者' "
				+" when apay.diagnose_name like '%精神%' then "
				+" '精神病患者' "
				+" when apay.diagnose_name like '%肺结核%' then "
				+" '肺结核患者' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.diagnose_name like '%尿毒症%' then "
				+" '尿毒症患者' "
				+" when apay.diagnose_name like '%精神%' then "
				+" '精神病患者' "
				+" when apay.diagnose_name like '%肺结核%' then "
				+" '肺结核患者' "
				+" end, "
				+" apay.org "
				+") t "
				+"where t.subsection in ('五保', "
				+" '三无', "
				+" '孤儿', "
				+" '尿毒症患者', "
				+" '精神病患者', "
				+" '肺结核患者', "
				+" '普通低保', "
				+" '城市分类施保', "
				+" '农村重点户') )a "
				+"left join manager_org org on a.org = org.organization_id "
				+"group by a.org ,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist order by a.org ";
		
		//纳入救助金额<2000：
		String sql01="select a.org,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist from (select rownum, t.*"
						+" from (select case "
						+" when apay.a1 in ('1', '2') then "
						+" '普通低保' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare)<2000 " 
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a1 in ('1', '2') then "
						+" '普通低保' "
						+" end, "
						+" apay.org "
						+"union "
						+"select case "
						+" when apay.a2 = '11' then "
						+" '城市分类施保' "
						+" when apay.a2 = '21' then "
						+" '农村重点户' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a2 = '11' then "
						+" '城市分类施保' "
						+" when apay.a2 = '21' then "
						+" '农村重点户' "
						+" end, "
						+" apay.org "
						+"union "
						+"select case "
						+" when apay.a3 = '1' then "
						+" '三无' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a3 = '1' then "
						+" '三无' "
						+" end, "
						+" apay.org "
						+"union "
						+"select case "
						+" when apay.a4 = '1' then "
						+" '五保' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a4 = '1' then "
						+" '五保' "
						+" end, "
						+" apay.org "
						+"union "
						+"select case "
						+" when apay.a6 = '1' then "
						+" '孤儿' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a6 = '1' then "
						+" '孤儿' "
						+" end, "
						+" apay.org "
						+"union "
						+"select case "
						+" when apay.diagnose_name like '%尿毒症%' then "
						+" '尿毒症患者' "
						+" when apay.diagnose_name like '%精神%' then "
						+" '精神病患者' "
						+" when apay.diagnose_name like '%肺结核%' then "
						+" '肺结核患者' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.diagnose_name like '%尿毒症%' then "
						+" '尿毒症患者' "
						+" when apay.diagnose_name like '%精神%' then "
						+" '精神病患者' "
						+" when apay.diagnose_name like '%肺结核%' then "
						+" '肺结核患者' "
						+" end, "
						+" apay.org "
						+") t "
						+"where t.subsection in ('五保', "
						+" '三无', "
						+" '孤儿', "
						+" '尿毒症患者', "
						+" '精神病患者', "
						+" '肺结核患者', "
						+" '普通低保', "
						+" '城市分类施保', "
						+" '农村重点户') )a "
						+"left join manager_org org on a.org = org.organization_id "
						+"group by a.org ,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist order by a.org ";
		
		//2000<=纳入救助金额<5000：
		String sql02="select a.org,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist from (select rownum, t.*"
								+" from (select case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') " 
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) between 2000 and 5000 " 
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end, "
								+" apay.org "
								+") t "
								+"where t.subsection in ('五保', "
								+" '三无', "
								+" '孤儿', "
								+" '尿毒症患者', "
								+" '精神病患者', "
								+" '肺结核患者', "
								+" '普通低保', "
								+" '城市分类施保', "
								+" '农村重点户') )a "
								+"left join manager_org org on a.org = org.organization_id "
								+"group by a.org ,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist order by a.org ";
				
				//纳入救助金额>=5000：
				String sql03="select a.org,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist from (select rownum, t.*"
								+" from (select case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') " 
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end, "
								+" apay.org "
								+") t "
								+"where t.subsection in ('五保', "
								+" '三无', "
								+" '孤儿', "
								+" '尿毒症患者', "
								+" '精神病患者', "
								+" '肺结核患者', "
								+" '普通低保', "
								+" '城市分类施保', "
								+" '农村重点户') )a "
								+"left join manager_org org on a.org = org.organization_id "
								+"group by a.org ,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist order by a.org ";
				
				//救助金额>=12000：
				String sql04="select a.org,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist from (select rownum, t.*"
								+" from (select case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and pay.pay_assist >= 12000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and pay.pay_assist >= 12000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and pay.pay_assist >= 12000 " 
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 " 
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and pay.pay_assist >= 12000 " 
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') " 
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and pay.pay_assist >= 12000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end, "
								+" apay.org "
								+"union "
								+"select case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and pay.pay_assist >= 12000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end, "
								+" apay.org "
								+") t "
								+"where t.subsection in ('五保', "
								+" '三无', "
								+" '孤儿', "
								+" '尿毒症患者', "
								+" '精神病患者', "
								+" '肺结核患者', "
								+" '普通低保', "
								+" '城市分类施保', "
								+" '农村重点户') )a "
								+"left join manager_org org on a.org = org.organization_id "
								+"group by a.org ,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist order by a.org ";
		paylist = reportService.findPayInviews(sql);
		paylist01 = reportService.findPayInviews(sql01);
		paylist02 = reportService.findPayInviews(sql02);
		paylist03 = reportService.findPayInviews(sql03);
		paylist04 = reportService.findPayInviews(sql04);
		
		String sql05 = "select a.type, a.org,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist from (select rownum, t.*"
				+" from (select '合计'as type,case "
				+" when apay.a1 in ('1', '2') then "
				+" '普通低保' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') " 
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a1 in ('1', '2') then "
				+" '普通低保' "
				+" end, "
				+" apay.org "
				+"union "
				+"select '合计'as type,case "
				+" when apay.a2 = '11' then "
				+" '城市分类施保' "
				+" when apay.a2 = '21' then "
				+" '农村重点户' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a2 = '11' then "
				+" '城市分类施保' "
				+" when apay.a2 = '21' then "
				+" '农村重点户' "
				+" end, "
				+" apay.org "
				+"union "
				+"select '合计'as type,case "
				+" when apay.a3 = '1' then "
				+" '三无' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') " 
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a3 = '1' then "
				+" '三无' "
				+" end, "
				+" apay.org "
				+"union "
				+"select '合计'as type,case "
				+" when apay.a4 = '1' then "
				+" '五保' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a4 = '1' then "
				+" '五保' "
				+" end, "
				+" apay.org "
				+"union "
				+"select '合计'as type,case "
				+" when apay.a6 = '1' then "
				+" '孤儿' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.a6 = '1' then "
				+" '孤儿' "
				+" end, "
				+" apay.org "
				+"union "
				+"select '合计'as type,case "
				+" when apay.diagnose_name like '%尿毒症%' then "
				+" '尿毒症患者' "
				+" when apay.diagnose_name like '%精神%' then "
				+" '精神病患者' "
				+" when apay.diagnose_name like '%肺结核%' then "
				+" '肺结核患者' "
				+" end as subsection, "
				+" count(apay.pnum) as pnum, "
				+" count(apay.persum) as persum, "
				+" nvl(sum(apay.total), 0) as total, "
				+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
				+" nvl(sum(apay.medicare), 0) as medicare, "
				+" nvl(sum(apay.assist), 0) as assist, "
				+" nvl(sum(apay.self), 0) as self, "
				+" nvl(sum(apay.ciassist), 0) as ciassist, "
				+" apay.org "
				+" from (select count(payy.biz_id) as pnum, "
				+" count(distinct(payy.member_id || payy.member_type)) as persum, "
				+" nvl(sum(payy.pay_total), 0) as total, "
				+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
				+" nvl(sum(payy.pay_medicare), 0) as medicare, "
				+" nvl(sum(payy.pay_assist), 0) as assist, "
				+" nvl(sum(payy.pay_self), 0) as self, "
				+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name "
				+" from (select biz.member_id, "
				+" biz.member_type, "
				+" biz.biz_id, "
				+" substr(biz.family_no, 1, 6) as org, "
				+" pay.pay_total, "
				+" pay.pay_medicare, "
				+" pay.pay_outmedicare, "
				+" pay.pay_assist, "
				+" nvl(pay.pay_total - pay.pay_medicare - "
				+" pay.pay_ciassist - pay.pay_assist, "
				+" 0) as pay_self, "
				+" pay.pay_ciassist, "
				+" pay.oper_time, "
				+" biz.person_type || biz.person_typex as persontype, "
				+" substr(biz.person_type, 1, 1) as a1, "
				+" substr(biz.person_type, 2, 1) as a2, "
				+" substr(biz.person_type, 3, 1) as a3, "
				+" substr(biz.person_type, 4, 1) as a4, "
				+" substr(biz.person_type, 5, 1) as a5, "
				+" substr(biz.person_typex, 1, 1) as a6, "
				+" biz.diagnose_name "
				+" from jz_biz biz, jz_pay pay "
				+" where biz.biz_id = pay.biz_id "
				+" and biz.biz_type = '2' "
				+" and biz.biz_status = '1' "
				+" and pay.pay_flag = '1' "
				+" and biz.medicare_type in ('1', '2') "
				+ " and substr(biz.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" union "
				+" select ma.member_id, "
				+" ma.member_type, "
				+" ma.biz_id, "
				+" substr(ma.family_no, 1, 6) as org, "
				+" ma.pay_total, "
				+" ma.pay_medicare, "
				+" ma.pay_outmedicare, "
				+" ma.pay_assist, "
				+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
				+" ma.pay_ciassist, "
				+" 0) as pay_self, "
				+" ma.pay_ciassist, "
				+" ma.oper_time, "
				+" base.assist_type || base.assist_typex as persontype, "
				+" substr(base.assist_type, 1, 1) as a1, "
				+" substr(base.assist_type, 1, 2) as a2, "
				+" substr(base.assist_type, 3, 1) as a3, "
				+" substr(base.assist_type, 4, 1) as a4, "
				+" substr(base.assist_type, 5, 1) as a5, "
				+" substr(base.assist_typex, 1, 1) as a6, "
				+" ma.diagnose_name "
				+" from jz_medicalafter ma "
				+" left join member_baseinfoview02 base "
				+" on ma.member_id = base.member_id "
				+" and ma.member_type = base.ds "
				+" where ma.biz_status = '1' "
				+" and ma.assist_type = '2' "
				+" and base.medicaretype in ('1', '2') "
				+ " and substr(ma.family_no,0,"
				+ orgLen
				+ ")= "
				+ oid
				+" ) payy "
				+" where 1 = 1 "+ jwhere
				+" group by payy.member_id || payy.member_type, "
				+" payy.org, "
				+" payy.a1, "
				+" payy.a2, "
				+" payy.a3, "
				+" payy.a4, "
				+" payy.a6, "
				+" payy.diagnose_name) apay "
				+" where 1 = 1 "
				+" group by case "
				+" when apay.diagnose_name like '%尿毒症%' then "
				+" '尿毒症患者' "
				+" when apay.diagnose_name like '%精神%' then "
				+" '精神病患者' "
				+" when apay.diagnose_name like '%肺结核%' then "
				+" '肺结核患者' "
				+" end, "
				+" apay.org "
										+ " union "
						+	"select '纳入救助金额<2000'as type,case "
						+" when apay.a1 in ('1', '2') then "
						+" '普通低保' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare)<2000 " 
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a1 in ('1', '2') then "
						+" '普通低保' "
						+" end, "
						+" apay.org "
						+"union "
						+"select '纳入救助金额<2000'as type,case "
						+" when apay.a2 = '11' then "
						+" '城市分类施保' "
						+" when apay.a2 = '21' then "
						+" '农村重点户' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a2 = '11' then "
						+" '城市分类施保' "
						+" when apay.a2 = '21' then "
						+" '农村重点户' "
						+" end, "
						+" apay.org "
						+"union "
						+"select '纳入救助金额<2000'as type,case "
						+" when apay.a3 = '1' then "
						+" '三无' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a3 = '1' then "
						+" '三无' "
						+" end, "
						+" apay.org "
						+"union "
						+"select '纳入救助金额<2000'as type,case "
						+" when apay.a4 = '1' then "
						+" '五保' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a4 = '1' then "
						+" '五保' "
						+" end, "
						+" apay.org "
						+"union "
						+"select '纳入救助金额<2000'as type,case "
						+" when apay.a6 = '1' then "
						+" '孤儿' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.a6 = '1' then "
						+" '孤儿' "
						+" end, "
						+" apay.org "
						+"union "
						+"select '纳入救助金额<2000'as type,case "
						+" when apay.diagnose_name like '%尿毒症%' then "
						+" '尿毒症患者' "
						+" when apay.diagnose_name like '%精神%' then "
						+" '精神病患者' "
						+" when apay.diagnose_name like '%肺结核%' then "
						+" '肺结核患者' "
						+" end as subsection, "
						+" count(apay.pnum) as pnum, "
						+" count(apay.persum) as persum, "
						+" nvl(sum(apay.total), 0) as total, "
						+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
						+" nvl(sum(apay.medicare), 0) as medicare, "
						+" nvl(sum(apay.assist), 0) as assist, "
						+" nvl(sum(apay.self), 0) as self, "
						+" nvl(sum(apay.ciassist), 0) as ciassist, "
						+" apay.org "
						+" from (select count(payy.biz_id) as pnum, "
						+" count(distinct(payy.member_id || payy.member_type)) as persum, "
						+" nvl(sum(payy.pay_total), 0) as total, "
						+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
						+" nvl(sum(payy.pay_medicare), 0) as medicare, "
						+" nvl(sum(payy.pay_assist), 0) as assist, "
						+" nvl(sum(payy.pay_self), 0) as self, "
						+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name "
						+" from (select biz.member_id, "
						+" biz.member_type, "
						+" biz.biz_id, "
						+" substr(biz.family_no, 1, 6) as org, "
						+" pay.pay_total, "
						+" pay.pay_medicare, "
						+" pay.pay_outmedicare, "
						+" pay.pay_assist, "
						+" nvl(pay.pay_total - pay.pay_medicare - "
						+" pay.pay_ciassist - pay.pay_assist, "
						+" 0) as pay_self, "
						+" pay.pay_ciassist, "
						+" pay.oper_time, "
						+" biz.person_type || biz.person_typex as persontype, "
						+" substr(biz.person_type, 1, 1) as a1, "
						+" substr(biz.person_type, 2, 1) as a2, "
						+" substr(biz.person_type, 3, 1) as a3, "
						+" substr(biz.person_type, 4, 1) as a4, "
						+" substr(biz.person_type, 5, 1) as a5, "
						+" substr(biz.person_typex, 1, 1) as a6, "
						+" biz.diagnose_name "
						+" from jz_biz biz, jz_pay pay "
						+" where biz.biz_id = pay.biz_id "
						+" and biz.biz_type = '2' "
						+" and biz.biz_status = '1' "
						+" and pay.pay_flag = '1' "
						+" and biz.medicare_type in ('1', '2') "
						+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare)<2000 "
						+ " and substr(biz.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" union "
						+" select ma.member_id, "
						+" ma.member_type, "
						+" ma.biz_id, "
						+" substr(ma.family_no, 1, 6) as org, "
						+" ma.pay_total, "
						+" ma.pay_medicare, "
						+" ma.pay_outmedicare, "
						+" ma.pay_assist, "
						+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
						+" ma.pay_ciassist, "
						+" 0) as pay_self, "
						+" ma.pay_ciassist, "
						+" ma.oper_time, "
						+" base.assist_type || base.assist_typex as persontype, "
						+" substr(base.assist_type, 1, 1) as a1, "
						+" substr(base.assist_type, 1, 2) as a2, "
						+" substr(base.assist_type, 3, 1) as a3, "
						+" substr(base.assist_type, 4, 1) as a4, "
						+" substr(base.assist_type, 5, 1) as a5, "
						+" substr(base.assist_typex, 1, 1) as a6, "
						+" ma.diagnose_name "
						+" from jz_medicalafter ma "
						+" left join member_baseinfoview02 base "
						+" on ma.member_id = base.member_id "
						+" and ma.member_type = base.ds "
						+" where ma.biz_status = '1' "
						+" and ma.assist_type = '2' "
						+" and base.medicaretype in ('1', '2') "
						+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare)<2000 "
						+ " and substr(ma.family_no,0,"
						+ orgLen
						+ ")= "
						+ oid
						+" ) payy "
						+" where 1 = 1 "+ jwhere
						+" group by payy.member_id || payy.member_type, "
						+" payy.org, "
						+" payy.a1, "
						+" payy.a2, "
						+" payy.a3, "
						+" payy.a4, "
						+" payy.a6, "
						+" payy.diagnose_name) apay "
						+" where 1 = 1 "
						+" group by case "
						+" when apay.diagnose_name like '%尿毒症%' then "
						+" '尿毒症患者' "
						+" when apay.diagnose_name like '%精神%' then "
						+" '精神病患者' "
						+" when apay.diagnose_name like '%肺结核%' then "
						+" '肺结核患者' "
						+" end, "
						+" apay.org "
														+ " union "
								+" select '2000<=纳入救助金额<5000'as type,case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '2000<=纳入救助金额<5000'as type,case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '2000<=纳入救助金额<5000'as type,case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') " 
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '2000<=纳入救助金额<5000'as type,case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) between 2000 and 5000 " 
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '2000<=纳入救助金额<5000'as type,case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '2000<=纳入救助金额<5000'as type,case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) between 2000 and 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end, "
								+" apay.org "
								+ " union "
								+ " select '纳入救助金额>=5000'as type,case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '纳入救助金额>=5000'as type,case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '纳入救助金额>=5000'as type,case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '纳入救助金额>=5000'as type,case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '纳入救助金额>=5000'as type,case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') " 
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '纳入救助金额>=5000'as type,case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and (pay.pay_total - pay.pay_medicare - pay.pay_ciassist - pay.pay_outmedicare) >= 5000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and (ma.pay_total - ma.pay_medicare - ma.pay_ciassist - ma.pay_outmedicare) >= 5000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end, "
								+" apay.org "
																+ " union "
								+ " select '救助金额>=12000'as type,case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and pay.pay_assist >= 12000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a1 in ('1', '2') then "
								+" '普通低保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '救助金额>=12000'as type,case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and pay.pay_assist >= 12000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a2 = '11' then "
								+" '城市分类施保' "
								+" when apay.a2 = '21' then "
								+" '农村重点户' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '救助金额>=12000'as type,case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and pay.pay_assist >= 12000 " 
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 " 
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a3 = '1' then "
								+" '三无' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '救助金额>=12000'as type,case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') "
								+ " and pay.pay_assist >= 12000 " 
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') " 
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a4 = '1' then "
								+" '五保' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '救助金额>=12000'as type,case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and pay.pay_assist >= 12000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.a6 = '1' then "
								+" '孤儿' "
								+" end, "
								+" apay.org "
								+"union "
								+"select '救助金额>=12000'as type,case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end as subsection, "
								+" count(apay.pnum) as pnum, "
								+" count(apay.persum) as persum, "
								+" nvl(sum(apay.total), 0) as total, "
								+" nvl(sum(apay.outmedicare), 0) as outmedicare, "
								+" nvl(sum(apay.medicare), 0) as medicare, "
								+" nvl(sum(apay.assist), 0) as assist, "
								+" nvl(sum(apay.self), 0) as self, "
								+" nvl(sum(apay.ciassist), 0) as ciassist, "
								+" apay.org "
								+" from (select count(payy.biz_id) as pnum, "
								+" count(distinct(payy.member_id || payy.member_type)) as persum, "
								+" nvl(sum(payy.pay_total), 0) as total, "
								+" nvl(sum(payy.pay_outmedicare), 0) as outmedicare, "
								+" nvl(sum(payy.pay_medicare), 0) as medicare, "
								+" nvl(sum(payy.pay_assist), 0) as assist, "
								+" nvl(sum(payy.pay_self), 0) as self, "
								+" nvl(sum(payy.pay_ciassist), 0) as ciassist, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name "
								+" from (select biz.member_id, "
								+" biz.member_type, "
								+" biz.biz_id, "
								+" substr(biz.family_no, 1, 6) as org, "
								+" pay.pay_total, "
								+" pay.pay_medicare, "
								+" pay.pay_outmedicare, "
								+" pay.pay_assist, "
								+" nvl(pay.pay_total - pay.pay_medicare - "
								+" pay.pay_ciassist - pay.pay_assist, "
								+" 0) as pay_self, "
								+" pay.pay_ciassist, "
								+" pay.oper_time, "
								+" biz.person_type || biz.person_typex as persontype, "
								+" substr(biz.person_type, 1, 1) as a1, "
								+" substr(biz.person_type, 2, 1) as a2, "
								+" substr(biz.person_type, 3, 1) as a3, "
								+" substr(biz.person_type, 4, 1) as a4, "
								+" substr(biz.person_type, 5, 1) as a5, "
								+" substr(biz.person_typex, 1, 1) as a6, "
								+" biz.diagnose_name "
								+" from jz_biz biz, jz_pay pay "
								+" where biz.biz_id = pay.biz_id "
								+" and biz.biz_type = '2' "
								+" and biz.biz_status = '1' "
								+" and pay.pay_flag = '1' "
								+" and biz.medicare_type in ('1', '2') " 
								+ " and pay.pay_assist >= 12000 "
								+ " and substr(biz.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" union "
								+" select ma.member_id, "
								+" ma.member_type, "
								+" ma.biz_id, "
								+" substr(ma.family_no, 1, 6) as org, "
								+" ma.pay_total, "
								+" ma.pay_medicare, "
								+" ma.pay_outmedicare, "
								+" ma.pay_assist, "
								+" nvl(ma.pay_total - ma.pay_medicare - ma.pay_assist - "
								+" ma.pay_ciassist, "
								+" 0) as pay_self, "
								+" ma.pay_ciassist, "
								+" ma.oper_time, "
								+" base.assist_type || base.assist_typex as persontype, "
								+" substr(base.assist_type, 1, 1) as a1, "
								+" substr(base.assist_type, 1, 2) as a2, "
								+" substr(base.assist_type, 3, 1) as a3, "
								+" substr(base.assist_type, 4, 1) as a4, "
								+" substr(base.assist_type, 5, 1) as a5, "
								+" substr(base.assist_typex, 1, 1) as a6, "
								+" ma.diagnose_name "
								+" from jz_medicalafter ma "
								+" left join member_baseinfoview02 base "
								+" on ma.member_id = base.member_id "
								+" and ma.member_type = base.ds "
								+" where ma.biz_status = '1' "
								+" and ma.assist_type = '2' "
								+" and base.medicaretype in ('1', '2') "
								+ " and ma.pay_assist >= 12000 "
								+ " and substr(ma.family_no,0,"
								+ orgLen
								+ ")= "
								+ oid
								+" ) payy "
								+" where 1 = 1 "+ jwhere
								+" group by payy.member_id || payy.member_type, "
								+" payy.org, "
								+" payy.a1, "
								+" payy.a2, "
								+" payy.a3, "
								+" payy.a4, "
								+" payy.a6, "
								+" payy.diagnose_name) apay "
								+" where 1 = 1 "
								+" group by case "
								+" when apay.diagnose_name like '%尿毒症%' then "
								+" '尿毒症患者' "
								+" when apay.diagnose_name like '%精神%' then "
								+" '精神病患者' "
								+" when apay.diagnose_name like '%肺结核%' then "
								+" '肺结核患者' "
								+" end, "
								+" apay.org "
				+") t "
				+"where t.subsection in ('五保', "
				+" '三无', "
				+" '孤儿', "
				+" '尿毒症患者', "
				+" '精神病患者', "
				+" '肺结核患者', "
				+" '普通低保', "
				+" '城市分类施保', "
				+" '农村重点户') )a "
				+"left join manager_org org on a.org = org.organization_id "
				+"group by a.type,a.org ,org.orgname,a.subsection,a.pnum,a.persum,a.total,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist,a.outmedicare,a.medicare,a.assist,a.self,a.ciassist order by a.org ";
		session.put("sql", sql05);
		HashMap title = new HashMap();	
		//title.put("ROWNUM,val", "序号");
		title.put("TYPE,val", "类别");
		title.put("ORGNAME,val", "县(市、区)");
		title.put("SUBSECTION,val", "人员类别");
		title.put("PERSUM,val", "人数");
		title.put("TOTAL,val", "总费用");						
		title.put("MEDICARE,val", "医保/农合报销");
		title.put("CIASSIST,val", "大病保险");
		title.put("ASSIST,val", "民政救助");
		title.put("SELF,val", "个人自理");
		title.put("OUTMEDICARE,val", "目录外费用");		
		session.put("title", title);
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 8 == organizationId.length() 
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String dividedInit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() ) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;

		} else {
			result = "此功能为区县使用！";
			return "result";
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String  divided(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		String jwhere = "";
		jwhere = jwhere + " and bpay.family_no like '" + organizationId + "%'"; 
		if(!"".equals(year)&& !(null==year)){
			jwhere = jwhere + " and to_char(bpay.oper_time,'yyyy')='" + year + "' ";
		}
		if("1".equals(type)){
			jwhere = jwhere + " and substr(bpay.person_type,1,1)<>'0' and bpay.member_type='1' ";
		}else if("2".equals(type)){
			jwhere = jwhere + " and substr(bpay.person_type,1,1)<>'0' and bpay.member_type='2' ";
		}else if("3".equals(type)){
			jwhere = jwhere + " and (substr(bpay.person_type,3,1)='1' or  substr(bpay.person_type,6,1)='1')";
		}else if("4".equals(type)){
			jwhere = jwhere + " and substr(bpay.person_type,4,1)='1' ";
		}
			
		String sql= " select rownum, t.* from (select case "
				+" when apay.total between 0 and 500 then    "
				+"  '0--500'    "
				+" when apay.total between 500.01 and 1000 then    "
				+"  '500--1000'    "
				+" when apay.total between 1000.01 and 2000 then    "
				+"  '1000--2000'    "
				+" when apay.total between 2000.01 and 3000 then    "
				+"  '2000--3000'    "
				+" when apay.total between 3000.01 and 4000 then    "
				+"  '3000--4000'    "
				+" when apay.total between 4000.01 and 5000 then    "
				+"  '4000--5000'    "
				+" when apay.total between 5000.01 and 10000 then    "
				+"  '5000--10000'    "
				+" when apay.total between 10000.01 and 20000 then    "
				+"  '10000--20000'    "
				+" when apay.total between 20000.01 and 30000 then    "
				+"  '20000--30000'    "
				+" when apay.total between 30000.01 and 40000 then    "
				+"  '30000--40000'    "
				+" when apay.total between 40000.01 and 50000 then    "
				+"  '40000--50000'    "
				+" when apay.total between 50000.01 and 100000 then    "
				+"  '50000--100000'    "
				+" when apay.total >= 100000.01 then    "
				+"  '100000-->'    "
                +" end as subsection,                                  "
                +" count(apay.persum) as persum,      "
                +" nvl(sum(apay.total), 0) as total,                "
                +" nvl(sum(apay.outmedicare), 0) as outmedicare,    "
                +" nvl(sum(apay.medicare), 0) as medicare,          "
                +" nvl(sum(apay.assist), 0) as assist,              "
                +" nvl(sum(apay.self), 0) as self,                  "
                +" nvl(sum(apay.ciassist), 0) as ciassist           "
                +" from (select count(bpay.biz_id) as pnum,         "
                +" count(distinct(bpay.member_id || bpay.member_type)) as persum, "
                +" nvl(sum(bpay.pay_total), 0) as total, "
                +" nvl(sum(bpay.pay_outmedicare), 0) as outmedicare, "
                +" nvl(sum(bpay.pay_medicare), 0) as medicare, "
                +" nvl(sum(bpay.pay_assist), 0) as assist, "
                +" nvl(sum(bpay.pay_self), 0) as self, "
                +" nvl(sum(bpay.pay_ciassist), 0) as ciassist "
                +" from (select pay.biz_id, pay.pay_total, pay.pay_medicare, "
                +" pay.pay_outmedicare, pay.pay_assist, "
                +" nvl(pay.pay_total - pay.pay_medicare - pay.pay_assist - "
                +" pay.pay_ciassist, 0) as pay_self, "
                +" pay.id_card, "
                +" pay.family_no, "
                +" pay.person_type, "
                +" pay.pay_ciassist, pay.member_id, pay.member_type, pay.oper_time from payview02 pay "
                +" where pay.biztype in ('ma', 'biz')) bpay "
                +" where 1 = 1 " + jwhere
                +" group by bpay.id_card) apay "
                +" where 1 = 1 "
                +" group by case "
                +" when apay.total between 0 and 500 then    "
                +"  '0--500'    "
                +" when apay.total between 500.01 and 1000 then    "
                +"  '500--1000'    "
                +" when apay.total between 1000.01 and 2000 then    "
                +"  '1000--2000'    "
                +" when apay.total between 2000.01 and 3000 then    "
                +"  '2000--3000'    "
                +" when apay.total between 3000.01 and 4000 then    "
                +"  '3000--4000'    "
                +" when apay.total between 4000.01 and 5000 then    "
                +"  '4000--5000'    "
                +" when apay.total between 5000.01 and 10000 then    "
                +"  '5000--10000'    "
                +" when apay.total between 10000.01 and 20000 then    "
                +"  '10000--20000'    "
                +" when apay.total between 20000.01 and 30000 then    "
                +"  '20000--30000'    "
                +" when apay.total between 30000.01 and 40000 then    "
                +"  '30000--40000'    "
                +" when apay.total between 40000.01 and 50000 then    "
                +"  '40000--50000'    "
                +" when apay.total between 50000.01 and 100000 then    "
                +"  '50000--100000'    "
                +" when apay.total >= 100000.01 then    "
                +"  '100000-->'    "
  				+" end "
  				+" order by CAST(substr(subsection, 0,"
  				+" INSTR(subsection, '-', 1, 1) - 1) as int)) t  ";
			paylist = reportService.findPayviews(sql);
			session.put("sql", sql);
			HashMap title = new HashMap();	
			//title.put("ROWNUM,val", "序号");	
			title.put("SUBSECTION,val", "医疗总费用范围");
			title.put("PERSUM,val", "人数");
			title.put("TOTAL,val", "总费用");						
			title.put("MEDICARE,val", "医保/农合报销");
			title.put("CIASSIST,val", "大病保险");
			title.put("ASSIST,val", "民政救助");
			title.put("SELF,val", "个人自理");
			title.put("OUTMEDICARE,val", "目录外费用");		
			session.put("title", title);	
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String specialInit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() ) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;

		} else {
			result = "此功能为区县使用！";
			return "result";
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String  special(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		String jwhere = "";
		jwhere = jwhere + " and payy.family_no like '" + organizationId + "%'"; 
		if(!"".equals(year)&& !(null==year)){
			jwhere = jwhere + " and to_char(payy.oper_time,'yyyy')='" + year + "' ";
		}
		if("1".equals(type)){
			jwhere = jwhere + " and payy.member_type='1' ";
		}else if("2".equals(type)){
			jwhere = jwhere + " and payy.member_type='2' ";
		}
			
		String sql=" select rownum, t.*                                    "
				 +" from (select case                                     "
				 +" when apay.diagnose_name like '%尿毒症%' then          "
				 +" '尿毒症'                                              "
				 +" when apay.diagnose_name like '%精神%' then            "
				 +" '精神病'                                              "
				 +" when apay.diagnose_name like '%肺结核%' then          "
				 +" '肺结核'                                              "
				 +" end as subsection,                                    "
				 +" sum(apay.pnum) as pnum,                             "
				 +" sum(apay.persum) as persum,                         "
				 +" nvl(sum(apay.total), 0) as total,                     "
				 +" nvl(sum(apay.outmedicare), 0) as outmedicare,         "
				 +" nvl(sum(apay.medicare), 0) as medicare,               "
				 +" nvl(sum(apay.assist), 0) as assist,                   "
				 +" nvl(sum(apay.self), 0) as self,                       "
				 +" nvl(sum(apay.ciassist), 0) as ciassist                "
				 +" from (select count(payy.biz_id) as pnum,              "
				 +" count(distinct(payy.id_card)) as persum,                        "
				 +" nvl(sum(payy.pay_total), 0) as total,                 "
				 +" nvl(sum(payy.pay_outmedicare), 0) as outmedicare,     "
				 +" nvl(sum(payy.pay_medicare), 0) as medicare,           "
				 +" nvl(sum(payy.pay_assist), 0) as assist,               "
				 +" nvl(sum(payy.pay_self), 0) as self,                   "
				 +" nvl(sum(payy.pay_ciassist), 0) as ciassist,           "
				 +" payy.diagnose_name                                    "
				 +" from (select biz.member_id,                           "
				 +" biz.member_type,                                      "
				 +" upper(biz.id_card) as id_card,                        "
				 +" biz.family_no,                                        "
				 +" biz.biz_id,                                           "
				 +" pay.pay_total,                                        "
				 +" pay.pay_medicare,                                     "
				 +" pay.pay_outmedicare,                                  "
				 +" pay.pay_assist,                                       "
				 +" nvl(pay.pay_total - pay.pay_medicare -                "
				 +" pay.pay_ciassist - pay.pay_assist,                    "
				 +" 0) as pay_self,                                       "
				 +" pay.pay_ciassist,                                     "
				 +" pay.oper_time,                                        "
				 +" biz.person_type || biz.person_typex as persontype,    "
				 +" substr(biz.person_type, 1, 1) as a1,                  "
				 +" substr(biz.person_type, 3, 1) as a3,                  "
				 +" substr(biz.person_type, 4, 1) as a4,                  "
				 +" substr(biz.person_type, 5, 1) as a5,                  "
				 +" substr(biz.person_typex, 1, 1) as a6,                 "
				 +" biz.diagnose_name                                     "
				 +" from jz_biz biz, jz_pay pay                           "
				 +" where biz.biz_id = pay.biz_id                         "
				 +" and biz.biz_type in ('1', '2')                        "
				 +" and biz.biz_status = '1'                              "
				 +" and biz.assist_flag = '1'                             "
				 +" and pay.sts = '1'                                     "
				 +" union                                                 "
				 +" select ma.member_id,                                  "
				 +" ma.member_type,                                       "
				 +" upper(ma.id_card) as id_card,                         "
				 +" ma.family_no,                                         "
				 +" ma.biz_id,                                            "
				 +" ma.pay_total,                                         "
				 +" ma.pay_medicare,                                      "
				 +" ma.pay_outmedicare,                                   "
				 +" ma.pay_assist,                                        "
				 +" nvl(ma.pay_total - ma.pay_medicare -                  "
				 +" ma.pay_assist - ma.pay_ciassist,                      "
				 +" 0) as pay_self,                                       "
				 +" ma.pay_ciassist,                                      "
				 +" ma.oper_time,                                         "
				 +" ma.assist_type_m || ma.assist_typex as persontype,    "
				 +" substr(ma.assist_type_m, 1, 1) as a1,                 "
				 +" substr(ma.assist_type_m, 3, 1) as a3,                 "
				 +" substr(ma.assist_type_m, 4, 1) as a4,                 "
				 +" substr(ma.assist_type_m, 5, 1) as a5,                 "
				 +" substr(ma.assist_typex, 1, 1) as a6,                  "
				 +" ma.diagnose_name                                      "
				 +" from jz_medicalafter ma                               "
				 +" where ma.biz_status = '1'                             "
				 +" and ma.assist_type in ('1', '2')) payy                "
				 +" where 1 = 1 " + jwhere
				 +" group by payy.id_card, payy.diagnose_name) apay       "
				 +" where 1 = 1                                           "
				 +" group by case                                         "
				 +" when apay.diagnose_name like '%尿毒症%' then          "
				 +" '尿毒症'                                              "
				 +" when apay.diagnose_name like '%精神%' then            "
				 +" '精神病'                                              "
				 +" when apay.diagnose_name like '%肺结核%' then          "
				 +" '肺结核'                                              "
				 +" end) t                                                "
				 +" where t.subsection in ('尿毒症', '精神病', '肺结核')  ";
			paylist = reportService.findPayviews(sql);
			session.put("sql", sql);
			HashMap title = new HashMap();	
			//title.put("ROWNUM,val", "序号");	
			title.put("SUBSECTION,val", "医疗总费用范围");
			title.put("PERSUM,val", "人数");
			title.put("TOTAL,val", "总费用");						
			title.put("MEDICARE,val", "医保/农合报销");
			title.put("CIASSIST,val", "大病保险");
			title.put("ASSIST,val", "民政救助");
			title.put("SELF,val", "个人自理");
			title.put("OUTMEDICARE,val", "目录外费用");		
			session.put("title", title);	
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String ratioInit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() ) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;

		} else {
			result = "此功能为区县使用！";
			return "result";
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String ratio(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		String jwhere = "";
		String jwhere1 = "";
		jwhere = jwhere + " and payy.family_no like '" + organizationId + "%'";
		jwhere1 = jwhere1 + " and bb.familyno like '" + organizationId + "%'";
		if(!"".equals(year)&& !(null==year)){
			jwhere = jwhere + " and to_char(payy.oper_time,'yyyy')='" + year + "' ";
		}
			
		String sql="select rownum, tt.*                                                                                                                                                                                                                                "
				+"  from (                                                                                                                                                                                                                                           "
				+"select rownum, t.*                                                                                                                                                                                                                                 "
				+"  from (select case                                                                                                                                                                                                                                "
				+"                 when apay.diagnose_name like '%尿毒症%' then                                                                                                                                                                                      "
				+"                  '尿毒症'                                                                                                                                                                                                                         "
				+"                 when apay.diagnose_name like '%精神%' then                                                                                                                                                                                        "
				+"                  '精神病'                                                                                                                                                                                                                         "
				+"                 when apay.diagnose_name like '%肺结核%' then                                                                                                                                                                                      "
				+"                  '肺结核'                                                                                                                                                                                                                         "
				+"               end as subsection,                                                                                                                                                                                                                  "
				+"               sum(apay.pnum) as pnum,                                                                                                                                                                                                             "
				+"               sum(apay.persum) as persum,                                                                                                                                                                                                         "
				+"               apay.pertotal,                                                                                                                                                                                                                      "
				+"               round((sum(apay.persum)/apay.pertotal)*100,4) as rate                                                                                                                                                                                        "
				+"          from (select                                                                                                                                                                                                                             "
				+"                      (select count(*) from member_baseinfoview02 bb where bb.personstate='正常' and bb.assist_type||bb.assist_typex <> '00000000000'"+ jwhere1 +")as pertotal,                                                                                 "
				+"                       count(payy.biz_id) as pnum,                                                                                                                                                                                                 "
				+"                       count(distinct(payy.id_card)) as persum,                                                                                                                                                                                    "
				+"                       payy.a3,                                                                                                                                                                                                                    "
				+"                       payy.a4,                                                                                                                                                                                                                    "
				+"                       payy.a6,                                                                                                                                                                                                                    "
				+"                       payy.diagnose_name                                                                                                                                                                                                          "
				+"                  from (select biz.member_id,                                                                                                                                                                                                      "
				+"                               biz.member_type,                                                                                                                                                                                                    "
				+"                               upper(biz.id_card) as id_card,                                                                                                                                                                                      "
				+"                               biz.family_no,                                                                                                                                                                                                      "
				+"                               biz.biz_id,                                                                                                                                                                                                         "
				+"                               pay.pay_total,                                                                                                                                                                                                      "
				+"                               pay.pay_medicare,                                                                                                                                                                                                   "
				+"                               pay.pay_outmedicare,                                                                                                                                                                                                "
				+"                               pay.pay_assist,                                                                                                                                                                                                     "
				+"                               nvl(pay.pay_total - pay.pay_medicare -                                                                                                                                                                              "
				+"                                   pay.pay_ciassist - pay.pay_assist,                                                                                                                                                                              "
				+"                                   0) as pay_self,                                                                                                                                                                                                 "
				+"                               pay.pay_ciassist,                                                                                                                                                                                                   "
				+"                               pay.oper_time,                                                                                                                                                                                                      "
				+"                               biz.person_type || biz.person_typex as persontype,                                                                                                                                                                  "
				+"                               substr(biz.person_type, 1, 1) as a1,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 3, 1) as a3,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 4, 1) as a4,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 5, 1) as a5,                                                                                                                                                                                "
				+"                               substr(biz.person_typex, 1, 1) as a6,                                                                                                                                                                               "
				+"                               biz.diagnose_name                                                                                                                                                                                                   "
				+"                          from jz_biz biz, jz_pay pay                                                                                                                                                                                              "
				+"                         where biz.biz_id = pay.biz_id                                                                                                                                                                                             "
				+"                           and biz.biz_type in ('1', '2')                                                                                                                                                                                          "
				+"                           and biz.biz_status = '1'                                                                                                                                                                                                "
				+"                           and biz.assist_flag = '1'                                                                                                                                                                                               "
				+"                           and pay.sts = '1'                                                                                                                                                                                                       "
				+"                        union                                                                                                                                                                                                                      "
				+"                        select ma.member_id,                                                                                                                                                                                                       "
				+"                               ma.member_type,                                                                                                                                                                                                     "
				+"                               upper(ma.id_card) as id_card,                                                                                                                                                                                       "
				+"                               ma.family_no,                                                                                                                                                                                                       "
				+"                               ma.biz_id,                                                                                                                                                                                                          "
				+"                               ma.pay_total,                                                                                                                                                                                                       "
				+"                               ma.pay_medicare,                                                                                                                                                                                                    "
				+"                               ma.pay_outmedicare,                                                                                                                                                                                                 "
				+"                               ma.pay_assist,                                                                                                                                                                                                      "
				+"                               nvl(ma.pay_total - ma.pay_medicare -                                                                                                                                                                                "
				+"                                   ma.pay_assist - ma.pay_ciassist,                                                                                                                                                                                "
				+"                                   0) as pay_self,                                                                                                                                                                                                 "
				+"                               ma.pay_ciassist,                                                                                                                                                                                                    "
				+"                               ma.oper_time,                                                                                                                                                                                                       "
				+"                               ma.assist_type_m || ma.assist_typex as persontype,                                                                                                                                                                  "
				+"                               substr(ma.assist_type_m, 1, 1) as a1,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 3, 1) as a3,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 4, 1) as a4,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 5, 1) as a5,                                                                                                                                                                               "
				+"                               substr(ma.assist_typex, 1, 1) as a6,                                                                                                                                                                                "
				+"                               ma.diagnose_name                                                                                                                                                                                                    "
				+"                          from jz_medicalafter ma                                                                                                                                                                                                  "
				+"                         where ma.biz_status = '1'                                                                                                                                                                                                 "
				+"                           and ma.assist_type in ('1', '2')) payy                                                                                                                                                                                  "
				+"                 where 1 = 1                                                                                                                                                                                                                       "
				+ jwhere
				+"                 group by payy.id_card, payy.a3,                                                                                                                                                                                                   "
				+"                          payy.a4,                                                                                                                                                                                                                 "
				+"                          payy.a6, payy.diagnose_name) apay                                                                                                                                                                                        "
				+"         where 1 = 1                                                                                                                                                                                                                               "
				+"         group by case                                                                                                                                                                                                                             "
				+"                    when apay.diagnose_name like '%尿毒症%' then                                                                                                                                                                                   "
				+"                     '尿毒症'                                                                                                                                                                                                                      "
				+"                    when apay.diagnose_name like '%精神%' then                                                                                                                                                                                     "
				+"                     '精神病'                                                                                                                                                                                                                      "
				+"                    when apay.diagnose_name like '%肺结核%' then                                                                                                                                                                                   "
				+"                     '肺结核'                                                                                                                                                                                                                      "
				+"                  end, apay.pertotal) t                                                                                                                                                                                                            "
				+" where t.subsection in ('尿毒症', '精神病', '肺结核')                                                                                                                                                                                              "
				+"union                                                                                                                                                                                                                                              "
				+"select rownum, t.*                                                                                                                                                                                                                                 "
				+"  from (select '特困供养人员' as subsection,                                                                                                                                                                                                       "
				+"               sum(apay.pnum) as pnum,                                                                                                                                                                                                             "
				+"               sum(apay.persum) as persum,                                                                                                                                                                                                         "
				+"               apay.pertotal,                                                                                                                                                                                                                      "
				+"               round((sum(apay.persum)/apay.pertotal)*100,4) as rate                                                                                                                                                                                        "
				+"          from (select                                                                                                                                                                                                                             "
				+"                      (select count(*) from member_baseinfoview02 bb where bb.personstate='正常' and ( substr(bb.assist_type,'4','1')='1' or  substr(bb.assist_type,'3','1')='1' or  substr(bb.assist_typex,'1','1')='1') "+ jwhere1 +")as pertotal,             "
				+"                       count(payy.biz_id) as pnum,                                                                                                                                                                                                 "
				+"                       count(distinct(payy.id_card)) as persum,                                                                                                                                                                                    "
				+"                       payy.diagnose_name                                                                                                                                                                                                          "
				+"                  from (select biz.member_id,                                                                                                                                                                                                      "
				+"                               biz.member_type,                                                                                                                                                                                                    "
				+"                               upper(biz.id_card) as id_card,                                                                                                                                                                                      "
				+"                               biz.family_no,                                                                                                                                                                                                      "
				+"                               biz.biz_id,                                                                                                                                                                                                         "
				+"                               pay.pay_total,                                                                                                                                                                                                      "
				+"                               pay.pay_medicare,                                                                                                                                                                                                   "
				+"                               pay.pay_outmedicare,                                                                                                                                                                                                "
				+"                               pay.pay_assist,                                                                                                                                                                                                     "
				+"                               nvl(pay.pay_total - pay.pay_medicare -                                                                                                                                                                              "
				+"                                   pay.pay_ciassist - pay.pay_assist,                                                                                                                                                                              "
				+"                                   0) as pay_self,                                                                                                                                                                                                 "
				+"                               pay.pay_ciassist,                                                                                                                                                                                                   "
				+"                               pay.oper_time,                                                                                                                                                                                                      "
				+"                               biz.person_type || biz.person_typex as persontype,                                                                                                                                                                  "
				+"                               substr(biz.person_type, 1, 1) as a1,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 3, 1) as a3,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 4, 1) as a4,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 5, 1) as a5,                                                                                                                                                                                "
				+"                               substr(biz.person_typex, 1, 1) as a6,                                                                                                                                                                               "
				+"                               biz.diagnose_name                                                                                                                                                                                                   "
				+"                          from jz_biz biz, jz_pay pay                                                                                                                                                                                              "
				+"                         where biz.biz_id = pay.biz_id                                                                                                                                                                                             "
				+"                           and biz.biz_type in ('1', '2')                                                                                                                                                                                          "
				+"                           and biz.biz_status = '1'                                                                                                                                                                                                "
				+"                           and biz.assist_flag = '1'                                                                                                                                                                                               "
				+"                           and pay.sts = '1'                                                                                                                                                                                                       "
				+"                           and (substr(biz.person_type, 3, 1)='1' or substr(biz.person_type, 4, 1)='1' or substr(biz.person_typex, 1, 1)='1')                                                                                                      "
				+"                        union                                                                                                                                                                                                                      "
				+"                        select ma.member_id,                                                                                                                                                                                                       "
				+"                               ma.member_type,                                                                                                                                                                                                     "
				+"                               upper(ma.id_card) as id_card,                                                                                                                                                                                       "
				+"                               ma.family_no,                                                                                                                                                                                                       "
				+"                               ma.biz_id,                                                                                                                                                                                                          "
				+"                               ma.pay_total,                                                                                                                                                                                                       "
				+"                               ma.pay_medicare,                                                                                                                                                                                                    "
				+"                               ma.pay_outmedicare,                                                                                                                                                                                                 "
				+"                               ma.pay_assist,                                                                                                                                                                                                      "
				+"                               nvl(ma.pay_total - ma.pay_medicare -                                                                                                                                                                                "
				+"                                   ma.pay_assist - ma.pay_ciassist,                                                                                                                                                                                "
				+"                                   0) as pay_self,                                                                                                                                                                                                 "
				+"                               ma.pay_ciassist,                                                                                                                                                                                                    "
				+"                               ma.oper_time,                                                                                                                                                                                                       "
				+"                               ma.assist_type_m || ma.assist_typex as persontype,                                                                                                                                                                  "
				+"                               substr(ma.assist_type_m, 1, 1) as a1,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 3, 1) as a3,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 4, 1) as a4,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 5, 1) as a5,                                                                                                                                                                               "
				+"                               substr(ma.assist_typex, 1, 1) as a6,                                                                                                                                                                                "
				+"                               ma.diagnose_name                                                                                                                                                                                                    "
				+"                          from jz_medicalafter ma                                                                                                                                                                                                  "
				+"                         where ma.biz_status = '1'                                                                                                                                                                                                 "
				+"                           and ma.assist_type in ('1', '2')                                                                                                                                                                                        "
				+"                           and (substr(ma.assist_type_m, 3, 1)='1' or substr(ma.assist_type_m, 4, 1)='1' or substr(ma.assist_typex, 1, 1)='1')                                                                                                     "
				+"                           ) payy                                                                                                                                                                                                                  "
				+"                 where 1 = 1                                                                                                                                                                                                                       "
				+ jwhere
				+"                 group by payy.id_card, payy.diagnose_name) apay                                                                                                                                                                                   "
				+"         where 1 = 1                                                                                                                                                                                                                               "
				+"         group by  apay.pertotal) t                                                                                                                                                                                                                "
				+"union                                                                                                                                                                                                                                              "
				+"select rownum, t.*                                                                                                                                                                                                                                 "
				+"  from (select '低保对象' as subsection,                                                                                                                                                                                                           "
				+"               sum(apay.pnum) as pnum,                                                                                                                                                                                                             "
				+"               sum(apay.persum) as persum,                                                                                                                                                                                                         "
				+"               apay.pertotal,                                                                                                                                                                                                                      "
				+"               round((sum(apay.persum)/apay.pertotal)*100,4) as rate                                                                                                                                                                                        "
				+"          from (select                                                                                                                                                                                                                             "
				+"                      (select count(*) from member_baseinfoview02 bb where bb.personstate='正常' and  substr(bb.assist_type,'1','1')<>'0' "+ jwhere1 +")as pertotal,                                                                                             "
				+"                       count(payy.biz_id) as pnum,                                                                                                                                                                                                 "
				+"                       count(distinct(payy.id_card)) as persum,                                                                                                                                                                                    "
				+"                       payy.diagnose_name                                                                                                                                                                                                          "
				+"                  from (select biz.member_id,                                                                                                                                                                                                      "
				+"                               biz.member_type,                                                                                                                                                                                                    "
				+"                               upper(biz.id_card) as id_card,                                                                                                                                                                                      "
				+"                               biz.family_no,                                                                                                                                                                                                      "
				+"                               biz.biz_id,                                                                                                                                                                                                         "
				+"                               pay.pay_total,                                                                                                                                                                                                      "
				+"                               pay.pay_medicare,                                                                                                                                                                                                   "
				+"                               pay.pay_outmedicare,                                                                                                                                                                                                "
				+"                               pay.pay_assist,                                                                                                                                                                                                     "
				+"                               nvl(pay.pay_total - pay.pay_medicare -                                                                                                                                                                              "
				+"                                   pay.pay_ciassist - pay.pay_assist,                                                                                                                                                                              "
				+"                                   0) as pay_self,                                                                                                                                                                                                 "
				+"                               pay.pay_ciassist,                                                                                                                                                                                                   "
				+"                               pay.oper_time,                                                                                                                                                                                                      "
				+"                               biz.person_type || biz.person_typex as persontype,                                                                                                                                                                  "
				+"                               substr(biz.person_type, 1, 1) as a1,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 3, 1) as a3,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 4, 1) as a4,                                                                                                                                                                                "
				+"                               substr(biz.person_type, 5, 1) as a5,                                                                                                                                                                                "
				+"                               substr(biz.person_typex, 1, 1) as a6,                                                                                                                                                                               "
				+"                               biz.diagnose_name                                                                                                                                                                                                   "
				+"                          from jz_biz biz, jz_pay pay                                                                                                                                                                                              "
				+"                         where biz.biz_id = pay.biz_id                                                                                                                                                                                             "
				+"                           and biz.biz_type in ('1', '2')                                                                                                                                                                                          "
				+"                           and biz.biz_status = '1'                                                                                                                                                                                                "
				+"                           and biz.assist_flag = '1'                                                                                                                                                                                               "
				+"                           and pay.sts = '1'                                                                                                                                                                                                       "
				+"                           and substr(biz.person_type, 1, 1)<>'0'                                                                                                                                                                                  "
				+"                        union                                                                                                                                                                                                                      "
				+"                        select ma.member_id,                                                                                                                                                                                                       "
				+"                               ma.member_type,                                                                                                                                                                                                     "
				+"                               upper(ma.id_card) as id_card,                                                                                                                                                                                       "
				+"                               ma.family_no,                                                                                                                                                                                                       "
				+"                               ma.biz_id,                                                                                                                                                                                                          "
				+"                               ma.pay_total,                                                                                                                                                                                                       "
				+"                               ma.pay_medicare,                                                                                                                                                                                                    "
				+"                               ma.pay_outmedicare,                                                                                                                                                                                                 "
				+"                               ma.pay_assist,                                                                                                                                                                                                      "
				+"                               nvl(ma.pay_total - ma.pay_medicare -                                                                                                                                                                                "
				+"                                   ma.pay_assist - ma.pay_ciassist,                                                                                                                                                                                "
				+"                                   0) as pay_self,                                                                                                                                                                                                 "
				+"                               ma.pay_ciassist,                                                                                                                                                                                                    "
				+"                               ma.oper_time,                                                                                                                                                                                                       "
				+"                               ma.assist_type_m || ma.assist_typex as persontype,                                                                                                                                                                  "
				+"                               substr(ma.assist_type_m, 1, 1) as a1,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 3, 1) as a3,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 4, 1) as a4,                                                                                                                                                                               "
				+"                               substr(ma.assist_type_m, 5, 1) as a5,                                                                                                                                                                               "
				+"                               substr(ma.assist_typex, 1, 1) as a6,                                                                                                                                                                                "
				+"                               ma.diagnose_name                                                                                                                                                                                                    "
				+"                          from jz_medicalafter ma                                                                                                                                                                                                  "
				+"                         where ma.biz_status = '1'                                                                                                                                                                                                 "
				+"                           and ma.assist_type in ('1', '2')                                                                                                                                                                                        "
				+"                           and substr(ma.assist_type_m, 1, 1)<>'0'                                                                                                                                                                                 "
				+"                           ) payy                                                                                                                                                                                                                  "
				+"                 where 1 = 1                                                                                                                                                                                                                       "
				+ jwhere
				+"                 group by payy.id_card, payy.diagnose_name) apay                                                                                                                                                                                   "
				+"         where 1 = 1                                                                                                                                                                                                                               "
				+"         group by  apay.pertotal) t) tt                                                                                                                                                                                                            "
				+"         order by length(subsection)                                                                                                                                                                                                               ";
		    ratelist = reportService.findRate(sql);
			session.put("sql", sql);
			HashMap title = new HashMap();	
			//title.put("ROWNUM,val", "序号");	
			title.put("SUBSECTION,val", "人员分类");
			title.put("PERSUM,val", "患者人数");
			title.put("PERTOTAL,val", "对象人数");						
			title.put("RATE,val", "患病率");		
			session.put("title", title);
		return SUCCESS;
	}
	
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setData(List<ReportDTO> data) {
		this.data = data;
	}

	public List<ReportDTO> getData() {
		return data;
	}

	@SuppressWarnings("rawtypes")
	public void setMap(HashMap map) {
		this.map = map;
	}

	@SuppressWarnings("rawtypes")
	public HashMap getMap() {
		return map;
	}

	public void setQuarterlist(List<String> quarterlist) {
		this.quarterlist = quarterlist;
	}

	public List<String> getQuarterlist() {
		return quarterlist;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getExt() {
		return ext;
	}

	public void setReporttype(String reporttype) {
		this.reporttype = reporttype;
	}

	public String getReporttype() {
		return reporttype;
	}

	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	public String getReportname() {
		return reportname;
	}

	@SuppressWarnings("rawtypes")
	public void setReportlist(Map reportlist) {
		this.reportlist = reportlist;
	}

	@SuppressWarnings("rawtypes")
	public Map getReportlist() {
		return reportlist;
	}

	public void setStart_month(String start_month) {
		this.start_month = start_month;
	}

	public String getStart_month() {
		return start_month;
	}

	public void setEnd_month(String end_month) {
		this.end_month = end_month;
	}

	public String getEnd_month() {
		return end_month;
	}

	public void setMonthlist(List<String> monthlist) {
		this.monthlist = monthlist;
	}

	public List<String> getMonthlist() {
		return monthlist;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		return month;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}

	public void setRegionlist(List<OrganizationDTO> regionlist) {
		this.regionlist = regionlist;
	}

	public List<OrganizationDTO> getRegionlist() {
		return regionlist;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setCitys(List<OrganizationDTO> citys) {
		this.citys = citys;
	}

	public List<OrganizationDTO> getCitys() {
		return citys;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setStreets(List<OrganizationDTO> streets) {
		this.streets = streets;
	}

	public List<OrganizationDTO> getStreets() {
		return streets;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setDiagnoselist(List<String> diagnoselist) {
		this.diagnoselist = diagnoselist;
	}

	public List<String> getDiagnoselist() {
		return diagnoselist;
	}

	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	public String getDiagnose() {
		return diagnose;
	}

	public void setRegionlist2(List<OrganizationDTO> regionlist2) {
		this.regionlist2 = regionlist2;
	}

	public List<OrganizationDTO> getRegionlist2() {
		return regionlist2;
	}

	public void setCitys2(List<OrganizationDTO> citys2) {
		this.citys2 = citys2;
	}

	public List<OrganizationDTO> getCitys2() {
		return citys2;
	}

	public String getMembertype() {
		return membertype;
	}

	public void setMembertype(String membertype) {
		this.membertype = membertype;
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

	public PayDTO getPayDTO() {
		return payDTO;
	}

	public void setPayDTO(PayDTO payDTO) {
		this.payDTO = payDTO;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}
	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
	}

	public List<PayDTO> getPaylist01() {
		return paylist01;
	}

	public void setPaylist01(List<PayDTO> paylist01) {
		this.paylist01 = paylist01;
	}

	public List<PayDTO> getPaylist02() {
		return paylist02;
	}

	public void setPaylist02(List<PayDTO> paylist02) {
		this.paylist02 = paylist02;
	}

	public List<PayDTO> getPaylist03() {
		return paylist03;
	}

	public void setPaylist03(List<PayDTO> paylist03) {
		this.paylist03 = paylist03;
	}

	public List<PayDTO> getPaylist04() {
		return paylist04;
	}

	public void setPaylist04(List<PayDTO> paylist04) {
		this.paylist04 = paylist04;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<RateDTO> getRatelist() {
		return ratelist;
	}

	public void setRatelist(List<RateDTO> ratelist) {
		this.ratelist = ratelist;
	}

}
