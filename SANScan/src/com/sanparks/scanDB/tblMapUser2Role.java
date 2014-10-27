package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblMapUser2Role extends ScanTableBase implements IModel {

	private DBForeignKey 	xavia_id;
	private DBForeignKey 	user_id;	 
	private DBForeignKey 	user_role_id;	
	private boolean 		b_active;

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

	public DBForeignKey getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(DBForeignKey user_role_id) {
		this.user_role_id = user_role_id;
	}

	public boolean isB_active() {
		return b_active;
	}

	public void setB_active(boolean b_active) {
		this.b_active = b_active;
	}

}
