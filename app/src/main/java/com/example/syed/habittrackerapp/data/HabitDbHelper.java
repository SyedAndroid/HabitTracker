package com.example.syed.habittrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.syed.habittrackerapp.data.HabitContract.HabitEntry;

/**
 * Created by syed on 2017-06-12.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habits.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ( "
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_DESC + " TEXT, "
                + HabitEntry.COLUMN_REPETITION + " INTEGER NOT NULL DEFAULT 1,"
                + HabitEntry.COLUMN_STATUS + " INTEGER NOT NULL DEFAULT 2);";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String SQL_DROP_TABLE = " DROP TABLE " + HabitEntry.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(SQL_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}
