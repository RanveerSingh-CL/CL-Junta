package com.skeleton.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Custom Class that extends @{@link RelativeLayout}
 * make the height equivalent to its width
 */

public class GridSpacing extends RelativeLayout {
    /**
     * Constructor
     *
     * @param context : context
     */
    public GridSpacing(final Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context : context
     * @param attrs   : attrs
     */
    public GridSpacing(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public GridSpacing(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
/*
This is the key that will make the height equivalent to its width
*/
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}