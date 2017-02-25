package com.ddapp.test.database;

/**
 * Created by mykola on 24.02.17.
 */

public class Student {
    public static final String TAG = Student.class.getSimpleName();
    public static final String TABLE = "StudentItem";

    // Labels Table Columns names
    public static final String KEY_StudID = "StudentId";
    public static final String KEY_Birthday = "Birthday";
    public static final String KEY_FirstName = "FirstName";
    public static final String KEY_LastName = "LastName";


    private String studentId;
    private String firstName;
    private String lastName;
    private long birthday;


    public Student() {
    }

    public String getStudentId() {

        return studentId;
    }

    public void setId(String StudentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public Student(String studentId, String firstName, String lastName, long birthday) {

        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }
}
