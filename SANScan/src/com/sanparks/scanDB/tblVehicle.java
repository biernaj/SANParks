package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblVehicle extends ScanTableBase implements IModel {

	private DBDate 	first_entry;			// YYYY-MM-DD HH24-MM-SS
	private boolean	b_manual_capture;		// Boolean - Manual Capture was necessary
	private String	vin_number;				// Vehicle VIN number, from licence disc. Can be checked against chassis VIN
	private String 	vehicle_make;
	private String 	vehicle_model;
	private int 	vehicle_year; 			// YYYY
	private DBDate 	last_entry; 			// YYYY-MM-DD HH24-MM-SS
	private int 	entry_count;			// incremented on each entry			
	private DBDate 	last_exit; 				// YYYY-MM-DD HH24-MM-SS
	private int 	exit_count;				// incremented on each exit			

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

	public DBDate getFirst_entry() {
		return first_entry;
	}

	public void setFirst_entry(String first_entry) {
		this.first_entry.set(first_entry);
	}

	public String getVehicle_make() {
		return vehicle_make;
	}

	public void setVehicle_make(String vehicle_make) {
		this.vehicle_make = vehicle_make;
	}

	public boolean getB_manual_capture() {
		return b_manual_capture;
	}

	public void setB_manual_capture(boolean b_manual_capture) {
		this.b_manual_capture = b_manual_capture;
	}

	public String getVin_number() {
		return vin_number;
	}

	public void setVin_number(String vin_number) {
		this.vin_number = vin_number;
	}

	public String getVehicle_model() {
		return vehicle_model;
	}

	public void setVehicle_model(String vehicle_model) {
		this.vehicle_model = vehicle_model;
	}

	public int getVehicle_year() {
		return vehicle_year;
	}

	public void setVehicle_year(int vehicle_year) {
		this.vehicle_year = vehicle_year;
	}

	public DBDate getLast_entry() {
		return last_entry;
	}

	public void setLast_entry(String last_entry) {
		this.last_entry.set(last_entry);
	}

	public int getEntry_count() {
		return entry_count;
	}

	public void setEntry_count(int entry_count) {
		this.entry_count = entry_count;
	}

	public DBDate getLast_exit() {
		return last_exit;
	}

	public void setLast_exit_date(String last_exit) {
		this.last_exit.set(last_exit);
	}

	public int getExit_count() {
		return exit_count;
	}

	public void setExit_count(int exit_count) {
		this.exit_count = exit_count;
	}

}
