package com.example.network;


import java.util.HashMap;

import com.example.network.commoninterceptor.CommonRequestInterceptor;
import com.example.network.commoninterceptor.CommonResponseInterceptor;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {
    private static final String TENCENT_BASE_URL = "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    private static INetworkRequiredInfo iNetworkRequiredInfo;
    public static HashMap<String,Retrofit> retrofitHashMap = new HashMap<>();

    public static void init(INetworkRequiredInfo networkRequiredInfo){
        iNetworkRequiredInfo = networkRequiredInfo;
    }

    public static Retrofit getRetrofit(Class service){
        if (retrofitHashMap.get(TENCENT_BASE_URL + service.getName()) != null){
            return retrofitHashMap.get(TENCENT_BASE_URL + service.getName());
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(TENCENT_BASE_URL);
        retrofitBuilder.client(getOkHttpClient());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        retrofitHashMap.put(TENCENT_BASE_URL+service.getName(),retrofit);
        return retrofit;

    }
    public static <T> T getService(Class<T> service){
        return  getRetrofit(service).create(service);
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new CommonRequestInterceptor(iNetworkRequiredInfo));
        okHttpClientBuilder.addInterceptor(new CommonResponseInterceptor());
        if (iNetworkRequiredInfo != null && iNetworkRequiredInfo.isDebug()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        return okHttpClientBuilder.build();
    }
    public  static <T>ObservableTransformer<T,T> applySchedulers(final Observer<T> observer){
        return  new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io());
                observable.subscribeOn(AndroidSchedulers.mainThread());
                observable.subscribe(observer);
                return observable;
            }
        };
    }
    }
