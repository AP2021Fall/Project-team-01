package controller;

import models.DatabaseHandler;
import models.User;
import view.ChangeRoleMenu;
import view.MainMenu;
import view.MenuController;
import view.Menus;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class MainMenuController {

    public static int choice;
    public static String team;
    public static String username;
    public static String pendingTeam;
    private static HashMap<String, String> changeRoleUsername = new HashMap<>();

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

    public static String createTeam(String teamName, String token) throws SQLException {
        if (User.getLoginUsers().get(token).getRole().equals("leader")) {
            if (DatabaseHandler.doesTeamNameExist(teamName))
                return ("There is another team with this name!");
            else {
                if (teamName.length()>=5 && teamName.length()<=12 && teamName.matches("[^0-9].*[0-9].*") && teamName.matches(".*[A-Z].*")) {
                    DatabaseHandler.createTeam(teamName, LocalDateTime.now(), User.getLoginUsers().get(token).getUsername());
                    return ("Team created successfully! Waiting For Admin’s confirmation…");
                } else {
                    return ("Team name is invalid!");
                }
            }
        } else {
            return ("you are not leader");
        }
    }

    public static void sendNotificationToUser(String notification, String username) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader") ||
                LoginController.getActiveUser().getRole().equals("admin")) {
            if (!DatabaseHandler.doesUsernameExist(username)) {
                System.out.println("No user exists with this username !");
            } else {
                DatabaseHandler.sendNotificationToUser(LoginController.getActiveUser().getUsername() + ": " +notification, username);
                System.out.println("notification sent successfully");
            }
        } else
            System.out.println("You do not have access to this section");

    }

    public static void sendNotificationToTeam(String notification, String teamName) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader") ||
                LoginController.getActiveUser().getRole().equals("admin")) {
            if (DatabaseHandler.doesTeamExistForUser(teamName, LoginController.getActiveUser().getUsername())
                    || LoginController.getActiveUser().getRole().equals("admin")) {
                DatabaseHandler.sendNotificationToTeam(LoginController.getActiveUser().getUsername() + ": " +notification, teamName);
                System.out.println("notification sent successfully");
            } else {
                System.out.println("No team exists with this name !");
            }
        } else
            System.out.println("You do not have access to this section");
    }

    public static void sendNotificationToAll(String notification) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            DatabaseHandler.sendNotificationToAll(LoginController.getActiveUser().getUsername() + ": " +notification);
            System.out.println("notification sent");
        }
    }

    public static void showProfile(String username) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            if (DatabaseHandler.doesUsernameExist(username)) {
                System.out.println("username: " + username + " email address: " +
                        DatabaseHandler.getEmailByUsername(username) + " role: " +
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
                if (DatabaseHandler.getRoleByUsername(username).equals("leader"))
                    DatabaseHandler.banLeader(username);
                else if (DatabaseHandler.getRoleByUsername(username).equals("member"))
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

    public static void showScoreboard(String teamName) throws SQLException {
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

    public static String changeRole(String username, String newRole) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("admin")) {
            if (DatabaseHandler.doesUsernameExist(username)) {
                String role = DatabaseHandler.getUserRole(username);
                if (DatabaseHandler.getNumberOfTeamsByUsername(username) == 1) {
                    if (newRole.equals("leader") && role.equals("member")) {
                        String teamName = DatabaseHandler.getUserTeams(username).get(0);
                        String preLeader = DatabaseHandler.getLeaderByTeamName(teamName);
                        if (DatabaseHandler.getNumberOfTeamsByUsername(preLeader) > 1)
                            return ("previous leader is joined in more than one team");
                        else {
                            DatabaseHandler.changeRole(username);
                            DatabaseHandler.changeRole(preLeader);
                            return ("role changed successfully");
                        }
                    } else if (newRole.equals("member") && role.equals("leader")){
                        changeRoleUsername = username;
                        return ("now enter a username to replace with this leader in team");
                    } else {
                        return ("invalid role");
                    }
                } else {
                    return ("user must be at exactly one team:)");
                }
            } else {
                return ("There is no user with this username");
            }
        } else {
            return ("you don't have access to this section");
        }
    }

    public static String changeRoleToMember(String newLeaderUsername, String token) throws SQLException {
        if (!DatabaseHandler.doesUsernameExist(newLeaderUsername))
            return ("user not found!");
        else {
            if (DatabaseHandler.getNumberOfTeamsByUsername(newLeaderUsername) != 1) {
                return ("sorry!! selected user must be member at exactly one team");
            } else {
                int teamId = DatabaseHandler.getTeamsIdByUsername(changeRoleUsername.get(token)).get(0);
                if (DatabaseHandler.isUsernameTeamMate(newLeaderUsername, teamId)) {
                    DatabaseHandler.changeRole(newLeaderUsername);
                    DatabaseHandler.changeRole(changeRoleUsername.get(token));
                    return ("Roles changed successfully!");
                } else {
                    return ("this user isn't in your team");
                }
            }
        }
    }


}
