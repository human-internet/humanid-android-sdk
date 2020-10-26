package com.humanid.auth.data.source.remote.api;

import androidx.annotation.NonNull;
import com.humanid.HumanIDSDK;
import com.humanid.auth.BuildConfig;
import com.humanid.auth.util.livedata.LiveDataCallAdapterFactory;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    /**
     * Tag holding class name "HttpClient"
     */
    private final static String TAG = HttpClient.class.getSimpleName();

    /**
     * HumanIDSDK instance.
     */
    private final static HumanIDSDK sdk = HumanIDSDK.getInstance();

    /**
     * String holding the base url to access for applying client ,converter , and call adapter.
     */
    private final static String baseUrl = "https://core.human-id.org/v0.0.2/mobile/";

    /**
     *
     * @return : Returns retrofit.builder object
     */
    @NonNull
    private static Retrofit.Builder retrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG){
            interceptor.level(HttpLoggingInterceptor.Level.BODY);
        }else{
            interceptor.level(Level.NONE);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor(sdk.getOptions().getApplicationID(), sdk.getOptions().getApplicationSecret()))
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create());
    }

    /**
     *
     * @param service : Generic type object
     * @return : Return retrofit object to configure and prepare your requests, responses, authentication, logging and error handling.
     */
    @NonNull
    public static  <T> T createService(@NonNull final Class<T> service) {
        return retrofit().build().create(service);
    }
}
