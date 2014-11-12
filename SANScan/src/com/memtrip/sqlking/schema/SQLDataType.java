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
 * SQL data types
 * @author	memtrip
 */
public class SQLDataType {
	// direct primitive types
	public static final String SQL_TEXT 	= "text";
	public static final String SQL_INTEGER 	= "integer";
	public static final String SQL_BOOLEAN 	= "boolean";
	public static final String SQL_LONG 	= "long";
	public static final String SQL_REAL 	= "real";
	public static final String SQL_BLOB 	= "blob";
	
	// Wrapped types
	public static final String SQL_DB_TEXT 			= "DBString";
	public static final String SQL_DB_INTEGER 		= "DBInteger";
	public static final String SQL_DB_BOOLEAN 		= "DBBoolean";
	public static final String SQL_DB_LONG 			= "DBLong";
	public static final String SQL_DB_REAL 			= "DBReal";
	public static final String SQL_DB_BLOB 			= "DBBlob";
	public static final String SQL_DB_DATE 			= "DBDate";
	public static final String SQL_DB_ENUM 			= "DBEnum";
	public static final String SQL_DB_FOREIGN_KEY 	= "DBForeignKey";
	public static final String SQL_DB_PRIMARY_KEY 	= "DBPrimaryKey";
}
