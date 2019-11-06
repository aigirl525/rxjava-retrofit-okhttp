package com.example.mynetwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mynetwork.api.NewsApiInterface;
import com.example.mynetwork.api.NewsChannelsBean;
import com.example.network.NetworkApi;
import com.example.mynetwork.databinding.ActivityMainBinding;
import com.example.network.observer.BaseObserver;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.getNewsChannels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkApi.getService(NewsApiInterface.class)
                        .getNewsChannels()
                        .compose(NetworkApi.applySchedulers(new BaseObserver<NewsChannelsBean>() {
                            @Override
                            public void onSuccess(NewsChannelsBean newsChannelsBean) {
                                Log.e("MainActivity",new Gson().toJson(newsChannelsBean));
                            }

                            @Override
                            public void onFailure(Throwable e) {

                            }
                        }));
            }
        });
    }
}
