package com.humanid.internal.data.source.remote.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.humanid.core.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseAPI {

    private final static String TAG = BaseAPI.class.getSimpleName();

    private Context applicationContext;
    private Retrofit retrofit;

    protected BaseAPI(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
        buildRetrofit();
    }

    private void buildRetrofit() {
        if (retrofit != null) return;

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    protected <T> T createService(@NonNull final Class<T> service) {
        return retrofit.create(service);
    }
}
