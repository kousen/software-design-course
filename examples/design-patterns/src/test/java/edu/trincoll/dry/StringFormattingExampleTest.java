package edu.trincoll.dry;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class StringFormattingExampleTest {
    
    @Test
    void reportGenerator_shouldFormatUserReport() {
        var user = new User("john@example.com", "John Doe");
        String report = ReportGenerator.generateUserReport(user);
        
        assertThat(report).contains("USER REPORT");
        assertThat(report).contains("John Doe");
        assertThat(report).contains("john@example.com");
        assertThat(report).contains("Generated on:");
    }
    
    @Test
    void reportGenerator_shouldFormatCustomerReport() {
        var customer = new Customer("jane@example.com", "Jane Smith", "555-1234");
        String report = ReportGenerator.generateCustomerReport(customer);
        
        assertThat(report).contains("CUSTOMER REPORT");
        assertThat(report).contains("Jane Smith");
        assertThat(report).contains("jane@example.com");
        assertThat(report).contains("555-1234");
        assertThat(report).contains("Generated on:");
    }
    
    @Test
    void reportUtils_shouldFormatHeader() {
        String header = ReportUtils.formatHeader("TEST REPORT");
        
        assertThat(header).startsWith("=");
        assertThat(header).contains("TEST REPORT");
        assertThat(header).endsWith("=\n\n");
    }
    
    @Test
    void reportUtils_shouldFormatField() {
        String field = ReportUtils.formatField("Name", "John Doe");
        
        assertThat(field).isEqualTo("Name: John Doe");
    }
    
    @Test
    void reportUtils_shouldFormatTimestamp() {
        String timestamp = ReportUtils.formatTimestamp();
        
        assertThat(timestamp).startsWith("Generated on: ");
        assertThat(timestamp).hasSize("Generated on: yyyy-MM-dd HH:mm:ss".length());
    }
}