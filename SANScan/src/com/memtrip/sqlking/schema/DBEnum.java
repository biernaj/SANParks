package com.memtrip.sqlking.schema;

import com.memtrip.sqlking.base.IColumn;
import com.memtrip.sqlking.helper.EnumUtils;

public final class DBEnum<E extends Enum<E>> implements IColumn {

	protected long val;

	@SuppressWarnings("hiding")
	public static final <E extends Enum<E>> DBEnum<E> getVal() {
		return EnumUtils.processBitVector(<E extends Enum<E>> Enum<E>, val);
	}
	
	public final <E extends Enum<E>> void setVal(final String enumName) {
		val = EnumUtils.generateBitVector(<E>, enumName);
	}
	
}
