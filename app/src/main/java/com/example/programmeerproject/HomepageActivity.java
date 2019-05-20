package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    User currentUser;

    String NoLessonsString;
    ArrayAdapter<String> adapter;

    TextView Username;
    TextView NoLessons;

    Button LessonOne;
    Button LessonTwo;
    Button LessonThree;
    Button LessonFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //gets the intent from the previous activity
        Intent intent = getIntent();

        //retrieves player
        currentUser = (User) intent.getSerializableExtra("current_User");

        Username = findViewById(R.id.UsernameText);
        Username.setText(currentUser.getName());

        if (currentUser.getRecentLesson().equals("NA")) {
            LessonOne = findViewById(R.id.LessonButtonONE);
            LessonOne.setVisibility(View.INVISIBLE);
            LessonTwo = findViewById(R.id.LessonButtonTWO);
            LessonTwo.setVisibility(View.INVISIBLE);
            LessonThree = findViewById(R.id.LessonButtonTHREE);
            LessonThree.setVisibility(View.INVISIBLE);
            LessonFour = findViewById(R.id.LessonButtonFOUR);
            LessonFour.setVisibility(View.INVISIBLE);

            NoLessonsString= "Je hebt nog geen recente inschrijvingen";
            NoLessons = findViewById(R.id.NoLessonTxT);
            NoLessons.setVisibility(View.VISIBLE);
            NoLessons.setText(NoLessonsString);
        }
    }

    public void Overview(View view) {
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra("current_User",currentUser);
        startActivity(intent);
    }
}
