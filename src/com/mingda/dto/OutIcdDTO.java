package com.mingda.dto;

import java.math.BigDecimal;

public class OutIcdDTO {
	private String outtype;
	private String calcType;
	private BigDecimal fixValue;
	private BigDecimal scale;
	private Integer seq;
	private String sts;
	private Integer icdId;
	private String organizationId;
	private String name ;

	public String getOuttype() {
		return outtype;
	}

	public void setOuttype(String outtype) {
		this.outtype = outtype;
	}

	public String getCalcType() {
		return calcType;
	}

	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}

	public BigDecimal getFixValue() {
		return fixValue;
	}

	public void setFixValue(BigDecimal fixValue) {
		this.fixValue = fixValue;
	}

	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public void setIcdId(Integer icdId) {
		this.icdId = icdId;
	}

	public Integer getIcdId() {
		return icdId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
