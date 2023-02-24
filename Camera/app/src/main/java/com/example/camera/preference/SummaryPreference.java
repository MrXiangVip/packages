package com.example.camera.preference;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;

import com.example.camerabg.R;


public class SummaryPreference extends Preference {
    private String TAG ="SummaryPreference";
    private RelativeLayout layout;

    private CharSequence mContentDescription;
    private int mID = View.NO_ID;
    private PreferenceScreen mRootPreference;
    private boolean enable;
    private OnLayoutClickListener mListener;

    public interface OnLayoutClickListener {
        /**
         * Callback when item clicked.
         *
         * @param  The view clicked.
         */
        void onLayoutClick( View view);
    }
    public SummaryPreference(Context context){
        this(context, null);
    }


    public SummaryPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SummaryPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }
    public SummaryPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setId(@IdRes int id) {
        mID = id;
    }

    public void setContentDescription(CharSequence contentDescription) {
        mContentDescription = contentDescription;
    }

    public void setOnLayoutClickListener(OnLayoutClickListener listener) {
        mListener = listener;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        layout = view.findViewById( R.id.summary_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "bind view");
                View view = v.findViewById(R.id.summary_layout);
                mListener.onLayoutClick( view );
            }
        });
    }

    public void setEnabled(boolean enabled) {
        enable = enabled;
        if (enabled) {
            mRootPreference.addPreference(this);
        } else  {
//            mRootPreference.removePreference(this);
//            setOnPreferenceClickListener( null);
        }
    }
    public void setRootPreference(PreferenceScreen rootPreference) {
        mRootPreference = rootPreference;
    }
}
