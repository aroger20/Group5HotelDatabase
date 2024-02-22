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
                    System.out.println("Creating database...");
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
}
