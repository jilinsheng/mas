package com.mingda.action;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.mingda.common.FileUpload;
import com.mingda.dto.DeptDTO;
import com.mingda.dto.DiagnoseTypeDTO;
import com.mingda.dto.JzMedicalafterfileDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.dto.TempDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.SystemDataService;
import com.mingda.service.TempService;
import com.mingda.webclient.YljzService;
import com.mingda.webclient.model.CiDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AfterAction extends ActionSupport {
	static final Logger log = Logger.getLogger(AfterAction.class);
	private static final long serialVersionUID = 1L;
	private String result;
	private SystemDataService systemDataService;
	private YljzService yljzService;
	private TempDTO tempDTO;
	private List<TempDTO> tempmembers;
	private TempService tempService;
	private List<TempDTO> payviews;
	private String r;
	private List<JzMedicalafterfileDTO> mafiles;
	private List<DeptDTO> depts;
	private List<DiagnoseTypeDTO> diagnosetypes;
	private List<OutIcdDTO> outicds;
	private CiDTO ciDTO;
	private List<File> af;
	private List<String> afFileName;
	private String orgid;
	private TempDTO tempDTOend;

	@SuppressWarnings("rawtypes")
	public String queryaftermemberinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 6) {
			return SUCCESS;
		} else {
			result = "此功能由区县使用！";
			return "result";
		}
	}

	@SuppressWarnings("rawtypes")
	public String queryaftermember() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempDTO.setOrg(organizationId.substring(0, 6));
		tempDTO.setOrganizationId(organizationId);
		tempmembers = tempService.findAftermember(tempDTO);
		tempDTO.setMemberId(tempmembers.get(0).getMemberId());
		tempDTO.setMemberType(tempmembers.get(0).getMemberType());
		payviews = tempService.findPayviews(tempDTO);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String afterapplyinitnew() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempDTO.setOrganizationId(organizationId);
		tempDTO.setBizType("ma");
		Boolean flag = false;
		if (tempDTO.getApproveId() == null) {
			flag = true;
		} else {
			r = tempService.findValBiz(tempDTO);

			if ("0".equals(r)) {
				flag = false;
			} else {
				flag = true;
			}
		}

		if (flag == true) {
			tempDTO = tempService.findAftermeberinfo(tempDTO);
			if (null == tempDTO.getApproveId()) {
			} else {
				mafiles = tempService.findJzMedicalafterfiles(new BigDecimal(
						tempDTO.getApproveId()).toString());
			}
			// 定点医院名称列表
			if (organizationId.length() > 6) {
				organizationId = organizationId.substring(0, 6);
			}
			depts = systemDataService.findDeptsByOrg(organizationId);
			if (null != depts && depts.size() > 0) {
				DeptDTO element = new DeptDTO();
				if (null == tempDTO.getApproveId()) {
				} else {
					element.setHospitalId((int) tempDTO.getHospitalId());
				}
			} else {
				DeptDTO element = new DeptDTO();
				element.setHospitalId(-1);
				element.setName("无");
				depts.add(0, element);
			}
			// 住院重大疾病病种
			diagnosetypes = tempService.findDiagnoseTypesByOrg(organizationId);
			if (null != diagnosetypes && diagnosetypes.size() > 0) {
				DiagnoseTypeDTO element = new DiagnoseTypeDTO();
				if (null == tempDTO.getApproveId()) {
				} else {
					element.setDiagnoseTypeId(tempDTO.getDiagnoseTypeId());
				}
			} else {
				DiagnoseTypeDTO element = new DiagnoseTypeDTO();
				element.setDiagnoseTypeId(0);
				element.setDiagnoseTypeName("无");
				diagnosetypes.add(0, element);
			}
			// 门诊特殊大病病种
			outicds = tempService.findOuticdsByOrg(organizationId);
			if (null != outicds && outicds.size() > 0) {
				OutIcdDTO element = new OutIcdDTO();
				if (null == tempDTO.getApproveId()) {
				} else {
					element.setIcdId(tempDTO.getIcdId());
				}
			} else {
				OutIcdDTO element = new OutIcdDTO();
				element.setIcdId(0);
				element.setName("无");
				outicds.add(0, element);
			}
			return SUCCESS;
		} else {
			payviews = tempService.findPayviews(tempDTO);
			result = "此条信息不允许修改！";
			return "result";
		}

	}

	// 计算医后大病保险
	@SuppressWarnings("rawtypes")
	public String calcaftermoney() {

		JSONObject json = new JSONObject();
		ciDTO = new CiDTO();
		ciDTO.setPaperID(tempDTO.getPaperid());
		if ("3".equals(tempDTO.getMedicareType())) {
			ciDTO.setMedicareType("0");
		} else {
			ciDTO.setMedicareType(tempDTO.getMedicareType());
		}
		ciDTO.setPay_Total(tempDTO.getPayTotal());
		ciDTO.setPay_Medicare(tempDTO.getPayMedicare());
		ciDTO.setPay_OutMedicare(tempDTO.getPayOutmedicare());
		ciDTO.setCalcType(tempDTO.getCalcType());
		ciDTO.setOld_Pay_Total(tempDTO.getOldPayTotal());
		ciDTO.setOld_Pay_Medicare(tempDTO.getOldPayMedicare());
		ciDTO.setOld_Pay_OutMedicare(tempDTO.getOldPayOutMedicare());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ciDTO.setEnd_time(sdf.format(tempDTO.getEndtime()));
		ciDTO = yljzService.getCiAssistByPaperIDEx(ciDTO);
		// 外伤、未经医保/新农合确认的转诊
		if (!"0".equals(tempDTO.getOtherType())) {
			ciDTO.setPayCIAssist(getCia(tempDTO));
		}
		if (tempDTO.getInsurance() == null || "".equals(tempDTO.getInsurance())) {
			tempDTO.setInsurance(new BigDecimal("0"));
		}
		if ("1".equals(ciDTO.getReturnFlag())) {
			tempDTO.setPaySumAssistScopeIn(ciDTO.getPay_Sum_AssistScope_In());
			tempDTO.setPayPreSumAssistScopeIn(ciDTO
					.getPay_PreSum_AssistScope_In());
			tempDTO.setPaySumAssistIn(ciDTO.getPaySumAssistIn());
			tempDTO.setPaySumAssistOut(ciDTO.getPaySumAssistOut());
			tempDTO.setSumMedicareScope(ciDTO.getSumMedicareScope());
			tempDTO.setPayCIAssist(ciDTO.getPayCIAssist());
			if ("2".equals(tempDTO.getAssistype())) {
				HashMap m = tempService.findMaMoney(tempDTO);
				json.put("m", m.get("m"));
				json.put("info", m.get("info"));
				json.put("in", ciDTO.getPaySumAssistIn());
				json.put("out", ciDTO.getPaySumAssistOut());
				json.put("scope", ciDTO.getSumMedicareScope());
				json.put("ci", ciDTO.getPayCIAssist());
				json.put("sum", ciDTO.getPay_Sum_AssistScope_In());
				json.put("preSum", ciDTO.getPay_PreSum_AssistScope_In());
			} else if ("1".equals(tempDTO.getAssistype())) {
				if ("1".equals(tempDTO.getAssistype())
						&& "2".equals(tempDTO.getMedicareType())) {
					HashMap m = tempService.findMaMoney(tempDTO);
					json.put("m", m.get("m"));
					json.put("info", m.get("info"));
					json.put("in", ciDTO.getPaySumAssistIn());
					json.put("out", ciDTO.getPaySumAssistOut());
					json.put("scope", ciDTO.getSumMedicareScope());
					json.put("ci", ciDTO.getPayCIAssist());
					json.put("sum", ciDTO.getPay_Sum_AssistScope_In());
					json.put("preSum", ciDTO.getPay_PreSum_AssistScope_In());
				} else {
					json.put("info", "成功");
					json.put("in", 0);
					json.put("out", 0);
					json.put("scope", 0);
					json.put("ci", 0);
					json.put("sum", 0);
					json.put("preSum", 0);
				}

			} else {
				if ("1".equals(tempDTO.getAssistype())
						&& "1".equals(tempDTO.getJzjButtonFlag())) {
					HashMap m = tempService.findMaMoney(tempDTO);
					json.put("m", m.get("m"));
					json.put("info", m.get("info"));
				} else {
					json.put("info", "成功");
				}
				json.put("in", 0);
				json.put("out", 0);
				json.put("scope", 0);
				json.put("ci", 0);
				json.put("sum", 0);
				json.put("preSum", 0);
			}
		} else {
			json.put("info", "大病保险计算失败!");
		}
		result = json.toString();
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String calcaftermoneyauto2() {
		JSONObject json = new JSONObject();
		ciDTO = new CiDTO();
		ciDTO.setPaperID(tempDTO.getPaperid());
		if ("3".equals(tempDTO.getMedicareType())) {
			ciDTO.setMedicareType("0");
		} else {
			ciDTO.setMedicareType(tempDTO.getMedicareType());
		}
		ciDTO.setPay_Total(tempDTO.getPayTotal());
		ciDTO.setPay_Medicare(tempDTO.getPayMedicare());
		ciDTO.setPay_OutMedicare(tempDTO.getPayOutmedicare());
		ciDTO.setCalcType(tempDTO.getCalcType());
		ciDTO.setOld_Pay_Total(tempDTO.getOldPayTotal());
		ciDTO.setOld_Pay_Medicare(tempDTO.getOldPayMedicare());
		ciDTO.setOld_Pay_OutMedicare(tempDTO.getOldPayOutMedicare());
		ciDTO = yljzService.getCiAssistByPaperID(ciDTO);
		// 外伤、未经医保/新农合确认的转诊
		if ("0".equals(tempDTO.getDiagnoseTypeId())) {
			ciDTO.setPayCIAssist(getCia(tempDTO));
		}
		if ("1".equals(ciDTO.getReturnFlag())) {
			tempDTO.setPaySumAssistScopeIn(ciDTO.getPay_Sum_AssistScope_In());
			tempDTO.setPayPreSumAssistScopeIn(ciDTO
					.getPay_PreSum_AssistScope_In());
			tempDTO.setPaySumAssistIn(ciDTO.getPaySumAssistIn());
			tempDTO.setPaySumAssistOut(ciDTO.getPaySumAssistOut());
			tempDTO.setSumMedicareScope(ciDTO.getSumMedicareScope());
			HashMap m = tempService.findMaMoney(tempDTO);
			json.put("m", m.get("m"));
			json.put("info", m.get("info"));
			json.put("in", ciDTO.getPaySumAssistIn());
			json.put("out", ciDTO.getPaySumAssistOut());
			json.put("scope", ciDTO.getSumMedicareScope());
			json.put("ci", ciDTO.getPayCIAssist());
			json.put("sum", ciDTO.getPay_Sum_AssistScope_In());
			json.put("preSum", ciDTO.getPay_PreSum_AssistScope_In());
		} else {
			json.put("info", "大病保险计算失败!");
		}
		result = json.toString();
		return SUCCESS;
	}

	private BigDecimal getCia(TempDTO tempDTO) {
		BigDecimal bl = BigDecimal.ZERO;// 大病保险金
		BigDecimal mline_y = new BigDecimal("9600");// "医保"起助线
		BigDecimal mline_n = new BigDecimal("6000");// "未经医保/新农合确认转诊"起助线
		BigDecimal payTotal = tempDTO.getPayTotal();
		BigDecimal payOutmedicare = tempDTO.getPayOutmedicare();
		BigDecimal payMedicare = tempDTO.getPayMedicare();
		// 医保
		if ("1".equals(tempDTO.getMedicareType())) {
			if ((payTotal.subtract(payOutmedicare).subtract(payMedicare))
					.compareTo(mline_y) == -1) {
			} else {
				bl = (payTotal.subtract(payOutmedicare).subtract(payMedicare)
						.subtract(mline_y)).multiply(new BigDecimal("0.3"));
			}
			// 新农合
		} else if ("2".equals(tempDTO.getMedicareType())) {
			if ((payTotal.subtract(payOutmedicare).subtract(payMedicare))
					.compareTo(mline_n) == -1) {
			} else {
				bl = (payTotal.subtract(payOutmedicare).subtract(payMedicare)
						.subtract(mline_n)).multiply(new BigDecimal("0.3"));
			}
			// 未参保/参合
		} else {

		}
		return bl;
	}

	// 医后报销申请保存
	@SuppressWarnings("rawtypes")
	public String afterapply() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String orgid = user.getOrganizationId();
		FileUpload fu = new FileUpload("/file/medicalafter");
		mafiles = new ArrayList<JzMedicalafterfileDTO>();
		long sumFilesSize = 0;
		tempDTO.setOrganizationId(orgid);
		tempDTO.setOrg(orgid.substring(0, 6));
		TempDTO temp = tempService.isline(tempDTO);
		if ((("220803".equals(tempDTO.getOrg())) || ("220225".equals(tempDTO
				.getOrg())))
				&& (("1".equals(tempDTO.getAssistTypeM().substring(2, 3))) || ("1"
						.equals(tempDTO.getAssistTypeM().substring(3, 4))))) {
			temp.setResult("1");
		}
		if ("0".equals(temp.getResult())) {
			result = "保障金大于封顶线，您重新填写救助金!<br/>累计总救助金：" + temp.getTotlePay()
					+ "元;<br/>住院总救助金：" + temp.getZyPay() + "元;<br/>门诊大病总救助金："
					+ temp.getMzPay() + "元;<br/>封顶线：" + temp.getTopLine()
					+ "元;";
			return "result";
		} else {
			if (null == af) {
				tempDTO = tempService.saveAfterApplyInfo(tempDTO);
			} else {
				for (int i = 0; i < af.size(); i++) {
					sumFilesSize = sumFilesSize + af.get(i).length();
				}
				if (sumFilesSize / 1024 > 1024) {
					result = "上传图片总大小为：" + (sumFilesSize / 1024)
							+ "KB，超出上线1024KB，请重新上传！";
					return "result";
				} else {
					tempDTO = tempService.saveAfterApplyInfo(tempDTO);
					for (int i = 0; i < af.size(); i++) {
						JzMedicalafterfileDTO filedto = new JzMedicalafterfileDTO();
						String sFileName = afFileName.get(i);
						if (null == sFileName || "".equals(sFileName)) {
						} else {
							filedto.setFilename(sFileName);
							File sFile = af.get(i);
							String dir = fu.filepath + "\\"
									+ tempDTO.getApproveId();
							fu.MakeDir(dir);
							String uu = UUID.randomUUID().toString();
							String bname = tempDTO.getApproveId() + "/" + uu
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
							filedto.setBizId(new BigDecimal(tempDTO
									.getApproveId()));
							mafiles.add(filedto);
						}
					}
					tempService.saveJzMedicalafterfiles(mafiles);
				}
			}
		}
		mafiles = tempService.findJzMedicalafterfiles(new BigDecimal(tempDTO
				.getApproveId()).toString());
		return SUCCESS;

	}
	
	@SuppressWarnings("rawtypes")
	public String viewafterapplys() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempmembers = tempService.findAfterapplys(tempDTO);
		tempDTO.setOrg(organizationId.substring(0, 6));
		return SUCCESS;
	}
	
	public String viewafterapply() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// start 梅河口20131018重大疾病-------------------------------------
		orgid = organizationId.substring(0, 6);
		tempDTO = tempService.findAftermeberinfo(tempDTO);
		tempDTO.setOrg(orgid);
		if (null == tempDTO.getApproveId()) {
		} else {
			mafiles = tempService.findJzMedicalafterfiles(new BigDecimal(
					tempDTO.getApproveId()).toString());
		}
		return SUCCESS;
	}
	
	public String delafterapply() {
		Boolean flag = false;
		if (tempDTO.getCalcType() == 2) {
			tempDTOend = tempService.findPayview01(tempDTO);
			if (tempDTOend.getApproveId() != null
					&& "2".equals(tempDTO.getAssistype())) {
				if (tempDTOend.getApproveId().toString()
						.equals(tempDTO.getApproveId().toString())
						&& "ma".equals(tempDTOend.getBiztype())) {
					flag = true;
				} else {
					flag = false;
				}
			} else {
				flag = true;
			}
		}
		if (tempDTO.getCalcType() == 1 || flag == true) {
			tempService.removeAfterapply(tempDTO);
		}
		if (flag == true) {
			result = "删除成功！";
			return "result";
		} else {
			result = "此条信息不允许删除！";
			return "result";
		}
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public YljzService getYljzService() {
		return yljzService;
	}

	public void setYljzService(YljzService yljzService) {
		this.yljzService = yljzService;
	}

	public TempDTO getTempDTO() {
		return tempDTO;
	}

	public void setTempDTO(TempDTO tempDTO) {
		this.tempDTO = tempDTO;
	}

	public List<TempDTO> getTempmembers() {
		return tempmembers;
	}

	public void setTempmembers(List<TempDTO> tempmembers) {
		this.tempmembers = tempmembers;
	}

	public TempService getTempService() {
		return tempService;
	}

	public void setTempService(TempService tempService) {
		this.tempService = tempService;
	}

	public List<TempDTO> getPayviews() {
		return payviews;
	}

	public void setPayviews(List<TempDTO> payviews) {
		this.payviews = payviews;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public List<JzMedicalafterfileDTO> getMafiles() {
		return mafiles;
	}

	public void setMafiles(List<JzMedicalafterfileDTO> mafiles) {
		this.mafiles = mafiles;
	}

	public List<DeptDTO> getDepts() {
		return depts;
	}

	public void setDepts(List<DeptDTO> depts) {
		this.depts = depts;
	}

	public List<DiagnoseTypeDTO> getDiagnosetypes() {
		return diagnosetypes;
	}

	public void setDiagnosetypes(List<DiagnoseTypeDTO> diagnosetypes) {
		this.diagnosetypes = diagnosetypes;
	}

	public List<OutIcdDTO> getOuticds() {
		return outicds;
	}

	public void setOuticds(List<OutIcdDTO> outicds) {
		this.outicds = outicds;
	}

	public CiDTO getCiDTO() {
		return ciDTO;
	}

	public void setCiDTO(CiDTO ciDTO) {
		this.ciDTO = ciDTO;
	}

	public List<File> getAf() {
		return af;
	}

	public void setAf(List<File> af) {
		this.af = af;
	}

	public List<String> getAfFileName() {
		return afFileName;
	}

	public void setAfFileName(List<String> afFileName) {
		this.afFileName = afFileName;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public TempDTO getTempDTOend() {
		return tempDTOend;
	}

	public void setTempDTOend(TempDTO tempDTOend) {
		this.tempDTOend = tempDTOend;
	}

}
