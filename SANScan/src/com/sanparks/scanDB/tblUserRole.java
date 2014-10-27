package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblUserRole extends ScanTableBase implements IModel {
	
	private String 	role_name;			 
	private boolean b_admin;		// Superuser?
	private boolean b_active;		
	private boolean b_create_user;	// Can the role add users?

//	public void onCreate () {
//		sql = "create table user_role ("
//				+ "id 				integer primary key autoincrement, "
//				+ "role_name		text not null, "	 
//				+ "b_admin			integer, "			// super user
//				+ "b_active			integer, "			// Boolean
//				+ "b_create_user	integer"			// Boolean - Can the role add users?
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

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public boolean isB_admin() {
		return b_admin;
	}

	public void setB_admin(boolean b_admin) {
		this.b_admin = b_admin;
	}

	public boolean isB_active() {
		return b_active;
	}

	public void setB_active(boolean b_active) {
		this.b_active = b_active;
	}

	public boolean isB_create_user() {
		return b_create_user;
	}

	public void setB_create_user(boolean b_create_user) {
		this.b_create_user = b_create_user;
	}


}
