package com.example.testdemo;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Zhiqiang_Li on 2017/9/20.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
class StateManager {
    private  boolean mIsEnabled;

    private  int mProgress;

    public StateManager(CircleProgressButton circleProgressButton) {
        mIsEnabled = circleProgressButton.isEnabled();
        mProgress = circleProgressButton.getProgress();
    }

    public void saveProgress(CircleProgressButton circleProgressButton){
        mProgress = circleProgressButton.getProgress();
    }

    public boolean isEnabled() {
        return mIsEnabled;
    }

    public int getProgress() {
        return mProgress;
    }

    public void checkedState(CircleProgressButton circleProgressButton) {
        if (circleProgressButton.getProgress() != getProgress()) {
            circleProgressButton.setProgress(circleProgressButton.getProgress());
        }else if (circleProgressButton.isEnabled() != isEnabled()){
            circleProgressButton.setEnabled(circleProgressButton.isEnabled());
        }
    }
}
