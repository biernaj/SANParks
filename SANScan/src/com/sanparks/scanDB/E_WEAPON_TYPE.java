package com.sanparks.scanDB;

public enum E_WEAPON_TYPE 
{
	WEAPON_TYPE_PISTOL 		(1, "Pistol"),
	WEAPON_TYPE_RIFLE 		(2, "Rifle"),
	WEAPON_TYPE_SHOTGUN 	(3, "Shotgun"),
	WEAPON_TYPE_MACHINE_GUN (4, "Machine Gun"),
	WEAPON_TYPE_BOW 		(5, "Bow"),
	WEAPON_TYPE_CROSSBOW 	(6, "Crossbow"),
	WEAPON_TYPE_OTHER 		(7, "Other");
	
	private final int 		val;
	private final String	name;
	public final static E_WEAPON_TYPE values[] = values();
	

	E_WEAPON_TYPE(int Val, String Name) 
	{
		this.val = Val;
		this.name = Name;
	}
	
	public int Val() 	{ return val; }
	public String Name() { return name; }
}
