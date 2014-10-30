package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBReal implements IColumn {

	protected double val;
	
	public double getVal() {
		return val;
	}
	
	public void setVal(double newVal) {
		val = newVal;
	}
}
