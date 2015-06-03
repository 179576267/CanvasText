package com.example.canvastext;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CanvasView extends View {
	/**
	 * TYPE_CIRCLE / TYPE_ROUND
	 */
	private int type;
	private static final int TYPE_CIRCLE = 0;
	private static final int TYPE_ROUND = 1;

	/**
	 * ͼƬ
	 */
	private Bitmap mSrc;
	/**
	 * Բ�ǵĴ�С
	 */
	private int mRadius;

	/**
	 * �ؼ��Ŀ��
	 */
	private int mWidth;
	/**
	 * �ؼ��ĸ߶�
	 */
	private int mHeight;
	/**
	 * Բ�α��ߵĿ��
	 */
	private int strokeWidth;
	/**
	 * Բ�α��ߵ���ɫ
	 */
	private int strokeColor;

	public CanvasView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasView(Context context) {
		this(context, null);
	}

	/**
	 * ��ʼ��һЩ�Զ���Ĳ���
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.CanvasView, defStyleAttr, 0);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CanvasView_src:
				mSrc = BitmapFactory.decodeResource(getResources(),
						a.getResourceId(attr, 0));
				break;
			case R.styleable.CanvasView_type:
				type = a.getInt(attr, 0);// Ĭ��ΪCircle
				break;
			case R.styleable.CanvasView_borderRadius:
				mRadius = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
								getResources().getDisplayMetrics()));// Ĭ��Ϊ10DP
				break;
				
			case R.styleable.CanvasView_strokeWidth:
				strokeWidth = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f,
								getResources().getDisplayMetrics()));// ���Բ�α߿�Ŀ��
				break;
				
			case R.styleable.CanvasView_bordercolor:
				strokeColor = a.getInt(attr, 0);// ���Բ�α߿����ɫ
				break;
				
			}
		}
		a.recycle();
	}

	/**
	 * ����ؼ��ĸ߶ȺͿ��
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/**
		 * ���ÿ��
		 */
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);

		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
		{
			mWidth = specSize;
		} else if (specMode == MeasureSpec.AT_MOST)// wrap_content
		{
			// ��ͼƬ�����Ŀ�
			int desireByImg = getPaddingLeft() + getPaddingRight()+ mSrc.getWidth();
			mWidth = Math.min(desireByImg, specSize);
		}

		/***
		 * ���ø߶�
		 */

		specMode = MeasureSpec.getMode(heightMeasureSpec);
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
		{
			mHeight = specSize;
		} else if (specMode == MeasureSpec.AT_MOST)// wrap_content
		{
			int desire = getPaddingTop() + getPaddingBottom()
					+ mSrc.getHeight();
			mHeight = Math.min(desire, specSize);
		}
		setMeasuredDimension(mWidth+strokeWidth*2, mHeight+strokeWidth*2);

	}

	/**
	 * ����
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		switch (type) {
		// �����TYPE_CIRCLE����Բ��
		case TYPE_CIRCLE:

			int min = Math.min(mWidth, mHeight);
			/**
			 * ���������һ�£���С��ֵ����ѹ��
			 */
			mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
			canvas.drawBitmap(createCircleImage(mSrc, min), strokeWidth, strokeWidth, null);
			
			if(strokeWidth!=0){
				Paint paint = new Paint();
				paint.setColor(strokeColor);
				paint.setAntiAlias(true);
				paint.setStyle(Paint.Style.STROKE); //���ƿ���Բ   
				paint.setStrokeWidth(strokeWidth);
				canvas.drawCircle(min/2+strokeWidth, min/2+strokeWidth, min/2+strokeWidth/2, paint);				
			}
			
			break;
		case TYPE_ROUND:
			canvas.drawBitmap(createRoundConerImage(mSrc), 0, 0, null);
			break;

		}

	}

	/**
	 * ����ԭͼ�ͱ䳤����Բ��ͼƬ
	 * 
	 * @param source
	 * @param min
	 * @return
	 */
	private Bitmap createCircleImage(Bitmap source, int min) {
		final Paint paint = new Paint();
		paint.setColor(Color.YELLOW);
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		/**
		 * ����һ��ͬ����С�Ļ���
		 */
		Canvas canvas = new Canvas(target);
		/**
		 * ���Ȼ���Բ��
		 */
		canvas.drawCircle(min/2, min/2, min/2 , paint);//�Ȼ��ľ�����״
		/**
		 * ʹ��SRC_IN���ο������˵��
		 */
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		/**
		 * ����ͼƬ
		 */
		canvas.drawBitmap(source, 0, 0, paint);//�󻭵������
		return target;
	}

	/**
	 * ����ԭͼ���Բ��
	 * 
	 * @param source
	 * @return
	 */
	private Bitmap createRoundConerImage(Bitmap source) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		Canvas canvas = new Canvas(target);
		RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
		canvas.drawRoundRect(rect, mRadius,mRadius, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}
}
