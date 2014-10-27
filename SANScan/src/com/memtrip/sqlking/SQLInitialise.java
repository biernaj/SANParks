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
import android.util.Log;

import com.memtrip.sqlking.base.IModel;
import com.memtrip.sqlking.helper.DatabaseHelper;
import com.memtrip.sqlking.helper.ReflectionHelper;
import com.memtrip.sqlking.helper.StringHelper;
import com.memtrip.sqlking.schema.Column;

/**
 * Build the SQL database based on the provided models
 * @author	memtrip
 */
public class SQLInitialise {

	private static final String TAG = "SQLInitialise";
	private SQLiteDatabase mDatabase;
	
	public SQLiteDatabase getDatabase() {
	    return mDatabase;
	}
	
	public SQLInitialise(String name, int version, Context context, IModel[] baseModelArray) {
		Log.v( SQLInitialise.TAG, "SQLInitialise()" );

		String[] schemaArray = new String[baseModelArray.length];
		String[] tableNameArray = new String[baseModelArray.length];
		for (int i = 0; i < baseModelArray.length; i++) {
			IModel baseModel = baseModelArray[i];
			schemaArray[i] = buildSchemaFromBaseModel(baseModel);
			tableNameArray[i] = baseModel.getClass().getSimpleName();
		}
		
		SQLOpen sqlOpen = new SQLOpen(context, name, version, schemaArray, tableNameArray, baseModelArray);
		mDatabase = sqlOpen.getDatabase();
	}
	
	/**
	 * Build a schema from the provided base model
	 * @param	baseModel	The baseModel to build the schema from
	 * @return	A schema string built on the provided baseModel
	 */
	private String buildSchemaFromBaseModel(IModel baseModel) {
		String tableName = ReflectionHelper.getClassName(baseModel);
		Column[] columns = DatabaseHelper.getSQLColumnFromBaseModel(baseModel);
		return StringHelper.buildCreateTableStatement(tableName, columns);
	}
}
