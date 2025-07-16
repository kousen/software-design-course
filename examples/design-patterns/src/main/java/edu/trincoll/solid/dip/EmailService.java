package edu.trincoll.solid.dip;

public class EmailService implements MessageService {
    
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("Email sent to " + recipient + ": " + message);
    }
}