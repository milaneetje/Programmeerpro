package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class RegistrationsActivity extends AppCompatActivity implements RegistrationGetter.Callback{

    User currentUser;

    TextView Username;
    TextView NumberofRegistrations;

    public static boolean ASC = true;
    public static boolean DESC = false;

    ArrayList<Registration> AllRegistrationObjects;
    ArrayList<String> currentUserRegistrations= new ArrayList<>();
    ArrayList<String> Orderedoccurrances = new ArrayList<>();
    ArrayList<String> FavoriteLessons = new ArrayList<>();

    Integer RegistrationCount = 0;
    String LessonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations);
        //gets the intent from the previous activity

        Intent intent = getIntent();

        //retrieves player
        currentUser = (User) intent.getSerializableExtra("current_User");

        Username = findViewById(R.id.UsernameTxt);
        Username.setText(currentUser.getName());

        //Gets all registrations
        RegistrationGetter getter = new RegistrationGetter(this);
        getter.getRegistration(this);
    }

    @Override
    //Function that is called if registrations have been found
    public void gotregistrations(ArrayList<Registration> registrations) {

        AllRegistrationObjects = registrations;

        //For all registrations checks what registrations belong to the user
        for (int i = 0; i < AllRegistrationObjects.size(); i++) {
            Registration r = AllRegistrationObjects.get(i);
            if (currentUser.getName().equals(r.getParticipant())) {
                RegistrationCount++;
                currentUserRegistrations.add(r.getName());
            }
        }

        //Sets Textview with the total time a user has registered
        NumberofRegistrations = findViewById(R.id.NumberofregistrationsTxt);
        String NoR = "Je hebt je " + RegistrationCount + " keer ingeschreven";
        NumberofRegistrations.setText(NoR);

        //Counts registrations
        countRegistrations(currentUserRegistrations);

    }

    @Override
    //Function that is called if registrations have NOT been found
    public void gotregistrationsserror(String message) {
        Toast.makeText(this, "Server Offline", Toast.LENGTH_SHORT).show();
    }

    //Function that creates a hashmap with a lessoncount and a lessonname
    public void countRegistrations(ArrayList<String> list)
    {
        // hashmap to store the frequency of lessons
        Map<String, Integer> userRegistrations = new HashMap<>();

        for (String i : list) {
            Integer j = userRegistrations.get(i);
            userRegistrations.put(i, (j == null) ? 1 : j + 1);
        }

        Map<String, Integer> sortedMapDesc = sortByNumberofRegistrations(userRegistrations, DESC);
        printLessons(sortedMapDesc);
    }

    //Function that sorts hashmap by lessoncount
    private static Map<String, Integer> sortByNumberofRegistrations(Map<String, Integer> unsortedRegistrations, final boolean order)
    {

        //Creates a linked list with the same values as the hashmap
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedRegistrations.entrySet());

        //Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> r1,
                               Map.Entry<String, Integer> r2)
            {
                if (order)
                {
                    return r1.getValue().compareTo(r2.getValue());
                }
                else
                {
                    return r2.getValue().compareTo(r1.getValue());

                }
            }
        });

        //Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedRegistrations = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedRegistrations.put(entry.getKey(), entry.getValue());
        }

        return sortedRegistrations;
    }

    //Function that fills the listview
    public void printLessons(Map<String, Integer> map)
    {
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            String strng = entry.getValue() + "x " + entry.getKey();
            Orderedoccurrances.add(strng);
        }

        //checks if there are already registrations on the usersname and handles different scenarios
        if (Orderedoccurrances.size() == 0) {
            TextView NoRegistrations = findViewById(R.id.NoRegistrationsTxt);
            NoRegistrations.setText("Je hebt nog geen inschrijvingen");
            NoRegistrations.setVisibility(View.VISIBLE);
        }
        //If the user has more than 5 different lesson registrations, we show the highest 3
        else if (Orderedoccurrances.size() >= 3) {
            for (int i = 0; i < 3; i++){
                FavoriteLessons.add(Orderedoccurrances.get(i));
            }

            //Sets the registrationadapter and fills the listview
            RegistrationsAdapter adapter = new RegistrationsAdapter(this, R.layout.list_item, FavoriteLessons, currentUser.getName());
            final ListView listView = findViewById(R.id.FavoriteLessonsList);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);

            //Checks what box was clicked and navigates to the detailpage
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String clickedLesson = (String) parent.getItemAtPosition(position);

                    String[] clickedString = clickedLesson.split(" ");
                    StringBuilder strBuilder = new StringBuilder();
                    for (int i=1; i < clickedString.length; i++ ) {
                        strBuilder.append(clickedString[i]);
                    }
                    LessonName = strBuilder.toString();
                    DetailRegistration(view);
                }
            });
        }

        //If the user has not more than three different lessons he has registered for, all of them
        //are shown
        else {

            //Sets the adapter and fills the listview
            RegistrationsAdapter adapter = new RegistrationsAdapter(this, R.layout.list_item, Orderedoccurrances, currentUser.getName());
            final ListView listView = findViewById(R.id.FavoriteLessonsList);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);

            //Listens for a click and pushes to the detailpage
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String clickedLesson = (String) parent.getItemAtPosition(position);

                    String[] clickedString = clickedLesson.split(" ");
                    StringBuilder strBuilder = new StringBuilder();
                    for (int i=1; i < clickedString.length; i++ ) {
                        strBuilder.append(clickedString[i]);
                    }
                    LessonName = strBuilder.toString();
                    DetailRegistration(view);
                }
            });
        }
    }

    //Function that pushes intent to the detailpage
    public void DetailRegistration(View view) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("current_User",currentUser);
        intent.putExtra("current_Lesson",LessonName);
        intent.putExtra("Activity_origin", "Registrations");
        startActivity(intent);
    }

    //Function that pushes intent to the overviewpage
    public void Overview(View view) {
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra("current_User",currentUser);
        startActivity(intent);
        finish();
    }

    //Function that pushes intetn to the Homepage
    public void Homepage(View view) {
        Intent intent = new Intent(this, HomepageActivity.class);
        intent.putExtra("current_User",currentUser);
        startActivity(intent);
        finish();
    }
}

