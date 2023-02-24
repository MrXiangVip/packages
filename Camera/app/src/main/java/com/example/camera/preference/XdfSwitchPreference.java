package com.example.camera.preference;

import android.content.Context;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.IdRes;

import com.example.camerabg.R;

public class XdfSwitchPreference extends SwitchPreference {
    private String TAG ="SummaryPreference";
    private RelativeLayout layout;

    private CharSequence mContentDescription;
    private int mID = View.NO_ID;
    private PreferenceScreen mRootPreference;
    private boolean enable;
    private XdfSwitchPreference.OnLayoutClickListener mListener;

    private LayoutInflater  inflater;
    private  Context  mContext;

    public interface OnLayoutClickListener {
        /**
         * Callback when item clicked.
         *
         * @param  The view clicked.
         */
        void onLayoutClick( View view);
    }

    public XdfSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public XdfSwitchPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XdfSwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XdfSwitchPreference(Context context) {
        super(context);

//        mContext =context;
        setLayoutResource(R.layout.xdf_switch_preference);

    }

    public void setId(@IdRes int id) {
        mID = id;
    }

    public void setContentDescription(CharSequence contentDescription) {
        mContentDescription = contentDescription;
    }

//    public void setOnLayoutClickListener(XdfSwitchPreference.OnLayoutClickListener listener) {
//        mListener = listener;
//    }

    protected View onCreateView(ViewGroup parent) {
         View rootView = super.onCreateView( parent);

//        inflater = LayoutInflater.from(getContext());
//        View rootView = inflater.inflate(R.layout.xdf_switch_preference, null);
        return rootView;
    }
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
//        layout = view.findViewById( R.id.xdf_switch_preference_layout);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "bind view");
//                View view = v.findViewById(R.id.xdf_switch_preference_layout);
////                mListener.onLayoutClick( view );
//            }
//        });
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMarginStart(40);
        layoutParams.setMarginEnd(40);
        view.setLayoutParams( layoutParams );

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
