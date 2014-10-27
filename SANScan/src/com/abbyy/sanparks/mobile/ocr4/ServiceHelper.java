// Copyright (c) ABBYY (BIT Software), 1993 - 2011. All rights reserved.
// Author: Rozumyanskiy Michael

package com.abbyy.sanparks.mobile.ocr4;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Class for showing {@link Toast} from non-main thread.
 */
public class ServiceHelper {
	/**
	 * Return result from service to the caller component.
	 * 
	 * @param service
	 *            Service which returns the result.
	 * @param pendingResult
	 *            {@link PendingIntent} object which was created by calling
	 *            {@link Activity#createPendingResult(int, Intent, int)} method.
	 * @param code
	 *            Result code.
	 * @param intent
	 *            {@link Intent} object which contains the return value.
	 *            Can be {@code null}.
	 */
	public static void sendResult( final Service service, final PendingIntent pendingResult, final int code,
			final Intent intent ) {
		if( pendingResult == null ) {
			Log.w( service.getClass().getName(), "Can't send result: pendingResult is null" );
			return;
		}

		try {
			if( intent == null ) {
				pendingResult.send( code );
			} else {
				pendingResult.send( service, code, intent );
			}
		} catch( final CanceledException exception ) {
			Log.w( service.getClass().getName(), "Failed to send result to calling activity", exception );
		} catch( final Exception exception ) {
			// PendingIntent.send() method throws NullPointerException
			// when Activity which is the target for result
			// already exists.
			Log.w( service.getClass().getName(), "Failed to send result to calling activity", exception );
		}
	}

	/**
	 * Show {@link Toast} from service.
	 * 
	 * @param context
	 *            Service context.
	 * @param messageId
	 *            Message identifier.
	 * @param duration
	 *            Duration of message show.
	 * 
	 * @see Toast#makeText(Context, int, int)
	 */
	public static void showToast( final Context context, final int messageId, final int duration ) {
		final Context applicationContext = context.getApplicationContext();
		final Looper mainLooper = Looper.getMainLooper();
		final Handler handler = new Handler( mainLooper );
		final Runnable toastRunnable = new Runnable() {
			@Override
			public void run() {
				Toast.makeText( applicationContext, messageId, duration ).show();
			}
		};

		handler.post( toastRunnable );
	}

	private ServiceHelper() {
		// The class is not served for instantiation.
	}
}
