package com.ddapp.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mykola on 20.02.17.
 */

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private long birthday;
    private List<Course> courses;

    public Student(String id, String firstName, String lastName, long birthday, List<Course> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.courses = courses;
    }

    public List<Course> getCourses() {

        return courses;
    }

    public long getBirthday() {
        return birthday;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getStringBirthday() {
        Date date = new Date(birthday * 1000);
        SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yy");
        String dateText = df2.format(date);
        return dateText;
    }

    public float getAveregeMark() {
        float result = 0;
        float count = courses.size();
        for (Course course : courses) {
            result += course.getMark();
        }
        return result / count;
    }

}
