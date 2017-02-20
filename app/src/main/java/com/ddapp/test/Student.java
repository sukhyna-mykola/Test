package com.ddapp.test;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by mykola on 20.02.17.
 */

public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private long birthday;

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

    private List<Course> courses;



}
