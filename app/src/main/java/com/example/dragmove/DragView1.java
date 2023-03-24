package com.example.dragmove;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class DragView1 extends View {
    private GestureDetector mGestureDetector;
    private Scroller mScroller;
    private int mLastX;
    private int mLastY;
    private int dx;
    private int dy;

    public DragView1(Context context) {
        super(context);
        initView(context);
    }

    public DragView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DragView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                int dx = (int) (e2.getX() - mLastX);
                int dy = (int) (e2.getY() - mLastY);
                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                layout(left, top, right, bottom);
                mLastX = (int) e2.getX();
                mLastY = (int) e2.getY();
                return true;
            }
        });
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                dx = (int) (event.getX() - mLastX);
                dy = (int) (event.getY() - mLastY);
                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;
                layout(left, top, right, bottom);
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                //如果要实现松手后自动回弹到某个位置,取消这里和computeScroll的实现
//                mScroller.startScroll(getLeft(), getTop(), dx, dy, 500);
                invalidate();
                break;
        }
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
     /*   if (mScroller.computeScrollOffset()) {
            layout(mScroller.getCurrX(), mScroller.getCurrY(), mScroller.getCurrX() + getWidth(), mScroller.getCurrY() + getHeight());
            postInvalidate();
        }*/
    }
}