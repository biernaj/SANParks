package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblMapVisitor2Entry extends ScanTableBase implements IModel {

	private DBForeignKey 		entry_id;			
	private DBEnum 				entry_reason; 	// E_ENTRY_REASON
	private DBEnum 				entry_mode;		// E_ENTRY_MODE
	private DBForeignKey 		visitor_id;		
	private DBForeignKey 		visitor_img_id;	
	private DBForeignKey 		identity_img_id;
	private DBForeignKey		vehicle_id;
	private DBInteger			visit_duration;			// expected duration (days)
	private DBString			reservation_number;
	private DBString 			wildcard_number;

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

	public int getEntry_id() {
		return entry_id.getVal();
	}

	public void setEntry_id(int entry_id) {
		this.entry_id.setVal(entry_id);
	}

	public int getVisitor_id() {
		return visitor_id.getVal();
	}

	public void setVisitor_id(int visitor_id) {
		this.visitor_id.setVal(visitor_id);
	}

	public int getVisitor_img_id() {
		return visitor_img_id.getVal();
	}

	public void setVisitor_img_id(int visitor_img_id) {
		this.visitor_img_id.setVal(visitor_img_id);
	}

	public int getIdentity_img_id() {
		return identity_img_id.getVal();
	}

	public void setIdentity_img_id(int identity_img_id) {
		this.identity_img_id.setVal(identity_img_id);
	}

	public int getVehicle_id() {
		return vehicle_id.getVal();
	}

	public void setVehicle_id(int vehicle_id) {
		this.vehicle_id.setVal(vehicle_id);
	}

	public E_ENTRY_REASON getEntry_reason() {
		final Class<E_ENTRY_REASON> enumClass = E_ENTRY_REASON.class;
		
		return (E_ENTRY_REASON) entry_reason.getVal(enumClass);
	}

	public void setEntry_reason(E_ENTRY_REASON newVal) {
		final Class<E_ENTRY_REASON> enumClass = E_ENTRY_REASON.class;

		this.entry_reason.setVal(enumClass, newVal);
	}	

	public E_ENTRY_MODE getEntry_mode() {
		final Class<E_ENTRY_MODE> enumClass = E_ENTRY_MODE.class;
		
		return (E_ENTRY_MODE) entry_mode.getVal(enumClass);
	}

	public void setEntry_mode(E_ENTRY_MODE newVal) {
		final Class<E_ENTRY_MODE> enumClass = E_ENTRY_MODE.class;

		this.entry_mode.setVal(enumClass, newVal);
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
