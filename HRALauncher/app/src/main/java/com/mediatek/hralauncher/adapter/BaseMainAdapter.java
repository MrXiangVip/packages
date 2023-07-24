package com.mediatek.hralauncher.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.mediatek.hralauncher.callback.BaseCallBack;
import com.mediatek.hralauncher.callback.MainRecyclerViewCallBack;

import java.util.List;

public abstract class BaseMainAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements MainRecyclerViewCallBack {

    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

}
