package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblMapVehicle2Entry extends ScanTableBase implements IModel {

	private DBForeignKey		entry_id;	
	private DBEnum				vehicle_type;	// E_VEHICLE_TYPE
	private DBForeignKey		vehicle_id;		
	private DBForeignKey		vehicle_img_id;	
	private DBForeignKey		plate_img_id; 	
	private DBString 			plate_reg_text;
	private DBForeignKey		disc_img_id; 	
	private DBString 			disc_reg_text;
	private DBEnum 				entry_reason;	// E_ENTRY_REASON	
	private DBInteger 			visit_duration;	// days

//	public void onCreate () {
//		sql = "create table map_vehicle2entry ("		// Allows for multiple entries, trailers, convoys, etc
//				+ "id					integer primary key,"
//				+ "entry_id				integer not null,"
//				+ "vehicle_id			integer not null,"
//				+ "vehicle_img_id		integer default null,"
//				+ "plate_reg_text 		text 	not null,"
//				+ "plate_img_id 		integer default null,"
//				+ "disc_reg_text 		text 	not null,"
//				+ "disc_img_id 			integer default null,"
//				+ "entry_reason			text 	not null,"	// E_ENTRY_REASON
//				+ "visit_duration		integer default 1," // days
//				+ "CONSTRAINT FOREIGN_KEY(xavia_id) 		references xavia(id),"
//				+ "CONSTRAINT FOREIGN_KEY(entry_id) 		references entry(id),"
//				+ "CONSTRAINT FOREIGN_KEY(vehicle_id) 		references vehicle(id),"
//				+ "CONSTRAINT FOREIGN_KEY(vehicle_img_id) 	references image(id)"
//				+ "CONSTRAINT FOREIGN_KEY(disc_img_id) 		references image(id)"
//				+ ");"
//				+ "create index mv2e_entry_fk 		on map_vehicle2entry (entry_id, id);"
//				+ "create index mv2e_vehicle_fk 	on map_vehicle2entry (vehicle_id);";
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

	public int getVehicle_id() {
		return vehicle_id.getVal();
	}

	public void setVehicle_id(int vehicle_id) {
		this.vehicle_id.setVal(vehicle_id);
	}

	public int getVehicle_img_id() {
		return vehicle_img_id.getVal();
	}

	public void setVehicle_img_id(int vehicle_img_id) {
		this.vehicle_img_id.setVal(vehicle_img_id);
	}

	public String getPlate_reg_text() {
		return plate_reg_text.getVal();
	}

	public void setPlate_reg_text(String plate_reg_text) {
		this.plate_reg_text.setVal(plate_reg_text);
	}

	public int getPlate_img_id() {
		return plate_img_id.getVal();
	}

	public void setPlate_img_id(int plate_img_id) {
		this.plate_img_id.setVal(plate_img_id);
	}

	public String getDisc_reg_text() {
		return disc_reg_text.getVal();
	}

	public void setDisc_reg_text(String disc_reg_text) {
		this.disc_reg_text.setVal(disc_reg_text);
	}

	public int getDisc_img_id() {
		return disc_img_id.getVal();
	}

	public void setDisc_img_id(int disc_img_id) {
		this.disc_img_id.setVal(disc_img_id) ;
	}

	public E_ENTRY_REASON getEntry_reason() {
		final Class<E_ENTRY_REASON> enumClass = E_ENTRY_REASON.class;
		
		return (E_ENTRY_REASON) entry_reason.getVal(enumClass);
	}

	public void setEntry_reason(E_ENTRY_REASON newVal) {
		final Class<E_ENTRY_REASON> enumClass = E_ENTRY_REASON.class;

		this.entry_reason.setVal(enumClass, newVal);
	}	

	public int getVisit_duration() {
		return visit_duration.getVal();
	}

	public void setVisit_duration(int visit_duration) {
		this.visit_duration.setVal(visit_duration);
	}

	public E_VEHICLE_TYPE getVehicle_type() {
		final Class<E_VEHICLE_TYPE> enumClass = E_VEHICLE_TYPE.class;
		
		return (E_VEHICLE_TYPE) vehicle_type.getVal(enumClass);
	}

	public void setVehicle_type(E_VEHICLE_TYPE newVal) {
		final Class<E_VEHICLE_TYPE> enumClass = E_VEHICLE_TYPE.class;

		this.vehicle_type.setVal(enumClass, newVal);
	}	

}
