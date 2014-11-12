package com.memtrip.sqlking.schema;

public abstract class DBKey extends DBRecordID {

	// Constructors
	
	public DBKey() { }
	
	public DBKey(DBRecordID id) {
		super(id);
	}

	public DBKey(long id) {
		super(id);
	}

	public DBKey(DBKey key) {
		super(key);
	}

	// Getters
	
	public DBKey getKey() {
		return this;
	}
	
	// Setters
	
	public DBKey setKey() {
		return this;
	}
	
}
