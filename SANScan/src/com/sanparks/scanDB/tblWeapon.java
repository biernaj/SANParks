package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblWeapon extends ScanTableBase implements IModel{

	private DBForeignKey	visitor_id;
	private E_WEAPON_TYPE	weapon_type;  
	private String			make;
	private	String			model;
	private String			caliber;
	private String			serial_number;
	private String			licence_number;
	private boolean			b_licence_valid;
	private int				suspicion_count;
	
//	public void onCreate () {
//	sql = "create table firearm ("
//			+ "id 				integer primary key autoincrement,"
//			+ "visitor_id		integer not null,"	
//			+ "weapon_type		integer not null," E_WEAPON_TYPE
//			+ "make				text not null,"		
//			+ "model			text,"
//			+ "caliber			text,"
//			+ "serial_number	text,"
//			+ "licence_number	text,"
//			+ "b_licence_valid	boolean,"
//			+ "suspicion_count	integer";	
//	execSQL(sql);
//}
//
//@Override
//public int add() {
//	return 0;
//}
//@Override
//public int update() {
//	return 0;
//}
//@Override
//public int delete() {
//	return 0;
//}
	public DBForeignKey getVisitor_id() {
		return visitor_id;
	}
	public void setVisitor_id(DBForeignKey visitor_id) {
		this.visitor_id = visitor_id;
	}
	public E_WEAPON_TYPE getWeapon_type() {
		return weapon_type;
	}
	public void setWeapon_type(E_WEAPON_TYPE weapon_type) {
		this.weapon_type = weapon_type;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCaliber() {
		return caliber;
	}
	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getLicence_number() {
		return licence_number;
	}
	public void setLicence_number(String licence_number) {
		this.licence_number = licence_number;
	}
	public boolean isB_licence_valid() {
		return b_licence_valid;
	}
	public void setB_licence_valid(boolean b_licence_valid) {
		this.b_licence_valid = b_licence_valid;
	}
	public int getSuspicion_count() {
		return suspicion_count;
	}
	public void setSuspicion_count(int suspicion_count) {
		this.suspicion_count = suspicion_count;
	}

}
