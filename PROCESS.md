# Day 1
Still working on the exact architecture of the app. Got Registration working. New users can be created, each with a username, password and a, for now empty, RecentLessonList.

# Day 2
Struggling to get the Login working. Wanted to just retrieve data for user. For now retrieving ALL users and looping through all of them until I find a username that matches.
After that checking if entered password equals registered password. if so, user can log in. Pretty sure this is not the most efficient.

# Day 3
Designing exact architecture for the overview. want to work with a recursive roster but also want to be able to register for lessons further than one week in the future. For now just looping
through the same 7 days without a date. Works for now, Definetely needs work to perfect it.

# Day 4
Refining Overview architecture, app is able to flip through roste AND go forward in date. This makes it possible to recursively use the same lessons each week but yet distinguish between
lessons in different weeks. Also it is no longer possible to go back further than today, mostly because lessons in the past hold no value.

# Day 5
Setting up registration for Lessons. Trying to figure out what architecture will work best. For now it has to hava a username, lesson, time and a date.

# Day 6
Finishing up registration. The list with participants is working, yet it is sloppy to keep registrations that are in the past, so would like to be able to update list everyday and remove the previous'
day registrations. Also working on updating users' RecentLessonlist using a PUT stringrequest. Not yet functional. App doesn't crash but also doesn't update the list either. Figured out how to retrieve only the data for current user
instead of entire list at: http://ide50-mielan29.legacy.cs50.io:8080/users. Might change Log in, but only if time is left.

