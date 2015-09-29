package com.mingda.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.mingda.common.FileUpload;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.PolicyDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.PolicyService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class PolicyAction extends ActionSupport {
	static final Logger log = Logger.getLogger(PolicyAction.class);
	private static final long serialVersionUID = 1L;
	private String result;
	private PolicyDTO policyDTO;
	private List<File> pf;
	private List<String> pfFileName;
	private List<String> pfContentType;
	private PolicyService policyService;
	private SystemDataService systemDataService;
	private List<PolicyDTO> policylist;
	private OrganizationDTO odto;
	@SuppressWarnings("rawtypes")
	private List<HashMap> filerealpaths;
	@SuppressWarnings("rawtypes")
	private List<HashMap> filerealpaths_file;
	private List<OrganizationDTO> odtos;
	private List<OrganizationDTO> odtoc;
	private String orgid;
	private String toolsmenu;
	private String totalstr;
	private String cur_page;
	private String pid;
	private String opertime1;
	private String opertime2;

	@SuppressWarnings("rawtypes")
	public String uploadpolicyinit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length()||2 == organizationId.length()) {
			return SUCCESS;
		} else {
			result = "没有操作权限！";
			return "result";
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String uploadpolicy(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String orgid = user.getEmpname();
		FileUpload fu = new FileUpload("/file/policyfile");
		policyDTO.setOrganizationid(orgid);
		policyDTO.setStatus("1");
		policyDTO.setCreatTime(new Date());
		policyDTO.setOperTime(policyDTO.getCreatTime());
			if (null == pf) {
				/*policyDTO.setFileNum("0");
				policyDTO = policyService.savePolicyFiles(policyDTO);*/
				result = "上传文件格式不正确，请上传.jpg、.doc、.docx、.pdf格式！";
				return "result";
			} else {
				policyDTO.setFileNum(pf.size()+"");
				policyDTO = policyService.savePolicyFiles(policyDTO);
				for (int i = 0; i < pf.size(); i++) {
					String filetype = pfFileName.get(i).split("\\.")[1];
					String sFileName = (i+1) + "." + filetype;
					if (null == sFileName || "".equals(sFileName)) {
					} else {
						File sFile = pf.get(i);
						String dir = fu.filepath + "\\"
								+ policyDTO.getPolicyId();
						fu.MakeDir(dir);
						String realpath = dir + "\\" + sFileName;
						realpath = realpath.replace("/", "\\\\");
						File pFile = new File(realpath);
						try {
							pFile.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
						fu.copy(sFile, pFile);
					}
				}
			}
		policyDTO = policyService.findPolicy(policyDTO);
		result = "业务政策发布成功！";
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String querypolicyinit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (10 >= organizationId.length()) {
			return SUCCESS;
		} else {
			result = "没有操作权限！";
			return "result";
		}
	}
	
	public String queryorgparent(){
		JSONObject json = new JSONObject();
		odtos = policyService.findOrgALL();
		json.put("r", odtos);
		result = json.toString();
		return SUCCESS;
	}
	
	public String queryorgchild(){
		JSONObject json = new JSONObject();
		String sql = " select a.organization_id, a.serialnumber, a.orgname, a.fullname, bb.uploadnum "
				+ " from manager_org a "
				+ " left join (select b.organizationid, count(*) as uploadnum "
				+ " from org_policy b "
				+ " where b.status = '1' "
				+ " group by b.organizationid) bb "
				+ " on a.serialnumber = bb.organizationid "
				+ " where 1=1 "
				+ " and a.parentorgid='" + orgid +"' "
				+ " and a.status = '1' "
				+ " order by a.organization_id ";
		policylist = policyService.findPolicyList(sql);
		//odtoc = systemDataService.findOrgChilds(orgid);
		json.put("c", policylist);
		result = json.toString();
		return SUCCESS;
	}
	
	public String querypolicybyorgid(){
		policylist = policyService.findPolicyByOrgid(policyDTO);
		odto  = policyService.getOrgName(policyDTO.getOrganizationid());
		return SUCCESS;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querypolicybyid(){
		policyDTO = policyService.findPolicy(policyDTO);
		String folder = policyDTO.getPolicyId();
		String fnum = policyDTO.getFileNum();
		filerealpaths = new ArrayList<HashMap>();
		filerealpaths_file = new ArrayList<HashMap>();
		File file = new File("//10.1.1.101/i/ftproot/yljz/policyfile/"+folder);
		String[] filelist = file.list();
		if(filelist!=null){
			for (int i = 0; i < filelist.length; i++) {
				File readfile = new File(file + "/" + filelist[i]);
	            if (!readfile.isDirectory()) {
	            	String filetype = getFileExtension(readfile);
	            	HashMap m =new HashMap();
	            	if("jpg".equals(filetype)){
						m.put("realpath", folder+"/"+readfile.getName());
						m.put("name", readfile.getName());
						filerealpaths.add(m);
	            	}else{
	            		m.put("realpath", folder+"/"+readfile.getName());
						m.put("name", readfile.getName());
						filerealpaths_file.add(m);
	            	}
					
	            }
			}
		}else{
			HashMap m =new HashMap();
			m.put("name", "无图片");
			filerealpaths.add(m);
		}
/*		if("".equals(fnum)||fnum==null){
		}else{
			for(int i=0;i<Integer.parseInt(fnum);i++){
				HashMap m =new HashMap();
				m.put("realpath", folder+"/"+(i+1)+".jpg");
				filerealpaths.add(m);
			}
		}*/
		return SUCCESS;
	}
	
	/**
	 * 获取文件扩展名
	 * @param file
	 * @return
	 */
	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String queryselfpolicyinit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 >= organizationId.length()) {
			return SUCCESS;
		} else {
			result = "没有操作权限！";
			return "result";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querypolicy(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		String jwhere = " and op.organizationid like '"+organizationId+"%'";
		if ((opertime1.equals("") || null == opertime1)
				&& (opertime2.equals("") || null == opertime2)) {
		} else if (opertime1.equals("") || null == opertime1) {
			jwhere = jwhere + " and op.creat_time > TO_DATE('"
					+ opertime2.substring(0, 10)
					+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";

		} else if (opertime2.equals("") || null == opertime2) {
			jwhere = jwhere + "and op.creat_time < TO_DATE('"
					+ opertime1.substring(0, 10)
					+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') ";

		} else {
			jwhere = jwhere + " and op.creat_time BETWEEN TO_DATE('"
					+ opertime1.substring(0, 10)
					+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') AND TO_DATE('"
					+ opertime2.substring(0, 10)
					+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
		}
		if (null == cur_page || "".equals(cur_page)) {
			sql = "select * from org_policy op where 1=1 " + jwhere;
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		policylist = policyService.findpolicybyorgid(sql,
				new BigDecimal(cur_page).intValue(),
				"page/policy/querypolicy.action");
		setToolsmenu(policyService.getToolsmenu());
		return SUCCESS;
	}
	
	public String deletpolicy(){
		JSONObject json = new JSONObject();
		Integer id = Integer.parseInt(pid);
		int i = policyService.deletepolicybyid(id);
		if(i==1){
			json.put("r", "删除成功！");
		}else{
			json.put("r", "删除失败！");
		}
		result = json.toString();
		return SUCCESS;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public PolicyDTO getPolicyDTO() {
		return policyDTO;
	}

	public void setPolicyDTO(PolicyDTO policyDTO) {
		this.policyDTO = policyDTO;
	}

	public PolicyService getPolicyService() {
		return policyService;
	}

	public void setPolicyService(PolicyService policyService) {
		this.policyService = policyService;
	}

	public List<File> getPf() {
		return pf;
	}

	public void setPf(List<File> pf) {
		this.pf = pf;
	}

	public List<String> getPfFileName() {
		return pfFileName;
	}

	public void setPfFileName(List<String> pfFileName) {
		this.pfFileName = pfFileName;
	}

	public List<String> getPfContentType() {
		return pfContentType;
	}

	public void setPfContentType(List<String> pfContentType) {
		this.pfContentType = pfContentType;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public List<PolicyDTO> getPolicylist() {
		return policylist;
	}

	public void setPolicylist(List<PolicyDTO> policylist) {
		this.policylist = policylist;
	}

	public OrganizationDTO getOdto() {
		return odto;
	}

	public void setOdto(OrganizationDTO odto) {
		this.odto = odto;
	}

	@SuppressWarnings("rawtypes")
	public List<HashMap> getFilerealpaths() {
		return filerealpaths;
	}

	@SuppressWarnings("rawtypes")
	public void setFilerealpaths(List<HashMap> filerealpaths) {
		this.filerealpaths = filerealpaths;
	}

	public List<OrganizationDTO> getOdtos() {
		return odtos;
	}

	public void setOdtos(List<OrganizationDTO> odtos) {
		this.odtos = odtos;
	}

	public List<OrganizationDTO> getOdtoc() {
		return odtoc;
	}

	public void setOdtoc(List<OrganizationDTO> odtoc) {
		this.odtoc = odtoc;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getTotalstr() {
		return totalstr;
	}

	public void setTotalstr(String totalstr) {
		this.totalstr = totalstr;
	}

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	@SuppressWarnings("rawtypes")
	public List<HashMap> getFilerealpaths_file() {
		return filerealpaths_file;
	}

	@SuppressWarnings("rawtypes")
	public void setFilerealpaths_file(List<HashMap> filerealpaths_file) {
		this.filerealpaths_file = filerealpaths_file;
	}
}
