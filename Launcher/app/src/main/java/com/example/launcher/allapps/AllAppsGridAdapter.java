package com.example.launcher.allapps;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher.BaseDraggingActivity;
import com.example.launcher.R;
import com.example.launcher.widget.BubbleTextView;

public class AllAppsGridAdapter extends RecyclerView.Adapter<AllAppsGridAdapter.ViewHolder> {
    public static final int VIEW_TYPE_ICON = 1 << 1;

    private final LayoutInflater mLayoutInflater;
    private final BaseDraggingActivity mLauncher;
    private final AlphabeticalAppsList mApps;
    private final View.OnClickListener mOnIconClickListener;
    private final GridLayoutManager mGridLayoutMgr;
    protected String mEmptySearchMessage;
    private int mAppsPerRow;

    public AllAppsGridAdapter(BaseDraggingActivity launcher, LayoutInflater inflater,
                              AlphabeticalAppsList apps) {
        Resources res = launcher.getResources();
        mLauncher = launcher;
        mApps = apps;
        mEmptySearchMessage = res.getString(R.string.all_apps_loading_message);
//        mGridSizer = new GridSpanSizer();
        mGridLayoutMgr = new AppsGridLayoutManager(launcher);
//        mGridLayoutMgr.setSpanSizeLookup(mGridSizer);
        mLayoutInflater = inflater;

        mOnIconClickListener = launcher.getItemOnClickListener();
        setAppsPerRow(mLauncher.getDeviceProfile().inv.numAllAppsColumns);
    }
    public void setAppsPerRow(int appsPerRow) {
        mAppsPerRow = appsPerRow;
        mGridLayoutMgr.setSpanCount(mAppsPerRow);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ICON:
                BubbleTextView icon = (BubbleTextView) mLayoutInflater.inflate(
                        R.layout.all_apps_icon, parent, false);
                icon.setOnClickListener(mOnIconClickListener);
//                icon.setOnLongClickListener(mOnIconLongClickListener);
//                icon.setLongPressTimeoutFactor(1f);
//                icon.setOnFocusChangeListener(mIconFocusListener);

                // Ensure the all apps icon height matches the workspace icons in portrait mode.
//                icon.getLayoutParams().height = mLauncher.getDeviceProfile().allAppsCellHeightPx;
                return new ViewHolder(icon);
            default:
                throw new RuntimeException("Unexpected view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return  mApps.getAdapterItems().size();
    }

    public GridLayoutManager getLayoutManager() {
        return mGridLayoutMgr;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
        }
    }

    public class AppsGridLayoutManager extends GridLayoutManager {

        public AppsGridLayoutManager(Context context) {
            super(context, 1, GridLayoutManager.VERTICAL, false);
        }
    }

}