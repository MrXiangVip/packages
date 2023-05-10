package com.mediatek.factorytest.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;

public class Vibrator extends Activity implements View.OnClickListener {

    private Button success;
    private Button  failed;
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        success = findViewById(R.id.bt_ok);
        failed  = findViewById(R.id.bt_failed);
        success.setOnClickListener( this );
        failed.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        if( v.equals( success )){
            setResult( Utils.SUCCESS_CODE);
            finish();
        }else if( v.equals( failed )){
            setResult( Utils.FAILED_CODE);
            finish();
        }
    }
}
