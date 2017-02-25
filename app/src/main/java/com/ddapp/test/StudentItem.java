package com.ddapp.test;

import com.ddapp.test.database.Course;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by mykola on 20.02.17.
 */

public class StudentItem {
    private String id;
    private String firstName;
    private String lastName;
    private long birthday;
    private List<CourseItem> courses;

    public StudentItem(String id, String firstName, String lastName, long birthday, List<CourseItem> courses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.courses = courses;
    }

    public List<CourseItem> getCourses() {

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

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setCourses(List<CourseItem> courses) {
        this.courses = courses;
    }

    public StudentItem() {

    }

    public float getAveregeMark() {
        float result = 0;
        float count = courses.size();
        for (CourseItem course : courses) {
            result += course.getMark();
        }
        return result / count;
    }

    @Override
    public String toString() {
        return firstName + " "+ lastName;
    }
}
