package com.example.zhiqiang_li.test;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Zhiqiang_Li on 2017/9/7.
 */

public class EPagerView {


    private Context context;
    private ViewPager viewPager;
    private final MyPagerAdapter pagerAdapter;


    public EPagerView(Context context, ViewPager viewPager, final OnPagerSelectorListener listener, final ArrayList<View> pagerList) {
        this.context = context;
        this.viewPager = viewPager;
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                listener.onPagerSelectorListener(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        pagerAdapter = new MyPagerAdapter(pagerList);

          viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(1);

    }

    private class MyPagerAdapter extends PagerAdapter {

        private ArrayList<View> pagerList;

        public MyPagerAdapter(ArrayList<View> pagerList) {
            this.pagerList = pagerList;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagerList.get(position));
            return pagerList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView(pagerList.get(position));
        }
    }
}
