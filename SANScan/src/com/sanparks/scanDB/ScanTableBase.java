package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.schema.*;

public abstract class ScanTableBase 
	{
	// Fields common to all tables
	protected	DBRecordID		id;
	protected	DBEnum 			synch_status;	// E_DB_SYNCH		
	protected	DBDate			synch_date;
	
//	public abstract int add		(); 
//	public abstract int update	(); 
//	public abstract int delete	(); 
//
//	public DB_table get()
//	{
//		return this;	
//	}
	
	/* _create_table_SQL()
	 * 
	 * creates the table with the extra common fields for DB synchronization and management
	 */
	protected String _create_table_SQL (String tableName, String tableFields) 
	{
		return "create table " + tableName + "("
				+ "id 			integer primary key autoincrement, " 
				+ "synch_status	integer,"
				+ "synch_date 	integer,"	// time stamp;
				+ tableFields;
	}
	
	//......record field methods
	
	public int getId() 
	{
		return id.getVal();
	}

	protected void setId(int id) 
	{
		this.id.setVal(id);
	}

	public E_DB_SYNCH getSynch_status() {
		final Class<E_DB_SYNCH> enumClass = E_DB_SYNCH.class;
		
		return (E_DB_SYNCH) synch_status.getVal(enumClass);
	}

	public void setSynch_status(E_DB_SYNCH newVal) {
		final Class<E_DB_SYNCH> enumClass = E_DB_SYNCH.class;

		this.synch_status.setVal(enumClass, newVal);
	}	
	
	public String getSynch_date() {
		return synch_date.getValString();
	}

	public void setSynch_date(String synch_date) throws ParseException {
		this.synch_date.setVal(synch_date);
	}
}
