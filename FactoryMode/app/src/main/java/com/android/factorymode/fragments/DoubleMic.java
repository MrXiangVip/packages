package com.android.factorymode.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

//import android.media.AudioSystem;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.android.factorymode.R;
import com.android.factorymode.widget.VUMeter;

import java.io.File;
import java.io.IOException;

public class DoubleMic extends Fragment implements View.OnClickListener {

    Handler h;
    private Button mBtMicFailed;
    private Button mBtMicOk;
    private Button mBtSpkFailed;
    private Button mBtSpkOk;
    boolean mMicClick = false;
    private MediaPlayer mPlayer;
    private MediaPlayer mPlayer2;
    private Button mRecord;
    private Button mRecord2;
    private MediaRecorder mRecorder;
    private MediaRecorder mRecorder2;
    SharedPreferences mSp;
    boolean mSpkClick = false;
    VUMeter mVUMeter;
    Runnable ra;

    private AudioManager mAudioManager;

    class MicRecorder1 implements Runnable {
        public void run() {
            mVUMeter.invalidate();
            h.postDelayed(this, 100L);
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.micrecorder, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mRecord = (Button) view.findViewById(R.id.mic_bt_start);
        this.mRecord.setOnClickListener(this);
        this.mRecord2 = (Button) view.findViewById(R.id.mic2_bt_start);
        this.mRecord2.setOnClickListener(this);
        this.mVUMeter = (VUMeter) view.findViewById(R.id.uvMeter);
        /* jacky add start */
        view.findViewById(R.id.bt_ok).setOnClickListener(this);
        view.findViewById(R.id.bt_failed).setOnClickListener(this);
        /* end */

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.h = new Handler();
        this.ra = new MicRecorder1();
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!validateMicAvailability() && !mMicClick) {
            Log.v("xxx", "MIC is inavailable");
            Toast toast = Toast.makeText(getActivity(), R.string.mic_inavailable, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 800);
            toast.show();
            this.mRecord.setEnabled(false);
            this.mRecord2.setEnabled(false);
        } else {
            mMicClick = true;
        }
    }

