package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblVisitor extends ScanTableBase implements IModel {

	private DBString 	first_name;
	private DBString 	last_name;
	private DBEnum 		id_type;			// E_ID_TYPE
	private DBString 	id_number;			// ID/Passport Number
	private DBDate 		first_entry;		// YYYY-MM-DD HH24:MM:SS
	private DBInteger 	entry_count;		// incremented on each entry			
	private DBDate 		last_entry;			// YYYY-MM-DD HH24:MM:SS
	private DBDate 		last_exit;			// YYYY-MM-DD HH24:MM:SS
	private DBInteger 	exit_count;			// incremented on each exit	
	private DBInteger 	suspicion_count;	
	
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
		return first_name.getVal();
	}

	public void setFirst_name(String first_name) {
		this.first_name.setVal(first_name);
	}

	public String getLast_name() {
		return last_name.getVal();
	}

	public void setLast_name(String last_name) {
		this.last_name.setVal(last_name);
	}

	public String getId_number() {
		return id_number.getVal();
	}

	public void setId_number(String id_number) {
		this.id_number.setVal(id_number);
	}

	public E_ID_TYPE getId_type() {
		final Class<E_ID_TYPE> enumClass = E_ID_TYPE.class;
		
		return (E_ID_TYPE) id_type.getVal(enumClass);
	}

	public void setId_type(E_ID_TYPE newVal) {
		final Class<E_ID_TYPE> enumClass = E_ID_TYPE.class;

		this.id_type.setVal(enumClass, newVal);
	}	
	
	public String getFirst_entry() {
		return first_entry.getValString();
	}

	public void setFirst_entry(String first_entry) throws ParseException {
		this.first_entry.setVal(first_entry);
	}

	public int getEntry_count() {
		return entry_count.getVal();
	}

	public void setEntry_count(int entry_count) {
		this.entry_count.setVal(entry_count);
	}

	public String getLast_entry() {
		return last_entry.getValString();
	}

	public void setLast_entry(String last_entry) throws ParseException {
		this.last_entry.setVal(last_entry);
	}

	public String getLast_exit() {
		return last_exit.getValString();
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

	public int getSuspicion_count() {
		return suspicion_count.getVal();
	}

	public void setSuspicion_count(int suspicion_count) {
		this.suspicion_count.setVal(suspicion_count);
	}

}
