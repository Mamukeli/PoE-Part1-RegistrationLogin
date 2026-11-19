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

    // third constructor - used when a message is read back from the JSON file
    public Message(String messageId, String messageHash, String recipient, String messageText) {
        this.messageId = messageId;
        this.messageHash = messageHash;
        this.recipient = recipient;
        this.messageText = messageText;
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
            MessageStore.addSent(this);
            return "Message successfully sent.";
        } else if (choice == 2) {
            return "Press 0 to delete the message.";
        } else if (choice == 3) {
            storeMessage();
            return "Message successfully stored.";
        } else {
            return "Invalid choice.";
        }
    }

    // adds this message to the disregarded array (after the user enters 0)
    public void disregardMessage() {
        MessageStore.addDisregarded(this);
    }

    // full details of this message: ID, hash, recipient, message
    public String messageDetails() {
        return "Message ID: " + messageId + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }

    // returns all messages sent while the program is running
    public static String printMessages() {
        String all = "";

        for (int i = 0; i < MessageStore.sentMessages.size(); i++) {
            all = all + MessageStore.sentMessages.get(i).messageDetails() + "\n\n";
        }

        return all;
    }

    // returns the total number of messages sent
    public static int returnTotalMessagess() {
        return MessageStore.sentMessages.size();
    }

    // saves this message in the stored array and in the JSON file
    public void storeMessage() {
        MessageStore.addStored(this);
        MessageStore.saveStoredMessages();
    }

    // turns this message into one JSON object
    public String toJson() {
        return "{\"messageId\": \"" + messageId + "\", "
                + "\"messageHash\": \"" + messageHash + "\", "
                + "\"recipient\": \"" + recipient + "\", "
                + "\"message\": \"" + cleanForJson(messageText) + "\"}";
    }

    // makes quotes and backslashes safe to put inside JSON text
    private String cleanForJson(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    // returns the message for when an ID is created
    public String messageIdGenerated() {
        return "Message ID generated: " + messageId;
    }

    // clears all the arrays, used by the unit tests
    public static void resetMessages() {
        MessageStore.clearAll();
    }

    public String getMessageId() {
        return messageId;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }
}