package com.humanid.filmreview.data.base;

import com.google.gson.Gson;

public abstract class RequestModel {
    private Gson gson;

    public RequestModel() {
        this.gson = new Gson();
    }

    protected Gson getGson(){
        return gson;
    }

    protected abstract String getJsonString();
}
