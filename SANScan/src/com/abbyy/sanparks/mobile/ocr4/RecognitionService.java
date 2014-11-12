// Copyright (c) ABBYY (BIT Software), 1993 - 2011. All rights reserved.
// Author: Rozumyanskiy Michael

package com.abbyy.sanparks.mobile.ocr4;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.abbyy.mobile.ocr4.Engine;
import com.abbyy.mobile.ocr4.RecognitionConfiguration;
import com.abbyy.mobile.ocr4.RecognitionManager;
import com.abbyy.mobile.ocr4.RecognitionManager.RecognitionCallback;
import com.abbyy.mobile.ocr4.RecognitionManager.RotationType;
import com.abbyy.mobile.ocr4.layout.MocrPrebuiltLayoutInfo;
import com.abbyy.sanparks.mobile.ocr4.RecognitionContext.RecognitionTarget;

/**
 * Image recognition service.
 */
public class RecognitionService extends IntentService implements RecognitionCallback {
	/** Logging tag. */
	private static final String TAG = "RecognitionService";

	public static final String ACTION_STOP_RECOGNITION =
			"com.abbyy.sanparks.mobile.ocr4.action.STOP_RECOGNITION";
	public static final String ACTION_RECOGNITION_PROGRESS =
			"com.abbyy.sanparks.mobile.ocr4.action.RECOGNITION_PROGRESS";
	public static final String ACTION_PREBUILT_WORDS_INFO =
			"com.abbyy.sanparks.mobile.ocr4.action.PREBUILT_WORDS_INFO";
	public static final String ACTION_ROTATION_TYPE_DETECTED =
			"com.abbyy.sanparks.mobile.ocr4.action.ROTATION_TYPE_DETECTED";

	public static final String EXTRA_RECOGNITION_PROGRESS =
			"com.abbyy.sanparks.mobile.ocr4.RECOGNITION_PROGRESS";
	public static final String EXTRA_PREBUILT_WORDS_INFO =
			"com.abbyy.sanparks.mobile.ocr4.EXTRA_PREBUILT_WORDS_INFO";
	public static final String EXTRA_THROWABLE_CLASS = "com.abbyy.sanparks.mobile.ocr4.THROWABLE_CLASS";
	public static final String EXTRA_THROWABLE_PROXY = "com.abbyy.sanparks.mobile.ocr4.THROWABLE_PROXY";
	public static final String EXTRA_RECOGNITION_RESULT = "com.abbyy.sanparks.mobile.ocr4.RECOGNITION_RESULT";
	public static final String EXTRA_RECOGNITION_TARGET = "com.abbyy.sanparks.mobile.ocr4.RECOGNITION_TARGET";

	/** Key which is used to transfer recognized image path to the service. */
	private static final String KEY_IMAGE_URI = "com.abbyy.sanparks.mobile.ocr4.IMAGE_URI";
	/** Key which is used to transfer {@link PendingIntent} to the service. */
	private static final String KEY_PENDING_RESULT = "com.abbyy.sanparks.mobile.ocr4.PENDING_RESULT";

	private BroadcastReceiver _receiver;

	private Uri _imageUri;
	private PendingIntent _pendingResult;

	private final AtomicBoolean _needToStop = new AtomicBoolean( false );
	private boolean _recognitionCanceled;

	private int _recognitionProgressCallbackCounter;
	private int _lastRecognitionCallbackProgress;
	private static final int RECOGNITION_CALLBACK_COUNTER_STEP = 15;
	private static final int RECOGNITION_CALLBACK_PROGRESS_STEP = 10;

	/**
	 * Start {@link RecognitionService} service.
	 *
	 * @param context
	 *            The context from which the start is being carried out.
	 * @param imageUri
	 *            URI of the image which if recognized.
	 * @param pendingResult
	 *            {@link PendingIntent} object wich is used to return the result.
	 */
	public static void start( final Context context, final Uri imageUri, final PendingIntent pendingResult ) {
		final Intent intent =
				new Intent( context, RecognitionService.class ).putExtra( RecognitionService.KEY_IMAGE_URI,
						imageUri ).putExtra( RecognitionService.KEY_PENDING_RESULT, pendingResult );
		context.startService( intent );
	}

