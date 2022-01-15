package view;

import controller.ProfileMenuController;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class ProfileMenu {

    public static void execute(String command) throws SQLException {
        Matcher matcher;

        if ((matcher = Regex.getCommandMatcher(command, Regex.CHANGE_PASSWORD)).matches()) {
            System.out.println(ProfileMenuController.changePassword(matcher.group(1), matcher.group(2)));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.CHANGE_USERNAME)).matches()) {
            System.out.println(ProfileMenuController.changeUsername(matcher.group(1)));
        } else if (Regex.getCommandMatcher(command, Regex.SHOW_TEAMS).matches()) {
            ProfileMenuController.showTeams();
        } else if (Regex.getCommandMatcher(command, Regex.SHOW_TEAM).matches()) {
            ProfileMenuController.showTeam(matcher.group(1));
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_PROFILE)).matches()) {
            ProfileMenuController.showMyProfile();
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_LOG)).matches()) {
            ProfileMenuController.showLogs();
        } else if ((matcher = Regex.getCommandMatcher(command, Regex.SHOW_NOTIFICATIONS)).matches()) {
            ProfileMenuController.showNotifications();
        } else if (command.equals("exit")) {
            MenuController.currentMenu = Menus.EXIT;
        }

    }
}
