package view;

import controller.MainMenuController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class MainMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher = Regex.getCommandMatcher(command, "^enter\\smenu\\s(.+)$");
        if (matcher.find()) {
            String menu = matcher.group(1);
            switch (menu) {
                case "profile menu":
                    MenuController.currentMenu = Menus.PROFILE_MENU;
                    ProfileMenu.showProfileMenu();
                    break;
                case "team menu":
                    MenuController.currentMenu = Menus.TEAM_MENU;
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
                    break;
            }
        } else if (command.equals("show --teams")) {
            MainMenuController.showTeams();
        }

    }

    public static void showMainMenu(){}

}
