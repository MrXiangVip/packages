package com.example.camera.effect;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.camera.common.IApp;
import com.example.camera.ui.AbstractViewManager;
import com.example.camerabg.R;

public class EffectViewManager extends AbstractViewManager {
    private ViewGroup mEffectViewGroup;

    public EffectViewManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
    }

    @Override
    protected View getView() {
        mEffectViewGroup = (ViewGroup) mParentView.findViewById(R.id.effect);
        ImageView imageView = (ImageView) mApp.getActivity().getLayoutInflater()
                .inflate(R.layout.matrix_display_entry_view, mEffectViewGroup, false);
        imageView.setImageDrawable(mApp.getActivity().getResources()
                .getDrawable(R.drawable.ic_matrix_display_entry));
        imageView.setVisibility(View.VISIBLE);
        mEffectViewGroup.addView( imageView);
        return mEffectViewGroup;
    }

}
