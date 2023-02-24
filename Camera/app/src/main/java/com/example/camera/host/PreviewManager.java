package com.example.camera.host;

import com.example.camerabg.R;
import com.example.camera.common.IApp;
import com.example.camera.common.PreviewFrameLayout;

public class PreviewManager {

    private IApp mApp;
    private PreviewFrameLayout mPreviewFrameLayout;

    public PreviewManager(IApp app) {
        mApp = app;

        mPreviewFrameLayout =
                (PreviewFrameLayout) mApp.getActivity().findViewById(R.id.preview_layout_container);
    }
}
