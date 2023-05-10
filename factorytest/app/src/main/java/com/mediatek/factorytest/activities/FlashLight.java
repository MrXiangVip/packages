package com.mediatek.factorytest.activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;

public class FlashLight extends Activity implements View.OnClickListener {
    private Button success;
    private Button  failed;
    private CameraManager mCameraManager;
    private String mCameraId;
    private final String TAG = Utils.TAG+this.getClass().getSimpleName() ;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_demo);

        success = findViewById(R.id.bt_ok);
        failed  = findViewById(R.id.bt_failed);
        success.setOnClickListener( this );
        failed.setOnClickListener( this );
        openFlash();

    }
    private String getCameraId() throws CameraAccessException {
        String[] ids = mCameraManager.getCameraIdList();
        for (String id : ids) {
            CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
            Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
            if (flashAvailable != null && flashAvailable
                    && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                return id;
            }
        }
        return null;
    }
    private void openFlash() {
        Log.d(TAG, "openFlash: ");
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = getCameraId();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            Log.e(TAG, "Couldn't set torch mode", e);
        }
    }
    private void closeFlash() {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }
        } catch (Exception e) {
            Log.e(TAG, "Couldn't set torch mode", e);
        }
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
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Log.d(TAG, "onDestroy: ");
        closeFlash();
        super.onDestroy();
    }
}
