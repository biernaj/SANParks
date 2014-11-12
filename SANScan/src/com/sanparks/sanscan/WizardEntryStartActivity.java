package com.sanparks.sanscan;

import com.sanparks.sanscan.util.SystemUiHider;
import com.sanparks.scanDB.tblEntry;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class WizardEntryStartActivity extends WizardEntryActivity implements OnItemSelectedListener
	{
	tblEntry Entry = new tblEntry();
	
	protected String 		m_entryType 		= "VEHICLE";
	protected String 		m_entryMode 		= "CAR";
	protected String 		m_visitReason 		= "RESERVATION";
	protected int 			m_visitorCount 		= 2;
	protected boolean 		m_bWeaponsPresent 	= false;
	
	private Spinner 		m_spinnerVehicleCategory;
	private Spinner 		m_spinnerEntryMode;
	private Spinner 		m_spinnerVisitReason;
	private EditText 		m_editTextVisitorCount;
//	private CheckBox		m_cbWeaponsPresent;
	private Button 			m_buttonWizardNext;
	
	protected void onCreate(Bundle savedInstanceState)
		{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wizard_entry);
		
		m_spinnerVehicleCategory= (Spinner) 	findViewById(R.id.spinnerVehicleCategory);
		m_spinnerEntryMode 		= (Spinner) 	findViewById(R.id.spinnerEntryMode);
		m_spinnerVisitReason 	= (Spinner) 	findViewById(R.id.spinnerVisitReason);
		m_editTextVisitorCount 	= (EditText) 	findViewById(R.id.editTextVisitorCount);
//		m_cbWeaponsPresent		= (CheckBox) 	findViewById(R.id.checkBoxWeaponsPresent);
		m_buttonWizardNext 		= (Button) 		findViewById(R.id.buttonWizardNext);
		
//......Setup Controls 

		//....VEHICLE CATEGORY - "Entry By"
			
		ArrayAdapter<CharSequence> adapterVehicleCategory = 
			ArrayAdapter.createFromResource(this,
		        R.array.entryCategory, 
		        android.R.layout.simple_spinner_item);

		adapterVehicleCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		m_spinnerVehicleCategory.setAdapter(adapterVehicleCategory);		
		m_spinnerVehicleCategory.setOnItemSelectedListener(this);
		
		//....ENTRY MODE
		
		ArrayAdapter<CharSequence> adapterEntryMode = 
			ArrayAdapter.createFromResource(this,
		        R.array.entryModeVehicle, 
		        android.R.layout.simple_spinner_item);

		adapterEntryMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		m_spinnerEntryMode.setAdapter(adapterEntryMode);		
		m_spinnerEntryMode.setOnItemSelectedListener(this);

		//....REASON FOR VISIT
		
		ArrayAdapter<CharSequence> adapterVisitReason = 
			ArrayAdapter.createFromResource(this,
		        R.array.visitReason, 
		        android.R.layout.simple_spinner_item);

		adapterVisitReason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		m_spinnerVisitReason.setAdapter(adapterVisitReason);	
		m_spinnerVisitReason.setOnItemSelectedListener(this);
		
		//....VISITOR ENTRY COUNT
		
		m_editTextVisitorCount.setOnEditorActionListener(new TextView.OnEditorActionListener() 
			{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
				{
				if (actionId == EditorInfo.IME_ACTION_DONE)
					{
					String txtVisitorCount = v.getText().toString();
					
					m_visitorCount = Integer.valueOf(txtVisitorCount);
					}
				return true;
				}
			}  );
		
		
		//............... Wizard Button
		
		m_buttonWizardNext.setOnClickListener(new View.OnClickListener()
			{
			@Override
			public void onClick(View v)
				{
				Intent nextIntent = null;
				Context appContext = getApplicationContext();
				
				if (validateFields ())
					{
					switch (m_entryType)
						{
						case "VEHICLE":
							nextIntent = new Intent(appContext, WizardEntryVehicleDetailActivity.class);
							break;
						case "AIRCRAFT":
							nextIntent = new Intent(appContext, WizardEntryAircraftDetailActivity.class);
							break;
						case "OTHER":
							nextIntent = new Intent(appContext, WizardEntryOtherDetailActivity.class);
							break;
						}
					if (nextIntent != null)
						startActivity(nextIntent);
					}
				else 
					{
					Toast msg = Toast.makeText(getApplicationContext(), "Some fields are not valid, please correct them", Toast.LENGTH_SHORT);
					msg.show();
					}
				}
			});  

		}
	
	private boolean validateFields ()
  		{
  		boolean b_validated = true;
  		
  		return b_validated;
  		}
    
	public void onCheckboxClicked(View view) 
		{
		m_bWeaponsPresent = ((CheckBox) view).isChecked();	// there is only one Checkbox on this page...
    
//	    // Check which checkbox was clicked
//	    switch(view.getId()) {
//	        case R.id:
//	            if (checked == true) {}
//	        case R.id.checkBoxLicenceDisc:
//	            if (checked) {}
//	            break;
//	    }
	}
	
  	@Override
  	public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
  		{
  		String item = (String) parent.getItemAtPosition(position);
  		int newEntryModeItemListResource = 0;
  		int parentId = parent.getId();

  		// Repopulate dependent controls after new choices have been made

  		// "ENTRY BY" item was selected
  		
  		if (parentId == m_spinnerVehicleCategory.getId() ) //&& item != m_entryType)
  			{
  			m_entryType = item;				// Save new choice

  			switch(item)
  				{
  				case "VEHICLE":		newEntryModeItemListResource = R.array.entryModeVehicle; 	break;
  				case "AIRCRAFT": 	newEntryModeItemListResource = R.array.entryModeAircraft; 	break;
  				case "OTHER": 		newEntryModeItemListResource = R.array.entryModeOther; 		break;
  				default:			newEntryModeItemListResource = -1;
  				}

  			if (newEntryModeItemListResource > 0)
  				{
  				ArrayAdapter<CharSequence> adapterEntryMode = 
  					ArrayAdapter.createFromResource(this,
						newEntryModeItemListResource, 
						android.R.layout.simple_spinner_item);
  			
  				m_spinnerEntryMode.setAdapter(adapterEntryMode);
  				}
  			}

  		// "ENTRY MODE" item was selected
  		
  		else if (parentId == m_spinnerEntryMode.getId()) // && item != m_entryMode)
  			{
  			m_entryMode = item;				// Save new choice

  			switch (m_entryMode)
	  			{
	  			case "PEDESTRIAN":	// all fall thru
	  				m_visitorCount = 1;
	  			case "BICYCLE":
	  			case "HORSE":
	  			case "CART":
	  			case "OTHER":
	  				m_visitorCount = 1;
	  				m_editTextVisitorCount.setText("1");
	  				break;
	  			}
  			}
  		}

  		
  	@Override
  	public void onNothingSelected(AdapterView<?> parent)
  		{
  		}
	}
