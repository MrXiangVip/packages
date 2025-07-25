package com.example.myapplication.drag;

import android.app.Activity;
import android.content.ClipData;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class DragActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag);
        // 初始化源视图（可拖拽的图片）
        ImageView draggableImageView = findViewById(R.id.draggableImageView);
        draggableImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 创建 ClipData（携带图片 URI）
                ClipData clipData = ClipData.newUri(
                        getContentResolver(),
                        "dragged_image",
                        Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.bg_joystick)
                );

                // 启动拖拽
                v.startDragAndDrop(clipData,
                        new View.DragShadowBuilder(v),
                        null,
                        0
                );
                return true;
            }
        });

// 初始化目标容器
        FrameLayout targetContainer = findViewById(R.id.targetContainer);
        targetContainer.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        // 处理图片放置
                        ImageView droppedImage = new ImageView(DragActivity.this);
                        droppedImage.setImageResource(R.drawable.bg_joystick);
                        ((FrameLayout) v).addView(droppedImage);
                        return true;
                }
                return true;
            }
        });

    }


}
