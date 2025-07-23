package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ScrollView;

import com.example.myapplication.R;

public class CreateInputConfigPanel extends ScrollView {
    public CreateInputConfigPanel(Context context) {
        this(context,null);
    }

    public CreateInputConfigPanel(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CreateInputConfigPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CreateInputConfigPanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate( R.layout.create_input_config_panel, this, true);
    }
}
