package com.example.programmeerproject;
import java.io.Serializable;

public class User implements Serializable {

    String Name;
    String Password;
    String RecentLesson;

    public User(String name, String password, String recentLesson) {
        this.Name = name;
        this.Password = password;
        this.RecentLesson = recentLesson;

    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getRecentLesson() {
        return RecentLesson;
    }
}

