package com.sanparks.sanscan;

import java.io.File;

import com.abbyy.mobile.ocr4.License;
import com.abbyy.sanparks.mobile.ocr4.ActivityBase;
import com.abbyy.sanparks.mobile.ocr4.PickImageActivity;
import com.abbyy.sanparks.mobile.ocr4.RecognitionActivity;
import com.abbyy.sanparks.mobile.ocr4.RecognitionContext;
import com.abbyy.sanparks.mobile.ocr4.RecognitionContext.RecognitionTarget;
import com.sanparks.scanDB.tblXavia;
import com.sanparks.sanscan.util.SystemUiHider;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.support.v4.app.NavUtils;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class WizardEntryActivity extends ActivityBase 
	{
	protected final String TAG = WizardEntryActivity.class.getName();

	protected static enum ImageSource 
		{
		RESOURCES,
		GALLERY,
		CAMERA
		}

	public static final String KEY_IMAGE_URI = "com.abbyy.sanparks.mobile.ocr4.IMAGE_URI";
	private static final String KEY_IMAGE_SOURCE = "com.abbyy.sanparks.mobile.ocr4.IMAGE_SOURCE";
	
	private static final int DIALOG_ERROR_LOADING_IMAGE = 0;
	private static final int DIALOG_SELECT_IMAGE_SOURCE = 1;
	
	protected static final int REQUEST_CODE_PICK_IMAGE 			= 1;
	protected static final int REQUEST_CODE_RECOGNISE_BARCODE 	= 2;
	protected static final int REQUEST_CODE_RECOGNISE_TEXT 		= 3;

	private ImageSource _imageSource = ImageSource.CAMERA;

	protected Uri _imageUri;
	protected Bitmap _image;
	protected AsyncTask<Void, Void, Bitmap> _imageLoadTask;

	/** Preview for recognized image. */
	protected ImageView _imagePreview;
	/** Button for showing recognized image source dialog. */
//	private View _sourceButton;
	
	
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
//	private static final boolean	AUTO_HIDE				= true;
	
	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
//	private static final int		AUTO_HIDE_DELAY_MILLIS	= 1000;
	
	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
//	private static final boolean	TOGGLE_ON_CLICK			= true;
	
	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
//	private static final int		HIDER_FLAGS				= SystemUiHider.FLAG_HIDE_NAVIGATION;
	
	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
//	private SystemUiHider			mSystemUiHider;
	
	public WizardEntryActivity ()
		{
		}

	@Override
	protected void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		
		setupActionBar();

		if( License.isLoaded() ) 
			{
			if( !initialize( savedInstanceState ) ) 
				{
				Log.w( TAG, "Failed to initialize Imaging" );
				finish();
				return;
				}
			}
		
//		_imagePreview = (ImageView) findViewById( R.id.image_ );
//		if( _imageUri == null ) 
//			{
//			// On first boot we get image from resources.
//			_imageUri = ;
//			}


		
		
		
//		final View controlsView = findViewById(R.id.fullscreen_content_controls);
//		final View contentView = findViewById(R.id.activity_entry_wizard_page1);
		
		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
//		mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
//		mSystemUiHider.setup();
//
//		mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener()
//			{
//			// Cached values.
//			int	mControlsHeight;
//			int	mShortAnimTime;
//			
//			@Override
//			@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//			public void onVisibilityChange(boolean visible)
//				{
//				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
//					{
//					// If the ViewPropertyAnimator API is available
//					// (Honeycomb MR2 and later), use it to animate
//					// the in-layout UI controls at the bottom of the
//					// screen.
//					if (mControlsHeight == 0)
//						{
//						mControlsHeight = controlsView.getHeight();
//						}
//					if (mShortAnimTime == 0)
//						{
//						mShortAnimTime = getResources().getInteger(android.R.integer.config_longAnimTime);
//						}
//
//					controlsView
//							.animate()
//							.translationY(visible ? 0 : mControlsHeight)
//							.setDuration(mShortAnimTime);
//					}
//				else
//					{
//					// If the ViewPropertyAnimator APIs aren't
//					// available, simply show or hide the in-layout
//					// UI
//					// controls.
//					controlsView
//							.setVisibility(visible ? View.VISIBLE
//									: View.GONE);
//					}
//				
//				if (visible && AUTO_HIDE)
//					{
//					// Schedule a hide().
//					delayedHide(AUTO_HIDE_DELAY_MILLIS);
//					}
//				}
//			});
//	
//		// Set up the user interaction to manually show or hide the system UI.
//		
//		contentView.setOnClickListener(new View.OnClickListener()
//			{
//			@Override
//			public void onClick(View view)
//				{
//				if (TOGGLE_ON_CLICK)
//					{
//					mSystemUiHider.toggle();
//					}
//				else
//					{
//					mSystemUiHider.show();
//					}
//				}
//			});
//		
//		// Upon interacting with UI controls, delay any scheduled hide()
//		// operations to prevent the jarring behavior of controls going away
//		// while interacting with the UI.
//
//		findViewById(R.id.buttonWizardNext).setOnTouchListener(mDelayHideTouchListener);
		}

	void dispatchImageSourceSelected() {
	switch ( _imageSource ) {
		case RESOURCES:
			// We use special URI to load image from resources.
			_imageUri =
					Uri.parse( String.format( "%s://%s/%d", ContentResolver.SCHEME_ANDROID_RESOURCE,
							getPackageName(), R.drawable.apple_touch_icon ) );
			loadImage();
			break;
		case GALLERY: {
			final Intent intent =
					new Intent( this, PickImageActivity.class )
						.putExtra(PickImageActivity.KEY_FROM_CAMERA, false );
			
			this.startActivityForResult( intent, REQUEST_CODE_PICK_IMAGE );
			break;
		}
		case CAMERA: {
			final Intent intent =
					new Intent( this, PickImageActivity.class )
						.putExtra(PickImageActivity.KEY_FROM_CAMERA, true );
			this.startActivityForResult( intent, REQUEST_CODE_PICK_IMAGE );
			break;
		}
		default:
			return;
	}
}	
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
		{
		super.onPostCreate(savedInstanceState);
		
//		// Trigger the initial hide() shortly after the activity has been
//		// created, to briefly hint to the user that UI controls
//		// are available.
//		delayedHide(100);

		tblXavia.registerXaviaUnit(this);
		}

	@Override
	public void onDestroy() {
		if( License.isLoaded() ) {
			unloadData();
		}

		Log.v(TAG, "onDestroy()" );
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();

		loadData();

		// enableRecognitionButtons();
	}
	
	void loadData() {
	if( this._imageUri != null && _image == null ) {
		loadImage();
	}
}

