package com.example.camera.exposure;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.camera.common.RotateLayout;
import com.example.camerabg.R;

public class ExposureView extends RotateLayout {

    private VerticalSeekBar mEvSeekbar;
    private String TAG ="ExposureView";

    public ExposureView(Context context) {
        super(context);
    }

    public ExposureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void onFinishInflate() {
        super.onFinishInflate();
        mEvSeekbar = (VerticalSeekBar) findViewById(R.id.ev_seekbar);
        mEvSeekbar.setThumb(this.getResources().getDrawable(R.drawable.icev_scrubber_xdf));
        mEvSeekbar.setOnSeekBarChangeListener(mEvSeekBarChangedListener);

    }

    private VerticalSeekBar.OnSeekBarChangeListener mEvSeekBarChangedListener =
            new VerticalSeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Log.d(TAG, "onProgressChanged "+progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Log.d(TAG, "onStartTrackingTouch ");

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Log.d(TAG, "onStopTrackingTouch ");

                }
            };



}
