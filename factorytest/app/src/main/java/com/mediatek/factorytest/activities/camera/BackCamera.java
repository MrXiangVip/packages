package com.mediatek.factorytest.activities.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;

import java.io.IOException;

public class BackCamera extends Activity implements View.OnClickListener {

    private Button success;
    private Button  failed;

    private SurfaceHolder surfaceHolder;
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private final String TAG = Utils.TAG+this.getClass().getSimpleName() ;

    private int findBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }

    public Camera getCameraInstance(int cameraID) {
        Camera camera = null;
        try {
            camera = Camera.open(cameraID); // attempt to get a Camera instance
        } catch (Exception e) {
            //Camera is notavailable (in use or does not exist)
        }
        return camera; // returns nullif camera is unavailable
    }

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_camera);
        success = findViewById(R.id.bt_ok);
        failed  = findViewById(R.id.bt_failed);
        success.setOnClickListener( this );
        failed.setOnClickListener( this );
        mSurfaceView = (SurfaceView) findViewById(R.id.mainSurfaceView);

    }
    protected  void onResume( ){
        super.onResume();
        Log.d(TAG, "onResume ");
        mCamera = getCameraInstance( findBackCamera());
        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback( mSurfaceHolderCallback );
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
        }catch (Exception e){
            Log.d(TAG, "initFrontCamera: "+e.toString());
        }
        if(mCamera != null) {
            try {
//                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
            } catch (RuntimeException e) {
                Log.d(TAG, "takePhotos: "+e.toString());
                Log.d(TAG, "takePhotos: "+Log.getStackTraceString(new Throwable()));

            }
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
    public void takeCapture(View view ) {
        try{
            mCamera.takePicture(null, null, pictureCallback);
        }catch (Exception e){
            Log.d(TAG, "takeCapture: ", new Throwable());
        }

    }

    final SurfaceHolder.Callback mSurfaceHolderCallback = new SurfaceHolder.Callback() {
        /** The camera device to use, or null if we haven't yet set a fixed surface size. */

        /** Whether we received a change callback after setting our fixed surface size. */

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // This is called every time the surface returns to the foreground

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i(TAG, "Surface destroyed");
            if (mCamera != null) {
                // 停止预览
                mCamera.stopPreview();
                // 释放摄像头
                mCamera.release();
                mCamera = null;
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            // On the first invocation, width and height were automatically set to the view's size
            Log.d(TAG, "surfaceChanged: "+width+" "+height);
            Log.i(TAG, "Surface created");
//            mCamera.setDisplayOrientation( 90 );
            // 重新开始预览
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d(TAG, "预览失败");
            }
        }
    };

    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            // 停止预览
            Log.d(TAG, "onPictureTaken: ");
            success.setEnabled( true );
            mCamera.stopPreview();
            mCamera.startPreview();
        }

    };
}
