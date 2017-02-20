package com.ddapp.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_student_list);
        if (fragment == null) {
            fragment = new StudentListFragment();
            fm.beginTransaction().add(R.id.activity_student_list, fragment)
                    .commit();
        }
    }
}
