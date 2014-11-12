package com.sanparks.scanDB;

public enum E_ENTRY_MODE 	
	{
	CAR			("Car"),
	TAXI		("Taxi"),
	BUS			("Bus"),
	TRUCK		("Truck"),
	TRAILER		("Trailer"),
	CARAVAN		("Caravan"),
	CAMPER		("Camper"),
	MOTORCYCLE	("Motorcycle"),
	AIRCRAFT	("Aircraft"),
	HELICOPTER 	("Helicopter"),		// Helicopter is a special case, since it can land anywhere
	BICYCLE		("Bicycle"),		// typically Staff
	PEDESTRIAN	("On Foot"),		// typically Staff
	HORSE		("Horse"),			// yes, well 
	CART		("Cart"),			// staff/migrant workers?
	OTHER		("Other");
	
	private final 		String 		 name;
	public final static E_ENTRY_MODE values[] = values();

	E_ENTRY_MODE (String Name)
		{
		this.name = Name;
		}

	public String getName()
		{
		return this.name;
		}
	}
