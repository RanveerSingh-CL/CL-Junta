package com.skeleton.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


/**
 * RecyclerTouchListener
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private GestureDetector gestureDetector;
    private ClickListener clickListener;

    /**
     * cocnstructor
     *
     * @param context       : context
     * @param recyclerView  : recyclerview reference
     * @param clickListener : ref of interface
     */
    public RecyclerTouchListener(final Context context, final RecyclerView recyclerView,
                                 final ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(final MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(final MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
                super.onLongPress(e);
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(final RecyclerView rv, final MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(final RecyclerView rv, final MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(final boolean disallowIntercept) {

    }

    /**
     * interface
     */
    public interface ClickListener {
        /**
         * abstract method on single click
         *
         * @param view     : view of item clicked
         * @param position : position at which item clicked
         */
        void onClick(View view, int position);

        /**
         * abstract method on long click
         *
         * @param view     : view of item clicked
         * @param position : position at which item clicked
         */
        void onLongClick(View view, int position);
    }


}


