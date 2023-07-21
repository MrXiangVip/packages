package com.mediatek.hralauncher.ui;

import android.os.Bundle;
import android.util.Log;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.adapter.LauncherAdapter;
import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.holder.AiQualityViewHolder;
import com.mediatek.hralauncher.holder.AiRecommendedViewHolder;
import com.mediatek.hralauncher.holder.AiSubjectViewHolder;
import com.mediatek.hralauncher.holder.BaseHolder;
import com.mediatek.hralauncher.holder.MyGrowUpHolder;
import com.mediatek.hralauncher.model.LauncherModel;
import com.mediatek.hralauncher.model.impl.AppPositionManager;
import com.mediatek.hralauncher.model.impl.LauncherModelView;
import com.mediatek.hralauncher.model.impl.LauncherAppViewHolderMgr;
import com.mediatek.hralauncher.model.interf.ILauncherPagersView;
import com.mediatek.hralauncher.model.interf.ISystemPMHooker;
import com.mediatek.hralauncher.widget.view.PageIndicatorView;
import com.mediatek.hralauncher.widget.view.ParallaxViewPager;

import java.util.ArrayList;
import java.util.List;

public class LauncherActivity extends BaseLauncherActivity{

    LauncherModel       mLauncherModel;
    LauncherModelView   mLauncherView;
    Boolean isFirstStart =false;

    ArrayList<BaseHolder>   mCardHolders= new ArrayList();
    ArrayList<BaseHolder>   mPrimaryCardHolders= new ArrayList();
    LauncherAdapter         mLauncherAdapter;
    ParallaxViewPager       mViewPager;
    PageIndicatorView       pageIndicatorView;

    private String TAG="LauncherActivity.";
    AiRecommendedViewHolder ai_recommended_ViewHolder;
    private AiSubjectViewHolder ai_subject_math;
    private AiSubjectViewHolder ai_subject_chinese;
    private AiSubjectViewHolder ai_subject_physical;
    private AiSubjectViewHolder ai_subject_english;
    private MyGrowUpHolder      ai_grow_up;
    private AiQualityViewHolder ai_quality_market;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_launcher);

        mViewPager = findViewById(R.id.mViewPager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        try{
            startModel();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void startModel( ){
        Log.d(TAG, "startModel");
        AppPositionManager appPositionManager = new AppPositionManager(this);

        LauncherAppViewHolderMgr factory = new LauncherAppViewHolderMgr(this, appPositionManager);

        mLauncherModel = LauncherModel.getInstance();

        mLauncherModel.init(this, new ISystemPMHooker() {
            @Override
            public List<AppInfo> onQueryAll(String str, List<AppInfo> list) {
                return list;
            }

            @Override
            public Boolean shouldIgnoreApp(String str, String str2) {
                return false;
            }
        });
        mLauncherView = new LauncherModelView(factory, appPositionManager, new AppListViewHolderManager(), isFirstStart);
        mLauncherModel.setLauncherView( mLauncherView );
        String gradePeriod = "点击登录";
        mLauncherModel.start(gradePeriod);
    }

    class AppListViewHolderManager implements ILauncherPagersView {

        @Override
        public void initAppPages(String gradePeriod, List<BaseHolder> viewHolders) {
            Log.d(TAG, "initAppPages");
            mLauncherAdapter =new  LauncherAdapter(mCardHolders, viewHolders);
            mViewPager.setAdapter( mLauncherAdapter );
            mViewPager.setCurrentItem( 0 );

        }

        @Override
        public void onCreateCardPages(String gradePeriod) {
            Log.d(TAG,"onCreateCardPages" );
            initViewHolders(gradePeriod);
            mLauncherAdapter = new LauncherAdapter(mCardHolders, null);
            mViewPager.setAdapter( mLauncherAdapter );
            mViewPager.setCurrentItem( 0 );
            pageIndicatorView.setSelection(0);
        }
    }

    void initViewHolders(String gradePeriod){
        Log.d(TAG,"initViewHolders "+gradePeriod );
        mCardHolders =new ArrayList();
        mPrimaryCardHolders =new ArrayList();

        ai_recommended_ViewHolder= new AiRecommendedViewHolder(this);
        ai_recommended_ViewHolder.onCreate();
        ai_subject_math = new AiSubjectViewHolder(this, AiSubjectViewHolder.SUBJECT_MATH);
        ai_subject_math.onCreate();
        ai_subject_chinese =  new AiSubjectViewHolder(this, AiSubjectViewHolder.SUBJECT_MATH);
        ai_subject_chinese.onCreate();
        ai_subject_english = new AiSubjectViewHolder(this, AiSubjectViewHolder.SUBJECT_ENGLISH);
        ai_subject_english.onCreate();
        ai_subject_physical = new AiSubjectViewHolder(this, AiSubjectViewHolder.SUBJECT_PHYSICAL);
        ai_subject_physical.onCreate();
        ai_quality_market = new AiQualityViewHolder( this);
        ai_quality_market.onCreate();
        ai_grow_up = new MyGrowUpHolder(this);
        ai_grow_up.onCreate();
        mPrimaryCardHolders.add(ai_recommended_ViewHolder);
        mPrimaryCardHolders.add(ai_subject_math);
        mPrimaryCardHolders.add(ai_subject_chinese);
        mPrimaryCardHolders.add(ai_subject_english);
        mPrimaryCardHolders.add(ai_subject_physical);
        mPrimaryCardHolders.add(ai_quality_market);
        mPrimaryCardHolders.add(ai_grow_up);

        mCardHolders = mPrimaryCardHolders;
    }
}
