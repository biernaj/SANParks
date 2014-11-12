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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.memtrip.sqlking.base.IModel;
import com.memtrip.sqlking.schema.*;

/**
 * A SQLite database helper class that handles the creation and updating of 
 * database tables. SQL statements that create tables should be defined here,
 * they should then be executed from the onCreate method. Drop queries should also
 * be provided for the database update.
 * @author	memtrip, 
 * @author  AdrianVelcich
 */
public class DatabaseHelper {
	static String TAG = "DatabaseHelper";
	
    /**
	 * A generic method that takes any class interface that extends BaseModel, it takes the provided
	 * cursor and populates a new instance of the provided BaseModel interface with the results.
	 * @param	c	The class interface to return the cursor results in
	 * @param	cursor	The provided SQLite cursor that the results should be populated from
	 * @param	baseModel	An instance of a class that extends BaseSQLModel, 
	 * 							it is used to get the columnType map associated with the 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] retrieveSQLSelectResults(Class<T> c, Cursor cursor, IModel baseModel) {
		if (c.isAssignableFrom(IModel.class))
			throw new IllegalArgumentException("Only classes that extends BaseSQLModel can be populated with SQLite cursor results");
		
		HashMap<String, Integer> columnTypeMap = getColumnNameTypeMap(baseModel);
		HashMap<String,Method> setMethods = ReflectionHelper.getSetterMethods(c);
		
		T[] result = (T[]) Array.newInstance(c, cursor.getCount());
		// Loop through all the rows in the cursor and execute the appropriate 
		// sqlModel setter method, this will populate the model object.

		cursor.moveToFirst();
		
		for (int i = 0; !cursor.isAfterLast(); i++) {
			T baseSQLModelObject = (T)ReflectionHelper.newInstance(c);
			
			for (int x = 0; x < cursor.getColumnCount(); x++) {
				Method executeMethod = setMethods.get(cursor.getColumnName(x));
				setColumnValue(baseSQLModelObject, columnTypeMap, executeMethod, cursor, x);
			}
			
			result[i] = baseSQLModelObject;
			cursor.moveToNext();
		}
		
		return (T[])result;
	}

	/**
	 * Return a list of cursor results mapped to their column names
	 * @param	cursor	The database cursor 
	 * @param	modelsInQuery	The object models that are used in the query
	 * @return	An array list of result maps
	 */
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<Map<String,T>> retrieveSQLSelectResultMap(Cursor cursor,Class<?>[] modelsInQuery) {	
		// Store the results in a map
		ArrayList<Map<String,T>> results = new ArrayList<Map<String,T>>();
		
		// populate the columnTypeMap with all possible model values
		HashMap<String, Integer> columnTypeMap = new HashMap<String, Integer>();
		
		for (Class<?> c : modelsInQuery) {
			IModel baseModel = (IModel)ReflectionHelper.newInstance(c);
			columnTypeMap.putAll(getColumnNameTypeMap(baseModel));
		}
		
		// loop through the cursor and populate a result map
		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			Map<String,T> resultMap = new HashMap<String,T>();
			
			for (int index = 0; index < cursor.getColumnCount(); index++) {
				String columnName = cursor.getColumnName(index);
				int columnType = columnTypeMap.get(columnName);
				
				switch (columnType) {
					case ORMDataType.FIELD_STRING:
						resultMap.put(columnName, (T)cursor.getString(index));
						break;
					
					case ORMDataType.FIELD_INTEGER:
						resultMap.put(columnName, (T)Integer.valueOf(cursor.getInt(index)));
						break;
						
					case ORMDataType.FIELD_BOOLEAN:
						boolean value = cursor.getInt(index) == 1 ? true : false;
						resultMap.put(columnName, (T)Boolean.valueOf(value));
						break;
					
					case ORMDataType.FIELD_LONG:
						resultMap.put(columnName, (T)Long.valueOf(cursor.getLong(index)));
						break;
					
					case ORMDataType.FIELD_DOUBLE:
						resultMap.put(columnName, (T)Double.valueOf(cursor.getDouble(index)));
						break;

					case ORMDataType.FIELD_BLOB:
						resultMap.put(columnName, (T)cursor.getBlob(index));
						break;
					
					case ORMDataType.FIELD_DATE:
						resultMap.put(columnName, (T)Integer.valueOf(cursor.getInt(index)));
						break;
					
					case ORMDataType.FIELD_ENUM:
						resultMap.put(columnName, (T)Long.valueOf(cursor.getLong(index)));
						break;
					
					case ORMDataType.FIELD_FOREIGN_KEY:
						resultMap.put(columnName, (T)Long.valueOf(cursor.getLong(index)));
						break;
					
					case ORMDataType.FIELD_PRIMARY_KEY:
						resultMap.put(columnName, (T)Long.valueOf(cursor.getLong(index)));
						break;
					
				}
			}
			
			results.add(resultMap);
			cursor.moveToNext();
		}
		
