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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.memtrip.sqlking.base.IModel;
import com.memtrip.sqlking.helper.DatabaseHelper;

/**
 * @author	memtrip
 */
public class SQLOpen extends SQLiteOpenHelper {
	private SQLiteDatabase mDatabase;
	private String[] mSchemaArray;
	private String[] mTableNameArray;
	private IModel[] mBaseModelArray;
	
	protected SQLiteDatabase getDatabase() {
	    return mDatabase;
	}
	
	protected SQLOpen(Context context, String name, int version, String[] schemaArray, String[] tableNameArray, IModel[] baseModelArray) {
		super(context, name, null, version);
		mSchemaArray = schemaArray;
		mTableNameArray = tableNameArray;
		mBaseModelArray = baseModelArray;
		mDatabase = getWritableDatabase();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		for (int i = 0; i < mBaseModelArray.length; i++)
			DatabaseHelper.validateBaseModel(mBaseModelArray[i]);
		
		for (String schema : mSchemaArray) {
			db.execSQL(schema);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			destroySchema(db);
			onCreate(db);
		}
	}
	
	/**
	 * Destroy the database schema
	 */
	private void destroySchema(SQLiteDatabase db) {
		for (String tableName : mTableNameArray) {
			db.execSQL("DROP TABLE IF EXISTS " + tableName);
		}
	}
}
