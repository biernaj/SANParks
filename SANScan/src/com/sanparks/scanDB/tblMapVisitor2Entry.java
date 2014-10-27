package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblMapVisitor2Entry extends ScanTableBase implements IModel {

	private DBForeignKey 		entry_id;			
	private E_ENTRY_REASON 		entry_reason;
	private E_ENTRY_MODE 		entry_mode;
	private DBForeignKey 		visitor_id;		
	private DBForeignKey 		visitor_img_id;	
	private DBForeignKey 		identity_img_id;
	private DBForeignKey		vehicle_id;
	private int 				visit_duration;			// expected duration (days)
	private String				reservation_number;
	private String 				wildcard_number;

//	public void onCreate () {
//		sql = "create table map_visitor2entry ("		// Allows for multiple entries and entry reasons
//				+ "id				integer primary key autoincrement,"
//				+ "entry_id			integer not null,"
//				+ "e_entry_reason	text not null,"	// ENUM_ENTRY_REASON
//				+ "visitor_id		integer not null,"
//				+ "visitor_img_id	integer not null,"
//				+ "identity_img_id  integer,"
//				+ "vehicle_id		integer,"	
//				+ "visit_duration	integer,"			// expected duration (days)
//				+ "reservation_number text,"
//				+ "FOREIGN_KEY(entry_id) 		references entry(id),"
//				+ "FOREIGN_KEY(visitor_id) 		references visitor(id),"
//				+ "FOREIGN_KEY(visitor_img_id) 	references image(id),"
//				+ "FOREIGN_KEY(id_img_id) 		references image(id),"
//				+ ");"
//				+ "create index mv2e_entry_fk 		on map_visitor2entry (entry_id, id);"
//				+ "create index mv2e_visitor_fk 	on map_visitor2entry (visitor_id);";
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

	public DBForeignKey getEntry_id() {
		return entry_id;
	}

	public void setEntry_id(DBForeignKey entry_id) {
		this.entry_id = entry_id;
	}

	public DBForeignKey getVisitor_id() {
		return visitor_id;
	}

	public void setVisitor_id(DBForeignKey visitor_id) {
		this.visitor_id = visitor_id;
	}

	public DBForeignKey getVisitor_img_id() {
		return visitor_img_id;
	}

	public void setVisitor_img_id(DBForeignKey visitor_img_id) {
		this.visitor_img_id = visitor_img_id;
	}

	public DBForeignKey getIdentity_img_id() {
		return identity_img_id;
	}

	public void setIdentity_img_id(DBForeignKey identity_img_id) {
		this.identity_img_id = identity_img_id;
	}

	public DBForeignKey getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(DBForeignKey vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public E_ENTRY_REASON getE_entry_reason() {
		return entry_reason;
	}

	public void setE_entry_reason(E_ENTRY_REASON e_entry_reason) {
		entry_reason = e_entry_reason;
	}

	public E_ENTRY_MODE getEntry_mode() {
		return entry_mode;
	}

	public void setEntry_mode(E_ENTRY_MODE entry_mode) {
		this.entry_mode = entry_mode;
	}

	public int getVisit_duration() {
		return visit_duration;
	}

	public void setVisit_duration(int visit_duration) {
		this.visit_duration = visit_duration;
	}

	public String getReservation_number() {
		return reservation_number;
	}

	public void setReservation_number(String reservation_number) {
		this.reservation_number = reservation_number;
	}

	public String getWildcard_number() {
		return wildcard_number;
	}

	public void setWildcard_number(String wildcard_number) {
		this.wildcard_number = wildcard_number;
	}
}
