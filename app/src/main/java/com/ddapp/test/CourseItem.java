package com.ddapp.test;

/**
 * Created by mykola on 24.02.17.
 */

public class CourseItem {
    private int mark;
    private String name;


    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseItem() {
    }

    public CourseItem(int mark, String name) {

        this.mark = mark;
        this.name = name;
    }

    @Override
    public String toString() {
        return name+" mark = "+mark;
    }
}
