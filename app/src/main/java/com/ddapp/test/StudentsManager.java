package com.ddapp.test;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mykola on 20.02.17.
 */
public class StudentsManager {
    private static StudentsManager manager;
    private List<Student> students;

    public static StudentsManager getInstance(Context context) {
        if (manager == null)
            manager = new StudentsManager();
        return manager;
    }

    private StudentsManager() {
        students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {

        this.students = students;
    }

    public Student getStudent(String id) {
        for (Student student : students) {

            if (student.getId().equals(id))
                return student;
        }
        return null;
    }
}
