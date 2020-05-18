# games-api
A technical test for interview

# Pre-requisites
* Java version version 1.8
* MongoDB running (See below for setting up)

# Setting up MongoDB
To add data to the mongoDB you will need to get mongoDB running using command "mongod" in your terminal window.
Then you can run the application and it will connect to mongoDB and do the rest for you.

# To Run this project
- for the logger environment variable, execute 'export LOG_LEVEL="VAR"' where VAR is one of the following:
	-DEBUG 
	-ERROR 
	-FATAL 
	-INFO 
	-OFF 
	-TRACE 
	-WARN
If no LOG_LEVEL variable is provided, a default level of INFO will be set automatically

- run 'mvn clean install'

- execute the jar using 'java -jar target/<jarname>'
	
# JSON Game Example
This application accepts "Games" in the form of Json as seen below:

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

Included at the base directory of this repo is a text document "Sample data to fill database". This document contains 5 JSON bodies, each of which is a full game which follows the example JSON structure above.

# The following endpoints exist:

(POST) http://localhost:8080/games - allows you to submit a game in the form of JSON (as seen above)

(GET) http://localhost:8080/games - returns all games in the database

(GET) http://localhost:8080/games/{gameTitle} - allows you to view an individual game by providing its title (e.g. "Uncharted 4" in POSTMAN or "Uncharted%204" in chrome)

(DELETE) http://localhost:8080/games/{gameTitle} - allows you to delete a game by providing the game title of the game you wish to delete

(GET) http://localhost:8080/games/report - allows you to view the highest liked game and user with most comments
