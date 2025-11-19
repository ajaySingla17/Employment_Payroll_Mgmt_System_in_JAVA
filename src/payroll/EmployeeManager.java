package payroll;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeManager {
    private final Path employeeCsv;
    private final Map<String, Employee> employees = new LinkedHashMap<>();

    public EmployeeManager(String csvPath) {
        this.employeeCsv = Paths.get(csvPath);
        load();
    }

    private void load() {
        try {
            if (!Files.exists(employeeCsv)) {
                Files.createFile(employeeCsv);
                Files.write(employeeCsv, Collections.singletonList("id,name,designation,department,pan,dateOfJoining"));
            }
            List<String> lines = Files.readAllLines(employeeCsv);
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 6) continue;
                Employee e = new Employee(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                employees.put(e.getId(), e);
            }
        } catch (IOException ex) {
            System.err.println("Error loading employees: " + ex.getMessage());
        }
    }

    public Collection<Employee> listAll() {
        return employees.values();
    }

    public Employee get(String id) {
        return employees.get(id);
    }

    public void add(Employee e) throws IOException {
        if (employees.containsKey(e.getId())) throw new IOException("Employee already exists");
        employees.put(e.getId(), e);
        persist();
    }

    public void update(Employee e) throws IOException {
        if (!employees.containsKey(e.getId())) throw new IOException("Employee not found");
        employees.put(e.getId(), e);
        persist();
    }

    public void delete(String id) throws IOException {
        employees.remove(id);
        persist();
    }

    private void persist() throws IOException {
        List<String> out = new ArrayList<>();
        out.add("id,name,designation,department,pan,dateOfJoining");
        out.addAll(employees.values().stream().map(Employee::toString).collect(Collectors.toList()));
        Files.write(employeeCsv, out);
    }
}
