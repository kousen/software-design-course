package edu.trincoll.inheritance;

import java.util.ArrayList;
import java.util.List;

public class Executive extends Manager {
    private double stockOptions;
    private boolean hasCompanyCar;
    private List<String> boardMemberships;
    
    public Executive(String name, String email, double salary, double bonusPercentage, 
                     String department, double stockOptions) {
        super(name, email, salary, bonusPercentage, department);
        this.stockOptions = stockOptions;
        this.hasCompanyCar = true;
        this.boardMemberships = new ArrayList<>();
    }
    
    @Override
    public double calculateBonus() {
        double managerBonus = super.calculateBonus();
        double executiveBonus = salary * 0.25;
        double stockBonus = stockOptions * 0.1;
        return managerBonus + executiveBonus + stockBonus;
    }
    
    @Override
    public double calculateMonthlyPay() {
        double basePay = super.calculateMonthlyPay();
        double carAllowance = hasCompanyCar ? 1000 : 0;
        return basePay + carAllowance;
    }
    
    public void vestStockOptions(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Stock options cannot be negative");
        }
        stockOptions += amount;
    }
    
    public void exerciseStockOptions(double amount) {
        if (amount > stockOptions) {
            throw new IllegalArgumentException("Cannot exercise more options than available");
        }
        stockOptions -= amount;
    }
    
    public void joinBoard(String companyName) {
        if (!boardMemberships.contains(companyName)) {
            boardMemberships.add(companyName);
        }
    }
    
    public void leaveBoard(String companyName) {
        boardMemberships.remove(companyName);
    }
    
    @Override
    public String getEmployeeInfo() {
        return String.format("Executive %s - Stock Options: $%.2f", 
                super.getEmployeeInfo(), stockOptions);
    }
    
    public double getStockOptions() {
        return stockOptions;
    }
    
    public void setStockOptions(double stockOptions) {
        if (stockOptions < 0) {
            throw new IllegalArgumentException("Stock options cannot be negative");
        }
        this.stockOptions = stockOptions;
    }
    
    public boolean isHasCompanyCar() {
        return hasCompanyCar;
    }
    
    public void setHasCompanyCar(boolean hasCompanyCar) {
        this.hasCompanyCar = hasCompanyCar;
    }
    
    public List<String> getBoardMemberships() {
        return new ArrayList<>(boardMemberships);
    }
}