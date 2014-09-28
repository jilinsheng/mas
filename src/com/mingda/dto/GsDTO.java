package com.mingda.dto;
/**
 * 
 * 公式报表
 *
 */
public class GsDTO {
 
	private String familyno;  //家庭ID
	private String membername;       //名称
	private String paperid;         //身份证
	private String ssn;       //社保ID
	private String sicken;    //救助病种
	private String address;       //家庭地址

	public String getFamilyno() {
		return familyno;
	}

	public void setFamilyno(String familyno) {
		this.familyno = familyno;
	}


	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getMembername() {
		return membername;
	}


	public String getPaperid() {
		return paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getSicken() {
		return sicken;
	}

	public void setSicken(String sicken) {
		this.sicken = sicken;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
