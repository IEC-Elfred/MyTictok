package com.example.mytictok.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mytictok.R;
import com.example.mytictok.bean.VideoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ControllerView extends RelativeLayout implements View.OnClickListener {
    @BindView(R.id.tv_content)
    TextView textView;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.lottie_anim)
    LottieAnimationView animationView;
    @BindView(R.id.rl_like)
    RelativeLayout rlLike;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_like)
    IconFontTextView ivLike;
    @BindView(R.id.tv_likecount)
    TextView tvLikecount;
    private OnVideo
    private VideoBean videoData;

    public ControllerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.iv_head:
                listener.onHeadClick();
                break;
            case R.id.rl_like:
                listener.onLikeClick();

                like();
                break;

        }
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_controller,this);
        ButterKnife.bind(this,rootView);

        //这里的this是controllerView,controllerView实现了View.OnClickListener
        ivHead.setOnClickListener(this);
        rlLike.setOnClickListener(this);
        setRotateAnim();
    }

    public void setVideoData(VideoBean videoData) {
        this.videoData = videoData;
        ivHead.setImageResource(videoData.getUserBean().getHead());
        tvNickname.setText("@"+videoData.getUserBean().getNickName());
        tvLikecount.setText(NumUtils.numberFilter(videoData.getLikeCount()));
        animationView.setAnimation("heart.json");

        //点赞状态
        if (videoData.isLiked()) {
            ivLike.setTextColor(getResources().getColor(R.color.color_FF0041));
        } else {
            ivLike.setTextColor(getResources().getColor(R.color.white));
        }


    }
    /**
     * 点赞动作
     */
    public void like() {
        if (!videoData.isLiked()) {
            //点赞
            animationView.setVisibility(VISIBLE);
            animationView.playAnimation();
            ivLike.setTextColor(getResources().getColor(R.color.color_FF0041));
        } else {
            //取消点赞
            animationView.setVisibility(INVISIBLE);
            ivLike.setTextColor(getResources().getColor(R.color.white));
        }

        videoData.setLiked(!videoData.isLiked());
    }
}
