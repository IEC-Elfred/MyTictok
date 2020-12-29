package com.example.mytictok.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mytictok.R;
import com.example.mytictok.base.BaseRvAdapter;
import com.example.mytictok.base.BaseRvViewHolder;
import com.example.mytictok.bean.VideoBean;
import com.example.mytictok.view.CircleImageView;

import java.util.List;

import butterknife.BindView;

public class FansAdapter extends BaseRvAdapter<VideoBean.UserBean, FansAdapter.FansViewHolder> {

    public FansAdapter(Context context, List<VideoBean.UserBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(FansViewHolder holder, VideoBean.UserBean userBean, int position) {
        holder.ivHead.setImageResource(userBean.getHead());
        holder.tvNickname.setText(userBean.getNickName());
        holder.tvFocus.setText(userBean.isFocused() ? "已关注" : "关注");

        holder.tvFocus.setOnClickListener(v -> {
            if (!userBean.isFocused()) {
                holder.tvFocus.setText("已关注");
                holder.tvFocus.setBackgroundResource(R.drawable.shape_round_halfwhite);
            } else {
                holder.tvFocus.setText("关注");
                holder.tvFocus.setBackgroundResource(R.drawable.shape_round_red);
            }

            userBean.setFocused(!userBean.isFocused());
        });
    }

    @NonNull
    @Override
    public FansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fans, parent, false);
        return new FansViewHolder(view);
    }

    public class FansViewHolder extends BaseRvViewHolder {
        @BindView(R.id.iv_head)
        CircleImageView ivHead;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.tv_focus)
        TextView tvFocus;

        public FansViewHolder(View itemView) {
            super(itemView);
        }
    }
}