	public static void stop( final Context context ) {
		final Intent intent = new Intent( context, RecognitionService.class );
		context.stopService( intent );
	}

	public RecognitionService() {
		super( RecognitionService.TAG );

		setIntentRedelivery( true );
	}

	@Override
	public void onCreate() {
		Log.v( RecognitionService.TAG, "onCreate()" );
		super.onCreate();

		this._receiver = new BroadcastReceiver() {
			@Override
			public void onReceive( final Context context, final Intent intent ) {
				Log.v( RecognitionService.TAG, "onReceive(" + intent + ")" );
				if( RecognitionService.ACTION_STOP_RECOGNITION.equals( intent.getAction() ) ) {
					RecognitionService.this.stopRecognition();
					return;
				}

				Log.w( RecognitionService.TAG, "Unknown intent" );
			}
		};
		registerReceiver( this._receiver, new IntentFilter( RecognitionService.ACTION_STOP_RECOGNITION ) );
	}

	@Override
	public void onDestroy() {
		Log.v( RecognitionService.TAG, "onDestroy()" );
		super.onDestroy();

		if( this._receiver != null ) {
			unregisterReceiver( this._receiver );
		}
	}

	@Override
	public void onHandleIntent( final Intent intent ) {
		Log.v( RecognitionService.TAG, "onHandleIntent(" + intent + ")" );

		// Gain data delivered to the service.
		if( !initialize( intent ) ) {
			Log.w( RecognitionService.TAG, "Failed to initialize" );
			ServiceHelper.sendResult( this, this._pendingResult, Activity.RESULT_CANCELED, null );
			return;
		}

		startRecognition();
	}

	@Override
	public boolean onRecognitionProgress( final int progress, final int warningCode ) {
		if( _needToStop.get() ) {
			_recognitionCanceled = true;
			return true;
		}

		final boolean needToCall =
				_recognitionProgressCallbackCounter % RecognitionService.RECOGNITION_CALLBACK_COUNTER_STEP == 0
						|| warningCode != 0
						|| progress >= 100
						|| progress >= _lastRecognitionCallbackProgress
								+ RecognitionService.RECOGNITION_CALLBACK_PROGRESS_STEP;
		if( needToCall ) {
			final Intent intent =
					new Intent( RecognitionService.ACTION_RECOGNITION_PROGRESS )
							.putExtra( RecognitionService.EXTRA_RECOGNITION_PROGRESS, progress )
							.setPackage( getPackageName() );
			sendBroadcast( intent );
		}
		_lastRecognitionCallbackProgress = progress;
		++_recognitionProgressCallbackCounter;

		return false;
	}

	@Override
	public void onRotationTypeDetected( final RotationType rotationType ) {
		RecognitionContext.setRotationType( rotationType );

		final Intent intent =
				new Intent( RecognitionService.ACTION_ROTATION_TYPE_DETECTED )
						.setPackage( getPackageName() );
		sendBroadcast( intent );
	}

	@Override
	public void onPrebuiltWordsInfoReady( final MocrPrebuiltLayoutInfo layoutInfo ) {
		final Intent intent =
				new Intent( RecognitionService.ACTION_PREBUILT_WORDS_INFO )
						.putExtra( RecognitionService.EXTRA_PREBUILT_WORDS_INFO, layoutInfo )
						.setPackage( getPackageName() );
		sendBroadcast( intent );
	}

	/**
	 * Initialize started service.
	 *
	 * @param intent
	 *            {@link Intent} object delivered to the service.
	 * @return {@code true} if the initialization succeed {@code false} otherwise.
	 */
	private boolean initialize( final Intent intent ) {
		this._imageUri = intent.getParcelableExtra( RecognitionService.KEY_IMAGE_URI );
		this._pendingResult = intent.getParcelableExtra( RecognitionService.KEY_PENDING_RESULT );

		return this._imageUri != null && this._pendingResult != null;
	}

