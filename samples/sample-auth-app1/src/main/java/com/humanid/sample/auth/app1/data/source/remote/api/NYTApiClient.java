package com.humanid.sample.auth.app1.data.source.remote.api;

import androidx.annotation.NonNull;

import com.humanid.sample.auth.app1.utils.LiveDataCallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NYTApiClient {

    private final static String TAG = NYTApiClient.class.getSimpleName();

    private final static String baseUrl = "https://api.nytimes.com/svc/";

    @NonNull
    private static Retrofit.Builder retrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("api-key", "U9v6QFGf9WG1Q5vgAgLMXIBz0itY2XuI")
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create());
    }

    @NonNull
    public static  <T> T createService(@NonNull final Class<T> service) {
        return retrofit().build().create(service);
    }
}
