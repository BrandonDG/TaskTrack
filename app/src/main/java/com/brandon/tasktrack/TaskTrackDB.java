package com.brandon.tasktrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Brandon on 2017-12-04.
 */

public class TaskTrackDB extends SQLiteOpenHelper {

    /**
     * Declare and instantiate constants used in TaskTrackDB.
     */
    private static final int    SCHEMA_VERSION           = 5;
    private static final String DB_NAME                  = "TaskTrack.db";
    public  static final String ID_COLUMN_NAME           = "_id";

    public  static final String TASKS_TABLE_NAME         = "tasks";
    public  static final String TASKNAME_COLUMN_NAME     = "name";
    public  static final String TASKTYPE_COLUMN_NAME     = "type";
    public  static final String FROMTIME_COLUMN_NAME     = "fromtime";
    public  static final String TOTIME_COLUMN_NAME       = "totime";
    public  static final String COMPLETED_COLUMN_NAME    = "completed";

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