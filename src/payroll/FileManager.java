package payroll;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileManager {
    private final Path salaryCsv;

    public FileManager(String salaryCsvPath) {
        this.salaryCsv = Paths.get(salaryCsvPath);
        try {
            if (!Files.exists(salaryCsv)) {
                Files.createFile(salaryCsv);
                Files.write(salaryCsv, Collections.singletonList("salaryId,employeeId,basic,allowance,deduction,gross,net,tax,month,year"));
            }
        } catch (IOException e) {
            System.err.println("Error initializing salary CSV: " + e.getMessage());
        }
    }

    public synchronized void appendSalary(SalaryRecord sr) throws IOException {
        Files.write(salaryCsv, Collections.singletonList(sr.toString()), StandardOpenOption.APPEND);
    }

    public List<SalaryRecord> loadAll() throws IOException {
        List<String> lines = Files.readAllLines(salaryCsv);
        List<SalaryRecord> out = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            String[] p = line.split(",");
            SalaryRecord sr = new SalaryRecord(p[0], p[1], Double.parseDouble(p[2]), Double.parseDouble(p[3]),
                    Double.parseDouble(p[4]), Double.parseDouble(p[5]), Double.parseDouble(p[6]),
                    Double.parseDouble(p[7]), p[8], Integer.parseInt(p[9]));
            out.add(sr);
        }
        return out;
    }
}
