package com.example.canvastext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MatrixTestView extends View {

	/**
	 * 图片
	 */
	private Bitmap mSrc;

	/**
	 * 控件的宽度
	 */
	private int mWidth;
	/**
	 * 控件的高度
	 */
	private int mHeight;

	public MatrixTestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MatrixTestView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	private void initView() {
		mSrc = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_60pt);
	}

//	/**
//	 * 计算控件的高度和宽度
//	 */
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//		/**
//		 * 设置宽度
//		 */
//		int specMode = MeasureSpec.getMode(widthMeasureSpec);
//		int specSize = MeasureSpec.getSize(widthMeasureSpec);
//
//		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
//		{
//			mWidth = specSize;
//		} else if (specMode == MeasureSpec.AT_MOST)// wrap_content
//		{
//			// 由图片决定的宽
//			int desireByImg = getPaddingLeft() + getPaddingRight()
//					+ mSrc.getWidth();
//			mWidth = Math.min(desireByImg, specSize);
//		}
//
//		/***
//		 * 设置高度
//		 */
//
//		specMode = MeasureSpec.getMode(heightMeasureSpec);
//		specSize = MeasureSpec.getSize(heightMeasureSpec);
//		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
//		{
//			mHeight = specSize;
//		} else if (specMode == MeasureSpec.AT_MOST)// wrap_content
//		{
//			int desire = getPaddingTop() + getPaddingBottom()
//					+ mSrc.getHeight();
//			mHeight = Math.min(desire, specSize);
//		}
//		setMeasuredDimension(mWidth, mHeight);
//
//	}

	@Override
	protected void onDraw(Canvas canvas) {
		Matrix matrix = new Matrix();
//		matrix.setRotate(30,mSrc.getWidth()/2,mSrc.getHeight()/2);
//		matrix.setTranslate(0,200);//控制Matrix进行位移。
//		matrix.setSkew(0.5f,0.5f);//控制Matrix进行倾斜，kx、ky为X、Y方向上的比例。
		matrix.setScale(0.2f,1f,mSrc.getWidth()/2,mSrc.getHeight()/2);//设置Matrix以(px,py)为轴心进行缩放，sx、sy为X、Y方向上的缩放比例。
		matrix.postRotate(30,mSrc.getWidth()/2,mSrc.getHeight()/2);//左乘
		Paint paint = new Paint();
		canvas.drawBitmap(mSrc, matrix, paint);
		super.onDraw(canvas);
	}

}
