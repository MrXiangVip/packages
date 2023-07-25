package com.mediatek.hralauncher.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mediatek.hralauncher.holder.BaseHolder;
import com.mediatek.hralauncher.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class LauncherAdapter extends PagerAdapter {
    private List<BaseHolder> mAppHolders;
    private List<BaseHolder> mCardHolders;
    private String TAG="LauncherAdapter.";

    public LauncherAdapter(List<BaseHolder> prevHolders, List<BaseHolder> appHolders) {
        this.mCardHolders = prevHolders;
        this.mAppHolders = appHolders;
    }

    @Override
    public int getCount() {
        Log.d(TAG,"getCount card holder size: " +ListUtils.size(this.mCardHolders) +" app holder size: "+ListUtils.size(this.mAppHolders));
        return ListUtils.size(this.mCardHolders)+ListUtils.size(this.mAppHolders);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((BaseHolder) object).getRootView();

    }

    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG,"instantiateItem "+position);
        BaseHolder holder;
        int prevSize = ListUtils.size(this.mCardHolders);
        if (position < prevSize) {
            holder = this.mCardHolders.get(position);
        } else {
            holder = this.mAppHolders.get(position - prevSize);
        }
        View view = holder.getRootView();
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        container.addView(view);
        return holder;
    }
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((BaseHolder) object).getRootView());
    }

    public List<BaseHolder> getAllHolders() {
        List<BaseHolder> holders = new ArrayList<>();
        if (this.mCardHolders != null) {
            holders.addAll(this.mCardHolders);
        }
        if (this.mAppHolders != null) {
            holders.addAll(this.mAppHolders);
        }
        return holders;
    }
    public int getCardViewCount() {
        return ListUtils.size(this.mCardHolders);
    }

}
