package com.ddapp.test;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by mykola on 20.02.17.
 */

public class FilterFragment extends DialogFragment {
    private AppCompatSpinner courseSelectField;
    private EditText markInputField;
    private Button buttonOk;
    private Button buttonClear;

    private Filter filter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_filter, null);
        courseSelectField = (AppCompatSpinner) v.findViewById(R.id.filter_courses_list);
        markInputField = (EditText) v.findViewById(R.id.filter_mark);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Constants.COURSES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSelectField.setAdapter(adapter);


        buttonOk = (Button) v.findViewById(R.id.ok);
        buttonClear = (Button) v.findViewById(R.id.clear);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int mark = Integer.parseInt(markInputField.getText().toString());

                    filter.setState(true);
                    filter.setMarkCourse(mark);
                    filter.setNameCourse(Constants.COURSES[courseSelectField.getSelectedItemPosition()]);

                    StudentsManager.getInstance(getContext()).removeAll();
                    StudentsManager.getInstance(getContext()).loadDataFromDB();
                    dismiss();

                } catch (Exception e) {
                    updateUI(filter);
                }

            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter.clear();
                StudentsManager.getInstance(getContext()).removeAll();
                StudentsManager.getInstance(getContext()).loadDataFromDB();
                dismiss();

            }
        });

        filter = StudentsManager.getInstance(getContext()).getFilter();
        updateUI(filter);

        Dialog dialog = new Dialog(getActivity(), R.style.MyCustomTheme);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(v);
        dialog.getWindow().setGravity(Gravity.RIGHT);

        dialog.getWindow().setLayout(getContext().getResources().getDisplayMetrics().widthPixels / 2, FrameLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        return dialog;

    }

    private void updateUI(Filter filter) {
        if (filter.getMarkCourse() != -1)
            markInputField.setText(String.valueOf(filter.getMarkCourse()));
        else markInputField.setText("");

        if (filter.getNameCourse() != null) {
            courseSelectField.setSelection(getPositionInList(filter.getNameCourse()));
        } else {
            courseSelectField.setSelection(0);
        }
    }


    private int getPositionInList(String name) {
        for (int i = 0; i < Constants.COURSES.length; i++) {
            if (name.equals(Constants.COURSES[i]))
                return i;
        }
        return 0;
    }
}
