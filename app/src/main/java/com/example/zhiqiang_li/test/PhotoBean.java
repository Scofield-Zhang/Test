package com.example.zhiqiang_li.test;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Zhiqiang_Li on 2017/9/17.
 */

public class PhotoBean implements  Comparable<Object>{
    private boolean isChecked;
    private String ImageResId;
    private Date time;

    public PhotoBean(boolean isChecked, String imageResId, Date time) {
        this.isChecked = isChecked;
        ImageResId = imageResId;
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getImageResId() {
        return ImageResId;
    }

    public void setImageResId(String imageResId) {
        ImageResId = imageResId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        if (this == o) {
            return 0;
        } else if (o != null && o instanceof PhotoBean) {
            PhotoBean u = (PhotoBean) o;
            if (u.getTime().getTime() < this.time.getTime()) //根据date进行排序
                return -1;
            else
                return 1;
        } else {
            return -1;
        }
    }
}
