package payroll;

import java.sql.*;
import java.util.*;

public class EmployeeManager {
    private final Map<String, Employee> employees = new LinkedHashMap<>();

    public EmployeeManager() {
        load();
    }

    private void load() {
        String sql = "SELECT * FROM employees";
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            System.err.println("Database connection is null, cannot load employees");
            return;
        }
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee e = new Employee(rs.getString("id"), rs.getString("name"),
                        rs.getString("designation"), rs.getString("department"),
                        rs.getString("pan"), rs.getString("dateOfJoining"));
                employees.put(e.getId(), e);
            }
        } catch (SQLException ex) {
            System.err.println("Error loading employees: " + ex.getMessage());
        }
    }

    public Collection<Employee> listAll() {
        return employees.values();
    }

    public Employee get(String id) {
        return employees.get(id);
    }

    public void add(Employee e) throws SQLException {
        if (employees.containsKey(e.getId())) throw new SQLException("Employee already exists");
        String sql = "INSERT INTO employees (id, name, designation, department, pan, dateOfJoining) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            throw new SQLException("Database connection is null!");
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, e.getId());
            pstmt.setString(2, e.getName());
            pstmt.setString(3, e.getDesignation());
            pstmt.setString(4, e.getDepartment());
            pstmt.setString(5, e.getPan());
            pstmt.setString(6, e.getDateOfJoining());
            pstmt.executeUpdate();
            employees.put(e.getId(), e);
        }
    }

    public void update(Employee e) throws SQLException {
        if (!employees.containsKey(e.getId())) throw new SQLException("Employee not found");
        String sql = "UPDATE employees SET name = ?, designation = ?, department = ?, pan = ?, dateOfJoining = ? WHERE id = ?";
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            throw new SQLException("Database connection is null!");
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, e.getName());
            pstmt.setString(2, e.getDesignation());
            pstmt.setString(3, e.getDepartment());
            pstmt.setString(4, e.getPan());
            pstmt.setString(5, e.getDateOfJoining());
            pstmt.setString(6, e.getId());
            pstmt.executeUpdate();
            employees.put(e.getId(), e);
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id = ?";
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            throw new SQLException("Database connection is null!");
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            employees.remove(id);
        }
    }
}
