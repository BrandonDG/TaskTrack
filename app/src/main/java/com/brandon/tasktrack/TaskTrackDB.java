package com.brandon.tasktrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.loader.PackageIconLoader;

import java.util.ArrayList;

/**
 * Created by Brandon on 2016-11-07.
 */

public class TaskTrackDB extends SQLiteOpenHelper {

    /**
     * Declare and instantiate constants used in CourseOpenHelper.
     */
    public  static final String COURSES_TABLE_NAME       = "tasks";
    public  static final String COURSENUMBER_COLUMN_NAME = "coursenumber";
    public  static final String TERM_COLUMN_NAME         = "term";
    public  static final String NAME_COLUMN_NAME         = "name";
    public  static final String DESCRIPTION_COLUMN_NAME  = "description";

    private static final int    SCHEMA_VERSION           = 5;
    private static final String DB_NAME                  = "TaskTrack.db";
    public  static final String TASKS_TABLE_NAME         = "tasks";
    public  static final String ID_COLUMN_NAME           = "_id";
    public  static final String TASKNAME_COLUMN_NAME     = "name";
    public  static final String TASKTYPE_COLUMN_NAME     = "type";
    public  static final String FROMTIME_COLUMN_NAME     = "fromtime";
    public  static final String TOTIME_COLUMN_NAME       = "totime";
    public  static final String COMPLETED_COLUMN_NAME    = "completed";

    // Make 2 tables, and the Type column in first table is ID of type in second table.
    public static final String TASKTYPES_TABLE_NAME       = "tasktypes";
    public static final String TASKTYPENAME_COLUMN_NAME   = "name";
    public static final String TASKTYPECOLOUR_COLUMN_NAME = "colour";


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
        /*
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);

        final String CREATE_TABLE_NAME;

        CREATE_TABLE_NAME = "CREATE TABLE IF NOT EXISTS " + COURSES_TABLE_NAME + " ( " +
                ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COURSENUMBER_COLUMN_NAME + " TEXT NOT NULL, " +
                TERM_COLUMN_NAME + " TEXT NOT NULL," +
                NAME_COLUMN_NAME + " TEXT NOT NULL, " +
                DESCRIPTION_COLUMN_NAME + " TEXT NOT NULL)";
        db.execSQL(CREATE_TABLE_NAME); */

