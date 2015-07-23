package com.mingda.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.mingda.common.ExportExcel;
import com.mingda.common.FileUpload;
import com.mingda.dto.DictDTO;
import com.mingda.dto.ExportxlsDTO;
import com.mingda.dto.JizhongDTO;
import com.mingda.dto.MemberBaseinfoviewDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.UserDTO;
import com.mingda.dto.WubaohuDTO;
import com.mingda.dto.WubaohufileDTO;
import com.mingda.service.GuaranteeService;
import com.mingda.service.SearchService;
import com.mingda.service.SummerHandleService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GuaranteeAction extends ActionSupport {
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
	private List<WubaohuDTO> guarantees;
	private List<DictDTO> nations;
	private SearchService searchService;
	private SystemDataService systemDataService;
	private SummerHandleService summerHandleService;
	private JizhongDTO jizhongDTO;
	private String memberId;
	private String ds;
	private String type;
	private List<ExportxlsDTO> es;
	private ExportxlsDTO exportxlsDTO;
	private File wbhfile;
	private String wbhfileFileName;
	private String wbhfileContentType;
	private String fileName;
	private List<HSSFRow> unlist;
	private List<File> wbh;
	private List<String> wbhFileName;
	private List<String> wbhContentType;
	private List<WubaohufileDTO> wbhfiles;
	private GuaranteeService guaranteeService;
	private String fid;
	private WubaohuDTO wubaohuDTO;
	private String a4;
	private String personsts;
	private String flag;

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
			if (!"".equals(personsts)) {
				jwhere = jwhere + " and mem.personstate='" + personsts + "'";
			}
			if (!"".equals(flag)) {
			}
			sql = " select mem.*, org.fullname as ORGNAME, "
					+ " decode(mem.member_id,mem.familyid,'五保户数据库',decode(ds,'1','城市数据库','2','农村数据库')) as comedata "
					+ " from MEMBER_BASEINFOVIEW02 mem, manager_org org where 1 = 1 and mem.a4 = '1' and org.organization_id(+) = substr(mem.familyno, 1, 10)";
			sql = sql + jwhere + "  order by mem.FAMILYNO";
			session.put("sql", sql);
			cur_page = "1";
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("ORGNAME,val", "所属");
			title.put("COMEDATA,val", "数据来源");
			session.put("title", title);
		} else {
			sql = (String) session.get("sql");
		}
		mbs = searchService.findTownMembers(sql,
				new BigDecimal(cur_page).intValue(),
				"page/guarantee/querymembers.action");
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

	// 五保户录入审批
	@SuppressWarnings("rawtypes")
	public String queryguaranteeinit() {
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
	public String queryguarantee() {
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
					jwhere = jwhere + " and  wbh.ssn  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and wbh.familyno  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  wbh.membername  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  wbh.paperid " + var;
				} else {
				}
			}
			if (!"".equals(a4)) {
				jwhere = jwhere + " and substr(wbh.assist_type,4,1)= '" + a4
						+ "' ";
			}
			jwhere = jwhere + " and  wbh.familyno like '" + oid + "%' ";
			sql = "select wbh.* ,substr(wbh.assist_type,4,1) as type, org.orgname from wubaohu wbh, manager_org org "
					+ "where org.serialnumber = substr(wbh.familyno,1,10) "
					+ " and wbh.flag = '1' ";
			sql = sql + jwhere + "  order by wbh.familyno";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		guarantees = summerHandleService.findGuarantee(sql, new BigDecimal(
				cur_page).intValue(), "page/guarantee/queryguarantee.action");
		setToolsmenu(summerHandleService.getToolsmenu());
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
	public String editguaranteeinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		nations = systemDataService.findNations();
		if (null == memberId || "".equals(memberId)) {
			if (6 == organizationId.length()) {
				orgs = systemDataService.findOrgChilds(organizationId);
				return SUCCESS;
			} else {
				return ERROR;
			}
		} else if ("edit".equals(type)) {
			wubaohuDTO = summerHandleService.findWubaohuById(memberId, ds);
			wbhfiles = guaranteeService.findWubaohufiles(memberId);
			result = "1";
			return "edit";
		} else if ("del".equals(type)) {
			jizhongDTO = new JizhongDTO();
			jizhongDTO.setDs(ds);
			jizhongDTO.setId(new BigDecimal(memberId));
			summerHandleService.removeGuarantee(jizhongDTO);
			guaranteeService.delWbnFileByBizId(memberId);
			result = "2";
			return "del";
		}
		return null;

	}

	public String editguarantee() {
		wubaohuDTO = summerHandleService.saveGuarantee(wubaohuDTO);
		FileUpload fu = new FileUpload("/file/wbhsfzm");
		wbhfiles = new ArrayList<WubaohufileDTO>();
		if (null == wbh) {
		} else {
			for (int i = 0; i < wbh.size(); i++) {
				WubaohufileDTO filedto = new WubaohufileDTO();
				String sFileName = wbhFileName.get(i);
				if (null == sFileName || "".equals(sFileName)) {
				} else {
					filedto.setFilename(sFileName);
					File sFile = wbh.get(i);
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
					File swbhFile = new File(realpath);
					try {
						swbhFile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fu.copy(sFile, swbhFile);
					filedto.setBizId(new BigDecimal(wubaohuDTO.getMemberId()
							.toString()));
					wbhfiles.add(filedto);
				}
			}
			guaranteeService.saveWubaohufiles(wbhfiles);
		}
		wbhfiles = guaranteeService.findWubaohufiles(new BigDecimal(wubaohuDTO
				.getMemberId().toString()).toString());
		return SUCCESS;
	}

	public String delfile() {
		guaranteeService.delWbhFile(fid);
		JSONObject json = new JSONObject();
		json.put("r", "删除成功 ！");
		result = json.toString();
		return SUCCESS;

	}

	// 五保户、优抚对象、excel导入

	@SuppressWarnings("rawtypes")
	public String uploadexcel() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String empid = user.getEmpid();
		FileUpload fu = new FileUpload("/file/excel");
		String realpath = fu.filepath + "/" + UUID.randomUUID().toString()
				+ fu.getExtention(wbhfileFileName);
		realpath = realpath.replace("/", "\\\\");
		File swbhFile = new File("\\\\" + realpath);
		try {
			swbhFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fu.copy(wbhfile, swbhFile);
		exportxlsDTO.setEmpId(empid);
		exportxlsDTO.setUploadtime(new Date());
		exportxlsDTO.setOrganizationId(organizationId);
		exportxlsDTO.setOpertime(new Date());
		exportxlsDTO.setRealpath(realpath);
		searchService.saveUploadXLS(exportxlsDTO);
		es = searchService.findEs(empid);

		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String uploadexcel1() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String empid = user.getEmpid();
		FileUpload fu = new FileUpload("/file/excel");
		String realpath = fu.filepath + "/" + UUID.randomUUID().toString()
				+ fu.getExtention(wbhfileFileName);
		realpath = realpath.replace("/", "\\\\");
		File swbhFile = new File("\\\\" + realpath);
		try {
			swbhFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fu.copy(wbhfile, swbhFile);
		exportxlsDTO.setEmpId(empid);
		exportxlsDTO.setUploadtime(new Date());
		exportxlsDTO.setOrganizationId(organizationId);
		exportxlsDTO.setOpertime(new Date());
		exportxlsDTO.setRealpath(realpath);
		searchService.saveUploadXLS(exportxlsDTO);
		es = searchService.findEs1(empid);

		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String uploadexcelinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String empid = user.getEmpid();
		if (6 == organizationId.length()) {
			es = searchService.findEs(empid);
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String uploadexcelinit1() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String empid = user.getEmpid();
		if (6 == organizationId.length()) {
			es = searchService.findEs1(empid);
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String deluploadexcel() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String empid = user.getEmpid();
		searchService.removeUploadXLS(exportxlsDTO);
		if ("1".equals(exportxlsDTO.getEtype())) {
			es = searchService.findEs(empid);
			return "1";
		}
		if ("2".equals(exportxlsDTO.getEtype())) {
			es = searchService.findEs(empid);
			return "2";
		}
		if ("3".equals(exportxlsDTO.getEtype())) {
			es = searchService.findEs1(empid);
			return "3";
		}
		return "";
	}

	public String exportexcel() {
		unlist = searchService.saveExportexcel(exportxlsDTO);
		String f = "";
		try {
			f = new String("导入没有成功数据excel".getBytes("gb2312"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		setFileName("attachment; filename=" + f + ".xls");
		return SUCCESS;
	}

	public InputStream getExcelFile() {
		ByteArrayInputStream bais = null;
		ExportExcel ee = new ExportExcel();
		ByteArrayOutputStream baos = ee.genExcelData(unlist);
		if (null != baos) {
			byte[] ba = baos.toByteArray();
			bais = new ByteArrayInputStream(ba);
		}
		return bais;
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

	public void setSummerHandleService(SummerHandleService summerHandleService) {
		this.summerHandleService = summerHandleService;
	}

	public SummerHandleService getSummerHandleService() {
		return summerHandleService;
	}

	public void setJizhongDTO(JizhongDTO jizhongDTO) {
		this.jizhongDTO = jizhongDTO;
	}

	public JizhongDTO getJizhongDTO() {
		return jizhongDTO;
	}

	public void setNations(List<DictDTO> nations) {
		this.nations = nations;
	}

	public List<DictDTO> getNations() {
		return nations;
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

	public void setEs(List<ExportxlsDTO> es) {
		this.es = es;
	}

	public List<ExportxlsDTO> getEs() {
		return es;
	}

	public void setExportxlsDTO(ExportxlsDTO exportxlsDTO) {
		this.exportxlsDTO = exportxlsDTO;
	}

	public ExportxlsDTO getExportxlsDTO() {
		return exportxlsDTO;
	}

	public File getWbhfile() {
		return wbhfile;
	}

	public void setWbhfile(File wbhfile) {
		this.wbhfile = wbhfile;
	}

	public String getWbhfileFileName() {
		return wbhfileFileName;
	}

	public void setWbhfileFileName(String wbhfileFileName) {
		this.wbhfileFileName = wbhfileFileName;
	}

	public String getWbhfileContentType() {
		return wbhfileContentType;
	}

	public void setWbhfileContentType(String wbhfileContentType) {
		this.wbhfileContentType = wbhfileContentType;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setUnlist(List<HSSFRow> unlist) {
		this.unlist = unlist;
	}

	public List<HSSFRow> getUnlist() {
		return unlist;
	}

	public List<WubaohufileDTO> getWbhfiles() {
		return wbhfiles;
	}

	public void setWbhfiles(List<WubaohufileDTO> wbhfiles) {
		this.wbhfiles = wbhfiles;
	}

	public List<File> getWbh() {
		return wbh;
	}

	public void setWbh(List<File> wbh) {
		this.wbh = wbh;
	}

	public List<String> getWbhFileName() {
		return wbhFileName;
	}

	public void setWbhFileName(List<String> wbhFileName) {
		this.wbhFileName = wbhFileName;
	}

	public List<String> getWbhContentType() {
		return wbhContentType;
	}

	public void setWbhContentType(List<String> wbhContentType) {
		this.wbhContentType = wbhContentType;
	}

	public GuaranteeService getGuaranteeService() {
		return guaranteeService;
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

	public List<WubaohuDTO> getGuarantees() {
		return guarantees;
	}

	public void setGuarantees(List<WubaohuDTO> guarantees) {
		this.guarantees = guarantees;
	}

	public WubaohuDTO getWubaohuDTO() {
		return wubaohuDTO;
	}

	public void setWubaohuDTO(WubaohuDTO wubaohuDTO) {
		this.wubaohuDTO = wubaohuDTO;
	}

	public String getA4() {
		return a4;
	}

	public void setA4(String a4) {
		this.a4 = a4;
	}

	public String getPersonsts() {
		return personsts;
	}

	public void setPersonsts(String personsts) {
		this.personsts = personsts;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
