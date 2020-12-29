package com.example.mytictok.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.mytictok.R;
import com.example.mytictok.activity.MainActivity;
import com.example.mytictok.base.BaseRvAdapter;
import com.example.mytictok.base.BaseRvViewHolder;
import com.example.mytictok.bean.VideoBean;
import com.example.mytictok.utils.NumUtils;

import java.util.List;

import butterknife.BindView;


public class WorkAdapter extends BaseRvAdapter<VideoBean, WorkAdapter.WorkViewHolder> {

    public WorkAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(WorkViewHolder holder, VideoBean videoBean, int position) {
        holder.ivCover.setImageResource(videoBean.getCoverRes());
        holder.tvLikeCount.setText(NumUtils.numberFilter(videoBean.getLikeCount()));

        holder.itemView.setOnClickListener(v -> {
            MainActivity.initPos = position;
            context.startActivity(new Intent(context, MainActivity.class));
        });
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rooView = LayoutInflater.from(context).inflate(R.layout.item_work, parent, false);
        return new WorkViewHolder(rooView);
    }

    public class WorkViewHolder extends BaseRvViewHolder {
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_likecount)
        TextView tvLikeCount;

        public WorkViewHolder(View itemView) {
            super(itemView);
        }
    }

}
