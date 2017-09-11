package com.example.zhiqiang_li.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zhiqiang_Li on 2017/9/8.
 *
 */

public class ColorPickerView extends View {

    private boolean switchPanel;

    private Rect mRect;

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private Bitmap bitmap;

    public ColorPickerView(Context context) {
        this(context,null);
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        Paint mColorPaint = new Paint();
        mColorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mColorPaint.setStrokeWidth(1);
        mColorPaint.setColor(Color.WHITE);

        mRect = new Rect();


        Paint mHuePaint = new Paint();
        mHuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mHuePaint.setStrokeWidth(1);
        mHuePaint.setColor(Color.WHITE);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawButton(canvas);

        drawColorPanel(canvas);

        drawHuePanel(canvas);
    }



    //绘制按钮
    private void drawButton(Canvas canvas) {
//     canvas.drawBitmap(bitmap,);
    }
    // 绘制颜色选择面板
    private void drawColorPanel(Canvas canvas) {
        // canvas.drawRoundRect();
    }
    // 绘制色相选择面板
    private void drawHuePanel(Canvas canvas) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void switchPanel(){

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);


    }
}
