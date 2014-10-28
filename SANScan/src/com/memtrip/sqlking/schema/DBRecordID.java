package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBRecordID implements IColumn {

	protected int val;
	
	public int getVal() {
		return val;
	}

	public String getValString() {
		return String.valueOf(val);
	}
	public void setVal(int newVal) {
		this.val = newVal;
	}

}
