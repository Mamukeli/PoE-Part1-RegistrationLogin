package chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    @Test
    public void testLoginSuccessful() {
        Registration reg = new Registration();
        reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        Login login = new Login(reg);
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        Registration reg = new Registration();
        reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        Login login = new Login(reg);
        assertFalse(login.loginUser("kyl_1", "wrongPassword"));
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        Registration reg = new Registration();
        reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        Login login = new Login(reg);
        login.loginUser("kyl_1", "Ch&&sec@ke99!");

        assertEquals("Welcome kyl_1, it is great to see you again.", login.returnLoginStatus("kyl_1"));
    }

    @Test
    public void testReturnLoginStatusFailure() {
        Registration reg = new Registration();
        reg.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");

        Login login = new Login(reg);
        login.loginUser("kyl_1", "wrongPassword");

        assertEquals("Username or password incorrect, please try again.", login.returnLoginStatus("kyl_1"));
    }
}
