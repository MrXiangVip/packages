package com.example.camera.exposure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatSeekBar;

public class VerticalSeekBar2 extends AppCompatSeekBar {
    private static final  String TAG = VerticalSeekBar2.class.getSimpleName();
    private Context mContext;
    private OnSeekBarChangeListener mListener;
    private static final double GAP_IN_PX = 4.25;

    /**
     * Constructor.
     *
     * @param context The Context the view is running in, through which it can access the current
     *                theme, resources, etc.
     */
    public VerticalSeekBar2(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * Constructor.
     *
     * @param context  The Context the view is running in, through which it can access the current
     *                 theme, resources, etc.
     * @param attrs    The attributes of the XML tag that is inflating the view.
     * @param defStyle An attribute in the current theme that contains a reference to a style
     *                 resource that supplies default values for the view. Can be 0 to not look
     *                 for defaults.
     */
    public VerticalSeekBar2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    /**
     * The constructor.
     *
     * @param context The Context the view is running in, through which it can access the current
     *                theme, resources, etc.
     * @param attrs   The attributes of the XML tag that is inflating the view.
     */
    public VerticalSeekBar2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

//    @Override
//    public void setThumb(Drawable thumb) {
//        if (thumb == null || mContext == null || !(thumb instanceof VectorDrawable)) {
//            return;
//        }
//        Bitmap bitmap = Bitmap.createBitmap(thumb.getIntrinsicWidth(),
//                thumb.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas bitmapCanvas = new Canvas(bitmap);
//        final float scale = mContext.getResources().getDisplayMetrics().density;
//        int gap = (int) (GAP_IN_PX * scale + 0.5f);
//        thumb.setBounds(gap, gap, bitmapCanvas.getWidth() - gap, bitmapCanvas.getHeight() - gap);
//        thumb.draw(bitmapCanvas);
//        BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
//        super.setThumb(bitmapDrawable);
//    }

    protected void onDraw(Canvas c) {
        c.rotate(-90);
        c.translate(-getHeight(), 0);
        super.onDraw(c);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        getProgressDrawable().setAlpha(255);
        setProgressDrawable(new ColorDrawable(Color.WHITE));
        int progress = getMax() - (int) (getMax() * event.getY() / getHeight());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mListener != null) {
                    mListener.onStartTrackingTouch(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                setProgress(progress);
                if (mListener != null) {
                    mListener.onProgressChanged(this, progress, true);
                }
                break;
            case MotionEvent.ACTION_UP:
                setProgress(progress);
                if (mListener != null) {
                    mListener.onStopTrackingTouch(this);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        super.setOnSeekBarChangeListener(l);
        mListener = l;
    }


}
