package com.example.zhiqiang_li.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

/**
 * Created by Zhiqiang_Li on 2017/9/7.
 *
 */

public class EmojiLayout extends LinearLayout implements OnTabSelectListener {
    public final String TAG = this.getClass().getSimpleName();

    private int[] iconNormal = {
            R.drawable.texttz_icon_normal,
            R.drawable.modetz_icon_normal,
            R.drawable.image_icon_normal,
            R.drawable.brush_icon_normal
    };
    private int[] iconSelected = {
            R.drawable.texttz_icon_selected,
            R.drawable.modetz_icon_selected,
            R.drawable.image_icon_selected,
            R.drawable.brush_icon_selected
    };
    private ArrayList<CustomTabEntity> mTabEntities;

    private CommonTabLayout tabLayout;
    private ViewPager viewpager;
    private boolean loaded = false;
    private HorizontalScrollView scrollView;
    private Context context;
    private LinearLayout container;
    private int emojiBtnWidth = 100;
    private int emojiBtnHeight = 100;

    private ArrayList<View> pagerList;
    private OnClickListener onTabCheckListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            onStickersPackSelected(view.getId());
        }
    };
    private EPagerView ePagerView;

    private OnPagerSelectorListener listener = new OnPagerSelectorListener() {
        @Override
        public void onPagerSelectorListener(int position) {
            tabLayout.setCurrentTab(position);
            if (position ==0 || position ==1){
                parent.setVisibility(VISIBLE);
            }else {
                parent.setVisibility(GONE);
            }
        }
    };
    private LinearLayout parent;

    public EmojiLayout(Context context) {
        this(context,null);
    }

    public EmojiLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmojiLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflate.inflate(R.layout.fragment_data, this);


        View pagerview0 = inflate.inflate(R.layout.viewpager0, null);
        View pagerview1 = inflate.inflate(R.layout.viewpager1, null);
        View pagerview2 = inflate.inflate(R.layout.viewpager2, null);
        View pagerview3 = inflate.inflate(R.layout.viewpager3, null);

        pagerList = new ArrayList<>();
        pagerList.add(pagerview0);
        pagerList.add(pagerview1);
        pagerList.add(pagerview2);
        pagerList.add(pagerview3);


        mTabEntities = new ArrayList<>();
    }

    public void showData() {
        if(loaded){
            return;
        }
        loadStickers();
        loaded = true;
        onStickersPackSelected(0);
    }

    private void loadStickers() {
        // 获取数据
        // 添加包按钮
        int index = 0;
        CheckedImageButton checkedImageButton = addCheckButton(index++, onTabCheckListener);
        checkedImageButton.setImageResource(R.mipmap.ic_launcher);
    }

    private CheckedImageButton addCheckButton(int index ,OnClickListener onClickListener){
        CheckedImageButton emotBtn = new CheckedImageButton(context);

       container.addView(emotBtn);
        emotBtn.setNormalBkResId(R.drawable.nim_sticker_button_background_normal_layer_list);
        emotBtn.setCheckedBkResId(R.drawable.nim_sticker_button_background_pressed_layer_list);
        emotBtn.setId(index);
        emotBtn.setOnClickListener(onClickListener);
        emotBtn.setScaleType(ImageView.ScaleType.FIT_CENTER);

        emotBtn.setPaddingValue(15);
        ViewGroup.LayoutParams emojBtnLayoutParams = emotBtn.getLayoutParams();
        emojBtnLayoutParams.width = emojiBtnWidth;
        emojBtnLayoutParams.height = emojiBtnHeight;
        emotBtn.setLayoutParams(emojBtnLayoutParams);

        return emotBtn;
    }

    private void onStickersPackSelected(int index){
        updatePackButton(index);
        showViewPager(index);
    }

    private void updatePackButton(int index) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View childAt = container.getChildAt(i);
            if (childAt!= null && childAt instanceof CheckedImageButton) {
                CheckedImageButton checkedImageButton = (CheckedImageButton) childAt;
                if (checkedImageButton.isChecked() && i != index) {
                    checkedImageButton.setChecked(false);
                }else if (!checkedImageButton.isChecked() && i == index){
                    checkedImageButton.setChecked(true);
                }
            }
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tabLayout = (CommonTabLayout) findViewById(R.id.tabLayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        container = (LinearLayout) findViewById(R.id.container);
        parent = (LinearLayout) findViewById(R.id.parent);
        for (int i = 0; i < iconNormal.length; i++) {
            mTabEntities.add(new TabEntity(iconNormal[i],iconSelected[i]));
        }
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(this);
    }


    private void showViewPager(int index){
        if (ePagerView == null) {
            ePagerView = new EPagerView(context, viewpager,  listener,pagerList);
        }
    }

    @Override
    public void onTabSelect(int position) {
        Log.d(TAG, "onTabSelect: "+position);
        viewpager.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }

}
