package com.mingda.dto;

import java.math.BigDecimal;

public class GuerfileDTO {
	private BigDecimal fileId; 
	private BigDecimal  bizId;
	private  String filename ;
	private String	realpath ; 
	private String realfilename;
	public BigDecimal getFileId() {
		return fileId;
	}
	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
	}
	public BigDecimal getBizId() {
		return bizId;
	}
	public void setBizId(BigDecimal bizId) {
		this.bizId = bizId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getRealpath() {
		return realpath;
	}
	public void setRealpath(String realpath) {
		this.realpath = realpath;
	}
	public String getRealfilename() {
		return realfilename;
	}
	public void setRealfilename(String realfilename) {
		this.realfilename = realfilename;
	}
	
}
