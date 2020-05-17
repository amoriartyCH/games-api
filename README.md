# games-api
A technical test for interview

# Pre-requisites
Java version version 1.8
MongoDB running (See below for setting up)

# Setting up MongoDB
To add data to the mongoDB you will need to get mongoDB running using command "mongod" in your terminal window.
Then you can run the application and it will connect to mongoDB and do the rest for you.

# To Run this project
Once mongoDB is running, simply clone the repository and run the application in Intellij.

This application accepts "Games" in the form of Json as seen below:
# JSON Game Example
{
	"title": "Gears of War: 2",
        "description": "New game, so new, number 2",
        "by": "Activision",
        "platform": ["PS4", "XboxOne", "PC"],
        "age_rating": "18",
        "likes": 205,
        "comments": [{
            "user": "Jake",
            "message": "new gears is good",
            "dateCreated": "2017-05-30",
            "likes": 5
        }, {
            "user": "shooterLooter",
            "message": "I didnt like this one",
            "dateCreated": "2016-04-02",
            "likes": 15
        }]
}

# The following endpoints exist:

(POST) http://localhost:8080/games - allows you to submit a game in the form of JSON (as seen above)

(GET) http://localhost:8080/games - returns all games in the database

(GET) http://localhost:8080/games/{gameTitle} - allows you to view an individual game by providing its title (e.g. "Uncharted 4" in POSTMAN or "Uncharted%204" in chrome)

(DELETE) http://localhost:8080/games/{gameTitle} - allows you to delete a game by providing the game title of the game you wish to delete

(GET) http://localhost:8080/games/report - allows you to view the highest liked game and user with most comments
