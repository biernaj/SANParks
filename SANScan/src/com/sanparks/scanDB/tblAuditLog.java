package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblAuditLog extends ScanTableBase implements IModel {

	private DBForeignKey 	xavia_id;	
	private String 			log_date; 	// YYYY-MM-DD
	private String 			log_time; 	// HH24-MM-SS
	private int 			log_code;	// 
	private int 			log_level;	
	private String 			log_text;	

//	public void onCreate () {
//		sql = "create table audit_log ("
//				+ "id				integer primary key autoincrement, "
//				+ "xavia_id			integer, "
//				+ "log_date 		text, "				// YYYY-MM-DD
//				+ "log_time 		text, "				// HH24-MM-SS
//				+ "log_code			integer not null, "	// 
//				+ "log_level		integer not null, "
//				+ "log_text			text not null "
//				+ ")";
//		execSQL(sql);
//	}
//
//	@Override
//	public int add() {
//		return 0;
//	}
//	@Override
//	public int update() {
//		return 0;
//	}
//	@Override
//	public int delete() {
//		return 0;
//	}

	public DBForeignKey getXavia_id() {
		return xavia_id;
	}

	public void setXavia_id(DBForeignKey xavia_id) {
		this.xavia_id = xavia_id;
	}

	public String getLog_date() {
		return log_date;
	}

	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}

	public String getLog_time() {
		return log_time;
	}

	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}

	public int getLog_level() {
		return log_level;
	}

	public void setLog_level(int log_level) {
		this.log_level = log_level;
	}

	public int getLog_code() {
		return log_code;
	}

	public void setLog_code(int log_code) {
		this.log_code = log_code;
	}

	public String getLog_text() {
		return log_text;
	}

	public void setLog_text(String log_text) {
		this.log_text = log_text;
	}
	
}
