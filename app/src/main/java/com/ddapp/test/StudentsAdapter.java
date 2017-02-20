package com.ddapp.test;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mykola on 20.02.17.
 */

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder>  {
    private List<Student> students;
    private Context context;

    public StudentsAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @Override
    public StudentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Student student = students.get(position);
        holder.studentName.setText(student.getFirstName() + " " + student.getLastName());
        holder.studentBirthday.setText(student.getStringBirthday());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = (FragmentActivity)(context);
                FragmentManager fm = activity.getSupportFragmentManager();
                InfoFragment fragment = InfoFragment.newInstance(student.getId());
                fragment.show(fm,"INFO");

            }
        });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView studentName;
        public TextView studentBirthday;
        public RelativeLayout item;

        public ViewHolder(View v) {
            super(v);
            item = (RelativeLayout) v;
            studentName = (TextView) v.findViewById(R.id.student_name);
            studentBirthday = (TextView) v.findViewById(R.id.student_birthday);

        }
    }

}

