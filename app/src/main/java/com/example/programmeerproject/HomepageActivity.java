package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HomepageActivity extends AppCompatActivity implements RegistrationGetter.Callback{

    User currentUser;

    String clickedString;

    TextView Username;
    TextView NoLessons;

    ArrayList<Registration> AllRegistrationObjects;
    ArrayList<Registration> UserRegistrationObjects = new ArrayList<>();
    ArrayList<Date> DateArray = new ArrayList<>();

    public static boolean ASC = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //gets the intent from the previous activity
        Intent intent = getIntent();

        //retrieves user
        currentUser = (User) intent.getSerializableExtra("current_User");

        //Finds username and sets it as the Textviewtext on screen
        Username = findViewById(R.id.UsernameText);
        Username.setText(currentUser.getName());

        //Gets all existing registrations
        RegistrationGetter getter = new RegistrationGetter(this);
        getter.getRegistration(this);
    }

    @Override
    //Function called when registrations are found
    public void gotregistrations(ArrayList<Registration> registrations) {

        //Finds all registrations with current user's username.
        AllRegistrationObjects = registrations;

        fillregistrationlist(AllRegistrationObjects);
    }

    @Override
    //Function called when registrations are NOT found
    public void gotregistrationsserror(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void fillregistrationlist(ArrayList<Registration> registrations) {

        for (int i =0; i < registrations.size(); i++) {
            Registration r = registrations.get(i);
            if (currentUser.getName().equals(r.getParticipant())) {
                try {

                    //Formats the string Date in the registrationobject back to a date
                    String DateString = r.getDate() + " 2019";
                    SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
                    java.util.Date Date = formatter.parse(DateString);

                    //Gets the current date with time set to 00:00:00
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    Date currentDate = cal.getTime();

                    //Checks if date of the registration is in the future
                    if (Date.compareTo(currentDate) >= 0) {

                        //Creates the exact Date and Time on which the registration is planned
                        String DateTimeString = r.getDate() + " 2019 " + r.getTime();
                        SimpleDateFormat DateTimeformatter =new SimpleDateFormat("E MMM dd yyyy HH:mm");
                        Date DateTime = DateTimeformatter.parse(DateTimeString);

                        DateArray.add(DateTime);
                        UserRegistrationObjects.add(r);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        //If user has no registrations in the future, A textview is filled with a message
        if (UserRegistrationObjects.size() == 0) {
            NoLessons = findViewById(R.id.NoLessonTxT);
            NoLessons.setText("Je hebt geen actieve inschrijvingen voor groepslessen");
            NoLessons.setVisibility(View.VISIBLE);
        }
        //If user does have registrations in the future, Registrations are sorted by Date and Time
        else {
            SortLessons(UserRegistrationObjects, DateArray);
        }
    }

    //onClick for the "Rooster" button
    public void Overview(View view) {
        Intent intent = new Intent(this, OverviewActivity.class);
        intent.putExtra("current_User",currentUser);
        startActivity(intent);
        finish();
    }

    //onClick for registration items in the Listview with active registrations.
    public void DetailRegistration(View view) {

        Intent intent = new Intent(this, DetailActivity.class);

        //Searches all the user's registrations for a lesson that matches the registration name
        for (int i = 0; i<UserRegistrationObjects.size(); i++) {
            Registration r = UserRegistrationObjects.get(i);
            if(r.getParticipant().equals(currentUser.getName()) && clickedString.equals(r.getName())) {
                intent.putExtra("current_registration", r);
            }
        }
        intent.putExtra("current_User",currentUser);
        intent.putExtra("Activity_origin", "Homepage");
        startActivity(intent);
        finish();
    }

    //onClick for the "Profiel" button
    public void Registrations(View view) {
        Intent intent = new Intent(this, RegistrationsActivity.class);
        intent.putExtra("current_User",currentUser);
        startActivity(intent);
    }

    //function that puts all registrations in a hasmap with all dates
    public void SortLessons(ArrayList<Registration> Registrationlist, ArrayList<Date> Datelist)
    {
        //Hashmap to store the frequency of lessons
        Map<Registration, Date> UnsortLesson = new HashMap<>();

        for (int i =0; i < Registrationlist.size(); i++) {
            Registration l = Registrationlist.get(i);
            Date j = Datelist.get(i);
            UnsortLesson.put(l,j);
        }

        Map<Registration, Date> sortedMapDesc = sortByDate(UnsortLesson, ASC);
        printLessons(sortedMapDesc);
    }

    //function that sorts the created hashmap by date
    private static Map<Registration, Date> sortByDate(Map<Registration, Date> unsortedRegistrations, final boolean order)
    {

        //Creates a linked list with the same values as the hashmap
        List<Map.Entry<Registration, Date>> list = new LinkedList<>(unsortedRegistrations.entrySet());

        //Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<Registration, Date>>()
        {
            public int compare(Map.Entry<Registration, Date> r1,
                               Map.Entry<Registration, Date> r2)
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
        Map<Registration, Date> sortedRegistrations = new LinkedHashMap<>();
        for (Map.Entry<Registration, Date> entry : list)
        {
            sortedRegistrations.put(entry.getKey(), entry.getValue());
        }

        return sortedRegistrations;
    }

    //Function that fills the listview with active registrations sorted by date
    public void printLessons(Map<Registration, Date> map)
    {
        ArrayList<String> OrderedRegistrationdates = new ArrayList<>();
        ArrayList<Registration> OrderedRegistrationnames = new ArrayList<>();

        //Traverses map to find all present entries
        for (Map.Entry<Registration, Date> entry : map.entrySet())
        {
            //Finds Date for every registration and adds it to an arraylist
            String datestrng = entry.getValue().toString();
            OrderedRegistrationdates.add(datestrng);

            //Finds Name for every registration and adds it to an arraylist
            Registration namestrng = entry.getKey();
            OrderedRegistrationnames.add(namestrng);
        }

        //Calls the adapter and fills the listview with the times and names of all registrations
        HomepageRegistrationAdapter adapter = new HomepageRegistrationAdapter(this, R.layout.list_item, OrderedRegistrationnames, OrderedRegistrationdates);
        final ListView listView = findViewById(R.id.RegistrationDayTimeList);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);

        //Sets a listener on ListviewItems that sends to the Detailpage
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Registration clickedRegistration = (Registration) parent.getItemAtPosition(position);

                clickedString = clickedRegistration.getName();
                DetailRegistration(view);
            }
        });
    }
}
