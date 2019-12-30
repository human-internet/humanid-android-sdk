package com.ericjohnson.moviecatalogue.loader;

import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ericjohnson.moviecatalogue.BuildConfig;
import com.ericjohnson.moviecatalogue.model.Movies;
import com.ericjohnson.moviecatalogue.utils.Constants;
import com.ericjohnson.moviecatalogue.utils.DateUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by EricJohnson on 3/2/2018.
 */

public class MovieAsynctaskLoader extends AsyncTaskLoader<ArrayList<Movies>> {

    private ArrayList<Movies> data;

    private String query;

    private boolean hasResult = false;

    private int type;


    public MovieAsynctaskLoader(Context context, String query, int type) {
        super(context);
        onContentChanged();
        this.query = query;
        this.type = type;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(data);
    }

    @Override
    public void deliverResult(ArrayList<Movies> data) {
        this.data = data;
        this.hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources(data);
            data = null;
            hasResult = false;
        }
    }

    @Override
    public ArrayList<Movies> loadInBackground() {

        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movies> movies = new ArrayList<>();

        final String TAG=MovieAsynctaskLoader.class.getSimpleName();

        String url;
        if (type == Constants.NOW_PLAYING_TYPE) {
            url = BuildConfig.NOW_PLAYING + BuildConfig.API_KEY + "&language=en-US";
        } else if (type == Constants.SEARCH_TYPE) {
            url = BuildConfig.SEARCH+ BuildConfig.API_KEY + "&language=en-US&query=" + query;
        } else {
            url = BuildConfig.UPCOMING+ BuildConfig.API_KEY + "&language=en-US";
        }

        client.get(url, new AsyncHttpResponseHandler() {

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

                        int id = currentMovies.getInt("id");
                        String title = currentMovies.getString("title");
                        String date = currentMovies.getString("release_date");
                        String imagePath = currentMovies.getString("poster_path");
                        String overview = currentMovies.getString("overview");

                        if (TextUtils.isEmpty(imagePath)) {
                            imagePath = "-1";
                        }

                        date = TextUtils.isEmpty(date) ? "-" : DateUtil.getReadableDate(date);

                        Movies movie = new Movies(id, title, imagePath, date, overview);
                        movies.add(movie);
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
        return movies;
    }

    private void onReleaseResources(ArrayList<Movies> data) {
        data.clear();
    }
}
