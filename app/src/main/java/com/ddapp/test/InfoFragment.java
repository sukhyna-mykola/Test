package com.ddapp.test;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by mykola on 20.02.17.
 */

public class InfoFragment extends DialogFragment {
    private RecyclerView listOfCourses;
    private TextView averageMark;
    private CoursesAdapter adapter;
    private Button okButton;

    private Student student;

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
        okButton = (Button) v.findViewById(R.id.ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listOfCourses.setLayoutManager(llm);
        adapter = new CoursesAdapter(student.getCourses());
        listOfCourses.setAdapter(adapter);


        averageMark.setText("Average mark : " + student.getAveregeMark());

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
    }

}
