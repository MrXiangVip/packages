package com.android.lock.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.lock.R;
import com.android.lock.bean.PackageBean;
import com.android.lock.utils.ProviderUtils;
import com.android.lock.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ManageApplicationAdapter extends RecyclerView.Adapter<ManageApplicationAdapter.ViewHolder> {

    Context mContext;
    List<PackageBean> mData;

    ManageApplicationAdapter(Context context) {
        mContext = context;
        Utils.initLaunchedPackageBean(context);//在init里加需要屏蔽的包名
        mData = Utils.getLaunchedPackageBean(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lock_item_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PackageBean bean = mData.get(position);
        holder.appName.setText( bean.getPackageLabel() );
        holder.appIcon.setImageDrawable( bean.getPackageIcon() );

        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues values = new ContentValues();
                values.put("packageName", bean.getPackageName());
                values.put("lockCondition", isChecked);
                ProviderUtils.getInstance(mContext).insertPackageBean( values);
                if( isChecked ){
                    holder.appStatus.setText("锁定");
                }else{
                    holder.appStatus.setText("未锁定");
                }
            }
        });
        if( bean.getLockFlag() == 0 ){
            holder.aSwitch.setChecked( false );
            holder.appStatus.setText("未锁定");
        }else if( bean.getLockFlag() == 1){
            holder.aSwitch.setChecked( true );
            holder.appStatus.setText("锁定");
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView  appName;
        public TextView  appStatus;
        public ImageView appIcon;
        public Switch     aSwitch;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appName = itemView.findViewById( R.id.app_name);
            appStatus =  itemView.findViewById(R.id.check_status );
            appIcon = itemView.findViewById( R.id.app_icon );
            aSwitch = itemView.findViewById(R.id.arrow);

        }

    }
}