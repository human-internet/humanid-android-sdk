package com.humanid.sample.auth.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.humanid.auth.HumanIDAuth;
import com.humanid.auth.ui.AuthActivity;
import com.humanid.sample.auth.app1.data.model.Resource;
import com.humanid.sample.auth.app1.data.source.remote.api.model.TopStoriesAPIResponse;
import com.humanid.sample.auth.app1.databinding.ActivityMainBinding;
import com.humanid.sample.auth.app1.utils.EventObserver;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TopStoriesViewModel viewModel;
    private ActivityMainBinding viewDataBinding;
    private TopStoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(TopStoriesViewModel.class);
        viewDataBinding.setVariable(BR.viewModel, viewModel);
        viewDataBinding.setLifecycleOwner(this);
        viewDataBinding.executePendingBindings();

        adapter = new TopStoriesAdapter();

        viewDataBinding.list.setLayoutManager(new LinearLayoutManager(this));
        viewDataBinding.list.setAdapter(adapter);

        setListener();
    }

    private void setListener() {
        viewModel.getList().observe(this, resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING:
                    break;
                case SUCCESS:
                    if (resource.data != null) {
                        adapter.updateData(resource.data);
                    }
                    break;
                case ERROR:
                    break;
                default:
                    break;
            }
        });

        viewModel.openAuth.observe(this, new EventObserver<>(aVoid -> {
            startActivity(new Intent(this, AuthActivity.class));
        }));
    }
}
