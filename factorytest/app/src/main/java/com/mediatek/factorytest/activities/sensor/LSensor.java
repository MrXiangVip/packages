package com.mediatek.factorytest.activities.sensor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.mediatek.factorytest.R;

public class LSensor extends Activity implements View.OnClickListener {

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_demo);
    }


    @Override
    public void onClick(View v) {

    }
}
