// Copyright (c) ABBYY (BIT Software), 1993 - 2012. All rights reserved.
// Author: Starosvetskiy Artyom

package com.abbyy.sanparks.mobile.ocr4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.abbyy.mobile.ocr4.RecognitionManager.RotationType;
import com.abbyy.mobile.ocr4.layout.MocrPrebuiltLayoutInfo;
import com.abbyy.mobile.ocr4.layout.MocrPrebuiltTextBlockInfo;
import com.abbyy.mobile.ocr4.layout.MocrPrebuiltTextLineInfo;

public class ProcessedImageView extends ImageView {
	
	private MocrPrebuiltLayoutInfo _layoutInfo;

	final Paint _wordPaint;
	final Paint _linePaint;

	// Pre-allocated {@code Matrix} for drawing the image.
	final Matrix _rotatedMatrix;

	public ProcessedImageView( final Context context ) {
		this( context, null, 0 );
	}
	
	public ProcessedImageView( final Context context, final AttributeSet attrs ) {
		this( context, attrs, 0 );
	}
	
	public ProcessedImageView( final Context context, final AttributeSet attrs, final int defStyle ) {
		super( context, attrs, defStyle );

		_wordPaint = new Paint( Paint.ANTI_ALIAS_FLAG );
		_wordPaint.setARGB( 0x3f, 0, 0, 0xff );
		_linePaint = new Paint( Paint.ANTI_ALIAS_FLAG );
		_linePaint.setARGB( 0x0f, 0, 0xff, 0 );
		
		_rotatedMatrix = new Matrix();
	}
	
	public void applyDetectedRotationType() {
		// Not implemented yet
	}

	public void setPrebuiltLayoutInfo( final MocrPrebuiltLayoutInfo layoutInfo ) {
		_layoutInfo = layoutInfo;
		invalidate();
	}

	@Override
	public void onDraw( Canvas canvas ){
		// Draw image
		super.onDraw( canvas );
		
		RotationType rotationType = RecognitionContext.getRotationType();
		
		if( _layoutInfo != null && rotationType == RotationType.NO_ROTATION ) {
			// Not implemented for rotated images yet
			
			// Prepare transformation matrix for the canvas
			@SuppressWarnings("deprecation")
			final Matrix matrix = canvas.getMatrix();
			matrix.preConcat( getImageMatrix() );
			canvas.setMatrix( matrix );
			
			// Draw words rectangles
			for( final MocrPrebuiltTextBlockInfo blockInfo : _layoutInfo.getPrebuiltTextBlocksInfo() ) {
				for( final MocrPrebuiltTextLineInfo lineInfo : blockInfo.getPrebuiltTextLinesInfo() ) {
					for( final Rect wordRect : lineInfo.getWordRects() ) {
						canvas.drawRect( wordRect, _wordPaint );
					}
					canvas.drawRect( lineInfo.getRect(), _linePaint );
				}
			}
		}
	}
	
}
