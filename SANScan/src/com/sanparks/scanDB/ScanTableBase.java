package com.sanparks.scanDB;

public abstract class ScanTableBase 
	{
	// Fields common to all tables
	private 	DBRecordID		id;	// Record ID
	private		E_DB_SYNCH		synch_status;
	private		DBDate			synch_date;

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
	
	public DBRecordID getId() 
	{
		return id;
	}

	public E_DB_SYNCH get_record_synch_status() 
	{
		return this.synch_status;
	}

	public void setRecord_synch_status(E_DB_SYNCH record_synch_status) 
	{
		this.synch_status = record_synch_status;
	}

	public DBDate getSynch_date() {
		return synch_date;
	}

	public void setSynch_date(DBDate synch_date) {
		this.synch_date = synch_date;
	}
}
