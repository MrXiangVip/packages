package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.myapplication.widget.FloatingView;
import com.example.myapplication.widget.SideBarView;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {
    String TAG = "MainActivity.";
    TextView eventInfo;

    Button upArrow, leftArrow, rightArrow, downArrow;
    Button xKey, yKey, aKey, bKey;
    Button backKey, modeKey, selectKey, startKey, sidebar;
    ImageView leftJoy, rightJoy, icJoystick;

    int leftJoyTop, leftJoyLeft;
    int rightJoyTop, rightJoyLeft;
    int leftJoyLastX, leftJoyLastY;//左摇杆上一次的位置数据
    int rightJoyLastX, rightJoyLastY;// 右摇杆上一次的位置数据
    FrameLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mainLayout = findViewById(R.id.main);
        eventInfo = findViewById(R.id.eventInfo);

        upArrow = findViewById(R.id.arrowUp);
        leftArrow = findViewById(R.id.arrowLeft);
        rightArrow = findViewById(R.id.arrowRight);
        downArrow = findViewById(R.id.arrowDown);
        leftJoy = findViewById(R.id.leftJoy);
        rightJoy = findViewById(R.id.rightJoy);
        leftJoyTop = leftJoy.getTop();
        leftJoyLeft = leftJoy.getLeft();
        rightJoyTop = rightJoy.getTop();
        rightJoyLeft = rightJoy.getLeft();

        xKey = findViewById(R.id.keyX);
        yKey = findViewById(R.id.keyY);
        aKey = findViewById(R.id.keyA);
        bKey = findViewById(R.id.keyB);

        backKey = findViewById(R.id.backKey);
        modeKey = findViewById(R.id.modeKey);
        selectKey = findViewById(R.id.selectKey);
        startKey = findViewById(R.id.startKey);
        sidebar = findViewById(R.id.showSideBar);
        sidebar.setOnClickListener(this);
        aKey.setOnClickListener(this);
        bKey.setOnClickListener(this);
        xKey.setOnClickListener(this);
        yKey.setOnClickListener(this);

        leftJoyLastX = (int) (getWindowManager().getCurrentWindowMetrics().getBounds().width() * 0.25);
        leftJoyLastY = (int) (getWindowManager().getCurrentWindowMetrics().getBounds().height() * 0.6);

        rightJoyLastX = (int) (getWindowManager().getCurrentWindowMetrics().getBounds().width() * 0.75);
        rightJoyLastY = (int) (getWindowManager().getCurrentWindowMetrics().getBounds().height() * 0.6);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getGameControllerIds();
        leftJoy.setX(leftJoyLastX);
        leftJoy.setY(leftJoyLastY);
        rightJoy.setX(rightJoyLastX);
        rightJoy.setY(rightJoyLastY);
    }

    HashMap<Integer, ImageView> pointMap = new HashMap<Integer, ImageView>();

    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent " + event.toString());
        Log.d(TAG, "x:" + event.getX() + " y:" + event.getY());
        FloatingView.getInstance(this).get(SideBarView.class).dismiss();
        int pointCount = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointCount = event.getPointerCount();//
                for (int i = 0; i < pointCount; i++) {
                    int slotId = event.getPointerId(i);
                    ImageView pointView = new ImageView(this);
                    pointView.setImageResource(R.drawable.ic_joystick);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    mainLayout.addView(pointView, params);
                    int pointX = (int) event.getX(i);
                    int pointY = (int) event.getY(i);
                    int imageWidth = pointView.getWidth();
                    int imageHeight = pointView.getHeight();
                    pointView.setX(pointX - imageWidth / 2);
                    pointView.setY(pointY - imageHeight / 2);
                    pointMap.put(slotId, pointView);

                }
                Log.d(TAG, "DOWN:" + pointMap.toString());
                break;
            case MotionEvent.ACTION_MOVE:
                pointCount = event.getPointerCount();//
                for (int i = 0; i < pointCount; i++) {
                    int slotId = event.getPointerId(i);
                    ImageView pointView = pointMap.get(slotId);
                    int pointX = (int) event.getX(i);
                    int pointY = (int) event.getY(i);

                    if (pointView != null) {
                        int imageWidth = pointView.getWidth();
                        int imageHeight = pointView.getHeight();
                        pointView.setX(pointX - imageWidth / 2);
                        pointView.setY(pointY - imageHeight / 2);
                    } else {
                        pointView = new ImageView(this);
                        pointView.setImageResource(R.drawable.ic_joystick);
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        mainLayout.addView(pointView, params);
                        int imageWidth = pointView.getWidth();
                        int imageHeight = pointView.getHeight();
                        pointX = (int) event.getX(i);
                        pointY = (int) event.getY(i);
                        pointView.setX(pointX - imageWidth / 2);
                        pointView.setY(pointY - imageHeight / 2);
                        pointMap.put(slotId, pointView);
                    }
                }
                Log.d(TAG, "MOVE:" + pointMap.toString());
                break;
            case MotionEvent.ACTION_UP:
                pointCount = event.getPointerCount();
                for (int i = 0; i < pointCount; i++) {
                    int slotId = event.getPointerId(i);
                    ImageView pointView = pointMap.get(slotId);
                    pointMap.remove(slotId);
                    if (pointView != null) {
                        mainLayout.removeView(pointView);
                    }
                }
                Log.d(TAG, "UP:" + pointMap.toString());
                break;
        }

        return super.onTouchEvent(event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d(TAG, "event " + event.toString());
        eventInfo.setText(event.toString());

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BUTTON_A:
                // 模拟完整的触摸事件序列
//                aKey.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
                aKey.setPressed(true);
                aKey.performClick();
                aKey.setPressed(false);
                break;
            case KeyEvent.KEYCODE_BUTTON_B:
//            case KeyEvent.KEYCODE_BACK:
                bKey.setPressed(true);
                bKey.performClick();
                bKey.setPressed(false);
                break;
            case KeyEvent.KEYCODE_BUTTON_X:
                xKey.setPressed(true);
                xKey.performClick();
                xKey.setPressed(false);
                break;
            case KeyEvent.KEYCODE_BUTTON_Y:
                yKey.setPressed(true);
                yKey.performClick();
                yKey.setPressed(false);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                upArrow.setPressed(true);
                upArrow.performClick();
                upArrow.setPressed(false);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                downArrow.setPressed(true);
                downArrow.performClick();
                downArrow.setPressed(false);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                leftArrow.setPressed(true);
                leftArrow.performClick();
                leftArrow.setPressed(false);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                rightArrow.setPressed(true);
                rightArrow.performClick();
                rightArrow.setPressed(false);
                break;
            case KeyEvent.KEYCODE_BACK:
                backKey.setPressed(true);
                backKey.performClick();
                backKey.setPressed(false);
                break;
            case KeyEvent.KEYCODE_BUTTON_MODE:
                modeKey.setPressed(true);
                modeKey.performClick();
                modeKey.setPressed(false);
                break;
            case KeyEvent.KEYCODE_BUTTON_START:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                startKey.setPressed(true);
                startKey.performClick();
                startKey.setPressed(false);
                break;
            case KeyEvent.KEYCODE_BUTTON_SELECT:
                selectKey.setPressed(true);
                selectKey.performClick();
                selectKey.setPressed(false);
                break;
            default:
                super.dispatchKeyEvent(event);
                break;
        }
        return true;
    }


    public boolean onGenericMotionEvent(MotionEvent event) {
        Log.d(TAG, "onGenericMotionEvent " + event.toString());
        eventInfo.setText(event.toString());

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            handleMouseEvent(event);
//            eventInfo.setText("mouse move to,x: "+deltaX+" y: "+deltaY);
        }
        boolean ret = super.onGenericMotionEvent(event);
        return ret;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick " + v.toString());
        if (v.getId() == R.id.showSideBar) {
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.SideBar");
//            startActivity(intent);
            FloatingView.getInstance(this).get(SideBarView.class).show();
        }
    }

    private void handleMouseEvent(MotionEvent event) {

        Log.d(TAG, "handleMouseEvent X:" + event.getX() + " Y: " + event.getY() + " " + toString());

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//
            float leftJoystickX = event.getAxisValue(MotionEvent.AXIS_X);
            float leftJoystickY = event.getAxisValue(MotionEvent.AXIS_Y);
//
            float rightJoystickX = event.getAxisValue(MotionEvent.AXIS_Z);
            float rightJoystickY = event.getAxisValue(MotionEvent.AXIS_RZ);

            if (leftJoystickX != 0 || leftJoystickY != 0) {
                leftJoy.setTranslationX(leftJoy.getTranslationX() + leftJoystickX * 20);
                leftJoy.setTranslationY(leftJoy.getTranslationY() + leftJoystickY * 20);
            }

            if (rightJoystickX != 0 || rightJoystickY != 0) {
                rightJoy.setTranslationX(rightJoy.getTranslationX() + rightJoystickX * 20);
                rightJoy.setTranslationY(rightJoy.getTranslationY() + rightJoystickY * 20);
            }
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
            leftJoy.setTranslationX(leftJoyLeft);
            leftJoy.setTranslationY(leftJoyTop);

            rightJoy.setTranslationX(rightJoyLeft);
            rightJoy.setTranslationY(rightJoyTop);
        }

    }

    public ArrayList<Integer> getGameControllerIds() {
        ArrayList<Integer> gameControllerDeviceIds = new ArrayList<Integer>();
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            int sources = dev.getSources();

            // Verify that the device has gamepad buttons, control sticks, or both.
            if (((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)
                    || ((sources & InputDevice.SOURCE_JOYSTICK)
                    == InputDevice.SOURCE_JOYSTICK)) {
                // This device is a game controller. Store its device ID.
                if (!gameControllerDeviceIds.contains(deviceId)) {
                    gameControllerDeviceIds.add(deviceId);
                }
            }
        }
        return gameControllerDeviceIds;
    }



}