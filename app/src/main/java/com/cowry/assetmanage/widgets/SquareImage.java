package com.cowry.assetmanage.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by acer on 2016/6/27.
 */
public class SquareImage extends ImageView {
    public SquareImage(Context context) {
        super(context);
    }

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,widthMeasureSpec);
    }
}
