package com.ericjohnson.moviecatalogue.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ericjohnson.moviecatalogue.BuildConfig;
import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.model.Cast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private Context context;

    private ArrayList<Cast> castList;

    public CastAdapter(Context context, ArrayList<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    public void clearCsatList() {
        castList.clear();
    }


    @NonNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false);
        return new CastAdapter.CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CastAdapter.CastViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_image)
                .fallback(R.drawable.ic_image);

        Glide.with(context).setDefaultRequestOptions(requestOptions)
                .load(BuildConfig.IMAGE_URL + castList.get(position).getProfilePath())
                .into(holder.imgPoster);

        holder.tvName.setText(castList.get(position).getName());
        holder.tvCharacter.setText(castList.get(position).getCharacter());
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPoster)
        ImageView imgPoster;

        @BindView(R.id.tvName)
        TextView tvName;

        @BindView(R.id.tvCharacter)
        TextView tvCharacter;

        CastViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
