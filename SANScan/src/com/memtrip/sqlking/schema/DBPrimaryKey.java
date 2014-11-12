package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBPrimaryKey extends DBKey implements IColumn{

	public DBPrimaryKey() { }

	public DBPrimaryKey(long Id) {
		super(Id);
	}

}
