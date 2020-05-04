package com.humanid.filmreview.activity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.humanid.filmreview.BuildConfig;
import com.humanid.filmreview.R;
import com.humanid.filmreview.adapter.CastAdapter;
import com.humanid.filmreview.adapter.ReviewAdapter;
import com.humanid.filmreview.db.DatabaseContract;
import com.humanid.filmreview.db.MoviesHelper;
import com.humanid.filmreview.domain.LoginHttpRequest;
import com.humanid.filmreview.domain.UserInteractor;
import com.humanid.filmreview.fragment.RateReviewDialogFragment;
import com.humanid.filmreview.loader.MovieDetailAsynctaskLoader;
import com.humanid.filmreview.model.Genre;
import com.humanid.filmreview.model.MovieDetail;
import com.humanid.filmreview.model.Review;
import com.humanid.filmreview.utils.DateUtil;
import com.humanid.filmreview.utils.Keys;
import com.humanid.humanidui.presentation.LoginCallback;
import com.humanid.humanidui.presentation.LoginManager;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

public class MovieDetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<MovieDetail> {

    @BindView(R.id.appBarMovieDetail)
    AppBarLayout appBarMovieDetail;

    @BindView(R.id.toolbarDetail)
    Toolbar toolbarDetail;

    @BindView(R.id.pb_movie_detail)
    ProgressBar pbMovieDetail;

    @BindView(R.id.imgBanner)
    ImageView imgBanner;

    @BindView(R.id.clDetailMovie)
    CoordinatorLayout clDetailMovie;

    @BindView(R.id.nsvDetail)
    NestedScrollView nsvDetail;

    @BindView(R.id.llButtonDetail)
    LinearLayout llButtonDetail;

    @BindView(R.id.imgPoster)
    ImageView imgPoster;

    @BindView(R.id.tvMovieTitle)
    TextView tvMovieTitle;

    @BindView(R.id.tv_movie_detail_error)
    TextView tvMovieDetailError;

    @BindView(R.id.tvMovieRating)
    TextView tvMovieRating;

    @BindView(R.id.tvSubTitle)
    TextView tvSubTitle;

    @BindView(R.id.tvOverview)
    TextView tvOverview;

    @BindView(R.id.rvCast)
    RecyclerView rvCast;

    @BindView(R.id.rvReviewDetail)
    RecyclerView rvReviewDetail;

    @BindView(R.id.btnMoreReview)
    Button btnMoreReview;

    @BindView(R.id.btnAdd)
    Button btnAdd;

    @BindView(R.id.btnPlay)
    ImageButton btnPlay;

    @BindView(R.id.btnRate)
    Button btnRate;

    private boolean isFavourited = false;

    private int id;

    private ArrayList<Review> reviews;

    private String imageUrl, releaseDate, description, videoUrl;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        MoviesHelper moviesHelper = new MoviesHelper(this);
        moviesHelper.open();

        id = getIntent().getIntExtra(Keys.KEY_MOVIE_ID, 0);
        final String title = getIntent().getStringExtra(Keys.KEY_TITLE);

