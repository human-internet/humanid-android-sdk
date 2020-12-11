package com.humanid.filmreview.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.humanid.filmreview.R;
import com.humanid.filmreview.adapter.ReviewAdapter;
import com.humanid.filmreview.data.content.review.GetReviewRequest.OnGetReviewCallback;
import com.humanid.filmreview.domain.content.ContentInteractor;
import com.humanid.filmreview.domain.user.UserInteractor;
import com.humanid.filmreview.fragment.RateReviewDialogFragment;
import com.humanid.filmreview.model.Review;
import com.humanid.filmreview.utils.Keys;
import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

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

    private void getData() {
        if (UserInteractor.getInstance(this).isLoggedIn()){
            ConnectivityManager cm = (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = null;
            if (cm != null) {
                activeNetwork = cm.getActiveNetworkInfo();
            }
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            if (isConnected) {
                ContentInteractor.getInstance()
                        .getReview(String.valueOf(id), new OnGetReviewCallback() {
                            @Override
                            public void onLoading() {
                                if (pbReview.getVisibility() == View.GONE) {
                                    pbReview.setVisibility(View.VISIBLE);
                                    rvReview.setVisibility(View.GONE);
                                    tvReviewError.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onGetReviewSuccess(final ArrayList<Review> reviews) {
                                showReviews(reviews);
                            }

                            @Override
                            public void onGetReviewFailed(final String message) {
                                pbReview.setVisibility(View.GONE);
                                rvReview.setVisibility(View.GONE);
                                tvReviewError.setVisibility(View.VISIBLE);
                                tvReviewError.setText(message);
                            }

                            @Override
                            public void onUnauthorized() {
                                pbReview.setVisibility(View.GONE);
                                rvReview.setVisibility(View.GONE);
                                tvReviewError.setVisibility(View.VISIBLE);
                                tvReviewError.setText("Your session is expired. Please login");
                            }
                        });

            } else {
                pbReview.setVisibility(View.GONE);
                rvReview.setVisibility(View.GONE);
                tvReviewError.setVisibility(View.VISIBLE);
                tvReviewError.setText(R.string.label_no_internet_connection);
            }
        }
    }

    private void showReviews(final ArrayList<Review> data) {
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

    public void showBottomSheet() {
        RateReviewDialogFragment rateReviewDialogFragment =
                RateReviewDialogFragment.newInstance((title, review) -> {

                });
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
