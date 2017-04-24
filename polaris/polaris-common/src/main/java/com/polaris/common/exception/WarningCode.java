package com.polaris.common.exception;

public enum WarningCode {

	CODE_50001(50001, "提示示例一"), CODE_50002(50002, "提示示例一");

	private final int warnCode;

	private final String warnMsg;

	private WarningCode(int warnCode, String warnMsg) {
		this.warnCode = warnCode;
		this.warnMsg = warnMsg;
	}

	public int getWarnCode() {
		return this.warnCode;
	}

	public String getWarnMsg() {
		return this.warnMsg;
	}

	@Override
	public String toString() {
		return "warnCode [" + warnCode + "] warnMsg [" + warnMsg + "]";
	}

	public static WarningCode valueOf(int warnCode) {
		for (WarningCode warn : values()) {
			if (warn.warnCode == warnCode) {
				return warn;
			}
		}
		throw new IllegalArgumentException("No matching WarningCode by warnCode [" + warnCode + "]");
	}

	public static void main(String[] args) {
		System.out.println(WarningCode.valueOf(50001));
	}

}
