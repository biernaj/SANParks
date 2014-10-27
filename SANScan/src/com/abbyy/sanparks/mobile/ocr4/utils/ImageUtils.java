// Copyright (c) ABBYY (BIT Software), 1993 - 2012. All rights reserved.

package com.abbyy.sanparks.mobile.ocr4.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CancellationException;

/**
 * Helper class for working with images.
 */
public class ImageUtils {

	private ImageUtils() {
		// This class should be instantiated.
	}

	/**
	 * Close {@link Closeable} object.
	 * 
	 * @param closeable
	 *            {@link Closeable} object.
	 */
	public static void closeSilently( final Closeable closable ) {
		if( closable == null ) {
			return;
		}
		try {
			closable.close();
		} catch( final Throwable exception ) {
			// Do nothing.
		}
	}

	public static final class ImageLoader {
		private static final String TAG = "ImageUtils.ImageLoader";

		private final ContentResolver _contentResolver;

		private Uri _imageUri = null;
		private BitmapFactory.Options _options = null;

		public ImageLoader( final Context context ) {
			_contentResolver = context.getContentResolver();
		}

		public Bitmap loadImage( final Uri imageUri ) throws ImageLoaderException, CancellationException {
			Log.v( ImageLoader.TAG, "loadImage(" + imageUri + ")" );

			_imageUri = imageUri;
			_options = new BitmapFactory.Options();

			try {
				determineDecodeOptions();
				return applyExifOrientation( loadImage(), getExifOrientation( imageUri ) );
			} finally {
				_imageUri = null;
				_options = null;
			}
		}
		
		private static int getExifOrientation( final Uri imageUri ) throws ImageLoaderException {
			  ExifInterface exif;
			  int orientation = 1;
			  try {
			    exif = new ExifInterface( imageUri.getPath() );
			    orientation = exif.getAttributeInt( ExifInterface.TAG_ORIENTATION, 1 );
			  } catch ( IOException exception ) {
				  onLoadImageFailed( imageUri, exception );
			  }
			  return orientation;
		}
		
		private static Bitmap applyExifOrientation( final Bitmap srcBitmap, final int exifOrientation ) {
			Matrix matrix = new Matrix();
			switch( exifOrientation ) {
				case 1:
					// Special case when no transformation is needed.
					return srcBitmap;
				case 3:
					matrix.setRotate( 180 );
					break;
				case 6:
					matrix.setRotate( -90 );
					break;
				case 8:
					matrix.setRotate( 90 );
					break;
				case 2:
				case 4:
				case 5:
				case 7:
					// Warning: Not implemented.
					// In this implementation the source bitmap is returned.
					return srcBitmap;
				default:
					// Ignore wrong exifOrientation value.
					return srcBitmap;
			}
			return Bitmap.createBitmap( srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true );
		}

		public void cancelLoadImage() {
			Log.v( ImageLoader.TAG, "cancelLoadImage()" );
			if( _options != null ) {
				_options.requestCancelDecode();
				Log.d( ImageLoader.TAG, "requestCancelDecode() called" );
			}
		}

		private void determineDecodeOptions() throws ImageLoaderException, CancellationException {
			Log.v( ImageLoader.TAG, "determineDecodeOptions()" );

			boolean isDetermineOptionsSucceeded;
			InputStream imageStream = null;
			try {
				imageStream = _contentResolver.openInputStream( _imageUri );
				isDetermineOptionsSucceeded = determineDecodeOptionsFromStream( imageStream );
			} catch( final Throwable exception ) {
				throw new ImageLoaderException( "Failed to determine decode options: " + _imageUri,
						exception );
			} finally {
				closeSilently( imageStream );
			}

			if( !isDetermineOptionsSucceeded ) {
				if( _options.mCancel ) {
					throw new CancellationException( "Loading image cancelled: " + _imageUri );
				}
				throw new ImageLoaderException( "Failed to determine decode options: " + _imageUri );
			}

			Log.d( ImageLoader.TAG, String.format( "Decode options: %dx%d, %s, sample size %d",
					_options.outWidth, _options.outHeight, _options.outMimeType,
					_options.inSampleSize ) );
		}

		private boolean determineDecodeOptionsFromStream( final InputStream imageStream ) {
			// Just determine an image size.
			_options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream( imageStream, null, _options );

			final int imageWidth = Math.max( _options.outWidth, _options.outHeight );
			final int imageHeight = Math.min( _options.outWidth, _options.outHeight );
			if( imageWidth <= 0 || imageHeight <= 0 ) {
				// An error has occurred during the decoding process.
				return false;
			}

			// Now we're ready to load the image.
			_options.inJustDecodeBounds = false;
			_options.inPreferredConfig = Bitmap.Config.RGB_565;
			return true;
		}

		public Bitmap loadImage() throws ImageLoaderException, CancellationException {
			Log.v( ImageLoader.TAG, "loadImage()" );

			InputStream imageStream = null;
			Bitmap image = null;
			try {
				long imageFileSize;
				byte[] imageData;
				try {
					final AssetFileDescriptor assetFileDescriptor =
							_contentResolver.openAssetFileDescriptor( _imageUri, "r" );
					imageFileSize = assetFileDescriptor.getLength();
					if( imageFileSize == AssetFileDescriptor.UNKNOWN_LENGTH ) {
						throw new IOException( "Failed to determine file size." );
					}
					imageStream = assetFileDescriptor.createInputStream();
				} catch( final FileNotFoundException e ) {
					final ParcelFileDescriptor parcelFileDescriptor =
							_contentResolver.openFileDescriptor( _imageUri, "r" );
					imageFileSize = parcelFileDescriptor.getStatSize();
					try {
						if( imageFileSize == -1L ) {
							throw new IOException( "Failed to determine file size." );
						}
						imageStream = _contentResolver.openInputStream( _imageUri );
					} finally {
						parcelFileDescriptor.close();
					}
				}
				imageData = new byte[(int) imageFileSize];
				imageStream.read( imageData );
				image = BitmapFactory.decodeByteArray( imageData, 0, (int) imageFileSize, _options );
			} catch( final FileNotFoundException exception ) {
				onLoadImageFailed( "File <" + _imageUri + "> not found.", exception );
			} catch( final IOException exception ) {
				onLoadImageFailed( _imageUri, exception );
			} finally {
				closeSilently( imageStream );
			}

			if( image == null ) {
				if( _options.mCancel ) {
					onLoadImageCancelled( _imageUri );
				}
				onLoadImageFailed( _imageUri, null );
			}
			Log.d( ImageLoader.TAG, "Image loaded successfully" );
			return image;
		}

		private static void onLoadImageCancelled( final Uri imageUri ) throws CancellationException {
			throw new CancellationException( "Loading image cancelled: " + imageUri );
		}

		private static void onLoadImageFailed( final String detailedMessage, final Throwable cause )
				throws ImageLoaderException {
			throw new ImageLoaderException( detailedMessage, cause );
		}

		private static void onLoadImageFailed( final Uri imageUri, final Throwable cause )
				throws ImageLoaderException {
			throw new ImageLoaderException( "Failed to load image: " + imageUri, cause );
		}
	}

	/**
	 * Exception class for reporting about image loading errors.
	 */
	public static final class ImageLoaderException extends Exception {
		/** serialVersionUID. */
		private static final long serialVersionUID = -8569052327420319850L;

		public ImageLoaderException( final String detailMessage ) {
			super( detailMessage );
		}

		public ImageLoaderException( final String detailMessage, final Throwable throwable ) {
			super( detailMessage, throwable );
		}
	}
}
