package edu.trincoll.dry;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// AFTER: DRY - Extracted common formatting functionality
public class ReportUtils {
    
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static String formatHeader(String title) {
        int totalWidth = 33;
        String border = "=".repeat(totalWidth);
        
        // Center the title
        int padding = (totalWidth - title.length()) / 2;
        String centeredTitle = " ".repeat(padding) + title + " ".repeat(padding);
        
        return border + "\n" + centeredTitle + "\n" + border + "\n\n";
    }
    
    public static String formatField(String label, String value) {
        return label + ": " + value;
    }
    
    public static String formatTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        return "Generated on: " + now.format(TIMESTAMP_FORMATTER);
    }
    
    public static String buildReport(String title, String... fields) {
        StringBuilder report = new StringBuilder();
        
        report.append(formatHeader(title));
        
        for (String field : fields) {
            report.append(field).append("\n");
        }
        
        report.append("\n").append(formatTimestamp()).append("\n");
        
        return report.toString();
    }
}