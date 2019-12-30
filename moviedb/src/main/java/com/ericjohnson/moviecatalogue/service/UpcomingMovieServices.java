package com.ericjohnson.moviecatalogue.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ericjohnson.moviecatalogue.BuildConfig;
import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.activity.MovieDetailActivity;
import com.ericjohnson.moviecatalogue.model.Movies;
import com.ericjohnson.moviecatalogue.utils.DateUtil;
import com.ericjohnson.moviecatalogue.utils.Keys;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.ericjohnson.moviecatalogue.db.DatabaseContract.CONTENT_URI;

/**
 * Created by EricJohnson on 3/16/2018.
 */

public class UpcomingMovieServices extends IntentService {

    public static final String TAG = UpcomingMovieServices.class.getSimpleName();

    private ArrayList<Movies> movies = new ArrayList<>();

    private static String TAG_TASK_MOVIES_LOG = "MoviesTask";

    public UpcomingMovieServices() {
        super("UpcomingMovieTask");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            getUpdatedMovie();
        }
    }


    private void getUpdatedMovie() {
        SyncHttpClient client = new SyncHttpClient();
        String url = BuildConfig.UPCOMING + BuildConfig.API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
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
                        String overview = responseObject.getString("overview");

                        if (TextUtils.isEmpty(imagePath)) {
                            imagePath = "-1";
                        }

                        date = TextUtils.isEmpty(date) ? "-" : DateUtil.getReadableDate(date);
                        String currentDate = DateUtil.getReadableDate(DateUtil.getCurrentDate());
                        if (date.equals(currentDate)) {
                            Movies movie = new Movies(id, title, imagePath, date, overview);
                            movies.add(movie);
                        }
                    }
                    Log.d(TAG_TASK_MOVIES_LOG, result);
                    if (movies.size() > 0) {
                        for (Movies newMovie : movies) {
                            showNotification(getApplicationContext(), newMovie);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(UpcomingMovieServices.this, R.string.message_error_load_data, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showNotification(Context context, Movies movies) {
        Uri uri = Uri.parse(CONTENT_URI + "/" + movies.getId());
        Intent notifIntent = new Intent(this, MovieDetailActivity.class);
        notifIntent.putExtra(Keys.KEY_MOVIE_ID, movies.getId());
        notifIntent.putExtra(Keys.KEY_TITLE, movies.getTitle());
        notifIntent.setData(uri);
        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(MovieDetailActivity.class)
                .addNextIntent(notifIntent)
                .getPendingIntent(movies.getId(), PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification;
        notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_local_movies)
                .setContentTitle(movies.getTitle())
                .setContentText(String.format("%s %s", movies.getTitle(),
                        getString(R.string.label_notif_message)))
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(movies.getId(), notification);
        }
    }
}