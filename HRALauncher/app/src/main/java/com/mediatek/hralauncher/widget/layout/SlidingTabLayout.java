package com.mediatek.hralauncher.widget.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.listener.OnTabSelectListener;

import java.util.List;

public class SlidingTabLayout extends HorizontalScrollView {
    private Context mContext;
    private List<String> mTitles;
    private List<Integer> mColors;
    private LinearLayout mTabsContainer;
    private int mTabCount;
    private int mCurrentTab;
    private boolean mTabSpaceEqual;
    private float mTabWidth=0;
    private float mSelTextsize;
    private float mTextsize;
    private OnTabSelectListener mListener;
    private int mTextBold;
    private int mTextUnselectColor;
    private float mTabPadding;

    private static final int TEXT_BOLD_NONE = 0;
    private static final int TEXT_BOLD_WHEN_SELECT = 1;
    private static final int TEXT_BOLD_BOTH = 2;

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mTabsContainer = new LinearLayout(context);
        addView(mTabsContainer);

        obtainAttributes(context, attrs);
    }
    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingTabLayout);
        mTabWidth = ta.getDimension(R.styleable.SlidingTabLayout_tl_tab_width, dp2px(-1));
        mTabSpaceEqual = ta.getBoolean(R.styleable.SlidingTabLayout_tl_tab_space_equal, false);
        mTabPadding = ta.getDimension(R.styleable.SlidingTabLayout_tl_tab_padding, mTabSpaceEqual || mTabWidth > 0 ? dp2px(0) : dp2px(20));
        mTextBold = ta.getInt(R.styleable.SlidingTabLayout_tl_textBold, TEXT_BOLD_NONE);
        mTextUnselectColor = ta.getColor(R.styleable.SlidingTabLayout_tl_textUnselectColor, Color.parseColor("#AAffffff"));
        mTextsize = ta.getDimension(R.styleable.SlidingTabLayout_tl_textsize, sp2px(14));
        mSelTextsize = ta.getDimension(R.styleable.SlidingTabLayout_tl_textSelsize, sp2px(14));

    }

    public void setTabs(List<String> tabs, List<Integer> colors) {
        mTitles = tabs;
        mColors = colors;
        notifyDataSetChanged();
        updateTabSelection(mCurrentTab);
    }

    public void notifyDataSetChanged() {
        mTabsContainer.removeAllViews();
        mTabCount = mTitles.size();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = View.inflate(mContext, R.layout.layout_tab, null);
            CharSequence pageTitle = mTitles.get(i);
            addTab(i, pageTitle.toString(), tabView);
        }
//        updateTabStyles();
    }

    public void setCurrentTab(int currentTab) {
        this.mCurrentTab = currentTab;
        updateTabSelection(mCurrentTab, mColors.get(mCurrentTab));
    }
    private void updateTabSelection(int position, int selectColor) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
            if (tab_title != null) {
                tab_title.setTextColor(isSelect ? selectColor : mTextUnselectColor);
                if (mTextBold == TEXT_BOLD_WHEN_SELECT) {
                    tab_title.getPaint().setFakeBoldText(isSelect);
                }
                tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, i == position ? mSelTextsize : mTextsize);
            }
        }
    }


    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == position;
            TextView tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
//            if (tab_title != null) {
//                tab_title.setTextColor(isSelect ? mTextSelectColor : mTextUnselectColor);
//                if (mTextBold == TEXT_BOLD_WHEN_SELECT) {
//                    tab_title.getPaint().setFakeBoldText(isSelect);
//                }
//                tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, i == position ? mSelTextsize : mTextsize);
//            }
        }
    }

    private void addTab(final int position, String title, View tabView) {
        TextView tv_tab_title = (TextView) tabView.findViewById(R.id.tv_tab_title);
        if (tv_tab_title != null) {
            if (title != null) {
                tv_tab_title.setText(title);
                tv_tab_title.setTextSize(TypedValue.COMPLEX_UNIT_PX, position == mCurrentTab ? mSelTextsize : mTextsize);
            }
        }

        tabView.setOnClickListener(v -> {
            setCurrentTab(position);
            if (mListener != null) {
                mListener.onTabSelect(position);
            }
        });

        /** 每一个Tab的布局参数 */
        LinearLayout.LayoutParams lp_tab = mTabSpaceEqual ?
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        if (mTabWidth > 0) {
            lp_tab = new LinearLayout.LayoutParams((int) mTabWidth, LayoutParams.MATCH_PARENT);
        }

        mTabsContainer.addView(tabView, position, lp_tab);
    }

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.mListener = listener;
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = this.mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
