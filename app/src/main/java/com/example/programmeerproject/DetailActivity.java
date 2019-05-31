package com.example.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.Attributes;


public class DetailActivity extends AppCompatActivity implements LessonGetter.Callback,
        RegistrationGetter.Callback {

    User currentUser;
    Lesson currentLesson;
    Lesson l;
    Lesson clickedLesson;
    String Date;
    Registration registration;


    public static boolean ASC = true;
    public static boolean DESC = false;
    Boolean Registered;

    Date currentDate;
    Date lessonDate;
    String ActivityID;
    String clickedDate;
    String currentDateString;
    String lessonDateString;
    String currentLessonTxt;
    String[] Days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    int currentDayIndex;
    int LessonDayIndex;

    ArrayList<Registration> RegistrationObjects;
    ArrayList<Registration> UserRegistrationObjects = new ArrayList<>();

    ArrayList<Lesson> LessonObjects;
    ArrayList<Lesson> SameLessons = new ArrayList<>();
    ArrayList<String> DateStringArray = new ArrayList<>();
    ArrayList<Date> DateArray = new ArrayList<>();


    TextView LessonTitle;
    TextView LessonDescription;
    TextView DateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //gets the intent from the previous activity
        Intent intent = getIntent();

        //Finds if intent is empty or not
        if (intent != null) {

            //Gets the Activity origin, a string that shows from what activity the user navigated
            //to the Detailactivity
            ActivityID = intent.getExtras().getString("Activity_origin");

            if (ActivityID.equals("Homepage")) {

                //Gets information in the intent
                registration = (Registration) intent.getSerializableExtra("current_registration");
                currentUser = (User) intent.getSerializableExtra("current_User");

                //Sets Textview LessonTitle
                LessonTitle = findViewById(R.id.LessonTitle);
                LessonTitle.setText(registration.getName());

                //Gets all lessons
                LessonGetter getter = new LessonGetter(this);

                getter.getLessons(this);

            } else if (ActivityID.equals("Overview")) {

                //retrieves user and lesson and Date
                currentUser = (User) intent.getSerializableExtra("current_User");

                currentLesson = (Lesson) intent.getSerializableExtra("current_Lesson");

                Date = (String) intent.getSerializableExtra("Date");

                //Sets Button register to visible
                Button button = findViewById(R.id.LessonRegister);
                button.setVisibility(View.VISIBLE);

                //Sets lessontitle
                LessonTitle = findViewById(R.id.LessonTitle);
                LessonTitle.setText(currentLesson.getName());

                //Sets lessondescription
                LessonDescription = findViewById(R.id.LessonDescription);
                LessonDescription.setText(currentLesson.getDescription());

                //Sets Date and time Textview
                DateTime = findViewById(R.id.DateTimeTxt);
                String DateTimeString = Date + " " + currentLesson.getTime();
                DateTime.setText(DateTimeString);
                DateTime.setVisibility(View.VISIBLE);

            } else if (ActivityID.equals("Registrations")) {

                //Gets information form the intent
                currentUser = (User) intent.getSerializableExtra("current_User");
                currentLessonTxt = (String) intent.getSerializableExtra("current_Lesson");

                //Sets Textview LessonTitle
                LessonTitle = findViewById(R.id.LessonTitle);
                LessonTitle.setText(currentLessonTxt);

                //Gets all lessons
                LessonGetter getter = new LessonGetter(this);

                getter.getLessons(this);
            } else {
                Toast.makeText(this, "Dit gaat niet goed", Toast.LENGTH_SHORT).show();
            }
        }

        //Gets all registrations
        RegistrationGetter getter = new RegistrationGetter(this);
        getter.getRegistration(this);
    }

    @Override
    //Function that is called if registrations are found
    public void gotregistrations(ArrayList<Registration> registrations) {

        RegistrationObjects = registrations;
        Registered = false;

        //Checks what registrations are on the users name and if the user is already registered for
        //the current lesson
        for (int i = 0; i < RegistrationObjects.size(); i++) {
            Registration r = RegistrationObjects.get(i);
            if (currentUser.getName().equals(r.getParticipant())) {
                UserRegistrationObjects.add(r);
                String DateString = r.getDate();
                String TimeString = r.getTime();
                if (DateString.equals(Date) && TimeString.equals(currentLesson.getTime())) {
                    Registered = true;
                    Toast.makeText(this, "U bent al ingeschreven voor deze les of hebt al een les op hetzelfde moment"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    //Function that is called if registrations are NOT found
    public void gotregistrationsserror(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    //Function that is called if lessons are found
    public void gotlessons(ArrayList<Lesson> lessons) {
        LessonObjects = lessons;

        //Checks all lessons that match the given registration or Lesson name, depending on what the
        //ActivityID is. Adds all lessons that match
        for (int i = 0; i < lessons.size(); i++) {
            l = lessons.get(i);
            String NameTxt = l.getName();

            if (ActivityID.equals("Homepage")) {
                if (NameTxt.equals(registration.getName())) {
                    SameLessons.add(l);
                }
            } else if (ActivityID.equals("Registrations")) {
                if (NameTxt.equals(currentLessonTxt)) {
                    SameLessons.add(l);
                }
            }
        }

        //Gets an instance of current Date
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        currentDate = cal.getTime();

        //Converts date to string formatted as: Day MM dd
        currentDateString = currentDate.toString();
        String[] arrOfcurrentDate = currentDateString.split(" ");
        currentDateString = arrOfcurrentDate[0] + " " + arrOfcurrentDate[1] + " " +
                arrOfcurrentDate[2];

        //Checks what the Index is for the current Day
        if (currentDateString.toLowerCase().contains("mon")) {
            currentDayIndex = 0;
        } else if (currentDateString.toLowerCase().contains("tue")) {
            currentDayIndex = 1;
        } else if (currentDateString.toLowerCase().contains("wed")) {
            currentDayIndex = 2;
        } else if (currentDateString.toLowerCase().contains("thu")) {
            currentDayIndex = 3;
        } else if (currentDateString.toLowerCase().contains("fri")) {
            currentDayIndex = 4;
        } else if (currentDateString.toLowerCase().contains("sat")) {
            currentDayIndex = 5;
        } else if (currentDateString.toLowerCase().contains("sun")) {
            currentDayIndex = 6;
        }

        for (int i = 0; i < SameLessons.size(); i++) {

            cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            l = SameLessons.get(i);

            //Checks what the Dayindex for the lesson is
            if (l.getDay().toLowerCase().contains("mon")) {
                LessonDayIndex = 0;
            } else if (l.getDay().toLowerCase().contains("tue")) {
                LessonDayIndex = 1;
            } else if (l.getDay().toLowerCase().contains("wed")) {
                LessonDayIndex = 2;
            } else if (l.getDay().toLowerCase().contains("thu")) {
                LessonDayIndex = 3;
            } else if (l.getDay().toLowerCase().contains("fri")) {
                LessonDayIndex = 4;
            } else if (l.getDay().toLowerCase().contains("sat")) {
                LessonDayIndex = 5;
            } else if (l.getDay().toLowerCase().contains("sun")) {
                LessonDayIndex = 6;
            }

            Integer DateTimeIndex = 0;

            //Handles a few exceptions
            //if currentdayIndex equals 6 it's sunday and we want to go back to monday
            if (currentDayIndex == 6) {
                DateTimeIndex = LessonDayIndex;
            } else {
                DateTimeIndex = LessonDayIndex - currentDayIndex;

                //If DateTimeIndex is smaller than 0 the lessonday is before the current day and
                //we have to move forward a week
                if (DateTimeIndex < 0) {
                    DateTimeIndex = DateTimeIndex + 7;
                }

                //Creates a integer for the lessontime
                String[] HourStr = l.getTime().split(":");
                Integer HourInt = Integer.parseInt(HourStr[0]);

                //Gets an instance of date using the dateindex and the Hour integer
                cal.add(Calendar.DATE, DateTimeIndex);
                cal.set(Calendar.HOUR_OF_DAY, HourInt);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);

                //Gets the date and time for the lesson
                lessonDate = cal.getTime();
                DateArray.add(lessonDate);
            }
        }

        //Sorts lessons by date
        SortLessons(SameLessons, DateArray);
    }

    @Override
    //Function that is called if lessons are NOT found
    public void gotlessonserror (String message){
        Toast.makeText(this, "No lessons available for this day", Toast.LENGTH_SHORT).show();
    }

    //onClick for Items and register button to register for a lesson
    public void Lessonregistration (View view){

        //Checks if Boolean Registered is true or not.
        if (Registered.equals(true)) {
            Toast.makeText(this, "U bent al ingeschreven voor deze les"
                    , Toast.LENGTH_SHORT).show();
        } else if (Registered.equals(false)) {

            //Depending on AcitivityID registration is handled differently
            if (ActivityID.equals("Overview")) {

                //Posts new registration
                Registration r = new Registration(currentLesson.getName(), Date,
                        currentLesson.getTime(), currentLesson.getLocation(), currentUser.getName());

                RegistrationSetter post = new RegistrationSetter(DetailActivity.this);

                post.setRegistration(currentLesson.getName(), currentUser.getName(), Date,
                        currentLesson.getTime(), currentLesson.getLocation());

            } else if (ActivityID.equals("Homepage")) {

                System.out.println(clickedDate);
                System.out.println(currentUser.getName());
                System.out.println(clickedLesson.getName());
                System.out.println(clickedLesson.getTime());
                System.out.println(clickedLesson.getLocation());

                //Posts new registration
                Registration r = new Registration(clickedLesson.getName(), clickedDate,
                        clickedLesson.getTime(), clickedLesson.getLocation(), currentUser.getName());

                RegistrationSetter post = new RegistrationSetter(DetailActivity.this);


                post.setRegistration(clickedLesson.getName(), currentUser.getName(),
                        clickedDate, clickedLesson.getTime(), clickedLesson.getLocation());
            }

            //pushes the user to the next activity
            Intent intent = new Intent(DetailActivity.this, HomepageActivity
                    .class);
            intent.putExtra("current_User", currentUser);
            startActivity(intent);
            finish();
        }

    }

    //Function that creates a hashmap with lessons coupled with dates
    public void SortLessons(ArrayList<Lesson> Lessonlist, ArrayList<Date> Datelist)
    {
        // hashmap to store the frequency of lessons
        Map<Lesson, Date> UnsortLesson = new HashMap<>();

        for (int i =0; i < Lessonlist.size(); i++) {
            Lesson l = Lessonlist.get(i);
            Date j = Datelist.get(i);
            UnsortLesson.put(l,j);
        }

        Map<Lesson, Date> sortedMapDesc = sortByDate(UnsortLesson, ASC);
        printLessons(sortedMapDesc);
    }

    //Function that sorts lessons by date
    private static Map<Lesson, Date> sortByDate(Map<Lesson, Date> unsortedRegistrations, final boolean order)
    {

        //Creates a linked list with the same values as the hashmap
        List<Map.Entry<Lesson, Date>> list = new LinkedList<>(unsortedRegistrations.entrySet());

        //Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<Lesson, Date>>()
        {
            public int compare(Map.Entry<Lesson, Date> r1,
                               Map.Entry<Lesson, Date> r2)
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
        Map<Lesson, Date> sortedRegistrations = new LinkedHashMap<>();
        for (Map.Entry<Lesson, Date> entry : list)
        {
            sortedRegistrations.put(entry.getKey(), entry.getValue());
        }

        return sortedRegistrations;
    }

    //Function that sets the ordered objects in the listview
    public void printLessons(Map<Lesson, Date> map)
    {
        ArrayList<String> OrderedDates = new ArrayList<>();
        final ArrayList<Lesson> OrderedLessons = new ArrayList<>();

        //Fills empty arralist with Dates
        for (Map.Entry<Lesson, Date> entry : map.entrySet())
        {
            String strng = entry.getValue().toString();
            OrderedDates.add(strng);
            Lesson lesson = entry.getKey();
            OrderedLessons.add(lesson);
        }

        //Finds and sets lessondescription.
        LessonDescription = findViewById(R.id.LessonDescription);
        LessonDescription.setText(SameLessons.get(0).getDescription());

        //Sets the samelesson adapter to fill the listview
        SameLessonAdapter adapter = new SameLessonAdapter(this, R.layout.list_item,
                OrderedDates);
        ListView listView = findViewById(R.id.DayTimeList);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);

        //Sets listview onClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                //Gets the Date of the clicked item and converts it to a separate day and time
                String clickedLessonDatestring = (String) parent.getItemAtPosition(position);
                clickedLesson = OrderedLessons.get(position);

                String [] clickedItem = clickedLessonDatestring.split(" ");
                clickedDate = clickedItem[0] + " " + clickedItem[1] + " " + clickedItem[2];

                String [] ClickedTimeArray = clickedItem[3].split(":");
                String ClickedTime = ClickedTimeArray[0] + ":" +ClickedTimeArray[1];

                Registered = false;


                //Checks if Date and Time of the clicked item matches with an existing registration
                for (int i = 0; i < UserRegistrationObjects.size(); i++) {
                    Registration r = UserRegistrationObjects.get(i);
                    String DateString = r.getDate();
                    String TimeString = r.getTime();

                    if (DateString.equals(clickedDate) && TimeString.equals(ClickedTime)) {
                        Registered = true;
                    }
                }
                Lessonregistration(view);
            }
        });
    }
}
