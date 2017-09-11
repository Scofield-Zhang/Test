package com.example.zhiqiang_li.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhiqiang_Li on 2017/9/7.
 *
 */

public class DetailFragment extends Fragment {


    private static final String TEST_DATA = "data";
    private TextView textView;
    private String data;
    private List<Integer> stickersThumb= new ArrayList<>();
    private SparseArray<String> stickersList = new SparseArray<>();

    private OnCheckedListener listener = new OnCheckedListener() {

        @Override
        public void onItemCheckedListener(int position) {
            String s = stickersList.get(position);
            Toast.makeText(getActivity(),s+position,Toast.LENGTH_SHORT).show();
        }
    };

    public static DetailFragment getNewInstance(String s) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TEST_DATA,s);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments!=null) {
            data = arguments.getString(TEST_DATA);
        }

        return inflater.inflate(R.layout.fragment_data,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }
}
