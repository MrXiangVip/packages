package com.example.camera.exposure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.camera.common.RotateLayout;
import com.example.camerabg.R;

public class ExposureView extends RotateLayout {

    private VerticalSeekBar mEvSeekbar;

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

    }

}
