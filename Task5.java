import java.sql.*;
import java.util.Scanner;

public class Task5 {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/StudentCourseRegistration";
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();

            char choice;
            do {
                displayMenu();

                int userChoice = getUserChoice();

                switch (userChoice) {
                    case 1:
                        displayCourseListing(stmt);
                        break;
                    case 2:
                        registerStudentForCourse(conn, stmt, scanner);
                        break;
                    case 3:
                        dropCourseForStudent(conn, stmt, scanner);
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose a valid option.");
                }

                System.out.print("Do you want to perform another action? (yes/no): ");
                choice = scanner.next().toLowerCase().charAt(0);
            } while (choice == 'y');

        } catch (SQLException | ClassNotFoundException se) {
            handleException(se);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
                scanner.close();
            } catch (SQLException se) {
                handleException(se);
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nStudent Course Registration System Menu:");
        System.out.println("1. Display Course Listing");
        System.out.println("2. Register Student for Course");
        System.out.println("3. Drop Course for Student\n");
    }

    private static int getUserChoice() {
        System.out.print("Enter your choice: ");
        try {
            return Integer.parseInt(System.console().readLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void displayCourseListing(Statement stmt) {
        try {
            String sql = "SELECT * FROM Course";
            ResultSet rs = stmt.executeQuery(sql);

            if (!rs.isBeforeFirst()) {
                System.out.println("No courses available in the database.");
            } else {
                System.out.println("Course Listing:");
                System.out.println("-------------------------------------------------------------");
                System.out.printf("%-15s %-40s %-30s %-10s\n", "Code", "Title", "Description", "Capacity");
                System.out.println("-------------------------------------------------------------");

                while (rs.next()) {
                    System.out.printf("%-15s %-40s %-30s %-10s\n",
                            rs.getString("course_code"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getInt("capacity"));
                }
            }

            rs.close();
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private static void registerStudentForCourse(Connection conn, Statement stmt, Scanner scanner) {
        try {
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter course code for registration: ");
            String courseCode = scanner.nextLine();

            String sql = "INSERT INTO Registration (student_id, course_code) VALUES (" + studentId + ", '" + courseCode
                    + "')";
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                System.out.println("Student with ID " + studentId + " registered for course " + courseCode);
            } else {
                System.out
                        .println("Student with ID " + studentId + " could not be registered for course " + courseCode);
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private static void dropCourseForStudent(Connection conn, Statement stmt, Scanner scanner) {
        try {
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter course code for removal: ");
            String courseCode = scanner.nextLine();

            String sql = "DELETE FROM Registration WHERE student_id = " + studentId + " AND course_code = '"
                    + courseCode + "'";
            int rowsAffected = stmt.executeUpdate(sql);

            if (rowsAffected > 0) {
                System.out.println("Student with ID " + studentId + " dropped course " + courseCode);
            } else {
                System.out.println("Student with ID " + studentId + " is not registered for course " + courseCode);
            }
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private static void handleException(Exception e) {
        if (e instanceof SQLException) {
            System.out.println("SQL Error: " + e.getMessage());
        } else {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
