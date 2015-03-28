package com.mingda.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PolicyDTO {

	private String policyId;
    private String policyTitle;
    private String policyMsg;
    private String fileNum;
    private String organizationid;
    private String status;
    private Date creatTime;
    private Date operTime;
    
    private String fullname;
    private BigDecimal uploadnum;
    private String orgname;
    private String serialnumber;
    
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getPolicyTitle() {
		return policyTitle;
	}
	public void setPolicyTitle(String policyTitle) {
		this.policyTitle = policyTitle;
	}
	public String getPolicyMsg() {
		return policyMsg;
	}
	public void setPolicyMsg(String policyMsg) {
		this.policyMsg = policyMsg;
	}
	public String getFileNum() {
		return fileNum;
	}
	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}
	public String getOrganizationid() {
		return organizationid;
	}
	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Date getOperTime() {
		return operTime;
	}
	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public BigDecimal getUploadnum() {
		return uploadnum;
	}
	public void setUploadnum(BigDecimal uploadnum) {
		this.uploadnum = uploadnum;
	}
	public String getOrgname() {
		return orgname;
	}
	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
    
    

}
