package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements UserGetter.Callback {

    EditText UserName;
    EditText Password;

    String usernameString;
    String passwordString;
    String UsernameInput;

    ArrayList<User> UserObjects;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Sets layout for the activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserGetter getter = new UserGetter(this);

        //Gets all existing users
        getter.getUser(this);
    }

    @Override
    //Function that is called by Usergetter when users have been found
    public void gotusers(ArrayList<User> users) {
        UserObjects = users;
    }

    @Override
    //Function that is called by Usergetter when users have not been found
    public void gotuserserror(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //onClick for the login button, retrieves entered username and password
    public void Login(View view) {
        UserName = findViewById(R.id.UsernameTextview);
        Password = findViewById(R.id.PasswordTextview);

        //Gets the filled in the Username and Password
        usernameString = UserName.getText().toString();
        passwordString = Password.getText().toString();

        Compare(UserObjects);
    }

    //onClick for the register button, pushes user to the register activity
    public void Register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    // Function that compares the entered username and finds corresponding user if there is one and
    // and compares entered password with entered password for user.
    public void Compare(ArrayList<User> objects) {

        Boolean filled = true;

        if (usernameString.length() == 0 || passwordString.length() == 0){
            filled = false;
        }

        if(filled.equals(true)) {
            //For the total number of users
            for (int i = 0; i < objects.size(); i++) {
                user = UserObjects.get(i);
                //If Username matches, For loop is stopped
                if (usernameString.equals(user.getName())) {
                    break;
                }
            }

            //If password for the user matches the entered password, user is logged in
            if (passwordString.equals(user.getPassword())) {
                //pushes the player to the next activity
                Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                intent.putExtra("current_User", user);
                startActivity(intent);
                finish();
            }
            //Else a toast messages shows
            else {
                Toast.makeText(this, "Onjuist wachtwoord of gebruikersnaam", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Vul ALLE velden in", Toast.LENGTH_SHORT).show();
        }
    }



}
