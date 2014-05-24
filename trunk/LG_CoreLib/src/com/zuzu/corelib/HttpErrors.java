package com.zuzu.corelib;

public class HttpErrors {
	public static final int NO_NETWORK_CONNECTION = 0;
	public static final int CONNECTION_REFUSED = 1;
	public static final int CONNECTION_TIMEOUT = 2;
	public static final int NETWORK_UNKNOWN = 3;

	public static final int REQUEST_CANCELLED = 10;

	public static final int NULL_DATA = 30;
	public static final int JSON_PARSE_ERROR = 31;

	private int mErrorCode;
	private String mErrorMsg;

	public HttpErrors(int errorCode) {
		this.mErrorCode = errorCode;
		this.mErrorMsg = "";
	}

	public HttpErrors(int errorCode, String errorMsg) {
		this.mErrorCode = errorCode;
		this.mErrorMsg = errorMsg;
	}

	public int getErrorCode() {
		return mErrorCode;
	}

	public String getErrorMsg() {
		return mErrorMsg;
	}
}
