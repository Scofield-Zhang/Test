package com.example.zhiqiang_li.test;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Zhiqiang_Li on 2017/9/17.
 */

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.ViewHolder> {

    private Context context;
    private List<PhotoBean> datas;
    private RecyclerView recyclerView;
    private LayoutInflater inflater;
    private int selectedItemPosition = -1;


    private OnItemClickListener onItemClickListener;

    public CameraAdapter(Context context, List<PhotoBean> datas, RecyclerView recyclerView) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.datas = datas;
        this.recyclerView = recyclerView;
    }

    private void setData(List<PhotoBean> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PhotoBean photoBean = datas.get(position);
        holder.cb.setVisibility(photoBean.isChecked() ? View.VISIBLE : View.INVISIBLE);
        Glide.with(context)
                .load(photoBean.getImageResId())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageView);

        holder.mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() != selectedItemPosition) {

                    ViewHolder viewHolderForLayoutPosition = (ViewHolder) recyclerView.findViewHolderForLayoutPosition(selectedItemPosition);
                    if (viewHolderForLayoutPosition != null) {
                        viewHolderForLayoutPosition.cb.setVisibility(View.INVISIBLE);
                        viewHolderForLayoutPosition.mask.setBackgroundColor(Color.TRANSPARENT);

                    } else {
                        notifyItemChanged(selectedItemPosition);
                    }

                    selectedItemPosition = selectedItemPosition == -1?0:selectedItemPosition;
                    datas.get(selectedItemPosition).setChecked(false);

                    selectedItemPosition = holder.getAdapterPosition();
                    datas.get(holder.getAdapterPosition()).setChecked(true);
                    holder.cb.setVisibility(View.VISIBLE);
                    holder.mask.setBackgroundColor(Color.argb(50, 0, 0, 0));

                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickListener(v, holder.getAdapterPosition());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final ImageView cb;
        private final View mask;

        ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.iv);
            this.cb = (ImageView) itemView.findViewById(R.id.cb);
            this.mask = itemView.findViewById(R.id.mask);
        }
    }

    void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
