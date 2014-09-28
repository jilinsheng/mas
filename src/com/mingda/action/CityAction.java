package com.mingda.action;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mingda.dto.MemberBaseinfoviewDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.SearchService;
import com.mingda.service.SummerHandleService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CityAction extends ActionSupport {
	static final Logger log = Logger.getLogger(CityAction.class);
	private String oid;
	private String term;
	private String toolsmenu;
	private String value;
	private String totalstr;
	private String result;
	private String cur_page;
	private String operational;
	private List<OrganizationDTO> orgs;
	private List<MemberBaseinfoviewDTO> mbs;
	private String memberId;
	private SearchService searchService;
	private SystemDataService systemDataService;
	private SummerHandleService summerHandleService;
	private String wubaohuFlag;
	private String youfuFlag;
	private String sanwuFlag;
	private String guerFlag;
	private MemberBaseinfoviewDTO memberBaseinfoviewDTO;
	private String assistType;
	private String assistTypex;
	private String medicaretype;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	public String querymembersinit() {
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
	public String querymembers() {
		Map session = ActionContext.getContext().getSession();
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
			jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			sql = " select mem.*, org.*, med.medicare_type "
					+ " from MEMBER_BASEINFOVIEW02 mem "
					+ " left join member_medicare med on mem.member_id=med.member_id and mem.ds=med.member_type "
					+ " left join manager_org org on org.depth = 5 "
					+ " and org.organization_id =  substr(mem.familyno, 1, 10) "
					+ " where mem.personstate = 'Õý³£' " + " and 1=1 "
					+ " and mem.ds = '1' ";
			sql = sql + jwhere + "  order by mem.FAMILYNO";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		mbs = searchService.findCityMembers(sql,
				new BigDecimal(cur_page).intValue(),
				"page/city/querymembers.action");
		setToolsmenu(searchService.getToolsmenu());
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}
	
	public String edittypeinit(){
		if(memberId == null || "".equals(memberId)){
		}else{
			memberBaseinfoviewDTO = searchService.edittypeinit(memberId,"1");
			medicaretype = memberBaseinfoviewDTO.getMedicaretype();
			assistType = memberBaseinfoviewDTO.getAssistType();
			assistTypex = memberBaseinfoviewDTO.getAssistTypex();
			String a3 = assistType.substring(2, 3);
			String a4 = assistType.substring(3, 4);
			String a5 = assistType.substring(4, 5);
			String a6 = assistTypex.substring(0, 1);
			if("1".equals(a4)){
				wubaohuFlag = "1";
			}else if("0".equals(a4)){
				wubaohuFlag = "0";
			}
			if("1".equals(a5)){
				youfuFlag = "1";
			}else if("0".equals(a5)){
				youfuFlag = "0";
			}
			if("1".equals(a3)){
				sanwuFlag = "1";
			}else if("0".equals(a3)){
				sanwuFlag = "0";
			}
			if("1".equals(a6)){
				guerFlag = "1";
			}else if("0".equals(a6)){
				guerFlag = "0";
			}
		}
		return SUCCESS;
	}
	
	public String edittype(){
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		String err[] = { "" };
		int i = searchService.editType(memberId, "1", wubaohuFlag, youfuFlag, assistType, sanwuFlag, guerFlag, assistTypex, medicaretype);
		if (i == 1) {
			err[0] = "1";
		} else {
			err[0] = "0";
		}
		arr = JSONArray.fromObject(err);
		json.put("hs", arr);
		result = json.toString();
		return SUCCESS;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public SearchService getSearchService() {
		return searchService;
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

	public List<OrganizationDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrganizationDTO> orgs) {
		this.orgs = orgs;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setMbs(List<MemberBaseinfoviewDTO> mbs) {
		this.mbs = mbs;
	}

	public List<MemberBaseinfoviewDTO> getMbs() {
		return mbs;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public SummerHandleService getSummerHandleService() {
		return summerHandleService;
	}

	public void setSummerHandleService(SummerHandleService summerHandleService) {
		this.summerHandleService = summerHandleService;
	}

	public String getWubaohuFlag() {
		return wubaohuFlag;
	}

	public void setWubaohuFlag(String wubaohuFlag) {
		this.wubaohuFlag = wubaohuFlag;
	}

	public String getYoufuFlag() {
		return youfuFlag;
	}

	public void setYoufuFlag(String youfuFlag) {
		this.youfuFlag = youfuFlag;
	}

	public MemberBaseinfoviewDTO getMemberBaseinfoviewDTO() {
		return memberBaseinfoviewDTO;
	}

	public void setMemberBaseinfoviewDTO(MemberBaseinfoviewDTO memberBaseinfoviewDTO) {
		this.memberBaseinfoviewDTO = memberBaseinfoviewDTO;
	}

	public String getAssistType() {
		return assistType;
	}

	public void setAssistType(String assistType) {
		this.assistType = assistType;
	}

	public String getSanwuFlag() {
		return sanwuFlag;
	}

	public void setSanwuFlag(String sanwuFlag) {
		this.sanwuFlag = sanwuFlag;
	}

	public String getAssistTypex() {
		return assistTypex;
	}

	public void setAssistTypex(String assistTypex) {
		this.assistTypex = assistTypex;
	}

	public String getGuerFlag() {
		return guerFlag;
	}

	public void setGuerFlag(String guerFlag) {
		this.guerFlag = guerFlag;
	}

	public String getMedicaretype() {
		return medicaretype;
	}

	public void setMedicaretype(String medicaretype) {
		this.medicaretype = medicaretype;
	}

}
