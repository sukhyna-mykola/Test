package com.ddapp.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.ddapp.test.database.Course;
import com.ddapp.test.database.CourseTable;
import com.ddapp.test.database.DBHelper;
import com.ddapp.test.database.DatabaseManager;
import com.ddapp.test.database.Student;
import com.ddapp.test.database.StudentCourse;
import com.ddapp.test.database.StudentCourseTable;
import com.ddapp.test.database.StudentTable;
import com.ddapp.test.filter.Filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.ddapp.test.Constants.COURSES;
import static com.ddapp.test.Constants.TAG;

/**
 * Created by mykola on 20.02.17.
 */
public class StudentsManager {
    private static StudentsManager manager;
    private List<StudentItem> students;

    private Filter filter;
    private Update callback;
    private SharedPreferences sPref;

    public static StudentsManager getInstance(Context context) {
        if (manager == null)
            manager = new StudentsManager(context);
        return manager;
    }

    public void setCallback(Update callback) {
        this.callback = callback;
    }

    private StudentsManager(Context context) {
        students = new ArrayList<>();
        filter = new Filter();
        DatabaseManager.initializeInstance(new DBHelper(context));
        sPref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
    }

    public List<StudentItem> getStudents() {
        return students;
    }

    public Filter getFilter() {
        return filter;
    }

    /**
     * Повертає студента по його id
     *
     * @param id - ідентифікатор студента
     * @return студента, по заданому id
     */
    public StudentItem getStudent(String id) {
        for (StudentItem student : students) {
            if (student.getId().equals(id))
                return student;
        }
        return null;
    }

    /**
     * Видаляє всіх студентів зі списку
     */
    public void removeAll() {
        students.clear();
        callback.resetViews();
    }

    /**
     * Завантажує дані про студентів з сервера
     */
    public  void loadDataFromURL() {
        new HTTPTask().execute();
    }


    /**
     * Завантажує дані про студентів з бази даних
     */
    public  void loadDataFromDB() {
        new ReadDatabaseTask().execute();

    }

    /**
     * Додає нових студениів в список
     *
     * @param newStudents - нові студенти
     */
    private void addStudents(List<StudentItem> newStudents) {
        for (StudentItem student : newStudents) {
            students.add(student);
        }
    }

    /**
     * Обчислює зсув
     *
     * @return зсув
     */
    private int calcOffset() {
        return students.size();
    }


    /**
     * Вставляє дані в базу даних
     */
    private  void putDataIntoDB(List<StudentItem> tmp) {
        StudentTable studentTable = new StudentTable();
        CourseTable courseTable = new CourseTable();
        StudentCourseTable studentCourseTable = new StudentCourseTable();

        studentCourseTable.delete();
        courseTable.delete();
        studentTable.delete();

        for (int i = 0; i < COURSES.length; i++) {
            courseTable.insert(new Course(COURSES[i], i));
        }

        for (StudentItem student : tmp) {
            Log.d(TAG, student.toString());
            Student stud = new Student(student.getId(), student.getFirstName(), student.getLastName(), student.getBirthday());
            studentTable.insert(stud);

            for (CourseItem course : student.getCourses()) {
                StudentCourse studCourse = new StudentCourse(student.getId(), getIdCourse(course.getName()), course.getMark());
                studentCourseTable.insert(studCourse);
            }
        }

    }

    /**
     * Зчитує дані з бази даних
     */
    private void readDataFromDB() {
        if (filter.isUse()) {
            Log.d(TAG, filter.toString());
            addStudents(DatabaseManager.getInstance().getStudentCourse(calcOffset(), filter));
        } else {
            addStudents(DatabaseManager.getInstance().getStudentCourse(calcOffset()));
        }
    }

    private int getIdCourse(String name) {
        for (int i = 0; i < COURSES.length; i++) {
            if (COURSES[i].equals(name))
                return i;

        }
        return -1;
    }

    private void saveStatusLogin(boolean statusLogin) {
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(Constants.LOGIN_KEY, statusLogin);
        ed.commit();

    }


    public boolean checkStatusLogin() {
        return sPref.getBoolean(Constants.LOGIN_KEY, false);
    }

    /**
     * Потік, для завантаження даних з сервера
     */
    private class HTTPTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                InputStream is = connect(Constants.DATA_URL);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int byteRead = 0;
                byte[] buffer = new byte[1024];
                while ((byteRead = is.read(buffer)) > 0) {
                    out.write(buffer, 0, byteRead);
                }
                out.close();
                String jsonResponce = new String(out.toByteArray());

                List<StudentItem> tmp = new JsonParser(jsonResponce).parse();
                putDataIntoDB(tmp);
                saveStatusLogin(true);
                readDataFromDB();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(Constants.TAG, "error " + e);
            }

            return null;
        }

        private InputStream connect(String urlString) throws IOException {
            String uri = Uri.parse(urlString).buildUpon()
                    .appendQueryParameter("format", "json").build().toString();
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            return conn.getInputStream();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.update();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    /**
     * Потік, для завантаження даних з бази даних
     */
    private class ReadDatabaseTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                readDataFromDB();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(Constants.TAG, "error " + e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.update();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    /**
     * Інтерфейс для оновлення UI
     */
    public interface Update {
        void update();
        void resetViews();
    }
}
