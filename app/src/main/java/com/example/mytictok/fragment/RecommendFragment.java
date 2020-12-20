package com.example.mytictok.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mytictok.R;
import com.example.mytictok.activity.MainActivity;
import com.example.mytictok.adapter.VideoAdapter;
import com.example.mytictok.base.BaseFragment;
import com.example.mytictok.bean.CurUserBean;
import com.example.mytictok.bean.DataCreate;
import com.example.mytictok.bean.PauseVideoEvent;
import com.example.mytictok.utils.OnVideoControllerListener;
import com.example.mytictok.utils.RxBus;
import com.example.mytictok.view.ControllerView;
import com.example.mytictok.view.FullScreenVideoView;
import com.example.mytictok.view.LikeView;
import com.example.mytictok.view.OnViewPagerListener;
import com.example.mytictok.view.ViewPagerLayoutManager;

import butterknife.BindView;
import rx.functions.Action1;


public class RecommendFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshLayout;

    private VideoAdapter adapter;
    private ViewPagerLayoutManager viewPagerLayoutManager;
    //当前播放位置
    private int curPlayPos = -1;
    private FullScreenVideoView videoView;
    private ImageView ivCurCover;
    public static int initPos = 0;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_recomment;
    }

    @Override
    protected void init() {
        adapter = new VideoAdapter(getContext(), DataCreate.datas);
        recyclerView.setAdapter(adapter);
        videoView = new FullScreenVideoView(getContext());
        setViewPagerLayoutManager();
        setRefreshEvent();

        //监听播放或暂停事件
        RxBus.getDefault().toObservable(PauseVideoEvent.class)
                .subscribe((Action1<PauseVideoEvent>) event -> {
                    if (event.isPlayOrPause()) {
                        ImageView iv_play = rootView.findViewById(R.id.iv_play);
                        videoView.start();
                        if (iv_play.getVisibility() == View.VISIBLE)
                            iv_play.setVisibility(View.GONE);
                    } else {
                        videoView.pause();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        //返回时，推荐页面可见，则继续播放视频
        if (MainActivity.curMainPage == 0 && MainFragment.curPage == 1) {
            videoView.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        videoView.stopPlayback();
    }

    private void setViewPagerLayoutManager() {
        viewPagerLayoutManager = new ViewPagerLayoutManager(getContext());
        recyclerView.setLayoutManager(viewPagerLayoutManager);
        recyclerView.scrollToPosition(initPos);
        viewPagerLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                playCurVideo(initPos);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                if (ivCurCover != null) {
                    ivCurCover.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                playCurVideo(position);
            }
        });
    }

    private void setRefreshEvent() {

        refreshLayout.setOnRefreshListener(() -> new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                refreshLayout.setRefreshing(false);
            }
        }.start());
    }

    private void playCurVideo(int position) {
        if (position == curPlayPos) {
            return;
        }
        View itemView = viewPagerLayoutManager.findViewByPosition(position);
        if (itemView == null)
            return;
        ViewGroup rootView = itemView.findViewById(R.id.rl_container);
        LikeView likeView = rootView.findViewById(R.id.likeview);
        ImageView ivPlay = rootView.findViewById(R.id.iv_play);
        ImageView ivCover = rootView.findViewById(R.id.iv_cover);
        ControllerView controllerView = rootView.findViewById(R.id.controller);
        //设置透明度
        ivPlay.setAlpha(0.4f);
        //设置单击暂停/播放事件
        likeView.setOnPlayPauseListener(() -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                ivPlay.setVisibility(View.VISIBLE);
            } else {
                videoView.start();
                ivPlay.setVisibility(View.GONE);
            }
        });
        //切换播放视频的作者主页数据
        RxBus.getDefault().post(new CurUserBean(DataCreate.datas.get(position).getUserBean()));
        curPlayPos = position;
        //切换播放器位置
        dettachParentView(rootView);
        likeShareEvent(controllerView);
        autoPlayVideo(curPlayPos, ivCover);
    }


    private void dettachParentView(ViewGroup rootView) {
        ViewGroup parent = (ViewGroup) videoView.getParent();
        if (parent != null) {
            parent.removeView(videoView);
        }
        rootView.addView(videoView, 0);
    }

    //自动播放视频
    private void autoPlayVideo(int position, ImageView ivCover) {
        String bgVideoPath;
        if (DataCreate.datas.get(position).getVideoRes() != 0) {
            bgVideoPath = "android.resource://" + getActivity().getPackageName() + "/" + DataCreate.datas.get(position).getVideoRes();
            videoView.setVideoPath(bgVideoPath);
        } else {
            Log.d("TAG", "autoPlayVideo:" + DataCreate.datas.get(position).getUri());
            videoView.setVideoURI(DataCreate.datas.get(position).getUri());
        }

        ImageView ivPlay = rootView.findViewById(R.id.iv_play);
        if (ivPlay.getVisibility() == View.VISIBLE)
            ivPlay.setVisibility(View.GONE);
        videoView.start();
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            //延迟取消封面，避免加载视频黑屏
            new CountDownTimer(200, 200) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    ivCover.setVisibility(View.GONE);
                    ivCurCover = ivCover;
                }
            }.start();
        });
    }

    /**
     * 用户操作事件
     */
    private void likeShareEvent(ControllerView controllerView) {
        controllerView.setListener(new OnVideoControllerListener() {
            @Override
            public void onHeadClick() {

            }

            @Override
            public void onLikeClick() {

            }

        });
    }

}