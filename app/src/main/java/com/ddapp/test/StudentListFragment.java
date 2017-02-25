package com.ddapp.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ddapp.test.filter.FilterFragment;


public class StudentListFragment extends Fragment implements StudentsManager.Update {

    private RecyclerView listOfStudents;
    private StudentsAdapter adapter;
    private LinearLayoutManager llm;
    private RelativeLayout bottomLayout;

    private boolean loaded;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student_list, container, false);


        listOfStudents = (RecyclerView) view.findViewById(R.id.students_list);
        bottomLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);

        llm = new LinearLayoutManager(getContext());
        adapter = new StudentsAdapter(StudentsManager.getInstance(getContext()).getStudents(), getContext());

        listOfStudents.setLayoutManager(llm);
        listOfStudents.setAdapter(adapter);

        listOfStudents.addOnScrollListener(new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore() {
                bottomLayout.setVisibility(View.VISIBLE);
                StudentsManager.getInstance(getContext()).loadDataFromDB();
            }
        });


        StudentsManager manager = StudentsManager.getInstance(getContext());
        manager.setCallback(this);

        if (manager.checkStatusLogin()) {
            manager.loadDataFromDB();
        } else {
            manager.loadDataFromURL();
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_filter:
                if (loaded) {
                    FilterFragment fragment = new FilterFragment();
                    fragment.show(getActivity().getSupportFragmentManager(), Constants.FILTER_DIALOG_TAG);
                } else Toast.makeText(getContext(), R.string.loading, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void update() {
        if (!loaded)
            loaded = !loaded;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(Constants.TAG, "Update");
                adapter.notifyDataSetChanged();
                bottomLayout.setVisibility(View.GONE);
            }
        });

    }


}
