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
import com.mingda.dto.GuerfileDTO;
import com.mingda.dto.MemberBaseinfoviewDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.UserDTO;
import com.mingda.dto.WubaohuDTO;
import com.mingda.service.GuaranteeService;
import com.mingda.service.OrphanService;
import com.mingda.service.SearchService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class OrphanAction extends ActionSupport {
	/**
	 * 
	 */
	static final Logger log = Logger.getLogger(OrphanAction.class);
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
	private List<WubaohuDTO> orphans;
	private List<DictDTO> nations;
	private SearchService searchService;
	private SystemDataService systemDataService;
	private OrphanService orphanService;
	private WubaohuDTO wubaohuDTO;
	private String memberId;
	private String ds;
	private String type;
	private File orphanfile;
	private String orphanfileFileName;
	private String orphanfileContentType;
	private String fileName;
	private List<File> orphan;
	private List<String> orphanFileName;
	private List<String> orphanContentType;
	private List<GuerfileDTO> orphanfiles;
	private GuaranteeService guaranteeService;
	private String fid;
	private String a3;

	@SuppressWarnings("rawtypes")
	public String queryorphanmembersinit() {
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
	public String queryorphanmembers() {
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
				if ("FAMILYNO".equals(term)) {
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
					"from MEMBER_BASEINFOVIEW02 mem where mem.personstate='正常' and mem.a6 = '1' ";
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
				"page/orphan/queryorphanmembers.action");
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
	//孤儿录入审批
	@SuppressWarnings("rawtypes")
	public String queryorphaninit() {
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
	public String queryorphan() {
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
				jwhere = jwhere + " and substr(wbh.assist_typex,0,1)= '" + a3 + "' ";
			}
			jwhere = jwhere + " and  wbh.familyno like '" + oid + "%' ";
			sql = "select wbh.* ,substr(wbh.assist_typex,0,1) as type, org.orgname from wubaohu wbh, manager_org org "
					+ " where org.organization_id = substr(wbh.familyno,1,10) "
					+ " and wbh.flag = '1' ";
			sql = sql + jwhere + "  order by wbh.familyno";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		orphans = orphanService.findOrphan(sql, new BigDecimal(
				cur_page).intValue(), "page/orphan/queryorphan.action");
		setToolsmenu(orphanService.getToolsmenu());
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
	public String editorphaninit() {
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
			wubaohuDTO = orphanService.findGuerById(memberId,ds);
			orphanfiles = orphanService.findGuerfiles(memberId);
			result="1";
			return "edit";
		} else if ("del".equals(type)) {
			wubaohuDTO=new WubaohuDTO();
			wubaohuDTO.setDs(ds);
			//wubaohuDTO.setId(new BigDecimal(memberId));
			//summerHandleService.removeorphan(jizhongDTO);
			result="2";
			return "del";
		}
		return null;

	}

	public String editorphan() {
		wubaohuDTO=orphanService.saveOrphan(wubaohuDTO);
		FileUpload fu = new FileUpload("/file/guersfzm");
		orphanfiles = new ArrayList<GuerfileDTO>();
		if (null == orphan) {
		} else {
			for (int i = 0; i < orphan.size(); i++) {
				GuerfileDTO filedto = new GuerfileDTO();
				String sFileName = orphanFileName.get(i);
				if (null == sFileName || "".equals(sFileName)) {
				} else {
					filedto.setFilename(sFileName);
					File sFile = orphan.get(i);
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
					File sorphanFile = new File(realpath);
					try {
						sorphanFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fu.copy(sFile, sorphanFile);
					filedto.setBizId(new BigDecimal(wubaohuDTO.getMemberId().toString()));
					orphanfiles.add(filedto);
				}
			}
			orphanService.saveGuerfiles(orphanfiles);
		}
		orphanfiles = orphanService.findGuerfiles(new BigDecimal(wubaohuDTO.getMemberId().toString()).toString());
		return SUCCESS;
	}
	
	public String delfile() {
		orphanService.delGuerFile(fid);
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

	public void setGuaranteeService(GuaranteeService guaranteeService) {
		this.guaranteeService = guaranteeService;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public List<WubaohuDTO> getorphans() {
		return orphans;
	}

	public void setorphans(List<WubaohuDTO> orphans) {
		this.orphans = orphans;
	}

	public File getOrphanfile() {
		return orphanfile;
	}

	public void setOrphanfile(File orphanfile) {
		this.orphanfile = orphanfile;
	}

	public String getOrphanfileFileName() {
		return orphanfileFileName;
	}

	public void setOrphanfileFileName(String orphanfileFileName) {
		this.orphanfileFileName = orphanfileFileName;
	}

	public String getOrphanfileContentType() {
		return orphanfileContentType;
	}

	public void setOrphanfileContentType(String orphanfileContentType) {
		this.orphanfileContentType = orphanfileContentType;
	}

	public List<File> getOrphan() {
		return orphan;
	}

	public void setOrphan(List<File> orphan) {
		this.orphan = orphan;
	}

	public List<String> getOrphanFileName() {
		return orphanFileName;
	}

	public void setOrphanFileName(List<String> orphanFileName) {
		this.orphanFileName = orphanFileName;
	}

	public List<String> getOrphanContentType() {
		return orphanContentType;
	}

	public void setOrphanContentType(List<String> orphanContentType) {
		this.orphanContentType = orphanContentType;
	}

	public List<GuerfileDTO> getOrphanfiles() {
		return orphanfiles;
	}

	public void setOrphanfiles(List<GuerfileDTO> orphanfiles) {
		this.orphanfiles = orphanfiles;
	}

	public GuaranteeService getGuaranteeService() {
		return guaranteeService;
	}

	public List<WubaohuDTO> getOrphans() {
		return orphans;
	}

	public void setOrphans(List<WubaohuDTO> orphans) {
		this.orphans = orphans;
	}

	public OrphanService getOrphanService() {
		return orphanService;
	}

	public void setOrphanService(OrphanService orphanService) {
		this.orphanService = orphanService;
	}

	public String getA3() {
		return a3;
	}

	public void setA3(String a3) {
		this.a3 = a3;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public WubaohuDTO getWubaohuDTO() {
		return wubaohuDTO;
	}

	public void setWubaohuDTO(WubaohuDTO wubaohuDTO) {
		this.wubaohuDTO = wubaohuDTO;
	}

}
