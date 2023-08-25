package com.example.launcher.allapps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher.R;
import com.example.launcher.views.RecyclerViewFastScroller;

public abstract class BaseRecyclerView extends RecyclerView {

    protected RecyclerViewFastScroller mScrollbar;

    public BaseRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public abstract void onUpdateScrollbar(int dy);

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        bindFastScrollbar();
    }

    public void bindFastScrollbar() {
        ViewGroup parent = (ViewGroup) getParent().getParent();
        mScrollbar = parent.findViewById(R.id.fast_scroller);
        mScrollbar.setRecyclerView(this, parent.findViewById(R.id.fast_scroller_popup));
        onUpdateScrollbar(0);
    }

}
