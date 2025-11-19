package payroll;

import java.io.IOException;
import java.util.*;

public class Main {
    private static final String EMP_CSV = "employee.csv";
    private static final String SAL_CSV = "salary.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Admin admin = new Admin("admin", "admin123");
        System.out.println("                                        === Employee Payroll System ===");

        if (!admin.login(sc)) {
            System.out.println("Login failed.");
            sc.close();
            return;
        }

        EmployeeManager em = new EmployeeManager(EMP_CSV);
        FileManager fm = new FileManager(SAL_CSV);
        ReportGenerator rg = new ReportGenerator(fm, em);

        while (true) {
        System.out.println(
            "1. Add Employee\n" +
            "2. View Employees\n" +
            "3. Edit Employee\n" +
            "4. Delete Employee\n" +
            "5. Enter Salary & Calculate\n" +
            "6. Generate Salary Slips\n" +
            "7. Generate Summary Report\n" +
            "8. Exit"
        );
            System.out.print("Choose: ");
            String c = sc.nextLine().trim();

            try {
                switch (c) {
                    case "1": addFlow(sc, em); break;
                    case "2": em.listAll().forEach(e -> System.out.println(e.getId()+" - "+e.getName())); break;
                    case "3": editFlow(sc, em); break;
                    case "4": delFlow(sc, em); break;
                    case "5": salaryFlow(sc, em, fm); break;
                    case "6":
                        System.out.print("Generate salary slips for (1) Specific Employee, (2) All: ");
                        String genOpt = sc.nextLine().trim();

                        System.out.print("Organize slips into per-employee folders? (y/n): ");
                        String perEmpAns = sc.nextLine().trim().toLowerCase();
                        boolean perEmployeeFolder = perEmpAns.equals("y") || perEmpAns.equals("yes");

                        System.out.print("Ask before overwriting existing slips? (y/n): ");
                        String confAns = sc.nextLine().trim().toLowerCase();
                        boolean confirmOverwrite = confAns.equals("y") || confAns.equals("yes");

                        if (genOpt.equals("1")) {
                            System.out.print("Employee ID: ");
                            String empId = sc.nextLine().trim();
                            for (SalaryRecord sr : fm.loadAll()) {
                                if (sr.getEmployeeId().equals(empId)) rg.generateSalarySlip(sr, perEmployeeFolder, confirmOverwrite, sc);
                            }
                        } else if (genOpt.equals("2")) {
                            for (SalaryRecord sr : fm.loadAll()) rg.generateSalarySlip(sr, perEmployeeFolder, confirmOverwrite, sc);
                        } else {
                            System.out.println("Invalid option.");
                        }
                        break;
                    case "7": rg.generateSummaryReport("summary_report.csv"); break;
                    case "8": sc.close(); return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }
    }

    private static void addFlow(Scanner sc, EmployeeManager em) throws IOException {
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Designation: "); String d = sc.nextLine();
        System.out.print("Department: "); String dep = sc.nextLine();
        System.out.print("PAN: "); String pan = sc.nextLine();
        System.out.print("DOJ: "); String doj = sc.nextLine();
        em.add(new Employee(id, name, d, dep, pan, doj));
    }

    private static void editFlow(Scanner sc, EmployeeManager em) throws IOException {
        System.out.print("ID: "); String id = sc.nextLine();
        Employee e = em.get(id);
        if (e == null) return;

        System.out.print("Name (" + e.getName() + "): "); String v = sc.nextLine(); if (!v.isEmpty()) e.setName(v);
        System.out.print("Designation (" + e.getDesignation() + "): "); v = sc.nextLine(); if (!v.isEmpty()) e.setDesignation(v);
        System.out.print("Department (" + e.getDepartment() + "): "); v = sc.nextLine(); if (!v.isEmpty()) e.setDepartment(v);
        System.out.print("PAN (" + e.getPan() + "): "); v = sc.nextLine(); if (!v.isEmpty()) e.setPan(v);

        em.update(e);
    }

    private static void delFlow(Scanner sc, EmployeeManager em) throws IOException {
        System.out.print("ID: "); String id = sc.nextLine();
        em.delete(id);
    }

    private static void salaryFlow(Scanner sc, EmployeeManager em, FileManager fm) throws IOException {
        System.out.print("Employee ID: "); String id = sc.nextLine();
        Employee e = em.get(id);
        if (e == null) return;

        System.out.print("Basic: "); double b = Double.parseDouble(sc.nextLine());
        System.out.print("Allowance: "); double a = Double.parseDouble(sc.nextLine());
        System.out.print("Deduction: "); double d = Double.parseDouble(sc.nextLine());
        System.out.print("Month: "); String m = sc.nextLine();
        System.out.print("Year: "); int y = Integer.parseInt(sc.nextLine());

        PayrollCalculator calc = new DefaultPayrollCalculator(b, a, d);

        String sid = UUID.randomUUID().toString().substring(0, 8);
        SalaryRecord sr = new SalaryRecord(
                sid, id, b, a, d,
                Math.round(calc.calculateGross()*100)/100.0,
                Math.round(calc.calculateNet()*100)/100.0,
                Math.round(calc.calculateTax()*100)/100.0,
                m, y
        );
        fm.appendSalary(sr);
    }
}
