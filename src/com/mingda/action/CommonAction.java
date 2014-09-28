package com.mingda.action;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.mingda.common.ReadXML;
import com.mingda.dto.BizCheckDTO;
import com.mingda.dto.DeptDTO;
import com.mingda.dto.HospitalPayDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.RoleDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.AuthorityService;
import com.mingda.service.SearchService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 菜单处理...
 * 
 * @author Administrator
 * 
 */
public class CommonAction extends ActionSupport {
	static final Logger log = Logger.getLogger(CommonAction.class);
	private String menustr;
	private List<BizCheckDTO> checks;
	private SearchService searchService;
	// 2010-12-21
	private AuthorityService authorityService;
	private String result;
	// 2010-12-21
	private String toolsmenu;
	private String cur_page;
	private UserDTO userDTO;
	private List<UserDTO> mus;
	// 2010-1-4
	private List<OrganizationDTO> orgs;
	private List<DeptDTO> depts;
	private List<HospitalPayDTO> hpays;
	private HospitalPayDTO hospitalPayDTO;
	private SystemDataService systemDataService;
	private String oid;
	private String hid;
	private String memberId;
	private String paperId;
	private String search;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String readMenu() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		ReadXML readxml = null;
		String org = "";
		if(organizationId.length()>6){
			org = organizationId.substring(0, 6);
		}else{
			org = organizationId;
		}
		if("220506".equals(org)){
			readxml = new ReadXML("com/mingda/common/menu3.xml");
		}else{
			readxml = new ReadXML("com/mingda/common/menu.xml");
		}
		Document menuxml = readxml.readXml();
		Element root = menuxml.getRootElement();
		menustr = root.asXML();
		return SUCCESS;
	}

	// 显示代办业务（住院未审核）
	public String detail() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();

		if (null == cur_page || "".equals(cur_page)) {
			cur_page = "1";
		} else {
		}
		checks = searchService.findNotCheck(organizationId, new BigDecimal(
				cur_page).intValue(), "page/common/detail.action");
		setToolsmenu(searchService.getToolsmenu());
		return SUCCESS;
	}

	// 修改密码
	public String modipwdinit() {
		return SUCCESS;
	}

	public String modipwd() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String spwd = user.getPwd();
		if (spwd.equals(userDTO.getPwd())) {
			if (userDTO.getNewpwd().equals(userDTO.getRepwd())) {
				userDTO.setEmpid(user.getEmpid());
				authorityService.saveUserpwd(userDTO);
				setResult("成功");
				ActionContext.getContext().getSession().remove("user");
			} else {
				setResult("新密码与确认密码不相符！");
			}

		} else {
			setResult("旧密码输入不正确！");
		}
		return SUCCESS;
	}

	// 人员管理
	public String getorgchilds() {
		JSONObject json = new JSONObject();
		List<OrganizationDTO> orgs = systemDataService.findOrgChilds(oid);
		JSONArray arr = new JSONArray();
		for (OrganizationDTO s : orgs) {
			JSONObject jo = JSONObject.fromObject(s);
			arr.add(jo);
		}
		if (2 == oid.length() || 4 == oid.length()) {
			json.put("id", "o2");
			json.put("l", "2");
		} else {
			json.put("id", "o" + (oid.length() / 2));
			json.put("l", "" + (oid.length() / 2));
		}
		json.put("orgs", arr);
		result = json.toString();
		return SUCCESS;
	}

	public String findemps() {
		JSONObject json = new JSONObject();
		List<UserDTO> users = systemDataService.findUsersByOrgan(oid);
		JSONArray arr = new JSONArray();
		for (UserDTO s : users) {
			JSONObject jo = JSONObject.fromObject(s);
			arr.add(jo);
		}
		json.put("users", arr);
		result = json.toString();
		return SUCCESS;
	}

	public String resetemp() {
		systemDataService.saveResetemp(userDTO);
		JSONObject json = new JSONObject();
		json.put("flag", "密码重置成功！");
		result = json.toString();
		return SUCCESS;
	}

	public String modiemp() {
		systemDataService.removeEmp(userDTO);
		JSONObject json = new JSONObject();
		json.put("flag", "账户已删除");
		result = json.toString();
		return SUCCESS;
	}

	public String addemp() {

		String a = systemDataService.saveEmp(userDTO);
		JSONObject json = new JSONObject();
		if ("1".equals(a)) {
			json.put("flag", "新建成功！");
		} else {
			json.put("flag", "账户名成重复，新建不成功！");
		}
		result = json.toString();
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes" })
	public String manageempinit() {
		boolean flag = false;
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		List<RoleDTO> roles = user.getRoles();
		for (RoleDTO s : roles) {
			if ("1".equals(s.getRoleId())) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		if (flag) {
			orgs = this.systemDataService.findOrgParentAndChilds(user
					.getOrganizationId());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String queryempinfoinit() {
		boolean flag = false;
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		List<RoleDTO> roles = user.getRoles();
		for (RoleDTO s : roles) {
			if ("1".equals(s.getRoleId())) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		if (flag) {
			orgs = this.systemDataService.findOrganizationExt(user
					.getOrganizationId());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String queryempinfo() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		mus = systemDataService.findMus(oid);
		orgs = this.systemDataService.findOrganizationExt(user
				.getOrganizationId());
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryhempinfoinit() {
		boolean flag = false;
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		List<RoleDTO> roles = user.getRoles();
		for (RoleDTO s : roles) {
			if ("1".equals(s.getRoleId())) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		if (flag) {
			depts = systemDataService.findDeptsByOrg("");
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String queryhempinfo() {
		mus = systemDataService.findHus(oid);
		depts = systemDataService.findDeptsByOrg("");
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryhospitalpayinit() {
		boolean flag = false;
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		List<RoleDTO> roles = user.getRoles();
		for (RoleDTO s : roles) {
			if ("2".equals(s.getRoleId())) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		if (flag) {

			// 获取机构
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			// 获取医院列表
			depts = systemDataService.findDeptsByOrg2(organizationId);
			if (null != depts && depts.size() > 0) {

			} else {
				DeptDTO element = new DeptDTO();
				element.setHospitalId(-1);
				element.setName("无");
				depts.add(0, element);
			}

			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String queryhospitalpay() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		depts = systemDataService.findDeptsByOrg2(organizationId);
		if (null != depts && depts.size() > 0) {

		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		hpays = systemDataService.findHospitalpays(hid, organizationId);
		return SUCCESS;
	}

	public String queryhospitals() {
		try {
			new URLDecoder();
			depts = systemDataService.findDeptsByOrg3(URLDecoder.decode(search,
					"UTF-8"));
			JSONArray arr = JSONArray.fromObject(depts);
			result = arr.toString();
			//System.out.println(result);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String admin() {
		boolean flag = false;
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		List<RoleDTO> roles = user.getRoles();
		for (RoleDTO s : roles) {
			if ("2".equals(s.getRoleId())) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		if (flag) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public String viewpayinfo() {
		if (-1 == hospitalPayDTO.getHospitalId()) {
		} else {
			hospitalPayDTO = systemDataService.findHospitalpay(hospitalPayDTO);
		}
		return SUCCESS;
	}

	public String savepayinfo() {
		hospitalPayDTO = systemDataService.saveHospitalpay(hospitalPayDTO);
		hospitalPayDTO = new HospitalPayDTO();
		hospitalPayDTO.setHospitalId(-1);
		return SUCCESS;
	}

	public String valpaperid() {
		JSONObject json = new JSONObject();
		HashMap<String, String> h = systemDataService.findvalpaperid(paperId);
		String str1 = systemDataService.findChachong(memberId, paperId);
		// 是否有效
		json.put("val", h.get("flag"));
		// 生日
		json.put("birth", h.get("birth"));
		// 重复
		json.put("cc", str1);
		result = json.toString();
		return SUCCESS;
	}

	// 临时救助对象身份证号验证
	public String valpaperid01() {
		JSONObject json = new JSONObject();
		HashMap<String, String> h = systemDataService.findvalpaperid(paperId);
		// String str1 = systemDataService.findChachong(memberId, paperId);
		// 是否有效
		json.put("val", h.get("flag"));
		// 生日
		json.put("birth", h.get("birth"));
		// 重复
		json.put("cc", "1");
		result = json.toString();
		return SUCCESS;
	}

	public String manageemp() {
		return SUCCESS;
	}

	public String getMenustr() {
		return menustr;
	}

	public void setMenustr(String menustr) {
		this.menustr = menustr;
	}

	public void setChecks(List<BizCheckDTO> checks) {
		this.checks = checks;
	}

	public List<BizCheckDTO> getChecks() {
		return checks;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String curPage) {
		cur_page = curPage;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setOrgs(List<OrganizationDTO> orgs) {
		this.orgs = orgs;
	}

	public List<OrganizationDTO> getOrgs() {
		return orgs;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOid() {
		return oid;
	}

	public List<UserDTO> getMus() {
		return mus;
	}

	public void setMus(List<UserDTO> mus) {
		this.mus = mus;
	}

	public List<DeptDTO> getDepts() {
		return depts;
	}

	public void setDepts(List<DeptDTO> depts) {
		this.depts = depts;
	}

	public List<HospitalPayDTO> getHpays() {
		return hpays;
	}

	public void setHpays(List<HospitalPayDTO> hpays) {
		this.hpays = hpays;
	}

	public HospitalPayDTO getHospitalPayDTO() {
		return hospitalPayDTO;
	}

	public void setHospitalPayDTO(HospitalPayDTO hospitalPayDTO) {
		this.hospitalPayDTO = hospitalPayDTO;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getHid() {
		return hid;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}
