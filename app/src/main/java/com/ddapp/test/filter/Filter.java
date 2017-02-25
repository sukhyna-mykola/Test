package com.ddapp.test.filter;

import com.ddapp.test.Constants;

/**
 * Created by mykola on 21.02.17.
 */

public class Filter {

    private int mark;
    private String name;
    private boolean use;


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

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public void clear() {
        this.mark = -1;
        this.name = Constants.COURSES[0];
        this.use = false;
    }

    public Filter() {
        clear();
    }

    @Override
    public String toString() {
        return name+" "+mark+" "+ use;
    }
}
