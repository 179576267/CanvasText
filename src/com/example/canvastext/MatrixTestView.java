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
	 * ͼƬ
	 */
	private Bitmap mSrc;

	/**
	 * �ؼ��Ŀ��
	 */
	private int mWidth;
	/**
	 * �ؼ��ĸ߶�
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
//	 * ����ؼ��ĸ߶ȺͿ��
//	 */
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//		/**
//		 * ���ÿ��
//		 */
//		int specMode = MeasureSpec.getMode(widthMeasureSpec);
//		int specSize = MeasureSpec.getSize(widthMeasureSpec);
//
//		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
//		{
//			mWidth = specSize;
//		} else if (specMode == MeasureSpec.AT_MOST)// wrap_content
//		{
//			// ��ͼƬ�����Ŀ�
//			int desireByImg = getPaddingLeft() + getPaddingRight()
//					+ mSrc.getWidth();
//			mWidth = Math.min(desireByImg, specSize);
//		}
//
//		/***
//		 * ���ø߶�
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
//		matrix.setTranslate(0,200);//����Matrix����λ�ơ�
//		matrix.setSkew(0.5f,0.5f);//����Matrix������б��kx��kyΪX��Y�����ϵı�����
		matrix.setScale(0.2f,1f,mSrc.getWidth()/2,mSrc.getHeight()/2);//����Matrix��(px,py)Ϊ���Ľ������ţ�sx��syΪX��Y�����ϵ����ű�����
		matrix.postRotate(30,mSrc.getWidth()/2,mSrc.getHeight()/2);//���
		Paint paint = new Paint();
		canvas.drawBitmap(mSrc, matrix, paint);
		super.onDraw(canvas);
	}

}
