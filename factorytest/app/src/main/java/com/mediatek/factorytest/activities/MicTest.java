package com.mediatek.factorytest.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;
import com.mediatek.factorytest.widget.VoiceLineView;

import java.io.File;
import java.io.IOException;

public class MicTest extends Activity implements View.OnClickListener{

    private Button      okBtn;
    private Button      failBtn;

    private ToggleButton        controlBtn;
    private ToggleButton        playBtn;
    private MediaRecorder       mainMediaRecorder;
    private MediaRecorder       subMediaRecorder;

    private MediaPlayer         mediaPlayer;
    private File                file;
    private VoiceLineView       mainVoiceLineView;
    private VoiceLineView       subVoiceLineVIew;
    private String TAG="DemoTest.";
    private int BASE = 1;

    private  final int  START_RECORDING     =0;     //开始录音
    private  final int  UPDATE_VOICE_LINE   =1;     //更新波形图
    private  final int  STOP_RECORDING      =2;     //停止录音
    private  final int  START_PLAYING       =3;     //播放录音
    private  final int  STOP_PLAYING       =4;     //停止播放录音
    private  final int  DE_INIT             =5;     //清理

    private  HandlerThread  mainHandlerThread;
    private  HandlerThread  subHandlerThread;
    private  Handler        mainHandler;
    private  Handler        subHandler;

    private double     mainDecibel=0; // 主麦分贝
    private double     subDecibel =0; // 副麦分贝


