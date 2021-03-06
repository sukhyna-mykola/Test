package com.ddapp.test.info;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ddapp.test.Constants;
import com.ddapp.test.CoursesAdapter;
import com.ddapp.test.R;
import com.ddapp.test.StudentItem;
import com.ddapp.test.StudentsManager;

/**
 * Created by mykola on 20.02.17.
 */

public class InfoFragment extends DialogFragment {
    private RecyclerView listOfCourses;
    private TextView averageMark;

    private CoursesAdapter adapter;

    private StudentItem student;

    public static InfoFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(Constants.ID_KEY, id);
        InfoFragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String id = getArguments().getString(Constants.ID_KEY);
        student = StudentsManager.getInstance(getContext()).getStudent(id);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_info, null);
        listOfCourses = (RecyclerView) v.findViewById(R.id.courses_list);
        averageMark = (TextView) v.findViewById(R.id.average_mark);


        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listOfCourses.setLayoutManager(llm);
        adapter = new CoursesAdapter(student.getCourses());
        listOfCourses.setAdapter(adapter);


        averageMark.setText("Average mark : " + student.getAveregeMark());

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.courses)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dismiss();
                    }
                })
                .create();
    }

}
