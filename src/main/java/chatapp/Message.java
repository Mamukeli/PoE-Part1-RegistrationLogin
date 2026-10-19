package chatapp;

import java.util.Random;

public class Message {

    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageText;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = createMessageId();
    }

    // creates a random 10-digit ID
    private String createMessageId() {
        Random random = new Random();
        String id = "";

        for (int i = 0; i < 10; i++) {
            id = id + random.nextInt(10);
        }

        return id;
    }

    // checks the ID is not more than 10 characters
    public boolean checkMessageID() {
        return messageId.length() <= 10;
    }

    public String getMessageId() {
        return messageId;
    }

    // temporary test - will be removed later
    public static void main(String[] args) {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        System.out.println(msg.getMessageId());   // expect: 10 digits
        System.out.println(msg.checkMessageID()); // expect: true
    }
}