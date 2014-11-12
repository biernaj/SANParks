package com.sanparks.scanDB;

import java.text.ParseException;
import java.util.Date;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblVehicle extends ScanTableBase implements IModel {

	private DBDate 		first_entry;			// YYYY-MM-DD HH24:MM:SS.SSS
	private DBBoolean	b_manual_capture;		// Boolean - Manual Capture was necessary
	private DBString	vin_number;				// Vehicle VIN number, from licence disc. Can be checked against chassis VIN
	private DBString 	vehicle_make;
	private DBString 	vehicle_model;
	private DBInteger 	vehicle_year; 			// YYYY
	private DBDate 		last_entry; 			// YYYY-MM-DD HH24-MM-SS
	private DBInteger 	entry_count;			// incremented on each entry			
	private DBDate 		last_exit; 				// YYYY-MM-DD HH24-MM-SS
	private DBInteger 	exit_count;				// incremented on each exit			

//	public void onCreate () {
//		
//		_create_table_SQL ("vehicle", 
//				  "first_entry 		text not null,"		// YYYY-MM-DD HH24-MM-SS
//		        + "b_manual_capture integer,"			// Boolean - Manual Capture was necessary
//				+ "vin_number		text not null
//				+ "vehicle_make 	text not null,"
//		        + "vehicle_model 	text not null,"
//				+ "vehicle_year 	integer,"			// YYYY
//				+ "last_entry_date 	text,"				// YYYY-MM-DD HH24-MM-SS
//				+ "entry_count		integer,"			// incremented on each entry			
//				+ "last_exit_date 	text,"				// YYYY-MM-DD HH24-MM-SS
//				+ "exit_count		integer"			// incremented on each exit			
//		        + ")");
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

	@Override
	public DBPrimaryKey add () {
		
		Date today = new Date();
	
		setEntry_count(1);
		setExit_count(0);

		try {
			setFirst_entry(today.toString());
			setLast_entry(today.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return super.add();
	}
	
	
	public DBDate getFirst_entry() {
		return first_entry.get();
	}

	public void setFirst_entry(String first_entry) throws ParseException {
		this.first_entry.setVal(first_entry);
	}

	public String getVehicle_make() {
		return vehicle_make.getVal();
	}

	public void setVehicle_make(String vehicle_make) {
		this.vehicle_make.setVal(vehicle_make);
	}

	public boolean getB_manual_capture() {
		return b_manual_capture.isVal();
	}

	public void setB_manual_capture(boolean b_manual_capture) {
		this.b_manual_capture.setVal(b_manual_capture);
	}

	public String getVin_number() {
		return vin_number.getVal();
	}

	public void setVin_number(String vin_number) {
		this.vin_number.setVal(vin_number);
	}

	public String getVehicle_model() {
		return vehicle_model.getVal();
	}

	public void setVehicle_model(String vehicle_model) {
		this.vehicle_model.setVal(vehicle_model);
	}

	public int getVehicle_year() {
		return vehicle_year.getVal();
	}

	public void setVehicle_year(int vehicle_year) {
		this.vehicle_year.setVal(vehicle_year);
	}

	public DBDate getLast_entry() {
		return last_entry.get();
	}

	public void setLast_entry(String last_entry) throws ParseException {
		this.last_entry.setVal(last_entry);
	}

	public int getEntry_count() {
		return entry_count.getVal();
	}

	public void setEntry_count(int entry_count) {
		this.entry_count.setVal(entry_count);
	}

	public DBDate getLast_exit() {
		return last_exit.get();
	}

	public void setLast_exit(String last_exit) throws ParseException {
		this.last_exit.setVal(last_exit);
	}

	public int getExit_count() {
		return exit_count.getVal();
	}

	public void setExit_count(int exit_count) {
		this.exit_count.setVal(exit_count);
	}

}
