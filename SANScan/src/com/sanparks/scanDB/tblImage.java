package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblImage extends ScanTableBase implements IModel{

	private String 			table_name;			// dynamic foreign key table name
	private DBForeignKey	table_id;			// dynamic foreign key record id
	private DBDate 			img_date;			// YYYY-MM-DD HH24-MM-SS	
	private String 			img_filename;
	private byte[]			img_data;			// BLOB	

//	public void onCreate () {
//		sql = "create table image ("
//				+ "id 				integer primary key autoincrement, "
//				+ "table_name		text not null, "	// dynamic foreign key table name
//				+ "table_id			integer not null, " // dynamic foreign key record id
//				+ "img_date			text not null, "	// YYYY-MM-DD HH24-MM-SS	
//				+ "img_filename		text, "
//				+ "img_data			blob";	
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

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public DBForeignKey getTable_id() {
		return table_id;
	}

	public void setTable_id(DBForeignKey table_id) {
		this.table_id = table_id;
	}

	public DBDate getImg_date() {
		return img_date;
	}

	public void setImg_date(DBDate img_date) {
		this.img_date = img_date;
	}

	public String getImg_filename() {
		return img_filename;
	}

	public void setImg_filename(String img_filename) {
		this.img_filename = img_filename;
	}

	public byte[] getImg_data() {
		return img_data;
	}

	public void setImg_data(byte[] img_data) {
		this.img_data = img_data;
	}

}
