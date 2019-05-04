package com.example.liaodh.keeprun;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.litepal.LitePal;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    public static Context getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        LitePal.initialize(this);
        mInstance = this;
    }

}
