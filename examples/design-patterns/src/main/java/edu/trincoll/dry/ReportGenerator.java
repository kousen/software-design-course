package edu.trincoll.dry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// BEFORE: Duplicated string formatting logic
public class ReportGenerator {
    
    public static String generateUserReport(User user) {
        StringBuilder report = new StringBuilder();
        
        // DUPLICATE: Header formatting logic
        report.append("=================================\n");
        report.append("         USER REPORT            \n");
        report.append("=================================\n\n");
        
        // DUPLICATE: Field formatting logic
        report.append("Name: ").append(user.name()).append("\n");
        report.append("Email: ").append(user.email()).append("\n\n");
        
        // DUPLICATE: Timestamp formatting logic
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        report.append("Generated on: ").append(now.format(formatter)).append("\n");
        
        return report.toString();
    }
    
    public static String generateCustomerReport(Customer customer) {
        StringBuilder report = new StringBuilder();
        
        // DUPLICATE: Same header formatting as above
        report.append("=================================\n");
        report.append("       CUSTOMER REPORT          \n");
        report.append("=================================\n\n");
        
        // DUPLICATE: Same field formatting pattern
        report.append("Name: ").append(customer.name()).append("\n");
        report.append("Email: ").append(customer.email()).append("\n");
        report.append("Phone: ").append(customer.phone()).append("\n\n");
        
        // DUPLICATE: Same timestamp formatting
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        report.append("Generated on: ").append(now.format(formatter)).append("\n");
        
        return report.toString();
    }
}