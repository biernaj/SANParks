package com.sanparks.sanscan;

public enum CHECK_DETAIL_TYPE {	
	CDT_VEHICLE		(1, "@string/title_Vehicle"),
	CDT_DRIVER		(2, "@string/title_Driver"),
	CDT_PASSENGER	(3, "@string/title_Passenger"),
	CDT_FIREARM		(4, "@string/title_Firearm");
	
	private final int 		val;
	private final String	name;

	CHECK_DETAIL_TYPE(int Val, String Name) 
	{
		this.val = Val;
		this.name = Name;
	}
	
	public int Val() 	{ return val; }
	public String Name() { return name; }
	
}
