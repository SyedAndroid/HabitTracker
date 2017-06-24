package com.example.syed.habittrackerapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.syed.habittrackerapp.data.HabitContract.HabitEntry;
import com.example.syed.habittrackerapp.data.HabitDbHelper;

/**
 * Created by syed on 2017-06-12.
 */

public class EditorActivity extends AppCompatActivity {

    private EditText mTitleEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mDescEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private Spinner mRepetitionSpinner;

    /**
     * EditText field to enter the pet's gender
     */
    private Spinner mStatusSpinner;
    private int mRepetition = HabitEntry.REPETITION_DAILY;
    private int mStatus = HabitEntry.STATUS_NOT_COMPLETED;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mTitleEditText = (EditText) findViewById(R.id.edit_title);
        mDescEditText = (EditText) findViewById(R.id.edit_desc);
        mRepetitionSpinner = (Spinner) findViewById(R.id.spinner_repete);
        mStatusSpinner = (Spinner) findViewById(R.id.spinner_status);
        setupSpinner();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter repetitionSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_repetition_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        repetitionSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mRepetitionSpinner.setAdapter(repetitionSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mRepetitionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.repe_monthly))) {
                        mRepetition = HabitEntry.REPETITION_MONTLY;
                    } else if (selection.equals(getString(R.string.repe_weekly))) {
                        mRepetition = HabitEntry.REPETITION_WEEKLY;
                    } else {
                        mRepetition = HabitEntry.REPETITION_YEARLY;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mRepetition = HabitEntry.REPETITION_DAILY;
            }
        });
        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_status_options, android.R.layout.simple_spinner_item);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mStatusSpinner.setAdapter(statusSpinnerAdapter);
        mStatusSpinner.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(select)) {
                    if (select.equals(getString(R.string.status_completed))) {
                        mStatus = HabitEntry.STATUS_COMPLETED;
                    } else if (select.equals(getString(R.string.status_later))) {
                        mStatus = HabitEntry.STATUS_NOT_COMPLETED;
                    } else {
                        mStatus = HabitEntry.STATUS_NOT_COMPLETED;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mStatus = HabitEntry.STATUS_NOT_COMPLETED;
            }
        }));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertHabit();

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }


    private void insertHabit() {
        String title = mTitleEditText.getText().toString().trim();
        String desc = mDescEditText.getText().toString().trim();

        HabitDbHelper mHelper = new HabitDbHelper(this);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(HabitEntry.COLUMN_TITLE, title);
        value.put(HabitEntry.COLUMN_DESC, desc);
        value.put(HabitEntry.COLUMN_STATUS, mRepetition);
        value.put(HabitEntry.COLUMN_STATUS, mStatus);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, value);
        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving Habit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }


}
