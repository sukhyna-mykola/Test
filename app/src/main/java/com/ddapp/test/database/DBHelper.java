package com.ddapp.test.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ddapp.test.Constants;

/**
 * Created by mykola on 21.02.17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=on;");
        //All necessary tables you like to create will create here
        db.execSQL(CourseTable.createTable());
        db.execSQL(StudentTable.createTable());
        db.execSQL(StudentCourseTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + Course.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + StudentCourse.TABLE);
        onCreate(db);
    }

}

