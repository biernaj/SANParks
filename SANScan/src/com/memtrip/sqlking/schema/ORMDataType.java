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
	public static final int FIELD_BOOLEAN 		= 2;
	public static final int FIELD_LONG 			= 3;
	public static final int FIELD_DOUBLE 		= 4;
	public static final int FIELD_BLOB 			= 5;
	public static final int FIELD_DATE 			= 6;		// maps as an SQLite integer and MySQL DATE
	public static final int FIELD_ENUM 			= 7;		// maps as an SQLite integer and a MySQL ENUM
	public static final int FIELD_PRIMARY_KEY 	= 8;		// maps as an SQLite long and MySQL INT(11)
	public static final int FIELD_FOREIGN_KEY 	= 9;		// maps as an SQLite long and MySQL INT(11)
}
