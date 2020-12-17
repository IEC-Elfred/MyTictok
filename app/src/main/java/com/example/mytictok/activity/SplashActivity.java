package com.example.mytictok.activity;

import android.content.Intent;
import android.os.CountDownTimer;

import com.example.mytictok.R;
import com.example.mytictok.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        setFullScreen();
        new CountDownTimer(500,500){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }.start();
    }
}