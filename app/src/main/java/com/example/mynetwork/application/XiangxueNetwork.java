package com.example.mynetwork.application;

import android.net.Uri;

import com.example.network.BuildConfig;
import com.example.network.INetworkRequiredInfo;

public class XiangxueNetwork implements INetworkRequiredInfo {
    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getAppVersionCode() {
        return String.valueOf(BuildConfig.VERSION_CODE);
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
