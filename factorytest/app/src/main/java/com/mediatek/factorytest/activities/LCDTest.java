package com.mediatek.factorytest.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;

public class LCDTest<what> extends Activity implements View.OnClickListener{


    private static final String TAG = Utils.TAG+"LCDTest";
    private Button okBtn;
    private Button failBtn;
    private LinearLayout judgLayout;
//    private ImageView  imageView;
    private TextView    switcher;
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);

//    private Integer[] mImageIds = {
//            R.drawable.lcd_test_00,
//            R.drawable.lcd_test_01,
//            R.drawable.lcd_test_02,
//            R.drawable.lcd_test_03,
//            R.drawable.lcd_test_04,
//            R.drawable.lcd_test_05,
//            R.drawable.lcd_test_06
//    };

    private int mImageIds[] ={ Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.WHITE,
            Color.BLACK};

    private final int INIT_INDEX=0;

    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int index = msg.what;
            setImageView( index );
        }
    };
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setAttributes(params);

        setContentView(R.layout.activity_lcd_test);

//        imageView = findViewById( R.id.imageSwitcher);
        switcher = findViewById( R.id.switcher);
        judgLayout = findViewById( R.id.judg_layout );
        okBtn = findViewById( R.id.bt_ok);
        failBtn = findViewById( R.id.bt_failed);
        okBtn.setOnClickListener( this );
        failBtn.setOnClickListener( this );
        handler.sendEmptyMessage( INIT_INDEX );
    }

    public void setImageView(int index ){
        Log.d(TAG, "setImageView : "+index);
        if( index < mImageIds.length ){
//            imageView.setImageResource( mImageIds[index] );
            switcher.setBackgroundColor( mImageIds[index] );
            index +=1;
            handler.sendEmptyMessageDelayed( index, 800);
        }else{
            judgLayout.setVisibility( View.VISIBLE );
        }
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.bt_ok:
                setResult( Utils.SUCCESS_CODE);
                finish();
                break;
            case R.id.bt_failed:
                setResult( Utils.FAILED_CODE);
                finish();
                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        return;
    }
}
