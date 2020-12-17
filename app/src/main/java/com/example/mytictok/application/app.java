package com.example.mytictok.application;

import android.app.Application;
import android.content.Context;

public class app extends Application {
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
