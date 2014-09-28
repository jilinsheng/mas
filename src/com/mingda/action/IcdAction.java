package com.mingda.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mingda.dto.IcdDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.IcdService;
import com.mingda.service.SearchService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class IcdAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private String name;
	private String pycode;
	private String seq;
	private String icdcode;
	private String salvFlag;
	private String result;
	private IcdDTO icdDTO;
	private Integer icdId;
	private String icdName;
	private String icdPycode;
	private String cur_page;
	private String toolsmenu;
	private List<IcdDTO> icds;
	private IcdService icdService;
	private SearchService searchService;
	private String flag;

	// 病种管理
	public String icdmanageinit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		if (2 == organizationId.length()) {
			flag = "1";
		} else {
			flag = "0";
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryicdmember() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		if (2 == organizationId.length()) {
			flag = "1";
		} else {
			flag = "0";
		}
		Map session = ActionContext.getContext().getSession();
		String sql = "";
		String jwhere = "";
		String jorder = "";
		if (null == cur_page || "".equals(cur_page)) {
			String name = icdName;
			String pyCode = icdPycode;
			if ("".equals(name) || name == null) {
			} else {
				jwhere = jwhere + "and icd.NAME like '%" + name + "%'";
			}
			if ("".equals(pyCode) || pyCode == null) {
			} else {
				jwhere = jwhere + "and icd.PYCODE like '%" + pyCode + "%'";
			}
			sql = "select icd.* from ICD10 icd where 1=1 ";
			jorder = " order by icd.name ";
			sql = sql + jwhere + jorder;
			session.put("sql", sql);
			HashMap title = new HashMap();
			title.put("NAME,val", "病种名称");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		icds = icdService.queryicdmember(sql,
				new BigDecimal(cur_page).intValue(),
				"page/icd/queryicdmember.action");
		setToolsmenu(icdService.getToolsmenu());
		return SUCCESS;
	}

	// 修改病种预处理
	public String editicdinfo() {
		icdDTO = icdService.editicdinfo(icdId);
		icdcode = icdDTO.getIcdcode();
		name = icdDTO.getName();
		pycode = icdDTO.getPycode();
		salvFlag = icdDTO.getSalvFlag();
		icdId = icdDTO.getIcdId();
		if (icdDTO != null && icdDTO.getSeq() != null) {
			seq = icdDTO.getSeq().toString();
		}
		return SUCCESS;
	}

	// 修改病种
	public String editicd() {
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		IcdDTO io = new IcdDTO();
		io.setIcdcode(icdcode);
		io.setName(name);
		io.setSalvFlag(salvFlag);
		io.setPycode(pycode);
		io.setSeq(Integer.parseInt(seq));
		String err[] = { "" };
		io.setIcdId(icdId);
		if (icdService.editicd(io) > 0) {
			err[0] = "1";
		} else {
			err[0] = "0";
		}
		arr = JSONArray.fromObject(err);
		json.put("hs", arr);
		result = json.toString();
		return SUCCESS;
	}

	// 新增病种
	public String addicdinit() {
		JSONObject json = new JSONObject();
		json.put("seq", icdService.findIcdSeq());
		result = json.toString();
		return SUCCESS;
	}

	public String addicd() {
		JSONObject json1 = new JSONObject();
		JSONArray arr1 = new JSONArray();
		String err[] = { "" };
		IcdDTO io = new IcdDTO();
		io.setIcdcode("");
		io.setName(name);
		io.setSalvFlag(salvFlag);
		io.setPycode(pycode);
		io.setSeq(Integer.parseInt(seq));
		io.setIcdId(icdId);
		if (icdService.addicd(io) > 0) {
			err[0] = "1";
		} else {
			err[0] = "0";
		}
		arr1 = JSONArray.fromObject(err);
		json1.put("add", arr1);
		result = json1.toString();
		return SUCCESS;
	}

	public List<IcdDTO> getIcds() {
		return icds;
	}

	public void setIcds(List<IcdDTO> icds) {
		this.icds = icds;
	}

	public IcdService getIcdService() {
		return icdService;
	}

	public void setIcdService(IcdService icdService) {
		this.icdService = icdService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getIcdName() {
		return icdName;
	}

	public void setIcdName(String icdName) {
		this.icdName = icdName;
	}

	public String getIcdPycode() {
		return icdPycode;
	}

	public void setIcdPycode(String icdPycode) {
		this.icdPycode = icdPycode;
	}

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public Integer getIcdId() {
		return icdId;
	}

	public void setIcdId(Integer icdId) {
		this.icdId = icdId;
	}

	public IcdDTO getIcdDTO() {
		return icdDTO;
	}

	public void setIcdDTO(IcdDTO icdDTO) {
		this.icdDTO = icdDTO;
	}

	public String getResult() {
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPycode() {
		return pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getIcdcode() {
		return icdcode;
	}

	public void setIcdcode(String icdcode) {
		this.icdcode = icdcode;
	}

	public String getSalvFlag() {
		return salvFlag;
	}

	public void setSalvFlag(String salvFlag) {
		this.salvFlag = salvFlag;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
