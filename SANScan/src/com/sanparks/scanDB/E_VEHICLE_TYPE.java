package com.sanparks.scanDB;

public enum E_VEHICLE_TYPE {
	
	VEHICLE_TYPE_CAR		(1, 	"Car"),
	VEHICLE_TYPE_TRUCK		(2, 	"Truck"),
	VEHICLE_TYPE_TRAILER	(3, 	"Trailer"),
	VEHICLE_TYPE_CARAVAN	(4, 	"Caravan"),
	VEHICLE_TYPE_CAMPER		(5, 	"Camper"),
	VEHICLE_TYPE_MOTORCYCLE	(6, 	"Motorcycle"),
	VEHICLE_TYPE_AIRCRAFT	(7, 	"Aircraft"),
	VEHICLE_TYPE_HELICOPTER (8, 	"Helicopter"),
	VEHICLE_TYPE_BICYCLE	(9, 	"Bicycle"),
	VEHICLE_TYPE_ON_FOOT	(10, 	"Foot"),
	VEHICLE_TYPE_HORSE		(11, 	"Horse"),
	VEHICLE_TYPE_CART		(12, 	"Cart"),
	VEHICLE_TYPE_OTHER		(13, 	"Other");
	
	private final int 		val;
	private final String 	name;

	E_VEHICLE_TYPE (int Val, String Name)
	{
	this.val = Val;
	this.name = Name;
	}

	public int getVal()
	{
		return this.val;
	}
	
	public String getName()
	{
		return this.name;
	}
	

}
