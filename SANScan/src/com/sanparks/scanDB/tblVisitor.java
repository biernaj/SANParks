package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblVisitor extends ScanTableBase implements IModel {

	private String 	first_name;
	private String 	last_name;
	private String 	id_type;			// ID/PASSPORT
	private String 	id_number;			// ID/Passport Number
	private String 	first_entry;		// YYYY-MM-DD HH24:MM:SS
	private int 	entry_count;		// incremented on each entry			
	private String 	last_entry;			// YYYY-MM-DD HH24:MM:SS
	private String 	last_exit;			// YYYY-MM-DD HH24:MM:SS
	private int 	exit_count;			// incremented on each exit	
	private int 	suspicion_count;	
	
//	public void onCreate () {
//		sql = "create table visitor ("
//				+ "id				integer primary key autoincrement, "
//				+ "first_name		text not null, "
//				+ "last_name		text not null, "
//				+ "id_type			text not null, "	// ID/PASSPORT
//				+ "id_number		text not null, "	// ID/Passport Number
//				+ "first_entry 		text, "				// YYYY-MM-DD  HH24:MM:SS
//				+ "entry_count		integer, "			// incremented on each entry			
//				+ "last_entry_date 	text, "				// YYYY-MM-DD HH24:MM:SS
//				+ "last_exit_date 	text, "				// YYYY-MM-DD HH24:MM:SS
//				+ "exit_count		integer"			// incremented on each exit			
//				+ ")";
//
//		execSQL(sql);
//	
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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getFirst_entry() {
		return first_entry;
	}

	public void setFirst_entry(String first_entry) {
		this.first_entry = first_entry;
	}

	public int getEntry_count() {
		return entry_count;
	}

	public void setEntry_count(int entry_count) {
		this.entry_count = entry_count;
	}

	public String getLast_entry() {
		return last_entry;
	}

	public void setLast_entry(String last_entry) {
		this.last_entry = last_entry;
	}

	public String getLast_exit() {
		return last_exit;
	}

	public void setLast_exit(String last_exit) {
		this.last_exit = last_exit;
	}

	public int getExit_count() {
		return exit_count;
	}

	public void setExit_count(int exit_count) {
		this.exit_count = exit_count;
	}

	public int getSuspicion_count() {
		return suspicion_count;
	}

	public void setSuspicion_count(int suspicion_count) {
		this.suspicion_count = suspicion_count;
	}

}
