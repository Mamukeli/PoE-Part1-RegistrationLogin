# PoE Part 1 — Registration and Login

A console-based Java application that allows a user to register an account and then log in.

## What this project does

The application allows a user to:
1. Register an account by entering a username, password, and South African cell phone number.
2. Log in using the same username and password.

All input is validated according to specific formatting rules before being accepted.

## Classes

### Registration
Handles account creation and validation:
- `checkUserName(String username)` — checks that the username contains an underscore and is no more than five characters long.
- `checkPasswordComplexity(String password)` — checks that the password is at least eight characters long and contains a capital letter, a number, and a special character.
- `checkCellPhoneNumber(String phoneNumber)` — checks that the cell phone number starts with South Africa's international code (+27) and is no more than ten digits long after that.
- `registerUser(String username, String password, String cellPhone)` — runs all three checks above and returns the correct success or failure message. If registration succeeds, the details are stored for later login.

### Login
Handles logging in using previously registered details:
- `loginUser(String username, String password)` — checks the entered username and password against what was stored during registration.
- `returnLoginStatus(String username)` — returns a welcome message if login succeeded, or a failure message if it did not.

### Main
Runs the console application: prompts the user through registration, then login, using `Scanner` for input.

## Testing

JUnit tests are included for both `Registration` and `Login`, covering:
- Correctly and incorrectly formatted usernames
- Passwords that do and don't meet complexity rules
- Correctly and incorrectly formatted cell phone numbers
- Successful and failed registration messages
- Successful and failed login outcomes

## How to run

1. Open the project in IntelliJ IDEA.
2. Run `Main.java` to use the console application.
3. Run `RegistrationTest.java` and `LoginTest.java` to execute the unit tests.

## Attribution

The cell phone number validation uses Java's built-in regular expression matching, based on the official Java documentation:
https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
