package com.sanparks.sanscan;

import com.sanparks.scanDB.tblEntry;

public class EntryWorkflow
	{
	private CHECK_TYPE current_check = null;

	public CHECK_TYPE get_next_check (tblEntry progress) 
		{
		CHECK_TYPE next_check = CHECK_TYPE.DRIVER_PILOT;
		
		switch (current_check)
			{
			case VEHICLE:			// OCR Vehicle Registration and Vehicle Licence Disc if applicable
				next_check = CHECK_TYPE.DRIVER_PILOT;
				break;

			case DRIVER_PILOT:		// OCR ID/passport/driver's or pilot's licence 
				next_check = CHECK_TYPE.PASSENGER;
				break;

			case PASSENGER:			// OCR ID/Passport or driver's licence
			next_check = CHECK_TYPE.PASSENGER;
				break;

			case BIOMETRIC_STAFF:	// Staff are only required to do the fingerprint scan
			next_check = CHECK_TYPE.PASSENGER;
				break;

			case BIOMETRIC:			// all visitors must give us their fingerprint
			next_check = CHECK_TYPE.PASSENGER;
				break;

			case WEAPON:			// OCR weapon licence number, photo of weapon (optional) 
			next_check = CHECK_TYPE.PASSENGER;
				break;

			case WILDCARD:			// get Wildcard number and name, possibly check with SANParks db, also possibly integrate with chip card reader
			next_check = CHECK_TYPE.PASSENGER;
				break;

			case RESERVATION:		// OCR reservation number, possibly integrate with SANParks db to confirm and validate number of guests 
			next_check = CHECK_TYPE.PASSENGER;
				break;

			case COMMENTS:			// User can enter comments if there was something suspicious about the party
			next_check = CHECK_TYPE.PASSENGER;
				break;

			default:
				next_check = CHECK_TYPE.DRIVER_PILOT;
				
			}	
		return next_check;
		}
	
	public CHECK_TYPE getCurrent_check()
		{
		return current_check;
		}

	public void setCurrent_check(CHECK_TYPE current_check)
		{
		this.current_check = current_check;
		}
	}
