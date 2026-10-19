package chatapp;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Registration registration = new Registration();
        Login login = new Login(registration);

        boolean registeredSuccessfully = false;

        // Registration section
        System.out.println("=== Registration ===");

        System.out.print("Enter a username: ");
        String username = input.nextLine();

        System.out.print("Enter a password: ");
        String password = input.nextLine();

        System.out.print("Enter your cell phone number (with country code, e.g. +27...): ");
        String cellPhone = input.nextLine();

        String registrationMessage = registration.registerUser(username, password, cellPhone);
        System.out.println(registrationMessage);

        // registration worked if all three success messages came back
        if (registrationMessage.contains("Username successfully captured.")
                && registrationMessage.contains("Password successfully captured.")
                && registrationMessage.contains("Cell phone number successfully added.")) {
            registeredSuccessfully = true;
        }

        // Login section - only offered if registration succeeded
        if (registeredSuccessfully) {
            System.out.println("\n=== Login ===");

            System.out.print("Enter your username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = input.nextLine();

            boolean loggedIn = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returnLoginStatus(loginUsername));

            // only logged in users can use QuickChat
            if (loggedIn) {
                QuickChat.start(input);
            }

        } else {
            System.out.println("\nRegistration was not successful, so login cannot proceed.");
        }
    }
}