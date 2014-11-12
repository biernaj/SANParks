/*
 * © 2010 ABBYY. All rights reserved.
 * ABBYY, FineReader, and ABBYY FineReader are either registered trademarks or trademarks of ABBYY Software
 * Ltd.
 */
package com.abbyy.sanparks.mobile.ocr4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.abbyy.mobile.ocr4.License;
import com.abbyy.sanparks.mobile.ocr4.RecognitionContext.RecognitionTarget;
import com.sanparks.sanscan.R;

/**
 * Main application's activity.
 */
public class MainActivity extends ActivityBase {
	/** Logging tag. */
	private static final String TAG = MainActivity.class.getName();

	private static enum ImageSource {
		RESOURCES,
		GALLERY,
		CAMERA
	}

	public static final String KEY_IMAGE_URI = "com.abbyy.sanparks.mobile.ocr4.IMAGE_URI";
	private static final String KEY_IMAGE_SOURCE = "com.abbyy.sanparks.mobile.ocr4.IMAGE_SOURCE";

	private static final int DIALOG_ERROR_LOADING_IMAGE = 0;
	private static final int DIALOG_SELECT_IMAGE_SOURCE = 1;

	private static final int REQUEST_CODE_PICK_IMAGE = 1;

	/** Preview for recognized image. */
	private ImageView _imagePreview;
	/** Button for showing recognized image source dialog. */
	private View _sourceButton;
	/** Button for showing preferences Activity. */
	private View _preferencesButton;
	/** Button for starting text recognition. */
	private View _recognizeTextButton;
	/** Button for starting BCR recognition. */
	private View _recognizeBcrButton;
	/** Button for starting barcode recognition. */
	private View _recognizeBarcodeButton;

	/** Recognized image source. */
	private ImageSource _imageSource = ImageSource.RESOURCES;

	private Uri _imageUri;
	private Bitmap _image;
	private AsyncTask<Void, Void, Bitmap> _imageLoadTask;

	/**
	 * Calls counter for enableButtons/disableButtons methods. Buttons are 
	 * enabled when _disableButtonsCounter == 0.
	 */
	private int _disableButtonsCounter = 0;

	@Override
	public void onCreate( final Bundle savedInstanceState ) {
		Log.v( MainActivity.TAG, "onCreate()" );
		super.onCreate( savedInstanceState );

		if( License.isLoaded() ) {
			if( !initialize( savedInstanceState ) ) {
				Log.w( MainActivity.TAG, "Failed to initialize activity" );
				finish();
				return;
			}

			setContentView( R.layout.main_view );
			setupViews();
		}
	}

	@Override
	public void onDestroy() {
		if( License.isLoaded() ) {
			unloadData();
		}

		Log.v( MainActivity.TAG, "onDestroy()" );
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();

		loadData();

		enableRecognitionButtons();
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		Log.v( MainActivity.TAG, "onRetainNonConfigurationInstance()" );
		return this._image;
	}

	@Override
	protected void onSaveInstanceState( final Bundle outState ) {
		Log.v( MainActivity.TAG, "onSaveInstanceState()" );
		super.onSaveInstanceState( outState );

		outState.putParcelable( MainActivity.KEY_IMAGE_URI, _imageUri );
		outState.putSerializable( MainActivity.KEY_IMAGE_SOURCE, _imageSource );
	}

