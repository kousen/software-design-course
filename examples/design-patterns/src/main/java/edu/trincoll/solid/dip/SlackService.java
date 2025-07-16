package edu.trincoll.solid.dip;

public class SlackService implements MessageService {
    
    @Override
    public void sendMessage(String message, String recipient) {
        System.out.println("Slack message sent to " + recipient + ": " + message);
    }
}