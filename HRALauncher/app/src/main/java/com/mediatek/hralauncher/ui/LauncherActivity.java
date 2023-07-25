package com.mediatek.hralauncher.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.Group;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.adapter.LauncherAdapter;
import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.holder.AiQualityViewHolder;
import com.mediatek.hralauncher.holder.AiRecommendedViewHolder;
import com.mediatek.hralauncher.holder.AiSubjectViewHolder;
import com.mediatek.hralauncher.holder.BaseHolder;
import com.mediatek.hralauncher.holder.MyGrowUpHolder;
import com.mediatek.hralauncher.listener.OnTabSelectListener;
import com.mediatek.hralauncher.model.LauncherModel;
import com.mediatek.hralauncher.model.impl.AppPositionManager;
import com.mediatek.hralauncher.model.impl.LauncherModelView;
import com.mediatek.hralauncher.model.impl.LauncherAppViewHolderMgr;
import com.mediatek.hralauncher.model.interf.ILauncherPagersView;
import com.mediatek.hralauncher.model.interf.ISystemPMHooker;
import com.mediatek.hralauncher.widget.layout.SlidingTabLayout;
import com.mediatek.hralauncher.widget.view.PageIndicatorView;
import com.mediatek.hralauncher.widget.view.ParallaxViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LauncherActivity extends BaseLauncherActivity implements OnTabSelectListener {

    LauncherModel       mLauncherModel;
    LauncherModelView   mLauncherView;
    Boolean isFirstStart =false;
    int mCurrentPosition = 0;

    ArrayList<BaseHolder>   mCardHolders= new ArrayList();
    ArrayList<BaseHolder>   mPrimaryCardHolders= new ArrayList();
    LauncherAdapter         mLauncherAdapter;
    ParallaxViewPager       mViewPager;
    PageIndicatorView       pageIndicatorView;
    Group                   user_center;
    Group                   nav_container;
    private String TAG="LauncherActivity.";
    AiRecommendedViewHolder ai_recommended_ViewHolder;
    private AiSubjectViewHolder ai_subject_math;
    private AiSubjectViewHolder ai_subject_chinese;
    private AiSubjectViewHolder ai_subject_physical;
    private AiSubjectViewHolder ai_subject_english;
    private MyGrowUpHolder      ai_grow_up;
    private AiQualityViewHolder ai_quality_market;

    private SlidingTabLayout        subject_container;

    private List mSubjectTitles = Arrays.asList("语文", "数学", "英语", "综合学科", "素质中心", "我的成长");
    private List mSubjectColors = Arrays.asList(
            Color.parseColor("#FF70DC73"),
            Color.parseColor("#FFFF9146"),
            Color.parseColor("#FF9584FF"),
            Color.parseColor("#FF3D9EFE"),
            Color.parseColor("#FF3D9EFE"),
            Color.parseColor("#FF3D9EFE")
            );


    public void onCreate(Bundle savedInstanceState){
        super.onCreate( savedInstanceState );
        Log.d(TAG,"onCreate");
        setContentView(R.layout.activity_launcher);

        user_center = findViewById( R.id.user_center );
        nav_container = findViewById(R.id.nav_container);
        mViewPager = findViewById(R.id.mViewPager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        try{
            startModel();
            initSubjectWidgets();
            initListener();
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
    private void initSubjectWidgets() {
        subject_container = findViewById( R.id.subject_container);
        subject_container.setTabs(mSubjectTitles, mSubjectColors);
        subject_container.setCurrentTab( 0 );
    }

    private void initListener() {

        subject_container.setOnTabSelectListener(this);

    }

    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {
        mViewPager.setCurrentItem(position + 1);
    }

    class AppListViewHolderManager implements ILauncherPagersView {

        @Override
        public void initAppPages(String gradePeriod, List<BaseHolder> viewHolders) {
            Log.d(TAG, "initAppPages");
            mLauncherAdapter =new  LauncherAdapter(mCardHolders, viewHolders);
            mViewPager.setAdapter( mLauncherAdapter );
            mViewPager.setCurrentItem( 0 );
            refreshUserAndNav(0);
            mViewPager.addOnPageChangeListener( new MyViewPagerChangeListener());

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
        ai_subject_chinese =  new AiSubjectViewHolder(this, AiSubjectViewHolder.SUBJECT_CHINESE);
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
    void  refreshUserAndNav(int position) {
        refreshUserCenterIcon(position);
        refreshNavContainer(position);
        refreshSubjectContainer(position);
    }
    void  refreshUserCenterIcon(int position ) {
        Log.e(TAG, "refreshUserCenterIcon position:$position");
        int cardSize = -1;
        if (mLauncherAdapter != null) {
            cardSize = mLauncherAdapter.getCardViewCount();
        }
        if (position == 0) {
            user_center.setVisibility(View.GONE);
            return;
        }
        if (position >= cardSize) {
            user_center.setVisibility(View.GONE);
        } else {
            user_center.setVisibility( View.VISIBLE);
        }
    }
    void refreshNavContainer(int position) {
        Log.e(TAG, "refreshNavContainer position:$position");
        int cardSize = -1;
        if (mLauncherAdapter != null) {
            cardSize = mLauncherAdapter.getCardViewCount();
        }

        if (position >= cardSize) {
            nav_container.setVisibility(View.GONE);
        } else {
            nav_container.setVisibility(View.VISIBLE);
        }
    }

    void refreshSubjectContainer(int position ) {
        if( 1 <= position && position <=6 ){
            subject_container.setCurrentTab(position-1);
        }
    }


    class MyViewPagerChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG,"onPageScrolled "+position);
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG,"onPageSelected "+position);
            mCurrentPosition = position;
            refreshUserAndNav(position);
            List<BaseHolder> holders =mLauncherAdapter.getAllHolders();
            if( holders != null ){
                int size = holders.size();
                for( int i =0; i< size; i++ ){
                    boolean isCurrent = (i==mCurrentPosition);
                    BaseHolder holder = holders.get(i);
                    if( isCurrent ){
                        holder.onScrollIn();
                    }else{
                        holder.onScrollOut();
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG,"onPageScrollStateChanged "+state);
        }
    }
}
