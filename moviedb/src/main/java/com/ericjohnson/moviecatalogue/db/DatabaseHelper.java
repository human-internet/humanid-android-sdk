package com.ericjohnson.moviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ericjohnson.moviecatalogue.db.DatabaseContract.MoviesColumns;
import static com.ericjohnson.moviecatalogue.db.DatabaseContract.TABLE_MOVIES;

/**
 * Created by EricJohnson on 3/25/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbmovies";

    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_TABLE_MOVIES = String.format(
            "CREATE TABLE %s (" + "%s INTEGER PRIMARY KEY,"
                    + " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_MOVIES,
            MoviesColumns._ID,
            MoviesColumns.TITLE,
            MoviesColumns.POSTER,
            MoviesColumns.RELEASEDATE,
            MoviesColumns.DESCRIPTION
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }
}
