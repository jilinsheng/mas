package com.mingda.dto;

import java.util.List;

public class UserDTO {
	private String empid;
	private String empname;
	private String accout;
	private String organizationId;
	private String pwd;
	private String orgname;
	private String fullname;
	// 2010-12-21
	private String newpwd;
	private String repwd;
	// 2011-1-4
	private List<RoleDTO> roles;

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getAccout() {
		return accout;
	}

	public void setAccout(String accout) {
		this.accout = accout;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setRepwd(String repwd) {
		this.repwd = repwd;
	}

	public String getRepwd() {
		return repwd;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setRoles(List<RoleDTO> roles) {
		this.roles = roles;
	}

	public List<RoleDTO> getRoles() {
		return roles;
	}

}
