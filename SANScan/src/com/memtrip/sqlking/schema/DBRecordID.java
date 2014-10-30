package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBRecordID implements IColumn {

	protected int _id;
	
	public int getVal() {
		return _id;
	}

	public String getValString() {
		return String.valueOf(_id);
	}
	public void setVal(int newVal) {
		this._id = newVal;
	}

}
