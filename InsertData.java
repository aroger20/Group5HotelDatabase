import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Scanner;

public class InsertData {


    public static void insertHotel(Scanner in, Connection conn) {

        try {

            System.out.println("\nEnter details for the new hotel:");

            System.out.print("Enter hotel ID: ");
            int hotelId = in.nextInt();
            in.nextLine();

            System.out.print("Enter number of rooms: ");
            int numberOfRooms = in.nextInt();
            in.nextLine();

            System.out.print("Enter hotel name: ");
            String hotelName = in.nextLine();

            System.out.print("Enter phone number: ");
            String phoneNumber = in.nextLine();

            System.out.print("Enter address: ");
            String address = in.nextLine();

            System.out.print("Enter email: ");
            String email = in.nextLine();

            String insertHotelQuery = "INSERT INTO hotel (hotel_id, no_of_rooms, name, phone_no, address, email) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertHotelQuery)) {

                preparedStatement.setInt(1, hotelId);

                preparedStatement.setInt(2, numberOfRooms);

                preparedStatement.setString(3, hotelName);

                preparedStatement.setString(4, phoneNumber);

                preparedStatement.setString(5, address);

                preparedStatement.setString(6, email);

                int executed = preparedStatement.executeUpdate();

                if (executed > 0) {

                    System.out.println("Record inserted successfully!");

                } else {

                    System.out.println("Failed to insert record.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertEmployee(Scanner in, Connection conn) {

        try {
            System.out.println("\nEnter details for the new employee:");
            System.out.print("Enter employee ID: ");
            int employeeId = in.nextInt();
            in.nextLine();

            System.out.print("Enter hotel ID: ");
            int hotelId = in.nextInt();
            in.nextLine();

            System.out.print("Enter employee name: ");
            String employeeName = in.nextLine();

            System.out.print("Enter job name: ");
            String jobName = in.nextLine();

            System.out.print("Enter salary: ");
            double salary = in.nextDouble();
            in.nextLine();

            System.out.print("Enter phone number: ");
            String phoneNumber = in.nextLine();

            System.out.print("Enter SSN: ");
            int ssn = in.nextInt();
            in.nextLine();

            String insertEmployeeQuery = "INSERT INTO employee (employee_id, hotel_id, name, job_name, salary, phone_no, SSN) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertEmployeeQuery)) {
                preparedStatement.setInt(1, employeeId);
                preparedStatement.setInt(2, hotelId);
                preparedStatement.setString(3, employeeName);
                preparedStatement.setString(4, jobName);
                preparedStatement.setDouble(5, salary);
                preparedStatement.setString(6, phoneNumber);
                preparedStatement.setInt(7, ssn);

                int executed = preparedStatement.executeUpdate();

                if (executed > 0) {
                    System.out.println("Record inserted successfully!");
                } else {
                    System.out.println("Failed to insert record.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertJobDesc(Scanner in, Connection conn) {

        try {
            System.out.println("\nEnter details for the new job description:");
            System.out.print("Enter employee ID: ");
            int employeeId = in.nextInt();
            in.nextLine();

            System.out.print("Enter job description: ");
            String jobDescription = in.nextLine();

            String insertJobDesc = "INSERT INTO job_description (employee_id, job_description) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertJobDesc)) {
                preparedStatement.setInt(1, employeeId);
                preparedStatement.setString(2, jobDescription);

                int executed = preparedStatement.executeUpdate();

                if (executed > 0) {
                    System.out.println("Record inserted successfully!");
                } else {
                    System.out.println("Failed to insert record.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertCustomer(Scanner in, Connection conn) {

        try {
            System.out.println("\nEnter details for the new customer:");
            System.out.print("Enter guest ID: ");
            int guestId = in.nextInt();
            in.nextLine();

            System.out.print("Enter customer name: ");
            String customerName = in.nextLine();

            System.out.print("Enter phone number: ");
            String phoneNumber = in.nextLine();

            System.out.print("Enter card number: ");
            String cardNumber = in.nextLine();
            //in.nextLine();

            System.out.print("Enter email: ");
            String email = in.nextLine();

            String insertCustomer = "INSERT INTO customer (guest_id, name, phone_no, card_number, email) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertCustomer)) {
                preparedStatement.setInt(1, guestId);
                preparedStatement.setString(2, customerName);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, cardNumber);
                preparedStatement.setString(5, email);

                int executed = preparedStatement.executeUpdate();

                if (executed > 0) {
                    System.out.println("Record inserted successfully!");
                } else {
                    System.out.println("Failed to insert record.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertReservation(Scanner in, Connection conn) {

        try {
            System.out.println("\nEnter details for the new reservation:");
            System.out.print("Enter reservation ID: ");
            int reservationId = in.nextInt();
            in.nextLine();

            System.out.print("Enter guest ID: ");
            int guestId = in.nextInt();
            in.nextLine();

            System.out.print("Enter start date (YYYY-MM-DD): ");
            String startDate = in.nextLine();

            System.out.print("Enter end date (YYYY-MM-DD): ");
            String endDate = in.nextLine();

            System.out.print("Enter number of guests: ");
            int numberOfGuests = in.nextInt();
            in.nextLine();

            System.out.print("Enter room number: ");
            int roomNumber = in.nextInt();
            in.nextLine();

            String insertReservation = "INSERT INTO reservation (reservation_id, guest_id, start_date, end_date, no_of_guests, room_no) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertReservation)) {
                preparedStatement.setInt(1, reservationId);
                preparedStatement.setInt(2, guestId);
                preparedStatement.setString(3, startDate);
                preparedStatement.setString(4, endDate);
                preparedStatement.setInt(5, numberOfGuests);
                preparedStatement.setInt(6, roomNumber);

                int executed = preparedStatement.executeUpdate();

                if (executed > 0) {
                    System.out.println("Record inserted successfully!");
                } else {
                    System.out.println("Failed to insert record.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertSpecialRequest(Scanner in, Connection conn) {

        try {
            System.out.println("\nEnter details for the new special request:");

            System.out.print("Enter reservation ID: ");
            int reservationId = in.nextInt();
            in.nextLine();

            System.out.print("Enter special request: ");
            String specialRequest = in.nextLine();

            String insertSpecialRequest = "INSERT INTO special_requests (reservation_id, special_requests) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(insertSpecialRequest)) {
                preparedStatement.setInt(1, reservationId);
                preparedStatement.setString(2, specialRequest);

                int executed = preparedStatement.executeUpdate();

                if (executed > 0) {
                    System.out.println("Record inserted successfully!");
                } else {
                    System.out.println("Failed to insert record.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}