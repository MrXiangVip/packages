package com.example.launcher.allapps;

public interface FloatingHeaderRow {

    FloatingHeaderRow[] NO_ROWS = new FloatingHeaderRow[0];
    void setup(FloatingHeaderView parent, FloatingHeaderRow[] allRows, boolean tabsHidden);

}
