package com.sanparks.scanDB;

import java.util.ArrayList;
import java.util.Map;

import com.memtrip.sqlking.schema.*;

public class tblMapVehicle2Entry extends ScanTableBase {

	private DBForeignKey	entry_fk;	
	private E_ENTRY_MODE	vehicle_type;	// E_VEHICLE_TYPE
	private DBForeignKey	vehicle_fk;		
	private DBForeignKey	vehicle_img_fk;	
	private DBForeignKey	plate_img_fk; 	
	private DBString 		plate_reg_text;
	private DBForeignKey	disc_img_fk; 	// Licence Disc image
	private DBString 		disc_reg_text;	// Licence Disc text
	private E_VISIT_REASON	entry_reason;	// E_ENTRY_REASON	
	private DBInteger 		visit_duration;	// days

//	public void onCreate () {
//		sql = "create table map_vehicle2entry ("		// Allows for multiple entries, trailers, convoys, etc
//				+ "id					integer primary key,"
//				+ "entry_fk				integer not null,"
//				+ "vehicle_fk			integer not null,"
//				+ "vehicle_img_fk		integer default null,"
//				+ "plate_reg_text 		text 	not null,"
//				+ "plate_img_fk 		integer default null,"
//				+ "disc_reg_text 		text 	not null,"
//				+ "disc_img_fk 			integer default null,"
//				+ "entry_reason			text 	not null,"	// E_ENTRY_REASON
//				+ "visit_duration		integer default 1," // days
//				+ "CONSTRAINT FOREIGN_KEY(xavia_fk) 		references xavia(id),"
//				+ "CONSTRAINT FOREIGN_KEY(entry_fk) 		references entry(id),"
//				+ "CONSTRAINT FOREIGN_KEY(vehicle_fk) 		references vehicle(id),"
//				+ "CONSTRAINT FOREIGN_KEY(vehicle_img_fk) 	references image(id)"
//				+ "CONSTRAINT FOREIGN_KEY(disc_img_fk) 		references image(id)"
//				+ ");"
//				+ "create index mv2e_entry_fk 		on map_vehicle2entry (entry_fk, id);"
//				+ "create index mv2e_vehicle_fk 	on map_vehicle2entry (vehicle_fk);";
//		execSQL(sql);
//	}
//

	public DBKey getEntry_fk() 
		{
		return (DBKey) entry_fk.get();
		}

	public void setEntry_fk(DBKey entry_fk) 
		{
		this.entry_fk.set(entry_fk);
		}

	public DBKey getVehicle_fk() 
		{
		return (DBKey) vehicle_fk.get();
		}

	public void setVehicle_fk(DBKey vehicle_fk) 
		{
		this.vehicle_fk.set(vehicle_fk.get());
		}

	public DBKey getVehicle_img_fk() 
		{
		return (DBKey) vehicle_img_fk.get();
		}

	public void setVehicle_img_fk(DBKey vehicle_img_fk) 
		{
		this.vehicle_img_fk.set(vehicle_img_fk);
		}

	public String getPlate_reg_text() 
		{
		return plate_reg_text.getVal();
		}

	public void setPlate_reg_text(String plate_reg_text) 
		{
		this.plate_reg_text.setVal(plate_reg_text);
		}

	public DBKey getPlate_img_fk() 
		{
		return (DBKey) plate_img_fk.get();
		}

	public void setPlate_img_fk(DBKey plate_img_fk) 
		{
		this.plate_img_fk.set(plate_img_fk);
		}

	public String getDisc_reg_text() 
		{
		return disc_reg_text.getVal();
		}

	public void setDisc_reg_text(String disc_reg_text) 
		{
		this.disc_reg_text.setVal(disc_reg_text);
		}

	public DBKey getDisc_img_fk() 
		{
		return (DBKey) disc_img_fk.get();
		}

	public void setDisc_img_fk(DBKey disc_img_fk) 
		{
		this.disc_img_fk.set(disc_img_fk.get()) ;
		}

	public E_VISIT_REASON getEntry_reason() 
		{
		return entry_reason;
		}

	public void setEntry_reason(E_VISIT_REASON newVal) 
		{
		entry_reason = newVal;
		}	

	public int getVisit_duration() 
		{
		return visit_duration.getVal();
		}

	public void setVisit_duration(int visit_duration) 
		{
		this.visit_duration.setVal(visit_duration);
		}

	public E_ENTRY_MODE getVehicle_type() 
		{
		return vehicle_type;
		}

	public void setVehicle_type(E_ENTRY_MODE newVal) {
		this.vehicle_type = newVal;
	}	

	//.... Operations
	
	/* getEntryVehicles()
	 * @param DBForeignKey entryID - The id of the Entry
	 * @returns ArrayList<Map<String,Object>> - an ArrayList of ObjectMaps (tblVehicle and tblMapVehicle2Entry table columns)  
	 */
	public static ArrayList<Map<String,Object>> getEntryVehicleList (DBForeignKey entryID)
		{
		String query = "SELECT v.* FROM vehicle AS v, map_vehicle2entry AS map" 
					+ " WHERE map.entry_id = ?"
					+ " AND v.id = m.vehicle_id";

		String[] whereConditions = {entryID.getValString()}; 

		try 
			{
			Class<?>[] queryModels = {tblVehicle.class, tblMapVehicle2Entry.class};
		
			return ScanDB.sqlProvider.rawSelectQuery(query, whereConditions, queryModels);
			}
		catch (Exception e) 
			{
			return null;	
			}
		}
	}
