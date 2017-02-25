package com.ddapp.test.database;

/**
 * Created by mykola on 24.02.17.
 */

public class StudentCourse {

    public static final String TAG = StudentCourse.class.getSimpleName();
    public static final String TABLE = "StudentCourse";

    // Labels Table Columns names
    public static final String KEY_StudCourseId = "StudCourseId";
    public static final String KEY_StudID = "StudentId";
    public static final String KEY_CourseId = "CourseId";
    public static final String KEY_Mark = "Mark";

    private int studCourseId;
    private String studentID;
    private int courseID;
    private int mark;

    public int getStudCourseId() {
        return studCourseId;
    }

    public String getStudentID() {
        return studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getMark() {
        return mark;
    }

    public StudentCourse(String studentID, int courseID, int mark) {

        this.studentID = studentID;
        this.courseID = courseID;
        this.mark = mark;
    }

    public StudentCourse(int StudCourseId, String studentID, int courseID, int mark) {

        this.studCourseId = studCourseId;
        this.studentID = studentID;
        this.courseID = courseID;
        this.mark = mark;
    }
}
