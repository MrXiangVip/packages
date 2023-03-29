package com.example.camera;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.camera.widget.CoverGroupButtonLayout;
import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    LinearLayout groupLayout;
    LinearLayout buttonLayout;
    float  lastX;
    float  lastY;
//    private View.OnClickListener onClickListener = new MyOnClickListener();
    private String TAG="Main";
    private List<String> mEntryValues = new ArrayList<>();

    private String mSelectedValue;
    private Button  selectedButton;
    private TextView textView;
    CoverGroupButtonLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        initGroupPref();
        layout = findViewById( R.id.group_pref);
        String[] originalEntryValuesInArray = getResources()
                .getStringArray(R.array.anti_flicker_entryvalues);
        for (String value : originalEntryValuesInArray) {
            mEntryValues.add(value);
        }
        layout.setEntryAndSelectedValue("off", mEntryValues);
        layout.setTitle("防闪烁");
        layout.initButtonView();
    }

}