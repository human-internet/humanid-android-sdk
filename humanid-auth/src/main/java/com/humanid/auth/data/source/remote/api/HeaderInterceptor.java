package com.humanid.auth.data.source.remote.api;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class HeaderInterceptor implements Interceptor {
    /**
     * Client id for header.
     */
    private String clientId;

    /**
     * Client secret for header.
     */
    private String clientSecret;

    /**
     * Constructor.
     * @param clientId : Client id for header.
     * @param clientSecret :  Client secret for header.
     */
    public HeaderInterceptor(final String clientId, final String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     *
     * @param chain : Chain object to chain together modifications to HTTP response
     * @return : Returns response object with Client id and client secret added to header.
     * @throws IOException
     */
    @NotNull
    @Override
    public Response intercept(@NotNull final Chain chain) throws IOException {
        return chain.proceed(mapHeaders(chain));
    }

    /**
     * Implements the logic of intercepting and header data to header request.
//     * @param chain : Chain object to chain together modifications to HTTP response
     * @return : Request.Builder object to be chained.
     */
    private Request mapHeaders(Interceptor.Chain chain) {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        builder.addHeader("client-id", clientId);
        builder.addHeader("client-secret", clientSecret);
        return builder.build();
    }
}

