package com.mediatek.factorytest.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
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
    private MediaRecorder       mediaRecorder;

    private MediaPlayer         mediaPlayer;
    private File                file;
    private VoiceLineView       voiceLineView;
    private VoiceLineView       voiceLineView2;
    private String TAG="DemoTest.";
    private int BASE = 1;

    private  final int  START_RECORDING     =0;
    private  final int  UPDATE_VOICE_LINE   =1;
    private  final int  STOP_RECORDING      =2;
    private  final int  PLAY_RECORDER       =3;
    private  final int  STOP_RECORDER       =4;
    private  final int  DE_INIT             =5;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            switch ( what ) {
                case START_RECORDING:
                    start_recording();
                    break;
                case UPDATE_VOICE_LINE:
                    update_voiceline();
                    break;
                case STOP_RECORDING:
                    stop_recording();
                    break;
                case PLAY_RECORDER:
                    play_recorder();
                    break;
                case STOP_RECORDER:
                    stop_recorder();
                    break;
                case DE_INIT:
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

        setContentView( R.layout.activity_mic);

        okBtn = findViewById( R.id.bt_ok);
        failBtn = findViewById( R.id.bt_failed);
        controlBtn = findViewById( R.id.recorder_controller );
        playBtn = findViewById( R.id.player_controller );

        okBtn.setOnClickListener(this);
        failBtn.setOnClickListener( this );
        controlBtn.setOnClickListener( this );
        playBtn.setOnClickListener( this );

        voiceLineView = (VoiceLineView) findViewById(R.id.voicLine);
        voiceLineView2 = (VoiceLineView) findViewById(R.id.voicLine2);

        handler.sendEmptyMessage( START_RECORDING );
    }

    @Override
    public void onClick(View view) {
        if( view.equals( okBtn )){
            handler.removeCallbacksAndMessages(null);
            setResult(Utils.SUCCESS_CODE);
            finish();
        }else if( view.equals( failBtn )){
            handler.removeCallbacksAndMessages(null);
            setResult( Utils.FAILED_CODE);
            finish();
        }
        if( view.equals( controlBtn) ){
            if( controlBtn.isChecked()  ){
                handler.sendEmptyMessage( START_RECORDING);
            }else{
                handler.sendEmptyMessage( STOP_RECORDING  );
            }
        }
        if( view.equals( playBtn ) ){
            if( playBtn.isChecked() ){
                handler.sendEmptyMessage( STOP_RECORDER );
            }else{
                handler.sendEmptyMessage( PLAY_RECORDER  );
            }
        }
    }

    @Override
    protected void onDestroy() {
        if( mediaRecorder != null ){
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if( mediaPlayer !=null ){
            mediaPlayer.stop();
        }
        super.onDestroy();
    }

    public void start_recording( ){
        Log.d(TAG, "start_recording");
        if( mediaPlayer != null ){
            mediaPlayer.stop();
            playBtn.setEnabled( false );
        }
        if (mediaRecorder == null){
            mediaRecorder = new MediaRecorder();
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setAudioChannels(2);
        file = new File(getExternalFilesDir(null).getAbsolutePath()+File.separator+"hello.mp3");
        Log.d(TAG, " record file path: "+file.getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mediaRecorder.setOutputFile(file.getAbsolutePath());
        mediaRecorder.setMaxDuration(1000 * 60 * 10);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
        handler.sendEmptyMessage( UPDATE_VOICE_LINE );
    }

    public void update_voiceline( ){
        Log.d(TAG, "update_voiceline");
        if( controlBtn.isChecked() ){
            double ratio = (double) mediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            //默认的最大音量是100,可以修改，但其实默认的，在测试过程中就有不错的表现
            //你可以传自定义的数字进去，但需要在一定的范围内，比如0-200，就需要在xml文件中配置maxVolume
            //同时，也可以配置灵敏度sensibility
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
            //只要有一个线程，不断调用这个方法，就可以使波形变化
            //主要，这个方法必须在ui线程中调用
            Log.d(TAG, "分贝值: "+db);
            if( db >10 ){
                voiceLineView.setVolume((int) (db));
                voiceLineView2.setVolume((int) (db));
            }
            handler.sendEmptyMessageDelayed( UPDATE_VOICE_LINE,100 );
        }
    }

    public void stop_recording( ){
        Log.d(TAG, "stop_recording");
        if( mediaRecorder!= null ){
//            停止录制 释放资源
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        playBtn.setEnabled(true);

    }
    public void play_recorder( ){
        Log.d(TAG, "play_recorder");
        try{
            if( mediaRecorder != null ){
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder =null;

            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource( file.getAbsolutePath() );
            Log.d(TAG, " "+file.getAbsolutePath());
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Log.d(TAG, "onCompletion");
                    playBtn.setChecked( true);
                }
            });
            mediaPlayer.prepare();
            mediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void stop_recorder( ){
        Log.d(TAG, "stop_recorder");
        if( mediaPlayer != null ){
            mediaPlayer.stop();
        }
    }
// 拦截back键
    public void onBackPressed(){
        return;
    }
}
