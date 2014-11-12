package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblMapUser2Role extends ScanTableBase implements IModel {

	private DBForeignKey 	xavia_fk;
	private DBForeignKey 	user_fk;	 
	private DBForeignKey 	user_role_fk;	
	private DBBoolean 		b_active;

//	public void onCreate () {
//		sql = "create table map_user2role ("
//				+ "id 				integer primary key autoincrement, "
//				+ "xavia_fk			integer, "
//				+ "user_fk			integer not null, "	 
//				+ "user_role_fk		integer not null, "	
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

	public DBKey getXavia_fk() {
		return (DBKey) xavia_fk.get();
	}

	public void setXavia_fk(DBKey xavia_fk) {
		this.xavia_fk.set(xavia_fk);
	}

	public DBKey getUser_fk() {
		return (DBKey) user_fk.get();
	}

	public void setUser_fk(DBKey user_fk) {
		this.user_fk.set(user_fk);
	}

	public DBKey getUser_role_fk() {
		return (DBKey) user_role_fk.get();
	}

	public void setUser_role_fk(DBKey user_role_fk) {
		this.user_role_fk.set(user_role_fk);
	}

	public boolean getB_active() {
		return b_active.isVal();
	}

	public void setB_active(boolean b_active) {
		this.b_active.setVal(b_active);
	}

}
