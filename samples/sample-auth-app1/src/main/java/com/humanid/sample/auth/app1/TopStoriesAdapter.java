package com.humanid.sample.auth.app1;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.humanid.sample.auth.app1.data.source.remote.api.model.TopStoriesAPIResponse;
import com.humanid.sample.auth.app1.databinding.ItemListBinding;

import java.util.ArrayList;
import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.ViewHolder> {

    private final static String TAG = TopStoriesAdapter.class.getSimpleName();

    private List<TopStoriesAPIResponse.Results> list = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_list, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(@NonNull List<TopStoriesAPIResponse.Results> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ItemListBinding binding;

        public ViewHolder(@NonNull ItemListBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void onBind(int position) {
            TopStoriesAPIResponse.Results result = list.get(position);

            binding.setTitle(result.getTitle());
            binding.setDescription(result.getAbstract());
            if (result.getMultimedia().size() > 0) {
                binding.setImageUrl(result.getMultimedia().get(0).getUrl());
            }
            binding.executePendingBindings();
        }
    }
}
