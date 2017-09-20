package com.example.testdemo;

import android.graphics.drawable.GradientDrawable;

/**
 * Created by Zhiqiang_Li on 2017/9/20.
 */

public class StrokeGradientDrawable {

    private int mStrokeWidth;

    private int mStrokeColor;

    private GradientDrawable mGradientDrawable;

    public StrokeGradientDrawable(GradientDrawable drawable) {
        this.mGradientDrawable = drawable;
    }

    public int getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        mGradientDrawable.setStroke(strokeWidth,getStrokeColor());
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.mStrokeColor = strokeColor;
        mGradientDrawable.setStroke(getStrokeWidth(),strokeColor);
    }

    public GradientDrawable getGradientDrawable() {
        return mGradientDrawable;
    }
}
