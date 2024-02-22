/**
 * This program uses a CLI to access a SQL database.
 * The database stores information about a hotel.
 *
 * @author Alexander Rogers, Jonathan Layden, Justin Salas, Kaylee Calvert
 */

import java.sql.*;
import java.io.*;
import java.util.Scanner;
public class Group5HotelDatabase {

    /**
     * The main method gets a server url, driver, username, and password from the user.
     * It then connects to the SQL server and runs a CLI to access it.
     */
    public static void main(String[] args) {
    	
    	System.out.println("Testing.");
        Connection conn = null;
        String url, driver, userName, password;
        Scanner in = new Scanner(System.in);
        if (args.length == 4) {
            url = args[0];
            userName = args[1];
            password = args[2];
            driver = args[3];
        }
        else {
            System.out.print("Please enter the url of the server: ");
            url = in.nextLine();
            System.out.print("Please enter your driver: ");
            driver = in.nextLine();
            System.out.print("Please enter your username: ");
            userName = in.nextLine();
            System.out.print("Please enter your password: ");
            password = in.nextLine();
        }
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            mainMenu(in, conn);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) conn.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method prints the main menu for the SQL server.
     * It has options to query, insert, update, delete, and load dummy data.
     * @param in Scanner
     * @param conn SQL connection
     */
    public static void mainMenu(Scanner in, Connection conn) {
        int input = 0;
        System.out.print("\n*********************************");
        System.out.print("\n* Group 5 Hotel Database System *");
        System.out.println("\n*********************************");
        boolean loop = true;
        while(loop) {
            while(true) {
                System.out.println("\n1)Query\n2)Insert\n3)Update\n4)Delete\n5)Create Database\n6)Load Dummy Data\n7)Quit");
                System.out.print("Please select a number: ");
                try {
                    input = in.nextInt();
                    if (input < 1 || input > 7) System.out.println("Error: Please enter a number 1-7.");
                    else break;
                }
                catch (Exception e) {
                    System.out.println("Error: Please enter a number.");
                    in.nextLine();
                }
            }
            switch(input) {
                case 1:
                    System.out.println("Loading query...");
                    break;
                case 2:
                    System.out.println("Loading insert...");
                    break;
                case 3:
                    System.out.println("Loading update...");
                    break;
                case 4:
                    System.out.println("Loading delete...");
                    break;
                case 5:
                    System.out.print("Creating database...");
                    createDatabase(conn);
                    System.out.println("Done!");
                    break;
                case 6:
                    System.out.print("Loading dummy data...");
                    insertDummyData(conn);
                    System.out.println("Done!");
                    break;
                case 7:
                    System.out.println("Goodbye...");
                    loop = false;
                    break;
            }
        }
    }

