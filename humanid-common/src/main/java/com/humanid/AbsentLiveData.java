package com.humanid;

import android.arch.lifecycle.LiveData;

public class AbsentLiveData<T> extends LiveData<T> {

    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> AbsentLiveData<T> create() {
        return new AbsentLiveData<>();
    }
}
