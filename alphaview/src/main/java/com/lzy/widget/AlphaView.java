package com.lzy.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2015/9/15
 * 描    述：可以渐变的View
 * 修订历史：
 * ================================================
 */
public class AlphaView extends View {

    private Bitmap mIconNormal;                   //默认图标
    private Bitmap mIconSelected;                 //选中的图标
    private String mText;                         //描述文本
    private int mTextColorNormal = 0xFF999999;    //描述文本的默认显示颜色
    private int mTextColorSelected = 0xFF46C01B;  //述文本的默认选中显示颜色
    private int mTextSize = 12;                   //描述文本的默认字体大小 12sp
    private int padding = 5;                      //文字和图片之间的距离 5dp

    private float mAlpha;                         //当前的透明度
    private Paint mSelectedPaint = new Paint();   //背景的画笔
    private Rect mIconAvailableRect = new Rect(); //图标可用的绘制区域
    private Rect mIconDrawRect = new Rect();      //图标真正的绘制区域
    private Paint mTextPaint;                     //描述文本的画笔
    private Rect mTextBound;                      //描述文本矩形测量大小
    private Paint.FontMetricsInt mFmi;            //用于获取字体的各种属性

    public AlphaView(Context context) {
        this(context, null);
    }

    public AlphaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlphaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getResources().getDisplayMetrics());
        padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, padding, getResources().getDisplayMetrics());

        //获取所有的自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AlphaView);
        BitmapDrawable iconNormal = (BitmapDrawable) a.getDrawable(R.styleable.AlphaView_av_tabIconNormal);
        if (iconNormal != null) {
            mIconNormal = iconNormal.getBitmap();
        }
        BitmapDrawable iconSelected = (BitmapDrawable) a.getDrawable(R.styleable.AlphaView_av_tabIconSelected);
        if (iconSelected != null) {
            mIconSelected = iconSelected.getBitmap();
        }
        mText = a.getString(R.styleable.AlphaView_av_tabText);
        mTextSize = a.getDimensionPixelSize(R.styleable.AlphaView_av_tabTextSize, mTextSize);
        mTextColorNormal = a.getColor(R.styleable.AlphaView_av_textColorNormal, mTextColorNormal);
        mTextColorSelected = a.getColor(R.styleable.AlphaView_av_textColorSelected, mTextColorSelected);
        a.recycle();

        initText();
    }

    /**
     * 如果有设置文字就获取文字的区域大小
     */
    private void initText() {
        if (mText != null) {
            mTextBound = new Rect();
            mTextPaint = new Paint();
            mTextPaint.setTextSize(mTextSize);
            mTextPaint.setAntiAlias(true);
            mTextPaint.setDither(true);
            mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
            mFmi = mTextPaint.getFontMetricsInt();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mText == null && (mIconNormal == null || mIconSelected == null)) {
            throw new IllegalArgumentException("必须设置 tabText 或者 tabIconSelected、tabIconNormal 两个，或者全部设置");
        }

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        //计算出可用绘图的区域
        int availableWidth = measuredWidth - paddingLeft - paddingRight;
        int availableHeight = measuredHeight - paddingTop - paddingBottom;
        if (mText != null && mIconNormal != null) {
            availableHeight -= (mTextBound.height() + padding);
            //计算出图标可以绘制的画布大小
            mIconAvailableRect.set(paddingLeft, paddingTop, paddingLeft + availableWidth, paddingTop + availableHeight);
            //计算文字的绘图区域
            int textLeft = paddingLeft + (availableWidth - mTextBound.width()) / 2;
            int textTop = mIconAvailableRect.bottom + padding;
            mTextBound.set(textLeft, textTop, textLeft + mTextBound.width(), textTop + mTextBound.height());
        } else if (mText == null) {
            //计算出图标可以绘制的画布大小
            mIconAvailableRect.set(paddingLeft, paddingTop, paddingLeft + availableWidth, paddingTop + availableHeight);
        } else if (mIconNormal == null) {
            //计算文字的绘图区域
            int textLeft = paddingLeft + (availableWidth - mTextBound.width()) / 2;
            int textTop = paddingTop + (availableHeight - mTextBound.height()) / 2;
            mTextBound.set(textLeft, textTop, textLeft + mTextBound.width(), textTop + mTextBound.height());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int alpha = (int) Math.ceil(mAlpha * 255);
        if (mIconNormal != null && mIconSelected != null) {
            Rect drawRect = availableToDrawRect(mIconAvailableRect, mIconNormal);
            mSelectedPaint.reset();
            mSelectedPaint.setAntiAlias(true);//设置抗锯齿
            mSelectedPaint.setFilterBitmap(true);//抗锯齿
            mSelectedPaint.setAlpha(255 - alpha);
            canvas.drawBitmap(mIconNormal, null, drawRect, mSelectedPaint);
            mSelectedPaint.reset();
            mSelectedPaint.setAntiAlias(true);//设置抗锯齿
            mSelectedPaint.setFilterBitmap(true);//抗锯齿
            mSelectedPaint.setAlpha(alpha); //setAlpha必须放在paint的属性最后设置，否则不起作用
            canvas.drawBitmap(mIconSelected, null, drawRect, mSelectedPaint);
        }
        if (mText != null) {
            //绘制原始文字,setAlpha必须放在paint的属性最后设置，否则不起作用
            mTextPaint.setColor(mTextColorNormal);
            mTextPaint.setAlpha(255 - alpha);
            //由于在该方法中，y轴坐标代表的是baseLine的值，经测试，mTextBound.height() + mFmi.bottom 就是字体的高
            //所以在最后绘制前，修正偏移量，将文字向上修正 mFmi.bottom / 2 即可实现垂直居中
            canvas.drawText(mText, mTextBound.left, mTextBound.bottom - mFmi.bottom / 2, mTextPaint);
            //绘制变色文字，setAlpha必须放在paint的属性最后设置，否则不起作用
            mTextPaint.setColor(mTextColorSelected);
            mTextPaint.setAlpha(alpha);
            canvas.drawText(mText, mTextBound.left, mTextBound.bottom - mFmi.bottom / 2, mTextPaint);
        }
    }

    private Rect availableToDrawRect(Rect availableRect, Bitmap bitmap) {
        float dx = 0, dy = 0;
        float wRatio = availableRect.width() * 1.0f / bitmap.getWidth();
        float hRatio = availableRect.height() * 1.0f / bitmap.getHeight();
        if (wRatio > hRatio) {
            dx = (availableRect.width() - hRatio * bitmap.getWidth()) / 2;
        } else {
            dy = (availableRect.height() - wRatio * bitmap.getHeight()) / 2;
        }
        int left = (int) (availableRect.left + dx + 0.5f);
        int top = (int) (availableRect.top + dy + 0.5f);
        int right = (int) (availableRect.right - dx + 0.5f);
        int bottom = (int) (availableRect.bottom - dy + 0.5f);
        mIconDrawRect.set(left, top, right, bottom);
        return mIconDrawRect;
    }

    /**
     * @param alpha 对外提供的设置透明度的方法，取值 0.0 ~ 1.0
     */
    public void setIconAlpha(float alpha) {
        if (alpha < 0 || alpha > 1) {
            throw new IllegalArgumentException("透明度必须是 0.0 - 1.0");
        }
        mAlpha = alpha;
        invalidateView();
    }

    /**
     * 根据当前所在线程更新界面
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
