package com.mingda.dto;

import java.math.BigDecimal;

public class TempRuleDTO {
	private Integer ruletempId;
	private String organizationId;
	private String personType;
	private BigDecimal scale;
	private String personTypeNj;
	private BigDecimal scaleNj;
	private String npersonType;
	private BigDecimal nscale;


	public Integer getRuletempId() {
		return ruletempId;
	}

	public void setRuletempId(Integer ruletempId) {
		this.ruletempId = ruletempId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public BigDecimal getScale() {
		return scale;
	}

	public void setScale(BigDecimal scale) {
		this.scale = scale;
	}

	public String getPersonTypeNj() {
		return personTypeNj;
	}

	public void setPersonTypeNj(String personTypeNj) {
		this.personTypeNj = personTypeNj;
	}

	public BigDecimal getScaleNj() {
		return scaleNj;
	}

	public void setScaleNj(BigDecimal scaleNj) {
		this.scaleNj = scaleNj;
	}

	public String getNpersonType() {
		return npersonType;
	}

	public void setNpersonType(String npersonType) {
		this.npersonType = npersonType;
	}

	public BigDecimal getNscale() {
		return nscale;
	}

	public void setNscale(BigDecimal nscale) {
		this.nscale = nscale;
	}

}
