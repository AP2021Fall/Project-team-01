package controller.ProfileMenuController;

import controller.LoginController;
import models.DatabaseHandler;
import models.User;
import view.Regex;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProfileMenuController {

    public static String changePassword(String oldPassword, String newPassword) throws SQLException {

        if (!LoginController.getActiveUser().getPassword().equals(oldPassword)) {
            return "wrong old password";
        } else if (oldPassword.equals(newPassword)) {
            return "please enter a new password";
        } else if (!Regex.isPasswordStrong(newPassword)) {
            return "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)";
        } else {
            DatabaseHandler.changePassword(LoginController.getActiveUser().getUsername(), newPassword);
            LoginController.getActiveUser().setPassword(newPassword);
            return "password changed successfully";
        }
    }

    public static String changeUsername(String newUsername, String token) throws SQLException {
        User activeUser = LoginController.getActiveUser();
        if (newUsername.length() < 4)
            return "Your new username must include at least 4 characters!";
        else if (DatabaseHandler.doesUsernameExist(newUsername))
            return "username already taken!";
        else if (!Regex.getCommandMatcher(newUsername, "([\\w\\d_]{4,})").matches())
            return "New username contains Special Characters! Please remove them and try again";
        else if (activeUser.getUsername().equals(newUsername))
            return "you already have this username !";
        else {
            DatabaseHandler.changeUsername(activeUser.getUsername(), newUsername);
            activeUser.setUsername(newUsername);
                return "username successfully changed";
        }
    }

    public static ArrayList<String> showTeams() throws SQLException {
        return (DatabaseHandler.getUserTeams(LoginController.getActiveUser().getUsername()));
    }

    public static String showTeam(String teamName) throws SQLException {
        return (teamName + ":" +"\nleader: " + DatabaseHandler.getLeaderByTeamName(teamName)+"\nmembers: " + DatabaseHandler.getMembersByTeamName(teamName));
    }


    public static String showMyProfile() {
        User user = LoginController.getActiveUser();
        return ("username: " + user.getUsername() + "\nemail address: " + user.getEmail() + "\nrole: " + user.getRole() +
                "\nscore: " + user.getPoint());
    }

    public static String showMyProfile(String username) throws SQLException {
        String email = DatabaseHandler.getEmailByUsername(username);
        String role = DatabaseHandler.getUserRole(username);
        int point = DatabaseHandler.getPointsOfUser(username);
        return ("username: " + username + "\nemail address: " + email + "\nrole: " + role +
                "\nscore: " + point);
    }


    public static String showLogs() throws SQLException {
        ArrayList<LocalDateTime> logs = new ArrayList<>();
        logs = DatabaseHandler.getLogsByUsername(LoginController.getActiveUser().getUsername());
        StringBuilder stringLogs = new StringBuilder();
        for (LocalDateTime log : logs)
            stringLogs.append(log).append("\n");
        return stringLogs.toString();
    }

    public static ArrayList<String> showNotifications(String token) throws SQLException {
        String username = User.getLoginUsers().get(token).getUsername();
        return (DatabaseHandler.getNotifications(username));
    }
}
