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
package com.memtrip.sqlking;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.memtrip.sqlking.base.IModel;
import com.memtrip.sqlking.helper.DatabaseHelper;
import com.memtrip.sqlking.helper.ReflectionHelper;
import com.memtrip.sqlking.helper.StringHelper;

/**
 * A wrapper class that encapsulates all SQLite behavior into a set of generic methods
 * @author	memtrip
 */
public class SQLProvider implements Closeable {
	private SQLiteDatabase mDatabase;
	
	public SQLProvider(SQLiteDatabase database) {
		mDatabase = database;
	}
	
	/**
	 * Insert a baseSQLModel into the table that relates to the baseSQLModel type
	 * @param	baseModel	The object being inserted into the SQLite table
	 * @return	The unique id of the created row
	 */
	public long insert(IModel baseModel) {
		String tableName = baseModel.getClass().getSimpleName();
		return mDatabase.insertOrThrow(tableName, null, DatabaseHelper.getContentValuesFromBaseModel(baseModel));
	}
	
	/**
	 * Insert an array of baseModel into the table that relates to the array database
	 * @param	baseModelArray	The array of objects being inserted into the SQLite table
	 * @return	The unique ids of the created rows
	 */
	public long[] insertArray(IModel[] baseModelArray) {
		if (baseModelArray != null && baseModelArray.length > 0) {
			long[] respnseIds = new long[baseModelArray.length];
			
			for (int i = 0; i < baseModelArray.length; i++) {
				respnseIds[i] = this.insert(baseModelArray[i]);
			}
			
			return respnseIds;
		} else {
			return null;
		}
	}
	
	/**
	 * Updates the row associated with the provided baseModeland the whereClause provided
	 * @param	columnValuePairs	The column values to update
	 * @param	whereClause	The condition of the update
	 * @param 	whereArgs	The condition arguments
	 * @param	classDef	The model that is being updated
	 * @return	The amount of rows that have been updated
	 */
	public int update(ContentValues columnValuePairs, String whereClause, String[] whereArgs, Class<?> classDef) {
		String tableName = classDef.getSimpleName();
		
		return mDatabase.update(
			tableName, 
			columnValuePairs, 
			whereClause, 
			whereArgs
		);
	}
	
	/**
	 * Selects all baseModel elements from the provided class, it requires a baseSQLModel
	 * instance to retrieve the columns that should be populated 
	 * @param	c	The baseSQLModel class that is being selected from the database
	 * @param	order	The order results should be returned (ASC or DESC)
	 * @param	limit	How many results should be returned
	 * @return	An array of BaseModel results
	 */
	public <T> T[] selectAll(Class<T> c, String order, String limit) {
		IModel model = (IModel)ReflectionHelper.newInstance(c);
		String tableName = model.getClass().getSimpleName();
		String[] columns = DatabaseHelper.getSQLColumnNamesFromBaseModel(model);
		
		Cursor cursor = mDatabase.query(tableName, 
			columns, 
			null, 
			null, 
			null, 
			null, 
			order, 
			limit
		);
		
		T[] result = DatabaseHelper.retrieveSQLSelectResults(c, cursor, model);
		return (T[])result;
	}
	
	/**
	 * Selects baseModel elements that match the provided whereClause and conditions
	 * @param	c	The baseSQLModel class that is being selected from the database
	 * @param	whereClause	The where clause that should be applied to the database query
	 * @param	conditions	The conditions of the where clause
	 * @param	order	The order results should be returned (ASC or DESC)
	 * @param	limit	How many results should be returned
	 * @return	An array of BaseModel results
	 */
	public <T> T[] selectByWhereClause(Class<T> c, String whereClause, String[] conditions, String order, String limit) {
		IModel model = (IModel)ReflectionHelper.newInstance(c);
		String tableName = model.getClass().getSimpleName();
		String[] columns = DatabaseHelper.getSQLColumnNamesFromBaseModel(model);
				
		Cursor cursor = mDatabase.query(tableName, 
			columns, 
			whereClause, 
			conditions, 
			null, 
			null, 
			order, 
			limit
		);
		
		T[] result = DatabaseHelper.retrieveSQLSelectResults(c, cursor, model);
		return (T[])result;
	}
	
