package com.skeleton.adapter;

/**
 * Created by rishucuber on 20/4/17.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * custom view pager for disabling paging with swipe
 */
public class CustomViewPager extends ViewPager {

    private boolean isEnabled;

    /**
     * @param context current context
     * @param attrs   attrs
     */
    public CustomViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.isEnabled = true;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (this.isEnabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        if (this.isSelected()) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    /**
     * @param enabled paging enabled or not
     */
    public void setPagingEnabled(final boolean enabled) {
        this.isEnabled = enabled;
    }
}
