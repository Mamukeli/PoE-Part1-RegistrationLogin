package chatapp;

public class Registration {
    // stores the registered user's details once registration succeeds
    private String storedUsername;
    private String storedPassword;
    private String storedCellPhone;

    // getters so other classes (like Login) can safely check stored details
    public String getStoredUsername() {
        return storedUsername;
    }

    public String getStoredPassword() {
        return storedPassword;
    }

    // checks for underscore and max 5 characters
    public boolean checkUserName(String username) {
        boolean hasUnderscore = username.contains("_");
        boolean correctLength = username.length() <= 5;

        if (hasUnderscore && correctLength) {
            return true;
        } else {
            return false;
        }
    }
    // checks password length, capital, number, special character
    public boolean checkPasswordComplexity(String password) {

        boolean longEnough = password.length() >= 8;
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        // checks each character against the complexity rules
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);

            if (Character.isUpperCase(currentChar)) hasCapital = true;
            if (Character.isDigit(currentChar)) hasNumber = true;
            if (!Character.isLetterOrDigit(currentChar)) hasSpecial = true;
        }

        return longEnough && hasCapital && hasNumber && hasSpecial;
    }

    // regex breakdown: ^ and $ mark start/end of string,
    // \\+27 matches the literal country code, \\d{1,10} matches
    // between 1 and 10 digits after that
    public boolean checkCellPhoneNumber(String phoneNumber) {

        String regex = "^\\+27\\d{1,10}$";

        return phoneNumber.matches(regex);
    }

    // validates and registers the user, returns result message
    public String registerUser(String username, String password, String cellPhone) {

        boolean validUsername = checkUserName(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validCellPhone = checkCellPhoneNumber(cellPhone);

        if (!validUsername && !validPassword) {
            return "Username is not correctly formatted; please ensure that your username "
                    + "contains an underscore and is no more than five characters in length.\n"
                    + "Password is not correctly formatted; please ensure that the password "
                    + "contains at least eight characters, a capital letter, a number, and a special character.";
        } else if (!validUsername) {
            return "Username is not correctly formatted; please ensure that your username "
                    + "contains an underscore and is no more than five characters in length.";
        } else if (!validPassword) {
            return "Password is not correctly formatted; please ensure that the password "
                    + "contains at least eight characters, a capital letter, a number, and a special character.";
        } else if (!validCellPhone) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        } else {
            // all checks passed - store the details for later login
            storedUsername = username;
            storedPassword = password;
            storedCellPhone = cellPhone;

            return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
        }
    }
}
