package com.humanid.sample.auth.app1;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.humanid.sample.auth.app1.data.DataRepository;
import com.humanid.sample.auth.app1.utils.livedata.vo.Resource;
import com.humanid.sample.auth.app1.data.source.remote.api.model.TopStoriesAPIResponse;
import com.humanid.sample.auth.app1.utils.livedata.vo.Event;

import java.util.List;

public class TopStoriesViewModel extends AndroidViewModel {

    private final static String TAG = TopStoriesViewModel.class.getSimpleName();

    public final MutableLiveData<Event<Boolean>> openAuth = new MutableLiveData<>();

    public TopStoriesViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public LiveData<Resource<List<TopStoriesAPIResponse.Results>>> getList() {
        return DataRepository.getInstance(getApplication()).getTopStories();
    }

    public void onClickLogin() {
        openAuth.postValue(new Event<>(true));
    }
}
