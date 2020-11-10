package com.humanid.auth.util.livedata;

import androidx.lifecycle.LiveData;

public class AbsentLiveData<T> extends LiveData<T> {

    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> AbsentLiveData<T> create() {
        return new AbsentLiveData<>();
    }
}
