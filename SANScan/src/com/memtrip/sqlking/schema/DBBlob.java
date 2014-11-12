package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBBlob implements IColumn {

	byte[] val;
	
	public DBBlob get() {
		return this;
	}

	public byte[] getVal () {
		return val;
	}
		
	public void setVal(byte[] newVal) {
		this.val = newVal;
	}
		
}
