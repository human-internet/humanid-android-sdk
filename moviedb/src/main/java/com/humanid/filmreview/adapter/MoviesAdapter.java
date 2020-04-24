package com.humanid.filmreview.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.humanid.filmreview.BuildConfig;
import com.humanid.filmreview.R;
import com.humanid.filmreview.model.Movies;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EricJohnson on 3/1/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private Context context;

    private ArrayList<Movies> moviesList;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemClickListener onItemClickListener;

    public MoviesAdapter(Context context, ArrayList<Movies> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    public void clearMoviesList() {
        moviesList.clear();
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_image)
                .fallback(R.drawable.ic_image);

        Glide.with(context).setDefaultRequestOptions(requestOptions)
                .load(BuildConfig.IMAGE_URL + moviesList.get(position).getPoster())
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

        holder.tvTitle.setText(moviesList.get(position).getTitle());

        String format = String.format("Released: %s", moviesList.get(position).getReleaseDate());
        SpannableStringBuilder str = new SpannableStringBuilder(format);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 10, format.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.tvReleaseDate.setText(str);

        if (holder.tvDescription != null) {
            holder.tvDescription.setText(moviesList.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgPosterMovie)
        ImageView ivPoster;

        @BindView(R.id.tv_title_item)
        TextView tvTitle;

        @Nullable
        @BindView(R.id.tv_description_item)
        TextView tvDescription;

        @BindView(R.id.tv_release_date)
        TextView tvReleaseDate;

        @BindView(R.id.rl_movies)
        RelativeLayout rlMovies;

        @BindView(R.id.pb_image)
        ProgressBar pbImage;

        MoviesViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition()));
        }
    }

    private Spanned toHtml(String htmlText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY);
        } else {
            if (htmlText == null) {
                return new SpannableString("");
            } else {
                return Html.fromHtml(htmlText);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
