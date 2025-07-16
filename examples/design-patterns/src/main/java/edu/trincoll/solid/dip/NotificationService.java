package edu.trincoll.solid.dip;

public class NotificationService {
    private final MessageService messageService;
    
    public NotificationService(MessageService messageService) {
        this.messageService = messageService;
    }
    
    public void notify(String message, String recipient) {
        messageService.sendMessage(message, recipient);
    }
}