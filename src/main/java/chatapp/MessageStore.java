package chatapp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MessageStore {

    // the five arrays from the brief
    static ArrayList<Message> sentMessages = new ArrayList<>();
    static ArrayList<Message> disregardedMessages = new ArrayList<>();
    static ArrayList<Message> storedMessages = new ArrayList<>();
    static ArrayList<String> messageHashes = new ArrayList<>();
    static ArrayList<String> messageIds = new ArrayList<>();

    // the file the stored messages are saved in
    static String fileName = "storedMessages.json";

    // adds a message to the sent array
    public static void addSent(Message message) {
        sentMessages.add(message);
        addHashAndId(message);
    }

    // adds a message to the disregarded array
    public static void addDisregarded(Message message) {
        disregardedMessages.add(message);
        addHashAndId(message);
    }

    // adds a message to the stored array
    public static void addStored(Message message) {
        storedMessages.add(message);
        addHashAndId(message);
    }

    // keeps the hash and ID arrays in step with the message arrays
    private static void addHashAndId(Message message) {
        messageHashes.add(message.getMessageHash());
        messageIds.add(message.getMessageId());
    }

    // saves all the stored messages into the JSON file
    // Reference: https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html
    public static void saveStoredMessages() {
        try {
            FileWriter writer = new FileWriter(fileName);

            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {
                writer.write("  " + storedMessages.get(i).toJson());

                // every message except the last needs a comma after it
                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");
            writer.close();

        } catch (IOException e) {
            System.out.println("Could not store the messages: " + e.getMessage());
        }
    }

    // reads the JSON file into the stored messages array
    // Reference: https://github.com/google/gson/blob/main/UserGuide.md
    public static void loadStoredMessages() {
        File file = new File(fileName);

        // nothing to read if no messages have been stored yet
        if (!file.exists()) {
            return;
        }

        try {
            FileReader reader = new FileReader(file);
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            reader.close();

            for (int i = 0; i < array.size(); i++) {
                JsonObject object = array.get(i).getAsJsonObject();

                String id = object.get("messageId").getAsString();
                String hash = object.get("messageHash").getAsString();
                String recipient = object.get("recipient").getAsString();
                String text = object.get("message").getAsString();

                addStored(new Message(id, hash, recipient, text));
            }

        } catch (Exception e) {
            System.out.println("Could not read the stored messages: " + e.getMessage());
        }
    }

    // puts the sent, disregarded and stored messages into one list for searching
    private static ArrayList<Message> allMessages() {
        ArrayList<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(disregardedMessages);
        all.addAll(storedMessages);
        return all;
    }

    // shows every stored message in full
    public static String displayStoredMessages() {
        if (storedMessages.size() == 0) {
            return "There are no stored messages.";
        }

        String result = "";

        for (int i = 0; i < storedMessages.size(); i++) {
            result = result + storedMessages.get(i).messageDetails() + "\n\n";
        }

        return result;
    }

    // a) shows the sender and recipient of every stored message
    public static String displaySenderAndRecipient(String sender) {
        if (storedMessages.size() == 0) {
            return "There are no stored messages.";
        }

        String result = "";

        for (int i = 0; i < storedMessages.size(); i++) {
            result = result + "Sender: " + sender
                    + " | Recipient: " + storedMessages.get(i).getRecipient() + "\n";
        }

        return result;
    }

    // b) finds the message with the most characters
    public static Message findLongestMessage() {
        Message longest = null;
        ArrayList<Message> all = allMessages();

        for (int i = 0; i < all.size(); i++) {
            if (longest == null
                    || all.get(i).getMessageText().length() > longest.getMessageText().length()) {
                longest = all.get(i);
            }
        }

        return longest;
    }

    // c) finds a message using its message ID (null if not found)
    public static Message findByMessageId(String id) {
        ArrayList<Message> all = allMessages();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getMessageId().equals(id)) {
                return all.get(i);
            }
        }

        return null;
    }

    // d) finds the text of all sent or stored messages for one recipient
    public static ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> found = new ArrayList<>();

        for (int i = 0; i < sentMessages.size(); i++) {
            if (sentMessages.get(i).getRecipient().equals(recipient)) {
                found.add(sentMessages.get(i).getMessageText());
            }
        }

        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getRecipient().equals(recipient)) {
                found.add(storedMessages.get(i).getMessageText());
            }
        }

        return found;
    }

    // e) deletes a message using its hash
    public static String deleteByHash(String hash) {
        Message found = null;
        ArrayList<Message> all = allMessages();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getMessageHash().equals(hash)) {
                found = all.get(i);
            }
        }

        if (found == null) {
            return "Message hash not found.";
        }

        // remove it from every array
        sentMessages.remove(found);
        disregardedMessages.remove(found);
        storedMessages.remove(found);
        messageHashes.remove(found.getMessageHash());
        messageIds.remove(found.getMessageId());

        // keep the JSON file in step with the stored array
        saveStoredMessages();

        return "Message: \"" + found.getMessageText() + "\" successfully deleted.";
    }

    // f) report with the full details of all sent and stored messages
    public static String displayReport() {
        if (sentMessages.size() == 0 && storedMessages.size() == 0) {
            return "There are no messages to report on.";
        }

        String report = "===== MESSAGE REPORT =====\n";

        for (int i = 0; i < sentMessages.size(); i++) {
            report = report + "\nSent message\n" + sentMessages.get(i).messageDetails() + "\n";
        }

        for (int i = 0; i < storedMessages.size(); i++) {
            report = report + "\nStored message\n" + storedMessages.get(i).messageDetails() + "\n";
        }

        return report;
    }

    // empties all the arrays
    public static void clearAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIds.clear();
    }
}