	/**
	 * Select baseModel elements that match the provided IN clause of column and args
	 * @param	c	The baseSQLModel class that is being selected from the database
	 * @param	baseModel	An instance of the baseSQLModel class that is being selected from the database
	 * @param	column	The column to perform the IN operation on
	 * @param	args	The args of the IN operation
	 * @return	An array of BaseModel results
	 */
	public <T> T[] selectIn(Class<T> c, String column, String[] args) {
		IModel model = (IModel)ReflectionHelper.newInstance(c);
		String tableName = model.getClass().getSimpleName();
		String[] columns = DatabaseHelper.getSQLColumnNamesFromBaseModel(model);
		
		Cursor cursor = mDatabase.query(tableName, 
			columns, 
			column + " IN (" + StringHelper.buildInClausePlaceholders(args.length) + ")", 
			args, 
			null, 
			null, 
			null, 
			null
		);
		
		T[] result = DatabaseHelper.retrieveSQLSelectResults(c, cursor, model);
		return (T[])result;
	}
	
	/**
	 * Selects a results map that matches the raw query provided. The map keys relate to 
	 * the column names that are being selected
	 * @param	query	The query to fetch the results for
	 * @param	args	The arguments for the query
	 * @param	modelsInQuery	The model objects used within the query
	 * @return	A map of the row data values returned
	 */
	public ArrayList<Map<String,Object>> rawSelectQuery(String query, String[] args, Class<?>[] modelsInQuery) {
		Cursor cursor = mDatabase.rawQuery(query, args);
		return DatabaseHelper.retrieveSQLSelectResultMap(cursor,modelsInQuery);
	}
	
	/**
	 * Count the number of rows in the provided table
	 * @param	c	The baseModel class that should have its rows counted
	 * @return	The number of rows in the table
	 */
	public int count(Class<?> c) {
		String tableName = c.getSimpleName();
		Cursor cursor = mDatabase.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
		
		// ensure there is at least one row and one column
		cursor.moveToFirst();
		if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
			return cursor.getInt(0);
		} else {
			return 0;
		}
	}
	
	/**
	 * Count the number of rows associated with the table and condition
	 * @param	tableName	The table to count
	 * @param	condition	The condition of the count
	 * @param	args	The condition arguments
	 * @return	The number of rows returned by the query
	 */
	public int countWhere(Class<?> c, String condition, String[] args) {
		String tableName = c.getSimpleName();
		String whereCondition = null;
		if (condition != null)
			whereCondition = " WHERE " + condition; 
		
		Cursor cursor = mDatabase.rawQuery(
			"SELECT COUNT(*) FROM " + tableName + whereCondition, 
			args
		);
		
		// ensure there is at least one row and one column
		cursor.moveToFirst();
		if (cursor.getCount() > 0 && cursor.getColumnCount() > 0) {
			return cursor.getInt(0);
		} else {
			return 0;
		}
	}

	/**
	 * Truncate all the rows of the provided baseModel class ref
	 * @param	c	The baseModel class that should be truncated
	 */
	public void truncate(Class<?> c) {
		String tableName = c.getSimpleName();
		mDatabase.execSQL("DELETE FROM " + tableName);
	}
	
	/**
	 * Delete rows from the baseModel class ref based on the column and value provided
	 * @param	c	The baseModel class that should perform the delete
	 * @param	column	The where clause column
	 * @param	value	The where clause value
	 * @return	the number of rows affected
	 */
	public int deleteBy(Class<?> c, String column, String value) {
		String tableName = c.getSimpleName();
		return mDatabase.delete(
			tableName, 
			column + " = ?", 
			new String[] {value}
		);
	}
	
	/**
	 * Delete multiple rows from the baseModel class ref based on the column and values provided
	 * @param	c	The baseModel class that should perform the delete
	 * @param	column	The where clause column
	 * @param	args	The where clause args
	 * @return	the number of rows affected
	 */
	public int deleteIn(Class<?> c, String column, String[] args) {
		String tableName = c.getSimpleName();
		return mDatabase.delete(
			tableName, 
			column + " IN (" + StringHelper.buildInClausePlaceholders(args.length) + ")", 
			args
		);
	}
	
	/**
	 * Delete multiple rows from the class ref based on the whereClause and args provided
	 * @param 	c	The baseModel class that should perform the delete
	 * @param	whereClause	The where clause
	 * @param 	args	The where clause args
	 * @return	the number of rows affected
	 */
	public int deleteWhere(Class<?> c, String whereClause, String[] args) {
		String tableName = c.getSimpleName();
		return mDatabase.delete(
			tableName, 
			whereClause, 
			args
		);
	}
	
	/**
	 * Execute a raw select query
	 * @param	query	The SQL query
	 * @param	args	The cursor arguments
	 * @return	A cursor of results
	 */
	public Cursor rawSelectQuery(String query, String[] args) {
		return mDatabase.rawQuery(query, args);
	}
	
	@Override
	public void close() throws IOException {
		mDatabase.close();
	}
}