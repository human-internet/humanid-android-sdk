package com.ericjohnson.moviecatalogue.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.activity.MovieDetailActivity;
import com.ericjohnson.moviecatalogue.adapter.MoviesAdapter;
import com.ericjohnson.moviecatalogue.loader.MovieAsynctaskLoader;
import com.ericjohnson.moviecatalogue.model.Movies;
import com.ericjohnson.moviecatalogue.utils.Constants;
import com.ericjohnson.moviecatalogue.utils.Keys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.ericjohnson.moviecatalogue.db.DatabaseContract.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Movies>>, MoviesAdapter.OnItemClickListener {

    @BindView(R.id.progress_bar)
    ProgressBar pbMovies;

    @BindView(R.id.tv_empty_result)
    TextView tvEmptyResult;

    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;

    Unbinder unbinder;

    private ArrayList<Movies> movies;

    private MoviesAdapter adapter;

    public NowPlayingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        unbinder = ButterKnife.bind(this, view);

        movies = new ArrayList<>();
        adapter = new MoviesAdapter(getContext(), movies);
        rvMovieList.setHasFixedSize(true);
        rvMovieList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvMovieList.addItemDecoration(new DividerItemDecoration(rvMovieList.getContext(),
                LinearLayoutManager.VERTICAL));
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        getData();
        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int id, Bundle args) {
        return new MovieAsynctaskLoader(getContext(), null, id);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movies>> loader, ArrayList<Movies> data) {
        pbMovies.setVisibility(View.GONE);
        adapter.clearMoviesList();
        if (data != null && !data.isEmpty()) {
            tvEmptyResult.setVisibility(View.GONE);
            rvMovieList.setVisibility(View.VISIBLE);
            movies.addAll(data);
        } else {
            tvEmptyResult.setVisibility(View.VISIBLE);
            tvEmptyResult.setText(R.string.label_movies_not_found);
            rvMovieList.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movies>> loader) {
        adapter.clearMoviesList();
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            getLoaderManager().initLoader(Constants.NOW_PLAYING_TYPE, null, this);

            if (pbMovies.getVisibility() == View.GONE) {
                pbMovies.setVisibility(View.VISIBLE);
                rvMovieList.setVisibility(View.GONE);
                tvEmptyResult.setVisibility(View.GONE);
            }

        } else {
            pbMovies.setVisibility(View.GONE);
            rvMovieList.setVisibility(View.GONE);
            tvEmptyResult.setVisibility(View.VISIBLE);
            tvEmptyResult.setText(R.string.label_no_internet_connection);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        Uri uri = Uri.parse(CONTENT_URI + "/" + movies.get(position).getId());
        intent.putExtra(Keys.KEY_MOVIE_ID, movies.get(position).getId());
        intent.putExtra(Keys.KEY_TITLE, movies.get(position).getTitle());
        intent.setData(uri);
        startActivity(intent);
    }
}
