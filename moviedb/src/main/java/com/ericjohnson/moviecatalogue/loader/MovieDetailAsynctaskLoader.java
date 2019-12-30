package com.ericjohnson.moviecatalogue.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ericjohnson.moviecatalogue.BuildConfig;
import com.ericjohnson.moviecatalogue.model.Cast;
import com.ericjohnson.moviecatalogue.model.Genre;
import com.ericjohnson.moviecatalogue.model.MovieDetail;
import com.ericjohnson.moviecatalogue.model.Review;
import com.ericjohnson.moviecatalogue.model.Video;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by EricJohnson on 3/3/2018.
 */

public class MovieDetailAsynctaskLoader extends AsyncTaskLoader<MovieDetail> {

    private int id;

    public MovieDetailAsynctaskLoader(Context context, int id) {
        super(context);
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public MovieDetail loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<String> videos = new ArrayList<>();

        final MovieDetail[] movieDetail = new MovieDetail[1];

        final ArrayList<Cast> castList = new ArrayList<>();

        final ArrayList<Review> reviewList = new ArrayList<>();

        final String TAG = MovieDetailAsynctaskLoader.class.getSimpleName();

        String url = BuildConfig.MOVIE + id + "?api_key=" +
                BuildConfig.API_KEY + "&language=en-US";

        String urlVideo = BuildConfig.MOVIE + id + "/videos?api_key=" +
                BuildConfig.API_KEY;

        String urlCast = BuildConfig.MOVIE + id + "/credits?api_key=" +
                BuildConfig.API_KEY;

        String urlReview = BuildConfig.MOVIE + id + "/reviews?api_key=" +
                BuildConfig.API_KEY;

        client.get(urlVideo, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray resultsArray = responseObject.getJSONArray("results");
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject currentMovies = resultsArray.getJSONObject(i);
                        String videoUrl = currentMovies.getString("key");

                        videos.add(videoUrl);
                    }
                    Log.d(TAG, result);

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        client.get(urlCast, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    String castCharacter;
                    String castName;
                    String castProfilePath;
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray castArray = responseObject.getJSONArray("cast");
                    if (castArray.length() > 0) {
                        for (int i = 0; i < castArray.length(); i++) {
                            JSONObject genreResult = castArray.getJSONObject(i);
                            castCharacter = genreResult.getString("character");
                            castName = genreResult.getString("name");
                            castProfilePath = genreResult.getString("profile_path");
                            Log.d(TAG, result);
                            Cast cast = new Cast(castCharacter, castName, castProfilePath);
                            castList.add(cast);
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        client.get(urlReview, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    String reviewContent;
                    String reviewAuthor;
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray reviewArray = responseObject.getJSONArray("results");
                    if (reviewArray.length() > 0) {
                        for (int i = 0; i < reviewArray.length(); i++) {
                            JSONObject genreResult = reviewArray.getJSONObject(i);
                            reviewContent = genreResult.getString("content");
                            reviewAuthor = genreResult.getString("author");
                            Log.d(TAG, result);
                            Review review = new Review(reviewContent, reviewAuthor);
                            reviewList.add(review);
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    ArrayList<Genre> genres = new ArrayList<>();
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    int id = responseObject.getInt("id");
                    String title = responseObject.getString("title");
                    String poster = responseObject.getString("poster_path");
                    String releaseDate = responseObject.getString("release_date");
                    String language = responseObject.getString("original_language");
                    String overview = responseObject.getString("overview");
                    String backdrop = responseObject.getString("backdrop_path");
                    boolean video = responseObject.getBoolean("video");
                    float rating = (float) responseObject.getDouble("vote_average");
                    JSONArray genresArray = responseObject.getJSONArray("genres");
                    if (genresArray.length() > 0) {
                        for (int i = 0; i < genresArray.length(); i++) {
                            JSONObject genreResult = genresArray.getJSONObject(i);
                            String genreName = genreResult.getString("name");
                            Genre genre = new Genre(genreName);
                            genres.add(genre);
                        }
                    } else {
                        genres = null;
                    }
                    Log.d(TAG, result);
                    movieDetail[0] = new MovieDetail(id, title, language, overview, poster, genres,
                            releaseDate, rating, videos, backdrop, castList, reviewList);

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return movieDetail[0];
    }

    @Override
    protected void onReset() {
        super.onReset();
    }
}
