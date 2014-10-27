package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblXavia extends ScanTableBase implements IModel {

	private String		serial_number;			// XAVIA serial number
	private String 		IMEI; 					
	private DBDate 		commissioned;			// YYYY-MM-DD HH24:MM:SS	
	private	E_DB_SYNCH 	db_synch_status;			
	private DBDate 		db_last_synch_started;	// YYYY-MM-DD HH24:MM:SS.SSS	
	private DBDate 		db_last_good_synch;		// YYYY-MM-DD HH24:MM:SS.SSS	

//	public void onCreate () {
//		sql = "create table xavia ("
//				+ "serial_number			text not null,"	// XAVIA unit serial number
//				+ "IMEI						text,"		
//				+ "commissioned				text,"			// YYYY-MM-DD HH24:MM:SS	
//				+ "synch_status				integer,"		// [DB_SYNCHED|DB_SYNCH_STARTED|DB_SYNCH_FAILED|DB_SYNCH_INTERRUPTED]
//				+ "db_last_synch_started	text,"			// YYYY-MM-DD HH24:MM:SS.SSS
//				+ "db_last_good_synch		text,"			// YYYY-MM-DD HH24:MM:SS.SSS	
//				+ ")";
//		execSQL(sql);
//		
//	}
	
	public String getSerial_number() {
		return serial_number;
	}
	public void setUnit_serial_number(String xavia_serial_number) {
		this.serial_number = xavia_serial_number;
	}
	public String getIMEI() {
		return this.IMEI;
	}
	public void setIMEI(String xavia_IMEI) {
		this.IMEI = xavia_IMEI;
	}
	public DBDate getCommissioned() {
		return commissioned;
	}
	public void setCommissioned(DBDate xavia_commissioned) {
		this.commissioned = xavia_commissioned;
	}

	public E_DB_SYNCH getDb_synch_status() {
		return db_synch_status;
	}

	public void setDb_synch_status(E_DB_SYNCH db_synch_status) {
		this.db_synch_status = db_synch_status;
	}

	public DBDate getDb_last_synch_started() {
		return db_last_synch_started;
	}

	public void setDb_last_synch_started(DBDate db_last_synch_started) {
		this.db_last_synch_started = db_last_synch_started;
	}

	public DBDate getDb_last_good_synch() {
		return db_last_good_synch;
	}

	public void setDb_last_good_synch(DBDate db_last_good_synch) {
		this.db_last_good_synch = db_last_good_synch;
	}
}
