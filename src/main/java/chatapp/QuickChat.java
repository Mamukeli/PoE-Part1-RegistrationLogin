package chatapp;

import java.util.Scanner;

public class QuickChat {

    // runs the QuickChat menu - the Scanner is passed in so the app shares one
    public static void start(Scanner input) {

        System.out.println("Welcome to QuickChat.");

        // the user decides how many messages they want to enter
        System.out.print("How many messages would you like to enter? ");
        int limit = readNumber(input);

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String option = input.nextLine().trim();

            if (option.equals("1")) {
                messagesEntered = sendMessages(input, messagesEntered, limit);
            } else if (option.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (option.equals("3")) {
                running = false;
            } else {
                System.out.println("Invalid option, please enter 1, 2 or 3.");
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
                    System.out.println("Message deleted.");
                } else {
                    System.out.println("Message was not deleted.");
                }
                messagesEntered++;
            } else if (choice.equals("3")) {
                System.out.println(message.SentMessage(3));
                messagesEntered++;
            } else {
                System.out.println("Invalid choice, please enter the message again.");
            }
        }

        return messagesEntered;
    }

}