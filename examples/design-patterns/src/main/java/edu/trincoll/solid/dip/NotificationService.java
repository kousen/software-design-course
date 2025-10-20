package edu.trincoll.solid.dip;

public class NotificationService {
    private final MessageService messageService;
    
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }
    
    public void notify(String message, String recipient) {
        messageService.sendMessage(message, recipient);
        logMessageSent(message, recipient);
    }

    private void logMessageSent(String message, String recipient) {
        System.out.println("Message sent to " + recipient + ": " + message);
    }
}