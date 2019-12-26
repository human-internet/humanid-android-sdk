package com.ericjohnson.moviecatalogue.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ericjohnson.moviecatalogue.BuildConfig;
import com.ericjohnson.moviecatalogue.model.Review;
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

public class ReviewAsynctaskLoader extends AsyncTaskLoader<ArrayList<Review>> {

    private ArrayList<Review> data;

    private int id;

    private boolean hasResult = false;

    public ReviewAsynctaskLoader(Context context, int id) {
        super(context);
        onContentChanged();
        this.id = id;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(data);
    }

    @Override
    public void deliverResult(ArrayList<Review> data) {
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
    public ArrayList<Review> loadInBackground() {

        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Review> reviews = new ArrayList<>();

        final String TAG=ReviewAsynctaskLoader.class.getSimpleName();

        String urlReview = BuildConfig.MOVIE + id + "/reviews?api_key=" +
                BuildConfig.API_KEY;

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
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray resultsArray = responseObject.getJSONArray("results");
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject reviewResults = resultsArray.getJSONObject(i);

                        String content = reviewResults.getString("content");
                        String author = reviewResults.getString("author");
                        Review review = new Review(content, author);
                        reviews.add(review);
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
        return reviews;
    }

    private void onReleaseResources(ArrayList<Review> data) {
        data.clear();
    }
}

