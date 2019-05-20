package com.example.programmeerproject;

import android.animation.RectEvaluator;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class UserGetter implements Response.ErrorListener, Response.Listener<JSONArray> {

    Context context;
    Callback callback;

    public UserGetter(Context context) {
        this.context = context;
    }

    // get high score from server
    public void getUser(Callback callback) {
        this.callback = callback;

        // url to server with the scores from the users
        String url = "http://ide50-mielan29.legacy.cs50.io:8080/users";

        // request
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,
                this, this);
        queue.add(request);

    }

    @Override
    public void onResponse(JSONArray response) {

        // Arraylist for User items
        ArrayList<User> quest = new ArrayList<>();

        try {

            // each time pick a new object, with a username and a score from that username
            for (int i = 0; i < response.length(); i++) {

                // get JSON object from server at position i
                JSONObject json = response.getJSONObject(i);

                // get username and password and recent lessons from JSON file in url
                String Username = json.getString("username");
                String Password = json.getString("password");
                String RecentLesson = json.getString("Recent Lesson");

                // put username, password and recent lesson in class
                User u = new User(Username, Password, RecentLesson);

                // add class object to list
                quest.add(u);
            }

            callback.gotusers(quest);

            // if failed
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface Callback {
        void gotusers(ArrayList<User> users);
        void gotuserserror(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.gotuserserror(error.getMessage());
    }


}