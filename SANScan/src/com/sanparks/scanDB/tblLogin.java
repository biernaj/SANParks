package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblLogin extends ScanTableBase implements IModel {

	private DBForeignKey 			xavia_fk;			
	private DBForeignKey 			user_fk;			
	private DBDate 					login_date;			// YYYY-MM-DD HH24-MM-SS
	private E_LOGIN_STATUS 			login_status;		// E_LOGIN_STATUS
	private E_LOGIN_METHOD			login_method;		// E_LOGIN_METHOD
	private DBString 				gps_coordinates;	// picked up from the Xavia, to determine entry gate				

//	public void onCreate () {
//		sql = "create table login ("
//				+ "id 				integer primary key autoincrement, "
//				+ "xavia_fk			integer, "
//				+ "user_fk			integer default 0, "
//				+ "login_date		text not null, "	// YYYY-MM-DD HH24:MM:SS
//				+ "login_status		text not null, "	// [SUCCESS|FAILED_USERNAME|FAILED_PASSWORD|FAILED_NOT_ACTIVE]
//				+ "gps_coordinates	text"				// picked up from the Xavia, to determine entry gate				
//				+ ")";
//		execSQL(sql);
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

	public DBDate getLogin_date() {
		return login_date;
	}

	public void setLogin_date(String login_date) throws ParseException {
		this.login_date.setVal(login_date);
	}

	public void setLogin_date(DBDate login_date) {
		this.login_date.set(login_date);
	}

	public E_LOGIN_METHOD getLogin_method() {
		return login_method;
	}

	public void setLogin_method(E_LOGIN_METHOD newVal) {
		this.login_method = newVal;
	}	
	
	public E_LOGIN_STATUS getLogin_status() {
		return login_status;
	}

	public void setLogin_status(E_LOGIN_STATUS newVal) {
		this.login_status = newVal;
	}	
	
	public String getGps_coordinates() {
		return gps_coordinates.getVal();
	}

	public void setGps_coordinates(String gps_coordinates) {
		this.gps_coordinates.setVal(gps_coordinates);
	}

}
