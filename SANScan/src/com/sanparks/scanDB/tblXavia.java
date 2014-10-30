package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblXavia extends ScanTableBase implements IModel {

	private DBString		serial_number;			// XAVIA serial number
	private DBString 		imei_number; 					
	private DBDate 			commissioned;			// YYYY-MM-DD HH24:MM:SS	
	private	DBEnum			db_synch_status;		// E_DB_SYNCH			
	private DBDate 			db_last_synch_started;	// YYYY-MM-DD HH24:MM:SS.SSS	
	private DBDate 			db_last_good_synch;		// YYYY-MM-DD HH24:MM:SS.SSS	

//	public void onCreate () {
//		sql = "create table xavia ("
//				+ "serial_number			text not null,"	// XAVIA unit serial number
//				+ "imei_number				text,"		
//				+ "commissioned				text,"			// YYYY-MM-DD HH24:MM:SS	
//				+ "synch_status				integer,"		// [DB_SYNCHED|DB_SYNCH_STARTED|DB_SYNCH_FAILED|DB_SYNCH_INTERRUPTED]
//				+ "db_last_synch_started	text,"			// YYYY-MM-DD HH24:MM:SS.SSS
//				+ "db_last_good_synch		text,"			// YYYY-MM-DD HH24:MM:SS.SSS	
//				+ ")";
//		execSQL(sql);
//		
//	}
	
	public String getSerial_number() {
		return serial_number.getVal();
	}
	public void setSerial_number(String xavia_serial_number) {
		serial_number.setVal(xavia_serial_number);
	}
	public String getImei_number() {
		return imei_number.getVal();
	}
	public void setImei_number(String xavia_IMEI) {
		imei_number.setVal(xavia_IMEI);
	}
	public String getCommissioned() {
		return commissioned.getValString();
	}
	public void setCommissioned(String xavia_commissioned) throws ParseException {
		commissioned.setVal(xavia_commissioned);
	}

	public E_DB_SYNCH getDb_synch_status() {
		final Class<E_DB_SYNCH> enumClass = E_DB_SYNCH.class;
		
		return (E_DB_SYNCH) db_synch_status.getVal(enumClass);
	}

	public void setDb_synch_status(E_DB_SYNCH newVal) {
		final Class<E_DB_SYNCH> enumClass = E_DB_SYNCH.class;

		this.db_synch_status.setVal(enumClass, newVal);
	}	

	public String getDb_last_synch_started() {
		return db_last_synch_started.getValString();
	}

	public void setDb_last_synch_started(String db_last_synch_started) throws ParseException {
		this.db_last_synch_started.setVal(db_last_synch_started);
	}

	public String getDb_last_good_synch() {
		return db_last_good_synch.getValString();
	}

	public void setDb_last_good_synch(String db_last_good_synch) throws ParseException {
		this.db_last_good_synch.setVal(db_last_good_synch);
	}
}
