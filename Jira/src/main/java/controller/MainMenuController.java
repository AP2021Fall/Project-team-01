package controller;

import controller.TeamMenuController.TeamMenuController;
import models.DatabaseHandler;
import models.User;
import view.ChangeRoleMenu;
import view.MenuController;
import view.Menus;
import view.Regex;

import javax.xml.crypto.Data;
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
            if (DatabaseHandler.doesTeamNameExist(teamName))
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
        if (LoginController.getActiveUser().getRole().equals("leader") ||
                LoginController.getActiveUser().getRole().equals("admin")){
            if (!DatabaseHandler.doesUsernameExist(username))  {
                System.out.println("No user exists with this username !");
            } else {
                DatabaseHandler.sendNotificationToUser(notification, username);
                System.out.println("notification sent successfully");
            }
        } else
            System.out.println("You do not have access to this section");

    }

    public static void sendNotificationToTeam(String notification, String teamName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader") ||
                LoginController.getActiveUser().getRole().equals("admin")) {
            if (DatabaseHandler.doesTeamExistForUser(teamName, LoginController.getActiveUser().getUsername())) {
                DatabaseHandler.sendNotificationToTeam(notification, teamName);
                System.out.println("notification sent successfully");
            } else {
                System.out.println("No team exists with this name !");
            }
        } else
            System.out.println("You do not have access to this section");
    }

    public static void sendNotificationToAll(String notification) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            DatabaseHandler.sendNotificationToAll(notification);
            System.out.println("notification sent");
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
                if (LoginController.getActiveUser().getRole().equals("leader"))
                    DatabaseHandler.banLeader(username);
                else if (LoginController.getActiveUser().getRole().equals("member"))
                    DatabaseHandler.banMember(username);
                System.out.println("username banned");
            } else
                System.out.println("There is no user with this username");
        } else
            System.out.println("You do not have access to this section");
    }

    public static void showPendingTeams() throws SQLException {
        ArrayList<String> pendingTeams = DatabaseHandler.getPendingTeams();
        for (String i : pendingTeams) {
            System.out.println(i);
        }
    }

    public static void acceptTeams(String[] teams) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            System.out.println(DatabaseHandler.acceptPendingTeams(teams));
        } else {
            System.out.println("You do not have access to this section");
        }
    }

    public static void rejectTeams(String[] teams) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            System.out.println(DatabaseHandler.rejectPendingTeams(teams));
        } else {
            System.out.println("You do not have access to this section");
        }
    }
    public static void showScoreboard (String teamName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            if (DatabaseHandler.doesTeamExist(teamName)) {
                ArrayList<String> show = DatabaseHandler.showScoreboard(teamName);
                for (int i = 0; i < show.size(); i++)
                    System.out.println(i + " " + show.get(i));
            } else {
                System.out.println("team does not exist");
            }
        } else {
            System.out.println("You do not have access to this section");
        }
    }

    public static void changeRole (String username, String newRole) throws SQLException{
        if (LoginController.getActiveUser().getRole().equals("admin")){
            if (DatabaseHandler.doesUsernameExist(username)){
                if (DatabaseHandler.getNumberOfTeamsByUsername(username) < 1){
                    if (newRole.equals("leader")){
                        String teamName = DatabaseHandler.getUserTeams(username).get(0);
                        String preLeader = DatabaseHandler.getLeaderByTeamName(teamName);
                        if(DatabaseHandler.getNumberOfTeamsByUsername(preLeader)>1)
                            System.out.println("previous leader is joined in more than one team");
                        else{
                            DatabaseHandler.changeRole(username);
                            DatabaseHandler.changeRole(preLeader);
                            System.out.println("User:" + username + "'s role changed to"+ newRole);
                        }
                    }
                    else {
                        System.out.println("now enter a username to replace with this leader in team");
                        MenuController.currentMenu = Menus.CHANGE_ROLE_MENU;
                        ChangeRoleMenu.showChangeRoleMenu();
                        MenuController.setChangeRoleUsername(username);
                    }
                }else{
                    System.out.println("this user is joined in more than one team");
                }
            }else{
                System.out.println("There is no user with this username");
            }
        }else {
            System.out.println("you don't have access to this section");
        }
    }

    public static void changeRoleToMember(String newLeaderUsername) throws SQLException {
        if (!DatabaseHandler.doesUsernameExist(newLeaderUsername))
            System.out.println("user not found!");
        else {
            if (DatabaseHandler.getNumberOfTeamsByUsername(newLeaderUsername)>1){
                System.out.println("this user is a member of more than one team");
            }else {
                DatabaseHandler.changeRole(newLeaderUsername);
                DatabaseHandler.changeRole(MenuController.getChangeRoleUsername());
                System.out.println("Roles changed successfully!");
            }
        }
    }


}
