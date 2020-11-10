package com.humanid.auth.data.source.remote.api;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class HeaderInterceptor implements Interceptor {
    private String clientId;

    private String clientSecret;

    public HeaderInterceptor(final String clientId, final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull final Chain chain) throws IOException {
        return chain.proceed(mapHeaders(chain));
    }

    private Request mapHeaders(Interceptor.Chain chain) {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        builder.addHeader("client-id", clientId);
        builder.addHeader("client-secret", clientSecret);
        return builder.build();
    }
}

