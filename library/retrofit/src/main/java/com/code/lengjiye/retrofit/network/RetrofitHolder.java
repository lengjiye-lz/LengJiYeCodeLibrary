package com.code.lengjiye.retrofit.network;

import com.code.lengjiye.app.AppMaster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类描述:
 * 创建人: lz
 * 创建时间: 2018/12/25
 * 修改备注:
 */
public class RetrofitHolder {

    private String mBaseUrl;

    private static Retrofit mRetrofit;
    private OkHttpClient client;


    public static Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            synchronized (RetrofitHolder.class) {
                new RetrofitHolder();
            }
        }
        return mRetrofit;
    }

    private RetrofitHolder() {

        mBaseUrl = AppMaster.getInstance().getServiceAddress();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient getClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            client = builder
                    .addInterceptor(new SignInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    //.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager)
                    //.hostnameVerifier(new HttpsUtils.AllHostnameVerifier())
                    .build();
        }

        return client;
    }

}
