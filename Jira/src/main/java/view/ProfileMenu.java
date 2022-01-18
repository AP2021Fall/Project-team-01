package view;

import controller.ProfileMenuController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class ProfileMenu {

    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command, Regex.CHANGE_PASSWORD)).matches()) {
            System.out.println(ProfileMenuController.changePassword(matcher.group(1), matcher.group(2)));
            MenuController.currentMenu = Menus.LOGIN_MENU;
            LoginMenu.showLogin();
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.CHANGE_USERNAME)).matches()) {
            System.out.println(ProfileMenuController.changeUsername(matcher.group(1)));
        } else if (Regex.getCommandMatcher(command, Regex.SHOW_TEAMS).matches()) {
            ProfileMenuController.showTeams();
        } else if (Regex.getCommandMatcher(command, Regex.SHOW_TEAM).matches()) {
            ProfileMenuController.showTeam(matcher.group(1));
        } else if ( Regex.getCommandMatcher(command, Regex.SHOW_PROFILE).matches()) {
            ProfileMenuController.showMyProfile();
        } else if ( Regex.getCommandMatcher(command, Regex.SHOW_LOG).matches()) {
            ProfileMenuController.showLogs();
        } else if ( Regex.getCommandMatcher(command, Regex.SHOW_NOTIFICATIONS).matches()) {
            ProfileMenuController.showNotifications();
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

    public static void showProfileMenu(){
        System.out.println("..........Profile Menu..........\n" +
                "valid commands:\n" +
                "Profile --showTeams\n" +
                "Profile --showTeam <team name>\n" +
                "Profile --show --myProfile\n" +
                "profile --show logs\n" +
                "profile --show notifications\n" +
                "back\n" +
                "exit");
    }

}
