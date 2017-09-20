package com.example.drawablechange;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Zhiqiang_Li on 2017/9/20.
 */

public class MyView extends View {
    StateListDrawable msStateListDrawable;
    private GradientDrawable mdDrawable;

    public MyView(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        mdDrawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{Color.BLACK,Color.RED});
//
//        msStateListDrawable = new StateListDrawable();
//
//        msStateListDrawable.addState(new int[]{android.R.attr.state_pressed},mdDrawable);
//
//        setBackground(msStateListDrawable);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Log.d("drawableStateChanged", "drawableStateChanged: ");
    }
}
