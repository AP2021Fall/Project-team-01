package controller;

import models.DatabaseHandler;
import models.User;
import view.MainMenu;
import view.MenuController;
import view.Menus;
import view.Regex;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class LoginController {
    private static User activeUser;

    public static User getActiveUser() {
        return activeUser;
    }

    public static void setActiveUser(User activeUser) {
        LoginController.activeUser = activeUser;
    }

    public static String loginUser(String username, String password) throws SQLException {
        if (!DatabaseHandler.doesUsernameExist(username))
            return ("There is not any user with username: " + username + "!");
        else if (!DatabaseHandler.getPasswordByUsername(username).equals(password))
            return ("Username and password didn't match!");
        String currentUserEmail = DatabaseHandler.getEmailByUsername(username);
        String currentUserRole = DatabaseHandler.getRoleByUsername(username);
        User currentUser = new User(username, password, currentUserEmail, currentUserRole);
        setActiveUser(currentUser);
        DatabaseHandler.logLogin(activeUser.getUsername(), LocalDateTime.now());
        MainMenu.show();
        MainMenu.showMainMenu();
        MenuController.currentMenu = Menus.MAIN_MENU;
        return ("user logged in successfully!");
    }

    public static String createUser(String username, String password, String confirmPassword,
                                    String email, String role) throws SQLException {
        String EMAIL_FORMAT = "^[a-zA-Z0-9.]+@(gmail||yahoo).com$";

        if (DatabaseHandler.doesUsernameExist(username))
            return ("user with username " + username + " already exists!");
        else if (!password.equals(confirmPassword))
            return ("Your passwords are not the same!");
        else if (DatabaseHandler.doesEmailExist(email))
            return ("User with this email already exists!");
        else if (!Regex.getCommandMatcher(email, EMAIL_FORMAT).matches())
            return ("Email address is invalid!");
        else if (!(role.equals("member") || role.equals("leader") || role.equals("admin")))
            return "invalid role";
        else {
            DatabaseHandler.createUser(username, password, email, role);
            return ("user created successfully!");
        }
    }
}
