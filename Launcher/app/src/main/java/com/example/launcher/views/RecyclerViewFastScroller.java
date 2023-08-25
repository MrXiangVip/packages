package com.example.launcher.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.launcher.allapps.BaseRecyclerView;

public class RecyclerViewFastScroller extends View {
    private TextView mPopupView;
    protected BaseRecyclerView mRv;


    public RecyclerViewFastScroller(Context context) {
        this(context, null);
    }

    public RecyclerViewFastScroller(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewFastScroller(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RecyclerViewFastScroller(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setRecyclerView(BaseRecyclerView rv, TextView popupView) {
        mRv = rv;
        mPopupView = popupView;


    }

}