package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBString implements IColumn {

	protected String val;
	
	public String getVal() {
		return this.val;
	}

	public void setVal(String val) {
		this.val = val;
	}

}
