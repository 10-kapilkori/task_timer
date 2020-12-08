package com.example.tasktimer;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class TasksContract {
    static final String TABLE_NAME = "Tasks";

    public static class Columns{
        public static final String _ID = BaseColumns._ID;
        public static final String Tasks_Name = "Name";
        public static final String Tasks_Description = "Description";
        public static final String Tasks_SortOrder = "SortOrder";

        private Columns() {
            // this is created only for single instantiation
        }
    }

    /**
     * The URI to access the Tasks table.
     */

    public static final Uri Content_URI = Uri.withAppendedPath(AppProvider.CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AppProvider.CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AppProvider.CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildTaskUri(long taskId) {
        return ContentUris.withAppendedId(Content_URI, taskId);
    }

    public static long getTaskId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
