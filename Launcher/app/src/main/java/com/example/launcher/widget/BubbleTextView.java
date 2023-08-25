package com.example.launcher.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.launcher.R;
import com.example.launcher.dot.DotInfo;
import com.example.launcher.icons.DotRenderer;
import com.example.launcher.model.ItemInfo;
import com.example.launcher.views.ActivityContext;
import com.example.launcher.views.FastBitmapDrawable;

public class BubbleTextView extends TextView {
    private float mTextAlpha = 1;
    private int mTextColor;
    private Drawable mIcon;
    private DotInfo mDotInfo;
    private final ActivityContext mActivity;
    private final int mDisplay;
    private DotRenderer mDotRenderer;
    private DotRenderer.DrawParams mDotParams;

    private static final int DISPLAY_WORKSPACE = 0;
    private static final int DISPLAY_ALL_APPS = 1;
    private static final int DISPLAY_FOLDER = 2;

    public BubbleTextView(Context context) {
        this(context, null);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleTextView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mActivity = ActivityContext.lookupContext(context);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.BubbleTextView, defStyle, 0);
        mDisplay = a.getInteger(R.styleable.BubbleTextView_iconDisplay, DISPLAY_WORKSPACE);

    }



    public void setTextVisibility(boolean visible) {
        setTextAlpha(visible ? 1 : 0);
    }

    private void setTextAlpha(float alpha) {
        mTextAlpha = alpha;
        super.setTextColor(getModifiedColor());
    }

    private int getModifiedColor() {
        if (mTextAlpha == 0) {
            // Special case to prevent text shadows in high contrast mode
            return Color.TRANSPARENT;
        }
        return setColorAlphaBound(mTextColor, Math.round(Color.alpha(mTextColor) * mTextAlpha));
    }

    public static int setColorAlphaBound(int color, int alpha) {
        if (alpha < 0) {
            alpha = 0;
        } else if (alpha > 255) {
            alpha = 255;
        }
        return (color & 0x00ffffff) | (alpha << 24);
    }

    public void applyDotState(ItemInfo itemInfo, boolean animate) {
        if (mIcon instanceof FastBitmapDrawable) {
            boolean wasDotted = mDotInfo != null;
            mDotInfo = mActivity.getDotInfoForItem(itemInfo);
            boolean isDotted = mDotInfo != null;
            float newDotScale = isDotted ? 1f : 0;
            if (mDisplay == DISPLAY_ALL_APPS) {
                mDotRenderer = mActivity.getDeviceProfile().mDotRendererAllApps;
            } else {
                mDotRenderer = mActivity.getDeviceProfile().mDotRendererWorkSpace;
            }
            if (wasDotted || isDotted) {
                // Animate when a dot is first added or when it is removed.
                if (animate && (wasDotted ^ isDotted) && isShown()) {
                    animateDotScale(newDotScale);
                } else {
                    cancelDotScaleAnim();
                    mDotParams.scale = newDotScale;
                    invalidate();
                }
            }
            if (itemInfo.contentDescription != null) {
                if (itemInfo.isDisabled()) {
                    setContentDescription(getContext().getString(R.string.disabled_app_label,
                            itemInfo.contentDescription));
                } else if (hasDot()) {
                    int count = mDotInfo.getNotificationCount();
                    setContentDescription(getContext().getResources().getQuantityString(
                            R.plurals.dotted_app_label, count, itemInfo.contentDescription, count));
                } else {
                    setContentDescription(itemInfo.contentDescription);
                }
            }
        }
    }
    private boolean hasDot() {
        return mDotInfo != null;
    }
    private void animateDotScale(float... dotScales) {
//        cancelDotScaleAnim();
//        mDotScaleAnim = ObjectAnimator.ofFloat(this, DOT_SCALE_PROPERTY, dotScales);
//        mDotScaleAnim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mDotScaleAnim = null;
//            }
//        });
//        mDotScaleAnim.start();
    }
    private void cancelDotScaleAnim() {
//        if (mDotScaleAnim != null) {
//            mDotScaleAnim.cancel();
//        }
    }
}