package chatapp;

import java.util.Random;

public class Message {

    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // normal constructor - creates a random ID
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageId = createMessageId();
        this.messageHash = createMessageHash();
    }

    // second constructor - lets us choose the ID, used for testing
    public Message(String messageId, int messageNumber, String recipient, String messageText) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
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

    // creates the hash: first 2 ID digits : message number : FIRSTLAST
    public String createMessageHash() {
        String[] words = messageText.trim().split(" ");

        // remove anything that is not a letter or number
        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");

        String firstTwo = messageId.substring(0, 2);

        return (firstTwo + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessageHash() {
        return messageHash;
    }

    // temporary test - will be removed later
    public static void main(String[] args) {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        System.out.println(msg.getMessageId());
        System.out.println(msg.checkMessageID());
        System.out.println(msg.checkRecipientCell());
        System.out.println(msg.checkMessageLength());
        System.out.println(msg.getMessageHash());

        // known ID so we can check the hash
        Message testMsg = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        System.out.println(testMsg.getMessageHash());   // expect: 00:0:HITONIGHT
    }
}