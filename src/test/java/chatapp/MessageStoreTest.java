package chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class MessageStoreTest {

    Message m1, m2, m3, m4, m5;

    // fills the arrays with the test data from the brief
    @BeforeEach
    public void setUp() {
        MessageStore.fileName = "testMessages.json";
        new File("testMessages.json").delete();
        Message.resetMessages();

        m1 = new Message("1111111111", 0, "+27834557896", "Did you get the cake?");
        m2 = new Message("2222222222", 1, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        m3 = new Message("3333333333", 2, "+27834484567", "Yohoooo, I am at your gate.");
        m4 = new Message("4444444444", 3, "0838884567", "It is dinner time !");
        m5 = new Message("5555555555", 4, "+27838884567", "Ok, I am leaving without you.");

        m1.SentMessage(1);       // Sent
        m2.SentMessage(3);       // Stored
        m3.disregardMessage();   // Disregard
        m4.SentMessage(1);       // Sent
        m5.SentMessage(3);       // Stored
    }

    // reads the JSON file back into the stored array
    @Test
    public void testReadJsonFileIntoArray() {
        // forget what is in memory, then read the file
        Message.resetMessages();
        MessageStore.loadStoredMessages();

        assertEquals(2, MessageStore.storedMessages.size());
        assertEquals(m2.getMessageText(), MessageStore.storedMessages.get(0).getMessageText());
        assertEquals(m5.getMessageText(), MessageStore.storedMessages.get(1).getMessageText());
        assertEquals(m2.getMessageHash(), MessageStore.storedMessages.get(0).getMessageHash());
    }
}