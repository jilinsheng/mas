package com.mingda.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mingda.dto.IcdDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.OuticdService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OuticdAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private OuticdService outicdService;
	private List<OrganizationDTO> orgs;
	private String treemenustr;
	private List<OutIcdDTO> outicds;
	private String organizationId;
	private Integer icdId;
	private String outtype;
	private String calcType;
	private BigDecimal fixValue;
	private BigDecimal scale;
	private Integer seq;
	private String sts;
	private List<IcdDTO> icds;
	private String result;
	private String method;
	private String save;
	private String stop;
	private Boolean flag;

	public String readtreemenu() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		orgs = outicdService.querymenumember(organizationId);
		arr = JSONArray.fromObject(orgs);
		json.put("TREEMENU", arr);
		treemenustr = json.toString();
		//System.out.println(treemenustr);
		return SUCCESS;
	}

	public String detailouticdmember() {
		outicds = outicdService.querydetailouticdmember(organizationId);
		return SUCCESS;
	}

	public String addouticdinfo() {
		icds = new ArrayList<IcdDTO>();
		icds = outicdService.queryicdnameall();
		return SUCCESS;
	}

	public String addouticd() {
		JSONObject json1 = new JSONObject();
		JSONArray arr1 = new JSONArray();
		String err[] = { "" };
		OutIcdDTO outIcdDTO = new OutIcdDTO();
		outIcdDTO.setOrganizationId(organizationId);
		outIcdDTO.setIcdId(icdId);
		outIcdDTO.setOuttype(outtype);
		outIcdDTO.setCalcType(calcType);
		outIcdDTO.setFixValue(fixValue);
		outIcdDTO.setScale(scale);
		outIcdDTO.setSeq(seq);
		outIcdDTO.setSts(sts);
		int results = outicdService.addouticd(outIcdDTO);
		if (results == 0) {
			err[0] = "1";
		} else if (results == 3) {
			err[0] = "3";
		} else if (results == 4) {
			err[0] = "4";
		} else if (results == 5) {
			err[0] = "5";
		} else {
			err[0] = "0";
		}
		arr1 = JSONArray.fromObject(err);
		json1.put("add", arr1);
		result = json1.toString();
		return SUCCESS;
	}

	public String saveouticd() {
		JSONObject json2 = new JSONObject();
		JSONArray arr2 = new JSONArray();
		String err[] = { "" };
		OutIcdDTO outIcdDTO = new OutIcdDTO();
		outIcdDTO.setOrganizationId(organizationId);
		outIcdDTO.setIcdId(icdId);
		outIcdDTO.setOuttype(outtype);
		outIcdDTO.setCalcType(calcType);
		outIcdDTO.setFixValue(fixValue);
		outIcdDTO.setScale(scale);
		outIcdDTO.setSeq(seq);
		outIcdDTO.setSts(sts);
		int rows = outicdService.saveouticd(outIcdDTO, method, flag);
		if (rows == 1) {
			err[0] = "1";
		} else if (rows == 2) {
			err[0] = "2";
		} else {
			err[0] = "0";
		}
		arr2 = JSONArray.fromObject(err);
		json2.put("save", arr2);
		save = json2.toString();
		return SUCCESS;
	}

	public String stopouticd() {
		JSONObject json2 = new JSONObject();
		JSONArray arr2 = new JSONArray();
		String err[] = { "" };
		OutIcdDTO outIcdDTO = new OutIcdDTO();
		outIcdDTO.setOrganizationId(organizationId);
		outIcdDTO.setIcdId(icdId);
		outIcdDTO.setOuttype(outtype);
		if("stop".equals(method)){
			outIcdDTO.setSts("0");
		}else if("start".equals(method)){
			outIcdDTO.setSts("1");
		}
		if (outicdService.saveouticd(outIcdDTO, method, flag) > 0) {
			err[0] = "1";
		} else {
			err[0] = "0";
		}
		arr2 = JSONArray.fromObject(err);
		json2.put("stop", arr2);
		stop = json2.toString();
		return SUCCESS;
	}

	public String getTreemenustr() {
		return treemenustr;
	}

	public void setTreemenustr(String treemenustr) {
		this.treemenustr = treemenustr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OuticdService getOuticdService() {
		return outicdService;
	}

	public void setOuticdService(OuticdService outicdService) {
		this.outicdService = outicdService;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public List<OrganizationDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrganizationDTO> orgs) {
		this.orgs = orgs;
	}

	public List<OutIcdDTO> getOuticds() {
		return outicds;
	}

	public void setOuticds(List<OutIcdDTO> outicds) {
		this.outicds = outicds;
	}

	public List<IcdDTO> getIcds() {
		return icds;
	}

	public void setIcds(List<IcdDTO> icds) {
		this.icds = icds;
	}

	public Integer getIcdId() {
		return icdId;
	}

	public void setIcdId(Integer icdId) {
		this.icdId = icdId;
	}

	public String getOuttype() {
		return outtype;
	}

	public void setOuttype(String outtype) {
		this.outtype = outtype;
	}

	public String getCalcType() {
		return calcType;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	public BigDecimal getFixValue() {
		return fixValue;
	}

	public void setFixValue(BigDecimal fixValue) {
		this.fixValue = fixValue;
	}

	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSave() {
		return save;
	}

	public void setSave(String save) {
		this.save = save;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
