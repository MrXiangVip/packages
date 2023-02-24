package com.example.launcher.graphics;

import android.view.View;

import com.example.launcher.widget.Workspace;

public class WorkspaceAndHotseatScrim extends Scrim{

    private Workspace mWorkspace;


    public WorkspaceAndHotseatScrim(View view) {
        super(view);

//        mMaskHeight = ResourceUtils.pxFromDp(ALPHA_MASK_BITMAP_DP,
//                view.getResources().getDisplayMetrics());
//        mTopScrim = Themes.getAttrDrawable(view.getContext(), R.attr.workspaceStatusBarScrim);
//        mBottomMask = mTopScrim == null ? null : createDitheredAlphaMask();
//        mHideSysUiScrim = mTopScrim == null;
//
//        onExtractedColorsChanged(mWallpaperColorInfo);
    }
    public void setWorkspace(Workspace workspace)  {
        mWorkspace = workspace;
    }

}
