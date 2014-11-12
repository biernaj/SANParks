package com.sanparks.scanDB;

import com.memtrip.sqlking.base.*;
import com.memtrip.sqlking.schema.*;

public class tblMapWeapon2Entry extends ScanTableBase implements IModel{

	private DBForeignKey 		entry_fk;			
	private DBForeignKey 		weapon_fk;		
	
//	public void onCreate () {
//		sql = "create table map_weapon2entry ("		// Allows for multiple entries and entry reasons
//				+ "id				integer primary key autoincrement,"
//				+ "entry_fk			integer not null,"
//				+ "FOREIGN_KEY(entry_fk) 		references entry(id),"
//				+ "FOREIGN_KEY(weapon_fk) 		references weapon(id),"
//				+ ");"
//				+ "create index mw2e_entry_fk 	on map_weapon2entry (entry_fk, id);"
//				+ "create index mw2e_weapon_fk 	on map_weapon2entry (weapon_fk);";
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

	public DBKey getEntry_fk() {
		return (DBKey) entry_fk.get();
	}

	public void setEntry_fk(DBKey entry_fk) {
		this.entry_fk.set(entry_fk);
	}

	public DBKey getWeapon_fk() {
		return (DBKey) weapon_fk.get();
	}

	public void setWeapon_fk(DBKey weapon_fk) {
		this.weapon_fk.set(weapon_fk);
	}


}
