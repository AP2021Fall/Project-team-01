package view;

import controller.MainMenuController;
import controller.TeamMenuController.TeamSelectionController;
import view.ProfileMenu.ProfileMenu;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class MainMenu {
    public static void execute(String command) throws SQLException {
        Matcher showTeamsMatcher = Regex.getCommandMatcher(command, "^enter\\smenu\\s(.+)$");
        Matcher createTeam = Regex.getCommandMatcher(command, "create\\s--team\\s(.+)");
        Matcher sendNotificationToUser = Regex.getCommandMatcher(command,
                "send --notification (.+) --username (.+)");
        Matcher sendNotificationToTeam = Regex.getCommandMatcher(command,
                "send --notification (.+) --team (.+)");
        Matcher sendNotificationToAll = Regex.getCommandMatcher(command,
                "send --notification (.+) --all");
        Matcher showProfile = Regex.getCommandMatcher(command, "show profile --username (.+)");
        Matcher banUser = Regex.getCommandMatcher(command, "ban user --user (.+)");
        Matcher acceptTeams = Regex.getCommandMatcher(command, "accept --teams (.+)");
        Matcher rejectTeams = Regex.getCommandMatcher(command, "reject --teams (.+)");
        Matcher showScoreboard = Regex.getCommandMatcher(command, "show score board --team name (.+)");
        Matcher changeRole = Regex.getCommandMatcher(command, "^change role --user (.+) --role (.+)$");
        if (showTeamsMatcher.find()) {
            String menu = showTeamsMatcher.group(1);
            switch (menu) {
                case "profile menu":
                    MenuController.currentMenu = Menus.PROFILE_MENU;
                    ProfileMenu.showProfileMenu();
                    break;
                case "team menu":
                    MenuController.currentMenu = Menus.TEAM_SELECTION;
                    TeamSelectionController.showTeams();
                    break;
                case "tasks page":
                    MenuController.currentMenu = Menus.TASKS_PAGE;
                    TasksPageMenu.showTaskPageMenu();
                    break;
                case "calendar menu":
                    MenuController.currentMenu = Menus.CALENDAR_MENU;
                    CalendarMenu.showCalendarMenu();
                    break;
            }
        } else if (command.equals("show --teams")) {
            MainMenuController.showTeams();
        } else if (createTeam.find()) {
            MainMenuController.createTeam(createTeam.group(1));
        } else if (sendNotificationToUser.find()) {
            MainMenuController.sendNotificationToUser(sendNotificationToUser.group(1), sendNotificationToUser.group(2));
        } else if (sendNotificationToTeam.find()) {
            MainMenuController.sendNotificationToTeam(sendNotificationToTeam.group(1), sendNotificationToTeam.group(2));
        } else if (command.equals("logout")) {
            LoginMenu.showLogin();
            MenuController.currentMenu = Menus.LOGIN_MENU;
        } else if (showProfile.find()) {
            MainMenuController.showProfile(showProfile.group(1));
        } else if (banUser.find()) {
            MainMenuController.banUser(banUser.group(1));
        } else if (sendNotificationToAll.find()) {
            MainMenuController.sendNotificationToAll(sendNotificationToAll.group(1));
        } else if (command.equals("show --pendingTeams")) {
            MainMenuController.showPendingTeams();
        } else if (acceptTeams.find()) {
            String[] teams = acceptTeams.group(1).split("\\s+");
            MainMenuController.acceptTeams(teams);
        } else if (rejectTeams.find()) {
            String[] teams = rejectTeams.group(1).split("\\s+");
            MainMenuController.rejectTeams(teams);
        } else if (showScoreboard.find()) {
            MainMenuController.showScoreboard(showScoreboard.group(1));
        } else if (changeRole.find()){
            MainMenuController.changeRole(changeRole.group(1), changeRole.group(2));
        } else {
            System.out.println("invalid command");
        }

    }

    public static void show() {
        System.out.println("menus");
        System.out.println("profile menu");
        System.out.println("team menu");
        System.out.println("tasks page");
        System.out.println("calendar menu");
    }

    public static void showMainMenu() {
        System.out.println("..........Main Menu.........\n" +
                "valid commands:\n" +
                "enter menu profile menu\n" +
                "enter menu team menu\n" +
                "enter menu tasks page\n" +
                "enter menu calendar menu\n" +
                "show --teams\n" +
                "change role --user <username> --role <new role>");
    }
}
