package com.mingda.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.mingda.dto.DeptDTO;
import com.mingda.dto.DiagnoseTypeDTO;
import com.mingda.dto.JzYearDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.OutIcdDTO;
import com.mingda.dto.TempDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.SystemDataService;
import com.mingda.service.TempService;
import com.mingda.webclient.YljzService;
import com.mingda.webclient.model.AfterDTO;
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
	private List<DeptDTO> depts;
	private List<DiagnoseTypeDTO> diagnosetypes;
	private List<OutIcdDTO> outicds;
	private CiDTO ciDTO;
	private String orgid;
	private TempDTO tempDTOend;
	private AfterDTO afterDTO;
	private List<OrganizationDTO> orgs;
	private String cur_page;
	private String value;
	private String operational;
	private String term;
	private String app;
	private String oid;
	private String toolsmenu;
	private String assistype;

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
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (null != organizationId && !"".equals(organizationId)) {
			organizationId = organizationId.substring(0, 6);
		}
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
		int businessyear = this.getBusinessYear(organizationId,tempDTO.getEndtime());
		System.out.println("计算大病保险,本次业务年度："+businessyear);
		ciDTO.setBusinessyear(businessyear+"");
		ciDTO.setOrgCode(organizationId);
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
				json.put("businessyear", ciDTO.getBusinessyear());
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
					json.put("businessyear", ciDTO.getBusinessyear());
				} else {
					json.put("info", "成功");
					json.put("in", 0);
					json.put("out", 0);
					json.put("scope", 0);
					json.put("ci", 0);
					json.put("sum", 0);
					json.put("preSum", 0);
					json.put("businessyear", ciDTO.getBusinessyear());
				}

			} else {
				if ("1".equals(tempDTO.getAssistype())
						&& "1".equals(tempDTO.getJzjButtonFlag())) {
					HashMap m = tempService.findMaMoney(tempDTO);
					json.put("m", m.get("m"));
					json.put("info", m.get("info"));
					json.put("businessyear", ciDTO.getBusinessyear());
				} else {
					json.put("info", "成功");
					json.put("businessyear", ciDTO.getBusinessyear());
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

/*	@SuppressWarnings("rawtypes")
	public String calcaftermoneyauto2() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (null != organizationId && !"".equals(organizationId)) {
			organizationId = organizationId.substring(0, 6);
		}
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
		int businessyear = this.getBusinessYear(organizationId,tempDTO.getEndtime());
		System.out.println("本次业务年度："+businessyear);
		ciDTO.setBusinessyear(businessyear+"");
		ciDTO.setOrgCode(organizationId);
		ciDTO = yljzService.getCiAssistByPaperIDEx(ciDTO);
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
			json.put("businessyear", ciDTO.getBusinessyear());
		} else {
			json.put("info", "大病保险计算失败!");
		}
		result = json.toString();
		return SUCCESS;
	}*/

	private BigDecimal getCia(TempDTO tempDTO) {
		BigDecimal bl = BigDecimal.ZERO;// 大病保险金
		BigDecimal mline_y = new BigDecimal("11000");// "医保"起助线
		BigDecimal mline_n = new BigDecimal("8000");// "未经医保/新农合确认转诊"起助线
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
		tempDTO.setOrganizationId(orgid);
		tempDTO.setOrg(orgid.substring(0, 6));
		TempDTO temp = tempService.isline(tempDTO);
		if ("0".equals(temp.getResult())) {
			result = "保障金大于封顶线，您重新填写救助金!<br/>累计总救助金：" + temp.getTotlePay()
					+ "元;<br/>住院总救助金：" + temp.getZyPay() + "元;<br/>门诊大病总救助金："
					+ temp.getMzPay() + "元;<br/>封顶线：" + temp.getTopLine()
					+ "元;";
			return "result";
		} else {
			tempDTO = tempService.saveAfterApplyInfo(tempDTO);
		}
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
	
	//调用getAssistMoneyAfterEx，计算救助金
	@SuppressWarnings({ "rawtypes" })
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
			afterDTO.setSpecBiz(-1);
			afterDTO.setBegin_Time(sdf.format(tempDTO.getBegintime()));
			afterDTO.setEnd_Time(sdf.format(tempDTO.getEndtime()));
			afterDTO.setDiagnose_Type_ID(new Integer(tempDTO
					.getDiagnoseTypeId()));
			afterDTO.setIcd_ID(0);//原的门诊特殊大病
			afterDTO.setPay_Total(tempDTO.getPayTotal());
			afterDTO.setPay_Medicare(tempDTO.getPayMedicare());
			afterDTO.setPay_OutMedicare(tempDTO.getPayOutmedicare());
			afterDTO.setPay_Sybx(tempDTO.getInsurance());
			afterDTO.setPay_Dbbx(tempDTO.getPayCIAssist());
			afterDTO.setHospital_Level(-1);
			afterDTO.setHospital_Local(-1);
			afterDTO.setHospital_Type(-1);
			int businessyear = this.getBusinessYear(organizationId,tempDTO.getEndtime());
			System.out.println("本次业务年度："+businessyear);
			afterDTO.setBusinessyear(businessyear+"");
			afterDTO = yljzService.getAssistMoneyAfterEx(afterDTO);
			
			if ("1".equals(afterDTO.getReturnFlag())) {
				if ("2".equals(tempDTO.getAssistype())) {
					json.put("m", afterDTO.getAssistMoney());
					json.put("info", afterDTO.getMessage());
					json.put("in", afterDTO.getAssistSumIn());
					json.put("out", afterDTO.getAssistSumOut());
					json.put("ci", afterDTO.getAssistCIA());
					json.put("sum", afterDTO.getAssistSum());
					json.put("calcmsg", afterDTO.getCalcMsg());
					json.put("businessyear", businessyear);
				} else {
					json.put("m", afterDTO.getAssistMoney());
					json.put("info", afterDTO.getMessage());
					json.put("in", afterDTO.getAssistSumIn());
					json.put("out", afterDTO.getAssistSumOut());
					json.put("ci", afterDTO.getAssistCIA());
					json.put("sum", afterDTO.getAssistSum());
					json.put("calcmsg", afterDTO.getCalcMsg());
					json.put("businessyear", businessyear);
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
	
	/**
	 * 审批查询初始化
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String queryafterapprovedoneinit() {
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();

		if (6 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
		}
		return SUCCESS;

	}
	
	/**
	 * 审批查询
	 * @return
	 */
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
	/**
	 *审批不同意
	 * @return
	 */
	public String afteryn() {
		tempDTO = tempService.saveAfteryn(tempDTO);
		result = "保存成功";
		return SUCCESS;
	}
	
	/*
	 * 出院时间与审批时间的年份一致时，本次业务年度为此年度；
	 * 出院时间为上一年（没有跨两年业务），审批时间是跨年的业务时，本次业务年度为
	 *		如果小于jz_year的设定时间，就以出院时间的年份为准；
	 *		如果大于jz_year的设定时间，就以审批时间的年份为准；
	 */
	private int getBusinessYear(String orgid,Date endtime){
		GregorianCalendar g = new GregorianCalendar();
		int year = g.get(Calendar.YEAR);// 获取年份
		Calendar c = Calendar.getInstance();
        c.setTime(endtime);
        int inyear = c.get(Calendar.YEAR);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		String sysdate = sdf.format(date);
		if(year==inyear){
		}else{
			if((year-1)==inyear){
				JzYearDTO jzYearDTO = new JzYearDTO();
				jzYearDTO.setOrganizationId(orgid);
				String businessdate = tempService.getBusinessYear(jzYearDTO);
				int i = this.compare_date(sysdate, businessdate);
				if(i==1){
				}else{
					year = year - 1;
				}
			}else{
				year = 0;
			}
		}
		return year;
	}
	
	private int compare_date(String DATE1, String DATE2) {
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = sdf.parse(DATE1);
            Date dt2 = sdf.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                //System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                //System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
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

	public AfterDTO getAfterDTO() {
		return afterDTO;
	}

	public void setAfterDTO(AfterDTO afterDTO) {
		this.afterDTO = afterDTO;
	}

	public List<OrganizationDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrganizationDTO> orgs) {
		this.orgs = orgs;
	}

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperational() {
		return operational;
	}

	public void setOperational(String operational) {
		this.operational = operational;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getAssistype() {
		return assistype;
	}

	public void setAssistype(String assistype) {
		this.assistype = assistype;
	}

}
