package com.ddapp.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ddapp.test.Constants;
import com.ddapp.test.Filter;
import com.ddapp.test.StudentItem;
import com.ddapp.test.database.Course;
import com.ddapp.test.database.CourseTable;
import com.ddapp.test.database.Student;
import com.ddapp.test.database.StudentCourse;
import com.ddapp.test.database.StudentCourseTable;
import com.ddapp.test.database.StudentTable;

import java.util.ArrayList;
import java.util.List;

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

