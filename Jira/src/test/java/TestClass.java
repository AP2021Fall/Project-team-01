import controller.LoginController;
import models.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.sql.SQLException;

public class TestClass {
    @BeforeAll
    void init(){
        User member = new User("ali", "Ali12345", "ali@gmail.com", "member");
        LoginController.setActiveUser(member);
        User admin = new User("sara", "sara12345", "sara@gmail.com", "admin");
    }

    @Test
    void CreateUserTest() throws SQLException {
        Assertions.assertEquals("user with username ali already exists!", LoginController.createUser("ali", "Ali12345", "Ali12345", "ali@gmail.com", "member"));
        Assertions.assertEquals("Your passwords are not the same!", LoginController.createUser("a", "Ali12345", "Ali11111", "ali@gmail.com", "member"));
        Assertions.assertEquals("User with this email already exists!", LoginController.createUser("a", "Ali12345", "Ali12345", "ali@gmail.com", "member"));
        Assertions.assertEquals("Email address is invalid!", LoginController.createUser("a", "Ali12345", "Ali12345", "a@ali.com", "member"));
        Assertions.assertEquals("user created successfully!", LoginController.createUser("a", "Ali12345", "Ali12345", "a@gmail.com", "member"));
    }

    @Test
    void LoginUserTest() throws SQLException {
        Assertions.assertEquals("There is not any user with username: z!", LoginController.loginUser("b", "Ali12345"));
        Assertions.assertEquals("Username and password didn't match!", LoginController.loginUser("ali", "12345678"));
        Assertions.assertEquals("user logged in successfully!", LoginController.loginUser("ali", "Ali12345"));
        Assertions.assertEquals("ali", LoginController.getActiveUser().getUsername());
    }


}
