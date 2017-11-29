package com.brandon.tasktrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    /**
     * Declaring variables used in CourseSelectActivity
     */
    private ListView             lv;
    private Intent               intent;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        lv = (ListView)findViewById(R.id.tasklist);

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());

        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(listHandler);
    }

    /**
     * OnItemClickListener for listAdapter
     */
    private AdapterView.OnItemClickListener listHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            //String term = lv.getItemAtPosition(position).toString();
        }
    };
}
