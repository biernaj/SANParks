package com.sanparks.sanscan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.abbyy.mobile.ocr4.*;
import com.abbyy.sanparks.mobile.ocr4.*;
import com.abbyy.sanparks.mobile.ocr4.utils.PreferenceUtils;

import com.sanparks.scanDB.*;

public class SANscan extends Application {
	
	public ScanDB scanDB = new ScanDB();
	
	// ABBYY OCR vars
	
	public static Context context;
	private static final String TAG = "SANScan Application";

	private static final String _licenseFile = "license";
	private static final String _applicationID = "Android_ID";

	private static final String _patternsFileExtension 		= ".mp3";
	private static final String _dictionariesFileExtension 	= ".mp3";
	private static final String _keywordsFileExtension 		= ".mp3";
	
	public void onCreate() 
		{
		Log.v( SANscan.TAG, "onCreate() - Started" );
        super.onCreate();

        context = getApplicationContext();
		
		scanDB.init(context);

		init_ABBYY_OCR();
		
		Log.v( SANscan.TAG, "onCreate() - Finished" );
		}
		
	public void init_ABBYY_OCR ()
		{
		// Write default settings to the settings store. These values will be written only during the first
		// startup or if the values are rubbed.

		PreferenceManager.setDefaultValues( this, R.xml.preferences, true );
	
		final DataSource 		assetDataSource = new AssetDataSource( this.getAssets() );
		final List<DataSource> 	dataSources 	= new ArrayList<DataSource>();

		dataSources.add( assetDataSource );
	
		Engine.loadNativeLibrary();
		
		try {
			Engine.createInstance( dataSources, new FileLicense( assetDataSource,
					SANscan._licenseFile, 
					SANscan._applicationID ),
					new Engine.DataFilesExtensions( SANscan._patternsFileExtension,
													SANscan._dictionariesFileExtension,
													SANscan._keywordsFileExtension ) );
	
			RecognitionContext.createInstance( this );
	
			filterRecognitionLanguagesPreferences( RecognitionContext.getLanguagesAvailableForOcr(), 
					getString( R.string.key_recognition_languages_ocr ) );
			
			filterRecognitionLanguagesPreferences( RecognitionContext.getLanguagesAvailableForBcr(),
					getString( R.string.key_recognition_languages_bcr ) );
			
			} 
		catch(final IOException e) 					{ Log.v( SANscan.TAG, "ABBYY OCR - IOException during initialisation" ); } 
		catch(final License.BadLicenseException e) 	{ Log.v( SANscan.TAG, "ABBYY OCR - Invalid License" ); }
		}
	
	@Override
	public void onTerminate() {
		Log.v( SANscan.TAG, "onTerminate()" );

		try {
			Engine.destroyInstance();
			RecognitionContext.destroyInstance();
		} catch( final IllegalStateException e ) {
			Log.e( SANscan.TAG, "onTerminate failed", e );
		}
		super.onTerminate();
	}

	public void filterRecognitionLanguagesPreferences( final Set<RecognitionLanguage> availableLanguages, final String preferenceKey ) {
		final Set<RecognitionLanguage> languages = PreferenceUtils.getRecognitionLanguages( this, preferenceKey );

		languages.retainAll( availableLanguages );

		PreferenceUtils.setRecognitionLanguages( this, preferenceKey, languages );
	}
}
