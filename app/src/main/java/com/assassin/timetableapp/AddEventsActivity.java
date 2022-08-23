package com.assassin.timetableapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEventsActivity extends AppCompatActivity {
    EditText courseName;
    EditText venue;
    EditText time;
    Spinner day;
    Button addButton;
    String toastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        courseName = (EditText) findViewById(R.id.editTextEventName);

        venue = (EditText) findViewById(R.id.editTexVenue);
        time = (EditText) findViewById(R.id.editTextTime);
        day = (Spinner) findViewById(R.id.daysSpinner);
        day.setSelection(day.getFirstVisiblePosition());

        String courseNameText = courseName.getText().toString();
        String venueText = venue.getText().toString();
        String timeText = time.getText().toString();
        String dayText = day.getSelectedItem().toString();


        addButton = (Button) findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseNameText = courseName.getText().toString();
                String venueText = venue.getText().toString();
                String timeText = time.getText().toString();

                String dayText = day.getSelectedItem().toString();

                System.out.println(dayText);



                if(courseNameText.isEmpty() || venueText.isEmpty() || timeText.isEmpty() || dayText.isEmpty()){
                    Toast.makeText(AddEventsActivity.this, "please fill all input fields", Toast.LENGTH_LONG).show();
                }
                else{



                    String log = "course Name: " + courseNameText + "day: " + dayText + "venue: " + venueText + "time: " + timeText;
                    Log.d("Values", log);

                    submitData(courseNameText, venueText, timeText, dayText);
                    resetFields();

                }

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void submitData(String courseNameText, String venueText, String timeText, String dayText) {

        Today today = new Today();
        today.setDay(dayText);

        DBHelper dbHelper = new DBHelper(AddEventsActivity.this);
            if(dbHelper.insertData(courseNameText, dayText, timeText, venueText)){
                Toast.makeText(AddEventsActivity.this, "added successfully", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(AddEventsActivity.this, "something went wrong", Toast.LENGTH_LONG).show();
            }





    }

    public void resetFields(){
        courseName.setText("");
        venue.setText("");
        time.setText("");

    }
}