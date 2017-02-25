package com.ddapp.test.database;

/**
 * Created by mykola on 20.02.17.
 */
public class Course {

    public static final String TAG = Course.class.getSimpleName();
    public static final String TABLE = "Course";
    // Labels Table Columns names
    public static final String KEY_CourseId = "CourseId";
    public static final String KEY_Name = "Name";

    private String name;
    private int courseId;

    public void setName(String name) {
        this.name = name;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Course() {

    }


    public int getCourseId() {
        return courseId;
    }

    public void setCourseID(int courseId) {
        this.courseId = courseId;
    }


    public String getName() {

        return name;
    }

    public Course(String name, int courseId) {
        this.name = name;
        this.courseId = courseId;
    }

    public Course(int courseId) {

        this.courseId = courseId;
    }

    public Course(String name) {

        this.name = name;
    }
}
