// Copyright (c) ABBYY (BIT Software), 1993 - 2010. All rights reserved.
// Author: Rozumyanskiy Michael

package com.abbyy.sanparks.mobile.ocr4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;

import com.abbyy.mobile.ocr4.layout.MocrPrebuiltLayoutInfo;
import com.sanparks.sanscan.R;

public final class RecognitionActivity extends NoSearchActivity {
	private static final String TAG = "RecognitionActivity";

	private static final String KEY_IMAGE_URI = "com.abbyy.mobile.ocr4.IMAGE_URI";

	private static final String KEY_IS_RECOGNIZING = "com.abbyy.mobile.ocr4.IS_RECOGNIZING";
	private static final String KEY_RECOGNITION_PROGRESS = "com.abbyy.mobile.ocr4.RECOGNITION_PROGRESS";

	private static final int DIALOG_ERROR_LOADING_IMAGE = 0;

	private static final int REQUEST_CODE_RECOGNITION_FINISHED = 0;

	private Uri 				_imageUri;
	private Bitmap 				_image;

	private ProgressBar 		_progressBar;
	private ProcessedImageView 	_imagePreview;
	private View 				_cancelButton;

	private boolean 			_isRecognizing;
	private int 				_recognitionProgress;

	private AsyncTask<Void, Void, Bitmap> _imageLoadTask;
	private BroadcastReceiver 	_progressReceiver;
	private BroadcastReceiver 	_prebuiltWordsInfoReceiver;
	private BroadcastReceiver 	_rotationTypeDetectionReceiver;

	public static void start( final Context context, final Uri imageUri ) 
	{
		final Intent intent =
				new Intent( context, RecognitionActivity.class )
						.putExtra( RecognitionActivity.KEY_IMAGE_URI, imageUri );

		context.startActivity( intent );
	}

	@Override
	protected void onCreate( final Bundle savedInstanceState ) 
	{
		Log.v( RecognitionActivity.TAG, "onCreate()" );
		super.onCreate( savedInstanceState );

		if( !initialize( savedInstanceState ) ) {
			Log.w( RecognitionActivity.TAG, "Failed to initialize activity" );
			finish();
			return;
		}

		setContentView( R.layout.recognition_view );
		setupViews();

		if( _image == null ) {
			loadImage();
		} else {
			startRecognition();
		}
	}

	@Override
	protected void onStart() {
		Log.v( RecognitionActivity.TAG, "onStart()" );
		super.onStart();

		_progressReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive( final Context context, final Intent intent ) {
				final int progress = intent.getIntExtra( RecognitionService.EXTRA_RECOGNITION_PROGRESS, 0 );
				dispatchRecognitionProgress( progress );
			}
		};
		
