package com.abbyy.sanparks.mobile.ocr4.utils;

import java.util.Iterator;

public class StringUtils {

	public static String join( final Iterable<? extends Object> collection, final String separator ) {
		Iterator<? extends Object> iterator;
		if( collection == null || ( !( iterator = collection.iterator() ).hasNext() ) ) {
			return "";
		}
		final StringBuilder oBuilder = new StringBuilder( String.valueOf( iterator.next() ) );
		while( iterator.hasNext() ) {
			oBuilder.append( separator ).append( iterator.next() );
		}
		return oBuilder.toString();
	}

}
