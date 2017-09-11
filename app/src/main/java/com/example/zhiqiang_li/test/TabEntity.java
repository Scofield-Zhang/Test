package com.example.zhiqiang_li.test;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Zhiqiang_Li on 2017/9/7.
 */

public class TabEntity implements CustomTabEntity {

    private int iconNormalResId;
    private int iconSelectedResId;

    public TabEntity(int iconNormalResId, int iconSelectedResId) {
        this.iconNormalResId = iconNormalResId;
        this.iconSelectedResId = iconSelectedResId;
    }

    public int getIconNormalResId() {
        return iconNormalResId;
    }

    public void setIconNormalResId(int iconNormalResId) {
        this.iconNormalResId = iconNormalResId;
    }

    public int getIconSelectedResId() {
        return iconSelectedResId;
    }

    public void setIconSelectedResId(int iconSelectedResId) {
        this.iconSelectedResId = iconSelectedResId;
    }

    @Override
    public String getTabTitle() {
        return null;
    }

    @Override
    public int getTabSelectedIcon() {
        return iconSelectedResId;
    }

    @Override
    public int getTabUnselectedIcon() {
        return iconNormalResId;
    }
}
