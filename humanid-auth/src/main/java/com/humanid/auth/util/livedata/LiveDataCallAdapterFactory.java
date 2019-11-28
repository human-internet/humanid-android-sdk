package com.humanid.auth.util.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.humanid.auth.data.source.remote.api.APIResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    private LiveDataCallAdapterFactory() { }

    public static LiveDataCallAdapterFactory create() {
        return new LiveDataCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations,
                                 @NonNull Retrofit retrofit) {

        if (getRawType(returnType) != LiveData.class) {
            return null;
        }

        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Type rawObservableType = getRawType(observableType);

        if (rawObservableType != APIResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }

        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalStateException("Response must be parameterized");
        }

        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);

        return new LiveDataCallAdapter<>(bodyType);
    }

    private static final class LiveDataCallAdapter<R>
            implements CallAdapter<R, LiveData<APIResponse<R>>> {
        private final Type responseType;

        LiveDataCallAdapter(Type responseType) {
            this.responseType = responseType;
        }

        @NonNull
        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public LiveData<APIResponse<R>> adapt(@NonNull Call<R> call) {
            return new LiveData<APIResponse<R>>() {

                private AtomicBoolean started = new AtomicBoolean(false);

                @Override
                protected void onActive() {
                    super.onActive();
                    if (started.compareAndSet(false, true)) {
                        call.enqueue(new Callback<R>() {
                            @Override
                            public void onResponse(@NonNull Call<R> call,
                                                   @NonNull Response<R> response) {
                                postValue(APIResponse.create(response));
                            }

                            @Override
                            public void onFailure(@NonNull Call<R> call, @NonNull Throwable t) {
                                postValue(APIResponse.create(t));
                            }
                        });
                    }
                }
            };
        }
    }
}
