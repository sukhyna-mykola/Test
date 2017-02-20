package com.ddapp.test;

/**
 * Created by mykola on 20.02.17.
 */
public class Course {
    private String name;
    private byte mark;

    public Course(String name, byte mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {

        return name;
    }

    public byte getMark() {
        return mark;
    }
}
