package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;

public class tblUser extends ScanTableBase implements IModel {

	private DBForeignKey 	xavia_id;		
	private DBForeignKey 	added_by_user_id;	// MUST be added by another user
	private String 			username;			
	private String 			first_name;		
	private String 			last_name;			
	private String 			nickname;		
	private String 			id_number;
	private String 			password_hash;		// Hash Key
	private String 			biometric_hash;	
	private	boolean 		b_administrator;	
	private boolean 		b_create_user;
	private DBDate 			first_login;		// YYYY-MM-DD HH24-MM-SS
	private DBDate 			last_login;			// YYYY-MM-DD HH24-MM-SS
	private boolean 		b_active;			// user can be locked out by setting this to FALSE
	private int 			suspicion_count;  			

//	public void onCreate () {
//		sql = "create table user ("
//				+ "id 				integer primary key autoincrement,"
//				+ "xavia_id			integer,"
//				+ "added_by_user_id	integer not null,"	// MUST be added by another user
//				+ "username			text not null,"
//				+ "first_name		text not null,"
//				+ "last_name		text not null,"	
//				+ "nickname			text,"
//				+ "id_number		text,"	
//				+ "password_hash	text," 				// Hash Key
//				+ "biometric_hash	text,"				
//				+ "b_administrator 	integer,"
//				+ "b_create_user    integer,"
//				+ "first_login		text,"				// YYYY-MM-DD HH24-MM-SS
//				+ "last_login		text,"				// YYYY-MM-DD HH24-MM-SS
//				+ "b_active			integer,"			// Boolean - user can be locked out by setting this to FALSE
//				+ "suspicion_count  integer"			
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

	public DBForeignKey getAdded_by_user_id() {
		return added_by_user_id;
	}

	public void setAdded_by_user_id(DBForeignKey user_id) {
		this.added_by_user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}

	public String getBiometric_hash() {
		return biometric_hash;
	}

	public void setBiometric_hash(String biometric_hash) {
		this.biometric_hash = biometric_hash;
	}

	public boolean isB_administrator() {
		return b_administrator;
	}

	public void setB_administrator(boolean b_administrator) {
		this.b_administrator = b_administrator;
	}

	public boolean isB_create_user() {
		return b_create_user;
	}

	public void setB_create_user(boolean b_create_user) {
		this.b_create_user = b_create_user;
	}

	public String getFirst_login_date() {
		return first_login.getString();
	}

	public void setFirst_login_date(String first_login_date) {
		first_login.set(first_login_date);
	}

	public String getLast_login_date() {
		return last_login.getString();
	}

	public void setLast_login_date(String last_login_date) {
		last_login.set(last_login_date);
	}

	public boolean isB_active() {
		return b_active;
	}

	public void setB_active(boolean b_active) {
		this.b_active = b_active;
	}

	public int getSuspicion_count() {
		return suspicion_count;
	}

	public void setSuspicion_count(int suspicion_count) {
		this.suspicion_count = suspicion_count;
	}


}
