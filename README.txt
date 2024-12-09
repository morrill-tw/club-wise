This Application was built using Java Swing and the JDBC SQL connector to connect to a MySQL database. It offers a user interface
for creating, reading, deleting, and updating data related to clubs. This includes member data, purchases, social media accounts, and more!

# Installation/Setup Instructions

First, nsure that you have downloaded the zip file that includes the src folder and the clubwise jar file. Unzip this file.

Next, you must have the MySQL Workbench downloaded on your machine. Once downloaded, open up the workbench, and connect to a MySQL server running on localhost with port 3306. Load the dump file here, and execute it to create the database. Ensure that this connection remains open (do not close out of the workbench).

That's it for setup!

# Running the Application

To run the application, follow these steps:

1. Open up a terminal.
2. cd to the unzipped folder containing all of the project files.
3. Execute the following command: java -jar clubwise.jar

After following these four steps, you should be able to interact with the application. First it will prompt you for your MySQL username and password, and then, if it is able to successfully connect to the database, you will be able to interact with it through the provided menu. Tada!
