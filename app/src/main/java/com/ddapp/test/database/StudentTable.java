package com.ddapp.test.database;

/**
 * Created by mykola on 24.02.17.
 */


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Tan on 1/26/2016.
 */
public class StudentTable {

    private Student student;

    public StudentTable() {
        student = new Student();
    }

    public static String createTable() {
        return "CREATE TABLE " + Student.TABLE + "("
                + Student.KEY_StudID + " TEXT PRIMARY KEY  ,"
                + Student.KEY_FirstName + " TEXT, "
                + Student.KEY_Birthday + " INTEGER, "
                + Student.KEY_LastName + " TEXT );";
    }


    public void insert(Student student) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_StudID, student.getStudentId());
        values.put(Student.KEY_LastName, student.getLastName());
        values.put(Student.KEY_FirstName, student.getFirstName());
        values.put(Student.KEY_Birthday, student.getBirthday());

        // Inserting Row
        db.insert(Student.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }


    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Student.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }


}
