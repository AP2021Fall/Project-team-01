package controller;

import models.DatabaseHandler;
import models.User;
import view.Regex;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class MainMenuController {

    public static void showTeams() throws SQLException {
        User user = LoginController.getActiveUser();
        if (user.getRole().equalsIgnoreCase("leader")) {
            ArrayList<String> arrayList = DatabaseHandler.getUserTeams(user.getUsername());
            if (arrayList.isEmpty())
                System.out.println("There is no team for you!");
            else {
                int counter = 0;
                while (counter < arrayList.size()) {
                    System.out.println((counter + 1) + "- " + arrayList.get(counter));
                    counter++;
                }
            }
        } else {
            System.out.println("you are not leader");
        }
    }

    public static void createTeam(String teamName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesTeamExist(teamName, LoginController.getActiveUser().getUsername()))
                System.out.println("There is another team with this name!");
            else {
                Matcher matcher = Regex.getCommandMatcher(teamName, Regex.TEAM_NAME);
                if (matcher.find()) {
                    DatabaseHandler.createTeam(teamName, LocalDateTime.now(), LoginController.getActiveUser().getUsername());
                    System.out.println("Team created successfully! Waiting For Admin’s confirmation…");
                } else {
                    System.out.println("Team name is invalid!");
                }
            }
        } else {
            System.out.println("you are not leader");
        }
    }

    public static void sendNotificationToUser(String notification, String username) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")){
            if (!DatabaseHandler.doesUsernameExist(username))  {
                System.out.println("No user exists with this username !");
            } else {
                DatabaseHandler.sendNotificationToUser(notification, username);
                System.out.println("notification sent successfully");
            }
        } else
            System.out.println("you are not leader");

    }

    public static void sendNotificationToTeam(String notification, String teamName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesTeamExist(teamName, LoginController.getActiveUser().getUsername())) {
                DatabaseHandler.sendNotificationToTeam(notification, teamName);
                System.out.println("notification sent successfully");
            } else {
                System.out.println("No team exists with this name !");
            }
        }
    }

    public static void showProfile(String username) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            if (DatabaseHandler.doesUsernameExist(username)) {
                System.out.println("username: " + username + "email address: " +
                        DatabaseHandler.getEmailByUsername(username) + "role: " +
                        DatabaseHandler.getRoleByUsername(username));
            } else {
                System.out.println("There is no user with this username");
            }
        } else
            System.out.println("You do not have access to this section");
    }

    public static void banUser(String username) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            if (DatabaseHandler.doesUsernameExist(username)) {
                DatabaseHandler.banUser(username);
                System.out.println("username banned");
            } else
                System.out.println("There is no user with this username");
        } else
            System.out.println("You do not have access to this section");
    }
}
