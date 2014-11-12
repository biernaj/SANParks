package com.sanparks.scanDB;

import com.memtrip.sqlking.schema.*;

public class tblMapVisitor2Entry extends ScanTableBase {

	private DBForeignKey 		entry_fk;			
	private E_VISIT_REASON 		entry_reason; 	// E_ENTRY_REASON
	private E_VISITOR_MODE		entry_mode;		// E_ENTRY_MODE
	private DBForeignKey 		visitor_fk;		
	private DBForeignKey 		visitor_img_fk;	
	private DBForeignKey 		identity_img_fk;
	private DBForeignKey		vehicle_fk;
	private DBInteger			visit_duration;			// expected duration (days)
	private DBString			reservation_number;
	private DBString 			wildcard_number;

//	public void onCreate () {
//		sql = "create table map_visitor2entry ("		// Allows for multiple entries and entry reasons
//				+ "id				integer primary key autoincrement,"
//				+ "entry_fk			integer not null,"
//				+ "e_entry_reason	text not null,"	// ENUM_ENTRY_REASON
//				+ "visitor_fk		integer not null,"
//				+ "visitor_img_fk	integer not null,"
//				+ "identity_img_fk  integer,"
//				+ "vehicle_fk		integer,"	
//				+ "visit_duration	integer,"			// expected duration (days)
//				+ "reservation_number text,"
//				+ "FOREIGN_KEY(entry_fk) 		references entry(id),"
//				+ "FOREIGN_KEY(visitor_fk) 		references visitor(id),"
//				+ "FOREIGN_KEY(visitor_img_fk) 	references image(id),"
//				+ "FOREIGN_KEY(id_img_fk) 		references image(id),"
//				+ ");"
//				+ "create index mv2e_entry_fk 		on map_visitor2entry (entry_fk, id);"
//				+ "create index mv2e_visitor_fk 	on map_visitor2entry (visitor_fk);";
//		
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

	public DBKey getEntry_fk() {
		return (DBKey) entry_fk.get();
	}

	public void setEntry_fk(DBKey entry_fk) {
		this.entry_fk.set(entry_fk);
	}

	public DBKey getVisitor_fk() {
		return (DBKey) visitor_fk.get();
	}

	public void setVisitor_fk(DBKey visitor_fk) {
		this.visitor_fk.set(visitor_fk);
	}

	public DBKey getVisitor_img_fk() {
		return (DBKey) visitor_img_fk.get();
	}

	public void setVisitor_img_fk(DBKey visitor_img_fk) {
		this.visitor_img_fk.set(visitor_img_fk);
	}

	public DBKey getIdentity_img_fk() {
		return (DBKey) identity_img_fk.get();
	}

	public void setIdentity_img_fk(DBKey identity_img_fk) {
		this.identity_img_fk.set(identity_img_fk);
	}

	public DBKey getVehicle_fk() {
		return (DBKey) vehicle_fk.get();
	}

	public void setVehicle_fk(DBKey vehicle_fk) {
		this.vehicle_fk.set(vehicle_fk);
	}

	public E_VISIT_REASON getEntry_reason() {
		return entry_reason;
	}

	public void setEntry_reason(E_VISIT_REASON newVal) {
		this.entry_reason = newVal;
	}	

	public E_VISITOR_MODE getEntry_mode() {
		return entry_mode;
	}

	public void setEntry_mode(E_VISITOR_MODE newVal) {
		this.entry_mode = newVal;
	}	

	public int getVisit_duration() {
		return visit_duration.getVal();
	}

	public void setVisit_duration(int visit_duration) {
		this.visit_duration.setVal(visit_duration);
	}

	public String getReservation_number() {
		return reservation_number.getVal();
	}

	public void setReservation_number(String reservation_number) {
		this.reservation_number.setVal(reservation_number);
	}

	public String getWildcard_number() {
		return wildcard_number.getVal();
	}

	public void setWildcard_number(String wildcard_number) {
		this.wildcard_number.setVal(wildcard_number);
	}
}
