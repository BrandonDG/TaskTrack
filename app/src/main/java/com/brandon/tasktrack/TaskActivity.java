package com.brandon.tasktrack;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    /**
     * Declaring variables used in TaskActivity
     */
    private ListView             lv;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        lv = (ListView)findViewById(R.id.tasklist);

        ArrayList<String> dummylist = new ArrayList<String>();
        dummylist.add("BLAH1");
        dummylist.add("BLAH2");
        dummylist.add("BLAH3");
        dummylist.add("BLAH4");

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dummylist);

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

    public void toStatistics(final View v) {
        Intent i = new Intent(this, StatisticsActivity.class);
        startActivity(i);
    }

    public void addTask(final View v) {

    }

    public void toSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void toSettingsModal(final View v) {
        //Intent i = new Intent(this, SettingsActivity.class);
        //startActivity(i);
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_settings);

        Button doneButton = (Button) dialog.findViewById(R.id.settingsModal_donebutton);
        Button toTypesButton = (Button) dialog.findViewById(R.id.settingsModal_totypesbutton);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialog.dismiss();
            }
        });

        toTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                toSettings();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    /*

    final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_view);

        Button cancelButton = (Button) dialog.findViewById(R.id.cancel_delete);
        Button deleteButton = (Button) dialog.findViewById(R.id.confirm_delete);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                removePhotoFromStorage();
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
     */
}
