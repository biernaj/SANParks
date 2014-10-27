package com.sanparks.scanDB;

public class DBDate {

	private String datestamp;
	
	public void set (String date) {
		datestamp = date;
	}
	
	public String getString () {
		return this.datestamp;
	}
	
}
