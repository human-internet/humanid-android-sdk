package com.humanid;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private final static String TAG = HttpClient.class.getSimpleName();

    private final static String baseUrl = "https://humanid.herokuapp.com/mobile";

    private static Retrofit.Builder retrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static  <T> T createService(@NonNull final Class<T> service) {
        return retrofit().build().create(service);
    }
}
