package com.mediatek.hralauncher.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract  class BaseSubAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
}
