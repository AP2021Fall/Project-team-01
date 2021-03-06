package controller.ProfileMenuController;

import controller.LoginController;
import models.DatabaseHandler;
import models.User;
import view.Regex;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProfileMenuController {

    public static String changePassword(String oldPassword, String newPassword, String token) throws SQLException {
        if (!User.getLoginUsers().get(token).getPassword().equals(oldPassword)) {
            return "wrong old password";
        } else if (oldPassword.equals(newPassword)) {
            return "please enter a new password";
        } else if (!Regex.isPasswordStrong(newPassword)) {
            return "Please Choose A strong Password (Containing at least 8 characters including 1 digit and 1 Capital Letter)";
        } else {
            DatabaseHandler.changePassword(User.getLoginUsers().get(token).getUsername(), newPassword);
            User.getLoginUsers().get(token).setPassword(newPassword);
            return "password changed successfully";
        }
    }

    public static String changeUsername(String newUsername, String token) throws SQLException {
        User activeUser = User.getLoginUsers().get(token);
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

    public static ArrayList<String> showTeams(String token) throws SQLException {
        return (DatabaseHandler.getUserTeams(User.getLoginUsers().get(token).getUsername()));
    }

    public static String showTeam(String teamName) throws SQLException {
        return (teamName + ":" + "\nleader: " + DatabaseHandler.getLeaderByTeamName(teamName) + "\nmembers: " + DatabaseHandler.getMembersByTeamName(teamName));
    }

    public static String showMyProfile(String token) {
        User user = User.getLoginUsers().get(token);
        return ("username: " + user.getUsername() + "\nemail address: " + user.getEmail() + "\nrole: " + user.getRole() +
                "\nscore: " + user.getPoint());
    }

    public static String showProfile(String username) throws SQLException {
        String email = DatabaseHandler.getEmailByUsername(username);
        String role = DatabaseHandler.getUserRole(username);
        int point = DatabaseHandler.getPointsOfUser(username);
        return ("username: " + username + "\nemail address: " + email + "\nrole: " + role +
                "\nscore: " + point);
    }


    public static String showLogs(String token) throws SQLException {
        ArrayList<LocalDateTime> logs = new ArrayList<>();
        logs = DatabaseHandler.getLogsByUsername(User.getLoginUsers().get(token).getUsername());
        StringBuilder stringLogs = new StringBuilder();
        for (LocalDateTime log : logs)
            stringLogs.append(log).append("\n");
        return stringLogs.toString();
    }

    public static String showNotifications(String token) throws SQLException {
        String username = User.getLoginUsers().get(token).getUsername();
        ArrayList<String> notifications = DatabaseHandler.getNotifications(username);
        StringBuilder stringNotifications = new StringBuilder();
        for (String notification : notifications)
            stringNotifications.append(notification).append("\n");
        return stringNotifications.toString();
    }
}