    private class MainRecorderHandler extends  Handler {
        MainRecorderHandler(Looper looper) {
            super(looper);
        }
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_RECORDING:
                    start_main_recorder();
                    break;
                case UPDATE_VOICE_LINE:
                    update_main_voiceline();
                    break;
                case STOP_RECORDING:
                    stop_main_recorder();
                    break;
                case START_PLAYING:
                    start_main_player();
                    break;
                case STOP_PLAYING:
                    stop_main_player();
                    break;
                case DE_INIT:
                    break;
                default:
                    break;
            }
        }
    }
    private class SubRecorderHandler extends  Handler {
        SubRecorderHandler(Looper looper) {
            super(looper);
        }
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_RECORDING:
                    start_sub_recorder();
                    break;
                case UPDATE_VOICE_LINE:
                    update_sub_voiceline();
                    break;
                case STOP_RECORDING:
                    stop_sub_recorder();
                    break;
                case START_PLAYING:
                    start_sub_player();
                    break;
                case STOP_PLAYING:
                    stop_sub_player();
                    break;
                case DE_INIT:
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView( R.layout.activity_mic);

        okBtn = findViewById( R.id.bt_ok);
        failBtn = findViewById( R.id.bt_failed);
        controlBtn = findViewById( R.id.recorder_controller );
        playBtn = findViewById( R.id.player_controller );

        okBtn.setOnClickListener(this);
        failBtn.setOnClickListener( this );
        controlBtn.setOnClickListener( this );
        playBtn.setOnClickListener( this );

        mainVoiceLineView = (VoiceLineView) findViewById(R.id.voicLine);
        subVoiceLineVIew = (VoiceLineView) findViewById(R.id.voicLine2);

        initHanlderThread();
//      初始化按钮状态
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //录音时  播放按钮不可用, 录音按钮可用
                playBtn.setEnabled( false );
            }
        });
    }

    private void initHanlderThread( ){
        Log.d(TAG, Thread.currentThread().getId()+" initHanlderThread ");
        mainHandlerThread = new HandlerThread("main");
        subHandlerThread  = new HandlerThread("sub");
        mainHandlerThread.start();
        subHandlerThread.start();
        mainHandler = new MainRecorderHandler( mainHandlerThread.getLooper());
        subHandler = new SubRecorderHandler( subHandlerThread.getLooper());

        mainHandler.sendEmptyMessage( START_RECORDING );
        subHandler.sendEmptyMessage( START_RECORDING );
    }
    @Override
    public void onClick(View view) {
        if( view.equals( okBtn )){
            mainHandler.removeCallbacksAndMessages(null);
            subHandler.removeCallbacksAndMessages(null);
            setResult(Utils.SUCCESS_CODE);
            finish();
        }else if( view.equals( failBtn )){
            mainHandler.removeCallbacksAndMessages(null);
            subHandler.removeCallbacksAndMessages(null);
            setResult( Utils.FAILED_CODE);
            finish();
        }
        if( view.equals( controlBtn) ){
            if( controlBtn.isChecked()  ){
                mainHandler.sendEmptyMessage( START_RECORDING);
                subHandler.sendEmptyMessage( START_RECORDING);
            }else{
                mainHandler.sendEmptyMessage( STOP_RECORDING  );
                subHandler.sendEmptyMessage( STOP_RECORDING  );
            }
        }
        if( view.equals( playBtn ) ){
            if( playBtn.isChecked() ){
                mainHandler.sendEmptyMessage( STOP_PLAYING );
                subHandler.sendEmptyMessage( STOP_PLAYING );
            }else{
                mainHandler.sendEmptyMessage( START_PLAYING  );
                subHandler.sendEmptyMessage( START_PLAYING  );
            }
        }
    }

    @Override
    protected void onDestroy() {
        if( mainMediaRecorder != null ){
            mainMediaRecorder.release();
            mainMediaRecorder = null;
        }
        if( subMediaRecorder != null ){
            subMediaRecorder.release();
            subMediaRecorder = null;
        }
        if( mediaPlayer !=null ){
            mediaPlayer.stop();
        }
        super.onDestroy();
    }

    public void start_main_recorder( ){
        Log.d(TAG, Thread.currentThread().getId()+" start main recorder ");
        if( mediaPlayer != null ){
            mediaPlayer.stop();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    playBtn.setEnabled( false);
                }
            });
        }
        if (mainMediaRecorder == null){
            mainMediaRecorder = new MediaRecorder();
        }
        mainMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mainMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mainMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mainMediaRecorder.setAudioChannels(2);
        file = new File(getExternalFilesDir(null).getAbsolutePath()+File.separator+"hello.mp3");
        Log.d(TAG, " record file path: "+file.getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mainMediaRecorder.setOutputFile(file.getAbsolutePath());
        mainMediaRecorder.setMaxDuration(1000 * 60 * 10);
        try {
            mainMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainMediaRecorder.start();
        mainHandler.sendEmptyMessage( UPDATE_VOICE_LINE );
    }

    public void start_sub_recorder( ){
        Log.d(TAG, Thread.currentThread().getId()+" start sub recorder ");
        if( mediaPlayer != null ){
            mediaPlayer.stop();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    playBtn.setEnabled( false );
                }
            });
        }
        if (subMediaRecorder == null){
            subMediaRecorder = new MediaRecorder();
        }
        subMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        subMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        subMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        subMediaRecorder.setAudioChannels(2);
        file = new File(getExternalFilesDir(null).getAbsolutePath()+File.separator+"hello.mp3");
        Log.d(TAG, " record file path: "+file.getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        subMediaRecorder.setOutputFile(file.getAbsolutePath());
        subMediaRecorder.setMaxDuration(1000 * 60 * 10);
        try {
            subMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        subMediaRecorder.start();
        subHandler.sendEmptyMessage( UPDATE_VOICE_LINE );
    }

    public void update_main_voiceline( ){
        Log.d(TAG, Thread.currentThread().getId()+ "  update_main_voiceline " );
        if( controlBtn.isChecked() ){
            double ratio = (double) mainMediaRecorder.getMaxAmplitude() / BASE;
            mainDecibel = 0;// 分贝
            //默认的最大音量是100,可以修改，但其实默认的，在测试过程中就有不错的表现
            //你可以传自定义的数字进去，但需要在一定的范围内，比如0-200，就需要在xml文件中配置maxVolume
            //同时，也可以配置灵敏度sensibility
            if (ratio > 1)
                mainDecibel = 20 * Math.log10(ratio);
            //只要有一个线程，不断调用这个方法，就可以使波形变化
            //主要，这个方法必须在ui线程中调用
            Log.d(TAG, Thread.currentThread().getId()+" 分贝值: "+mainDecibel);
            if( mainDecibel >10 ){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainVoiceLineView.setVolume((int) (mainDecibel));
                    }
                });
            }
            mainHandler.sendEmptyMessageDelayed( UPDATE_VOICE_LINE,1000 );
        }
    }

    public void update_sub_voiceline( ){
        Log.d(TAG, Thread.currentThread().getId()+ "  update_sub_voiceline " );
        if( controlBtn.isChecked() ){
            double ratio = (double) subMediaRecorder.getMaxAmplitude() / BASE;
            subDecibel = 0;// 分贝
            //默认的最大音量是100,可以修改，但其实默认的，在测试过程中就有不错的表现
            //你可以传自定义的数字进去，但需要在一定的范围内，比如0-200，就需要在xml文件中配置maxVolume
            //同时，也可以配置灵敏度sensibility
            if (ratio > 1)
                subDecibel = 20 * Math.log10(ratio);
            //只要有一个线程，不断调用这个方法，就可以使波形变化
            //主要，这个方法必须在ui线程中调用
            Log.d(TAG, Thread.currentThread().getId()+" 分贝值: "+subDecibel);
            if( subDecibel >10 ){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        subVoiceLineVIew.setVolume((int) (subDecibel));
                    }
                });
            }
            subHandler.sendEmptyMessageDelayed( UPDATE_VOICE_LINE,1000 );
        }
    }

    public void stop_main_recorder ( ){
        Log.d(TAG, Thread.currentThread().getId()+" stop_main_recorder");
        if( mainMediaRecorder!= null ){
//            停止录制 释放资源
            mainMediaRecorder.stop();
            mainMediaRecorder.release();
            mainMediaRecorder = null;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //停止录音后，播放按钮可用，录音按钮可用
                playBtn.setEnabled(true);
            }
        });


    }
    public void stop_sub_recorder ( ){
        Log.d(TAG, Thread.currentThread().getId()+" stop_sub_recorder");
        if( subMediaRecorder!= null ){
//            停止录制 释放资源
            subMediaRecorder.stop();
            subMediaRecorder.release();
            subMediaRecorder = null;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playBtn.setEnabled(true);
            }
        });
    }
    public void start_main_player( ){
        Log.d(TAG, "stop_main_player");
        try{
            if( mainMediaRecorder != null ){
                mainMediaRecorder.stop();
                mainMediaRecorder.release();
                mainMediaRecorder =null;

            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource( file.getAbsolutePath() );
            Log.d(TAG, " "+file.getAbsolutePath());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(TAG, "onCompletion");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playBtn.setChecked( true);
                        }
                    });
                }
            });
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void start_sub_player( ){
        Log.d(TAG, "play_sub_recorder");
        try{
            if( subMediaRecorder != null ){
                subMediaRecorder.stop();
                subMediaRecorder.release();
                subMediaRecorder =null;

            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource( file.getAbsolutePath() );
            Log.d(TAG, " "+file.getAbsolutePath());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(TAG, "onCompletion");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playBtn.setChecked( true);
                        }
                    });
                }
            });
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    void stop_main_player( ){
        Log.d(TAG, "stop main player");
        if( mediaPlayer != null ){
            mediaPlayer.stop();
        }
    }
    void stop_sub_player( ){
        Log.d(TAG, "stop sub player");
        if( mediaPlayer != null ){
            mediaPlayer.stop();
        }
    }
// 拦截back键
    public void onBackPressed(){
        return;
    }
}
