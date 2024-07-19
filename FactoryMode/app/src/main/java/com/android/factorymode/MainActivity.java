package com.android.factorymode;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements View.OnClickListener {

    Button button;
    RelativeLayout layout;
    private String TAG="MainActivity.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = findViewById(R.id.notify);
        button.setOnClickListener( this );

        layout = findViewById(R.id.notice_layout );
        layout.setOnClickListener( this );

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch "+v+" "+event.getAction());
                return false;
            }
        });

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch "+v+" "+event.getAction());
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if( v.getId()==R.id.notify ){
            Log.d(TAG, "button  clicked");
        }else if( v.getId()==R.id.notice_layout ){
            Log.d(TAG, "layout clicked");
        }
    }


}