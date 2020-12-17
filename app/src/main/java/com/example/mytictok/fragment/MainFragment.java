package com.example.mytictok.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidkun.xtablayout.XTabLayout;
import com.example.mytictok.R;
import com.example.mytictok.adapter.CommPagerAdapter;
import com.example.mytictok.base.BaseFragment;
import com.example.mytictok.bean.PauseVideoEvent;
import com.example.mytictok.utils.RxBus;

import java.util.ArrayList;

import butterknife.BindView;


public class MainFragment extends BaseFragment {
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tab_title)
    XTabLayout tabTile;

    @BindView(R.id.tab_mainmenu)
    XTabLayout tabMainMenu;

    private CurrentFragment currentFragment = new CurrentFragment();
    private RecommendFragment recommendFragment = new RecommendFragment();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommPagerAdapter pagerAdapter;
    //默认显示第一页
    public static int curPage = 1;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        setFragments();
        setMainMenu();
    }

    private void setFragments() {
        currentFragment = new CurrentFragment();
        recommendFragment = new RecommendFragment();
        fragments.add(recommendFragment);
        fragments.add(currentFragment);
        tabTile.addTab(tabTile.newTab().setText("推荐"));
        tabTile.addTab(tabTile.newTab().setText("全部"));
        pagerAdapter = new CommPagerAdapter(getChildFragmentManager(),fragments,new String[]{"推荐","全部"});
        viewPager.setAdapter(pagerAdapter);
        tabTile.setupWithViewPager(viewPager);
        tabTile.getTabAt(1).select();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curPage = position;

                if (position == 1) {
                    //继续播放
                    RxBus.getDefault().post(new PauseVideoEvent(true));
                } else {
                    //切换到其他页面，需要暂停视频
                    RxBus.getDefault().post(new PauseVideoEvent(false));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setMainMenu() {
        tabMainMenu.addTab(tabMainMenu.newTab().setText("首页"));
        tabMainMenu.addTab(tabMainMenu.newTab().setText("好友"));
        tabMainMenu.addTab(tabMainMenu.newTab().setText(""));
        tabMainMenu.addTab(tabMainMenu.newTab().setText("消息"));
        tabMainMenu.addTab(tabMainMenu.newTab().setText("我"));
    }
}