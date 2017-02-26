package com.ddapp.test;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.ddapp.test.services.HTTPIntentService;


public class StudentListFragment extends Fragment implements StudentsManager.Update {

    private RecyclerView listOfStudents;
    private StudentsAdapter adapter;
    private LinearLayoutManager llm;
    private RelativeLayout loadingLayout;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
    private StudentsManager manager;
    private BroadcastReceiver br;

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
        loadingLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout_recyclerView);

        llm = new LinearLayoutManager(getContext());
        adapter = new StudentsAdapter(StudentsManager.getInstance(getContext()).getStudents(), getContext());

        listOfStudents.setLayoutManager(llm);
        listOfStudents.setAdapter(adapter);
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra(Constants.STATUS, 0);
                Log.d(Constants.TAG, "status = " + status);
                switch (status) {
                    case Constants.STATUS_COMPLETE:
                        manager.loadAsyncDataFromDB();
                        loaded = true;
                        break;
                }

            }
        };

        endlessRecyclerOnScrollListener = new
                EndlessRecyclerOnScrollListener(llm) {
                    @Override
                    public void onLoadMore() {
                        loadingLayout.setVisibility(View.VISIBLE);
                        StudentsManager.getInstance(getContext()).loadAsyncDataFromDB();
                    }
                };

        listOfStudents.addOnScrollListener(endlessRecyclerOnScrollListener);


        manager = StudentsManager.getInstance(getContext());
        manager.setCallback(this);

        if (manager.checkStatusLogin()) {
            loaded = true;
            manager.loadAsyncDataFromDB();
        } else {
            loaded = false;

            if (!isMyServiceRunning(HTTPIntentService.class)) {
                Log.d(Constants.TAG, "run service");
                Intent intent = new Intent(getContext(), HTTPIntentService.class);
                getContext().startService(intent);
            }
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(Constants.TAG, "Update");
                adapter.notifyDataSetChanged();
                loadingLayout.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void resetViews() {
        endlessRecyclerOnScrollListener.reset();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(Constants.TAG, "onStart . loaded = " + loaded);
        if (!loaded) {
            IntentFilter intFilt = new IntentFilter(Constants.ACTION);
            getActivity().registerReceiver(br, intFilt);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "onStop . loaded = " + loaded);
        if (!loaded) {
            getActivity().unregisterReceiver(br);
        }
    }

    /**
     * Перевіряє, чи запущений сервіс на даний момент
     * @param serviceClass
     * @return
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
