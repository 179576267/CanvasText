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
	 * 图片
	 */
	private Bitmap mSrc;
	/**
	 * 圆角的大小
	 */
	private int mRadius;

	/**
	 * 控件的宽度
	 */
	private int mWidth;
	/**
	 * 控件的高度
	 */
	private int mHeight;
	/**
	 * 圆形边线的宽度
	 */
	private int strokeWidth;
	/**
	 * 圆形边线的颜色
	 */
	private int strokeColor;

	public CanvasView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CanvasView(Context context) {
		this(context, null);
	}

	/**
	 * 初始化一些自定义的参数
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
				type = a.getInt(attr, 0);// 默认为Circle
				break;
			case R.styleable.CanvasView_borderRadius:
				mRadius = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
								getResources().getDisplayMetrics()));// 默认为10DP
				break;
				
			case R.styleable.CanvasView_strokeWidth:
				strokeWidth = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0f,
								getResources().getDisplayMetrics()));// 获得圆形边框的宽度
				break;
				
			case R.styleable.CanvasView_bordercolor:
				strokeColor = a.getInt(attr, 0);// 获得圆形边框的颜色
				break;
				
			}
		}
		a.recycle();
	}

	/**
	 * 计算控件的高度和宽度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/**
		 * 设置宽度
		 */
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);

		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
		{
			mWidth = specSize;
		} else if (specMode == MeasureSpec.AT_MOST)// wrap_content
		{
			// 由图片决定的宽
			int desireByImg = getPaddingLeft() + getPaddingRight()+ mSrc.getWidth();
			mWidth = Math.min(desireByImg, specSize);
		}

		/***
		 * 设置高度
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
	 * 绘制
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		switch (type) {
		// 如果是TYPE_CIRCLE绘制圆形
		case TYPE_CIRCLE:

			int min = Math.min(mWidth, mHeight);
			/**
			 * 长度如果不一致，按小的值进行压缩
			 */
			mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
			canvas.drawBitmap(createCircleImage(mSrc, min), strokeWidth, strokeWidth, null);
			
			if(strokeWidth!=0){
				Paint paint = new Paint();
				paint.setColor(strokeColor);
				paint.setAntiAlias(true);
				paint.setStyle(Paint.Style.STROKE); //绘制空心圆   
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
	 * 根据原图和变长绘制圆形图片
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
		 * 产生一个同样大小的画布
		 */
		Canvas canvas = new Canvas(target);
		/**
		 * 首先绘制圆形
		 */
		canvas.drawCircle(min/2, min/2, min/2 , paint);//先画的决定形状
		/**
		 * 使用SRC_IN，参考上面的说明
		 */
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		/**
		 * 绘制图片
		 */
		canvas.drawBitmap(source, 0, 0, paint);//后画的是填充
		return target;
	}

	/**
	 * 根据原图添加圆角
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
