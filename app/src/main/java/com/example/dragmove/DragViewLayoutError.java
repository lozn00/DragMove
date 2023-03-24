package com.example.dragmove;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

public class DragViewLayoutError extends ViewGroup {

    private ViewDragHelper mViewDragHelper;

    public DragViewLayoutError(Context context) {
        this(context, null);
    }

    public DragViewLayoutError(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewLayoutError(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragCallback());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

   /* @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }*/


    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View childView = findTopChildUnder((int) ev.getX(), (int) ev.getY());
            if (childView != null) {
                ViewGroup.LayoutParams layoutParams = childView.getLayoutParams();
                if (layoutParams instanceof LayoutParams) {
                    LayoutParams layoutParamsCurrent = (LayoutParams) layoutParams;
                    if (layoutParamsCurrent.isDraggable) {
                        mViewDragHelper.captureChildView(childView, ev.getPointerId(0));
                        return true;
                    }
                }
//&& ((LayoutParams) childView.getLayoutParams()).isDraggable

            }
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {


        public boolean isDraggable = true;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }


    private View findTopChildUnder(int x, int y) {
        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE && x >= child.getLeft() && x < child.getRight() && y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public DragViewLayoutError.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new DragViewLayoutError.LayoutParams(this.getContext(), attrs);
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int leftBound = getPaddingLeft();
            int rightBound = getWidth() - child.getWidth() - getPaddingRight();
            return Math.min(Math.max(left, leftBound), rightBound);
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int topBound = getPaddingTop();
            int bottomBound = getHeight() - child.getHeight() - getPaddingBottom();
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xVel, float yVel) {
            super.onViewReleased(releasedChild, xVel, yVel);
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }
    }
}