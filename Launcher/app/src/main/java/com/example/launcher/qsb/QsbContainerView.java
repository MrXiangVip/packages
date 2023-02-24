package com.example.launcher.qsb;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_BIND;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_PROVIDER;

import android.appwidget.AppWidgetHost;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.launcher.R;
import com.example.launcher.config.FeatureFlags;

public class QsbContainerView extends FrameLayout {
    private static final int REQUEST_BIND_QSB = 1;


    public QsbContainerView(@NonNull Context context) {
        super(context);
    }

    public QsbContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QsbContainerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public QsbContainerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * A fragment to display the QSB.
     */
    public static class QsbFragment extends FragmentWithPreview {
        private FrameLayout mWrapper;
//        private QsbWidgetHost mQsbWidgetHost;

        public View onCreateView(
                LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            mWrapper = new FrameLayout(getContext());
            // Only add the view when enabled
            if (isQsbEnabled()) {
//                mQsbWidgetHost.startListening();
                mWrapper.addView(createQsb(mWrapper));
            }
            return mWrapper;
        }

        public boolean isQsbEnabled() {
            return FeatureFlags.QSB_ON_FIRST_SCREEN;
        }

        private View createQsb(ViewGroup container) {

            return getDefaultView(container, true /* show setup icon */);
        }

        protected View getDefaultView(ViewGroup container, boolean showSetupIcon) {
            // Return a default widget with setup icon.
            View v = QsbWidgetHostView.getDefaultView(container);
            if (showSetupIcon) {
                View setupButton = v.findViewById(R.id.btn_qsb_setup);
                setupButton.setVisibility(View.VISIBLE);
//                setupButton.setOnClickListener((v2) -> startActivityForResult(
//                        new Intent(ACTION_APPWIDGET_BIND)
//                                .putExtra(EXTRA_APPWIDGET_ID, mQsbWidgetHost.allocateAppWidgetId())
//                                .putExtra(EXTRA_APPWIDGET_PROVIDER, mWidgetInfo.provider),
//                        REQUEST_BIND_QSB));
            }
            return v;
        }

    }

//    public static class QsbWidgetHost extends AppWidgetHost {
//
//    }
}