package com.ericjohnson.moviecatalogue.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

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
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movies>>, MoviesAdapter.OnItemClickListener {

    @BindView(R.id.sv_search)
    SearchView svSearch;

    @BindView(R.id.progress_bar)
    ProgressBar pbMovies;

    @BindView(R.id.tv_empty_result)
    TextView tvEmptyResult;

    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;

    private ArrayList<Movies> movies;

    private MoviesAdapter adapter;

    Unbinder unbinder;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        unbinder = ButterKnife.bind(this, view);

        movies = new ArrayList<>();
        adapter = new MoviesAdapter(getContext(), movies);
        rvMovieList.setHasFixedSize(true);
        rvMovieList.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        rvMovieList.setItemAnimator(new DefaultItemAnimator());
        rvMovieList.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    Bundle searchquery = new Bundle();
                    searchquery.putString(Keys.KEY_QUERY, query);
                    getData(searchquery);
                    svSearch.clearFocus();
                    InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputManager != null) {
                        inputManager.hideSoftInputFromWindow(svSearch.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        svSearch.clearFocus();
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int id, Bundle args) {
        String query;
        query = args.getString(Keys.KEY_QUERY);
        return new MovieAsynctaskLoader(getContext(), query, id);
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

    private void getData(Bundle query) {

        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            getLoaderManager().restartLoader(Constants.SEARCH_TYPE, query, this);

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
