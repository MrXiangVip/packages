package com.example.camera;

import android.os.Bundle;

public class PermissionActivity extends  QuickActivity {

    protected void onPermissionCreateTasks(Bundle savedInstanceState) {
        onCreateTasks(savedInstanceState);

    }

    protected void onCreateTasks(Bundle savedInstanceState) {
    }

    protected void onPermissionResumeTasks() {
        onResumeTasks();
    }

    protected void onResumeTasks() {
    }
}