        setSupportActionBar(toolbarDetail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressDialog = new ProgressDialog(this);

        getMovieDetail(id);

        appBarMovieDetail.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {

                    toolbarDetail.setTitle(tvMovieTitle.getText().toString());
                    nsvDetail.setBackground(getResources().getDrawable(R.color.colorWhite));

                    isShow = true;
                } else if (isShow) {

                    toolbarDetail.setTitle(" ");
                    nsvDetail.setBackground(getResources().getDrawable(R.drawable.bg_rectangle_white_top_corner));

                    isShow = false;
                }
            }
        });

        btnAdd.setOnClickListener(v -> addFavorite());

        btnMoreReview.setOnClickListener(view -> {
            Intent intent = new Intent(getBaseContext(), ReviewActivity.class);
            Uri uri = Uri.parse(DatabaseContract.CONTENT_URI + "/" + id);
            intent.putExtra(Keys.KEY_MOVIE_ID, id);
            intent.setData(uri);
            startActivity(intent);
        });

        btnRate.setOnClickListener(view -> {
                if (UserInteractor.getInstance(this).isLoggedIn()){
                    showBottomSheet();
                }else{
                    loginHumanID();
                }
            }
        );

        btnPlay.setOnClickListener(view -> {
            Uri webpage = Uri.parse("http://www.youtube.com/watch?v=" + videoUrl);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

            if (webIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(webIntent);
            }

        });
    }

    private void addFavorite() {
        if (UserInteractor.getInstance(MovieDetailActivity.this).isLoggedIn()){
            if (!isFavourited) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Audio.Playlists.Members._ID, id);
                values.put(DatabaseContract.MoviesColumns.TITLE, tvMovieTitle.getText().toString());
                values.put(DatabaseContract.MoviesColumns.POSTER, imageUrl);
                values.put(DatabaseContract.MoviesColumns.RELEASEDATE, releaseDate);
                values.put(DatabaseContract.MoviesColumns.DESCRIPTION, description);

                getContentResolver().insert(DatabaseContract.CONTENT_URI, values);
                isFavourited = true;
                Toast.makeText(MovieDetailActivity.this, R.string.label_added_to_favourite,
                        Toast.LENGTH_SHORT).show();
            } else {
                if (getIntent().getData() != null) {
                    getContentResolver().delete(getIntent().getData(), null, null);
                    isFavourited = false;
                    Toast.makeText(MovieDetailActivity.this, R.string.label_removed_from_favourite,
                            Toast.LENGTH_SHORT).show();
                }
            }

            changeTextButtonFavorite();

        }else{
            loginHumanID();
        }
    }

    private void loginHumanID(){
        LoginManager.INSTANCE.getInstance(this).registerCallback(new LoginCallback() {
            @Override
            public void onSuccess(@NotNull String exchangeToken) {
                UserInteractor.getInstance(MovieDetailActivity.this).login(exchangeToken, new LoginHttpRequest.OnLoginCallback() {
                    @Override
                    public void onLoading() {
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();
                    }

                    @Override
                    public void onLoginSuccess() {
                        if (progressDialog != null){
                            progressDialog.dismiss();
                            Toast.makeText(MovieDetailActivity.this, "Login succeed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onLoginFailed(String message) {
                        if (progressDialog != null){
                            progressDialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onError(@NotNull String errorMessage) {
                Toast.makeText(MovieDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MovieDetailActivity.this, "request cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showBottomSheet() {
        RateReviewDialogFragment rateReviewDialogFragment =
                RateReviewDialogFragment.newInstance();
        rateReviewDialogFragment.show(getSupportFragmentManager(),
                RateReviewDialogFragment.TAG);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Loader<MovieDetail> onCreateLoader(int id, Bundle args) {
        int movieId = args.getInt(Keys.KEY_MOVIE_ID);
        return new MovieDetailAsynctaskLoader(this, movieId);
    }

    @Override
    public void onLoadFinished(Loader<MovieDetail> loader, MovieDetail data) {
        if (data != null) {
            pbMovieDetail.setVisibility(View.GONE);
            clDetailMovie.setVisibility(View.VISIBLE);
            llButtonDetail.setVisibility(View.VISIBLE);

            tvMovieTitle.setText(!TextUtils.isEmpty(data.getTitle()) ? data.getTitle() : "");
            tvOverview.setText(!TextUtils.isEmpty(data.getOverview()) ? data.getOverview() : "-");

            imgPoster.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_image));

            imageUrl = data.getPoster();
            releaseDate = data.getReleaseDate();
            description = data.getOverview();

            CastAdapter castAdapter = new CastAdapter(this, data.getCast());
            rvCast.setHasFixedSize(true);
            rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rvCast.setItemAnimator(new DefaultItemAnimator());
            rvCast.setAdapter(castAdapter);

            reviews = new ArrayList<>();
            
            if (data.getReview().size() > 2) {
                for (int i = 0; i < 3; i++) {
                    reviews.add(data.getReview().get(i));
                }
            } else {
                reviews.addAll(data.getReview());
            }

            ReviewAdapter reviewAdapter = new ReviewAdapter(this, reviews);
            rvReviewDetail.setHasFixedSize(true);
            rvReviewDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvReviewDetail.setItemAnimator(new DefaultItemAnimator());
            rvReviewDetail.setAdapter(reviewAdapter);

            if (data.getPoster() != null) {
                Glide.with(this).load(BuildConfig.IMAGE_URL + data.getPoster()).into(imgPoster);
            }


            if (data.getBackdrop() != null) {
                Glide.with(this).load(BuildConfig.IMAGE_URL_342 + data.getBackdrop()).into(imgBanner);
            }

            if (data.getVideo().size() > 0) {
                btnPlay.setVisibility(View.VISIBLE);
                videoUrl = data.getVideo().get(0);
            }

            String rating;
            if (data.getVoteAverage() > 0) {
                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                rating = decimalFormat.format(data.getVoteAverage());
            } else {
                rating = "-";
            }

            String ratingString = String.format("%s/", rating);

            tvMovieRating.setText(ratingString);

            String releaseDate = !TextUtils.isEmpty(data.getReleaseDate()) ? DateUtil.getReadableDate(data.getReleaseDate()) : "-";
            String language;
            if (data.getLanguage().equals("en")) {
                language = !TextUtils.isEmpty(data.getLanguage()) ? getString(R.string.label_english) : "-";
            } else {
                language = !TextUtils.isEmpty(data.getLanguage()) ? data.getLanguage() : "-";
            }


            String genreString;
            if (data.getGenres() != null) {
                StringBuilder genres = new StringBuilder();
                for (Genre genre : data.getGenres()) {
                    genres.append(genre.getGenreName()).append(", ");
                }
                genres.deleteCharAt(genres.length() - 2);
                genreString = genres.toString();
            } else {
                genreString = "-";
            }

            String subTitle = String.format("%s | %s | %s", releaseDate, genreString, language);
            tvSubTitle.setText(subTitle);

            Uri uri = getIntent().getData();
            if (uri != null) {
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.getCount() == 0) {
                        isFavourited = false;
                    } else {
                        isFavourited = true;
                    }
                    cursor.close();
                }
            }

            changeTextButtonFavorite();
        } else {
            pbMovieDetail.setVisibility(View.GONE);
            tvMovieDetailError.setVisibility(View.VISIBLE);
        }
    }

    private void changeTextButtonFavorite(){
        btnAdd.setText(isFavourited ? "Remove Favorite" : "Add To Favorite");
    }

    @Override
    public void onLoaderReset(Loader<MovieDetail> loader) {

    }

    private void getMovieDetail(int id) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            Bundle bundle = new Bundle();
            bundle.putInt(Keys.KEY_MOVIE_ID, id);
            getLoaderManager().initLoader(0, bundle, this);
            if (pbMovieDetail.getVisibility() == View.GONE) {
                pbMovieDetail.setVisibility(View.VISIBLE);
                tvMovieDetailError.setVisibility(View.GONE);
            }
        } else {
            pbMovieDetail.setVisibility(View.GONE);
            tvMovieDetailError.setVisibility(View.VISIBLE);
            tvMovieDetailError.setText(R.string.label_no_internet_connection);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        LoginManager.INSTANCE.getInstance(this).onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
