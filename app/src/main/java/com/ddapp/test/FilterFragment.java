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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by mykola on 20.02.17.
 */

public class FilterFragment extends DialogFragment {
    private AppCompatSpinner courseSelectField;
    private EditText markInputField;
    private Button buttonOk;
    private Button buttonClear;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_filter, null);
        courseSelectField = (AppCompatSpinner) v.findViewById(R.id.filter_courses_list);
        markInputField = (EditText) v.findViewById(R.id.filter_mark);
        buttonOk = (Button) v.findViewById(R.id.ok);
        buttonClear = (Button) v.findViewById(R.id.clear);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Dialog dialog = new Dialog(getActivity(), R.style.MyCustomTheme);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setContentView(v);
        dialog.getWindow().setGravity(Gravity.RIGHT);

        dialog.getWindow().setLayout(getContext().getResources().getDisplayMetrics().widthPixels/2, FrameLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        return dialog;

    }
}
