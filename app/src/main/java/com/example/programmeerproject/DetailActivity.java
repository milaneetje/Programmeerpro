package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    User currentUser;
    Lesson currentLesson;

    TextView LessonTitle;
    TextView LessonDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //gets the intent from the previous activity
        Intent intent = getIntent();

        //retrieves user and lesson
        currentUser = (User) intent.getSerializableExtra("current_User");

        currentLesson = (Lesson) intent.getSerializableExtra("current_Lesson");

        //Sets lessontitle
        LessonTitle= findViewById(R.id.LessonTitle);
        LessonTitle.setText(currentLesson.getName());

        //Sets lessondescription
        LessonDescription = findViewById(R.id.LessonDescription);
        LessonDescription.setText(currentLesson.getDescription());
    }
}
