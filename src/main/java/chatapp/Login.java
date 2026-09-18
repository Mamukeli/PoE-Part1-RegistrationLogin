package chatapp;

public class Login {

    private Registration registration;
    private boolean loginSuccessful;

    // links this Login to a Registration
    public Login(Registration registration) {
        this.registration = registration;
        this.loginSuccessful = false;
    }

    // checks username and password against stored details
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

    // returns welcome or failure message
    public String returnLoginStatus(String username) {

        if (loginSuccessful) {
            return "Welcome " + username + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
