import java.sql.*;
import java.io.*;
import java.util.Scanner;
public class Group5HotelDatabase {
    public static void main(String[] args) {
        Connection conn = null;
        String url, driver, userName, password;
        Scanner in = new Scanner(System.in);
        System.out.print("Please enter the url of the server: ");
        url = in.nextLine();
        System.out.print("Please enter your driver: ");
        driver = in.nextLine();
        System.out.print("Please enter your username: ");
        userName = in.nextLine();
        System.out.print("Please enter your password: ");
        password = in.nextLine();
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

    public static void mainMenu(Scanner in, Connection conn) {
        int input;
        System.out.print("\n*********************************");
        System.out.print("\n* Group 5 Hotel Database System *");
        System.out.println("\n*********************************");
        boolean loop = true;
        while(loop) {
            while(true) {
                System.out.println("\n1)Query\n2)Insert\n3)Update\n4)Delete\n5)Load Dummy Data\n6)Quit");
                System.out.print("Please select a number: ");
                input = in.nextInt();
                if (input < 0 || input > 6) System.out.println("Please enter a number 1-5.");
                else break;
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
                    System.out.println("Loading dummy data...");
                    break;
                case 6:
                    System.out.println("Goodbye...");
                    loop = false;
                    break;
            }
        }
    }
}
