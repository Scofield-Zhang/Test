package com.example.zhiqiang_li.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Zhiqiang_Li on 2017/9/17.
 */

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.ViewHolder> {

    private Context context;
    private List<PhotoBean> datas;
    private LayoutInflater inflater;

    public CameraAdapter(Context context, List<PhotoBean> datas) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.datas = datas;
    }

    private void setData(List<PhotoBean> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotoBean photoBean = datas.get(position);
        holder.cb.setChecked(photoBean.isChecked());
        Glide.with(context)
                .load(photoBean.getImageResId())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final CheckBox cb;

        ViewHolder(View itemView) {
           super(itemView);
           this.imageView = (ImageView) itemView.findViewById(R.id.iv);
           this.cb = (CheckBox)itemView.findViewById(R.id.cb);
        }
    }
}
