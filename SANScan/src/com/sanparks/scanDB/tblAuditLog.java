package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.schema.*;

public class tblAuditLog extends ScanTableBase {

	private DBForeignKey 	xavia_fk;	
	private DBDate 			log_date; 	// YYYY-MM-DD HH24:MM:SS.SSSS
	private DBInteger		log_code;	// 
	private DBInteger  		log_level;	
	private DBString 		log_text;	

//	public void onCreate () {
//		sql = "create table audit_log ("
//				+ "id				integer primary key autoincrement,"
//				+ "xavia_id			integer,"
//				+ "log_date 		text,"				// YYYY-MM-DD HH24:MM:SS.SSS
//				+ "log_code			integer not null,"	 
//				+ "log_level		integer not null,"
//				+ "log_text			text not null"
//				+ ")";
//		execSQL(sql);
//	}

	public DBKey getXavia_fk() {
		return (DBKey) xavia_fk.get();
	}

	public void setXavia_fk(DBKey xavia_fk) {
		this.xavia_fk.set(xavia_fk);
	}

	public String getLog_date() {
		return log_date.getValString();
	}

	public void setLog_date(String log_date) throws ParseException {
		this.log_date.setVal(log_date);
	}

	public int getLog_level() {
		return log_level.getVal();
	}

	public void setLog_level(int log_level) {
		this.log_level.setVal(log_level);
	}

	public int getLog_code() {
		return log_code.getVal();
	}

	public void setLog_code(int log_code) {
		this.log_code.setVal(log_code);
	}

	public String getLog_text() {
		return log_text.getVal();
	}

	public void setLog_text(String log_text) {
		this.log_text.setVal(log_text);
	}
	
}
