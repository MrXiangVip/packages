package com.example.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.camera.common.IApp;
import com.example.camera.host.CameraAppUI;
import com.example.camerabg.R;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class QuickSwitcherManager extends AbstractViewManager {

    private IApp mIApp;
    private View mTopBar;
    private View mModeView;
    private View mFlashView;
    private View mHdrView;
    private ViewGroup mOptionRoot;
    private LinearLayout mQuickSwitcherLayout;
    private static final int MARGIN_IN_DP = 50;
    private ConcurrentSkipListMap<Integer, View> mQuickItems = new ConcurrentSkipListMap<>();
    private String TAG="QuickSwitcherManager";

    private ImageView mFlashOffIcon;
    private ImageView mFlashAutoIcon;
    private ImageView mFlashOnIcon;
    private ViewGroup mOptionLayout;
    private View      mFlashChoiceView;

    public QuickSwitcherManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
        mIApp = app;
        mTopBar = app.getActivity().findViewById(R.id.top_bar);
        mOptionRoot = (ViewGroup) mApp.getActivity().findViewById(R.id.quick_switcher_option);
//        mOrientationChangeListener = new OnOrientationChangeListenerImpl();
    }

    protected View getView() {
        mQuickSwitcherLayout = (LinearLayout) mParentView.findViewById(R.id.quick_switcher);
        updateQuickItems();
        return mQuickSwitcherLayout;
    }
    private void updateQuickItems() {
//        float density = mApp.getActivity().getResources().getDisplayMetrics().density;
//        int marginInPix = (int) (MARGIN_IN_DP * density);
//        int imageSize = (int) (50 * density);
//        if (mQuickSwitcherLayout != null ) {
//            mModeView = mQuickSwitcherLayout.findViewById(R.id.mode);
//            mQuickSwitcherLayout.removeAllViews();
//        }
//        if (mQuickSwitcherLayout != null) {
//            Iterator iterator = mQuickItems.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry map = (Map.Entry) iterator.next();
//                View view = (View) map.getValue();
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                        imageSize,
//                        imageSize);
//                params.setMargins(marginInPix, 0, 0, 0);
//                view.setLayoutParams(params);
//                mQuickSwitcherLayout.addView(view);
//            }
//        }
//        if (mModeView != null){
//            mQuickSwitcherLayout.addView(mModeView);
//        }
//        updateViewOrientation();

        mTopBar = mApp.getActivity().findViewById(R.id.top_bar);
        mModeView = mQuickSwitcherLayout.findViewById(R.id.mode);
        mFlashView =  mQuickSwitcherLayout.findViewById(R.id.flash);
        mHdrView =  mQuickSwitcherLayout.findViewById(R.id.hdr);



        mFlashView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animator animator = (Animator) AnimatorInflater.loadAnimator(mApp.getActivity(), R.animator.image_enter);
                animator.setTarget(mFlashView);
                animator.start();

                initializeFlashChoiceView();
                showQuickSwitcherOption(mOptionLayout);
            }
        });

        mModeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CameraAppUI)mApp.getAppUi()).showSetting();
            }
        });


    }

    public void updateViewOrientation() {
//        int orientation = mApp.getGSensorOrientation();
//        CameraUtil.rotateRotateLayoutChildView(mApp.getActivity(), mView, orientation, false);
    }

    public void showQuickSwitcherOption(View optionView) {
        if (mOptionRoot.getChildCount() != 0) {
            Log.e(TAG, "[showQuickSwitcherOption] Already has options to be shown!");
            return;
        }
//        Animation inAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_top_in);
        mOptionRoot.addView(optionView);
        mOptionRoot.setVisibility(View.VISIBLE);
        mOptionRoot.setClickable(true);
//        mOptionRoot.startAnimation(inAnim);
        mTopBar.setVisibility(View.GONE);

    }
    private void initializeFlashChoiceView() {
        if (mOptionLayout == null) {
            ViewGroup viewGroup = (ViewGroup) mApp.getActivity().findViewById(R.id.quick_switcher_option);
            mOptionLayout = (ViewGroup) mApp.getActivity().getLayoutInflater().inflate(
                    R.layout.flash_option, viewGroup, false);
            mFlashChoiceView = mOptionLayout.findViewById(R.id.flash_choice);
            mFlashOnIcon = (ImageView) mOptionLayout.findViewById(R.id.flash_on);
            mFlashOffIcon = (ImageView) mOptionLayout.findViewById(R.id.flash_off);
            mFlashAutoIcon = (ImageView) mOptionLayout.findViewById(R.id.flash_auto);
            mFlashOffIcon.setOnClickListener(mFlashChoiceViewListener);
            mFlashOnIcon.setOnClickListener(mFlashChoiceViewListener);
            mFlashAutoIcon.setOnClickListener(mFlashChoiceViewListener);
        }
    }

    private View.OnClickListener mFlashChoiceViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            hideQuickSwitcherOption();
        }
    };
    public void hideQuickSwitcherOption() {
        Animation outAnim = AnimationUtils.loadAnimation( mApp.getActivity(), R.anim.anim_top_out);
        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                mOptionRoot.setVisibility(View.GONE);
                mOptionRoot.setClickable(false);
//                mOptionRoot.removeAllViews();
                mTopBar.setVisibility(View.VISIBLE);

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mOptionRoot.startAnimation(outAnim);
        outAnim.setFillAfter(true);
    }
}
