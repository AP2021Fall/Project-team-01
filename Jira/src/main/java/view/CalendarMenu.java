package view;

import controller.CalendarMenuController;

import java.sql.SQLException;

public class CalendarMenu {
    public static void execute(String command) throws SQLException {
        if (Regex.getCommandMatcher(command, Regex.SHOW_DEADLINES).matches()){
            CalendarMenuController.showDeadlines();
        } else if (command.equals("exit")) {
            MenuController.currentMenu = Menus.EXIT;
            LoginMenu.showLogin();
        } else if (command.equals("back")) {
            MenuController.currentMenu = Menus.MAIN_MENU;
            MainMenu.showMainMenu();
        } else {
            System.out.println(Regex.INVALID_COMMAND);
        }
    }
    public static void showCalendarMenu(){
        System.out.println("..........Calendar Menu..........\n" +
                "valid commands:\n" +
                "calendar --show deadlines\n" +
                "back\n" +
                "exit");
    }
}
