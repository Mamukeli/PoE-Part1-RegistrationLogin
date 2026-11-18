package chatapp;

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

    // empties all the arrays
    public static void clearAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIds.clear();
    }
}