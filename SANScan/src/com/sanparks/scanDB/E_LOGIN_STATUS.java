package com.sanparks.scanDB;

public enum E_LOGIN_STATUS {
	LOGIN_SUCCESS,
	LOGIN_FAILED_USERNAME,
	LOGIN_FAILED_PASSWORD,
	LOGIN_FAILED_ACCOUNT_LOCKED;

	public final static E_LOGIN_STATUS values[] = values();
	
}
