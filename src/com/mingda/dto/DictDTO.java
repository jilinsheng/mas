package com.mingda.dto;

import java.io.Serializable;

public class DictDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6633813307979399960L;
	private Integer dictionaryId;
	private Integer dictsortId;
	private String item;
	private Short sequence;
	private String status;

	public Integer getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Integer dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public Integer getDictsortId() {
		return dictsortId;
	}

	public void setDictsortId(Integer dictsortId) {
		this.dictsortId = dictsortId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Short getSequence() {
		return sequence;
	}

	public void setSequence(Short sequence) {
		this.sequence = sequence;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
