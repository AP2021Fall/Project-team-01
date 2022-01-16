package view;

import controller.MainMenuController;

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
        Matcher showProfile = Regex.getCommandMatcher(command, "show profile --username (.+)");
        Matcher banUser = Regex.getCommandMatcher(command, "ban user --user (.+)");
        if (showTeamsMatcher.find()) {
            String menu = showTeamsMatcher.group(1);
            switch (menu) {
                case "profile menu":
                    MenuController.currentMenu = Menus.PROFILE_MENU;
                    break;
                case "team menu":
                    MenuController.currentMenu = Menus.TEAM_MENU;
                    break;
                case "tasks page":
                    MenuController.currentMenu = Menus.TASKS_PAGE;
                    break;
                case "calendar menu":
                    MenuController.currentMenu = Menus.CALENDAR_MENU;
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
        } else if (command.equals("exit")) {
            MenuController.currentMenu = Menus.EXIT;
        } else if (showProfile.find()) {
            MainMenuController.showProfile(showProfile.group(1));
        } else if (banUser.find()) {
            MainMenuController.banUser(banUser.group(1));
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

}
