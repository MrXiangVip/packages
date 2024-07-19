package com.android.factorymode.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;

import com.android.factorymode.R;
import com.android.factorymode.widget.VoiceLineView;

import java.io.File;
import java.io.IOException;

public class MicPhoneTest extends Fragment implements  View.OnClickListener{

    private Button      okBtn;
    private Button      failBtn;

    private ToggleButton    recordBtn;
    private ToggleButton    playBtn;
    private MediaRecorder   mediaRecorder;

    private MediaPlayer mediaPlayer;
    private File file;
    private VoiceLineView       voiceLineView;
//    private VoiceLineView       voiceLineView2;
    private String TAG="DemoTest.";
    private int BASE = 1;
    SharedPreferences localSharedPreferences;

    private AudioManager mAudioManager;

    private  final int  START_RECORDING     =0;
    private  final int  UPDATE_VOICE_LINE   =1;
    private  final int  STOP_RECORDING      =2;
    private  final int  PLAY_RECORDER       =3;
    private  final int  STOP_RECORDER       =4;
    private  final int  DE_INIT             =5;

    private AudioManager localAudioManager = null;
    private int cur_volume;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_left_mic, container, false);
        return  view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        okBtn = view.findViewById( R.id.bt_ok);
        failBtn = view.findViewById( R.id.bt_failed);
        recordBtn = view.findViewById( R.id.recorder_controller );
        playBtn = view.findViewById( R.id.player_controller );

        okBtn.setOnClickListener(this);
        failBtn.setOnClickListener( this );
        recordBtn.setOnClickListener( this );
        playBtn.setOnClickListener( this );

        voiceLineView = (VoiceLineView) view.findViewById(R.id.voicLine);
//        voiceLineView2 = (VoiceLineView) view.findViewById(R.id.voicLine2);
        localSharedPreferences = getActivity().getSharedPreferences("FactoryMode", 0);

//        handler.sendEmptyMessage( START_RECORDING );
        okBtn.setEnabled(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        Utils.setWindowsFullScreen(getWindow());
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    public void onClick(View view) {
        if( view.equals( okBtn )){
            handler.removeCallbacksAndMessages(null);
//            Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_name, "success");
//            finish();
        }else if( view.equals( failBtn )){
            handler.removeCallbacksAndMessages(null);
//            Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_name, "failed");
//            finish();
        }
        if( view.equals( recordBtn) ){
            if( recordBtn.isChecked()  ){
                handler.sendEmptyMessage( STOP_RECORDING  );
            }else{
                handler.sendEmptyMessage( START_RECORDING);
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
    public void onDestroy() {
        if( mediaRecorder != null ){
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if( mediaPlayer !=null ){
            mediaPlayer.stop();
        }
        if(localAudioManager != null)
            localAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, cur_volume, 0);
        super.onDestroy();
    }

    public void start_recording( ){
//        mAudioManager.setParameters("SET_MIC_CHOOSE=1");

        Log.d(TAG, "start_recording");
        if( mediaPlayer != null ){
            mediaPlayer.stop();
            playBtn.setEnabled( false );
        }
        if (mediaRecorder == null){
            mediaRecorder = new MediaRecorder();
        }
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(2);//
//        mediaRecorder.setAudioEncodingBitRate(320000);
//        mediaRecorder.setAudioSamplingRate(44100);

        file = new File(getActivity().getExternalFilesDir(null).getAbsolutePath()+File.separator+"hello.mp3");
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

        handler.sendEmptyMessageDelayed( UPDATE_VOICE_LINE,300 );
        okBtn.setEnabled(false);
    }

    public void update_voiceline( ){
        Log.d(TAG, "update_voiceline");
        if(   null != mediaRecorder){
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
            }
            handler.sendEmptyMessageDelayed( UPDATE_VOICE_LINE,100 );
        }
    }

    public void stop_recording( ){
        Log.d(TAG, "stop_recording");
        if( mediaRecorder!= null ){
            try {
                mediaRecorder.stop();
            } catch (RuntimeException e) {
                Log.w("Recorder", "Catch RuntimeException on MediaRecorder.stop() due to a call " +
                        "immediately after MediaRecorder.start().");
            }
            mediaRecorder.release();
            mediaRecorder = null;
        }
        handler.removeMessages(UPDATE_VOICE_LINE);
        playBtn.setEnabled(true);
        okBtn.setEnabled(false);
    }
    public void play_recorder( ){
        Log.d(TAG, "play_recorder");
        try{
            if( mediaRecorder != null ){
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder =null;

            }
            localAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
            localAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            cur_volume = localAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            int max_volume = localAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            localAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max_volume, 0);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource( file.getAbsolutePath() );
            mediaPlayer.setVolume(1.0f,1.0f);
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
            okBtn.setEnabled(true);
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
