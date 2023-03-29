package com.example.camera.gesture;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class GestureRecognizer {
    public interface  Listener extends GestureDetector.OnGestureListener{

    }
    private class GestureListenerImpl extends GestureDetector.SimpleOnGestureListener {

    }
    private Listener mListener;
    private final GestureDetector mGestureDetector;

    public GestureRecognizer( Context context,  Listener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureListenerImpl(), null, true);
    }

    public void onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
    }
}
