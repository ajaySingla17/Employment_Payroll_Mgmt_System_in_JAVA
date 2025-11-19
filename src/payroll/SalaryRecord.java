package payroll;

public class SalaryRecord {
    private String salaryId;
    private String employeeId;
    private double basic;
    private double allowance;
    private double deduction;
    private double gross;
    private double net;
    private double tax;
    private String month;
    private int year;

    public SalaryRecord(String salaryId, String employeeId, double basic, double allowance, double deduction,
                        double gross, double net, double tax, String month, int year) {
        this.salaryId = salaryId;
        this.employeeId = employeeId;
        this.basic = basic;
        this.allowance = allowance;
        this.deduction = deduction;
        this.gross = gross;
        this.net = net;
        this.tax = tax;
        this.month = month;
        this.year = year;
    }

    public String getSalaryId() { return salaryId; }
    public String getEmployeeId() { return employeeId; }
    public double getBasic() { return basic; }
    public double getAllowance() { return allowance; }
    public double getDeduction() { return deduction; }
    public double getGross() { return gross; }
    public double getNet() { return net; }
    public double getTax() { return tax; }
    public String getMonth() { return month; }
    public int getYear() { return year; }

    @Override
    public String toString() {
        return salaryId + "," + employeeId + "," + basic + "," + allowance + "," + deduction + "," + gross + "," + net + "," + tax + "," + month + "," + year;
    }
}
