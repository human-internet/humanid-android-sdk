package com.humanid.sample.auth.app1.data.source.remote.api;

import androidx.lifecycle.LiveData;

import com.humanid.sample.auth.app1.data.model.APIResponse;
import com.humanid.sample.auth.app1.data.source.remote.api.model.TopStoriesAPIResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NYTApiService {

    @GET("topstories/v2/{section}.json")
    LiveData<APIResponse<TopStoriesAPIResponse>> listTopStories(@Path("section") String section);
}
