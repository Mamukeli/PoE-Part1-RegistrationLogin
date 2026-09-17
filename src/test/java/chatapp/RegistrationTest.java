package chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    @Test
    public void testUsernameCorrectlyFormatted() {
        Registration reg = new Registration();
        assertTrue(reg.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        Registration reg = new Registration();
        assertFalse(reg.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexity() {
        Registration reg = new Registration();
        assertTrue(reg.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Registration reg = new Registration();
        assertFalse(reg.checkPasswordComplexity("password"));
    }

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Registration reg = new Registration();
        assertTrue(reg.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Registration reg = new Registration();
        assertFalse(reg.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testRegisterUserSuccessMessage() {
        Registration reg = new Registration();
        String result = reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.", result);
    }

    @Test
    public void testRegisterUserUsernameFailureMessage() {
        Registration reg = new Registration();
        String result = reg.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.", result);
    }
}