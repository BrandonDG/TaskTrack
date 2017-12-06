package com.brandon.tasktrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//import com.koushikdutta.ion.loader.PackageIconLoader;

import java.util.ArrayList;

/**
 * Created by Brandon on 2016-11-07.
 */

public class TaskTrackDB extends SQLiteOpenHelper {

    /**
     * Declare and instantiate constants used in CourseOpenHelper.
     */
    private static final int    SCHEMA_VERSION            = 4;
    private static final String DB_NAME                  = "a3.db";
    public  static final String COURSES_TABLE_NAME       = "courses";
    public  static final String ID_COLUMN_NAME           = "_id";
    public  static final String COURSENUMBER_COLUMN_NAME = "coursenumber";
    public  static final String TERM_COLUMN_NAME         = "term";
    public  static final String NAME_COLUMN_NAME         = "name";
    public  static final String DESCRIPTION_COLUMN_NAME  = "description";

    /**
     * Constructor.
     * @param ctx object context
     */
    public TaskTrackDB(final Context ctx) {
        super(ctx, DB_NAME, null, SCHEMA_VERSION);
    }

    /**
     * Oncreate behavior.
     * @param db database to create table on
     */
    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);

        final String CREATE_TABLE_NAME;

        CREATE_TABLE_NAME = "CREATE TABLE IF NOT EXISTS " + COURSES_TABLE_NAME + " ( " +
                ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COURSENUMBER_COLUMN_NAME + " TEXT NOT NULL, " +
                TERM_COLUMN_NAME + " TEXT NOT NULL," +
                NAME_COLUMN_NAME + " TEXT NOT NULL, " +
                DESCRIPTION_COLUMN_NAME + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_NAME);
    }

    /**
     * onUpgrade behavior.
     * @param db database object
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(final SQLiteDatabase db,
                          final int oldVersion,
                          final int newVersion) {
        onCreate(db);
    }

    /**
     * Populate SQLiteDatabase with JSON values.
     * @param context current context
     */
    /*
    public void populate(Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            JsonObject obj = Ion.with(context).load("http://max.bcit.ca/comp.json")
                    .asJsonObject().get();

            final JsonArray terms = obj.get("terms").getAsJsonArray();

            if (getNumberOfCourses(db) == 0) {
                int idCounter = 0;
                db.beginTransaction();
                try {
                } finally {
                    try {
                        for (final JsonElement element : terms) {
                            String term = element.getAsJsonObject().get("term").getAsString();
                            JsonArray classes = element.getAsJsonObject().get("classes").getAsJsonArray();
                            for (final JsonElement course : classes) {
                                String num = course.getAsJsonObject().get("id").getAsString();
                                String name = course.getAsJsonObject().get("name").getAsString();
                                String description = course.getAsJsonObject().get("description").getAsString();

                                insertCourses(db, idCounter++, "Term " + term, num, name, description);
                            }
                        }
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                }
            }

        } catch (Exception ex) {

        }
    } */

    /**
     * Insert a course into the SQLiteDatabase.
     * @param db to enter in
     * @param id to ener
     * @param term to enter
     * @param coursenumber to enter
     * @param name to enter
     * @param description to enter
     */
    private void insertCourses(final SQLiteDatabase db,
                               final int id,
                               final String term,
                               final String coursenumber,
                               final String name,
                               final String description) {
        final ContentValues contentValues;
        contentValues = new ContentValues();

        contentValues.put(ID_COLUMN_NAME, id);
        contentValues.put(TERM_COLUMN_NAME, term);
        contentValues.put(COURSENUMBER_COLUMN_NAME, coursenumber);
        contentValues.put(NAME_COLUMN_NAME, name);
        contentValues.put(DESCRIPTION_COLUMN_NAME, description);
        db.insert(COURSES_TABLE_NAME, null, contentValues);
    }

    /**
     * Get amount of courses in database.
     * @param db
     * @return
     */
    private long getNumberOfCourses(final SQLiteDatabase db) {
        final long numEntries;

        numEntries = DatabaseUtils.queryNumEntries(db, COURSES_TABLE_NAME);

        return numEntries;
    }

    /**
     * Get all distinct terms in database.
     * @return ArrayList<String> containing terms.
     */
    public ArrayList<String> getTerms() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> results = new ArrayList<String>();
        String query = "SELECT DISTINCT " + TERM_COLUMN_NAME + " FROM " + COURSES_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            results.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return results;
    }

    /**
     * Get all course numbers for a term.
     * @param term to get course numbers of
     * @return ArrayList<String> with results
     */
    public ArrayList<String> queryTermCourseNumbers(String term) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> results = new ArrayList<String>();
        String query = "SELECT DISTINCT " + COURSENUMBER_COLUMN_NAME + " FROM " + COURSES_TABLE_NAME
                + " WHERE " + TERM_COLUMN_NAME + " = " + "'" + term + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            results.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return results;

    }

    /**
     * Get course description for a course.
     * @param num of course
     * @return String containing the description
     */
    public String queryCourseDescription(String num) {
        SQLiteDatabase db = this.getReadableDatabase();
        String description;
        String query = "SELECT DISTINCT " + DESCRIPTION_COLUMN_NAME + " FROM " + COURSES_TABLE_NAME
                + " WHERE " + COURSENUMBER_COLUMN_NAME + " = " + "'" + num + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        description = cursor.getString(0);
        cursor.close();
        db.close();
        return description;
    }

    /**
     * Get a course name for a course number.
     * @param num to search for
     * @return name
     */
    public String queryCourseName(String num) {
        SQLiteDatabase db = this.getReadableDatabase();
        String courseNumber;
        String query = "SELECT DISTINCT " + NAME_COLUMN_NAME + " FROM " + COURSES_TABLE_NAME
                + " WHERE " + COURSENUMBER_COLUMN_NAME + " = " + "'" + num + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        courseNumber = cursor.getString(0);
        cursor.close();
        db.close();
        return courseNumber;
    }
}