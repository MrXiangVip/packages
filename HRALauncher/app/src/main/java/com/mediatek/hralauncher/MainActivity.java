package com.mediatek.hralauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView video;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        video=findViewById(R.id.videov);
        video.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video2);
        MediaController mediaController=new MediaController(MainActivity.this);
        video.setMediaController(mediaController);

        imageView=this.findViewById(R.id.imagev);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });
        video.start();

    }


    public void play(){
        if(video.isPlaying()&&video!=null){
            imageView.setImageResource(android.R.drawable.ic_media_pause);
            video.pause();
            return;
        }
        imageView.setImageResource(android.R.drawable.ic_media_play);
        video.start();

    }


}