    /**
     * Creates the hotel_database on the sql server as well as all the tables.
     * @param conn Connection
     */
    public static void createDatabase(Connection conn) {
        try {
            //database creation
            Statement stmt = conn.createStatement();
            String create = "CREATE DATABASE hotel_database";
            stmt.executeUpdate(create);
            create = "USE hotel_database";
            stmt.executeUpdate(create);

            //table creation
            String createTable = "CREATE TABLE hotel (" +
                    "hotel_id INT NOT NULL," +
                    "no_of_rooms INT," +
                    "name VARCHAR(50) NOT NULL," +
                    "phone_no VARCHAR(25) NOT NULL," +
                    "address VARCHAR(125) NOT NULL," +
                    "email VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY (hotel_id))";
            stmt.executeUpdate(createTable);

            createTable = "CREATE TABLE employee (" +
                    "employee_id INT NOT NULL," +
                    "hotel_id INT NOT NULL," +
                    "name VARCHAR(50) NOT NULL," +
                    "job_name varchar(50) NOT NULL," +
                    "salary FLOAT NOT NULL," +
                    "phone_no VARCHAR(25)," +
                    "SSN INT NOT NULL," +
                    "PRIMARY KEY (employee_id)," +
                    "FOREIGN KEY (hotel_id) REFERENCES hotel(hotel_id)," +
                    "UNIQUE (SSN))";
            stmt.executeUpdate(createTable);

            createTable = "CREATE TABLE job_description (" +
                    "employee_id INT NOT NULL," +
                    "job_description VARCHAR(125) NOT NULL," +
                    "FOREIGN KEY (employee_id) REFERENCES employee(employee_id) ON DELETE CASCADE)";
            stmt.executeUpdate(createTable);

            createTable = "CREATE TABLE customer (" +
                    "guest_id INT NOT NULL," +
                    "name VARCHAR(50) NOT NULL," +
                    "phone_no VARCHAR(25)," +
                    "card_number BIGINT NOT NULL," +
                    "email VARCHAR(50)," +
                    "PRIMARY KEY (guest_id))";
            stmt.executeUpdate(createTable);

            createTable = "CREATE TABLE reservation (" +
                    "reservation_id INT NOT NULL," +
                    "guest_id INT NOT NULL," +
                    "start_date DATE NOT NULL," +
                    "end_date DATE NOT NULL," +
                    "no_of_guests INT," +
                    "room_no INT NOT NULL," +
                    "PRIMARY KEY (reservation_id)," +
                    "FOREIGN KEY (guest_id) REFERENCES customer(guest_id) ON DELETE CASCADE)";
            stmt.executeUpdate(createTable);

            createTable = "CREATE TABLE special_requests (" +
                    "reservation_id INT NOT NULL," +
                    "special_requests VARCHAR(125) NOT NULL," +
                    "FOREIGN KEY (reservation_id) REFERENCES reservation(reservation_id) ON DELETE CASCADE)";
            stmt.executeUpdate(createTable);

            createTable = "CREATE TABLE can_be (" +
                    "employee_id INT NOT NULL," +
                    "guest_id INT NOT NULL," +
                    "PRIMARY KEY (employee_id, guest_id)," +
                    "FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE," +
                    "FOREIGN KEY (guest_id) REFERENCES customer (guest_id) ON DELETE CASCADE)";
            stmt.executeUpdate(createTable);

            createTable = "CREATE TABLE has (" +
                    "hotel_id INT NOT NULL," +
                    "reservation_id INT NOT NULL," +
                    "PRIMARY KEY (hotel_id, reservation_id)," +
                    "FOREIGN KEY (hotel_id) REFERENCES hotel (hotel_id) ON DELETE CASCADE," +
                    "FOREIGN KEY (reservation_id) REFERENCES reservation (reservation_id) ON DELETE CASCADE)";
            stmt.executeUpdate(createTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Add dummy data to the hotel_database
     * @param conn Connection
     */
    public static void insertDummyData(Connection conn) {
        try{
            Statement stmt = conn.createStatement();

            //Hotel data
            String insert = "INSERT INTO hotel \n" +
                    "VALUES (1, 150, 'Tranquil Inn & Suites', '123-456-7890', '1624 West Street, Hays, KS 81452', 'TranquilInn@gmail.com')";
            stmt.executeUpdate(insert);

            //Employee data
            insert = "INSERT INTO employee\n" +
                    "VALUES (1, 1, 'Alex Rogers', 'General Manager', 95000, '321-456-7891', '124545674'),\n" +
                    "(2, 1, 'Justin Salas', 'Head of Security', 72000, '789-123-4561', '784532647'),\n" +
                    "(3, 1, 'Jonathan Layden', 'House Keeping Manager', 150, '405-457-4848', '784532612'),\n" +
                    "(4, 1, 'Kaylee Calvert', 'Front Desk Manager', 68000, '154-124-7894', '784222612')";
            stmt.executeUpdate(insert);

            //Customer data
            insert = "INSERT INTO customer\n" +
                    "VALUES (1, 'Luke Skywalker', '000-0123', 0120301120121212, 'lukesky@gmail.com'),\n" +
                    "(2, 'Darth Maul', '000-4321', 0974627148212371, 'damauler@gmail.com'),\n" +
                    "(3, 'Kylo Ren', '000-6541', 0832573847212366, 'ren_kylo@gmail.com'),\n" +
                    "(4, 'Jabba Hutt', '000-7243', 1858274638156172, 'jhutt@gmail.com')";
            stmt.executeUpdate(insert);

            //Job description data
            insert = "INSERT INTO job_description\n" +
                    "VALUES (1, 'Manage entire hotel.'),\n" +
                    "(1, 'Manage all employees.'),\n" +
                    "(2, 'Take down bad guys.'),\n" +
                    "(2, 'Keep guests safe.'),\n" +
                    "(3, 'Keep rooms clean.'),\n" +
                    "(3, 'Keep rooms fully stocked.'),\n" +
                    "(4, 'Receive incoming guests.'),\n" +
                    "(4, 'Manage reservations.')";
            stmt.executeUpdate(insert);

            //Reservation data
            insert = "INSERT INTO reservation\n" +
                    "VALUES (1, 1, '2024-02-14', '2024-02-18', 2, 33),\n" +
                    "(2, 1, '2025-10-10', '2025-10-12', 1, 24)";
            stmt.executeUpdate(insert);

            //Special request data
            insert = "INSERT INTO special_requests\n" +
                    "VALUES (1, 'Would like a picture of John Cena on the nightstand.'),\n" +
                    "(2, 'Wedding anniversary: Would like roses all throughout the room.')";
            stmt.executeUpdate(insert);

            //Can be data
            insert = "INSERT INTO can_be\n" +
                    "VALUES (1, 1)";
            stmt.executeUpdate(insert);

            //Has data
            insert = "INSERT INTO has\n" +
                    "VALUES (1, 1),\n" +
                    "(1, 2)";
            stmt.executeUpdate(insert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
