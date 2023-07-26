package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.mediatek.hralauncher.R;

public class AiSubjectViewHolder extends  BaseHolder implements View.OnClickListener{

    public static final int SUBJECT_MATH = 0;
    public static final int SUBJECT_CHINESE = 1;
    public static final int SUBJECT_ENGLISH = 2;
    public static final int SUBJECT_PHYSICAL = 3;
    int subject;

    AppCompatImageView module_jdcs;
    AppCompatImageView module_fxgg;
    AppCompatImageView module_ctb;
    ConstraintLayout   left_custom_container;
    VideoView          mVideoView;
    MediaController    mediaController;
    private String TAG="AiSubjectViewHolder.";

    public AiSubjectViewHolder(Context mContext, int mSubject) {
        super(mContext);
        subject = mSubject;
    }

    @Override
    int getLayoutId() {
        return R.layout.viewholder_ai_subject;
    }

    @Override
    void initView(Context context, View view) {

        module_jdcs = view.findViewById( R.id.iv_jdcs);
        module_fxgg = view.findViewById( R.id.iv_fxgg);
        module_ctb = view.findViewById( R.id.iv_ctb);
        left_custom_container = view.findViewById(R.id.left_custom_container);
        mVideoView = view.findViewById(R.id.videov);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        updateStatusBySubject(subject);
        updateVideoBySubject( subject );
    }
    private void updateStatusBySubject(int subject){
        switch (subject ){
            case  SUBJECT_MATH:
                updateLeftContainer(R.mipmap.amodule_big_green_bg, subject);
                break;
            case  SUBJECT_CHINESE:
                updateLeftContainer(R.mipmap.amodule_big_orange_bg, subject);
                break;
            case  SUBJECT_ENGLISH:
                updateLeftContainer(R.mipmap.amodule_big_purple_bg, subject);

                break;
            case  SUBJECT_PHYSICAL:
                updateLeftContainer(R.mipmap.amodule_big_blue_bg, subject);
                break;
            default:
                break;
        }
    }
    private void  updateLeftContainer(int  bgId,  int subject) {
        left_custom_container.setBackgroundResource(bgId );
    }

    private void updateVideoBySubject( int subject ){
        Log.d(TAG, "updateVideoBySubject "+subject);
        switch (subject ) {
            case  SUBJECT_MATH:
                mVideoView.setVideoPath("android.resource://"+context.getPackageName()+"/"+R.raw.video1);
                break;
            case  SUBJECT_CHINESE:
                mVideoView.setVideoPath("android.resource://"+context.getPackageName()+"/"+R.raw.video2);
                break;
            case  SUBJECT_ENGLISH:
                mVideoView.setVideoPath("android.resource://"+context.getPackageName()+"/"+R.raw.video3);
                break;
            case  SUBJECT_PHYSICAL:
                mVideoView.setVideoPath("android.resource://"+context.getPackageName()+"/"+R.raw.video4);
                break;
            default:
                break;
        }
        mediaController=new MediaController(context);
//        mediaController.setAnchorView( mVideoView);
        mVideoView.setMediaController(mediaController);
        mediaController.setMediaPlayer( mVideoView );
    }

    @Override
    public void onScrollIn() {
        super.onScrollIn();
        Log.d(TAG,"onScrollIn current subject : "+subject);
        mVideoView.start();
        mediaController.hide();
    }

    @Override
    public void onScrollOut() {
        super.onScrollOut();
        Log.d(TAG,"onScrollOut current : "+subject);
        if(mVideoView.isPlaying() && mVideoView!=null) {
            mVideoView.pause();
            mediaController.hide();
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick ");
    }
}
