package com.mingda.dto;

import java.math.BigDecimal;

public class RateDTO {
	private String subsection;
	private BigDecimal persum;
	private BigDecimal pertotal;
	private BigDecimal rate;
	public String getSubsection() {
		return subsection;
	}
	public void setSubsection(String subsection) {
		this.subsection = subsection;
	}
	public BigDecimal getPersum() {
		return persum;
	}
	public void setPersum(BigDecimal persum) {
		this.persum = persum;
	}
	public BigDecimal getPertotal() {
		return pertotal;
	}
	public void setPertotal(BigDecimal pertotal) {
		this.pertotal = pertotal;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
