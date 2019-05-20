package com.example.programmeerproject;


import android.content.Context;
import android.util.EventLogTags;

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

public class LessonGetter implements Response.ErrorListener, Response.Listener<JSONArray> {

    Context context;
    Callback callback;

    public LessonGetter(Context context) {
        this.context = context;
    }

    // get lessons from server
    public void getLessons(Callback callback) {
        this.callback = callback;

        // url to server with the scores from the users
        String url = "http://ide50-mielan29.legacy.cs50.io:8080/lessons";

        // requests
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,
                this, this);
        queue.add(request);
    }

    @Override
    public void onResponse(JSONArray response) {

        // Arraylist for Lesson items
        ArrayList<Lesson> quest = new ArrayList<>();

        try {
            // each time pick a new object, with a Name, Day, Time and Description
            for (int i = 0; i < response.length(); i++) {

                // get JSON object from server at position i
                JSONObject json = response.getJSONObject(i);

                // get Day, Time, Location an Description from JSON file in url
                String Name = json.getString("Name");
                String Day = json.getString("Day");
                String Time = json.getString("Time");
                String Location = json.getString("Location");
                String Description = json.getString("Description");


                // put Name, Day, Time and Description in class
                Lesson l = new Lesson(Name, Description, Day, Time, Location);

                // add class object to list
                quest.add(l);
            }
            System.out.println("Tot hier komen we nog.");

            callback.gotlessons(quest);

            // if failed
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface Callback {
        void gotlessons(ArrayList<Lesson> Lesson);
        void gotlessonserror(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        callback.gotlessonserror(error.getMessage());
    }


}
