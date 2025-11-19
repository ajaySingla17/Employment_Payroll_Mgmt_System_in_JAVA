package payroll;

public class TaxCalculator {
    private double taxableIncome;

    public TaxCalculator(double taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public double computeTax() {
        double tax = 0;
        double income = taxableIncome;

        if (income <= 250000) return 0;
        if (income <= 500000) return (income - 250000) * 0.05;

        tax += 250000 * 0.05;
        if (income <= 1000000) return tax + (income - 500000) * 0.20;

        tax += 500000 * 0.20;
        tax += (income - 1000000) * 0.30;

        return tax;
    }
}
