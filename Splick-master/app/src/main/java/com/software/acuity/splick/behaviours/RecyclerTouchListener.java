package com.software.acuity.splick.behaviours;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.acuity.splick.interfaces.IRecyclerClickListener;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    GestureDetector gestureDetector;
    IRecyclerClickListener recyclerClickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final IRecyclerClickListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (child != null && recyclerClickListener != null) {
                    recyclerClickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && recyclerClickListener != null && gestureDetector.onTouchEvent(e)) {
            recyclerClickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
