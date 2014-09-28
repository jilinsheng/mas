package com.mingda.dto;

public class AddrBookDTO {
	private Integer empId;
	private String organizationId;//机构号
	private String name;//姓名
	private String duty;//职务
	private String workPhone;//办公电话
	private String mobilePhone;//手机
	private String sts;//短信通知数据状态（0：禁用，1：启用）
	private String orgname; //机构名称
	private String privilege;//权限
	private String stsval ; //短信通知值
	private String dutyval; //职务值

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}
	
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String pivilege) {
		this.privilege = pivilege;
	}

	public void setStsval(String stsval) {
		this.stsval = stsval;
	}

	public String getStsval() {
		return stsval;
	}

	public void setDutyval(String dutyval) {
		this.dutyval = dutyval;
	}

	public String getDutyval() {
		return dutyval;
	}
	
}
