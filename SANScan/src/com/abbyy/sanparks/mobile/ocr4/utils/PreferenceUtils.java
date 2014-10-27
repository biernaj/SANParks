// Copyright (c) ABBYY (BIT Software), 1993 - 2012. All rights reserved.

package com.abbyy.sanparks.mobile.ocr4.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.EnumSet;
import java.util.Set;

import com.abbyy.mobile.ocr4.RecognitionLanguage;

/**
 * Helper class for working with application preferences.
 */
public class PreferenceUtils {

	private static final String RECOGNITION_LANGUAGES_SEPARATOR = ";";

	/**
	 * <p>
	 * Get the boolean flag value from the preferences. 
	 * </p>
	 * 
	 * @param context
	 *            Contexts from which the preferences are loaded.
	 * @param preferenceKey
	 *            Preference key
	 * @param defaultValue
	 *            Default value to return if there is no preference with the key {@code preferenceKey}.
	 * @return {@code boolean} value.
	 */
	public static boolean getBooleanFlag( final Context context, final String preferenceKey, final boolean defaultValue ) 
	{
		return PreferenceManager.getDefaultSharedPreferences( context )
			.getBoolean( preferenceKey, defaultValue );
	}

	/**
	 * <p>
	 * Get recognition language set from the preferences.
	 * </p>
	 * 
	 * @param context
	 *            Contexts from which the preferences are loaded.
	 * @param preferenceKey
	 *            Preference key
	 * @return Recognition language set.
	 */
	public static Set<RecognitionLanguage> getRecognitionLanguages( final Context context, final String preferenceKey ) 
		{
		final String[] languageNames =
				PreferenceManager.getDefaultSharedPreferences( context ).getString( preferenceKey, "" )
						.split( PreferenceUtils.RECOGNITION_LANGUAGES_SEPARATOR );
		
		final Set<RecognitionLanguage> languages = EnumSet.noneOf( RecognitionLanguage.class );

		for( final String languageName : languageNames ) 
		{
			final RecognitionLanguage language = RecognitionLanguage.valueOf( languageName );

			if( language == null ) {
				throw new NullPointerException( "No recognition language \"" + languageName + "\" found." );
			}

			languages.add( language );
		}
		return languages;
	}

	public static void setRecognitionLanguages( final Context context, final String preferenceKey, final Set<RecognitionLanguage> languages ) 
	{
		PreferenceManager
				.getDefaultSharedPreferences( context )
				.edit()
				.putString( preferenceKey,
						StringUtils.join( languages, PreferenceUtils.RECOGNITION_LANGUAGES_SEPARATOR ) );
	}

	private PreferenceUtils() {
		// This class should not be instantiated.
	}
}
