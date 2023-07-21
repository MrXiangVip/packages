package com.mediatek.hralauncher.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class PageIndicatorView extends View {
    private String TAG="PageIndicatorView.";

    public PageIndicatorView(Context context) {
        super(context);
    }

    public PageIndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PageIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PageIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void onPageSelected(int position) {
        onPageSelect(position);
    }
    private void onPageSelect(int position) {
        Log.d(TAG, "onPageSelect "+position);
    }
    public void setSelection(int position) {
        Log.d(TAG, "setSelection "+position);
    }
}
