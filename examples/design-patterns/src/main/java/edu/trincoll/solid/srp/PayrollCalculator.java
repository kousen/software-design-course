package edu.trincoll.solid.srp;

public class PayrollCalculator {
    private static final double TAX_RATE = 0.25;
    private static final double BENEFITS_RATE = 0.10;
    
    public double calculateNetPay(Employee employee) {
        double gross = employee.getSalary();
        double taxes = gross * TAX_RATE;
        double benefits = gross * BENEFITS_RATE;
        return gross - taxes - benefits;
    }
    
    public double calculateTaxes(Employee employee) {
        return employee.getSalary() * TAX_RATE;
    }
    
    public double calculateBenefits(Employee employee) {
        return employee.getSalary() * BENEFITS_RATE;
    }
}