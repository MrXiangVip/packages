package com.example.launcher.allapps;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AllAppsRecyclerView extends BaseRecyclerView{

    private AlphabeticalAppsList mApps;


    public AllAppsRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public AllAppsRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AllAppsRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onUpdateScrollbar(int dy) {

    }

    public void setApps(AlphabeticalAppsList apps) {
        mApps = apps;
    }
    public AlphabeticalAppsList getApps() {
        return mApps;
    }


    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }
}
