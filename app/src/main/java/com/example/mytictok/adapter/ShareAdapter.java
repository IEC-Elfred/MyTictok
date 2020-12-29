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
import com.example.mytictok.bean.ShareBean;

import java.util.List;

import butterknife.BindView;

/**
 * create by libo
 * create on 2020-05-25
 * description
 */
public class ShareAdapter extends BaseRvAdapter<ShareBean, ShareAdapter.ShareViewHolder> {

    public ShareAdapter(Context context, List<ShareBean> datas) {
        super(context, datas);
    }

    @Override
    protected void onBindData(ShareViewHolder holder, ShareBean shareBean, int position) {
        holder.tvIcon.setText(shareBean.getIconRes());
        holder.tvText.setText(shareBean.getText());
        holder.viewBg.setBackgroundResource(shareBean.getBgRes());
    }

    @NonNull
    @Override
    public ShareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_share, parent, false);
        return new ShareViewHolder(view);
    }

    public class ShareViewHolder extends BaseRvViewHolder {
        @BindView(R.id.tv_icon)
        TextView tvIcon;
        @BindView(R.id.tv_text)
        TextView tvText;
        @BindView(R.id.view_bg)
        View viewBg;

        public ShareViewHolder(View itemView) {
            super(itemView);
        }
    }
}
