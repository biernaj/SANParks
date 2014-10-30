package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblMapUser2Role extends ScanTableBase implements IModel {

	private DBForeignKey 	xavia_id;
	private DBForeignKey 	user_id;	 
	private DBForeignKey 	user_role_id;	
	private DBBoolean 		b_active;

//	public void onCreate () {
//		sql = "create table map_user2role ("
//				+ "id 				integer primary key autoincrement, "
//				+ "xavia_id			integer, "
//				+ "user_id			integer not null, "	 
//				+ "user_role_id		integer not null, "	
//				+ "b_active			integer "			// Boolean
//				+ ")";
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

	public int getXavia_id() {
		return xavia_id.getVal();
	}

	public void setXavia_id(int xavia_id) {
		this.xavia_id.setVal(xavia_id);
	}

	public int getUser_id() {
		return user_id.getVal();
	}

	public void setUser_id(int user_id) {
		this.user_id.setVal(user_id);
	}

	public int getUser_role_id() {
		return user_role_id.getVal();
	}

	public void setUser_role_id(int user_role_id) {
		this.user_role_id.setVal(user_role_id);
	}

	public boolean isB_active() {
		return b_active.isVal();
	}

	public void setB_active(boolean b_active) {
		this.b_active.setVal(b_active);
	}

}
