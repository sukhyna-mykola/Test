package com.ddapp.test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.ddapp.test.Constants.BIRTHDAY_TAG;
import static com.ddapp.test.Constants.COURSES_TAG;
import static com.ddapp.test.Constants.FIRST_NAME_TAG;
import static com.ddapp.test.Constants.ID_TAG;
import static com.ddapp.test.Constants.LAST_NAME_TAG;
import static com.ddapp.test.Constants.MARK_TAG;
import static com.ddapp.test.Constants.NAME_TAG;

/**
 * Created by mykola on 24.02.17.
 */

public class JsonParser {
    private String json;

    public JsonParser(String json) {
        this.json = json;
    }


    /**
     * Розбирає JSON відповідь від сервера
     * @return список студентів
     * @throws JSONException
     */
    public List<Student> parse() throws JSONException {

        List<Student> students = new ArrayList<>();
        List<Course> courses;

        JSONArray studentsArray = new JSONArray(json);
        JSONArray coursesArray = null;

        JSONObject student = null;
        JSONObject course = null;

        String firstName = null;
        String lastName = null;
        String id = null;
        long birthday;

        String name = null;
        int mark;

        for (int i = 0; i < studentsArray.length(); i++) {
            student = studentsArray.getJSONObject(i);
            firstName = student.getString(FIRST_NAME_TAG);
            lastName = student.getString(LAST_NAME_TAG);
            id = student.getString(ID_TAG);
            birthday = student.getLong(BIRTHDAY_TAG);
            courses = new ArrayList<>();
            coursesArray = student.getJSONArray(COURSES_TAG);
            for (int j = 0; j < coursesArray.length(); j++) {
                course = coursesArray.getJSONObject(j);
                name = course.getString(NAME_TAG);
                mark = course.getInt(MARK_TAG);
                courses.add(new Course(name, mark));
            }
            students.add(new Student(id, firstName, lastName, birthday, courses));

        }
        return students;
    }
}