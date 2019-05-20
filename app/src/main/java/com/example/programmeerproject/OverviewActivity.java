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

public class OverviewActivity extends AppCompatActivity implements LessonGetter.Callback {

    ArrayList<Lesson> LessonObjects;

    User currentUser;

    Lesson l;

    Button b;
    TextView DayText;

    String DayString;
    Integer DayIndex;
    String[] Days = {"error", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        LessonGetter getter = new LessonGetter(this);

        //gets the intent from the previous activity
        Intent intent = getIntent();

        //retrieves player
        currentUser = (User) intent.getSerializableExtra("current_User");

        getter.getLessons(this);
    }

    @Override
    public void gotlessons(ArrayList<Lesson> lessons) {

        Date currentDay = Calendar.getInstance().getTime();
        String DayString = currentDay.toString();
        System.out.println(DayString);
        DayIndex = 0;
        if (DayString.toLowerCase().contains("mon")) {
            DayIndex = 1;
        } else if (DayString.toLowerCase().contains("tue")) {
            DayIndex = 2;
        } else if (DayString.toLowerCase().contains("wed")) {
            DayIndex = 3;
        } else if (DayString.toLowerCase().contains("thu")) {
            DayIndex = 4;
        } else if (DayString.toLowerCase().contains("fri")) {
            DayIndex = 5;
        } else if (DayString.toLowerCase().contains("sat")) {
            DayIndex = 6;
        } else if (DayString.toLowerCase().contains("sun")) {
            DayIndex = 7;
        } else {
            Toast.makeText(this, "Dit gaat niet goed", Toast.LENGTH_SHORT).show();
        }
        DayText = findViewById(R.id.DayTextview);
        DayString = Days[DayIndex];
        DayText.setText(DayString);

        LessonObjects = lessons;
        fillOverview(LessonObjects, Days[DayIndex]);
    }

    @Override
    public void gotlessonserror(String message) {
        Toast.makeText(this, "No lessons available for this day", Toast.LENGTH_SHORT).show();
    }

    public void NextDay(View view) {
        DayIndex = (DayIndex + 1) % 8;
        if (DayIndex == 0) {
            DayIndex = 1;
            DayString = Days[DayIndex];
            DayText.setText(DayString);
            fillOverview(LessonObjects, DayString);
        } else {
            DayString = Days[DayIndex];
            DayText.setText(DayString);
            fillOverview(LessonObjects, DayString);
        }
    }

    public void PreviousDay(View view) {
        DayIndex = (DayIndex - 1);
        if (DayIndex == 0) {
            DayIndex = 7;
            DayString = Days[DayIndex];
            DayText.setText(DayString);
            fillOverview(LessonObjects, DayString);
        } else {
            DayString = Days[DayIndex];
            DayText.setText(DayString);
            fillOverview(LessonObjects, DayString);
        }

    }

    public void fillOverview(ArrayList<Lesson> lessons, String Day) {

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

        for (int i = 0; i < lessons.size(); i++) {
            l = lessons.get(i);
            String DayTxt = l.getDay();
            String Time = l.getTime();
            Integer TimeIndex;

            if (DayTxt.equals(Day)) {

                if (Time.equals("08:00")) {
                    TimeIndex = 10;
                } else if (Time.equals("09:00")) {
                    TimeIndex = 20;
                } else if (Time.equals("10:00")) {
                    TimeIndex = 30;
                } else if (Time.equals("11:00")) {
                    TimeIndex = 40;
                } else if (Time.equals("12:00")) {
                    TimeIndex = 50;
                } else if (Time.equals("13:00")) {
                    TimeIndex = 60;
                } else if (Time.equals("14:00")) {
                    TimeIndex = 70;
                } else if (Time.equals("15:00")) {
                    TimeIndex = 80;
                } else if (Time.equals("16:00")) {
                    TimeIndex = 90;
                } else if (Time.equals("17:00")) {
                    TimeIndex = 100;
                } else if (Time.equals("18:00")) {
                    TimeIndex = 110;
                } else if (Time.equals("19:00")) {
                    TimeIndex = 120;
                } else if (Time.equals("20:00")) {
                    TimeIndex = 130;
                } else if (Time.equals("21:00")) {
                    TimeIndex = 140;
                } else {
                    TimeIndex = 1000;
                }

                if (TimeIndex.equals(1000)) {

                    Toast.makeText(this, "Time is incompatible", Toast.LENGTH_SHORT).show();
                } else {

                    String Location = l.getLocation();

                    if (Location.equals("1")) {
                        TimeIndex = TimeIndex;
                    } else if (Location.equals("2")) {
                        TimeIndex = TimeIndex + 1;
                    } else if (Location.equals("3")) {
                        TimeIndex = TimeIndex + 2;
                    } else if (Location.equals("4")) {
                        TimeIndex = TimeIndex + 3;
                    }

                    TimeIndex.toString();
                    String ButtonName = "button" + TimeIndex;
                    int resID = getResources().getIdentifier(ButtonName, "id", getPackageName());

                    b = findViewById(resID);
                    b.setVisibility(View.VISIBLE);
                    b.setText(l.getName());
                }
            }

        }
    }

    public void onClick(View view) {
        Button button = findViewById(view.getId());
        String ButtonText = button.getText().toString();
        for (int i = 0; i < LessonObjects.size(); i++) {
            l = LessonObjects.get(i);
            String LessonName = l.getName();
            if (LessonName.equals(ButtonText)) {
                break;
            }
        }
        Intent intent = new Intent(OverviewActivity.this, DetailActivity.class);
        intent.putExtra("current_User", currentUser);
        intent.putExtra("current_Lesson", l);
        startActivity(intent);
    }
}
