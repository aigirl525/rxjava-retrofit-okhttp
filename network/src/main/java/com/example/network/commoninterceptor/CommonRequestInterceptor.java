package com.example.network.commoninterceptor;

import com.example.network.INetworkRequiredInfo;
import com.example.network.utils.TecentUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor implements Interceptor {
    private INetworkRequiredInfo requiredInfo;
    public CommonRequestInterceptor(INetworkRequiredInfo requiredInfo){
        this.requiredInfo = requiredInfo;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        String timeStr = TecentUtil.getTimeStr();
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion",this.requiredInfo.getAppVersionCode());
        builder.addHeader("Source", "source");
        builder.addHeader("Authorization", TecentUtil.getAuthorization(timeStr));
        builder.addHeader("Date", timeStr);
        return chain.proceed(builder.build());
    }
}
