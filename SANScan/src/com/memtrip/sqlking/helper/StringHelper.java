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
package com.memtrip.sqlking.helper;

import android.annotation.SuppressLint;

import com.memtrip.sqlking.schema.Column;
import com.memtrip.sqlking.schema.SQLDataType;

/**
 * A helper class that contains string utility methods
 * @author	memtrip
 */
public class StringHelper {
	private static final String EMPTY = "";
	
	/**
	 * Removes the prefix from a method name and converts the result into lower case
	 * @param	methodName	The name of the method
	 * @return	The method name with the prefix removed
	 */
	public static String removePrefixFromMethod(String methodName, String prefix) {
		String newMethodName = methodName.replace(prefix, EMPTY);
		newMethodName = firstCharacterToCase(newMethodName,false);
		return newMethodName;
	}
	
	/**
	 * Converts the first letter of variableName to uppercase and appends 
	 * "get" to it
	 * @param	variableName	The variableName to create the getter for
	 * @return	A getter method name based on the variable name provided
	 */
	public static String buildGetterMethodNameFromVariableName(String variableName) {
		String methodName = "get";
		methodName += firstCharacterToCase(variableName,true);
		return methodName;
	}
	
	/**
	 * Build a list of "?" placeholders seperated by commas(,) based on the 
	 * provided length.
	 * @return	A string of "?" place holders
	 */
	public static String buildInClausePlaceholders(int length) {
		StringBuilder stringBuilder = new StringBuilder();
		
		if (length > 0) {
			for (int i = 0; i < length; i++)
				stringBuilder.append("?,");
		
			stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
		}
		
		return stringBuilder.toString();
	}
	
	/**
	 * Build a create table statement based on the provided tableName and columns
	 * @param	tableName	The name of the table that the statement will create
	 * @param 	columns		The columns of the table being created
	 * @return	A SQL statement that will create a table
	 */
	public static String buildCreateTableStatement(String tableName, Column[] columns) {
		StringBuilder statementBuilder = new StringBuilder();
		
		statementBuilder.append("CREATE TABLE ");
		statementBuilder.append(tableName);
		statementBuilder.append(" (");
		statementBuilder.append(System.getProperty("line.separator"));
		
		for (int i = 0; i < columns.length; i++) {
			Column column = columns[i];
			statementBuilder.append(column.getName());
			statementBuilder.append(" ");
			statementBuilder.append(fixColumnBooleanValue(column.getType()));
			
			// do not display the comma on the last column entry
			if (i != columns.length-1)
				statementBuilder.append(",");
			
			statementBuilder.append(System.getProperty("line.separator"));
		}
		
		statementBuilder.append(");");
		
		return statementBuilder.toString();
	}
	
	/**
	 * Removes the get / set prefix from the methodName and converts
	 * the first character to lowercase
	 * @param	methodName	The methodName to fix
	 * @return	The methodName with the first 3 characters remove and the first character lowercase
	 */
	public static String removeGetOrSetFromMethodName(String methodName) {
		String fixedMethodName = methodName;

		if (fixedMethodName.length() > 3)
			return firstCharacterToCase(methodName.substring(3),false);
		else
			return fixedMethodName;
	}
	
	/**
	 * Boolean data types should be defined as integer in SQL statements. 
	 * This method converts any SQL_BOOLEAN constants into SQL_INTEGER values to fix this edge case.
	 * @param	value	The value to convert into an integer if it is boolean
	 * @return	The value converted to integer if it is boolean
	 */
	private static String fixColumnBooleanValue(String value) {
		if (value.equals(SQLDataType.SQL_BOOLEAN))
			value = SQLDataType.SQL_INTEGER;
		return value;
	}
	
	/**
	 * Converts the first character in a string to either upper or lower case
	 * @param	string	The string to change the first letter case for
	 * @param	upper	Change to upper or lower?
	 * @return	The string with its first character to the desired case
	 */
	@SuppressLint("DefaultLocale")
	private static String firstCharacterToCase(String string, boolean upper) {
		String firstChar = string.substring(0,1);
		string = string.substring(1, string.length());
		
		if (upper) {
			string = firstChar.toUpperCase() + string;
		} else {
			string = firstChar.toLowerCase() + string;
		}
		
		return string;
	}
}
