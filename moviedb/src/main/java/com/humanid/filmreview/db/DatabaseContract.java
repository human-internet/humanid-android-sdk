package com.humanid.filmreview.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by EricJohnson on 3/25/2018.
 */

public class DatabaseContract {

    public static final String TABLE_MOVIES = "movies";

    public static final class MoviesColumns implements BaseColumns {

        public static final String TITLE = "title";

        public static final String POSTER = "poster";

        public static final String RELEASEDATE = "releasedate";

        public static final String DESCRIPTION = "description";
    }

    public static final String AUTHORITY = "com.ericjohnson.moviecatalogue";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIES)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
}
