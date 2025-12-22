package payroll;

import java.sql.*;

public class MySQLConnectionTest {
    public static void main(String[] args) {
        System.out.println("Testing MySQL connection...");

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database connection details
            String url = "jdbc:mysql://localhost:3306/employee_payroll?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String username = "root"; // Your MySQL username
            String password = "root"; // REPLACE THIS with your actual MySQL root password

            // Establish connection
            Connection connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                System.out.println("✅ MySQL connection successful!");

                // Test if tables exist
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet tables = metaData.getTables(null, null, "employees", null);
                if (tables.next()) {
                    System.out.println("✅ Tables exist in database");
                } else {
                    System.out.println("⚠️  Tables not found. Please run database_setup.sql in MySQL Workbench");
                }

                connection.close();
            }

        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL JDBC Driver not found. Make sure mysql-connector-java-8.0.33.jar is in lib/ folder");
        } catch (SQLException e) {
            System.err.println("❌ MySQL connection failed: " + e.getMessage());
            System.err.println("Make sure:");
            System.err.println("1. MySQL Server is running");
            System.err.println("2. Database 'employee_payroll' exists");
            System.err.println("3. Username/password are correct");
        }
    }
}