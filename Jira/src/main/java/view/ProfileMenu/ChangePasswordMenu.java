package view.ProfileMenu;

import controller.MainMenuController;
import controller.ProfileMenuController.ChangePasswordMenuController;
import view.MainMenu;
import view.MenuController;
import view.Menus;
import view.Regex;

import java.sql.SQLException;
import java.util.regex.Matcher;

public class ChangePasswordMenu {
    public static void execute(String command) throws SQLException {
        Matcher matcher;
        if ((matcher = Regex.getCommandMatcher(command,Regex.CHANGE_PASSWORD)).matches())
            ChangePasswordMenuController.changePassword(matcher.group(1),matcher.group(2));
        else if ((matcher = Regex.getCommandMatcher(command,Regex.BACK)).matches()){
            MenuController.currentMenu = Menus.PROFILE_MENU;
            ProfileMenu.showProfileMenu();
            ChangePasswordMenuController.setCounter(0);
        }else
            System.out.println("Invalid Command");
    }
}
