package com.ddapp.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mykola on 20.02.17.
 */

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {
    private List<Course> courses;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView courseName;
        public TextView courseMark;

        public ViewHolder(View v) {
            super(v);
            courseName = (TextView) v.findViewById(R.id.course_name);
            courseMark = (TextView) v.findViewById(R.id.course_mark);
        }
    }


    public CoursesAdapter(List<Course> courses) {
        this.courses = courses;
    }


    @Override
    public CoursesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.courseName.setText(course.getName());
        holder.courseMark.setText(course.getMark()+" ");

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

