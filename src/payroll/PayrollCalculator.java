package payroll;

public abstract class PayrollCalculator {
    protected double basic;
    protected double allowance;
    protected double deduction;

    public PayrollCalculator(double basic, double allowance, double deduction) {
        this.basic = basic;
        this.allowance = allowance;
        this.deduction = deduction;
    }

    public abstract double calculateGross();
    public abstract double calculateTax();
    public abstract double calculateNet();
}
