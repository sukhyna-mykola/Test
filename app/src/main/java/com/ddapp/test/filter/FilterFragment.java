package com.ddapp.test.filter;

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

import com.ddapp.test.Constants;
import com.ddapp.test.R;
import com.ddapp.test.StudentsManager;

/**
 * Created by mykola on 20.02.17.
 */

public class FilterFragment extends DialogFragment implements View.OnClickListener {
    private AppCompatSpinner courseSelectField;
    private EditText markInputField;
    private Button okButton;
    private Button clearButton;
    
    private Filter filter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_filter, null);
        courseSelectField = (AppCompatSpinner) v.findViewById(R.id.filter_courses_list);
        markInputField = (EditText) v.findViewById(R.id.filter_mark);
        okButton = (Button) v.findViewById(R.id.ok);
        clearButton = (Button) v.findViewById(R.id.clear);

        okButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Constants.COURSES);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSelectField.setAdapter(adapter);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok: {
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
                break;
            }
            case R.id.clear: {
                filter.clear();
                StudentsManager.getInstance(getContext()).removeAll();
                StudentsManager.getInstance(getContext()).loadAsyncDataFromDB();
                dismiss();
                break;
            }
        }
    }
}
