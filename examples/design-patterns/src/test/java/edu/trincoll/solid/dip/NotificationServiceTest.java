package edu.trincoll.solid.dip;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class NotificationServiceTest {
    
    @Test
    void notificationServiceWorksWithEmailService() {
        MessageService emailService = new EmailService();
        NotificationService notificationService = new NotificationService(emailService);
        
        assertThat(notificationService).isNotNull();
        notificationService.notify("Hello World", "test@example.com");
    }
    
    @Test
    void notificationServiceWorksWithSMSService() {
        MessageService smsService = new SMSService();
        NotificationService notificationService = new NotificationService(smsService);
        
        assertThat(notificationService).isNotNull();
        notificationService.notify("Hello World", "+1234567890");
    }
    
    @Test
    void notificationServiceWorksWithSlackService() {
        MessageService slackService = new SlackService();
        NotificationService notificationService = new NotificationService(slackService);
        
        assertThat(notificationService).isNotNull();
        notificationService.notify("Hello World", "@username");
    }
    
    @Test
    void notificationServiceDependsOnAbstraction() {
        MessageService[] services = {
            new EmailService(),
            new SMSService(),
            new SlackService()
        };
        
        for (MessageService service : services) {
            NotificationService notificationService = new NotificationService(service);
            assertThat(notificationService).isNotNull();
            notificationService.notify("Test message", "recipient");
        }
    }
    
    @Test
    void emailServiceImplementsMessageService() {
        EmailService emailService = new EmailService();
        
        assertThat(emailService).isInstanceOf(MessageService.class);
        emailService.sendMessage("Test", "test@example.com");
    }
    
    @Test
    void smsServiceImplementsMessageService() {
        SMSService smsService = new SMSService();
        
        assertThat(smsService).isInstanceOf(MessageService.class);
        smsService.sendMessage("Test", "+1234567890");
    }
    
    @Test
    void slackServiceImplementsMessageService() {
        SlackService slackService = new SlackService();
        
        assertThat(slackService).isInstanceOf(MessageService.class);
        slackService.sendMessage("Test", "@user");
    }
}