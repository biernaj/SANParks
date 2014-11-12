package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBRecordID implements IColumn {

	protected long _id;

	//.... Constructors
	
	public DBRecordID () {
		_id = 0;
	}
	public DBRecordID (long Id) {
		_id = Id;
	}
	
	public DBRecordID (DBRecordID Id) {
		_id = Id.getVal();
	}
	
	//.... Getters
	
	public DBRecordID get() {
		return this;
	}

	public long getVal() {
		return _id;
	}

	public String getValString() {
		return String.valueOf(_id);
	}

	//.... Setters
	
	public DBRecordID setVal(long newVal) {
		this._id = newVal;
		return this;
	}
	
	public DBRecordID set (DBRecordID id) {
		return setVal(id.getVal());	
	}

}
