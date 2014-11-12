package com.memtrip.sqlking.schema;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.memtrip.sqlking.base.IColumn;

public class DBDate implements IColumn {

	protected long val;
	
	public DBDate () {
		val = new Date().getTime();
	}
	public DBDate get() {
		return this;
	}
	
	public long getVal() {
		return val;
	}

	public String getValString() {
		DateFormat df = DateFormat.getDateInstance();
		
		return df.format(val);
	}

	public DBDate set(DBDate newVal) {
		this.val = newVal.getVal();
		return this;
	}

	public DBDate setVal(long newVal) {
		this.val = newVal;
		return this;
	}

	public DBDate setVal(String newVal) throws ParseException {
		Date newDate = DateFormat.getDateInstance().parse(newVal);
		this.val = newDate.getTime();
		return this;
	}

	public DBDate setVal(Date newVal) {
		this.val = newVal.getTime();
		return this;
	}
	
}
