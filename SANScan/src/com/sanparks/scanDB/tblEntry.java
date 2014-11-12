package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblEntry extends ScanTableBase implements IModel {

	private DBForeignKey 	xavia_fk;			// which Xavia unit is this entry being captured on?
	private DBForeignKey	user_fk;			// operator's user record id
	private DBDate 			entry_date;			// YYYY-MM-DD HH24-MM-SS
	private E_VISIT_REASON	entry_reason;		 		
	private DBInteger		visitor_count;		// may differ from scanned visitor count (allows for visitors without ID)
	private DBBoolean		b_manual_capture;	// manual capture was necessary, OCR failed.
	private DBBoolean 		b_authorised;	 	// the Entry capture process is complete, the party may enter
	private DBBoolean 		b_suspicious;		// there was something suspicious about the party 
	private DBBoolean 		b_weapons;			// the party have weapons with them  
	private DBForeignKey	location_fk;		// which location did this entry occur at?
	private DBString		gps_coordinates;	// picked up from the Xavia, to determine which entry gate, or where a spot-check was done				
	private DBString 		comments;			// User comments

//	public String onCreate() {
//		return "create table entry ("
//				+ "id 				integer primary key autoincrement,"
//				+ "xavia_fk			integer,"
//				+ "user_fk 			integer,"			// Operator's user record id
//				+ "entry_date 		text not null,"	// YYYY-MM-DD
//				+ "entry_reason		integer,"			// ENUM_ENTRY_REASON (Tourism/Business/Research/Staff/etc)
//				+ "visitor_count	integer,"			// May differ from scanned occupant count (allows for visitors without ID)
//		        + "b_manual_capture integer,"			// Boolean - Manual Capture was necessary
//				+ "b_authorised		integer,"			// Boolean 
//				+ "b_suspicious	 	integer,"			// Boolean
//				+ "b_firearms		integer,"			// Boolean
//				+ "gps_coordinates	text,"				// picked up from the Xavia, to determine which entry gate				
//				+ "comments			text"				// User comments
//				+ ")"; 
//		execSQL(sql);
//		
//	}

	public DBKey getXavia_fk() {
		return (DBKey) xavia_fk.get();
	}

	public void setXavia_fk(DBKey xavia_fk) {
		this.xavia_fk.set(xavia_fk);
	}

	public DBKey getUser_fk() {
		return (DBKey) user_fk.get();
	}

	public void setUser_fk(DBKey user_fk) {
		this.user_fk.set(user_fk);
	}

	public String getEntry_date() {
		return entry_date.getValString();
	}

	public void setEntry_date(String entry_date) throws ParseException {
		this.entry_date.setVal(entry_date);
	}

	public boolean getB_manual_capture() {
		return b_manual_capture.isVal();
	}

	public void setB_manual_capture(boolean b_manual_capture) {
		this.b_manual_capture.setVal(b_manual_capture);
	}

	public boolean getB_authorised() {
		return b_authorised.isVal();
	}

	public void setB_authorised(boolean b_authorised) {
		this.b_authorised.setVal(b_authorised);
	}

	public int getVisitor_count() {
		return visitor_count.getVal();
	}

	public void setVisitor_count(int visitor_count) {
		this.visitor_count.setVal(visitor_count);
	}

	public boolean getB_suspicious() {
		return b_suspicious.isVal();
	}

	public void setB_suspicious(boolean b_suspicious) {
		this.b_suspicious.setVal(b_suspicious);
	}

	public E_VISIT_REASON getEntry_reason() {
		
		return entry_reason;
	}

	public void setEntry_reason(E_VISIT_REASON newVal) {

		this.entry_reason = newVal;
	}	
	
	public boolean getB_weapons() {
		return b_weapons.isVal();
	}

	public void setB_weapons(boolean b_firearms) {
		this.b_weapons.setVal(b_firearms);
	}

	public String getComments() {
		return comments.getVal();
	}

	public void setComments(String comments) {
		this.comments.setVal(comments);
	}

	public String getGps_coordinates() {
		return gps_coordinates.getVal();
	}

	public void setGps_coordinates(String gps_coordinates) 
		{
		this.gps_coordinates.setVal(gps_coordinates);
		}

	public DBForeignKey getLocation_fk()
		{
		return location_fk;
		}

	public void setLocation_fk(DBPrimaryKey location_pk)
		{
		this.location_fk = (DBForeignKey) (DBKey) location_pk;	// double cast from Primary key to Foreign Key
		}

	//.... Operations
	
	//	public tblEntry[] getAll ()
}
