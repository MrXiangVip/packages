package com.example.camera.gesture;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class GestureManager {
    private class GestureListenerImpl implements GestureRecognizer.Listener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private GestureRecognizer  mGestureRecognizer;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mGestureRecognizer.onTouchEvent(motionEvent);
            return true;
        }
    };

    public GestureManager(Context context) {
        mGestureRecognizer = new GestureRecognizer(context, new GestureListenerImpl());
    }

    public View.OnTouchListener getOnTouchListener() {
        return mTouchListener;
    }
}
