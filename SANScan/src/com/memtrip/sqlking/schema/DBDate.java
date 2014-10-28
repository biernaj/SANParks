package com.memtrip.sqlking.schema;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.memtrip.sqlking.base.IColumn;

public class DBDate implements IColumn {

	private long val;
	
	public long getVal() {
		return val;
	}

	public String getValString() {
		DateFormat df = DateFormat.getDateInstance();
		
		return df.format(val);
	}

	public void setVal(long newVal) {
		this.val = newVal;
	}

	public void setVal(String newVal) throws ParseException {
		Date newDate = DateFormat.getDateInstance().parse(newVal);
		this.val = newDate.getTime();
	}
	
}
