package com.example.mytictok.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytictok.R;
import com.example.mytictok.adapter.GridVideoAdapter;
import com.example.mytictok.base.BaseFragment;
import com.example.mytictok.bean.DataCreate;

import butterknife.BindView;


public class CurrentFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshLayout;
    private GridVideoAdapter adpter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_recomment;
    }

    @Override
    protected void init() {
        new DataCreate().initData();
        //瀑布流式布局，可以实现交错
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adpter = new GridVideoAdapter(getContext(),DataCreate.datas);
        recyclerView.setAdapter(adpter);
        refreshLayout.setOnRefreshListener(()->{
            new CountDownTimer(500,500){

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    refreshLayout.setRefreshing(false);
                }
            }.start();
        });
    }
}