package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements UserGetter.Callback {

    User currentUser;
    EditText Name;
    EditText Password;
    EditText Passwordconfirm;

    ArrayList<User> UserObjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

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

    public void Register(View view) {

        Boolean exists = false;
        Boolean filled = true;

        //Finds Fields to be entered
        Name = findViewById(R.id.UsernameTextview);
        Password = findViewById(R.id.PasswordTextview);
        Passwordconfirm = findViewById(R.id.PasswordConfirm);

        //Gets the filled in the name and password and password confirmation
        String NameString = Name.getText().toString();
        String PasswordString = Password.getText().toString();
        String PassworconfirmString = Passwordconfirm.getText().toString();
        String CurrentlessonString = "NA";

        if (NameString.length() == 0 || PasswordString.length() == 0 || PassworconfirmString.length() == 0){
            filled = false;
        }

        //creates a new User
        for (int i =0; i<UserObjects.size(); i++) {
            User user = UserObjects.get(i);
            if (NameString.equals(user.getName())) {
                exists = true;
                break;
            }
        }

        if(filled.equals(true)) {
            if (exists.equals(false)) {
                if (PasswordString.equals(PassworconfirmString)) {
                    currentUser = new User(NameString, PasswordString, CurrentlessonString);

                    UserSetter post = new UserSetter(RegisterActivity.this);
                    post.setUser(NameString, PasswordString, CurrentlessonString);

                    //pushes the User to the next activity
                    Intent intent = new Intent(RegisterActivity.this, HomepageActivity.class);
                    intent.putExtra("current_User", currentUser);
                    startActivity(intent);
                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Wachtwoord en bevestiging komen niet overeen", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Username bestaat al", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Vul ALLE velden in", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}