package com.example.testdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

/**
 * Created by Zhiqiang_Li on 2017/9/20.
 * <p>
 * 变形drawable
 */

public class MorphingAnimation {

    public static final int DURATION_NORMAL = 400;
    public static final int DURATION_INSTANT = 400;

    private TextView mView;

    private StrokeGradientDrawable mDrawable;

    private int mFromWidth;
    private int mToWidth;
    private float mPadding;
    private int mDuration;
    private OnAnimatorEndListener listener;
    private int mFromStrokeColor;
    private int mToStrokeColor;
    private float mFromCornerRadius;
    private float mToCornerRadius;
    private int mFromColor;
    private int mToColor;

    public void setFromColor(int mFromColor) {
        this.mFromColor = mFromColor;
    }

    public void setToColor(int mToColor) {
        this.mToColor = mToColor;
    }

    public MorphingAnimation(TextView textView, StrokeGradientDrawable drawable) {
        mView = textView;
        this.mDrawable = drawable;
    }

    public void setFromWidth(int mFromWidth) {
        this.mFromWidth = mFromWidth;
    }

    public void setToWidth(int mToWidth) {
        this.mToWidth = mToWidth;
    }

    public void setPadding(float mPadding) {
        this.mPadding = mPadding;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public void setListener(OnAnimatorEndListener listener) {
        this.listener = listener;
    }

    public void setFromStrokeColor(int mFromStrokeColor) {
        this.mFromStrokeColor = mFromStrokeColor;
    }

    public void setToStrokeColor(int mToStrokeColor) {
        this.mToStrokeColor = mToStrokeColor;
    }

    public void setFromCornerRadius(float mFromCornerRadius) {
        this.mFromCornerRadius = mFromCornerRadius;
    }

    public void setToCornerRadius(float mToCornerRadius) {
        this.mToCornerRadius = mToCornerRadius;
    }

    protected void start() {
        final ValueAnimator mValueAnimator = ValueAnimator.ofInt(mFromWidth, mToWidth);
        final GradientDrawable gradientDrawable = mDrawable.getGradientDrawable();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // 当前的进度的值
                Integer value = (Integer) valueAnimator.getAnimatedValue();
                int leftOffset;
                int rightOffset;
                int padding;
                if (mFromWidth > mToWidth) {
                    leftOffset = (mFromWidth - value) / 2;
                    rightOffset = mFromWidth - leftOffset;
                    // 得到动画的百分比 设置padding 值
                    padding = (int) (mPadding - mPadding * valueAnimator.getAnimatedFraction());
                } else {
                    leftOffset = (mToWidth - value) / 2;
                    rightOffset = mToWidth - leftOffset;
                    // 得到动画的百分比 设置padding 值
                    padding = (int) (mPadding - mPadding * valueAnimator.getAnimatedFraction());
                }

                gradientDrawable.setBounds(leftOffset + padding, padding, rightOffset - padding, mView.getHeight() - padding);
            }
        });




        ObjectAnimator bgObjectAnimator = ObjectAnimator.ofInt(gradientDrawable, "color", mFromColor, mToColor);
        bgObjectAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator strokeColorAnimator = ObjectAnimator.ofInt(mDrawable, "strokeColor", mFromStrokeColor, mToStrokeColor);
        strokeColorAnimator.setEvaluator(new ArgbEvaluator());


        ObjectAnimator cornerAnimator = ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", mFromCornerRadius, mToCornerRadius);

        AnimatorSet mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setDuration(mDuration);
        mAnimatorSet.playTogether(mValueAnimator,bgObjectAnimator, strokeColorAnimator, cornerAnimator);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (listener != null) {
                    listener.OnAnimatorEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        mAnimatorSet.start();

    }

}
