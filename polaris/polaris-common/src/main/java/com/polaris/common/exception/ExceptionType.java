package com.polaris.common.exception;

public enum ExceptionType {

	WARNING("Warning"),
	
	ERROR("Error");
	
	private final String desc;
	
	private ExceptionType(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public static ExceptionType getExceptionTypeBy(String desc) {
		for (ExceptionType type : values()) {
			if (type.desc.equalsIgnoreCase(desc)) {
				return type;
			}
		}
		throw new IllegalArgumentException("No matching ExceptionType by desc [" + desc + "]");
	}
	
	@Override
	public String toString() {
		return this.desc;
	}
	
	public static void main(String[] args) {
		ExceptionType type = ExceptionType.getExceptionTypeBy("Warning");
		System.out.println(type.toString());
	}
	
}
