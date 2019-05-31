package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OverviewActivity extends AppCompatActivity implements LessonGetter.Callback {

    ArrayList<Lesson> LessonObjects;
    ArrayList<Lesson> DayLessonObjects = new ArrayList<>();

    User currentUser;

    Lesson l;

    Button b;
    TextView DayText;

    String DayString;
    Integer DayIndex;

    Date date;
    Date currentDate;
    String currentDateString;
    Calendar calendar = Calendar.getInstance();

    String[] Days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    Map<Button, Lesson> ButtonTimes = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        LessonGetter getter = new LessonGetter(this);

        //gets the intent from the previous activity
        Intent intent = getIntent();

        //retrieves user
        currentUser = (User) intent.getSerializableExtra("current_User");

        //Gets all lessons
        getter.getLessons(this);
    }

    @Override
    //Function that's called when Lessons are found
    public void gotlessons(ArrayList<Lesson> lessons) {

        //Gets the current Date
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        currentDate = cal.getTime();

        //Creates a string for todays date
        currentDateString = currentDate.toString();
        String[] arrOfcurrentDate = currentDateString.split(" ");
        currentDateString = arrOfcurrentDate[0] + " " + arrOfcurrentDate[1] + " " + arrOfcurrentDate[2];

        //Gets a second instance of the date that will change through navigation in the activity
        date = cal.getTime();
        DayString = date.toString();

        //Creates a string for the 2nd date instance
        String[] arrOfDayString = DayString.split(" ");
        DayString = arrOfDayString[0] + " " + arrOfDayString[1] + " " + arrOfDayString[2];

        //Finds a integer to the corresponding day in the week
        DayIndex = 7;
        if (DayString.toLowerCase().contains("mon")) {
            DayIndex = 0;
        } else if (DayString.toLowerCase().contains("tue")) {
            DayIndex = 1;
        } else if (DayString.toLowerCase().contains("wed")) {
            DayIndex = 2;
        } else if (DayString.toLowerCase().contains("thu")) {
            DayIndex = 3;
        } else if (DayString.toLowerCase().contains("fri")) {
            DayIndex = 4;
        } else if (DayString.toLowerCase().contains("sat")) {
            DayIndex = 5;
        } else if (DayString.toLowerCase().contains("sun")) {
            DayIndex = 6;
        } else {
            Toast.makeText(this, "Dit gaat niet goed", Toast.LENGTH_SHORT).show();
        }

        //Sets a textview with today's date string
        DayText = findViewById(R.id.DayTextview);
        DayText.setText(DayString);

        //Gets all lessons
        LessonObjects = lessons;

        //Calls function fillOverview
        fillOverview(LessonObjects, Days[DayIndex]);
    }

    @Override
    //Function that's called when Lessons are NOT found
    public void gotlessonserror(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //onClick for the "vooruit" button that changes date by one day forward
    public void NextDay(View view) {

        //Gets an instance of date one day forward
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();

        //Creates a string for the date formatted as: Day MM dd
        DayString = date.toString();
        String[] arrOfDayString = DayString.split(" ");
        DayString = arrOfDayString[0] + " " + arrOfDayString[1] + " " + arrOfDayString[2];

        //Sets a textview with the datestring
        DayText = findViewById(R.id.DayTextview);
        DayText.setText(DayString);

        //If dayindex equals 6 day is sunday and next dayindex should be 1 again
        if (DayIndex == 6) {
            DayIndex = 0;
            fillOverview(LessonObjects, Days[DayIndex]);
        } else {
            DayIndex = DayIndex + 1;
            fillOverview(LessonObjects, Days[DayIndex]);
        }
    }

    //onClick for the "terug" button that changes date by one day backwards
    public void PreviousDay(View view) {

        //Makes sure that if date equals today's date, users can't navigate back further
        if (date.equals(currentDate)) {
            Toast.makeText(this, "No lessons available in the past", Toast.LENGTH_SHORT).show();
        }
        //If date does not equal today's date it HAS to be bigger and users can navigate back to today
        else {
            //Gets an instance of date one day forward
            calendar.add(Calendar.DATE, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            date = calendar.getTime();

            //Creates a string for the date formatted as: Day MM dd
            DayString = date.toString();
            String[] arrOfDayString = DayString.split(" ");
            DayString = arrOfDayString[0] + " " + arrOfDayString[1] + " " + arrOfDayString[2];

            //If dayindex equals 6 day is sunday and next dayindex should be 1 again
            DayText = findViewById(R.id.DayTextview);
            DayText.setText(DayString);

            //If dayindex equals 0 than current day is monday and we want to navigate to sunday
            if (DayIndex == 0) {
                DayIndex = 6;
                fillOverview(LessonObjects, Days[DayIndex]);
            } else {
                DayIndex = DayIndex - 1;
                fillOverview(LessonObjects, Days[DayIndex]);
            }
        }


    }

    //Function that fills the overview
    public void fillOverview(ArrayList<Lesson> lessons, String Day) {

        //Makes sure that arraylist DayLessonObjects is empty every time the function is called
        DayLessonObjects.clear();

        //Sets every button back to invisible before filling the overview
        Integer Buttonindex;
        for (int j = 0; j < 14; j++) {
            for (int k = 0; k < 4; k++) {
                Buttonindex = (j + 1) * 10 + k;
                Buttonindex.toString();
                String ButtonName = "button" + Buttonindex;
                int resID = getResources().getIdentifier(ButtonName, "id", getPackageName());
                b = findViewById(resID);
                b.setVisibility(View.INVISIBLE);
            }
        }

        //Finds all the lessons on the current day

        for (int m = 0; m < lessons.size(); m++) {
            l = lessons.get(m);
            String DayTxt = l.getDay();

            if (DayTxt.equals(Day)) {
                DayLessonObjects.add(l);
            }
        }

        //For all of the current day's lessons, finds the button by index of time and location
        for (int i = 0; i < DayLessonObjects.size(); i++) {
            Lesson l = DayLessonObjects.get(i);
            String Time = l.getTime();
            Integer TimeIndex = 0;

            //converts the time and location string to an integer and calculates the timeindex
            String[] TimeArray = Time.split(":");
            Integer TimeInt = Integer.parseInt(TimeArray[0]);
            Integer LocationInt = Integer.parseInt(l.getLocation());
            TimeIndex = ((TimeInt - 8) + 1) * 10 + (LocationInt - 1);
            if(l.getLocation().equals("Conditietraining")){
            }


            //Creates a string with which a button can be identified
            TimeIndex.toString();
            String ButtonName = "button" + TimeIndex;
            int resID = getResources().getIdentifier(ButtonName, "id", getPackageName());

            //Finds correct button with the created string, sets text and visibility
            b = findViewById(resID);
            b.setVisibility(View.VISIBLE);
            b.setText(l.getName());

            //Creates a hashmap that contains all of current day's lessons and the corresponding buttons
            ButtonTimes.put(b, l);
        }

    }

    //onClick for lessonbuttons
    public void onClick(View view) {

        //Finds clicked button
        Button button = findViewById(view.getId());
        Lesson lesson;

        for (Map.Entry<Button, Lesson> entry : ButtonTimes.entrySet())
        {
            Button b = entry.getKey();
            //if clicked button matches a button in the hashmap, the corresponding lesson is found
            if (button.equals(b)) {
                lesson = entry.getValue();
                Intent intent = new Intent(OverviewActivity.this, DetailActivity.class);
                intent.putExtra("current_User", currentUser);
                intent.putExtra("current_Lesson", lesson);
                intent.putExtra("Date", DayString);
                intent.putExtra("Activity_origin", "Overview");
                startActivity(intent);
            }
        }
    }
}
