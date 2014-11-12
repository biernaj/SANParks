package com.sanparks.sanscan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.abbyy.mobile.ocr4.License;
import android.app.Dialog;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.abbyy.sanparks.mobile.ocr4.*;
import com.abbyy.sanparks.mobile.ocr4.RecognitionContext.RecognitionTarget;

/**
 * An activity representing a single Check detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link CheckListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link CheckDetailFragment}.
 */
public class CheckDetailActivity extends NoSearchActivity {

	private static final String PACKAGE = "com.google.zxing.client.android"; 
//	private static final String TAG 	= "SANscan"; 

	private static final String TAG = CheckDetailActivity.class.getName();

	private static enum ImageSource {
		RESOURCES,
		GALLERY,
		CAMERA
	}

	public static final String KEY_IMAGE_URI = "com.sanparks.sanscan.IMAGE_URI";
	private static final String KEY_IMAGE_SOURCE = "com.sanparks.sanscan.IMAGE_SOURCE";

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
	public static final String KEY_FROM_CAMERA = "com.abbyy.sanparks.mobile.ocr4.FROM_CAMERA";

	/** A request code for picking image from a gallery. */
//	private static final int REQUEST_CODE_OPEN_PHOTO = 1;
	/** A request code for capturing image using a camera. */
	private static final int REQUEST_CODE_TAKE_PHOTO = 2;

	/** Whether image must be captured using a camera or picked from a gallery. */
	private boolean _isFromCamera = false;
	/** Has a result already been received. */
	private boolean _isResultReceived = false;

	/**
	 * Calls counter for enableButtons/disableButtons methods. Buttons are enabled when _disableButtonsCounter
	 * == 0.
	 */
	private int _disableButtonsCounter = 0;

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
			if( !intent.hasExtra( CheckDetailActivity.KEY_FROM_CAMERA ) ) {
				return false;
			}
			_isFromCamera = intent.getBooleanExtra( CheckDetailActivity.KEY_FROM_CAMERA, false );
		} else {
			_isFromCamera = savedInstanceState.getBoolean( CheckDetailActivity.KEY_FROM_CAMERA );
			_imageUri = savedInstanceState.getParcelable( CheckDetailActivity.KEY_IMAGE_URI );
		}
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v( CheckDetailActivity.TAG, "onCreate()" );
		super.onCreate(savedInstanceState);

		if( License.isLoaded() ) {
			if( !initialize( savedInstanceState ) ) {
				Log.w( CheckDetailActivity.TAG, "Failed to initialize activity" );
				finish();
				return;
			}

			setContentView( R.layout.activity_check_detail_vehicle );
			setupViews();
		}

