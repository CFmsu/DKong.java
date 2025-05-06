this project is a recreation of the original Donkey Kong from 1981. It was made for CSCI 282 (Java programming 2) and has been built solely within java, utilizing JFrame for creating the window and rendering sprites onto that window.

Almost all of the logic in this game is dependant on collision between invisible hitboxes. they are all hidden, but you can view them all by using the drawHitbox() method within any class that extends "Entity".
This excludes the "Platforms", which represent the location that Rails are, but aren't explicitly tied to the rails because they were created separately. using drawRectangle() works for it.

The original plan for this project was to use libGDX, but too many issues had arisen with using the library for me to have finished this project in time for submission.

I have utilized a series of YouTube tutorials by Kaarin Gaming for platformers to assist in creating the game, as I had no prior experience to creating a game in java:
https://www.youtube.com/watch?v=6_N8QZ47toY&list=PL4rzdwizLaxYmltJQRjq18a9gsSyEQQ-0&index=1&ab_channel=KaarinGaming

The tutorial covers the basics of creating a video game in java. It is not a tutorial for Donkey Kong in particular, but just platformers in general, which is the genre of games Donkey Kong falls within.
It was very helpful in getting me started on creating my game, but I stopped following it after the 10th video in order to make the game into Donkey Kong and not the game that he was making.

The spritesheet used for this project was sourced from the Spriter's Resource website: https://www.spriters-resource.com/fullview/32590/
The sprite for the rails were created by me taking a screenshot of them from the original Donkey Kong game, which is probably why it's a little fuzzy looking.

Nintendo owns all copyrights to Donkey Kong and Super Mario, and I take no ownership of their IP's. This is only a project that I've completed for a college course.
