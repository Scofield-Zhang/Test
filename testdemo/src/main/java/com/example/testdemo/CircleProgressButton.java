package com.example.testdemo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.StateSet;

/**
 * Created by Zhiqiang_Li on 2017/9/19.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

public class CircleProgressButton extends android.support.v7.widget.AppCompatButton {
    public static final int START_STATE_PROGRESS = 0;

    public static final int ERROR_STATE_PROGRESS = -1;

    public static final int SUCCESS_STATE_PROGRESS = 100;

    private static final int CANCEL_STATE_PROGRESS = -2;

    private static final int PAUSE_STATE_PROGRESS = 101;

    private ColorStateList mCompleteColorStateList;

    private ColorStateList mErrorColorStateList;

    private int mColorProgress;

    private int mColorIndicator;

    private int mColorIndicatorBackground;

    private int mPaddingProgress;
    private int mStrokeWidth;
    private int mStrokeColor;

    private StrokeGradientDrawable background;

    private boolean mConfigurationChanged;

    private StateManager mStateManager;

    private StateListDrawable mIdleStateDrawable;
    private OnAnimatorEndListener onProgressStateListener = new OnAnimatorEndListener() {
        @Override
        public void OnAnimatorEnd() {
            mProgressStatus = ProgressStatus.START;
            mStateManager.checkedState(CircleProgressButton.this);
        }
    };


    private enum ProgressStatus {
        START, CANCEL, PAUSE, SUCCESS, ERROR
    }

    // 拓展
    private String progressText;

    private String idleText;

    private String errorText;

    private String completeText;

    private int mMaxProgress;

    private int mProgress;

    private CircleProgressDrawable mCircleProgressDrawable;

    private ProgressStatus mProgressStatus;

    private ColorStateList mIdleColorStatusList;
    // 圆角
    private float mCornerRadius;


    public CircleProgressButton(Context context) {
        this(context, null);
    }

    public CircleProgressButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }


    private void init(Context context, AttributeSet attrs) {

        initAttribute(context, attrs);

        mMaxProgress = 100;
        mProgressStatus = ProgressStatus.START;
        setText(idleText);

        mStateManager = new StateManager(this);
        initIdleStateDrawable();

        setBackground(mIdleStateDrawable);

    }

    private void initIdleStateDrawable() {
        int colorNormal = getNormalColor(mIdleColorStatusList);
        int colorPressed = getPressedColor(mIdleColorStatusList);
        int colorFocused = getFocusedColor(mIdleColorStatusList);
        int colorDisabled = getDisabledColor(mIdleColorStatusList);
        if (background == null) {
            background = createDrawable(colorNormal);
        }

        StrokeGradientDrawable drawablePressed = createDrawable(colorPressed);
        StrokeGradientDrawable drawableFocused = createDrawable(colorFocused);
        StrokeGradientDrawable drawableDisabled = createDrawable(colorDisabled);
        mIdleStateDrawable = new StateListDrawable();

        mIdleStateDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed.getGradientDrawable());
        mIdleStateDrawable.addState(new int[]{android.R.attr.state_focused}, drawableFocused.getGradientDrawable());
        mIdleStateDrawable.addState(new int[]{android.R.attr.state_enabled}, drawableDisabled.getGradientDrawable());
        mIdleStateDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private StrokeGradientDrawable createDrawable(int colorNormal) {
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cpb_background).mutate();
        drawable.setColor(colorNormal);
        drawable.setCornerRadius(mCornerRadius);
        StrokeGradientDrawable strokeGradientDrawable = new StrokeGradientDrawable(drawable);
        strokeGradientDrawable.setStrokeColor(colorNormal);
        strokeGradientDrawable.setStrokeWidth(mStrokeWidth);
        return strokeGradientDrawable;
    }

    private int getDisabledColor(ColorStateList mIdleColorStatusList) {
        return mIdleColorStatusList.getColorForState(new int[]{android.R.attr.state_enabled}, 0);
    }

    private int getFocusedColor(ColorStateList mIdleColorStatusList) {
        return mIdleColorStatusList.getColorForState(new int[]{android.R.attr.state_focused}, 0);
    }

    private int getPressedColor(ColorStateList mIdleColorStatusList) {
        return mIdleColorStatusList.getColorForState(new int[]{android.R.attr.state_pressed}, 0);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressButton, 0, 0);
        if (typedArray == null) {
            return;
        }

        try {
            idleText = typedArray.getString(R.styleable.CircleProgressButton_idle_text);
            progressText = typedArray.getString(R.styleable.CircleProgressButton_cpb_progress_text);
            completeText = typedArray.getString(R.styleable.CircleProgressButton_cpb_complete_text);
            errorText = typedArray.getString(R.styleable.CircleProgressButton_cpb_error_text);

            mPaddingProgress = typedArray.getDimensionPixelSize(R.styleable.CircleProgressButton_cpb_paddingProgress, 0);
            mStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.CircleProgressButton_cpb_strokeWidth, 4);
            mCornerRadius = typedArray.getDimension(R.styleable.CircleProgressButton_cpb_cornerRadius, 0);

            mStrokeColor = typedArray.getColor(R.styleable.CircleProgressButton_cpb_strokeColor, Color.BLUE);

            int idleColorStatus = typedArray.getResourceId(R.styleable.CircleProgressButton_cpb_selector_idle, R.color.cpb_idle_color_status);
            mIdleColorStatusList = getResources().getColorStateList(idleColorStatus);

            int completeColorStatus = typedArray.getResourceId(R.styleable.CircleProgressButton_cpb_selector_complete, R.color.cpb_idle_color_status);
            mCompleteColorStateList = getResources().getColorStateList(completeColorStatus);

            int errorColorStatus = typedArray.getResourceId(R.styleable.CircleProgressButton_cpb_selector_error, R.color.cpb_idle_color_status);
            mErrorColorStateList = getResources().getColorStateList(errorColorStatus);

            mColorProgress = typedArray.getColor(R.styleable.CircleProgressButton_cpb_colorProgress, Color.RED);
            mColorIndicator = typedArray.getColor(R.styleable.CircleProgressButton_cpb_colorProgress, Color.BLUE);
            mColorIndicatorBackground = typedArray.getColor(R.styleable.CircleProgressButton_cpb_colorProgress, Color.GREEN);
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            setProgress(mProgress);
        }
    }

    public void setProgress(int progress) {
        mProgress = progress;

        if (mProgress >= START_STATE_PROGRESS) {
            mProgressStatus = ProgressStatus.START;
            // 变形动画
            morphProgress();
            invalidate();
        } else if (mProgress == ERROR_STATE_PROGRESS) {
            // 下载失败
            mProgressStatus = ProgressStatus.ERROR;
        } else if (mProgress == CANCEL_STATE_PROGRESS) { // 取消下载
            // 取消界面
            mProgressStatus = ProgressStatus.SUCCESS;
            // 重新下载
        } else if (mProgress == PAUSE_STATE_PROGRESS) { // 暂停下载
            // TODO

        } else if (mProgress == SUCCESS_STATE_PROGRESS) {
            // 按钮为成功状态
            // 文字为已下载
        } else if (mProgress < START_STATE_PROGRESS) {
            // 下载出错
        }
    }

    private void morphProgress() {
        setWidth(getWidth());
        MorphingAnimation animation = createProgressMorph(mCornerRadius, getHeight(), getWidth(), getHeight());
        animation.setFromColor(getNormalColor(mIdleColorStatusList));
        animation.setToColor(mColorProgress);

        animation.setFromStrokeColor(getNormalColor(mIdleColorStatusList));
        animation.setToStrokeColor(mColorIndicatorBackground);

        animation.setListener(onProgressStateListener);
        animation.start();
    }

    private int getNormalColor(ColorStateList mIdleColorStatusList) {
        return mIdleColorStatusList.getColorForState(new int[]{android.R.attr.state_enabled}, 0);
    }

    private MorphingAnimation createProgressMorph(float fromCorner, int toCorner, int fromWidth, int toWidth) {
        // boolean
        MorphingAnimation animation = new MorphingAnimation(this, background);
        animation.setFromCornerRadius(fromCorner);
        animation.setToCornerRadius(toCorner);
        animation.setFromWidth(fromWidth);
        animation.setToWidth(toWidth);
        if (mConfigurationChanged) {
            animation.setDuration(MorphingAnimation.DURATION_INSTANT);
        } else {
            animation.setDuration(MorphingAnimation.DURATION_NORMAL);
        }

        mConfigurationChanged = false;
        return animation;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mProgress > 0 && mProgressStatus == ProgressStatus.START) {
            if (mCircleProgressDrawable == null) {
                int offset = (getWidth() - getHeight()) / 2;
                int size = getHeight() - mPaddingProgress * 2;
                mCircleProgressDrawable = new CircleProgressDrawable(size, mStrokeWidth, mStrokeColor);
            }
            float sweepAngle = (360f / mMaxProgress) * mProgress;
            mCircleProgressDrawable.setSweepAngle(sweepAngle);
            mCircleProgressDrawable.draw(canvas);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    public int getProgress() {
        return mProgress;
    }
}