		_prebuiltWordsInfoReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive( final Context context, final Intent intent ) {
				final MocrPrebuiltLayoutInfo layoutInfo = (MocrPrebuiltLayoutInfo) intent.getSerializableExtra( RecognitionService.EXTRA_PREBUILT_WORDS_INFO );
				_imagePreview.setPrebuiltLayoutInfo( layoutInfo );
			}
		};
		
		_rotationTypeDetectionReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive( final Context context, final Intent intent ) {
				_imagePreview.applyDetectedRotationType();
			}
		};
		
		registerReceiver( _progressReceiver,
				new IntentFilter( RecognitionService.ACTION_RECOGNITION_PROGRESS ) );
		
		registerReceiver( _prebuiltWordsInfoReceiver,
				new IntentFilter( RecognitionService.ACTION_PREBUILT_WORDS_INFO ) );
		
		registerReceiver( _rotationTypeDetectionReceiver,
				new IntentFilter( RecognitionService.ACTION_ROTATION_TYPE_DETECTED ) );
	}

	@Override
	protected void onStop() {
		Log.v( RecognitionActivity.TAG, "onStop()" );
		super.onStop();

		if( _progressReceiver != null ) {
			unregisterReceiver( _progressReceiver );
		}
		if( _prebuiltWordsInfoReceiver != null ) {
			unregisterReceiver( _prebuiltWordsInfoReceiver );
		}
		if( _rotationTypeDetectionReceiver != null ) {
			unregisterReceiver( _rotationTypeDetectionReceiver );
		}
	}

	@Override
	protected void onDestroy() {
		Log.v( RecognitionActivity.TAG, "onDestroy()" );
		super.onDestroy();

		RecognitionContext.cancelGetImage();
		if( _imageLoadTask != null ) {
			_imageLoadTask.cancel( true );
		}

		if( isFinishing() ) {
			stopRecognition();
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		Log.v( RecognitionActivity.TAG, "onRetainNonConfigurationInstance()" );
		return _image;
	}

	@Override
	protected void onSaveInstanceState( final Bundle outState ) {
		Log.v( RecognitionActivity.TAG, "onSaveInstanceState()" );
		super.onSaveInstanceState( outState );

		outState.putParcelable( RecognitionActivity.KEY_IMAGE_URI, _imageUri );
		outState.putBoolean( RecognitionActivity.KEY_IS_RECOGNIZING, _isRecognizing );
		outState.putInt( RecognitionActivity.KEY_RECOGNITION_PROGRESS, _recognitionProgress );
	}

	@Override
	protected Dialog onCreateDialog( final int dialogId ) {
		switch ( dialogId ) {
			case DIALOG_ERROR_LOADING_IMAGE:
				return new AlertDialog.Builder( this ).setTitle( getString( R.string.dialog_error ) )
						.setMessage( getString( R.string.error_loading_image ) )
						.setPositiveButton( "Close", new DialogInterface.OnClickListener() {
							@Override
							public void onClick( final DialogInterface dialog, final int id ) {
								dialog.cancel();
							}
						} )
						.setOnCancelListener( new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel( final DialogInterface dialog ) {
								RecognitionActivity.this.finish();
							}
						} )
						.create();
			default:
				return super.onCreateDialog( dialogId );
		}
	}

	@Override
	protected void onActivityResult( final int requestCode, final int resultCode, final Intent data ) {
		switch ( requestCode ) {
			case REQUEST_CODE_RECOGNITION_FINISHED:
				RecognitionService.stop( getApplicationContext() );
				if( resultCode == Activity.RESULT_OK ) {
					final String result = data.getStringExtra( RecognitionService.EXTRA_RECOGNITION_RESULT );
					dispatchRecognitionSucceeded( result );
				} else {
					if( data.hasExtra( RecognitionService.EXTRA_THROWABLE_PROXY ) ) {
						final String throwableClassName =
								data.getStringExtra( RecognitionService.EXTRA_THROWABLE_CLASS );
						final Exception throwableProxy =
								(Exception) data
										.getSerializableExtra( RecognitionService.EXTRA_THROWABLE_PROXY );
						Throwable t = null;
						try {
							t = (Throwable) Class.forName( throwableClassName )
									.getConstructor( String.class )
									.newInstance( throwableProxy.getMessage() );
							t.setStackTrace( throwableProxy.getStackTrace() );
						} catch( final ClassNotFoundException e ) {
						} catch( final NoSuchMethodException e ) {
						} catch( final InvocationTargetException e ) {
						} catch( final IllegalAccessException e ) {
						} catch( final InstantiationException e ) {
						}
						if( t == null ) {
							t = throwableProxy;
						}
						dispatchRecognitionFailed( t );
					} else {
						dispatchRecognitionCancelled();
					}
				}
				break;

			default:
				super.onActivityResult( requestCode, resultCode, data );
		}
	}

	@SuppressWarnings("deprecation")
	private boolean initialize( final Bundle savedInstanceState ) {
		if( savedInstanceState == null ) {
			// Activity is created.
			final Intent intent = getIntent();
			_imageUri = intent.getParcelableExtra( RecognitionActivity.KEY_IMAGE_URI );
			_isRecognizing = false;
			_recognitionProgress = 0;
		} else {
			// Activity is re-created after configuration changes.
			_imageUri = savedInstanceState.getParcelable( RecognitionActivity.KEY_IMAGE_URI );
			_image = (Bitmap) getLastNonConfigurationInstance();
			_isRecognizing = savedInstanceState.getBoolean( RecognitionActivity.KEY_IS_RECOGNIZING );
			_recognitionProgress =
					savedInstanceState.getInt( RecognitionActivity.KEY_RECOGNITION_PROGRESS, 0 );
		}

		return _imageUri != null;
	}

	private void setupViews() {
		_progressBar = (ProgressBar) findViewById( R.id.progress );

		dispatchRecognitionProgress( _recognitionProgress );

		_imagePreview = (ProcessedImageView) findViewById( R.id.image_preview );
		_imagePreview.setImageBitmap( _image );

		final View.OnClickListener cancelListener = new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				Log.v( RecognitionActivity.TAG, "Stop button clicked" );
				RecognitionActivity.this.dispatchCancelClick();
			}
		};

		_cancelButton = findViewById( R.id.button_cancel );
		_cancelButton.setOnClickListener( cancelListener );
	}

	private void loadImage() {
		final Uri uri = _imageUri;
		if( _imageLoadTask != null ) {
			_imageLoadTask.cancel( true );
		}

		_imageLoadTask = new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground( final Void... params ) {
				return RecognitionContext.getImage( uri );
			}

			@Override
			protected void onPostExecute( final Bitmap result ) {
				dispatchImageLoaded( result );
			}
		};
		_imageLoadTask.execute();
	}

	@SuppressWarnings("deprecation")
	void dispatchImageLoaded( final Bitmap image ) {
		Log.v( RecognitionActivity.TAG, "dispatchImageLoaded()" );

		if( image == null ) {
			showDialog( RecognitionActivity.DIALOG_ERROR_LOADING_IMAGE );
		} else {
			_image = image;
			_imagePreview.setImageBitmap( image );
			startRecognition();
		}
	}

	private void startRecognition() 
	{
		if( !_isRecognizing ) {
			final Intent resultIntent = new Intent().putExtra( RecognitionActivity.KEY_IMAGE_URI, _imageUri );

			final PendingIntent pendingResult =
					createPendingResult( RecognitionActivity.REQUEST_CODE_RECOGNITION_FINISHED, resultIntent,
							PendingIntent.FLAG_ONE_SHOT );

			RecognitionService.start( getApplicationContext(), _imageUri, pendingResult );

			_isRecognizing = true;
		}
		Log.v( RecognitionActivity.TAG, "startRecognition() - " + String.valueOf(_isRecognizing));
	}

	private void stopRecognition() 
	{
		final Intent intent = 
				new Intent( RecognitionService.ACTION_STOP_RECOGNITION ).setPackage( getPackageName() );
		sendBroadcast( intent );
		RecognitionService.stop( getApplicationContext() );
	}

	void dispatchCancelClick() {
		finish();
	}

	private void dispatchRecognitionProgress( final int progress ) {
		_recognitionProgress = progress;
		Log.v(RecognitionActivity.TAG, "Progress" + String.valueOf(progress));
		_progressBar.setProgress( progress );
	}

	private void dispatchRecognitionSucceeded( final Object result ) {
		Log.v( RecognitionActivity.TAG, "dispatchRecognitionSucceeded()" );

		if( result != null ) {
			ProcessResultsActivity.start( this, result );
		} else {
			Toast.makeText( this, "No result.", Toast.LENGTH_LONG ).show();
		}

		finish();
	}

	private void dispatchRecognitionCancelled() {
		Log.v( RecognitionActivity.TAG, "dispatchRecognitionCancelled()" );
		finish();
	}

	void dispatchRecognitionFailed( final Throwable throwable ) {
		if( throwable == null ) {
			throw new NullPointerException( "dispatchRecognitionFailed() argument is null" );
		}
		Log.v( RecognitionActivity.TAG, "dispatchRecognitionFailed()", throwable );

		// TODO: Show error message in a dialog.
		String message = throwable.getMessage();
		if( message == null ) {
			message = throwable.toString();
		}
		Toast.makeText( this, message, Toast.LENGTH_LONG ).show();
		finish();
	}

}
