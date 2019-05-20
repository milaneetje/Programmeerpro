package com.example.programmeerproject;

import java.io.Serializable;

public class Lesson implements Serializable{

    String Name;
    String Description;
    String Day;
    String Time;
    String Location;

    public Lesson(String name, String description, String day, String time, String location) {
        this.Name = name;
        this.Description = description;
        this.Day = day;
        this.Time = time;
        this.Location = location;

    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getDay() {
        return Day;
    }

    public String getTime() {
        return Time;
    }

    public String getLocation(){return Location;}
}
