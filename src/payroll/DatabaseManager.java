package payroll;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_payroll?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASS = "root"; 
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("❌ Error getting database connection: " + e.getMessage());
            return null;
        }
    }

    private static void createTables() throws SQLException {
        String createEmployeesTable = """
            CREATE TABLE IF NOT EXISTS employees (
                id TEXT PRIMARY KEY,
                name TEXT NOT NULL,
                designation TEXT,
                department TEXT,
                pan TEXT,
                dateOfJoining TEXT
            );
            """;

        String createSalariesTable = """
            CREATE TABLE IF NOT EXISTS salaries (
                salaryId TEXT PRIMARY KEY,
                employeeId TEXT NOT NULL,
                basic REAL,
                allowance REAL,
                deduction REAL,
                gross REAL,
                net REAL,
                tax REAL,
                month TEXT,
                year INTEGER,
                FOREIGN KEY (employeeId) REFERENCES employees(id)
            );
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createEmployeesTable);
            stmt.execute(createSalariesTable);
        }
    }
}