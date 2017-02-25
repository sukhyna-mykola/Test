package com.ddapp.test.database;

/**
 * Created by mykola on 24.02.17.
 */


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ddapp.test.CourseItem;
import com.ddapp.test.StudentItem;

import java.util.ArrayList;
import java.util.List;

import static com.ddapp.test.Constants.TAG;

/**
 * Created by Tan on 1/26/2016.
 */
public class DatabaseManager {
    private Integer mOpenCounter = 0;

    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter+=1;
        if(mOpenCounter == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter-=1;
        if(mOpenCounter == 0) {
            // Closing database
            mDatabase.close();

        }
    }
    public synchronized List<StudentItem> getStudentCourse(int offset) {
        StudentItem student = new StudentItem();
        List<StudentItem> students = new ArrayList<StudentItem>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT * FROM " + Student.TABLE + " LIMIT 20 OFFSET " + offset;
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                student = new StudentItem();
                student.setId(cursor.getString(cursor.getColumnIndex(Student.KEY_StudID)));
                student.setBirthday(cursor.getLong(cursor.getColumnIndex(Student.KEY_Birthday)));
                student.setFirstName(cursor.getString(cursor.getColumnIndex(Student.KEY_FirstName)));
                student.setLastName(cursor.getString(cursor.getColumnIndex(Student.KEY_LastName)));
                List<CourseItem> courseItems = getCourses(student.getId());
                student.setCourses(courseItems);
                Log.d(TAG, student.toString());
                students.add(student);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return students;

    }

    private List<CourseItem> getCourses(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        List<CourseItem> items = new ArrayList<>();
        CourseItem course;
        String selectQuery = " SELECT "
                + "t1." + StudentCourse.KEY_Mark
                + " , t2." + Course.KEY_Name
                + " FROM " + StudentCourse.TABLE + " t1 "
                + " INNER JOIN " + Course.TABLE + " t2 ON t1." + Course.KEY_CourseId + " =  t2." + StudentCourse.KEY_CourseId +
                " WHERE t1." + StudentCourse.KEY_StudID + "='" + id + "'";
        //  Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                course = new CourseItem();
                course.setMark(cursor.getInt(cursor.getColumnIndex(StudentCourse.KEY_Mark)));
                course.setName(cursor.getString(cursor.getColumnIndex(Course.KEY_Name)));
                Log.d(TAG, course.toString());
                items.add(course);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return items;
    }
}
