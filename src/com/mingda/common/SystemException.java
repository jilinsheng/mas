package com.mingda.common;

public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// Òì³£´úÂë
	private String key;

	private Object[] values;

	public SystemException() {
		super();
	}

	public SystemException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable throwable) {
		super(throwable);
	}

	public SystemException(String message, String key) {
		super(message);
		this.key = key;
	}

	public SystemException(String message, String key, Object value) {
		super(message);
		this.key = key;
		this.values = new Object[] { value };
	}

	public SystemException(String message, String key, Object[] values) {
		super(message);
		this.key = key;
		this.values = values;
	}

	public String getKey() {
		return key;
	}

	public Object[] getValues() {
		return values;
	}
}
