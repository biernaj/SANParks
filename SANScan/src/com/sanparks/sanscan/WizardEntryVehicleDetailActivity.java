package com.sanparks.sanscan;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
//import android.widget.Toast;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abbyy.mobile.ocr4.License;
//import com.abbyy.sanparks.mobile.ocr4.MainActivity;
import com.abbyy.sanparks.mobile.ocr4.PickImageActivity;
import com.abbyy.sanparks.mobile.ocr4.RecognitionContext.RecognitionTarget;

public class WizardEntryVehicleDetailActivity extends WizardEntryActivity
	{
	private static final int m_currentTypeLicencePlate	= 1;
	private static final int m_currentTypeLicenceDisc	= 2;
	private static final int m_currentTypeDriversLicence= 3;
	private int m_currentType;
	
	private Button m_buttonPhotoAndOCR_LicencePlate 	= null;
	private Button m_buttonPhotoAndBCR_LicenceDisk 		= null;
	private Button m_buttonPhotoAndBCR_DriversLicence 	= null;
	
	private EditText m_editTextRegistration 	= null;
	private EditText m_editTextLicenceDisc 		= null;
	private EditText m_editTextDriverId 		= null;
	
	private CheckBox m_cbRegistrationOk 		= null;	
	private CheckBox m_cbLicenceDiscOk 			= null;		
	private CheckBox m_cbDriverLicenceOk 		= null;	
//	private CheckBox m_cbDriverBiometricsOk 	= null;	

	private String 	m_vehicleRegistration;
	private String 	m_vehicleLicence;
	private String 	m_driverLicenceId;

	private Uri 	_number_plate_imageUri;
	private Uri 	_licence_disc_imageUri;
	private Uri 	_drivers_licence_imageUri;
	private Bitmap 	_number_plate_image;
	private Bitmap 	_licence_disc_image;
	private Bitmap 	_drivers_licence_image;

	private Boolean m_bValidated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wizard_vehicle_detail);

		m_editTextRegistration 	= (EditText) findViewById(R.id.edit_licence_plate);
		m_editTextLicenceDisc 	= (EditText) findViewById(R.id.edit_licence_disc);
		m_editTextDriverId 		= (EditText) findViewById(R.id.editDriver_id_number);

		m_cbRegistrationOk		= (CheckBox) findViewById(R.id.checkbox_licence_plate);
		m_cbLicenceDiscOk		= (CheckBox) findViewById(R.id.checkbox_licence_disc);
		m_cbDriverLicenceOk		= (CheckBox) findViewById(R.id.checkbox_driver_id_number);
//		m_cbDriverBiometricsOk	= (CheckBox) findViewById(R.id.checkBoxWeaponsPresent);

		m_buttonPhotoAndOCR_LicencePlate 	= (Button) findViewById(R.id.ButtonScanLicencePlate);
		m_buttonPhotoAndBCR_LicenceDisk 	= (Button) findViewById(R.id.ButtonScanLicenceDisc);
		m_buttonPhotoAndBCR_DriversLicence 	= (Button) findViewById(R.id.buttonScanDriverLicence);

		_imagePreview = (ImageView) findViewById( R.id.imageLicencePlate );

		
