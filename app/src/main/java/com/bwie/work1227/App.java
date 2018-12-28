package com.bwie.work1227;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
