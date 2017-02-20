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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Student> students;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView studentName;
        public TextView studentBirthday;

        public ViewHolder(View v) {
            super(v);
            studentName = (TextView) v.findViewById(R.id.student_name);
            studentBirthday = (TextView) v.findViewById(R.id.student_birthday);
        }
    }


    public MyAdapter(List<Student> students) {
        this.students = students;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.studentName.setText(student.getFirstName() + " " + student.getLastName());
        holder.studentBirthday.setText(student.getBirthday() + "");

    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}

