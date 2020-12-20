package com.example.mytictok.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.androidkun.xtablayout.XTabLayout;
import com.example.mytictok.R;
import com.example.mytictok.adapter.CommPagerAdapter;
import com.example.mytictok.adapter.GridVideoAdapter;
import com.example.mytictok.base.BaseFragment;
import com.example.mytictok.bean.DataCreate;
import com.example.mytictok.bean.PauseVideoEvent;
import com.example.mytictok.bean.VideoBean;
import com.example.mytictok.utils.RxBus;
import com.example.mytictok.view.FullScreenVideoView;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.mytictok.bean.DataCreate.userList;


public class MainFragment extends BaseFragment {
    private CurrentFragment currentLocationFragment;
    private RecommendFragment recommendFragment;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tab_title)
    XTabLayout tabTitle;
    @BindView(R.id.tab_mainmenu)
    XTabLayout tabMainMenu;
    @BindView(R.id.up_load)
    ImageView upLoad;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private CommPagerAdapter pagerAdapter;
    /**
     * 默认显示第一页推荐页
     */
    public static int curPage = 1;
    private FullScreenVideoView videoView;
    private GridVideoAdapter gridVideoAdapter;
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
        currentLocationFragment = new CurrentFragment();
        recommendFragment = new RecommendFragment();
        fragments.add(currentLocationFragment);
        fragments.add(recommendFragment);

        tabTitle.addTab(tabTitle.newTab().setText("全部"));
        tabTitle.addTab(tabTitle.newTab().setText("推荐"));

        pagerAdapter = new CommPagerAdapter(getChildFragmentManager(), fragments, new String[]{"全部", "推荐"});
        viewPager.setAdapter(pagerAdapter);
        tabTitle.setupWithViewPager(viewPager);

        tabTitle.getTabAt(1).select();

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

    @OnClick(R.id.up_load)
    public void upLoad() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("video/*");
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1 : {
                if (resultCode == Activity.RESULT_OK ) {
                    Log.d("TAG", "onActivityResult: "+data.getData());
                    VideoBean videoBean = new VideoBean();
                    videoBean.setUri(data.getData());
                    videoBean.setContent("test");
                    VideoBean.UserBean userBeanOne = new VideoBean.UserBean();
                    userBeanOne.setUid(9);
                    userBeanOne.setHead(R.mipmap.head1);
                    userBeanOne.setNickName("Author");
                    userBeanOne.setSign("test");
                    userBeanOne.setSubCount(119323);
                    userBeanOne.setFocusCount(482);
                    userBeanOne.setFansCount(32823);
                    userBeanOne.setWorkCount(42);
                    userBeanOne.setDynamicCount(42);
                    userBeanOne.setLikeCount(821);
                    userList.add(userBeanOne);
                    videoBean.setUserBean(userBeanOne);
                    DataCreate.datas.add(videoBean);

                }
            }
        }
    }
}