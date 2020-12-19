package com.example.mytictok.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerLayoutManager extends LinearLayoutManager {
    private PagerSnapHelper mPagerSnapHelper;
    private RecyclerView mRecyclerview;
    private OnViewPagerListener mOnViewPagerListener;
    //位移，用来判断移动方向
    private int mDrift;

    public ViewPagerLayoutManager(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPagerSnapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mPagerSnapHelper.attachToRecyclerView(view);
        this.mRecyclerview = view;
        mRecyclerview.addOnChildAttachStateChangeListener(onChildAttachStateChangeListener);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    //滑动状态改变监听
    @Override
    public void onScrollStateChanged(int state) {
        switch (state) {
            case RecyclerView.SCROLL_STATE_IDLE:
                View viewIdle = mPagerSnapHelper.findSnapView(this);
                if (viewIdle == null) {
                    return;
                }
                int positionIdle = getPosition(viewIdle);
                if (mOnViewPagerListener != null && getChildCount() == 1) {
                    mOnViewPagerListener.onPageSelected(positionIdle, positionIdle == getItemCount() - 1);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                View viewDrag = mPagerSnapHelper.findSnapView(this);
                if (viewDrag != null) {
                    int positionDrag = getPosition(viewDrag);
                }
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                View viewSettling = mPagerSnapHelper.findSnapView(this);
                if (viewSettling != null) {
                    int positionSettling = getPosition(viewSettling);
                }
                break;
        }
    }

    //监听竖直方向的相对偏移量
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.mDrift = dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    public void setOnViewPagerListener(OnViewPagerListener listener) {
        this.mOnViewPagerListener = listener;
    }
    //通过scrollHorizontallyBy（）和scrollVerticallyBy（）可以拿到滑动偏移量，可以判断滑动方向
    //通过滑动方向判断释放上一页还是下一页
    private RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        //第一次进入界面的监听，可以用来实现首次播放的逻辑
        public void onChildViewAttachedToWindow(@NonNull View view) {
            if (mOnViewPagerListener != null && getChildCount() == 1) {
                mOnViewPagerListener.onInitComplete();
            }
        }
        //要回收item的时候，可以释放资源的监听
        @Override
        public void onChildViewDetachedFromWindow(@NonNull View view) {
            if (mDrift >= 0) {
                if (mOnViewPagerListener != null)
                    mOnViewPagerListener.onPageRelease(true,getPosition(view));
                else
                if (mOnViewPagerListener != null)
                    mOnViewPagerListener.onPageRelease(false, getPosition(view));
                mOnViewPagerListener.onPageRelease(true,getPosition(view));
            }
        }
    };


}
