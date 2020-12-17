package com.example.mytictok.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.mytictok.R;
import com.example.mytictok.base.BaseRvAdapter;
import com.example.mytictok.base.BaseRvViewHolder;
import com.example.mytictok.bean.VideoBean;
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

    }

    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    public class VideoViewHolder extends BaseRvViewHolder {
        @BindView(R.id.likeview)
        LikeView likeView;
        @BindView(R.id.controller)
        ControllerView controllerView;
        @BindView(R.id.iv_cover)
        ImageView ivCover;

        public VideoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
