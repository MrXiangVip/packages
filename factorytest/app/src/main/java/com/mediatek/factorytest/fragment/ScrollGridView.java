package com.mediatek.factorytest.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;


public class ScrollGridView extends GridView  {
    public ScrollGridView(Context context){
        super(context);
    }
    public ScrollGridView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public ScrollGridView(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

        int expandSpec = MeasureSpec.makeMeasureSpec( Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
