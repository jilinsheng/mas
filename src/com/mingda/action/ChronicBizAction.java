package com.mingda.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.mingda.dto.BillInfoDTO;
import com.mingda.dto.ChronicBizDTO;
import com.mingda.dto.ChronicCheckDTO;
import com.mingda.dto.OrganizationDTO;
import com.mingda.dto.UserDTO;
import com.mingda.service.ChronicBizService;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ChronicBizAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	private List<OrganizationDTO> orgs;
	private List<ChronicCheckDTO> ccds;
	private List<ChronicBizDTO> bbds;
	private String toolsmenu;
	private String oid;
	private String result;
	private String value;
	private String operational;
	private String term;
	private String cur_page;
	private SystemDataService systemDataService;
	private ChronicBizService chronicBizService;
	private String memberId;
	private String memberType;
	private String familyno;
	private String membername;
	private String paperid;
	private BillInfoDTO billInfoDTO;
	private ChronicCheckDTO chronicCheckDTO;
	private String payassist;
	private String payself;
	private String remark;
	private String paymedbalance;
	private String hospitalname;
	private String buydrugcost;
	private String balance;
	private String bizId;

	
	@SuppressWarnings("rawtypes")
	public String querychronicbizappinit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 4 == organizationId.length()
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicbizmember(){
		// 获取机构
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
					jwhere = jwhere + " and  mv.SSN  " + var;
				} else if ("FAMILYNO".equals(term)) {
					jwhere = jwhere + " and  mv.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  mv.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  mv.PAPERID " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  mv.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  mv.familyno like '" + oid + "%' ";
			}
			sql = " select * from mv_chronic mv where mv.sts='1' and mv.salstate ='1' ";
		sql = sql
				+ jwhere
				+ "  order by mv.FAMILYNO ";
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
		setCcds(chronicBizService.findChronicMember(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronicbiz/querychronicbizmember.action"));
		setToolsmenu(chronicBizService.getToolsmenu());
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}
	
	public String buydruginit(){
		billInfoDTO = chronicBizService.findBillInfo(memberId,memberType);
		chronicCheckDTO = chronicBizService.findBizInfo(memberId,memberType);
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String chronicbizsave(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String empid = user.getEmpid();
		JSONObject json1 = new JSONObject();
		JSONArray arr1 = new JSONArray();
		String err[] = { "" };
		BillInfoDTO billInfoDTO = new BillInfoDTO();
		ChronicBizDTO chronicBillDTO = new ChronicBizDTO();
		//帐单表插入
		billInfoDTO.setMemberId(memberId);
		billInfoDTO.setMemberType(memberType);
		billInfoDTO.setIncome(new BigDecimal("0"));
		BigDecimal buydrug = new BigDecimal(buydrugcost);
		BigDecimal assist = new BigDecimal(payassist);
		BigDecimal balance01 = new BigDecimal(balance);
		if(buydrug.compareTo(balance01) == -1){
			billInfoDTO.setPayout(buydrug);
			billInfoDTO.setSubject("非定点药店购药"+ buydrugcost + "元");
		}else{
			billInfoDTO.setPayout(balance01);
			billInfoDTO.setSubject("非定点药店购药"+ balance + "元");
		}
		billInfoDTO.setBalance(new BigDecimal(paymedbalance));
		billInfoDTO.setOpertime(new Date());
		billInfoDTO.setOptsts("1");
		//非定点医院购药帐单表插入
		BigDecimal self = new BigDecimal(payself);
		chronicBillDTO.setPayTotal(assist.add(self));
		chronicBillDTO.setPayAssist(assist);
		chronicBillDTO.setPaySelf(self);
		chronicBillDTO.setSts("1");
		chronicBillDTO.setPayMedbalance(new BigDecimal(paymedbalance));
		chronicBillDTO.setMemberId(memberId);
		chronicBillDTO.setMemberType(memberType);
		chronicBillDTO.setHospitalName(hospitalname);
		chronicBillDTO.setIcard(paperid);
		chronicBillDTO.setMemvername(membername);
		chronicBillDTO.setFamilyNo(familyno);
		chronicBillDTO.setRemark(remark);
		chronicBillDTO.setOperTime(new Date());
		chronicBillDTO.setCreatTime(new Date());
		chronicBillDTO.setUserId(new Integer(empid));
		int results = chronicBizService.chronicbizsave(billInfoDTO, chronicBillDTO);
		if (results == 0) {
			err[0] = "0";
		} else {
			err[0] = "1";
		}
		arr1 = JSONArray.fromObject(err);
		json1.put("save", arr1);
		result = json1.toString();
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String querychronicbizinit(){
		Map session = ActionContext.getContext().getSession();
		UserDTO user = (UserDTO) session.get("user");
		String organizationId = user.getOrganizationId();
		if (6 == organizationId.length() || 4 == organizationId.length()
				|| 2 == organizationId.length()) {
			if (2 == organizationId.length()) {
				orgs = systemDataService.findOrganizationExt(organizationId);
			} else {
				orgs = systemDataService.findOrgParentAndChilds(organizationId);
			}
			return SUCCESS;
		} else {
			result = "您没有操作此功能权限！";
			return ERROR;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querychronicbizmembers(){
		// 获取机构
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
					jwhere = jwhere + " and  biz.FAMILYNO  " + var;
				} else if ("MEMBERNAME".equals(term)) {
					jwhere = jwhere + " and  biz.MEMBERNAME  " + var;
				} else if ("PAPERID".equals(term)) {
					jwhere = jwhere + " and  biz.ICARD " + var;
				} else {
				}
			}
			if (oid == null || "".equals(oid)) {
				jwhere = jwhere + " and  biz.familyno like '" + organizationId
						+ "%' ";
			} else {
				jwhere = jwhere + " and  biz.familyno like '" + oid + "%' ";
			}
			sql = " select * from chronic_biz biz where biz.sts='1' ";
		sql = sql
				+ jwhere
				+ "  order by biz.FAMILYNO,biz.member_id,biz.member_type,biz.createtime ";
		session.put("sql", sql);
		cur_page = "1";
	} else {
		sql = (String) session.get("sql");
	}
		setBbds(chronicBizService.findChronicMembers(sql,
				new BigDecimal(cur_page).intValue(),
				"page/chronicbiz/querychronicbizmembers.action"));
		setToolsmenu(chronicBizService.getToolsmenu());
		if (2 == organizationId.length()) {
			orgs = systemDataService.findOrganizationExt(organizationId);
		} else {
			orgs = systemDataService.findOrgParentAndChilds(organizationId);
		}
		return SUCCESS;
	}
	
	public String chronicmoneyback(){
		String type ="";
		JSONObject json1 = new JSONObject();
		JSONArray arr1 = new JSONArray();
		String err[] = { "" };
		BillInfoDTO billInfo_i = new BillInfoDTO();
		BillInfoDTO billInfo_u = new BillInfoDTO();
		BillInfoDTO billInfo_u1 = new BillInfoDTO();
		ChronicBizDTO chronicBizDTO = new ChronicBizDTO();
		chronicBizDTO.setBizId(Integer.parseInt(bizId));
		chronicBizDTO.setSts("0");
		chronicBizDTO.setOperTime(new Date());
		billInfoDTO = chronicBizService.findBillInfo(memberId,memberType);
		if(billInfoDTO.getBizId().compareTo(new BigDecimal(bizId))==0){
			type ="1";
			billInfo_i.setBizId(new BigDecimal(bizId));
			billInfo_i.setMemberId(memberId);
			billInfo_i.setMemberType(memberType);
			billInfo_i.setIncome(billInfoDTO.getPayout());
			billInfo_i.setPayout(new BigDecimal("0"));
			billInfo_i.setBalance(billInfoDTO.getBalance().add(billInfoDTO.getPayout()));
			billInfo_i.setSubject("退费存入购药费"+billInfoDTO.getPayout()+"元");
			billInfo_i.setOpertime(new Date());
			billInfo_i.setOptsts("0");
			billInfo_u.setChronicbillId(billInfoDTO.getChronicbillId());
			billInfo_u.setOptsts("0");
		}else{
			type = "2";
			BillInfoDTO queryBill = chronicBizService.findBill(bizId);
			billInfo_i.setBizId(new BigDecimal(bizId));
			billInfo_i.setMemberId(memberId);
			billInfo_i.setMemberType(memberType);
			billInfo_i.setIncome(queryBill.getPayout());
			billInfo_i.setPayout(new BigDecimal("0"));
			billInfo_i.setBalance(queryBill.getBalance().add(queryBill.getPayout()));
			billInfo_i.setSubject("退费存入购药费"+queryBill.getPayout()+"元");
			billInfo_i.setOpertime(new Date());
			billInfo_i.setOptsts("0");
			billInfo_u.setBizId(new BigDecimal(bizId));
			billInfo_u.setOptsts("0");
			billInfo_u1.setChronicbillId(billInfoDTO.getChronicbillId());
			billInfo_u1.setBalance(billInfoDTO.getBalance().add(queryBill.getPayout()));
		}
		int results = chronicBizService.chronicbizback(chronicBizDTO,billInfo_i,billInfo_u,billInfo_u1,type);

		if (results == 0) {
			err[0] = "0";
		} else {
			err[0] = "1";
		}
		arr1 = JSONArray.fromObject(err);
		json1.put("back", arr1);
		result = json1.toString();
		return SUCCESS;
	}
	
	public ChronicBizService getChronicBizService() {
		return chronicBizService;
	}
	public void setChronicBizService(ChronicBizService chronicBizService) {
		this.chronicBizService = chronicBizService;
	}

	public List<OrganizationDTO> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrganizationDTO> orgs) {
		this.orgs = orgs;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
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

	public String getCur_page() {
		return cur_page;
	}

	public void setCur_page(String cur_page) {
		this.cur_page = cur_page;
	}

	public List<ChronicCheckDTO> getCcds() {
		return ccds;
	}

	public void setCcds(List<ChronicCheckDTO> ccds) {
		this.ccds = ccds;
	}

	public String getToolsmenu() {
		return toolsmenu;
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public String getFamilyno() {
		return familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public BillInfoDTO getBillInfoDTO() {
		return billInfoDTO;
	}

	public void setBillInfoDTO(BillInfoDTO billInfoDTO) {
		this.billInfoDTO = billInfoDTO;
	}

	public String getPayassist() {
		return payassist;
	}

	public void setPayassist(String payassist) {
		this.payassist = payassist;
	}

	public String getPayself() {
		return payself;
	}

	public void setPayself(String payself) {
		this.payself = payself;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPaymedbalance() {
		return paymedbalance;
	}

	public void setPaymedbalance(String paymedbalance) {
		this.paymedbalance = paymedbalance;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public String getBuydrugcost() {
		return buydrugcost;
	}

	public void setBuydrugcost(String buydrugcost) {
		this.buydrugcost = buydrugcost;
	}

	public ChronicCheckDTO getChronicCheckDTO() {
		return chronicCheckDTO;
	}

	public void setChronicCheckDTO(ChronicCheckDTO chronicCheckDTO) {
		this.chronicCheckDTO = chronicCheckDTO;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public List<ChronicBizDTO> getBbds() {
		return bbds;
	}

	public void setBbds(List<ChronicBizDTO> bbds) {
		this.bbds = bbds;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

}
