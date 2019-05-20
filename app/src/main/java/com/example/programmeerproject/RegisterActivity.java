package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    User currentUser;
    TextView Name;
    TextView Password;
    TextView Passwordconfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
    }

    public void Register(View view) {

        Name = findViewById(R.id.UsernameTextview);
        Password = findViewById(R.id.PasswordTextview);
        Passwordconfirm = findViewById(R.id.PasswordConfirm);

        //Gets the filled in the name and the selected difficulty
        String NameString = Name.getText().toString();
        String PasswordString = Password.getText().toString();
        String PassworconfirmString = Passwordconfirm.getText().toString();
        String CurrentlessonString = "NA";

        //creates a new player
        if(PasswordString.equals(PassworconfirmString)){
            currentUser = new User(NameString, PasswordString, CurrentlessonString);

            UserSetter post = new UserSetter(RegisterActivity.this);
            post.setUser(NameString, PasswordString, CurrentlessonString);

            //pushes the player to the next activity
            Intent intent = new Intent(RegisterActivity.this, OverviewActivity.class);
            intent.putExtra("current_Player", currentUser);
            startActivity(intent);
            finish();
        }
        else {
            Toast toast=Toast.makeText(getApplicationContext(),"Password and confirmation don't match!",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
