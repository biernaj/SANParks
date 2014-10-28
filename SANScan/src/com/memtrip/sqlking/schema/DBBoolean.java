package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBBoolean implements IColumn {

	private boolean val;
	
	public boolean isVal() {
		return val;
	}

	public void setVal(boolean val) {
		this.val = val;
	}

}
