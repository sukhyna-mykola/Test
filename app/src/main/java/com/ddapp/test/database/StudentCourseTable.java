package com.ddapp.test.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mykola on 24.02.17.
 */

public class StudentCourseTable {
    private final String TAG = StudentCourseTable.class.getSimpleName().toString();

    public static String createTable() {

        return "CREATE TABLE " + StudentCourse.TABLE + "("
                + StudentCourse.KEY_StudCourseId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + StudentCourse.KEY_StudID + " TEXT, "
                + StudentCourse.KEY_CourseId + " INTEGER, "
                + StudentCourse.KEY_Mark + " INTEGER ,"
                + " FOREIGN KEY (" + StudentCourse.KEY_StudID + ") REFERENCES " + Student.TABLE + "(" + Student.KEY_StudID + ") ,"
                + " FOREIGN KEY (" + StudentCourse.KEY_CourseId + ") REFERENCES " + Course.TABLE + "(" + Course.KEY_CourseId + ")"
                + ");";
    }


    public void insert(StudentCourse studentCourse) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(studentCourse.KEY_StudID, studentCourse.getStudentID());
        values.put(studentCourse.KEY_CourseId, studentCourse.getCourseID());
        values.put(studentCourse.KEY_Mark, studentCourse.getMark());

        // Inserting Row
        db.insert(StudentCourse.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();

    }


    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(StudentCourse.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

}