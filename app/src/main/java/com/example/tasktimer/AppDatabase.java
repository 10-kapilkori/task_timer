package com.example.tasktimer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class AppDatabase extends SQLiteOpenHelper {
    private static final String TAG = "AppDatabase";

    private static final String Database_Name = "TaskTimer.db";
    private static final int Database_Version = 1;

    private static AppDatabase instance = null;
    private AppDatabase(Context context) {
        super(context, Database_Name, null, Database_Version);
        Log.d(TAG, "AppDatabase: constructor");
    }

    /**
     * get an instance of the app's singleton database helper object
     * @param context the content providers context
     * @return a SQlite database helper object
     */

    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: starts");

        String sSQL;

//        sSQL = "CREATE TABLE Tasks (_id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, Description TEXT, SortOrder INTEGER, CategoryID INTEGER);";

        sSQL = "CREATE TABLE " + TasksContract.TABLE_NAME + " ("
                + TasksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
                + TasksContract.Columns.Tasks_Name + " TEXT NOT NULL, "
                + TasksContract.Columns.Tasks_Description + " TEXT, "
                + TasksContract.Columns.Tasks_SortOrder + " INTEGER);";

        Log.d(TAG, sSQL);
        db.execSQL(sSQL);

        Log.d(TAG, "onCreate: ends");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        switch (oldVersion){
            case 1:
                // upgrade for old version 1
                break;
            default:
                throw new IllegalStateException("onUpgrade() with unknown newVersion " + newVersion);
        }
        Log.d(TAG, "onUpgrade: ends");
    }
}
