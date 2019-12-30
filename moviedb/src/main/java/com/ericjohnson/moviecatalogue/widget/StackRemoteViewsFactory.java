package com.ericjohnson.moviecatalogue.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.ericjohnson.moviecatalogue.BuildConfig;
import com.ericjohnson.moviecatalogue.R;
import com.ericjohnson.moviecatalogue.db.DatabaseContract;
import com.ericjohnson.moviecatalogue.db.MoviesHelper;
import com.ericjohnson.moviecatalogue.model.Movies;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Movies> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private MoviesHelper moviesHelper;

    public StackRemoteViewsFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

    }

    @Override
    public void onCreate() {
        moviesHelper = new MoviesHelper(mContext);
        moviesHelper.open();
    }

    @Override
    public void onDataSetChanged() {
        Cursor cursor = moviesHelper.queryProvider();
        if (cursor.moveToFirst()) {
            do {
                Movies movies = new Movies();
                movies.setId(cursor.getInt(cursor.getColumnIndex(DatabaseContract.MoviesColumns._ID)));
                movies.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesColumns.TITLE)));
                movies.setPoster(cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesColumns.POSTER)));
                movies.setReleaseDate(cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesColumns.RELEASEDATE)));
                movies.setDescription(cursor.getString(cursor.getColumnIndex(DatabaseContract.MoviesColumns.DESCRIPTION)));
                mWidgetItems.add(movies);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
    }

    @Override
    public void onDestroy() {
        mWidgetItems.clear();
        moviesHelper.close();
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Bitmap bmp = null;
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        String movieTitle = "";
        if (mWidgetItems.size() > 0) {
            try {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.ic_image)
                        .error(R.drawable.ic_image)
                        .fallback(R.drawable.ic_image);

                bmp = Glide.with(mContext)
                        .setDefaultRequestOptions(requestOptions)
                        .asBitmap()
                        .load(BuildConfig.IMAGE_URL_342 + mWidgetItems.get(position).getPoster())
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

                movieTitle = mWidgetItems.get(position).getTitle();

            } catch (InterruptedException | ExecutionException e) {
                Log.d("Widget Load Error", "error");
            }

            rv.setImageViewBitmap(R.id.imageView, bmp);
            rv.setTextViewText(R.id.tv_detail, movieTitle);

            Bundle extras = new Bundle();
            extras.putInt(StackWidget.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);

            rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
            return rv;
        }
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
