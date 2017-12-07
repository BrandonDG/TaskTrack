package com.brandon.tasktrack;

import android.app.Dialog;
import android.content.Intent;
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

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {

    /**
     * Declaring variables used in TaskActivity
     */
    private ListView             lv;
    private ArrayAdapter<String> listAdapter;
    private TaskTrackDB          ttdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        lv = (ListView)findViewById(R.id.tasklist);

        ttdb = new TaskTrackDB(getApplicationContext());

        //ArrayList<String> dummylist = new ArrayList<String>();
        //dummylist.add("BLAH1");
        //dummylist.add("BLAH2");
        //dummylist.add("BLAH3");
        //dummylist.add("BLAH4");

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ttdb.getTasks());

        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(listHandler);
    }

    @Override
    public void onResume() {
        super.onResume();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ttdb.getTasks());
        lv.setAdapter(listAdapter);
        lv.setOnItemClickListener(listHandler);
    }

    /**
     * OnItemClickListener for listAdapter
     */
    private AdapterView.OnItemClickListener listHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            final String taskname = lv.getItemAtPosition(position).toString();
            Log.d("taskValue", taskname);
            final Dialog dialog = new Dialog(view.getContext());
            dialog.setContentView(R.layout.dialog_managetask);

            Button completeButton = (Button) dialog.findViewById(R.id.manageTask_completebutton);
            Button removeButton = (Button) dialog.findViewById(R.id.manageTask_removebutton);
            Button cancelButton = (Button) dialog.findViewById(R.id.manageTask_cancelbutton);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;

            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Log.d("Marked as Complete: ", "" + ttdb.markAsComplete(taskname));
                    onResume();
                    dialog.dismiss();
                }
            });

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Log.d("Deleted: ", "" + ttdb.removeTask(taskname));
                    onResume();
                    dialog.dismiss();
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    dialog.dismiss();
                }
            });

            /*
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    dialog.dismiss();
                }
            });

            addTaskButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Task task = new Task(((EditText)dialog.findViewById(R.id.newTaskModal_nametextbox)).getText().toString(),
                            ((EditText)dialog.findViewById(R.id.newTaskModal_typetextbox)).getText().toString(),
                            ((EditText)dialog.findViewById(R.id.newTaskModal_fromtimetextbox)).getText().toString(),
                            ((EditText)dialog.findViewById(R.id.newTaskModal_totimetextbox)).getText().toString());
                    SQLiteDatabase db = ttdb.getWritableDatabase();
                    ttdb.insertTask(db, task);
                    onResume();
                    dialog.dismiss();
                }
            }); */

            dialog.show();
            dialog.getWindow().setAttributes(lp);
        }
    };

    public void toStatistics(final View v) {
        Intent i = new Intent(this, StatisticsActivity.class);
        startActivity(i);
    }

    public void toAddTaskModal(final View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_newtask);

        Button cancelButton = (Button) dialog.findViewById(R.id.newTaskModal_cancelbutton);
        Button addTaskButton = (Button) dialog.findViewById(R.id.newTaskModal_addbutton);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialog.dismiss();
            }
        });

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Task task = new Task(((EditText)dialog.findViewById(R.id.newTaskModal_nametextbox)).getText().toString(),
                        ((EditText)dialog.findViewById(R.id.newTaskModal_typetextbox)).getText().toString(),
                        ((EditText)dialog.findViewById(R.id.newTaskModal_fromtimetextbox)).getText().toString(),
                        ((EditText)dialog.findViewById(R.id.newTaskModal_totimetextbox)).getText().toString());
                SQLiteDatabase db = ttdb.getWritableDatabase();
                ttdb.insertTask(db, task);
                onResume();
                dialog.dismiss();
            }
        });

        /*
        toTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                toSettings();
            }
        }); */

        dialog.show();
        dialog.getWindow().setAttributes(lp);
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
