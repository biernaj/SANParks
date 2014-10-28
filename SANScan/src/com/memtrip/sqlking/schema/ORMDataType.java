/**
 * Copyright 2013-present memtrip.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.memtrip.sqlking.schema;

/**
 * ORM data types
 * @author	memtrip
 */
public class ORMDataType {
	public static final int FIELD_STRING 		= 0;
	public static final int FIELD_INTEGER 		= 1;
	public static final int FIELD_LONG 			= 2;
	public static final int FIELD_DOUBLE 		= 3;
	public static final int FIELD_BLOB 			= 4;
	public static final int FIELD_BOOLEAN 		= 5;
	public static final int FIELD_ENUM 			= 6;
	public static final int FIELD_PRIMARY_KEY 	= 7;		// maps as an SQLite long and MySQL INT(11)
	public static final int FIELD_FOREIGN_KEY 	= 8;		// maps as an SQLite long and MySQL INT(11)
	public static final int FIELD_DATE 			= 9;		// maps as an SQLite integer and MySQL DATE
	public static final int FIELD_TIME 			= 10;		// maps as an SQLite integer and MySQL TIME
	public static final int FIELD_DATETIME 		= 11;		// maps as an SQLite long and MySQL TIMESTAMP
}
