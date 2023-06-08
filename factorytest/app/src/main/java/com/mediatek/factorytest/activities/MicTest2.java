package com.mediatek.factorytest.activities;

import static java.lang.Thread.sleep;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
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

import androidx.core.app.ActivityCompat;

import com.mediatek.factorytest.R;
import com.mediatek.factorytest.utils.Utils;
import com.mediatek.factorytest.widget.VoiceLineView;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MicTest2 extends Activity implements View.OnClickListener {

    private Button okBtn;
    private Button failBtn;

    private ToggleButton recordBtn;
    private ToggleButton playBtn;


    private File file;
    private VoiceLineView mainVoiceLineView;
    private VoiceLineView subVoiceLineView;
    private String TAG = "DemoTest.";
    private int BASE = 1;

    private final int START_RECORDING = 0;     //开始录音
    private final int UPDATE_VOICE_LINE = 1;     //更新波形图
    private final int STOP_RECORDING = 2;     //停止录音
    private final int START_PLAYING = 3;     //播放录音
    private final int STOP_PLAYING = 4;     //停止播放录音
    private final int DE_INIT = 5;     //清理

    private HandlerThread mainHandlerThread;
    private HandlerThread subHandlerThread;
    private Handler mainHandler;
    private Handler subHandler;

    private double mainDecibel = 0; // 主麦分贝
    private double subDecibel = 0; // 副麦分贝

    private AudioRecord mainAudioRecord;
    private AudioRecord subAudioRecord;
    private final int mSamplingRate = 48000;
    private final int mChannelConfig = AudioFormat.CHANNEL_IN_MONO;
    private final int mLeftChannel  = AudioFormat.CHANNEL_IN_LEFT;
    private final int mRightChannel = AudioFormat.CHANNEL_IN_RIGHT;

    private final int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private int     minRecordBuffSizeInBytes;
    private class MainRecorderHandler extends Handler {
        MainRecorderHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_RECORDING:
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            try{
                                start_main_recorder();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
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

    private class SubRecorderHandler extends Handler {
        SubRecorderHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_RECORDING:
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                start_sub_recorder();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                    break;
//                case UPDATE_VOICE_LINE:
//                    update_sub_voiceline();
//                    break;
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

        setContentView(R.layout.activity_mic);

        okBtn = findViewById(R.id.bt_ok);
        failBtn = findViewById(R.id.bt_failed);
        recordBtn = findViewById(R.id.recorder_controller);
        playBtn = findViewById(R.id.player_controller);

        okBtn.setOnClickListener(this);
        failBtn.setOnClickListener(this);
        recordBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);

        mainVoiceLineView = (VoiceLineView) findViewById(R.id.voicLine);
        subVoiceLineView = (VoiceLineView) findViewById(R.id.voicLine2);

        initHanlderThread();
//      初始化按钮状态
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //录音时  播放按钮不可用, 录音按钮可用
                playBtn.setEnabled(false);
            }
        });
    }

    private void initHanlderThread() {
        Log.d(TAG, Thread.currentThread().getId() + " initHanlderThread ");
        mainHandlerThread = new HandlerThread("main");
        mainHandlerThread.start();
        mainHandler = new MainRecorderHandler(mainHandlerThread.getLooper());
        mainHandler.sendEmptyMessage(START_RECORDING);

        subHandlerThread = new HandlerThread("sub");
        subHandlerThread.start();
        subHandler = new SubRecorderHandler(subHandlerThread.getLooper());
        subHandler.sendEmptyMessage(START_RECORDING);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(okBtn)) {
            mainHandler.removeCallbacksAndMessages(null);
            subHandler.removeCallbacksAndMessages(null);
            setResult(Utils.SUCCESS_CODE);
            finish();
        } else if (view.equals(failBtn)) {
            mainHandler.removeCallbacksAndMessages(null);
            subHandler.removeCallbacksAndMessages(null);
            setResult(Utils.FAILED_CODE);
            finish();
        }
        if (view.equals(recordBtn)) {
            if (recordBtn.isChecked()) {
                mainHandler.sendEmptyMessage(START_RECORDING);
                subHandler.sendEmptyMessage(START_RECORDING);
            } else {
                mainHandler.sendEmptyMessage(STOP_RECORDING);
                subHandler.sendEmptyMessage(STOP_RECORDING);
            }
        }
        if (view.equals(playBtn)) {
            if (playBtn.isChecked()) {
                mainHandler.sendEmptyMessage(STOP_PLAYING);
                subHandler.sendEmptyMessage(STOP_PLAYING);
            } else {
                mainHandler.sendEmptyMessage(START_PLAYING);
                subHandler.sendEmptyMessage(START_PLAYING);
            }
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    public void start_main_recorder() throws IOException, InterruptedException {
        Log.d(TAG, Thread.currentThread().getId() + " start main recorder ");
//      创建文件
        file = new File(getExternalFilesDir(null).getAbsolutePath(), "hello.mp3");
        Log.d(TAG, " record file path: " + file.getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(bos);
        minRecordBuffSizeInBytes = AudioRecord.getMinBufferSize(mSamplingRate,
                mLeftChannel, mAudioFormat);
        short[] buffer = new short[minRecordBuffSizeInBytes];
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mainAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, mSamplingRate, mLeftChannel,
                mAudioFormat, 2 * minRecordBuffSizeInBytes);
        mainAudioRecord.startRecording();
        while( recordBtn.isChecked() ){
            int bufferRead = mainAudioRecord.read( buffer, 0, minRecordBuffSizeInBytes);
            for(int i=0; i< bufferRead; i++){
                dos.writeShort( buffer[i]);
            }
//          计算分贝值
            update_main_voiceline();
            sleep(100);
        }
        mainAudioRecord.stop();
        mainAudioRecord.release();

        dos.close();
        bos.close();
        fos.close();

    }

    public void start_sub_recorder( ) throws IOException, InterruptedException {
        Log.d(TAG, Thread.currentThread().getId()+" start sub recorder ");

//      创建文件
        file = new File(getExternalFilesDir(null).getAbsolutePath(), "hello.mp3");
        Log.d(TAG, " record file path: " + file.getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream dos = new DataOutputStream(bos);
        minRecordBuffSizeInBytes = AudioRecord.getMinBufferSize(mSamplingRate,
                mRightChannel, mAudioFormat);
        short[] buffer = new short[minRecordBuffSizeInBytes];
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        subAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, mSamplingRate, mRightChannel,
                mAudioFormat, 2 * minRecordBuffSizeInBytes);
        subAudioRecord.startRecording();
        while( recordBtn.isChecked() ){
            int bufferRead = subAudioRecord.read( buffer, 0, minRecordBuffSizeInBytes);
            for(int i=0; i< bufferRead; i++){
                dos.writeShort( buffer[i]);
            }
//          计算分贝值
            update_sub_voiceline();
            sleep(100);
        }
        subAudioRecord.stop();
        subAudioRecord.release();

        dos.close();
        bos.close();
        fos.close();
    }

    public void update_main_voiceline( ){
        Log.d(TAG, Thread.currentThread().getId()+ "  update_main_voiceline " );
        if( recordBtn.isChecked() ){
            //r是实际读取的数据长度，一般而言r会小于buffersize
            short[] buffer = new short[minRecordBuffSizeInBytes];
            int r = mainAudioRecord.read(buffer, 0, minRecordBuffSizeInBytes);
            long v = 0;
            // 将 buffer 内容取出，进行平方和运算
            for (int i = 0; i < buffer.length; i++) {
                v += buffer[i] * buffer[i];
            }
            // 平方和除以数据总长度，得到音量大小。
            double mean = v / (double) r;
            double mainDecibel = 10 * Math.log10(mean);
            Log.d(TAG, Thread.currentThread().getId()+" 分贝值: "+mainDecibel);
            if( mainDecibel >10 ){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainVoiceLineView.setVolume((int) (mainDecibel));
                    }
                });
            }
        }
    }

    public void update_sub_voiceline( ){
        Log.d(TAG, Thread.currentThread().getId()+ "  update_sub_voiceline " );
        if( recordBtn.isChecked() ){
            //r是实际读取的数据长度，一般而言r会小于buffersize
            short[] buffer = new short[minRecordBuffSizeInBytes];
            int r = subAudioRecord.read(buffer, 0, minRecordBuffSizeInBytes);
            long v = 0;
            // 将 buffer 内容取出，进行平方和运算
            for (int i = 0; i < buffer.length; i++) {
                v += buffer[i] * buffer[i];
            }
            // 平方和除以数据总长度，得到音量大小。
            double mean = v / (double) r;
            double subDecibel = 10 * Math.log10(mean);
            Log.d(TAG, Thread.currentThread().getId()+" 分贝值: "+subDecibel);
            if( subDecibel >10 ){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        subVoiceLineView.setVolume((int) (subDecibel));
                    }
                });
            }
        }
    }

    public void stop_main_recorder ( ){
        Log.d(TAG, Thread.currentThread().getId()+" stop_main_recorder");

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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playBtn.setEnabled(true);
            }
        });
    }
    public void start_main_player( ){
        Log.d(TAG, "stop_main_player");


    }
    public void start_sub_player( ){
        Log.d(TAG, "play_sub_recorder");


    }


    void stop_main_player( ){
        Log.d(TAG, "stop main player");

    }
    void stop_sub_player( ){
        Log.d(TAG, "stop sub player");

    }
// 拦截back键
    public void onBackPressed(){
        return;
    }
}