	private void startRecognition() {
		final RecognitionTarget recognitionTarget = RecognitionContext.getRecognitionTarget();

		_needToStop.set( false );
		_recognitionCanceled = false;
		_recognitionProgressCallbackCounter = 0;
		_lastRecognitionCallbackProgress = 0;

		final RecognitionConfiguration recognitionConfiguration = new RecognitionConfiguration();
		if( recognitionTarget == RecognitionTarget.BARCODE ) {
			recognitionConfiguration.setImageResolution( 300 );
		} else {
			recognitionConfiguration.setImageResolution( 0 );
		}

		int imageProcessingOptions = RecognitionConfiguration.ImageProcessingOptions.PROHIBIT_VERTICAL_CJK_TEXT;
		if( RecognitionContext.shouldDetectPageOrientation() ) {
			imageProcessingOptions |= RecognitionConfiguration.ImageProcessingOptions.DETECT_PAGE_ORIENTATION;
		}
		if( RecognitionContext.shouldPrebuildWordsInfo() ) {
			imageProcessingOptions |= RecognitionConfiguration.ImageProcessingOptions.PREBUILD_WORDS_INFO;
		}
		if( RecognitionContext.shouldBuildWordsInfo() ) {
			imageProcessingOptions |= RecognitionConfiguration.ImageProcessingOptions.BUILD_WORDS_INFO;
		}
		recognitionConfiguration.setImageProcessingOptions( imageProcessingOptions );

		recognitionConfiguration.setRecognitionMode( RecognitionContext.getRecognitionMode() );

		recognitionConfiguration.setRecognitionLanguages( RecognitionContext
				.getRecognitionLanguages( recognitionTarget ) );

		final RecognitionManager recognitionManager =
				Engine.getInstance().getRecognitionManager( recognitionConfiguration );

		final Bitmap image = RecognitionContext.getImage( this._imageUri );

		RecognitionContext.setRotationType( RotationType.NO_ROTATION ); // Reset the stored rotation type value

		try {
			Object result = null;
			switch ( recognitionTarget ) {
				case TEXT:
					result = recognitionManager.recognizeText( image, this );
					break;
				case BUSINESS_CARD:
					result = recognitionManager.recognizeBusinessCard( image, this );
					break;
				case BARCODE:
					result = recognitionManager.recognizeBarcode( image, this );
					break;
			}
			if( !_recognitionCanceled ) {
				sendSuccessResult( result );
			}
		} catch( final Throwable exception ) {
			Log.w( RecognitionService.TAG, "Failed to recognize image", exception );
			sendFailResult( exception );
		} finally {
			try {
				recognitionManager.close();
			} catch( final IOException e ) {
			}
		}
	}

	void stopRecognition() {
		_needToStop.set( true );
	}

	private void sendSuccessResult( final Object result ) {
		Intent intent = null;
		if( result != null ) {
			intent = new Intent()
					.putExtra( RecognitionService.EXTRA_RECOGNITION_RESULT, result.toString() );
		}
		ServiceHelper.sendResult( this, this._pendingResult, Activity.RESULT_OK, intent );
	}

	private void sendFailResult( final Throwable throwable ) {
		Intent intent = null;
		if( throwable != null ) {
			// Proxy exception is created because we cannot send user defined exception as intent extra.
			// So if 'throwable' is instance of Exception then it's cause is lost.
			final Exception throwableProxy = new Exception( throwable.getMessage() );
			throwableProxy.setStackTrace( throwable.getStackTrace() );
			intent = new Intent()
					.putExtra( RecognitionService.EXTRA_THROWABLE_CLASS, throwable.getClass().getName() )
					.putExtra( RecognitionService.EXTRA_THROWABLE_PROXY, throwableProxy );
		}
		ServiceHelper.sendResult( this, this._pendingResult, Activity.RESULT_CANCELED, intent );
	}

}
