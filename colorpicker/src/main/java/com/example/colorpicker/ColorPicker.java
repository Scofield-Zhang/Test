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
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zhiqiang_Li on 2017/9/18.
 */
@SuppressLint("DrawAllocation")
public class ColorPicker extends View {

    private static final int CHANGE_COLOR = 122;
    private RectF mRect;

    private Paint mPaint;

    private final int border_width = 10;


    private Point mStartTouchPoint = null;
    // 色相
    private float hue = 180;
    // 饱和度
    private float sat = 1f;
    // 明度
    private float val = 1f;
    // hue shader
    private Shader mHueShader;
    // 描边的画笔
    private Paint bPaint;
    // 描边的颜色
    private int borderColor = Color.BLACK;

    private boolean isSatPanel;

    private Paint cPaint;
    // 按钮圆的半径
    private int btnRadius;
    // 间隔
    private int spacing;
    //明暗shader
    private Shader mValShader;
    // 控件的宽
    private int WIDTH;
    // 控件的高
    private int COLOR_WIDTH;

    private int HEIGHT;
    // 指示器圆角的大小
    private int roundCornerSize = 20;

    private RectF rectF;

    //指示圆环1
    private int indicatorRadius;
    // 指示圆环2
    private int oldRadius;

    private Paint iPaint;

