package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBForeignKey extends DBKey implements IColumn {

	public DBForeignKey(String tableName, DBPrimaryKey pk) {
		super(pk);
	}

	public DBForeignKey(DBPrimaryKey pk) {
		super(pk);
	}

}
