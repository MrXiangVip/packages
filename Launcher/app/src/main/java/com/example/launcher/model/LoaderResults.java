package com.example.launcher.model;

import static com.example.launcher.util.Executors.MAIN_EXECUTOR;
import  com.example.launcher.model.BgDataModel.Callbacks;
import com.example.launcher.LauncherAppState;
import com.example.launcher.util.LooperExecutor;

public class LoaderResults extends BaseLoaderResults {

    public LoaderResults(LauncherAppState app, BgDataModel dataModel,
                         AllAppsList allAppsList, Callbacks[] callbacks) {
        this(app, dataModel, allAppsList, callbacks, MAIN_EXECUTOR);
    }

    public LoaderResults(LauncherAppState app, BgDataModel dataModel,
                         AllAppsList allAppsList, Callbacks[] callbacks, LooperExecutor executor) {
        super(app, dataModel, allAppsList, callbacks, executor);
    }

}
