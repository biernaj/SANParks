package com.memtrip.sqlking.schema;

//import java.util.EnumSet;
//import java.util.List;

import com.memtrip.sqlking.base.IColumn;
import com.memtrip.sqlking.helper.EnumUtils;
import com.memtrip.sqlking.helper.Validate;

public final class DBEnum implements IColumn {

	protected long val;

	public final <E extends Enum<E>> Enum<E> getVal(final Class<E> enumClass) {
		
        EnumUtils.checkBitVectorable(enumClass).getEnumConstants();
//        final EnumSet<E> results = EnumSet.noneOf(EnumUtils.asEnum(enumClass));
        
        Validate.notNull(val);
        
        for (final E constant : enumClass.getEnumConstants()) {
            if ((val & 1 << (constant.ordinal() % Long.SIZE)) != 0) {
                return constant;
            }
        }
        return null;
	}
	
	public final <E extends Enum<E>> void setVal(final Class<E> enumClass, final Enum<E> enumName) {
		val = EnumUtils.generateBitVector(enumClass, enumName);
	}
	
}
