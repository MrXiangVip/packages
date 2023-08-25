package com.example.launcher.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FastBitmapDrawable extends Drawable {
    private float mScale = 1;
    protected Bitmap mBitmap;
    protected final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mScale != 1f) {
            int count = canvas.save();
            Rect bounds = getBounds();
            canvas.scale(mScale, mScale, bounds.exactCenterX(), bounds.exactCenterY());
            drawInternal(canvas, bounds);
            canvas.restoreToCount(count);
        } else {
            drawInternal(canvas, getBounds());
        }
    }
    protected void drawInternal(Canvas canvas, Rect bounds) {
        canvas.drawBitmap(mBitmap, null, bounds, mPaint);
    }
    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
