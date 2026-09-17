package chatapp;

public class Login {

    private Registration registration;
    private boolean loginSuccessful;

    // Login needs to know which Registration object holds the registered details
    public Login(Registration registration) {
        this.registration = registration;
        this.loginSuccessful = false;
    }

    // checks if the entered username and password match what was registered
    public boolean loginUser(String username, String password) {

        String storedUsername = registration.getStoredUsername();
        String storedPassword = registration.getStoredPassword();

        if (username.equals(storedUsername) && password.equals(storedPassword)) {
            loginSuccessful = true;
        } else {
            loginSuccessful = false;
        }

        return loginSuccessful;
    }

    // returns the correct message depending on login outcome
    public String returnLoginStatus(String username) {

        if (loginSuccessful) {
            return "Welcome " + username + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