    @Override
    public void onClick(View paramView) {
        SharedPreferences localSharedPreferences = this.mSp;
        String str1 = Environment.getExternalStorageState();
        String str2 = "mounted";

        if (paramView.getId() == this.mRecord.getId()) {
            Intent mIntent = new Intent();
            mIntent.setAction("com.custom.mic.board");
            getActivity().sendBroadcast(mIntent);
            if (str1.equals(str2)) {
                if ((this.mRecord.getTag() != null)
                        && (this.mRecord.getTag().equals("ing")))
                    stopAndSave();
                else
                    start();
            } else {
                this.mRecord.setText(R.string.sdcard_tips_failed);
            }
            return;
        }

        if (paramView.getId() == this.mRecord2.getId()) {
            Intent mIntent = new Intent();
            mIntent.setAction("com.custom.mic.board");
            getActivity().sendBroadcast(mIntent);
            if (str1.equals(str2)) {
                if ((this.mRecord2.getTag() != null)
                        && (this.mRecord2.getTag().equals("ing")))
                    stopAndSave2();
                else
                    start2();
            } else {
                this.mRecord2.setText(R.string.sdcard_tips_failed);
            }
            return;
        }
        /*jacky add start*/
        switch (paramView.getId()) {
            case R.id.bt_ok:
//                Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_name, "success");
//                finish();
                break;
            case R.id.bt_failed:
//                Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_name, "failed");
//                finish();
                break;

            default:
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean validateMicAvailability() {
        Boolean available = true;

        AudioRecord recorder =
                new AudioRecord(MediaRecorder.AudioSource.MIC, 44100,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_DEFAULT, 44100);

        try{
            if(recorder.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED ){
                available = false;

            }
            recorder.startRecording();
            if(recorder.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING){
                recorder.stop();
                available = false;
            }
            recorder.stop();
        } finally{
            recorder.release();
            recorder = null;
        }
        return available;
    }

    private void start() {
        h.post(ra);
        if (this.mPlayer != null)
            this.mPlayer.stop();
        if (!Environment.getExternalStorageState().equals("mounted"))
            this.mRecord.setText(R.string.sdcard_tips_failed);
        try {
            mRecord2.setEnabled(false);
            this.mRecorder = new MediaRecorder();
            this.mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            this.mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            this.mRecorder.setAudioChannels(2);
            this.mVUMeter.setRecorder(this.mRecorder);
            StringBuilder localStringBuilder = new StringBuilder();
            String str = null;
            File localFile = Environment.getExternalStorageDirectory();
            localStringBuilder.append(localFile).append(File.separator)
                    .append("test.mp3");
            str = localStringBuilder.toString();
            if (!new File(str).exists())
                new File(str).createNewFile();
            this.mRecorder.setOutputFile(str);
            this.mRecorder.prepare();
            this.mRecorder.start();
            this.mRecord.setTag("ing");
            this.mRecord.setText(R.string.Mic_stop);
//            AudioSystem.setParameters("SET_MIC_CHOOSE=1");//only main mic
            mAudioManager.setParameters("SET_MIC_CHOOSE=1");
        } catch (Exception localException) {
            String str3 = localException.getMessage();
            Toast.makeText(getActivity(), str3, Toast.LENGTH_LONG);
            this.mRecord.setTag("ing");
        }
    }

    private void start2() {
        this.h.post(this.ra);
        if (this.mPlayer2 != null)
            this.mPlayer2.stop();
        if (!Environment.getExternalStorageState().equals("mounted"))
            this.mRecord2.setText(R.string.sdcard_tips_failed);
        try {
            mRecord.setEnabled(false);
            this.mRecorder2 = new MediaRecorder();
            this.mRecorder2.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.mRecorder2.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            this.mRecorder2.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            this.mRecorder2.setAudioChannels(2);
            this.mVUMeter.setRecorder(this.mRecorder2);
            StringBuilder localStringBuilder = new StringBuilder();
            String str = null;
            File localFile = Environment.getExternalStorageDirectory();
            localStringBuilder.append(localFile).append(File.separator)
                    .append("test.mp3");
            str = localStringBuilder.toString();
            if (!new File(str).exists())
                new File(str).createNewFile();
            this.mRecorder2.setOutputFile(str);
            this.mRecorder2.prepare();
            this.mRecorder2.start();
            this.mRecord2.setTag("ing");
            this.mRecord2.setText(R.string.Mic_stop);
//            AudioSystem.setParameters("SET_MIC_CHOOSE=2");//only Ref mic
            mAudioManager.setParameters("SET_MIC_CHOOSE=2");
        } catch (Exception localException) {
            String str3 = localException.getMessage();
            Toast.makeText(getActivity(), str3, Toast.LENGTH_SHORT);
            this.mRecord2.setTag("ing");
        }
    }


    private void stopAndSave() {
        this.h.removeCallbacks(this.ra);
        if(mRecorder!=null){
            try {
                this.mRecorder.setOnErrorListener(null);
                this.mRecorder.setOnInfoListener(null);
                this.mRecorder.setPreviewDisplay(null);
                this.mRecorder.stop();
            } catch (IllegalStateException e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
                this.mRecorder = null;
                this.mRecorder = new MediaRecorder();

            }catch (RuntimeException e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
            }catch (Exception e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
            }
            this.mRecorder.release();
            this.mRecorder = null;
            this.mRecord.setText(R.string.Mic_start);
            this.mRecord.setTag("");
            this.mVUMeter.SetCurrentAngle(0);
            this.mVUMeter.setRecorder(null);
            try {
                MediaPlayer localMediaPlayer = new MediaPlayer();
                this.mPlayer = localMediaPlayer;
                this.mPlayer.setDataSource("/sdcard/test.mp3");
                this.mPlayer.prepare();
                this.mPlayer.start();
                mRecord2.setEnabled(true);
                Log.d("liuwuchuan","stopAndSave");
            } catch (IllegalArgumentException localIllegalArgumentException) {
                localIllegalArgumentException.printStackTrace();
            } catch (IllegalStateException localIllegalStateException) {
                localIllegalStateException.printStackTrace();
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
        }
        mMicClick = false;

    }

    private void stopAndSave2() {
        this.h.removeCallbacks(this.ra);
        if(mRecorder2!=null){
            try {
                this.mRecorder2.setOnErrorListener(null);
                this.mRecorder2.setOnInfoListener(null);
                this.mRecorder2.setPreviewDisplay(null);
                this.mRecorder2.stop();
            } catch (IllegalStateException e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
                this.mRecorder2 = null;
                this.mRecorder2 = new MediaRecorder();

            }catch (RuntimeException e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
            }catch (Exception e) {
                // TODO: handle exception
                Log.i("Exception", Log.getStackTraceString(e));
            }

            this.mRecorder2.release();
            this.mRecorder2 = null;
            this.mRecord2.setText(R.string.Mic_start2);
            this.mRecord2.setTag("");
            this.mVUMeter.SetCurrentAngle(0);
            this.mVUMeter.setRecorder(null);
            try {
                MediaPlayer localMediaPlayer = new MediaPlayer();
                this.mPlayer2 = localMediaPlayer;
                this.mPlayer2.setDataSource("/sdcard/test.mp3");
                this.mPlayer2.prepare();
                this.mPlayer2.start();
                mRecord.setEnabled(true);
            } catch (IllegalArgumentException localIllegalArgumentException) {
                localIllegalArgumentException.printStackTrace();
            } catch (IllegalStateException localIllegalStateException) {
                localIllegalStateException.printStackTrace();
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
        }
        mMicClick = false;
    }

}
