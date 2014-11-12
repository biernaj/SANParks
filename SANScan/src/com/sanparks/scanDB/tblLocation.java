package com.sanparks.scanDB;

import com.memtrip.sqlking.schema.DBCoordinate;
import com.memtrip.sqlking.schema.DBForeignKey;

public class tblLocation extends ScanTableBase
	{
	E_LOCATION_TYPE 	location_type;
	DBCoordinate		location;
	int					radius;					// meters
	DBForeignKey		parent_location_fk;
	}
