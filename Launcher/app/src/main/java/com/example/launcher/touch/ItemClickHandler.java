package com.example.launcher.touch;

import android.view.View;

public class ItemClickHandler {

    public static final View.OnClickListener INSTANCE = getInstance(null);

    public static final View.OnClickListener getInstance(String sourceContainer) {
        return v -> onClick(v, sourceContainer);
    }

    private static void onClick(View v, String sourceContainer) {

    }
}