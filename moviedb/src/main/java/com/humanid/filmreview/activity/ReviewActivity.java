package com.humanid.filmreview.activity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.humanid.filmreview.R;
import com.humanid.filmreview.adapter.ReviewAdapter;
import com.humanid.filmreview.fragment.RateReviewDialogFragment;
import com.humanid.filmreview.loader.ReviewAsynctaskLoader;
import com.humanid.filmreview.model.Review;
import com.humanid.filmreview.utils.Keys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<Review>> {

    @BindView(R.id.pbReview)
    ProgressBar pbReview;

    @BindView(R.id.tvReviewError)
    TextView tvReviewError;

    @BindView(R.id.rvReview)
    RecyclerView rvReview;

    @BindView(R.id.btnAddReview)
    Button btnAddReview;

    @BindView(R.id.toolbarReview)
    Toolbar toolbarReview;

    private ArrayList<Review> reviews;

    private int id;

    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra(Keys.KEY_MOVIE_ID, 0);

        setSupportActionBar(toolbarReview);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_review);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        reviews = new ArrayList<>();
        adapter = new ReviewAdapter(this, reviews);
        rvReview.setHasFixedSize(true);
        rvReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvReview.setItemAnimator(new DefaultItemAnimator());
        rvReview.setAdapter(adapter);

        btnAddReview.setOnClickListener(view -> showBottomSheet());

        getData();
    }

    @NonNull
    @Override
    public Loader<ArrayList<Review>> onCreateLoader(int id, Bundle args) {
        int movieId = args.getInt(Keys.KEY_MOVIE_ID);
        return new ReviewAsynctaskLoader(this, movieId);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> data) {
        pbReview.setVisibility(View.GONE);
        adapter.clearReviewList();
        if (data != null && !data.isEmpty()) {
            tvReviewError.setVisibility(View.GONE);
            rvReview.setVisibility(View.VISIBLE);
            reviews.addAll(data);

        } else {
            tvReviewError.setVisibility(View.VISIBLE);
            tvReviewError.setText(R.string.label_review_not_found);
            rvReview.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Review>> loader) {
        adapter.clearReviewList();
        adapter.notifyDataSetChanged();
    }

    private void getData() {
        ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            Bundle bundle = new Bundle();
            bundle.putInt(Keys.KEY_MOVIE_ID, id);
            getLoaderManager().initLoader(5, bundle, this);

            if (pbReview.getVisibility() == View.GONE) {
                pbReview.setVisibility(View.VISIBLE);
                rvReview.setVisibility(View.GONE);
                tvReviewError.setVisibility(View.GONE);
            }

        } else {
            pbReview.setVisibility(View.GONE);
            rvReview.setVisibility(View.GONE);
            tvReviewError.setVisibility(View.VISIBLE);
            tvReviewError.setText(R.string.label_no_internet_connection);
        }
    }

    public void showBottomSheet() {
        RateReviewDialogFragment rateReviewDialogFragment =
                RateReviewDialogFragment.newInstance();
        rateReviewDialogFragment.show(getSupportFragmentManager(),
                RateReviewDialogFragment.TAG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
