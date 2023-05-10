package com.mediatek.factorytest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.mediatek.factorytest.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by carlos on 2016/1/29.
 * 自定义声音振动曲线view
 */
public class VoiceLineView extends View {
    private final int LINE = 0;
    private final int RECT = 1;

    private int middleLineColor = Color.BLACK;
    private int voiceLineColor = Color.BLACK;
    private float middleLineHeight = 4;
    private Paint paint;
    private Paint paintVoicLine;
    private int mode;
    /**
     * 灵敏度
     */
    private int sensibility = 10;

    private float maxVolume = 100;


    private float translateX = 0;
    private boolean isSet = false;

    /**
     * 振幅
     */
    private float amplitude = 1;
    /**
     * 音量
     */
    private float volume = 10;
    private int fineness = 1;
    private float targetVolume = 1;


    private long speedY = 50;
    private float rectWidth = 25;
    private float rectSpace = 5;
    private float rectInitHeight = 4;
    private List<Rect> rectList;

    private long lastTime = 0;
    private int lineSpeed = 90;

    List<Path> paths = null;

    public VoiceLineView(Context context) {
        super(context);
    }

    public VoiceLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAtts(context, attrs);
    }

    public VoiceLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAtts(context, attrs);
    }

    private void initAtts(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.voiceView);
        mode = typedArray.getInt(R.styleable.voiceView_viewMode, 0);
        voiceLineColor = typedArray.getColor(R.styleable.voiceView_voiceLine, Color.BLACK);
        maxVolume = typedArray.getFloat(R.styleable.voiceView_maxVolume, 100);
        sensibility = typedArray.getInt(R.styleable.voiceView_sensibility, 10);
        if (mode == RECT) {
            rectWidth = typedArray.getDimension(R.styleable.voiceView_rectWidth, 25);
            rectSpace = typedArray.getDimension(R.styleable.voiceView_rectSpace, 5);
            rectInitHeight = typedArray.getDimension(R.styleable.voiceView_rectInitHeight, 4);
        } else {
            middleLineColor = typedArray.getColor(R.styleable.voiceView_middleLine, Color.BLACK);
            middleLineHeight = typedArray.getDimension(R.styleable.voiceView_middleLineHeight, 4);
            lineSpeed = typedArray.getInt(R.styleable.voiceView_lineSpeed, 90);
            fineness = typedArray.getInt(R.styleable.voiceView_fineness, 1);
            paths = new ArrayList<>(2);
            for (int i = 0; i < 2; i++) {
                paths.add(new Path());
            }
        }
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mode == RECT) {
            drawVoiceRect(canvas);
        } else {
            drawMiddleLine(canvas);
//            drawVoiceLine(canvas);
            drawVoiceLine2(canvas);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void drawMiddleLine(Canvas canvas) {
        if (paint == null) {
            paint = new Paint();
            paint.setColor(middleLineColor);
            paint.setAntiAlias(true);
        }
        canvas.save();
        canvas.drawRect(0, getHeight() / 2 - middleLineHeight / 2, getWidth(), getHeight() / 2 + middleLineHeight / 2, paint);
        canvas.restore();
    }

    private void drawVoiceLine(Canvas canvas) {
        lineChange();
        if (paintVoicLine == null) {
            paintVoicLine = new Paint();
            paintVoicLine.setColor(voiceLineColor);
            paintVoicLine.setAntiAlias(true);
            paintVoicLine.setStyle(Paint.Style.STROKE);
            paintVoicLine.setStrokeWidth(2);
        }
        canvas.save();
        int moveY = getHeight() / 2;
        for (int i = 0; i < paths.size(); i++) {
            paths.get(i).reset();
            paths.get(i).moveTo(getWidth(), getHeight() / 2);
        }
        for (float i = getWidth() - 1; i >= 0; i -= fineness) {
            amplitude = 4 * volume * i / getWidth() - 4 * volume * i * i / getWidth() / getWidth();
            for (int n = 1; n <= paths.size(); n++) {
                float sin = amplitude * (float) Math.sin((i - Math.pow(1.22, n)) * Math.PI / 180 - translateX);
                paths.get(n - 1).lineTo(i, (2 * n * sin / paths.size() - 15 * sin / paths.size() + moveY));
//                float lineY = getSinPoint(i*100, getHeight() );
//                paths.get(n-1).lineTo( i, lineY);
            }
        }
        for (int n = 0; n < paths.size(); n++) {
            if (n == paths.size() - 1) {
                paintVoicLine.setAlpha(255);
            } else {
                paintVoicLine.setAlpha(n * 130 / paths.size());
            }
            if (paintVoicLine.getAlpha() > 0) {
                canvas.drawPath(paths.get(n), paintVoicLine);
            }
        }
        canvas.restore();
    }

    private int scale_x = 2;
    private int scale_y = 200;
    private double ox=10;
    private double oy=240;
    private int x=0;
    private int y=0;
    private int t=0;
    LinkedList<Point> points =new LinkedList<Point>();
    private void drawVoiceLine2(Canvas canvas) {
        if (paintVoicLine == null) {
            paintVoicLine = new Paint();
            paintVoicLine.setColor(voiceLineColor);
            paintVoicLine.setAntiAlias(true);
            paintVoicLine.setStyle(Paint.Style.STROKE);
            paintVoicLine.setStrokeWidth(2);
        }

//绘制正弦曲线
        canvas.save();

//        t+=2;
//        x=t*scale_x % (200*2);

//        y= (int) (Math.sin(targetVolume)*scale_y+240);
//
        y= getHeight()/2 - (int) (Math.sin(targetVolume*Math.PI/200)*scale_y);
        if( points.size() >=180){
            points.removeFirst();
        }
        points.addLast( new Point(x,y));

        System.out.println("---------"+Math.round(Math.sin(t)));

        if( points.size() >1 ){
            for(int i=0; i< points.size()-1;){

                paint.setColor(Color.RED);
                canvas.drawLine(i*5,(int)points.get(i).y, (i+1)*5,(int)points.get(i+1).y, paint);
                System.out.println(x+"s"+y);
                i+=1;
            }
        }

        ox=x;
        oy=y;
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

    }

    private void drawVoiceRect(Canvas canvas) {
        if (paintVoicLine == null) {
            paintVoicLine = new Paint();
            paintVoicLine.setColor(voiceLineColor);
            paintVoicLine.setAntiAlias(true);
            paintVoicLine.setStyle(Paint.Style.STROKE);
            paintVoicLine.setStrokeWidth(2);
        }
        if (rectList == null) {
            rectList = new LinkedList<>();
        }
        int totalWidth = (int) (rectSpace + rectWidth);
        if (speedY % totalWidth < 6) {
            Rect rect = new Rect((int) (-rectWidth - 10 - speedY + speedY % totalWidth),
                    (int) (getHeight() / 2 - rectInitHeight / 2 - (volume == 10 ? 0 : volume / 2)),
                    (int) (-10 - speedY + speedY % totalWidth),
                    (int) (getHeight() / 2 + rectInitHeight / 2 + (volume == 10 ? 0 : volume / 2)));
            if (rectList.size() > getWidth() / (rectSpace + rectWidth) + 2) {
                rectList.remove(0);
            }
            rectList.add(rect);
        }
        canvas.translate(speedY, 0);
        for (int i = rectList.size() - 1; i >= 0; i--) {
            canvas.drawRect(rectList.get(i), paintVoicLine);
        }
        rectChange();
    }

    public void setVolume(int volume) {
//        if (volume > maxVolume * sensibility / 25) {
//            isSet = true;
//            this.targetVolume = getHeight() * volume / 2 / maxVolume;
//        }

        targetVolume = volume;
        invalidate();
    }

    private void lineChange() {
        if (lastTime == 0) {
            lastTime = System.currentTimeMillis();
            translateX += 1.5;
        } else {
            if (System.currentTimeMillis() - lastTime > lineSpeed) {
                lastTime = System.currentTimeMillis();
                translateX += 1.5;
            } else {
                return;
            }
        }
        if (volume < targetVolume && isSet) {
            volume += getHeight() / 30;
        } else {
            isSet = false;
            if (volume <= 10) {
                volume = 10;
            } else {
                if (volume < getHeight() / 30) {
                    volume -= getHeight() / 60;
                } else {
                    volume -= getHeight() / 30;
                }
            }
        }
    }

    private void rectChange() {
        speedY += 6;
        if (volume < targetVolume && isSet) {
            volume += getHeight() / 30;
        } else {
            isSet = false;
            if (volume <= 10) {
                volume = 10;
            } else {
                if (volume < getHeight() / 30) {
                    volume -= getHeight() / 60;
                } else {
                    volume -= getHeight() / 30;
                }
            }
        }
    }



    private float getSinPoint(float x, float offset) {
        return (float) (4/(4+Math.pow(x,4))*Math.sin(3*x + 5*offset));
    }
}