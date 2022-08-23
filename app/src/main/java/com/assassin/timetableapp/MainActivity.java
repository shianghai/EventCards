package com.assassin.timetableapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ListView mainListView;

    public static final String DATABASE_NAME = "TDB.db";
    public static final String TABLE_NAME = "time_table";
    public static final String COLUMN_NAME_EVENT_NAME = "event_name";
    public static final String COLUMN_NAME_VENUE = "venue";
    public static final String COLUMN_NAME_DAY = "day";
    private static final String COLUMN_ID="id";
    public static final String COLUMN_NAME_TIME = "time";
    private Object o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ListView listView = (ListView) findViewById(R.id.mainListView);
        LayoutInflater inflater = getLayoutInflater();
        View header =  inflater.inflate(R.layout.list_header, null);
        listView.addHeaderView(header);



        Spinner filterSpinner = (Spinner) findViewById(R.id.filterSpinner);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(20);
                ((TextView) adapterView.getChildAt(0)).setPadding(5,5,5,5);
                String listItem = adapterView.getItemAtPosition(i).toString();
                createEventsCard(listItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Calendar cal  = Calendar.getInstance();
        SimpleDateFormat sdf  = new SimpleDateFormat("dd-MM-yyyy");
        Format f  = new SimpleDateFormat("EEEE");
        String dayToday  = f.format(new Date());

        //getTodayTimeTable(dayToday);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEventsActivity.class);
                startActivity(intent);


            }
        });
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
        if (id == R.id.view_all) {

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Format f  = new SimpleDateFormat("EEEE");
        String dayToday  = f.format(new Date());

        createEventsCard("all");
    }







    public void createEventsCard(String day) {
        LinearLayout noCardsLl = (LinearLayout) findViewById(R.id.noTtLl);
        mainListView = (ListView) findViewById(R.id.mainListView);
        ViewGroup mainListViewParent = (ViewGroup) mainListView.getParent();
        ViewGroup noCardsViewParent = (ViewGroup) noCardsLl.getParent();
        View noCardView = getLayoutInflater().inflate(R.layout.no_cards_layout, noCardsViewParent, false);



        LinearLayout nocardLL = (LinearLayout) findViewById(R.id.noCardLl);
        DBHelper dbHelper = new DBHelper(MainActivity.this);

        if (day.equals("all")) {
            Cursor cursor = dbHelper.getAllEvents();
            if (cursor.getCount() == 0) {

                    if(noCardsViewParent != null){
                        noCardsViewParent.removeView(noCardView);
                        noCardsViewParent.addView(noCardView);

                    }
                    else if(mainListViewParent != null){
                        mainListViewParent.removeView(mainListView);
                        noCardsViewParent.removeView(noCardView);
                        noCardsViewParent.addView(noCardView);
                    }

            } else {

                if(noCardsViewParent != null){
                    noCardsViewParent.removeView(noCardView);
                    mainListViewParent.removeView(mainListView);
                    mainListViewParent.removeView(noCardView);
                    noCardsViewParent.addView(mainListView);

                }
                else if(mainListViewParent != null){
                    mainListViewParent.removeView(mainListView);
                    noCardsViewParent.removeView(noCardView);
                    mainListViewParent.addView(mainListView);
                }


            String[] fromColumns = {COLUMN_NAME_DAY, COLUMN_NAME_EVENT_NAME, COLUMN_NAME_TIME, COLUMN_NAME_VENUE};
                int[] toViews = {R.id.dayTextView, R.id.courseNameTextView, R.id.timeTextView, R.id.venueTextView};
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.table_card, cursor, fromColumns, toViews, 0);
                simpleCursorAdapter.notifyDataSetChanged();

                mainListView.setAdapter(simpleCursorAdapter);
                mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                        String[] options = {"Edit", "Delete"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Card Options");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if("Edit".equals(options[i])){
                                    Intent intent = new Intent(MainActivity.this, EditEventActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                        return true;
                    }
                });


            }


        }else{

                Cursor cursor = dbHelper.getEventsForDay(day);
                if (cursor.getCount() == 0) {

                    if(noCardsViewParent != null){
                        noCardsViewParent.removeView(noCardView);
                        noCardsViewParent.addView(noCardView);

                    }
                    else if(mainListViewParent != null){
                        noCardsViewParent.removeView(noCardView);
                        mainListViewParent.removeView(mainListView);
                        noCardsViewParent.addView(noCardView);
                    }


                    }

            else {


                    if(noCardsViewParent != null){
                        noCardsViewParent.removeView(noCardView);
                        mainListViewParent.removeView(mainListView);
                        noCardsViewParent.addView(mainListView);

                    }
                    else if(mainListViewParent != null){
                        mainListViewParent.removeView(mainListView);
                        mainListViewParent.addView(mainListView);
                    }
                String[] fromColumns = {COLUMN_NAME_DAY, COLUMN_NAME_EVENT_NAME, COLUMN_NAME_TIME, COLUMN_NAME_VENUE};
                int[] toViews = {R.id.dayTextView, R.id.courseNameTextView, R.id.timeTextView, R.id.venueTextView};
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.table_card, cursor, fromColumns, toViews, 0);
                simpleCursorAdapter.notifyDataSetChanged();
                mainListView = (ListView) findViewById(R.id.mainListView);
                mainListView.setAdapter(simpleCursorAdapter);

                mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                        String[] options = {"Edit", "Delete"};
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Card Options");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if("Edit".equals(options[i])){
                                    Today today = new Today();
                                    Intent intent = new Intent(MainActivity.this, EditEventActivity.class);
                                    Bundle bundle = new Bundle();
                                    TextView name = (TextView) view.findViewById(R.id.courseNameTextView);
                                    System.out.println(name.getText().toString());

                                    today = (Today) adapterView.getItemAtPosition(i);
                                    Log.i( "today name", today.getCourse_name());
                                    intent.putExtra("eventName", name.getText().toString());
                                    startActivity(intent);
                                }
                            }
                        });
                        builder.show();

                        return true;
                    }
                });


            }
        }
    }
}