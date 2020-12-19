package com.example.mytictok.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.mytictok.R;
import com.example.mytictok.utils.AnimUtils;

import java.lang.reflect.GenericArrayType;
import java.util.Random;

public class LikeView extends RelativeLayout {
    private GestureDetector gestureDetector;
    private int likeViewSize = 330;
    private int[] angles = new int[]{-30, 0, 30};
    private boolean canSingleTabShow = false;
    private OnPlayPauseListener onPlayPauseListener;
    private OnLikeListener onLikeListener;

    public LikeView(Context context) {
        super(context);
        init();
    }

    public LikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LikeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                addLikeView(e);
                onLikeListener.onLikeListener();
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return super.onSingleTapConfirmed(e);
            }
        });

    }

    private void addLikeView(MotionEvent e) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.mipmap.ic_like);
        addView(imageView);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(likeViewSize, likeViewSize);
        layoutParams.leftMargin = (int) e.getX() - likeViewSize / 2;
        layoutParams.topMargin = (int) e.getY() - likeViewSize;
        imageView.setLayoutParams(layoutParams);
        playAnim(imageView);
    }

    private void playAnim(View view) {
        AnimationSet animationSet = new AnimationSet(true);
        int degrees = angles[new Random().nextInt(3)];
        animationSet.addAnimation(AnimUtils.rotateAnim(0, 0, degrees));
        animationSet.addAnimation(AnimUtils.scaleAnim(100, 2f, 1f, 0));
        animationSet.addAnimation(AnimUtils.alphaAnim(0, 1, 100, 0));
        animationSet.addAnimation(AnimUtils.scaleAnim(500, 1f, 1.8f, 300));
        animationSet.addAnimation(AnimUtils.alphaAnim(1f, 0, 500, 300));
        animationSet.addAnimation(AnimUtils.translateAnim(500, 0, 0, 0, -400, 300));

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation aimation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().post(() -> removeView(view));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animationSet);
    }


    public interface OnPlayPauseListener {
        void onPlayOrPause();
    }

    public interface OnLikeListener {
        void onLikeListener();
    }


    //设置单击暂停
    public void setOnPlayPauseListener(OnPlayPauseListener onPlayPauseListener) {
        this.onPlayPauseListener = onPlayPauseListener;
    }

    //设置双击点赞事件
    public void setOnLikeListener(OnLikeListener onLikeListener) {
        this.onLikeListener = onLikeListener;
    }
}
