package com.ddapp.test.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ddapp.test.CourseItem;
import com.ddapp.test.filter.Filter;
import com.ddapp.test.StudentItem;

import java.util.ArrayList;
import java.util.List;

import static com.ddapp.test.Constants.TAG;

/**
 * Created by mykola on 24.02.17.
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
        mOpenCounter += 1;
        if (mOpenCounter == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter -= 1;
        if (mOpenCounter == 0) {
            // Closing database
            mDatabase.close();

        }
    }

    /**
     * Отримує список всіх студентів
     * @param offset - зсув
     * @return список всіх студентів
     */
    public synchronized List<StudentItem> getStudentCourse(int offset) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT * FROM " + Student.TABLE + " LIMIT 20 OFFSET " + offset;
        Log.d(TAG, selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        List<StudentItem> students = parse(cursor);
        cursor.close();

        DatabaseManager.getInstance().closeDatabase();
        return students;

    }

    /**
     * Отримує список студентів по заданому фільтру
     * @param offset зсув
     * @param filter параметри вибірки
     * @return
     */
    public synchronized List<StudentItem> getStudentCourse(int offset, Filter filter) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT S." + Student.KEY_FirstName + ", S." + Student.KEY_LastName +
                ", S." + Student.KEY_Birthday + ", S." + Student.KEY_StudID + " FROM " + StudentCourse.TABLE + " AS SC " +
                " INNER JOIN " + Course.TABLE + " AS C ON C." + Course.KEY_CourseId + " = SC." + StudentCourse.KEY_CourseId +
                " INNER JOIN " + Student.TABLE + " AS S ON S." + Student.KEY_StudID + " = SC." + StudentCourse.KEY_StudID +
                " WHERE C." + Course.KEY_Name + " = '" + filter.getName() + "' AND SC." + StudentCourse.KEY_Mark + " = " + filter.getMark() +
                " LIMIT 20 OFFSET " + offset;

        /*
              selectQuery =   "select * from "+StudentCourse.TABLE+" join " +
                        "(select "+ StudentCourse.TABLE + "." + StudentCourse.KEY_StudID +" from "+StudentCourse.TABLE +
                        " join "+Course.TABLE+" ON "+Course.TABLE+"."+Course.KEY_CourseId+"="+StudentCourse.TABLE+"."+StudentCourse.KEY_CourseId+
                        " where "+StudentCourse.TABLE+"."+StudentCourse.KEY_Mark+" = "+filter.getMarkCourse()+" AND "+Course.TABLE+"."+Course.KEY_Name+" = '"+filter.getNameCourse()+"') t1 " +
                        " ON "+StudentCourse.TABLE+"."+StudentCourse.KEY_StudID+"=t1."+ StudentCourse.KEY_StudID  +
                        " join " + Student.TABLE + " ON " + Student.TABLE + "." + Student.KEY_StudID + "=" + StudentCourse.TABLE + "." + StudentCourse.KEY_StudID +
                        " join " + Course.TABLE + " ON " + Course.TABLE + "." + Course.KEY_CourseId + "=" + StudentCourse.TABLE + "." + StudentCourse.KEY_CourseId +
                        " LIMIT 80 OFFSET " + offset;*/
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<StudentItem> students = parse(cursor);
        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return students;
    }

    /**
     * Отримує cприсок предметів ID студента
     * @param id ID студента
     * @return cприсок предметів
     */
    private List<CourseItem> getCourses(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        List<CourseItem> items = new ArrayList<>();
        CourseItem course;
        String selectQuery = " SELECT "
                + " SC." + StudentCourse.KEY_Mark
                + " , C." + Course.KEY_Name
                + " FROM " + StudentCourse.TABLE + " AS SC "
                + " INNER JOIN " + Course.TABLE + " AS C ON C." + Course.KEY_CourseId + " =  SC." + StudentCourse.KEY_CourseId +
                " WHERE SC." + StudentCourse.KEY_StudID + "='" + id + "'";
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



    /**
     * Формує список студентів із Cursor
     * @param cursor курсор
     * @return список студентів
     */
    private List<StudentItem> parse(Cursor cursor) {
        StudentItem student = new StudentItem();
        List<StudentItem> students = new ArrayList<StudentItem>();
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

        return students;
    }

}
