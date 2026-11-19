package chatapp;

import java.util.ArrayList;
import java.util.Scanner;

public class QuickChat {

    // runs the QuickChat menu - the Scanner is passed in so the app shares one
    public static void start(Scanner input, String username) {

        System.out.println("Welcome to QuickChat.");

        // read any stored messages from the JSON file into the stored array
        MessageStore.loadStoredMessages();

        // the user decides how many messages they want to enter
        System.out.print("How many messages would you like to enter? ");
        int limit = readNumber(input);

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");
            String option = input.nextLine().trim();

            if (option.equals("1")) {
                messagesEntered = sendMessages(input, messagesEntered, limit);
            } else if (option.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (option.equals("3")) {
                running = false;
            } else if (option.equals("4")) {
                storedMessagesMenu(input, username);
            } else {
                System.out.println("Invalid option, please enter 1, 2, 3 or 4.");
            }
        }

        System.out.println("Total messages sent: " + Message.returnTotalMessagess());
    }

    // keeps asking until the user enters a whole number from 1 to 9999
    private static int readNumber(Scanner input) {
        while (true) {
            String text = input.nextLine().trim();

            if (text.matches("[1-9][0-9]{0,3}")) {
                return Integer.parseInt(text);
            }

            System.out.print("Please enter a whole number greater than 0: ");
        }
    }

    // lets the user enter messages until the limit is reached
    private static int sendMessages(Scanner input, int messagesEntered, int limit) {

        if (messagesEntered >= limit) {
            System.out.println("You have already entered all " + limit + " messages.");
            return messagesEntered;
        }

        while (messagesEntered < limit) {
            System.out.println("\n--- Message " + (messagesEntered + 1) + " of " + limit + " ---");

            System.out.print("Enter recipient number (e.g. +27718693002): ");
            String recipient = input.nextLine().trim();

            System.out.print("Enter your message (max 250 characters): ");
            String text = input.nextLine();

            Message message = new Message(messagesEntered, recipient, text);

            // check the recipient number
            String recipientCheck = message.checkRecipientCell();
            if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                System.out.println(recipientCheck);
                continue;
            }

            // check the message length
            String lengthCheck = message.checkMessageLength();
            System.out.println(lengthCheck);
            if (!lengthCheck.equals("Message ready to send.")) {
                continue;
            }

            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Your choice: ");
            String choice = input.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println(message.SentMessage(1));
                System.out.println(message.messageDetails());
                messagesEntered++;
            } else if (choice.equals("2")) {
                System.out.println(message.SentMessage(2));
                System.out.print("Enter 0 to confirm: ");
                String confirm = input.nextLine().trim();

                if (confirm.equals("0")) {
                    message.disregardMessage();
                    System.out.println("Message disregarded.");
                    messagesEntered++;
                } else {
                    System.out.println("Message was not disregarded, please enter the message again.");
                }
            } else if (choice.equals("3")) {
                System.out.println(message.SentMessage(3));
                messagesEntered++;
            } else {
                System.out.println("Invalid choice, please enter the message again.");
            }
        }

        return messagesEntered;
    }

    // the Stored Messages menu (main menu option 4)
    private static void storedMessagesMenu(Scanner input, String username) {

        // show the messages that were read from the JSON file
        System.out.println("\nStored messages (read from the JSON file):");
        System.out.println(MessageStore.displayStoredMessages());

        boolean back = false;

        while (!back) {
            System.out.println("===== STORED MESSAGES =====");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search for messages by recipient");
            System.out.println("5) Delete a message using its message hash");
            System.out.println("6) Display message report");
            System.out.println("7) Back to main menu");
            System.out.print("Choose an option: ");
            String choice = input.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println(MessageStore.displaySenderAndRecipient(username));
            } else if (choice.equals("2")) {
                showLongestMessage();
            } else if (choice.equals("3")) {
                searchByMessageId(input);
            } else if (choice.equals("4")) {
                searchByRecipient(input);
            } else if (choice.equals("5")) {
                deleteMessage(input);
            } else if (choice.equals("6")) {
                System.out.println(MessageStore.displayReport());
            } else if (choice.equals("7")) {
                back = true;
            } else {
                System.out.println("Invalid option, please enter 1 to 7.");
            }
        }
    }

    // shows the longest message
    private static void showLongestMessage() {
        Message longest = MessageStore.findLongestMessage();

        if (longest == null) {
            System.out.println("There are no messages yet.");
        } else {
            System.out.println("Longest message:");
            System.out.println(longest.messageDetails());
        }
    }

    // asks for a message ID and shows the recipient and message
    private static void searchByMessageId(Scanner input) {
        System.out.print("Enter the message ID: ");
        String id = input.nextLine().trim();

        Message found = MessageStore.findByMessageId(id);

        if (found == null) {
            System.out.println("Message ID not found.");
        } else {
            System.out.println("Recipient: " + found.getRecipient());
            System.out.println("Message: " + found.getMessageText());
        }
    }

    // asks for a recipient and shows all their sent or stored messages
    private static void searchByRecipient(Scanner input) {
        System.out.print("Enter the recipient number: ");
        String recipient = input.nextLine().trim();

        ArrayList<String> found = MessageStore.searchByRecipient(recipient);

        if (found.size() == 0) {
            System.out.println("No messages found for " + recipient + ".");
        } else {
            for (int i = 0; i < found.size(); i++) {
                System.out.println((i + 1) + ") " + found.get(i));
            }
        }
    }

    // asks for a message hash and deletes that message
    private static void deleteMessage(Scanner input) {
        System.out.print("Enter the message hash: ");
        String hash = input.nextLine().trim().toUpperCase();

        System.out.println(MessageStore.deleteByHash(hash));
    }
}