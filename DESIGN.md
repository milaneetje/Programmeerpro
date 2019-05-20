# Design document

## Login
UI
When a user already has a account he or she can login using their username and password. If not he or she can register and create an account

Backend
When a user tries to login, Rester tries to find a Json object for that username. If rester can't find a Json object with the given username, it will return a statement saying 'account does not exist', If rester can find a json object but the password is incorrect, it will return a statement saying 'password is incorrect'. When Rester finds an object with the given username and the password is correct, it logs into the application by loading the homescreen.

## Register
UI
When a user wants to register He can give in a username and password. By entering a username and password an account will be created and the user will be sent to the homescreen.

Backend
When a user registers an account is created by creating a new json object with that username containing the password and an empty list for past registrations. After that the homescreen activity starts.

## Homescreen
UI
At the homescreen the user is able to directly register for classes that he has registered for the most. He is also able to fo to the overview.

Backend
When the homescreen is loaded past registrations are selected from the user json and the class that has the most recent registrations is displayed directly on the homescreen for the user to register. When these frequent lessons are selected the user is directly sent to the detailed lesson activity.

## Overview
UI
At the overview screen the user is presented with a timetable where all the lessons are displayed at their designated times and dates

Backend
When the overview is selected, all the lessons are selected from the overview json. For now only the name and time and date and location are used to put it at the right place in the timetable. OPTIONAL: when a lesson is full, it'll turn grey and won't be selectable anymore.

## Detail
UI
At the detail screen the user is presented with a detailed description of the lesson, the amount of places left, the time and date and the location also the user will be able to register for the class.

Backend
When a lesson is selected all the information of the lesson itself is selected from the Json overview, this contains the name, description, number of places left, time and date and the location. When a user decides to register for the class, his username will be added tot the registration list inside the overview json.

## Registrations
UI
At the registration screen all of the active registrations belonging to the logged in user will be displayed in a timetable like the overview one.

Backend
When the current registrations are displayed all the lessons where the user is registered are selected and the name, time and date and location. These are diplayed in a timetable like the one in the overview activity.

## Lesson Data
the data from the lessons are taken directly from https://usc.uva.nl/sport/groepsfitness/ and parsed into the overview json manually

![](images/Programmeerproject-design.png)
