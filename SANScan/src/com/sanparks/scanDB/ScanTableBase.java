package com.sanparks.scanDB;

import android.content.ContentValues;

import com.memtrip.sqlking.base.IModel;
import com.memtrip.sqlking.schema.*;
import com.memtrip.sqlking.helper.DatabaseHelper;
import static com.sanparks.scanDB.E_DB_SYNCH.*;

public abstract class ScanTableBase implements IModel 
	{
	// Fields common to all tables
	private	DBPrimaryKey	id;
	private	E_DB_SYNCH 		synch_status;			
	private	DBDate			synch_date;		// Last good synch date
	private DBBoolean		b_deleted;
	
	public ScanTableBase () {
		synch_status = DB_UNSYNCHED;
		b_deleted	 = new DBBoolean();
		synch_date	 = new DBDate();
	}
		
	
	public DBPrimaryKey add () {

		this.setSynch_status(E_DB_SYNCH.DB_UNSYNCHED);

		ScanDB.sqlProvider.insert(this);
		
		return id;
	}

	public int update () {
		ContentValues 	contentValues = DatabaseHelper.getContentValuesFromBaseModel(this);
		String record_id[] = { this.id.getValString() };

		this.setSynch_status(E_DB_SYNCH.DB_UNSYNCHED);
		
		return ScanDB.sqlProvider.update(contentValues, "id = ?", record_id, this.getClass());
	}

	public int delete	(DBRecordID id) {

		this.setB_deleted(true);
		
		this.setSynch_status(E_DB_SYNCH.DB_UNSYNCHED);
		
		return ScanDB.sqlProvider.deleteBy(this.getClass(), "id", id.getValString());
	}

	public ScanTableBase retrieve(DBPrimaryKey primary_key)
	{
	
		return this;	
	}
	
	/* _create_table_SQL()
	 * 
	 * creates the table with the extra common fields for DB synchronization and management
	 */
	protected String create_table_SQL (String tableName, String tableFields) 
	{
		return "create table " + tableName + "("
				+ "id 			integer primary key autoincrement, " 
				+ "synch_status	integer,"
				+ "synch_date 	integer,"	// time stamp;
				+ "b_deleted	boolean,"	
				+ tableFields;
	}
	
	//......record field methods
	
	public DBKey getId() 
	{
		return this.id;
	}

	protected void setId(DBKey id) 
	{
		this.id.set(id);
	}

	protected void setId(long id) 
	{
		this.id.setVal(id);
	}

	public E_DB_SYNCH getSynch_status() {
		return synch_status;
	}

	protected void setSynch_status(E_DB_SYNCH newVal) {
		this.synch_status = newVal;
	}	
	
	public DBDate getSynch_date() {
		return synch_date;
	}

	protected void setSynch_date(DBDate synch_date) {
		this.synch_date.set(synch_date);
	}
	
	public boolean getB_deleted() {
		return this.b_deleted.isVal();
	}

	public void setB_deleted(boolean newVal) {
		this.b_deleted.setVal(newVal);
	}

}
