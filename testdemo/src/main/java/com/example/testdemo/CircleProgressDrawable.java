package com.example.testdemo;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Zhiqiang_Li on 2017/9/20.
 */

class CircleProgressDrawable extends Drawable {


    private  int mStartAngle;

    private  float mSweepAngle;

    private int mSize;

    private int mStrokeWidth;

    private int mStrokeColor;

    private Path mPath;

    private RectF mRectF;

    private Paint mPaint;

    public CircleProgressDrawable(int size , int strokeWidth , int strokeColor) {
        mSize = size;
        mStrokeWidth = strokeWidth;
        mStrokeColor = strokeColor;
        mStartAngle = -90;
        mSweepAngle = 0;
    }

    private Paint createPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mStrokeColor);
        }
        return mPaint;
    }

    public void setSweepAngle(float mSweepAngle) {
        this.mSweepAngle = mSweepAngle;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Rect bounds = getBounds();
        if (mPath == null) {
            mPath = new Path();
        }
        mPath.reset();
        mPath.addArc(getRect(),mStartAngle,mSweepAngle);
        mPath.offset(bounds.left,bounds.top);

        canvas.drawPath(mPath,createPaint());
    }



    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int i) {

    }

    public int getSize() {
        return mSize;
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    public RectF getRect() {
        if (mRectF == null) {
            int index = mStrokeWidth / 2;
            mRectF = new RectF(index,index,getSize() - index , getSize() - index);
        }
        return mRectF;
    }
}
