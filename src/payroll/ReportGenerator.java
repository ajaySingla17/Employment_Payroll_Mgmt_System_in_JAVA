package payroll;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReportGenerator {
    private final FileManager fileManager;
    private final EmployeeManager employeeManager;

    public ReportGenerator(FileManager fm, EmployeeManager em) {
        this.fileManager = fm;
        this.employeeManager = em;
    }

    /**
     * Generate a salary slip for a salary record.
     * @param sr the salary record
     * @param perEmployeeFolder if true, place slip under salary_slips/<employeeId>/
     * @param confirmOverwrite if true, prompt the provided Scanner before overwriting existing file
     * @param sc Scanner to use for interactive confirmation when confirmOverwrite is true (can be null if confirmOverwrite=false)
     */
    public void generateSalarySlip(SalaryRecord sr, boolean perEmployeeFolder, boolean confirmOverwrite, java.util.Scanner sc) {
        Employee e = employeeManager.get(sr.getEmployeeId());
        if (e == null) return;

        // Prepare directory for salary slips (optionally per-employee)
        Path dir = Paths.get("salary_slips");
        if (perEmployeeFolder) dir = dir.resolve(e.getId());

        // sanitize employee name for filenames
        String safeName = e.getName() == null ? "unknown" : e.getName().replaceAll("[^a-zA-Z0-9._-]", "_");

        String fileName = "salaryslip_" + e.getId() + "_" + safeName + "_" + sr.getMonth() + "_" + sr.getYear() + ".txt";

        List<String> lines = Arrays.asList(
                "Salary Slip for " + e.getName(),
                "Employee ID: " + e.getId(),
                "Designation: " + e.getDesignation(),
                "",
                "Basic: " + sr.getBasic(),
                "Allowance: " + sr.getAllowance(),
                "Gross: " + sr.getGross(),
                "Deduction: " + sr.getDeduction(),
                "Tax: " + sr.getTax(),
                "Net Pay: " + sr.getNet()
        );

        try {
            Files.createDirectories(dir);
            Path filePath = dir.resolve(fileName);

            if (Files.exists(filePath) && confirmOverwrite && sc != null) {
                System.out.print("File " + filePath.toString() + " already exists. Overwrite? (y/n): ");
                String ans = sc.nextLine().trim().toLowerCase();
                if (!ans.equals("y") && !ans.equals("yes")) {
                    System.out.println("Skipped: " + filePath.toString());
                    return;
                }
            }

            Files.write(filePath, lines);
            System.out.println("Slip generated: " + filePath.toString());
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    public void generateSummaryReport(String outCsv) {
        try {
            List<SalaryRecord> all = fileManager.loadAll();
            List<String> lines = new ArrayList<>();
            lines.add("salaryId,employeeId,gross,net,tax,month,year");

            lines.addAll(all.stream()
                    .map(sr -> String.join(",", sr.getSalaryId(), sr.getEmployeeId(),
                            String.valueOf(sr.getGross()), String.valueOf(sr.getNet()),
                            String.valueOf(sr.getTax()), sr.getMonth(), String.valueOf(sr.getYear())))
                    .collect(Collectors.toList()));

            Files.write(Paths.get(outCsv), lines);
            System.out.println("Summary generated: " + outCsv);
        } catch (IOException ex) {
            System.err.println("Error generating summary: " + ex.getMessage());
        }
    }
}