void unloadData() {

	if (_imagePreview != null)
		_imagePreview.setImageBitmap( null );

	RecognitionContext.cancelGetImage();

	if( this._imageLoadTask != null ) {
		this._imageLoadTask.cancel( true );
	}
}
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		Log.v( TAG, "onRetainNonConfigurationInstance()" );
		return this._image;
	}


	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
		{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			{
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
		{
		int id = item.getItemId();
		if (id == android.R.id.home)
			{
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
			}
		return super.onOptionsItemSelected(item);
		}
	
	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
//	View.OnTouchListener	mDelayHideTouchListener	= new View.OnTouchListener()
//		{
//		@Override
//		public boolean onTouch(View view, MotionEvent motionEvent)
//			{
//			if (AUTO_HIDE)
//				delayedHide(AUTO_HIDE_DELAY_MILLIS);
//
//			return false;
//			}
//		};
//	
//	Handler	 mHideHandler  = new Handler();
//	Runnable mHideRunnable = new Runnable() { @Override public void run() { mSystemUiHider.hide(); }};
//
//	/**
//	 * Schedules a call to hide() in [delay] milliseconds, canceling any
//	 * previously scheduled calls.
//	 */
//	private void delayedHide(int delayMillis)
//		{
//		mHideHandler.removeCallbacks(mHideRunnable);
//		mHideHandler.postDelayed(mHideRunnable, delayMillis);
//		}
//

	@Override
	protected void onSaveInstanceState( final Bundle outState ) {
		Log.v( TAG, "onSaveInstanceState()" );
		super.onSaveInstanceState( outState );

		outState.putParcelable( KEY_IMAGE_URI, _imageUri );
		outState.putSerializable( KEY_IMAGE_SOURCE, _imageSource );
	}

	private boolean initialize( final Bundle savedInstanceState ) 
		{
		if( savedInstanceState != null ) 
			{
			_imageUri = savedInstanceState.getParcelable( KEY_IMAGE_URI );
			_image = (Bitmap) getLastNonConfigurationInstance();
			_imageSource = (ImageSource) savedInstanceState.getSerializable( KEY_IMAGE_SOURCE );
	
			if( _image != null && _imageUri == null ) 
				{
				return false;
				}
			}
			return true;
		}
	
	private void startRecognition(Uri uri) 
		{
		RecognitionActivity.start( this, uri );
		}

	private void takePhoto() 
		{
		// We must specify a destination path for an image.
		final File photo = new File( Environment.getExternalStorageDirectory(), genPhotoFileName() );

		_imageUri = Uri.fromFile( photo );

		final Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE )
			.putExtra( MediaStore.EXTRA_OUTPUT, _imageUri );

		startActivityForResult( intent, PickImageActivity.REQUEST_CODE_TAKE_PHOTO );
		}

	private String genPhotoFileName() 
		{
		return "photo.jpg";
		}
	
	void dispatchRecognizeClick( final RecognitionTarget recognitionTarget ) 
		{
		//	disableRecognitionButtons();
		
		RecognitionContext.setRecognitionTarget( recognitionTarget );
	
		startRecognition();
		}

	/**
	 * Start recognition process for an image with given URI.
	 * 
	 * @param imageUri
	 *            URI of the image to recognize.
	 */
	private void startRecognition() 
		{
		RecognitionActivity.start( this, _imageUri );
		}

	private void loadImage() 
		{
		//  disableRecognitionButtons();
		_imagePreview.setImageBitmap( null );

		final Uri uri = this._imageUri;
		
		if( this._imageLoadTask != null ) 
			{
			this._imageLoadTask.cancel( true );
			}

		this._imageLoadTask = new AsyncTask<Void, Void, Bitmap>() 
			{
			@Override
			protected Bitmap doInBackground( final Void... params ) 
				{
				return RecognitionContext.getImage( uri );
				}

			@Override
			protected void onPostExecute( final Bitmap result ) 
				{
				dispatchImageLoaded( result );
				}
			};

		this._imageLoadTask.execute();
		}

	void dispatchImageLoaded( final Bitmap image ) 
		{
		Log.v( TAG, "dispatchImageLoaded()" );
	
		if( image != null ) 
			{
			_imagePreview.setImageBitmap( image );
			}
	
//		dispatchRecognizeClick (RecognitionTarget.TEXT);
		}
	}
