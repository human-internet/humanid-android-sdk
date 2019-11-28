package com.humanid.sample.auth.app1.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.humanid.sample.auth.app1.utils.livedata.vo.APIResponse;
import com.humanid.sample.auth.app1.utils.livedata.vo.Resource;
import com.humanid.sample.auth.app1.data.source.remote.api.NYTApiClient;
import com.humanid.sample.auth.app1.data.source.remote.api.NYTApiService;
import com.humanid.sample.auth.app1.data.source.remote.api.model.TopStoriesAPIResponse;
import com.humanid.sample.auth.app1.utils.livedata.NetworkBoundResource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataRepository {

    private final static String TAG = DataRepository.class.getSimpleName();

    private static volatile DataRepository INSTANCE;

    private final Context applicationContext;

    private DataRepository(@NonNull Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @NonNull
    public static DataRepository getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (DataRepository.class) {
                if (INSTANCE == null) {
                    Context applicationContext = context.getApplicationContext();

                    INSTANCE = new DataRepository(applicationContext);
                }
            }
        }

        return INSTANCE;
    }

    @NonNull
    public LiveData<Resource<List<TopStoriesAPIResponse.Results>>> getTopStories() {
        return new NetworkBoundResource<List<TopStoriesAPIResponse.Results>, TopStoriesAPIResponse>() {

            private List<TopStoriesAPIResponse.Results> results = new ArrayList<>();

            @NonNull
            @Override
            protected LiveData<List<TopStoriesAPIResponse.Results>> loadFromLocal() {
                return new LiveData<List<TopStoriesAPIResponse.Results>>() {

                    private AtomicBoolean started = new AtomicBoolean(false);

                    @Override
                    protected void onActive() {
                        super.onActive();
                        if (started.compareAndSet(false, true)) {
                            postValue(results);
                        }
                    }
                };
            }

            @Override
            protected boolean shouldFetch(@Nullable List<TopStoriesAPIResponse.Results> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<APIResponse<TopStoriesAPIResponse>> createCall() {
                return NYTApiClient.createService(NYTApiService.class).listTopStories("business");
            }

            @Override
            protected void saveCallResult(@NonNull TopStoriesAPIResponse item) {
                results.addAll(item.getResults());
            }
        }.asLiveData();
    }
}
