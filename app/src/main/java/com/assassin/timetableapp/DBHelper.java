package com.assassin.timetableapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TDB.db";
    public static final String TABLE_NAME = "time_table";
    public static final String COLUMN_NAME_EVENT_NAME = "event_name";
    public static final String COLUMN_NAME_VENUE = "venue";
    public static final String COLUMN_NAME_DAY = "day";
    private static final String COLUMN_ID="_id";
    public static final String COLUMN_NAME_TIME = "time";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_EVENT_NAME + " TEXT," +
                    COLUMN_NAME_DAY + " TEXT,"+
                    COLUMN_NAME_TIME + " TEXT,"+
                    COLUMN_NAME_VENUE + " TEXT)";
    private static final String SQL_CREATE_ENTRIES_IF_NOT_EXIST =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( " +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_EVENT_NAME + " TEXT," +
                    COLUMN_NAME_DAY + " TEXT,"+
                    COLUMN_NAME_TIME + " TEXT,"+
                    COLUMN_NAME_VENUE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES_IF_NOT_EXIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertData (String event_name, String day, String time, String venue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_EVENT_NAME, event_name);
        contentValues.put(COLUMN_NAME_DAY, day);
        contentValues.put(COLUMN_NAME_TIME, time);
        contentValues.put(COLUMN_NAME_VENUE, venue);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }


    public Cursor getAllEvents() {
            List<Today> todayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String[] projection = {
                    COLUMN_ID,
                    COLUMN_NAME_EVENT_NAME,
                    COLUMN_NAME_DAY,
                    COLUMN_NAME_TIME,
                    COLUMN_NAME_VENUE
            };
            String selection = COLUMN_NAME_DAY + " = ?";
            String[] selectionArgs = {};
            String sortOrder =
                    COLUMN_NAME_DAY + " DESC";

        Cursor cursor = db.rawQuery("SELECT  * FROM time_table", null);

        return cursor;
    }

    public Cursor deleteEvent(String eventName){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {

                COLUMN_NAME_EVENT_NAME,
                COLUMN_NAME_DAY,
                COLUMN_NAME_TIME,
                COLUMN_NAME_VENUE
        };
        String selection = COLUMN_NAME_DAY + " = ?";
        String[] selectionArgs = {eventName};
        String sortOrder =
                COLUMN_NAME_DAY + " DESC";
        Cursor cursor = db.rawQuery("SELECT  * FROM time_table WHERE day = " + eventName, null);

        return  cursor;
}
    public List<Today> getEvent(String eventName){
        List<Today> todayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {

                COLUMN_NAME_EVENT_NAME,
                COLUMN_NAME_DAY,
                COLUMN_NAME_TIME,
                COLUMN_NAME_VENUE
        };

// Filter results WHERE "title" = 'My Title'
        String selection = COLUMN_NAME_DAY + " = ?";
        String[] selectionArgs = {eventName};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                COLUMN_NAME_DAY + " DESC";

        Cursor cursor = db.rawQuery("SELECT  * FROM time_table WHERE day" + " = ?", selectionArgs);


        if(cursor.moveToFirst()){
            do{
                Today today = new Today();
                today.setCourse_name(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EVENT_NAME)));
                today.setVenue(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_VENUE)));
                today.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIME)));
                today.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            }
            while (cursor.moveToNext());
        }


        return  todayList;


    }

    public Cursor getEventsForDay(String dayToday) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {

                COLUMN_NAME_EVENT_NAME,
                COLUMN_NAME_DAY,
                COLUMN_NAME_TIME,
                COLUMN_NAME_VENUE
        };

// Filter results WHERE "title" = 'My Title'
        String selection = COLUMN_NAME_DAY + " = ?";
        String[] selectionArgs = {dayToday};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                COLUMN_NAME_DAY + " DESC";

        Cursor cursor = db.rawQuery("SELECT  * FROM time_table WHERE day" + " = ?", selectionArgs);

        cursor.moveToFirst();
        return  cursor;
    }

    public int updateEvent(Today today){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_EVENT_NAME, today.getCourse_name());
        contentValues.put(COLUMN_NAME_DAY, today.getDay());
        contentValues.put(COLUMN_NAME_TIME, today.getTime());
        contentValues.put(COLUMN_NAME_VENUE, today.getVenue());

        return db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[] {String.valueOf(today.get_id())});

    }
    public void deleteEvent(Today today){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new  String[] {String.valueOf(today.get_id())});
        db.close();
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }
}
