package com.example.launcher.allapps;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

import com.example.launcher.R;

public class WorkModeSwitch extends Switch {
    public WorkModeSwitch(Context context) {
        super(context);
    }

    public WorkModeSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WorkModeSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WorkModeSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void update(boolean isChecked) {
        super.setChecked(isChecked);
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                isChecked ? R.drawable.ic_corp : R.drawable.ic_corp_off, 0, 0, 0);
        setEnabled(true);
    }
    public void setWorkTabVisible(boolean workTabVisible) {

    }
}
