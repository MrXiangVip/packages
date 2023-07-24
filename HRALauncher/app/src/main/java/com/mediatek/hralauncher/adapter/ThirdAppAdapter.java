package com.mediatek.hralauncher.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.classify.PrimitiveSimpleAdapter;
import com.mediatek.hralauncher.common.AppInfo;

import java.util.List;

public class ThirdAppAdapter extends PrimitiveSimpleAdapter<ThirdAppAdapter.ViewHolder> {
    private static final String TAG = "ThirdAppAdapter.";
    Context mContext;
    private List<AppInfo> mMockSource;
    private boolean mEditMode = false;

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
        if (mMockSource == null) {
            return 0;
        }
        return mMockSource.size();
    }

    @Override
    protected void onBindMainViewHolder(ViewHolder holder, int position) {
//        holder.bind(position);
        Log.d(TAG, "onBindMainViewHolder " + position);
        holder.bind(this.mMockSource.get(position), this.mEditMode);
    }

    @Override
    protected void onBindSubViewHolder(ViewHolder holder, int mainPosition, int subPosition) {
        Log.d(TAG, "onBindSubViewHolder " + mainPosition + " " + subPosition);
    }

    @Override
    protected int getSubItemCount(int parentPosition) {
        return 0;
    }

    class ViewHolder extends PrimitiveSimpleAdapter.ViewHolder {

        View holderView;
        ImageView ivAppicon;
        TextView iReaderFolderTag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            holderView = itemView;
            ivAppicon = holderView.findViewById(R.id.iv_appicon);
            iReaderFolderTag = holderView.findViewById(R.id.i_reader_folder_tag);
        }

        public void bind(final AppInfo iReaderMockData, boolean inEditMode) {
            ThirdAppAdapter.this.setAppIcon(ivAppicon, iReaderMockData.getIcon());
            iReaderFolderTag.setText(iReaderMockData.getAppName());
        }
    }

    public void setAppIcon(ImageView imageView, Drawable drawable) {
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        } else {
            imageView.setImageDrawable(null);
        }
    }

    public void onItemClick(ViewHolder viewHolder, int parentIndex, int index) {
        Log.d(TAG, "onItemClick");
        AppInfo appInfo = mMockSource.get(parentIndex);
        String packageName = appInfo.getPackageName();
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
}