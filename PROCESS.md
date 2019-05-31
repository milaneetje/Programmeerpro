# Day 1
Started working on an idea for a groupfitness app. At the USC I noticed they don't have a mobile app on which users can easily register for an app. After a meeting with my supervisor(Martijn) we agreed to move onto design given that some things would be changed and specified more accurately

# Day 2
After changing the idea file I started working on the design. Struggling to find APIs that give me the information that I want/need. After a second meeting with my supervisor, we agreed that I would be able to use the Rester API from the last assignment of the programming course. This involves multiple lists, one for users, one for lessons, and one for registrations

# Day 3
After changing some minor things to the desing I started working on creating the separate screens I would need. My reasoning is that if I build the screens first I can later work on connecting them and making sure that everything works.

# Day 4
Today I ran into some problems with the horizontalscrollview in the overview. Mostly the problem is that I can only insert items horizontally and not vertically. I played around with multiple scrollviews, that didn't scroll at the same time. This made the activity very unorganized. I finally settled on putting a gridlayout inside the scrollview so I can create rows and columns and I can find a cell by knowing what row and column it is in.

# Day 5
Finished last few screens. No real problems. Started setting up Rester URLs and determining which attributes the different lists needed.

# Day 6
Started working on register activity. Got the activity to register a new user with an id, name, password and a recentLessonList for recent subscriptions. Tried to get Login working but not able to get it to recognise password confidently.

# Day 7
Still struggling to get the Login working. Wanted to just retrieve data for a single user who's name matched with the input from the activity. For now retrieving ALL users and looping through all of them until I find a username that matches.
After that checking if entered password equals registered password. if so, user can log in. Pretty sure this is not the most efficient.

# Day 8
Designing exact architecture for the overview. want to work with a recursive roster but also want to be able to register for lessons further than one week in the future. For now just looping
through the same 7 days without a date. Works for now, Definetely needs work to perfect it.

# Day 9
Refining Overview architecture, app is able to flip through roste AND go forward in date. This makes it possible to recursively use the same lessons each week but yet distinguish between lessons in different weeks. Also it is no longer possible to go back further than today, mostly because lessons in the past hold no value.

# Day 10
Setting up registration for Lessons. Trying to figure out what architecture will work best. For now it has to hava a username, lesson, time and a date.

# Day 11
Finishing up registration. The list with participants is working, yet it is sloppy to keep registrations that are in the past, so would like to be able to update list everyday and remove the previous'
day registrations. Also working on updating users' RecentLessonlist using a PUT stringrequest. Not yet functional. App doesn't crash but also doesn't update the list either. Figured out how to retrieve only the data for current user
instead of entire list at: http://ide50-mielan29.legacy.cs50.io:8080/users. Might change Log in, but only if time is left.

# Day 12 
Not able to remove registrations from the past, but managed to only select registrations that have today's date or higher. Also want to make a few extra connections to make the app more intuitive. Through the overview I want a user to be able to register for a specific lesson on a specific time and date. I changed the favorite lessons at the homepage to active registrations, which seemed more intuitive. Not really sure how to implement that but I do think this is nicer. Also, if a lesson is tapped on the homepage, I want to show all lesson a week forward and be able to register for other lessons on tap.

# Day 13
Not able to update the URL with a favoritelessonlist. To bypass the issue the registrationactivity will be a userprofile where the 3 most registered for lessons will be shown together with the total number of registrations. Here I also want to be able to navigate to a lesson page on tap where a user can register for a lesson a week in advance. 

# Day 14
Homepage activity is working. Lessons are shown in order of registration and not ordered by date. Looks sloppy. Working on a fix but first want to make sure all the functionality is working.

# Day 15
Noticed some faulty registrations. Either time didn't match with the time of the selected lesson or wasn't able to register for a lesson. Not sure what the problem is. Detail activity now shows slightly different layouts depending on whether the app navigated there from the overview or from the homepage. Also on Detail activity Lessons are not ordered by date but by day in the week. So monday comes before friday whether that is an earlier date or not. Just like Homepage it looks sloppy. Pretty sure if I fix one, I can use the same solution to the other. 

# Day 16
Registrations seem to be working. As far as I can tell no more double registrations or registrations on wrong times. Not really sure what was wrong or how I fixed it. Working on the userpage. I am able to figure out how many times a user has registered in total but not able to create a list of three favorite lessons ordered by number of registrations. 

# Day 17
Got the userpage up and running. Able to sort lessons now by using a hashmap and a linked list. Feels like I'm almost done but little things keep popping up. Now mainly fixing bugs, working on ordering the lessons in homepage and detail activity. Still have to make sure all activities are able to rotate and not lose info. Kind of leaving that to the last moment.

# Day 18
Registrations are NOT working. Found some more faulty registrations. Seems to be especially clear with classes that have multiple lessons on the same day. Figured out, the app just takes the first lesson it sees that has the same name and pushes that to the next activity. Not really sure how to elegantly solve this without using a MASSIVE if statement, covering all the buttons or rewriting the entire activity.

# Day 19
Due to the feeling of time pressure I have decided to solve the registration issue by writing a massive if statement to cover all the buttons. Registrations work right now, but code is a lot bigger and messier. Also have to work on my comments in the lines of code. Leaving that for last together with the rotation.

# Day 20




