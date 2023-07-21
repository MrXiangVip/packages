package com.mediatek.hralauncher.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.classify.PrimitiveSimpleAdapter;

public class ThirdAppAdapter extends PrimitiveSimpleAdapter<ThirdAppAdapter.ViewHolder> {
    private static final String TAG = "ThirdAppAdapter.";
    Context mContext;

    public ThirdAppAdapter(Context context) {
        mContext = context;
    }


    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_i_reader_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected int getItemCount() {
        return 0;
    }

    @Override
    protected void onBindMainViewHolder(ViewHolder holder, int position) {
//        holder.bind(position);
        Log.d(TAG, "onBindMainViewHolder "+position);
    }

    @Override
    protected void onBindSubViewHolder(ViewHolder holder, int mainPosition, int subPosition) {
        Log.d(TAG, "onBindSubViewHolder "+mainPosition +" "+ subPosition);
    }

    @Override
    protected int getSubItemCount(int parentPosition) {
        return 0;
    }

    class ViewHolder extends PrimitiveSimpleAdapter.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }


    }

}