//		setContentView(R.layout.activity_check_detail_vehicle);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) 
		{
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();

			arguments.putString(CheckDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(CheckDetailFragment.ARG_ITEM_ID));

			CheckDetailFragment fragment = new CheckDetailFragment();
			
			fragment.setArguments(arguments);

			getFragmentManager().beginTransaction().add(R.id.check_detail_container, fragment).commit();
		}
		
	     try {
	            Button scannerPlate = (Button)findViewById(R.id.buttonScanVehicleRegistration);

	            scannerPlate.setOnClickListener(new OnClickListener() 
	            {
	                public void onClick(View v) 
	                {
//	                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//	                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
//	                    startActivityForResult(intent, 0);

                	}
	            });
	            
	            Button scannerDisc = (Button)findViewById(R.id.buttonScanVehicleLicenceDisc);

	            scannerDisc.setOnClickListener(new OnClickListener() 
	            {
	                public void onClick(View v) 
	                {
	                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
	                    startActivityForResult(intent, 0);
	                }
	            });
	            
	        } 
		 	catch (ActivityNotFoundException e) {
			      showDownloadDialog(this, "Download ZXING barcode scanner", "", "Yes", "No");
	        }
	   		
	}
	 
	private static AlertDialog showDownloadDialog(final Activity activity,
	                                                CharSequence stringTitle,
	                                                CharSequence stringMessage,
	                                                CharSequence stringButtonYes,
	                                                CharSequence stringButtonNo) {
	    AlertDialog.Builder downloadDialog = new AlertDialog.Builder(activity);
	    downloadDialog.setTitle(stringTitle);
	    downloadDialog.setMessage(stringMessage);
	    downloadDialog.setPositiveButton(stringButtonYes, new DialogInterface.OnClickListener() 
	    {
	      public void onClick(DialogInterface dialogInterface, int i) {
	        Uri uri = Uri.parse("market://search?q=pname:" + PACKAGE);
	        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	        try {
	          activity.startActivity(intent);
	        } catch (ActivityNotFoundException anfe) {
	          // Hmm, market is not installed
	          Log.w(TAG, "Android Market is not installed; cannot install Barcode Scanner");
	        }
	      }
	    });
	    
	    downloadDialog.setNegativeButton(stringButtonNo, new DialogInterface.OnClickListener() {
	      public void onClick(DialogInterface dialogInterface, int i) {}
	    });
	    return downloadDialog.show();
	  }	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                // Handle successful scan
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format , Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
                Toast toast = Toast.makeText(this, "Scan was Cancelled!", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
            }
        }
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			navigateUpTo(new Intent(this, CheckListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onDestroy() {
		if( License.isLoaded() ) {
			unloadData();
		}

		Log.v( CheckDetailActivity.TAG, "onDestroy()" );
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
		Log.v( CheckDetailActivity.TAG, "onRetainNonConfigurationInstance()" );
		return this._image;
	}

	@Override
	protected void onSaveInstanceState( final Bundle outState ) {
		Log.v( CheckDetailActivity.TAG, "onSaveInstanceState()" );
		super.onSaveInstanceState( outState );

		outState.putParcelable( CheckDetailActivity.KEY_IMAGE_URI, _imageUri );
		outState.putSerializable( CheckDetailActivity.KEY_IMAGE_SOURCE, _imageSource );
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
								CheckDetailActivity.this._imageSource = ImageSource.values()[which];
								dispatchImageSourceSelected();
								dialog.dismiss();
							}
						} ).create();
			default:
				return super.onCreateDialog( dialogId );
		}
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
				PreferencesActivity.start( CheckDetailActivity.this );
			}
		} );

		_sourceButton = findViewById( R.id.button_image_source );
		_sourceButton.setOnClickListener( new View.OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick( final View view ) {
				// TODO: update this
				showDialog( CheckDetailActivity.DIALOG_SELECT_IMAGE_SOURCE );
			}
		} );

		_recognizeTextButton = findViewById( R.id.button_recognize );
		_recognizeTextButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				CheckDetailActivity.this.dispatchRecognizeClick( RecognitionTarget.TEXT );
			}
		} );

		_recognizeBcrButton = findViewById( R.id.button_recognize_bcr );
		_recognizeBcrButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				CheckDetailActivity.this.dispatchRecognizeClick( RecognitionTarget.BUSINESS_CARD );
			}
		} );

		_recognizeBarcodeButton = findViewById( R.id.button_recognize_barcode );
		_recognizeBarcodeButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick( final View view ) {
				CheckDetailActivity.this.dispatchRecognizeClick( RecognitionTarget.BARCODE );
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
				this.startActivityForResult( intent, CheckDetailActivity.REQUEST_CODE_PICK_IMAGE );
				break;
			}
			case CAMERA: {
				final Intent intent =
						new Intent( this, PickImageActivity.class ).putExtra(
								PickImageActivity.KEY_FROM_CAMERA, true );
				this.startActivityForResult( intent, CheckDetailActivity.REQUEST_CODE_PICK_IMAGE );
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

	@SuppressWarnings("deprecation")
	void dispatchImageLoaded( final Bitmap image ) {
		Log.v( CheckDetailActivity.TAG, "dispatchImageLoaded()" );

		if( image == null ) {
			//TODO: update this
			
			showDialog( CheckDetailActivity.DIALOG_ERROR_LOADING_IMAGE );
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
