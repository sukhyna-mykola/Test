package com.ddapp.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mykola on 21.02.17.
 */

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Constants.DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + Constants.TABLE_STUDENTS + " ("
                + Constants.ID_FIELD + " " + Constants.TEXT + ","
                + Constants.LAST_NAME_FIELD + " " + Constants.TEXT + ","
                + Constants.FIRST_NAME_FIELD + " " + Constants.TEXT + ","
                + Constants.BIRTHDAY_FIELD + " " + Constants.INTEGER + ","
                + Constants.COURSE_NULL_FIELD + " " + Constants.INTEGER + ","
                + Constants.COURSE_FIRST_FIELD + " " + Constants.INTEGER + ","
                + Constants.COURSE_SECOND_FIELD + " " + Constants.INTEGER + ","
                + Constants.COURSE_THIRD_FIELD + " " + Constants.INTEGER
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<Student> readDataFromDB(int offset) {
        List<Student> students;
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.query(Constants.TABLE_STUDENTS + " LIMIT 20 OFFSET " + offset + " ", null, null, null, null, null, null);

        students = parseStudents(c);
        db.close();

        return students;
    }

    public List<Student> readDataFromDB(Filter filter, int offset) {
        List<Student> students;

        String where = new String(Constants.coursesMap.get(filter.getNameCourse()));
        where += ("='" + filter.getMarkCourse() + "' LIMIT 20 OFFSET " + offset + " ");
        Log.d(Constants.TAG, where);

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query(Constants.TABLE_STUDENTS, null, where, null, null, null, null);
        students = parseStudents(c);
        db.close();

        Log.d(Constants.TAG, "students count = " + students.size());

        return students;
    }

    private List<Student> parseStudents(Cursor c) {
        List<Student> students = new ArrayList<>();
        if (c.moveToFirst()) {

            int firstNameColIndex = c.getColumnIndex(Constants.FIRST_NAME_FIELD);
            int lastNameColIndex = c.getColumnIndex(Constants.LAST_NAME_FIELD);
            int idColIndex = c.getColumnIndex(Constants.ID_FIELD);
            int birthdayColIndex = c.getColumnIndex(Constants.BIRTHDAY_FIELD);
            int courceMarkNullColIndex = c.getColumnIndex(Constants.COURSE_NULL_FIELD);
            int courceMarkFirstColIndex = c.getColumnIndex(Constants.COURSE_FIRST_FIELD);
            int courceMarkSecondColIndex = c.getColumnIndex(Constants.COURSE_SECOND_FIELD);
            int courceMarkThirdColIndex = c.getColumnIndex(Constants.COURSE_THIRD_FIELD);


            do {

                String firstName = c.getString(firstNameColIndex);
                String lastName = c.getString(lastNameColIndex);
                String id = c.getString(idColIndex);
                long birthday = c.getLong(birthdayColIndex);


                int courceMarkNull = c.getInt(courceMarkNullColIndex);
                int courceMarkFirst = c.getInt(courceMarkFirstColIndex);
                int courceMarkSecond = c.getInt(courceMarkSecondColIndex);
                int courceMarkThird = c.getInt(courceMarkThirdColIndex);

                List<Course> courses = new ArrayList<>();
                courses.add(new Course(Constants.COURSES[0], (byte) courceMarkNull));
                courses.add(new Course(Constants.COURSES[1], (byte) courceMarkFirst));
                courses.add(new Course(Constants.COURSES[2], (byte) courceMarkSecond));
                courses.add(new Course(Constants.COURSES[3], (byte) courceMarkThird));

                Log.d(Constants.TAG, firstName);

                Student student = new Student(id, firstName, lastName, birthday, courses);
                students.add(student);

            } while (c.moveToNext());
        }

        c.close();
        return students;
    }

    public void putDataToDB(Student student) {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        Log.d(Constants.TAG, "name = " + student.getFirstName());

        cv.put(Constants.ID_FIELD, student.getId());
        cv.put(Constants.FIRST_NAME_FIELD, student.getFirstName());
        cv.put(Constants.LAST_NAME_FIELD, student.getLastName());
        cv.put(Constants.BIRTHDAY_FIELD, student.getBirthday());
        for (Course course : student.getCourses()) {
            cv.put(Constants.coursesMap.get(course.getName()), course.getMark());
        }
        db.insert(Constants.TABLE_STUDENTS, null, cv);
        db.close();
    }

    public void clearDB() {
        SQLiteDatabase db = getWritableDatabase();
        Log.d(Constants.TAG," deleted "+db.delete(Constants.TABLE_STUDENTS, null, null));
        db.close();
    }
}

