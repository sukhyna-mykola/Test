package com.ddapp.test;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StudentListFragment extends Fragment {
    private RecyclerView listOfStudents;
    private StudentsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        updateUI();
        new RequestTask().execute();
        listOfStudents = (RecyclerView) view.findViewById(R.id.students_list);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        listOfStudents.setLayoutManager(llm);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
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
                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class RequestTask extends AsyncTask<String, String, Integer> {

        @Override
        protected Integer doInBackground(String... uri) {

            try {
                InputStream is = connect(Constants.DATA_URL);
                XmlParser parser = new XmlParser();
                StudentsManager.getInstance(getContext()).setStudents(parser.parse(is));
                return HttpURLConnection.HTTP_OK;

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        private InputStream connect(String urlString) throws IOException {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
            conn.connect();
            return conn.getInputStream();
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            adapter = new StudentsAdapter(StudentsManager.getInstance(getContext()).getStudents(),
                    getContext());
            listOfStudents.setAdapter(adapter);

        }

    }


    private void updateUI() {

    }


}
