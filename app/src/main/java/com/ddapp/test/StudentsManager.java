package com.ddapp.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mykola on 20.02.17.
 */
public class StudentsManager {
    private static StudentsManager manager;
    private List<Student> students;

    private DBHelper helper;
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
        helper = new DBHelper(context);
        filter = new Filter();
        sPref = context.getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
    }

    public List<Student> getStudents() {
        return students;
    }

    public Filter getFilter() {
        return filter;
    }


    public Student getStudent(String id) {
        for (Student student : students) {

            if (student.getId().equals(id))
                return student;
        }
        return null;
    }


    public void loadDataFromDB() {
        Log.d(Constants.TAG, "filter.status = " + filter.isState());
        if (filter.isState()) {
            addStudents(helper.readDataFromDB(filter, students.size()));
        } else {
            addStudents(helper.readDataFromDB(students.size()));
        }

        callback.update();
    }


    public void removeAll() {
        students.clear();
    }


    public void loadDataFromURL() {
        new RequestTask().execute();
    }

    private void addStudents(List<Student> newStudents) {
        for (Student student : newStudents) {
            students.add(student);
        }
    }

    private void putDataIntoDB() {
        helper.putDataToDB(students);
    }


    private class RequestTask extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... uri) {

            try {
                InputStream is = connect(Constants.DATA_URL);
                ByteArrayOutputStream out  = new ByteArrayOutputStream();
                int byteRead = 0;
                byte[] buffer = new byte[1024];
                while ((byteRead=is.read(buffer))>0){
                    out.write(buffer,0,byteRead);
                }
                out.close();
                String jsonResponce = new String(out.toByteArray());

                //XmlParser parser = new XmlParser();
                //addStudents(parser.parse(is));

                addStudents(new JsonParser(jsonResponce).parse());
                putDataIntoDB();
                saveStatusLogin(true);
                removeAll();
                loadDataFromDB();
                return HttpURLConnection.HTTP_OK;

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

    }

    private void saveStatusLogin(boolean statusLogin) {

        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean(Constants.LOGIN_KEY, statusLogin);
        ed.commit();

    }


    public boolean checkStatusLogin() {
        return sPref.getBoolean(Constants.LOGIN_KEY, false);
    }

    public interface Update {
        void update();
    }
}
