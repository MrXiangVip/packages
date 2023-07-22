package com.mediatek.hralauncher.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mediatek.hralauncher.adapter.BaseMainAdapter;
import com.mediatek.hralauncher.adapter.BaseSubAdapter;
import com.mediatek.hralauncher.classify.BaseSimpleAdapter;

public class ClassifyView extends FrameLayout {
    private RecyclerView mMainRecyclerView;
    private RecyclerView mSubRecyclerView;
    private int mMainSpanCount=3;
    private int mSubSpanCount=3;

    /**
     * 放置主要RecyclerView的容器
     */
    private ViewGroup mMainContainer;
    /**
     * 放置次级RecyclerView的容器
     */
    private ViewGroup mSubContainer;

    private RecyclerView.OnItemTouchListener mMainItemTouchListener;
    private RecyclerView.OnItemTouchListener mSubItemTouchListener;
//    private MainRecyclerViewCallBack mMainCallBack;
//    private SubRecyclerViewCallBack mSubCallBack;

    public ClassifyView(@NonNull Context context) {
        this(context, null);
    }

    public ClassifyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassifyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ClassifyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {


        mMainContainer = new FrameLayout(context);
        mSubContainer = new FrameLayout(context);
        mMainContainer.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mMainRecyclerView = getMain(context, attrs);
        mSubRecyclerView = getSub(context, attrs);

        mMainContainer.addView(mMainRecyclerView);
        addViewInLayout(mMainContainer, 0, mMainContainer.getLayoutParams());

    }
    protected RecyclerView getMain(Context context, AttributeSet parentAttrs) {
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new GridLayoutManager(context, mMainSpanCount));
//        RecyclerView.ItemAnimator itemAnimator = new ClassifyItemAnimator();
//        itemAnimator.setChangeDuration(CHANGE_DURATION);
//        recyclerView.setItemAnimator(itemAnimator);
        return recyclerView;
    }

    protected RecyclerView getSub(Context context, AttributeSet parentAttrs) {
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new GridLayoutManager(context, mSubSpanCount));
//        RecyclerView.ItemAnimator itemAnimator = new ClassifyItemAnimator();
//        itemAnimator.setChangeDuration(CHANGE_DURATION);
//        recyclerView.setItemAnimator(itemAnimator);
        return recyclerView;
    }


    public void setAdapter(BaseSimpleAdapter baseSimpleAdapter) {
        setAdapter(baseSimpleAdapter.getMainAdapter(), baseSimpleAdapter.getSubAdapter());
//        if (baseSimpleAdapter.isShareViewPool()) {
//            setShareViewPool(new RecyclerView.RecycledViewPool());
//        }
    }
    public void setAdapter(BaseMainAdapter mainAdapter, BaseSubAdapter subAdapter) {
        mMainRecyclerView.setAdapter(mainAdapter);
//        mMainRecyclerView.addOnItemTouchListener(mMainItemTouchListener);
//        mMainCallBack = mainAdapter;
        mSubRecyclerView.setAdapter(subAdapter);
//        mSubRecyclerView.addOnItemTouchListener(mSubItemTouchListener);
//        mSubCallBack = subAdapter;
//        mMainRecyclerView.setOnDragListener(new MainDragListener());
    }
}
