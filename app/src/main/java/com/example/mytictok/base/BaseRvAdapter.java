package com.example.mytictok.base;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseRvAdapter<T,VH extends BaseRvViewHolder> extends RecyclerView.Adapter<VH> {
    protected Context context;
    protected List<T> mDatas;
    protected OnItemClickListener onItemClickListener;


    public BaseRvAdapter(Context context, List<T> datas) {
        this.context = context;
        mDatas = datas;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        //item点击事件获取
//        if (onItemClickListener != null) {
//            holder.itemView.setOnClickListener(v -> {
//                onItemClickListener.onItemClick(v, position);
//            });
//        }
        onBindData(holder,mDatas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected abstract void onBindData(VH holder, T data, int position);


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }


}