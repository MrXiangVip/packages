package com.mediatek.factorytest.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;

public class KeyCode extends Activity implements View.OnClickListener {

    private Button  failed, success;
    public Button volumeUp, volumeDown,powerKey;


    public boolean flagPower=false;
    public boolean flagUp=false;
    public boolean flagDown=false;
    private final String TAG = Utils.TAG+this.getClass().getSimpleName() ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_test);

        powerKey = findViewById(R.id.power_key);
        volumeUp = findViewById( R.id.volume_up);
        volumeDown = findViewById( R.id.volume_down );


        failed = findViewById( R.id.bt_failed);
        failed.setOnClickListener( this );
        success = findViewById(R.id.bt_ok);
        success.setEnabled( false );
    }

    @Override
    public void onClick(View v) {
        if (v.equals(failed)) {
            setResult(Utils.FAILED_CODE);
            finish();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown occured:" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            flagUp = true;
            volumeUp.setBackgroundColor(getColor(R.color.limegreen));
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            flagDown = true;
            volumeDown.setBackgroundColor(getColor(R.color.limegreen));

        }else if( keyCode == KeyEvent.KEYCODE_POWER){
            flagPower = true;
            powerKey.setBackgroundColor( getColor(R.color.limegreen));
        }

        if( flagDown && flagUp && flagPower ){
            setResult( Utils.SUCCESS_CODE );
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        return;
    }

    protected void onDestroy() {

        super.onDestroy();
    }
}