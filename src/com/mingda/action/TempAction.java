package com.mingda.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.mingda.common.FileUpload;
import com.mingda.dto.BillDTO;
import com.mingda.dto.DeptDTO;
import com.mingda.dto.DiagnoseTypeDTO;
import com.mingda.dto.DictDTO;
import com.mingda.dto.JzMedicalafterRuleDTO;
import com.mingda.dto.JzMedicalafterfileDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.dto.SecondApproveDTO;
import com.mingda.dto.SecondBatchDTO;
import com.mingda.dto.SecondBillDTO;
import com.mingda.dto.TempApprovefileDTO;
import com.mingda.dto.TempBillDTO;
import com.mingda.dto.TempDTO;
import com.mingda.dto.TempMonthDTO;
import com.mingda.dto.TempRuleDTO;
import com.mingda.dto.TempSecondDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.SystemDataService;
import com.mingda.service.TempService;
import com.mingda.webclient.YljzService;
import com.mingda.webclient.model.AfterDTO;
import com.mingda.webclient.model.CiDTO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TempAction extends ActionSupport {
	static final Logger log = Logger.getLogger(TempAction.class);
	private static final long serialVersionUID = 1L;
	private String oid;
	private String term;
	private String toolsmenu;
	private String value;
	private String totalstr;
	private String cur_page;
	private String operational;
	private String app;
	private String impl;
	private TempDTO tempDTO;
	private List<TempDTO> tempmembers;
	private List<OrganizationDTO> orgs;
	private String result;
	private SystemDataService systemDataService;
	private TempService tempService;
	private List<OutIcdDTO> outicds;
	private List<DictDTO> nations;
	private List<TempMonthDTO> months;
	private TempMonthDTO tempMonthDTO;
	private List<TempMonthDTO> years;
	private String fileName;
	private List<TempBillDTO> bills;
	private List<TempSecondDTO> seconds;
	private TempSecondDTO tempSecondDTO;
	private SecondApproveDTO secondApproveDTO;
	private List<SecondApproveDTO> sas;
	private List<SecondBatchDTO> sbs;
	private List<SecondBillDTO> sbills;
	private SecondBatchDTO secondBatchDTO;
	private String opertime1;
	private String opertime2;
	private String opertime3;
	private String opertime4;
	private TempRuleDTO tempRuleDTO;
	private String fid;
	private List<File> af;
	private List<String> afFileName;
	private List<String> afContentType;
	private List<JzMedicalafterfileDTO> mafiles;
	private List<File> ta;
	private List<String> taFileName;
	private List<String> taContentType;
	private List<TempApprovefileDTO> tafiles;
	private String dis;
	private String souce;
	private int orgIdLength;
	private YljzService yljzService;
	private CiDTO ciDTO;
	private TempDTO tempDTOend;
	private JzMedicalafterRuleDTO jzMedicalafterRuleDTO;
	private List<TempDTO> payviews;
	private String r;
	private List<DeptDTO> depts;
	private List<DiagnoseTypeDTO> diagnosetypes;
	private AfterDTO afterDTO;
	private String assistype;
	private String assismoney1;
	private String assismoeny2;
	private String method;
	private String biztype;
	private List<JzMedicalafterfileDTO> pics;
	private List<BillDTO> mabills;
	@SuppressWarnings("rawtypes")
	private HashMap map;
	private String hid;
	private String htype;
	// start 梅河口20131018重大疾病-------------------------------------
	private String orgid;
	private String diagnosetype;
	private String implsts;
	// end 梅河口20131018重大疾病-------------------------------------
	private String minpay;
	private String bizstatus;
	private String ds;

	@SuppressWarnings("rawtypes")
	public String createtemppersoninit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		orgs = systemDataService.findOrgChilds(organizationId);
		setNations(systemDataService.findNations());
		return SUCCESS;
	}

	public String createtempperson() {
		tempDTO = tempService.saveTempPerson(tempDTO);
		// tempDTO.setA1("0");
		tempmembers = new ArrayList<TempDTO>();
		tempmembers.add(tempDTO);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String createtemppersoninit1() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 8) {
			orgs = systemDataService.findOrgChilds(organizationId);
			setNations(systemDataService.findNations());
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return "result";
		}
	}

	public String createtempperson1() {
		tempDTO = tempService.saveTempPerson(tempDTO);
		tempDTO.setA1("0");
		tempmembers = new ArrayList<TempDTO>();
		tempmembers.add(tempDTO);
		return SUCCESS;
	}

	public String updatetempperson() {
		// 查询相应业务
		String sql_c = "SELECT * FROM TEMP_APPROVE APP where app.member_id='"
				+ tempDTO.getMemberId() + "'";
		String sql_u = "update temp_approve app " + " set app.member_type = '"
				+ tempDTO.getMemberType() + "' " + " where app.member_id = '"
				+ tempDTO.getMemberId() + "' ";
		int counts = tempService.findTempAppCounts(sql_c);
		tempDTO = tempService.saveTempPerson(tempDTO);
		if (counts > 0) {
			tempService.upTempApp(sql_u);
		}
		BigDecimal id = new BigDecimal(tempDTO.getMemberId());
		if (id.compareTo(BigDecimal.ZERO) == 0) {
			result = "修改失败！";
		} else if (id.compareTo(BigDecimal.ONE) == 0) {
			result = "修改成功！";
		} else if (id.compareTo(BigDecimal.ONE) == 1) {
			result = "保存成功！";
		} else {
			result = "保存失败！";
		}

		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String updatetemppersoninit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		orgs = systemDataService.findOrgChilds(organizationId);
		setNations(systemDataService.findNations());
		tempDTO.setOrganizationId(organizationId);
		tempDTO = tempService.findTempmeberinfo(tempDTO);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querytemppersoninit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 8) {
			// 获取机构
			if (6 == organizationId.length() || 8 == organizationId.length()) {
				if (2 == organizationId.length()) {
					orgs = systemDataService
							.findOrganizationExt(organizationId);
				} else {
					orgs = systemDataService
							.findOrgParentAndChilds(organizationId);
				}
			}
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return "result";
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querytempperson() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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

			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.organizationid like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  mem.organizationid like '" + oid
						+ "%' ";
			}
			sql = "select * from temp_person mem where 1=1 " + jwhere;
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findTempperson(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/querytemperson.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}

		return SUCCESS;
	}

	// 临时救助：申请查询
	@SuppressWarnings("rawtypes")
	public String querytempmember() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempDTO.setOrganizationId(organizationId);
		tempmembers = tempService.findTempmember(tempDTO);
		if (null != tempmembers && tempmembers.size() > 0) {
			return SUCCESS;
		} else {
			orgs = systemDataService.findOrgChilds(organizationId);
			setNations(systemDataService.findNations());
			return "createtempperson";
		}
	}

	@SuppressWarnings("rawtypes")
	public String querytempmemberinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 8) {
			return SUCCESS;
		} else {
			result = "此功能由乡镇街道使用！";
			return "result";
		}
	}

	// 临时救助申请（乡镇）

	@SuppressWarnings("rawtypes")
	public String tempapplyinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempDTO = tempService.findTempmeberinfo(tempDTO);
		outicds = systemDataService.findSickens(organizationId.substring(0, 6),
				"1", "1", "");
		return SUCCESS;

	}

	// 临时救助申请（乡镇）
	public String tempapply() {
		FileUpload fu = new FileUpload("/file/tempfile");
		tafiles = new ArrayList<TempApprovefileDTO>();
		long sumFilesSize = 0;
		if (null == ta) {
		} else {
			for (int i = 0; i < ta.size(); i++) {
				sumFilesSize = sumFilesSize + ta.get(i).length();
			}
			if (sumFilesSize / 1024 > 1024) {
				result = "上传图片总大小为：" + (sumFilesSize / 1024)
						+ "KB，超出上线1024KB，请重新上传！";
				return "result";
			} else {
				/*
				 * int count = tempService.findTaAppCount(tempDTO); if(count>0){
				 * result = "此人有审批中数据，无法再次申请！"; return "result"; }else{
				 */
				tempDTO = tempService.saveTempApplyInfo(tempDTO);
				for (int i = 0; i < ta.size(); i++) {
					TempApprovefileDTO filedto = new TempApprovefileDTO();
					String sFileName = taFileName.get(i);
					if (null == sFileName || "".equals(sFileName)) {
					} else {
						filedto.setFilename(sFileName);
						File sFile = ta.get(i);
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
						filedto.setBizId(new BigDecimal(tempDTO.getApproveId()));
						tafiles.add(filedto);
					}
				}
				tempService.saveTempApprovefiles(tafiles);
				/* } */
			}
		}
		tafiles = tempService.findTempApprovefiles(new BigDecimal(tempDTO
				.getApproveId()).toString());

		tempDTO = tempService.findTempmeberinfo(tempDTO);
		return SUCCESS;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querytempapprove() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
			if ("".equals(app)) {
			} else {
				jwhere = jwhere + " and t.approvests ='" + app + "'";
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.organizationid like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  mem.organizationid like '" + oid
						+ "%' ";
			}
			sql = "select t.pay_total,t.pay_medicare,t.pay_assist, t.implsts, t.approve_id ,t.member_id,t.member_type ,t.applytime ,t.approvests,mem.membername,mem.familyno,mem.paperid,mem.relmaster  "
					+ ", mem.personstate, mem.assist_type, t.pay_outmedicare, t.hospitalname, mem.assist_typex, "
					+ " decode(t.yb_sicken, null, '无', t.yb_sicken) || '>>' || decode(t.yb_sickenval, null, '无', t.yb_sickenval) || '>>' || "
					+ " decode(t.ts_sicken, null, '无', t.ts_sicken) || '>>' || decode(t.ts_sickenval, null, '无', t.ts_sickenval) as DIAGNOSE_NAME "
					+ " from temp_approve t, temp_person mem where mem.member_id = t.member_id "
					+ " and mem.member_type = t.member_type   and (t.implsts is null or t.implsts='1') "
					+ jwhere + " order by mem.familyno";
			session.put("sql", sql);
			cur_page = "1";
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("DIAGNOSE_NAME,val", "患病名称");
			title.put("PAY_TOTAL,val", "总费用");
			title.put("PAY_MEDICARE,val", "统筹");
			title.put("PAY_ASSIST,val", "医疗救助");
			title.put("PAY_OUTMEDICARE,val", "目录外费用");
			title.put("HOSPITALNAME,val", "就诊医院");
			session.put("title", title);
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findTempapprove(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/querytempapprove.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querytempapproveinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 6) {
			// 获取机构
			if (6 == organizationId.length() || 8 == organizationId.length()) {
				if (2 == organizationId.length()) {
					orgs = systemDataService
							.findOrganizationExt(organizationId);
				} else {
					orgs = systemDataService
							.findOrgParentAndChilds(organizationId);
				}
			}
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return "result";
		}

	}

	// 临时救助申请
	@SuppressWarnings("rawtypes")
	public String tempapproveinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		orgIdLength = organizationId.length();
		tempDTO.setBizType("ta");
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
			tempDTO = tempService.findTempmeberinfo(tempDTO);
			tafiles = tempService.findTempApprovefiles(new BigDecimal(tempDTO
					.getApproveId()).toString());
			return SUCCESS;
		} else {
			payviews = tempService.findPayviews(tempDTO);
			result = "此条信息不允许修改！";
			return "result";
		}
		/*
		 * tempDTOend = tempService.findPayview01(tempDTO);
		 * if(tempDTOend.getApproveId() != null){ if
		 * (tempDTOend.getApproveId().toString
		 * ().equals(tempDTO.getApproveId().toString()) &&
		 * "ta".equals(tempDTOend.getBiztype())){ flag=true; }else{ flag=false;
		 * } }else{ flag=true; } if(flag==true){ tempDTO =
		 * tempService.findTempmeberinfo(tempDTO); tafiles =
		 * tempService.findTempApprovefiles(new BigDecimal(tempDTO
		 * .getApproveId()).toString()); return SUCCESS; }else{ payviews =
		 * tempService.findPayviews(tempDTO); result="此条信息不允许修改！"; return
		 * "result"; }
		 */

	}

	// 临时救助申请
	public String tempapprove() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		tempDTO.setOrganizationId(organizationId);
		FileUpload fu = new FileUpload("/file/tempfile");
		tafiles = new ArrayList<TempApprovefileDTO>();
		long sumFilesSize = 0;
		if (null == ta) {
			tempDTO = tempService.saveTempApproveInfo(tempDTO);
		} else {
			for (int i = 0; i < ta.size(); i++) {
				sumFilesSize = sumFilesSize + ta.get(i).length();
			}
			if (sumFilesSize / 1024 > 1024) {
				result = "上传图片总大小为：" + (sumFilesSize / 1024)
						+ "KB，超出上线1024KB，请重新上传！";
				return "result";
			} else {
				tempDTO = tempService.saveTempApproveInfo(tempDTO);
				for (int i = 0; i < ta.size(); i++) {
					TempApprovefileDTO filedto = new TempApprovefileDTO();
					String sFileName = taFileName.get(i);
					if (null == sFileName || "".equals(sFileName)) {
					} else {
						filedto.setFilename(sFileName);
						File sFile = ta.get(i);
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
						filedto.setBizId(new BigDecimal(tempDTO.getApproveId()));
						tafiles.add(filedto);
					}
				}
				tempService.saveTempApprovefiles(tafiles);
			}
		}
		tafiles = tempService.findTempApprovefiles(new BigDecimal(tempDTO
				.getApproveId()).toString());

		tempDTO = tempService.findTempmeberinfo(tempDTO);
		return SUCCESS;
	}

	// 临时救助 修改
	@SuppressWarnings("rawtypes")
	public String viewtempappupdateinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		orgIdLength = organizationId.length();
		tempDTO.setBizType("ta");
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
			tempDTO = tempService.findTempmeberinfo(tempDTO);
			tafiles = tempService.findTempApprovefiles(new BigDecimal(tempDTO
					.getApproveId()).toString());
			return SUCCESS;
		} else {
			payviews = tempService.findPayviews(tempDTO);
			result = "此条信息不允许修改！";
			return "result";
		}
		/*
		 * tempDTOend = tempService.findPayview01(tempDTO); if(tempDTOend !=
		 * null){ if
		 * (tempDTOend.getApproveId().toString().equals(tempDTO.getApproveId
		 * ().toString()) && "ta".equals(tempDTOend.getBiztype())){ flag=true;
		 * }else{ flag=false; } }else{ flag=true; } if(flag==true){ tempDTO =
		 * tempService.findTempmeberinfo(tempDTO); tafiles =
		 * tempService.findTempApprovefiles(new BigDecimal(tempDTO
		 * .getApproveId()).toString()); return SUCCESS; }else{ payviews =
		 * tempService.findPayviews(tempDTO); result="此条信息不允许修改！"; return
		 * "result"; }
		 */
	}

	// 临时救助审批查询

	@SuppressWarnings("rawtypes")
	public String querytempsinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();

		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	public String viewtemp() {
		tempDTO = tempService.findTempmeberinfo(tempDTO);
		tafiles = tempService.findTempApprovefiles(new BigDecimal(tempDTO
				.getApproveId()).toString());
		return SUCCESS;
	}

	// 计算保障金额
	@SuppressWarnings("rawtypes")
	public String calctempmoney() {
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
		if ("1".equals(ciDTO.getReturnFlag())) {
			tempDTO.setPaySumAssistIn(ciDTO.getPaySumAssistIn());
			tempDTO.setPaySumAssistOut(ciDTO.getPaySumAssistOut());
			tempDTO.setSumMedicareScope(ciDTO.getSumMedicareScope());
			tempDTO.setPayCIAssist(ciDTO.getPayCIAssist());
			HashMap m = tempService.findtempmoney(tempDTO);
			json.put("in", ciDTO.getPaySumAssistIn());
			json.put("out", ciDTO.getPaySumAssistOut());
			json.put("scope", ciDTO.getSumMedicareScope());
			json.put("ci", ciDTO.getPayCIAssist());
			json.put("m", m.get("m"));
			json.put("info", m.get("info"));
			log.debug("####>>>>" + json.toString());
		} else {
			json.put("info", "大病保险计算失败!");
		}
		result = json.toString();
		return SUCCESS;

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
		ciDTO = yljzService.getCiAssistByPaperID(ciDTO);
		// 外伤、未经医保/新农合确认的转诊
		if (tempDTO.getDiagnoseTypeId() != 0) {
			ciDTO.setPayCIAssist(getCia(tempDTO));
		}
		System.out.println(tempDTO.getMedicareType());
		System.out.println(tempDTO.getAssistype());
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

	public String calcaftermoneyauto2(){
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
		if (tempDTO.getDiagnoseTypeId() != 0) {
			ciDTO.setPayCIAssist(getCia(tempDTO));
		}
		if ("1".equals(ciDTO.getReturnFlag())) {
			tempDTO.setPaySumAssistScopeIn(ciDTO.getPay_Sum_AssistScope_In());
			tempDTO.setPayPreSumAssistScopeIn(ciDTO
					.getPay_PreSum_AssistScope_In());
			tempDTO.setPaySumAssistIn(ciDTO.getPaySumAssistIn());
			tempDTO.setPaySumAssistOut(ciDTO.getPaySumAssistOut());
			tempDTO.setSumMedicareScope(ciDTO.getSumMedicareScope());
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
	
	private BigDecimal getCia(TempDTO tempDTO) {
		BigDecimal bl = BigDecimal.ZERO;// 大病保险金
		BigDecimal mline_y = new BigDecimal("8000");// "医保"起助线
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

	// 计算医后救助金自动
	@SuppressWarnings("rawtypes")
	public String calcaftermoneyauto() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String assisttype = tempDTO.getAssistTypeM() + tempDTO.getAssistTypex()
				+ "";
		String organizationId = user.getOrganizationId();
		if (null != organizationId && !"".equals(organizationId)) {
			organizationId = organizationId.substring(0, 6);
		}
		JSONObject json = new JSONObject();
		if (!"00000000000".equals(assisttype)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			afterDTO = new AfterDTO();
			afterDTO.setOrgCode(organizationId);
			afterDTO.setHospital_ID(new Long(tempDTO.getHospitalId()));
			afterDTO.setMedicareType(tempDTO.getMedicareType());
			afterDTO.setMemberType(tempDTO.getMemberType());
			afterDTO.setMemberID(tempDTO.getMemberId());
			afterDTO.setMedicareType(tempDTO.getMedicareType());
			afterDTO.setBizType(new Integer(tempDTO.getAssistype()));
			afterDTO.setSpecBiz(tempDTO.getSpecBiz());
			afterDTO.setBegin_Time(sdf.format(tempDTO.getBegintime()));
			afterDTO.setEnd_Time(sdf.format(tempDTO.getEndtime()));
			afterDTO.setDiagnose_Type_ID(new Integer(tempDTO
					.getDiagnoseTypeId()));
			afterDTO.setIcd_ID(new Integer(tempDTO.getIcdId()));
			afterDTO.setPay_Total(tempDTO.getPayTotal());
			afterDTO.setPay_Medicare(tempDTO.getPayMedicare());
			afterDTO.setPay_OutMedicare(tempDTO.getPayOutmedicare());
			afterDTO.setPay_Sybx(tempDTO.getInsurance());
			afterDTO.setPay_Dbbx(tempDTO.getPayCIAssist());
			afterDTO = yljzService.getAssistMoneyAfter(afterDTO);
			if ("1".equals(afterDTO.getReturnFlag())) {
				if ("2".equals(tempDTO.getAssistype())) {
					json.put("m", afterDTO.getAssistMoney());
					json.put("info", afterDTO.getMessage());
					json.put("in", afterDTO.getAssistSumIn());
					json.put("out", afterDTO.getAssistSumOut());
					json.put("ci", afterDTO.getAssistCIA());
					json.put("sum", afterDTO.getAssistSum());
					json.put("calcmsg", afterDTO.getCalcMsg());
				} else {
					json.put("m", afterDTO.getAssistMoney());
					json.put("info", afterDTO.getMessage());
					json.put("in", afterDTO.getAssistSumIn());
					json.put("out", afterDTO.getAssistSumOut());
					json.put("ci", afterDTO.getAssistCIA());
					json.put("sum", afterDTO.getAssistSum());
					json.put("calcmsg", afterDTO.getCalcMsg());
				}

			} else {
				json.put("info", "救助金计算失败!");
			}
		} else {
			json.put("info", "普通居民不在救助范围内！");
		}
		result = json.toString();
		return SUCCESS;
	}

	// 临时救助生成发放文件 查询当前临时救助 人数金额
	@SuppressWarnings("rawtypes")
	public String createtempbillinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 6) {
			// 获取机构
			if (6 == organizationId.length() || 8 == organizationId.length()) {
				if (2 == organizationId.length()) {
					orgs = systemDataService
							.findOrganizationExt(organizationId);
				} else {
					orgs = systemDataService
							.findOrgParentAndChilds(organizationId);
				}
			}
			result = this.tempService.findTempStatInfo(organizationId);
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return "result";
		}

	}

	// 临时救助生成发放文件 查询当前临时救助 人数金额
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String createtempbill() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and mem.organizationid   like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  mem.organizationid like '" + oid
						+ "%' ";
			}
			if (null == app || "".equals(app)) {

			} else if ("1".equals(app)) {
				jwhere = jwhere + " and  t.member_type='1' ";
			} else if ("2".equals(app)) {
				jwhere = jwhere + " and  t.member_type='2' ";
			}
			sql = "select t.pay_total,t.pay_medicare,t.pay_assist, t.implsts, t.approve_id ,t.member_id,t.member_type ,t.applytime ,t.approvests,mem.membername,mem.familyno,mem.paperid,mem.relmaster,mem.assist_typex,mem.assist_type "
					+ " from temp_approve t, temp_person mem where mem.member_id = t.member_id "
					+ " and mem.member_type = t.member_type  and t.approvests ='2' and (t.implsts is null or t.implsts='1') "
					+ jwhere + " order by mem.familyno";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findTempapprove(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/createtempbill.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		result = this.tempService.findTempStatInfo(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String createmabillinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 6) {
			// 获取机构
			if (6 == organizationId.length() || 8 == organizationId.length()) {
				if (2 == organizationId.length()) {
					orgs = systemDataService
							.findOrganizationExt(organizationId);
				} else {
					orgs = systemDataService
							.findOrgParentAndChilds(organizationId);
				}
			}
			result = this.tempService.findMaStatInfo(organizationId);
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return "result";
		}

	}

	// 临时救助生成发放文件 查询当前临时救助 人数金额
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String createmabill() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
					jwhere = jwhere + " and t.family_no  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  t.name  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  t.id_card " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and t.family_no   like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  t.family_no like '" + oid + "%' ";
			}
			if (null == app || "".equals(app)) {

			} else if ("1".equals(app)) {
				jwhere = jwhere + " and  t.member_type='1' ";
			} else if ("2".equals(app)) {
				jwhere = jwhere + " and  t.member_type='2' ";
			}
			sql = "select t.pay_total,t.pay_medicare,t.pay_assist, t.implsts, t.biz_id as approve_id ,t.member_id,t.member_type ,t.assist_time as applytime ,"
					+ "t.biz_status as approvests,t.name as membername,t.family_no as familyno,t.id_card as paperid, t.diagnose_name as relmaster  "
					+ " from jz_medicalafter t where   t.biz_status = '1' and (t.implsts is null or t.implsts='1') "
					+ jwhere + " order by t.family_no";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findMaapprove(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/createmabill.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		result = this.tempService.findMaStatInfo(organizationId);
		return SUCCESS;
	}

	// 临时救助审批查询

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querytemps() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		orgIdLength = organizationId.length();
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
			if ("".equals(app)) {
			} else {
				jwhere = jwhere + " and t.approvests ='" + app + "'";
			}
			if (null == souce || "".equals(souce)) {

			} else if ("1".equals(souce)) {
				jwhere = jwhere + " and  t.member_type='1' ";
			} else if ("2".equals(souce)) {
				jwhere = jwhere + " and  t.member_type='2' ";
			}
			if ("".equals(impl)) {

			} else if ("1".equals(impl)) {
				jwhere = jwhere + " and (t.implsts is null or t.implsts='1')";
			} else if ("2".equals(impl)) {
				jwhere = jwhere + " and t.implsts ='2'";
			}
			if ((opertime1.equals("") || null == opertime1)
					&& (opertime2.equals("") || null == opertime2)) {
			} else if (opertime1.equals("") || null == opertime1) {
				jwhere = jwhere + " and t.applytime > TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";

			} else if (opertime2.equals("") || null == opertime2) {
				jwhere = jwhere + "and t.applytime < TO_DATE('"
						+ opertime1.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') ";

			} else {
				jwhere = jwhere + " and t.applytime BETWEEN TO_DATE('"
						+ opertime1.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') AND TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}
			if ((opertime3.equals("") || null == opertime3)
					&& (opertime4.equals("") || null == opertime4)) {
			} else if (opertime3.equals("") || null == opertime3) {
				jwhere = jwhere + " and t.approvetime > TO_DATE('"
						+ opertime4.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";

			} else if (opertime4.equals("") || null == opertime4) {
				jwhere = jwhere + "and t.approvetime < TO_DATE('"
						+ opertime3.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') ";

			} else {
				jwhere = jwhere + " and t.approvetime BETWEEN TO_DATE('"
						+ opertime3.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') AND TO_DATE('"
						+ opertime4.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.organizationid like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  mem.organizationid like '" + oid
						+ "%' ";
			}
			sql = "select t.implsts, t.approve_id ,t.member_id,t.member_type ,t.applytime ,t.approvests,"
					+ " t.hospitalname, "
					// +
					// " decode(t.yb_sicken, null, '无', t.yb_sicken) || '>>' || decode(t.yb_sickenval, null, '无', t.yb_sickenval) || '>>' || "
					// +
					// " decode(t.ts_sicken, null, '无', t.ts_sicken) || '>>' || decode(t.ts_sickenval, null, '无', t.ts_sickenval) as DIAGNOSE_NAME ,"
					+ " decode(t.yb_sicken, null, '无', substr(t.yb_sicken,0,length(t.yb_sicken)-1)) as yb_sicken ,"
					+ " decode(t.yb_sickenval, null, '无', t.yb_sickenval) as yb_sickenval ,"
					+ " decode(t.ts_sicken, null, '无', substr(t.ts_sicken,0,length(t.ts_sicken)-1)) as ts_sicken ,"
					+ " decode(t.ts_sickenval, null, '无', t.ts_sickenval) as ts_sickenval ,"
					+ " mem.membername,mem.familyno,mem.paperid,mem.relmaster,mem.address, t.pay_total,t.pay_medicare,t.pay_outmedicare,t.pay_ciassist,t.pay_assist, "
					+ " mem.personstate, mem.assist_type, mem.assist_typex "
					+ " from temp_approve t,  temp_person mem where mem.member_id = t.member_id "
					+ " and mem.member_type = t.member_type    "
					+ jwhere
					+ " order by mem.familyno";
			session.put("sql", sql);
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			// title.put("DIAGNOSE_NAME,val", "患病名称");
			title.put("YB_SICKEN,val", "一般慢性病");
			title.put("YB_SICKENVAL,val", "一般慢性病（其他）");
			title.put("TS_SICKEN,val", "特殊慢性病");
			title.put("TS_SICKENVAL,val", "特殊慢性病（其他）");
			title.put("HOSPITALNAME,val", "就诊医院");
			title.put("PAY_TOTAL,val", "总费用");
			title.put("PAY_ASSIST,val", "救助金");
			title.put("PAY_MEDICARE,val", "统筹");
			title.put("PAY_OUTMEDICARE,val", "目录外");
			title.put("PAY_CIASSIST,val", "大病保险费用");
			title.put("ADDRESS,val", "住址");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findTempapprove(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/querytemps.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	// 生成发放数据
	@SuppressWarnings("rawtypes")
	public String genbilldatainit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		months = tempService.findTempMonths(organizationId);
		GregorianCalendar g = new GregorianCalendar();
		int year = (int) g.get(Calendar.YEAR);
		years = new ArrayList<TempMonthDTO>();
		for (int i = 0; i <= year - 2009; i++) {
			TempMonthDTO e = new TempMonthDTO();
			e = new TempMonthDTO();
			e.setYear(new BigDecimal(2009 + i).toString());
			e.setOrganizationId(new BigDecimal(2009 + i).toString() + "年");
			years.add(e);
		}
		result = this.tempService.findTempStatInfo(organizationId);
		char a = result.charAt(3);
		if (a == '0') {
			dis = "0";
		} else {
			dis = "1";
		}
		return SUCCESS;
	}

	// 生成发放数据
	@SuppressWarnings("rawtypes")
	public String genbilldata() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempMonthDTO.setOrganizationId(organizationId);
		tempService.saveGenBillData(tempMonthDTO, app);
		return genbilldatainit();
	}

	// 生成发放数据
	@SuppressWarnings("rawtypes")
	public String genmabilldatainit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		months = tempService.findMaMonths(organizationId);
		GregorianCalendar g = new GregorianCalendar();
		int year = (int) g.get(Calendar.YEAR);
		years = new ArrayList<TempMonthDTO>();
		for (int i = 0; i <= year - 2009; i++) {
			TempMonthDTO e = new TempMonthDTO();
			e = new TempMonthDTO();
			e.setYear(new BigDecimal(2009 + i).toString());
			e.setOrganizationId(new BigDecimal(2009 + i).toString() + "年");
			years.add(e);
		}
		result = this.tempService.findMaStatInfo(organizationId);
		char a = result.charAt(3);
		if (a == '0') {
			dis = "0";
		} else {
			dis = "1";
		}
		return SUCCESS;
	}

	// 生成发放数据
	@SuppressWarnings("rawtypes")
	public String genmabilldata() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempMonthDTO.setOrganizationId(organizationId);
		tempService.saveGenMaBillData(tempMonthDTO, app);
		return genmabilldatainit();
	}

	@SuppressWarnings("rawtypes")
	public String removebillmonth() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempMonthDTO.setOrganizationId(organizationId);
		result = tempService.removeBillMonth(tempMonthDTO);
		return genbilldatainit();
	}

	@SuppressWarnings("rawtypes")
	public String removemabillmonth() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempMonthDTO.setOrganizationId(organizationId);
		result = tempService.removeMaBillMonth(tempMonthDTO);
		return genmabilldatainit();
	}

	// 补录：申请查询
	@SuppressWarnings("rawtypes")
	public String queryaddmember() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempDTO.setOrganizationId(organizationId);
		tempmembers = tempService.findAddmember(tempDTO);
		return SUCCESS;
	}

	// 补录：申请查询
	@SuppressWarnings("rawtypes")
	public String queryaddmemberinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 6) {
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return "result";
		}
	}

	// 事后补录：
	@SuppressWarnings("rawtypes")
	public String queryaftermemberinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 8) {
			return SUCCESS;
		} else {
			result = "此功能由乡镇街道使用！";
			return "result";
		}
	}

	@SuppressWarnings("rawtypes")
	public String queryaftermember() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// test
		tempDTO.setOrg(organizationId.substring(0, 6));
		tempDTO.setOrganizationId(organizationId);
		tempmembers = tempService.findAftermember(tempDTO);
		tempDTO.setMemberId(tempmembers.get(0).getMemberId());
		tempDTO.setMemberType(tempmembers.get(0).getMemberType());
		payviews = tempService.findPayviews(tempDTO);
		return SUCCESS;
	}

	public String addapplyinit() {
		tempDTO = tempService.findAddmeberinfo(tempDTO);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String afterapplyinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempDTO.setOrganizationId(organizationId);
		tempDTO.setBizType("ma");
		jzMedicalafterRuleDTO = tempService.findMedicalafterRule(tempDTO);
		if (jzMedicalafterRuleDTO.getRuleId() != null) {
			tempDTO.setDbButtonFlag("0");
			tempDTO.setJzjButtonFlag("1");
		} else {
			tempDTO.setDbButtonFlag("1");
			tempDTO.setJzjButtonFlag("0");
		}
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
					element.setHospitalId(-1);
					element.setName("请选择....");
				} else {
					element.setHospitalId(-1);
					element.setName("请选择....");
					element.setHospitalId((int) tempDTO.getHospitalId());
				}
				depts.add(0, element);
			} else {
				DeptDTO element = new DeptDTO();
				element.setHospitalId(0);
				element.setName("无");
				depts.add(0, element);
			}
			// 住院重大疾病病种
			diagnosetypes = tempService.findDiagnoseTypesByOrg(organizationId);
			if (null != diagnosetypes && diagnosetypes.size() > 0) {
				DiagnoseTypeDTO element = new DiagnoseTypeDTO();
				if (null == tempDTO.getApproveId()) {
					element.setDiagnoseTypeId(0);
					element.setDiagnoseTypeName("请选择....");
				} else {
					element.setDiagnoseTypeId(tempDTO.getDiagnoseTypeId());
				}
				diagnosetypes.add(0, element);
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
					element.setIcdId(0);
					element.setName("请选择....");
				} else {
					element.setIcdId(tempDTO.getIcdId());
				}
				outicds.add(0, element);
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
		/*
		 * if (tempDTO.getCalcType()==2) { tempDTOend =
		 * tempService.findPayview01(tempDTO); if(tempDTOend.getApproveId() !=
		 * null && "2".equals(tempDTO.getAssistype())){ if
		 * (tempDTOend.getApproveId
		 * ().toString().equals(tempDTO.getApproveId().toString()) &&
		 * "ma".equals(tempDTOend.getBiztype())){ flag=true; }else{ flag=false;
		 * result="此条信息不允许修改！"; } }else{ flag=true; } }
		 * if(tempDTO.getCalcType()==1 || flag==true){ tempDTO =
		 * tempService.findAftermeberinfo(tempDTO); if (null ==
		 * tempDTO.getApproveId()) { } else { mafiles =
		 * tempService.findJzMedicalafterfiles(new BigDecimal(
		 * tempDTO.getApproveId()).toString()); } flag=true;
		 * 
		 * } if(flag==true){ return SUCCESS; }else{ payviews =
		 * tempService.findPayviews(tempDTO);
		 * 
		 * return "result"; }
		 */

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

	public String afteryn() {
		tempDTO = tempService.saveAfteryn(tempDTO);
		result = "保存成功";
		return SUCCESS;
	}

	// 保存补录审批信息
	public String addapply() {
		tempDTO = tempService.saveAddApplyInfo(tempDTO);
		return SUCCESS;

	}

	public String delfile() {
		tempService.delMaFile(fid);
		JSONObject json = new JSONObject();
		json.put("r", "删除成功 ！");
		result = json.toString();
		return SUCCESS;

	}

	public String deltafile() {
		tempService.delTaFile(fid);
		JSONObject json = new JSONObject();
		json.put("r", "删除成功 ！");
		result = json.toString();
		return SUCCESS;

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
		tempDTO.setOrg(orgid.substring(0, 6));
		TempDTO temp = tempService.isline(tempDTO);
		if ("0".equals(temp.getResult())) {
			result = "保障金大于封顶线，您重新填写救助金!<br/>累计总救助金：" + temp.getTotlePay()
					+ "元;<br/>住院总救助金：" + temp.getZyPay() + "元;<br/>门诊大病总救助金："
					+ temp.getMzPay() + "元;<br/>封顶线：" + temp.getTopLine()
					+ "元;";
			return "result";
		} else {
			if (orgid.length() >= 8) {
				tempDTO.setBizStatus("-1");
			} else if (orgid.length() <= 6) {
				tempDTO.setBizStatus("1");
			}
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
					/*
					 * int count = tempService.findMaAppCount(tempDTO);
					 * if(count>0 && tempDTO.getCalcType()==1){ result =
					 * "此人有审批中数据，无法再次申请！"; return "result"; }else{
					 */
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
					/* } */
				}
			}
		}
		mafiles = tempService.findJzMedicalafterfiles(new BigDecimal(tempDTO
				.getApproveId()).toString());
		return SUCCESS;

	}

	public String deladdapply() {
		tempService.removeAddapply(tempDTO);
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

	public String viewaddapplys() {
		tempmembers = tempService.findAddapplys(tempDTO);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String viewafterapplys() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempmembers = tempService.findAfterapplys(tempDTO);
		// test
		tempDTO.setOrg(organizationId.substring(0, 6));
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryaddapproveinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 6) {
			if (6 == organizationId.length() || 8 == organizationId.length()) {
				if (2 == organizationId.length()) {
					orgs = systemDataService
							.findOrganizationExt(organizationId);
				} else {
					orgs = systemDataService
							.findOrgParentAndChilds(organizationId);
				}
			}
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return "result";
		}
	}

	@SuppressWarnings("rawtypes")
	public String queryafterapproveinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();

		if (6 == organizationId.length() || 8 == organizationId.length()) {
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

	@SuppressWarnings("rawtypes")
	public String queryafterapprovedoneinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();

		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryaddapprove() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}
			sql = "select mem.membername, mem.paperid, mem.familyno,  a.member_id, "
					+ " a.member_type,  cs, pay_total,  pay_medicare, pay_outmedicare, "
					+ " pay_assist from MEMBER_BASEINFOVIEW02 mem, "
					+ " (select t.member_id, t.member_type,  count(*) as cs, "
					+ " sum(nvl(t.pay_total, 0)) as pay_total, "
					+ " sum(nvl(t.pay_medicare, 0)) as pay_medicare, "
					+ " sum(nvl(t.pay_outmedicare, 0)) as pay_outmedicare, "
					+ " sum(nvl(t.pay_assist, 0)) as pay_assist "
					+ " from jz_addassistdata t group by (t.member_id, t.member_type)) a "
					+ " where mem.member_id = a.member_id and mem.ds = a.member_type "
					+ jwhere + " order by mem.familyno";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findAddapprove(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/queryaddapprove.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryafterapprove() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mem.familyno like '" + oid + "%' ";
			}
			if (null == app || "".equals(app)) {

			} else if ("1".equals(app)) {
				jwhere = jwhere + " and  a.member_type='1' ";
			} else if ("2".equals(app)) {
				jwhere = jwhere + " and  a.member_type='2' ";
			}
			String jwhere1 = "";
			if ((opertime1.equals("") || null == opertime1)
					&& (opertime2.equals("") || null == opertime2)) {
			} else if (opertime1.equals("") || null == opertime1) {
				jwhere1 = jwhere1 + " and t.oper_time > TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";

			} else if (opertime2.equals("") || null == opertime2) {
				jwhere1 = jwhere1 + "and t.oper_time < TO_DATE('"
						+ opertime1.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') ";

			} else {
				jwhere1 = jwhere1 + " and t.oper_time BETWEEN TO_DATE('"
						+ opertime1.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') AND TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}
			if (null == assistype || "".equals(assistype)) {

			} else {
				jwhere1 = jwhere1 + " and t.assist_type = '" + assistype + "'";
			}

			sql = "select mem.membername, mem.paperid, mem.familyno,  a.member_id, "
					+ " a.member_type,  cs, pay_total,  pay_medicare, pay_outmedicare, "
					+ " pay_assist , pay_ciassist, mem.address, mem.rpraddress ,  a.diagnose_name, a.hospital_name, mem.personstate, mem.assist_type, mem.assist_typex, mem.orgname1, mem.orgname2 "
					+ " from "
					+ " (select a.*,org1.orgname as orgname1 ,org2.orgname as orgname2 from MEMBER_BASEINFOVIEW02 a "
					+ " left join manager_org org1 "
					+ " on org1.depth = 4 "
					+ " and org1.organization_id = substr(a.familyno, 1, 8) "
					+ " left join manager_org org2 "
					+ " on org2.depth = 5 "
					+ " and org2.organization_id = substr(a.familyno, 1, 10))mem, "
					+ " (select t.member_id, t.member_type,  count(*) as cs, "
					+ " sum(nvl(t.pay_total, 0)) as pay_total, "
					+ " sum(nvl(t.pay_medicare, 0)) as pay_medicare, "
					+ " sum(nvl(t.pay_outmedicare, 0)) as pay_outmedicare, "
					+ " sum(nvl(t.pay_assist, 0)) as pay_assist ,"
					+ " sum(nvl(t.pay_ciassist,0)) as pay_ciassist, "
					+ " max(t.diagnose_name) as diagnose_name,  max(t.hospital_name) as hospital_name "
					+ " from jz_medicalafter t where t.biz_status='1' "
					+ jwhere1
					+ " group by (t.member_id, t.member_type)) a "
					+ " where mem.member_id = a.member_id and mem.ds = a.member_type "
					+ jwhere + " order by mem.familyno";
			session.put("sql", sql);
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("PAY_TOTAL,val", "总费用");
			title.put("PAY_ASSIST,val", "救助金");
			title.put("PAY_CIASSIST,val", "大病保险");
			title.put("PAY_MEDICARE,val", "统筹");
			title.put("PAY_OUTMEDICARE,val", "目录外费用");
			title.put("ADDRESS,val", "家庭地址");
			title.put("RPRADDRESS,val", "户口所在地");
			title.put("ORGNAME1", "街道/乡镇");
			title.put("ORGNAME2", "社区/村");
			title.put("DIAGNOSE_NAME,val", "患病名称");
			title.put("HOSPITAL_NAME,val", "医院名称");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findAfterapprove(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/queryafterapprove.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryafterapprovedone() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and family_no  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  name  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  id_card " + var;
				} else {
				}
			}
			if (app == null || "".equals(app)) {
			} else {
				jwhere = jwhere + " and biz_status = '" + app + "'";
			}
			if (assistype == null || "".equals(assistype)) {
			} else {
				jwhere = jwhere + " and assist_type = '" + assistype + "'";
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  family_no like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  family_no like '" + oid + "%' ";
			}
			sql = "select biz_id, hospital_name, assist_type, jz.organization_id, member_id, member_type, "
					+ "decode(assist_type,'1','门诊','2','住院',null) as assist_type_text, "
					+ "personstate, assist_type_m, "
					+ "family_no as familyno, family_address, name as membername, sex, id_card as paperid , begin_time, diagnose_name, end_time, assist_time, "
					+ "pay_total, pay_medicare, pay_outmedicare, pay_assist, biz_status, oper_time, jz.assist_typex, "
					+ "org1.orgname as org1, "
					+ "org2.orgname as org2, "
					+ "org3.orgname as org3  from jz_medicalafter jz"
					+ " left join manager_org org1 on org1.depth = 3 and substr(jz.family_no,0,6)=org1.organization_id "
					+ " left join manager_org org2 on org2.depth = 4 and substr(jz.family_no,0,8)=org2.organization_id "
					+ " left join manager_org org3 on org3.depth = 5 and substr(jz.family_no,0,10)=org3.organization_id "
					+ "where 1=1 " + jwhere + "  order by oper_time desc  ";
			session.put("sql", sql);
			cur_page = "1";
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("DIAGNOSE_NAME,val", "患病名称");
			title.put("PAY_TOTAL,val", "总费用");
			title.put("PAY_MEDICARE,val", "统筹");
			title.put("PAY_ASSIST,val", "医疗救助");
			title.put("PAY_OUTMEDICARE,val", "目录外费用");
			title.put("HOSPITAL_NAME,val", "就诊医院");
			title.put("ASSIST_TYPE_TEXT,val", "救助类型");
			title.put("ORG1,val", "区县");
			title.put("ORG2,val", "乡镇");
			title.put("ORG3,val", "社区/街道");
			session.put("title", title);
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findAfterapprovedone(sql, new BigDecimal(
				cur_page).intValue(), "page/temp/queryafterapprovedone.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String genbillfile() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String orgname = user.getOrgname();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sdf.format(new Date());
		String targetfile = date + orgname + "临时救助发放文件.txt";
		// targetfile="临时救助发放文件.txt";
		try {
			fileName = new String(targetfile.getBytes("GBK"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String genmabillfile() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String orgname = user.getOrgname();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sdf.format(new Date());
		String targetfile = date + orgname + "医后报销发放文件.txt";
		// targetfile="临时救助发放文件.txt";
		try {
			fileName = new String(targetfile.getBytes("GBK"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	// 已发放名单查询
	@SuppressWarnings("rawtypes")
	public String queryusedbillsinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		months = tempService.findTempMonths(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryusedbills() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
					jwhere = jwhere + " and  tp.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  tp.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  tp.organizationid  like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  tp.organizationid  like '" + oid
						+ "%' ";
			}
			sql = " select bill.member_id, bill.member_type, bill.membername, "
					+ " bill.paperid, bill.salmoney, bill.opertime, org.orgname "
					+ " from temp_bill bill, temp_person tp, manager_org org "
					+ " where tp.member_id = bill.member_id "
					+ " and tp.member_type = bill.member_type "
					+ " and org.organization_id = tp.organizationid "
					+ " and bill.mid = '" + app + "'";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setBills(tempService.findUsedBills(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/queryusedbills.action"));
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		months = tempService.findTempMonths(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryusedmabillsinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		months = tempService.findMaMonths(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryusedmabills() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
				if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  bill.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  bill.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  org.organization_id  like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  org.organization_id  like '" + oid
						+ "%' ";
			}
			sql = " select bill.member_id, bill.member_type, bill.membername, "
					+ " bill.paperid, bill.salmoney, bill.opertime, org.orgname "
					+ " from ma_bill bill, manager_org org "
					+ " where  org.serialnumber = substr( bill.familyno,1,10) "
					+ " and bill.mid = '" + app + "'";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		setBills(tempService.findUsedMaBills(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/queryusedmabills.action"));
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		months = tempService.findMaMonths(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querybillstatinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		months = tempService.findTempMonths(organizationId);
		return SUCCESS;

	}

	@SuppressWarnings({ "rawtypes" })
	public String querybillstat() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		years = tempService.findTempStat(oid);
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		months = tempService.findTempMonths(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querymabillstatinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		months = tempService.findMaMonths(organizationId);
		return SUCCESS;

	}

	@SuppressWarnings({ "rawtypes" })
	public String querymabillstat() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		years = tempService.findMaStat(oid);
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		months = tempService.findMaMonths(organizationId);
		return SUCCESS;
	}

	// 二次救助
	@SuppressWarnings("rawtypes")
	public String querysecondmemberinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			// 查询封顶线
			tempSecondDTO = tempService.findToplineByOrganno(organizationId);
			SecondBatchDTO secondBatchDTO = new SecondBatchDTO();
			secondBatchDTO.setOrganizationId(organizationId);
			// 临时修改-20140106
			// secondBatchDTO.setYear(year);
			secondBatchDTO.setYear(2013);
			sbs = tempService.findSecondBatchs(secondBatchDTO);
			if (null != sbs && sbs.size() > 0) {
				impl = "0";// 生成审批数据不能操作
			} else {
				impl = "1";// 生成审批数据能操作
			}
			return SUCCESS;
		} else {
			result = "没有操作的权限！";
			return ERROR;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querysecondmember() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		String jwhere = "";
		String jwhere2 = "";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		if (null == cur_page || "".equals(cur_page)) {
			// 临时修改-20140106
			// tempSecondDTO.setYear((new BigDecimal(year)).toString());
			tempSecondDTO.setYear("2013");
			BigDecimal bb = new BigDecimal(tempSecondDTO.getSalpercent());
			if (null == app || "".equals(app)) {

			} else if ("1".equals(app)) {
				jwhere = jwhere + " and m.ds = '1' ";
			} else if ("2".equals(app)) {
				jwhere = jwhere + " and m.ds = '2' ";
			}
			if (null == minpay || "".equals(minpay)) {

			} else {

				jwhere2 = jwhere2 + "and round((  decode (sign(((payself - "
						+ tempSecondDTO.getSalscope() + ") * "
						+ bb.divide(new BigDecimal(100)).toString() + ")-"
						+ tempSecondDTO.getTopline() + ") ,1,"
						+ tempSecondDTO.getTopline() + ",0 ,"
						+ tempSecondDTO.getTopline() + ",-1, ((payself - "
						+ tempSecondDTO.getSalscope() + ") * "
						+ bb.divide(new BigDecimal(100)).toString()
						+ ") ) *100))/100 >= " + Long.parseLong(minpay);
			}
			sql = "select k.*,  round((  decode (sign(((payself - "
					+ tempSecondDTO.getSalscope()
					+ ") * "
					+ bb.divide(new BigDecimal(100)).toString()
					+ ")-"
					+ tempSecondDTO.getTopline()
					+ ") ,1,"
					+ tempSecondDTO.getTopline()
					+ ",0 ,"
					+ tempSecondDTO.getTopline()
					+ ",-1, ((payself - "
					+ tempSecondDTO.getSalscope()
					+ ") * "
					+ bb.divide(new BigDecimal(100)).toString()
					+ ") ) *100))/100 as salmoney from (select m.familyno,m.ds, "
					+ " m.membername,  m.paperid,  pay.pay_total, pay.pay_medicare, "
					+ " pay.pay_outmedicare, pay.pay_assist, pay.medicare_type, pay.pay_ciassist, "
					+ " decode(pay.medicare_type, 0, ((pay.pay_total * 0.8) - pay.pay_assist ), "
					+ " (pay.pay_total - pay.pay_medicare - pay.pay_outmedicare - "
					+ " pay.pay_assist - pay.pay_ciassist)) as payself  from MEMBER_BASEINFOVIEW02 m, "
					+ "  (select mem.member_id, "
					+ " mem.ds as member_type, "
					+ " max(p.medicare_type) medicare_type, "
					+ " sum(p.pay_total) pay_total, "
					+ " sum(p.pay_medicare) pay_medicare, "
					+ " sum(p.pay_outmedicare) pay_outmedicare, "
					+ " sum(p.pay_assist) pay_assist, "
					+ " sum(p.pay_ciassist) pay_ciassist "
					+ " from payview p, MEMBER_BASEINFOVIEW02 mem "
					+ " where mem.member_id = p.member_id "
					+ " and mem.ds = p.member_type "
					+ " and p.oper_time between "
					+ " (to_date('"
					+ tempSecondDTO.getYear()
					+ "-01-01:00:00:00', 'YYYY-MM-DD:HH24:MI:SS')) and "
					+ " (to_date('"
					+ tempSecondDTO.getYear()
					+ "-12-31:23:59:59', "
					+ " 'YYYY-MM-DD:HH24:MI:SS')) "
					+ " and mem.familyno like '"
					+ organizationId
					+ "%' "
					+ " group by mem.member_id, mem.ds) pay "
					+ " where pay.member_id = m.member_id  and pay.member_type = m.ds "
					+ jwhere + " order by m.familyno) k where payself > "
					+ tempSecondDTO.getSalscope() + jwhere2
					+ " order by familyno";

			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		SecondBatchDTO secondBatchDTO = new SecondBatchDTO();
		secondBatchDTO.setOrganizationId(organizationId);
		// 临时修改-20140106
		// secondBatchDTO.setYear(year);
		secondBatchDTO.setYear(2013);
		sbs = tempService.findSecondBatchs(secondBatchDTO);
		if (null != sbs && sbs.size() > 0) {
			impl = "0";// 生成审批数据不能操作
		} else {
			impl = "1";// 生成审批数据能操作
		}
		result = tempService.findSecnodmembersinfo(sql);
		seconds = tempService.findSecnodmembers(
				sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/querysecondmember.action?tempSecondDTO.salscope="
						+ tempSecondDTO.getSalscope()
						+ "&tempSecondDTO.salpercent="
						+ tempSecondDTO.getSalpercent()
						+ "&tempSecondDTO.topline="
						+ tempSecondDTO.getTopline());
		setToolsmenu(tempService.getToolsmenu());
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String gensecondapprove() {

		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempSecondDTO.setOrganizationId(organizationId);

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		// 临时修改-20140106
		// tempSecondDTO.setYear((new BigDecimal(year)).toString());
		tempSecondDTO.setYear("2013");

		String sql = "select * from second_approve t  where t.familyno like '"
				+ organizationId + "%'";

		sas = tempService.findSecondApproves(sql);
		if (null != sas && sas.size() > 0) {
			result = "已经生成审批数据，不能重复生成！";
		} else {
			result = tempService.saveSecondApproves(tempSecondDTO, app, minpay);
			if ("1".equals(result)) {
				result = "生成成功！";
			} else {
				result = "生成失败！";
			}
		}
		JSONObject json = new JSONObject();
		json.put("r", result);
		result = json.toString();
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querysecondapprovesinit() {

		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		if (organizationId.length() == 6) {
			if (6 == organizationId.length() || 8 == organizationId.length()) {
				if (2 == organizationId.length()) {
					orgs = systemDataService
							.findOrganizationExt(organizationId);
				} else {
					orgs = systemDataService
							.findOrgParentAndChilds(organizationId);
				}
			}
			tempSecondDTO = tempService.findToplineByOrganno(organizationId);
			SecondBatchDTO secondBatchDTO = new SecondBatchDTO();
			secondBatchDTO.setOrganizationId(organizationId);
			secondBatchDTO.setYear(year);
			sbs = tempService.findSecondBatchs(secondBatchDTO);
			if (null != sbs && sbs.size() > 0) {
				impl = "0";// 不能操作
			} else {
				impl = "1";// 能操作
			}
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querysecondapproves() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		String sql = "";
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
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
					jwhere = jwhere + " and tp.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  tp.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  tp.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  tp.familyno  like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  tp.familyno  like '" + oid + "%' ";
			}
			if ("".equals(app)) {

			} else {
				jwhere = jwhere + " and tp.approvests =" + app;
			}
			if (null == souce || "".equals(souce)) {

			} else if ("1".equals(souce)) {
				jwhere = jwhere + " and  tp.member_type='1' ";
			} else if ("2".equals(souce)) {
				jwhere = jwhere + " and  tp.member_type='2' ";
			}
			sql = "select * from second_approve tp  where 1=1 " + jwhere;
			session.put("sql", sql);

			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("SALMONEY,val", "本年度二次救助救助金");
			title.put("APPROVESTS,val", "审批状态(1:同意2:不同意)");
			title.put("MEMBER_TYPE,val", "来源(1:城市2:农村)");
			session.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempSecondDTO = tempService.findToplineByOrganno(organizationId);
		SecondBatchDTO secondBatchDTO = new SecondBatchDTO();
		secondBatchDTO.setOrganizationId(organizationId);
		secondBatchDTO.setYear(year);
		sbs = tempService.findSecondBatchs(secondBatchDTO);
		if (null != sbs && sbs.size() > 0) {
			impl = "0";// 不能操作
		} else {
			impl = "1";// 能操作
		}
		sas = tempService.findSecondApproves(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/querysecondapproves.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	public String editsecondapprove() {
		secondApproveDTO = tempService.saveSecondApprove(secondApproveDTO);
		JSONObject json = new JSONObject();
		json.put("r", "保存成功 ！");
		result = json.toString();
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String delsecondapprove() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempService.removeSecondApproves(organizationId);
		JSONObject json = new JSONObject();
		json.put("r", "审批记录全部删除，请重新生成审批单 ！");
		result = json.toString();
		return SUCCESS;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querysecondbillsinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		if (organizationId.length() == 6) {
			SecondBatchDTO secondBatchDTO = new SecondBatchDTO();
			if (6 == organizationId.length() || 8 == organizationId.length()) {
				secondBatchDTO.setOrganizationId(organizationId);
				// 临时救助-20140106
				// secondBatchDTO.setYear(year);
				secondBatchDTO.setYear(2013);
				sbs = tempService.findSecondBatchs(secondBatchDTO);
				if (null != sbs && sbs.size() > 0) {
					impl = "0";// 不能操作
				} else {
					impl = "1";// 能操作
				}
				String sql = "";
				if (null == cur_page || "".equals(cur_page)) {
					String jwhere = "";
					if (oid == null || "".equals(oid)) {
						jwhere = jwhere + " and  tp.familyno  like '"
								+ organizationId + "%' ";
					}
					if ("".equals(app)) {

					} else {
						jwhere = jwhere + " and tp.approvests =1";
					}
					sql = "select * from second_approve tp  where 1=1 "
							+ jwhere;
					session.put("sql", sql);
					cur_page = "1";
				} else {
					sql = (String) session.get("sql");
				}
				sas = tempService.findSecondApproves(sql, new BigDecimal(
						cur_page).intValue(),
						"page/temp/querysecondbillsinit.action");
				setToolsmenu(tempService.getToolsmenu());
			}
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String createsecondbills() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		result = tempService.saveSecondbills(organizationId);
		return querysecondbillsinit();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String gensecondbillinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		Calendar calendar = Calendar.getInstance();
		// 临时修改---20140106
		// int year = calendar.get(Calendar.YEAR);
		int year = 2013;
		sbs = tempService.findSecondBatchs(organizationId, year);
		// secondBatchDTO.setYear(year);
		// 生成EXCEL
		String jwhere = " and bill.batch_id = '" + sbs.get(0).getBatchId()
				+ "' " + " and to_char(bill.oper_time,'yyyy')='" + year + "'";
		String sql = "select decode(bill.member_type,'1','城市','2','农村',null,'来源不详')as SOUCE,bill.paperid,bill.membername,bill.salmoney,base.address"
				+ " from second_bill bill"
				+ " left join member_baseinfoview02 base "
				+ " on bill.member_id = base.member_id and bill.member_type = base.ds "
				+ " where 1=1 " + jwhere;
		session.put("sql", sql);

		HashMap title = new HashMap();
		title.put("PAPERID,val", "身份证号");
		title.put("MEMBERNAME,val", "姓名");
		title.put("SALMONEY,val", "救助金");
		title.put("ADDRESS,val", "居住地址");
		title.put("SOUCE,val", "来源");
		session.put("title", title);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String gensecondbillfile() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String orgname = user.getOrgname();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sdf.format(new Date());
		String targetfile = date + orgname + "二次救助发放文件.txt";
		// targetfile="临时救助发放文件.txt";
		try {
			fileName = new String(targetfile.getBytes("GBK"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String removesecondbillfile() {
		tempService.removeSecondBill(secondBatchDTO);
		result = "删除成功，请重新生成本年度二次救助金发放名单！";
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String querysecondbilldoneinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		sbs = tempService.findSecondBatchByOrgID(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querysecondbilldone() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
					jwhere = jwhere + " and  bill.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and bill.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  bill.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  bill.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  bill.familyno  like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  bill.familyno  like '" + oid + "%' ";
			}
			if ("".equals(app)) {

			} else {
				jwhere = jwhere + " and bill.oper_time between (to_date('"
						+ app
						+ "-01-01:00:00:00', 'YYYY-MM-DD:HH24:MI:SS')) and "
						+ " (to_date('" + app
						+ "-12-31:23:59:59', 'YYYY-MM-DD:HH24:MI:SS'))";
			}
			sql = "select * from second_bill bill where  1=1 " + jwhere
					+ " order by bill.familyno";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		sbills = tempService.findSecondBills(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/querysecondbilldone.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		sbs = tempService.findSecondBatchByOrgID(organizationId);
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String managetempinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 6) {
			tempRuleDTO = this.tempService.findTempRuleByOrgid(organizationId);
			if (null == tempRuleDTO.getPersonType()
					|| "".equals(tempRuleDTO.getPersonType())) {
			} else {
				tempRuleDTO.setScale(tempRuleDTO.getScale().multiply(
						new BigDecimal(100)));
			}
			if (null == tempRuleDTO.getPersonTypeNj()
					|| "".equals(tempRuleDTO.getPersonTypeNj())) {
			} else {
				tempRuleDTO.setScaleNj(tempRuleDTO.getScaleNj().multiply(
						new BigDecimal(100)));
			}
			if (null == tempRuleDTO.getNscale()
					|| "".equals(tempRuleDTO.getNscale())) {
			} else {
				tempRuleDTO.setNscale(tempRuleDTO.getNscale().multiply(
						new BigDecimal(100)));
			}
			return SUCCESS;
		} else {
			result = "您没有操作权限！";
			return ERROR;
		}

	}

	public String managetemp() {
		tempRuleDTO = tempService.saveTempRule(tempRuleDTO);
		result = "保存成功";
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String backall() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		tempService.removeSecondAll(organizationId, year);
		JSONObject json = new JSONObject();
		json.put("r", "删除成功 ！");
		result = json.toString();
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String madownloadExcelInit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempMonthDTO.setOrganizationId(organizationId);
		tempMonthDTO = tempService.findMaBill(tempMonthDTO);
		// 生成EXCEL文件
		String jwhere = " and bill.mid = '" + tempMonthDTO.getMid() + "'";
		String jorder = " order by base.familyno ";
		String sql = "select * from ma_bill bill "
				+ " left join member_baseinfoview02 base "
				+ " on bill.member_id = base.member_id and bill.member_type = base.ds "
				+ " where 1=1 " + jwhere + jorder;
		session.put("sql", sql);

		HashMap title = new HashMap();
		title.put("PAPERID,val", "身份证号");
		title.put("MEMBERNAME,val", "姓名");
		title.put("SALMONEY,val", "救助金");
		title.put("ADDRESS,val", "居住地址");
		title.put("FAMILYNO", "家庭编码");
		session.put("title", title);
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String tempdownloadExcelInit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempMonthDTO.setOrganizationId(organizationId);
		tempMonthDTO = tempService.findtempBill(tempMonthDTO);
		// 生成EXCEL文件
		// 生成EXCEL文件
		String jwhere = " and bill.mid = '" + tempMonthDTO.getMid() + "'";
		String jorder = " order by base.familyno ";
		String sql = "select * from temp_bill bill "
				+ " left join member_baseinfoview02 base "
				+ " on bill.member_id = base.member_id and bill.member_type = base.ds "
				+ " where 1=1 " + jwhere + jorder;
		session.put("sql", sql);

		HashMap title = new HashMap();
		title.put("PAPERID,val", "身份证号");
		title.put("MEMBERNAME,val", "姓名");
		title.put("SALMONEY,val", "救助金");
		title.put("ADDRESS,val", "居住地址");
		title.put("FAMILYNO,val", "家庭编号");
		session.put("title", title);
		return SUCCESS;
	}

	// 医后附件补录
	@SuppressWarnings("rawtypes")
	public String afterannexaddinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();

		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryafterannexadd() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and ma.family_no  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and ma.name  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and ma.id_card " + var;
				} else {
				}
			}
			if (app == null || "".equals(app)) {
			} else {
				jwhere = jwhere + " and ma.biz_status = '" + app + "'";
			}
			if (null == assistype || "".equals(assistype)) {

			} else {
				jwhere = jwhere + " and ma.assist_type = '" + assistype + "'";
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and ma.family_no like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and ma.family_no like '" + oid + "%' ";
			}
			sql = " select ma.biz_id, ma.hospital_name, ma.assist_type, ma.organization_id, ma.member_id, ma.member_type, ma.family_no, ma.family_address, ma.name, ma.sex, "
					+ " decode(ma.assist_type,'1','门诊','2','住院',null) as assist_type_text, "
					+ " ma.id_card, ma.begin_time, ma.diagnose_name, ma.end_time, ma.assist_time, ma.pay_total, ma.pay_medicare, ma.pay_outmedicare, ma.pay_assist, "
					+ " ma.biz_status, ma.oper_time, ma.implsts, ma.medicare_type, ma.hospital_type, ma.assist_type_m, ma.pay_sumassist_in, "
					+ " ma.pay_sumassist_out, ma.sum_medicarescope, ma.pay_ciassist, ma.sum_assitscope, ma.sum_pre_assitscope, base.assist_type as assist_type_base, base.personstate, base.assist_typex as assist_typex_base "
					+ " from jz_medicalafter ma "
					+ " left join member_baseinfoview02 base "
					+ " on ma.member_id = base.member_id "
					+ " and ma.member_type = base.ds " + " where 1=1 "
					/*
					 * +
					 * " not exists (select 1 from jz_medicalafterfile maf where ma.biz_id = maf.biz_id) "
					 */
					+ jwhere + "  order by ma.oper_time desc  ";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findAfterannexadd(sql, new BigDecimal(
				cur_page).intValue(), "page/temp/queryafterannexadd.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String afteruploadaddinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 8) {
			tempDTO = tempService.findMAmemberinfo(tempDTO);
			mafiles = tempService.findJzMedicalafterfiles(new BigDecimal(
					tempDTO.getApproveId()).toString());
			return SUCCESS;
		} else {
			result = "此功能由乡镇街道使用！";
			return "result";
		}
	}

	public String afteruploadadd() {
		FileUpload fu = new FileUpload("/file/medicalafter");
		mafiles = new ArrayList<JzMedicalafterfileDTO>();
		long sumFilesSize = 0;
		if (null == af) {
			result = "没有附件上传！";
		} else {
			for (int i = 0; i < af.size(); i++) {
				sumFilesSize = sumFilesSize + af.get(i).length();
			}
			if (sumFilesSize / 1024 > 1024) {
				result = "上传图片总大小为：" + (sumFilesSize / 1024)
						+ "KB，超出上线1024KB，请重新上传！";
				return "result";
			} else {
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
						filedto.setBizId(new BigDecimal(tempDTO.getApproveId()));
						mafiles.add(filedto);
					}
				}
				tempService.saveJzMedicalafterfiles(mafiles);
				result = "附件补录成功！";
			}
		}
		mafiles = tempService.findJzMedicalafterfiles(new BigDecimal(tempDTO
				.getApproveId()).toString());
		return SUCCESS;
	}

	// 临时附件补录
	@SuppressWarnings("rawtypes")
	public String tempannexaddinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();

		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querytempannexadd() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and mem.familyno  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and mem.membername  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and mem.paperid " + var;
				} else {
				}
			}
			if (app == null || "".equals(app)) {
			} else {
				jwhere = jwhere + " and t.approvests = '" + app + "'";
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mem.organizationid like '"
						+ organizationId + "%' ";
			} else {
				jwhere = jwhere + " and  mem.organizationid like '" + oid
						+ "%' ";
			}
			sql = "select t.implsts, t.approve_id ,t.member_id,t.member_type ,t.applytime ,t.approvests,"
					+ " t.hospitalname, "
					+ " decode(t.yb_sicken, null, '', t.yb_sicken || '>>') || decode(t.yb_sickenval, null, '', t.yb_sickenval || '>>') || "
					+ " decode(t.ts_sicken, null, '', t.ts_sicken || '>>') || decode(t.ts_sickenval, null, '', t.ts_sickenval) as DIAGNOSE_NAME , "
					+ " mem.membername,mem.familyno,mem.paperid,mem.relmaster,mem.address, t.pay_total,t.pay_medicare,t.pay_outmedicare,t.pay_assist, "
					+ " mem.personstate, mem.assist_type, mem.assist_typex "
					+ " from temp_approve t,  temp_person mem where mem.member_id = t.member_id "
					+ " and mem.member_type = t.member_type    "
					+ jwhere
					+ " order by mem.familyno";
			session.put("sql", sql);
			cur_page = "1";
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findTempannexadd(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/querytempannexadd.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String tempuploadaddinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (organizationId.length() == 8) {
			tempDTO = tempService.findTempmemberinfo(tempDTO);
			tafiles = tempService.findTempApprovefiles(new BigDecimal(tempDTO
					.getApproveId()).toString());
			return SUCCESS;
		} else {
			result = "此功能由乡镇街道使用！";
			return "result";
		}
	}

	public String tempuploadadd() {
		FileUpload fu = new FileUpload("/file/tempfile");
		tafiles = new ArrayList<TempApprovefileDTO>();
		long sumFilesSize = 0;
		if (null == ta) {
			result = "没有附件上传！";
		} else {
			for (int i = 0; i < ta.size(); i++) {
				sumFilesSize = sumFilesSize + ta.get(i).length();
			}
			if (sumFilesSize / 1024 > 1024) {
				result = "上传图片总大小为：" + (sumFilesSize / 1024)
						+ "KB，超出上线1024KB，请重新上传！";
				return "result";
			} else {
				for (int i = 0; i < ta.size(); i++) {
					TempApprovefileDTO filedto = new TempApprovefileDTO();
					String sFileName = taFileName.get(i);
					if (null == sFileName || "".equals(sFileName)) {
					} else {
						filedto.setFilename(sFileName);
						File sFile = ta.get(i);
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
						filedto.setBizId(new BigDecimal(tempDTO.getApproveId()));
						tafiles.add(filedto);
					}
				}
				tempService.saveTempApprovefiles(tafiles);
				result = "附件补录成功！";
			}
		}
		tafiles = tempService.findTempApprovefiles(new BigDecimal(tempDTO
				.getApproveId()).toString());
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String queryaftermembersgsinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryaftermembersgs() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
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
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and family_no  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  name  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  id_card " + var;
				} else {
				}
			}
			if ((opertime1.equals("") || null == opertime1)
					&& (opertime2.equals("") || null == opertime2)) {
			} else if (opertime1.equals("") || null == opertime1) {
				jwhere = jwhere + " and jz.assist_time > TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";

			} else if (opertime2.equals("") || null == opertime2) {
				jwhere = jwhere + "and jz.assist_time < TO_DATE('"
						+ opertime1.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') ";

			} else {
				jwhere = jwhere + " and jz.assist_time BETWEEN TO_DATE('"
						+ opertime1.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') AND TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}
			if ((opertime3.equals("") || null == opertime3)
					&& (opertime4.equals("") || null == opertime4)) {
			} else if (opertime3.equals("") || null == opertime3) {
				jwhere = jwhere + " and jz.oper_time > TO_DATE('"
						+ opertime4.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";

			} else if (opertime4.equals("") || null == opertime4) {
				jwhere = jwhere + "and jz.oper_time < TO_DATE('"
						+ opertime3.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') ";

			} else {
				jwhere = jwhere + " and jz.oper_time BETWEEN TO_DATE('"
						+ opertime3.substring(0, 10)
						+ " 00:00:00', 'yyyy-MM-dd hh24:mi:ss') AND TO_DATE('"
						+ opertime4.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}
			if ("".equals(impl)) {

			} else if ("1".equals(impl)) {
				jwhere = jwhere + " and (jz.implsts is null or jz.implsts='1')";
			} else if ("2".equals(impl)) {
				jwhere = jwhere + " and jz.implsts ='2'";
			}
			if (app == null || "".equals(app)) {
			} else {
				jwhere = jwhere + " and biz_status = '" + app + "'";
			}
			if (null == assistype || "".equals(assistype)) {

			} else {
				jwhere = jwhere + " and jz.assist_type = '" + assistype + "'";
			}
			if (null == ds || "".equals(ds)) {

			} else {
				jwhere = jwhere + " and jz.member_type = '" + ds + "'";
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  family_no like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  family_no like '" + oid + "%' ";
			}
			sql = "select biz_id, hospital_name, assist_type, jz.organization_id, member_id, member_type, "
					+ "personstate, assist_type_m, "
					+ "family_no as familyno, family_address, name as membername, sex, id_card as paperid , begin_time, diagnose_name, end_time, assist_time, "
					+ "pay_total, pay_medicare, pay_outmedicare, pay_assist, pay_ciassist, biz_status, oper_time, jz.assist_typex, "
					+ " decode(jz.biz_status,0,'不同意',1,'同意',-1,'审批中')  as bizStatusText, decode(jz.implsts,2,'已发放','未发放') as implstsText, "
					+ " decode(jz.assist_type,'1','门诊','2','住院',null) as assist_type_text ,"
					+ " (decode(jz.member_type, '1', '城市', '2', '农村', null) || "
					+ " decode(substr(jz.assist_type_m, 1, 1), "
					+ " 1, "
					+ " '-在保户', "
					+ " 2, "
					+ " '-在保户', "
					+ " null) || "
					+ " decode(substr(jz.assist_type_m, 3, 1), 1, '-三无人员', null) || "
					+ " decode(substr(jz.assist_type_m, 4, 1), 1, '-五保户', null) || "
					+ " decode(substr(jz.assist_type_m, 5, 1), 1, '-优抚对象', null) || "
					+ " decode(substr(jz.assist_typex, 1, 1), 1, '-孤儿', null)) as meminfo,"
					+ "org1.orgname as org1, "
					+ "org2.orgname as org2, "
					+ "org3.orgname as org3  from jz_medicalafter jz"
					+ " left join manager_org org1 on org1.depth = 3 and substr(jz.family_no,0,6)=org1.organization_id "
					+ " left join manager_org org2 on org2.depth = 4 and substr(jz.family_no,0,8)=org2.organization_id "
					+ " left join manager_org org3 on org3.depth = 5 and substr(jz.family_no,0,10)=org3.organization_id "
					+ "where 1=1 " + jwhere + "  order by oper_time desc  ";
			session.put("sql", sql);
			cur_page = "1";
			HashMap title = new HashMap();
			title.put("FAMILYNO,val", "家庭编号");
			title.put("MEMBERNAME,val", "姓名");
			title.put("PAPERID,val", "身份证号");
			title.put("MEMINFO,val", "身份类别");
			title.put("DIAGNOSE_NAME,val", "患病名称");
			title.put("PAY_TOTAL,val", "总费用");
			title.put("PAY_MEDICARE,val", "统筹");
			title.put("PAY_CIASSIST,val", "大病保险费用");
			title.put("PAY_ASSIST,val", "医疗救助");
			title.put("PAY_OUTMEDICARE,val", "目录外费用");
			title.put("HOSPITAL_NAME,val", "就诊医院");
			title.put("ORG1,val", "区县");
			title.put("ORG2,val", "乡镇");
			title.put("ORG3,val", "社区/街道");
			title.put("ASSIST_TIME,val", "申请时间");
			title.put("OPER_TIME", "审批时间");
			title.put("BIZSTATUSTEXT,val", "审批状态");
			title.put("ASSIST_TYPE_TEXT,val", "救助类型");
			title.put("IMPLSTSTEXT,val", "发放状态");
			session.put("title", title);
		} else {
			sql = (String) session.get("sql");
		}
		tempmembers = tempService.findAfterapprovedone(sql, new BigDecimal(
				cur_page).intValue(), "page/temp/queryaftermembersgs.action");
		setToolsmenu(tempService.getToolsmenu());
		// 获取机构
		if (6 == organizationId.length() || 8 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;
	}

	public String queryPaysinfo() {
		payviews = tempService.findPayviews(tempDTO);
		return SUCCESS;
	}

	// 二次救助
	public InputStream getInputStream() {
		return tempService.findFileStream(tempMonthDTO);
	}

	public InputStream getMaInputStream() {
		return tempService.findMaFileStream(tempMonthDTO);
	}

	public InputStream getInputStreamSecond() {
		return tempService.findFileStreamSecond(secondBatchDTO);
	}

	public TempService getTempService() {
		return tempService;
	}

	public void setTempService(TempService tempService) {
		this.tempService = tempService;
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public TempDTO getTempDTO() {
		return tempDTO;
	}

	public void setTempDTO(TempDTO tempDTO) {
		this.tempDTO = tempDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<TempDTO> getTempmembers() {
		return tempmembers;
	}

	public void setTempmembers(List<TempDTO> tempmembers) {
		this.tempmembers = tempmembers;
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

	public List<OutIcdDTO> getOuticds() {
		return outicds;
	}

	public void setOuticds(List<OutIcdDTO> outicds) {
		this.outicds = outicds;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getApp() {
		return app;
	}

	public void setImpl(String impl) {
		this.impl = impl;
	}

	public String getImpl() {
		return impl;
	}

	public void setNations(List<DictDTO> nations) {
		this.nations = nations;
	}

	public List<DictDTO> getNations() {
		return nations;
	}

	public List<TempMonthDTO> getMonths() {
		return months;
	}

	public void setMonths(List<TempMonthDTO> months) {
		this.months = months;
	}

	public void setTempMonthDTO(TempMonthDTO tempMonthDTO) {
		this.tempMonthDTO = tempMonthDTO;
	}

	public TempMonthDTO getTempMonthDTO() {
		return tempMonthDTO;
	}

	public void setYears(List<TempMonthDTO> years) {
		this.years = years;
	}

	public List<TempMonthDTO> getYears() {
		return years;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setBills(List<TempBillDTO> bills) {
		this.bills = bills;
	}

	public List<TempBillDTO> getBills() {
		return bills;
	}

	public void setSeconds(List<TempSecondDTO> seconds) {
		this.seconds = seconds;
	}

	public List<TempSecondDTO> getSeconds() {
		return seconds;
	}

	public TempSecondDTO getTempSecondDTO() {
		return tempSecondDTO;
	}

	public void setTempSecondDTO(TempSecondDTO tempSecondDTO) {
		this.tempSecondDTO = tempSecondDTO;
	}

	public void setSas(List<SecondApproveDTO> sas) {
		this.sas = sas;
	}

	public List<SecondApproveDTO> getSas() {
		return sas;
	}

	public void setSecondApproveDTO(SecondApproveDTO secondApproveDTO) {
		this.secondApproveDTO = secondApproveDTO;
	}

	public SecondApproveDTO getSecondApproveDTO() {
		return secondApproveDTO;
	}

	public List<SecondBatchDTO> getSbs() {
		return sbs;
	}

	public void setSbs(List<SecondBatchDTO> sbs) {
		this.sbs = sbs;
	}

	public List<SecondBillDTO> getSbills() {
		return sbills;
	}

	public void setSbills(List<SecondBillDTO> sbills) {
		this.sbills = sbills;
	}

	public void setSecondBatchDTO(SecondBatchDTO secondBatchDTO) {
		this.secondBatchDTO = secondBatchDTO;
	}

	public SecondBatchDTO getSecondBatchDTO() {
		return secondBatchDTO;
	}

	public void setOpertime1(String opertime1) {
		this.opertime1 = opertime1;
	}

	public String getOpertime1() {
		return opertime1;
	}

	public void setOpertime2(String opertime2) {
		this.opertime2 = opertime2;
	}

	public String getOpertime2() {
		return opertime2;
	}

	public void setTempRuleDTO(TempRuleDTO tempRuleDTO) {
		this.tempRuleDTO = tempRuleDTO;
	}

	public TempRuleDTO getTempRuleDTO() {
		return tempRuleDTO;
	}

	public void setMafiles(List<JzMedicalafterfileDTO> mafiles) {
		this.mafiles = mafiles;
	}

	public List<JzMedicalafterfileDTO> getMafiles() {
		return mafiles;
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

	public List<String> getAfContentType() {
		return afContentType;
	}

	public void setAfContentType(List<String> afContentType) {
		this.afContentType = afContentType;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public List<File> getTa() {
		return ta;
	}

	public void setTa(List<File> ta) {
		this.ta = ta;
	}

	public List<String> getTaFileName() {
		return taFileName;
	}

	public void setTaFileName(List<String> taFileName) {
		this.taFileName = taFileName;
	}

	public List<String> getTaContentType() {
		return taContentType;
	}

	public void setTaContentType(List<String> taContentType) {
		this.taContentType = taContentType;
	}

	public List<TempApprovefileDTO> getTafiles() {
		return tafiles;
	}

	public void setTafiles(List<TempApprovefileDTO> tafiles) {
		this.tafiles = tafiles;
	}

	public String getDis() {
		return dis;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

	public String getSouce() {
		return souce;
	}

	public int getOrgIdLength() {
		return orgIdLength;
	}

	public void setOrgIdLength(int orgIdLength) {
		this.orgIdLength = orgIdLength;
	}

	public void setSouce(String souce) {
		this.souce = souce;
	}

	public YljzService getYljzService() {
		return yljzService;
	}

	public void setYljzService(YljzService yljzService) {
		this.yljzService = yljzService;
	}

	public CiDTO getCiDTO() {
		return ciDTO;
	}

	public void setCiDTO(CiDTO ciDTO) {
		this.ciDTO = ciDTO;
	}

	public TempDTO getTempDTOend() {
		return tempDTOend;
	}

	public void setTempDTOend(TempDTO tempDTOend) {
		this.tempDTOend = tempDTOend;
	}

	public JzMedicalafterRuleDTO getJzMedicalafterRuleDTO() {
		return jzMedicalafterRuleDTO;
	}

	public void setJzMedicalafterRuleDTO(
			JzMedicalafterRuleDTO jzMedicalafterRuleDTO) {
		this.jzMedicalafterRuleDTO = jzMedicalafterRuleDTO;
	}

	public List<TempDTO> getPayviews() {
		return payviews;
	}

	public void setPayviews(List<TempDTO> payviews) {
		this.payviews = payviews;
	}

	public String getOpertime3() {
		return opertime3;
	}

	public void setOpertime3(String opertime3) {
		this.opertime3 = opertime3;
	}

	public String getOpertime4() {
		return opertime4;
	}

	public void setOpertime4(String opertime4) {
		this.opertime4 = opertime4;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
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

	public AfterDTO getAfterDTO() {
		return afterDTO;
	}

	public void setAfterDTO(AfterDTO afterDTO) {
		this.afterDTO = afterDTO;
	}

	@SuppressWarnings("rawtypes")
	public String afterapplyinitnew() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		tempDTO.setOrganizationId(organizationId);
		tempDTO.setBizType("ma");
		jzMedicalafterRuleDTO = tempService.findMedicalafterRule(tempDTO);
		if (jzMedicalafterRuleDTO.getRuleId() != null) {
			tempDTO.setDbButtonFlag("0");
			tempDTO.setJzjButtonFlag("1");
		} else {
			tempDTO.setDbButtonFlag("1");
			tempDTO.setJzjButtonFlag("0");
		}
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
		/*
		 * if (tempDTO.getCalcType()==2) { tempDTOend =
		 * tempService.findPayview01(tempDTO); if(tempDTOend.getApproveId() !=
		 * null && "2".equals(tempDTO.getAssistype())){ if
		 * (tempDTOend.getApproveId
		 * ().toString().equals(tempDTO.getApproveId().toString()) &&
		 * "ma".equals(tempDTOend.getBiztype())){ flag=true; }else{ flag=false;
		 * result="此条信息不允许修改！"; } }else{ flag=true; } }
		 * if(tempDTO.getCalcType()==1 || flag==true){ tempDTO =
		 * tempService.findAftermeberinfo(tempDTO); if (null ==
		 * tempDTO.getApproveId()) { } else { mafiles =
		 * tempService.findJzMedicalafterfiles(new BigDecimal(
		 * tempDTO.getApproveId()).toString()); } flag=true;
		 * 
		 * } if(flag==true){ return SUCCESS; }else{ payviews =
		 * tempService.findPayviews(tempDTO);
		 * 
		 * return "result"; }
		 */

	}

	public String getAssistype() {
		return assistype;
	}

	public void setAssistype(String assistype) {
		this.assistype = assistype;
	}

	/**
	 * 对账单 查询 医后报销
	 * 
	 * @return
	 */
	public String querycheckaccoutsmainit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// start 梅河口20131018重大疾病-------------------------------------
		orgid = organizationId.substring(0, 6);
		// end 梅河口20131018重大疾病-------------------------------------
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		/* depts = systemDataService.findDeptsByOrg(organizationId); */
		depts = tempService.findMaHospitalNameById(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String querycheckaccoutsma() {
		Map map = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) map.get("user");
		String organizationId = user.getOrganizationId();
		String var = value;
		String jwhere = "";
		String sql = "";
		// start 梅河口20131018重大疾病-------------------------------------
		if ("1".equals(implsts)) {
			jwhere = jwhere + " and (aft.implsts is null or aft.implsts ='1') ";
		} else if ("2".equals(implsts)) {
			jwhere = jwhere + " and  aft.implsts='2' ";
		}
		if (!"".equals(bizstatus)) {
			jwhere = jwhere + " and aft.biz_status = '" + bizstatus + "' ";
		}
		orgid = organizationId.substring(0, 6);
		String m_jwhere = "";
		String m_sql = "";
		String m_where = "";
		String m_from = "";
		if ("220506".equals(organizationId.substring(0, 6))) {
			if ("1".equals(diagnosetype)) {
				m_jwhere = m_jwhere + " and  f.scaler > 0 ";
			}
			m_sql = " , aft.diagnose_type_id, f.organization_id dorg, f.diagnose_type_id as dtypeid, f.diagnose_type_name dtypename, f.scaler ";
			m_where = " and aft.diagnose_type_id = f.diagnose_type_id(+) "
					+ m_jwhere;
			m_from = " ,(select * from diagnose_type dia where dia.organization_id='220506') f ";

		}
		// end 梅河口20131018重大疾病-------------------------------------
		if (null == cur_page || "".equals(cur_page)) {
			if (!"".equals(var)) {
				if ("=".equals(operational)) {
					var = " = '" + var + "'";
				} else if ("like".equals(operational)) {
					var = "like  '%" + var + "%'";
				} else {
					var = "";
				}
				if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and aft.family_no  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  aft.name  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  aft.id_card " + var;
				} else {
				}
			}
			if (null == hid || "".equals(hid)) {
			} else if ("无".equals(hid)) {
			} else if ("全部".equals(hid)) {
			} else {
				jwhere = jwhere + " and aft.hospital_name  ='" + hid + "' ";
			}
			if ("1".equals(htype)) {
				jwhere = jwhere + " and  aft.hospital_type=1";
			} else if ("2".equals(htype)) {
				jwhere = jwhere
						+ " and  (aft.hospital_type=2 or aft.hospital_type is null) ";
			}
			if ((opertime1.equals("") || null == opertime1)
					&& (opertime2.equals("") || null == opertime2)) {
			} else if (opertime1.equals("") || null == opertime1) {
				jwhere = jwhere + " and aft.oper_time <= TO_DATE('"
						+ opertime2.substring(0, 10) + "', 'yyyy-MM-dd')";

			} else if (opertime2.equals("") || null == opertime2) {
				jwhere = jwhere + "and aft.oper_time >= TO_DATE('"
						+ opertime1.substring(0, 10) + "', 'yyyy-MM-dd') ";

			} else {
				jwhere = jwhere + " and aft.oper_time BETWEEN TO_DATE('"
						+ opertime1.substring(0, 10)
						+ "', 'yyyy-MM-dd') AND TO_DATE('"
						+ opertime2.substring(0, 10)
						+ " 23:59:59', 'yyyy-MM-dd hh24:mi:ss')";
			}
			if ((assismoney1.equals("") || null == assismoney1)
					&& (assismoeny2.equals("") || null == assismoeny2)) {
			} else if (assismoney1.equals("") || null == assismoney1) {
				jwhere = jwhere + " and aft.pay_assist <=" + assismoeny2;

			} else if (assismoeny2.equals("") || null == assismoeny2) {
				jwhere = jwhere + " and aft.pay_assist >= " + assismoney1;

			} else {
				jwhere = jwhere + " and aft.pay_assist BETWEEN " + assismoney1
						+ " AND " + assismoeny2;
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  aft.family_no like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  aft.family_no like '" + oid + "%' ";
			}
			if ("1".equals(method)) {
				jwhere = jwhere + " and  aft.member_type=1";
			} else if ("2".equals(method)) {
				jwhere = jwhere + " and  aft.member_type=2 ";
			}
			if ("1".equals(biztype)) {
				jwhere = jwhere + " and  aft.assist_type=1 ";
			} else if ("2".equals(biztype)) {
				jwhere = jwhere + " and  aft.assist_type=2 ";
			}
			sql = "select aft.biz_id,aft.hospital_name as hname,aft.hospital_type, "
					+ " decode(aft.hospital_type,'1','辖区内','2','其他') as hospitaltypetext, "
					+ " aft.assist_type, "
					+ "  decode(aft.assist_type,'1','门诊','2','住院','3','其他') as assisttypetext, "
					+ " aft.family_no,aft.name,bw.mastername,aft.id_card,"
					+ " aft.diagnose_name as icdname,aft.pay_total,aft.pay_medicare,aft.pay_outmedicare,aft.pay_assist,"
					+ " nvl(aft.pay_total - aft.pay_medicare -aft.pay_assist - aft.pay_ciassist,0) as pay_self, "
					+ " aft.pay_ciassist,aft.diagnose_name,aft.begin_time,aft.end_time,aft.oper_time, aft.family_address, aft.orgname1, aft.orgname2, "
					+ "(decode(aft.member_type, '1', '城市', '2', '农村', null) || decode(substr(aft.assist_type_m, 1, 1),1,'-在保户',2,'-在保户', null) || "
					+ "decode(substr(aft.assist_type_m, 3, 1), 1, '-三无人员', null) || "
					+ "decode(substr(aft.assist_type_m, 4, 1), 1, '-五保户', null) || "
					+ "decode(substr(aft.assist_type_m, 5, 1), 1, '-优抚对象', null) || "
					+ "decode(substr(aft.assist_typex, 1, 1), 1, '-孤儿', null)) as meminfo "
					+ m_sql
					+ " from (select ma.*,org1.orgname as orgname1, org2.orgname as orgname2  from jz_medicalafter ma "
					+ " left join manager_org org1 on "
					+ " org1.depth = 4 and org1.organization_id = substr(ma.family_no, 1, 8) "
					+ " left join manager_org org2 on "
					+ " org2.depth = 5 and org2.organization_id = substr(ma.family_no, 1, 10)) aft,member_baseinfoview02 bw "
					+ m_from
					+ " where 1=1 and aft.member_id(+) = bw.member_id and aft.member_type(+) = bw.ds "
					+ jwhere + m_where + " and aft.biz_status in( '1','-1') "
					// + " and aft.implsts = '2'"
					+ " order by aft.oper_time desc";
			map.put("sql", sql);
			HashMap title = new LinkedHashMap();
			title.put("ASSISTTYPETEXT,val", "救助类型（门诊/住院）");
			title.put("FAMILY_NO,val", "家庭编号");
			title.put("HNAME,val", "就诊医院");
			title.put("HOSPITALTYPETEXT,val", "医院类别");
			title.put("MASTERNAME,val", "户主姓名");
			title.put("NAME,val", "患者姓名");
			title.put("ID_CARD,val", "身份证号码");
			title.put("MEMINFO,val", "人员类别");
			title.put("DIAGNOSE_NAME,val", "确诊患病名称");
			title.put("BEGIN_TIME,val", "入院时间");
			title.put("END_TIME,val", "出院时间");
			title.put("PAY_TOTAL,val", "总费用");
			title.put("PAY_MEDICARE,val", "统筹支付");
			title.put("PAY_OUTMEDICARE,val", "目录外费用");
			title.put("PAY_CIASSIST,val", "大病保险");
			title.put("PAY_ASSIST,val", "医疗救助金");
			title.put("PAY_SELF,val", "个人承担");
			title.put("OPER_TIME,val", "结算时间");
			// title.put("FAMILY_ADDRESS,val", "居住地址");
			// title.put("ORGNAME1,val", "所属街道/乡镇");
			// title.put("ORGNAME2,val", "所属社区/村");
			map.put("title", title);
			cur_page = "1";
		} else {
			sql = (String) map.get("sql");
		}
		payviews = tempService.findMaAccounts(sql,
				new BigDecimal(cur_page).intValue(),
				"page/temp/querycheckaccoutsma.action");
		setToolsmenu(tempService.getToolsmenu());
		String sql1 = " select count(1) as zrc, nvl(sum(pay_total), 0) as zm, nvl(sum(pay_assist), 0) as zjzj , nvl(sum(pay_ciassist), 0) as zdbbx from ( "
				+ sql + " )";
		setResult(tempService.findInfo(sql1));
		// 获取机构
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		// 获取医院列表
		// depts = systemDataService.findDeptsByOrg(organizationId);
		depts = tempService.findMaHospitalNameById(organizationId);
		if (null != depts && depts.size() > 0) {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(null);
			element.setName("全部");
			depts.add(0, element);
		} else {
			DeptDTO element = new DeptDTO();
			element.setHospitalId(-1);
			element.setName("无");
			depts.add(0, element);
		}
		return SUCCESS;
	}

	public String viewmapay() {

		tempDTO = tempService.findMaByBizId(tempDTO);
		/*
		 * bizCheckDTO = new BizCheckDTO();
		 * bizCheckDTO.setFamilyNo(bizDTO.getFamilyNo());
		 * bizCheckDTO.setBizId(bizDTO.getBizId()); pays =
		 * baseBizService.findBizPayInfo(bizDTO);
		 */
		pics = tempService.findJzMedicalafterfiles(tempDTO.getBizid()
				.toString());
		return SUCCESS;
	}

	/**
	 * 对账单统计
	 * 
	 * @return
	 */
	public String checkaccoutsmainit() {
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String organizationId = user.getOrganizationId();
		// 获取机构
		if (2 == organizationId.length() || 4 == organizationId.length()
				|| 6 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String printmaaccounts() {
		mabills = tempService.findafteraccounts(tempDTO);
		UserDTO user = (UserDTO) ActionContext.getContext().getSession()
				.get("user");
		String fullname = user.getFullname();
		String title = "";
		if ("1".equals(tempDTO.getMemberType())) {
			title = fullname + "城市低保";
		} else if ("2".equals(tempDTO.getMemberType())) {
			title = fullname + "农村低保";
		} else {
			title = fullname + "";
		}
		map = new HashMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("begin", sdf.format(tempDTO.getBegintime()));
		map.put("end", sdf.format(tempDTO.getEndtime()));
		map.put("current", sdf.format(new Date()));
		map.put("title", title);
		return SUCCESS;
	}

	public String tempback() {
		tempDTO = tempService.upTempBack(tempDTO);
		result = "保存成功";
		return SUCCESS;
	}

	public String getAssismoney1() {
		return assismoney1;
	}

	public void setAssismoney1(String assismoney1) {
		this.assismoney1 = assismoney1;
	}

	public String getAssismoeny2() {
		return assismoeny2;
	}

	public void setAssismoeny2(String assismoeny2) {
		this.assismoeny2 = assismoeny2;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBiztype() {
		return biztype;
	}

	public void setBiztype(String biztype) {
		this.biztype = biztype;
	}

	public List<JzMedicalafterfileDTO> getPics() {
		return pics;
	}

	public void setPics(List<JzMedicalafterfileDTO> pics) {
		this.pics = pics;
	}

	public List<BillDTO> getMabills() {
		return mabills;
	}

	public void setMabills(List<BillDTO> mabills) {
		this.mabills = mabills;
	}

	@SuppressWarnings("rawtypes")
	public HashMap getMap() {
		return map;
	}

	@SuppressWarnings("rawtypes")
	public void setMap(HashMap map) {
		this.map = map;
	}

	public String getHid() {
		return hid;
	}

	public void setHid(String hid) {
		this.hid = hid;
	}

	public String getHtype() {
		return htype;
	}

	public void setHtype(String htype) {
		this.htype = htype;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getDiagnosetype() {
		return diagnosetype;
	}

	public void setDiagnosetype(String diagnosetype) {
		this.diagnosetype = diagnosetype;
	}

	public String getImplsts() {
		return implsts;
	}

	public void setImplsts(String implsts) {
		this.implsts = implsts;
	}

	public String getMinpay() {
		return minpay;
	}

	public void setMinpay(String minpay) {
		this.minpay = minpay;
	}

	public String getBizstatus() {
		return bizstatus;
	}

	public void setBizstatus(String bizstatus) {
		this.bizstatus = bizstatus;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

}
