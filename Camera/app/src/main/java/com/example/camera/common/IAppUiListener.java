package com.example.camera.common;

public interface IAppUiListener {


    interface OnModeChangeListener {
        /**
         * This gets called when current mode is changed.
         *
         * @param newModeKey key of the new mode to switch to.
         */
        public void onModeSelected( String newModeKey);
    }
}
