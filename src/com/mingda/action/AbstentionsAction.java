package com.mingda.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.mingda.common.FileUpload;
import com.mingda.dto.DictDTO;
import com.mingda.dto.MemberBaseinfoviewDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.SanwufileDTO;
import com.mingda.dto.UserDTO;
import com.mingda.dto.WubaohuDTO;
import com.mingda.service.AbstentionsService;
import com.mingda.service.SearchService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AbstentionsAction extends ActionSupport {
	/**
	 * 
	 */
	static final Logger log = Logger.getLogger(AbstentionsAction.class);
	private static final long serialVersionUID = 1L;
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
	private List<WubaohuDTO> sanwus;
	private List<DictDTO> nations;
	private SearchService searchService;
	private SystemDataService systemDataService;
	private WubaohuDTO wubaohuDTO;
	private String memberId;
	private String ds;
	private String type;
	private File absfile;
	private String absfileFileName;
	private String absfileContentType;
	private String fileName;
	private List<File> abs;
	private List<String> absFileName;
	private List<String> absContentType;
	private List<SanwufileDTO> absfiles;
	private AbstentionsService abstentionsService;
	private String fid;
	private String a3;

	@SuppressWarnings("rawtypes")
	public String queryabsmembersinit() {
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
	public String queryabsmembers() {
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
			sql = "select mem.* , ( select org.fullname  from manager_org org  where org.organization_id = substr(mem.familyno, 1, 10)) as orgname  " +
					"from MEMBER_BASEINFOVIEW02 mem where mem.personstate='正常' and mem.a3 = '1' ";
			sql = sql + jwhere + "  order by mem.FAMILYNO";
			session.put("sql", sql);
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		mbs = searchService.findTownMembers(sql,
				new BigDecimal(cur_page).intValue(),
				"page/abstentions/queryabsmembers.action");
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
	// 三无人员录入审批
	@SuppressWarnings("rawtypes")
	public String queryabsinit() {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryabs() {
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
					jwhere = jwhere + " and  wbh.ssn " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and wbh.familyno " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and wbh.membername " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and wbh.paperid " + var;
				} else {
				}
			}
			if (!"".equals(a3)){
				jwhere = jwhere + " and substr(wbh.assist_type,3,1)= '" + a3 + "' ";
			}
			jwhere = jwhere + " and  wbh.familyno like '" + oid + "%' ";
			sql = "select wbh.* , substr(wbh.assist_type,3,1) as type, org.orgname from wubaohu wbh, manager_org org "
					+ " where org.organization_id = substr(wbh.familyno,1,10) "
					+ " and wbh.flag = '1' ";
			sql = sql + jwhere + "  order by wbh.familyno";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		sanwus = abstentionsService.findAbstentions(sql, new BigDecimal(
				cur_page).intValue(), "page/abstentions/queryabs.action");
		setToolsmenu(abstentionsService.getToolsmenu());
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String editabsinit() {
		nations = systemDataService.findNations();
		if (null == memberId || "".equals(memberId)) {
			Map session = ActionContext.getContext().getSession();
			UserDTO user = (UserDTO) session.get("user");
			String organizationId = user.getOrganizationId();
			if (6 == organizationId.length()) {
				orgs = systemDataService.findOrgChilds(organizationId);
				return SUCCESS;
			}else{
				return ERROR;
			}
		} else if ("edit".equals(type)) {
			wubaohuDTO = abstentionsService.findSanwuById(memberId,ds);
			absfiles = abstentionsService.findSanwufiles(memberId);
			result="1";
			return "edit";
		} else if ("del".equals(type)) {
			wubaohuDTO=new WubaohuDTO();
			wubaohuDTO.setDs(ds);
			//sanwuDTO.setId(new BigDecimal(memberId));
			//summerHandleService.removeSpec(sanwuDTO);
			result="2";
			return "del";
		}
		return null;

	}

	public String editabs() {
		wubaohuDTO=abstentionsService.saveAbstentions(wubaohuDTO);
		FileUpload fu = new FileUpload("/file/sanwusfzm");
		absfiles = new ArrayList<SanwufileDTO>();
		if (null == abs) {
		} else {
			for (int i = 0; i < abs.size(); i++) {
				SanwufileDTO filedto = new SanwufileDTO();
				String sFileName = absFileName.get(i);
				if (null == sFileName || "".equals(sFileName)) {
				} else {
					filedto.setFilename(sFileName);
					File sFile = abs.get(i);
					String dir = fu.filepath + "\\" + wubaohuDTO.getMemberId();
					fu.MakeDir(dir);
					String uu = UUID.randomUUID().toString();
					String bname = wubaohuDTO.getMemberId() + "/" + uu
							+ fu.getExtention(sFileName);
					filedto.setRealpath(bname);
					String realpath = dir + "\\" + uu
							+ fu.getExtention(sFileName);
					filedto.setRealfilename(realpath);
					realpath = realpath.replace("/", "\\\\");
					File sabsFile = new File(realpath);
					try {
						sabsFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fu.copy(sFile, sabsFile);
					filedto.setBizId(new BigDecimal(wubaohuDTO.getMemberId()));
					absfiles.add(filedto);
				}
			}
			abstentionsService.saveSanwufiles(absfiles);
		}
		absfiles = abstentionsService.findSanwufiles(wubaohuDTO.getMemberId());
		return SUCCESS;
	}
	
	public String delfile() {
		abstentionsService.delSanwuFile(fid);
		JSONObject json = new JSONObject();
		json.put("r", "删除成功 ！");
		result = json.toString();
		return SUCCESS;

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

	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
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

	public List<MemberBaseinfoviewDTO> getMbs() {
		return mbs;
	}

	public void setMbs(List<MemberBaseinfoviewDTO> mbs) {
		this.mbs = mbs;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public List<DictDTO> getNations() {
		return nations;
	}

	public void setNations(List<DictDTO> nations) {
		this.nations = nations;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public File getAbsfile() {
		return absfile;
	}

	public void setAbsfile(File absfile) {
		this.absfile = absfile;
	}

	public String getAbsfileFileName() {
		return absfileFileName;
	}

	public void setAbsfileFileName(String absfileFileName) {
		this.absfileFileName = absfileFileName;
	}

	public String getAbsfileContentType() {
		return absfileContentType;
	}

	public void setAbsfileContentType(String absfileContentType) {
		this.absfileContentType = absfileContentType;
	}

	public List<File> getAbs() {
		return abs;
	}

	public void setAbs(List<File> abs) {
		this.abs = abs;
	}

	public List<String> getAbsFileName() {
		return absFileName;
	}

	public void setAbsFileName(List<String> absFileName) {
		this.absFileName = absFileName;
	}

	public List<String> getAbsContentType() {
		return absContentType;
	}

	public void setAbsContentType(List<String> absContentType) {
		this.absContentType = absContentType;
	}

	public AbstentionsService getAbstentionsService() {
		return abstentionsService;
	}

	public void setAbstentionsService(AbstentionsService abstentionsService) {
		this.abstentionsService = abstentionsService;
	}

	public List<WubaohuDTO> getSanwus() {
		return sanwus;
	}

	public void setSanwus(List<WubaohuDTO> sanwus) {
		this.sanwus = sanwus;
	}

	public List<SanwufileDTO> getAbsfiles() {
		return absfiles;
	}

	public void setAbsfiles(List<SanwufileDTO> absfiles) {
		this.absfiles = absfiles;
	}

	public WubaohuDTO getWubaohuDTO() {
		return wubaohuDTO;
	}

	public void setWubaohuDTO(WubaohuDTO wubaohuDTO) {
		this.wubaohuDTO = wubaohuDTO;
	}


}
