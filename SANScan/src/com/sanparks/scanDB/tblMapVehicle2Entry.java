package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.DBForeignKey;

public class tblMapVehicle2Entry extends ScanTableBase implements IModel {

	private DBForeignKey		entry_id;	
	private E_VEHICLE_TYPE		vehicle_type;
	private DBForeignKey		vehicle_id;		
	private DBForeignKey		vehicle_img_id;	
	private DBForeignKey		plate_img_id; 	
	private String 				plate_reg_text;
	private DBForeignKey		disc_img_id; 	
	private String 				disc_reg_text;
	private E_ENTRY_REASON 		entry_reason;	
	private int 				visit_duration;	// days

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

	public DBForeignKey getEntry_id() {
		return entry_id;
	}

	public void setEntry_id(DBForeignKey entry_id) {
		this.entry_id = entry_id;
	}

	public DBForeignKey getVehicle_id() {
		return vehicle_id;
	}

	public void setVehicle_id(DBForeignKey vehicle_id) {
		this.vehicle_id = vehicle_id;
	}

	public DBForeignKey getVehicle_img_id() {
		return vehicle_img_id;
	}

	public void setVehicle_img_id(DBForeignKey vehicle_img_id) {
		this.vehicle_img_id = vehicle_img_id;
	}

	public String getPlate_reg_text() {
		return plate_reg_text;
	}

	public void setPlate_reg_text(String plate_reg_text) {
		this.plate_reg_text = plate_reg_text;
	}

	public DBForeignKey getPlate_img_id() {
		return plate_img_id;
	}

	public void setPlate_img_id(DBForeignKey plate_img_id) {
		this.plate_img_id = plate_img_id;
	}

	public String getDisc_reg_text() {
		return disc_reg_text;
	}

	public void setDisc_reg_text(String disc_reg_text) {
		this.disc_reg_text = disc_reg_text;
	}

	public DBForeignKey getDisc_img_id() {
		return disc_img_id;
	}

	public void setDisc_img_id(DBForeignKey disc_img_id) {
		this.disc_img_id = disc_img_id;
	}

	public E_ENTRY_REASON getE_entry_reason() {
		return entry_reason;
	}

	public void setE_entry_reason(E_ENTRY_REASON e_entry_reason) {
		this.entry_reason = e_entry_reason;
	}

	public int getVisit_duration() {
		return visit_duration;
	}

	public void setVisit_duration(int visit_duration) {
		this.visit_duration = visit_duration;
	}

	public E_VEHICLE_TYPE getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(E_VEHICLE_TYPE vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

}