//.....Scanning 

		//.....Licence Plate Scan and OCR
		
		m_buttonPhotoAndOCR_LicencePlate.setOnClickListener(new View.OnClickListener()
			{
			@Override
			public void onClick(View v)
				{
				m_currentType = m_currentTypeLicencePlate;
				
				Intent nextIntent = new Intent(getApplicationContext(), PickImageActivity.class).putExtra(
						PickImageActivity.KEY_FROM_CAMERA, true );
				
				startActivityForResult(nextIntent, REQUEST_CODE_PICK_IMAGE);
				}
			});  
		
		_imagePreview.setOnClickListener(new OnClickListener() 
				{
				@Override
				public void onClick(View v)
					{
					m_currentType = m_currentTypeLicenceDisc;

					dispatchRecognizeClick (RecognitionTarget.TEXT);
					
//					Intent nextIntent = new Intent(getApplicationContext(), PickImageActivity.class).putExtra(
//							PickImageActivity.KEY_FROM_CAMERA, true );
//					
//					startActivityForResult(nextIntent, REQUEST_CODE_PICK_IMAGE);
					}
				});
		
		//.....Licence Disc Scan Barcode
		
		m_buttonPhotoAndBCR_LicenceDisk.setOnClickListener(new View.OnClickListener()
			{
			@Override
			public void onClick(View v)
				{
				m_currentType = m_currentTypeLicenceDisc;

				Intent nextIntent = new Intent(getApplicationContext(), PickImageActivity.class)
					.putExtra(PickImageActivity.KEY_FROM_CAMERA, true );
				
				startActivityForResult(nextIntent, REQUEST_CODE_PICK_IMAGE);
				}
			});  
		
		//.....Driver's Licence Disc Scan Barcode
		
		m_buttonPhotoAndBCR_DriversLicence.setOnClickListener(new View.OnClickListener()
			{
			@Override
			public void onClick(View v)
				{
				m_currentType = m_currentTypeDriversLicence;

				Intent nextIntent = new Intent(getApplicationContext(), PickImageActivity.class)
					.putExtra(PickImageActivity.KEY_FROM_CAMERA, true );

				startActivityForResult(nextIntent, REQUEST_CODE_PICK_IMAGE);
				}
			});  
		
//.....Editing of OCR values 

		m_editTextRegistration.setOnEditorActionListener(new TextView.OnEditorActionListener() 
			{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
				{
				
				m_currentType = m_currentTypeLicencePlate;

				if (actionId == EditorInfo.IME_ACTION_NEXT)
					{
					m_vehicleRegistration = v.getText().toString();
					
					m_cbRegistrationOk.setChecked(m_vehicleRegistration.length() > 1);
					return true;
					}
				return false;
				}
			});
		
		
		m_editTextLicenceDisc.setOnEditorActionListener(new TextView.OnEditorActionListener() 
			{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
				{
				m_currentType = m_currentTypeLicenceDisc;

				if (actionId == EditorInfo.IME_ACTION_NEXT)
					{
					m_vehicleLicence = v.getText().toString();

					m_cbLicenceDiscOk.setChecked(m_vehicleLicence.length() > 1);
					return true;
					}
				return false;
				}
			});
		
		m_editTextDriverId.setOnEditorActionListener(new TextView.OnEditorActionListener() 
			{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
				{
				m_currentType = m_currentTypeDriversLicence;

				if (actionId == EditorInfo.IME_ACTION_DONE)
					{
					m_driverLicenceId = v.getText().toString();
					m_cbDriverLicenceOk.setChecked(m_driverLicenceId.length() > 1);
					return true;
					}
				return false;
				}
			});
		
		//	setupActionBar();
	
		//	final View controlsView = findViewById(R.id.fullscreen_content_controls);
		//	final View contentView = findViewById(R.id.activity_entry_wizard_page1);
		}

	@Override
	protected void onActivityResult( final int requestCode, final int resultCode, final Intent data ) 
		{
		switch ( requestCode ) 
			{
			case REQUEST_CODE_PICK_IMAGE:
				if( resultCode == Activity.RESULT_OK )
					{
					_imageUri = data.getParcelableExtra( KEY_IMAGE_URI );
					
					switch (m_currentType)
						{
						case m_currentTypeLicencePlate:
							dispatchRecognizeClick(RecognitionTarget.TEXT);
							break;
						case m_currentTypeLicenceDisc:
							dispatchRecognizeClick(RecognitionTarget.BARCODE);
							break;
						case m_currentTypeDriversLicence:
							dispatchRecognizeClick(RecognitionTarget.BARCODE);
							break;
						}
					}
				
				break;
				
			case REQUEST_CODE_RECOGNISE_TEXT:
				if( resultCode == Activity.RESULT_OK ) 
					{
					_imageUri = data.getParcelableExtra( KEY_IMAGE_URI );
					}
				break;
				
			case REQUEST_CODE_RECOGNISE_BARCODE:
				if( resultCode == Activity.RESULT_OK ) 
					{
					_imageUri = data.getParcelableExtra( KEY_IMAGE_URI );
					}
				break;
				
			default:
				super.onActivityResult( requestCode, resultCode, data );
			}
		}
	}
