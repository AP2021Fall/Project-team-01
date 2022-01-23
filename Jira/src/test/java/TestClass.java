import controller.LoginController;
import controller.TasksPageController;
import models.DatabaseHandler;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class TestClass {
    @BeforeAll
    static void init() throws SQLException {
        DatabaseHandler.connect();
        DatabaseHandler.createUser("ali", "Ali12345", "ali@gmail.com", "member");
        DatabaseHandler.createUser("sara", "123", "sara@gmail.com", "leader");
        DatabaseHandler.createTeam("team1", LocalDateTime.now(), "sara");
        int teamId = DatabaseHandler.getTeamIdByTeamName("team1");
        DatabaseHandler.createTask("task1", "2022-01-01 23:59:00", "2022-06-01 23:59:00", teamId);
        DatabaseHandler.addMemberToTeam("ali", teamId);

    }

    @Test
    void CreateUserTest() throws SQLException {
        Assertions.assertEquals("user with username ali already exists!", LoginController.createUser("ali", "Ali12345", "Ali12345", "ali@gmail.com", "member"));
        Assertions.assertEquals("Your passwords are not the same!", LoginController.createUser("reza", "Ali12345", "Ali11111", "ali@gmail.com", "member"));
        Assertions.assertEquals("User with this email already exists!", LoginController.createUser("reza", "Ali12345", "Ali12345", "ali@gmail.com", "member"));
        Assertions.assertEquals("Email address is invalid!", LoginController.createUser("reza", "Ali12345", "Ali12345", "a@ali.com", "member"));
        Assertions.assertEquals("user created successfully!", LoginController.createUser("reza", "Ali12345", "Ali12345", "a@gmail.com", "member"));
    }

    @Test
    void LoginUserTest() throws SQLException {
        Assertions.assertEquals("There is not any user with username: b!", LoginController.loginUser("b", "Ali12345"));
        Assertions.assertEquals("Username and password didn't match!", LoginController.loginUser("ali", "12345678"));
        Assertions.assertEquals("user logged in successfully!", LoginController.loginUser("ali", "Ali12345"));
        Assertions.assertEquals("ali", LoginController.getActiveUser().getUsername());
    }

    @Test
    void  EditTitleTest() throws SQLException {
        int teamId = DatabaseHandler.getTeamIdByTeamName("team1");
        int taskId = DatabaseHandler.getTaskIdByTaskTitle("task1", teamId);
        Assertions.assertEquals("task with id: "+1000+" doesn't exist!", TasksPageController.editTitle(1000, "newTask1"));
        LoginController.setActiveUser(new User("ali", "Ali12345", "ali@gmail.com", "member"));
        Assertions.assertEquals("you don't have access to do this action!", TasksPageController.editTitle(taskId, "newTask1"));
        LoginController.setActiveUser(new User("sara", "123", "sara@gmail.com", "leader"));
        Assertions.assertEquals("title updated successfully!", TasksPageController.editTitle(taskId, "newTask1"));
        TasksPageController.editTitle(taskId, "task1");
    }

    @Test
    void EditDescriptionTest() throws SQLException {
        int teamId = DatabaseHandler.getTeamIdByTeamName("team1");
        int taskId = DatabaseHandler.getTaskIdByTaskTitle("task1", teamId);
//        LoginController.loginUser("sara", "123");
        LoginController.setActiveUser(new User("sara", "123", "sara@gmail.com", "leader"));
        Assertions.assertEquals("Description updated successfully!", TasksPageController.editDescription(taskId, "this is task one"));
    }

    @Test
    void EditPriorityTest() throws SQLException {
        int teamId = DatabaseHandler.getTeamIdByTeamName("team1");
        int taskId = DatabaseHandler.getTaskIdByTaskTitle("task1", teamId);
        LoginController.setActiveUser(new User("sara", "123", "sara@gmail.com", "leader"));
        Assertions.assertEquals("Priority updated successfully!", TasksPageController.editPriority(taskId, "High"));
    }

    @Test
    void EditDeadlineTest() throws SQLException {
        int teamId = DatabaseHandler.getTeamIdByTeamName("team1");
        int taskId = DatabaseHandler.getTaskIdByTaskTitle("task1", teamId);
        LoginController.setActiveUser(new User("sara", "123", "sara@gmail.com", "leader"));
        Assertions.assertEquals("deadline changed successfully", TasksPageController.editDeadline(taskId, "2022-03-01 23:59:00"));
    }

    @Test
    void AddAssignedUserTest() throws SQLException {
        int teamId = DatabaseHandler.getTeamIdByTeamName("team1");
        int taskId = DatabaseHandler.getTaskIdByTaskTitle("task1", teamId);
        LoginController.setActiveUser(new User("sara", "123", "sara@gmail.com", "leader"));
        Assertions.assertEquals("user: ali added successfully!", TasksPageController.addAssignedUser(taskId, "ali"));
    }

    @Test
    void RemoveAssignedUserTest() throws SQLException {
        int teamId = DatabaseHandler.getTeamIdByTeamName("team1");
        int taskId = DatabaseHandler.getTaskIdByTaskTitle("task1", teamId);
        LoginController.setActiveUser(new User("sara", "123", "sara@gmail.com", "leader"));
        Assertions.assertEquals("user: ali removed successfully!", TasksPageController.removeAssignedUser(taskId, "ali"));
    }

    @Test
    void IsDeadlineValidTest(){
        Assertions.assertNull(TasksPageController.isDeadlineValid(LocalDateTime.now(), "2020-01-01|23:59"));
    }

}
