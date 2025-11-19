package payroll;

public class DefaultPayrollCalculator extends PayrollCalculator {

    public DefaultPayrollCalculator(double basic, double allowance, double deduction) {
        super(basic, allowance, deduction);
    }

    @Override
    public double calculateGross() {
        return basic + allowance;
    }

    @Override
    public double calculateTax() {
        double annual = (calculateGross() - deduction) * 12;
        TaxCalculator tc = new TaxCalculator(annual);
        return tc.computeTax() / 12.0;
    }

    @Override
    public double calculateNet() {
        return calculateGross() - deduction - calculateTax();
    }
}
