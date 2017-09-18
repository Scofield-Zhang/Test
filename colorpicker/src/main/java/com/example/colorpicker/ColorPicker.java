package com.example.colorpicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zhiqiang_Li on 2017/9/18.
 */

public class ColorPicker extends View {

    private RectF mRect;

    private Paint mPaint;

    private LinearGradient linearGradient;

    private final int border_width = 10;


    private Point mStartTouchPoint = null;
    // 色相
    private float hue = 360f;
    // 饱和度
    private int sat;
    // 明度
    private int val;
    // hue shader
    private Shader mHueShader;
    // 描边的画笔
    private Paint bPaint;
    // 描边的颜色
    private int borderColor = Color.BLACK;

    private boolean isSatPanel;


    public ColorPicker(Context context) {
        this(context, null);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {


        mRect = new RectF();
        mPaint = new Paint();


        bPaint = new Paint();
        bPaint.setStyle(Paint.Style.STROKE);
        bPaint.setStrokeWidth(border_width);
        bPaint.setColor(borderColor);
        bPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        mRect.left = getPaddingLeft() + border_width;
        mRect.top = getPaddingTop() + border_width;
        mRect.right = w - getPaddingRight() - border_width;
        mRect.bottom = h - getPaddingBottom() - border_width;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(500, 100);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRect, bPaint);


        if (mHueShader == null) {
            int[] hue = new int[361];
            int count = 0;
            for (int i = hue.length - 1; i >= 0; i--, count++) {
                hue[count] = Color.HSVToColor(new float[]{i, 1f, 1f});
            }

            mHueShader = new LinearGradient(
                    mRect.left,
                    mRect.top,
                    mRect.right,
                    mRect.bottom,
                    hue,
                    null,
                    Shader.TileMode.CLAMP);
            mPaint.setShader(mHueShader);
        }
        canvas.drawRect(mRect, mPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isUpdated = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartTouchPoint = new Point((int) event.getY(), (int) event.getX());
                isUpdated = moveTrackersIfNeeded(event, isSatPanel);
                break;
            case MotionEvent.ACTION_MOVE:
                isUpdated = moveTrackersIfNeeded(event, isSatPanel);
                break;
            case MotionEvent.ACTION_UP:
                mStartTouchPoint = null;
                isUpdated = moveTrackersIfNeeded(event, isSatPanel);
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean moveTrackersIfNeeded(MotionEvent event, boolean isSatPanel) {
        if (mStartTouchPoint == null)
            return false;
        boolean update = false;
        int startX = mStartTouchPoint.x;
        int startY = mStartTouchPoint.y;
        if (isSatPanel) {
            if (mRect.contains(startX, startY)) {
                hue = pointToHue(startX);
                update = true;
            } else {

            }

        }

        return update;
    }

    private float pointToHue(float startX) {
        final RectF rect = mRect;
        float width = rect.width();
        if (startX < rect.left) {
            startX = 0f;
        } else if (startX > rect.right) {
            startX = width;
        } else {
            startX = startX - rect.left;
        }
        return 360f - (startX * 360 / width);
    }

}
