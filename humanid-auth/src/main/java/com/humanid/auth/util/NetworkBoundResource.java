package com.humanid.auth.util;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.humanid.auth.data.source.remote.api.APIResponse;
import com.humanid.data.model.Resource;
import com.humanid.util.Executors;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private Executors executors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource() {
        this.executors = Executors.getInstance();
        init();
    }

    private void init() {
        result.setValue(Resource.loading(null));
        LiveData<ResultType> localSource = loadFromLocal();

        result.addSource(localSource, data -> {
            result.removeSource(localSource);

            if (shouldFetch(data)) {
                fetchFromNetwork(localSource);
            } else {
                result.addSource(localSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(@NonNull Resource<ResultType> newValue) {
        if (result.getValue() == newValue) return;
        result.setValue(newValue);
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromLocal();

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<APIResponse<RequestType>> createCall();

    private void fetchFromNetwork(LiveData<ResultType> localSource) {
        LiveData<APIResponse<RequestType>> apiResponse = createCall();

        result.addSource(localSource, newData -> setValue(Resource.loading(newData)));

        result.addSource(apiResponse, response -> {
            if (response == null) return;

            result.removeSource(apiResponse);
            result.removeSource(localSource);

            if (response.isSuccessful()) {
                executors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    executors.mainThread().execute(() ->
                            result.addSource(loadFromLocal(), newData ->
                                    setValue(Resource.success(newData))));
                });
            } else {
                onFetchFailed();
                result.addSource(localSource, newData ->
                        setValue(Resource.error(response.getErrorMessage(), newData)));
            }
        });
    }

    @WorkerThread
    public RequestType processResponse(APIResponse<RequestType> response) {
        return response.getData();
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    protected void onFetchFailed() {

    }

    public final LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
