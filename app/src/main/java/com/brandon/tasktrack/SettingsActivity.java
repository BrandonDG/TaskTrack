package com.brandon.tasktrack;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity {

    /**
     * Declaring variables used in TaskActivity
     */
    private ListView             lv;
    private ArrayAdapter<String> listAdapter;
    private TaskTrackDB          ttdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        lv = (ListView) findViewById(R.id.settings_tasktypelist);
        ttdb = new TaskTrackDB(getApplicationContext());

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ttdb.getTaskTypes());

        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(listHandler);
    }

    @Override
    public void onResume() {
        super.onResume();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ttdb.getTaskTypes());
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(listHandler);
    }

    public void settingsBackToTask(final View v) {
        finish();
    }

    public void toCreateTaskTypeModal(final View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_tasktypeedit);

        Button cancelButton = (Button) dialog.findViewById(R.id.newTaskTypeModal_cancelbutton);
        Button addButton = (Button) dialog.findViewById(R.id.newTaskTypeModal_addbutton);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialog.dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TaskType tasktype = new TaskType(((EditText)dialog.findViewById(R.id.newTaskTypeModal_typetextbox)).getText().toString(),
                        ((EditText)dialog.findViewById(R.id.newTaskTypeModal_colourtextbox)).getText().toString());
                SQLiteDatabase db = ttdb.getWritableDatabase();
                ttdb.insertTaskType(db, tasktype);
                onResume();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * OnItemClickListener for listAdapter
     */
    private AdapterView.OnItemClickListener listHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        }
    };
}
