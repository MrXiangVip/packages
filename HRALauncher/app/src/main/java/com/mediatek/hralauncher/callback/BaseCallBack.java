package com.mediatek.hralauncher.callback;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface BaseCallBack {

    void onItemClick(int position, View pressedView);
    void onItemClick(RecyclerView recyclerView, int position, View pressedView);

}
