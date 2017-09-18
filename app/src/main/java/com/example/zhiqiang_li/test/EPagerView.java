package com.example.zhiqiang_li.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Zhiqiang_Li on 2017/9/7.
 */

public class EPagerView implements LoaderManager.LoaderCallbacks<Cursor> {


    private Context context;
    private ViewPager viewPager;
    private final MyPagerAdapter pagerAdapter;
    private List<PhotoBean> photoBeen;


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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class MyPagerAdapter extends PagerAdapter {

        private ArrayList<View> pagerList;
        private ATCircleView atcv;

        public MyPagerAdapter(ArrayList<View> pagerList) {
            this.pagerList = pagerList;
        }

        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View child = pagerList.get(position);
            container.addView(child);
            switch (position) {
                case 0:

                    break;
                case 1:
                    break;
                case 2:
                    obtainCamera(child);
                    break;
                case 3:
                    viewPagerPanel(child);
                    break;
            }


            return pagerList.get(position);
        }

        private void viewPagerPanel(View child) {
            SeekBar seekBar = (SeekBar) child.findViewById(R.id.seekBar);
            atcv = (ATCircleView) child.findViewById(R.id.atcv);
            seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
            RadioGroup radioGroup = (RadioGroup) child.findViewById(R.id.rg);
            radioGroup.setOnCheckedChangeListener(onCheckedListener);

        }

        private RadioGroup.OnCheckedChangeListener onCheckedListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_free_line:

                        break;
                    case R.id.rb_horizontal:

                        break;
                    case R.id.rb_vertical:

                        break;
                    case R.id.rb_diagonal:

                        break;
                    case R.id.rb_kaleidoscope:

                        break;
                }
            }
        };

        private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                atcv.setRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pagerList.get(position));
        }
    }

    private static final String cameraPath = Environment.getExternalStoragePublicDirectory(Environment.
            DIRECTORY_DCIM).getAbsolutePath() + File.separator + "Camera";

    private void obtainCamera(final View child) {
        photoBeen = new ArrayList<>();
        RecyclerView recycler = (RecyclerView) child.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        // recycler.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,5, Color.WHITE));
        File file = new File(cameraPath);
        String[] list = file.list();
        int number = list.length >= 20 ? 20 : list.length;
        for (int i = 0; i < number; i++) {
            getPhotoInfo(list[i]);
        }
        Collections.sort(photoBeen);
        CameraAdapter mAdapter = new CameraAdapter(context, photoBeen, recycler);
        recycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(context, "点击：position" + position + ",----->" + photoBeen.get(position).getImageResId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPhotoInfo(String aList) {
        try {
            ExifInterface exifInterface = new ExifInterface(cameraPath + File.separator + aList);
            String attribute = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            photoBeen.add(new PhotoBean(false, cameraPath + File.separator + aList, StrToDate(attribute)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
