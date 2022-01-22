import controller.LoginController;
import controller.TasksPageController;
import models.DatabaseHandler;
import models.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class TestClass {
//    @BeforeAll
//    void init() throws SQLException {
//        User member = new User("ali", "Ali12345", "ali@gmail.com", "member");
//        DatabaseHandler.createUser("ali", "Ali12345", "ali@gmail.com", "member");
//        LoginController.setActiveUser(member);
//        User leader = new User("sara", "sara12345", "sara@gmail.com", "leader");
//        DatabaseHandler.createUser("sara", "sara12345", "sara@gmail.com", "leader");
//        DatabaseHandler.createTeam("team1", LocalDateTime.now(), "sara");
//        DatabaseHandler.createTask("task1", "2022-01-30 23:59:00", "2022-03-30 23:59:00", 1);
//    }

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

    @Test
    void EditTitleTest() throws SQLException {
//        User member = new User("ali", "Ali12345", "ali@gmail.com", "member");
//        LoginController.setActiveUser(member);
//        DatabaseHandler.createUser("ali", "Ali12345", "ali@gmail.com", "member");

        int taskId = DatabaseHandler.getTaskIdByTaskTitle ("task1", 1);
        Assertions.assertEquals("task with id: " + taskId + " doesn't exist!", TasksPageController.editTitle(taskId, "newTitle"));
        Assertions.assertEquals("you don't have access to do this action!", TasksPageController.editTitle(taskId, "newTitle"));

//        User leader = new User("sara", "sara12345", "sara@gmail.com", "leader");
//        LoginController.setActiveUser(leader);
//        DatabaseHandler.createUser("sara", "sara12345", "sara@gmail.com", "leader");
        Assertions.assertEquals("title updated successfully!", TasksPageController.editTitle(taskId, "newTitle"));
    }


}
