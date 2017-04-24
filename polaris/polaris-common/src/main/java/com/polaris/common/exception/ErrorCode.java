package com.polaris.common.exception;

public enum ErrorCode {

	CODE_10001(10001, "网络连接不上，请检查网络连接"), CODE_10002(10002, "服务器发生问题");

	private final int errCode;

	private final String errMsg;

	private ErrorCode(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public int getErrCode() {
		return this.errCode;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	@Override
	public String toString() {
		return "errCode [" + errCode + "] errMsg [" + errMsg + "]";
	}
	
	public static ErrorCode valueOf(int errCode) {
		for (ErrorCode err : values()) {
			if (err.errCode == errCode) {
				return err;
			}
		}
		throw new IllegalArgumentException("No matching ErrorCode by errCode [" + errCode + "]");
	}
	
	public static void main(String[] args) {
		System.out.println(ErrorCode.valueOf(10001));
	}

}
