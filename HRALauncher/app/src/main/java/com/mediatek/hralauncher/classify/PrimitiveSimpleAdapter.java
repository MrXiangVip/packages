package com.mediatek.hralauncher.classify;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mediatek.hralauncher.adapter.BaseMainAdapter;
import com.mediatek.hralauncher.adapter.BaseSubAdapter;

public abstract class PrimitiveSimpleAdapter<VH extends PrimitiveSimpleAdapter.ViewHolder> implements BaseSimpleAdapter {

    private SimpleMainAdapter mSimpleMainAdapter = new SimpleMainAdapter();
    private SimpleSubAdapter mSimpleSubAdapter = new SimpleSubAdapter();
    private String TAG = "PrimitiveSimpleAdapter.";

    @Override
    public BaseMainAdapter getMainAdapter() {
        return mSimpleMainAdapter;
    }

    @Override
    public BaseSubAdapter getSubAdapter() {
        return mSimpleSubAdapter;
    }

    protected abstract VH onCreateViewHolder(ViewGroup parent, int viewType);
    protected abstract int getItemCount();
    protected abstract void onBindMainViewHolder(VH holder, int position);
    protected abstract void onBindSubViewHolder(VH holder, int mainPosition, int subPosition);
    protected abstract int getSubItemCount(int parentPosition);


    private class SimpleMainAdapter extends BaseMainAdapter<VH> {
        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder ");
            VH vh = PrimitiveSimpleAdapter.this.onCreateViewHolder(parent, viewType);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            PrimitiveSimpleAdapter.this.onBindMainViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return PrimitiveSimpleAdapter.this.getItemCount();

        }
    }

    private class SimpleSubAdapter extends BaseSubAdapter<VH> {
        private int mParentPosition = -1;

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            VH vh = PrimitiveSimpleAdapter.this.onCreateViewHolder(parent, viewType);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            PrimitiveSimpleAdapter.this.onBindSubViewHolder(holder, mParentPosition, position);
        }

        @Override
        public int getItemCount() {
            return getSubItemCount(mParentPosition);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}