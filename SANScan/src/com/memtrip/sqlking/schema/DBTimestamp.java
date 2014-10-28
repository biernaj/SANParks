package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;

public class DBTimestamp implements IColumn {

	private long timestamp;
	
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
