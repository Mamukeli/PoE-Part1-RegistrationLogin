package chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;
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

    // --- arrays ---

    @Test
    public void testSentMessagesArrayPopulated() {
        assertEquals(2, MessageStore.sentMessages.size());
        assertEquals("Did you get the cake?", MessageStore.sentMessages.get(0).getMessageText());
        assertEquals("It is dinner time !", MessageStore.sentMessages.get(1).getMessageText());
    }

    @Test
    public void testOtherArraysPopulated() {
        assertEquals(1, MessageStore.disregardedMessages.size());
        assertEquals(2, MessageStore.storedMessages.size());
        assertEquals(5, MessageStore.messageHashes.size());
        assertEquals(5, MessageStore.messageIds.size());
    }

    // --- reading the JSON file ---

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

    @Test
    public void testDisplayStoredMessages() {
        String result = MessageStore.displayStoredMessages();

        assertTrue(result.contains(m2.getMessageText()));
        assertTrue(result.contains(m5.getMessageText()));
    }

    // --- features from the brief ---

    @Test
    public void testSenderAndRecipient() {
        String result = MessageStore.displaySenderAndRecipient("kyl_1");

        assertTrue(result.contains("Sender: kyl_1 | Recipient: +27838884567"));
    }

    @Test
    public void testLongestMessage() {
        Message longest = MessageStore.findLongestMessage();

        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMessageText());
    }

    @Test
    public void testSearchByMessageId() {
        Message found = MessageStore.findByMessageId("4444444444");

        assertEquals("0838884567", found.getRecipient());
        assertEquals("It is dinner time !", found.getMessageText());
        assertNull(MessageStore.findByMessageId("0000000000"));
    }

    @Test
    public void testSearchByRecipient() {
        ArrayList<String> found = MessageStore.searchByRecipient("+27838884567");

        assertEquals(2, found.size());
        assertEquals("Where are you? You are late! I have asked you to be on time.", found.get(0));
        assertEquals("Ok, I am leaving without you.", found.get(1));
    }

    @Test
    public void testDeleteMessageByHash() {
        String result = MessageStore.deleteByHash(m2.getMessageHash());

        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.", result);
        assertEquals(1, MessageStore.storedMessages.size());
        assertEquals(4, MessageStore.messageHashes.size());
        assertFalse(MessageStore.messageHashes.contains(m2.getMessageHash()));
    }

    @Test
    public void testDeleteUnknownHash() {
        assertEquals("Message hash not found.", MessageStore.deleteByHash("00:0:NOTHING"));
    }

    @Test
    public void testDisplayReport() {
        String report = MessageStore.displayReport();

        assertTrue(report.contains(m1.getMessageHash()));
        assertTrue(report.contains(m1.getRecipient()));
        assertTrue(report.contains(m1.getMessageText()));
        assertTrue(report.contains(m5.getMessageText()));
    }
}