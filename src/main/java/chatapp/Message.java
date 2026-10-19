package chatapp;

import java.util.ArrayList;
import java.util.Random;

public class Message {

    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // shared by all messages while the program runs
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static int totalSent = 0;

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

    // choice 1 = send, 2 = disregard, 3 = store
    public String SentMessage(int choice) {
        if (choice == 1) {
            sentMessages.add(messageDetails());
            totalSent++;
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            return "Message successfully stored.";
        } else {
            return "Invalid choice.";
        }
    }

    // full details of this message: ID, hash, recipient, message
    public String messageDetails() {
        return "Message ID: " + messageId + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }

    // returns all messages sent while the program is running
    public String printMessages() {
        String all = "";

        for (int i = 0; i < sentMessages.size(); i++) {
            all = all + sentMessages.get(i) + "\n\n";
        }

        return all;
    }

    // returns the total number of messages sent
    public int returnTotalMessagess() {
        return totalSent;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessageHash() {
        return messageHash;
    }

    // temporary test - will be removed later
    public static void main(String[] args) {
        Message msg1 = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        Message msg2 = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");

        System.out.println(msg1.SentMessage(1)); // expect: Message successfully sent.
        System.out.println(msg2.SentMessage(2)); // expect: Press 0 to delete the message.
        System.out.println(msg1.SentMessage(3)); // expect: Message successfully stored.

        System.out.println(msg1.messageDetails());
        System.out.println(msg1.printMessages());
        System.out.println("Total sent: " + msg1.returnTotalMessagess()); // expect: 1
    }
}