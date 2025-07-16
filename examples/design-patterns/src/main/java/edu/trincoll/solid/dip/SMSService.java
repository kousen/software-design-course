package edu.trincoll.solid.dip;

public class SMSService implements MessageService {
    
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("SMS sent to " + recipient + ": " + message);
    }
}