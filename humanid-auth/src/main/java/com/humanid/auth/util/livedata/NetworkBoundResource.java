package com.humanid.auth.util.livedata;

import android.text.TextUtils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.google.android.gms.common.internal.Objects;
import com.humanid.auth.data.source.remote.api.APIResponse;
import com.humanid.auth.util.livedata.vo.Resource;
import com.humanid.util.Executors;

public abstract class NetworkBoundResource<ResultType, RequestType> {


    /**
     * An instance of the custom Executors class, which holds three Executor objects defined in the Java library.
     */
    private Executors executors;

    /**
     * <p>A MediatorLiveData object, defined in the Android library, of type Resource<ResultType>.
     * The Resource class is a custom class designed to store the Status (loading/success/failure) of data</p>
     */
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    /**
     * Constructor.
     */
    @MainThread
    public NetworkBoundResource() {
        this.executors = Executors.getInstance();
        init();
    }

    /**
     * <p>Fetches objects of type Resource<ResultType> locally and from a server. These objects are added to the member variable result.</p>
     */
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

    /**
     * Sets Type of member variable result to newValue.
     * @param newValue :  the Type of data, which is some type of Resource object, that result will now store.
     */
    @MainThread
    private void setValue(@NonNull Resource<ResultType> newValue) {
        if (Objects.equal(result.getValue(), newValue)) return;
        result.setValue(newValue);
    }

    /**
     *
     * @return :  Returns local data that is eventually added to the member variable result.
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromLocal();

    /**
     *
     * @param data : The data that is being fetched or loaded locally
     * @return : Returns true if data should be fetched from the network instead of loaded locally.
     */
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    /**
     *
     * @return : Returns the data as a LiveData<APIResponse<RequestType>> object.
     */
    @NonNull
    @MainThread
    protected abstract LiveData<APIResponse<RequestType>> createCall();

    private void fetchFromNetwork(@NonNull LiveData<ResultType> localSource) {
        LiveData<APIResponse<RequestType>> networkSource = createCall();

        result.addSource(localSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(networkSource, response -> {
            result.removeSource(networkSource);
            result.removeSource(localSource);

            if (response != null && response.isSuccessful()) {
                executors.diskIO().execute(() -> {
                    RequestType item = processResponse(response);
                    if (item != null) saveCallResult(item);
                    executors.mainThread().execute(() ->
                            result.addSource(loadFromLocal(),
                                    newData -> setValue(Resource.success(newData))));
                });
            } else {
                onFetchFailed();
                result.addSource(localSource, newData -> {
                    String errorMessage = null;
                    if (response != null && !TextUtils.isEmpty(response.getMessage())) {
                        errorMessage = response.getMessage();
                    }
                    setValue(Resource.error(errorMessage, newData));
                });
            }
        });
    }

    /**
     *
     * @param response :  The data of which the Type is to be derived from
     * @return : Returns the Type of response as a generic type.
     */
    @Nullable
    @WorkerThread
    public RequestType processResponse(@NonNull APIResponse<RequestType> response) {
        return response.getData();
    }

    /**
     * Function to be overriden.
     * @param item : RequestType
     */
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    /**
     * Function to be overriden.
     */
    protected void onFetchFailed() {}

    /**
     *
     * @return : Returns copy of results.
     */
    @NonNull
    public final LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }
}
