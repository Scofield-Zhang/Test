package com.example.zhiqiang_li.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zhiqiang_Li on 2017/9/17.
 */

public class ATCircleView extends View {

    private Paint mPaint;
    // 圆的半径
    private int radius;
    // 是否描边
    private boolean isStroke;
    // 描边的颜色
    private int strokeColor;
    // 描边的宽度
    private int strokeWidth;
    // 填充的颜色
    private int solidColor;
    private Paint sPaint;

    public ATCircleView(Context context) {
        this(context, null);
    }

    public ATCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ATCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.ATCircleView);
        isStroke = typedArray.getBoolean(R.styleable.ATCircleView_isStroke, false);
        radius = typedArray.getDimensionPixelSize(R.styleable.ATCircleView_isStroke, 10);
        strokeColor = typedArray.getColor(R.styleable.ATCircleView_isStroke, Color.GRAY);
        solidColor = typedArray.getColor(R.styleable.ATCircleView_solidColor, Color.WHITE);
        strokeWidth = typedArray.getColor(R.styleable.ATCircleView_strokeWidth, 2);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(solidColor);

        if (isStroke) {
            sPaint = new Paint();
            sPaint.setAntiAlias(true);
            sPaint.setStyle(Paint.Style.STROKE);
            sPaint.setStrokeWidth(strokeWidth);
            sPaint.setColor(strokeColor);
        }
    }


    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setStroke(boolean stroke) {
        isStroke = stroke;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setSolidColor(int solidColor) {
        this.solidColor = solidColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getMeasuredWidth() /  2,getMeasuredHeight() / 2, radius, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
