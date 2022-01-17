package view;

import controller.MainMenuController;
import controller.TeamMenuController.TeamSelectionController;

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

    public static void showMainMenu() {
        System.out.println("..........Main Menu.........\n" +
                "valid commands:\n" +
                "enter menu profile menu\n" +
                "enter menu team menu\n" +
                "enter menu tasks page\n" +
                "enter menu calendar menu\n" +
                "show --teams");
    }
}
