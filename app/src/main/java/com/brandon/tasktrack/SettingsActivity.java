package com.brandon.tasktrack;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void settingsBackToTask(final View v) {
        finish();
    }

    public void toCreateTaskTypeModal(final View v) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_tasktypeedit);

        Button cancelButton = (Button) dialog.findViewById(R.id.newTaskTypeModal_cancelbutton);
        //Button toTypesButton = (Button) dialog.findViewById(R.id.settingsModal_totypesbutton);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
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
}
