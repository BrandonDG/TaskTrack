package com.brandon.tasktrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }

    public void backToTasks(final View v) {
        finish();
    }
}
