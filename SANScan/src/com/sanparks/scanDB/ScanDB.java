package com.sanparks.scanDB;

import java.util.ArrayList;
//import java.util.Date;
import java.util.Map;

import com.memtrip.sqlking.SQLInitialise;
import com.memtrip.sqlking.SQLProvider;
import com.memtrip.sqlking.base.IModel;
import com.memtrip.sqlking.schema.*;

import android.content.Context;

public class ScanDB {
	
	private IModel[] modelArray = new IModel[] {
			new tblAuditLog(),
			new tblEntry(),
			new tblImage(),
			new tblLogin(),
			new tblMapUser2Role(),
			new tblMapVehicle2Entry(),
			new tblMapVisitor2Entry(),
			new tblMapWeapon2Entry(),
			new tblUser(),
			new tblUserRole(),
			new tblVehicle(),
			new tblVisitor(),
			new tblWeapon(),
			new tblXavia()
		};

	private String 				databaseName = "SANscan";
	private int 				versionNumber = 3;
	private SQLInitialise 		sql;	
	public static SQLProvider 	sqlProvider = null;

//	private	DBDate			last_synch;			
//	private String			lastSQL;

	public void init (Context appContext) {
		sql = new SQLInitialise(
			    databaseName,
			    versionNumber,
			    appContext,
			    modelArray
			);

		sqlProvider = new SQLProvider(sql.getDatabase());
	}

	public void pause() {
		
	}
	
	public void resume() {
		
	}
	
	/* getEntryList()
	 * @param orderBy - e.g. 'entry_date DESC'
	 * @param limit - e.g. '0,10'
	 * @returns tblEntry[] - an array of CheckDetail objects -> Vehicle, Driver, Passenger etc. 
	 */
	public static tblEntry[] getEntryList (String orderBy, String limit) 
		{
		if (sqlProvider != null)
			return sqlProvider.selectAll(tblEntry.class, orderBy, limit);
		else
			return null;
		}

//	/* getCheckList()
//	 * @param orderBy - eg 'entry_date DESC'
//	 * @param limit - eg '0,10'
//	 * @returns tblEntry[] - an array of CheckDetail objects -> Vehicle, Driver, Passenger etc. 
//	 */
//	public ArrayList<Map<String,Object>> getCheckList (String orderBy, String limit) 
//	{
//		return sqlProvider.selectAll(tblEntry.class, orderBy, limit);		
//	}
	
	/** getEntryVisitorsByEntryMode
	 * @param entryID - The id of the Entry
	 * @param entryMode - filter for ENTRY_MODE_DRIVER, ENTRY_MODE_PASSENGER etc
	 * @param vehicleID - if no vehicle is present, pass null
	 * @return ArrayList<Map<String,Object>> - an ArrayList of ObjectMaps (tblVisitor and tblMapVisitor2Entry table columns)  
	 */
	public static ArrayList<Map<String,Object>> getEntryVisitorListByEntryMode (DBForeignKey entryID, E_VISITOR_MODE entryMode, DBForeignKey vehicleID)
		{
		String query = "SELECT v.* FROM visitor AS v, map_visitor2entry AS map" 
					+ " WHERE map.entry_id = ?"
					+ " AND v.id = map.visitor_id";

		String[] whereConditions = {entryID.getValString()}; 

		switch (entryMode)
			{
			case PEDESTRIAN:
				break;
				
			case DRIVER:
			case PILOT:
			case CREW:
			case PASSENGER:
				query += " AND map.vehicle_id = ?";
				whereConditions[1] = vehicleID.getValString();
				break;
			case ALL:
				// this case should only be queried on the server
				break;
			}

		try 
			{
			Class<?>[] queryModels = {tblVisitor.class, tblMapVisitor2Entry.class};
		
			return sqlProvider.rawSelectQuery(query, whereConditions, queryModels);
			}
		catch (Exception e) 
			{
			return null;	
			}
			}
	
	/* getEntryWeapons()
	 * @param DBForeignKey entryID - The id of the Entry
	 * @returns ArrayList<Map<String,Object>> - an ArrayList of ObjectMaps (tblWeapon and tblMapWeapon2Entry table columns)  
	 */
	public static ArrayList<Map<String,Object>> getEntryWeaponList (DBForeignKey entryID)
		{
		String query = "SELECT w.* FROM weapon AS v, map_weapon2entry AS map" 
					+ " WHERE map.entry_id = ?"
					+ " AND w.id = map.weapon_id";

		String[] whereConditions = {entryID.getValString()}; 

		try {
			Class<?>[] queryModels = {tblWeapon.class, tblMapWeapon2Entry.class};
		
			return sqlProvider.rawSelectQuery(query, whereConditions, queryModels);
			}
		catch (Exception e) 
			{
			return null;	
			}
		}

	public ArrayList<Map<String,tblEntry>> getNewEntryList () 
		{

//		ArrayList<Map<String, Object>> recList = new ArrayList<Map<String, Object>>();
//		
//		Date today = new Date();
//		
//		
////		recList.add("", );
		
		return null;
		}
	}
