package com.ericjohnson.moviecatalogue.domain;

import android.content.Context;

import com.ericjohnson.moviecatalogue.BuildConfig;
import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.preference.AppPreference;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginHttpRequest {

    private AsyncHttpClient asyncHttpClient;

    private Context context;

    private AppPreference appPreference;

    public LoginHttpRequest(Context context) {
        this.asyncHttpClient = new AsyncHttpClient();
        this.context = context;
        this.appPreference = new AppPreference(context);
    }

    public void login(String exchangeToken, OnLoginCallback onLoginCallback){
        onLoginCallback.onLoading();

        String url = BuildConfig.DEMO_APP + "users/log-in";

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("exchangeToken", exchangeToken);
            StringEntity entity = new StringEntity(jsonObject.toString());
            asyncHttpClient.addHeader("clientSecret", context.getString(R.string.human_id_secret));
            asyncHttpClient.post(context, url, entity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String response = new String(responseBody);
                        JSONObject responseObject = new JSONObject(response);
                        if (responseObject.optString("message").equalsIgnoreCase("OK")){
                            String accessToken = responseObject.optJSONObject("data").optString("token");
                            appPreference.setAccessToken(accessToken);
                            onLoginCallback.onLoginSuccess();
                        }else{
                            onLoginCallback.onLoginFailed(responseObject.optString("message"));
                        }
                    }catch (Exception e){
                        onLoginCallback.onLoginFailed(context.getString(R.string.error_message_unable_to_reach_server));
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (error.getMessage() != null){
                        onLoginCallback.onLoginFailed(error.getMessage());
                    }else{
                        onLoginCallback.onLoginFailed(context.getString(R.string.error_message_unable_to_reach_server));
                    }
                }
            });
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
            onLoginCallback.onLoginFailed(context.getString(R.string.error_message_unable_to_reach_server));
        }
    }

    public interface OnLoginCallback{
        void onLoading();

        void onLoginSuccess();

        void onLoginFailed(String message);
    }
}
