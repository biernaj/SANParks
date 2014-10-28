package com.sanparks.scanDB;

abstract public class DBRecordID {

	protected long id;
	
	public long get () {
		return id;
	}

	public String getString() {
		return Long.toString(id);
	}

	public void set(int id) {
		this.id = id;
	}

}
