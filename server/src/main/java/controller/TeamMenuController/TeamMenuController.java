package controller.TeamMenuController;

import controller.LoginController;
import models.DatabaseHandler;
import models.Team;
import models.User;
import view.Regex;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class TeamMenuController {
    private static HashMap<String, Team> currentTeam = new HashMap<>();

    public static HashMap<String, Team> getCurrentTeam() {
        return currentTeam;
    }

    public static void setCurrentTeam(HashMap<String, Team> currentTeam) {
        TeamMenuController.currentTeam = currentTeam;
    }

    public static void showAllTasksLeader() throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            ArrayList<String> print = DatabaseHandler.getTeamTasksByTeamId(TeamMenuController.getTeam().getId());
            if (print.size() != 0) {
                for (String s : print) {
                    System.out.println(s);
                }
            } else
                System.out.println("no task exist in this team");
        } else
            System.out.println("You do not have the permission to do this action!");
    }


    public static String createTask(String taskTitle, String creationTime, String deadline, String teamName) throws SQLException {
        if (Regex.getCommandMatcher(creationTime, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}").matches()) {
            if (Regex.getCommandMatcher(deadline, "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}").matches()) {
                int teamId = DatabaseHandler.getTeamIdByTeamName(teamName);
                if (DatabaseHandler.doesTaskExist(taskTitle, teamId)) {
                    return ("task title exist already");
                } else {
                    if (isDeadlineAndCreateValid(creationTime, deadline)) {
                        DatabaseHandler.createTask(taskTitle, creationTime, deadline, teamId);
                        return ("task created successfully");
                    } else
                        return "invalid deadline or start date";
                }
            } else
                return ("Invalid deadline");
        } else
            return ("Invalid start date");
    }


    public static boolean isDeadlineAndCreateValid(String creationTime, String deadline) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;
        LocalDateTime end;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        start = LocalDateTime.parse(creationTime, formatter);
        end = LocalDateTime.parse(deadline, formatter);
        if (end.isBefore(now)) {
            System.out.println("deadline has expired please insert a correct deadline");
            return false;
        }
        if (end.isBefore(start)) {
            System.out.println("deadline is before start please insert a correct deadline");
            return false;
        }
        if (start.isBefore(now)) {
            System.out.println("startTime is before now please insert a correct startTime");
            return false;
        }
        return true;
    }

    //show members and leader
    public static ArrayList<String> showMembersLeader(String token) throws SQLException {
        return DatabaseHandler.getMembersByTeamName(TeamMenuController.getCurrentTeam().get(token).getName());
    }

    public static void addMemberToTeam(String name) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesUsernameExist(name)) {
                if (DatabaseHandler.isUserMember(name))
                    if (!(DatabaseHandler.isUserInTeam(name, TeamMenuController.getTeam().getName()))) {
                        DatabaseHandler.addMemberToTeam(name, TeamMenuController.getTeam().getId());
                        System.out.println("member added successfully");
                    } else
                        System.out.println("This user is already in your team");
                else
                    System.out.println("This user has a leader role you cant add it to your team");
            } else
                System.out.println("no user with this username exists");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void deleteMemberFromTeam(String name, String token) throws SQLException {
        int teamId = TeamMenuController.getCurrentTeam().get(token).getId();
        if (User.getLoginUsers().get(token).getRole().equals("leader")) {
            if (DatabaseHandler.doesUsernameExist(name)) {
                if (DatabaseHandler.isUserMember(name))
                    if (DatabaseHandler.isUserInTeam(name, TeamMenuController.getCurrentTeam().get(token).getName()))
                        DatabaseHandler.removeMemberFromTeam(name, teamId);
                    else
                        System.out.println("This user is not in your team to remove it");
                else
                    System.out.println("you cant remove yourself!");
            } else
                System.out.println("no user with this username exists");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void suspendMember(String username) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesUsernameExist(username)) {
                if (DatabaseHandler.isUserMember(username))
                    if (DatabaseHandler.isUserInTeam(username, TeamMenuController.getTeam().getName())) {
                        deleteMemberFromTeam(username);
                        DatabaseHandler.addToSuspendedList(username, TeamMenuController.getTeam().getId());
                    } else
                        System.out.println("This user is not in your team to suspend it");
                else
                    System.out.println("you cant suspend yourself!");
            } else
                System.out.println("no user with this username exists");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    //TODO what the hell is this
//    public static void promoteMember(String username) throws SQLException {
//        if (LoginController.getActiveUser().getRole().equals("leader")) {
//            if (DatabaseHandler.doesUsernameExist(username)) {
//                if (DatabaseHandler.isUserMember(username))
//                    if (DatabaseHandler.isUserInTeam(username, TeamMenuController.getTeam().getName())) {
//                        MainMenuController.changeRole(username, "leader");
//                    } else
//                        System.out.println("This user is not in your team to promote it");
//                else
//                    System.out.println("you cant promote yourself!");
//            } else
//                System.out.println("no user with this username exists");
//        } else
//            System.out.println("You do not have the permission to do this action!");
//    }

    //we use task title for assigning instead task id
    public static void assignMemberToTask(String taskTitle, String username) throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            if (DatabaseHandler.doesUsernameExist(username)) {
                if (DatabaseHandler.isUserInTeam(username, TeamMenuController.getTeam().getName())) {
                    if (DatabaseHandler.doesTaskExist(taskTitle, TeamMenuController.getTeam().getId())) {
                        int taskId = DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId());
                        if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskId))) {
                            if (!DatabaseHandler.isUsernameAssigned(taskId, username)) {
                                DatabaseHandler.assignUser(taskId, username);
                                System.out.println("task assigned successfully");
                            } else {
                                System.out.println("member is already assigned");
                            }
                        } else
                            System.out.println("this task doesnt belong to this team");
                    } else
                        System.out.println("no task exist with this id in any teams");
                } else
                    System.out.println("This user is not in your team to assign");
            } else
                System.out.println("No user exists with this username!");
        } else
            System.out.println("You do not have the permission to do this action!");
    }

    public static void showScoreboardToLeader() throws SQLException {
        if (LoginController.getActiveUser().getRole().equals("leader")) {
            ScoreBoardController.showScoreboard();
        } else
            System.out.println("You do not have the permission to do this action!");
    }
}
