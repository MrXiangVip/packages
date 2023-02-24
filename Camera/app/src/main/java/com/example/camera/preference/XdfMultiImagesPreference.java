package com.example.camera.preference;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.camerabg.R;


public class XdfMultiImagesPreference extends Preference {

    private PreferenceScreen mRootPreference;
    private boolean enable;
    private  OnImageClickListener mListener;
    private ImageButton scence_mode_button;
    private ImageButton white_balance_button;
    private ImageButton ais_button;
    private ImageOnClickListener imageOnClickListener= new ImageOnClickListener();
    public XdfMultiImagesPreference(Context context){
        this(context, null);
    }


    public XdfMultiImagesPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XdfMultiImagesPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }
    public XdfMultiImagesPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public interface OnImageClickListener {
        /**
         * Callback when item clicked.
         *
         * @param  The view clicked.
         */
        void onImageClick( View view);
    }



    @Override
    protected void onBindView(View view) {
        super.onBindView(view);

        scence_mode_button = view.findViewById(R.id.scence_mode );
        scence_mode_button.setOnClickListener(  imageOnClickListener );

        white_balance_button = view.findViewById( R.id.white_balance );
        white_balance_button.setOnClickListener( imageOnClickListener );

        ais_button = view.findViewById( R.id.ais );
        ais_button.setOnClickListener( imageOnClickListener );
//        ais_button.setSelected( );
    }

    private class ImageOnClickListener  implements  View.OnClickListener{

        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(),  v.getId()+"",  Toast.LENGTH_SHORT).show();
            mListener.onImageClick( v);

        }
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

    public void setAisChecked( boolean checked ){
        ais_button.setSelected( checked );
    }
    public void setRootPreference(PreferenceScreen rootPreference) {
        mRootPreference = rootPreference;
    }

    public void setOnImageClickListener( OnImageClickListener listener) {
        mListener = listener;
    }
}
