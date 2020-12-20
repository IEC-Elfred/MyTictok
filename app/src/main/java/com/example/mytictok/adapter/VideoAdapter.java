package com.example.mytictok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.mytictok.R;
import com.example.mytictok.base.BaseRvAdapter;
import com.example.mytictok.base.BaseRvViewHolder;
import com.example.mytictok.bean.VideoBean;
import com.example.mytictok.view.ControllerView;
import com.example.mytictok.view.LikeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends BaseRvAdapter<VideoBean,VideoAdapter.VideoViewHolder> {

    public VideoAdapter(Context context, List<VideoBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(VideoAdapter.VideoViewHolder holder, VideoBean data, int position) {
        holder.controllerView.setVideoData(data);
        holder.ivCover.setImageResource(data.getCoverRes());
        holder.likeView.setOnLikeListener(()->{
            if (!data.isLiked()){
                //未点赞，会有点赞效果，否则无
                holder.controllerView.like();
            }
        });
    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video,parent,false);
        return new VideoViewHolder(view);
    }

    public class VideoViewHolder extends BaseRvViewHolder {
        @BindView(R.id.likeview)
        LikeView likeView;
        @BindView(R.id.controller)
        ControllerView controllerView;
        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.iv_play)
        ImageView ivPlay;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (ivPlay.getVisibility() == View.VISIBLE)
                ivPlay.setVisibility(View.GONE);
        }
    }
}
