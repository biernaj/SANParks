package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblLogin extends ScanTableBase implements IModel {

	private DBForeignKey 			xavia_id;			
	private DBForeignKey 			user_id;			
	private DBDate 					login_date;			// YYYY-MM-DD HH24-MM-SS
	private DBEnum<E_LOGIN_STATUS> 	login_status;		// [SUCCESS|FAILED_USERNAME|FAILED_MAX_ATTEMPTS|FAILED_NOT_ACTIVE]
	private DBEnum<E_LOGIN_METHOD>	login_method;
	private DBString 				gps_coordinates;	// picked up from the Xavia, to determine entry gate				

//	public void onCreate () {
//		sql = "create table login ("
//				+ "id 				integer primary key autoincrement, "
//				+ "xavia_id			integer, "
//				+ "user_id			integer default 0, "
//				+ "login_date		text not null, "	// YYYY-MM-DD HH24:MM:SS
//				+ "login_status		text not null, "	// [SUCCESS|FAILED_USERNAME|FAILED_PASSWORD|FAILED_NOT_ACTIVE]
//				+ "gps_coordinates	text"				// picked up from the Xavia, to determine entry gate				
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

	public DBDate getLogin_date() {
		return login_date;
	}

	public void setLogin_date(String login_date) throws ParseException {
		this.login_date.setVal(login_date);
	}

	public void setLogin_date(DBDate login_date) {
		this.login_date = login_date;
	}

	public E_LOGIN_STATUS getLogin_status() {
		return login_status;
	}

	public void setLogin_status(E_LOGIN_STATUS login_status) {
		this.login_status = login_status;
	}

	public E_LOGIN_METHOD getLogin_method() {
		return login_method;
	}

	public void setLogin_method(E_LOGIN_METHOD login_method) {
		this.login_method = login_method;
	}

	public String getGps_coordinates() {
		return gps_coordinates.getVal();
	}

	public void setGps_coordinates(String gps_coordinates) {
		this.gps_coordinates.setVal(gps_coordinates);
	}

}
