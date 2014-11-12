package com.sanparks.sanscan;

public enum CHECK_TYPE 
	{	
	VEHICLE			("@string/title_vehicle"),		// OCR Vehicle Registration and Vehicle Licence Disc if applicable  
	DRIVER_PILOT	("@string/title_driver_pilot"),	// OCR ID/passport/driver's or pilot's licence 
	PASSENGER		("@string/title_passenger"),	// OCR ID/Passport or driver's licence
	BIOMETRIC_STAFF	("@string/title_staff"),		// Staff are only required to do the fingerprint scan
	BIOMETRIC		("@string/title_biometric"),	// all visitors must give us their fingerprint
	WEAPON			("@string/title_weapon"),		// OCR weapon licence number, photo of weapon (optional) 
	WILDCARD		("@string/title_wildcard"),		// get Wildcard number and name, possibly check with SANParks db, also possibly integrate with chip card reader
	RESERVATION		("@string/title_reservation"),	// OCR reservation number, possibly integrate with SANParks db to confirm and validate number of guests 
	COMMENTS		("@string/title_comments");		// User can enter comments if there was something suspicious about the party
	
	public final static CHECK_TYPE values[] = values();

	public final String	title;

	private CHECK_TYPE(String title) 
		{
		this.title = title;
		}
	
	public String getTitle()	
		{ 
		return title; 
		}
	}
