package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBInteger implements IColumn {

	protected int val;
	
	public int getVal() {
		return val;
	}
	
	public void setVal(int newVal) {
		val = newVal;
	}
}
