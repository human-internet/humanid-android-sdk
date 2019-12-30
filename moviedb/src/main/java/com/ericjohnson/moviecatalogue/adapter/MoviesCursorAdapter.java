package com.ericjohnson.moviecatalogue.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ericjohnson.moviecatalogue.BuildConfig;
import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.activity.MovieDetailActivity;
import com.ericjohnson.moviecatalogue.listener.CustomOnClickLIstener;
import com.ericjohnson.moviecatalogue.model.Movies;
import com.ericjohnson.moviecatalogue.utils.DateUtil;
import com.ericjohnson.moviecatalogue.utils.Keys;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ericjohnson.moviecatalogue.db.DatabaseContract.CONTENT_URI;

public class MoviesCursorAdapter extends RecyclerView.Adapter<MoviesCursorAdapter.ViewHolder> {

    private Cursor movies;

    private Activity activity;

    public MoviesCursorAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setCursor(Cursor cursor) {
        this.movies = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Movies movies = getItem(position);
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_image)
                .fallback(R.drawable.ic_image);

        Glide.with(activity).setDefaultRequestOptions(requestOptions)
                .load(BuildConfig.IMAGE_URL + movies.getPoster())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.pbImage.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.pbImage.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.ivPoster);
        holder.tvTitle.setText(movies.getTitle());
        holder.tvReleaseDate.setText(DateUtil.getReadableDate(movies.getReleaseDate()));
        holder.tvDescription.setText(movies.getDescription());
        holder.rlMovies.setOnClickListener(new CustomOnClickLIstener(position, new CustomOnClickLIstener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(activity, MovieDetailActivity.class);
                Uri uri = Uri.parse(CONTENT_URI + "/" + movies.getId());
                intent.putExtra(Keys.KEY_MOVIE_ID, movies.getId());
                intent.putExtra(Keys.KEY_TITLE, movies.getTitle());
                intent.putExtra(Keys.KEY_DESCRIPTION, movies.getDescription());
                intent.setData(uri);
                activity.startActivity(intent);
            }
        }));
    }

    private Movies getItem(int position) {
        if (!movies.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movies(movies);
    }

    @Override
    public int getItemCount() {
        if (movies == null) return 0;
        return movies.getCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPosterMovie)
        ImageView ivPoster;

        @BindView(R.id.tv_title_item)
        TextView tvTitle;

        @BindView(R.id.tv_release_date)
        TextView tvReleaseDate;

        @BindView(R.id.tv_description_item)
        TextView tvDescription;

        @BindView(R.id.rl_movies)
        RelativeLayout rlMovies;

        @BindView(R.id.pb_image)
        ProgressBar pbImage;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
