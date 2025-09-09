package edu.trincoll.inheritance;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {
    private double bonusPercentage;
    private final List<Employee> teamMembers;
    private String department;
    
    public Manager(String name, String email, double salary, double bonusPercentage, String department) {
        super(name, email, salary);
        this.bonusPercentage = bonusPercentage;
        this.department = department;
        this.teamMembers = new ArrayList<>();
    }
    
    @Override
    public double calculateBonus() {
        double baseBonus = super.calculateBonus();
        double managementBonus = salary * (bonusPercentage / 100);
        double teamBonus = teamMembers.size() * 1000;
        return baseBonus + managementBonus + teamBonus;
    }
    
    @Override
    public double calculateMonthlyPay() {
        double basePay = super.calculateMonthlyPay();
        double monthlyBonus = calculateBonus() / 12;
        return basePay + monthlyBonus;
    }
    
    public void addTeamMember(Employee employee) {
        if (employee == this) {
            throw new IllegalArgumentException("Manager cannot manage themselves");
        }
        if (!teamMembers.contains(employee)) {
            teamMembers.add(employee);
        }
    }
    
    public void removeTeamMember(Employee employee) {
        teamMembers.remove(employee);
    }
    
    public List<Employee> getTeamMembers() {
        return new ArrayList<>(teamMembers);
    }
    
    public int getTeamSize() {
        return teamMembers.size();
    }
    
    public double getTeamTotalSalary() {
        return teamMembers.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }
    
    public void giveTeamRaise(double percentage) {
        for (Employee member : teamMembers) {
            member.giveRaise(percentage);
        }
    }
    
    @Override
    public String getEmployeeInfo() {
        return String.format("%s - %s Department (Team size: %d)", 
                super.getEmployeeInfo(), department, teamMembers.size());
    }
    
    public double getBonusPercentage() {
        return bonusPercentage;
    }
    
    public void setBonusPercentage(double bonusPercentage) {
        if (bonusPercentage < 0 || bonusPercentage > 100) {
            throw new IllegalArgumentException("Bonus percentage must be between 0 and 100");
        }
        this.bonusPercentage = bonusPercentage;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
}