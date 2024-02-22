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
                    System.out.println("Loading dummy data...");
                    break;
                case 7:
                    System.out.println("Goodbye...");
                    loop = false;
                    break;
            }
        }
    }

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
}
