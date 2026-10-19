package chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    // test data from the brief
    String recipient1 = "+27718693002";
    String text1 = "Hi Mike, can you join us for dinner tonight?";
    String recipient2 = "08575975889";
    String text2 = "Hi Keegan, did you receive the payment?";

    // start every test with fresh totals
    @BeforeEach
    public void setUp() {
        Message.resetMessages();
    }

    // --- message length ---

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(0, recipient1, text1);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        Message msg = new Message(0, recipient1, "a".repeat(260));
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", msg.checkMessageLength());
    }

    // --- recipient number ---

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message(0, recipient1, text1);
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message msg = new Message(1, recipient2, text2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    // --- message hash ---

    @Test
    public void testMessageHash() {
        Message msg = new Message("0012345678", 0, recipient1, text1);
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    // tests more hashes in a loop
    @Test
    public void testMessageHashesInLoop() {
        String[] texts = {text1, text2, "Hi Mike, thanks"};
        String[] expected = {"00:0:HITONIGHT", "00:1:HIPAYMENT", "00:2:HITHANKS"};

        for (int i = 0; i < texts.length; i++) {
            Message msg = new Message("0012345678", i, recipient1, texts[i]);
            assertEquals(expected[i], msg.createMessageHash());
        }
    }

    // --- message ID ---

    @Test
    public void testMessageIdCreated() {
        Message msg = new Message(0, recipient1, text1);
        assertTrue(msg.checkMessageID());
        assertEquals("Message ID generated: " + msg.getMessageId(), msg.messageIdGenerated());
    }

    // --- send, discard, store ---

    @Test
    public void testSendMessage() {
        Message msg = new Message(0, recipient1, text1);
        assertEquals("Message successfully sent.", msg.SentMessage(1));
    }

    @Test
    public void testDisregardMessage() {
        Message msg = new Message(1, recipient2, text2);
        assertEquals("Press 0 to delete the message.", msg.SentMessage(2));
    }

    @Test
    public void testStoreMessage() {
        Message msg = new Message(0, recipient1, text1);
        assertEquals("Message successfully stored.", msg.SentMessage(3));
    }

    // --- total sent ---

    @Test
    public void testTotalMessagesSent() {
        Message msg1 = new Message(0, recipient1, text1);
        Message msg2 = new Message(1, recipient2, text2);

        msg1.SentMessage(1);   // message 1 is sent
        msg2.SentMessage(2);   // message 2 is disregarded

        assertEquals(1, Message.returnTotalMessagess());
    }
}