package com.assassin.timetableapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class TTableContract {
    private TTableContract() {}

    public static class TableEntry implements BaseColumns {
        public static final String TABLE_NAME = "time_table";
        public static final String COLUMN_NAME_COURSE_NAME = "course_name";
        public static final String COLUMN_NAME_VENUE = "venue";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_TIME = "time";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TableEntry.TABLE_NAME + " (" +
                        TableEntry._ID + " INTEGER PRIMARY KEY," +
                        TableEntry.COLUMN_NAME_COURSE_NAME + " TEXT," +
                        TableEntry.COLUMN_NAME_DAY + " TEXT,"+
                        TableEntry.COLUMN_NAME_TIME + " TEXT,"+
                        TableEntry.COLUMN_NAME_VENUE + " TEXT)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TableEntry.TABLE_NAME;

    }

}