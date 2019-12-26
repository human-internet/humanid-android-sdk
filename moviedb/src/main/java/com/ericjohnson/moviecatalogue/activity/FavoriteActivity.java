package com.ericjohnson.moviecatalogue.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.adapter.MoviesCursorAdapter;
import com.ericjohnson.moviecatalogue.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.BaseColumns._ID;
import static com.ericjohnson.moviecatalogue.db.DatabaseContract.CONTENT_URI;
import static com.ericjohnson.moviecatalogue.db.DatabaseContract.MoviesColumns.POSTER;
import static com.ericjohnson.moviecatalogue.db.DatabaseContract.MoviesColumns.RELEASEDATE;
import static com.ericjohnson.moviecatalogue.db.DatabaseContract.MoviesColumns.TITLE;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.toolbarFavorite)
    Toolbar toolbar;

    @BindView(R.id.rvFavorite)
    RecyclerView rvFavorite;

    @BindView(R.id.tv_empty_result)
    TextView tvEmptyResult;

    private Cursor movieList;

    private MoviesCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.label_favourite);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        adapter = new MoviesCursorAdapter(this);
        adapter.setCursor(movieList);
        rvFavorite.setHasFixedSize(true);
        rvFavorite.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvFavorite.addItemDecoration(new DividerItemDecoration(rvFavorite.getContext(),
                LinearLayoutManager.VERTICAL));
        rvFavorite.setItemAnimator(new DefaultItemAnimator());
        rvFavorite.setAdapter(adapter);

        getSupportLoaderManager().initLoader(Constants.FAVORITE_MOVIE_TYPE, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {_ID, TITLE, POSTER, RELEASEDATE};
        return new CursorLoader(this, CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0) {
            tvEmptyResult.setVisibility(View.GONE);
            rvFavorite.setVisibility(View.VISIBLE);
            adapter.setCursor(data);
        } else {
            tvEmptyResult.setVisibility(View.VISIBLE);
            tvEmptyResult.setText(R.string.label_empty_favorite_movies);
            rvFavorite.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.setCursor(null);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