	@Override
	protected Dialog onCreateDialog( final int dialogId ) {
		switch ( dialogId ) {
			case DIALOG_ERROR_LOADING_IMAGE:
				return new AlertDialog.Builder( this )
						.setCancelable( false )
						.setTitle( getString( R.string.dialog_error ) )
						.setMessage( getString( R.string.error_loading_image ) )
						.setPositiveButton( getString( R.string.button_close ),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick( final DialogInterface dialog, final int id ) {
										dialog.cancel();
									}
								} ).create();
			case DIALOG_SELECT_IMAGE_SOURCE:
				final String[] listItemLabels =
						{
							getString( R.string.radio_from_resources ),
							getString( R.string.radio_from_gallery ), getString( R.string.radio_from_camera )
						};
				return new AlertDialog.Builder( this ).setTitle( getString( R.string.dialog_image_source ) )
						.setSingleChoiceItems( listItemLabels, -1, new DialogInterface.OnClickListener() {
							@Override
							public void onClick( final DialogInterface dialog, final int which ) {
								MainActivity.this._imageSource = ImageSource.values()[which];
								dispatchImageSourceSelected();
								dialog.dismiss();
							}
						} ).create();
			default:
				return super.onCreateDialog( dialogId );
		}
	}

	@Override
	protected void onActivityResult( final int requestCode, final int resultCode, final Intent data ) {
		switch ( requestCode ) {
			case REQUEST_CODE_PICK_IMAGE:
				if( resultCode == Activity.RESULT_OK ) {
					_imageUri = data.getParcelableExtra( MainActivity.KEY_IMAGE_URI );
				}
				break;
			default:
				super.onActivityResult( requestCode, resultCode, data );
		}
	}

	private boolean initialize( final Bundle savedInstanceState ) {
		if( savedInstanceState != null ) {
			_imageUri = savedInstanceState.getParcelable( MainActivity.KEY_IMAGE_URI );
			_image = (Bitmap) getLastNonConfigurationInstance();
			_imageSource = (ImageSource) savedInstanceState.getSerializable( MainActivity.KEY_IMAGE_SOURCE );

			if( _image != null && _imageUri == null ) {
				return false;
			}
		}

		return true;
	}

	private void setupViews() {
		_imagePreview = (ImageView) findViewById( R.id.image_preview );
		if( _imageUri == null ) {
			// On first boot we get image from resources.
			dispatchImageSourceSelected();
		}

		_preferencesButton = findViewById( R.id.button_preferences );
		_preferencesButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				PreferencesActivity.start( MainActivity.this );
			}
		} );

		_sourceButton = findViewById( R.id.button_image_source );
		_sourceButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				showDialog( MainActivity.DIALOG_SELECT_IMAGE_SOURCE );
			}
		} );

		_recognizeTextButton = findViewById( R.id.button_recognize );
		_recognizeTextButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				MainActivity.this.dispatchRecognizeClick( RecognitionTarget.TEXT );
			}
		} );

		_recognizeBcrButton = findViewById( R.id.button_recognize_bcr );
		_recognizeBcrButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				MainActivity.this.dispatchRecognizeClick( RecognitionTarget.BUSINESS_CARD );
			}
		} );

		_recognizeBarcodeButton = findViewById( R.id.button_recognize_barcode );
		_recognizeBarcodeButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				MainActivity.this.dispatchRecognizeClick( RecognitionTarget.BARCODE );
			}
		} );
	}

	void loadData() {
		if( this._imageUri != null && _image == null ) {
			loadImage();
		}
	}

	void unloadData() {
		_imagePreview.setImageBitmap( null );

		RecognitionContext.cancelGetImage();

		if( this._imageLoadTask != null ) {
			this._imageLoadTask.cancel( true );
		}
	}

	void dispatchImageSourceSelected() {
		switch ( _imageSource ) {
			case RESOURCES:
				// We use special URI to load image from resources.
				_imageUri =
						Uri.parse( String.format( "%s://%s/%d", ContentResolver.SCHEME_ANDROID_RESOURCE,
								getPackageName(), R.drawable.business_card ) );
				loadImage();
				break;
			case GALLERY: {
				final Intent intent =
						new Intent( this, PickImageActivity.class ).putExtra(
								PickImageActivity.KEY_FROM_CAMERA, false );
				this.startActivityForResult( intent, MainActivity.REQUEST_CODE_PICK_IMAGE );
				break;
			}
			case CAMERA: {
				final Intent intent =
						new Intent( this, PickImageActivity.class ).putExtra(
								PickImageActivity.KEY_FROM_CAMERA, true );
				this.startActivityForResult( intent, MainActivity.REQUEST_CODE_PICK_IMAGE );
				break;
			}
			default:
				return;
		}
	}

	/**
	 * Handle "Recognize *" buttons click.
	 * 
	 * @param recognitionType
	 *            Chosen recognition method.
	 * 
	 * @see RecognitionContext#RECONIZE_TEXT
	 * @see RecognitionContext#RECONIZE_BCR
	 * @see RecognitionContext#RECONIZE_BARCODE
	 */
	void dispatchRecognizeClick( final RecognitionTarget recognitionTarget ) {
		disableRecognitionButtons();

		RecognitionContext.setRecognitionTarget( recognitionTarget );

		startRecognition();
	}

	/**
	 * Start recognition process for an image with given URI.
	 * 
	 * @param imageUri
	 *            URI of the image to recognize.
	 */
	private void startRecognition() {
		RecognitionActivity.start( this, _imageUri );
	}

	private void loadImage() {
		disableRecognitionButtons();

		_imagePreview.setImageBitmap( null );
		final Uri uri = this._imageUri;
		if( this._imageLoadTask != null ) {
			this._imageLoadTask.cancel( true );
		}

		this._imageLoadTask = new AsyncTask<Void, Void, Bitmap>() {
			@Override
			protected Bitmap doInBackground( final Void... params ) {
				return RecognitionContext.getImage( uri );
			}

			@Override
			protected void onPostExecute( final Bitmap result ) {
				dispatchImageLoaded( result );
			}
		};
		this._imageLoadTask.execute();
	}

	void dispatchImageLoaded( final Bitmap image ) {
		Log.v( MainActivity.TAG, "dispatchImageLoaded()" );

		if( image == null ) {
			showDialog( MainActivity.DIALOG_ERROR_LOADING_IMAGE );
		} else {
			_imagePreview.setImageBitmap( image );
		}

		enableRecognitionButtons();
	}

	/**
	 * Enable "Recognize *" buttons. You should call enabledButtons() for each disableButtons() call to enable
	 * buttons.
	 */
	private void enableRecognitionButtons() {
		_disableButtonsCounter = Math.max( _disableButtonsCounter - 1, 0 );
		if( _disableButtonsCounter == 0 ) {
			setEnabledRecognitionButtons( true );
		}
	}

	/**
	 * Disable "Recognize *" buttons. You should call enabledButtons() for each disableButtons() call to
	 * enable buttons.
	 */
	private void disableRecognitionButtons() {
		if( _disableButtonsCounter == 0 ) {
			setEnabledRecognitionButtons( false );
		}
		++_disableButtonsCounter;
	}

	/**
	 * <p>
	 * For internal usage. Change "enabled" state of "Recognize *" buttons.
	 * </p>
	 * <p>
	 * Use enableRecognitionButtons() and disableRecognitionButtons() instead.
	 * </p>
	 * 
	 * @param enabled
	 *            New "enabled" state.
	 */
	private void setEnabledRecognitionButtons( final boolean enabled ) {
		if( _recognizeTextButton != null ) {
			_recognizeTextButton.setEnabled( enabled );
		}
		if( _recognizeBcrButton != null ) {
			_recognizeBcrButton.setEnabled( enabled );
		}
		if( _recognizeBarcodeButton != null ) {
			_recognizeBarcodeButton.setEnabled( enabled );
		}
	}

}
