package com.sanparks.scanDB;

public enum E_ENTRY_MODE {
	ENTRY_MODE_DRIVER		(0, "@string/title_driver"),
	ENTRY_MODE_PASSENGER	(1, "@string/title_passenger"),
	ENTRY_MODE_PEDESTRIAN	(2, "@string/title_pedestrian"),
	ENTRY_MODE_ALL			(3, "@string/title_All types");

	private final int 		val;
	private final String	name;

	E_ENTRY_MODE(int Val, String Name) 
	{
		this.val = Val;
		this.name = Name;
	}
	
	public int Val() 	{ return val; }
	public String Name() { return name; }
}

