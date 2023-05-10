package com.mediatek.factorytest.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mediatek.factorytest.R;

public class DemoTest extends Activity implements View.OnClickListener {

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_demo);
    }


    @Override
    public void onClick(View v) {

    }
}
