package view;

import controller.MainMenuController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class MainMenu {
    public static void execute(String command) throws SQLException {
        Matcher showTeamsMatcher = Regex.getCommandMatcher(command, "^enter\\smenu\\s(.+)$");
        Matcher showTeamMatcher = Regex.getCommandMatcher(command, "^show\\s--team\\s(.+)$");
        Matcher createTeam = Regex.getCommandMatcher(command, "create\\s--team\\s(.+)");
        if (showTeamsMatcher.find()) {
            String menu = showTeamsMatcher.group(1);
            switch (menu){
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
                case "notification bar":
                    MenuController.currentMenu = Menus.NOTIFICATION_BAR;
                    break;
            }
        } else if(command.equals("show --teams")) {
            MainMenuController.showTeams();
        } else if (showTeamMatcher.find()) {
            MainMenuController.showTeam(showTeamMatcher.group(1));
        }

    }

}
