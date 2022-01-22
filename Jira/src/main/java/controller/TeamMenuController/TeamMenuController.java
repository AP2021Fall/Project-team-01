package controller.TeamMenuController;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import controller.LoginController;
import controller.MainMenuController;
import models.DatabaseHandler;
import models.Team;
import view.Regex;
import view.TeamMenu.TeamMenu;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class TeamMenuController {
    private static Team currentTeam;

    public static Team getTeam() {
        return currentTeam;
    }

    public static void setTeam(Team team) {
        TeamMenuController.currentTeam = team;
    }

    public static void showTeamMenu() {
        System.out.println("Enter Menu");
        System.out.println("Scoreboard");
        System.out.println("BoardMenu");
        System.out.println("Roadmap");
        System.out.println("ChatRoom");
        System.out.println("Tasks");
        System.out.println("back");
    }

    public static void showAllTasksLeader() {
        if (LoginController.getActiveUser().getUsername().equals("leader")) {
            if (DatabaseHandler.doesTaskExistInTeam(TeamMenuController.getTeam().getId())) {
                ArrayList<String> print = DatabaseHandler.showTeamTasks(TeamMenuController.getTeam().getId());
                for (String s : print) {
                    System.out.println(s);
                }
            } else
                System.out.println("no task exist in this team");
        } else
            System.out.println("You do not have the permission to do this action!");
    }


    public static void createTask(String taskTitle, String creationTime, String deadline) throws SQLException {

        if (Regex.getCommandMatcher(creationTime, "yyyy-MM-dd HH:mm:ss").matches()) {
            if (Regex.getCommandMatcher(deadline, "yyyy-MM-dd HH:mm:ss").matches()) {
                if (DatabaseHandler.doesTaskExist(taskTitle)) {
                    int taskId = DatabaseHandler.getTaskIdByTaskTitle(taskTitle, TeamMenuController.getTeam().getId());
                    if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskId))) {
                        System.out.println("There is another task with this title!");
                    } else {
                        DatabaseHandler.createTaskLeader(taskTitle, creationDate, deadlineDate, TeamMenuController.getTeam().getId());
                    }
                } else
                    DatabaseHandler.createTaskLeader(taskTitle, creationDate, deadlineDate, TeamMenuController.getTeam().getId());
            } else
                System.out.println("Invalid deadline");
        } else
            System.out.println("Invalid start date");
    }


    public static void showMembersLeader() {
        if (DatabaseHandler.doesTeamHaveMember(TeamMenuController.getTeam().getId())) {
            ArrayList<String> print = DatabaseHandler.showTeamMembers(TeamMenuController.getTeam().getName());
            for (String s : print)
                System.out.println(s);
        } else
            System.out.println("This team has no member");
    }

    public static void addMemberToTeam(String name) throws SQLException {
        if (DatabaseHandler.doesUsernameExist(name)) {
            if (DatabaseHandler.isUserMember(name))
                if ( ! (DatabaseHandler.isUserInTeam(name, TeamMenuController.getTeam().getId())))
                    DatabaseHandler.addMemberToTeam(name, TeamMenuController.getTeam().getId());
                else
                    System.out.println("This user is already in your team");
            else
                System.out.println("This user has a leader role you cant add it to your team");
        } else
            System.out.println("no user with this username exists");
    }

    public static void deleteMemberFromTeam(String group) {
        if (DatabaseHandler.doesUsernameExist(name)) {
            if (DatabaseHandler.isUserMember(name))
                if (DatabaseHandler.isUserInTeam(name, TeamMenuController.getTeam().getId()))
                    DatabaseHandler.removeMemberFromTeam(name, TeamMenuController.getTeam().getId());
                else
                    System.out.println("This user is not in your team to remove it");
            else
                System.out.println("you cant remove yourself!");
        } else
            System.out.println("no user with this username exists");
    }

    public static void suspendMember(String username) throws SQLException {
        if (DatabaseHandler.doesUsernameExist(username)) {
            if (DatabaseHandler.isUserMember(username))
                if (DatabaseHandler.isUserInTeam(username, TeamMenuController.getTeam().getId())) {
                    deleteMemberFromTeam(username);
                    DatabaseHandler.addToSuspendedList(username , TeamMenuController.getTeam().getId());
                }
                else
                    System.out.println("This user is not in your team to suspend it");
            else
                System.out.println("you cant suspend yourself!");
        } else
            System.out.println("no user with this username exists");
    }

    public static void promoteMember(String username) throws SQLException {
        if (DatabaseHandler.doesUsernameExist(username)) {
            if (DatabaseHandler.isUserMember(username))
                if (DatabaseHandler.isUserInTeam(username, TeamMenuController.getTeam().getId())) {
                   MainMenuController.changeRole(username , "leader");
                }
                else
                    System.out.println("This user is not in your team to promote it");
            else
                System.out.println("you cant promote yourself!");
        } else
            System.out.println("no user with this username exists");
    }

    public static void assignMemberToTask( String taskTitle , String username) throws SQLException {
        if (DatabaseHandler.doesUsernameExist(username)) {
                if (DatabaseHandler.isUserInTeam(username, TeamMenuController.getTeam().getId())) {
                    if (DatabaseHandler.doesTaskExist(taskTitle)) {
                        int taskId = DatabaseHandler.getTaskIdByTaskTitle(taskTitle , TeamMenuController.getTeam().getId());
                        if (LoginController.getActiveUser().getUsername().equals(DatabaseHandler.getTaskLeaderByTaskId(taskId)))
                            DatabaseHandler.assignMemberToTaskByLeader(username, taskId);
                        else
                            System.out.println("this task doesnt belong to this team");
                    }else
                        System.out.println("no task exist with this id in any teams");
                }
                else
                    System.out.println("This user is not in your team to assign");
        } else
            System.out.println("No user exists with this username!");
    }

    public static void showScoreboardToLeader() throws SQLException {
        ScoreBoardController.showScoreboard();
    }
}
