package com.humanid.filmreview.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    private SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "MovieDb.pref";

    private static final String KEY_ACCESSTOKEN = "access_token";

    public AppPreference(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setAccessToken(String accessToken){
        sharedPreferences.edit().putString(KEY_ACCESSTOKEN, accessToken).apply();
    }

    public String getAccessToken(){
        return sharedPreferences.getString(KEY_ACCESSTOKEN, "");
    }

    public void clear(){
        sharedPreferences.edit().clear().apply();
    }
}
