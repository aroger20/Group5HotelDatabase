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
	    
	/*
	// Justin Salas: For quick run config use
		if (args.length < 4 || args.length > 5) {
            System.out.print("Incorrect command-line arguments provided.");
            System.out.println("Usage: java Group5HotelDatabase <url> <driver> <username> <password>");
            System.exit(1);
        }

        // Retrieve command-line arguments
        String url = args[0];
        String driver = args[1];
        String userName = args[2];
        String password = args[3];

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            mainMenu(new Scanner(System.in), conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 	*
 	*/


	    
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
                    queryMenu(in, conn);
                    break;
                case 2:
		    InsertData insertData = new InsertData();
                    System.out.println("1) Hotel\n2) Employee\n3) Job Description\n4) Customer\n5) Reservation\n6) Special Request");
                    System.out.print("Select option: ");
                    int userOpt = in.nextInt();

                    switch (userOpt) {
                        case 1:
                            InsertData.insertHotel(in, conn);
                            break;
                        case 2:
                            InsertData.insertEmployee(in, conn);
                            break;
                        case 3:
                            InsertData.insertJobDesc(in, conn);
                            break;
                        case 4:
                            InsertData.insertCustomer(in, conn);
                            break;
                        case 5:
                            InsertData.insertReservation(in, conn);
                            break;
                        case 6:
                            InsertData.insertSpecialRequest(in, conn);
                            break;
                        default:
                            System.out.println("Please select an option from 1-6.");
                    }
                    System.out.println("Loading insert...");
                    break;
                case 3:
                    System.out.println("Loading update...");
                    break;
                case 4:
                    System.out.println("Loading delete...");
                    delete(conn, in);
                    System.out.println("Done!");
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
    
    /**
     * Displays a menu of query options and prompts the user for input.
     *
     * @param in Scanner object for user input
     * @param c  Connection to the database
     */
    private static void queryMenu(Scanner in, Connection c) {
    	
    	// Initialize database variables
    	ResultSet rs = null;
    	Statement s = null;
    	PreparedStatement ps = null;
    	ResultSetMetaData rsmd = null;
    	
    	
    	// Variable to store user's choice
    	int choice = 0;
    	
    	// Flag to control loop
    	boolean loop = true;
    	
    	try {
    		// Main loop for displaying the query menu
	    	while (loop) {
	    		// Display the query menu options
		    	System.out.println("Query menu; please choose from one of the options below. Enter 12 to return to the main menu.");
		    	System.out.println();
		    	System.out.println("1)Retrieve All Hotels");
		    	System.out.println("2)Retrieve All Employees");
		    	System.out.println("3)Retrieve All Customers");
		    	System.out.println("4)Retrieve All Reservations");
		    	System.out.println("5)Retrieve All Reservations for a specific customer...");
		    	System.out.println("6)Retrieve All Reservations for a specific date range...");
		    	System.out.println("7)Retrieve All Employees in a specific job position...");
		    	System.out.println("8)Retrieve All Special Requests for a specific reservation...");
		    	System.out.println("9)Retrieve All Reservations for Valentine's Day and any special requests");
		    	System.out.println("10)Retrieve All Customers who have more than one reservation");
		    	System.out.println("11)Retrieve Total Number of reservations for each customer");
		    	System.out.println("12)Return to Main Menu");
		    	
		    	try {
		    		// Prompt user for choice
		    		System.out.print("Please enter choice: ");
		    		choice = in.nextInt();
		    		in.nextLine(); // Consume newline character
		    		System.out.println();
		    	} catch (Exception e) {
		    		System.out.println("Please enter a valid number.");
		    		e.printStackTrace();
		    	}
		    	
		    	// Perform action based on user's choice
		    	switch(choice) {
		    		case 1:
		    			retrieveAll(c,s,rs,rsmd,"hotel");
		    			break;
		    		case 2:
		    			retrieveAll(c,s,rs,rsmd,"employee");
		    			break;
		    		case 3:
		    			retrieveAll(c,s,rs,rsmd,"customer");
		    			break;
		    		case 4:
		    			retrieveAll(c,s,rs,rsmd,"reservation");
		    			break;
		    		case 5:
		    			System.out.print("Please enter customer name: ");
		    			String name = in.nextLine();
		    			reservationsByCustomer(c,ps,rs,rsmd,name);
		    			break;
		    		case 6:
		    			System.out.print("Please enter start date (yyyy-mm-dd): ");
		    			String startDate = in.nextLine();
		    			System.out.print("Please enter end date (yyyy-mm-dd): ");
		    			String endDate = in.nextLine();
		    			reservationsByDate(c,ps,rs,rsmd,startDate,endDate);
		    			break;
		    		case 7:
		    			System.out.print("Please enter a position title: ");
		    			String jobTitle = in.nextLine();
		    			employeesByJob(c,ps,rs,rsmd,jobTitle);
		    			break;
		    		case 8:
		    			System.out.print("Please enter a reservation id: ");
		    			String reservationId = in.nextLine();
		    			requestsByReservation(c,ps,rs,rsmd,reservationId);
		    			break;
		    		case 9:
		    			reservationsOnValentines(c,s,rs,rsmd);
		    			break;
		    		case 10:
		    			moreThanOneReservation(c,s,rs,rsmd);
		    			break;
		    		case 11:
		    			totalReservationsPerCustomer(c,s,rs,rsmd);
		    			break;
		    		case 12:
		    			loop = false;
		    			break;
		    		default:
		    			System.out.println("You must choose between 1-12");
		    	} 
	    	}
    	} finally {
    		// Close resources in the finally block to ensure they're closed regardless of exceptions
    		try {
    			if (rs != null) {
    				rs.close();
    			}
    			if (ps != null) {
    				ps.close();
    			}
    			if (s != null) {
    				s.close();
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }

    /**
     * Retrieves the total number of reservations for each customer.
     *
     * @param c         Connection to the database
     * @param s         Statement object for executing SQL queries
     * @param rs        ResultSet object for storing query results
     * @param metaData  ResultSetMetaData object for retrieving metadata of the query results
     */
	private static void totalReservationsPerCustomer(Connection c, Statement s, ResultSet rs, ResultSetMetaData metaData) {

		try {
			s = c.createStatement();
			
			// Execute SQL query
			rs = s.executeQuery("SELECT c.name, COUNT(r.reservation_id) AS total_reservations "
					+ "FROM customer c "
					+ "LEFT JOIN reservation r USING(guest_id) "
					+ "GROUP BY c.name");
			
			// Get metadata
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			// Print column headers
			for (int i = 1; i <= columnCount; i++) {
				System.out.print(metaData.getColumnName(i) + "\t");
			}
			System.out.println();
			
			// Print query results
			while(rs.next()) {
				for(int i = 1; i <= columnCount; i++) {
					System.out.print(rs.getObject(i) + "\t");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
		} catch (SQLException sqle) {
			// Handle SQL exceptions
			sqle.printStackTrace();
		} 
	}

	/**
	 * Retrieves customers who have more than one reservation from the database and prints their information.
	 *
	 * @param c         Connection to the database
	 * @param s         Statement object for executing SQL queries
	 * @param rs        ResultSet object for storing query results
	 * @param metaData  ResultSetMetaData object for retrieving metadata of the query results
	 */
	private static void moreThanOneReservation(Connection c, Statement s, ResultSet rs, ResultSetMetaData metaData) {
		
		try {
			// Create a statement for executing SQL queries
			s = c.createStatement();
			
			// Execute SQL query to retrieve customers with more than one reservation
			rs = s.executeQuery("SELECT c.guest_id, c.name, c.phone_no, c.card_number, c.email, COUNT(r.reservation_id) AS reservation_count "
					+ "FROM customer c "
					+ "JOIN reservation r ON c.guest_id = r.guest_id "
					+ "GROUP BY c.guest_id, c.name, c.phone_no, c.card_number, c.email "
					+ "HAVING COUNT(r.reservation_id) > 1");
			
			// Retreive metadata of the query results
			metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			// Print column headers
			for (int i = 1; i <= columnCount; i++) {
				System.out.print(metaData.getColumnName(i) + "\t");
			}
			System.out.println();
			
			// Print query results
			while(rs.next()) {
				for(int i = 1; i <= columnCount; i++) {
					System.out.print(rs.getObject(i) + "\t");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
		} catch (SQLException sqle) {
			// handle sql exceptions
			sqle.printStackTrace();
		} 
	}

	/**
	 * Retrieves reservations made on Valentine's Day and any special requests associated with them.
	 *
	 * @param c         Connection to the database
	 * @param s         Statement object for executing SQL queries
	 * @param rs        ResultSet object for storing query results
	 * @param metaData  ResultSetMetaData object for retrieving metadata of the query results
	 */
	private static void reservationsOnValentines(Connection c, Statement s, ResultSet rs, ResultSetMetaData metaData) {
	    
	    try {
	        // Create a statement for executing SQL queries
	        s = c.createStatement();
	        
	        // Execute SQL query to retrieve reservations on Valentine's Day and associated special requests
	        rs = s.executeQuery("SELECT r.reservation_id, r.guest_id, r.start_date, r.end_date, r.no_of_guests, r.room_no, s.special_requests "
	                + "FROM reservation r "
	                + "JOIN special_requests s USING(reservation_id) "
	                + "WHERE r.start_date BETWEEN '2024-02-14' AND '2024-02-18'");
	        
	        // Retrieve metadata of the query results
	        metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        
	        // Print column headers
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.print(metaData.getColumnName(i) + "\t");
	        }
	        System.out.println();
	        
	        // Print query results
	        while(rs.next()) {
	            for(int i = 1; i <= columnCount; i++) {
	                System.out.print(rs.getObject(i) + "\t");
	            }
	            System.out.println();
	        }
	        System.out.println();
	        System.out.println();
	    } catch (SQLException sqle) {
	        // Handle SQL exceptions
	        sqle.printStackTrace();
	    } 
	}

	/**
	 * Retrieves special requests associated with a specific reservation.
	 *
	 * @param c             Connection to the database
	 * @param ps            PreparedStatement object for executing SQL queries with parameters
	 * @param rs            ResultSet object for storing query results
	 * @param metaData      ResultSetMetaData object for retrieving metadata of the query results
	 * @param reservationId Reservation ID for which special requests are to be retrieved
	 */
	private static void requestsByReservation(Connection c, PreparedStatement ps, ResultSet rs, ResultSetMetaData metaData, String reservationId) {
	    
	    try {
	        // Create a prepared statement for executing SQL queries with parameters
	        ps = c.prepareStatement("SELECT * FROM special_requests WHERE reservation_id = ?");
	        ps.setString(1, reservationId);
	        
	        // Execute SQL query to retrieve special requests for the given reservation ID
	        rs = ps.executeQuery();
	        
	        // Retrieve metadata of the query results
	        metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        
	        // Print column headers
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.print(metaData.getColumnName(i) + "\t");
	        }
	        System.out.println();
	        
	        // Print query results
	        while(rs.next()) {
	            for(int i = 1; i <= columnCount; i++) {
	                System.out.print(rs.getObject(i) + "\t");
	            }
	            System.out.println();
	        }
	        System.out.println();
	        System.out.println();
	    } catch (SQLException sqle) {
	        // Handle SQL exceptions
	        sqle.printStackTrace();
	    } 
	}

	/**
	 * Retrieves employees with a specific job title from the database.
	 *
	 * @param c         Connection to the database
	 * @param ps        PreparedStatement object for executing SQL queries with parameters
	 * @param rs        ResultSet object for storing query results
	 * @param metaData  ResultSetMetaData object for retrieving metadata of the query results
	 * @param jobTitle  Job title of the employees to be retrieved
	 */
	private static void employeesByJob(Connection c, PreparedStatement ps, ResultSet rs, ResultSetMetaData metaData, String jobTitle) {
	    
	    try {
	        // Create a prepared statement for executing SQL queries with parameters
	        ps = c.prepareStatement("SELECT * FROM employee WHERE job_name = ?");
	        ps.setString(1, jobTitle);
	        
	        // Execute SQL query to retrieve employees with the specified job title
	        rs = ps.executeQuery();
	        
	        // Retrieve metadata of the query results
	        metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        
	        // Print column headers
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.print(metaData.getColumnName(i) + "\t");
	        }
	        System.out.println();
	        
	        // Print query results
	        while(rs.next()) {
	            for(int i = 1; i <= columnCount; i++) {
	                System.out.print(rs.getObject(i) + "\t");
	            }
	            System.out.println();
	        }
	        System.out.println();
	        System.out.println();
	    } catch (SQLException sqle) {
	        // Handle SQL exceptions
	        sqle.printStackTrace();
	    } 
	}

	/**
	 * Retrieves reservations made within a specific date range from the database.
	 *
	 * @param c         Connection to the database
	 * @param ps        PreparedStatement object for executing SQL queries with parameters
	 * @param rs        ResultSet object for storing query results
	 * @param metaData  ResultSetMetaData object for retrieving metadata of the query results
	 * @param startDate Start date of the date range
	 * @param endDate   End date of the date range
	 */
	private static void reservationsByDate(Connection c, PreparedStatement ps, ResultSet rs, ResultSetMetaData metaData, String startDate, String endDate) {
	    
	    try {
	        // Create a prepared statement for executing SQL queries with parameters
	        ps = c.prepareStatement("SELECT * FROM reservation WHERE start_date >= ? AND end_date <= ?");
	        ps.setString(1, startDate);
	        ps.setString(2, endDate);
	        
	        // Execute SQL query to retrieve reservations within the specified date range
	        rs = ps.executeQuery();
	        
	        // Retrieve metadata of the query results
	        metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        
	        // Print column headers
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.print(metaData.getColumnName(i) + "\t");
	        }
	        System.out.println();
	        
	        // Print query results
	        while(rs.next()) {
	            for(int i = 1; i <= columnCount; i++) {
	                System.out.print(rs.getObject(i) + "\t");
	            }
	            System.out.println();
	        }
	        System.out.println();
	        System.out.println();
	    } catch (SQLException sqle) {
	        // Handle SQL exceptions
	        sqle.printStackTrace();
	    } 
	}

	/**
	 * Retrieves reservations made by a specific customer from the database.
	 *
	 * @param c         Connection to the database
	 * @param ps        PreparedStatement object for executing SQL queries with parameters
	 * @param rs        ResultSet object for storing query results
	 * @param metaData  ResultSetMetaData object for retrieving metadata of the query results
	 * @param name      Name of the customer whose reservations are to be retrieved
	 */
	private static void reservationsByCustomer(Connection c, PreparedStatement ps, ResultSet rs, ResultSetMetaData metaData, String name) {
	    
	    try {
	        // Create a prepared statement for executing SQL queries with parameters
	        ps = c.prepareStatement("SELECT r.reservation_id, r.guest_id, r.start_date, r.end_date, r.no_of_guests, r.room_no "
	                + "FROM reservation r JOIN customer c USING(guest_id) "
	                + "WHERE c.name = ?");
	        ps.setString(1, name);
	        
	        // Execute SQL query to retrieve reservations made by the specified customer
	        rs = ps.executeQuery();
	        
	        // Retrieve metadata of the query results
	        metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        
	        // Print column headers
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.print(metaData.getColumnName(i) + "\t");
	        }
	        System.out.println();
	        
	        // Print query results
	        while(rs.next()) {
	            for(int i = 1; i <= columnCount; i++) {
	                System.out.print(rs.getObject(i) + "\t");
	            }
	            System.out.println();
	        }
	        System.out.println();
	        System.out.println();
	    } catch (SQLException sqle) {
	        // Handle SQL exceptions
	        sqle.printStackTrace();
	    } 
	}

	/**
	 * Retrieves all records from a specified table in the database.
	 *
	 * @param c         Connection to the database
	 * @param s         Statement object for executing SQL queries
	 * @param rs        ResultSet object for storing query results
	 * @param metaData  ResultSetMetaData object for retrieving metadata of the query results
	 * @param table     Name of the table from which records are to be retrieved
	 */
	private static void retrieveAll(Connection c, Statement s, ResultSet rs, ResultSetMetaData metaData, String table) {
	    
	    try {
	        // Create a statement for executing SQL queries
	        s = c.createStatement();
	        
	        // Execute SQL query to retrieve all records from the specified table
	        rs = s.executeQuery("SELECT * FROM " + table);
	        
	        // Retrieve metadata of the query results
	        metaData = rs.getMetaData();
	        int columnCount = metaData.getColumnCount();
	        
	        // Print column headers
	        for (int i = 1; i <= columnCount; i++) {
	            System.out.print(metaData.getColumnName(i) + "\t");
	        }
	        System.out.println();
	        
	        // Print query results
	        while(rs.next()) {
	            for(int i = 1; i <= columnCount; i++) {
	                System.out.print(rs.getObject(i) + "\t");
	            }
	            System.out.println();
	        }
	        System.out.println();
	        System.out.println();
	    } catch (SQLException sqle) {
	        // Handle SQL exceptions
	        sqle.printStackTrace();
	    } 
	}

    public static void delete(Connection conn, Scanner in) {
        try {
            Statement stmt = conn.createStatement();

            System.out.print("Enter the name of the table from which you want to delete records: ");
            String tableName = in.next().trim();

            in.nextLine();

            System.out.print("Enter the condition for deletion (e.g., 'id = 1'): ");
            String condition = in.nextLine().trim();

            StringBuilder deleteQuery = new StringBuilder("DELETE FROM " + tableName);
            if (!condition.isEmpty()) {
                deleteQuery.append(" WHERE ").append(condition);
            }
            System.out.println("DEBUG: Delete query: " + deleteQuery.toString());

            int rowsAffected = stmt.executeUpdate(deleteQuery.toString());
            System.out.println(rowsAffected + " records deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
