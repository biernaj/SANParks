package com.sanparks.sanscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.abbyy.sanparks.mobile.ocr4.PickImageActivity;

public class WizardEntryAircraftDetailActivity extends WizardEntryActivity
	{
	@Override
	protected void onCreate(Bundle savedInstanceState)
		{
		final Button m_buttonGetImageAndOCR;
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wizard_aircraft_detail);

		m_buttonGetImageAndOCR = (Button) findViewById(R.id.buttonScanAircraftRegistration);

		m_buttonGetImageAndOCR.setOnClickListener(new View.OnClickListener()
			{
			@Override
			public void onClick(View v)
				{
				Intent nextIntent = null;
				
				nextIntent = new Intent(getApplicationContext(), PickImageActivity.class);
						startActivity(nextIntent);
				}
			});  
		
		//	setupActionBar();
	
		//	final View controlsView = findViewById(R.id.fullscreen_content_controls);
		//	final View contentView = findViewById(R.id.activity_entry_wizard_page1);

		
		}
	}
