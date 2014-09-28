package com.mingda.dto;

import java.math.BigDecimal;

public class DiagnoseTypeDTO {
	private Integer diagnoseTypeId;
	private String organizationId;
	private String diagnoseTypeName;
	private BigDecimal scaler;
	private Integer seq;
	public Integer getDiagnoseTypeId() {
		return diagnoseTypeId;
	}
	public void setDiagnoseTypeId(Integer diagnoseTypeId) {
		this.diagnoseTypeId = diagnoseTypeId;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	public String getDiagnoseTypeName() {
		return diagnoseTypeName;
	}
	public void setDiagnoseTypeName(String diagnoseTypeName) {
		this.diagnoseTypeName = diagnoseTypeName;
	}
	public BigDecimal getScaler() {
		return scaler;
	}
	public void setScaler(BigDecimal scaler) {
		this.scaler = scaler;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}
