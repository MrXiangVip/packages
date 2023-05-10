package com.mediatek.factorytest.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;

public class BackLight extends Activity implements View.OnClickListener{


    private static final String TAG = Utils.TAG+"LCDTest";
    private Button okBtn;
    private Button failBtn;
    private LinearLayout judgLayout;
//
    private final int SET_BRIGHTNESS_MODE_MANUAL =0;
    private final int SET_BRIGHTNESS =1;
    Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch ( what ) {
//              如果是自动调节亮度模式，先修改为手动调节亮度模式
                case SET_BRIGHTNESS_MODE_MANUAL:
                    setScreenBrightnessModeManual();
                    handler.sendEmptyMessageDelayed( SET_BRIGHTNESS,0 );
                    break;
//              设置亮度
                case SET_BRIGHTNESS:
                    setScreenBrightness();
                    break;
                default:
                    break;
            }
        }
    };

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
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.setAttributes(params);

        setContentView(R.layout.activity_test_backlight);

        judgLayout = findViewById( R.id.judg_layout );
        okBtn = findViewById( R.id.bt_ok);
        failBtn = findViewById( R.id.bt_failed);
        okBtn.setOnClickListener( this );
        failBtn.setOnClickListener( this );
//      设置亮度调节模式
        handler.sendEmptyMessage( SET_BRIGHTNESS_MODE_MANUAL );
    }
    /**
     * 获取屏幕的亮度
     * @param activity
     * @return
     */
    public  int getScreenBrightness() {
        int nowBrightnessValue = 0;
        try {
            nowBrightnessValue = Settings.System.getInt(
                    getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * 设置亮度
     * @param activity
     * @param brightness
     */
    public  void setScreenBrightness(  ) {
        int brightness = getScreenBrightness();
        Log.d(TAG, "getScreenBrightness: "+brightness);
        brightness +=50;
        if( brightness <=255 ){
//            WindowManager.LayoutParams lp = getWindow().getAttributes();
//            lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
//            getWindow().setAttributes(lp);
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
            handler.sendEmptyMessageDelayed( SET_BRIGHTNESS ,1000);
            Log.d(TAG, "setScreenBrightness: "+brightness);
        }else{
            Log.d(TAG, "setScreenBrightness over ");
            judgLayout.setVisibility( View.VISIBLE);
        }

    }
    public void setScreenBrightnessModeManual( ){
        Log.d(TAG, "setScreenBrightnessModeManual: ");
        ContentResolver contentResolver = getContentResolver();
        try {
            int mode = Settings.System.getInt(contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
            if (mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
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