		return results;
	}
	
	/**
	 * Populate a ContentValues collection based on the field values stored in baseModel
	 * @param	baseModel	The baseModel to get the ContentValues from
	 * @return	The ContentValues object populated from the provided baseModel
	 */
	public static ContentValues getContentValuesFromBaseModel(IModel baseModel) {

		Class<?> 		currentClass = baseModel.getClass();
		ContentValues 	contentValues = new ContentValues();
		Field[] 		fields;
		HashMap<String,Method> getMethods = ReflectionHelper.getGetterMethods(baseModel.getClass());
		
//		ArrayList<Column> columns = new ArrayList<Column>();

		while (currentClass != null) {

			fields = currentClass.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {

				String name = fields[i].getName();
				String type = getSQLDataTypeFromClassRef(fields[i].getType());

		//...... Wrapped types
					
				if (type.equals(SQLDataType.SQL_DB_TEXT)) {
					contentValues.put(name, (String)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name)));

				} else if (type.equals(SQLDataType.SQL_DB_BOOLEAN)) {
				boolean value = ((Boolean)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).booleanValue();
				contentValues.put(name, value ? 1 : 0);

				} else if (type.equals(SQLDataType.SQL_DB_DATE)) {
					contentValues.put(name, ((DBDate)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).getVal());
	
				} else if (type.equals(SQLDataType.SQL_DB_ENUM)) {
					contentValues.put(name, ((Enum<?>)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).ordinal());

				} else if (type.equals(SQLDataType.SQL_DB_INTEGER)) {
					contentValues.put(name, ((DBInteger)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).getVal());

				} else if (type.equals(SQLDataType.SQL_DB_LONG)) {
					contentValues.put(name, ((DBLong)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).getVal());

				} else if (type.equals(SQLDataType.SQL_DB_REAL)) {
					contentValues.put(name, ((DBReal)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).getVal());

				} else if (type.equals(SQLDataType.SQL_DB_BLOB)) {
					contentValues.put(name, ((DBBlob)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).getVal());

				} else if (type.equals(SQLDataType.SQL_DB_PRIMARY_KEY)) {
					contentValues.put(name, ((Long)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))));

				} else if (type.equals(SQLDataType.SQL_DB_FOREIGN_KEY)) {
					contentValues.put(name, ((Long)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))));

					//...... Primitive types
					
				} else if (type.equals(SQLDataType.SQL_TEXT)) {
					contentValues.put(name, (String)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name)));
	
				} else if (type.equals(SQLDataType.SQL_INTEGER)) {
					contentValues.put(name, ((Integer)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).intValue());
	
				} else if (type.equals(SQLDataType.SQL_BOOLEAN)) {
					boolean value = ((Boolean)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).booleanValue();
					contentValues.put(name, value ? 1 : 0);
	
				} else if (type.equals(SQLDataType.SQL_LONG)) {
					contentValues.put(name, ((Long)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).longValue());
	
				} else if (type.equals(SQLDataType.SQL_REAL)) {
					contentValues.put(name, ((Double)ReflectionHelper.invokeMethod(baseModel, getMethods.get(name))).doubleValue());
	
				} else if (type.equals(SQLDataType.SQL_BLOB)) {
					contentValues.put(name, (byte[])ReflectionHelper.invokeMethod(baseModel, getMethods.get(name)));

				}
			}
			currentClass = currentClass.getSuperclass();
		}
		
		return contentValues;
	}
	
	/**
	 * Retrieve the column information for the provided BaseModel
	 * @param	currentClass2	The baseModel to retrieve the column information for
	 * @return	An array of column information associated with the provided BaseModel
	 */
	public static Column[] getSQLColumnFromBaseModel(IModel baseModel) 
		{
		Class<?> currentClass = baseModel.getClass();
		Field[] fields;
		ArrayList<Column> columns = new ArrayList<Column>();
		
		while (currentClass != null) 
		{
			fields = currentClass.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				Column column = new Column();
	
				column.setName(fields[i].getName());
				Log.v(TAG, "getSQLColumnFromBaseModel:- '" + fields[i].getName() + "'");
				column.setType(getSQLDataTypeFromClassRef(fields[i].getType()));
	
				columns.add(column);
			}
			currentClass = currentClass.getSuperclass();
		}
		Column columnArray[] = new Column[columns.size()];
		columnArray = columns.toArray(columnArray);

		return columnArray ;
		}
	
	/**
	 * Retrieve the column names for the provided BaseModel
	 * @param	baseModel	The baseModel to retrieve the column name for
	 * @return	An array of column names associated with the provided BaseModel
	 */
	public static String[] getSQLColumnNamesFromBaseModel(IModel baseModel) 
		{
		Class<?> currentClass = baseModel.getClass();
		Field[] fields;
		ArrayList<String> columns = new ArrayList<String>();

		while (currentClass != null) {

			fields = currentClass.getDeclaredFields();
			
			for (int i = 0; i < fields.length; i++) {	
				columns.add(fields[i].getName());
				}

			currentClass = currentClass.getSuperclass();
		}
		
		String columnNames[] = new String[columns.size()];
		columnNames = columns.toArray(columnNames);		

		return columnNames;
		}
	
	/**
	 * Validate the BaseModel to ensure that the properties match the 
	 * get and set methods provided.
	 * @param	baseModel	The model to validate
	 */
	public static void validateBaseModel(IModel baseModel) {
		Field[] fields;
		String[] methodNames = null; // ReflectionHelper.getMethodNamesFromMethods(baseModel.getClass().getDeclaredMethods());
		Class<?> currentClass = baseModel.getClass();
		
		while (currentClass != null) {

			Log.v(TAG, "ValidateBaseModel - " + currentClass.getSimpleName());

			fields = currentClass.getDeclaredFields();
			methodNames = ReflectionHelper.getMethodNamesFromMethods(currentClass.getDeclaredMethods());

			for (int i = 0; i < fields.length; i++) {
				int fieldNameFoundCount = 0;
				String fieldName = fields[i].getName();
				
				for (int x = 0; x < methodNames.length; x++) {
					if (fieldName.equals(methodNames[x])) {
						fieldNameFoundCount++;
						
						if (fieldNameFoundCount == 2)
							break;
					}
				}
				
				if (fieldNameFoundCount != 2)
					throw new IllegalStateException(
						"\"" + fieldName + "\"->[" + baseModel.getClass().getSimpleName() + "] " + 
						"does not have an associated get or set method"
					);
			}
			currentClass = currentClass.getSuperclass();
		}
	}
	
	/**
	 * Determine the data type of the provided class reference and return
	 * the associated SQL data type
	 * @param	clazz	The class reference
	 * @return	The SQL data type to return
	 */
	private static String getSQLDataTypeFromClassRef(Class<?> clazz) {
		String dataType = null;
		
		// Primitive types

		if (clazz.equals(String.class)) {
			dataType = SQLDataType.SQL_TEXT;

		} else if (clazz.equals(Integer.class)) {
			dataType = SQLDataType.SQL_INTEGER;
		
		} else if (clazz.equals(Boolean.class)) {
			dataType = SQLDataType.SQL_BOOLEAN;
		
		} else if (clazz.equals(Long.class)) {
			dataType = SQLDataType.SQL_LONG;
		
		} else if (clazz.equals(Double.class)) {
			dataType = SQLDataType.SQL_REAL;
		
		} else if (clazz.equals(byte[].class)) {
			dataType = SQLDataType.SQL_BLOB;
		
		// Wrapped types
			
		} else if (clazz.equals(DBString.class)) {
			dataType = SQLDataType.SQL_DB_TEXT;

		} else if (clazz.equals(DBInteger.class)) {
			dataType = SQLDataType.SQL_DB_INTEGER;
		
		} else if (clazz.equals(DBBoolean.class)) {
			dataType = SQLDataType.SQL_DB_BOOLEAN;
		
		} else if (clazz.equals(DBLong.class)) {
			dataType = SQLDataType.SQL_DB_LONG;
		
		} else if (clazz.equals(DBReal.class)) {
			dataType = SQLDataType.SQL_DB_REAL;
		
		} else if (clazz.equals(DBBlob.class)) {
			dataType = SQLDataType.SQL_DB_BLOB;

		} else if (clazz.equals(DBDate.class)) {		
			dataType = SQLDataType.SQL_DB_DATE;
		
		} else if (clazz.isEnum()) {
			dataType = SQLDataType.SQL_DB_ENUM;
		
		} else if (clazz.equals(DBForeignKey.class)) {
			dataType = SQLDataType.SQL_DB_FOREIGN_KEY;
		
		} else if (clazz.equals(DBPrimaryKey.class)) {
			dataType = SQLDataType.SQL_DB_PRIMARY_KEY;
		} else {
			// TODO: throw an exception if the datatype is not found		
		}
			
	
		return dataType;
	}
	
	/**
	 * Determine the data type of the provided class reference and return
	 * the associated ORM data type
	 * @param	clazz	The class reference
	 * @return	The ORM data type to return
	 */
	private static int getORMDataTypeFromClassRef(Class<?> clazz) {
		int dataType = -1;
		
			// Primitive Types
		
		if (clazz.equals(String.class)) {
			dataType = ORMDataType.FIELD_STRING;

		} else if (clazz.equals(Integer.class)) {
			dataType = ORMDataType.FIELD_INTEGER;
		
		} else if (clazz.equals(Boolean.class)) {
			dataType = ORMDataType.FIELD_BOOLEAN;
		
		} else if (clazz.equals(Long.class)) {
			dataType = ORMDataType.FIELD_LONG;
		
		} else if (clazz.equals(double.class)) {
			dataType = ORMDataType.FIELD_DOUBLE;

			// Wrapped Types
		
		} else if (clazz.equals(DBString.class)) {
			dataType = ORMDataType.FIELD_STRING;

		} else if (clazz.equals(DBInteger.class)) {
			dataType = ORMDataType.FIELD_INTEGER;
		
		} else if (clazz.equals(DBBoolean.class)) {
			dataType = ORMDataType.FIELD_BOOLEAN;
		
		} else if (clazz.equals(DBLong.class)) {
			dataType = ORMDataType.FIELD_LONG;
		
		} else if (clazz.equals(DBDate.class)) {
			dataType = ORMDataType.FIELD_DATE;
		
		} else if (clazz.equals(DBReal.class)) {
			dataType = ORMDataType.FIELD_DOUBLE;
		
		} else if (clazz.equals(DBBlob.class)) {
			dataType = ORMDataType.FIELD_BLOB;
		
		} else if (clazz.isEnum()) {
			dataType = ORMDataType.FIELD_ENUM;

		} else if (clazz.equals(DBForeignKey.class)) {
			dataType = ORMDataType.FIELD_FOREIGN_KEY;

		} else if (clazz.equals(DBPrimaryKey.class)) {
			dataType = ORMDataType.FIELD_PRIMARY_KEY;
		}
		
		return dataType;
	}
	
	/**
	 * Build a HashMap for column name / type key value pairs
	 * @param	baseModel	The baseModel to get the HashMap from
	 * @return	A HashMap that contains column name / type key value pairs
	 */
	private static HashMap<String,Integer> getColumnNameTypeMap(IModel baseModel) 
		{
		Class<?> currentClass = baseModel.getClass();
		HashMap<String,Integer> columnNameTypeMap = new HashMap<String,Integer>();
		Field[] fields;
		
		while (currentClass != null) 
			{
			fields = currentClass.getDeclaredFields();
			
			for (int i = 0; i < fields.length; i++) 
				{
				columnNameTypeMap.put(fields[i].getName(), getORMDataTypeFromClassRef(fields[i].getType()));
				}
			currentClass = currentClass.getSuperclass(); 	
			}

		return columnNameTypeMap;
		}

	/**
	 * Use reflection to access the setter methods of the baseModel instance, then set
	 * the value of whatever type is associated with the current cursorColumn. The method
	 * cursor.getType(int) is only available in API 11+, so to determine the type, each table column
	 * has a key value pair comprising of columnName/dataType
	 * @param	baseSQLModel	The object to invoke the setter method on
	 * @param	columnTypeMap	columnName/dataType pairs
	 * @param	executeMethod	The setter method that will be invoked on the provided object
	 * @param	cursor	The database cursor to get the value from
	 * @param	index	The cursor index that the desired value is positioned at
	 */
	private static void setColumnValue(Object baseSQLModel, HashMap<String,Integer> columnTypeMap, Method executeMethod, Cursor cursor, int index) {
		int columnType = columnTypeMap.get(cursor.getColumnName(index));
		
		switch (columnType) {
			case ORMDataType.FIELD_STRING:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getString(index));
				break;
			
			case ORMDataType.FIELD_INTEGER:			// Fall thru
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getInt(index));
				break;
			
			case ORMDataType.FIELD_BOOLEAN:
				boolean value = cursor.getInt(index) == 1 ? true : false;
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, value);
				break;
			
			case ORMDataType.FIELD_LONG:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getLong(index));
				break;
			
			case ORMDataType.FIELD_DOUBLE:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getDouble(index));
				break;

			case ORMDataType.FIELD_BLOB:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getBlob(index));
				break;
			
			case ORMDataType.FIELD_DATE:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getLong(index));
				break;

			case ORMDataType.FIELD_ENUM:
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getLong(index));
				break;
			
			case ORMDataType.FIELD_FOREIGN_KEY:		
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getLong(index));
				break;

			case ORMDataType.FIELD_PRIMARY_KEY:		
				ReflectionHelper.invokeMethod(baseSQLModel, executeMethod, cursor.getLong(index));
				break;
		}
	}
}
