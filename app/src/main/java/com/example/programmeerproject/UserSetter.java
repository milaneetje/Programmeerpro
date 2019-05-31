package com.example.programmeerproject;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserSetter {

    Context context;
    String AllLessons;

    public UserSetter(Context context) {
        this.context = context;
    }

    public void setUser(final String name, final String password, final String lesson){

        RequestQueue queue = Volley.newRequestQueue(this.context);

        // url where we want to post the username and password
        String urlUsers = "http://ide50-mielan29.legacy.cs50.io:8080/users";

        // new string request
        StringRequest pasteUser = new StringRequest(Request.Method.POST, urlUsers,
                null, new Response.ErrorListener() {

            // if post failed
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            // create new hashmap
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", name);
                params.put("password", password );
                params.put("Recent Lesson", lesson);

                return  params;
            }
        };

        queue.add(pasteUser);

    }

    public void setRegistrationList(final String lesson, final String RecentLessons, final String Username){

        RequestQueue queue = Volley.newRequestQueue(this.context);

        // url where we want to post the username and password
        String urlUsers = "http://ide50-mielan29.legacy.cs50.io:8080/users?username=" + Username;

        // new string request
        StringRequest pasteRegistrationList = new StringRequest(Request.Method.PUT, urlUsers,
                null, new Response.ErrorListener() {

            // if post failed
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            // create new hashmap
            @Override
            protected Map<String, String> getParams(){
                if (RecentLessons.equals("NA")){
                    AllLessons = lesson;
                }
                else {
                    AllLessons = lesson + RecentLessons;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("Recent Lesson", AllLessons);

                return  params;
            }
        };

        queue.add(pasteRegistrationList);

    }

}