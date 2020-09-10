# Assignment 2 RPC Task - System Integration

## Group #IkkeForLangt

<br>
<br>

# RPC Assignment

Banks collect data about their customers, services, and transactions. The data comes in
various formats from different sources and platforms, such as mobile and web apps, ATP
machines, shops, but after some processing, it is stored permanently in SQL databases.
Your task is to create

- a server application that can open, read, and process banking data in text format and
  store it in a database,
- a client application, which provides the source data in files and receives a report
  about the current size of the database.
  The applications should illustrate the use of RPC.

You can choose:

    • the business case (what kind of data is collected)
    • the format of the collected data: txt, csv, html, xml, or json
    • the type of the SQL database

You can work in groups.
The solution of this task brings five credits to each active participant.

<br>
<br>

# Our project

- We chose to use the same business case as in the class, with bank customers with the following data (ACCNUM, NAME, AMOUNT)

- For the format of the collected data, we chose to work with csv

- for the SQL database, we used H2 Embedded

<br>
<br>

# How to

- open project and go to [/rmi](https://github.com/grem848/Soft2020Fall-SI/tree/master/P5/src/main/java/dk/dd/rmi/) and open both [dbserver](https://github.com/grem848/Soft2020Fall-SI/tree/master/P5/src/main/java/dk/dd/rmi/dbserver) and [dbclient](https://github.com/grem848/Soft2020Fall-SI/tree/master/P5/src/main/java/dk/dd/rmi/dbclient)
- run the following programs in the following order

  1. run [RMIRegistry.java](https://github.com/grem848/Soft2020Fall-SI/blob/master/P5/src/main/java/dk/dd/rmi/dbserver/RMIRegistry.java) to build the registry
  2. run [DbServerApplication.java](https://github.com/grem848/Soft2020Fall-SI/blob/master/P5/src/main/java/dk/dd/rmi/dbserver/DbServerApplication.java) start the server application
  3. run [RMIClientDB.java](https://github.com/grem848/Soft2020Fall-SI/blob/master/P5/src/main/java/dk/dd/rmi/dbclient/RMIClientDB.java) to test connecting via a client and getting all millionaires in the database

- open [localhost:8090](http://localhost:8090) and try clicking 'Send' without picking a file first, to see the current size of the database

- open [localhost:8090](http://localhost:8090) again, this time picking the file [bankData.csv](https://github.com/grem848/Soft2020Fall-SI/blob/master/P5/files/bankData.csv) which is located in the folder [files](https://github.com/grem848/Soft2020Fall-SI/blob/master/P5/files) in the project (you can also do it with your own csv, but it requires the same structure to work)

- click 'Send' to upload the csv file to the server
  - if the upload was succesful you should see a success message and the current size of the database, now including the 100 records you added via the csv
