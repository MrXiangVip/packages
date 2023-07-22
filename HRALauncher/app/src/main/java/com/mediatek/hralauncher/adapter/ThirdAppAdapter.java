package com.mediatek.hralauncher.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.classify.PrimitiveSimpleAdapter;
import com.mediatek.hralauncher.common.AppInfo;

import java.util.List;

public class ThirdAppAdapter extends PrimitiveSimpleAdapter<ThirdAppAdapter.ViewHolder> {
    private static final String TAG = "ThirdAppAdapter.";
    Context mContext;
    private List<AppInfo> mMockSource;
    private boolean mEditMode =false;

    public ThirdAppAdapter(Context context) {
        mContext = context;
    }

    public void setMockSource(List<AppInfo> mockSource) {
        this.mMockSource = mockSource;
//        notifyDataSetChanged();
    }

    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder ");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_i_reader_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected int getItemCount() {
        if( mMockSource == null ){
            return 0;
        }
        return mMockSource.size();
    }

    @Override
    protected void onBindMainViewHolder(ViewHolder holder, int position) {
//        holder.bind(position);
        Log.d(TAG, "onBindMainViewHolder "+position);
        holder.bind(this.mMockSource.get(position), this.mEditMode);
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

        public void bind(final AppInfo iReaderMockData, boolean inEditMode) {
//            ThirdAppAdapter.this.setAppIcon(/*iReaderMockData.getAppInfo(), */this.mBinding.ivAppicon, iReaderMockData.getIcon());
        }
    }
    public void setAppIcon(ImageView imageView, Drawable drawable) {

    }
}
