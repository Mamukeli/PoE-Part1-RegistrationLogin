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

    // checks the recipient starts with +27 and has up to 10 digits after it
    // same regex as Part 1
    public String checkRecipientCell() {
        String regex = "^\\+27\\d{1,10}$";

        if (recipient.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // checks the message is 250 characters or less
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int extra = messageText.length() - 250;
            return "Message exceeds 250 characters by " + extra + "; please reduce the size.";
        }
    }

    public String getMessageId() {
        return messageId;
    }

    // temporary test - will be removed later
    public static void main(String[] args) {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        System.out.println(msg.getMessageId());
        System.out.println(msg.checkMessageID());
        System.out.println(msg.checkRecipientCell());
        System.out.println(msg.checkMessageLength());

        Message wrongNumber = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        System.out.println(wrongNumber.checkRecipientCell());

        Message longMessage = new Message(2, "+27718693002", "a".repeat(260));
        System.out.println(longMessage.checkMessageLength());
    }
}