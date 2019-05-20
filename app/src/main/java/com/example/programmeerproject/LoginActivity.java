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

    ArrayList<User> UserObjects;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserGetter getter = new UserGetter(this);

        getter.getUser(this);
    }

    @Override
    public void gotusers(ArrayList<User> users) {

        UserObjects = users;
        String Users = "aantal users zijn: " + users.size();
        Toast.makeText(this, Users, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotuserserror(String message) {
        Toast.makeText(this, "Dit gaat niet goed", Toast.LENGTH_SHORT).show();
    }

    public void Login(View view) {

        UserName = findViewById(R.id.UsernameTextview);
        Password = findViewById(R.id.PasswordTextview);

        //Gets the filled in the Username and Password
        usernameString = UserName.getText().toString();
        passwordString = Password.getText().toString();

        Compare(UserObjects);
    }

    public void Register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void Compare(ArrayList<User> objects) {

        for (int i =0; i<objects.size(); i++) {
            user = UserObjects.get(i);
            if (usernameString.equals(user.getName())){
                break;
            }
        }

        if(passwordString.equals(user.getPassword())) {
            //pushes the player to the next activity
            Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
            intent.putExtra("current_User",user);
            startActivity(intent);
            finish();
        }
        else{
            Toast.makeText(this, "Onjuist wachtwoord", Toast.LENGTH_SHORT).show();
        }
    }

}
