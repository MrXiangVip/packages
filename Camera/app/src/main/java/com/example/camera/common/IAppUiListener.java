package com.example.camera.common;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public interface IAppUiListener {

    interface ISurfaceStatusListener {
        /**
         * Invoked when a surface is ready for use.
         * @param surfaceObject The created surfaceHolder.
         * @param width The width of the surface
         * @param height The height of the surface
         */
        void surfaceAvailable(Object surfaceObject, int width, int height);

        /**
         * Invoked when the surface's buffers size changed.
         *  @param surfaceObject The changed surface
         * @param width The new width of the surface
         * @param height The new height of the surface
         */
        void surfaceChanged(Object surfaceObject, int width, int height);

        /**
         * Invoked when the specified surface is about to be destroyed.
         * @param surfaceObject The surface is about to be destroyed
         * @param width
         * @param height
         *
         */
        void surfaceDestroyed(Object surfaceObject, int width, int height);
    }

    interface OnModeChangeListener {
        /**
         * This gets called when current mode is changed.
         *
         * @param newModeKey key of the new mode to switch to.
         */
        public void onModeSelected( String newModeKey);
    }

    interface OnGestureListener {
        /**
         * Notified when a tap occurs with the down {@link MotionEvent}
         * that triggered it. This will be triggered immediately for
         * every down event. All other events should be preceded by this.
         *
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onDown(MotionEvent event);

        /**
         * Notified when a tap occurs with the up {@link MotionEvent}
         * that triggered it. This will be triggered immediately for
         * every up event. All other events should be preceded by this.
         *
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onUp(MotionEvent event);

        /**
         * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
         * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
         * the x and y axis in pixels per second.
         *
         * @param e1 The first down motion event that started the fling.
         * @param e2 The move motion event that triggered the current onFling.
         * @param velocityX The velocity of this fling measured in pixels per second
         *              along the x axis.
         * @param velocityY The velocity of this fling measured in pixels per second
         *              along the y axis.
         *
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
        /**
         * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
         * current move {@link MotionEvent}. The distance in x and y is also supplied for
         * convenience.
         *
         * @param e1 The first down motion event  position that started the scrolling.
         * @param e2 The move motion event  position that started the scrolling.
         * @param dx The distance along the X axis that has been scrolled since the last
         *              call to onScroll. This is NOT the distance between {@code e1}
         *              and {@code e2}.
         * @param dy The distance along the Y axis that has been scrolled since the last
         *              call to onScroll. This is NOT the distance between {@code e1}
         *              and {@code e2}.
         *
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */

        boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy);

        /**
         * Notified when a tap occurs with the up {@link MotionEvent}
         * that triggered it.
         *
         * @param x The up motion event x position that completed the first tap
         * @param y The up motion event y position that completed the first tap
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onSingleTapUp(float x, float y);

        /**
         * Notified when a single-tap occurs.
         *
         * Unlike {@link OnGestureListener#onSingleTapUp(float x, float y)}, this
         * will only be called after the detector is confident that the user's
         * first tap is not followed by a second tap leading to a double-tap
         * gesture.
         *
         * @param x The down motion event x position of the single-tap.
         * @param y The down motion event y position of the single-tap.
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onSingleTapConfirmed(float x, float y);

        /**
         * Notified when a double-tap occurs.
         *
         * @param x The down motion event x position of the first tap of the double-tap.
         * @param y The down motion event y position of the first tap of the double-tap.
         *
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onDoubleTap(float x, float y);

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param scaleGestureDetector The scale gesture detector for scale event.
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onScale(ScaleGestureDetector scaleGestureDetector);

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         * @param scaleGestureDetector The scale gesture detector for scale event.
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector);


        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         *
         * @param scaleGestureDetector The scale gesture detector for scale event.
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onScaleEnd(ScaleGestureDetector scaleGestureDetector);

        /**
         * Notified when a long press occurs with the initial on down {@link MotionEvent}
         * that trigged it.
         *
         * @param x The initial on down motion event x position that started the long press.
         * @param y The initial on down motion event y position that started the long press.
         * @return <code>true</code> to prevent this gesture from being propagated
         * further, or <code>false</code> to indicate that you have not handled this gesture
         * and it should continue to be propagated.
         */
        boolean onLongPress(float x, float y);
    }

    interface OnThumbnailClickedListener {
        /**
         *Called when thumbnail clicked.
         */
        void onThumbnailClicked();
    }

}
