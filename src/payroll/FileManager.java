package payroll;

import java.sql.*;
import java.util.*;

public class FileManager {

    public FileManager() {
        // No initialization needed, tables are created in DatabaseManager
    }

    public synchronized void appendSalary(SalaryRecord sr) throws SQLException {
        String sql = "INSERT INTO salaries (salaryId, employeeId, basic, allowance, deduction, gross, net, tax, month, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            throw new SQLException("Database connection is null!");
        }
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sr.getSalaryId());
            pstmt.setString(2, sr.getEmployeeId());
            pstmt.setDouble(3, sr.getBasic());
            pstmt.setDouble(4, sr.getAllowance());
            pstmt.setDouble(5, sr.getDeduction());
            pstmt.setDouble(6, sr.getGross());
            pstmt.setDouble(7, sr.getNet());
            pstmt.setDouble(8, sr.getTax());
            pstmt.setString(9, sr.getMonth());
            pstmt.setInt(10, sr.getYear());
            pstmt.executeUpdate();
        }
    }

    public List<SalaryRecord> loadAll() throws SQLException {
        List<SalaryRecord> out = new ArrayList<>();
        String sql = "SELECT * FROM salaries";
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) {
            throw new SQLException("Database connection is null!");
        }
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SalaryRecord sr = new SalaryRecord(rs.getString("salaryId"), rs.getString("employeeId"),
                        rs.getDouble("basic"), rs.getDouble("allowance"), rs.getDouble("deduction"),
                        rs.getDouble("gross"), rs.getDouble("net"), rs.getDouble("tax"),
                        rs.getString("month"), rs.getInt("year"));
                out.add(sr);
            }
        }
        return out;
    }
}
