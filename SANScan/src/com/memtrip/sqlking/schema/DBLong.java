package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBLong implements IColumn {
	
	protected long val;
	
	public long getVal() {
		return val;
	}

	public void setVal(long newVal) {
		val = newVal;
	}
	
}