        db.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        String CREATE_TABLE_NAME;
        CREATE_TABLE_NAME = "CREATE TABLE IF NOT EXISTS " + TASKS_TABLE_NAME + " ( " +
                ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASKNAME_COLUMN_NAME + " TEXT NOT NULL, " +
                TASKTYPE_COLUMN_NAME + " TEXT NOT NULL, " +
                FROMTIME_COLUMN_NAME + " TEXT NOT NULL, " +
                TOTIME_COLUMN_NAME + " TEXT NOT NULL, " +
                COMPLETED_COLUMN_NAME + " TEXT NOT NULL);";
        db.execSQL(CREATE_TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " + TASKTYPES_TABLE_NAME);
        CREATE_TABLE_NAME = "CREATE TABLE IF NOT EXISTS " + TASKTYPES_TABLE_NAME + " ( " +
                ID_COLUMN_NAME + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASKTYPENAME_COLUMN_NAME + " TEXT NOT NULL, " +
                TASKTYPECOLOUR_COLUMN_NAME + " TEXT NOT NULL)";
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

    /*
    private void insertTask(final SQLiteDatabase db,
                               final int id,
                               final String name,
                               final String type,
                               final String fromTime,
                               final String toTime) {
        final ContentValues contentValues;
        contentValues = new ContentValues();

        contentValues.put(ID_COLUMN_NAME, id);
        contentValues.put(TASKNAME_COLUMN_NAME, name);
        contentValues.put(TASKTYPE_COLUMN_NAME, type);
        contentValues.put(FROMTIME_COLUMN_NAME, fromTime);
        contentValues.put(TOTIME_COLUMN_NAME, toTime);
        db.insert(TASKS_TABLE_NAME, null, contentValues);
    } */

    public void insertTask(final SQLiteDatabase db,
                            final Task task) {
        final ContentValues contentValues;
        contentValues = new ContentValues();

        contentValues.put(ID_COLUMN_NAME, getNumberOfTasks(db) - 1);
        contentValues.put(TASKNAME_COLUMN_NAME, task.getName());
        contentValues.put(TASKTYPE_COLUMN_NAME, task.getType());
        contentValues.put(FROMTIME_COLUMN_NAME, task.getEndTime());
        contentValues.put(TOTIME_COLUMN_NAME, task.getStartTime());
        contentValues.put(COMPLETED_COLUMN_NAME, task.getCompleted());
        db.insert(TASKS_TABLE_NAME, null, contentValues);
    }

    public void insertTaskType(final SQLiteDatabase db,
                                final TaskType tasktype) {
        final ContentValues contentValues;
        contentValues = new ContentValues();

        contentValues.put(ID_COLUMN_NAME, getNumberOfTaskTypes(db) - 1);
        contentValues.put(TASKTYPENAME_COLUMN_NAME, tasktype.getName());
        contentValues.put(TASKTYPECOLOUR_COLUMN_NAME, tasktype.getColour());
        db.insert(TASKTYPES_TABLE_NAME, null, contentValues);
    }

    /*
    private void insertTaskType(final SQLiteDatabase db,
                            final int id,
                            final String name,
                            final String colour) {
        final ContentValues contentValues;
        contentValues = new ContentValues();

        contentValues.put(ID_COLUMN_NAME, id);
        contentValues.put(TASKTYPENAME_COLUMN_NAME, name);
        contentValues.put(TASKTYPECOLOUR_COLUMN_NAME, colour);
        db.insert(TASKTYPES_TABLE_NAME, null, contentValues);
    } */

    /**
     * Get amount of Tasks in database.
     * @param db
     * @return
     */
    private long getNumberOfTasks(final SQLiteDatabase db) {
        final long numEntries;
        numEntries = DatabaseUtils.queryNumEntries(db, TASKS_TABLE_NAME);
        return numEntries;
    }

    private long getNumberOfTaskTypes(final SQLiteDatabase db) {
        final long numEntries;
        numEntries = DatabaseUtils.queryNumEntries(db, TASKTYPES_TABLE_NAME);
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

    public ArrayList<String> getTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> results = new ArrayList<String>();
        String query = "SELECT DISTINCT " + TASKNAME_COLUMN_NAME + " FROM " + TASKS_TABLE_NAME;
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

    public ArrayList<String> getTaskTypes() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> results = new ArrayList<String>();
        String query = "SELECT DISTINCT " + TASKTYPENAME_COLUMN_NAME + " FROM " + TASKTYPES_TABLE_NAME;
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

    public String queryTaskName(String taskname) {
        SQLiteDatabase db = this.getReadableDatabase();
        String courseNumber;
        String query = "SELECT DISTINCT " + TASKNAME_COLUMN_NAME + " FROM " + TASKS_TABLE_NAME
                + " WHERE " + TASKNAME_COLUMN_NAME + " = " + "'" + taskname + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        courseNumber = cursor.getString(0);
        cursor.close();
        db.close();
        return courseNumber;
    }

    public String queryTaskTypeName(String tasktypename) {
        SQLiteDatabase db = this.getReadableDatabase();
        String courseNumber;
        String query = "SELECT DISTINCT " + TASKTYPENAME_COLUMN_NAME + " FROM " + TASKTYPES_TABLE_NAME
                + " WHERE " + TASKTYPENAME_COLUMN_NAME + " = " + "'" + tasktypename + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        courseNumber = cursor.getString(0);
        cursor.close();
        db.close();
        return courseNumber;
    }

    public boolean removeTask(String taskname) {
        return (this.getWritableDatabase()).delete(TASKS_TABLE_NAME, TASKNAME_COLUMN_NAME + " = ?", new String[] { taskname }) > 0;
    }

    public String removeTaskType(String tasktypename) {
        SQLiteDatabase db = this.getReadableDatabase();
        String courseNumber;
        String query = "DELETE FROM " + TASKTYPES_TABLE_NAME
                + " WHERE " + TASKTYPENAME_COLUMN_NAME + " = " + "'" + tasktypename + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        courseNumber = cursor.getString(0);
        cursor.close();
        db.close();
        return courseNumber;
    }

    public boolean markAsComplete(String taskname) {
        final ContentValues contentValues;
        contentValues = new ContentValues();
        contentValues.put(COMPLETED_COLUMN_NAME, "true");
        return (this.getWritableDatabase()).update(TASKS_TABLE_NAME, contentValues, TASKNAME_COLUMN_NAME + " = ?", new String[] { taskname }) > 0;
    }
}