package com.humanid.auth.util.livedata;

import androidx.lifecycle.LiveData;

/**
 * Null extension of the LiveData Class. Overrides main thread with value null
 */
public class AbsentLiveData<T> extends LiveData<T> {

    /**
     * Constructor. postValue(null) is called to post null to the main thread.
     */
    private AbsentLiveData() {
        postValue(null);
    }

    /**
     * Creates a new AbsentLiveData object and returns it.
     * @return AbsentLiveData<T>: the new object created by this function
     */
    public static <T> AbsentLiveData<T> create() {
        return new AbsentLiveData<>();
    }
}
