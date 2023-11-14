package com.example.camera.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.example.camera.common.IApp;
import com.example.camera.common.IAppUiListener.OnThumbnailClickedListener;
import com.example.camera.common.RotateImageView;
import com.example.camerabg.R;

public class ThumbnailViewManager extends AbstractViewManager{
    private final Context mContext;
    private RotateImageView mThumbnailView;
    private static final int THUMB_COLOR = 0XFF303030;
    private static final int BORDER_COLOR = 0x4D000000;


    private RoundedBitmapDrawable mRoundDrawable;
    private Bitmap mRoundedBitmap = null;
    private OnThumbnailClickedListener mOnClickListener;

    public ThumbnailViewManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
        mContext = app.getActivity();
    }

    @Override
    protected View getView() {
        mThumbnailView = (RotateImageView) mParentView
                .findViewById(R.id.thumbnail);
        mRoundDrawable = createRoundDrawable(null, THUMB_COLOR);
        mThumbnailView.setImageDrawable(mRoundDrawable);
        mThumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onThumbnailClicked();
                }
            }
        });
        return mThumbnailView;
    }
    public void setThumbnailClickedListener(OnThumbnailClickedListener listener) {
        mOnClickListener = listener;
    }

    private RoundedBitmapDrawable createRoundDrawable(Bitmap bitmap, final int color) {
        int thumbWidth = mThumbnailView.getLayoutParams().width;
        int thumbHeight = mThumbnailView.getLayoutParams().height;
        //setContentDescription is added for testing
        mThumbnailView.setContentDescription("Has Content");
        // crop a square bitmap according minimum edge.
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(thumbWidth, thumbHeight,
                    Bitmap.Config.ARGB_8888);
            mThumbnailView.setContentDescription("No Content");
        }
        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        Bitmap squareBitmap;
        if (bmpHeight > bmpWidth) {
            squareBitmap = Bitmap.createBitmap(bitmap, 0,
                    (bmpHeight - bmpWidth) / 2, bmpWidth, bmpWidth);
        } else if (bmpHeight < bmpWidth) {
            squareBitmap = Bitmap.createBitmap(bitmap,
                    (bmpWidth - bmpHeight) / 2, 0, bmpHeight, bmpHeight);
        } else {
            squareBitmap = bitmap;
        }
        // center scale to the size of thumbnail view.
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(squareBitmap,
                thumbWidth, thumbHeight, true);
        // Calculate the bitmap radius
        int borderWidthHalf = 2;
        int bitmapWidth = scaledBitmap.getWidth();
        int bitmapHeight = scaledBitmap.getHeight();
        int bitmapRadius = Math.min(bitmapWidth, bitmapHeight) / 2;
        int bitmapSquareWidth = Math.min(bitmapWidth, bitmapHeight);
        int newBitmapSquareWidth = bitmapSquareWidth - borderWidthHalf * 2;
        /*
         * Initializing a new empty bitmap. Set the bitmap size from source
         * bitmap Also add the border space to new bitmap
         */
        //if (mRoundedBitmap != null) {
        //    mRoundedBitmap.recycle();
        //    mRoundedBitmap = null;
        //}
        mRoundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth,
                newBitmapSquareWidth, Bitmap.Config.ARGB_8888);
        // Initialize a new Canvas to draw empty bitmap
        Canvas canvas = new Canvas(mRoundedBitmap);
        // Draw a solid color to canvas
        canvas.drawColor(color);
        // Calculation to draw bitmap at the circular bitmap center position
        int centerX = newBitmapSquareWidth - bitmapWidth;
        int centerY = newBitmapSquareWidth - bitmapHeight;
        /*
         * Now draw the bitmap to canvas. Bitmap will draw its center to
         * circular bitmap center by keeping border spaces
         */
        canvas.drawBitmap(scaledBitmap, centerX, centerY, null);
        // Initializing a new Paint instance to draw circular border
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidthHalf * 2);
        borderPaint.setColor(BORDER_COLOR);
        /*
         * Draw the circular border to bitmap. Draw the circle at the center of
         * canvas.
         */
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getWidth() / 2,
                newBitmapSquareWidth / 2, borderPaint);
        // Create a new RoundedBitmapDrawable
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory
                .create(mApp.getActivity().getResources(), mRoundedBitmap);
        // Set the corner radius of the bitmap drawable
        roundedBitmapDrawable.setCornerRadius(bitmapRadius);
        roundedBitmapDrawable.setAntiAlias(true);
        bitmap.recycle();
        squareBitmap.recycle();
        scaledBitmap.recycle();
        bitmap = null;
        squareBitmap = null;
        scaledBitmap = null;
        return roundedBitmapDrawable;
    }
}
