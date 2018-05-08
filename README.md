#### PING Interns Repository

# Hotel and Restaurant Bot

## Description
Hotel and Restaurant Bot is a search for a hotel spot and a restaurant search. If a user enters a city or place name, the bot will provide
a hotel spot or restaurant located near the city or place of user input. If the user input can not be found by the bot, 
then the bot will ask for input again while sending notification to the user to enter a city name or a more general place.

## Getting Started
This project use the [Gradle](https://spring.io/guides/gs/gradle/) build system.

First download the project by cloning this repository or downloading an archived snapshot. (See the options on the right hand side.)

You can open the project in your favorite IDE such as IntelliJ, NetBeans, STS, Eclipse, etc. In IntelliJ, go to ```File > Open > Select one of the sample directories that you downloaded from this repository > Select the build.gradle file > Ok.``` This should load the project properly.

This project uses [JpaRepository](http://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html) feature from spring framework and [Flyway](https://flywaydb.org) to easily migrate the database.

## Run in Localhost
To Run this project in your localhost, make sure you have Java and MySQL installed.
###MySQL
You can download MySQL [here](http://dev.mysql.com/downloads/). Take a look at ```application.properties```, and you'll see ```spring.datasource.url =jdbc:mysql://localhost/chatbot```. This means the project will migrate the database to your local database named **pariwisata_bot**.

First, start your MySQL and create your database by opening ```http://localhost/phpmyadmin``` in your browser. Select ```Databases``` then create a database named **chatbot**.Import the database from this source with name "chatbot.sql".After that Run the program, it'll automatically migrate the databases created in project to your localhost. 

**DO NOT** change anything in your movie database (phpmyadmin).

###Run & Send Request 
Once the project loaded properly in your IDE, right click on ```Chatbot.java``` then select ```Run```. It should build and run the project. 

If you look at ```application.properties``` file, you could see ```server.port=8080```, so this means that the program use server in port 8080.

You can use [Postman](https://www.getpostman.com) to help you construct request quickly. In Postman, set URL to ```http://localhost:8080/kaskusWebhook``` with method type **POST**. As for the body, construct JSON needed for the request. API can be found in [Kaskus Chat API](http://slate.obrol.id/#introduction).