    private Paint oPaint;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == CHANGE_COLOR){
                iPaint.setColor(Color.HSVToColor(new float[]{hue, sat, val}));

            }
        }
    };


    public ColorPicker(Context context) {
        this(context, null);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WIDTH = dpToPx(context, 330);
        HEIGHT = dpToPx(context, 32);
        COLOR_WIDTH = dpToPx(context, 250);
        indicatorRadius = dpToPx(context, 13);
        oldRadius = dpToPx(context, 10);
        spacing = dpToPx(context, 30);
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

        cPaint = new Paint();
        cPaint.setStyle(Paint.Style.FILL);
        cPaint.setAntiAlias(true);

        iPaint = new Paint();

        oPaint = new Paint();
        oPaint.setColor(Color.RED);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        btnRadius = getMeasuredHeight() / 3;

        mRect.left = getPaddingLeft() + border_width + btnRadius * 2 + spacing;
        mRect.top = getPaddingTop() + border_width + roundCornerSize / 2;
        mRect.right = COLOR_WIDTH - border_width - roundCornerSize / 2;
        mRect.bottom = h - getPaddingBottom() - border_width - roundCornerSize / 2;

        cPaint.setShader(new SweepGradient(btnRadius, getMeasuredHeight() / 2, new int[]{Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN}, null));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(WIDTH, HEIGHT);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(WIDTH, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, HEIGHT);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(mRect, bPaint);

        if (isSatPanel) {
            rectF = drawSatAndValShader();
        } else {
            rectF = drawHueShader();
        }
        canvas.drawRect(mRect, mPaint);

        canvas.drawCircle(btnRadius, getMeasuredHeight() / 2, btnRadius, cPaint);

        drawIndicator(canvas, rectF);


        canvas.drawCircle(getMeasuredWidth() - getPaddingRight() - indicatorRadius - oldRadius,
                oldRadius,
                oldRadius,
                oPaint
        );

        canvas.drawCircle(getMeasuredWidth() - getPaddingRight() - indicatorRadius,
                getMeasuredHeight() - indicatorRadius,
                indicatorRadius,
                iPaint
        );



        rectF = null;
    }

    private RectF drawSatAndValShader() {
        LinearGradient composeShader = generateSatAndValShader();
        mPaint.setShader(composeShader);

        Point p = satAndValToPoint(sat, val);

        RectF rectF = new RectF();

        rectF.left = p.x - roundCornerSize / 2;
        rectF.top = mRect.top - border_width;
        rectF.right = p.x + roundCornerSize / 2;
        rectF.bottom = mRect.bottom + roundCornerSize / 2;
        return rectF;
    }

    private Point satAndValToPoint(float sat, float val) {
        final RectF rectF = mRect;
        float width = mRect.width();
        Point p = new Point();
        p.y = (int) rectF.top;
        if (sat == 1) {
            p.x = (int) (rectF.left + (int) (val * width / 2));
        } else {
            p.x = (int) (rectF.left + (width - sat * width / 2));
        }
        return p;
    }

    private LinearGradient generateSatAndValShader() {
        if (mValShader == null) {
            mValShader = new LinearGradient(mRect.left, mRect.top, mRect.right, mRect.top, 0xffffffff, 0xff000000, Shader.TileMode.CLAMP);
        }
        int rgb = Color.HSVToColor(new float[]{hue, 1f, 1f});

        return new LinearGradient(
                mRect.left,
                mRect.top,
                mRect.right,
                mRect.top,
                new int[]{0xff000000, rgb, 0xffffffff},
                null,
                Shader.TileMode.CLAMP);
    }


    private RectF drawHueShader() {
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
                    mRect.top,
                    hue,
                    null,
                    Shader.TileMode.CLAMP);
        }
        mPaint.setShader(mHueShader);

        Point p = HueToPoint(hue);
        RectF rectF = new RectF();

        rectF.left = p.x - roundCornerSize / 2;
        rectF.top = mRect.top - border_width;
        rectF.right = p.x + roundCornerSize / 2;
        rectF.bottom = mRect.bottom + border_width;

        return rectF;
    }

    private Point HueToPoint(float hue) {
        final RectF rectF = mRect;
        float width = mRect.width();
        Point point = new Point();
        point.x = (int) (rectF.left + width - (hue * width / 360f));
        point.y = (int) rectF.top;
        return point;
    }

    private void drawIndicator(Canvas canvas, RectF rect) {
        canvas.drawRoundRect(rect, rect.width() / 2, rect.width() / 2, bPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isUpdated = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartTouchPoint = new Point((int) event.getX(), (int) event.getY());
                isUpdated = moveTrackersIfNeeded(event, isSatPanel);
                break;
            case MotionEvent.ACTION_MOVE:
                isUpdated = moveTrackersIfNeeded(event, isSatPanel);
                break;
            case MotionEvent.ACTION_UP:
                mStartTouchPoint = null;
                moveTrackersIfNeeded(event, isSatPanel);
                break;
        }
        if (isUpdated) {
            invalidate();
            if (listener != null) {
                mHandler.sendEmptyMessage(CHANGE_COLOR);
                if (isSatPanel) {
                    mHandler.sendEmptyMessage(CHANGE_COLOR);
//                    listener.onColorDrawableEnd(Color.HSVToColor(new float[]{hue, sat, val}));
                }
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    private boolean moveTrackersIfNeeded(MotionEvent event, boolean isSatValPanel) {
        if (mStartTouchPoint == null)
            return false;
        boolean update = false;
        int startX = mStartTouchPoint.x;
        int startY = mStartTouchPoint.y;

        if (mRect.contains(startX, startY)) {
            if (!isSatValPanel) {
                hue = pointToHue(event.getX());
                update = true;
            } else {
                float[] floats = pointToSatVal(event.getX(), event.getY());
                sat = floats[0];
                val = floats[1];
                Log.d("moveTrackersIfNeeded", "moveTrackersIfNeeded: sat" + sat + "-------val" + val);
                update = true;
            }
        } else if (event.getX() > 0 && event.getX() <= btnRadius * 2 && event.getY() > 0 && event.getY() <= getMeasuredHeight()
                && MotionEvent.ACTION_DOWN == event.getAction()) {
            isSatPanel = !isSatPanel;
            update = true;

            invalidate();
        }else if (event.getX() > getMeasuredWidth() - indicatorRadius - oldRadius && event.getX() < getMeasuredWidth()
                && event.getY() > 0 && event.getY() <= getMeasuredHeight() && MotionEvent.ACTION_DOWN == event.getAction()){

        }

        return update;
    }

    private float[] pointToSatVal(float x, float y) {

        final RectF rect = mRect;
        float[] result = new float[2];
        float width = rect.width();
        if (x < rect.left) {
            x = rect.left;
        } else if (x > rect.right) {
            x = rect.right;
        }
        float dx = x - rect.left;
        if (dx <= width / 2) {
            result[0] = 1;
            result[1] = 2 * dx / width;
        } else {
            result[0] = 2 - 2 * dx / width;
            result[1] = 1;
        }
        return result;
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

    private int dpToPx(Context c, float dipValue) {
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();
        float val = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
        int res = (int) (val + 0.5); // Round
        // Ensure at least 1 pixel if val was > 0
        return res == 0 && val > 0 ? 1 : res;
    }



    private OnColorDrawEndListener listener;

    public interface OnColorDrawEndListener {
        void onColorDrawableEnd(int color);
    }

    public void setOnColorDrawEndListener(OnColorDrawEndListener listener) {
        this.listener = listener;
    }
}
