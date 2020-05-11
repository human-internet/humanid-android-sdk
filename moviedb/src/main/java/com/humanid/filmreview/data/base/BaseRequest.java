package com.humanid.filmreview.data.base;

import android.content.Context;
import com.google.gson.Gson;
import com.humanid.filmreview.R;
import com.humanid.filmreview.preference.AppPreference;
import com.humanid.filmreview.utils.ContextProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import org.jetbrains.annotations.Nullable;

public abstract class BaseRequest<T extends RequestModel, D extends ResponseModel> {
    private T request;

    private D response;

    private AsyncHttpClient asyncHttpClient;

    private Context context;

    protected AppPreference appPreference;

    private Gson mGson;

    private RequestMethod requestMethod;

    public BaseRequest() {
        this.asyncHttpClient = new AsyncHttpClient();
        this.asyncHttpClient.setLoggingEnabled(true);
        this.context = ContextProvider.get();
        this.appPreference = new AppPreference(context);
        this.mGson = new Gson();
    }

    public void setRequest(final T request) {
        this.request = request;
    }

    public void setResponse(final D response) {
        this.response = response;
    }

    public void setRequestMethod(final RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    abstract protected void onRequestSuccess(D response);

    abstract protected String getUrl();

    abstract protected void onLoading();

    abstract protected void onRequestFailure(String message);

    public void execute(){
        onLoading();

        asyncHttpClient.addHeader("clientSecret", context.getString(R.string.api_secret));

        if (!appPreference.getAccessToken().isEmpty()){
            asyncHttpClient.addHeader("userAccessToken", appPreference.getAccessToken());
        }

        switch (requestMethod){
            case POST:
                postRequest();
                break;

            case PUT:
                putRequest();
                break;

            default:
                getRequest();
                break;
        }

    }

    private void getRequest() {
        asyncHttpClient.get(context, getUrl(),  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    D newResponse = mGson.fromJson(new String(responseBody), (Type) response.getClass());
                    onRequestSuccess(newResponse);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (error.getMessage() != null) {
                    onRequestFailure(error.getMessage());
                } else {
                    onRequestFailure(context.getString(R.string.error_message_unable_to_reach_server));
                }
            }
        });
    }

    private void postRequest(){
        asyncHttpClient.post(context, getUrl(), getStringEntity(), "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    D newResponse = mGson.fromJson(new String(responseBody), (Type) response.getClass());
                    onRequestSuccess(newResponse);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (error.getMessage() != null) {
                    onRequestFailure(error.getMessage());
                } else {
                    onRequestFailure(context.getString(R.string.error_message_unable_to_reach_server));
                }
            }
        });
    }

    private void putRequest(){
        if (getStringEntity() == null){
            asyncHttpClient.put(getUrl(),  new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        D newResponse = mGson.fromJson(new String(responseBody), (Type) response.getClass());
                        onRequestSuccess(newResponse);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (error.getMessage() != null) {
                        onRequestFailure(error.getMessage());
                    } else {
                        onRequestFailure(context.getString(R.string.error_message_unable_to_reach_server));
                    }
                }
            });
        }else{
            asyncHttpClient.put(context, getUrl(), getStringEntity(), "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (statusCode == 200) {
                        D newResponse = mGson.fromJson(new String(responseBody), (Type) response.getClass());
                        onRequestSuccess(newResponse);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (error.getMessage() != null) {
                        onRequestFailure(error.getMessage());
                    } else {
                        onRequestFailure(context.getString(R.string.error_message_unable_to_reach_server));
                    }
                }
            });
        }
    }

    @Nullable
    private StringEntity getStringEntity() {
        StringEntity entity = null;
        String data = request.getJsonString();
        if (data != null){
            try {
                entity = new StringEntity(data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }
}
