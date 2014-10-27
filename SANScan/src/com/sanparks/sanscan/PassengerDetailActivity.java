package com.sanparks.sanscan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
//import main.java.com.google.zxing.integration.android.*;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * An activity representing a single Check detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link CheckListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link CheckDetailFragment}.
 */
public class PassengerDetailActivity extends Activity {

	private static final String PACKAGE = "com.google.zxing.client.android"; 
	private static final String TAG 	= "SANscan"; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_check_detail_passenger);

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

			getFragmentManager().beginTransaction().add(R.id.passenger_detail_container, fragment).commit();
		}
		
	     try {
	            Button scannerDisc = (Button)findViewById(R.id.buttonScanPassengerID);

	            scannerDisc.setOnClickListener(new OnClickListener() 
	            {
	                public void onClick(View v) 
	                {
	                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
	                    intent.putExtra("SCAN_MODE", "BAR_CODE_MODE");
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
			navigateUpTo(new Intent(this, PassengerListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
