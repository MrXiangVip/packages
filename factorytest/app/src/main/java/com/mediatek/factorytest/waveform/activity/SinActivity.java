package com.mediatek.factorytest.waveform.activity;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.mediatek.factorytest.R;

public class SinActivity extends Activity {

    /** Called when the activity is first created. */

    private SurfaceView surfaceView;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private int scale_x = 20;
    private int scale_y = 50;
    private double ox=10;
    private double oy=240;
    private double x=0;
    private double y=0;
    private double t=0;
    private boolean flag=true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sin_main);
        surfaceView = (SurfaceView) findViewById(R.id.main_suf);
        surfaceHolder = surfaceView.getHolder();
        paint = new Paint();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            public void surfaceDestroyed(SurfaceHolder holder) {
// TODO Auto-generated method stub
            }
            public void surfaceCreated(SurfaceHolder holder) {
// TODO Auto-generated method stub
                new Thread(new Runnable() {
                    @Override
                    public void run() {
// TODO Auto-generated method stub
                        while (flag) {
                            try {
                                Thread.sleep(10);
                                Canvas canvas=surfaceHolder.lockCanvas();
                                if(canvas!=null)
                                {
//绘制坐标轴
                                    paint.setColor(Color.WHITE);
                                    canvas.drawText("O",20,220, paint);
                                    paint.setColor(Color.BLUE);
                                    canvas.drawLine(10,10,10,480, paint);
                                    canvas.drawText("Y",20,30, paint);
                                    paint.setColor(Color.GREEN);
                                    canvas.drawLine(0,240,320,240, paint);
                                    canvas.drawText("X",300,260, paint);
//绘制正弦曲线
                                    t+=0.1;
                                    x=t*scale_x;
                                    y=Math.sin(t)*scale_y+240;
                                    System.out.println("---------"+Math.round(Math.sin(t)));
                                    if (t>0) {
                                        paint.setColor(Color.RED);
                                        canvas.drawLine(10+(int)ox,(int)oy,10+(int)x,(int)y, paint);
                                        System.out.println(x+"s"+y);
                                    }
                                    ox=x;
                                    oy=y;
                                    if (t>15) {
                                        flag=false;
                                    }
                                }
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
// TODO Auto-generated method stub
            }
        });
    }
}
