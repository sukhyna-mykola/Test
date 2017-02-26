package com.ddapp.test.filter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.ddapp.test.Constants;
import com.ddapp.test.R;
import com.ddapp.test.StudentsManager;

/**
 * Created by mykola on 20.02.17.
 */

public class FilterFragment extends DialogFragment {
    private AppCompatSpinner courseSelectField;
    private EditText markInputField;


    private Filter filter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_filter, null);
        courseSelectField = (AppCompatSpinner) v.findViewById(R.id.filter_courses_list);
        markInputField = (EditText) v.findViewById(R.id.filter_mark);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Constants.COURSES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSelectField.setAdapter(adapter);

        filter = StudentsManager.getInstance(getContext()).getFilter();
        updateUI(filter);

        Dialog dialog = new AlertDialog.Builder(getContext())
                .setView(v)
                .setTitle(R.string.filters)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {

                            int mark = Integer.parseInt(markInputField.getText().toString());

                            filter.setUse(true);
                            filter.setMark(mark);
                            filter.setName(Constants.COURSES[courseSelectField.getSelectedItemPosition()]);

                            StudentsManager.getInstance(getContext()).removeAll();
                            StudentsManager.getInstance(getContext()).loadAsyncDataFromDB();
                            dismiss();
                        } catch (Exception e) {
                            filter.setUse(false);
                            filter.setMark(-1);
                            updateUI(filter);
                        }
                    }
                })
                .setNegativeButton(R.string.clear, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filter.clear();
                        StudentsManager.getInstance(getContext()).removeAll();
                        StudentsManager.getInstance(getContext()).loadAsyncDataFromDB();
                        dismiss();
                    }
                })
                .create();

        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        return dialog;

    }

    private void updateUI(Filter filter) {
        if (filter.getMark() != -1)
            markInputField.setText(String.valueOf(filter.getMark()));
        else markInputField.setText("");

        if (filter.getName() != null) {
            courseSelectField.setSelection(getPositionInList(filter.getName()));
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
