/*
 * © 2010 ABBYY. All rights reserved.
 * ABBYY, FineReader, and ABBYY FineReader are either registered trademarks or trademarks of ABBYY Software
 * Ltd.
 */
package com.abbyy.sanparks.mobile.ocr4;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import java.io.File;

/**
 * An activity for picking image from an image gallery or capturing it using
 * a camera.
 * <p>
 * {@link PickImageActivity#EXTRA_FROM_CAMERA} must be used to specify image source ({@code true} for the
 * camera and {@code false} for the image gallery).
 * </p>
 */
public class PickImageActivity extends NoSearchActivity {
	/** Logging tag. */
	public static final String TAG = "PickImageActivity";

	public static final String KEY_IMAGE_URI = "com.abbyy.sanparks.mobile.ocr4.IMAGE_URI";
	public static final String KEY_FROM_CAMERA = "com.abbyy.sanparks.mobile.ocr4.FROM_CAMERA";

	/** A request code for picking image from a gallery. */
	public static final int REQUEST_CODE_OPEN_PHOTO = 1;
	/** A request code for capturing image using a camera. */
	public static final int REQUEST_CODE_TAKE_PHOTO = 2;

	/** Whether image must be captured using a camera or picked from a gallery. */
	private boolean _isFromCamera = true;
	/** Image URI. */
	private Uri _imageUri;
	/** Has a result already been received. */
	private boolean _isResultReceived = false;

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		Log.v( PickImageActivity.TAG, "onCreate()" );
		super.onCreate( savedInstanceState );

		// Initialize the activity.
		if( !initialize( savedInstanceState ) ) {
			Log.w( PickImageActivity.TAG, "Failed to initialize" );
			finish();
		}

		// Open gallery or camera only if the activity has just been created.
		if( savedInstanceState == null ) {
			if( _isFromCamera ) {
				takePhoto();
			} else {
				openPhotoGallery();
			}
		}
	}

	@Override
	protected void onSaveInstanceState( final Bundle outState ) {
		Log.v( PickImageActivity.TAG, "onSaveInstanceState()" );
		super.onSaveInstanceState( outState );

		outState.putBoolean( PickImageActivity.KEY_FROM_CAMERA, _isFromCamera );
		outState.putParcelable( PickImageActivity.KEY_IMAGE_URI, _imageUri );
	}

	/**
	 * Initialize an activity.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after
	 *            previously being shut down then this Bundle contains the data it
	 *            most recently supplied in onSaveInstanceState.
	 *            <b>Note: Otherwise it is null.</b>
	 * @return {@code true} if activity was initialized successfully, {@code false} otherwise.
	 */
	private boolean initialize( final Bundle savedInstanceState ) {
		if( savedInstanceState == null ) {
			final Intent intent = getIntent();
			if( !intent.hasExtra( PickImageActivity.KEY_FROM_CAMERA ) ) {
				return false;
			}
			_isFromCamera = intent.getBooleanExtra( PickImageActivity.KEY_FROM_CAMERA, false );
		} else {
			_isFromCamera = savedInstanceState.getBoolean( PickImageActivity.KEY_FROM_CAMERA );
			_imageUri = savedInstanceState.getParcelable( PickImageActivity.KEY_IMAGE_URI );
		}
		return true;
	}

	/**
	 * Open an image gallery to pick image from.
	 */
	private void openPhotoGallery() {
		final Intent intent = new Intent( Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI );
		startActivityForResult( intent, PickImageActivity.REQUEST_CODE_OPEN_PHOTO );
	}

	/**
	 * Open a camera application to capture an image.
	 */
	private void takePhoto() {
		// We must specify a destination path for an image.
		final File photo = new File( Environment.getExternalStorageDirectory(), genPhotoFileName() );
		_imageUri = Uri.fromFile( photo );
		final Intent intent =
				new Intent( MediaStore.ACTION_IMAGE_CAPTURE ).putExtra( MediaStore.EXTRA_OUTPUT, _imageUri );
		startActivityForResult( intent, PickImageActivity.REQUEST_CODE_TAKE_PHOTO );
	}

	private String genPhotoFileName() {
		return "photo.jpg";
	}

	@Override
	protected void onActivityResult( final int requestCode, final int resultCode, final Intent data ) {
		Log.v( PickImageActivity.TAG, "onActivityResult()" );

		switch ( requestCode ) {
			case REQUEST_CODE_OPEN_PHOTO:
				// Sometimes the result is returned twice, so we have to check
				// if we have already received the result or not.
				if( resultCode == Activity.RESULT_OK && !_isResultReceived ) {
					_isResultReceived = true;

					_imageUri = data.getData();
					if( _imageUri != null ) {
						RecognitionContext.cleanupImage();

						final Intent intent = getIntent().putExtra( PickImageActivity.KEY_IMAGE_URI, _imageUri );
						setResult( Activity.RESULT_OK, intent );
					} else {
						Log.w( PickImageActivity.TAG, "Received URI is null" );
						// TODO: String to resources
						Toast.makeText( this, "Failed to pick photo.", Toast.LENGTH_LONG ).show();
					}
				}
				finish();
				break;

			case REQUEST_CODE_TAKE_PHOTO:
				// Sometimes the result is returned twice, so we have to check
				// if we have already received the result or not.
				if( resultCode == Activity.RESULT_OK && !_isResultReceived ) {
					_isResultReceived = true;
					RecognitionContext.cleanupImage();

					final Intent intent = getIntent().putExtra( PickImageActivity.KEY_IMAGE_URI, _imageUri );
					setResult( Activity.RESULT_OK, intent );
				}
				finish();
				break;

			default:
				super.onActivityResult( requestCode, resultCode, data );
		}
	}
}
