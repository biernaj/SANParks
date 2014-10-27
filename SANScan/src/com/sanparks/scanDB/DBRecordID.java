package com.sanparks.scanDB;

abstract public class DBRecordID {

	int _id;
	
	public int get () {
		return _id;
	}

	public String getString() {
		return Integer.toString(_id);
	}

	public void set(int ID) {
		_id = ID;
	}

}
