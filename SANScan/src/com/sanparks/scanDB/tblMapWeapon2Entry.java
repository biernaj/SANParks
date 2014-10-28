package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.DBForeignKey;

public class tblMapWeapon2Entry extends ScanTableBase implements IModel{

	private DBForeignKey 		entry_id;			
	private DBForeignKey 		weapon_id;		
	
//	public void onCreate () {
//		sql = "create table map_weapon2entry ("		// Allows for multiple entries and entry reasons
//				+ "id				integer primary key autoincrement,"
//				+ "entry_id			integer not null,"
//				+ "FOREIGN_KEY(entry_id) 		references entry(id),"
//				+ "FOREIGN_KEY(weapon_id) 		references weapon(id),"
//				+ ");"
//				+ "create index mw2e_entry_fk 	on map_weapon2entry (entry_id, id);"
//				+ "create index mw2e_weapon_fk 	on map_weapon2entry (weapon_id);";
//		
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

	public DBForeignKey getEntry_id() {
		return entry_id;
	}

	public void setEntry_id(DBForeignKey entry_id) {
		this.entry_id = entry_id;
	}

	public DBForeignKey getWeapon_id() {
		return weapon_id;
	}

	public void setWeapon_id(DBForeignKey weapon_id) {
		this.weapon_id = weapon_id;
	}


}
