package com.mediatek.hralauncher.ui.user.activity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.ui.user.adapter.LearnTimeAdapter;
import com.mediatek.hralauncher.ui.user.fragment.AiInteractiveFragment;
import com.mediatek.hralauncher.ui.user.fragment.FatherFragment;
import com.mediatek.hralauncher.ui.user.fragment.MessageCenterFragment;
import com.mediatek.hralauncher.ui.user.fragment.PersonCenterFragment;

import java.util.ArrayList;
import java.util.List;

public class PersonCenterActivity extends AppCompatActivity {

    private List<Fragment>          fragments = new ArrayList<>();
    private PersonCenterFragment    personFragment;
    private FatherFragment          fatherFragment;
    private AiInteractiveFragment   aiInteractiveFragment;
    private MessageCenterFragment   messageFragment;

    private LearnTimeAdapter        learnTimeAdapter;
    ViewPager2  personCenterVp2;
    Button      btnPerson;
    Button      btnFather;
    Button      btnInteractive;
    Button      buttonMessageNotify;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        init();


    }

    protected void init() {
        btnPerson = findViewById( R.id.btn_p_person);
        btnFather = findViewById( R.id.btn_p_father);
        btnInteractive = findViewById( R.id.btn_p_interactive);
        buttonMessageNotify = findViewById( R.id.buttonMessageNotify);

        personFragment = new PersonCenterFragment();
        fragments.add(personFragment);

        fatherFragment = new FatherFragment();
        fragments.add(fatherFragment);

        aiInteractiveFragment = new AiInteractiveFragment();
        fragments.add( aiInteractiveFragment );

        messageFragment = new MessageCenterFragment();
        fragments.add(messageFragment);

        learnTimeAdapter = new LearnTimeAdapter(this, fragments);
        if (null == personCenterVp2) {
            personCenterVp2 = findViewById(R.id.person_center_vp2);
        }

        personCenterVp2.setAdapter(learnTimeAdapter);
        personCenterVp2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        ensureSelect(true, false, false, false);

    }

    private void ensureSelect(boolean first, boolean second, boolean three, boolean four) {
        btnPerson.setSelected(first);
        btnFather.setSelected(second);
        btnInteractive.setSelected(three);
        buttonMessageNotify.setSelected(four);
    }
}