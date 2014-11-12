package com.sanparks.scanDB;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.memtrip.sqlking.schema.*;

public class tblXavia extends ScanTableBase {

	private DBString	serial_number;			// XAVIA serial number
	private DBString 	imei_number; 					
	private DBDate 		commissioned;			// YYYY-MM-DD HH24:MM:SS	
	private	E_DB_SYNCH	db_synch_status;		// E_DB_SYNCH			
	private DBDate 		db_last_synch_started;	// YYYY-MM-DD HH24:MM:SS.SSS	
	private DBDate 		db_last_good_synch;		// YYYY-MM-DD HH24:MM:SS.SSS	

	public tblXavia () {
		serial_number 			= new DBString();
		imei_number   			= new DBString();
		commissioned  			= new DBDate();
		db_synch_status 		= E_DB_SYNCH.DB_UNSYNCHED;
		db_last_synch_started 	= new DBDate();
		db_last_good_synch		= new DBDate();
	}	
	
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
	public DBDate getCommissioned() {
		return commissioned;
	}
	public void setCommissioned(DBDate date) {
		this.commissioned.set(date);
	}

	public E_DB_SYNCH getDb_synch_status() {
		return db_synch_status;
	}

	public void setDb_synch_status(E_DB_SYNCH newVal) {
		this.db_synch_status = newVal;
	}	

	public DBDate getDb_last_synch_started() {
		return db_last_synch_started;
	}

	public void setDb_last_synch_started(DBDate date) {
		this.db_last_synch_started.set(date);
	}

	public DBDate getDb_last_good_synch() {
		return db_last_good_synch;
	}

	public void setDb_last_good_synch(DBDate date) {
		this.db_last_good_synch.set(date);
	}
	
	//.... Operations

	public static boolean registerXaviaUnit (Activity activity ) {

		String device_identifier = null;
		TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
		
		if (tm != null)
			device_identifier = tm.getDeviceId();
		
		tblXavia recs[] = /* (tblXavia[]) */ ScanDB.sqlProvider.<tblXavia>selectAll(tblXavia.class, "id ASC", "0, 1");	
		
		if (recs == null || recs.length == 0) {		// DB not yet registered on this Xavia unit?

			tblXavia registration_rec = new tblXavia();
			
			registration_rec.setImei_number(device_identifier);
			
			registration_rec.add();

		} else if (! device_identifier.equals(recs[0].getImei_number())) {
			// Error, this device is already registered on another Xavia device
			return false;
		}
			
	return true;
	}
}
