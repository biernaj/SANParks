package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblEntry extends ScanTableBase implements IModel {

	private DBForeignKey 			xavia_id;
	private DBForeignKey			user_id;			// Operator's user record id
	private DBDate 					entry_date;			// YYYY-MM-DD HH24-MM-SS
	private DBEnum<E_ENTRY_REASON>	entry_reason;		
	private DBInteger				total_visitor_count;// May differ from scanned occupant count (allows for visitors without ID)
	private DBBoolean				b_manual_capture;	// Manual Capture was necessary
	private DBBoolean 				b_authorised;	 
	private DBBoolean 				b_suspicious;	
	private DBBoolean 				b_firearms;			// Boolean
	private DBString 				comments;			// User comments
	private DBString				gps_coordinates;	// picked up from the Xavia, to determine which entry gate				

//	public String onCreate() {
//		return "create table entry ("
//				+ "id 				integer primary key autoincrement, "
//				+ "xavia_id			integer, "
//				+ "user_id 			integer, "			// Operator's user record id
//				+ "entry_date 		text not null, "	// YYYY-MM-DD
//				+ "entry_reason		integer, "			// ENUM_ENTRY_REASON (Tourism/Business/Research/Staff/etc)
//				+ "total_visitor_count	integer, "			// May differ from scanned occupant count (allows for visitors without ID)
//		        + "b_manual_capture integer, "			// Boolean - Manual Capture was necessary
//				+ "b_authorised		integer, "			// Boolean 
//				+ "b_suspicious	 	integer, "			// Boolean
//				+ "b_firearms		integer, "			// Boolean
//				+ "comments			text, "				// User comments
//				+ "gps_coordinates	text"				// picked up from the Xavia, to determine which entry gate				
//				+ ")"; 
//		execSQL(sql);
//		
//	}

	public DBForeignKey getXavia_id() {
		return xavia_id;
	}

	public void setXavia_id(DBForeignKey xavia_id) {
		this.xavia_id = xavia_id;
	}

	public DBForeignKey getUser_id() {
		return user_id;
	}

	public void setUser_id(DBForeignKey user_id) {
		this.user_id = user_id;
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

	public boolean isB_authorised() {
		return b_authorised.isVal();
	}

	public void setB_authorised(boolean b_authorised) {
		this.b_authorised.setVal(b_authorised);
	}

	public int getVisitor_count() {
		return total_visitor_count.getVal();
	}

	public void setVisitor_count(int visitor_count) {
		this.total_visitor_count.setVal(visitor_count);
	}

	public boolean isB_suspicious() {
		return b_suspicious.isVal();
	}

	public void setB_suspicious(boolean b_suspicious) {
		this.b_suspicious.setVal(b_suspicious);
	}

	public E_ENTRY_REASON getE_entry_reason() {
		return entry_reason.getVal();
	}

	public void setE_entry_reason(E_ENTRY_REASON e_entry_reason) {
		this.entry_reason.setVal(e_entry_reason);
	}

	public boolean isB_firearms() {
		return b_firearms.isVal();
	}

	public void setB_firearms(boolean b_firearms) {
		this.b_firearms.setVal(b_firearms);
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

	public void setGps_coordinates(String gps_coordinates) {
		this.gps_coordinates.setVal(gps_coordinates);
	}
	
}
