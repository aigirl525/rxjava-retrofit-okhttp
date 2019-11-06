package com.example.mynetwork.application;

import android.app.Application;

import com.example.network.NetworkApi;


public class XiangxueApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkApi.init(new XiangxueNetwork());
    }
}
