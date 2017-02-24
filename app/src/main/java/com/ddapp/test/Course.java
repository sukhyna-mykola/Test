package com.ddapp.test;

/**
 * Created by mykola on 20.02.17.
 */
public class Course {
    private String name;
    private int mark;

    public Course(String name, int mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {

        return name;
    }

    public int getMark() {
        return mark;
    }
}
