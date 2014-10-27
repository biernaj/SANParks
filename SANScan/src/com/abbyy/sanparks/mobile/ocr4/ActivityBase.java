package com.abbyy.sanparks.mobile.ocr4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.abbyy.mobile.ocr4.License;
import com.sanparks.sanscan.R;
public class ActivityBase extends Activity {

	/** It is forbidden to redefine in child Activities dialog with dialogID = DIALOG_BAD_LICENSE */
	private static final int DIALOG_BAD_LICENSE = 100500;

	@Override
	protected void onCreate( final Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );

		if( !License.isLoaded() ) {
			dispatchBadLicense();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Dialog onCreateDialog( final int dialogId ) {
		switch ( dialogId ) {
			case DIALOG_BAD_LICENSE:
				return new AlertDialog.Builder( this )
						.setCancelable( false )
						.setTitle( getString( R.string.dialog_error ) )
						.setMessage( getString( R.string.error_bad_license ) )
						.setPositiveButton( getString( R.string.button_close ),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick( final DialogInterface dialog, final int id ) {
										ActivityBase.this.finish();
									}
								} ).create();
			default:
				return super.onCreateDialog( dialogId );
		}
	}

	@SuppressWarnings("deprecation")
	private void dispatchBadLicense() {
		showDialog( ActivityBase.DIALOG_BAD_LICENSE );
	}

}
