package com.example.mynetwork.api;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface NewsApiInterface {
    @GET("release/channel")
    Observable<NewsChannelsBean> getNewsChannels();
}
