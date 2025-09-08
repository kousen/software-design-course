package edu.trincoll.inheritance;

import java.util.HashSet;
import java.util.Set;

public class Developer extends Employee {
    private Set<String> programmingLanguages;
    private String primaryLanguage;
    private int linesOfCodeWritten;
    private boolean hasOnCallDuty;
    
    public Developer(String name, String email, double salary, String primaryLanguage) {
        super(name, email, salary);
        this.primaryLanguage = primaryLanguage;
        this.programmingLanguages = new HashSet<>();
        this.programmingLanguages.add(primaryLanguage);
        this.linesOfCodeWritten = 0;
        this.hasOnCallDuty = false;
    }
    
    @Override
    public double calculateBonus() {
        double baseBonus = super.calculateBonus();
        double skillBonus = programmingLanguages.size() * 500;
        double productivityBonus = Math.min(linesOfCodeWritten / 100, 5000);
        double onCallBonus = hasOnCallDuty ? 2000 : 0;
        return baseBonus + skillBonus + productivityBonus + onCallBonus;
    }
    
    public void addProgrammingLanguage(String language) {
        programmingLanguages.add(language);
    }
    
    public void removeProgrammingLanguage(String language) {
        if (language.equals(primaryLanguage)) {
            throw new IllegalArgumentException("Cannot remove primary language");
        }
        programmingLanguages.remove(language);
    }
    
    public boolean knowsLanguage(String language) {
        return programmingLanguages.contains(language);
    }
    
    public void writeLinesOfCode(int lines) {
        if (lines < 0) {
            throw new IllegalArgumentException("Lines of code cannot be negative");
        }
        this.linesOfCodeWritten += lines;
    }
    
    public double getProductivityScore() {
        int languageScore = programmingLanguages.size() * 10;
        int codeScore = Math.min(linesOfCodeWritten / 1000, 50);
        return languageScore + codeScore;
    }
    
    @Override
    public String getEmployeeInfo() {
        return String.format("%s - Primary: %s, Languages: %d", 
                super.getEmployeeInfo(), primaryLanguage, programmingLanguages.size());
    }
    
    public Set<String> getProgrammingLanguages() {
        return new HashSet<>(programmingLanguages);
    }
    
    public String getPrimaryLanguage() {
        return primaryLanguage;
    }
    
    public void setPrimaryLanguage(String primaryLanguage) {
        this.primaryLanguage = primaryLanguage;
        programmingLanguages.add(primaryLanguage);
    }
    
    public int getLinesOfCodeWritten() {
        return linesOfCodeWritten;
    }
    
    public void setLinesOfCodeWritten(int linesOfCodeWritten) {
        if (linesOfCodeWritten < 0) {
            throw new IllegalArgumentException("Lines of code cannot be negative");
        }
        this.linesOfCodeWritten = linesOfCodeWritten;
    }
    
    public boolean isHasOnCallDuty() {
        return hasOnCallDuty;
    }
    
    public void setHasOnCallDuty(boolean hasOnCallDuty) {
        this.hasOnCallDuty = hasOnCallDuty;
    }
}