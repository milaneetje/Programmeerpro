package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    User currentUser;
    Lesson currentLesson;
    String Date;

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

        Date = (String) intent.getSerializableExtra("Date");

        //Sets lessontitle
        LessonTitle= findViewById(R.id.LessonTitle);
        LessonTitle.setText(currentLesson.getName());

        //Sets lessondescription
        LessonDescription = findViewById(R.id.LessonDescription);
        LessonDescription.setText(currentLesson.getDescription());
    }

    public void Lessonregistration(View view) {

        Registration r = new Registration(currentLesson.getName(), Date, currentLesson.getTime(), currentLesson.getLocation(), currentUser.getName());

        RegistrationSetter post = new RegistrationSetter(DetailActivity.this);
        post.setRegistration(currentLesson.getName(), currentUser.getName(), Date, currentLesson.getTime(), currentLesson.getLocation());

        UserSetter UpdateLesson = new UserSetter(DetailActivity.this);
        UpdateLesson.setRegistrationList(currentLesson.getName(),currentUser.getRecentLesson(), currentUser.getName());

        //pushes the player to the next activity
        Intent intent = new Intent(DetailActivity.this, RegistrationsActivity.class);
        intent.putExtra("current_User", currentUser);
        startActivity(intent);
        finish();

    }
}
