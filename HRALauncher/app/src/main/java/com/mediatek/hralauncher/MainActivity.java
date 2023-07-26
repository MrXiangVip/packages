package com.mediatek.hralauncher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView video;
    ImageView imageView;
    private WebView mWebView;
    private String url ="https://lingsy.oss-cn-beijing.aliyuncs.com/%E6%B5%85%E8%B0%88%E3%80%8A%E8%AF%97%E7%BB%8F%E3%80%8B%E4%B8%AD%E7%94%9F%E5%91%BD%E6%84%8F%E8%B1%A1%E7%9A%84%E6%83%85%E6%84%9F%E5%86%85%E6%B6%B5%E4%B8%8E%E4%BB%B7%E5%80%BC_%E9%A1%BE%E6%99%B4%E5%B7%9D.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPDF();

    }

    public void loadPDF( ){
        mWebView = findViewById(R.id.view_web);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setSavePassword(false);
        webSettings.setBuiltInZoomControls(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl("file:///android_asset/index.html?" + url);
    }

    public void loadVideo( ){
        video=findViewById(R.id.videov);
        video.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.video2);
        MediaController mediaController=new MediaController(this);
        video.setMediaController(mediaController);
        video.start();
    }


}