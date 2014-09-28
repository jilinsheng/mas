package com.mingda.dto;

public class IcdDTO {
	private Integer icdId;
	private String icdcode;
	private String name;
	private String pycode;
	private Integer seq;
	private String salvFlag;

	public Integer getIcdId() {
		return icdId;
	}

	public void setIcdId(Integer icdId) {
		this.icdId = icdId;
	}

	public String getIcdcode() {
		return icdcode;
	}

	public void setIcdcode(String icdcode) {
		this.icdcode = icdcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPycode() {
		return pycode;
	}

	public void setPycode(String pycode) {
		this.pycode = pycode;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getSalvFlag() {
		return salvFlag;
	}

	public void setSalvFlag(String salvFlag) {
		this.salvFlag = salvFlag;
	}

}
