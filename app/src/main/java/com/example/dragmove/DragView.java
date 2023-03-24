package com.example.dragmove;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DragView extends View {
    private int mLastX;
    private int mLastY;

    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                int left = getLeft() + deltaX;
                int top = getTop() + deltaY;
                int right = getRight() + deltaX;
                int bottom = getBottom() + deltaY;
                // 限制子View的移动范围，避免越界
                if (left < 0) {
                    left = 0;
                    right = left + getWidth();
                }
                if (right > ((View) getParent()).getWidth()) {
                    right = ((View) getParent()).getWidth();
                    left = right - getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + getHeight();
                }
                if (bottom > ((View) getParent()).getHeight()) {
                    bottom = ((View) getParent()).getHeight();
                    top = bottom - getHeight();
                }
                layout(left, top, right, bottom);
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}