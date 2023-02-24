package com.example.launcher.qsb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.launcher.Launcher;
import com.example.launcher.R;

public class QsbWidgetHostView extends NavigableAppWidgetHostView{

    public QsbWidgetHostView(Context context) {
        super(context);
    }

    public QsbWidgetHostView(Context context, int animationIn, int animationOut) {
        super(context, animationIn, animationOut);
    }

    public static View getDefaultView(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qsb_default_view, parent, false);
        v.findViewById(R.id.btn_qsb_search).setOnClickListener((v2) ->
                Launcher.getLauncher(v2.getContext()).startSearch("", false, null, true));
        return v;
    }
}
