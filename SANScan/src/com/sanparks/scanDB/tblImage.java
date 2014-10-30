package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblImage extends ScanTableBase implements IModel{

	private DBString 		table_name;			// dynamic foreign key table name
	private DBForeignKey	table_id;			// dynamic foreign key record id
	private DBDate 			img_date;			// YYYY-MM-DD HH24-MM-SS	
	private DBString 		img_filename;
	private DBBlob			img_data;			// BLOB	

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
		return table_name.getVal();
	}

	public void setTable_name(String table_name) {
		this.table_name.setVal(table_name);
	}

	public int getTable_id() {
		return table_id.getVal();
	}

	public void setTable_id(int table_id) {
		this.table_id.setVal(table_id);
	}

	public String getImg_date() {
		return img_date.getValString();
	}

	public void setImg_date(String img_date) throws ParseException {
		this.img_date.setVal(img_date);
	}

	public String getImg_filename() {
		return img_filename.getVal();
	}

	public void setImg_filename(String img_filename) {
		this.img_filename.setVal(img_filename);
	}

	public byte[] getImg_data() {
		return img_data.getVal();
	}

	public void setImg_data(byte[] img_data) {
		this.img_data.setVal(img_data);
	}

}
