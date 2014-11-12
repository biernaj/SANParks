package com.sanparks.scanDB;

import java.text.ParseException;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblUser extends ScanTableBase implements IModel {

	private DBForeignKey 	xavia_fk;		
	private DBForeignKey 	added_by_user_fk;	// MUST be added by another user
	private DBString 		username;			
	private DBString 		first_name;		
	private DBString 		last_name;			
	private DBString 		nickname;		
	private DBString 		id_number;
	private DBString 		password_hash;		// Hash Key
	private DBString 		biometric_hash;	
	private	DBBoolean 		b_administrator;	
	private DBBoolean 		b_create_user;
	private DBDate 			first_login;		// YYYY-MM-DD HH24-MM-SS
	private DBDate 			last_login;			// YYYY-MM-DD HH24-MM-SS
	private DBBoolean 		b_active;			// user can be locked out by setting this to FALSE
	private DBInteger 		suspicion_count;  			

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

	public DBKey getXavia_fk() {
		return (DBKey) xavia_fk.get();
	}

	public void setXavia_fk(DBKey xavia_fk) {
		this.xavia_fk.set(xavia_fk);
	}

	public DBKey getAdded_by_user_fk() {
		return (DBKey) added_by_user_fk.get();
	}

	public void setAdded_by_user_fk(DBKey user_fk) {
		this.added_by_user_fk.set(user_fk);
	}

	public String getUsername() {
		return username.getVal();
	}

	public void setUsername(String username) {
		this.username.setVal(username);
	}

	public String getFirst_name() {
		return first_name.getVal();
	}

	public void setFirst_name(String first_name) {
		this.first_name.setVal(first_name);
	}

	public String getLast_name() {
		return last_name.getVal();
	}

	public void setLast_name(String last_name) {
		this.last_name.setVal(last_name);
	}

	public String getNickname() {
		return nickname.getVal();
	}

	public void setNickname(String nickname) {
		this.nickname.setVal(nickname);
	}

	public String getId_number() {
		return id_number.getVal();
	}

	public void setId_number(String id_number) {
		this.id_number.setVal(id_number);
	}

	public String getPassword_hash() {
		return password_hash.getVal();
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash.setVal(password_hash);
	}

	public String getBiometric_hash() {
		return biometric_hash.getVal();
	}

	public void setBiometric_hash(String biometric_hash) {
		this.biometric_hash.setVal(biometric_hash);
	}

	public boolean getB_administrator() {
		return b_administrator.isVal();
	}

	public void setB_administrator(boolean b_administrator) {
		this.b_administrator.setVal(b_administrator);
	}

	public boolean getB_create_user() {
		return b_create_user.isVal();
	}

	public void setB_create_user(boolean b_create_user) {
		this.b_create_user.setVal(b_create_user);
	}

	public String getFirst_login() {
		return first_login.getValString();
	}

	public void setFirst_login(String first_login_date) throws ParseException {
		first_login.setVal(first_login_date);
	}

	public String getLast_login() {
		return last_login.getValString();
	}

	public void setLast_login(String last_login_date) throws ParseException {
		last_login.setVal(last_login_date);
	}

	public boolean getB_active() {
		return b_active.isVal();
	}

	public void setB_active(boolean b_active) {
		this.b_active.setVal(b_active);
	}

	public int getSuspicion_count() {
		return suspicion_count.getVal();
	}

	public void setSuspicion_count(int suspicion_count) {
		this.suspicion_count.setVal(suspicion_count);
	}


}
