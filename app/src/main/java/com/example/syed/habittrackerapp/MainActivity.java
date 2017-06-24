package com.example.syed.habittrackerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.syed.habittrackerapp.data.HabitContract.HabitEntry;
import com.example.syed.habittrackerapp.data.HabitDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RVAdapter mAdapter;
    RecyclerView reView;

    HabitDbHelper mDbHelp = new HabitDbHelper(this);

    ArrayList<Habit> habits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                int code = 0;
                startActivityForResult(intent, code);

                //Do nothing

            }
        });
        displayDatabaseInfo();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        habits.clear();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            habits.clear();
            View view = findViewById(R.id.toast);
            deleteHabit();
            displayDatabaseInfo();
            mAdapter.setDeleteHabitList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void insertData() {
        SQLiteDatabase db = mDbHelp.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(HabitEntry.COLUMN_TITLE, "Exercise");
        values.put(HabitEntry.COLUMN_DESC, "Go to gym today");
        values.put(HabitEntry.COLUMN_REPETITION, HabitEntry.REPETITION_DAILY);
        values.put(HabitEntry.COLUMN_STATUS, HabitEntry.STATUS_NOT_COMPLETED);

        long status = db.insert(HabitEntry.TABLE_NAME, null, values);

    }

    private void deleteHabit() {
        SQLiteDatabase db = mDbHelp.getWritableDatabase();
        String selection = HabitEntry._ID + " = ?";
        int[] deleteList = mAdapter.getDeleteHabitList();
        for (int i = 0; i < deleteList.length; i++) {

            displayDatabaseInfo();

            Habit habit = habits.get(i);
            if (deleteList[i] == 1) {
                String[] selectionArgs = {habit.getId() + ""};
                int success = db.delete(HabitEntry.TABLE_NAME, selection, selectionArgs);
            }
            habits.clear();
        }

        // Specify arguments in placeholder order.
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelp.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_TITLE,
                HabitEntry.COLUMN_DESC,
                HabitEntry.COLUMN_REPETITION,
                HabitEntry.COLUMN_STATUS};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        //TextView displayView = (TextView) findViewById(R.id.);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
           /*displayView.setText("The Habit table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_TITLE + " - " +
                    HabitEntry.COLUMN_DESC + " - " +
                    HabitEntry.COLUMN_REPETITION+ " - " +
                    HabitEntry.COLUMN_STATUS + "\n");
*/
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int titleColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_TITLE);
            int descColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DESC);
            int repColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_REPETITION);
            int statusColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_STATUS);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                String currentDesc = cursor.getString(descColumnIndex);
                int currentRep = cursor.getInt(repColumnIndex);
                int currentStatus = cursor.getInt(statusColumnIndex);

                habits.add(new Habit(currentID, currentTitle, currentDesc, getStringRepe(currentRep), getStringStatus(currentStatus)));
                // Display the values from each column of the current row in the cursor in the TextView
                /*displayView.append(("\n" + currentID + " - " +
                        currentTitle + " - " +
                        currentDesc + " - " +
                        currentRep + " - " +
                        currentStatus));*/
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

        mAdapter = new RVAdapter(habits, this);
        LinearLayoutManager LM = new LinearLayoutManager(this);
        reView = (RecyclerView) findViewById(R.id.rview);
        reView.setLayoutManager(LM);
        reView.setHasFixedSize(true);
        reView.setAdapter(mAdapter);
    }

    public String getStringRepe(int i) {
        String RepeString = "";
        switch (i) {
            case 0:
                RepeString = "Daily";
                break;
            case 1:
                RepeString = "Weekly";
                break;
            case 2:
                RepeString = "Monthly";
                break;
            case 3:
                RepeString = "Yearly";
                break;
        }
        return RepeString;
    }

    public String getStringStatus(int i) {
        String statusString = "";
        switch (i) {
            case 0:
                statusString = "Not yet";
                break;
            case 1:
                statusString = "Completed";
                break;
        }
        return statusString;
    }
}
