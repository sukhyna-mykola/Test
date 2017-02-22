package com.ddapp.test;

/**
 * Created by mykola on 21.02.17.
 */

public class Filter {

    private int markCourse;
    private String nameCourse;
    private boolean state;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }


    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public int getMarkCourse() {
        return markCourse;
    }

    public void setMarkCourse(int markCourse) {
        this.markCourse = markCourse;
    }

    public void clear() {
        this.markCourse = -1;
        this.nameCourse =Constants.COURSES[0];
        this.state = false;
    }

    public Filter() {
        clear();
    }
}
