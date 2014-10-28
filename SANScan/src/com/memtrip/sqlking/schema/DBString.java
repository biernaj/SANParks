package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBString implements IColumn {

	private String val;
	
	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
