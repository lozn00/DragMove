package com.example.dragmove;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.core.view.ViewConfigurationCompat;

public class DragView3Error extends View {

    private Scroller mScroller;
//    private Paint mPaint;
    /**
     * 屏幕拖动最小像素
     */
    private int mTouchSlop;
    /**
     * View宽度
     */
    private int width;
    /**
     * View高度
     */
    private int height;
    /**
     * MotionEvent.getX()
     */
    private int mEventX;
    /**
     * MotionEvent.getY()
     */
    private int mEventY;
    /**
     * View到屏幕左边距离
     */
    private int mStartX;
    /**
     * View到屏幕顶部距离
     */
    private int mStartY;
    /**
     * View默认大小
     */
    private static int DEFAULT_SIZE = 200;

    public DragView3Error(Context context) {
        this(context, null);
    }

    public DragView3Error(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        mPaint = new Paint();
        mScroller = new Scroller(context);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledHoverSlop(configuration);
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
    }

   /* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            if (heightMode == MeasureSpec.EXACTLY) {
                width = MeasureSpec.getSize(heightMeasureSpec);
            } else {
                width = DEFAULT_SIZE;
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = width;
        }
        setMeasuredDimension(width, height);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    /*    if (null != mBitmap) {
            Rect src = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            Rect dst = new Rect(0, 0, width, height);
            canvas.drawBitmap(mBitmap, src, dst, mPaint);
        } else {
        }*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mEventX = (int) event.getX();
                mEventY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mStartX = (int) event.getRawX() - mEventX;
                mStartY = (int) event.getRawY() - mEventY;
                layout(mStartX, mStartY, mStartX + width, mStartY + height);
                break;
            case MotionEvent.ACTION_UP:
                startScroller();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int l = mScroller.getCurrX();
            layout(l, mStartY, l + width, mStartY + height);
            invalidate();
        }
    }

    /**
     * 开始Scroller动画
     */
    private void startScroller() {
        mScroller.forceFinished(true);
        mScroller.startScroll(mStartX, mStartY, -mStartX, 0);
        int screenWidth = getScreenWidth();
        // Scroller动画默认250ms，超过屏幕一半时设置为500ms
        if (mStartX > screenWidth / 2) {
            mScroller.extendDuration(500);
        }
        invalidate();
    }

    private int getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }
}